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
		int segCount = aBuffer.getUint16();
		int searchRange = aBuffer.getUint16();
		int entrySelector = aBuffer.getUint16();
		int rangeShift = aBuffer.getUint16();
		endCode = aBuffer.getUint16Array(segCount / 2);
		int reservedPad = aBuffer.getUint16();

		if (reservedPad != 0)
		{
			throw new IllegalStateException("Expected reservedPad to be zero: " + reservedPad);
		}

		startCode = aBuffer.getUint16Array(segCount / 2);
		idDelta = aBuffer.getInt16Array(segCount / 2);
		idRangeOffset = aBuffer.getUint16Array(segCount / 2);
		glyphIndexArray = aBuffer.getUint16Array(aBuffer.position() - startOffset);

//		for (int i = 0; i < startCode.length; i++)
//		{
//			System.out.printf("%8d %8d %8d %8d\n", startCode[i], endCode[i], idDelta[i], idRangeOffset[i]);
//		}
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
					int address = (idRangeOffset[i] / 2 + (aCharacter - startCode[i]) + i - startCode.length) & 0xffff;

					if (address < 0 || address > glyphIndexArray.length)
					{
//						System.out.println("Bad offset: " + address + ", range: " + glyphIndexArray.length);
						return -1;
					}

					glyphIndex = glyphIndexArray[address];

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

				return (idDelta[i] + glyphIndex) & 0xFFFF;
			}
		}

		return -1;
	}
}
