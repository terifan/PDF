package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import static org.terifan.pdfwriter.Utilities.renderLine;


public class Line implements Producer, Serializable
{
	private final static long serialVersionUID = 1L;

	private double mLeft;
	private double mTop;
	private double mRight;
	private double mBottom;
	private Color mStrokeColor;
	private double mStrokeThickness;


	public Line()
	{
	}


	public Line(double aTop, double aLeft, double aBottom, double aRight, double aStrokeThickness, Color aStrokeColor)
	{
		mLeft = aLeft;
		mTop = aTop;
		mRight = aRight;
		mBottom = aBottom;
		mStrokeThickness = aStrokeThickness;
		mStrokeColor = aStrokeColor;
	}


	public double getStrokeThickness()
	{
		return mStrokeThickness;
	}


	public Line setStrokeThickness(double aStrokeThickness)
	{
		this.mStrokeThickness = aStrokeThickness;
		return this;
	}


	public double getLeft()
	{
		return mLeft;
	}


	public Line setLeft(double aLeft)
	{
		this.mLeft = aLeft;
		return this;
	}


	public double getTop()
	{
		return mTop;
	}


	public Line setTop(double aTop)
	{
		this.mTop = aTop;
		return this;
	}


	public double getRight()
	{
		return mRight;
	}


	public Line setRight(double aRight)
	{
		this.mRight = aRight;
		return this;
	}


	public double getBottom()
	{
		return mBottom;
	}


	public Line setBottom(double aBottom)
	{
		this.mBottom = aBottom;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Line setStrokeColor(Color aStrokeColor)
	{
		this.mStrokeColor = aStrokeColor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderLine(content, mLeft, mTop, mRight, mBottom, mStrokeThickness, mStrokeColor);

		return baos.toString();
	}
}
