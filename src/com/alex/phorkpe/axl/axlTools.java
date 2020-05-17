package com.alex.phorkpe.axl;

import java.util.ArrayList;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.Variables.cucmAXLVersion;
import com.alex.phorkpe.utils.Variables.statusType;
import com.cisco.axl.api._10.UpdateAppUserReq;
import com.cisco.axlapiservice10.AXLError;

public class axlTools
	{
	
	/**
	 * Used to associate the given device list to the dedicated application user
	 * @throws Exception 
	 */
	public synchronized static void associatePhoneToUser(String applicationUser, ArrayList<Device> deviceList) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			associatePhoneToUserV105(applicationUser, deviceList);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void associatePhoneToUserV105(String applicationUser, ArrayList<Device> deviceList) throws Exception
		{
		try
			{
			com.cisco.axl.api._10.UpdateAppUserReq req = new com.cisco.axl.api._10.UpdateAppUserReq();
			com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices devices = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices();
			
			req.setUserid(applicationUser);
			
			for(Device d : deviceList)
				{
				if((d.getStatus().equals(statusType.error)) || (d.getStatus().equals(statusType.disabled)))
					{
					Variables.getLogger().debug("Reminder, device '"+d.getIp()+"' has been disabled and therefore will not be processed");
					}
				else
					{
					devices.getDevice().add(d.getName());
					}
				}
			
			req.setAssociatedDevices(devices);
			
			com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().updateAppUser(req);
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while sending the AXL request : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to dissociate the given device list from the dedicated application user
	 * @throws Exception 
	 */
	public synchronized static void dissociatePhoneFromUser(String applicationUser, ArrayList<Device> deviceList) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			dissociatePhoneFromUserV105(applicationUser, deviceList);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void dissociatePhoneFromUserV105(String applicationUser, ArrayList<Device> deviceList) throws Exception
		{
		try
			{
			com.cisco.axl.api._10.UpdateAppUserReq req = new com.cisco.axl.api._10.UpdateAppUserReq();
			com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices devices = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices();
			
			req.setUserid(applicationUser);
			
			req.setAssociatedDevices(devices);//We give an empty device list which should remove all the devices
			
			com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().updateAppUser(req);
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while sending the AXL request : "+e.getMessage(),e);
			}
		}
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
