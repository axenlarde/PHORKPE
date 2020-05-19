package com.alex.phorkpe.action;


import com.alex.phorkpe.axl.axlTools;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.misc.KeyPress;
import com.alex.phorkpe.misc.KeyPress.KeyType;
import com.alex.phorkpe.utils.Variables.statusType;
import com.alex.phorkpe.utils.Variables;

/**
 * Thread responsible of sending key press command to one phone
 *
 * @author Alexandre RATEL
 */
public abstract class SendKey extends Thread implements SendKeyInt
	{
	/**
	 * Variables
	 */
	public enum SendMethod
		{
		http,
		jtapi
		};
	
	protected Device device;

	public SendKey(Device device)
		{
		super();
		this.device = device;
		}
	
	public void run()
		{
		Variables.getLogger().debug(device.getInfo()+" : Press key request started");
		
		try
			{
			device.setStatus(statusType.processing);
			
			for(KeyPress kp : device.getKeyPressProfile().getKeyList())
				{
				if(kp.getType().equals(KeyType.wait))
					{
					Variables.getLogger().debug(device.getInfo()+" : Waiting "+kp.getKey()+" ms");
					this.sleep(Integer.parseInt(kp.getKey()));
					}
				else if(kp.getType().equals(KeyType.reset))
					{
					Variables.getLogger().debug(device.getInfo()+" : Reseting");
					axlTools.resetDevice(device.getName());
					}
				else if(kp.getType().equals(KeyType.restart))
					{
					Variables.getLogger().debug(device.getInfo()+" : Restarting");
					axlTools.restartDevice(device.getName());
					}
				else
					{
					String content = buildKeyPressRequest(device, kp);
					
					send(content);//Sending the request
					Variables.getLogger().debug(device.getInfo()+" : Key '"+kp.getKey()+"' sent with success !");
					}
				
				this.sleep(device.getKeyPressProfile().getDefaultInterCommandTimer());
				}
			device.setStatus(statusType.done);
			}
		catch (Exception e)
			{
			Variables.getLogger().error(device.getInfo()+" : ERROR while sending keys, aborting : "+e.getMessage(),e);
			device.setStatusDesc(e.getMessage());
			device.setStatus(statusType.error);
			}
		}
	
	/**
	 * Build the request to send
	 */
	protected String buildKeyPressRequest(Device device, KeyPress kp)
		{
		String content = "XML=<CiscoIPPhoneExecute>"
						+ "	<ExecuteItem Priority=\""+device.getKeyPressProfile().getPriority()+"\" URL=\"Key:"+kp.getKey()+"\"/>"
						+ "</CiscoIPPhoneExecute>";
		
		return content;
		}

	public Device getDevice()
		{
		return device;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
