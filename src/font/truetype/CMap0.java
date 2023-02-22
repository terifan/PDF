package font.truetype;


class CMap0 implements CMap
{
	private int length;
	private int language;
	private int[] glyphIndexArray; // Glyph index array


	public CMap0(ByteBufferReader aBuffer)
	{
		length = aBuffer.getUint16();
		language = aBuffer.getUint16();
		glyphIndexArray = aBuffer.getUint8Array(256);
	}


	@Override
	public int findGlyphIndex(int aCharacter)
	{
		return aCharacter < 0 || aCharacter >= glyphIndexArray.length ? -1 : glyphIndexArray[aCharacter];
	}
}
