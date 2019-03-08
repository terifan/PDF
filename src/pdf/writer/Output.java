package pdf;

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


	public void println(String aText) throws IOException
	{
		byte[] buf = aText.getBytes();
		mOutput.write(buf);
		mOutput.write('\n');
		mSize += buf.length + 1;
	}


	public void println(String aText, Object... aParams) throws IOException
	{
		byte[] buf = new Formatter(Locale.US).format(aText, aParams).toString().getBytes();
		mOutput.write(buf);
		mOutput.write('\n');
		mSize += buf.length + 1;
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
