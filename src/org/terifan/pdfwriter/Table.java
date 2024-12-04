package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.terifan.pdfwriter.Insets.ZERO;
import static org.terifan.pdfwriter.Utilities.renderLine;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Table implements Content
{
	private TableRow mHeader;
	private List<TableRow> mContents;
	private double[] mColumnWidths;
	private double mLayoutHeight;
	private boolean mRepeatHeader;
	private boolean mBreakRows;
	private Insets mMargins;
	private Color mFillColor;
	private Color mBorderColor;
	private Insets mBorderThickness;
	private Color mCellBorderColor;
	private Insets mCellPadding;
	private boolean mDrawGrid;
	private boolean mHeaderConsumed;
	private Color mHorizontalGridColor;
	private Color mVerticalGridColor;
	private Double mHorizontalGridThickness;
	private Double mVerticalGridThickness;
	private Double mHeaderGridThickness;
	private double mRowSpacing;
	private double mColumnSpacing;
	private double mHeaderSpacing;
	private int mRenderRow;
	private double mWidth;

	@Deprecated
	private Color mHeaderGridColor;


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
		mMargins = new Insets();
		mCellPadding = null;
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


	@Deprecated
	public Color getHeaderGridColor()
	{
		return mHeaderGridColor;
	}


	@Deprecated
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


	public Insets getCellPadding()
	{
		return mCellPadding;
	}


	public Table setCellPadding(Insets aCellPadding)
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


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Table setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public Color getBorderColor()
	{
		return mBorderColor;
	}


	public Table setBorderColor(Color aBorderColor)
	{
		this.mBorderColor = aBorderColor;
		return this;
	}


	public Insets getBorderThickness()
	{
		return mBorderThickness;
	}


	public Table setBorderThickness(Insets aBorderThickness)
	{
		this.mBorderThickness = aBorderThickness;
		return this;
	}


	public Table setBorder(Color aColor, Insets aThickness)
	{
		setBorderColor(aColor);
		setBorderThickness(aThickness);
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


	public TableRow getHeader()
	{
		return mHeader;
	}


	public Table setHeader(TableRow aHeader)
	{
		mHeader = aHeader;
		return this;
	}


	public double[] getColumnWidths()
	{
		return mColumnWidths;
	}


	public List<TableRow> getContents()
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


	public Table add(TableRow aContent)
	{
		mContents.add(aContent);
		return this;
	}


	public Table addRow(Content... aContent)
	{
		mContents.add(new TableRow(Arrays.asList(aContent)));
		return this;
	}


	public Insets getMargins()
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
		for (TableRow row : mContents)
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
		boolean firstRow = true;

		if (getHeader() != null && !getHeader().isEmpty() && (isRepeatHeader() || !isHeaderConsumed()))
		{
			for (Content p : getHeader())
			{
				p.reuseContent();
			}
			y = renderRow(aPDFWriter, aPage, content, y, aBoundsLeft, aBoundsBottom, aBoundsRight, getHeader(), -1, firstRow) - mHeaderSpacing;
			setHeaderConsumed(true);
			firstRow = false;
		}

		if (y > 0)
		{
			for (int tableRowIndex = 0; mRenderRow < getContents().size(); tableRowIndex++)
			{
				y = renderRow(aPDFWriter, aPage, content, y, aBoundsLeft, aBoundsBottom, aBoundsRight, getContents().get(mRenderRow), tableRowIndex, firstRow);

				if (y < 0)
				{
					break;
				}

				mRenderRow++;
				firstRow = false;
			}
		}

		if (getBorderColor() != null)
		{
			renderRectangle(content, aBoundsLeft, y, aBoundsRight, y, null, new Insets(0, 0, mBorderThickness.bottom(), 0), getBorderColor());
		}

//		double boundsWidth = aBoundsRight - aBoundsLeft;
//		double[] columnWidths = getColumnWidths();
//		double x0 = aBoundsLeft;
//		for (int column = 0; column < columnWidths.length; column++)
//		{
//			if (column > 0)
//			{
//				renderLine(content, x0, aBoundsTop, x0, y, getVerticalGridThickness(), getVerticalGridColor());
//			}
//			x0 += columnWidths[column] * boundsWidth;
//		}

		return y;
	}


	private double renderRow(PDFWriter aPDFWriter, Page aPage, Output aOutput, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, TableRow aRow, int aTableRowIndex, boolean aFirstRow) throws IOException
	{
		double[] columnWidths = getColumnWidths();
		double xstart = aBoundsLeft + ZERO.first(getBorderThickness()).left();
		double xend = aBoundsRight - 0*ZERO.first(getBorderThickness()).right();
		double boundsWidth = xend - xstart - mColumnSpacing * (columnWidths.length - 1);
		double rowHeight = 0;

		Output[] outputs = new Output[columnWidths.length];

		double est = 0;
		{
			double x0 = xstart;
			for (int dataColumn = 0, layoutColumn = 0, n = Math.min(columnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
			{
				Content content = aRow.get(dataColumn);
				double x1 = x0 + columnWidths[layoutColumn] * boundsWidth;

				Insets in;
				if (content instanceof TableCell v)
				{
					in = Insets.add(Insets.first(v.getPadding(), aRow.getPadding(), mCellPadding, ZERO), v.getBorderThickness());

					for (int i = 1; i < v.getColSpan(); i++)
					{
						layoutColumn++;
						x1 += mColumnSpacing + columnWidths[layoutColumn] * boundsWidth;
					}
				}
				else
				{
					in = Insets.first(aRow.getPadding(), mCellPadding, ZERO);
				}

				content.layout(x0 + in.left(), x1 - in.left() - in.right());

				est  = Math.max(est, content.getHeight());

				x0 = x1 + mColumnSpacing;
			}
		}

		if (aBoundsTop - est < aBoundsBottom)
		{
			return -1;
		}

		double x0 = xstart;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(columnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);
			double x1 = x0 + columnWidths[layoutColumn] * boundsWidth;

			Insets in;
			if (content instanceof TableCell v)
			{
				in = Insets.add(Insets.first(v.getPadding(), aRow.getPadding(), mCellPadding, ZERO), v.getBorderThickness());

				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					x1 += mColumnSpacing + columnWidths[layoutColumn] * boundsWidth;
				}
			}
			else
			{
				in = Insets.first(aRow.getPadding(), mCellPadding, ZERO);
			}

			content.layout(x0 + in.left(), x1 - in.left() - in.right());

			outputs[dataColumn] = new Output(new ByteArrayOutputStream());
			double contentHeight = content.produce(aPDFWriter, outputs[dataColumn], aPage, aBoundsTop - in.top(), x0 + in.left(), aBoundsBottom, x1 - in.right());
			rowHeight = Math.max(rowHeight, aBoundsTop - contentHeight + in.top() + in.bottom());

			x0 = x1 + mColumnSpacing;
		}

		if (aRow.isEmpty())
		{
			Insets in = Insets.first(aRow.getPadding(), ZERO);
			rowHeight = in.top() + in.bottom();
		}

//		if (aBoundsTop - rowHeight < aBoundsBottom && !isBreakRows())
//		{
//			return -1;
//		}

		double y1 = aBoundsTop - rowHeight;

		boolean visible = aRow.size() == 0;
		for (int column = 0, n = Math.min(columnWidths.length, aRow.size()); column < n; column++)
		{
			Content content = aRow.get(column);
			visible |= aBoundsTop - content.getHeight() > aBoundsBottom;
		}
		if (!visible)
		{
			return -1;
		}

		if (aRow.getBorderColor() != null || aRow.getFillColor() != null || mFillColor != null)
		{
			renderRectangle(aOutput, aBoundsLeft, aBoundsTop, xend, y1, aRow.getFillColor() == null ? mFillColor : aRow.getFillColor(), aRow.getBorderThickness(), aRow.getBorderColor());
		}
		if (getBorderColor() != null)
		{
			if (aFirstRow)
			{
				renderRectangle(aOutput, aBoundsLeft, aBoundsTop, xend, y1, null, new Insets(mBorderThickness.top(), mBorderThickness.left(), 0, mBorderThickness.right()), getBorderColor());
			}
			else
			{
				renderRectangle(aOutput, aBoundsLeft, aBoundsTop, xend, y1, null, new Insets(0, mBorderThickness.left(), 0, mBorderThickness.right()), getBorderColor());
			}
		}

		x0 = xstart;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(columnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);

			Color fillColor = null;
			Color[] cellBorderColors = null;
			Insets thickness = new Insets(0, 0, 0, 0);

			double x1 = x0 + columnWidths[layoutColumn] * boundsWidth;

			if (content instanceof TableCell v)
			{
				if (v.getFillColor() != null)
				{
					fillColor = v.getFillColor();
				}
				if (v.getBorderColor() != null)
				{
					cellBorderColors = v.getBorderColor();
				}
				if (v.getBorderThickness() != null)
				{
					thickness = v.getBorderThickness();
				}

				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					x1 += mColumnSpacing + columnWidths[layoutColumn] * boundsWidth;
				}
			}

			renderRectangle(aOutput, x0, aBoundsTop, x1, Math.max(y1, aBoundsBottom), fillColor, thickness, cellBorderColors);

			if (aTableRowIndex > 0)
			{
				renderLine(aOutput, x0, aBoundsTop, x1, aBoundsTop, getHorizontalGridThickness(), getHorizontalGridColor());
			}
			if (dataColumn > 0)
			{
				renderLine(aOutput, x0, y1, x0, aBoundsTop, getVerticalGridThickness(), getVerticalGridColor());
			}

			aOutput.print(((ByteArrayOutputStream)outputs[dataColumn].getOutput()).toString());

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
