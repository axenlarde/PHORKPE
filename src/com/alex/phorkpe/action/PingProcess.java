package com.alex.phorkpe.action;

import java.net.InetAddress;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.Variables.statusType;

/**
 * used to ping a device
 *
 * @author Alexandre RATEL
 */
public class PingProcess extends Thread
	{
	/**
	 * Variables
	 */
	private Device device;
	private int timeout;

	public PingProcess(Device device, int timeout)
		{
		super();
		this.device = device;
		this.timeout = timeout;
		}
	
	public void run()
		{
		if(ping(device.getIp()))
			{
			Variables.getLogger().debug(device.getIp()+" : Ping success !");
			}
		else
			{
			Variables.getLogger().debug(device.getIp()+" : Ping failed");
			device.setStatus(statusType.error);
			device.setStatusDesc("Ping failed");
			}
		}
	
	private boolean ping(String ip)
		{
		try
			{
			InetAddress inet = InetAddress.getByName(ip);
			return inet.isReachable(timeout);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while pinging "+device.getIp());
			}
		return false;
		}
	
	/*2019*//*RATEL Alexandre 8)*/
	}
