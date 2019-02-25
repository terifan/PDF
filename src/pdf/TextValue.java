package pdf;

import java.io.IOException;
import pdf.writer.Output;


public class TextValue implements Value
{
	private String mValue;


	public TextValue(String aValue)
	{
		mValue = aValue;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mValue.getBytes());
	}
}
