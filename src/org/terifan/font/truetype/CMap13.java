package org.terifan.font.truetype;


class CMap13 implements CMap
{
	private final int[] mStartCharCode;
	private final int[] mEndCharCode;
	private final int[] mGlyphId;
	private final int mEntryCount;


	CMap13(ByteBufferReader aBuffer)
	{
		int reserved = aBuffer.getUint16();
		long length = aBuffer.getUint32();
		aBuffer.getUint32();
		long groupCount = aBuffer.getUint32();
		if (reserved != 0 || length < 16 || length != 16L + 12L * groupCount || groupCount > Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("Invalid cmap format 13 header");
		}

		mStartCharCode = new int[(int)groupCount];
		mEndCharCode = new int[(int)groupCount];
		mGlyphId = new int[(int)groupCount];
		long entryCount = 0;
		for (int i = 0; i < groupCount; i++)
		{
			long start = aBuffer.getUint32();
			long end = aBuffer.getUint32();
			long glyph = aBuffer.getUint32();
			if (start > end || end > Character.MAX_CODE_POINT || (i > 0 && start <= mEndCharCode[i - 1]) || glyph > 0xFFFF)
			{
				throw new IllegalArgumentException("Invalid cmap format 13 group: " + i);
			}
			mStartCharCode[i] = (int)start;
			mEndCharCode[i] = (int)end;
			mGlyphId[i] = (int)glyph;
			entryCount += end - start + 1;
		}
		mEntryCount = (int)Math.min(entryCount, Integer.MAX_VALUE);
	}


	@Override
	public int getEntryCount()
	{
		return mEntryCount;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		int low = 0;
		int high = mStartCharCode.length - 1;
		while (low <= high)
		{
			int middle = (low + high) >>> 1;
			if (aCharacter < mStartCharCode[middle])
			{
				high = middle - 1;
			}
			else if (aCharacter > mEndCharCode[middle])
			{
				low = middle + 1;
			}
			else
			{
				return mGlyphId[middle];
			}
		}
		return -1;
	}
}