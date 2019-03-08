package pdf;

import java.io.IOException;


public class ByteValue implements Value
{
	private byte[] mValue;


	public ByteValue(byte[] aValue)
	{
		mValue = aValue;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mValue);
	}
}
