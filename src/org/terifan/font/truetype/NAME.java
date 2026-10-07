package org.terifan.font.truetype;

import java.util.ArrayList;
import java.util.HashMap;


class NAME
{
	private final ArrayList<NameRecord> mNameRecords;


	public NAME(ByteBufferReader aBuffer, HashMap<String, Table> aTables)
	{
		mNameRecords = new ArrayList<>();

		int offset = aTables.get("name").mOffset;
		int end = offset + aTables.get("name").mLength;
		if (aTables.get("name").mLength < 6)
		{
			throw new IllegalArgumentException("Invalid name table length");
		}

		aBuffer.position(offset);

		int format = aBuffer.getUint16();
		int count = aBuffer.getUint16();
		int stringOffset = aBuffer.getUint16();
		long recordsEnd = (long)offset + 6 + (long)count * 12;
		if (format != 0 && format != 1 || recordsEnd > end || offset + (long)stringOffset > end || offset + (long)stringOffset < recordsEnd)
		{
			throw new IllegalArgumentException("Invalid name table header");
		}
		if (format == 1)
		{
			aBuffer.position((int)recordsEnd);
			int languageTagCount = aBuffer.getUint16();
			if ((long)aBuffer.position() + 4L * languageTagCount > offset + stringOffset)
			{
				throw new IllegalArgumentException("Invalid name language-tag records");
			}
		}

		aBuffer.position(offset + 6);

		for (int i = 0; i < count; i++)
		{
			mNameRecords.add(new NameRecord(aBuffer, offset + stringOffset, end));
		}
	}


	public String getName(Integer aPlatformID, Integer aPlatformSpecificID, Integer aLanguageID, int aNameID)
	{
		for (NameRecord name : mNameRecords)
		{
			if (aPlatformID == null || name.platformID == aPlatformID)
			{
				if (aPlatformSpecificID == null || name.platformSpecificID == aPlatformSpecificID)
				{
					if (aLanguageID == null || name.languageID == aLanguageID)
					{
						if (name.nameID == aNameID)
						{
							return name.value;
						}
					}
				}
			}
		}

		return null;
	}
}
