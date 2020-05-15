package com.alex.phorkpe.action;

import javax.telephony.events.TermEv;

import com.alex.phorkpe.jtapi.JTAPIConnection;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;
import com.cisco.jtapi.extensions.CiscoTermInServiceEv;
import com.cisco.jtapi.extensions.CiscoTermOutOfServiceEv;
import com.cisco.jtapi.extensions.CiscoTerminal;
import com.cisco.jtapi.extensions.CiscoTerminalObserver;

/**
 * Used to send key press using JTAPI
 * 
 * Use the CUCM server as a relay to send keys to the phones.
 * Has we authenticate to the server using JTAPI, we do not need additional phone authentication
 * So it allows to fix phone certificate issues but is much slower.
 * Indeed, we must not harassed the same phone with JTAPI request as mentioned in JTAPI the documentation :
 * 
 * Limitation with sendData() API on CiscoTerminal :
 * If JTAPI applications make simultaneous back to back requests for sendData() API on the same CiscoTerminal,
 * without any delay between requests, then some of these requests may fail. Applications cannot determine
 * whether a request was successful or not, as Cisco JTAPI API returns successfully as soon as the phone receives
 * data and does not wait for a response from the phone. Also, the IP phone might display a blank screen on
 * sending simultaneous requests to send data.
 * To avoid these issues, JTAPI applications should ensure some time delay between two successive sendData()
 * requests while pushing XSI data to the IP phones via Cisco JTAPI
 *
 * @author Alexandre RATEL
 */
public class SendKeyJTAPI extends SendKey implements CiscoTerminalObserver
	{
	/**
	 * Variables
	 */
	private boolean terminalReady;
	
	public SendKeyJTAPI(Device device)
		{
		super(device);
		terminalReady = false;
		}

	@Override
	public void send(String content) throws Exception
		{
		JTAPIConnection connection = Variables.getJtapiConnection();
		if(connection == null)
			{
			connection = new JTAPIConnection(UsefulMethod.getTargetOption("jtapihost"),
					UsefulMethod.getTargetOption("senduser"),
					UsefulMethod.getTargetOption("sendpassword"));
			
			Variables.setJtapiConnection(connection);
			}
		
		CiscoTerminal terminal = (CiscoTerminal) connection.getProvider().getTerminal(device.getName());
		terminal.addObserver(this);
		
		
		//Wait for the terminal to get ready
		Variables.getLogger().debug(device.getInfo()+" : JTAPI : Waiting for terminal to get ready");
		while(!terminalReady)this.sleep(100);
		
		String reply = "";
		
		if(terminal.getState() == CiscoTerminal.IN_SERVICE)
			{
			reply = terminal.sendData(content);
			//Variables.getLogger().debug(device.getInfo()+" : Key sent using JTAPI and reply is : "+reply);
			if(reply.toLowerCase().contains("success"))Variables.getLogger().debug(device.getInfo()+" : JTAPI : Key sent with success");
			else throw new Exception(device.getInfo()+" : JTAPI : ERROR received while sending key");
			}
		else
			{
			throw new Exception(device.getInfo()+" : JTAPI : Could not send key, the device is not in service");
			}
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
						Variables.getLogger().debug(device.getInfo()+" : JTAPI : Terminal ready "+terminal.getName());
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
	
	/*2020*//*RATEL Alexandre 8)*/
	}
