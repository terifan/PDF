package samples;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import pdf.Font;
import pdf.FontRef;
import font.truetype.TrueTypeFont;
import java.io.FileInputStream;
import java.util.zip.InflaterInputStream;
import pdf.Array;
import pdf.ByteValue;
import pdf.Dictionary;
import pdf.NumberValue;
import pdf.Paragraph;
import pdf.writer.PDFWriter;
import pdf.Ref;
import pdf.Struct;
import pdf.TextValue;
import pdf.writer.TextArea;
import util.Streams;


public class GenerateDocument
{
	public static void main(String... args)
	{
		try
		{
//			Streams.transfer(new InflaterInputStream(new FileInputStream("d:\\Desktop\\pdf\\font.ttf.zip")), "d:\\Desktop\\pdf\\font.ttf");
			Streams.transfer(new InflaterInputStream(new FileInputStream("d:\\Desktop\\pdf\\x.txt")), "d:\\Desktop\\pdf\\xx.txt");

			byte[] fontData1 = Streams.readAll("d:\\Desktop\\pdf\\Catamaran-Regular.ttf");
			FontRef fontRef1 = new FontRef(new TrueTypeFont(fontData1), "F6");

			byte[] fontData2 = Streams.readAll("d:\\Desktop\\pdf\\font.ttf");
			FontRef fontRef2 = new FontRef(new TrueTypeFont(fontData2), "F7");

			Font font1 = new Font(fontRef2, 11);
			Font font2 = new Font(fontRef2, 24);
			Font font3 = new Font(fontRef2, 8);
			Font font4 = new Font(fontRef1, 12);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try (PDFWriter pdf = new PDFWriter(baos))
			{
				TextArea textArea1 = new TextArea(800, 70, 650, 550, font1, new Paragraph("Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."), new Paragraph("Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia."), new Paragraph("Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy."), new Paragraph("Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016."), new Paragraph("Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!"));
				TextArea textArea4 = new TextArea(625, 70, 475, 550, font4, new Paragraph("Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."), new Paragraph("Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia."), new Paragraph("Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy."), new Paragraph("Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016."), new Paragraph("Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!"));
				TextArea textArea2 = new TextArea(450, 70, 100, 300, font2, new Paragraph("O kulturze pisze się i podaje jej definicje na wiele różnych sposobów. Niektórzy antropologowie uważaja, że kutura to komunikowanie się, i że komunikowanie się to kultura. Inni widzą kulturę jako ujakościowienie się lub jako kultywację pewnego stylu życia. Kulturalna osoba mogłaby być widziana jakościowo lepszą i całokształtną. My uważamy, że kultura ma w posiadaniu bardziej specjalistyczne znaczenia, współpracujące z ustalonymi sposobami zachowywania dla odrębnych grup ludzkich. Kultura to sklep ze wspólnymi społecznymi doświadczeniami kompletującymi plany i sposoby bycia ludzi i epok. Religia jest zawsze znajdywana blisko podstawowych struktur społeczeństwa i jego kultury. Wielu ludzi zaczyna ich pierwsze spotkanie z bóstwem i z istotami supernaturalnymi bardzo wcześnie w życiu. Bez względu na to, jak bardzo oni moga się zmienić i unowocześnić swoje postępowanie z biegiem wieku, religia i tak kontynuuje specjalne efekty na ich myślenie i postępowanie. Warto jest, by specjaliści od reklam pomyśleli by w jak najbardziej korzystny sposób uwzględnić w ich pracy istnienie tej ludzkiej ambicji na godność dla celów marketingowych."));
				TextArea textArea3 = new TextArea(450, 330, 100, 550, font3, new Paragraph("Moje życie Mam na imię Ola. Niektórzy mówią na mnie Aleksandra. Mam 30 lat. Mam brata, ale nie mam siostry. Mieszkam w Warszawie. To stolica Polski. Urodziłam się tu. Mieszkam w samym centrum. Idąc do pracy, przechodzę obok zamku. Lubię oglądać zabytki. Nie mam dzieci, ale mój brat ma dwójkę dzieci. Czasem bawię się z nimi. Lubię odwiedzać rodzinę. Prawie cała moja rodzina też mieszka w Warszawie. Czasem odwiedzają mnie rodzice. Staram się wtedy przygotować pyszne jedzenie. Podczas wizyty dużo rozmawiamy. Miło jest spędzać tak czas. Bardzo lubię konie. Niestety mieszkam w mieście, więc nie mogę ich hodować. Gdybym mieszkała na wsi, chciałabym mieć konia. Jeżdżenie na koniu jest przyjemne. Mam swojego węża. Jest bardzo ładny i nie jest groźny. Ale nie da się na nim jeździć jak na koniu. Pracuję przy komputerze. Pomagam innym ludziom. Pomaganie innym jest ważne. Ludzie uśmiechają się, gdy im pomogę i dziękują. Każdy powinien być miły i pomocny. Bardzo lubię biegać. Bieganie jest dobre dla zdrowia. Można spotkać miłych ludzi biegając. Jeśli nie biegaliście do tej pory – spróbujcie."));

				String content = "";
				content += textArea1.produce();
				content += textArea2.produce();
				content += textArea3.produce();
				content += textArea4.produce();

				Ref refContent = pdf.print(new Struct(false, new TextValue("BT /G 24 Tf 175 820 Td (Hello World!) Tj ET\n" + content)));

				Ref refStandardFont = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type1").put("/BaseFont", "/Helvetica")));

				Ref refFontData1 = pdf.print(new Struct(true, new ByteValue(fontData1)));
				Ref refFontData2 = pdf.print(new Struct(true, new ByteValue(fontData2)));

				Ref refCMap1 = pdf.registerFont(fontRef1);
				Ref refCMap2 = pdf.registerFont(fontRef2);

				Array fontBBox1 = new Array();
				fontBBox1.add(fontRef1.getFontFile().getFontBBox()[0]);
				fontBBox1.add(fontRef1.getFontFile().getFontBBox()[1]);
				fontBBox1.add(fontRef1.getFontFile().getFontBBox()[2]);
				fontBBox1.add(fontRef1.getFontFile().getFontBBox()[3]);

				Array fontBBox2 = new Array();
				fontBBox2.add(fontRef2.getFontFile().getFontBBox()[0]);
				fontBBox2.add(fontRef2.getFontFile().getFontBBox()[1]);
				fontBBox2.add(fontRef2.getFontFile().getFontBBox()[2]);
				fontBBox2.add(fontRef2.getFontFile().getFontBBox()[3]);

				Ref refFontDescriptor1 = pdf.print(new Struct(new Dictionary().put("/Type", "/FontDescriptor").put("/Ascent", fontRef1.getFontFile().getAscent()).put("/CapHeight", 715).put("/Descent", fontRef1.getFontFile().getDescent()).put("/Flags", 0).put("/FontBBox", fontBBox1).put("/FontFile2", refFontData1).put("/FontName", "/" + fontRef1.getFontFile().getName()).put("/ItalicAngle", 0).put("/StemV", 76)));
				Ref refFontDescriptor2 = pdf.print(new Struct(new Dictionary().put("/Type", "/FontDescriptor").put("/Ascent", fontRef2.getFontFile().getAscent()).put("/CapHeight", 715).put("/Descent", fontRef2.getFontFile().getDescent()).put("/Flags", 0).put("/FontBBox", fontBBox2).put("/FontFile2", refFontData2).put("/FontName", "/" + fontRef2.getFontFile().getName()).put("/ItalicAngle", 0).put("/StemV", 76)));

				Ref refDescendantFont1 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/CIDFontType2").put("/BaseFont", "/" + fontRef1.getFontFile().getName()).put("/CIDSystemInfo", new Dictionary().put("/Ordering", "(Identity)").put("/Registry", "(Adobe)").put("/Supplement", 0)).put("/CIDToGIDMap", "/Identity").put("/FontDescriptor", refFontDescriptor1)));
				Ref refDescendantFont2 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/CIDFontType2").put("/BaseFont", "/" + fontRef2.getFontFile().getName()).put("/CIDSystemInfo", new Dictionary().put("/Ordering", "(Identity)").put("/Registry", "(Adobe)").put("/Supplement", 0)).put("/CIDToGIDMap", "/Identity").put("/FontDescriptor", refFontDescriptor2)));

//				Ref refDescendantFont1 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/CIDFontType2").put("/BaseFont", "/" + fontRef1.getFontFile().getName()).put("/CIDSystemInfo", new Dictionary().put("/Ordering", "(Identity)").put("/Registry", "(Adobe)").put("/Supplement", 0)).put("/CIDToGIDMap", "/Identity").put("/W", pdf.getFontWidths(fontRef1)).put("/FontDescriptor", refFontDescriptor1)));
//				Ref refDescendantFont2 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/CIDFontType2").put("/BaseFont", "/" + fontRef2.getFontFile().getName()).put("/CIDSystemInfo", new Dictionary().put("/Ordering", "(Identity)").put("/Registry", "(Adobe)").put("/Supplement", 0)).put("/CIDToGIDMap", "/Identity").put("/W", pdf.getFontWidths(fontRef2)).put("/FontDescriptor", refFontDescriptor2)));

				Ref refFont1 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type0").put("/BaseFont", "/" + fontRef1.getFontFile().getName()).put("/DescendantFonts", refDescendantFont1).put("/Encoding", "/Identity-H").put("/ToUnicode", refCMap1)));
				Ref refFont2 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type0").put("/BaseFont", "/" + fontRef2.getFontFile().getName()).put("/DescendantFonts", refDescendantFont2).put("/Encoding", "/Identity-H").put("/ToUnicode", refCMap2)));

				Dictionary fontsDic = new Dictionary()
					.put("/G", refStandardFont)
					.put(fontRef1.getIdentity(), refFont1)
					.put(fontRef2.getIdentity(), refFont2);

				Ref refResources = pdf.print(new Struct(new Dictionary().put("/Font", fontsDic)));

				Ref refPage = pdf.print(new Struct(new Dictionary().put("/Type", "/Page").put("/MediaBox", "[0 0 595.28 841.89]").put("/Contents", refContent).put("/Resources", refResources)));

				pdf.addPage(refPage);
			}

			try (FileOutputStream fos = new FileOutputStream("d:\\Desktop\\pdf\\output.pdf"))
			{
				baos.writeTo(fos);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
