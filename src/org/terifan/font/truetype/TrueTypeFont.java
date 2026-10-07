package org.terifan.font.truetype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import org.terifan.font.FontFile;


/**
 * TTF file specification:
 * https://developer.apple.com/fonts/TrueType-Reference-Manual/
 */
public class TrueTypeFont implements FontFile
{
	private final ByteBufferReader mBuffer;

	private final HEAD mHEAD;
	private final HHEA mHHEA;
	private final HMTX mHMTX;
	private final NAME mNAME;
	private final ArrayList<CMap> mCmaps;
	private final HashMap<String, Table> mTables;
	private final HashMap<Integer, GLYF> mGlyphs;
	private final HashMap<Integer, Integer> mGlyphLookup;
	private final int mNumGlyphs;
	private final LOCA mLOCA;


	public TrueTypeFont(byte[] aData)
	{
		mBuffer = new ByteBufferReader(aData);
		mTables = new HashMap<>();
		mGlyphs = new HashMap<>();
		mGlyphLookup = new HashMap<>();
		mCmaps = new ArrayList<>();

		readOffsetTables();
		mHEAD = new HEAD(mBuffer, mTables);
		mNumGlyphs = readGlyphCount();
		mLOCA = new LOCA(mBuffer, mTables, mHEAD, mNumGlyphs);
		readCharacterMap();
		mHHEA = new HHEA(mBuffer, mTables);
		mHMTX = new HMTX(mHHEA, mNumGlyphs, mBuffer, mTables);
		mNAME = new NAME(mBuffer, mTables);
	}


	@Override
	public String getName()
	{
		String name = mNAME.getName(3, 1, 0x0409, 4);
		if (name == null)
		{
			name = mNAME.getName(null, null, null, 4);
		}
		return name == null ? "Unknown" : name;
	}


	/**
	 * left, bottom, top, right ???
	 */
	@Override
	public double[] getFontBBox()
	{
		return new double[]
		{
			mHEAD.mXMin, mHEAD.mYMin, mHEAD.mXMax, mHEAD.mYMax
		};
	}


	@Override
	public int getUnitsPerEm()
	{
		return mHEAD.mUnitsPerEm;
	}


	@Override
	public double getGlyphWidth(int aSymbol)
	{
		GLYF glyf = getGlyph(aSymbol);
		return glyf.xMax - glyf.xMin;
	}


	GLYF getGlyph(int aSymbol)
	{
		if (aSymbol < 0 || aSymbol >= mNumGlyphs)
		{
			throw new IllegalArgumentException("Glyph index out of range: " + aSymbol);
		}
		return mGlyphs.computeIfAbsent(aSymbol, s -> new GLYF(mBuffer, mTables, mLOCA, s));
	}


	@Override
	public double getGlyphAdvanceWidth(int aSymbol)
	{
		return mHMTX.getMetrics(aSymbol).mAdvanceWidth;
	}


	@Override
	public double getGlyphLeftSideBearing(int aSymbol)
	{
		return mHMTX.getMetrics(aSymbol).mLeftSideBearing;
	}


	@Override
	public double getLineHeight()
	{
		return getAscent() - getDescent() + getLineGap();
	}


	@Override
	public double getLineGap()
	{
		return mHHEA.mLineGap;
	}


	@Override
	public double getAscent()
	{
		return mHHEA.mAscent;
	}


	@Override
	public double getDescent()
	{
		return mHHEA.mDescent;
	}


	@Override
	public double getMinLeftSideBearing()
	{
		return mHHEA.mMinLeftSideBearing;
	}


	@Override
	public double getMinRightSideBearing()
	{
		return mHHEA.mMinRightSideBearing;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		return mGlyphLookup.computeIfAbsent(aCharacter, v ->
		{
			if (aCharacter < 0 || aCharacter > Character.MAX_CODE_POINT || (aCharacter >= 0xD800 && aCharacter <= 0xDFFF))
			{
				return 0;
			}

			for (CMap cmap : mCmaps)
			{
				int glyph = cmap.findGlyphIndex(aCharacter);
				if (glyph >= 0)
				{
					if (glyph >= mNumGlyphs)
					{
						throw new IllegalArgumentException("cmap glyph index out of range: " + glyph);
					}
					return glyph;
				}
			}
			return 0;
		});
	}


	// https://developer.apple.com/fonts/TrueType-Reference-Manual/RM06/Chap6.html
	private void readOffsetTables()
	{
		long scalerType = mBuffer.getUint32();
		int numTables = mBuffer.getUint16();
		mBuffer.getUint16();
		mBuffer.getUint16();
		mBuffer.getUint16();
		if (scalerType != 0x00010000L && scalerType != 0x74727565L)
		{
			throw new IllegalArgumentException("Unsupported TrueType scaler type: " + Long.toHexString(scalerType));
		}
		if (numTables == 0 || numTables > (mBuffer.length() - 12) / 16)
		{
			throw new IllegalArgumentException("Invalid SFNT table count: " + numTables);
		}

		int directoryEnd = 12 + numTables * 16;
		String previousTag = null;

		for (int i = 0; i < numTables; i++)
		{
			String tag = mBuffer.getString(4);
			if (previousTag != null && previousTag.compareTo(tag) >= 0)
			{
				throw new IllegalArgumentException("SFNT table tags are not strictly sorted: " + tag);
			}
			previousTag = tag;
			int checksum = mBuffer.getInt32();
			long offset = mBuffer.getUint32();
			long length = mBuffer.getUint32();
			if (offset > Integer.MAX_VALUE || length > Integer.MAX_VALUE || offset < directoryEnd || offset + length > mBuffer.length() || (offset & 3) != 0)
			{
				throw new IllegalArgumentException("Invalid table range for " + tag + ": offset=" + offset + ", length=" + length);
			}

			Table table = new Table(checksum, (int)offset, (int)length);
			if (mTables.putIfAbsent(tag, table) != null)
			{
				throw new IllegalArgumentException("Duplicate SFNT table: " + tag);
			}
			if (calculateTableChecksum(table, "head".equals(tag)) != checksum)
			{
				throw new IllegalArgumentException("Checksum error: tag: " + tag + ", table: " + table);
			}
		}

		for (String tag : new String[] {"head", "hhea", "hmtx", "maxp", "name", "cmap", "loca", "glyf"})
		{
			if (!mTables.containsKey(tag))
			{
				throw new IllegalArgumentException("Missing required TrueType table: " + tag);
			}
		}
	}


