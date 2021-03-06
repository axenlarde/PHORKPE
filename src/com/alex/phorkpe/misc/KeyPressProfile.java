package com.alex.phorkpe.misc;

import java.util.ArrayList;

/**
 * Key Press Profile
 *
 * @author Alexandre RATEL
 */
public class KeyPressProfile
	{
	/**
	 * Variables
	 */
	private String name;
	private int priority;
	private int defaultInterCommandTimer;
	ArrayList<KeyPress> keyList;
	
	public KeyPressProfile(String name, int priority, int defaultInterCommandTimer, ArrayList<KeyPress> keyList)
		{
		super();
		this.name = name;
		this.priority = priority;
		this.defaultInterCommandTimer = defaultInterCommandTimer;
		this.keyList = keyList;
		}

	public String getName()
		{
		return name;
		}

	public int getPriority()
		{
		return priority;
		}

	public int getDefaultInterCommandTimer()
		{
		return defaultInterCommandTimer;
		}

	public ArrayList<KeyPress> getKeyList()
		{
		return keyList;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
