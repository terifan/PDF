package org.terifan.pdfwriter;

import org.terifan.font.FontFile;


public class Style implements Cloneable
{
	private Font mFont;
	private double mSize;
	private Color mTextColor;
	private Color mBorderColor;
	private Color mFillColor;
	private Color mHighlightColor;
	private FontFile mFontFile;
	private Double mCharacterSpacing;
	private Insets mMargins;


	public Style()
	{
		mSize = 10;
		mTextColor = Color.BLACK;
	}


	public Style(Font aFont, double aSize)
	{
		this();

		mSize = aSize;
		setFont(aFont);
	}


	public Insets getMargins()
	{
		return mMargins;
	}


	public Style setMargins(Insets aMargins)
	{
		this.mMargins = aMargins;
		return this;
	}


	public Double getCharacterSpacing()
	{
		return mCharacterSpacing;
	}


	public Style setCharacterSpacing(Double aCharacterSpacing)
	{
		mCharacterSpacing = aCharacterSpacing;
		return this;
	}


	public Color getTextColor()
	{
		return mTextColor;
	}


	public Style setTextColor(Color aTextColor)
	{
		mTextColor = aTextColor;
		return this;
	}


	public Color getBorderColor()
	{
		return mBorderColor;
	}


	public Style setBorderColor(Color aBorderColor)
	{
		mBorderColor = aBorderColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Style setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public Color getHighlightColor()
	{
		return mHighlightColor;
	}


	public Style setHighlightColor(Color aHighlightColor)
	{
		mHighlightColor = aHighlightColor;
		return this;
	}


	public String getIdentity()
	{
		return mFont.getIdentity();
	}


	public Font getFont()
	{
		return mFont;
	}


	public Style setFont(Font aFont)
	{
		mFont = aFont;
		mFontFile = mFont.getFontFile();
		return this;
	}


	public double getSize()
	{
		return mSize;
	}


	public Style setSize(double aSize)
	{
		mSize = aSize;
		return this;
	}


	public double getAscent()
	{
		return scale(mFontFile.getAscent());
	}


	public double getDescent()
	{
		return scale(mFontFile.getDescent());
	}


	public double getLineGap()
	{
		return scale(mFontFile.getLineGap());
	}


	public double getLineHeight()
	{
		return scale(mFontFile.getLineHeight());
	}


	public int getGlyphIndex(int aCharacter)
	{
		int glyph = mFont.getFontFile().findGlyphIndex(aCharacter);
		mFont.registerGlyph(aCharacter, glyph);
		return glyph;
	}


	public double getAdvance(char aCharacter)
	{
		double cs = mCharacterSpacing == null ? 1 : mCharacterSpacing;
		return mSize * mFont.getFontFile().getGlyphAdvanceWidth(getGlyphIndex(aCharacter)) * cs / mFont.getFontFile().getUnitsPerEm();
	}


	public double getLeftBearing(char aCharacter)
	{
		double cs = mCharacterSpacing == null ? 1 : mCharacterSpacing;
		return mSize * mFont.getFontFile().getGlyphLeftSideBearing(getGlyphIndex(aCharacter)) * cs / mFont.getFontFile().getUnitsPerEm();
	}


	private double scale(double aValue)
	{
		return aValue * mSize / (mFont.getFontFile().getAscent() - mFont.getFontFile().getDescent());
	}


	public double measureText(String aText, int aOffset, int aLength)
	{
		double len = 0;

		for (int i = 0; i < aLength; i++)
		{
			char c = aText.charAt(aOffset + i);
			if (c != 10 && c != 13)
			{
				len += getAdvance(c);
			}
		}

		return len;
	}


	@Override
	public Style clone()
	{
		try
		{
			return (Style)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new IllegalStateException(e);
//			Style style = new Style();
//			style.mFont;
//			style.mSize;
//			style.mTextColor;
//			style.mBorderColor;
//			style.mFillColor;
//			style.mHighlightColor;
//			style.mFontFile;
//			style.mCharacterSpacing;
//			style.mMargins;
		}
	}
}
