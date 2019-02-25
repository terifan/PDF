package samples;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import pdf.Font;
import pdf.FontRef;
import font.truetype.TrueTypeFont;
import pdf.ByteValue;
import pdf.Dictionary;
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
			byte[] fontData = Streams.readAll("d:\\Desktop\\pdf\\Anonymous_Pro_PL.ttf");
			FontRef fontRef = new FontRef(new TrueTypeFont(fontData), "F7");

			Font font1 = new Font(fontRef, 12);
			Font font2 = new Font(fontRef, 24);
			Font font3 = new Font(fontRef, 9);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try (PDFWriter pdf = new PDFWriter(baos))
			{
				TextArea textArea1 = new TextArea(750, 70, 500, 550, font1, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki. Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia. Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy. Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016. Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!");
				TextArea textArea2 = new TextArea(450, 70, 100, 300, font2, "O kulturze pisze się i podaje jej definicje na wiele różnych sposobów. Niektórzy antropologowie uważaja, że kutura to komunikowanie się, i że komunikowanie się to kultura. Inni widzą kulturę jako ujakościowienie się lub jako kultywację pewnego stylu życia. Kulturalna osoba mogłaby być widziana jakościowo lepszą i całokształtną. My uważamy, że kultura ma w posiadaniu bardziej specjalistyczne znaczenia, współpracujące z ustalonymi sposobami zachowywania dla odrębnych grup ludzkich. Kultura to sklep ze wspólnymi społecznymi doświadczeniami kompletującymi plany i sposoby bycia ludzi i epok. Religia jest zawsze znajdywana blisko podstawowych struktur społeczeństwa i jego kultury. Wielu ludzi zaczyna ich pierwsze spotkanie z bóstwem i z istotami supernaturalnymi bardzo wcześnie w życiu. Bez względu na to, jak bardzo oni moga się zmienić i unowocześnić swoje postępowanie z biegiem wieku, religia i tak kontynuuje specjalne efekty na ich myślenie i postępowanie. Warto jest, by specjaliści od reklam pomyśleli by w jak najbardziej korzystny sposób uwzględnić w ich pracy istnienie tej ludzkiej ambicji na godność dla celów marketingowych.");
				TextArea textArea3 = new TextArea(450, 330, 100, 550, font3, "Moje życie Mam na imię Ola. Niektórzy mówią na mnie Aleksandra. Mam 30 lat. Mam brata, ale nie mam siostry. Mieszkam w Warszawie. To stolica Polski. Urodziłam się tu. Mieszkam w samym centrum. Idąc do pracy, przechodzę obok zamku. Lubię oglądać zabytki. Nie mam dzieci, ale mój brat ma dwójkę dzieci. Czasem bawię się z nimi. Lubię odwiedzać rodzinę. Prawie cała moja rodzina też mieszka w Warszawie. Czasem odwiedzają mnie rodzice. Staram się wtedy przygotować pyszne jedzenie. Podczas wizyty dużo rozmawiamy. Miło jest spędzać tak czas. Bardzo lubię konie. Niestety mieszkam w mieście, więc nie mogę ich hodować. Gdybym mieszkała na wsi, chciałabym mieć konia. Jeżdżenie na koniu jest przyjemne. Mam swojego węża. Jest bardzo ładny i nie jest groźny. Ale nie da się na nim jeździć jak na koniu. Pracuję przy komputerze. Pomagam innym ludziom. Pomaganie innym jest ważne. Ludzie uśmiechają się, gdy im pomogę i dziękują. Każdy powinien być miły i pomocny. Bardzo lubię biegać. Bieganie jest dobre dla zdrowia. Można spotkać miłych ludzi biegając. Jeśli nie biegaliście do tej pory – spróbujcie.");

				String content = "";
				content += textArea1.produce();
				content += textArea2.produce();
				content += textArea3.produce();

				Ref refContent = pdf.print(new Struct(false, new TextValue("BT /G 24 Tf 175 820 Td (Hello World!)Tj ET\n" + content)));

				Ref refFont1 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type1").put("/BaseFont", "/Helvetica")));

				Ref refFontData = pdf.print(new Struct(true, new ByteValue(fontData)));

				Ref refCMap = pdf.registerFont(fontRef);

				Ref refFontDescriptor = pdf.print(new Struct(new Dictionary().put("/Type", "/FontDescriptor").put("/Ascent", 984).put("/CapHeight", 743).put("/Descent", -273).put("/Flags", 4).put("/FontBBox", "[ -452 -273 1470 1056 ]").put("/FontFile2", refFontData).put("/FontName", "/Merriweather-Bold").put("/ItalicAngle", 0).put("/StemV", 188)));

				Ref refDescendantFont = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/CIDFontType2").put("/BaseFont", "/Merriweather-Bold").put("/CIDSystemInfo", new Dictionary().put("/Ordering", "(Identity)").put("/Registry", "(Adobe)").put("/Supplement", 0)).put("/CIDToGIDMap", "/Identity").put("/DW", 0).put("/FontDescriptor", refFontDescriptor)));

				Ref refFont2 = pdf.print(new Struct(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type0").put("/BaseFont", "/Merriweather-Bold").put("/DescendantFonts", refDescendantFont).put("/Encoding", "/Identity-H").put("/ToUnicode", refCMap)));

				Ref refResources = pdf.print(new Struct(new Dictionary().put("/Font", new Dictionary().put("/G", refFont1).put("/F7", refFont2))));

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
