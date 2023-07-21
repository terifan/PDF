package org.terifan.pdfwriter;

import java.io.IOException;


public class EmbeddedFile extends Resource
{
	private byte[] mData;
	private Ref mResourceRef;
	private Margins mMargins;
	private String mType;


	public EmbeddedFile(byte[] aData, String aType) throws IOException
	{
		mMargins = new Margins();
		mType = aType;
		mData = aData;
	}


	public boolean isReady()
	{
		return mData != null;
	}


	public Margins getMargins()
	{
		return mMargins;
	}


	public EmbeddedFile setMargins(Margins aMargins)
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
