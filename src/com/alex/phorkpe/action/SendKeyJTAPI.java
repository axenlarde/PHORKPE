package com.alex.phorkpe.action;

import com.alex.phorkpe.jtapi.JTAPITerminal;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;
import com.cisco.jtapi.extensions.CiscoTerminal;

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
public class SendKeyJTAPI extends SendKey
	{
	/**
	 * Variables
	 */
	
	public SendKeyJTAPI(Device device)
		{
		super(device);
		}

	@Override
	public void send(String content) throws Exception
		{
		JTAPITerminal terminal = UsefulMethod.getTerminal(device.getName());
		
		//We wait for the terminal to get ready
		while(!terminal.isTerminalReady())this.sleep(100);
		
		String reply = "";
		
		if(terminal.getTerminal().getState() == CiscoTerminal.IN_SERVICE)
			{
			reply = terminal.getTerminal().sendData(content);
			if(!reply.toLowerCase().contains("success"))throw new Exception(device.getInfo()+" : JTAPI : ERROR received while sending key");
			}
		else
			{
			throw new Exception(device.getInfo()+" : JTAPI : Could not send key, the device is not in service");
			}
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
