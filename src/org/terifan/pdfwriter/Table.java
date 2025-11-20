package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.terifan.pdfwriter.Insets.THIN;
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
	public void layout(double aBoundsLeft, double aBoundsRight)
	{
		mLayoutWidth = aBoundsRight - aBoundsLeft;
		mLayoutHeight = mBorderThickness.top() + mBorderThickness.bottom();

		if (mHeader != null && !mHeader.isEmpty() && (isRepeatHeader() || !isHeaderConsumed()))
		{
			for (Content p : mHeader)
			{
				p.reuseContent();
			}

			mLayoutHeight += layoutRow(mHeader, aBoundsLeft, aBoundsRight);
			mLayoutHeight += mHeaderSpacing;
		}

		for (int tableRowIndex = 0, sourceRowIndex = mRenderRow; sourceRowIndex < mContents.size(); tableRowIndex++, sourceRowIndex++)
		{
			mLayoutHeight += layoutRow(mContents.get(sourceRowIndex), aBoundsLeft, aBoundsRight);
		}
	}


	private double layoutRow(TableRow aRow, double aBoundsLeft, double aBoundsRight)
	{
		Insets rowBorderThickness = Insets.add(aRow.getBorderThickness());

		double x0 = aBoundsLeft + rowBorderThickness.left();
		double x1 = aBoundsRight - rowBorderThickness.right();
		double boundsWidth = (x1 - rowBorderThickness.right()) - (x0 + rowBorderThickness.left());
		double rowHeight = 0;

		double[] cw = aRow.getColumnWidths() != null ? aRow.getColumnWidths() : mColumnWidths;
		double columnX0 = aBoundsLeft;

		for (int columnIndex = 0, layoutColumn = 0, n = Math.min(cw.length, aRow.size()); columnIndex < n; columnIndex++, layoutColumn++)
		{
			Content content = aRow.get(columnIndex);
			double columnX1 = columnX0 + cw[layoutColumn] * boundsWidth;

			Insets cellPadding;
			if (content instanceof TableCell v)
			{
				cellPadding = Insets.add(v.getPadding(), aRow.getPadding(), mCellPadding, v.getBorderThickness());
				for (int i = 1; i < v.getColSpan(); i++)
				{
					columnX1 += cw[++layoutColumn] * boundsWidth;
				}
			}
			else
			{
				cellPadding = Insets.add(aRow.getPadding(), mCellPadding);
			}

			content.layout(columnX0 + cellPadding.left(), columnX1 - cellPadding.right());

			rowHeight = Math.max(rowHeight, content.getLayoutHeight() + cellPadding.top() + cellPadding.bottom() + rowBorderThickness.top() + rowBorderThickness.bottom());

			columnX0 = columnX1;
		}

		return rowHeight;
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
			y = renderRow(aPDFWriter, aPage, textOutput, fillOutput, lineOutput, y, aBoundsLeft + mBorderThickness.left(), aBoundsBottom, aBoundsRight - mBorderThickness.right(), mHeader, -1);
			if (y == null)
			{
				y = aBoundsBottom;
			}
			else
			{
				y -= mHeaderSpacing;
				if (y > 0)
				{
					setHeaderConsumed(true);
				}
			}
		}

		if (y > 0)
		{
			for (int tableRowIndex = 0; mRenderRow < mContents.size(); tableRowIndex++)
			{
				TableRow row = mContents.get(mRenderRow);

				y = renderRow(aPDFWriter, aPage, textOutput, fillOutput, lineOutput, y, aBoundsLeft + mBorderThickness.left(), aBoundsBottom, aBoundsRight - mBorderThickness.right(), row, tableRowIndex);

//				if (y == null || y < aBoundsBottom)
				if (y == null)
				{
					y = aBoundsBottom;
					break;
				}

				mRenderRow++;
			}

//			y -= mBorderThickness.bottom();

			if (!isConsumed())
			{
				y -= mRowSpacing;
			}
		}

		y -= mBorderThickness.bottom();

//		renderRectangle(backgroundOutput, lineOutput, aBoundsLeft, aBoundsTop, aBoundsRight, y, mFillColor, THIN, mBorderPattern, Color.CYAN);
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

