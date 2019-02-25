package pdf;

import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class Dictionary
{
	private LinkedHashMap<String, Object> mMap = new LinkedHashMap<>();


	public Dictionary put(String aKey, Object aValue)
	{
		mMap.put(aKey, aValue);
		return this;
	}


	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<< ");
		boolean first = true;
		for (Entry<String, Object> entry : mMap.entrySet())
		{
			if (!first)
			{
				sb.append(" ");
			}
			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());

			first = false;
		}
		sb.append(" >>");
		return sb.toString();
	}
}
