package org.terifan.pdfwriter;

import java.io.IOException;


public class Ref implements Value
{
	protected int mReference;


	public Ref(int aReference)
	{
		mReference = aReference;
	}


	public int getRef()
	{
		return mReference;
	}


	void setRef(int aReference)
	{
		mReference = aReference;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mReference + " 0 R");
	}
}
