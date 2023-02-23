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


	static void fillRect(Output aContent, double aX0, double aY0, double aX1, double aY1, Color aFillColor, Color aBorderColor) throws IOException
	{
		if (aFillColor != null)
		{
			aContent.println("%s rg", aFillColor);
			aContent.println("%f %f m", aX0, aY0);
			aContent.println("%f %f l", aX1, aY0);
			aContent.println("%f %f l", aX1, aY1);
			aContent.println("%f %f l", aX0, aY1);
			aContent.println("f");
		}
		if (aBorderColor != null)
		{
			aContent.println("%s RG", aBorderColor);
			aContent.println("%f %f m", aX0, aY0);
			aContent.println("%f %f l", aX1, aY0);
			aContent.println("%f %f l", aX1, aY1);
			aContent.println("%f %f l", aX0, aY1);
			aContent.println("s");
		}
	}
}