package pdf;

import java.io.IOException;
import pdf.writer.Output;


public class Ref implements Value
{
	protected int mReference;


	public Ref(int aReference)
	{
		mReference = aReference;
	}


	public int getRef()
	{
		return mReference;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.print(mReference + " 0 R");
	}
}
