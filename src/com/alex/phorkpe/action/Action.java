package com.alex.phorkpe.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.alex.phorkpe.action.SendKey.SendMethod;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.Variables.statusType;

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
			/**
			 * First we ping the devices to check if they are reachable
			 */
			if(Boolean.parseBoolean(UsefulMethod.getTargetOption("pingdevices")))
				{
				ArrayList<Thread> PingThreadList = new ArrayList<Thread>();
				int pingTimeout = Integer.parseInt(UsefulMethod.getTargetOption("pingtimeout"));
				for(Device d : Variables.getDeviceList())
					{
					PingThreadList.add(new PingProcess(d,pingTimeout));
					}
				
				ThreadManager PTM = new ThreadManager(Integer.parseInt(UsefulMethod.getTargetOption("maxthread")), 100, PingThreadList);
				PTM.start();
				Variables.getLogger().debug("Thread Manager started !");
				
				/**
				 * We wait till the ThreadManager ends
				 */
				while(PTM.isAlive())
					{
					this.sleep(100);
					}
				}
			
			/**
			 * We feed the Thread Manager then start it
			 */
			ArrayList<Thread> keyPressThreadList = new ArrayList<Thread>();
			SendMethod method = SendMethod.valueOf(UsefulMethod.getTargetOption("sendmethod"));
			
			for(Device d : Variables.getDeviceList())
				{
				if((d.getStatus().equals(statusType.error)) || (d.getStatus().equals(statusType.disabled)))
					{
					Variables.getLogger().debug("Reminder, device '"+d.getIp()+"' has been disabled and therefore will not be processed");
					}
				else
					{
					if(method.equals(SendMethod.jtapi))keyPressThreadList.add(new SendKeyJTAPI(d));
					else keyPressThreadList.add(new SendKeyHTTP(d));//Default HTTP
					}
				}
			
			ThreadManager KPTM = new ThreadManager(Integer.parseInt(UsefulMethod.getTargetOption("maxthread")), 100, keyPressThreadList);
			KPTM.start();
			Variables.getLogger().debug("Thread Manager starts !");
			
			/**
			 * We wait till the ThreadManager ends
			 */
			while(KPTM.isAlive())
				{
				this.sleep(100);
				}
			
			Variables.getLogger().debug("Thread Manager ends !");
			
			/**
			 * We eventually shut down the JTAPI connection
			 */
			if(Variables.getJtapiConnection() != null)Variables.getJtapiConnection().disconnect();
			
			/**
			 * We finish by writing the result file
			 */
			Variables.getLogger().debug("Writing the result file");
			String splitter = UsefulMethod.getTargetOption("csvsplitter");
			String firstLine = "Device IP;Device Name;KeyPressProfile;Status;Description\r\n";
			
			BufferedWriter buffReport = new BufferedWriter(new FileWriter(new File(Variables.getMainDirectory()+"/"+Variables.getResultFileName()), false));
			buffReport.write(firstLine);
			for(Device d : Variables.getDeviceList())
				{
				if((d.getStatus().equals(statusType.error)) || (d.getStatus().equals(statusType.disabled)))
					{
					buffReport.write(d.getIp()+splitter+
							d.getName()+splitter+
							d.getKeyPressProfile().getName()+splitter+
							d.getStatus().name()+splitter+
							d.getStatusDesc()+"\r\n");
					}
				}
			buffReport.flush();
			buffReport.close();
			
			Variables.getLogger().debug("Result file written with success !");
			Variables.getLogger().debug("END");
			System.exit(0);
			}
		catch (Exception e)
			{
			Variables.getLogger().debug("ERROR : "+e.getMessage(),e);
			}
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
