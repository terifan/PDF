package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class TableArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Table mTable;


	public TableArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Table aTable)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
		mTable = aTable;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Output content = new Output(baos);

		content.println("1 0 0 RG");
		content.println(mBoundsLeft + " " + mBoundsTop + " m");
		content.println(mBoundsRight + " " + mBoundsTop + " l");
		content.println(mBoundsRight + " " + mBoundsBottom + " l");
		content.println(mBoundsLeft + " " + mBoundsBottom + " l");
		content.println("s");

		double width = mBoundsRight - mBoundsLeft;
		double[] columnWidths = mTable.getColumnWidths();
		double y = mBoundsTop;

		for (; mTable.mRenderRow < mTable.getContents().size(); )
		{
			double x0 = mBoundsLeft;
			double y0 = y;
			double y1 = y0 - 10;

			if (y1 < mBoundsBottom)
			{
				break;
			}

			double newY = y0;

			for (int column = 0; column < columnWidths.length; column++)
			{
				double cw = columnWidths[column] * width;

				double x1 = x0 + cw;

				newY = Math.min(newY, mTable.getContents().get(mTable.mRenderRow).get(column).produce(aPDFWriter, content, aPage, y0, x0, mBoundsBottom, x1));

				x0 = x1;
			}

			x0 = mBoundsLeft;

			for (int column = 0; column < columnWidths.length; column++)
			{
				double cw = columnWidths[column] * width;

				double x1 = x0 + cw;

				content.println("0 0 1 RG");
				content.println(x0 + " " + y0 + " m");
				content.println(x1 + " " + y0 + " l");
				content.println(x1 + " " + newY + " l");
				content.println(x0 + " " + newY + " l");
				content.println("s");

				x0 = x1;
			}

			y = newY;
			mTable.mRenderRow++;
		}

//		content.print("q ");
//		content.print(mBoundsRight - mBoundsLeft);
//		content.print(" 0 ");
//		content.print(" 0 ");
//		content.print(mBoundsTop - mBoundsBottom);
//		content.print(" ");
//		content.print(mBoundsLeft);
//		content.print(" ");
//		content.print(mBoundsBottom);
//		content.print(" cm " + mTable.getIdentity());
//		content.println(" Do Q");

		return baos.toString();
	}
}
