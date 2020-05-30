package com.alex.phorkpe.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.alex.phorkpe.jtapi.JTAPIConnection;
import com.alex.phorkpe.jtapi.JTAPITerminal;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.misc.KeyPressProfile;

/**********************************
 * Used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class Variables
	{
	/**
	 * Variables
	 */
		
	/********************************************
	 * statusType :
	 ***************************************/
	public enum statusType
		{
		init,
		waiting,
		processing,
		done,
		disabled,
		error
		};
		
	/********************************************
	 * cucmAXLVersion :
	 * Is used to set the cucm AXL version used for the injection
	 ***************************************/
	public enum cucmAXLVersion
		{
		version80,
		version85,
		version90,
		version91,
		version100,
		version105,
		version110,
		version115,
		version120,
		version125
		};
	
	//Misc
	private static String softwareName;
	private static String softwareVersion;
	private static Logger logger;
	private static ArrayList<String[][]> tabConfig;
	private static String mainDirectory;
	private static String configFileName;
	private static String deviceListFileName;
	private static String keyPressProfileListFileName;
	private static String resultFileName;
	private static ArrayList<Device> deviceList;
	private static ArrayList<KeyPressProfile> keyPressprofileList;
	private static JTAPIConnection jtapiConnection;
	private static String user;
	private static String password;
	private static String JTAPIHost;
	private static int timeout;
	private static ArrayList<JTAPITerminal> terminalList;
	private static boolean CUCMReachable;
	private static cucmAXLVersion CUCMVersion;
	private static com.cisco.axlapiservice10.AXLPort AXLConnectionToCUCMV105;//Connection to CUCM version 105
	private static boolean associatePhoneUsingAXL;
    
	//Langage management
	public enum language{english,french};
	private static String languageFileName;
	private static ArrayList<ArrayList<String[][]>> languageContentList;
	
    /**************
     * Constructor
     **************/
	public Variables()
		{
		mainDirectory = ".";
		configFileName = "configFile.xml";
		deviceListFileName = "deviceList.csv";
		keyPressProfileListFileName = "keyPressProfileList.xml";
		resultFileName = "result.csv";
		languageFileName = "languages.xml";
		terminalList = new ArrayList<JTAPITerminal>();
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		Variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		Variables.softwareVersion = softwareVersion;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		Variables.logger = logger;
		}

	public static ArrayList<String[][]> getTabConfig()
		{
		return tabConfig;
		}

	public static void setTabConfig(ArrayList<String[][]> tabConfig)
		{
		Variables.tabConfig = tabConfig;
		}

	public static String getMainDirectory()
		{
		return mainDirectory;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static String getDeviceListFileName()
		{
		return deviceListFileName;
		}

	public static String getKeyPressProfileListFileName()
		{
		return keyPressProfileListFileName;
		}

	public static String getResultFileName()
		{
		return resultFileName;
		}

	public static ArrayList<Device> getDeviceList()
		{
		return deviceList;
		}

	public static void setDeviceList(ArrayList<Device> deviceList)
		{
		Variables.deviceList = deviceList;
		}

	public static ArrayList<KeyPressProfile> getKeyPressprofileList()
		{
		return keyPressprofileList;
		}

	public static void setKeyPressprofileList(ArrayList<KeyPressProfile> keyPressprofileList)
		{
		Variables.keyPressprofileList = keyPressprofileList;
		}

	public synchronized static JTAPIConnection getJtapiConnection() throws Exception
		{
		return jtapiConnection;
		}

	public static String getUser()
		{
		return user;
		}

	public static void setUser(String user)
		{
		Variables.user = user;
		}

	public static String getPassword()
		{
		return password;
		}

	public static void setPassword(String password)
		{
		Variables.password = password;
		}

	public static String getJTAPIHost()
		{
		return JTAPIHost;
		}

	public static void setJTAPIHost(String jTAPIHost)
		{
		JTAPIHost = jTAPIHost;
		}

	public static int getTimeout()
		{
		return timeout;
		}

	public static void setTimeout(int timeout)
		{
		Variables.timeout = timeout;
		}

	public static void setJtapiConnection(JTAPIConnection jtapiConnection)
		{
		Variables.jtapiConnection = jtapiConnection;
		}

	public static ArrayList<JTAPITerminal> getTerminalList()
		{
		return terminalList;
		}

	public static void setTerminalList(ArrayList<JTAPITerminal> terminalList)
		{
		Variables.terminalList = terminalList;
		}

	public static boolean isCUCMReachable()
		{
		return CUCMReachable;
		}

	public static void setCUCMReachable(boolean cUCMReachable)
		{
		CUCMReachable = cUCMReachable;
		}

	public static cucmAXLVersion getCUCMVersion()
		{
		if(CUCMVersion == null)
			{
			//It has to be initiated
			try
				{
				CUCMVersion = UsefulMethod.convertStringToCUCMAXLVersion(UsefulMethod.getTargetOption("axlversion"));
				Variables.getLogger().info("CUCM version : "+Variables.getCUCMVersion());
				}
			catch(Exception e)
				{
				getLogger().debug("The AXL version couldn't be parsed. We will use the default version", e);
				CUCMVersion = cucmAXLVersion.version105;
				}
			}
		return CUCMVersion;
		}

	public static void setCUCMVersion(cucmAXLVersion cUCMVersion)
		{
		CUCMVersion = cUCMVersion;
		}

	public synchronized static com.cisco.axlapiservice10.AXLPort getAXLConnectionToCUCMV105()
		{
		return AXLConnectionToCUCMV105;
		}

	public static void setAXLConnectionToCUCMV105(com.cisco.axlapiservice10.AXLPort aXLConnectionToCUCMV105)
		{
		AXLConnectionToCUCMV105 = aXLConnectionToCUCMV105;
		}

	public synchronized static boolean isAssociatePhoneUsingAXL()
		{
		return associatePhoneUsingAXL;
		}

	public static void setAssociatePhoneUsingAXL(boolean associatePhoneUsingAXL)
		{
		Variables.associatePhoneUsingAXL = associatePhoneUsingAXL;
		}
	
	public static ArrayList<ArrayList<String[][]>> getLanguageContentList() throws Exception
		{
		if(languageContentList == null)
			{
			Variables.getLogger().debug("Initialisation of languageContentList");
			Variables.setLanguageContentList(UsefulMethod.readExtFile("language", Variables.getLanguageFileName()));
			}
		
		return languageContentList;
		}

	public static void setLanguageContentList(
			ArrayList<ArrayList<String[][]>> languageContentList)
		{
		Variables.languageContentList = languageContentList;
		}

	public static String getLanguageFileName()
		{
		return languageFileName;
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}

