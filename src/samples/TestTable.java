package samples;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Table;
import org.terifan.pdfwriter.TableArea;


public class TestTable
{
	public static void main(String... args)
	{
		try
		{
			Font[] fonts = {new ExtendedFont(TestTable.class.getResourceAsStream("verdana.ttf").readAllBytes())};
			int[] sizes = {8,15,20};
			Color[] textColors = {new Color(1,0,0), new Color(0,1,0), new Color(0,0,1)};
			Color[] boxFillColor = {new Color(0.5,0,0), new Color(0,0.5,0), new Color(0,0,0.5)};
			Color[] boxStrokeColor = {new Color(0.5,0.5,0), new Color(0,0.5,0.5), new Color(0.5,0,0.5)};
			Color[] highlightColors = {new Color(0.5,0,0), new Color(0,0.5,0), new Color(0,0,0.5)};

			ArrayList<String> words = new ArrayList<>();
			try (LineNumberReader in = new LineNumberReader(new FileReader("D:\\Documents\\words.txt")))
			{
				for (String s; (s = in.readLine()) != null;)
				{
					words.add(s);
				}
			}

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\output.pdf")).setCompress(!true))
			{
				Random rnd = new Random(2);

				Table table = new Table(0.3, 0.5, 0.2)
					.setRepeatHeader(true)
					.setBreakRows(!true);

				table.setHeader(Arrays.asList(
					new Paragraph(new Style(fonts[0], sizes[1]), "ID"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Description"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Price")
				));
				for (int i = 0; i < 10; i++)
				{
					ArrayList<Span> name = new ArrayList<>();
					name.add(new Span(new Style().setFont(fonts[0]), i + " "));

					for (int j = 1 + rnd.nextInt(20); --j >= 0;)
					{
						Style style = new Style()
							.setFont(fonts[0])
							.setSize(sizes[rnd.nextInt(sizes.length)])
							.setTextColor(textColors[rnd.nextInt(textColors.length)]);

						if (rnd.nextBoolean()) style.setHighlightColor(highlightColors[rnd.nextInt(highlightColors.length)]);
						if (rnd.nextBoolean()) style.setBoxStrokeColor(boxStrokeColor[rnd.nextInt(boxStrokeColor.length)]);
						if (rnd.nextBoolean()) style.setBoxFillColor(boxFillColor[rnd.nextInt(boxFillColor.length)]);

						name.add(new Span(style, i+words.get(rnd.nextInt(words.size())) + " "));
					}

					table.addRow(Arrays.asList(
						new Paragraph(new Style(fonts[0], sizes[1]), "product-" + i),
						new Paragraph(name).setStrokeColor(Color.RED),
						new Paragraph(new Style(fonts[0], sizes[1]), String.format("%5.2f", rnd.nextDouble()*100)).setAlignment(Alignment.RIGHT)
					));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TableArea(780, 70, 470, 530, table));
					page.append(new TableArea(450, 70, 70, 530, table));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TableArea(780, 70, 70, 530, table));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
