package pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;


public class Struct
{
	private Dictionary mDictionary;
	private byte[] mContent;


	public Struct()
	{
	}


	public Struct(Dictionary aDictionary)
	{
		setDictionary(aDictionary);
	}


	public Struct(boolean aCompress, byte[] aContent) throws IOException
	{
		setContent(aCompress, aContent);
	}


	public Struct(boolean aCompress, byte[] aContent, Dictionary aDictionary) throws IOException
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


	public byte[] getContent()
	{
		return mContent;
	}


	public void setContent(boolean aCompress, byte[] aContent) throws IOException
	{
		if (mDictionary == null)
		{
			mDictionary = new Dictionary();
		}

		if (aCompress)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (DeflaterOutputStream dos = new DeflaterOutputStream(baos))
			{
				dos.write(aContent);
			}

			mContent = baos.toByteArray();

			mDictionary.put("/Filter", "/FlateDecode");
			mDictionary.put("/Length1", mContent.length);
		}
		else
		{
			mContent = aContent;
		}

		mDictionary.put("/Length", aContent.length);
	}
}
