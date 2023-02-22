package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Table implements Producer
{
	private List<Paragraph> mHeader;
	private List<List<Paragraph>> mContents;
	private double[] mColumnWidths;
	private double mLayoutHeight;
	private boolean mRepeatFirstRow;
	private Margins mMargins;
	private Color mGridColor;
	private boolean mDrawGrid;
	private double mRowPaddingBottom;

	int mRenderRow;


	public Table(boolean aRepeatFirstRow, double... aColumnWidths)
	{
		mRepeatFirstRow = aRepeatFirstRow;
		mColumnWidths = aColumnWidths;

		mContents = new ArrayList<>();
		mHeader = new ArrayList<>();
		mGridColor = Color.BLACK;
		mMargins = new Margins();
	}


	public List<Paragraph> getHeader()
	{
		return mHeader;
	}


	public void setHeader(List<Paragraph> aHeader)
	{
		this.mHeader = aHeader;
	}


	public double[] getColumnWidths()
	{
		return mColumnWidths;
	}


	public List<List<Paragraph>> getContents()
	{
		return mContents;
	}


	public double getRowPaddingBottom()
	{
		return mRowPaddingBottom;
	}


	public void setRowPaddingBottom(double aRowPaddingBottom)
	{
		mRowPaddingBottom = aRowPaddingBottom;
	}


	public boolean isDrawGrid()
	{
		return mDrawGrid;
	}


	public Table setDrawGrid(boolean aDrawGrid)
	{
		mDrawGrid = aDrawGrid;
		return this;
	}


	public Table addRow(List<Paragraph> aContent)
	{
		mContents.add(aContent);
		return this;
	}


//	public Table add(double aWidth, ContentStream aContentStream)
//	{
//		if (mColumnCount > 0 && mNextColumn == mColumnCount)
//		{
//			mNextColumn = 0;
//			mWriteRow++;
//		}
//
//		if (mColumnWidths.size() == mWriteRow)
//		{
//			mColumnWidths.add(new ArrayList<>());
//			mContents.add(new ArrayList<>());
//		}
//
//		mColumnWidths.get(mWriteRow).add(aWidth);
//		mContents.get(mWriteRow).add(aContentStream);
//
//		mNextColumn++;
//
//		return this;
//	}


	public Margins getMargins()
	{
		return mMargins;
	}


	public Color getGridColor()
	{
		return mGridColor;
	}


	public void setGridColor(Color aGridColor)
	{
		mGridColor = aGridColor;
	}


	public double getLayoutHeight()
	{
		return mLayoutHeight;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		return baos.toString();
	}

//	public String getContent(double aOriginX, double aOriginY, double aWidth, double aHeight) throws IOException
//	{
//		StringBuilder builder = new StringBuilder();
//
//		mLayoutHeight = 0;
//
//		boolean drawExtraTableHeader = mRepeatFirstRow && mReadRow > 0;
//
//		for (; mReadRow < mContents.size();)
//		{
//			int row = mReadRow;
//			if (drawExtraTableHeader)
//			{
//				row = 0;
//			}
//
//			double offsetX = 0;
//			double rowHeight = 0;
//			boolean consumed = true;
//
//			for (int column = 0; column < mColumnWidths.get(row).size(); column++)
//			{
//				double x = aOriginX + offsetX;
//				double y = aOriginY - mLayoutHeight;
//				double w = aWidth * mColumnWidths.get(row).get(column);
//				double h = aHeight - mLayoutHeight;
//
//				if (drawExtraTableHeader)
//				{
//					mContents.get(row).get(column).reuseContent();
//				}
//
//				ContentArea contentArea = new ContentArea(x, y, w, h, mContents.get(row).get(column));
//				builder.append(contentArea.getContent());
//				rowHeight = Math.max(rowHeight, contentArea.getLayoutHeight() + mRowPaddingBottom);
//				offsetX += w;
//
//				consumed &= mContents.get(row).get(column).isConsumed();
//			}
//
//			offsetX = 0;
//
//			// draw grid
//			if (mDrawGrid)
//			{
//				builder.append("q 0.5 w ");
//				builder.append(mGridColor + " RG ");
//				for (int column = 0; column < mColumnWidths.get(mReadRow).size(); column++)
//				{
//					double x = aOriginX + offsetX;
//					double y = aOriginY - mLayoutHeight;
//					double w = aWidth * mColumnWidths.get(mReadRow).get(column);
//					double h = rowHeight;
//					builder.append(new Rectangle(x, y, w, h).getContent());
//					offsetX += w;
//				}
//				builder.append("Q\r\n");
//			}
//
//			mLayoutHeight += rowHeight;
//
//			if (consumed)
//			{
//				if (!drawExtraTableHeader)
//				{
//					mReadRow++;
//				}
//
//				if (mLayoutHeight > aHeight)
//				{
//					break;
//				}
//			}
//			else
//			{
//				break;
//			}
//
//			drawExtraTableHeader = false;
//		}
//
//		return builder.toString();
//	}


//	@Override
//	public boolean isConsumed()
//	{
//		return mReadRow == mContents.size();
//	}
//
//
//	@Override
//	public void reuseContent()
//	{
//		mReadRow = 0;
//		mLayoutHeight = 0;
//
//		for (int row = 0; row < mContents.size(); row++)
//		{
//			for (int column = 0; column < mContents.get(row).size(); column++)
//			{
//				if (mContents.get(row).get(column) == null)
//				{
//					throw new IllegalArgumentException("Content in column " + column + ", row " + row + " is null.");
//				}
//
//				mContents.get(row).get(column).reuseContent();
//			}
//		}
//	}
}
