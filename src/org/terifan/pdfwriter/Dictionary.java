package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.terifan.bundle.Bundle;


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
		aOutput.print("<< ");
		boolean first = true;
		for (Entry<String, Value> entry : mMap.entrySet())
		{
			if (!first)
			{
				aOutput.print(" ");
			}
			aOutput.print(entry.getKey());
			aOutput.print(" ");
			entry.getValue().writeTo(aOutput);

			first = false;
		}
		aOutput.println(" >>");
	}


	public Bundle asJSON() throws IOException
	{
		Bundle bundle = new Bundle();
		for (Entry<String,Value> entry : mMap.entrySet())
		{
			if (entry.getValue() instanceof Array)
			{
				bundle.putArray(entry.getKey(), ((Array)entry.getValue()).asJSON());
			}
			else if (entry.getValue() instanceof Dictionary)
			{
				bundle.putBundle(entry.getKey(), ((Dictionary)entry.getValue()).asJSON());
			}
			else if (entry.getValue() instanceof NumberValue)
			{
				if (((NumberValue)entry.getValue()).getDouble() == null)
				{
					bundle.putNumber(entry.getKey(), ((NumberValue)entry.getValue()).getDouble());
				}
				else
				{
					bundle.putNumber(entry.getKey(), ((NumberValue)entry.getValue()).getInteger());
				}
			}
			else if (entry.getValue() instanceof TextValue)
			{
				bundle.putString(entry.getKey(), ((TextValue)entry.getValue()).getValue());
			}
			else
			{
				throw new IllegalStateException("unsupported: " + entry);
			}
		}
		return bundle;
	}
}
