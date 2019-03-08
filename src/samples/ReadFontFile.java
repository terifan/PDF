package samples;

import font.FontFile;
import font.truetype.TrueTypeFont;
import pdf.Array;
import pdf.Style;
import pdf.ExtendedFont;
import pdf.Obj;
import pdf.Symbol;
import util.Streams;


public class ReadFontFile
{
	public static void main(String... args)
	{
		try
		{
			byte[] fontData = Streams.readAll("d:\\Desktop\\pdf\\Catamaran-Regular.ttf");
			FontFile fontFile = new TrueTypeFont(fontData);
			ExtendedFont fontRef = new ExtendedFont("F7", fontData);
			Style font = new Style(fontRef, 12);

			fontRef.lookup(new Symbol(font, 'a'));

			Array fontBBox = new Array(fontFile.getFontBBox());
			double ascent = font.getAscent();
			double descent = font.getDescent();
			String name = fontFile.getName();

			System.out.println(fontBBox.asJSON());
			System.out.println(ascent);
			System.out.println(descent);
			System.out.println(name);

			System.out.println("XXX 0 obj");
			System.out.print(new Obj(true, fontRef).asString());
			System.out.println("endobj");
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
