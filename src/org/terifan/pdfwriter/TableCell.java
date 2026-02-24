package org.terifan.pdfwriter;

import java.io.IOException;


public class TableCell implements Content
{
	private Content mContent;
	private int mColSpan;
	private Color mFillColor;
	private Color[] mBorderColor;
	private Insets mBorderThickness;
	private String mBorderPattern;
	private Insets mPadding;


	public TableCell()
	{
		mColSpan = 1;
	}


	public TableCell(Content aContent)
	{
		mColSpan = 1;
		mContent = aContent;
	}


	public TableCell setContent(Content aContent)
	{
		mContent = aContent;
		return this;
	}


	@Override
	public void reuseContent()
	{
		mContent.reuseContent();
	}


	@Override
	public void layout(double aX0, double aX1)
	{
		mContent.layout(aX0, aX1);
	}


	@Override
	public double getLayoutHeight()
	{
		return mContent.getLayoutHeight();
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aY0, double aX0, double aY1, double aX1) throws IOException
	{
		return mContent.produce(aPDFWriter, aOutput, aPage, aY0, aX0, aY1, aX1);
	}


	@Override
	public boolean isConsumed()
	{
		return mContent.isConsumed();
	}


	@Override
	public double getLayoutWidth()
	{
		return mContent.getLayoutWidth();
	}


	public int getColSpan()
	{
		return mColSpan;
	}


	public TableCell setColSpan(int aColSpan)
	{
		mColSpan = aColSpan;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public TableCell setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public Color[] getBorderColor()
	{
		return mBorderColor;
	}


	public TableCell setBorderColor(Color... aColors)
	{
		mBorderColor = aColors.length == 0 ? null : aColors;
		return this;
	}


	public Insets getBorderThickness()
	{
		return mBorderThickness;
	}


	public TableCell setBorderThickness(Insets aThickness)
	{
		mBorderThickness = aThickness;
		return this;
	}


	public TableCell setBorder(Color aColor, Insets aThickness)
	{
		setBorderColor(aColor, aColor, aColor, aColor);
		setBorderThickness(aThickness);
		return this;
	}


	public String getBorderPattern()
	{
		return mBorderPattern;
	}


	/**
	 * The following dash pattern format is used to set the dash: Dash_array Dash_phase d, where Dash_array is an array that specifies the
	 * lengths of dashes and gaps, the Dash_phase specifies the distance into the dash pattern at which to start the dash and the d is the
	 * setdash operator. For example, the pattern format [3] 0 d defines the dash pattern as 3 units of dash and 3 units of gap, the pattern
	 * format [2 1] 0 d defines the dash pattern as 2 units of dash and 1 unit of gap and the pattern format [] 0 d defines a solid line
	 * with no dash.
	 */
	public TableCell setBorderPattern(String aBorderPattern)
	{
		mBorderPattern = aBorderPattern;
		return this;
	}


	public Insets getPadding()
	{
		return mPadding;
	}


	public TableCell setPadding(Insets aPadding)
	{
		mPadding = aPadding;
		return this;
	}
}
