package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;


public class Obj
{
	private Dictionary mDictionary;
	private Value mContent;


	public Obj()
	{
	}


	public Obj(Dictionary aDictionary)
	{
		setDictionary(aDictionary);
	}


	public Obj(boolean aCompress, Value aContent) throws IOException
	{
		setContent(aCompress, aContent);
	}


	public Obj(boolean aCompress, Value aContent, Dictionary aDictionary) throws IOException
	{
		if (aContent != null)
		{
			setContent(aCompress, aContent);
		}
		setDictionary(aDictionary);
	}


	public Dictionary getDictionary()
	{
		return mDictionary;
	}


	public Obj setDictionary(Dictionary aDictionary)
	{
		mDictionary = aDictionary;
		return this;
	}


	public Value getContent()
	{
		return mContent;
	}


	private void setContent(boolean aCompress, Value aContent) throws IOException
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

			mContent = new ArrayValue(baos.toByteArray());

			mDictionary.put("/Filter", new TextValue("/FlateDecode"));
			mDictionary.put("/Length1", new NumberValue(baos.size()));
		}
		else
		{
			try (Output out = new Output(baos))
			{
				aContent.writeTo(out);
			}

			mContent = new ArrayValue(baos.toByteArray());
		}

		mDictionary.put("/Length", new NumberValue(baos.size()));
	}


	void write(Output aOutput) throws IOException
	{
		if (mDictionary != null)
		{
			mDictionary.writeTo(aOutput);
		}

		if (mContent != null)
		{
			aOutput.println("stream");
			mContent.writeTo(aOutput);
			aOutput.print("endstream");
		}
	}


	public String asString() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output out = new Output(baos);
		write(out);
		return baos.toString();
	}
}
