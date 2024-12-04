package org.terifan.pdfwriter;

import java.util.ArrayList;
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


	public TableRow(Object... aContents)
	{
		mContents = new ArrayList<>();
		for (Object o : aContents)
		{
			if (o instanceof Collection v)
			{
				mContents.addAll(v);
			}
			else if (o instanceof Content v)
			{
				mContents.add(v);
			}
			else if (o.getClass().isArray())
			{
				for (int i = 0, n = java.lang.reflect.Array.getLength(o); i < n; i++)
				{
					mContents.add((Content)java.lang.reflect.Array.get(o, i));
				}
			}
			else
			{
				throw new IllegalArgumentException(o.getClass().getName());
			}
		}
	}


	public TableRow(ArrayList<Content> aContents)
	{
		this.mContents = aContents;
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
		this.mPadding = aPadding;
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


	public Color getFillColor()
	{
		return mFillColor;
	}


	public TableRow setFillColor(Color aFillColor)
	{
		this.mFillColor = aFillColor;
		return this;
	}
}
