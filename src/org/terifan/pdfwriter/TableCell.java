package org.terifan.pdfwriter;

import java.io.IOException;


public class TableCell implements Content
{
	private Content mContent;
	private int mColSpan;
	private Color mCellFillColor;
	private Color mCellBorderColor;


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


	public Color getCellFillColor()
	{
		return mCellFillColor;
	}


	public TableCell setCellFillColor(Color aCellFillColor)
	{
		this.mCellFillColor = aCellFillColor;
		return this;
	}


	public Color getCellBorderColor()
	{
		return mCellBorderColor;
	}


	public TableCell setCellBorderColor(Color aCellBorderColor)
	{
		this.mCellBorderColor = aCellBorderColor;
		return this;
	}

}
