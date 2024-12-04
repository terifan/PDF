package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class TableArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Table mTable;
	private Color mBackgroundColor;


	public TableArea(double aLeft, double aTop, double aRight, double aBottom, Table aTable)
	{
		if (aRight < aLeft || aBottom > aTop)
		{
			throw new IllegalArgumentException();
		}

		mBoundsTop = aTop;
		mBoundsLeft = aLeft;
		mBoundsBottom = aBottom;
		mBoundsRight = aRight;
		mTable = aTable;
	}


	public Color getBackgroundColor()
	{
		return mBackgroundColor;
	}


	public TableArea setBackgroundColor(Color aBackgroundColor)
	{
		mBackgroundColor = aBackgroundColor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderRectangle(content, mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom, null, null, mBackgroundColor, null);

		mTable.produce(aPDFWriter, content, aPage, mBoundsTop, mBoundsLeft, mBoundsBottom, mBoundsRight);

		return baos.toString();
	}
}
