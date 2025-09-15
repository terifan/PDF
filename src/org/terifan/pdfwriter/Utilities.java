package org.terifan.pdfwriter;

import java.io.IOException;
import static org.terifan.pdfwriter.Insets.bottom;
import static org.terifan.pdfwriter.Insets.left;
import static org.terifan.pdfwriter.Insets.right;
import static org.terifan.pdfwriter.Insets.top;


public class Utilities
{
	static String roundDouble(double aValue)
	{
		String s = Double.toString(aValue).replace(",", ".");
		int o = s.indexOf(".");

		if (o == -1)
		{
			return s;
		}

		s = s.substring(0, Math.min(s.length(), o + 7));

		if (s.endsWith(".0"))
		{
			return s.substring(0, s.length() - 2);
		}

		return s;
	}


	static void renderRectangle(Output aFillContent, Output aLineContent, double aX0, double aY0, double aX1, double aY1, Double aRadius, Color aFillColor, Double aThickness, Color aBorderColor) throws IOException
	{
		if (aFillColor == null && aBorderColor == null)
		{
			return;
		}

		Output content = aFillColor != null ? aFillContent : aLineContent;

		if (aFillColor != null)
		{
			content.println("%s rg", aFillColor);
		}
		if (aThickness != null && aBorderColor != null)
		{
			content.println("%s RG", aBorderColor);
			content.println("%f w", aThickness);
		}
		for (int i = 0; i < 2; i++)
		{
			if (i == 0 && aFillColor == null || i == 1 && (aBorderColor == null || aThickness == null))
			{
				continue;
			}
			if (aRadius == null || aRadius == 0)
			{
				content.print("%f %f m ", aX0, aY0);
				content.print("%f %f l ", aX1, aY0);
				content.print("%f %f l ", aX1, aY1);
				content.print("%f %f l ", aX0, aY1);
				content.print("%f %f l ", aX0, aY0);
			}
			else
			{
				content.print("%f %f m ", aX0 + aRadius, aY0);
				content.print("%f %f l ", aX1 - aRadius, aY0);
				content.print("%f %f ", aX1, aY0);
				content.print("%f %f y ", aX1, aY0 - aRadius);
				content.print("%f %f l ", aX1, aY1 + aRadius);
				content.print("%f %f ", aX1, aY1);
				content.print("%f %f y ", aX1 - aRadius, aY1);
				content.print("%f %f l ", aX0 + aRadius, aY1);
				content.print("%f %f ", aX0, aY1);
				content.print("%f %f y ", aX0, aY1 + aRadius);
				content.print("%f %f l ", aX0, aY0 - aRadius);
				content.print("%f %f ", aX0, aY0);
				content.print("%f %f y ", aX0 + aRadius, aY0);
			}
			content.println(i == 0 ? "f" : "s");
		}
	}


	static void renderRectangle(Output aFillContent, Output aLineContent, double aX0, double aY0, double aX1, double aY1, Color aFillColor, Insets aThickness, String aBorderPattern, Color... aBorderColor) throws IOException
	{
		if (aFillColor != null)
		{
			aFillContent.print("q ");
			aFillContent.print("%s rg ", aFillColor);
			aFillContent.print("%f w ", 1.0);
			aFillContent.print("%f %f m ", aX0 + left(aThickness), aY0 - top(aThickness));
			aFillContent.print("%f %f l ", aX1 - right(aThickness), aY0 - top(aThickness));
			aFillContent.print("%f %f l ", aX1 - right(aThickness), aY1 + bottom(aThickness));
			aFillContent.print("%f %f l ", aX0 + left(aThickness), aY1 + bottom(aThickness));
			aFillContent.print("%f %f l ", aX0 + left(aThickness), aY0 - top(aThickness));
			aFillContent.print("f ");
			aFillContent.println("Q");
		}

		if (aBorderPattern != null)
		{
			aLineContent.println("%s d", aBorderPattern);
		}

		if (aThickness != null && aBorderColor != null && aBorderColor.length > 0)
		{
			if (aThickness.top() > 0)
			{
				aLineContent.print("%s RG ", aBorderColor[0]);
				aLineContent.print("%f w ", aThickness.top());
				aLineContent.print("%f %f m ", aX0, aY0 - aThickness.top() / 2);
				aLineContent.print("%f %f l ", aX1, aY0 - aThickness.top() / 2);
				aLineContent.println("s");
			}

			if (aThickness.right() > 0)
			{
				aLineContent.print("%s RG ", aBorderColor[1 % aBorderColor.length]);
				aLineContent.print("%f w ", aThickness.right());
				aLineContent.print("%f %f m ", aX1 - aThickness.right() / 2, aY0);
				aLineContent.print("%f %f l ", aX1 - aThickness.right() / 2, aY1);
				aLineContent.println("s");
			}

			if (aThickness.bottom() > 0)
			{
				aLineContent.print("%s RG ", aBorderColor[2 % aBorderColor.length]);
				aLineContent.print("%f w ", aThickness.bottom());
				aLineContent.print("%f %f m ", aX0, aY1 + aThickness.bottom() / 2);
				aLineContent.print("%f %f l ", aX1, aY1 + aThickness.bottom() / 2);
				aLineContent.println("s");
			}

			if (aThickness.left() > 0)
			{
				aLineContent.print("%s RG ", aBorderColor[3 % aBorderColor.length]);
				aLineContent.print("%f w ", aThickness.left());
				aLineContent.print("%f %f m ", aX0 + aThickness.left() / 2, aY0);
				aLineContent.print("%f %f l ", aX0 + aThickness.left() / 2, aY1);
				aLineContent.println("s");
			}
		}
	}


	static void renderLine(Output aContent, double aX0, double aY0, double aX1, double aY1, Double aThickness, Color aColor) throws IOException
	{
		if (aColor != null)
		{
			aContent.print("%s RG ", aColor);
			if (aThickness != null)
			{
				aContent.print("%f w ", aThickness);
			}
			aContent.print("%f %f m ", aX0, aY0);
			aContent.print("%f %f l ", aX1, aY1);
			aContent.println("s");
		}
	}
}
