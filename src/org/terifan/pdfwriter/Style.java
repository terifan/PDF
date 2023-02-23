package org.terifan.pdfwriter;

import org.terifan.font.FontFile;


public class Style
{
	private Font mFont;
	private double mSize;
	private Color mTextColor;
	private Color mBoxStrokeColor;
	private Color mBoxFillColor;
	private Color mHighlightColor;
	private FontFile mFontFile;


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


	public Color getTextColor()
	{
		return mTextColor;
	}


	public Style setTextColor(Color aTextColor)
	{
		mTextColor = aTextColor;
		return this;
	}


	public Color getBoxStrokeColor()
	{
		return mBoxStrokeColor;
	}


	public Style setBoxStrokeColor(Color aBoxStrokeColor)
	{
		mBoxStrokeColor = aBoxStrokeColor;
		return this;
	}


	public Color getBoxFillColor()
	{
		return mBoxFillColor;
	}


	public Style setBoxFillColor(Color aBoxFillColor)
	{
		mBoxFillColor = aBoxFillColor;
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
		int glyph = mFont.getFontFile().findGlyphIndexImpl(aCharacter);

		mFont.registerGlyph(aCharacter, glyph);

		return glyph;
	}


	public double getAdvance(char aCharacter)
	{
		return mSize * mFont.getFontFile().getGlyphAdvanceWidth(getGlyphIndex(aCharacter)) / mFont.getFontFile().getUnitsPerEm();
	}


	public double getLeftBearing(char aCharacter)
	{
		return mSize * mFont.getFontFile().getGlyphLeftSideBearing(getGlyphIndex(aCharacter)) / mFont.getFontFile().getUnitsPerEm();
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
			len += getAdvance(aText.charAt(aOffset + i));
		}

		return len;
	}
}
