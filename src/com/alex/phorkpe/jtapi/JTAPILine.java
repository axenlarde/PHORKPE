package com.alex.phorkpe.jtapi;

import javax.telephony.Address;
import javax.telephony.AddressObserver;
import javax.telephony.Terminal;
import javax.telephony.TerminalObserver;
import javax.telephony.callcontrol.CallControlCallObserver;
import javax.telephony.events.AddrEv;
import javax.telephony.events.CallEv;
import javax.telephony.events.TermEv;

import com.alex.phorkpe.utils.Variables;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;

/**
 * A phone line controlled using JTAPI
 *
 * @author Alexandre RATEL
 */
public class JTAPILine implements AddressObserver, TerminalObserver, CallControlCallObserver
	{
	/**
	 * Variables
	 */
	private Address line;
	private boolean lineReady, terminalReady, ready;
	
	public JTAPILine(Address line) throws Exception
		{
		super();
		this.line = line;
		
		//Adding the observers
		this.line.addCallObserver(this);
		this.line.addObserver(this);
		this.line.getTerminals()[0].addObserver(this);
		}
	
	/**
	 * To stop the monitoring
	 */
	public void dispose()
		{
		Variables.getLogger().debug(line.getName()+" : disposing the line");
		line.removeCallObserver(this);
		line.removeObserver(this);
		line.getTerminals()[0].removeObserver(this);
		Variables.getLogger().debug(line.getName()+" : line dispose done");
		}

	@Override
	public void terminalChangedEvent(TermEv[] eventList)
		{
		if (eventList != null)
			{
			for (int i = 0; i < eventList.length; i++)
				{
				Terminal terminal = eventList[i].getTerminal();
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

	@Override
	public void addressChangedEvent(AddrEv[] eventList)
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void callChangedEvent(CallEv[] eventList)
		{
		// TODO Auto-generated method stub
		
		}

	public Address getLine()
		{
		return line;
		}

	public boolean isLineReady()
		{
		return lineReady;
		}

	public boolean isTerminalReady()
		{
		return terminalReady;
		}

	public boolean isReady()
		{
		return ready;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
