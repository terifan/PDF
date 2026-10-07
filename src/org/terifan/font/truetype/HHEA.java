package org.terifan.font.truetype;

import java.util.HashMap;


class HHEA
{
	double mVersion;
	float mAscent;
	float mDescent;
	float mLineGap;
	float mAdvanceWidthMax;
	float mMinLeftSideBearing;
	float mMinRightSideBearing;
	float mXMaxExtent;
	int mCaretSlopeRise;
	int mCaretSlopeRun;
	float mCaretOffset;
	int mReserved1;
	int mReserved2;
	int mReserved3;
	int mReserved4;
	int mMetricDataFormat;
	int mNumOfLongHorMetrics;


	public HHEA(ByteBufferReader aBuffer, HashMap<String, Table> aTables)
	{
		Table table = aTables.get("hhea");
		if (table.mLength < 36)
		{
			throw new IllegalArgumentException("Invalid hhea table length: " + table.mLength);
		}
		aBuffer.position(table.mOffset);

		mVersion = aBuffer.getFixed();
		if (mVersion != 1.0)
		{
			throw new IllegalArgumentException("Unsupported hhea table version: " + mVersion);
		}
		mAscent = aBuffer.getFword();
		mDescent = aBuffer.getFword();
		mLineGap = aBuffer.getFword();
		mAdvanceWidthMax = aBuffer.getUint16();
		mMinLeftSideBearing = aBuffer.getFword();
		mMinRightSideBearing = aBuffer.getFword();
		mXMaxExtent = aBuffer.getFword();
		mCaretSlopeRise = aBuffer.getInt16();
		mCaretSlopeRun = aBuffer.getInt16();
		mCaretOffset = aBuffer.getFword();
		mReserved1 = aBuffer.getInt16();
		mReserved2 = aBuffer.getInt16();
		mReserved3 = aBuffer.getInt16();
		mReserved4 = aBuffer.getInt16();
		mMetricDataFormat = aBuffer.getInt16();
		mNumOfLongHorMetrics = aBuffer.getUint16();
		if (mMetricDataFormat != 0 || mNumOfLongHorMetrics == 0 || mReserved1 != 0 || mReserved2 != 0 || mReserved3 != 0 || mReserved4 != 0)
		{
			throw new IllegalArgumentException("Invalid hhea metric fields");
		}
	}


	@Override
	public String toString()
	{
		return "HHEA{" + "mVersion=" + mVersion + ", mAscent=" + mAscent + ", mDescent=" + mDescent + ", mLineGap=" + mLineGap + ", mAdvanceWidthMax=" + mAdvanceWidthMax + ", mMinLeftSideBearing=" + mMinLeftSideBearing + ", mMinRightSideBearing=" + mMinRightSideBearing + ", mXMaxExtent=" + mXMaxExtent + ", mCaretSlopeRise=" + mCaretSlopeRise + ", mCaretSlopeRun=" + mCaretSlopeRun + ", mCaretOffset=" + mCaretOffset + ", mReserved1=" + mReserved1 + ", mReserved2=" + mReserved2 + ", mReserved3=" + mReserved3 + ", mReserved4=" + mReserved4 + ", mMetricDataFormat=" + mMetricDataFormat + ", mNumOfLongHorMetrics=" + mNumOfLongHorMetrics + '}';
	}
}
