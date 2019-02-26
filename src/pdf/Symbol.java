package pdf;

import font.FontFile;


public class Symbol
{
	private Font mFont;
	private char mCharacter;
	private int mSymbol;
	private double mWidth;
	private double mAdvance;
	private double mLeftBearing;
	private double mUnitsPerEm;


	public Symbol(Font aFont, char aCharacter)
	{
		FontFile fontFile = aFont.getFontRef().getFontFile();

		mFont = aFont;
		mCharacter = aCharacter;
		mSymbol = fontFile.findGlyphIndex(mCharacter);
		mWidth = fontFile.getGlyphWidth(mSymbol);
		mAdvance = fontFile.getGlyphAdvanceWidth(mSymbol);
		mLeftBearing = fontFile.getGlyphLeftSideBearing(mSymbol);
		mUnitsPerEm = mFont.getFontRef().getFontFile().getUnitsPerEm();
	}


	public char getCharacter()
	{
		return mCharacter;
	}


	public int getSymbol()
	{
		return mSymbol;
	}


	public double getWidth()
	{
		return mWidth;
	}


	public double getAdvance()
	{
		return mAdvance / mUnitsPerEm;
	}


	public double getLeftBearing()
	{
		return mLeftBearing / mUnitsPerEm;
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
