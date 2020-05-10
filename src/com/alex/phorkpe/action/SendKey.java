package com.alex.phorkpe.action;

import java.util.regex.Pattern;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.misc.KeyPress;
import com.alex.phorkpe.misc.KeyPress.KeyType;
import com.alex.phorkpe.utils.RESTGear;
import com.alex.phorkpe.utils.RESTGear.requestType;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables.statusType;
import com.alex.phorkpe.utils.Variables;

/**
 * Thread responsible of sending key press command to one phone
 *
 * @author Alexandre RATEL
 */
public class SendKey extends Thread
	{
	/**
	 * Variables
	 */
	private Device device;

	public SendKey(Device device)
		{
		super();
		this.device = device;
		}
	
	public void run()
		{
		Variables.getLogger().debug("Device "+device.getIp()+" press key request started");
		device.setStatus(statusType.processing);
		
		String uri = "http://"+device.getIp()+"/CGI/Execute";
		
		try
			{
			for(KeyPress kp : device.getKeyPressProfile().getKeyList())
				{
				if(kp.getType().equals(KeyType.wait))
					{
					this.sleep(Long.parseLong(kp.getKey()));
					}
				else
					{
					String content = "XML=<CiscoIPPhoneExecute>"
						+ "	<ExecuteItem Priority=\""+device.getKeyPressProfile().getPriority()+"\" URL=\"Key:"+kp.getKey()+"\"/>"
						+ "</CiscoIPPhoneExecute>";
					
					content = UsefulMethod.escapeHTML(content);
					
					String reply = RESTGear.httpSend(requestType.POST, uri, content, device.getUser(), device.getPassword(), "text/xml", device.getTimeout());
					
					if(Pattern.matches(".*Success.*", reply))
						{
						device.setStatus(statusType.done);
						Variables.getLogger().debug("Device "+device.getIp()+" key sent !");
						}
					else
						{
						Variables.getLogger().debug("Device "+device.getIp()+" key sent with error : "+reply);
						throw new Exception(reply);
						}
					}
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error(device.getIp()+" : ERROR while sending keys, aborting : "+e.getMessage(),e);
			device.setStatusDesc(e.getMessage());
			device.setStatus(statusType.error);
			}
		}

	public Device getDevice()
		{
		return device;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
