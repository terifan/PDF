package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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

		double y = mBoundsTop;

		if (mTable.isRepeatHeader() || !mTable.isHeaderConsumed())
		{
			for (Paragraph p : mTable.getHeader())
			{
				p.reset();
			}
			y = renderRow(aPDFWriter, aPage, content, y, mTable.getHeader());
			mTable.setHeaderConsumed(true);
		}

		if (y > 0)
		{
			for (; mTable.mRenderRow < mTable.getContents().size(); )
			{
				y = renderRow(aPDFWriter, aPage, content, y, mTable.getContents().get(mTable.mRenderRow));

				if (y < 0)
				{
					break;
				}

				mTable.mRenderRow++;
			}
		}

		return baos.toString();
	}


	private double renderRow(PDFWriter aPDFWriter, Page aPage, Output aContent, double aY0, List<Paragraph> aRow) throws IOException
	{
		double boundsWidth = mBoundsRight - mBoundsLeft;
		double[] columnWidths = mTable.getColumnWidths();
		double rowHeight = 0;

		double x0 = mBoundsLeft;
		for (int column = 0; column < columnWidths.length; column++)
		{
			double cw = columnWidths[column] * boundsWidth;
			double x1 = x0 + cw;

			Paragraph paragraph = aRow.get(column);
			if (!paragraph.isReady())
			{
				paragraph.layout(aY0, x0, mBoundsBottom, x1);
			}

			rowHeight = Math.max(rowHeight, paragraph.getHeight());
			x0 = x1;
		}

		if (aY0 - rowHeight < mBoundsBottom && !mTable.isBreakRows())
		{
			return -1;
		}

		double y1 = aY0 - rowHeight;

		boolean visible = false;
		for (int column = 0; column < columnWidths.length; column++)
		{
			Paragraph paragraph = aRow.get(column);
			if (!paragraph.getLayout().isEmpty())
			{
				visible |= aY0 - paragraph.getLayout().get(0).mHeight > mBoundsBottom;
			}
		}
		if(!visible)
		{
			return -1;
		}

		fillRect(aContent, mBoundsLeft, aY0, mBoundsRight, y1, mTable.getFillColor(), mTable.getStrokeColor());

		x0 = mBoundsLeft;
		for (int column = 0; column < columnWidths.length; column++)
		{
			double cw = columnWidths[column] * boundsWidth;
			double x1 = x0 + cw;

			Paragraph paragraph = aRow.get(column);

			fillRect(aContent, x0, aY0, x1, Math.max(y1, mBoundsBottom), mTable.getCellFillColor(), mTable.getCellBorderColor());

			paragraph.produce(aPDFWriter, aContent, aPage, aY0, x0, mBoundsBottom, x1);

			x0 = x1;
		}

		aY0 -= rowHeight;

		if (aY0 < mBoundsBottom)
		{
			return -1;
		}

		return aY0;
	}
}
