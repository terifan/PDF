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
	protected TableRow mHeader;
	protected List<TableRow> mContents;
	protected double[] mColumnWidths;
	protected boolean mRepeatHeader;
	protected boolean mBreakRows;
	protected Insets mMargins;
	protected Color mFillColor;
	protected Color mBorderColor;
	protected Insets mBorderThickness;
	protected Color mCellBorderColor;
	protected Insets mCellPadding;
	protected boolean mDrawGrid;
	protected boolean mHeaderConsumed;
	protected Color mHorizontalGridColor;
	protected Color mHeaderGridColor;
	protected Color mVerticalGridColor;
	protected Double mHorizontalGridThickness;
	protected Double mVerticalGridThickness;
	protected Double mHeaderGridThickness;
	protected double mRowSpacing;
	protected double mHeaderSpacing;
	protected int mRenderRow;
	protected double mLayoutWidth;
	protected double mLayoutHeight;

	@Deprecated
	protected boolean mDrawExtendedLines;


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


	public Color getHeaderGridColor()
	{
		return mHeaderGridColor;
	}


	public Table setHeaderGridColor(Color aHeaderGridColor)
	{
		mHeaderGridColor = aHeaderGridColor;
		return this;
	}


	@Deprecated
	public boolean isDrawExtendedLines()
	{
		return mDrawExtendedLines;
	}


	@Deprecated
	public Table setDrawExtendedLines(boolean aDrawExtendedLines)
	{
		mDrawExtendedLines = aDrawExtendedLines;
		return this;
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
		mLayoutWidth = aX1 - aX0;
		mLayoutHeight = 0;

		double xstart = aX0 + ZERO.first(mBorderThickness).left();
		double xend = aX1;
		double boundsWidth = xend - xstart;

		for (int tableRowIndex = 0, sourceRowIndex = mRenderRow; sourceRowIndex < mContents.size(); tableRowIndex++, sourceRowIndex++)
		{
			TableRow aRow = mContents.get(sourceRowIndex);
			double x0 = aX0;
			double rowHeight = 0;

			for (int dataColumn = 0, layoutColumn = 0, n = Math.min(mColumnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
			{
				Content content = aRow.get(dataColumn);
				double x1 = x0 + mColumnWidths[layoutColumn] * boundsWidth;

				Insets in;
				if (content instanceof TableCell v)
				{
					in = Insets.add(Insets.first(v.getPadding(), aRow.getPadding(), mCellPadding, ZERO), v.getBorderThickness());
					for (int i = 1; i < v.getColSpan(); i++)
					{
						x1 += mColumnWidths[++layoutColumn] * boundsWidth;
					}
				}
				else
				{
					in = Insets.first(aRow.getPadding(), mCellPadding, ZERO);
				}

				content.layout(x0 + in.left(), x1 - in.left() - in.right());

				double contentHeight = content.getLayoutHeight();

				rowHeight = Math.max(rowHeight, contentHeight + in.bottom());

				x0 = x1;
			}

			mLayoutHeight += rowHeight;
		}
	}


	@Override
	public double getLayoutWidth()
	{
		return mLayoutWidth;
	}


	@Override
	public double getLayoutHeight()
	{
		return mLayoutHeight;
	}


	public int getRowCount()
	{
		return mContents.size();
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		double y = aBoundsTop;
		boolean firstRowInTable = true;

		if (mRenderRow >= mContents.size())
		{
			return y;
		}

		if (mHeader != null && !mHeader.isEmpty() && (isRepeatHeader() || !isHeaderConsumed()))
		{
			for (Content p : mHeader)
			{
				p.reuseContent();
			}
			y = renderRow(aPDFWriter, aPage, aOutput, y, aBoundsLeft, aBoundsBottom, aBoundsRight, mHeader, -1, firstRowInTable) - mHeaderSpacing;
			if (y > 0)
			{
				setHeaderConsumed(true);
			}
			firstRowInTable = false;
		}

		if (y > 0)
		{
			for (int tableRowIndex = 0; mRenderRow < mContents.size(); tableRowIndex++)
			{
				y = renderRow(aPDFWriter, aPage, aOutput, y, aBoundsLeft, aBoundsBottom, aBoundsRight, mContents.get(mRenderRow), tableRowIndex, firstRowInTable);

				if (y < 0)
				{
					break;
				}

				mRenderRow++;
				firstRowInTable = false;
			}
		}

		if (getBorderColor() != null)
		{
			renderRectangle(aOutput, aOutput, aBoundsLeft, y, aBoundsRight, y, null, new Insets(0, 0, mBorderThickness.bottom(), 0), mBorderColor);
		}

		if (mDrawExtendedLines)
		{
			double boundsWidth = aBoundsRight - aBoundsLeft;
			double[] columnWidths = getColumnWidths();
			double x0 = aBoundsLeft;
			for (int column = 0; column < columnWidths.length; column++)
			{
				if (column > 0)
				{
					renderLine(aOutput, x0, aBoundsTop, x0, aBoundsBottom, getVerticalGridThickness(), getVerticalGridColor());
				}
				x0 += columnWidths[column] * boundsWidth;
			}
		}

		return y;
	}


	private double renderRow(PDFWriter aPDFWriter, Page aPage, Output aOutput, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, TableRow aRow, int aTableRowIndex, boolean aFirstRowInTable) throws IOException
	{
		double x0 = aBoundsLeft;
		double x1 = aBoundsRight;

		Insets rowBorderThickness = aRow.getBorderThickness() == null ? ZERO : aRow.getBorderThickness();
		x0 += rowBorderThickness.left();
		x1 -= rowBorderThickness.right();

		double boundsWidth = x1 - x0;
		double rowHeight = 0;

		Output[] outputs = new Output[mColumnWidths.length];

		if (aRow.isEmpty())
		{
			Insets in = Insets.first(aRow.getPadding(), ZERO);
			rowHeight += in.top() + in.bottom();
		}
		else
		{
			for (int loop = 0; loop < 2; loop++)
			{
				double columnX0 = x0;

				for (int dataColumn = 0, layoutColumn = 0, n = Math.min(mColumnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
				{
					Content content = aRow.get(dataColumn);
					double columnX1 = columnX0 + mColumnWidths[layoutColumn] * boundsWidth;

					Insets in;
					if (content instanceof TableCell v)
					{
						in = Insets.add(Insets.first(v.getPadding(), aRow.getPadding(), mCellPadding, ZERO), v.getBorderThickness());
						for (int i = 1; i < v.getColSpan(); i++)
						{
							columnX1 += mColumnWidths[++layoutColumn] * boundsWidth;
						}
					}
					else
					{
						in = Insets.first(aRow.getPadding(), mCellPadding, ZERO);
					}

					content.layout(columnX0 + in.left(), columnX1 - in.left() - in.right());

					double contentHeight;
					if (loop == 0)
					{
						contentHeight = content.getLayoutHeight();
						rowHeight = Math.max(rowHeight, contentHeight + in.bottom());
					}
					else
					{
						outputs[dataColumn] = new Output(new ByteArrayOutputStream());
						contentHeight = content.produce(aPDFWriter, outputs[dataColumn], aPage, aBoundsTop - in.top(), columnX0 + in.left(), aBoundsBottom, columnX1 - in.right());
						rowHeight = Math.max(rowHeight, aBoundsTop - contentHeight + in.bottom());
					}

					columnX0 = columnX1;
				}

				if (loop == 0 && aTableRowIndex > 0 && aBoundsTop - rowHeight < aBoundsBottom)
				{
					return -1;
				}
			}
		}

		Output borderOutput = new Output();

		rowHeight += rowBorderThickness.top() + rowBorderThickness.bottom();

		double y0 = aBoundsTop;
		double y1 = y0 - rowHeight;

		if (aRow.getBorderColor() != null || aRow.getFillColor() != null || mFillColor != null)
		{
			Color fillColor = aRow.getFillColor() == null ? mFillColor : aRow.getFillColor();
			Insets borderThickness = mDrawExtendedLines ? ZERO : rowBorderThickness;
			renderRectangle(aOutput, borderOutput, x0 - borderThickness.left(), y0, x1, y1, fillColor, borderThickness, aRow.getBorderColor());
		}
		if (!mDrawExtendedLines && mBorderColor != null)
		{
			Insets borderThickness = new Insets(aFirstRowInTable ? mBorderThickness.top() : 0, mBorderThickness.left(), mBorderThickness.bottom(), mBorderThickness.right());
			renderRectangle(aOutput, borderOutput, x0 - borderThickness.left(), y0, x1, y1, null, borderThickness, mBorderColor);
		}

		y0 += rowBorderThickness.top();
		y1 += rowBorderThickness.bottom();

		double columnX0 = x0;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(mColumnWidths.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);

			Color cellFillColor = null;
			Color[] cellBorderColors = null;
			Insets cellBorderThickness = new Insets(0, 0, 0, 0);

			double columnX1 = columnX0 + mColumnWidths[layoutColumn] * boundsWidth;

			if (content instanceof TableCell v)
			{
				if (v.getFillColor() != null)
				{
					cellFillColor = v.getFillColor();
				}
				if (v.getBorderColor() != null)
				{
					cellBorderColors = v.getBorderColor();
				}
				if (v.getBorderThickness() != null)
				{
					cellBorderThickness = v.getBorderThickness();
				}

				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					columnX1 += mColumnWidths[layoutColumn] * boundsWidth;
				}
			}

			renderRectangle(aOutput, borderOutput, columnX0, y0, columnX1, Math.max(y1, aBoundsBottom), cellFillColor, cellBorderThickness, cellBorderColors);

			if (aTableRowIndex == 0 && mHeaderGridThickness > 0)
			{
				renderLine(borderOutput, columnX0, y0, columnX1, y0, mHeaderGridThickness, mHeaderGridColor);
			}
			if (aTableRowIndex > 0)
			{
				renderLine(borderOutput, columnX0, y0, columnX1, y0, mHorizontalGridThickness, mHorizontalGridColor);
			}
			if (!mDrawExtendedLines && dataColumn > 0)
			{
				renderLine(borderOutput, columnX0, y1, columnX0, y0, mVerticalGridThickness, mVerticalGridColor);
			}

			aOutput.print(outputs[dataColumn].getOutput().toString());

			columnX0 = columnX1;
		}

		aOutput.append(borderOutput);

		y0 -= rowHeight + mRowSpacing;

		if (y0 < aBoundsBottom)
		{
			return -1;
		}

		return y0;
	}
}
