package org.terifan.pdfwriter;


public class Color
{
	public final static Color BLACK = new Color(0,0,0);
	public final static Color LIGHT_GRAY = new Color(0.75,0.75,0.75);
	public final static Color GRAY = new Color(0.5,0.5,0.5);
	public final static Color DARK_GRAY = new Color(0.25,0.25,0.25);
	public final static Color WHITE = new Color(1,1,1);
	public final static Color BLUE = new Color(0,0,1);
	public final static Color DARK_BLUE = new Color(0,0,0.5);
	public final static Color CYAN = new Color(0,1,1);
	public final static Color DARK_CYAN = new Color(0,0.5,0.5);
	public final static Color GREEN = new Color(0,1,0);
	public final static Color DARK_GREEN = new Color(0,0.5,0);
	public final static Color MAGENTA = new Color(1,0,1);
	public final static Color DARK_MAGENTA = new Color(0.5,0,0.5);
	public final static Color RED = new Color(1,0,0);
	public final static Color DARK_RED = new Color(0.5,0,0);
	public final static Color YELLOW = new Color(1,1,0);
	public final static Color DARK_YELLOW = new Color(0.5,0.5,0);

	private double r, g, b;

	public Color(double r, double g, double b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public String toString()
	{
		return Utilities.roundDouble(r) + " " + Utilities.roundDouble(g) + " " + Utilities.roundDouble(b);
	}

	@Override
	public Color clone()
	{
		return new Color(r, g, b);
	}
}