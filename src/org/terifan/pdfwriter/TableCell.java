package org.terifan.pdfwriter;

import java.io.IOException;


public class TableCell implements Content
{
	private Content mContent;
	private int mColSpan;
	private Color mFillColor;
	private Color[] mBorderColor;
	private Insets mBorder;
	private Insets mPadding;


	public TableCell(Content aContent)
	{
		mContent = aContent;
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
	public double getHeight()
	{
		return mContent.getHeight();
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
	public double getWidth()
	{
		return mContent.getWidth();
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
		return mBorder;
	}


	public TableCell setBorderThickness(Insets aThickness)
	{
		mBorder = aThickness;
		return this;
	}


	public TableCell setBorder(Color aColor, Insets aThickness)
	{
		setBorderColor(aColor, aColor, aColor, aColor);
		setBorderThickness(aThickness);
		return this;
	}


	public Insets getPadding()
	{
		return mPadding;
	}


	public TableCell setPadding(Insets aPadding)
	{
		this.mPadding = aPadding;
		return this;
	}
}
