package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
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
	protected boolean mHeaderConsumed;
	protected double mLayoutWidth;
	protected double mLayoutHeight;
	protected int mRenderRow;

	protected double mRowSpacing;
	protected double mHeaderSpacing;
	protected Insets mCellPadding;

	protected Color mFillColor;
	protected Color mBorderColor;
	protected Insets mBorderThickness;

	protected boolean mDrawGrid;
	protected Color mHorizontalGridColor;
	protected Color mHeaderGridColor;
	protected Color mVerticalGridColor;
	protected Double mHorizontalGridThickness;
	protected Double mVerticalGridThickness;
	protected Double mHeaderGridThickness;

	protected boolean mExtendTableEnabled;

	@Deprecated
	protected Color mCellBorderColor;
	private String mBorderPattern;


	/**
	 * @param aColumnWeights
	 *   Weight of each column. Weights are normalized and column widths are computed from these weights.
	 *   <p>
	 *   Hint: to create five equally sized columns simply write: <pre>new Table(new double[5])</pre>
	 *   </p>
	 */
	public Table(double... aColumnWeights)
	{
		setColumnWeights(aColumnWeights);

		mContents = new ArrayList<>();
		mMargins = new Insets();
		mCellPadding = null;
		mVerticalGridThickness = 0.5;
		mHorizontalGridThickness = 0.5;
		mHeaderGridThickness = 0.5;
		mBorderThickness = new Insets();
		mCellPadding = new Insets();
	}


	/**
	 * @param aColumnWeights
	 *   Weight of each column. Weights are normalized and column widths are computed from these weights.
	 *   <p>
	 *   Hint: to create five equally sized columns simply write: <pre>table.setColumnWeights(new double[5])</pre>
	 *   </p>
	 */
	public Table setColumnWeights(double... aColumnWeights)
	{
		mColumnWidths = aColumnWeights;

		double w = Arrays.stream(mColumnWidths).sum();
		if (w <= 0)
		{
			Arrays.fill(mColumnWidths, 1.0 / mColumnWidths.length);
		}
		else
		{
			for (int i = 0; i < mColumnWidths.length; i++)
			{
				mColumnWidths[i] /= w;
			}
		}

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


	/**
	 *
	 * @return true if the Table should be extended.
	 */
	public boolean isExtendTableEnabled()
	{
		return mExtendTableEnabled;
	}


	/**
	 * The Table will be extended to fill the entire TableArea it is placed in.
	 *
	 * @param aExtendTableEnabled true if the table should be extended.
	 */
	public Table setExtendTableEnabled(boolean aExtendTableEnabled)
	{
		mExtendTableEnabled = aExtendTableEnabled;
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


	public Table setCellPadding(Insets aDefaultCellPadding)
	{
		mCellPadding = aDefaultCellPadding;
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
		mBorderColor = aBorderColor;
		return this;
	}


	public Insets getBorderThickness()
	{
		return mBorderThickness;
	}


	public Table setBorderThickness(Insets aBorderThickness)
	{
		mBorderThickness = aBorderThickness;
		return this;
	}


	public Table setBorder(Color aColor, Insets aThickness)
	{
		setBorderColor(aColor);
		setBorderThickness(aThickness);
		return this;
	}


	public String getBorderPattern()
	{
		return mBorderPattern;
	}


	public Table setBorderPattern(String aBorderPattern)
	{
		mBorderPattern = aBorderPattern;
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
			TableRow row = mContents.get(sourceRowIndex);
			double x0 = aX0;
			double rowHeight = 0;
			double[] cw = row.getColumnWidths() != null ? row.getColumnWidths() : mColumnWidths;

			for (int dataColumn = 0, layoutColumn = 0, n = Math.min(cw.length, row.size()); dataColumn < n; dataColumn++, layoutColumn++)
			{
				Content content = row.get(dataColumn);
				double x1 = x0 + cw[layoutColumn] * boundsWidth;

				Insets in;
				if (content instanceof TableCell v)
				{
					in = Insets.add(Insets.first(v.getPadding(), row.getPadding(), mCellPadding, ZERO), v.getBorderThickness());
					for (int i = 1; i < v.getColSpan(); i++)
					{
						x1 += cw[++layoutColumn] * boundsWidth;
					}
				}
				else
				{
					in = Insets.first(row.getPadding(), mCellPadding, ZERO);
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
		Double y = aBoundsTop;
		boolean firstRowInTable = true;

		if (mRenderRow >= mContents.size())
		{
			return y;
		}

		Output backgroundOutput = new Output();
		Output fillOutput = new Output();
		Output lineOutput = new Output();
		Output textOutput = new Output();

		y -= mBorderThickness.top();

		if (mHeader != null && !mHeader.isEmpty() && (isRepeatHeader() || !isHeaderConsumed()))
		{
			for (Content p : mHeader)
			{
				p.reuseContent();
			}
			y = renderRow(aPDFWriter, aPage, textOutput, fillOutput, lineOutput, y, aBoundsLeft + mBorderThickness.left(), aBoundsBottom, aBoundsRight - mBorderThickness.right(), mHeader, -1, firstRowInTable) - mHeaderSpacing;
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
				y = renderRow(aPDFWriter, aPage, textOutput, fillOutput, lineOutput, y, aBoundsLeft + mBorderThickness.left(), aBoundsBottom, aBoundsRight - mBorderThickness.right(), mContents.get(mRenderRow), tableRowIndex, firstRowInTable);

				if (y == null)
				{
					y = aBoundsBottom;
					break;
				}

				mRenderRow++;
				firstRowInTable = false;
			}

			y -= mBorderThickness.bottom();

			if (!isConsumed())
			{
				y -= mRowSpacing;
			}
		}

		renderRectangle(backgroundOutput, lineOutput, aBoundsLeft, aBoundsTop, aBoundsRight, y, mFillColor, mBorderThickness, mBorderPattern, mBorderColor);

		if (mExtendTableEnabled)
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

		aOutput.append(backgroundOutput);
		aOutput.append(fillOutput);
		aOutput.append(lineOutput);
		aOutput.append(textOutput);

		mLayoutHeight = aBoundsTop - y;

		return y;
	}


	private Double renderRow(PDFWriter aPDFWriter, Page aPage, Output textOutput, Output fillOutput, Output borderOutput, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, TableRow aRow, int aTableRowIndex, boolean aFirstRowInTable) throws IOException
	{
		Insets rowBorderThickness = aRow.getBorderThickness() == null ? ZERO : aRow.getBorderThickness();

		double x0 = aBoundsLeft;
		double x1 = aBoundsRight;

		double boundsWidth = (x1 - rowBorderThickness.right()) - (x0 + rowBorderThickness.right());
		double rowHeight = 0;
		double[] cw = aRow.getColumnWidths() != null ? aRow.getColumnWidths() : mColumnWidths;

		Output[] outputs = new Output[cw.length];

		if (aRow.isEmpty())
		{
			Insets in = Insets.first(aRow.getPadding(), ZERO);
			rowHeight += in.top() + in.bottom();
		}
		else
		{
			x0 += rowBorderThickness.left();
			x1 -= rowBorderThickness.right();

			for (int loop = 0; loop < 2; loop++)
			{
				double columnX0 = x0;

				for (int dataColumn = 0, layoutColumn = 0, n = Math.min(cw.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
				{
					Content content = aRow.get(dataColumn);
					double columnX1 = columnX0 + cw[layoutColumn] * boundsWidth;

					Insets padding = null;
					if (content instanceof TableCell v)
					{
						padding = Insets.add(Insets.first(v.getPadding(), aRow.getPadding(), mCellPadding), v.getBorderThickness());
						for (int i = 1; i < v.getColSpan(); i++)
						{
							columnX1 += cw[++layoutColumn] * boundsWidth;
						}
					}
					else
					{
						padding = Insets.add(aRow.getPadding(), mCellPadding);
					}

					content.layout(columnX0 + padding.left(), columnX1 - padding.right());

					double contentHeight;
					if (loop == 0)
					{
						contentHeight = content.getLayoutHeight();
						rowHeight = Math.max(rowHeight, contentHeight + padding.bottom());
					}
					else
					{
						outputs[dataColumn] = new Output(new ByteArrayOutputStream());
						contentHeight = content.produce(aPDFWriter, outputs[dataColumn], aPage, aBoundsTop - rowBorderThickness.top() - padding.top(), columnX0 + padding.left(), aBoundsBottom, columnX1 - padding.right());
						rowHeight = Math.max(rowHeight, aBoundsTop - contentHeight + padding.bottom());
					}

					columnX0 = columnX1;
				}

				if (loop == 0 && aTableRowIndex > 0 && aBoundsTop - rowHeight < aBoundsBottom)
				{
					return null;
				}
			}
		}

		rowHeight += rowBorderThickness.top() + rowBorderThickness.bottom();

		double y0 = aBoundsTop;
		double y1 = y0 - rowHeight;

		renderRectangle(fillOutput, borderOutput, x0 - rowBorderThickness.left(), y0, x1 + rowBorderThickness.right(), y1 - rowBorderThickness.bottom(), aRow.getFillColor(), aRow.getBorderThickness(), aRow.getBorderPattern(), aRow.getBorderColor());

		y0 -= rowBorderThickness.top();
		y1 -= rowBorderThickness.bottom();

		double columnX0 = x0;
		for (int dataColumn = 0, layoutColumn = 0, n = Math.min(cw.length, aRow.size()); dataColumn < n; dataColumn++, layoutColumn++)
		{
			Content content = aRow.get(dataColumn);

			Color cellFillColor = null;
			Color[] cellBorderColors = null;
			Insets cellBorderThickness = new Insets();
			String borderPattern = null;

			double columnX1 = columnX0 + cw[layoutColumn] * boundsWidth;

			if (content instanceof TableCell v)
			{
				cellFillColor = v.getFillColor();
				cellBorderColors = v.getBorderColor();
				cellBorderThickness.set(v.getBorderThickness());
				borderPattern = v.getBorderPattern();

				for (int i = 1; i < v.getColSpan(); i++)
				{
					layoutColumn++;
					columnX1 += cw[layoutColumn] * boundsWidth;
				}
			}

			renderRectangle(fillOutput, borderOutput, columnX0, y0, columnX1, y1 + cellBorderThickness.bottom(), cellFillColor, cellBorderThickness, borderPattern, cellBorderColors);

			if (aTableRowIndex == 0 && mHeaderGridThickness > 0)
			{
				renderLine(borderOutput, columnX0, y0, columnX1, y0, mHeaderGridThickness, mHeaderGridColor);
			}
			if (aTableRowIndex > 0)
			{
				renderLine(borderOutput, columnX0, y0, columnX1, y0, mHorizontalGridThickness, mHorizontalGridColor);
			}
			if (!mExtendTableEnabled && dataColumn > 0)
			{
				renderLine(borderOutput, columnX0, y1, columnX0, y0, mVerticalGridThickness, mVerticalGridColor);
			}

			textOutput.print(outputs[dataColumn].getOutput().toString());

			columnX0 = columnX1;
		}

		y0 = y1;

		return y0;
	}
}