	private int calculateTableChecksum(Table aTable, boolean aHeadTable)
	{
		int old = mBuffer.position();
		long sum = 0;
		try
		{
			for (int offset = 0; offset < aTable.mLength; offset += 4)
			{
				long word = 0;
				for (int i = 0; i < 4; i++)
				{
					int relativeOffset = offset + i;
					int value = 0;
					if (relativeOffset < aTable.mLength && !(aHeadTable && relativeOffset >= 8 && relativeOffset < 12))
					{
						mBuffer.position(aTable.mOffset + relativeOffset);
						value = mBuffer.getUint8();
					}
					word = (word << 8) | value;
				}
				sum = (sum + word) & 0xffffffffL;
			}
		}
		finally
		{
			mBuffer.position(old);
		}
		return (int)sum;
	}


	private int readGlyphCount()
	{
		Table maxp = mTables.get("maxp");
		if (maxp.mLength < 32)
		{
			throw new IllegalArgumentException("Invalid maxp table length: " + maxp.mLength);
		}
		mBuffer.position(maxp.mOffset);
		if (mBuffer.getUint32() != 0x00010000L)
		{
			throw new IllegalArgumentException("Unsupported maxp table version");
		}
		int numGlyphs = mBuffer.getUint16();
		if (numGlyphs == 0)
		{
			throw new IllegalArgumentException("Font has no glyphs");
		}
		return numGlyphs;
	}


	// https://developer.apple.com/fonts/TrueType-Reference-Manual/RM06/Chap6cmap.html
	private void readCharacterMap()
	{
		Table cmap = mTables.get("cmap");
		if (cmap.mLength < 4)
		{
			throw new IllegalArgumentException("Invalid cmap table length: " + cmap.mLength);
		}
		int startPosition = cmap.mOffset;

		mBuffer.position(startPosition);
		int version = mBuffer.getUint16();
		int numberSubtables = mBuffer.getUint16();

		if (version != 0)
		{
			throw new IllegalStateException("Unexpected cmap version: " + version);
		}

		if (4L + 8L * numberSubtables > cmap.mLength)
		{
			throw new IllegalArgumentException("Truncated cmap encoding records");
		}

		ArrayList<CMapTable> list = new ArrayList<>();
		HashMap<CMapTable, Integer> priorities = new HashMap<>();

		for (int i = 0; i < numberSubtables; i++)
		{
			int platformId = mBuffer.getUint16();
			int platformSpecificId = mBuffer.getUint16();
			long offset = mBuffer.getUint32();
			if (offset < 4L + 8L * numberSubtables || offset + 2 > cmap.mLength || offset > Integer.MAX_VALUE)
			{
				throw new IllegalArgumentException("Invalid cmap subtable offset: " + offset);
			}
			CMapTable cmapTable = new CMapTable(platformId, platformSpecificId, (int)offset);
			int nextRecordPosition = mBuffer.position();
			mBuffer.position(startPosition + (int)offset);
			int format = mBuffer.getUint16();
			mBuffer.position(nextRecordPosition);
			int priority = cmapTable.getPriority(format);
			if (priority != Integer.MAX_VALUE)
			{
				list.add(cmapTable);
				priorities.put(cmapTable, priority);
			}
		}
		list.sort(Comparator.comparingInt(priorities::get));

		for (CMapTable cmapTable : list)
		{
			int subtableStart = startPosition + cmapTable.getOffset();
			mBuffer.position(subtableStart);
			int cmapFormat = mBuffer.getUint16();
			boolean longLength = cmapFormat == 10 || cmapFormat == 12 || cmapFormat == 13;
			mBuffer.position(subtableStart + (longLength ? 4 : 2));
			long subtableLength = longLength ? mBuffer.getUint32() : mBuffer.getUint16();
			long subtableEnd = (long)cmapTable.getOffset() + subtableLength;
			long minimumLength = cmapFormat == 10 ? 20 : cmapFormat == 12 || cmapFormat == 13 ? 16 : 6;
			if (subtableLength < minimumLength || subtableEnd > cmap.mLength)
			{
				throw new IllegalArgumentException("Invalid cmap format " + cmapFormat + " length: " + subtableLength);
			}

			mBuffer.position(subtableStart + 2);

			switch (cmapFormat)
			{
				case 0 -> mCmaps.add(new CMap0(mBuffer));
				case 4 -> mCmaps.add(new CMap4(mBuffer));
				case 6 -> mCmaps.add(new CMap6(mBuffer));
				case 10 -> mCmaps.add(new CMap10(mBuffer));
				case 12 -> mCmaps.add(new CMap12(mBuffer));
				case 13 -> mCmaps.add(new CMap13(mBuffer));
			}
			if (mBuffer.position() > subtableStart + subtableLength)
			{
				throw new IllegalArgumentException("Cmap format " + cmapFormat + " exceeds its declared length");
			}
		}
		if (mCmaps.isEmpty())
		{
			throw new IllegalArgumentException("No supported Unicode cmap subtable");
		}
	}
}
