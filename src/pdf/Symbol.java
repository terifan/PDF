package pdf;

import font.FontFile;


public class Symbol
{
	private Font mFont;
	private char mCharacter;
	private int mGlyphIndex;
	private double mWidth;
	private double mAdvance;
	private double mLeftBearing;


	public Symbol(Font aFont, char aCharacter)
	{
		FontFile fontFile = aFont.getFontInstance().getFontFile();

		mFont = aFont;
		mCharacter = aCharacter;
		mGlyphIndex = fontFile.findGlyphIndex(mCharacter);
		mWidth = fontFile.getGlyphWidth(mGlyphIndex);
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


	public double getWidth()
	{
		return mWidth;
	}


	public double getAdvance()
	{
		return mAdvance;
	}


	public double getLeftBearing()
	{
		return mLeftBearing;
	}


	public Font getFont()
	{
		return mFont;
	}


	public boolean isBreakChar()
	{
		return Character.isWhitespace(mCharacter);
	}


	@Override
	public String toString()
	{
		return Character.toString(mCharacter);
	}
}
