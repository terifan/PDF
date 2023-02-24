package org.terifan.pdfwriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Table
{
	private List<Paragraph> mHeader;
	private List<List<Paragraph>> mContents;
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
	private double mRowPaddingBottom;
	private Color mHorizontalGridColor;
	private Color mVerticalGridColor;
	private Color mHeaderGridColor;
	private Double mHorizontalGridThickness;
	private Double mVerticalGridThickness;
	private Double mHeaderGridThickness;

	int mRenderRow;


	public Table(double... aColumnWidths)
	{
		mColumnWidths = aColumnWidths;

		mContents = new ArrayList<>();
		mHeader = new ArrayList<>();
		mMargins = new Margins();
		mCellPadding = new Margins(0, 0, 0, 0);
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
		this.mHeaderGridColor = aHeaderGridColor;
		return this;
	}


	public Double getHeaderGridThickness()
	{
		return mHeaderGridThickness;
	}


	public Table setHeaderGridThickness(Double aHeaderGridThickness)
	{
		this.mHeaderGridThickness = aHeaderGridThickness;
		return this;
	}


	public Color getHorizontalGridColor()
	{
		return mHorizontalGridColor;
	}


	public Table setHorizontalGridColor(Color aHorizontalGridColor)
	{
		this.mHorizontalGridColor = aHorizontalGridColor;
		return this;
	}


	public Color getVerticalGridColor()
	{
		return mVerticalGridColor;
	}


	public Table setVerticalGridColor(Color aVerticalGridColor)
	{
		this.mVerticalGridColor = aVerticalGridColor;
		return this;
	}


	public Double getHorizontalGridThickness()
	{
		return mHorizontalGridThickness;
	}


	public Table setHorizontalGridThickness(Double aHorizontalGridThickness)
	{
		this.mHorizontalGridThickness = aHorizontalGridThickness;
		return this;
	}


	public Double getVerticalGridThickness()
	{
		return mVerticalGridThickness;
	}


	public Table setVerticalGridThickness(Double aVerticalGridThickness)
	{
		this.mVerticalGridThickness = aVerticalGridThickness;
		return this;
	}


	boolean isHeaderConsumed()
	{
		return mHeaderConsumed;
	}


	void setHeaderConsumed(boolean aHeaderConsumed)
	{
		this.mHeaderConsumed = aHeaderConsumed;
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
		this.mCellBorderColor = aCellBorderColor;
		return this;
	}


	public Color getCellFillColor()
	{
		return mCellFillColor;
	}


	public Table setCellFillColor(Color aCellFillColor)
	{
		this.mCellFillColor = aCellFillColor;
		return this;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Table setStrokeColor(Color aStrokeColor)
	{
		this.mStrokeColor = aStrokeColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Table setFillColor(Color aFillColor)
	{
		this.mFillColor = aFillColor;
		return this;
	}


	public boolean isRepeatHeader()
	{
		return mRepeatHeader;
	}


	public Table setRepeatHeader(boolean aRepeatHeader)
	{
		this.mRepeatHeader = aRepeatHeader;
		return this;
	}


	public boolean isBreakRows()
	{
		return mBreakRows;
	}


	public Table setBreakRows(boolean aBreakRows)
	{
		this.mBreakRows = aBreakRows;
		return this;
	}


	public List<Paragraph> getHeader()
	{
		return mHeader;
	}


	public Table setHeader(Paragraph... aHeader)
	{
		return setHeader(Arrays.asList(aHeader));
	}


	public Table setHeader(List<Paragraph> aHeader)
	{
		this.mHeader = aHeader;
		return this;
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


	public Table addRow(Paragraph... aHeader)
	{
		return addRow(Arrays.asList(aHeader));
	}


	public Table addRow(List<Paragraph> aContent)
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


	public boolean isConsumed()
	{
		return mRenderRow >= mContents.size();
	}


	public int getRenderRow()
	{
		return mRenderRow;
	}


	public void reuseContent()
	{
		mRenderRow = 0;
		for (List<Paragraph> row : mContents)
		{
			for (Paragraph cell : row)
			{
				cell.reuseContent();
			}
		}
	}
}
