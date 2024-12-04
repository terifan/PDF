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
			Font font1 = new ExtendedFont(TestTextLayout.class.getResourceAsStream("opensans.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("c:\\temp\\output.pdf")).setCompress(!true))
			{
				Style style0 = new Style(font1, 15);
				Style style1 = new Style(font1, 15).setFillColor(Color.YELLOW);
				Style style2 = new Style(font1, 10).setFillColor(Color.RED);

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea( 70, 790, 530, 750, new Paragraph(style0, "TextArea Anchor")).setAnchor(Anchor.NORTH));
					page.append(new TextArea( 70, 750, 215, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH_WEST));
					page.append(new TextArea(225, 750, 375, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH));
					page.append(new TextArea(385, 750, 530, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH_EAST));
					page.append(new TextArea( 70, 520, 215, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.WEST));
					page.append(new TextArea(225, 520, 375, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.CENTER));
					page.append(new TextArea(385, 520, 530, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.EAST));
					page.append(new TextArea( 70, 290, 215,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH_WEST));
					page.append(new TextArea(225, 290, 375,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH));
					page.append(new TextArea(385, 290, 530,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")), new Paragraph(style1, "para3")).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH_EAST));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea( 70, 790, 530, 750, new Paragraph(style0, "TextArea Anchor + Paragraph alignment")).setAnchor(Anchor.NORTH));
					page.append(new TextArea( 70, 750, 215, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH_WEST));
					page.append(new TextArea(225, 750, 375, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH));
					page.append(new TextArea(385, 750, 530, 530, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.NORTH_EAST));
					page.append(new TextArea( 70, 520, 215, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.WEST));
					page.append(new TextArea(225, 520, 375, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.CENTER));
					page.append(new TextArea(385, 520, 530, 300, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.EAST));
					page.append(new TextArea( 70, 290, 215,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH_WEST));
					page.append(new TextArea(225, 290, 375,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH));
					page.append(new TextArea(385, 290, 530,  70, new Paragraph(style1, "paragraph1"), new Paragraph(style1, "para2", "span2", "span3").add(new Span(style2,"ohhhhwrap!!")).setAlignment(Alignment.RIGHT), new Paragraph(style1, "para3").setAlignment(Alignment.RIGHT)).setBackground(Color.LIGHT_GRAY).setAnchor(Anchor.SOUTH_EAST));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
