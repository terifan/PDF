package org.terifan.font.truetype;


class NameRecord
{
	int platformID;
	int platformSpecificID;
	int languageID;
	int nameID;
	String value;


	NameRecord(ByteBufferReader aBuffer, int aStringOffset)
	{
		platformID = aBuffer.getUint16();
		platformSpecificID = aBuffer.getUint16();
		languageID = aBuffer.getUint16();
		nameID = aBuffer.getUint16();
		int length = aBuffer.getUint16();
		int offset = aBuffer.getUint16();

		int pos = aBuffer.position();
		aBuffer.position(aStringOffset + offset);
		value = aBuffer.getString(length);
		aBuffer.position(pos);
	}


	@Override
	public String toString()
	{
		return "NameRecord{" + "platformID=" + platformID + ", platformSpecificID=" + platformSpecificID + ", languageID=" + languageID + ", nameID=" + nameID + ", value=" + value + '}';
	}
}
