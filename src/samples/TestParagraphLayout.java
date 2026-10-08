package samples;

import java.io.FileOutputStream;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Insets;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.TextArea;


public class TestParagraphLayout
{
	public static void main(String... args)
	{
		try
		{
			Font font1 = new ExtendedFont(TestMultipleFonts.class.getResourceAsStream("resources/VendSans-Regular.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:\\temp\\output.pdf")).setCompress(false))
			{
				Style[] style =
				{
					new Style(font1, 10).setHighlightColor(Color.YELLOW), //.setBorder(Color.RED, new Insets(1, 1, 1, 1)),
					new Style(font1, 20).setHighlightColor(Color.YELLOW), //.setBorder(Color.RED, new Insets(1, 1, 1, 1)),
					new Style(font1, 16).setHighlightColor(Color.YELLOW), //.setBorder(Color.RED, new Insets(1, 1, 1, 1))
				};

				try (Page page = pdf.addPage())
				{
					String[] english = "MDS consists of workers with different responsibilities running in parallel on three Windows machines hosted on Azure, and one on Surikat. Workers communicate with each other via web services hosted on Azure.".split(" ");

					int w = PDFWriter.A4_Portrait.width - 5;

					Paragraph[] parapgraphs =
					{
						new Paragraph(), new Paragraph(), new Paragraph(), new Paragraph()
					};

					for (Paragraph p : parapgraphs)
					{
						int s = 0;
						for (String text : english)
						{
							p.add(new Span(style[s++ % style.length], text + " "));
						}
					}

					page.append(new TextArea(5, 790, w, 700, parapgraphs[0].setAlignment(Alignment.LEFT)).setBackground(Color.CYAN));
					page.append(new TextArea(5, 690, w, 600, parapgraphs[1].setAlignment(Alignment.CENTER)).setBackground(Color.CYAN));
					page.append(new TextArea(5, 590, w, 500, parapgraphs[2].setAlignment(Alignment.RIGHT)).setBackground(Color.CYAN));
					page.append(new TextArea(5, 490, w, 400, parapgraphs[3].setAlignment(Alignment.JUSTIFY)).setBackground(Color.CYAN));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
