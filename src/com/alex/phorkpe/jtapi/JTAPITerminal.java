package com.alex.phorkpe.jtapi;

import javax.telephony.events.TermEv;

import com.alex.phorkpe.utils.Variables;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;
import com.cisco.jtapi.extensions.CiscoTerminalObserver;

/**
 * Used to store a terminal and its observer
 *
 * @author Alexandre RATEL
 */
public class JTAPITerminal implements CiscoTerminalObserver
	{
	/**
	 * Variables
	 */
	private CiscoTerminal terminal;
	private boolean terminalReady;

	public JTAPITerminal(CiscoTerminal terminal) throws Exception
		{
		super();
		this.terminal = terminal;
		this.terminalReady = false;
		
		this.terminal.addObserver(this);
		}

	@Override
	public void terminalChangedEvent(TermEv[] eventList)
		{
		if (eventList != null)
			{
			for (int i = 0; i < eventList.length; i++)
				{
				CiscoTerminal terminal = (CiscoTerminal) eventList[i].getTerminal();
				switch(eventList[i].getID())
					{
					case CiscoTermInServiceEv.ID:
						{
						Variables.getLogger().debug(terminal.getName()+" : JTAPI : Terminal ready "+terminal.getName());
						terminalReady = true;
						break;
						}
					case CiscoTermOutOfServiceEv.ID:
						{
						terminalReady = false;
						break;
						}
					}
				}
			}
		}

	public CiscoTerminal getTerminal()
		{
		return terminal;
		}

	public boolean isTerminalReady()
		{
		return terminalReady;
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
