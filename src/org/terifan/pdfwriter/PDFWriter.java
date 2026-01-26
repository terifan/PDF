package org.terifan.pdfwriter;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class PDFWriter implements AutoCloseable
{
	private ArrayList<Offset> mObjectOffsets;
	private ArrayList<Page> mPages;
	private Output mOutput;
	private HashMap<Resource, Ref> mFonts;
	protected boolean mCompress;

	public final static Dimension A4_Portrait = new Dimension(595, 842);
	public final static Dimension A4_Landscape = new Dimension(842, 595);


	public PDFWriter(OutputStream aOutput) throws IOException
	{
		mObjectOffsets = new ArrayList<>();
		mPages = new ArrayList<>();
		mFonts = new HashMap<>();
		mCompress = true;

		mOutput = new Output(aOutput);
		mOutput.println("%PDF-1.6");
	}


	public PDFWriter setCompress(boolean aCompress)
	{
		mCompress = aCompress;
		return this;
	}


	public Page addPage()
	{
		return addPage(mPages.isEmpty() ? A4_Portrait : mPages.getLast().getDimension());
	}


	public Page addPage(Dimension aDimension)
	{
		Page page = new Page(this, aDimension);
		mPages.add(page);
		return page;
	}


	ObjRef alloc(Obj aObject) throws IOException
	{
		Offset offset = new Offset(-1);

		mObjectOffsets.add(offset);

		return new ObjRef(new Ref(mObjectOffsets.size()), aObject, offset);
	}


	public Ref print(Obj aObject) throws IOException
	{
		mObjectOffsets.add(new Offset(mOutput.size()));

		mOutput.println(mObjectOffsets.size() + " 0 obj");
		aObject.write(mOutput);
		mOutput.println("");
		mOutput.println("endobj");

		return new Ref(mObjectOffsets.size());
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
		if (mOutput == null)
		{
			return;
		}

		for (Page page : mPages)
		{
			page.close();
		}

		for (Resource font : mFonts.keySet())
		{
			mFonts.put(font, font.print(this));
		}

		Dictionary pagesDic = new Dictionary()
			.put("/Type", "/Pages");

		ObjRef pagesObj = alloc(new Obj().setDictionary(pagesDic));

		Array pages = new Array();
		for (Page page : mPages)
		{
			page.setParent(pagesObj);
			pages.add(page.printHeader());
		}

		pagesDic
			.put("/Kids", pages)
			.put("/Count", pages.size());

		Ref root = print(new Obj()
			.setDictionary(new Dictionary()
				.put("/Type", "/Catalog")
				.put("/Pages", pagesObj.getReference())
			)
		);

		pagesObj.writeTo(mOutput);

		int xref = mOutput.size();

		Dictionary trailer = new Dictionary()
			.put("/Size", 1 + mObjectOffsets.size())
			.put("/Root", root);

		mOutput.println("xref");
		mOutput.println(String.format("0 %d", 1 + mObjectOffsets.size()));
		mOutput.println("0000000000 65535 f");

		for (Offset offset : mObjectOffsets)
		{
			offset.print(mOutput);
		}

		mOutput.println("trailer");
		trailer.writeTo(mOutput);
		mOutput.println("");
		mOutput.println("startxref");
		mOutput.println(Integer.toString(xref));
		mOutput.println("%%EOF");

		mOutput.close();
		mOutput = null;
	}
}
