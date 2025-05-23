package org.terifan.pdfwriter;

import java.io.IOException;


public class DictionaryValue implements Value
{
	private Dictionary mValue;


	public DictionaryValue(Dictionary aValue)
	{
		mValue = aValue;
	}


	public Dictionary getValue()
	{
		return mValue;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		mValue.writeTo(aOutput);
	}
}
