package org.terifan.pdfwriter;

import org.terifan.font.FontFile;
import org.terifan.font.truetype.TrueTypeFont;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class ExtendedFont extends Font implements Value
{
	private final static boolean ALWAYS_COMPRESS_FONT_DATA = true;

	private FontFile mFontFile;
	private TreeMap<Integer, Integer> mGlyphMap;
	private byte[] mFontData;
	private Ref mResourceRef;


	public ExtendedFont(byte[] aFontData)
	{
		mFontData = aFontData;
		mFontFile = new TrueTypeFont(aFontData);
		mGlyphMap = new TreeMap<>();
	}


	public byte[] getFontData()
	{
		return mFontData;
	}


	@Override
	public FontFile getFontFile()
	{
		return mFontFile;
	}


	/**
	 * NOTE: problem exists! If this information is added to the font declaration space characters cannot be selected...
	 */
	public Array generateWidthsArray()
	{
		Array array = new Array();

		List<Integer> symbols = mGlyphMap.keySet().stream().map(e->mGlyphMap.get(e)).sorted().collect(Collectors.toList());

		for (int i = 0; i < symbols.size(); i++)
		{
			boolean done = false;

			if (i < symbols.size() - 1)
			{
				Integer s0 = symbols.get(i + 0);
				Integer s1 = symbols.get(i + 1);
				double w0 = mFontFile.getGlyphWidth(s0);
				double w1 = mFontFile.getGlyphWidth(s1);

				if (w0 == w1)
				{
					int j = i + 1;
					for (; j < symbols.size(); j++)
					{
						Integer s = symbols.get(j);
						double w = mFontFile.getGlyphWidth(s);
						if (w0 != w)
						{
							break;
						}
						s1 = s;
					}

					array.add(s0).add(s1).add(w0);
					done = true;
					i = j - 1;
				}
				else if (s0 + 1 == s1)
				{
					Array widths = new Array();
					widths.add(w0);

					int j = i + 1;
					for (int k = 0; j < symbols.size(); k++, j++)
					{
						Integer s2 = symbols.get(j + 0);
						Integer s3 = symbols.get(j + 1);
						double w2 = mFontFile.getGlyphWidth(s2);
						double w3 = s3 == symbols.size() ? -1 : mFontFile.getGlyphWidth(s3);
						if (w2 == w3) // if a repetition is found
						{
							break;
						}
						if (s0 + k + 1 != s2)
						{
							break;
						}
						widths.add(w2);
					}

					array.add(s0).add(widths);
					done = true;
					i = j - 1;
				}
			}

			if (!done)
			{
				Integer symbol = symbols.get(i);
				double w = mFontFile.getGlyphWidth(symbol);
				array.add(symbol).add(new Array().add(w));
			}
		}

		return array;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.println("/CIDInit /ProcSet findresource begin");
		aOutput.println("12 dict begin");
		aOutput.println("begincmap");
		aOutput.println("/CIDSystemInfo");
		aOutput.println("<< /Registry (Adobe) /Ordering (UCS) /Supplement 0 >> def");
		aOutput.println("/CMapName /Adobe-Identity-UCS def");
		aOutput.println("/CMapType 2 def");
		aOutput.println("1 begincodespacerange");
		aOutput.println("<0000> <FFFF>");
		aOutput.println("endcodespacerange");

		Integer[] keys = mGlyphMap.keySet().toArray(new Integer[mGlyphMap.size()]);

		for (int outer = 0; outer < keys.length; outer += 100)
		{
			int size = Math.min(mGlyphMap.size() - outer * 100, 100);

			aOutput.println(size + " beginbfchar");

			for (int inner = outer; inner < outer + size; inner++)
			{
				aOutput.println(String.format("<%04X> <%04X>", mGlyphMap.get(keys[inner]), keys[inner]));
			}

			aOutput.println("endbfchar");
		}

		aOutput.println("endcmap");
		aOutput.println("CMapName currentdict /CMap defineresource pop");
		aOutput.println("end");
		aOutput.println("end");
	}


	public String generateCMap() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output out = new Output(baos);
		writeTo(out);
		return baos.toString();
	}


	@Override
	public void registerGlyph(int aGlyph, int aCharacter)
	{
		mGlyphMap.put(aGlyph, aCharacter);
	}


	@Override
	Ref print(PDFWriter aWriter) throws IOException
	{
		if (mResourceRef == null)
		{
			Ref data = aWriter.print(new Obj(ALWAYS_COMPRESS_FONT_DATA | aWriter.mCompress, new ArrayValue(getFontData())));
			Ref cmap = aWriter.print(new Obj(aWriter.mCompress, this));

			Array box = new Array(mFontFile.getFontBBox());

			String fontName = "/" + mFontFile.getName().replace(" ", "+");

			Ref fontDescriptor = aWriter.print(new Obj(aWriter.mCompress, null, new Dictionary()
				.put("/Type", "/FontDescriptor")
				.put("/FontName", fontName)
				.put("/Ascent", mFontFile.getAscent())
				.put("/CapHeight", 715)
				.put("/Descent", mFontFile.getDescent())
				.put("/Flags", 0)
				.put("/FontBBox", box)
				.put("/FontFile2", data)
				.put("/ItalicAngle", 0)
				.put("/StemV", 76))
			);

			Ref descendantFont = aWriter.print(new Obj(aWriter.mCompress, null, new Dictionary()
				.put("/Type", "/Font")
				.put("/Subtype", "/CIDFontType2")
				.put("/BaseFont", fontName)
				.put("/CIDSystemInfo", new Dictionary()
					.put("/Ordering", "(Identity)")
					.put("/Registry", "(Adobe)")
					.put("/Supplement", 0))
				.put("/CIDToGIDMap", "/Identity")
				.put("/FontDescriptor", fontDescriptor)
			));

			mResourceRef = aWriter.print(new Obj(new Dictionary()
				.put("/Type", "/Font")
				.put("/Subtype", "/Type0")
				.put("/BaseFont", fontName)
				.put("/DescendantFonts", new Array().add(descendantFont))
				.put("/Encoding", "/Identity-H")
				.put("/ToUnicode", cmap)
			));

			mFontData = null;
		}

		return mResourceRef;
	}
}
