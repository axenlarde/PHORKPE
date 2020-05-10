package com.alex.phorkpe.action;

import java.util.ArrayList;

import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;

public class Action extends Thread
	{
	/**
	 * Variables
	 */
	
	
	public Action()
		{
		super();
		start();
		}
	
	public void run()
		{
		try
			{
			ArrayList<Thread> threadList = new ArrayList<Thread>();
			for(Device d : Variables.getDeviceList())
				{
				threadList.add(new SendKey(d));
				}
			ThreadManager TM = new ThreadManager(Integer.parseInt(UsefulMethod.getTargetOption("maxthread")), 100, threadList);
			TM.start();
			
			/**
			 * We wait till the ThreadManager ends
			 */
			while(TM.isAlive())
				{
				this.sleep(100);
				}
			
			/**
			 * We finish by writing the result file
			 */
			//To be written
			}
		catch (Exception e)
			{
			Variables.getLogger().debug("ERROR : "+e.getMessage(),e);
			}
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
