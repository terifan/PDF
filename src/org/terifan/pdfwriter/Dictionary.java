package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class Dictionary
{
	private LinkedHashMap<String, Value> mMap = new LinkedHashMap<>();


	public Dictionary()
	{
	}


	public boolean isEmpty()
	{
		return mMap.isEmpty();
	}


	public Dictionary put(String aKey, Value aValue)
	{
		mMap.put(aKey, aValue);
		return this;
	}


	public Dictionary put(String aKey, String aValue)
	{
		mMap.put(aKey, new TextValue(aValue));
		return this;
	}


	public Dictionary put(String aKey, Dictionary aValue)
	{
		mMap.put(aKey, new DictionaryValue(aValue));
		return this;
	}


	public Dictionary put(String aKey, int aValue)
	{
		mMap.put(aKey, new NumberValue(aValue));
		return this;
	}


	public Dictionary put(String aKey, double aValue)
	{
		mMap.put(aKey, new NumberValue(aValue));
		return this;
	}


	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print("<<");
		boolean first = true;
		for (Entry<String, Value> entry : mMap.entrySet())
		{
			if (!first)
			{
				aOutput.println("");
			}
			aOutput.print(entry.getKey());
			aOutput.print(" ");
			entry.getValue().writeTo(aOutput);
			first = false;
		}
		aOutput.print(">>");
	}
}
