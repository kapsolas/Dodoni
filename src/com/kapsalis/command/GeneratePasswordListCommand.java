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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.kapsalis.data.model.Placemark;

class GeneratePasswordListCommand implements Command 
{
	public boolean execute(Object arg)
	{
		boolean result = true;
		
		@SuppressWarnings("unchecked")
		List<Placemark> arg2 = (List<Placemark>)arg;
		List<Placemark> lPlacemarks = arg2;
		
		List<String>passwords = new ArrayList<String>();
		List<String>uniquepasswords = new ArrayList<String>();
		
		for (Placemark pm : lPlacemarks)
	    {
			passwords.add(pm.getName());
	    }
		
		uniquepasswords = new ArrayList<String>(new HashSet<String>(passwords));
		
		Collections.sort(uniquepasswords);
		
		System.out.println("[+] Wordlist generated from SSIDs in alphabetical order.");
		
		for (String s : uniquepasswords)
		{
			System.out.println(s);
		}
		
		System.out.println("[+] Wordlist contains [" + uniquepasswords.size() + "] unique words.\n\n");
		
		return result;
	}
}
