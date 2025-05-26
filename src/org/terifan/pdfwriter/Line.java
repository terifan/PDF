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


	public Line(double aLeft, double aTop, double aRight, double aBottom)
	{
		this(aLeft, aTop, aRight, aBottom, 1, Color.BLACK);
	}


	public Line(double aLeft, double aTop, double aRight, double aBottom, double aStrokeThickness, Color aStrokeColor)
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
		mStrokeThickness = aStrokeThickness;
		return this;
	}


	public double getLeft()
	{
		return mLeft;
	}


	public Line setLeft(double aLeft)
	{
		mLeft = aLeft;
		return this;
	}


	public double getTop()
	{
		return mTop;
	}


	public Line setTop(double aTop)
	{
		mTop = aTop;
		return this;
	}


	public double getRight()
	{
		return mRight;
	}


	public Line setRight(double aRight)
	{
		mRight = aRight;
		return this;
	}


	public double getBottom()
	{
		return mBottom;
	}


	public Line setBottom(double aBottom)
	{
		mBottom = aBottom;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Line setStrokeColor(Color aStrokeColor)
	{
		mStrokeColor = aStrokeColor;
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
