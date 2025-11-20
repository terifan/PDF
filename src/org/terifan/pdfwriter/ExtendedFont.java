package org.terifan.pdfwriter;

import org.terifan.font.FontFile;
import org.terifan.font.truetype.TrueTypeFont;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class ExtendedFont extends Font implements Value, Cloneable
{
	private final static boolean ALWAYS_COMPRESS_FONT_DATA = true;

	private final static double SCALE = 72;

	private FontFile mFontFile;
	private TreeMap<Integer, Integer> mGlyphMap;
	private Ref mResourceRef;
	private byte[] mFontData;


	private ExtendedFont()
	{
	}


	public ExtendedFont(byte[] aFontData)
	{
		this(() -> aFontData);
	}


	public ExtendedFont(Provider aProvider)
	{
		try
		{
			mFontData = aProvider.get();
			mFontFile = new TrueTypeFont(mFontData);
			mGlyphMap = new TreeMap<>();
		}
		catch (Exception e)
		{
			throw new IllegalStateException(e);
		}
	}


	@Override
	public void reuse()
	{
		mResourceRef = null;
		mGlyphMap.clear();
	}


	@FunctionalInterface
	public interface Provider
	{
		byte[] get() throws Exception;
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


	public double getDefaultWidth()
	{
		return getMedianWidth();
	}


	private double getPopularWidth()
	{
		HashMap<Double, Integer> def = new HashMap<>();
		for (int i : mGlyphMap.values())
		{
			double w = mFontFile.getGlyphWidth(i);
			def.put(w, def.getOrDefault(w, 0) + 1);
		}
		int c = 0;
		double w = 0;
		for (Entry<Double, Integer> entry : def.entrySet())
		{
			if (entry.getValue() > c)
			{
				c = entry.getValue();
				w = entry.getKey();
			}
		}
		return SCALE * w;
	}


	private double getMedianWidth()
	{
		ArrayList<Double> symbols = new ArrayList<>();
		for (int i : mGlyphMap.values())
		{
			symbols.add(mFontFile.getGlyphWidth(i));
		}
		symbols.sort(Double::compare);

		return SCALE * symbols.get(symbols.size() / 2);
	}


	private double getAverageWidth()
	{
		double w = 0;
		Collection<Integer> symbols = mGlyphMap.values();
		for (int i = 0; i < symbols.size(); i++)
		{
			w += mFontFile.getGlyphWidth(i);
		}
		return SCALE * w / symbols.size();
	}


	/**
	 * NOTE: problem exists! If this information is added to the font declaration space characters cannot be selected...
	 */
	public Array generateWidthsArray()
	{
		Array array = new Array();

		List<Integer> symbols = mGlyphMap.keySet().stream().map(e -> mGlyphMap.get(e)).sorted().collect(Collectors.toList());

		double ss = SCALE;

		// {32=321, 40=345, 41=346, 44=325, 45=341, 46=324, 48=290, 49=291, 50=292, 51=293,
//		System.out.println(mGlyphMap);

		for (int charIndex = 0; charIndex < symbols.size(); charIndex++)
		{
//			System.out.println(mGlyphMap);
//			System.out.println(mGlyphMap.get(symbols.get(charIndex))+"\t" + mFontFile.getGlyphWidth(symbols.get(charIndex)));
		}
//		System.out.println(mGlyphMap.get(32));

		for (int symbolIndex = 0, count = symbols.size(); symbolIndex < count; symbolIndex++)
		{
			if (symbolIndex < count - 1)
			{
				int s0 = symbols.get(symbolIndex + 0);
				int s1 = symbols.get(symbolIndex + 1);
				double w0 = ss * mFontFile.getGlyphWidth(s0);
				double w1 = ss * mFontFile.getGlyphWidth(s1);

				if (w0 == w1)
				{
					int j = symbolIndex + 1;
					for (; j < count; j++)
					{
						int s = symbols.get(j);
						if (w0 != ss * mFontFile.getGlyphWidth(s))
						{
							break;
						}
						s1 = s;
					}

					array.add(s0).add(s1).add(w0);
					symbolIndex = j - 1;
					continue;
				}
				else if (s1 == s0 + 1)
				{
					Array widths = new Array();
					widths.add(w0);

					int j = symbolIndex + 1;
					for (int k = 0; j < count - 1; k++, j++)
					{
						int s2 = symbols.get(j + 0);
						if (s0 + k + 1 != s2)
						{
							break;
						}

						int s3 = symbols.get(j + 1);
						double w2 = ss * mFontFile.getGlyphWidth(s2);
						double w3 = s3 == count ? -1 : ss * mFontFile.getGlyphWidth(s3);
						if (w2 == w3) // if a repetition is found
						{
							break;
						}

						widths.add(w2);
					}

					array.add(s0).add(widths);
					symbolIndex = j - 1;
					continue;
				}
			}

			int symbol = symbols.get(symbolIndex);
			double w = ss * mFontFile.getGlyphWidth(symbol);
			array.add(symbol).add(new Array().add(w));
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
		aOutput.println("/CMapName " + "/" + mFontFile.getName().replace(" ", "+") + " def");
		aOutput.println("/CMapType 2 def");
		aOutput.println("1 begincodespacerange");
		aOutput.println("<0000> <FFFF>");
		aOutput.println("endcodespacerange");

		Integer[] keys = mGlyphMap.keySet().toArray(Integer[]::new);

		for (int index = 0; index < keys.length;)
		{
			int size = Math.min(mGlyphMap.size() - index, 100);

			aOutput.println(size + " beginbfchar");

			for (int i = 0; i < size; i++, index++)
			{
				aOutput.println(String.format("<%04X> <%04X>", mGlyphMap.get(keys[index]), keys[index]));
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
		mGlyphMap.putIfAbsent(aGlyph, aCharacter);
	}


	@Override
	Ref print(PDFWriter aWriter) throws IOException
	{
		if (mResourceRef == null)
		{
			Ref data = aWriter.print(new Obj(ALWAYS_COMPRESS_FONT_DATA | aWriter.mCompress, new ArrayValue(getFontData())));
			Ref cmap = aWriter.print(new Obj(aWriter.mCompress, this));

			Array box = new Array(
				SCALE * mFontFile.getFontBBox()[0],
				SCALE * mFontFile.getFontBBox()[1],
				SCALE * mFontFile.getFontBBox()[2],
				SCALE * mFontFile.getFontBBox()[3]
			);

			String fontName = "/" + mFontFile.getName().replace(" ", "+");

			Ref fontDescriptor = aWriter.print(new Obj(aWriter.mCompress, null, new Dictionary()
				.put("/Type", "/FontDescriptor")
				.put("/FontName", fontName)
				.put("/Flags", 4) // see 5.7.1 Font Descriptor Flag
				.put("/Ascent", SCALE * mFontFile.getAscent())
				.put("/Descent", SCALE * mFontFile.getDescent())
				.put("/StemV", SCALE * (mFontFile.getFontBBox()[2] - mFontFile.getFontBBox()[0]) * 0.13)
				.put("/CapHeight", SCALE * mFontFile.getAscent() * 0.8)
				.put("/AvgWidth", getAverageWidth())
				.put("/ItalicAngle", 0)
				.put("/FontBBox", box)
				.put("/FontFile2", data)
			)
			);

			Obj o = new Obj(aWriter.mCompress, null, new Dictionary()
				.put("/Type", "/Font")
				.put("/FontDescriptor", fontDescriptor)
				.put("/BaseFont", fontName)
				.put("/Subtype", "/CIDFontType2")
				.put("/CIDToGIDMap", "/Identity")
				.put("/CIDSystemInfo", new Dictionary()
					.put("/Registry", "(Adobe)")
					.put("/Ordering", "(Identity)")
					.put("/Supplement", 0))
				.put("/W", generateWidthsArray())
				.put("/DW", getDefaultWidth())
			);

			Ref descendantFont = aWriter.print(o);

			mResourceRef = aWriter.print(new Obj(new Dictionary()
				.put("/Type", "/Font")
				.put("/Subtype", "/Type0")
				.put("/BaseFont", fontName)
				.put("/Encoding", "/Identity-H")
				.put("/DescendantFonts", new Array().add(descendantFont))
				.put("/ToUnicode", cmap)
			));

			mFontData = null;
		}

		return mResourceRef;
	}


	@Override
	public ExtendedFont clone() throws CloneNotSupportedException
	{
		try
		{
			return (ExtendedFont)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			ExtendedFont font = new ExtendedFont();
			font.mFontFile = mFontFile;
			font.mGlyphMap = mGlyphMap;
			font.mFontData = mFontData;
			font.mResourceRef = mResourceRef;
			return font;
		}
	}
}
