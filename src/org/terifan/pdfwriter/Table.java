package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.terifan.pdfwriter.Utilities.renderLine;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Table implements Content
{
	private List<Content> mHeader;
	private List<List<Content>> mContents;
	private double[] mColumnWidths;
	private double mLayoutHeight;
	private boolean mRepeatHeader;
	private boolean mBreakRows;
	private Margins mMargins;
	private Color mStrokeColor;
	private Color mFillColor;
	private Color mCellBorderColor;
	private Color mCellFillColor;
	private Margins mCellPadding;
	private boolean mDrawGrid;
	private boolean mHeaderConsumed;
	private Color mHorizontalGridColor;
	private Color mVerticalGridColor;
	private Color mHeaderGridColor;
	private Double mHorizontalGridThickness;
	private Double mVerticalGridThickness;
	private Double mHeaderGridThickness;
	private double mRowSpacing;
	private double mColumnSpacing;
	private double mHeaderSpacing;
	private int mRenderRow;
	private double mWidth;


	/**
	 * @param aColumnWeights Weight of each column. Weights are normalized and column widths are computed from these weights.
	 */
	public Table(double... aColumnWeights)
	{
		if (aColumnWeights.length == 0)
		{
			throw new IllegalArgumentException("No column widths defined!");
		}

		mColumnWidths = aColumnWeights;

		double w = Arrays.stream(mColumnWidths).sum();
		for (int i = 0; i < mColumnWidths.length; i++)
		{
			mColumnWidths[i] /= w;
		}

		mContents = new ArrayList<>();
		mHeader = new ArrayList<>();
		mMargins = new Margins();
		mCellPadding = new Margins(0, 0, 0, 0);
		mVerticalGridThickness = 0.5;
		mHorizontalGridThickness = 0.5;
		mHeaderGridThickness = 0.5;
	}


	public double getHeaderSpacing()
	{
		return mHeaderSpacing;
	}


	public Table setHeaderSpacing(double aHeaderSpacing)
	{
		mHeaderSpacing = aHeaderSpacing;
		return this;
	}


	public Color getHeaderGridColor()
	{
		return mHeaderGridColor;
	}


	public Table setHeaderGridColor(Color aHeaderGridColor)
	{
		mHeaderGridColor = aHeaderGridColor;
		return this;
	}


	public Double getHeaderGridThickness()
	{
		return mHeaderGridThickness;
	}


	public Table setHeaderGridThickness(Double aHeaderGridThickness)
	{
		mHeaderGridThickness = aHeaderGridThickness;
		return this;
	}


	public Color getHorizontalGridColor()
	{
		return mHorizontalGridColor;
	}


	public Table setHorizontalGridColor(Color aHorizontalGridColor)
	{
		mHorizontalGridColor = aHorizontalGridColor;
		return this;
	}


	public Color getVerticalGridColor()
	{
		return mVerticalGridColor;
	}


	public Table setVerticalGridColor(Color aVerticalGridColor)
	{
		mVerticalGridColor = aVerticalGridColor;
		return this;
	}


	public Double getHorizontalGridThickness()
	{
		return mHorizontalGridThickness;
	}


	public Table setHorizontalGridThickness(Double aHorizontalGridThickness)
	{
		mHorizontalGridThickness = aHorizontalGridThickness;
		return this;
	}


	public Double getVerticalGridThickness()
	{
		return mVerticalGridThickness;
	}


	public Table setVerticalGridThickness(Double aVerticalGridThickness)
	{
		mVerticalGridThickness = aVerticalGridThickness;
		return this;
	}


	boolean isHeaderConsumed()
	{
		return mHeaderConsumed;
	}


	void setHeaderConsumed(boolean aHeaderConsumed)
	{
		mHeaderConsumed = aHeaderConsumed;
	}


	public Margins getCellPadding()
	{
		return mCellPadding;
	}


	public Table setCellPadding(Margins aCellPadding)
	{
		mCellPadding = aCellPadding;
		return this;
	}


	public Color getCellBorderColor()
	{
		return mCellBorderColor;
	}


	public Table setCellBorderColor(Color aCellBorderColor)
	{
		mCellBorderColor = aCellBorderColor;
		return this;
	}


	public Color getCellFillColor()
	{
		return mCellFillColor;
	}


	public Table setCellFillColor(Color aCellFillColor)
	{
		mCellFillColor = aCellFillColor;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Table setStrokeColor(Color aStrokeColor)
	{
		mStrokeColor = aStrokeColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Table setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public boolean isRepeatHeader()
	{
		return mRepeatHeader;
	}


	public Table setRepeatHeader(boolean aRepeatHeader)
	{
		mRepeatHeader = aRepeatHeader;
		return this;
	}


	public boolean isBreakRows()
	{
		return mBreakRows;
	}


	public Table setBreakRows(boolean aBreakRows)
	{
		mBreakRows = aBreakRows;
		return this;
	}


	public List<Content> getHeader()
	{
		return mHeader;
	}


	public Table setHeader(Content... aHeader)
	{
		return setHeader(Arrays.asList(aHeader));
	}


	public Table setHeader(List<Content> aHeader)
	{
		mHeader = aHeader;
		return this;
	}


	public double[] getColumnWidths()
	{
		return mColumnWidths;
	}


	public List<List<Content>> getContents()
	{
		return mContents;
	}


	public double getRowSpacing()
	{
		return mRowSpacing;
	}


	public Table setRowSpacing(double aRowSpacing)
	{
		mRowSpacing = aRowSpacing;
		return this;
	}


	public double getColumnSpacing()
	{
		return mColumnSpacing;
	}


	public Table setColumnSpacing(double aColumnSpacing)
	{
		this.mColumnSpacing = aColumnSpacing;
		return this;
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


	public Table addRow(Object... aItem)
	{
		ArrayList<Content> row = new ArrayList<>();
		for (Object o : aItem)
		{
			if (o instanceof Collection v)
			{
				row.addAll(v);
			}
			else if (o instanceof Content v)
			{
				row.add(v);
			}
			else if (o.getClass().isArray())
			{
				for (int i = 0, n = java.lang.reflect.Array.getLength(o); i < n; i++)
				{
					row.add((Content)java.lang.reflect.Array.get(o, i));
				}
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		return addRow(row);
	}


	public Table addRow(List<Content> aContent)
	{
		mContents.add(aContent);
		return this;
	}


	public Margins getMargins()
	{
		return mMargins;
	}


	public double getLayoutHeight()
	{
		return mLayoutHeight;
	}


	@Override
	public boolean isConsumed()
	{
		return mRenderRow >= mContents.size();
	}


	public int getRenderRow()
	{
		return mRenderRow;
	}


	@Override
	public void reuseContent()
	{
		mRenderRow = 0;
		for (List<Content> row : mContents)
		{
			for (Content cell : row)
			{
				cell.reuseContent();
			}
		}
	}


	@Override
	public void layout(double aX0, double aX1)
	{
		mWidth = aX1 - aX0;
	}


	@Override
	public double getWidth()
	{
		return mWidth;
	}


	@Override
	public double getHeight()
	{
//		throw new UnsupportedOperationException("Not supported yet.");
		return 16;
	}


	public int getRowCount()
	{
		return mContents.size();
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output content, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		double y = aBoundsTop;

		if (!getHeader().isEmpty() && (isRepeatHeader() || !isHeaderConsumed()))
		{
			for (Content p : getHeader())
			{
				p.reuseContent();
			}
			y = renderRow(aPDFWriter, aPage, content, y, aBoundsLeft, aBoundsBottom, aBoundsRight, getHeader(), -1) - mHeaderSpacing;
			setHeaderConsumed(true);
		}

		if (y > 0)
		{
			for (int tableRowIndex = 0; mRenderRow < getContents().size(); tableRowIndex++)
			{
				y = renderRow(aPDFWriter, aPage, content, y, aBoundsLeft, aBoundsBottom, aBoundsRight, getContents().get(mRenderRow), tableRowIndex);

				if (y < 0)
				{
					break;
				}

				mRenderRow++;
			}
		}

		double boundsWidth = aBoundsRight - aBoundsLeft;
		double[] columnWidths = getColumnWidths();
		double x0 = aBoundsLeft;
		for (int column = 0; column < columnWidths.length; column++)
		{
			if (column > 0)
			{
				renderLine(content, x0, aBoundsTop, x0, aBoundsBottom, getVerticalGridThickness(), getVerticalGridColor());
			}
			x0 += columnWidths[column] * boundsWidth;
		}

		return y;
	}


	private double renderRow(PDFWriter aPDFWriter, Page aPage, Output aOutput, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, List<Content> aRow, int aTableRowIndex) throws IOException
	{
		double[] columnWidths = getColumnWidths();
		double boundsWidth = aBoundsRight - aBoundsLeft - mColumnSpacing * (columnWidths.length - 1);
		double rowHeight = 0;

		Output[] outputs = new Output[columnWidths.length];

		double x0 = aBoundsLeft;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(columnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);

			double x1 = x0 + columnWidths[layoutColumn] * boundsWidth;

			if (content instanceof TableCell v)
			{
				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					x1 += mColumnSpacing + columnWidths[layoutColumn] * boundsWidth;
				}
			}

			content.layout(x0, x1);

			outputs[dataColumn] = new Output(new ByteArrayOutputStream());
			rowHeight = Math.max(rowHeight, aBoundsTop - content.produce(aPDFWriter, outputs[dataColumn], aPage, aBoundsTop, x0, aBoundsBottom, x1));

			x0 = x1 + mColumnSpacing;
		}

		if (aBoundsTop - rowHeight < aBoundsBottom && !isBreakRows())
		{
			return -1;
		}

		double y1 = aBoundsTop - rowHeight;

		boolean visible = false;
		for (int column = 0, n = Math.min(columnWidths.length, aRow.size()); column < n; column++)
		{
			Content content = aRow.get(column);
			visible |= aBoundsTop - content.getHeight() > aBoundsBottom;
//			if (!content.getLayout().isEmpty())
//			{
//				visible |= mBoundsTop - content.getLayout().get(0).height > mBoundsBottom;
//			}
		}
		if (!visible)
		{
			return -1;
		}

		renderRectangle(aOutput, aBoundsLeft, aBoundsTop, aBoundsRight, y1, null, null, getFillColor(), getStrokeColor());

		x0 = aBoundsLeft;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(columnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);

			Color cellFillColor = getCellFillColor();
			Color cellBorderColor = getCellBorderColor();

			double x1 = x0 + columnWidths[layoutColumn] * boundsWidth;

			if (content instanceof TableCell v)
			{
				if (v.getCellFillColor() != null)
				{
					cellFillColor = v.getCellFillColor();
				}
				if (v.getCellBorderColor() != null)
				{
					cellBorderColor = v.getCellBorderColor();
				}

				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					x1 += mColumnSpacing + columnWidths[layoutColumn] * boundsWidth;
				}
			}

			renderRectangle(aOutput, x0, aBoundsTop, x1, Math.max(y1, aBoundsBottom), null, null, cellFillColor, cellBorderColor);

			if (aTableRowIndex == 0)
			{
				renderLine(aOutput, x0, aBoundsTop, x1, aBoundsTop, getHeaderGridThickness(), getHeaderGridColor());
			}
			else if (aTableRowIndex > 0)
			{
				renderLine(aOutput, x0, aBoundsTop, x1, aBoundsTop, getHorizontalGridThickness(), getHorizontalGridColor());
			}

			aOutput.print(((ByteArrayOutputStream)outputs[dataColumn].getOutput()).toString());
//			content.produce(aPDFWriter, aOutput, aPage, aBoundsTop, x0, aBoundsBottom, x1);

			x0 = x1 + mColumnSpacing;
		}

		aBoundsTop -= rowHeight + mRowSpacing;

		if (aBoundsTop < aBoundsBottom)
		{
			return -1;
		}

		return aBoundsTop;
	}
}
