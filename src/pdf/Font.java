package pdf;

import java.util.ArrayList;


public class Font
{
	private ExtendedFont mFontRef;
	private double mSize;


	public Font(ExtendedFont aFontRef, double aSize)
	{
		mFontRef = aFontRef;
		mSize = aSize;
	}


	public ExtendedFont getFontInstance()
	{
		return mFontRef;
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
		return scale(mFontRef.getFontFile().getLineHeight());
	}


	public double getAscent()
	{
		return scale(mFontRef.getFontFile().getAscent());
	}


	public double getDescent()
	{
		return scale(mFontRef.getFontFile().getDescent());
	}


	public double getLineGap()
	{
		return scale(mFontRef.getFontFile().getLineGap());
	}


	private double scale(double aValue)
	{
//		System.out.println("bbox: "+mFontRef.getFontFile().getFontBBox()[0] + "\t" + mFontRef.getFontFile().getFontBBox()[1] + "\t" + mFontRef.getFontFile().getFontBBox()[2] + "\t" + mFontRef.getFontFile().getFontBBox()[3] + "\theight: " + mFontRef.getFontFile().getLineHeight()+ "\tgap: " + mFontRef.getFontFile().getLineGap() + "\tascent: " + mFontRef.getFontFile().getAscent() + "\tdescent: " + mFontRef.getFontFile().getDescent());

		return aValue * mSize / (mFontRef.getFontFile().getAscent() - mFontRef.getFontFile().getDescent());
	}
}
