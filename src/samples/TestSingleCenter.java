package samples;

import java.io.FileOutputStream;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Anchor;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Dictionary;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.TextArea;


public class TestSingleCenter
{
	public static void main(String... args)
	{
		try
		{
			Font font = new ExtendedFont(TestMultipleFonts.class.getResourceAsStream("resources/VendSans-Regular.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:\\temp\\output.pdf")))
			{
				try (Page page = pdf.addPage())
				{
					Style style = new Style(font, 60).setHighlightColor(Color.YELLOW).setExtGState(new Dictionary().put("/ca",0.25));

					Paragraph p1 = new Paragraph();
					p1.setAlignment(Alignment.CENTER);
					p1.add(style, "ONLY FOR DEMONSTRATION PURPOSES");

					Paragraph p2 = new Paragraph();
					p2.setAlignment(Alignment.CENTER);
					p2.add(style, "ONLY FOR DEMONSTRATION PURPOSES");

					Paragraph p3 = new Paragraph();
					p3.setAlignment(Alignment.CENTER);
					p3.add(style, "ONLY FOR DEMONSTRATION PURPOSES");

					int h = page.getDimension().height;
					page.append(new TextArea(0, h, page.getDimension().width, h/2, p1).setAnchor(Anchor.WEST));
					page.append(new TextArea(0, h, page.getDimension().width, 0, p2).setAnchor(Anchor.CENTER));
					page.append(new TextArea(0, h/2, page.getDimension().width, 0, p3).setAnchor(Anchor.EAST));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
