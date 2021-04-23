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
			Font font1 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\Lux-Montag.ttf"));
			Font font2 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\Ubuntu-R.ttf"));
			Font font3 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\OpenSans-Regular.ttf"));
			Font font4 = new ExtendedFont(Streams.readAll("d:\\Desktop\\pdf\\coolvetica rg.ttf"));

			Style style1 = new Style(font1, 11);
			Style style2 = new Style(font2, 24);
			Style style3 = new Style(font3, 8);
			Style style4 = new Style(font4, 12);
			Style style5 = new Style(font1, 8);

			Image image1 = new Image(Streams.readAll("d:\\Desktop\\pdf\\image1.jpg"), false);
			Image image2 = new Image(Streams.readAll("d:\\Desktop\\pdf\\image2.png"), true);

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\Desktop\\pdf\\output_java.pdf")).setCompress(true))
			{
				try (Page page = pdf.addPage())
				{
					page.registerFont(font0);
					page.append("BT " + font0.getIdentity() + " 24 Tf 225 820 Td (Hello World!) Tj ET\n");

					page.append(new TextArea(800, 70, 650, 550, new Paragraph(style1, "Я живу в центре Европы - в Польше. Но в каком городе я живу? Ниже я дам несколько советов, которые помогут вам решить эту загадку.Мой город занимает четвертое место в Польше по численности населения. Там вы найдете места, названия которых совпадают с названиями других мест в мире, например, Килиманджаро или Морские Око. Известен и Мультимедийный фонтан, расположенный рядом с Залом столетия, еще несколько десятилетий назад город не принадлежал Польше. В моем городе множество мостов, рек, островов и островков. В моем городе вы также найдете ров, который работает постоянно и в нем постоянно есть вода. Из моего города он ближе к столице Германии - Берлину, чем к столице Польши - Варшаве.Город очень популярен у туристов - каждый год миллионы их ходят по улицам города. Многие из них посещают наш знаменитый зоопарк. Здесь очень популярен африканариум, где можно увидеть многих животных практически в естественных условиях. Для любителей культуры в городе есть множество театров, кинотеатров и музеев. Город выбран")));
					page.append(new TextArea(450, 330, 100, 550, new Paragraph(style2, "Ζω στη μέση της Ευρώπης - στην Πολωνία. Αλλά σε ποια πόλη μένω; Παρακάτω θα παρουσιάσω μερικές συμβουλές που θα σας βοηθήσουν να λύσετε αυτό το παζλ. Η πόλη μου είναι η τέταρτη στην Πολωνία ως προς τον πληθυσμό. Εκεί θα βρείτε μέρη που έχουν τα ίδια ονόματα με άλλα μέρη στον κόσμο - για παράδειγμα Kilimanjaro ή Morskie Oko. Το Multimedia Fountain που βρίσκεται δίπλα στο Centennial Hall είναι επίσης γνωστό. Μέχρι πριν από αρκετές δεκαετίες, η πόλη δεν ανήκε στην Πολωνία. Η πόλη μου έχει τόνους γεφυρών, ποταμών, νησιών και νησίδων. Στην πόλη μου θα βρείτε επίσης τάφρο που λειτουργεί συνεχώς και έχει νερό όλη την ώρα. Από την πόλη μου, είναι πιο κοντά στην πρωτεύουσα της Γερμανίας - στο Βερολίνο παρά στην πρωτεύουσα της Πολωνίας - Βαρσοβία. Η πόλη είναι πολύ δημοφιλής στους τουρίστες - κάθε χρόνο εκατομμύρια από αυτούς περπατούν στους δρόμους της πόλης. Πολλοί από αυτούς επισκέπτονται τον περίφημο ζωολογικό μας κήπο. Το Africarium είναι πολύ δημοφιλές εκεί, όπου είναι δυνατό να δείτε πολλά ζώα σε σχεδόν φυσικές συνθήκες. Για τους λάτρεις του πολιτισμού, η πόλη προσφέρει πολλά θέατρα, κινηματογράφους και μουσεία. Η πόλη έχει επιλεγεί")));
					page.append(new TextArea(450, 70, 100, 300, new Paragraph(style3, "Moje życie Mam na imię Ola. Niektórzy mówią na mnie Aleksandra. Mam 30 lat. Mam brata, ale nie mam siostry. Mieszkam w Warszawie. To stolica Polski. Urodziłam się tu. Mieszkam w samym centrum. Idąc do pracy, przechodzę obok zamku. Lubię oglądać zabytki. Nie mam dzieci, ale mój brat ma dwójkę dzieci. Czasem bawię się z nimi. Lubię odwiedzać rodzinę. Prawie cała moja rodzina też mieszka w Warszawie. Czasem odwiedzają mnie rodzice. Staram się wtedy przygotować pyszne jedzenie. Podczas wizyty dużo rozmawiamy. Miło jest spędzać tak czas. Bardzo lubię konie. Niestety mieszkam w mieście, więc nie mogę ich hodować. Gdybym mieszkała na wsi, chciałabym mieć konia. Jeżdżenie na koniu jest przyjemne. Mam swojego węża. Jest bardzo ładny i nie jest groźny. Ale nie da się na nim jeździć jak na koniu. Pracuję przy komputerze. Pomagam innym ludziom. Pomaganie innym jest ważne. Ludzie uśmiechają się, gdy im pomogę i dziękują. Każdy powinien być miły i pomocny. Bardzo lubię biegać. Bieganie jest dobre dla zdrowia. Można spotkać miłych ludzi biegając. Jeśli nie biegaliście do tej pory – spróbujcie.")));

					page.append(new TextArea(625, 70, 475, 300, new Paragraph(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."), new Paragraph(style4, "Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia."), new Paragraph(style4, "Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy."), new Paragraph(style4, "Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016."), new Paragraph(style4, "Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!")));

					page.append(new ImageArea(625, 330, 475, 550, image1));
					page.append(new ImageArea(280, 70, 120, 300, image2));
				}

				try (Page page = pdf.addPage())
				{
					page.append(new TextArea(800, 70, 475, 550,
						new Paragraph(new Span(style1, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style5, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style3, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")),
						new Paragraph(new Span(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki."))
					));

					page.append(new TextArea(250, 70, 100, 550,  new Paragraph(new Span(style4, "Mieszkam w środku Europy – w Polsce. Ale w którym mieście mieszkam? Poniżej przedstawię kilka podpowiedzi które powinny ułatwić rozwiązanie tej zagadki.")), new Paragraph(new Span(style4, "Moje miasto jest czwarte w Polsce pod względem liczby ludności. Znajdziesz w nim miejsca, które nazywają się tak samo jak inne miejsca na świecie – np. Kilimandżaro albo Morskie Oko. Znana jest też Fontanna Multimedialna umieszczona obok Hali Stulecia.")), new Paragraph(new Span(style4, "Jeszcze kilkadziesiąt lat temu miasto nie należało do Polski. Moje miasto ma mnóstwo mostów, rzek oraz wysp i wysepek. W moim mieście znajdziecie też fosę, która cały czas działa i w której cały czas jest woda. Z mojego miasta bliżej jest do stolicy Niemiec – Berlina niż do stolicy Polski – Warszawy.")), new Paragraph(new Span(style4, "Miasto jest bardzo lubiane przez turystów – każdego roku miliony z nich chodzą po ulicach miasta. Wielu z nich odwiedza nasze słynne ZOO. Bardzo popularne w nim jest Afrykarium, w którym możliwe jest oglądanie wielu zwierząt w prawie naturalnych warunkach. Dla miłośników kultury miasto oferuje liczne teatry, kina i muzea. Miasto zostało wybrane Europejską Stolicą Kultury 2016 i Światową Stolicą Książki 2016.")), new Paragraph(new Span(style4, "Czy wystarczy już tych podpowiedzi? Jeśli nie – to jeszcze ostatnia: łacińska nazwa mojego miasta to Vratislavia, a niemiecka to Breslau. Czy już znasz odpowiedź? Tak, to Wrocław!"))));

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
