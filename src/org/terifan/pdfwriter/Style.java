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
	private double mAdjust;
	private Insets mMargins;
	private Insets mBorderThickness;
	private String mBorderPattern;
	private double mLineExtra;
	private Double mLineGap;
	private Dictionary mExtGState;


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


	public Style setExtGState(Dictionary aExtGState)
	{
		mExtGState = aExtGState;
		return this;
	}


	public Dictionary getExtGState()
	{
		return mExtGState;
	}


	public Insets getBorderThickness(Insets aInsets)
	{
		if (aInsets == null)
		{
			aInsets = new Insets();
		}
		return aInsets.set(mBorderThickness);
	}


	public Style setBorderThickness(Insets aThickness)
	{
		mBorderThickness = aThickness;
		return this;
	}


	public Style setBorder(Color aColor, Insets aThickness)
	{
		mBorderColor = aColor;
		mBorderThickness = aThickness;
		return this;
	}


	public Insets getMargins(Insets aInsets)
	{
		if (aInsets == null)
		{
			aInsets = new Insets();
		}
		return aInsets.set(mMargins);
	}


	public Style setMargins(Insets aMargins)
	{
		mMargins = aMargins;
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


	public String getBorderPattern()
	{
		return mBorderPattern;
	}


	public Style setBorderPattern(String aBorderPattern)
	{
		mBorderPattern = aBorderPattern;
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
		return scale(mFontFile.getAscent()) + mLineExtra / 2;
	}


	public double getDescent()
	{
		return scale(mFontFile.getDescent()) + mLineExtra / 2;
	}


	public double getLineGap()
	{
		return mLineGap == null ? scale(mFontFile.getLineGap()) : mLineGap;
	}


	/**
	 * Override the line gap specified by the font used or null to use the font file line gap.
	 *
	 * @param aLineGap the line gap specified by the font used or null to use the font file line gap
	 */
	public Style setLineGap(Double aLineGap)
	{
		mLineGap = aLineGap;
		return this;
	}


	public double getLineHeight()
	{
		return scale(mFontFile.getLineHeight()) + mLineExtra;
	}


	public int getGlyphIndex(int aCharacter)
	{
		try
		{
			int glyph = mFontFile.findGlyphIndex(aCharacter);
			mFont.registerGlyph(aCharacter, glyph);
			return glyph;
		}
		catch (Exception e)
		{
			System.out.println(e);
			int glyph = mFontFile.findGlyphIndex(' ');
			mFont.registerGlyph(aCharacter, glyph);
			return glyph;
		}
	}


	public double getAdvance(char aCharacter)
	{
		double aw;
		double cs = mCharacterSpacing == null ? 1.0 : mCharacterSpacing;
		try
		{
			aw = mFontFile.getGlyphAdvanceWidth(getGlyphIndex(aCharacter));
		}
		catch (Exception e)
		{
			System.out.println("Font renderer error, getAdvance, char " + (int)aCharacter + ": " + e);
			e.printStackTrace(System.out);
			aw = mFontFile.getGlyphAdvanceWidth(getGlyphIndex(' '));
		}
		return mSize * aw * cs / mFontFile.getUnitsPerEm();
	}


	public double getLeftBearing(char aCharacter)
	{
		double cs = mCharacterSpacing == null ? 1 : mCharacterSpacing;
		double aw;
		try
		{
			aw = mFontFile.getGlyphLeftSideBearing(getGlyphIndex(aCharacter));
		}
		catch (Exception e)
		{
			System.out.println("Font renderer error, getLeftBearing, char " + (int)aCharacter + ": " + e);
			e.printStackTrace(System.out);
			aw = mFontFile.getGlyphLeftSideBearing(getGlyphIndex(' '));
		}
		return mSize * aw * cs / mFontFile.getUnitsPerEm();
	}


	private double scale(double aValue)
	{
		return aValue * mSize / (mFontFile.getAscent() - mFontFile.getDescent());
//		return aValue * mSize / mFontFile.getLineHeight();
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


	public double getAdjust()
	{
		return mAdjust;
	}


	public Style setAdjust(double aAdjust)
	{
		mAdjust = aAdjust;
		return this;
	}


	public double getLineExtra()
	{
		return mLineExtra;
	}


	public Style setLineExtra(double aLineExtra)
	{
		mLineExtra = aLineExtra;
		return this;
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
		}
	}


	@Override
	public String toString()
	{
		return "Style{" + "mFont=" + mFont.getFontFile().getName() + ", mSize=" + mSize + ", lineHeight=" + getLineHeight() + ", ascent=" + getAscent() + ", descent=" + getDescent() + ", mCharacterSpacing=" + mCharacterSpacing + ", mAdjust=" + mAdjust + ", mMargins=" + mMargins + ", mBorderThickness=" + mBorderThickness + ", mBorderPattern=" + mBorderPattern + ", mLineExtra=" + mLineExtra + ", mLineGap=" + mLineGap + '}';
	}
}
