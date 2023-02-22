package org.terifan.font.truetype;

import java.util.ArrayList;
import java.util.HashMap;


class NAME
{
	private ArrayList<NameRecord> mNameRecords;


	public NAME(ByteBufferReader aBuffer, HashMap<String, Table> mTables)
	{
		mNameRecords = new ArrayList<>();

		int offset = mTables.get("name").mOffset;

		aBuffer.position(offset);

		int format = aBuffer.getUint16();
		int count = aBuffer.getUint16();
		int stringOffset = aBuffer.getUint16();

		for (int i = 0; i < count; i++)
		{
			mNameRecords.add(new NameRecord(aBuffer, offset + stringOffset));
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
