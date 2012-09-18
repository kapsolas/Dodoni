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

package com.kapsalis.command;

public class CommandFactory 
{
	public static GeneratePasswordListCommand newGeneratePasswordListCommand()
	{
		return new GeneratePasswordListCommand();
	}
	
	public static OUIBreakdownCommand newOUIBreakdownCommand()
	{
		return new OUIBreakdownCommand();
	}
}
