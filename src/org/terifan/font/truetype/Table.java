package org.terifan.font.truetype;


class Table
{
	int mChecksum;
	int mOffset;
	int mLength;


	public Table(int aChecksum, int aOffset, int aLength)
	{
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
