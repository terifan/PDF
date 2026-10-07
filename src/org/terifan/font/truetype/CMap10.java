package org.terifan.font.truetype;


class CMap10 implements CMap
{
	private final int mStartCharCode;
	private final int[] mGlyphIdArray;


	CMap10(ByteBufferReader aBuffer)
	{
		int reserved = aBuffer.getUint16();
		long length = aBuffer.getUint32();
		aBuffer.getUint32();
		long startCharCode = aBuffer.getUint32();
		long numChars = aBuffer.getUint32();
		if (reserved != 0 || length < 20 || length != 20L + 2L * numChars || startCharCode > Character.MAX_CODE_POINT || startCharCode + numChars > Character.MAX_CODE_POINT + 1L || numChars > Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("Invalid cmap format 10 header");
		}
		mStartCharCode = (int)startCharCode;
		mGlyphIdArray = aBuffer.getUint16Array((int)numChars);
	}


	@Override
	public int getEntryCount()
	{
		return mGlyphIdArray.length;
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		int index = aCharacter - mStartCharCode;
		return index < 0 || index >= mGlyphIdArray.length ? -1 : mGlyphIdArray[index];
	}
}