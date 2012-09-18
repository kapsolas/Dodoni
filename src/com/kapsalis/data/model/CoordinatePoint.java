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

public class CoordinatePoint 
{
	private float longitude;
	private float latitude;
	
	public static CoordinatePoint newCoordinatePoint()
	{
		return new CoordinatePoint();
	}
	
	public static CoordinatePoint newCoordinatePoint(float longitude, float latitude)
	{
		return new CoordinatePoint(longitude, latitude);
	}
	
	private CoordinatePoint()
	{
		
	}
	
	private CoordinatePoint(float longitude, float latitude)
	{
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public float getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}
	
	public float getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}
}
