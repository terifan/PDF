package org.terifan.font.truetype;

import java.util.HashMap;


class GLYF
{
	int numberOfContours;
	float xMin;
	float yMin;
	float xMax;
	float yMax;


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

		switch (aHEAD.mIndexToLocFormat)
		{
			case 0:
				aBuffer.position(o + aIndex * 2);
				offset = aBuffer.getUint16() * 2;
				break;
			case 1:
				aBuffer.position(o + aIndex * 4);
				offset = aBuffer.getInt32();
				break;
			default:
				throw new IllegalArgumentException("" + aHEAD.mIndexToLocFormat);
		}

		aBuffer.position(old);

		return aTables.get("glyf").mOffset + offset;
	}


	@Override
	public String toString()
	{
		return "GLYF{" + "xMin=" + xMin + ", yMin=" + yMin + ", xMax=" + xMax + ", yMax=" + yMax + '}';
	}
}
