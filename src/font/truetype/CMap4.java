package font.truetype;


class CMap4 implements CMap
{
	private int language;
	private int[] startCode; // Starting character code for each segment
	private int[] idDelta; // Delta for all character codes in segment
	private int[] idRangeOffset; // Offset in bytes to glyph indexArray, or 0
	private int[] glyphIndexArray; // Glyph index array
	private int[] endCode; // Ending character code for each segment, last = 0xFFFF.


	public CMap4(ByteBufferReader mBuffer)
	{
		int startOffset = mBuffer.position();

		int length = mBuffer.getUint16();
		language = mBuffer.getUint16();
		int segCount = mBuffer.getUint16() / 2; // 2 * segCount
		int searchRange = mBuffer.getUint16(); // 2 * (2**FLOOR(log2(segCount)))
		int entrySelector = mBuffer.getUint16(); // log2(searchRange/2)
		int rangeShift = mBuffer.getUint16(); // (2 * segCount) - searchRange
		endCode = mBuffer.getUint16array(segCount);
		int reservedPad = mBuffer.getUint16();

		if (reservedPad != 0)
		{
			throw new IllegalStateException("Expected reservedPad to be zero: " + reservedPad);
		}

		startCode = mBuffer.getUint16array(segCount);
		idDelta = mBuffer.getInt16array(segCount);
		idRangeOffset = mBuffer.getUint16array(segCount);
		glyphIndexArray = mBuffer.getUint16array((mBuffer.position() - startOffset) / 2);
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		for (int i = 0; i < startCode.length; i++)
		{
//				System.out.printf("%8d %8d %8d %8d\n", startCode[i], endCode[i], idDelta[i], idRangeOffset[i]);

			if (endCode[i] >= aCharacter)
			{
				int glyphIndex;

				if (idRangeOffset[i] != 0)
				{
					glyphIndex = glyphIndexArray[idRangeOffset[i] / 2 + (aCharacter - startCode[i]) + i - startCode.length];

					if (glyphIndex == 0)
					{
						return -1;
					}
				}
				else
				{
					glyphIndex = aCharacter;
				}

				return (idDelta[i] + glyphIndex) & 0xFFFF;
			}
		}

		return -1;
	}
}
