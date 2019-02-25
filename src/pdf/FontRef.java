package pdf;

import font.FontFile;


public class FontRef
{
	private FontFile mFontFile;
	private String mIdentity;


	public FontRef(FontFile aFontFile, String aIdentity)
	{
		mFontFile = aFontFile;
		mIdentity = aIdentity;
	}


	public FontFile getFontFile()
	{
		return mFontFile;
	}


	public String getIdentity()
	{
		return mIdentity;
	}
}
