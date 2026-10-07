package org.terifan.font.truetype;

import java.util.HashMap;


class HMTX
{
	private final LongHorMetric[] mMetrics;


	public HMTX(HHEA aHHEA, int aNumGlyphs, ByteBufferReader aBuffer, HashMap<String, Table> aTables)
	{
		Table table = aTables.get("hmtx");
		int metricCount = aHHEA.mNumOfLongHorMetrics;
		if (metricCount < 1 || metricCount > aNumGlyphs || table.mLength < metricCount * 4L + (aNumGlyphs - metricCount) * 2L)
		{
			throw new IllegalArgumentException("Invalid hmtx metrics: longMetrics=" + metricCount + ", glyphs=" + aNumGlyphs + ", length=" + table.mLength);
		}
		aBuffer.position(table.mOffset);

		mMetrics = new LongHorMetric[aNumGlyphs];

		int lastAdvanceWidth = 0;
		for (int i = 0; i < metricCount; i++)
		{
			lastAdvanceWidth = aBuffer.getUint16();
			mMetrics[i] = new LongHorMetric(lastAdvanceWidth, aBuffer.getInt16());
		}
		for (int i = metricCount; i < aNumGlyphs; i++)
		{
			mMetrics[i] = new LongHorMetric(lastAdvanceWidth, aBuffer.getInt16());
		}
	}


	public LongHorMetric getMetrics(int aSymbol)
	{
		if (aSymbol < 0 || aSymbol >= mMetrics.length)
		{
			throw new IllegalArgumentException("Glyph index out of range: " + aSymbol);
		}
		return mMetrics[aSymbol];
	}
}