//		mLayoutHeight = aBoundsTop - y;

		return y;
	}


	private Double renderRow(PDFWriter aPDFWriter, Page aPage, Output aTextOutput, Output aFillOutput, Output aBorderOutput, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, TableRow aRow, int aTableRowIndex) throws IOException
	{
		Insets rowBorderThickness = Insets.add(aRow.getBorderThickness());

		double x0 = aBoundsLeft + rowBorderThickness.left();
		double x1 = aBoundsRight - rowBorderThickness.right();
		double boundsWidth = x1 - x0;
		double rowHeight = 0;

		double[] cw = aRow.getColumnWidths() != null ? aRow.getColumnWidths() : mColumnWidths;

		Output[] outputs = new Output[cw.length];

		if (aRow.isEmpty())
		{
			Insets padding = Insets.add(aRow.getPadding());
			rowHeight += padding.top() + padding.bottom() + rowBorderThickness.top() + rowBorderThickness.bottom();
		}
		else
		{
			double[] columnOffsets = new double[1 + Math.min(cw.length, aRow.size())];
			Insets[] columnInsets = new Insets[Math.min(cw.length, aRow.size())];

			columnOffsets[0] = x0;

			for (int columnIndex = 0, layoutColumn = 0, n = Math.min(cw.length, aRow.size()); columnIndex < n; columnIndex++, layoutColumn++)
			{
				Content content = aRow.get(columnIndex);
				double columnX1 = columnOffsets[columnIndex] + cw[layoutColumn] * boundsWidth;

				Insets cellPadding;
				if (content instanceof TableCell v)
				{
					cellPadding = Insets.add(v.getPadding(), aRow.getPadding(), mCellPadding, v.getBorderThickness());
					for (int i = 1; i < v.getColSpan(); i++)
					{
						columnX1 += cw[++layoutColumn] * boundsWidth;
					}
				}
				else
				{
					cellPadding = Insets.add(aRow.getPadding(), mCellPadding);
				}

				columnInsets[columnIndex] = cellPadding;

				content.layout(columnOffsets[columnIndex] + cellPadding.left(), columnX1 - cellPadding.right());

				rowHeight = Math.max(rowHeight, content.getLayoutHeight() + cellPadding.top() + cellPadding.bottom() + rowBorderThickness.top() + rowBorderThickness.bottom());

				columnOffsets[columnIndex + 1] = columnX1;
			}

			if (aTableRowIndex > 0 && aBoundsTop - rowHeight <= aBoundsBottom)
			{
				return null;
			}

			for (int columnIndex = 0, layoutColumn = 0, n = Math.min(cw.length, aRow.size()); columnIndex < n; columnIndex++, layoutColumn++)
			{
				Content content = aRow.get(columnIndex);
				double columnX1 = columnOffsets[1 + columnIndex];
				double cx0 = columnOffsets[columnIndex] + columnInsets[columnIndex].left();
				double cx1 = columnX1 - columnInsets[columnIndex].right();
				double cy0 = aBoundsTop - rowBorderThickness.top() - columnInsets[columnIndex].top();

				outputs[columnIndex] = new Output(new ByteArrayOutputStream());
//				content.produce(aPDFWriter, outputs[columnIndex], aPage, cy0, cx0, aBoundsTop - rowHeight, cx1);
				content.produce(aPDFWriter, outputs[columnIndex], aPage, cy0, cx0, aBoundsBottom, cx1);
			}
		}

		double y0 = aBoundsTop;
		double y1 = aBoundsTop - rowHeight;

		renderRectangle(aFillOutput, aBorderOutput, aBoundsLeft, y0, aBoundsRight, y1, aRow.getFillColor(), aRow.getBorderThickness(), aRow.getBorderPattern(), aRow.getBorderColor());

		double _y0 = y0 - rowBorderThickness.top();
		double _y1 = y1 + rowBorderThickness.bottom();

//		renderLine(aBorderOutput, aBoundsLeft, y0, aBoundsRight, y1, 0.5, aTableRowIndex == -1 ? Color.CYAN : Color.MAGENTA);

		double columnX0 = x0;
		for (int dataColumn = 0, layoutColumn = 0, columnCount = Math.min(cw.length, aRow.size()); dataColumn < columnCount; dataColumn++, layoutColumn++)
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

//			renderRectangle(aFillOutput, aBorderOutput, columnX0, _y0, columnX1, _y1, cellFillColor, THIN, borderPattern, Color.GREEN);
			renderRectangle(aFillOutput, aBorderOutput, columnX0, _y0, columnX1, _y1, cellFillColor, cellBorderThickness, borderPattern, cellBorderColors);

			if (aTableRowIndex == 0 && mHeaderGridThickness > 0)
			{
				renderLine(aBorderOutput, columnX0, _y0, columnX1, _y0, mHeaderGridThickness, mHeaderGridColor);
			}
			if (aTableRowIndex > 0)
			{
				renderLine(aBorderOutput, columnX0, _y0, columnX1, _y0, mHorizontalGridThickness, mHorizontalGridColor);
			}
			if (!mExtendTableEnabled && dataColumn > 0)
			{
				renderLine(aBorderOutput, columnX0, _y1, columnX0, _y0, mVerticalGridThickness, mVerticalGridColor);
			}

			aTextOutput.print(outputs[dataColumn].getOutput().toString());

			columnX0 = columnX1;
		}

		return y1;
	}
}
