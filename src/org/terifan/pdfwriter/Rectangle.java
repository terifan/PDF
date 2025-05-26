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


	public Rectangle(double aLeft, double aTop, double aRight, double aBottom)
	{
		this(aLeft, aTop, aRight, aBottom, 1, 0, Color.BLACK, null);
	}


	public Rectangle(double aLeft, double aTop, double aRight, double aBottom, double aStrokeThickness, double aCornerRadius, Color aStrokeColor, Color aFillColor)
	{
		if (aRight < aLeft || aBottom > aTop)
		{
			throw new IllegalArgumentException(aRight + " < " + aLeft + " || " + aBottom + " > " + aTop);
		}

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
		mStrokeThickness = aStrokeThickness;
		return this;
	}


	public double getLeft()
	{
		return mLeft;
	}


	public Rectangle setLeft(double aLeft)
	{
		mLeft = aLeft;
		return this;
	}


	public double getTop()
	{
		return mTop;
	}


	public Rectangle setTop(double aTop)
	{
		mTop = aTop;
		return this;
	}


	public double getRight()
	{
		return mRight;
	}


	public Rectangle setRight(double aRight)
	{
		mRight = aRight;
		return this;
	}


	public double getBottom()
	{
		return mBottom;
	}


	public Rectangle setBottom(double aBottom)
	{
		mBottom = aBottom;
		return this;
	}


	public double getCornerRadius()
	{
		return mCornerRadius;
	}


	public Rectangle setCornerRadius(double aCornerRadius)
	{
		mCornerRadius = aCornerRadius;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Rectangle setStrokeColor(Color aStrokeColor)
	{
		mStrokeColor = aStrokeColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Rectangle setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderRectangle(content, content, mLeft, mTop, mRight, mBottom, mCornerRadius, mFillColor, mStrokeThickness, mStrokeColor);

		return baos.toString();
	}
}
