package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.terifan.pdfwriter.Utilities.fillRect;


public class TableArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Table mTable;
	private Color mBackgroundColor;


	public TableArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Table aTable)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
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

		fillRect(content, mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom, mBackgroundColor, null);

		double boundsWidth = mBoundsRight - mBoundsLeft;
		double[] columnWidths = mTable.getColumnWidths();
		double y0 = mBoundsTop;

		for (; mTable.mRenderRow < mTable.getContents().size(); )
		{
			double rowHeight = 0;

			double x0 = mBoundsLeft;
			for (int column = 0; column < columnWidths.length; column++)
			{
				double cw = columnWidths[column] * boundsWidth;
				double x1 = x0 + cw;

				Paragraph paragraph = mTable.getContents().get(mTable.mRenderRow).get(column);
				if (!paragraph.isReady())
				{
					paragraph.layout(y0, x0, mBoundsBottom, x1);
				}

				rowHeight = Math.max(rowHeight, paragraph.getHeight());
				x0 = x1;
			}

			if (y0 - rowHeight < mBoundsBottom && !mTable.isBreakRows())
			{
				break;
			}

			double y1 = y0 - rowHeight;

			boolean visible = false;
			for (int column = 0; column < columnWidths.length; column++)
			{
				Paragraph paragraph = mTable.getContents().get(mTable.mRenderRow).get(column);
				if (!paragraph.getLayout().isEmpty())
				{
					visible |= y0 - paragraph.getLayout().get(0).mHeight > mBoundsBottom;
				}
			}
			if(!visible)
			{
				break;
			}

			fillRect(content, mBoundsLeft, y0, mBoundsRight, y1, mTable.getFillColor(), mTable.getStrokeColor());

			x0 = mBoundsLeft;
			for (int column = 0; column < columnWidths.length; column++)
			{
				double cw = columnWidths[column] * boundsWidth;
				double x1 = x0 + cw;

				Paragraph paragraph = mTable.getContents().get(mTable.mRenderRow).get(column);

				fillRect(content, x0, y0, x1, Math.max(y1, mBoundsBottom), mTable.getCellFillColor(), mTable.getCellBorderColor());

				paragraph.produce(aPDFWriter, content, aPage, y0, x0, mBoundsBottom, x1);

				x0 = x1;
			}

			y0 -= rowHeight;

			if (y0 < mBoundsBottom)
			{
				break;
			}

			mTable.mRenderRow++;
		}

		return baos.toString();
	}
}
