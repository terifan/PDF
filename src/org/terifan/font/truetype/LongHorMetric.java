package org.terifan.font.truetype;


class LongHorMetric
{
	int mAdvanceWidth;
	int mLeftSideBearing;


	public LongHorMetric(int aAdvanceWidth, int aLeftSideBearing)
	{
		mAdvanceWidth = aAdvanceWidth;
		mLeftSideBearing = aLeftSideBearing;
	}


	@Override
	public String toString()
	{
		return "LongHorMetric{" + "mAdvanceWidth=" + mAdvanceWidth + ", mLeftSideBearing=" + mLeftSideBearing + '}';
	}
}
