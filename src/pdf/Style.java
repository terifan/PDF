package pdf;

import font.FontFile;


public class Style
{
	private Font mFont;
	private double mSize;
	private FontFile mFontFile;
	private double mLineHeight;
	private double mAscent;
	private double mDescent;
	private double mLineGap;


	public Style(Font aFont, double aSize)
	{
		mFont = aFont;
		mSize = aSize;

		mFontFile = mFont.getFontFile();
		mLineHeight = scale(mFontFile.getLineHeight());
		mAscent = scale(mFontFile.getAscent());
		mDescent = scale(mFontFile.getDescent());
		mLineGap = scale(mFontFile.getLineGap());
	}


	public String getIdentity()
	{
		return mFont.getIdentity();
	}


	public Font getFont()
	{
		return mFont;
	}


	public double getSize()
	{
		return mSize;
	}


	public double getAscent()
	{
		return mAscent;
	}


	public double getDescent()
	{
		return mDescent;
	}


	public double getLineGap()
	{
		return mLineGap;
	}


	public double getLineHeight()
	{
		return mLineHeight;
	}


	public int getGlyphIndex(int aCharacter)
	{
		int glyph = mFontFile.findGlyphIndexImpl(aCharacter);

		mFont.registerGlyph(aCharacter, glyph);

		return glyph;
	}


	public double getAdvance(char aCharacter)
	{
		return mSize * mFontFile.getGlyphAdvanceWidth(getGlyphIndex(aCharacter)) / mFontFile.getUnitsPerEm();
	}


	public double getLeftBearing(char aCharacter)
	{
		return mSize * mFontFile.getGlyphLeftSideBearing(getGlyphIndex(aCharacter)) / mFontFile.getUnitsPerEm();
	}


	private double scale(double aValue)
	{
		return aValue * mSize / (mFontFile.getAscent() - mFontFile.getDescent());
	}


	public double measureText(String aText, int aOffset, int aLength)
	{
		double len = 0;

		for (int i = 0; i < aLength; i++)
		{
			len += getAdvance(aText.charAt(aOffset + i));
		}

		return len;
	}
}
