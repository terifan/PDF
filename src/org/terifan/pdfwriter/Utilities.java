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
			if (aRadius == null)
			{
				content.println("%f %f m", aX0, aY0);
				content.println("%f %f l", aX1, aY0);
				content.println("%f %f l", aX1, aY1);
				content.println("%f %f l", aX0, aY1);
				content.println("%f %f l", aX0, aY0);
			}
			else
			{
				content.println("%f %f m", aX0 + aRadius, aY0);
				content.println("%f %f l", aX1 - aRadius, aY0);
				content.println("%f %f", aX1, aY0);
				content.println("%f %f y", aX1, aY0 - aRadius);
				content.println("%f %f l", aX1, aY1 + aRadius);
				content.println("%f %f", aX1, aY1);
				content.println("%f %f y", aX1 - aRadius, aY1);
				content.println("%f %f l", aX0 + aRadius, aY1);
				content.println("%f %f", aX0, aY1);
				content.println("%f %f y", aX0, aY1 + aRadius);
				content.println("%f %f l", aX0, aY0 - aRadius);
				content.println("%f %f", aX0, aY0);
				content.println("%f %f y", aX0 + aRadius, aY0);
			}
			content.println(i == 0 ? "f" : "s");
		}
	}


	static void renderRectangle(Output aFillContent, Output aLineContent, double aX0, double aY0, double aX1, double aY1, Color aFillColor, Insets aThickness, String aBorderPattern, Color... aBorderColor) throws IOException
	{
		aFillContent.println("q");

		if (aFillColor != null)
		{
			aFillContent.println("%s rg", aFillColor);
			aFillContent.println("%f w", 1.0);
			aFillContent.println("%f %f m", aX0 + left(aThickness), aY0 - top(aThickness));
			aFillContent.println("%f %f l", aX1 - right(aThickness), aY0 - top(aThickness));
			aFillContent.println("%f %f l", aX1 - right(aThickness), aY1 + bottom(aThickness));
			aFillContent.println("%f %f l", aX0 + left(aThickness), aY1 + bottom(aThickness));
			aFillContent.println("%f %f l", aX0 + left(aThickness), aY0 - top(aThickness));
			aFillContent.println("f");
		}

		if (aBorderPattern != null)
		{
			aLineContent.println("%s d", aBorderPattern);
		}

		if (aThickness != null && aBorderColor != null && aBorderColor.length > 0)
		{
			if (aThickness.top() > 0)
			{
				aLineContent.println("%s RG", aBorderColor[0]);
				aLineContent.println("%f w", aThickness.top());
				aLineContent.println("%f %f m", aX0, aY0 - aThickness.top() / 2);
				aLineContent.println("%f %f l", aX1, aY0 - aThickness.top() / 2);
				aLineContent.println("s");
			}

			if (aThickness.right() > 0)
			{
				aLineContent.println("%s RG", aBorderColor[1 % aBorderColor.length]);
				aLineContent.println("%f w", aThickness.right());
				aLineContent.println("%f %f m", aX1 - aThickness.right() / 2, aY0);
				aLineContent.println("%f %f l", aX1 - aThickness.right() / 2, aY1);
				aLineContent.println("s");
			}

			if (aThickness.bottom() > 0)
			{
				aLineContent.println("%s RG", aBorderColor[2 % aBorderColor.length]);
				aLineContent.println("%f w", aThickness.bottom());
				aLineContent.println("%f %f m", aX0, aY1 + aThickness.bottom() / 2);
				aLineContent.println("%f %f l", aX1, aY1 + aThickness.bottom() / 2);
				aLineContent.println("s");
			}

			if (aThickness.left() > 0)
			{
				aLineContent.println("%s RG", aBorderColor[3 % aBorderColor.length]);
				aLineContent.println("%f w", aThickness.left());
				aLineContent.println("%f %f m", aX0 + aThickness.left() / 2, aY0);
				aLineContent.println("%f %f l", aX0 + aThickness.left() / 2, aY1);
				aLineContent.println("s");
			}
		}

		aFillContent.println("Q");
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
