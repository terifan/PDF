package org.terifan.font.truetype;

import java.util.HashMap;


class LOCA
{
	private final int[] mOffsets;


	LOCA(ByteBufferReader aBuffer, HashMap<String, Table> aTables, HEAD aHEAD, int aNumGlyphs)
	{
		Table loca = aTables.get("loca");
		Table glyf = aTables.get("glyf");
		int entrySize = aHEAD.mIndexToLocFormat == 0 ? 2 : 4;
		long requiredLength = ((long)aNumGlyphs + 1) * entrySize;
		if (requiredLength > loca.mLength)
		{
			throw new IllegalArgumentException("Invalid loca table length: " + loca.mLength);
		}

		aBuffer.position(loca.mOffset);
		mOffsets = new int[aNumGlyphs + 1];
		int previous = 0;
		for (int i = 0; i < mOffsets.length; i++)
		{
			long offset = aHEAD.mIndexToLocFormat == 0 ? aBuffer.getUint16() * 2L : aBuffer.getUint32();
			if (offset < previous || offset > glyf.mLength)
			{
				throw new IllegalArgumentException("Invalid loca offset at glyph " + i + ": " + offset);
			}
			mOffsets[i] = (int)offset;
			previous = (int)offset;
		}
	}


	int getStart(int aGlyphIndex)
	{
		return mOffsets[aGlyphIndex];
	}


	int getEnd(int aGlyphIndex)
	{
		return mOffsets[aGlyphIndex + 1];
	}
}