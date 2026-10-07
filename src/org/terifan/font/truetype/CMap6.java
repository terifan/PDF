package org.terifan.font.truetype;


class CMap6 implements CMap
{
	private final int mFirstCode;
	private final int[] mGlyphIdArray;


	public CMap6(ByteBufferReader aBuffer)
	{
		int length = aBuffer.getUint16();
		aBuffer.getUint16();
		mFirstCode = aBuffer.getUint16();
		int entryCount = aBuffer.getUint16();
		if (length != 10 + entryCount * 2 || mFirstCode + entryCount > 0x10000)
		{
			throw new IllegalArgumentException("Invalid cmap format 6 length or code range: " + length);
		}
		mGlyphIdArray = aBuffer.getUint16Array(entryCount);
	}


	@Override
	public int getEntryCount()
	{
		return mGlyphIdArray.length;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		int index = aCharacter - mFirstCode;
		return index < 0 || index >= mGlyphIdArray.length ? -1 : mGlyphIdArray[index];
	}
}
