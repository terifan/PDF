package pdf;

import font.FontFile;


public class Symbol
{
	private Style mStyle;
	private char mCharacter;
	private int mGlyphIndex;
	private double mAdvance;
	private double mLeftBearing;


	public Symbol(Style aStyle, char aCharacter)
	{
		FontFile fontFile = ((ExtendedFont)aStyle.getFontInstance()).getFontFile();

		mStyle = aStyle;
		mCharacter = aCharacter;
		mGlyphIndex = fontFile.findGlyphIndex(mCharacter);
		mAdvance = fontFile.getGlyphAdvanceWidth(mGlyphIndex) / fontFile.getUnitsPerEm();
		mLeftBearing = fontFile.getGlyphLeftSideBearing(mGlyphIndex) / fontFile.getUnitsPerEm();
	}


	public char getCharacter()
	{
		return mCharacter;
	}


	public int getGlyphIndex()
	{
		return mGlyphIndex;
	}


	public double getAdvance()
	{
		return mAdvance;
	}


	public double getLeftBearing()
	{
		return mLeftBearing;
	}


	public Style getStyle()
	{
		return mStyle;
	}


	public boolean isBreakChar()
	{
		return Character.isWhitespace(mCharacter);
	}
}
