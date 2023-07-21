package org.terifan.pdfwriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.zip.DeflaterInputStream;
import samples.Streams;


public class SVG implements Content
{
	private boolean mConsumed;


	public SVG()
	{
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		try
		{
			ArrayList<String> lines = new ArrayList<>();
			try (LineNumberReader in = new LineNumberReader(new FileReader(new File("d:\\svg.txt"))))
			{
				for (String s; (s = in.readLine()) != null;)
				{
					lines.add(s);
				}
			}

			double x = 0;
			double y = 0;
			double ox = 0;
			double oy = aBoundsTop;
			double sx = 0.5;
			double sy = 0.5;
			double startX = 0;
			double startY = 0;

			String pattern = "%.1f %.1f ";

			outer:
			for (int i = 0; i < lines.size();)
			{
				String command = lines.get(i++);
				if (command.startsWith("//"))
				{
					continue;
				}
				switch (command)
				{
					case "":
						break outer;
					case "rg":
					{
						String[] params = lines.get(i++).split(",");
						double r = Double.parseDouble(params[0]);
						double g = Double.parseDouble(params[1]);
						double b = Double.parseDouble(params[2]);
						aOutput.println("%s rg", new Color(r,g,b));
						break;
					}
					case "m":
						for (int j = 0;; j++)
						{
							if (i >= lines.size() || !lines.get(i).matches("[\\-\\.0-9]{1,}.*"))
							{
								break;
							}
							String[] params = lines.get(i++).split(",");
							x = startX + Double.parseDouble(params[0]);
							y = startY + Double.parseDouble(params[1]);
							aOutput.print(pattern + "m ", ox + x * sx, oy - y * sy);
							if (j == 0)
							{
								startX = x;
								startY = y;
							}
						}
						break;
					case "M":
						for (int j = 0;; j++)
						{
							if (i >= lines.size() || !lines.get(i).matches("[\\-\\.0-9]{1,}.*"))
							{
								break;
							}
							String[] params = lines.get(i++).split(",");
							x = Double.parseDouble(params[0]);
							y = Double.parseDouble(params[1]);
							aOutput.print(pattern + "m ", ox + x * sx, oy - y * sy);
							if (j == 0)
							{
								startX = x;
								startY = y;
							}
						}
						break;
					case "h":
					{
						String[] params = lines.get(i++).split(",");
						x += Double.parseDouble(params[0]);
						aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						break;
					}
					case "H":
					{
						String[] params = lines.get(i++).split(",");
						x = Double.parseDouble(params[0]);
						aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						break;
					}
					case "v":
					{
						String[] params = lines.get(i++).split(",");
						y += Double.parseDouble(params[0]);
						aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						break;
					}
					case "V":
					{
						String[] params = lines.get(i++).split(",");
						y = Double.parseDouble(params[0]);
						aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						break;
					}
					case "cp":
						aOutput.print(pattern + "l ", ox + startX * sx, oy - startY * sy);
						break;
					case "l":
						for (;;)
						{
							if (i >= lines.size() || !lines.get(i).matches("[\\-\\.0-9]{1,}.*"))
							{
								break;
							}
							String[] params = lines.get(i++).split(",");
							x += Double.parseDouble(params[0]);
							y += Double.parseDouble(params[1]);
							aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						}
						break;
					case "L":
						for (;;)
						{
							if (i >= lines.size() || !lines.get(i).matches("[\\-\\.0-9]{1,}.*"))
							{
								break;
							}
							String[] params = lines.get(i++).split(",");
							x = Double.parseDouble(params[0]);
							y = Double.parseDouble(params[1]);
							aOutput.print(pattern + "l ", ox + x * sx, oy - y * sy);
						}
						break;
					case "c":
						for (;;)
						{
							if (i >= lines.size() || !lines.get(i).matches("[\\-\\.0-9]{1,}.*"))
							{
								break;
							}
							String[] params1 = lines.get(i++).split(",");
							String[] params2 = lines.get(i++).split(",");
							String[] params3 = lines.get(i++).split(",");
							double x0 = x + Double.parseDouble(params1[0]);
							double y0 = y + Double.parseDouble(params1[1]);
							double x1 = x + Double.parseDouble(params2[0]);
							double y1 = y + Double.parseDouble(params2[1]);
							double x2 = x + Double.parseDouble(params3[0]);
							double y2 = y + Double.parseDouble(params3[1]);
							aOutput.print(pattern, ox + x0 * sx, oy - y0 * sy);
							aOutput.print(pattern, ox + x1 * sx, oy - y1 * sy);
							aOutput.print(pattern, ox + x2 * sx, oy - y2 * sy);
							aOutput.print("c");
							x = x2;
							y = y2;
						}
						break;
					case "Z":
					case "z":
						aOutput.println("f*");
						x = startX;
						y = startY;
						break;
					default:
						throw new IllegalStateException(command);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}

		mConsumed = true;

		return aBoundsTop - 100;
	}


	@Override
	public double getWidth()
	{
		return 100;
	}


	@Override
	public double getHeight()
	{
		return 100;
	}


	@Override
	public void reuseContent()
	{
		mConsumed = false;
	}


	@Override
	public boolean isConsumed()
	{
		return mConsumed;
	}


	@Override
	public void layout(double aBoundsLeft, double aBoundsRight)
	{
	}
}
