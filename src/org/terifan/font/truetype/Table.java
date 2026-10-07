package org.terifan.font.truetype;


class Table
{
	final int mChecksum;
	final int mOffset;
	final int mLength;


	public Table(int aChecksum, int aOffset, int aLength)
	{
		if (aOffset < 0 || aLength < 0)
		{
			throw new IllegalArgumentException("Negative table range: offset=" + aOffset + ", length=" + aLength);
		}
		mChecksum = aChecksum;
		mOffset = aOffset;
		mLength = aLength;
	}


	@Override
	public String toString()
	{
		return "Table{" + "mChecksum=" + mChecksum + ", mOffset=" + mOffset + ", mLength=" + mLength + '}';
	}
}
