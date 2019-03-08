package pdf;

import java.util.ArrayList;


public class Style
{
	private Font mFont;
	private double mSize;


	public Style(Font aFontRef, double aSize)
	{
		mFont = aFontRef;
		mSize = aSize;
	}


	public Font getFontInstance()
	{
		return mFont;
	}


	public double getSize()
	{
		return mSize;
	}


	public double getAdvance(Symbol aSymbol)
	{
		return mSize * aSymbol.getAdvance();
	}


	public double getLeftBearing(Symbol aSymbol)
	{
		return mSize * aSymbol.getLeftBearing();
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


	private double scale(double aValue)
	{
//		System.out.println("bbox: "+mFontRef.getFontFile().getFontBBox()[0] + "\t" + mFontRef.getFontFile().getFontBBox()[1] + "\t" + mFontRef.getFontFile().getFontBBox()[2] + "\t" + mFontRef.getFontFile().getFontBBox()[3] + "\theight: " + mFontRef.getFontFile().getLineHeight()+ "\tgap: " + mFontRef.getFontFile().getLineGap() + "\tascent: " + mFontRef.getFontFile().getAscent() + "\tdescent: " + mFontRef.getFontFile().getDescent());

		return aValue * mSize / (mFont.getFontFile().getAscent() - mFont.getFontFile().getDescent());
	}
}
