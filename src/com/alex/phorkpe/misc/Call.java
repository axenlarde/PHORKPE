package com.alex.phorkpe.misc;

import com.alex.phorkpe.utils.Variables;

public class Call
	{
	/**
	 * Variables
	 */
	public enum CallType
		{
		call,
		holdon,
		holdoff,
		transfer,
		conference,
		wait,
		waitandanswer,
		end
		};
	
	private String value;
	private CallType type;
	
	public Call(String value, CallType type)
		{
		super();
		this.value = value;
		this.type = type;
		
		if(type.equals(CallType.wait))
			{
			try
				{
				Integer.parseInt(this.value);//We do that just to check that a correct waiting time has been provided
				}
			catch(Exception e)
				{
				Variables.getLogger().error("ERROR while parsing waiting time, applying default value (500) : "+e.getMessage());
				this.value = "500";
				}
			}
		}

	public String getValue()
		{
		return value;
		}

	public CallType getType()
		{
		return type;
		}

	
	/*2020*//*RATEL Alexandre 8)*/
	}
