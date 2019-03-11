package pdf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;


public class Page implements AutoCloseable
{
	private final PDFWriter mWriter;
	private StringBuilder mBuffer;
	private HashMap<String, Font> mFonts;
	private Ref mRefContent;


	Page(PDFWriter aWriter)
	{
		mWriter = aWriter;
		mBuffer = new StringBuilder();
		mFonts = new HashMap<>();
	}


	public void append(TextArea aTextArea) throws IOException
	{
		mBuffer.append(aTextArea.produce(this));
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
		Font existing = mFonts.get(aFont.getIdentity());

		if (existing != null && existing != aFont)
		{
			throw new IllegalArgumentException("Font identity already used with another font: " + aFont.getIdentity());
		}

		if (existing == null)
		{
			mWriter.registerFont(aFont);

			mFonts.put(aFont.getIdentity(), aFont);
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


	Ref printHeader() throws IOException
	{
		Dictionary fontsDic = new Dictionary();

		for (Entry<String, Font> font : mFonts.entrySet())
		{
			fontsDic.put(font.getKey(), mWriter.getFontRef(font.getValue()));
		}

		Ref refResources = mWriter.print(new Obj(new Dictionary().put("/Font", fontsDic)));

		return mWriter.print(new Obj(new Dictionary().put("/Type", "/Page").put("/MediaBox", "[0 0 595 842]").put("/Contents", mRefContent).put("/Resources", refResources)));
	}
}
