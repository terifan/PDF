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

		s = s.substring(0, o+1) + s.substring(o+1, o+Math.min(s.length()-o,4));

		if (s.endsWith(".0"))
		{
			return s.substring(0, s.length()-2);
		}

		return s;
	}


	static void renderRectangle(Output aContent, double aX0, double aY0, double aX1, double aY1, Double aStrokeThickness, Double aRadius, Color aFillColor, Color aBorderColor) throws IOException
	{
		aContent.println("%s rg", aFillColor);
		aContent.println("%s RG", aBorderColor);
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