package org.terifan.font.truetype;

import java.util.HashMap;


class GLYF
{
	final float xMin;
	final float yMin;
	final float xMax;
	final float yMax;


	public GLYF(ByteBufferReader aBuffer, HashMap<String, Table> aTables, LOCA aLOCA, int aSymbol)
	{
		Table glyf = aTables.get("glyf");
		int start = aLOCA.getStart(aSymbol);
		int end = aLOCA.getEnd(aSymbol);
		if (start == end)
		{
			xMin = yMin = xMax = yMax = 0;
			return;
		}
		if (end - start < 10)
		{
			throw new IllegalArgumentException("Truncated glyf record for glyph " + aSymbol);
		}
		aBuffer.position(glyf.mOffset + start);

		aBuffer.getInt16();
		xMin = aBuffer.getFword();
		yMin = aBuffer.getFword();
		xMax = aBuffer.getFword();
		yMax = aBuffer.getFword();
		if (xMin > xMax || yMin > yMax)
		{
			throw new IllegalArgumentException("Invalid glyf bounds for glyph " + aSymbol);
		}
	}


	@Override
	public String toString()
	{
		return "GLYF{" + "xMin=" + xMin + ", yMin=" + yMin + ", xMax=" + xMax + ", yMax=" + yMax + '}';
	}
}
