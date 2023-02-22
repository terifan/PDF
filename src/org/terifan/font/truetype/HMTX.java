package org.terifan.font.truetype;

import java.util.HashMap;


class HMTX
{
	private LongHorMetric[] mMetrics;


	public HMTX(HHEA aHHEA, ByteBufferReader aBuffer, HashMap<String, Table> aTables)
	{
		aBuffer.position(aTables.get("hmtx").mOffset);

		mMetrics = new LongHorMetric[aHHEA.mNumOfLongHorMetrics];

		for (int i = 0; i < aHHEA.mNumOfLongHorMetrics; i++)
		{
			mMetrics[i] = new LongHorMetric(aBuffer.getUint16(), aBuffer.getInt16());
		}
	}


	public LongHorMetric getMetrics(int aSymbol)
	{
		return mMetrics[aSymbol];
	}
}
