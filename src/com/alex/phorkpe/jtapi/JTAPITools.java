package com.alex.phorkpe.jtapi;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.alex.phorkpe.axl.axlTools;
import com.alex.phorkpe.utils.UsefulMethod;
import com.alex.phorkpe.utils.Variables;

public class JTAPITools
	{
	
	/**
	 * Will initialize the JTAPI connection
	 */
	public static JTAPIConnection initJTAPIConnection() throws Exception
		{
		if(Variables.getJtapiConnection() == null)
			{
			Variables.setJtapiConnection(new JTAPIConnection(UsefulMethod.getTargetOption("cucmip"),
					UsefulMethod.getTargetOption("senduser"),
					UsefulMethod.getTargetOption("sendpassword")));
			
			return Variables.getJtapiConnection();
			}
		else
			{
			Variables.getLogger().debug("JTAPI connection already up");
			return Variables.getJtapiConnection();
			}
		}
	
	/**
	 * Will start a line control
	 */
	public static JTAPILine startLineControl(String lineNumber, JPanel panel) throws Exception
		{
		Variables.getLogger().debug("Starting control for line : "+lineNumber);
		/***
		 * We need to associate the line's device to the PHORKPE user to allow to control it
		 * So first we find that device name using AXL
		 */
		String phoneName = axlTools.getPhoneFromLineNumber(lineNumber);
		Variables.getLogger().debug(lineNumber+" : The associated phone is : "+phoneName);
		ArrayList<String> phoneList = new ArrayList<String>();
		phoneList.add(phoneName);
		axlTools.associatePhoneToUser(Variables.getUser(), phoneList);
		/********/
		
		if(Variables.getJtapiConnection() == null)JTAPITools.initJTAPIConnection();
		//Getting the line from the provider
		JTAPILine line = new JTAPILine(Variables.getJtapiConnection().getProvider().getAddress(lineNumber));
		Variables.getLineList().add(line);
		return line;
		}
	
	/**
	 * Will stop a line control
	 */
	public static void stopLineControl(String lineNumber) throws Exception
		{
		JTAPILine line = UsefulMethod.getJTAPILine(lineNumber);
		line.dispose();
		Variables.getLineList().remove(line);
		
		/**
		 * We also dissociate the phone from the application user
		 */
		axlTools.dissociateFromUser(Variables.getUser());
		/******/
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
