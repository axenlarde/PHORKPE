package com.alex.phorkpe.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.alex.phorkpe.action.SendKey.SendMethod;
import com.alex.phorkpe.axl.axlTools;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;
import com.alex.phorkpe.utils.Variables.statusType;

public class Action extends Thread
	{
	/**
	 * Variables
	 */
	private ThreadManager PTM;
	private ThreadManager KPTM;
	private boolean run;
	
	public Action()
		{
		super();
		run = true;
		start();
		
		}
	
	public void run()
		{
		try
			{
			/**
			 * First we ping the devices to check if they are reachable
			 */
			if(Boolean.parseBoolean(UsefulMethod.getTargetOption("pingdevices")) && run)
				{
				ArrayList<Thread> PingThreadList = new ArrayList<Thread>();
				int pingTimeout = Integer.parseInt(UsefulMethod.getTargetOption("pingtimeout"));
				for(Device d : Variables.getDeviceList())
					{
					PingThreadList.add(new PingProcess(d,pingTimeout));
					}
				
				PTM = new ThreadManager(Integer.parseInt(UsefulMethod.getTargetOption("maxthread")), 100, PingThreadList);
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
			 * we first have to associate the phone to the dedicated application user
			 * They have to be associated all at the same time in the same AXL request
			 */
			SendMethod method = SendMethod.valueOf(UsefulMethod.getTargetOption("sendmethod"));
			if(Variables.isAssociatePhoneUsingAXL() && run)
				{
				Variables.getLogger().debug("Sending AXL request to associate the devices to the application user");
				UsefulMethod.initAXLConnectionToCUCM();
				axlTools.associatePhoneToUser(Variables.getUser(), Variables.getDeviceList());
				Variables.getLogger().debug("AXL request done with success !");
				}
			
			/**
			 * If JTAPI is asked we initiate the connection with the CUCM
			 */
			if(method.equals(SendMethod.jtapi) && run)
				{
				if(Variables.getJtapiConnection() == null)UsefulMethod.initJTAPIConnection();
				Variables.getLogger().debug("Waiting for the JTAPI Provider to detect the newly associated devices");
				this.sleep(500);
				while(Variables.getJtapiConnection().getProvider().getTerminals().length < Variables.getDeviceList().size())
					{
					this.sleep(500);	
					}
				Variables.getLogger().debug("The JTAPI provider detected the devices !");
				}
			
			/**
			 * We feed the Thread Manager then start it
			 */
			ArrayList<Thread> keyPressThreadList = new ArrayList<Thread>();
			
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
			
			if(run)
				{
				KPTM = new ThreadManager(Integer.parseInt(UsefulMethod.getTargetOption("maxthread")), 100, keyPressThreadList);
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
				}
			
			/**
			 * We eventually shut down the JTAPI and the AXL connection
			 */
			if(Variables.getJtapiConnection() != null)Variables.getJtapiConnection().disconnect();
			if(Variables.getAXLConnectionToCUCMV105() != null)shutDownAXLConnection();
			
			/**
			 * We display the result in the logs
			 */
			Variables.getLogger().debug("Final result :");
			StringBuffer buf = new StringBuffer("");
			for(Device d : Variables.getDeviceList())
				{
				buf.append(d.getInfo()+" : "+d.getStatus()+"\r\n");
				}
			Variables.getLogger().debug(buf.toString());
			
			/**
			 * We finish by writing the result file
			 */
			if(run)
				{
				Variables.getLogger().debug("Writing the result file");
				String splitter = UsefulMethod.getTargetOption("csvsplitter");
				String firstLine = "Device IP;Device Name;KeyPressProfile;Status;Description\r\n";
				
				BufferedWriter buffReport = new BufferedWriter(new FileWriter(new File(Variables.getMainDirectory()+"/"+Variables.getResultFileName()), false));
				buffReport.write(firstLine);
				for(Device d : Variables.getDeviceList())
					{
					buffReport.write(d.getIp()+splitter+
							d.getName()+splitter+
							d.getKeyPressProfile().getName()+splitter+
							d.getStatus().name()+splitter+
							d.getStatusDesc()+"\r\n");
					}
				buffReport.flush();
				buffReport.close();
				
				Variables.getLogger().debug("Result file written with success !");
				}
			
			Variables.getLogger().debug("END");
			
			if(!run)System.exit(0);
			}
		catch (Exception e)
			{
			Variables.getLogger().debug("ERROR : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to stop the application
	 */
	public void shutDown()
		{
		Variables.getLogger().debug("Trying to stop the main thread");
		this.run = false;
		if(PTM != null)PTM.shutDown();
		if(KPTM != null)KPTM.shutDown();
		}
	
	private void shutDownAXLConnection()
		{
		try
			{
			/**
			 * We remove the devices from the application user device list
			 */
			if(Variables.isAssociatePhoneUsingAXL())
				{
				Variables.getLogger().debug("Dissociating devices from the application user");
				axlTools.dissociatePhoneFromUser(Variables.getUser(), Variables.getDeviceList());
				Variables.getLogger().debug("Dissociating done !");
				}
			/**
			 * There is no method to shutdown the AXL connection
			 * I don't know how to do that, maybe it's automatic when the software ends ?
			 */
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while dissociating device from the application user : "+e.getMessage(),e);
			}
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
