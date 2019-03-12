package samples;

import java.io.FileOutputStream;
import pdf.Style;
import pdf.ExtendedFont;
import pdf.Font;
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
			Font font0 = new StandardFont("G", "Helvetica");
			Font font1 = new ExtendedFont("F6", Streams.readAll("d:\\Desktop\\pdf\\Catamaran-Regular.ttf"));
			Font font2 = new ExtendedFont("F7", Streams.readAll("d:\\Desktop\\pdf\\AmaticSC-Regular.ttf"));
			Font font3 = new ExtendedFont("F8", Streams.readAll("d:\\Desktop\\pdf\\Montez-Regular.ttf"));
			Font font4 = new ExtendedFont("F9", Streams.readAll("d:\\Desktop\\pdf\\Muli.ttf"));

			Style style1 = new Style(font4, 11);
			Style style2 = new Style(font3, 24);
			Style style3 = new Style(font1, 8);
			Style style4 = new Style(font2, 12);

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\Desktop\\pdf\\output_java.pdf")).setCompress(false))
			{
				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(800, 70, 650, 550, new Paragraph(style1, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."), new Paragraph(style1, "Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia."), new Paragraph(style1, "Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy."), new Paragraph(style1, "Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016."), new Paragraph(style1, "Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!")));
					page.append(new TextArea(450, 70, 100, 300, new Paragraph(style2, "O kulturze pisze się i podaje jej definicje na wiele różnych sposobów. Niektórzy antropologowie uważaja, że kutura to komunikowanie się, i że komunikowanie się to kultura. Inni widzą kulturę jako ujakościowienie się lub jako kultywację pewnego stylu życia. Kulturalna osoba mogłaby być widziana jakościowo lepszą i całokształtną. My uważamy, że kultura ma w posiadaniu bardziej specjalistyczne znaczenia, współpracujące z ustalonymi sposobami zachowywania dla odrębnych grup ludzkich. Kultura to sklep ze wspólnymi społecznymi doświadczeniami kompletującymi plany i sposoby bycia ludzi i epok. Religia jest zawsze znajdywana blisko podstawowych struktur społeczeństwa i jego kultury. Wielu ludzi zaczyna ich pierwsze spotkanie z bóstwem i z istotami supernaturalnymi bardzo wcześnie w życiu. Bez względu na to, jak bardzo oni moga się zmienić i unowocześnić swoje postępowanie z biegiem wieku, religia i tak kontynuuje specjalne efekty na ich myślenie i postępowanie. Warto jest, by specjaliści od reklam pomyśleli by w jak najbardziej korzystny sposób uwzględnić w ich pracy istnienie tej ludzkiej ambicji na godność dla celów marketingowych.")));
					page.append(new TextArea(450, 330, 100, 550, new Paragraph(style3, "Moje życie Mam na imię Ola. Niektórzy mówią na mnie Aleksandra. Mam 30 lat. Mam brata, ale nie mam siostry. Mieszkam w Warszawie. To stolica Polski. Urodziłam się tu. Mieszkam w samym centrum. Idąc do pracy, przechodzę obok zamku. Lubię oglądać zabytki. Nie mam dzieci, ale mój brat ma dwójkę dzieci. Czasem bawię się z nimi. Lubię odwiedzać rodzinę. Prawie cała moja rodzina też mieszka w Warszawie. Czasem odwiedzają mnie rodzice. Staram się wtedy przygotować pyszne jedzenie. Podczas wizyty dużo rozmawiamy. Miło jest spędzać tak czas. Bardzo lubię konie. Niestety mieszkam w mieście, więc nie mogę ich hodować. Gdybym mieszkała na wsi, chciałabym mieć konia. Jeżdżenie na koniu jest przyjemne. Mam swojego węża. Jest bardzo ładny i nie jest groźny. Ale nie da się na nim jeździć jak na koniu. Pracuję przy komputerze. Pomagam innym ludziom. Pomaganie innym jest ważne. Ludzie uśmiechają się, gdy im pomogę i dziękują. Każdy powinien być miły i pomocny. Bardzo lubię biegać. Bieganie jest dobre dla zdrowia. Można spotkać miłych ludzi biegając. Jeśli nie biegaliście do tej pory – spróbujcie.")));

					page.append(new TextArea(625, 70, 475, 550, new Paragraph(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."), new Paragraph(style4, "Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia."), new Paragraph(style4, "Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy."), new Paragraph(style4, "Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016."), new Paragraph(style4, "Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!")));

					page.registerFont(font0);
//					page.append("BT /G 24 Tf 175 820 Td (Hello World!) Tj ET\n");
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(800, 70, 475, 550,
						new Paragraph(new Span(style1, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style2, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style3, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."))
					));

					page.append(new TextArea(250, 70, 100, 550,  new Paragraph(new Span(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")), new Paragraph(new Span(style4, "Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia.")), new Paragraph(new Span(style4, "Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy.")), new Paragraph(new Span(style4, "Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016.")), new Paragraph(new Span(style4, "Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!"))));
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
