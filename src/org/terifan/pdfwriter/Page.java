package org.terifan.pdfwriter;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;


public class Page implements AutoCloseable
{
	private final PDFWriter mWriter;
	private final Dimension mDimension;
	private StringBuilder mBuffer;
	private HashMap<String, Resource> mFonts;
	private HashMap<String, Dictionary> mExtGState;
	private int mImageCount;
	private Ref mRefContent;
	private Dictionary mXObjectResourceDictionary;
	private Dictionary mResourcesDictionary;
	private HashMap<UUID, String> mIdentityMap;
	private ObjRef mParent;


	Page(PDFWriter aWriter, Dimension aDimension)
	{
		mWriter = aWriter;
		mDimension = aDimension;
		mBuffer = new StringBuilder();
		mFonts = new HashMap<>();
		mResourcesDictionary = new Dictionary();
		mIdentityMap = new HashMap<>();
		mXObjectResourceDictionary = new Dictionary();
		mExtGState = new HashMap<>();
	}


	public void append(Producer aProducer) throws IOException
	{
		mBuffer.append(aProducer.produce(mWriter, this));
	}


	public void append(String aRawPdfCode)
	{
		mBuffer.append(aRawPdfCode);
	}


	public void registerFont(Style aStyle) throws IOException
	{
		registerFont(aStyle.getFont());
	}


	public void registerFont(Font aFont) throws IOException
	{
		if (!mIdentityMap.containsKey(aFont.getUUID()))
		{
			String identity = "/f" + mFonts.size();
			mIdentityMap.put(aFont.getUUID(), identity);
			aFont.setIdentity(identity);

			mWriter.registerFont(aFont);
			mFonts.put(aFont.getIdentity(), aFont);
		}
	}


	public void registerImage(Image aImage) throws IOException
	{
		if (!mIdentityMap.containsKey(aImage.getUUID()))
		{
			String identity = "/im" + mImageCount++;
			mIdentityMap.put(aImage.getUUID(), identity);
			aImage.setIdentity(identity);

			mXObjectResourceDictionary.put(identity, aImage.print(mWriter));
		}
	}


	public Ref registerEmbeddedFile(EmbeddedFile aEmbeddedFile) throws IOException
	{
		String identity = "/EF" + mImageCount++;
		mIdentityMap.put(aEmbeddedFile.getUUID(), identity);
		aEmbeddedFile.setIdentity(identity);
		Ref ref = aEmbeddedFile.print(mWriter);
		mXObjectResourceDictionary.put(identity, ref);
		return ref;
	}


	@Override
	public void close() throws IOException
	{
		if (mRefContent == null)
		{
			mRefContent = mWriter.print(new Obj(mWriter.mCompress, new TextValue(mBuffer.toString())));
		}
	}


	public Dimension getDimension()
	{
		return mDimension;
	}


	Ref printHeader() throws IOException
	{
		Array resArr = new Array();
		resArr.add("/PDF").add("/Text");

		Ref refResources = mWriter.print(new Obj().setContent(resArr));

		Dictionary resDic = new Dictionary();
		resDic.put("/ProcSet", refResources);

		if (!mFonts.isEmpty())
		{
			Dictionary fontsDic = new Dictionary();
			for (Entry<String, Resource> font : mFonts.entrySet())
			{
				fontsDic.put(font.getKey(), mWriter.getFontRef(font.getValue()));
			}
			resDic.put("/Font", fontsDic);
		}

		if (!mExtGState.isEmpty())
		{
			for (Entry<String, Dictionary> entry : mExtGState.entrySet())
			{
				resDic.put(entry.getKey(), new Dictionary().put("/Type", "/ExtGState").putAll(entry.getValue()));
			}

			System.out.println(resDic);
		}

		if (!mXObjectResourceDictionary.isEmpty())
		{
			mResourcesDictionary.put("/XObject", mXObjectResourceDictionary);
			resDic.put("/XObject", mXObjectResourceDictionary);
		}

		return mWriter.print(new Obj(new Dictionary()
			.put("/Type", "/Page")
			.put("/Parent", mParent.getReference())
			.put("/MediaBox", "[0 0 " + mDimension.width + " " + mDimension.height + "]")
			.put("/Contents", mRefContent)
			.put("/Resources", resDic)
		));
	}


	void setParent(ObjRef aPagesRef)
	{
		mParent = aPagesRef;
	}


	String registerExtGState(Dictionary aExtGState)
	{
		for (Entry<String, Dictionary> entry : mExtGState.entrySet())
		{
			if (entry.getValue().equals(aExtGState))
			{
				return entry.getKey();
			}
		}
		String key = "/GS" + (1 + mExtGState.size());
		mExtGState.put(key, aExtGState);
		return key;
	}
}
