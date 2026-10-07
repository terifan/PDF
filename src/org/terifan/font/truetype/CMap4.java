package org.terifan.font.truetype;


class CMap4 implements CMap
{
	private final int[] mStartCode;
	private final int[] mIdDelta;
	private final int[] mIdRangeOffset;
	private final int[] mGlyphIndexArray;
	private final int[] mEndCode;


	public CMap4(ByteBufferReader aBuffer)
	{
		int length = aBuffer.getUint16();
		aBuffer.getUint16();
		int segCountX2 = aBuffer.getUint16();
		int segCount = segCountX2 / 2;
		int searchRange = aBuffer.getUint16();
		int entrySelector = aBuffer.getUint16();
		int rangeShift = aBuffer.getUint16();

		if (segCountX2 == 0 || (segCountX2 & 1) != 0 || length < 16 + 8 * segCount)
		{
			throw new IllegalArgumentException("Invalid cmap format 4 header");
		}
		int powerOfTwo = Integer.highestOneBit(segCount);
		if (searchRange != powerOfTwo * 2 || entrySelector != Integer.numberOfTrailingZeros(powerOfTwo) || rangeShift != segCountX2 - searchRange)
		{
			throw new IllegalArgumentException("Invalid cmap format 4 search parameters");
		}

		mEndCode = aBuffer.getUint16Array(segCount);
		int reservedPad = aBuffer.getUint16();

		if (reservedPad != 0)
		{
			throw new IllegalStateException("Expected reservedPad to be zero: " + reservedPad);
		}

		mStartCode = aBuffer.getUint16Array(segCount);
		mIdDelta = aBuffer.getInt16Array(segCount);
		mIdRangeOffset = aBuffer.getUint16Array(segCount);
		int glyphArrayBytes = length - 16 - 8 * segCount;
		if ((glyphArrayBytes & 1) != 0)
		{
			throw new IllegalArgumentException("Invalid cmap format 4 length: " + length);
		}
		mGlyphIndexArray = aBuffer.getUint16Array(glyphArrayBytes / 2);

		for (int i = 0; i < segCount; i++)
		{
			if (mStartCode[i] > mEndCode[i] || (i > 0 && mStartCode[i] <= mEndCode[i - 1]) || (mIdRangeOffset[i] & 1) != 0)
			{
				throw new IllegalArgumentException("Invalid cmap format 4 segment: " + i);
			}
			if (mIdRangeOffset[i] != 0)
			{
				int firstGlyph = mIdRangeOffset[i] / 2 + i - segCount;
				int lastGlyph = firstGlyph + mEndCode[i] - mStartCode[i];
				if (firstGlyph < 0 || lastGlyph >= mGlyphIndexArray.length)
				{
					throw new IllegalArgumentException("Invalid cmap format 4 glyph range: " + i);
				}
			}
		}
		if (mEndCode[segCount - 1] != 0xFFFF)
		{
			throw new IllegalArgumentException("Missing cmap format 4 sentinel segment");
		}

//		for (int i = 0; i < mStartCode.length; i++)
//		{
//			System.out.printf("%8d %8d %8d %8d\n", mStartCode[i], mEndCode[i], mIdDelta[i], mIdRangeOffset[i]);
//		}
	}


	@Override
	public int getEntryCount()
	{
		return mGlyphIndexArray.length;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		if (aCharacter < 0 || aCharacter > 0xFFFF)
		{
			return -1;
		}
		for (int i = 0; i < mStartCode.length; i++)
		{
//			System.out.printf("%8d %8d %8d %8d\n", mStartCode[i], mEndCode[i], mIdDelta[i], mIdRangeOffset[i]);

			if (mEndCode[i] >= aCharacter)
			{
				if (aCharacter < mStartCode[i])
				{
					return -1;
				}
				int glyphIndex;

				if (mIdRangeOffset[i] != 0)
				{
					int address = mIdRangeOffset[i] / 2 + (aCharacter - mStartCode[i]) + i - mStartCode.length;

					if (address < 0 || address >= mGlyphIndexArray.length)
					{
//						System.out.println("Bad offset: " + address + ", range: " + mGlyphIndexArray.length);
						return -1;
					}

					glyphIndex = mGlyphIndexArray[address];

					if (glyphIndex == 0)
					{
						return 0;
					}
				}
				else
				{
					glyphIndex = aCharacter;
				}

				return (glyphIndex + mIdDelta[i]) & 0xFFFF;
			}
		}

		return -1;
	}
}
