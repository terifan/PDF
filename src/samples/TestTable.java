package samples;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.InflaterInputStream;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Insets;
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
//	public static void main(String ... args)
//	{
//		try
//		{
//			byte[] data = Files.readAllBytes(Paths.get("C:\\Users\\patrik\\Desktop\\Untitled document.pdf"));
//			InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(data,197,315));
//			System.out.println(new String(in.readAllBytes()));
//		}
//		catch (Throwable e)
//		{
//			e.printStackTrace(System.out);
//		}
//	}
	public static void main(String... args)
	{
		try
		{
			Font[] fonts =
			{
				new ExtendedFont(TestTable.class.getResourceAsStream("resources/VendSans-Regular.ttf").readAllBytes())
			};
			int[] sizes =
			{
				6, 12, 24
			};
			Color[] textColors =
			{
				new Color(0, 0, 0.2), new Color(0, 0, 0.3), new Color(0, 0, 0.4), new Color(0, 0, 0.5),
				new Color(0, 0, 0.6), new Color(0, 0, 0.7), new Color(0, 0, 0.8), new Color(0, 0, 0.9)
			};
			Color[] boxFillColor =
			{
				new Color(0, 0.2, 0), new Color(0, 0.3, 0), new Color(0, 0.4, 0), new Color(0, 0.5, 0),
				new Color(0, 0.6, 0), new Color(0, 0.7, 0), new Color(0, 0.8, 0), new Color(0, 0.9, 0)
			};
			Color[] boxStrokeColor =
			{
				new Color(0.5, 0.5, 0), new Color(0, 0.5, 0.5), new Color(0.5, 0, 0.5)
			};
			Color[] highlightColors =
			{
				new Color(0.2, 0, 0), new Color(0.3, 0, 0), new Color(0.4, 0, 0), new Color(0.5, 0, 0),
				new Color(0.6, 0, 0), new Color(0.7, 0, 0), new Color(0.8, 0, 0), new Color(0.9, 0, 0)
			};

			ArrayList<String> words = new ArrayList<>();
			words.addAll(Arrays.asList("apple", "banan", "dog", "cat", "table", "chair"));

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:/temp/output.pdf")).setCompress(!true))
			{
				Random rnd = new Random(2);

				Table table = new Table(0.3, 0.5, 0.2)
					.setRepeatHeader(true)
					.setBreakRows(false)
					.setFillColor(Color.LIGHT_GRAY)
					.setBorder(Color.RED, new Insets(1, 1, 1, 1));

				table.setHeader(new TableRow(
					new Paragraph(new Style(fonts[0], sizes[1]), "ID"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Description"),
					new Paragraph(new Style(fonts[0], sizes[1]), "Price").setAlignment(Alignment.RIGHT)
				));

				Style style0 = new Style().setFont(fonts[0]).setSize(12).setTextColor(Color.BLACK);
				table.add(new TableRow(new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test")))).setBorder(Color.BLUE, new Insets(1, 0, 0, 0)));
				table.add(new TableRow(new TableCell(new Paragraph(new Span(style0, "0test 1test 2test 3test 4test 5test 6test"))), new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test")))).setBorder(Color.BLUE, new Insets(1, 0, 0, 0)));
				table.add(new TableRow(new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test")))).setBorder(Color.BLUE, new Insets(1, 0, 0, 0)));
				table.add(new TableRow(new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test"))), new TableCell(new Paragraph(new Span(style0, "test")))).setBorder(Color.BLUE, new Insets(1, 0, 0, 0)));

				for (int i = 0; i < 4; i++)
				{
					ArrayList<Span> spans = new ArrayList<>();
					spans.add(new Span(new Style().setFont(fonts[0]), "" + i));

					for (int j = 0, n = 1 + rnd.nextInt(20); j < n; j++)
					{
						Style style = new Style()
							.setFont(fonts[0])
							.setSize(sizes[rnd.nextInt(sizes.length)])
							.setTextColor(textColors[rnd.nextInt(textColors.length)]);

						if (rnd.nextBoolean())
						{
							style.setFont(fonts[rnd.nextInt(fonts.length)]);
						}
						if (rnd.nextBoolean())
						{
							style.setHighlightColor(highlightColors[rnd.nextInt(highlightColors.length)]);
						}
						if (rnd.nextBoolean())
						{
							style.setBorder(boxStrokeColor[rnd.nextInt(boxStrokeColor.length)], new Insets(3, 3, 3, 3));
						}
						if (rnd.nextBoolean())
						{
							style.setFillColor(boxFillColor[rnd.nextInt(boxFillColor.length)]);
						}

						spans.add(new Span(style, i + ":" + j + words.get(rnd.nextInt(words.size()))));
					}

					TableRow tableRow = new TableRow(
						new TableCell(new Paragraph(new Style(fonts[0], sizes[1]), "product-" + i))
							.setFillColor(new Color(100, 150, 255))
							.setBorder(Color.YELLOW, new Insets(1, 1, 1, 1)),
						new TableCell(new Paragraph(spans))
							.setBorder(Color.YELLOW, new Insets(1, 1, 1, 1)),
						new TableCell(new Paragraph(new Style(fonts[0], sizes[1]), String.format("%.2f", rnd.nextDouble() * 100)).setAlignment(Alignment.RIGHT))
							.setBorder(Color.YELLOW, new Insets(1, 1, 1, 1))
					).setBorder(Color.GREEN, new Insets(1, 1, 1, 1));

					if (i == 1)
					{
						tableRow.setFillColor(new Color(255, 100, 255));
					}

					table.add(tableRow);
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TableArea(70, 780, 530, 420, table).setBackgroundColor(Color.CYAN));
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
