package pdf;

import java.io.IOException;
import java.util.ArrayList;
import pdf.writer.Output;


public class Array implements Value
{
	private ArrayList<Value> mArray = new ArrayList<>();


	public Array add(Value aValue)
	{
		mArray.add(aValue);
		return this;
	}


	public Array add(double aValue)
	{
		mArray.add(new NumberValue(aValue));
		return this;
	}


	public int size()
	{
		return mArray.size();
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print("[");

		boolean first = true;
		for (Value value : mArray)
		{
			if (!first)
			{
				aOutput.print(" ");
			}
			value.writeTo(aOutput);
			first = false;
		}

		aOutput.print("]");
	}
}
