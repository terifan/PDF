package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.Objects;


public class TextValue implements Value
{
	private String mValue;


	public TextValue(String aValue)
	{
		mValue = aValue;
	}


	public String getValue()
	{
		return mValue;
	}


	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 17 * hash + Objects.hashCode(this.mValue);
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
		final TextValue other = (TextValue)obj;
		return Objects.equals(this.mValue, other.mValue);
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mValue.getBytes());
	}


	@Override
	public String toString()
	{
		return Output.writeSingle(this);
	}
}
