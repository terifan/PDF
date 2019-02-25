package pdf;

import java.util.ArrayList;


public class Array
{
	private ArrayList<Object> mArray = new ArrayList<>();


	public Array add(Object aValue)
	{
		mArray.add(aValue);
		return this;
	}


	public int size()
	{
		return mArray.size();
	}


	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object value : mArray)
		{
			if (!first)
			{
				sb.append(" ");
			}
			sb.append(value);
			first = false;
		}
		return "[" + sb.toString() + "]";
	}
}
