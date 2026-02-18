package org.terifan.font.truetype;


class CMap0 implements CMap
{
	private int length;
	private int language;
	private int[] mGlyphIndexArray; // Glyph index array


	public CMap0(ByteBufferReader aBuffer)
	{
		length = aBuffer.getUint16();
		language = aBuffer.getUint16();
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
