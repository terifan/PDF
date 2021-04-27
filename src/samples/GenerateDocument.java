package samples;

import java.io.FileOutputStream;
import pdf.Style;
import pdf.ExtendedFont;
import pdf.Font;
import pdf.Resource;
import pdf.Image;
import pdf.ImageArea;
import pdf.Paragraph;
import pdf.PDFWriter;
import pdf.Page;
import pdf.Span;
import pdf.StandardFont;
import pdf.TextArea;
import util.Streams;


public class GenerateDocument
{
	public static void main(String... args)
	{
		try
		{
			Font font0 = new StandardFont("Helvetica");
			Font font1 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\lux-montag\\lux-montag.ttf"));
			Font font2 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\ubuntu\\ubuntu-r.ttf"));
			Font font3 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\open-sans\\opensans-regular.ttf"));
			Font font4 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\coolvetica\\coolvetica compressed rg.ttf"));

			Style style1 = new Style(font1, 16);
			Style style2 = new Style(font2, 11);
			Style style3 = new Style(font3, 13);
			Style style4 = new Style(font4, 27);

			Image image1 = new Image(Streams.readAll("d:\\Desktop\\pdf\\image1.jpg"), false);
			Image image2 = new Image(Streams.readAll("d:\\Desktop\\pdf\\image2.png"), true);

			String russian = "- Я живу в центре Европы - в Польше. Но в каком городе я живу? Ниже я дам несколько советов, которые помогут вам решить эту загадку.";
			String greek = "- Ζω στη μέση της Ευρώπης - στην Πολωνία. Αλλά σε ποια πόλη μένω; Παρακάτω θα παρουσιάσω μερικές συμβουλές που θα σας βοηθήσουν να λύσετε αυτό το παζλ.";
			String polish = "- Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.";

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\Desktop\\pdf\\output_java.pdf")).setCompress(true))
			{
				try (Page page = pdf.addPage())
				{
					page.registerFont(font0);
//					page.append("BT " + font0.getIdentity() + " 24 Tf 225 820 Td (Standard ascii text) Tj ET\n");

					page.append(new TextArea(780, 70, 605, 550, new Paragraph(style1, russian), new Paragraph(style1, greek), new Paragraph(style1, polish)));
					page.append(new TextArea(605, 70, 455, 300, new Paragraph(style2, russian), new Paragraph(style2, greek), new Paragraph(style2, polish)));
					page.append(new TextArea(450, 70, 70, 300, new Paragraph(style3, russian), new Paragraph(style3, greek), new Paragraph(style3, polish)));
					page.append(new TextArea(450, 330, 70, 550, new Paragraph(style4, russian), new Paragraph(style4, greek), new Paragraph(style4, polish)));

					page.append(new ImageArea(605, 330, 465, 550, image1));
					page.append(new ImageArea(250, 70, 70, 300, image2));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(800, 70, 475, 550,  new Paragraph(new Span(style1, polish), new Span(style2, russian), new Span(style3, greek))));

					page.append(new ImageArea(625, 70, 300, 550, image1));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
