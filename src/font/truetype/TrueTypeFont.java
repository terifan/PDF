package font.truetype;

import java.util.HashMap;
import font.FontFile;


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
	private CMap4 mCmap;
	private HashMap<String, Table> mTables;


	public TrueTypeFont(byte[] aData)
	{
		mBuffer = new ByteBufferReader(aData);
		mTables = new HashMap<>();

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
		return mNAME.getName(null, null, null, 1);
	}


	/**
	 *   left, bottom, top, right ???
	 */
	@Override
	public double[] getFontBBox()
	{
		return new double[]{mHEAD.mXMin,mHEAD.mYMin,mHEAD.mXMax,mHEAD.mYMax};
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
		return mHEAD.mYMax - mHEAD.mYMin;
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
		return mCmap.findGlyphIndex(aCharacter);
	}


	private void readOffsetTables()
	{
		long scalarType = mBuffer.getUint32();
		int numTables = mBuffer.getUint16();
		int searchRange = mBuffer.getUint16();
		int entrySelector = mBuffer.getUint16();
		int rangeShift = mBuffer.getUint16();

		for (int i = 0; i < numTables; i++)
		{
			String tag = mBuffer.getString(4);
			Table table = new Table(mBuffer.getInt32(), mBuffer.getInt32(), mBuffer.getInt32());

			System.out.println(tag + " " + table);

			if (!tag.equals("head"))
			{
				if ((int)calculateTableChecksum(table.mOffset, table.mLength) != table.mChecksum)
				{
					throw new IllegalStateException("Checksum error: " + calculateTableChecksum(table.mOffset, table.mLength) + " != " + table.mChecksum);
				}
			}

			mTables.put(tag, table);
		}
	}


	private long calculateTableChecksum(int aOffset, int aLength)
	{
		int old = mBuffer.position();

		mBuffer.position(aOffset);

		long sum = 0;
		for (int i = (aLength + 3) / 4; --i >= 0;)
		{
			sum += mBuffer.getUint32();
		}

		mBuffer.position(old);
		return sum;
	}


	private void readCharacterMap()
	{
		mBuffer.position(mTables.get("cmap").mOffset);

		int version = mBuffer.getInt16();
		int numberSubtables = mBuffer.getInt16();

		if (version != 0)
		{
			throw new IllegalStateException("Unexpected cmap version: " + version);
		}

		CMapTable[] cmap = new CMapTable[numberSubtables];

		for (int i = 0; i < numberSubtables; i++)
		{
			int p = mBuffer.getInt16();
			int ps = mBuffer.getInt16();
			int o = mBuffer.getInt32();

			Platform platform = Platform.values()[p];
			PlatformSpecific platformSpecific = platform == Platform.Microsoft ? PlatformSpecific.values()[7 + ps] : PlatformSpecific.values()[ps];

			cmap[i] = new CMapTable(platform, platformSpecific, o);

			System.out.println(cmap[i]);
		}

		mBuffer.position(mTables.get("cmap").mOffset + cmap[0].mOffset);

		int cmapFormat = mBuffer.getUint16();

		switch (cmapFormat)
		{
			case 4:
				mCmap = new CMap4(mBuffer);
				break;
			default:
				throw new IllegalStateException("Cmap not implemented: " + cmapFormat);
		}
	}
}
