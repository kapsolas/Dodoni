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

package com.kapsalis.data.xml;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.kapsalis.data.model.CoordinatePoint;
import com.kapsalis.data.model.Placemark;

public class KMLParser extends DefaultHandler
{
	private boolean isPlacemark;
	private StringBuilder sb;
	private Placemark pm;
	private List<Placemark> lPlacemarks;
	private CoordinatePoint cp; 
    
	public KMLParser()
	{
		super();
		lPlacemarks = new ArrayList<Placemark>();
	}

    // //////////////////////////////////////////////////////////////////
    // Event handlers.
    // //////////////////////////////////////////////////////////////////

    public void startDocument()
    {
        // System.out.println("Start document");
    }

    public void endDocument()
    {
        // System.out.println("End document");
    }

    public void startElement(String uri, String name, String qName, Attributes atts)
    {

        sb = new StringBuilder();

        /*
        if ("".equals(uri)) 
        	System.out.println("Start element: " + qName); 
        else
        	System.out.println("Start element: {" + uri + "}" + name);
        */
        
        if ("Placemark".equals(name))
        {
        	isPlacemark = true;
        	pm = Placemark.newPlacemark();
        }
        else if ("coordinates".equals(name))
        {
        	cp = CoordinatePoint.newCoordinatePoint();
        }
    }

    public void endElement(String uri, String name, String qName)
    {
        /*
         * if ("".equals(uri)) System.out.println("End element: " + qName); else
         * System.out.println("End element: {" + uri + "}" + name);
         */
        
        if ("Placemark".equals(name))
        {
        	//System.out.println(pm.toString());
        	
        	lPlacemarks.add(pm);
        	pm = null;
        	isPlacemark = false;
        	//System.out.println("ArraySize: " + lPlacemarks.size());
        }
        
        if (isPlacemark)
    	{
        	if ("name".equals(name))
        	{
        		pm.setName(sb.toString());
        	}
	        else if ("description".equals(name))
	        {
	        	String tmp = sb.toString();
	        	String bssid = null, capabilities = null, frequency = null;
	        	String[] tokenized = tmp.split("<br/>");
	        	
	        	for (int i = 0; i < tokenized.length; i++)
	        	{
	        		//System.out.println("[" + i + "]" + tokenized[i]);
	        		if (tokenized[i].indexOf("BSSID") > -1)
	        		{
	        			bssid = tokenized[i].replace("<b>", "").replace("</b>", "").replace("BSSID: ", "");
	        		}
	        		else if (tokenized[i].indexOf("Capabilities") > -1)
	        		{
	        			capabilities = tokenized[i].replace("<b>", "").replace("</b>", "").split(":")[1].trim();
	        		}
	        		else if(tokenized[i].indexOf("Frequency") > -1)
	        		{
	        			frequency = tokenized[i].replace("<b>", "").replace("</b>", "").split(":")[1].trim();
	        		}
	        	}
	        	
	        	pm.setBSSID(bssid);
	        	pm.setCapabilities(capabilities);
	        	pm.setFrequency(frequency);
        		pm.setDescription(sb.toString());
        	}
	        else if ("coordinates".equals(name))
	        {
	        	String tmp = sb.toString();
	        	
	        	float longitude = Float.valueOf(((String)tmp.split(",")[0]).trim()).floatValue();
	        	float latitude = Float.valueOf(((String)tmp.split(",")[1]).trim()).floatValue();
	        	cp.setLongitude(longitude);
	        	cp.setLatitude(latitude);
	        	pm.setCoordinatePoint(cp);
	        }
    	}
    }
    
    public void characters(char ch[], int start, int length)
    {
        // System.out.print("Characters: \"");
        for (int i = start; i < start + length; i++)
        {
            switch (ch[i])
            {
                case '\\':
                    // System.out.print("\\\\");
                    sb.append("\\\\");
                    break;
                case '"':
                    // System.out.print("\\\"");
                    sb.append("\\\"");
                    break;
                case '\n':
                    // System.out.print("\\n");
                    sb.append("\\n");
                    break;
                case '\r':
                    // System.out.print("\\r");
                    sb.append("\\r");
                    break;
                case '\t':
                    // System.out.print("\\t");
                    sb.append("\\t");
                    break;

                default:
                    // System.out.print(ch[i]);
                    sb.append(ch[i]);
                    break;
            }
        }
        // System.out.print("\"\n");
    }

    public List<Placemark>getArrayListOfPlaceMarks()
    {
    	return lPlacemarks;
    }
}
