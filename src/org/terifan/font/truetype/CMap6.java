package org.terifan.font.truetype;


class CMap6 implements CMap
{
	private int mFirstCode;
	private int[] mGlyphIdArray;
	private int mEntryCount;


	public CMap6(ByteBufferReader aBuffer)
	{
		int length = aBuffer.getUint16();
		int language = aBuffer.getUint16();
		mFirstCode = aBuffer.getUint16();
		mEntryCount = aBuffer.getUint16();
		mGlyphIdArray = aBuffer.getInt16Array(mEntryCount);
	}


	@Override
	public int getEntryCount()
	{
		return mEntryCount;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		return mGlyphIdArray[aCharacter - mFirstCode];
	}
}
