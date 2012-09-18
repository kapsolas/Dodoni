/*
 * Copyright 2012 All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 * 
 * Name                 Date            Version       Modification
 * ===========================================================================================================================================================
 * Jim Kapsalis         2012.09.11        1.0         Initial Creation
 * 
 * 
 */

package com.kapsalis.data.model;

public class Placemark 
{
	private CoordinatePoint cp;
	private String name;
	private String description;
	private String bssid;
	private String capabilities;
	private String frequency;
	
	public static Placemark newPlacemark()
	{
		return new Placemark();
	}
	
	public static Placemark newPlacemark(CoordinatePoint cp, String name, String description, String bssid, String capabilities, String frequency)
	{
		return new Placemark(cp, name, description, bssid, capabilities, frequency);
	}
	
	private Placemark()
	{
		
	}
	
	private Placemark(CoordinatePoint cp, String name, String description, String bssid, String capabilities, String frequency)
	{
		this.cp = cp;
		this.name = name;
		this.description = description;
		this.bssid = bssid;
		this.capabilities = capabilities;
		this.frequency = frequency;
	}
	
	public CoordinatePoint getCoordinatePoint()
	{
		return cp;
	}
	
	public void setCoordinatePoint(CoordinatePoint cp)
	{
		this.cp = cp;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getBSSID()
	{
		return bssid;
	}
	
	public void setBSSID(String bssid)
	{
		this.bssid = bssid;
	}
	
	public String getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}
	
	public String getCapabilities()
	{
		return capabilities;
	}
	
	public void setCapabilities(String capabilities)
	{
		this.capabilities = capabilities;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Name: ").append(name).append("\n");
		sb.append("     BSSID: ").append(bssid).append("\n");
		sb.append("     Capabilities: ").append(capabilities).append("\n");
		sb.append("     Frequency: ").append(frequency).append("\n");
		
		return sb.toString();
	}
}
