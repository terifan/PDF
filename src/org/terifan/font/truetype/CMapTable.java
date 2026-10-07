package org.terifan.font.truetype;


class CMapTable
{
	private final int mPlatformId;
	private final int mPlatformSpecificId;
	private final int mOffset;


	public CMapTable(int aPlatformId, int aPlatformSpecificId, int aOffset)
	{
		mPlatformId = aPlatformId;
		mPlatformSpecificId = aPlatformSpecificId;
		mOffset = aOffset;
	}


	public int getOffset()
	{
		return mOffset;
	}


	public int getPlatformId()
	{
		return mPlatformId;
	}


	public int getPlatformSpecificId()
	{
		return mPlatformSpecificId;
	}


	public int getPriority(int aFormat)
	{
		if (mPlatformId == 0)
		{
			return switch (aFormat)
			{
				case 12 -> 0;
				case 13 -> 1;
				case 10 -> 2;
				case 4 -> 3;
				case 6 -> 4;
				case 0 -> 5;
				default -> Integer.MAX_VALUE;
			};
		}
		if (mPlatformId == 3 && mPlatformSpecificId == 10 && (aFormat == 10 || aFormat == 12 || aFormat == 13))
		{
			return aFormat == 12 ? 0 : aFormat == 10 ? 1 : 2;
		}
		if (mPlatformId == 3 && mPlatformSpecificId == 1 && (aFormat == 4 || aFormat == 6 || aFormat == 12 || aFormat == 13))
		{
			return aFormat == 4 ? 2 : 3;
		}
		if (mPlatformId == 3 && mPlatformSpecificId == 0 && (aFormat == 0 || aFormat == 4))
		{
			return 5;
		}
		return Integer.MAX_VALUE;
	}


	@Override
	public String toString()
	{
		return "CMap{" + "platformId=" + mPlatformId + ", platformSpecificId=" + mPlatformSpecificId + ", offset=" + mOffset + '}';
	}
}
