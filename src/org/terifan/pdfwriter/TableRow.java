package org.terifan.pdfwriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class TableRow implements Iterable<Content>
{
	private List<Content> mContents;
	private Insets mPadding;
	private Color mBorderColor;
	private Color mFillColor;
	private Insets mBorderThickness;
	private String mBorderPattern;
	private double[] mColumnWidths;


	public TableRow(Object... aContents)
	{
		mContents = new ArrayList<>();

		for (Object o : aContents)
		{
			add(o);
		}
	}


	public TableRow add(Object aObject)
	{
		if (aObject instanceof Collection v)
		{
			mContents.addAll(v);
		}
		else if (aObject instanceof Content v)
		{
			mContents.add(v);
		}
		else if (aObject.getClass().isArray())
		{
			for (int i = 0, n = java.lang.reflect.Array.getLength(aObject); i < n; i++)
			{
				mContents.add((Content)java.lang.reflect.Array.get(aObject, i));
			}
		}
		else
		{
			throw new IllegalArgumentException(aObject.getClass().getName());
		}
		return this;
	}


	public TableRow(ArrayList<Content> aContents)
	{
		mContents = aContents;
	}


	@Override
	public Iterator<Content> iterator()
	{
		return mContents.iterator();
	}


	public int size()
	{
		return mContents.size();
	}


	public Content get(int aIndex)
	{
		return mContents.get(aIndex);
	}


	public boolean isEmpty()
	{
		return mContents.isEmpty();
	}


	public Insets getPadding()
	{
		return mPadding;
	}


	public TableRow setPadding(Insets aPadding)
	{
		mPadding = aPadding;
		return this;
	}


	public Color getBorderColor()
	{
		return mBorderColor;
	}


	public TableRow setBorderColor(Color aColor)
	{
		mBorderColor = aColor;
		if (mBorderThickness == null)
		{
			mBorderThickness = new Insets(1, 1, 1, 1);
		}
		return this;
	}


	public Insets getBorderThickness()
	{
		return mBorderThickness;
	}


	public TableRow setBorderThickness(Insets aThickness)
	{
		mBorderThickness = aThickness;
		return this;
	}


	public TableRow setBorder(Color aColor, Insets aThickness)
	{
		mBorderColor = aColor;
		mBorderThickness = aThickness;
		return this;
	}


	public String getBorderPattern()
	{
		return mBorderPattern;
	}


	public TableRow setBorderPattern(String aBorderPattern)
	{
		mBorderPattern = aBorderPattern;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public TableRow setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public double[] getColumnWidths()
	{
		return mColumnWidths;
	}


	public TableRow setColumnWidths(double... aColumnWidths)
	{
		mColumnWidths = aColumnWidths;

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
}
