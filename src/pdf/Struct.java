package pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import pdf.writer.Output;


public class Struct
{
	private Dictionary mDictionary;
	private Value mContent;


	public Struct()
	{
	}


	public Struct(Dictionary aDictionary)
	{
		setDictionary(aDictionary);
	}


	public Struct(boolean aCompress, Value aContent) throws IOException
	{
		setContent(aCompress, aContent);
	}


	public Struct(boolean aCompress, Value aContent, Dictionary aDictionary) throws IOException
	{
		setContent(aCompress, aContent);
		setDictionary(aDictionary);
	}


	public Dictionary getDictionary()
	{
		return mDictionary;
	}


	public Struct setDictionary(Dictionary aDictionary)
	{
		mDictionary = aDictionary;
		return this;
	}


	public Value getContent()
	{
		return mContent;
	}


	public void setContent(boolean aCompress, Value aContent) throws IOException
	{
		if (mDictionary == null)
		{
			mDictionary = new Dictionary();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (aCompress)
		{
			try (Output out = new Output(new DeflaterOutputStream(baos)))
			{
				aContent.writeTo(out);
			}

			mContent = new ByteValue(baos.toByteArray());

			mDictionary.put("/Filter", new TextValue("/FlateDecode"));
			mDictionary.put("/Length1", new NumberValue(baos.size()));
		}
		else
		{
			try (Output out = new Output(baos))
			{
				aContent.writeTo(out);
			}

			mContent = new ByteValue(baos.toByteArray());
		}

		mDictionary.put("/Length", new NumberValue(baos.size()));
	}


	public void write(Output aOutput) throws IOException
	{
		if (mContent != null)
		{
			aOutput.println("stream");
			mContent.writeTo(aOutput);
			aOutput.println("endstream");
		}
	}
}
