package org.terifan.font.truetype;

import java.util.HashMap;


class GLYF
{
	int numberOfContours;
	float xMin;
	float yMin;
	float xMax;
	float yMax;

//	int[] endPtsOfContours;
//	int instructionLength;
//	int[] instructions;
//	int[] flags;
//	int[] xCoordinates;
//	int[] yCoordinates;


	public GLYF(ByteBufferReader aBuffer, HashMap<String, Table> aTables, HEAD aHEAD, int aSymbol)
	{
		aBuffer.position(getGlyphOffset(aBuffer, aTables, aHEAD, aSymbol));

		numberOfContours = aBuffer.getInt16();
		xMin = aBuffer.getFword();
		yMin = aBuffer.getFword();
		xMax = aBuffer.getFword();
		yMax = aBuffer.getFword();
	}


	private int getGlyphOffset(ByteBufferReader aBuffer, HashMap<String, Table> aTables, HEAD aHEAD, int aIndex)
	{
		int o = aTables.get("loca").mOffset;

		int old = aBuffer.position();
		int offset;

		if (aHEAD.mIndexToLocFormat == 1)
		{
			aBuffer.position(o + aIndex * 4);
			offset = aBuffer.getInt32();
		}
		else
		{
			aBuffer.position(o + aIndex * 2);
			offset = aBuffer.getUint16() * 2;
		}

		aBuffer.position(old);

		return aTables.get("glyf").mOffset + offset;
	}
}
