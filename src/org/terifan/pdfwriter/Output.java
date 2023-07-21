package org.terifan.pdfwriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;
import java.util.Locale;


class Output implements AutoCloseable
{
	private OutputStream mOutput;
	private int mSize;


	public Output(OutputStream aOutput)
	{
		mOutput = aOutput;
	}


	public void print(byte[] aBuffer) throws IOException
	{
		mOutput.write(aBuffer);
		mSize += aBuffer.length;
	}


	public void print(String aText) throws IOException
	{
		print(aText.getBytes());
	}


	public void print(double aNumber) throws IOException
	{
		String s = "" + aNumber;
		if (s.endsWith(".0"))
		{
			s = s.substring(0, s.length() - 2);
		}

		print(s.getBytes());
	}


	public void println(String aText) throws IOException
	{
		byte[] buf = aText.getBytes();
		mOutput.write(buf);
		mOutput.write('\n');
		mSize += buf.length + 1;
	}


	public void print(String aText, Object... aParams) throws IOException
	{
		byte[] buf = new Formatter(Locale.US).format(aText, aParams).toString().getBytes();
		mOutput.write(buf);
		mSize += buf.length;
	}


	public void println(String aText, Object... aParams) throws IOException
	{
		print(aText + "\n", aParams);
	}


	@Override
	public void close() throws IOException
	{
		mOutput.close();
	}


	public int size()
	{
		return mSize;
	}
}
