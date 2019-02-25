package font.truetype;

import java.util.HashMap;


class HMTX
{
	LongHorMetric[] hMetrics;
	int[] leftSideBearing;


	public HMTX(HHEA mHHEA, ByteBufferReader mBuffer, HashMap<String, Table> mTables)
	{
		mBuffer.position(mTables.get("hmtx").mOffset);

		hMetrics = new LongHorMetric[mHHEA.mNumOfLongHorMetrics];
		leftSideBearing = new int[mHHEA.mNumOfLongHorMetrics];
	}
}
