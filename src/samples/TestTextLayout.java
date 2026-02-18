package samples;

import java.io.FileOutputStream;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Anchor;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.TextArea;


public class TestTextLayout
{
	public static void main(String... args)
	{
		try
		{
			Font font1 = new ExtendedFont(TestTextLayout.class.getResourceAsStream("resources/Segoeui-Regular.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:\\temp\\output.pdf")).setCompress(!true))
			{
				Style style0 = new Style(font1, 15);
				Style[] style1 = {
					new Style(font1, 12).setFillColor(Color.YELLOW),
					new Style(font1, 12).setFillColor(Color.YELLOW).setLineExtra(4),
					new Style(font1, 12).setFillColor(Color.YELLOW).setLineExtra(8)
				};
				Style style2 = new Style(font1, 10).setFillColor(Color.RED);
				Style style3 = new Style(font1, 8).setTextColor(Color.GRAY);
				Style style4 = new Style(font1, 20).setFillColor(Color.CYAN).setTextColor(Color.BLUE);
				Style style5 = new Style(font1, 8).setFillColor(Color.GREEN);

				Alignment[] al = Alignment.values();
				Anchor[] an = Anchor.values();

//				try (Page page = pdf.addPage())
//				{
//					page.append(new TextArea( 70, 790, 530, 750, new Paragraph(style0, "TextArea anchor")).setAnchor(Anchor.NORTH));
//					for (int y = 290, i = 0; y < 800; y+=230)
//					{
//						for (int x = 70; x < 385; x+=150, i++)
//						{
//							page.append(
//								new TextArea(x, y, x+140, y-200,
//									new Paragraph(style1, "para1"),
//									new Paragraph(style1, "para2").add(new Span(style5,"span2")).add(new Span(style4,"span3")).add(new Span(style2,"span4ohhhhwrap!!")),
//									new Paragraph(style1, "para3")
//								)
//								.setBackground(Color.LIGHT_GRAY)
//								.setAnchor(an[i]));
//							page.append(new TextArea(x, y+10, x+140, y-220, new Paragraph(style3, ""+an[i])).setAnchor(Anchor.NORTH_WEST));
//						}
//					}
//				}
				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(70, 790, 530, 750, new Paragraph(style0, "TextArea anchor + Paragraph alignment")).setAnchor(Anchor.NORTH));

					for (int y = 290, r = 0, i = 0; y < 800; y += 230, r++)
					{
						for (int x = 70, ix=0; x < 385; x += 150, i++, ix++)
						{
							Style _style1 = style1[ix];
							page.append(
								new TextArea(x, y, x + 140, y - 200,
									new Paragraph(_style1, "[pÄra1|").setAlignment(al[r]),
									new Paragraph(_style1, "[pÄra2|").add(new Span(style5, "spÄn2")).add(new Span(style4, "spÄn3")).add(new Span(style2, "span4ohhhhwrap!!")).setAlignment(al[r]),
									new Paragraph(_style1, "[pÄra3|").setAlignment(al[r])
								)
									.setBackground(Color.LIGHT_GRAY)
									.setAnchor(an[i]));
							page.append(new TextArea(x, y + 10, x + 140, y - 220, new Paragraph(style3, an[i] + " + " + al[r])).setAnchor(Anchor.NORTH_WEST));
						}
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
