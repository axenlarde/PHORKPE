package com.alex.phorkpe.axl;

import java.util.ArrayList;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.Variables.cucmAXLVersion;
import com.alex.phorkpe.utils.Variables.statusType;
import com.cisco.axl.api._10.DoDeviceResetReq;
import com.cisco.axl.api._10.UpdateAppUserReq;
import com.cisco.axl.api._10.XFkType;
import com.cisco.axlapiservice10.AXLError;

public class axlTools
	{
	
	/**
	 * Used to associate the given device list to the dedicated application user
	 * @throws Exception 
	 */
	public synchronized static void associateDeviceToUser(String applicationUser, ArrayList<Device> deviceList) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			associateDeviceToUserV105(applicationUser, deviceList);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void associateDeviceToUserV105(String applicationUser, ArrayList<Device> deviceList) throws Exception
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
			throw new Exception("ERROR while sending AssociatePhoneToUser AXL request : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to associate the given device list to the dedicated application user
	 * @throws Exception 
	 */
	public synchronized static void associatePhoneToUser(String applicationUser, ArrayList<String> phoneList) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			associatePhoneToUserV105(applicationUser, phoneList);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void associatePhoneToUserV105(String applicationUser, ArrayList<String> phoneList) throws Exception
		{
		try
			{
			com.cisco.axl.api._10.UpdateAppUserReq req = new com.cisco.axl.api._10.UpdateAppUserReq();
			com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices devices = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices();
			
			req.setUserid(applicationUser);
			
			for(String s : phoneList)
				{
				devices.getDevice().add(s);
				}
			
			req.setAssociatedDevices(devices);
			
			com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().updateAppUser(req);
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while sending AssociatePhoneToUser AXL request : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to dissociate the given device list from the dedicated application user
	 * @throws Exception 
	 */
	public synchronized static void dissociateFromUser(String applicationUser) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			dissociateFromUserV105(applicationUser);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void dissociateFromUserV105(String applicationUser) throws Exception
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
			throw new Exception("ERROR while sending DissociatePhoneFromUser AXL request : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to reset a phone
	 * @param deviceName
	 * @throws Exception 
	 */
	public synchronized static void resetDevice(String deviceName) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			resetDeviceV105(deviceName);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void resetDeviceV105(String deviceName) throws Exception
		{
		try
			{
			if(Variables.getAXLConnectionToCUCMV105() == null)UsefulMethod.initAXLConnectionToCUCM();
		
			com.cisco.axl.api._10.DoDeviceResetReq req = new com.cisco.axl.api._10.DoDeviceResetReq();
		
			req.setDeviceName(getPhoneUUIDV105(deviceName));
			req.setDeviceResetType("Reset");
			
			com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().doDeviceReset(req);
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while sending ResetDevice AXL request : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to restart a phone
	 * @param deviceName
	 * @throws Exception 
	 */
	public synchronized static void restartDevice(String deviceName) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			restartDeviceV105(deviceName);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static void restartDeviceV105(String deviceName) throws Exception
		{
		if(Variables.getAXLConnectionToCUCMV105() == null)UsefulMethod.initAXLConnectionToCUCM();
		
		com.cisco.axl.api._10.DoDeviceResetReq req = new com.cisco.axl.api._10.DoDeviceResetReq();
	
		req.setDeviceName(getPhoneUUIDV105(deviceName));
		req.setDeviceResetType("Restart");
		
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().doDeviceReset(req);
		}
	
	private synchronized static com.cisco.axl.api._10.XFkType getPhoneUUIDV105(String deviceName) throws AXLError
		{
		com.cisco.axl.api._10.GetPhoneReq req = new com.cisco.axl.api._10.GetPhoneReq();
		com.cisco.axl.api._10.RPhone returnedTags = new com.cisco.axl.api._10.RPhone();
		req.setName(deviceName);
		returnedTags.setUuid("");
		req.setReturnedTags(returnedTags);
		com.cisco.axl.api._10.GetPhoneRes resp = Variables.getAXLConnectionToCUCMV105().getPhone(req);//We send the request to the CUCM
		com.cisco.axl.api._10.XFkType xfk = new com.cisco.axl.api._10.XFkType();
		xfk.setUuid(resp.getReturn().getPhone().getUuid());
		return xfk;
		}
	
	public static String getPhoneFromLineNumber(String lineNumber) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			return getPhoneFromLineNumberV105(lineNumber);
			}
		else
			{
			throw new Exception("unsupported AXL version");
			}
		}
	
	private static String getPhoneFromLineNumberV105(String lineNumber) throws Exception
		{
		//To be written
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
