package org.terifan.font.truetype;


class CMap0 implements CMap
{
	private final int[] mGlyphIndexArray;


	public CMap0(ByteBufferReader aBuffer)
	{
		int length = aBuffer.getUint16();
		if (length != 262)
		{
			throw new IllegalArgumentException("Invalid cmap format 0 length: " + length);
		}
		aBuffer.getUint16();
		mGlyphIndexArray = aBuffer.getUint8Array(256);
	}


	@Override
	public int getEntryCount()
	{
		return mGlyphIndexArray.length;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		return aCharacter < 0 || aCharacter >= mGlyphIndexArray.length ? -1 : mGlyphIndexArray[aCharacter];
	}
}
