package font.truetype;

import java.util.HashMap;


class HEAD
{
	double mVersion;
	double mFontRevision;
	long mChecksumAdjustment;
	int mFlags;
	int mUnitsPerEm;
	long mCreated;
	long mModified;
	float mXMin;
	float mYMin;
	float mXMax;
	float mYMax;
	int mMacStyle;
	int mLowestRecPPEM;
	int mFontDirectionHint;
	int mIndexToLocFormat;
	int mGlyphDataFormat;
	long mScalarType;
	int mNumTables;
	int mSearchRange;
	int mEntrySelector;
	int mRangeShift;


	public HEAD(ByteBufferReader mBuffer, HashMap<String, Table> mTables)
	{
		mBuffer.position(mTables.get("head").mOffset);

		mVersion = mBuffer.getFixed();
		mFontRevision = mBuffer.getFixed();
		mChecksumAdjustment = mBuffer.getUint32();

		long magicNumber = mBuffer.getUint32();

		if (magicNumber != 0x5f0f3cf5)
		{
			throw new IllegalStateException("Bad magicNumber: " + Long.toString(magicNumber, 16));
		}

		mFlags = mBuffer.getUint16();
		mUnitsPerEm = mBuffer.getUint16();
		mCreated = mBuffer.getDate();
		mModified = mBuffer.getDate();
		mXMin = mBuffer.getFword();
		mYMin = mBuffer.getFword();
		mXMax = mBuffer.getFword();
		mYMax = mBuffer.getFword();
		mMacStyle = mBuffer.getUint16();
		mLowestRecPPEM = mBuffer.getUint16();
		mFontDirectionHint = mBuffer.getInt16();
		mIndexToLocFormat = mBuffer.getInt16();
		mGlyphDataFormat = mBuffer.getInt16();
	}


	@Override
	public String toString()
	{
		return "HEAD{" + "mVersion=" + mVersion + ", mFontRevision=" + mFontRevision + ", mChecksumAdjustment=" + mChecksumAdjustment + ", mFlags=" + mFlags + ", mUnitsPerEm=" + mUnitsPerEm + ", mCreated=" + mCreated + ", mModified=" + mModified + ", mXMin=" + mXMin + ", mYMin=" + mYMin + ", mXMax=" + mXMax + ", mYMax=" + mYMax + ", mMacStyle=" + mMacStyle + ", mLowestRecPPEM=" + mLowestRecPPEM + ", mFontDirectionHint=" + mFontDirectionHint + ", mIndexToLocFormat=" + mIndexToLocFormat + ", mGlyphDataFormat=" + mGlyphDataFormat + ", mScalarType=" + mScalarType + ", mNumTables=" + mNumTables + ", mSearchRange=" + mSearchRange + ", mEntrySelector=" + mEntrySelector + ", mRangeShift=" + mRangeShift + '}';
	}
}
