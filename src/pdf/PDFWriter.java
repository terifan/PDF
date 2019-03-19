package pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class PDFWriter implements AutoCloseable
{
	private ArrayList<Integer> mReferences;
	private ArrayList<Page> mPages;
	private Output mOutput;
	private HashMap<Resource, Ref> mFonts;
	protected boolean mCompress;


	public PDFWriter(OutputStream aOutput) throws IOException
	{
		mReferences = new ArrayList<>();
		mPages = new ArrayList<>();
		mFonts = new HashMap<>();
		mCompress = true;

		mOutput = new Output(aOutput);
		mOutput.println("%PDF-1.3");
	}


	public PDFWriter setCompress(boolean aCompress)
	{
		mCompress = aCompress;
		return this;
	}


	public Page addPage()
	{
		Page page = new Page(this);
		mPages.add(page);
		return page;
	}


	public Ref print(Obj aObject) throws IOException
	{
		mReferences.add(mOutput.size());

		mOutput.println(mReferences.size() + " 0 obj");
		aObject.write(mOutput);
		mOutput.println("endobj");

		return new Ref(mReferences.size());
	}


	Array getFontWidths(ExtendedFont aFontRef)
	{
		return aFontRef.generateWidthsArray();
	}


	/**
	 * Registers a font, the value is null until the write closing
	 */
	void registerFont(Resource aFont) throws IOException
	{
		mFonts.put(aFont, null);
	}


	/**
	 * Page calls this method when writer is closing
	 */
	Ref getFontRef(Resource aFont)
	{
		return mFonts.get(aFont);
	}


	@Override
	public void close() throws IOException
	{
		for (Page page : mPages)
		{
			page.close();
		}

		for (Resource font : mFonts.keySet())
		{
			mFonts.put(font, font.print(this));
		}

		Array pages = new Array();
		for (Page page : mPages)
		{
			pages.add(page.printHeader());
		}

		Ref pagesRef = print(new Obj().setDictionary(new Dictionary().put("/Count", pages.size()).put("/Type", "/Pages").put("/Kids", pages)));
		Ref root = print(new Obj().setDictionary(new Dictionary().put("/Type", "/Catalog").put("/Pages", pagesRef)));

		int xref = mOutput.size();

		Dictionary trailer = new Dictionary().put("/Size", 1 + mReferences.size()).put("/Root", root);

		mOutput.println("xref");
		mOutput.println(String.format("0 %d", 1 + mReferences.size()));
		mOutput.println("0000000000 65535 f");

		for (Integer ref : mReferences)
		{
			mOutput.println(String.format("%010d 00000 n", ref));
		}

		mOutput.println("trailer");
		trailer.writeTo(mOutput);
		mOutput.println("startxref");
		mOutput.println(Integer.toString(xref));
		mOutput.println("%%EOF");

		mOutput.close();
	}
}
