package samples;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
			Font font1 = new ExtendedFont(TestTable.class.getResourceAsStream("verdana.ttf").readAllBytes());

			Style[] styles = {
				new Style(font1, 7),
				new Style(font1, 12),
				new Style(font1, 20)
			};

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\output.pdf")).setCompress(true))
			{
				Random rnd = new Random(1);

				Table table = new Table(true, 0.3, 0.5, 0.2);
				table.setHeader(Arrays.asList(
					new Paragraph(styles[1], "ID"),
					new Paragraph(styles[1], "Description"),
					new Paragraph(styles[1], "Price")
				));
				for (int i = 0; i < 25; i++)
				{
					ArrayList<Span> name = new ArrayList<>();
					for (int j = rnd.nextInt(200); --j >= 0;)
					{
						String s = "";
						for (int k = 3+rnd.nextInt(10); --k >= 0;)
						{
							s += (char)('a' + rnd.nextInt(26));
						}
						name.add(new Span(styles[rnd.nextInt(styles.length)], s + " "));
					}

					table.addRow(Arrays.asList(
						new Paragraph(styles[1], "product-" + i),
						new Paragraph(name),
						new Paragraph(styles[1], String.format("%5.2f", rnd.nextDouble()*100))
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
