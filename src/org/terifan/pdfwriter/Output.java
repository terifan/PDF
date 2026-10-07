package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static org.terifan.pdfwriter.Utilities.roundDouble;


class Output implements AutoCloseable
{
	private OutputStream mOutput;
	private int mSize;


	public Output()
	{
		this(new ByteArrayOutputStream());
	}


	public Output(OutputStream aOutput)
	{
		mOutput = aOutput;
	}


	public OutputStream getOutput()
	{
		return mOutput;
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
		print(roundDouble(aNumber));
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0, pi = 0; i < aText.length(); i++)
		{
			char c = aText.charAt(i);
			if (c == '%')
			{
				c = aText.charAt(++i);
				switch (c)
				{
					case 'f':
						baos.write(roundDouble((double)aParams[pi++]).getBytes());
						break;
					case 'd':
					case 's':
						if (aParams[pi] instanceof Color v)
						{
							baos.write(v.getRGBString().getBytes());
						}
						else if (aParams[pi] instanceof String v)
						{
							baos.write(v.getBytes());
						}
						else
						{
							new UnsupportedOperationException("Unsupported type: " + aParams[pi].getClass()).printStackTrace();
							baos.write(aParams[pi].toString().getBytes());
						}
						pi++;
						break;
					default:
						throw new IllegalStateException(aText);
				}
			}
			else
			{
				baos.write(c);
			}
		}
		byte[] buf = baos.toByteArray();

		mOutput.write(buf);
		mSize += buf.length;
	}


	public void println(String aText, Object... aParams) throws IOException
	{
		print(aText + "\n", aParams);
	}


	public void append(Output aOutput) throws IOException
	{
		if (aOutput.mOutput instanceof ByteArrayOutputStream v)
		{
			v.writeTo(mOutput);
			mSize += v.size();
		}
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


	/**
	 * warning: Will return the internal stream as a String and may result in a non-descriptive text.
	 */
	@Override
	public String toString()
	{
		return mOutput.toString();
	}


	static String writeSingle(Value aValue)
	{
		try
		{
			Output out = new Output();
			aValue.writeTo(out);
			return out.toString();
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
