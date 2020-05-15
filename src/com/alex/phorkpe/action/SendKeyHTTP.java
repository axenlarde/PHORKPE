package com.alex.phorkpe.action;

import java.util.regex.Pattern;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.RESTGear;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.RESTGear.requestType;

/**
 * Used to send key press using direct HTTP to the phone
 * 
 * Allow to address much more phone at the same time but need authentication
 * So cannot be used to fix certificate issue
 *
 * @author Alexandre RATEL
 */
public class SendKeyHTTP extends SendKey
	{
	/**
	 * Variables
	 */
	
	public SendKeyHTTP(Device device)
		{
		super(device);
		}

	@Override
	public void send(String content) throws Exception
		{
		String uri = "http://"+device.getIp()+"/CGI/Execute";
		
		String reply = RESTGear.httpSend(requestType.POST, uri, content, Variables.getUser(), Variables.getPassword(), "text/xml", Variables.getTimeout());
					
		if(Pattern.matches(".*Success.*", reply))
			{
			Variables.getLogger().debug(device.getInfo()+" : HTTP key sent with success");
			}
		else
			{
			throw new Exception(device.getInfo()+" : HTTP key returned an error : "+reply);
			}
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
