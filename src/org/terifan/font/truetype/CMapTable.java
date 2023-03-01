package org.terifan.font.truetype;


class CMapTable
{
	private Platform mPlatform;
	private PlatformSpecific mPlatformSpecific;
	private int mOffset;


	public CMapTable(int aPlatformId, int aPlatformSpecificId, int aOffset)
	{
		if (aOffset < 0)
		{
			throw new IllegalArgumentException();
		}

		mPlatform = Platform.values()[aPlatformId];
		mPlatformSpecific = mPlatform == Platform.Microsoft ? PlatformSpecific.values()[PlatformSpecific.Symbol.ordinal() + aPlatformSpecificId] : PlatformSpecific.values()[aPlatformSpecificId];
		mOffset = aOffset;
	}


	public int getOffset()
	{
		return mOffset;
	}


	@Override
	public String toString()
	{
		return "CMap{" + "platform=" + mPlatform + ", platformSpecific=" + mPlatformSpecific + ", offset=" + mOffset + '}';
	}
}
