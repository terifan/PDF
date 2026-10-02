package org.terifan.font.truetype;


class CMap0 implements CMap
{
	private int mLength;
	private int mLanguage;
	private int[] mGlyphIndexArray; // Glyph index array


	public CMap0(ByteBufferReader aBuffer)
	{
		mLength = aBuffer.getUint16();
		mLanguage = aBuffer.getUint16();
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
