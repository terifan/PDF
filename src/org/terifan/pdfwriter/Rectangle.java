package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Rectangle implements Producer, Serializable
{
	private final static long serialVersionUID = 1L;

	private double mLeft;
	private double mTop;
	private double mRight;
	private double mBottom;
	private double mCornerRadius;
	private Color mStrokeColor;
	private Color mFillColor;
	private double mStrokeThickness;


	public Rectangle()
	{
	}


	public Rectangle(double aTop, double aLeft, double aBottom, double aRight, double aStrokeThickness, double aCornerRadius, Color aStrokeColor, Color aFillColor)
	{
		mLeft = aLeft;
		mTop = aTop;
		mRight = aRight;
		mBottom = aBottom;
		mStrokeThickness = aStrokeThickness;
		mCornerRadius = aCornerRadius;
		mStrokeColor = aStrokeColor;
		mFillColor = aFillColor;
	}


	public double getStrokeThickness()
	{
		return mStrokeThickness;
	}


	public Rectangle setStrokeThickness(double aStrokeThickness)
	{
		this.mStrokeThickness = aStrokeThickness;
		return this;
	}


	public double getLeft()
	{
		return mLeft;
	}


	public Rectangle setLeft(double aLeft)
	{
		this.mLeft = aLeft;
		return this;
	}


	public double getTop()
	{
		return mTop;
	}


	public Rectangle setTop(double aTop)
	{
		this.mTop = aTop;
		return this;
	}


	public double getRight()
	{
		return mRight;
	}


	public Rectangle setRight(double aRight)
	{
		this.mRight = aRight;
		return this;
	}


	public double getBottom()
	{
		return mBottom;
	}


	public Rectangle setBottom(double aBottom)
	{
		this.mBottom = aBottom;
		return this;
	}


	public double getCornerRadius()
	{
		return mCornerRadius;
	}


	public Rectangle setCornerRadius(double aCornerRadius)
	{
		this.mCornerRadius = aCornerRadius;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Rectangle setStrokeColor(Color aStrokeColor)
	{
		this.mStrokeColor = aStrokeColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Rectangle setFillColor(Color aFillColor)
	{
		this.mFillColor = aFillColor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderRectangle(content, mLeft, mTop, mRight, mBottom, mStrokeThickness, mCornerRadius, mFillColor, mStrokeColor);

		return baos.toString();
	}
}
