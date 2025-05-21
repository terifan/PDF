package org.terifan.pdfwriter;

import java.io.IOException;


public class NumberValue implements Value
{
	private Double mDouble;
	private Integer mInteger;


	public NumberValue(double aValue)
	{
		mDouble = aValue;
	}


	public NumberValue(int aValue)
	{
		mInteger = aValue;
	}


	public Double getDouble()
	{
		return mDouble;
	}


	public Integer getInteger()
	{
		return mInteger;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		if (mDouble != null)
		{
			String s = mDouble.toString();
			if (s.endsWith(".0"))
			{
				s = s.substring(0, s.length() - 2);
			}
			aOutput.print(s);
		}
		else
		{
			aOutput.print(mInteger.toString());
		}
	}
}
