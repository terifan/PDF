package pdf;

import java.io.IOException;


public class ArrayValue implements Value
{
	private byte[] mValue;


	public ArrayValue(byte[] aValue)
	{
		mValue = aValue;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mValue);
	}
}
