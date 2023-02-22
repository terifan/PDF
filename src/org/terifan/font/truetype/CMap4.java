package org.terifan.font.truetype;


class CMap4 implements CMap
{
	private int[] startCode; // Starting character code for each segment
	private int[] idDelta; // Delta for all character codes in segment
	private int[] idRangeOffset; // Offset in bytes to glyph indexArray, or 0
	private int[] glyphIndexArray; // Glyph index array
	private int[] endCode; // Ending character code for each segment, last = 0xFFFF.


	public CMap4(ByteBufferReader aBuffer)
	{
		int startOffset = aBuffer.position();

		int length = aBuffer.getUint16();
		int language = aBuffer.getUint16();
		int segCount = aBuffer.getUint16() / 2; // 2 * segCount
		int searchRange = aBuffer.getUint16(); // 2 * (2**FLOOR(log2(segCount)))
		int entrySelector = aBuffer.getUint16(); // log2(searchRange/2)
		int rangeShift = aBuffer.getUint16(); // (2 * segCount) - searchRange
		endCode = aBuffer.getUint16Array(segCount);
		int reservedPad = aBuffer.getUint16();

		if (reservedPad != 0)
		{
			throw new IllegalStateException("Expected reservedPad to be zero: " + reservedPad);
		}

		startCode = aBuffer.getUint16Array(segCount);
		idDelta = aBuffer.getInt16Array(segCount);
		idRangeOffset = aBuffer.getUint16Array(segCount);
		glyphIndexArray = aBuffer.getUint16Array((aBuffer.position() - startOffset) / 2);
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		for (int i = 0; i < startCode.length; i++)
		{
//			System.out.printf("%8d %8d %8d %8d\n", startCode[i], endCode[i], idDelta[i], idRangeOffset[i]);

			if (endCode[i] >= aCharacter)
			{
				int glyphIndex;

				if (idRangeOffset[i] != 0)
				{
					int magic = idRangeOffset[i] / 2 + (aCharacter - startCode[i]) + i - startCode.length;

					if (magic < 0 || magic > glyphIndexArray.length)
					{
						return -1;
					}

					glyphIndex = glyphIndexArray[magic];

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
