package com.alex.phorkpe.misc;

import com.alex.phorkpe.utils.Variables;

public class KeyPress
	{
	/**
	 * Variables
	 */
	public enum KeyType
		{
		key,
		wait
		};
	
	private String key;
	private KeyType type;
	
	public KeyPress(String key, KeyType type)
		{
		super();
		this.key = key;
		this.type = type;
		
		if(type.equals(KeyType.wait))
			{
			try
				{
				Integer.parseInt(this.key);//We do that just to check that a correct waiting time has been provided
				}
			catch(Exception e)
				{
				Variables.getLogger().error("ERROR while parsing waiting time, applying default value (500) : "+e.getMessage());
				this.key = "500";
				}
			}
		}

	public String getKey()
		{
		return key;
		}

	public KeyType getType()
		{
		return type;
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
