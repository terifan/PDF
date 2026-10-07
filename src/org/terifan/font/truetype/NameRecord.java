package org.terifan.font.truetype;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


class NameRecord
{
	int platformID;
	int platformSpecificID;
	int languageID;
	int nameID;
	String value;


	NameRecord(ByteBufferReader aBuffer, int aStringOffset, int aTableEnd)
	{
		platformID = aBuffer.getUint16();
		platformSpecificID = aBuffer.getUint16();
		languageID = aBuffer.getUint16();
		nameID = aBuffer.getUint16();
		int length = aBuffer.getUint16();
		int offset = aBuffer.getUint16();

		int pos = aBuffer.position();
		long stringStart = (long)aStringOffset + offset;
		if (stringStart + length > aTableEnd)
		{
			throw new IllegalArgumentException("Name string exceeds name table bounds");
		}
		Charset charset = getCharset(platformID, platformSpecificID);
		if ((charset.equals(StandardCharsets.UTF_16BE)) && (length & 1) != 0)
		{
			throw new IllegalArgumentException("Odd UTF-16 name string length");
		}
		aBuffer.position((int)stringStart);
		value = new String(aBuffer.getByteArray(length), charset);
		aBuffer.position(pos);
	}


	private Charset getCharset(int aPlatformId, int aEncodingId)
	{
		if (aPlatformId == 0 || aPlatformId == 3 && (aEncodingId == 0 || aEncodingId == 1 || aEncodingId == 10))
		{
			return StandardCharsets.UTF_16BE;
		}
		if (aPlatformId == 3)
		{
			return switch (aEncodingId)
			{
				case 2 -> Charset.forName("Shift_JIS");
				case 3 -> Charset.forName("GBK");
				case 4 -> Charset.forName("Big5");
				case 5 -> Charset.forName("x-Johab");
				default -> StandardCharsets.ISO_8859_1;
			};
		}
		if (aPlatformId == 1 && aEncodingId == 0)
		{
			return Charset.forName("x-MacRoman");
		}
		return StandardCharsets.ISO_8859_1;
	}


	@Override
	public String toString()
	{
		return "NameRecord{" + "platformID=" + platformID + ", platformSpecificID=" + platformSpecificID + ", languageID=" + languageID + ", nameID=" + nameID + ", value=" + value + '}';
	}
}
