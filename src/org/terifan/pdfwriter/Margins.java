package org.terifan.pdfwriter;


public class Margins implements Cloneable
{
	private Double top, left, bottom, right;


	public Margins()
	{
	}


	public Margins(double aTop, double aLeft, double aBottom, double aRight)
	{
		top = aTop;
		left = aLeft;
		bottom = aBottom;
		right = aRight;
	}


	public Margins setTop(Double aTop)
	{
		top = aTop;
		return this;
	}


	public Margins setLeft(Double aLeft)
	{
		left = aLeft;
		return this;
	}


	public Margins setBottom(Double aBottom)
	{
		bottom = aBottom;
		return this;
	}


	public Margins setRight(Double aRight)
	{
		right = aRight;
		return this;
	}


	public double left()
	{
		return left == null ? 0 : left;
	}


	public double right()
	{
		return right == null ? 0 : right;
	}


	public double top()
	{
		return top == null ? 0 : top;
	}


	public double bottom()
	{
		return bottom == null ? 0 : bottom;
	}


	public double left(Margins aOther)
	{
		return left == null ? aOther == null ? 0 : aOther.left(null) : left;
	}


	public double right(Margins aOther)
	{
		return right == null ? aOther == null ? 0 : aOther.right(null) : right;
	}


	public double top(Margins aOther)
	{
		return top == null ? aOther == null ? 0 : aOther.top(null) : top;
	}


	public double bottom(Margins aOther)
	{
		return bottom == null ? aOther == null ? 0 : aOther.bottom(null) : bottom;
	}


	@Override
	public Margins clone()
	{
		try
		{
			return (Margins)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return new Margins(top, left, bottom, right);
		}
	}
}
