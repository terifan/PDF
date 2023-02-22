package samples;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.TextArea;


/**
 * brute force testing Windows TTF files
 */
public class TestReadingFontFiles
{
	public static void main(String ... args)
	{
		try
		{
			String russian = "ибо многое, о люди, то же самое произойдет и с вами, как с судьей в этом вопросе, даже более, если вы так преданы им: ибо я видел, что, если вы имеете такое же мнение о других, то также произойдет и о вы, даже если бы это было не так: те, которые не негодуют на тех, кто рождается, но все те, кто стремится причинить такой малый вред, довольны. Если я человек, кто бы он ни был, о, присутствующий у его могилы, по причине чего вы объявляете о человеческих добродетелях в письменных текстах, я embsammen, если тем, кому о них рассказывали в течение нескольких дней, они говорят: но потому что все они люди, все время не хватает по причине, пока вы внемлите рабочим. , по этой причине и город испытывает меня, предусмотренный теми, кто говорит напрасно, из малого заповедь сделана , будучи, таким образом, аббатом, если вы действительно прощаете их, а не слушателей.";
			String greek = "περὶ πολλοῦ ἂν ποιησαίμην, ὦ ἄνδρες, τὸ τοιούτους ὑμᾶς ἐμοὶ δικαστὰς περὶ τούτου τοῦ πράγματος γενέσθαι, οἷοίπερ ἂν ὑμῖν αὐτοῖς εἴητε τοιαῦτα πεπονθότες: εὖ γὰρ οἶδ᾽ ὅτι, εἰ τὴν αὐτὴν γνώμην περὶ τῶν ἄλλων ἔχοιτε, ἥνπερ περὶ ὑμῶν αὐτῶν, οὐκ ἂν εἴη: ὅστις οὐκ ἐπὶ τοῖς γεγενημένοις ἀγανακτοίη, ἀλλὰ πάντες ἂν περὶ τῶν τὰ τοιαῦτα ἐπιτηδευόντων τὰς ζημίας μικρὰς ἡγοῖσθε. εἰ μὲν ἡγούμην οἷόν τε εἶναι, ὦ παρόντες ἐπὶ τῷδε τῷ τάφῳ, λόγῳ δηλῶσαι τὴν τῶν ἐνθάδε κειμένων ἀνδρῶν ἀρετήν, ἐμεμψάμην ἂν τοῖς ἐπαγγείλασιν ἐπ᾽ αὐτοῖς ἐξ ὀλίγων ἡμερῶν λέγειν: ἐπειδὴ δὲ πᾶσιν ἀνθρώποις ὁ πᾶς χρόνος οὐχ ἱκανὸς λόγον ἴσον παρασκευάσαι τοῖς τούτων ἔργοις, διὰ τοῦτο καὶ ἡ πόλις μοι δοκεῖ, προνοουμένη τῶν ἐνθάδε λεγόντων, ἐξ ὀλίγου τὴν πρόσταξιν ποιεῖσθαι, ἡγουμένη οὕτως ἂν μάλιστα συγγνώμης αὐτοὺς παρὰ τῶν ἀκουσάντων τυγχάνειν.";
			String polish = "bo wiele, o ludzie, to samo spotka was jako sędziego w tej sprawie, a nawet więcej, jeśli będziecie im tak oddani: bo widziałem, że jeśli macie takie samo zdanie o innych, stanie się to również o ty, nawet gdyby tak nie było: ci, którzy nie są oburzeni na tych, którzy się urodzili, ale wszyscy ci, którzy chcą wyrządzić tak małą krzywdę, są zadowoleni. Jeśli jestem człowiekiem, kimkolwiek on jest, o obecny przy grobie, z powodu którego ogłaszacie cnoty ludzi w tekstach pisanych, ja embsammen, jeśli tym, którym powiedziano o nich od kilku dni, powiedzą: ale ponieważ wszyscy są ludźmi, cały czas nie wystarcza z jakiegoś powodu, dopóki zajmujecie się robotnikami. , dlatego też miasto mnie doświadcza, przewidziane przez tych, którzy mówią na próżno, od małego przykazanie jest wydane , będąc w ten sposób opatem, jeśli rzeczywiście wybaczysz im, a nie słuchaczom.";
			String latvian = "jo daudz kas, ak, cilvēki, notiks ar jums kā šīs lietas tiesnesim, vēl jo vairāk, ja jūs viņiem tik ļoti uzticēsities: jo esmu redzējis, ka, ja jums būs tāds pats viedoklis par citiem, tas notiks arī jūs, pat ja tā nebūtu: tie, kas nav sašutuši par dzimušajiem, bet visi tie, kas cenšas nodarīt tik mazu ļaunumu, ir apmierināti. Ja es esmu cilvēks, lai kas arī viņš būtu, ak, kas atrodas pie viņa kapa, kura dēļ jūs rakstītajos tekstos pasludināt cilvēku tikumus, es apsveicu, ja tiem, kam par tiem ir stāstīts dažas dienas, viņi saka: jo viņi visi ir vīrieši, ar visu laiku nepietiek, kamēr jūs rūpējaties par strādniekiem. , šī iemesla dēļ arī pilsēta mani pārbauda, ​​ko nodrošina tie, kas runā veltīgi, no mazuma rodas bauslis , tādējādi būdams abats, ja tiešām jūs viņiem piedodat, nevis klausītāji.";
			String english = "for many things, O people, will happen to you as the judge of this matter, the more so if you put so much trust in them: for I have seen that if you have the same opinion of others, it will happen to you also, even if so would not be: those who are not outraged at the born, but all those who seek to do so little harm are satisfied. If I am a man, whoever he may be, O who is at his grave, for whose sake you in the written to proclaim the virtues of men in texts, I congratulate them if those who have been told about them for a few days say: because they are all men, all the time is not enough while you take care of the workers. , for this reason also the city examines me, which is provided by those who speak in vain, from a little comes the commandment , thus being an abbot, if indeed you forgive them and not the listeners.";

			for (Path path : Files.list(Paths.get("c:\\windows\\fonts")).filter(e -> e.toString().endsWith(".ttf")).toList())
			{
				Style style;
				try
				{
					style = new Style(new ExtendedFont(Files.readAllBytes(path)), 11);
				}
				catch (Exception e)
				{
					System.out.println("Error reading font file: " + path);
					continue;
				}
				try
				{
					try (PDFWriter pdf = new PDFWriter(new ByteArrayOutputStream()); Page page = pdf.addPage())
					{
						page.append(new TextArea(780, 70, 70, 550, new Paragraph(style, english)));
					}

					System.out.println("English OK: " + path);
				}
				catch (Exception e)
				{
					System.out.println("Error render english font: " + path);
				}
				try
				{
					try (PDFWriter pdf = new PDFWriter(new ByteArrayOutputStream()); Page page = pdf.addPage())
					{
						page.append(new TextArea(780, 70, 70, 550, new Paragraph(style, russian), new Paragraph(style, greek), new Paragraph(style, polish), new Paragraph(style, latvian)));
					}

					System.out.println("Unicode OK: " + path);
				}
				catch (Exception e)
				{
					System.out.println("Error render uncode font: " + path);
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
