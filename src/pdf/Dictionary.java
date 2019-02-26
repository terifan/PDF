package pdf;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import pdf.writer.Output;


public class Dictionary implements Value
{
	private LinkedHashMap<String, Value> mMap = new LinkedHashMap<>();


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
}
