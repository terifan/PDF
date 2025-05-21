package org.terifan.pdfwriter;

import java.io.IOException;


public class Utilities
{
	static String roundDouble(double aValue)
	{
		String s = "" + ((int)(aValue * 1000) / 1000.0);

		int o = s.indexOf(".");

		if (o == -1)
		{
			return s;
		}

		s = s.substring(0, o + 1) + s.substring(o + 1, o + Math.min(s.length() - o, 4));

		if (s.endsWith(".0"))
		{
			return s.substring(0, s.length() - 2);
		}

		return s;
	}


	static void renderRectangle(Output aContent, double aX0, double aY0, double aX1, double aY1, Double aStrokeThickness, Double aRadius, Color aFillColor, Color aBorderColor) throws IOException
	{
		if (aFillColor == null && aBorderColor == null)
		{
			return;
		}

		if (aFillColor != null)
		{
			aContent.println("%s rg", aFillColor);
		}
		if (aBorderColor != null)
		{
			aContent.println("%s RG", aBorderColor);
		}
		if (aStrokeThickness != null)
		{
			aContent.println("%f w", aStrokeThickness);
		}
		for (int i = 0; i < 2; i++)
		{
			if (i == 0 && aFillColor == null || i == 1 && aBorderColor == null)
			{
				continue;
			}
			if (aRadius == null)
			{
				aContent.println("%f %f m", aX0, aY0);
				aContent.println("%f %f l", aX1, aY0);
				aContent.println("%f %f l", aX1, aY1);
				aContent.println("%f %f l", aX0, aY1);
				aContent.println("%f %f l", aX0, aY0);
			}
			else
			{
				aContent.println("%f %f m", aX0 + aRadius, aY0);
				aContent.println("%f %f l", aX1 - aRadius, aY0);
				aContent.println("%f %f", aX1, aY0);
				aContent.println("%f %f y", aX1, aY0 - aRadius);
				aContent.println("%f %f l", aX1, aY1 + aRadius);
				aContent.println("%f %f", aX1, aY1);
				aContent.println("%f %f y", aX1 - aRadius, aY1);
				aContent.println("%f %f l", aX0 + aRadius, aY1);
				aContent.println("%f %f", aX0, aY1);
				aContent.println("%f %f y", aX0, aY1 + aRadius);
				aContent.println("%f %f l", aX0, aY0 - aRadius);
				aContent.println("%f %f", aX0, aY0);
				aContent.println("%f %f y", aX0 + aRadius, aY0);
			}
			aContent.println(i == 0 ? "f" : "s");
		}
	}


	static void renderRectangle(Output aFillContent, Output aBorderContent, double aX0, double aY0, double aX1, double aY1, Color aFillColor, Insets aThickness, Color... aBorderColor) throws IOException
	{
		if (aFillColor != null)
		{
			aFillContent.println("%s rg", aFillColor);
			aFillContent.println("%f w", 1.0);
			aFillContent.println("%f %f m", aX0 + aThickness.left(), aY0 - aThickness.top());
			aFillContent.println("%f %f l", aX1 - aThickness.right(), aY0 - aThickness.top());
			aFillContent.println("%f %f l", aX1 - aThickness.right(), aY1 + aThickness.bottom());
			aFillContent.println("%f %f l", aX0 + aThickness.left(), aY1 + aThickness.bottom());
			aFillContent.println("%f %f l", aX0 + aThickness.left(), aY0 - aThickness.top());
			aFillContent.println("f");
		}

		if (aThickness != null && aBorderColor != null && aBorderColor.length > 0)
		{
			if (aThickness.top() > 0)
			{
				aBorderContent.println("%s RG", aBorderColor[0]);
				aBorderContent.println("%f w", aThickness.top());
				aBorderContent.println("%f %f m", aX0, aY0 - aThickness.top() / 2);
				aBorderContent.println("%f %f l", aX1, aY0 - aThickness.top() / 2);
				aBorderContent.println("s");
			}

			if (aThickness.right() > 0)
			{
				aBorderContent.println("%s RG", aBorderColor[1 % aBorderColor.length]);
				aBorderContent.println("%f w", aThickness.right());
				aBorderContent.println("%f %f m", aX1 - aThickness.right() / 2, aY0);
				aBorderContent.println("%f %f l", aX1 - aThickness.right() / 2, aY1);
				aBorderContent.println("s");
			}

			if (aThickness.bottom() > 0)
			{
				aBorderContent.println("%s RG", aBorderColor[2 % aBorderColor.length]);
				aBorderContent.println("%f w", aThickness.bottom());
				aBorderContent.println("%f %f m", aX0, aY1 + aThickness.bottom() / 2);
				aBorderContent.println("%f %f l", aX1, aY1 + aThickness.bottom() / 2);
				aBorderContent.println("s");
			}

			if (aThickness.left() > 0)
			{
				aBorderContent.println("%s RG", aBorderColor[3 % aBorderColor.length]);
				aBorderContent.println("%f w", aThickness.left());
				aBorderContent.println("%f %f m", aX0 + aThickness.left() / 2, aY0);
				aBorderContent.println("%f %f l", aX0 + aThickness.left() / 2, aY1);
				aBorderContent.println("s");
			}
		}
	}


	static void renderLine(Output aContent, double aX0, double aY0, double aX1, double aY1, Double aThickness, Color aColor) throws IOException
	{
		if (aColor != null)
		{
			aContent.println("%s RG", aColor);
			if (aThickness != null)
			{
				aContent.println("%f w", aThickness);
			}
			aContent.println("%f %f m", aX0, aY0);
			aContent.println("%f %f l", aX1, aY1);
			aContent.println("s");
		}
	}
}
