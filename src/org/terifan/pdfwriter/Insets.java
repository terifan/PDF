package org.terifan.pdfwriter;


public class Insets implements Cloneable
{
	private Double top, left, bottom, right;

	public final static Insets ZERO = new Insets()
	{
		@Override
		public double bottom()
		{
			return 0;
		}


		@Override
		public double top()
		{
			return 0;
		}


		@Override
		public double right()
		{
			return 0;
		}


		@Override
		public double left()
		{
			return 0;
		}
	};

	public final static Insets ONE = new Insets()
	{
		@Override
		public double bottom()
		{
			return 1;
		}


		@Override
		public double top()
		{
			return 1;
		}


		@Override
		public double right()
		{
			return 1;
		}


		@Override
		public double left()
		{
			return 1;
		}
	};


	public Insets()
	{
	}


	public Insets(Insets aOther)
	{
		set(aOther);
	}


	public Insets(double aTop, double aLeft, double aBottom, double aRight)
	{
		top = aTop;
		left = aLeft;
		bottom = aBottom;
		right = aRight;
	}


	public Insets(Double aTop, Double aLeft, Double aBottom, Double aRight)
	{
		top = aTop;
		left = aLeft;
		bottom = aBottom;
		right = aRight;
	}


	/**
	 * Null safe method to update this Insets with values in the provided Insets.
	 *
	 * @param aOther source Insets or null.
	 */
	public void set(Insets aOther)
	{
		if (aOther!=null)
		{
			left = aOther.left;
			right = aOther.right;
			top = aOther.top;
			bottom = aOther.bottom;
		}
	}


	public Insets setTop(Double aTop)
	{
		top = aTop;
		return this;
	}


	public Insets setLeft(Double aLeft)
	{
		left = aLeft;
		return this;
	}


	public Insets setBottom(Double aBottom)
	{
		bottom = aBottom;
		return this;
	}


	public Insets setRight(Double aRight)
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


	public static double left(Insets aInsets)
	{
		return aInsets == null || aInsets.left == null ? 0 : aInsets.left;
	}


	public static double right(Insets aInsets)
	{
		return aInsets == null || aInsets.right == null ? 0 : aInsets.right;
	}


	public static double top(Insets aInsets)
	{
		return aInsets == null || aInsets.top == null ? 0 : aInsets.top;
	}


	public static double bottom(Insets aInsets)
	{
		return aInsets == null || aInsets.bottom == null ? 0 : aInsets.bottom;
	}


	public static Insets first(Insets... aInsets)
	{
		Insets result = new Insets();
		for (Insets i : aInsets)
		{
			if (i != null)
			{
				if (i.left != null && result.left == null)
				{
					result.left = i.left;
				}
				if (i.right != null && result.right == null)
				{
					result.right = i.right;
				}
				if (i.top != null && result.top == null)
				{
					result.top = i.top;
				}
				if (i.bottom != null && result.bottom == null)
				{
					result.bottom = i.bottom;
				}
			}
		}
		return result;
	}


	public static Insets add(Insets... aInsets)
	{
		Insets result = new Insets(0, 0, 0, 0);
		for (Insets i : aInsets)
		{
			if (i != null)
			{
				result.left += i.left();
				result.right += i.right();
				result.top += i.top();
				result.bottom += i.bottom();
			}
		}
		return result;
	}


	@Override
	public Insets clone()
	{
		try
		{
			return (Insets)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return new Insets(top, left, bottom, right);
		}
	}


	@Override
	public String toString()
	{
		return "Margins{" + "top=" + top + ", left=" + left + ", bottom=" + bottom + ", right=" + right + '}';
	}
}
