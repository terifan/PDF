package samples;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Insets;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Table;
import org.terifan.pdfwriter.TableArea;
import org.terifan.pdfwriter.TableCell;
import org.terifan.pdfwriter.TableRow;


public class TestTable
{
	public static void main(String... args)
	{
		try
		{
			Font[] fonts =
			{
				new ExtendedFont(TestTable.class.getResourceAsStream("resources/Segoeui-Regular.ttf").readAllBytes())
			};
			int[] sizes =
			{
				8, 15, 20
			};
			Color[] textColors =
			{
				new Color(1, 0, 0), new Color(0, 1, 0), new Color(0, 0, 1)
			};
			Color[] boxFillColor =
			{
				new Color(0.5, 0, 0), new Color(0, 0.5, 0), new Color(0, 0, 0.5)
			};
			Color[] boxStrokeColor =
			{
				new Color(0.5, 0.5, 0), new Color(0, 0.5, 0.5), new Color(0.5, 0, 0.5)
			};
			Color[] highlightColors =
			{
				new Color(0.5, 0, 0), new Color(0, 0.5, 0), new Color(0, 0, 0.5)
			};

			ArrayList<String> words = new ArrayList<>();
			words.addAll(Arrays.asList("apple", "banan", "dog", "cat", "table", "chair"));

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:/temp/output.pdf")).setCompress(!true))
			{
				Random rnd = new Random(2);

				Table table = new Table(0.3, 0.5, 0.2)
					.setRepeatHeader(true)
					.setBreakRows(!true)
					.setFillColor(Color.LIGHT_GRAY)
					.setBorderColor(Color.RED)
					.setBorderThickness(new Insets(1, 1, 1, 1));

				table.setHeader(new TableRow(
					new Paragraph(new Style(fonts[0], sizes[1]), "ID"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Description"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Price").setAlignment(Alignment.RIGHT)
				));
				for (int i = 0; i < 100; i++)
				{
					ArrayList<Span> name = new ArrayList<>();
					name.add(new Span(new Style().setFont(fonts[0]), i + " "));

					for (int j = 1 + rnd.nextInt(20); --j >= 0;)
					{
						Style style = new Style()
							.setFont(fonts[0])
							.setSize(sizes[rnd.nextInt(sizes.length)])
							.setTextColor(textColors[rnd.nextInt(textColors.length)]);

						if (rnd.nextBoolean())
						{
							style.setHighlightColor(highlightColors[rnd.nextInt(highlightColors.length)]);
						}
						if (rnd.nextBoolean())
						{
							style.setBorderThickness(Insets.ONE);
							style.setBorderColor(boxStrokeColor[rnd.nextInt(boxStrokeColor.length)]);
						}
						if (rnd.nextBoolean())
						{
							style.setFillColor(boxFillColor[rnd.nextInt(boxFillColor.length)]);
						}

						name.add(new Span(style, i + words.get(rnd.nextInt(words.size())) + "s "));
					}

					TableRow tableRow = new TableRow(
						new TableCell(new Paragraph(new Style(fonts[0], sizes[1]), "product-" + i))
							.setFillColor(new Color(100,150,255))
							.setBorder(Color.YELLOW, new Insets(1,1,1,1)),
						new TableCell(new Paragraph(name))
							.setBorder(Color.YELLOW, new Insets(1,1,1,1)),
						new TableCell(new Paragraph(new Style(fonts[0], sizes[1]), String.format("%.2f", rnd.nextDouble() * 100)).setAlignment(Alignment.RIGHT))
							.setBorder(Color.YELLOW, new Insets(1,1,1,1))
					).setBorder(Color.GREEN, new Insets(1, 1, 1, 1));
					if (i == 1)
					{
						tableRow.setFillColor(new Color(255,100,255));
					}

					table.add(tableRow);
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TableArea(70, 780, 530, 420, table));
					page.append(new TableArea(70, 400, 530, 70, table));
				}

				while (!table.isConsumed())
				{
					try (Page page = pdf.addPage())
					{
						page.append(new TableArea(70, 780, 530, 70, table).setBackgroundColor(Color.LIGHT_GRAY));
					}
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
