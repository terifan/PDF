package pdf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;


public class Page implements AutoCloseable
{
	private final PDFWriter mWriter;
	private StringBuilder mBuffer;
	private HashMap<String, Font> mFonts;
	private int mImageCount;
	private Ref mRefContent;
	private Dictionary mXObjectResourceDictionary;
	private Dictionary mResourcesDictionary;
	private HashMap<UUID, String> mIdentityMap;


	Page(PDFWriter aWriter)
	{
		mWriter = aWriter;
		mBuffer = new StringBuilder();
		mFonts = new HashMap<>();
		mResourcesDictionary = new Dictionary();
		mIdentityMap = new HashMap<>();
	}


	public void append(Producer aProducer) throws IOException
	{
		mBuffer.append(aProducer.produce(mWriter, this));
	}


	public void append(String aPDFCode)
	{
		mBuffer.append(aPDFCode);
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

			registerXObject(aImage.getIdentity(), aImage.writeTo(mWriter));
		}
	}


	@Override
	public void close() throws IOException
	{
		if (mRefContent == null)
		{
			mRefContent = mWriter.print(new Obj(mWriter.mCompress, new TextValue(mBuffer.toString())));
		}
	}


	void registerXObject(String aName, Ref aRef)
	{
		if (mXObjectResourceDictionary == null)
		{
			mXObjectResourceDictionary = new Dictionary();
			mResourcesDictionary.put("/XObject", mXObjectResourceDictionary);
		}

		mXObjectResourceDictionary.put(aName, aRef);
	}


	Ref printHeader() throws IOException
	{
		Dictionary resDic = new Dictionary();
		resDic.put("/ProcSet", new Array().add("/PDF").add("/Text"));

		if (!mFonts.isEmpty())
		{
			Dictionary fontsDic = new Dictionary();
			for (Entry<String, Font> font : mFonts.entrySet())
			{
				fontsDic.put(font.getKey(), mWriter.getFontRef(font.getValue()));
			}
			resDic.put("/Font", fontsDic);
		}

		if (mXObjectResourceDictionary != null)
		{
//			Ref ref = mWriter.print(new Obj().setDictionary(new Dictionary().put("/XObject", mXObjectResourceDictionary)));

			resDic.put("/XObject", mXObjectResourceDictionary);
		}

		Ref refResources = mWriter.print(new Obj(resDic));

		return mWriter.print(new Obj(new Dictionary().put("/Type", "/Page").put("/MediaBox", "[0 0 595 842]").put("/Contents", mRefContent).put("/Resources", refResources)));
	}
}
