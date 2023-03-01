package org.terifan.font.truetype;


class CMap4 implements CMap
{
	private int[] mStartCode; // Starting character code for each segment
	private int[] mIdDelta; // Delta for all character codes in segment
	private int[] mIdRangeOffset; // Offset in bytes to glyph indexArray, or 0
	private int[] mGlyphIndexArray; // Glyph index array
	private int[] mEndCode; // Ending character code for each segment, last = 0xFFFF.


	public CMap4(ByteBufferReader aBuffer)
	{
		int startOffset = aBuffer.position();

		int length = aBuffer.getUint16();
		int language = aBuffer.getUint16();
		int segCount = aBuffer.getUint16();
		int searchRange = aBuffer.getUint16();
		int entrySelector = aBuffer.getUint16();
		int rangeShift = aBuffer.getUint16();
		mEndCode = aBuffer.getUint16Array(segCount / 2);
		int reservedPad = aBuffer.getUint16();

		if (reservedPad != 0)
		{
			throw new IllegalStateException("Expected reservedPad to be zero: " + reservedPad);
		}

		mStartCode = aBuffer.getUint16Array(segCount / 2);
		mIdDelta = aBuffer.getInt16Array(segCount / 2);
		mIdRangeOffset = aBuffer.getUint16Array(segCount / 2);
		mGlyphIndexArray = aBuffer.getUint16Array(aBuffer.position() - startOffset);

//		for (int i = 0; i < startCode.length; i++)
//		{
//			System.out.printf("%8d %8d %8d %8d\n", startCode[i], endCode[i], idDelta[i], idRangeOffset[i]);
//		}
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		for (int i = 0; i < mStartCode.length; i++)
		{
//			System.out.printf("%8d %8d %8d %8d\n", mStartCode[i], mEndCode[i], mIdDelta[i], mIdRangeOffset[i]);

			if (mEndCode[i] >= aCharacter)
			{
				int glyphIndex;

				if (mIdRangeOffset[i] != 0)
				{
					int address = (mIdRangeOffset[i] / 2 + (aCharacter - mStartCode[i]) + i - mStartCode.length) & 0xffff;

					if (address < 0 || address > mGlyphIndexArray.length)
					{
//						System.out.println("Bad offset: " + address + ", range: " + glyphIndexArray.length);
						return -1;
					}

					glyphIndex = mGlyphIndexArray[address];

					if (glyphIndex == 0)
					{
//						System.out.println("Bad glyph: " + address);
						return -1;
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
