package font.truetype;

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


	public HHEA(ByteBufferReader mBuffer, HashMap<String, Table> mTables)
	{
		mBuffer.position(mTables.get("hhea").mOffset);

		mVersion = mBuffer.getFixed();
		mAscent = mBuffer.getFword();
		mDescent = mBuffer.getFword();
		mLineGap = mBuffer.getFword();
		mAdvanceWidthMax = mBuffer.getFword();
		mMinLeftSideBearing = mBuffer.getFword();
		mMinRightSideBearing = mBuffer.getFword();
		mXMaxExtent = mBuffer.getFword();
		mCaretSlopeRise = mBuffer.getInt16();
		mCaretSlopeRun = mBuffer.getInt16();
		mCaretOffset = mBuffer.getFword();
		mReserved1 = mBuffer.getInt16();
		mReserved2 = mBuffer.getInt16();
		mReserved3 = mBuffer.getInt16();
		mReserved4 = mBuffer.getInt16();
		mMetricDataFormat = mBuffer.getInt16();
		mNumOfLongHorMetrics = mBuffer.getUint16();
	}
}
