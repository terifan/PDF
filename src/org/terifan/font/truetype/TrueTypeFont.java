package org.terifan.font.truetype;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import org.terifan.font.FontFile;


/**
 * http://stevehanov.ca/blog/index.php?id=143 https://developer.apple.com/fonts/TrueType-Reference-Manual/RM06/Chap6cmap.html
 * https://docs.microsoft.com/en-us/typography/opentype/spec/cmap
 */
public class TrueTypeFont implements FontFile
{
	private ByteBufferReader mBuffer;

//	private final static int ON_CURVE        =  1;
//	private final static int X_IS_BYTE       =  2;
//	private final static int Y_IS_BYTE       =  4;
//	private final static int REPEAT          =  8;
//	private final static int X_DELTA         = 16;
//	private final static int Y_DELTA         = 32;
	private HEAD mHEAD;
	private HHEA mHHEA;
	private HMTX mHMTX;
	private NAME mNAME;
	private ArrayList<CMap> mCmaps;
	private HashMap<String, Table> mTables;


	public TrueTypeFont(byte[] aData)
	{
		mBuffer = new ByteBufferReader(aData);
		mTables = new HashMap<>();
		mCmaps = new ArrayList<>();

		readOffsetTables();
		mHEAD = new HEAD(mBuffer, mTables);
		readCharacterMap();
		mHHEA = new HHEA(mBuffer, mTables);
		mHMTX = new HMTX(mHHEA, mBuffer, mTables);
		mNAME = new NAME(mBuffer, mTables);
	}


	@Override
	public String getName()
	{
		return mNAME.getName(null, null, null, 4);
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
		GLYF glyf = new GLYF(mBuffer, mTables, mHEAD, aSymbol);

		return glyf.xMax - glyf.xMin;
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
		return getAscent() - getDescent();
//		return mHEAD.mYMax - mHEAD.mYMin;
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
		for (int attempt = 0, c = aCharacter; attempt <= 2; attempt++)
		{
			for (CMap cmap : mCmaps)
			{
				int glyph = cmap.findGlyphIndex(c);
				if (glyph != -1)
				{
					return glyph;
				}
			}

			if (attempt == 0)
			{
				// attempt to normalize the character to a simpler type
				c = Normalizer.normalize(Character.toString(c), Normalizer.Form.NFD).charAt(0);
			}
			else
			{
				// attempt to return a space for missing glyphs
				c = ' ';
			}
		}

		throw new IllegalArgumentException("Glyph not found: " + aCharacter + ", char: " + (char)aCharacter);
	}


	// https://developer.apple.com/fonts/TrueType-Reference-Manual/RM06/Chap6.html
	private void readOffsetTables()
	{
		long scalerType = mBuffer.getUint32();
		int numTables = mBuffer.getUint16();
		int searchRange = mBuffer.getUint16();
		int entrySelector = mBuffer.getUint16();
		int rangeShift = mBuffer.getUint16();

		for (int i = 0; i < numTables; i++)
		{
			String tag = mBuffer.getString(4);
			Table table = new Table(mBuffer.getInt32(), mBuffer.getInt32(), mBuffer.getInt32());

			if (!"head".equals(tag))
			{
				if (table.mOffset + table.mLength > mBuffer.length() || calculateTableChecksum(table.mOffset, table.mLength) != table.mChecksum)
				{
					throw new IllegalStateException("Checksum error: tag: " + tag + ", table: " + table);
				}
			}

			mTables.put(tag, table);
		}
	}


	private int calculateTableChecksum(int aOffset, int aLength)
	{
		int old = mBuffer.position();

		mBuffer.position(aOffset);

		long sum = 0;
		for (int i = (aLength + 3) / 4; --i >= 0;)
		{
			sum += mBuffer.getUint32();
		}

		mBuffer.position(old);
		return (int)sum;
	}


	// https://developer.apple.com/fonts/TrueType-Reference-Manual/RM06/Chap6cmap.html
	private void readCharacterMap()
	{
		int startPosition = mTables.get("cmap").mOffset;

		mBuffer.position(startPosition);
		int version = mBuffer.getUint16();
		int numberSubtables = mBuffer.getUint16();

		if (version != 0)
		{
			throw new IllegalStateException("Unexpected cmap version: " + version);
		}

		ArrayList<CMapTable> list = new ArrayList<>();

		for (int i = 0; i < numberSubtables; i++)
		{
			int platformId = mBuffer.getUint16();
			int platformSpecificId = mBuffer.getUint16();
			int offset = mBuffer.getInt32();

			list.add(new CMapTable(platformId, platformSpecificId, offset));
		}

		for (CMapTable cmapTable : list)
		{
			mBuffer.position(startPosition + cmapTable.getOffset());

			int cmapFormat = mBuffer.getUint16();

			switch (cmapFormat)
			{
				case 0:
					mCmaps.add(new CMap0(mBuffer));
					break;
				case 4:
					mCmaps.add(new CMap4(mBuffer));
					break;
				case 6:
					mCmaps.add(new CMap6(mBuffer));
					break;
				default:
					System.out.println("Cmap format not implemented: " + cmapFormat);
					break;
			}
		}
	}
}
