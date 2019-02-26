package font.truetype;

import java.util.HashMap;


class GLYF
{
	int numberOfContours;
	float xMin;
	float yMin;
	float xMax;
	float yMax;

	int[] endPtsOfContours;
	int instructionLength;
	int[] instructions;
	int[] flags;
	int[] xCoordinates;
	int[] yCoordinates;


	public GLYF(ByteBufferReader mBuffer, HashMap<String, Table> mTables, HEAD mHEAD, int aSymbol)
	{
		mBuffer.position(getGlyphOffset(mBuffer, mTables, mHEAD, aSymbol));

		numberOfContours = mBuffer.getInt16();
		xMin = mBuffer.getFword();
		yMin = mBuffer.getFword();
		xMax = mBuffer.getFword();
		yMax = mBuffer.getFword();
	}


	private int getGlyphOffset(ByteBufferReader mBuffer, HashMap<String, Table> mTables, HEAD mHEAD, int aIndex)
	{
		int o = mTables.get("loca").mOffset;

		int old = mBuffer.position();
		int offset;

		if (mHEAD.mIndexToLocFormat == 1)
		{
			mBuffer.position(o + aIndex * 4);
			offset = mBuffer.getInt32();
		}
		else
		{
			mBuffer.position(o + aIndex * 2);
			offset = mBuffer.getUint16() * 2;
		}

		mBuffer.position(old);

		return mTables.get("glyf").mOffset + offset;
	}
}
