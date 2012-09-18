/*
 * Copyright 2012 All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 * 
 * Name                 Date            Version       Modification
 * ===========================================================================================================================================================
 * Jim Kapsalis         2012.09.18        1.0         Initial Creation
 * 
 * 
 */
package com.kapsalis.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.kapsalis.data.model.Placemark;

public class OUIBreakdownCommand implements Command 
{
	public boolean execute(Object arg)
	{
		boolean result = true;
		
		@SuppressWarnings("unchecked")
		List<Placemark> arg2 = (List<Placemark>)arg;
		List<Placemark> lPlacemarks = arg2;
		
		List<String>ouis = new ArrayList<String>();
		List<String>uniqueoui = new ArrayList<String>();
		
		Map<String, Integer> ouiMap = new HashMap<String, Integer>();
		Map<String, String> mfgsMap = new HashMap<String, String>();
		
		try
		{
			mfgsMap = getManufacuters();
			
			for (Placemark pm : lPlacemarks )
			{
				String oui = pm.getBSSID();
				
				// bit of a hack to ensure we are looking at BSSIDs instead of the CMDA antennas
				if ( oui.indexOf(":") > 0 )
					ouis.add(oui.replaceAll(":", "-"));
			}
			
			uniqueoui = new ArrayList<String>(new HashSet<String>(ouis));
			
			Collections.sort(uniqueoui);
			
			for (String s : uniqueoui)
			{
				// pull just the first part of the BSSID. XX:YY:ZZ
				s = s.substring(0,8);
				
				Integer i = ouiMap.get((String)s);
				
				// start counting the times each mfg is used.
				if ( i == null )
				{
					i = new Integer(1);
					ouiMap.put(s, i);
				}
				else
				{
					i = new Integer(i.intValue()+1);
					ouiMap.put(s, i);
				}
			}
			
			
			// TODO: The below method is not returning the sorted map; Even though we are getting a sorted list of values..
			//       need to debug this later
			Map<String, Integer> sortedMap = sortByComparator(ouiMap);
			Iterator<Entry<String, Integer>> it = sortedMap.entrySet().iterator();
			
			while ( it.hasNext() )
			{
				Map.Entry<String, Integer> pairs = it.next();
				String mfg = mfgsMap.get(pairs.getKey());
				StringBuilder sb = new StringBuilder();
				sb.append("OUI: ").append(pairs.getKey());
				sb.append(" MFG: ");
				if (mfg == null)
					sb.append("Manufacturer is not found. Try to update your OUI.txt ");
				else
					sb.append(mfg);
				sb.append(" Count: ").append(pairs.getValue());
				
				System.out.println( sb.toString() );
			}
		}
		catch(FileNotFoundException e)
		{
			result = false;
		}
		
		
		if (result)
		{
			System.out.println("[+] Vendor breakdown based on BSSIDs captured.\n\n");
		}
		else
		{
			System.out.println("[!] There is an issue with parsing the manufacturers for the access points. \n\n");
		}
		
		return result;
	}
	
	private Map<String, String> getManufacuters() throws FileNotFoundException
	{
		Map <String, String>mfgs = new HashMap<String,String>();
		
		Scanner scanner = new Scanner(new FileInputStream("oui.txt"));
	    try 
	    {
	    	while (scanner.hasNextLine())
	    	{
	    		String line = scanner.nextLine();
	        
	    		if ( line.indexOf("(hex)") > 0 )
	    		{
	    			String[] tokenized = line.split("\t");
	    			
	    			String tmpOui = tokenized[0].substring(0,8);
	    			String tmpMfg = tokenized[2];
	    			
	    			//System.out.println("[" + tmpOui.toLowerCase() + "] [" + tmpMfg + "]");
	    			
	    			mfgs.put(tmpOui, tmpMfg);
	    		}
	    	}
	    }
	    finally
	    {
	    	scanner.close();
	    }
		
		return mfgs;
	}

	private static Map<String, Integer> sortByComparator (Map<String, Integer> unsortedMap)
	{
		List<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(unsortedMap.entrySet());
		
		// sort the list based on the comparator...
		Collections.sort(list, new Comparator<Object>() {
			public int compare (Object o1, Object o2) {
				Integer a = (Integer)((Entry<String, Integer>) o1).getValue();
				Integer b = (Integer)((Entry<String, Integer>) o2).getValue();
				
				//System.out.println("a[" + a + "] b[" + b + "] = " + a.compareTo(b));
				
				return b.compareTo(a);
			}
		});
		
		Map<String, Integer> sortedMap = new HashMap<String, Integer>();
		for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) 
		{
		     Entry<String, Integer> entry = (Entry<String, Integer>)it.next();
		     sortedMap.put((String)entry.getKey(), (Integer)entry.getValue());
		     
		     
		     //System.out.println("OUI: " + (String)entry.getKey() + " Count: " + (Integer)entry.getValue());
		     //System.out.println("Added===> " + (String)entry.getKey() + "...." + (Integer)entry.getValue());
		}
		
		return sortedMap;
	}
}
