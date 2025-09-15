package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;


public class Array implements Value
{
	private ArrayList<Value> mArray = new ArrayList<>();


	public Array()
	{
	}


	public Array(double... aValues)
	{
		for (double value : aValues)
		{
			add(value);
		}
	}


	public Array add(Value aValue)
	{
		mArray.add(aValue);
		return this;
	}


	public Array add(String aValue)
	{
		mArray.add(new TextValue(aValue));
		return this;
	}


	public Array add(int aValue)
	{
		mArray.add(new NumberValue(aValue));
		return this;
	}


	public Array add(double aValue)
	{
		mArray.add(new NumberValue(aValue));
		return this;
	}


	public Value get(int aIndex)
	{
		return mArray.get(aIndex);
	}


	public int size()
	{
		return mArray.size();
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print("[");

		boolean first = true;
		for (Value value : mArray)
		{
			if (!first)
			{
				aOutput.print(" ");
			}
			value.writeTo(aOutput);
			first = false;
		}

		aOutput.print("]");
	}
}
