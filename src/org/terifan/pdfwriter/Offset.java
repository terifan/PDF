package org.terifan.pdfwriter;

import java.io.IOException;


class Offset
{
	private int mOffset;


	public Offset(int aOffset)
	{
		mOffset = aOffset;
	}


	public void set(int aOffset)
	{
		mOffset = aOffset;
	}


	void print(Output aOutput) throws IOException
	{
		aOutput.println(String.format("%010d 00000 n", mOffset));
	}
}
