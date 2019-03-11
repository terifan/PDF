package pdf;

import font.FontFile;
import java.util.ArrayList;


public class Style
{
	private Font mFont;
	private double mSize;
	private FontFile mFontFile;


	public Style(Font aFontRef, double aSize)
	{
		mFont = aFontRef;
		mSize = aSize;

		mFontFile = mFont.getFontFile();
	}


	public String getIdentity()
	{
		return mFont.getIdentity();
	}


	public Font getFontInstance()
	{
		return mFont;
	}


	public double getSize()
	{
		return mSize;
	}


	public int getGlyphIndex(int aCharacter)
	{
		return mFontFile.findGlyphIndex(aCharacter);
	}


	public double getAdvance(int aCharacter)
	{
		return mFontFile.getGlyphAdvanceWidth(getGlyphIndex(aCharacter)) / mFontFile.getUnitsPerEm();
	}


	public double getAdvance(Symbol aSymbol)
	{
		return mSize * getAdvance(aSymbol.getCharacter());
	}


	public double getLeftBearing(int aCharacter)
	{
		return mFontFile.getGlyphLeftSideBearing(getGlyphIndex(aCharacter)) / mFontFile.getUnitsPerEm();
	}


	public double getLeftBearing(Symbol aSymbol)
	{
		return mSize * getLeftBearing(aSymbol.getCharacter());
	}


	public double measureText(ArrayList<Symbol> aText, int aOffset, int aLength)
	{
		double len = 0;

		for (int i = 0; i < aLength; i++)
		{
			len += getAdvance(aText.get(aOffset + i));
		}

		return len;
	}


	public double getLineHeight()
	{
		return scale(mFont.getFontFile().getLineHeight());
	}


	public double getAscent()
	{
		return scale(mFont.getFontFile().getAscent());
	}


	public double getDescent()
	{
		return scale(mFont.getFontFile().getDescent());
	}


	public double getLineGap()
	{
		return scale(mFont.getFontFile().getLineGap());
	}


	public int lookup(Symbol aSymbol)
	{
		return mFontFile.findGlyphIndex(aSymbol.getCharacter());
	}


	private double scale(double aValue)
	{
		return aValue * mSize / (mFont.getFontFile().getAscent() - mFont.getFontFile().getDescent());
	}
}
