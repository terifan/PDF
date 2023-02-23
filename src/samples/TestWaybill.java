package samples;

import java.io.FileOutputStream;
import java.util.Arrays;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Line;
import org.terifan.pdfwriter.Margins;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Rectangle;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Table;
import org.terifan.pdfwriter.TableArea;
import org.terifan.pdfwriter.TextArea;


public class TestWaybill
{
	public static void main(String... args)
	{
		try
		{
			Font font1 = new ExtendedFont(TestWaybill.class.getResourceAsStream("ubuntu.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\output.pdf")).setCompress(!true))
			{
				Style style1 = new Style(font1, 7);
				Style style2 = new Style(font1, 12);

				Margins margins = new Margins(2,2,2,2);

				Table table = new Table(0.2, 0.4, 0.1, 0.1, 0.1, 0.1)
					.setRepeatHeader(true)
					.setBreakRows(!true)
					.setVerticalGridColor(Color.BLACK)
					.setVerticalGridThickness(0.5)
					.setHeaderGridThickness(1.0)
					.setHeaderGridColor(Color.BLACK)
					;

				table.setHeader(Arrays.asList(new Paragraph(style1, "Marks and numbers"),
					new Paragraph(style1, "Goods description / reference").setMargins(margins),
					new Paragraph(new Span(style1, "Qty."), new Span(style1, "Pkg.")).setAlignment(Alignment.SPLIT).setMargins(margins),
					new Paragraph(style1, "Gross weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins),
					new Paragraph(style1, "Volume, m³").setAlignment(Alignment.RIGHT).setMargins(margins),
					new Paragraph(style1, "LDM").setAlignment(Alignment.RIGHT).setMargins(margins)
				));

				for (int i = 0; i < 10; i++)
				{
					table.addRow(Arrays.asList(new Paragraph(style1, "CPH5002074").setMargins(margins),
						new Paragraph(style1, "50548 qwerty qwerty qwerty qwerty qwerty").setMargins(margins),
						new Paragraph(new Span(style1, "1"), new Span(style1, "XP")).setAlignment(Alignment.SPLIT).setMargins(margins),
						new Paragraph(style1, "6.00").setAlignment(Alignment.RIGHT).setMargins(margins),
						new Paragraph(style1, "0.04").setAlignment(Alignment.RIGHT).setMargins(margins),
						new Paragraph(style1, "-").setAlignment(Alignment.RIGHT).setMargins(margins)
					));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(780, 70, 500, 550, new Paragraph(style1, "Shipper:"), new Paragraph(style2, "test").setMargins(new Margins(0, 4, 0, 0)), new Paragraph(style2, "test").setMargins(new Margins(0, 4, 0, 0))));

					page.append(new Rectangle(780, 70, 70, 530, 0.5, 10, Color.BLACK, null));

					page.append(new Line(780, 70, 70, 530, 0.5, Color.BLACK));

					page.append(new TableArea(500, 70, 200, 530, table));
				}

				while (!table.isConsumed())
				{
					try (Page page = pdf.addPage())
					{
						page.append(new TableArea(780, 70, 70, 530, table).setBackgroundColor(Color.LIGHT_GRAY));
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
