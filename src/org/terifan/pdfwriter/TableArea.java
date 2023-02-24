package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.terifan.pdfwriter.Paragraph.Row;
import static org.terifan.pdfwriter.Utilities.renderRectangle;
import static org.terifan.pdfwriter.Utilities.renderLine;


public class TableArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Table mTable;
	private Color mBackgroundColor;


	public TableArea(double aBoundsLeft, double aBoundsTop, double aBoundsRight, double aBoundsBottom, Table aTable)
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

		renderRectangle(content, mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom, null, null, mBackgroundColor, null);

		double y = mBoundsTop;

		if (mTable.isRepeatHeader() || !mTable.isHeaderConsumed())
		{
			for (Paragraph p : mTable.getHeader())
			{
				p.reset();
			}
			y = renderRow(aPDFWriter, aPage, content, y, mTable.getHeader(), -1);
			mTable.setHeaderConsumed(true);
		}

		if (y > 0)
		{
			for (int tableRowIndex = 0; mTable.mRenderRow < mTable.getContents().size(); tableRowIndex++)
			{
				y = renderRow(aPDFWriter, aPage, content, y, mTable.getContents().get(mTable.mRenderRow), tableRowIndex);

				if (y < 0)
				{
					break;
				}

				mTable.mRenderRow++;
			}
		}


		double boundsWidth = mBoundsRight - mBoundsLeft;
		double[] columnWidths = mTable.getColumnWidths();
		double x0 = mBoundsLeft;
		for (int column = 0; column < columnWidths.length; column++)
		{
			renderLine(content, x0, mBoundsTop, x0, mBoundsBottom, mTable.getVerticalGridThickness(), mTable.getVerticalGridColor());
			x0 += columnWidths[column] * boundsWidth;
		}

//		if (mTable.isConsumed() && !mTable.getFooter().isEmpty())
//		{
//			int hh = 0;
//			for (Paragraph p : mTable.getHeader())
//			{
//				p.layout(mBoundsTop, mBoundsLeft, mBoundsBottom, mBoundsRight);
//
//				int h = 0;
//				for (Row row : p.getLayout())
//				{
//					h += row.height;
//				}
//				hh = Math.max(h, hh);
//			}
//			System.out.println(hh);
//			y = mBoundsBottom - hh;
//			renderRow(aPDFWriter, aPage, content, y, mTable.getFooter(), -1);
//		}

		return baos.toString();
	}


	private double renderRow(PDFWriter aPDFWriter, Page aPage, Output aContent, double aY0, List<Paragraph> aRow, int aTableRowIndex) throws IOException
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
				paragraph.layout(x0, x1);
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
				visible |= aY0 - paragraph.getLayout().get(0).height > mBoundsBottom;
			}
		}
		if(!visible)
		{
			return -1;
		}

		renderRectangle(aContent, mBoundsLeft, aY0, mBoundsRight, y1, null, null, mTable.getFillColor(), mTable.getStrokeColor());

		x0 = mBoundsLeft;
		for (int column = 0; column < columnWidths.length; column++)
		{
			double cw = columnWidths[column] * boundsWidth;
			double x1 = x0 + cw;

			Paragraph paragraph = aRow.get(column);

			renderRectangle(aContent, x0, aY0, x1, Math.max(y1, mBoundsBottom), null, null, mTable.getCellFillColor(), mTable.getCellBorderColor());

//			if (column > 0)
//			{
//				renderLine(aContent, x0, aY0, x0, Math.max(y1, mBoundsBottom), mTable.getVerticalGridThickness(), mTable.getVerticalGridColor());
//			}
			if (aTableRowIndex == 0)
			{
				renderLine(aContent, x0, aY0, x1, aY0, mTable.getHeaderGridThickness(), mTable.getHeaderGridColor());
			}
			else if (aTableRowIndex > 0)
			{
				renderLine(aContent, x0, aY0, x1, aY0, mTable.getHorizontalGridThickness(), mTable.getHorizontalGridColor());
			}

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
