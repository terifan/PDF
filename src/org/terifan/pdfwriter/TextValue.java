package org.terifan.pdfwriter;

import java.io.IOException;


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
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mValue.getBytes());
	}
}
