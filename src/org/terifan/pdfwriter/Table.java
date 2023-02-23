package org.terifan.pdfwriter;

import java.util.ArrayList;
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
	private double mRowPaddingBottom;

	int mRenderRow;


	public Table(double... aColumnWidths)
	{
		mColumnWidths = aColumnWidths;

		mContents = new ArrayList<>();
		mHeader = new ArrayList<>();
		mMargins = new Margins();
		mCellPadding = new Margins(0, 0, 0, 0);
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
}
