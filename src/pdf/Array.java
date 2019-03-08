package pdf;

import java.io.IOException;
import java.util.ArrayList;


public class Array implements Value
{
	private ArrayList<Value> mArray = new ArrayList<>();


	public Array()
	{
	}


	public Array(double[] aValues)
	{
		for (double value : aValues)
		{
			add(value);
		}
	}


	public Array add(Value aValue)
	{
		mArray.add(aValue);
		return this;
	}


	public Array add(String aValue)
	{
		mArray.add(new TextValue(aValue));
		return this;
	}


	public Array add(int aValue)
	{
		mArray.add(new NumberValue(aValue));
		return this;
	}


	public Array add(double aValue)
	{
		mArray.add(new NumberValue(aValue));
		return this;
	}


	public Value get(int aIndex)
	{
		return mArray.get(aIndex);
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


	public org.terifan.bundle.Array asJSON() throws IOException
	{
		org.terifan.bundle.Array array = new org.terifan.bundle.Array();
		for (Value value : mArray)
		{
			if (value instanceof NumberValue)
			{
				if (((NumberValue)value).getDouble() == null)
				{
					array.add(((NumberValue)value).getInteger());
				}
				else
				{
					array.add(((NumberValue)value).getDouble());
				}
			}
			else if (value instanceof TextValue)
			{
				array.add(((TextValue)value).getValue());
			}
			else if (value instanceof Array)
			{
				array.add(((Array)value).asJSON());
			}
			else if (value instanceof Obj)
			{
				array.add(((Dictionary)value).asJSON());
			}
			else
			{
				throw new IllegalStateException("unsupported: " + value);
			}
		}
		return array;
	}
}
