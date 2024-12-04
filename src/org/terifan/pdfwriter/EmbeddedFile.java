package org.terifan.pdfwriter;

import java.io.IOException;


public class EmbeddedFile extends Resource
{
	private byte[] mData;
	private Ref mResourceRef;
	private Insets mMargins;
	private String mType;


	public EmbeddedFile(byte[] aData, String aType) throws IOException
	{
		mMargins = new Insets();
		mType = aType;
		mData = aData;
	}


	public boolean isReady()
	{
		return mData != null;
	}


	public Insets getMargins()
	{
		return mMargins;
	}


	public EmbeddedFile setMargins(Insets aMargins)
	{
		mMargins = aMargins;
		return this;
	}


	@Override
	Ref print(PDFWriter aWriter) throws IOException
	{
		if (mResourceRef == null)
		{
			Dictionary dic = new Dictionary();
			dic.put("/Type", "/EmbeddedFile");
			dic.put("/Subtype", "/" + mType);

			mResourceRef = aWriter.print(new Obj(false, new ArrayValue(mData), dic));
			mData = null;
		}

		return mResourceRef;
	}
}
