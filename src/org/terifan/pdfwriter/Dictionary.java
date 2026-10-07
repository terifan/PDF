package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Objects;


public class Dictionary implements Value
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


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print("<<");
		for (Entry<String, Value> entry : mMap.entrySet())
		{
			aOutput.print(" ");
			aOutput.print(entry.getKey());
			aOutput.print(" ");
			entry.getValue().writeTo(aOutput);
		}
		aOutput.print(" >>");
	}


	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.mMap);
		return hash;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Dictionary other = (Dictionary)obj;
		return Objects.equals(this.mMap, other.mMap);
	}


	Dictionary putAll(Dictionary aValue)
	{
		mMap.putAll(aValue.mMap);
		return this;
	}


	@Override
	public String toString()
	{
		return Output.writeSingle(this);
	}
}
