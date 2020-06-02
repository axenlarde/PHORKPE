package com.alex.phorkpe.misc;

import java.util.ArrayList;

/**
 * Key Press Profile
 *
 * @author Alexandre RATEL
 */
public class CallProfile
	{
	/**
	 * Variables
	 */
	private String name;
	private int defaultInterCommandTimer;
	ArrayList<Call> callList;
	
	public CallProfile(String name, int defaultInterCommandTimer, ArrayList<Call> callList)
		{
		super();
		this.name = name;
		this.defaultInterCommandTimer = defaultInterCommandTimer;
		this.callList = callList;
		}

	public String getName()
		{
		return name;
		}

	public int getDefaultInterCommandTimer()
		{
		return defaultInterCommandTimer;
		}

	public ArrayList<Call> getCallList()
		{
		return callList;
		}

	
	
	/*2020*//*RATEL Alexandre 8)*/
	}
