package pdf;

import font.FontFile;


public class Symbol
{
	private Font mFont;
	private char mCharacter;
	private int mSymbol;
	private double mWidth;


	public Symbol(Font aFont, char aCharacter)
	{
		FontFile fontFile = aFont.getFontRef().getFontFile();

		mFont = aFont;
		mCharacter = aCharacter;
		mSymbol = fontFile.findGlyphIndex(mCharacter);
		mWidth = fontFile.getGlyphWidth(mSymbol);
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
		return mFont.scale(mWidth);
	}


	public Font getFont()
	{
		return mFont;
	}


	public boolean isBreakChar()
	{
		return mCharacter == ' ';
	}


	@Override
	public String toString()
	{
		return "" + mCharacter;
	}
}
