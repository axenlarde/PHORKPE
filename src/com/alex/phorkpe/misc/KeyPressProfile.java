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
	private long defaultInterCommandTimer;
	ArrayList<KeyPress> keyList;
	
	public KeyPressProfile(String name, int priority, long defaultInterCommandTimer, ArrayList<KeyPress> keyList)
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

	public long getDefaultInterCommandTimer()
		{
		return defaultInterCommandTimer;
		}

	public ArrayList<KeyPress> getKeyList()
		{
		return keyList;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
