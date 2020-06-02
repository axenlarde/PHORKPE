package com.alex.phorkpe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFileChooser;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Level;

import com.alex.phorkpe.jtapi.JTAPIConnection;
import com.alex.phorkpe.jtapi.JTAPILine;
import com.alex.phorkpe.jtapi.JTAPITerminal;
import com.alex.phorkpe.misc.Device;
import com.alex.phorkpe.misc.KeyPress;
import com.alex.phorkpe.misc.KeyPress.KeyType;
import com.alex.phorkpe.misc.KeyPressProfile;
import com.alex.phorkpe.utils.Variables.cucmAXLVersion;
import com.cisco.jtapi.extensions.CiscoTerminal;

/**********************************
 * Class used to store the useful static methods
 * 
 * @author RATEL Alexandre
 **********************************/
public class UsefulMethod
	{
	
	/*****
	 * Method used to read the main config file
	 * @throws Exception 
	 */
	public static ArrayList<String[][]> readMainConfigFile(String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			file = xMLReader.fileRead("./"+fileName);
			
			listParams.add("config");
			return xMLGear.getResultListTab(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the file : "+fileName+" : "+exc.getMessage());
			}
		
		}
	
	/***************************************
	 * Method used to get a specific value
	 * in the user preference XML File
	 ***************************************/
	public synchronized static String getTargetOption(String node) throws Exception
		{
		//We first seek in the configFile.xml
		for(String[] s : Variables.getTabConfig().get(0))
			{
			if(s[0].equals(node))return s[1];
			}
		
		/***********
		 * If this point is reached, the option looked for was not found
		 */
		throw new Exception("Option \""+node+"\" not found"); 
		}
	/*************************/
	
	
	
	/************************
	 * Check if java version
	 * is correct
	 ***********************/
	public static void checkJavaVersion()
		{
		try
			{
			String jVer = new String(System.getProperty("java.version"));
			Variables.getLogger().info("Detected JRE version : "+jVer);
			
			/*Need to use the config file value*/
			
			if(jVer.contains("1.6"))
				{
				/*
				if(Integer.parseInt(jVer.substring(6,8))<16)
					{
					Variables.getLogger().info("JRE version is not compatible. The application will now exit. system.exit(0)");
					System.exit(0);
					}*/
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().info("ERROR : It has not been possible to detect JRE version",exc);
			}
		}
	/***********************/
	
	/*********************************************
	 * Used to get a file path
	 * @throws Exception 
	 *********************************************/
	public static String getFilePath(ArrayList<String> allowedExtensionList, String invitPhrase, String selectButton) throws Exception
		{
		JFileChooser fcSource = new JFileChooser();
		try
			{
			fcSource.setCurrentDirectory(new File(Variables.getMainDirectory()));
			
			fcSource.setDialogTitle(invitPhrase);
			
			EasyFileFilter myFilter = new EasyFileFilter(allowedExtensionList);
			fcSource.setFileFilter(myFilter);
			
			int resultat = fcSource.showDialog(fcSource, selectButton);
			if(resultat == fcSource.APPROVE_OPTION)
				{
				return fcSource.getSelectedFile().toString();
				}
			else
				{
				return null;
				}
			}
		catch(Exception exc)
			{
			throw new Exception("ERROR : While fetching a file");
			}
		}
	
	/************
	 * Method used to read a simple configuration file
	 * @throws Exception 
	 */
	public static ArrayList<String> readFile(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading the file : "+fileName);
			file = getFlatFileContent(fileName);
			
			listParams.add(param);
			
			return xMLGear.getResultList(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the "+fileName+" file : "+exc.getMessage());
			}
		}
	
	/************
	 * Method used to read a configuration file
	 * @throws Exception 
	 */
	public static ArrayList<String[][]> readFileTab(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading of the "+param+" file : "+fileName);
			file = getFlatFileContent(fileName);
			
			listParams.add(param);
			return xMLGear.getResultListTab(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the "+param+" file : "+exc.getMessage());
			}
		}
	
	/************
	 * Method used to read an advanced configuration file
	 * @throws Exception 
	 */
	public static ArrayList<ArrayList<String[][]>> readExtFile(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading of the file : "+fileName);
			file = getFlatFileContent(fileName);
			
			listParams.add(param);
			return xMLGear.getResultListTabExt(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the file : "+exc.getMessage());
			}
		}
	
	/**
	 * Used to return the file content regarding the data source (xml file or database file)
	 * @throws Exception 
	 */
	public static String getFlatFileContent(String fileName) throws Exception, FileNotFoundException
		{
		return xMLReader.fileRead(Variables.getMainDirectory()+"/"+fileName);
		}
	
	/**
	 * Method used to initialize the database from
	 * a collection file
	 * @throws 
	 */
	public static void initDatabase() throws Exception
		{
		Variables.setKeyPressprofileList(initKeyPressProfileList());
		Variables.setDeviceList(initDeviceList());
		}
	
	/************
	 * Method used to initialize the Device Type list from
	 * the xml file
	 */
	public static ArrayList<KeyPressProfile> initKeyPressProfileList() throws Exception
		{
		ArrayList<String> listParams = new ArrayList<String>();
		ArrayList<String[][]> result;
		ArrayList<ArrayList<String[][]>> extendedList;
		ArrayList<KeyPressProfile> keyPressProfileList = new ArrayList<KeyPressProfile>();
		
		Variables.getLogger().info("Initializing the Key press profile list from collection file");
		
		listParams.add("profiles");
		listParams.add("profile");
		result = xMLGear.getResultListTab(UsefulMethod.getFlatFileContent(Variables.getKeyPressProfileListFileName()), listParams);
		extendedList = xMLGear.getResultListTabExt(UsefulMethod.getFlatFileContent(Variables.getKeyPressProfileListFileName()), listParams);
		
		for(int i=0; i<result.size(); i++)
			{
			try
				{
				String[][] tab = result.get(i);
				String keyPressProfileName = UsefulMethod.getItemByName("name", tab);
				
				if(keyPressProfileList.size() > 0)
					{
					/**
					 * First we check for duplicates
					 */
					boolean found = false;
					for (KeyPressProfile kpp : keyPressProfileList)
						{
						if (kpp.getName().equals(keyPressProfileName))
							{
							Variables.getLogger()
									.debug("Duplicate found, do not adding the following key press profile : "
											+ keyPressProfileName);
							found = true;
							break;
							}
						}
					if(found) continue;
					}
				
				ArrayList<String[][]> tabE = extendedList.get(i);
				
				ArrayList<KeyPress> toSend = new ArrayList<KeyPress>();
				
				for(int j=0; j<tab.length; j++)
					{
					if(tab[j][0].equals("tosend"))
						{
						for(String[] s : tabE.get(j))
							{
							toSend.add(new KeyPress(s[1],KeyType.valueOf(s[0])));
							}
						}
					}
				
				keyPressProfileList.add(new KeyPressProfile(keyPressProfileName,
						Integer.parseInt(UsefulMethod.getItemByName("priority", tab)),
						Integer.parseInt(UsefulMethod.getItemByName("defaultintercommandtimer", tab)),
						toSend));
				}
			catch (Exception e)
				{
				Variables.getLogger().error("Failed to load a new Key press profile : "+e.getMessage(), e);
				}
			}
		
		Variables.getLogger().debug(keyPressProfileList.size()+" key press profile found");
		Variables.getLogger().debug("Key press profile list initialization done");
		return keyPressProfileList;
		}
	
	/************
	 * Method used to initialize the CUCM list from
	 * the xml file
	 */
	public static ArrayList<Device> initDeviceList() throws Exception
		{
		File deviceListFile = new File(Variables.getMainDirectory()+"/"+Variables.getDeviceListFileName());
		BufferedReader buffer  = new BufferedReader(new FileReader(deviceListFile));
		String inputLine = new String("");
		ArrayList<Device> deviceList = new ArrayList<Device>();
		Variables.getLogger().info("Initializing the device list from collection file");
		int index = 1;
		
		while(((inputLine = buffer.readLine()) != null) && (!inputLine.equals("")))
			{
			try
				{
				String[] tab = inputLine.split(UsefulMethod.getTargetOption("csvsplitter"));
				Device device = new Device(tab[0], tab[1], UsefulMethod.getKeyPressProfile(tab[2]));
				
				/**
				 * We check for duplicates
				 */
				if(deviceList.size() > 0)
					{
					boolean found = false;
					
					for(Device d : deviceList)
						{
						if(d.getInfo().equals(device.getInfo()))
							{
							Variables.getLogger().debug("Duplicate found, do not adding the device line "+index+" : "+tab[0]+" : "+tab[1]);
							found = true;
							break;
							}
						}
					if(found)continue;
					}
				
				deviceList.add(device);
				}
			catch (Exception e)
				{
				Variables.getLogger().error("ERROR while processing line "+index+" : "+e.getMessage(),e);
				}
			
			index++;
			}
		
		Variables.getLogger().debug(deviceList.size()+" device found");
		Variables.getLogger().debug("Device list initialization done");
		return deviceList;
		}
	
	/**
	 * Method used when the application failed to 
	 * initialize
	 */
	public static void failedToInit(Exception exc)
		{
		Variables.getLogger().error(exc.getMessage(),exc);
		Variables.getLogger().error("Application failed to init : System.exit(0)");
		System.exit(0);
		}
	
	/**
	 * Initialization of the internal variables from
	 * what we read in the configuration file
	 * @throws Exception 
	 */
	public static void initInternalVariables() throws Exception
		{
		/***********
		 * Logger
		 */
		String level = UsefulMethod.getTargetOption("log4j");
		if(level.compareTo("DEBUG")==0)
			{
			Variables.getLogger().setLevel(Level.DEBUG);
			}
		else if (level.compareTo("INFO")==0)
			{
			Variables.getLogger().setLevel(Level.INFO);
			}
		else if (level.compareTo("ERROR")==0)
			{
			Variables.getLogger().setLevel(Level.ERROR);
			}
		else
			{
			//Default level is INFO
			Variables.getLogger().setLevel(Level.INFO);
			}
		Variables.getLogger().info("Log level found in the configuration file : "+Variables.getLogger().getLevel().toString());
		/*************/
		
		Variables.setUser(UsefulMethod.getTargetOption("senduser"));
		Variables.setPassword(UsefulMethod.getTargetOption("sendpassword"));
		Variables.setJTAPIHost(UsefulMethod.getTargetOption("cucmip"));
		Variables.setTimeout(Integer.parseInt(UsefulMethod.getTargetOption("sendtimeout")));
		Variables.setAssociatePhoneUsingAXL(Boolean.parseBoolean(UsefulMethod.getTargetOption("associatephoneusingaxl")));
		}
	
	/**************
	 * Method aims to get a template item value by giving its name
	 * @throws Exception 
	 *************/
	public static String getItemByName(String name, String[][] itemDetails) throws Exception
		{
		for(int i=0; i<itemDetails.length; i++)
			{
			if(itemDetails[i][0].equals(name))
				{
				return itemDetails[i][1];
				}
			}
		//throw new Exception("Item not found : "+name);
		Variables.getLogger().debug("Item not found : "+name);
		return "";
		}
	
	/**
	 * Method used to find the file name from a file path
	 * For intance :
	 * C:\JAVAELILEX\YUZA\Maquette_CNAF_TA_FichierCollecteDonneesTelephonie_v1.7_mac.xls
	 * gives :
	 * Maquette_CNAF_TA_FichierCollecteDonneesTelephonie_v1.7_mac.xls
	 */
	public static String extractFileName(String fullFilePath)
		{
		String[] tab =  fullFilePath.split("\\\\");
		return tab[tab.length-1];
		}
	
	/**********************************************************
	 * Method used to disable security in order to accept any
	 * certificate without trusting it
	 */
	public static void disableSecurity()
		{
		try
        	{
            X509TrustManager xtm = new HttpsTrustManager();
            TrustManager[] mytm = { xtm };
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, mytm, null);
            SSLSocketFactory sf = ctx.getSocketFactory();

            HttpsURLConnection.setDefaultSSLSocketFactory(sf);
            
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
            	{
                public boolean verify(String hostname, SSLSession session)
                	{
                    return true;
                	}
            	}
            );
        	}
        catch (Exception e)
        	{
            e.printStackTrace();
        	}
		}
	
	public static Device getDevice(String ip)
		{
		for(Device d : Variables.getDeviceList())
			{
			if(d.getIp().equals(ip))return d;
			}
		
		return null;
		}
	
	public static KeyPressProfile getKeyPressProfile(String name) throws Exception
		{
		for(KeyPressProfile kpp : Variables.getKeyPressProfileList())
			{
			if(kpp.getName().equals(name))return kpp;
			}
		
		throw new Exception("The following key press profile was not found : "+name);
		}
	
	/**
	 * Not used but I keep it just in case
	 */
	public synchronized static String escapeHTML(String textToConvert)
		{
		textToConvert = textToConvert.replaceAll("&", "&amp;");
		textToConvert = textToConvert.replaceAll("\"", "&quot;");
		textToConvert = textToConvert.replaceAll("'", "&apos;");
		textToConvert = textToConvert.replaceAll("<", "&lt;");
		textToConvert = textToConvert.replaceAll(">", "&gt;");
		
		return textToConvert;
		}
	
	/**
	 * Not used but I keep it just in case
	 */
	public synchronized static String convertEncodeType(String textToConvert)
		{
		textToConvert = textToConvert.replaceAll("\\\n", "%0A");
		textToConvert = textToConvert.replaceAll("<", "%3C");
		textToConvert = textToConvert.replaceAll("=", "%3D");
		textToConvert = textToConvert.replaceAll("\\\"", "%22");
		textToConvert = textToConvert.replaceAll(":", "%3A");
		textToConvert = textToConvert.replaceAll("\\.", "%2E");
		textToConvert = textToConvert.replaceAll(">", "%3E");
		textToConvert = textToConvert.replaceAll("/", "%2F");
		textToConvert = textToConvert.replaceAll("\\[", "%5B");
		textToConvert = textToConvert.replaceAll("\\]", "%5D");
		textToConvert = textToConvert.replaceAll("!", "%21");
		
		return textToConvert;
		}
	
	/**
	 * Return an existing CiscoTerminal or create a new one if it doesn't exist
	 * @throws Exception 
	 */
	public synchronized static JTAPITerminal getTerminal(String deviceName) throws Exception
		{
		for(JTAPITerminal jt : Variables.getTerminalList())
			{
			if(jt.getTerminal().getName().equals(deviceName))return jt;
			}
		
		//If we reach this point it means that no existing terminal were found, so we create a new one
		Variables.getLogger().debug(deviceName+" : JTAPI : Terminal not found in the list : creating a new one");
		JTAPIConnection connection = Variables.getJtapiConnection();
		
		CiscoTerminal terminal = (CiscoTerminal) connection.getProvider().getTerminal(deviceName);
		if(!terminal.isRegistered())
			{
			Variables.getLogger().debug(deviceName+" : Device is not registered so we do not proceed");
			throw new Exception(deviceName+" : The device is not registered");
			}
		
		JTAPITerminal jt = new JTAPITerminal(terminal);
		Variables.getTerminalList().add(jt);
		return jt;
		}
	
	/**
	 * To clear terminal list
	 * It discards the terminal observer and empty the list
	 */
	public static void clearTerminalList()
		{
		if((Variables.getTerminalList() != null) && (Variables.getTerminalList().size() > 0))
			{
			for(JTAPITerminal jt : Variables.getTerminalList())
				{
				jt.getTerminal().removeObserver(jt);
				}
			
			Variables.getTerminalList().clear();
			Variables.getLogger().debug("Terminal list cleared");
			}
		}
	
	/**
	 * Method which convert a string into cucmAXLVersion
	 */
	public static cucmAXLVersion convertStringToCUCMAXLVersion(String version)
		{
		if(version.contains("80"))
			{
			return cucmAXLVersion.version80;
			}
		else if(version.contains("85"))
			{
			return cucmAXLVersion.version85;
			}
		else if(version.contains("105"))
			{
			return cucmAXLVersion.version105;
			}
		else
			{
			//Default : 10.5
			return cucmAXLVersion.version105;
			}
		}
	
	/***
	 * Method used to get the AXL version from the CUCM
	 * We contact the CUCM using a very basic request and therefore get the version
	 * @throws Exception 
	 */
	public static cucmAXLVersion getAXLVersionFromTheCUCM() throws Exception
		{
		/**
		 * In this method version we just read the version from the configuration file
		 * This has to be improved to match the method description
		 **/
		cucmAXLVersion AXLVersion;
		
		AXLVersion = UsefulMethod.convertStringToCUCMAXLVersion("version"+getTargetOption("axlversion"));
		
		return AXLVersion;
		}
	
	/******
	 * Method used to initialize the AXL Connection to the CUCM
	 */
	public static synchronized void initAXLConnectionToCUCM() throws Exception
		{
		try
			{
			String axlHost = UsefulMethod.getTargetOption("cucmip");
			String axlP = UsefulMethod.getTargetOption("cucmaxlport");
			Variables.getLogger().debug("Initializing AXL connection to "+axlHost+":"+axlP);
			
			UsefulMethod.disableSecurity();//We first turned off security
			
			if(Variables.getCUCMVersion().equals(cucmAXLVersion.version85))
				{
				throw new Exception("AXL unsupported version");
				}
			else if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
				{
				if(Variables.getAXLConnectionToCUCMV105() == null)
					{
					com.cisco.axlapiservice10.AXLAPIService axlService = new com.cisco.axlapiservice10.AXLAPIService();
					com.cisco.axlapiservice10.AXLPort axlPort = axlService.getAXLPort();
					
					// Set the URL, user, and password on the JAX-WS client
					String validatorUrl = "https://"+axlHost+":"+axlP+"/axl/";
					
					((BindingProvider) axlPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, validatorUrl);
					((BindingProvider) axlPort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, UsefulMethod.getTargetOption("senduser"));
					((BindingProvider) axlPort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, UsefulMethod.getTargetOption("sendpassword"));
					
					Variables.setAXLConnectionToCUCMV105(axlPort);
					Variables.getLogger().debug("AXL WSDL Initialization done");
					}
				else
					{
					Variables.getLogger().debug("AXL connection already initialized, aborting");
					}
				}
			
			/**
			 * We now check if the CUCM is reachable by asking him its version
			 */
			Variables.getLogger().debug("CUCM version : "+SimpleRequest.getCUCMVersion());
			Variables.setCUCMReachable(true);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("Error while initializing AXL CUCM connection : "+e.getMessage(),e);
			Variables.setCUCMReachable(false);
			throw e;
			}
		}
	
	public static JTAPILine getJTAPILine(String lineNumber) throws Exception
		{
		for(JTAPILine line : Variables.getLineList())
			{
			if(line.getLine().getName().equals(lineNumber))return line;
			}
		
		throw new Exception("Line not found : "+lineNumber);
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}

