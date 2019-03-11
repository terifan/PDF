package pdf;

import font.FontFile;


public class Symbol
{
	private Style mStyle;
	private char mCharacter;
	private FontFile mFontFile;


	public Symbol(Style aStyle, char aCharacter)
	{
		mStyle = aStyle;
		mCharacter = aCharacter;
		mFontFile = mStyle.getFontInstance().getFontFile();
	}


	public char getCharacter()
	{
		return mCharacter;
	}


	public Style getStyle()
	{
		return mStyle;
	}


	public int getGlyphIndex()
	{
		return mFontFile.findGlyphIndex(mCharacter);
	}


	public double getAdvance()
	{
		return mFontFile.getGlyphAdvanceWidth(getGlyphIndex()) / mFontFile.getUnitsPerEm();
	}


	public double getLeftBearing()
	{
		return mFontFile.getGlyphLeftSideBearing(getGlyphIndex()) / mFontFile.getUnitsPerEm();
	}


	public boolean isBreakChar()
	{
		return Character.isWhitespace(mCharacter);
	}
}
