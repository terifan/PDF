package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.Objects;


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
	public int hashCode()
	{
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.mDouble);
		hash = 53 * hash + Objects.hashCode(this.mInteger);
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
		final NumberValue other = (NumberValue)obj;
		if (!Objects.equals(this.mDouble, other.mDouble))
		{
			return false;
		}
		return Objects.equals(this.mInteger, other.mInteger);
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


	@Override
	public String toString()
	{
		return Output.writeSingle(this);
	}
}
