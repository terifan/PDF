package font.truetype;


class CMapTable
{
	Platform mPlatform;
	PlatformSpecific mPlatformSpecific;
	int mOffset;


	public CMapTable(Platform aPlatform, PlatformSpecific aPlatformSpecific, int aOffset)
	{
		mPlatform = aPlatform;
		mPlatformSpecific = aPlatformSpecific;
		mOffset = aOffset;
	}


	@Override
	public String toString()
	{
		return "CMap{" + "platform=" + mPlatform + ", platformSpecific=" + mPlatformSpecific + ", offset=" + mOffset + '}';
	}
}
