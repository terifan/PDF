package org.terifan.font.truetype;

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
		Table table = mTables.get("head");
		if (table.mLength < 54)
		{
			throw new IllegalArgumentException("Invalid head table length: " + table.mLength);
		}
		mBuffer.position(table.mOffset);

		mVersion = mBuffer.getFixed();
		if (mVersion != 1.0)
		{
			throw new IllegalArgumentException("Unsupported head table version: " + mVersion);
		}
		mFontRevision = mBuffer.getFixed();
		mChecksumAdjustment = mBuffer.getUint32();

		long magicNumber = mBuffer.getUint32();

		if (magicNumber != 0x5f0f3cf5)
		{
			throw new IllegalStateException("Bad magicNumber: " + Long.toString(magicNumber, 16));
		}

		mFlags = mBuffer.getUint16();
		mUnitsPerEm = mBuffer.getUint16();
		if (mUnitsPerEm < 16 || mUnitsPerEm > 16384)
		{
			throw new IllegalArgumentException("Invalid unitsPerEm: " + mUnitsPerEm);
		}
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
		if (mIndexToLocFormat != 0 && mIndexToLocFormat != 1)
		{
			throw new IllegalArgumentException("Invalid indexToLocFormat: " + mIndexToLocFormat);
		}
		if (mGlyphDataFormat != 0 || mXMin > mXMax || mYMin > mYMax)
		{
			throw new IllegalArgumentException("Invalid head table values");
		}

//		if((mFlags&1)!=0)System.out.println("Bit 0: Baseline for font at y=0.");
//		if((mFlags&0b1)!=0)System.out.println("Bit 1: Left sidebearing point at x=0 (relevant only for TrueType rasterizers) - see additional information below regarding variable fonts.");
//		if((mFlags&0b10)!=0)System.out.println("Bit 2: Instructions may depend on point size.");
//		if((mFlags&0b100)!=0)System.out.println("Bit 3: Force ppem to integer values for all internal scaler math; may use fractional ppem sizes if this bit is clear. It is strongly recommended that this be set in hinted fonts.");
//		if((mFlags&0b1000)!=0)System.out.println("Bit 4: Instructions may alter advance width (the advance widths might not scale linearly).");
//		if((mFlags&0b10000)!=0)System.out.println("Bit 5: This bit is not used in OpenType, and should not be set in order to ensure compatible behavior on all platforms. If set, it may result in different behavior for vertical layout in some platforms. (See Apple’s specification for details regarding behavior in Apple platforms.)");
//		if((mFlags&0b111100000)!=0)System.out.println("Bits 6 – 10: These bits are not used in OpenType and should always be cleared. (See Apple’s specification for details regarding legacy use in Apple platforms.)");
//		if((mFlags&0b10000000000)!=0)System.out.println("Bit 11: Font data is “lossless” as a result of having been subjected to optimizing transformation and/or compression (such as compression mechanisms defined by ISO/IEC 14496-18, MicroType® Express, WOFF 2.0, or similar) where the original font functionality and features are retained but the binary compatibility between input and output font files is not guaranteed. As a result of the applied transform, the DSIG table may also be invalidated.");
//		if((mFlags&0b100000000000)!=0)System.out.println("Bit 12: Font converted (produce compatible metrics).");
//		if((mFlags&0b1000000000000)!=0)System.out.println("Bit 13: Font optimized for ClearType®. Note, fonts that rely on embedded bitmaps (EBDT) for rendering should not be considered optimized for ClearType, and therefore should keep this bit cleared.");
//		if((mFlags&0b10000000000000)!=0)System.out.println("Bit 14: Last Resort font. If set, indicates that the glyphs encoded in the 'cmap' subtables are simply generic symbolic representations of code point ranges and do not truly represent support for those code points. If unset, indicates that the glyphs encoded in the 'cmap' subtables represent proper support for those code points.");
//		if((mFlags&0b100000000000000)!=0)System.out.println("Bit 15: Reserved, set to 0.");
	}


	@Override
	public String toString()
	{
		return "HEAD{" + "mVersion=" + mVersion + ", mFontRevision=" + mFontRevision + ", mChecksumAdjustment=" + mChecksumAdjustment + ", mFlags=" + mFlags + ", mUnitsPerEm=" + mUnitsPerEm + ", mCreated=" + mCreated + ", mModified=" + mModified + ", mXMin=" + mXMin + ", mYMin=" + mYMin + ", mXMax=" + mXMax + ", mYMax=" + mYMax + ", mMacStyle=" + mMacStyle + ", mLowestRecPPEM=" + mLowestRecPPEM + ", mFontDirectionHint=" + mFontDirectionHint + ", mIndexToLocFormat=" + mIndexToLocFormat + ", mGlyphDataFormat=" + mGlyphDataFormat + ", mScalarType=" + mScalarType + ", mNumTables=" + mNumTables + ", mSearchRange=" + mSearchRange + ", mEntrySelector=" + mEntrySelector + ", mRangeShift=" + mRangeShift + '}';
	}
}
