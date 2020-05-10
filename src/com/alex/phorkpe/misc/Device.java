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
	private String ip;
	private KeyPressProfile keyPressProfile;
	private statusType status;
	private String statusDesc, user, password;
	private int timeout;
	
	public Device(String ip, KeyPressProfile keyPressProfile, String user, String password, int timeout)
		{
		super();
		this.ip = ip;
		this.keyPressProfile = keyPressProfile;
		this.user = user;
		this.password = password;
		this.timeout = timeout;
		this.status = statusType.init;
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

	public String getUser()
		{
		return user;
		}

	public String getPassword()
		{
		return password;
		}

	public int getTimeout()
		{
		return timeout;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
