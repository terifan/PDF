package pdf;

import java.io.IOException;
import pdf.writer.Output;


public class NumberValue implements Value
{
	private Double mDouble;
	private Integer mInteger;


	public NumberValue(double aValue)
	{
		mDouble = aValue;
	}


	public NumberValue(int aValue)
	{
		mInteger = aValue;
	}


	public Double getDouble()
	{
		return mDouble;
	}


	public Integer getInteger()
	{
		return mInteger;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		if (mDouble != null)
		{
			aOutput.print(mDouble.toString());
		}
		else
		{
			aOutput.print(mInteger.toString());
		}
	}
}
