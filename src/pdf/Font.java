package pdf;

import font.FontFile;
import java.util.ArrayList;


public class Font
{
	private FontRef mFontRef;
	private double mSize;


	public Font(FontRef aFontRef, double aSize)
	{
		mFontRef = aFontRef;
		mSize = aSize;
	}


	public FontRef getFontRef()
	{
		return mFontRef;
	}


	public double getSize()
	{
		return mSize;
	}


	public double measureText(ArrayList<Symbol> aText, int aOffset, int aLength)
	{
		FontFile fontFile = mFontRef.getFontFile();
		double len = 0;

		for (int i = 0; i < aLength; i++)
		{
			len += scale(fontFile.getGlyphWidth(aText.get(aOffset + i).getSymbol()) - fontFile.getMinLeftSideBearing() - fontFile.getMinRightSideBearing());
		}

		return len;
	}


	public double getLineHeight()
	{
		return scale(mFontRef.getFontFile().getLineHeight());
	}


	public double getLineGap()
	{
		return scale(mFontRef.getFontFile().getLineGap());
	}


	public double getMinLeftSideBearing()
	{
		return scale(mFontRef.getFontFile().getMinLeftSideBearing());
	}


	public double getMinRightSideBearing()
	{
		return scale(mFontRef.getFontFile().getMinRightSideBearing());
	}


	public double getAscent()
	{
		return scale(mFontRef.getFontFile().getAscent());
	}


	public double getDescent()
	{
		return scale(mFontRef.getFontFile().getDescent());
	}


	double scale(double aLineHeight)
	{
		return aLineHeight * mSize / 15;
	}
}
