package com.alex.phorkpe.misc;

import com.alex.phorkpe.utils.Variables.statusType;

/**
 * Device
 *
 * @author Alexandre RATEL
 */

public class Device
	{
	/**
	 * Variables
	 */
	private String ip, name;
	private KeyPressProfile keyPressProfile;
	private statusType status;
	private String statusDesc;
	
	public Device(String ip, String name, KeyPressProfile keyPressProfile)
		{
		super();
		this.ip = ip;
		this.name = name;
		this.keyPressProfile = keyPressProfile;
		this.status = statusType.init;
		}

	public String getInfo()
		{
		return name+" : "+ip;
		}
	
	public String getIp()
		{
		return ip;
		}

	public KeyPressProfile getKeyPressProfile()
		{
		return keyPressProfile;
		}

	public statusType getStatus()
		{
		return status;
		}

	public void setStatus(statusType status)
		{
		this.status = status;
		}

	public String getStatusDesc()
		{
		return statusDesc;
		}

	public void setStatusDesc(String statusDesc)
		{
		this.statusDesc = statusDesc;
		}

	public String getName()
		{
		return name;
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
