package org.terifan.pdfwriter;


public class Utilities
{
	public static String roundDouble(double aValue)
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
}