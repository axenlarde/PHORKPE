package com.alex.phorkpe.jtapi;

import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;
import javax.telephony.ProviderObserver;
import javax.telephony.events.ProvEv;
import javax.telephony.events.ProvInServiceEv;
import javax.telephony.events.ProvShutdownEv;

import com.alex.phorkpe.utils.Variables;
import com.cisco.cti.util.Condition;
import com.cisco.jtapi.CiscoAddrCreatedEvImpl;
import com.cisco.jtapi.CiscoAddrRemovedEvImpl;


/**
 * A JTAPI connection with a CUCM server
 *
 * @author Alexandre RATEL
 */
public class JTAPIConnection implements ProviderObserver
	{
	/**
	 * Variables
	 */
	private String login, password, server;
	private Provider provider;
	private Condition conditionInService = new Condition ();

	public JTAPIConnection(String server, String login, String password) throws Exception
		{
		super();
		this.server = server;
		this.login = login;
		this.password = password;
		
		initConnection();
		}

	private void initConnection() throws Exception
		{
		JtapiPeer peer = JtapiPeerFactory.getJtapiPeer ( null );
		Variables.getLogger().debug("JTAPI : Got peer "+peer);
		
		String providerString = server + ";login=" + login + ";passwd=" + password;
		Variables.getLogger().debug("JTAPI : providerString = "+providerString);
		
		provider = peer.getProvider(providerString);
		Variables.getLogger().debug("JTAPI : Got provider "+provider);
		
		provider.addObserver(this);
		conditionInService.waitTrue();
		Variables.getLogger().debug("JTAPI : Now In service !");
		}
	
	@Override
	public void providerChangedEvent(ProvEv[] eventList)
		{
		if (eventList != null)
			{
			for (int i = 0; i < eventList.length; i++)
				{
				if (eventList[i] instanceof ProvInServiceEv)
					{
					conditionInService.set ();
					}
				else if (eventList[i] instanceof ProvShutdownEv)
					{
					Variables.getLogger().debug("JTAPI : The provider has been shut down successfully");
					}
				else if (eventList[i] instanceof CiscoAddrCreatedEvImpl)
					{
					Variables.getLogger().debug("JTAPI : A new line has been associated to the JTAPI user. We need to restart the monitoring");
					}
				else if (eventList[i] instanceof CiscoAddrRemovedEvImpl)
					{
					Variables.getLogger().debug("JTAPI : A line has been removed from the JTAPI user. We need to restart the monitoring");
					}
				}
			}
		}

	/**
	 * Method used to stop the JTAPI Connection
	 */
	public void disconnect() throws Exception
		{
		provider.shutdown();
		}

	public Provider getProvider()
		{
		return provider;
		}

	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
