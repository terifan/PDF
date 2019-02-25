package pdf;

import java.io.IOException;
import pdf.writer.Output;


public class NumberValue implements Value
{
	private int mValue;


	public NumberValue(int aValue)
	{
		mValue = aValue;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(Integer.toString(mValue));
	}
}
