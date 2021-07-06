package samples;

import font.FontFile;
import font.truetype.TrueTypeFont;
import java.io.File;
import java.util.HashMap;
import org.terifan.bundle.Array;
import org.terifan.bundle.Bundle;
import util.Streams;


public class ExportTTF
{
	public static void main(String... args)
	{
		try
		{
			File dir = new File("d:\\Desktop\\pdf");
			export(new File("d:\\Desktop\\pdf\\lux-montag\\lux-montag.ttf"), dir);
			export(new File("d:\\Desktop\\pdf\\ubuntu\\ubuntu-r.ttf"), dir);
			export(new File("d:\\Desktop\\pdf\\open-sans\\opensans-regular.ttf"), dir);
			export(new File("d:\\Desktop\\pdf\\coolvetica\\coolvetica compressed rg.ttf"), dir);
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}


	private static void export(File aInputTTF, File aOutputFolder)
	{
		FontFile fontFile = new TrueTypeFont(Streams.readAll(aInputTTF));

		Bundle glyphIndex = new Bundle();
		Array glyphMetrics = new Array();
		HashMap<String, Integer> lookup = new HashMap<>();

		for (int c = 0; c < 65535; c++)
		{
			int g;

			try
			{
				g = fontFile.findGlyphIndexImpl(c);
			}
			catch (IllegalArgumentException e)
			{
				continue;
			}

			try
			{
				int aw = (int)fontFile.getGlyphAdvanceWidth(g);
				int lb = (int)fontFile.getGlyphLeftSideBearing(g);
//				int w = (int)fontFile.getGlyphWidth(g);

				String key = aw + " " + lb;

				if (lookup.get(key) != null)
				{
					glyphIndex.putArray(Integer.toString(c), Array.of(g, lookup.get(key)));
				}
				else
				{
					glyphIndex.putArray(Integer.toString(c), Array.of(g, glyphMetrics.size()));

					lookup.put(key, glyphMetrics.size());

					glyphMetrics.add(Array.of(aw, lb));
				}
			}
			catch (Exception e)
			{
//				System.out.println("Error loading glyph: " + c);
			}
		}

		Bundle bundle = new Bundle();
		bundle.putString("Name", fontFile.getName());
		bundle.putNumber("Ascent", fontFile.getAscent());
		bundle.putNumber("Descent", fontFile.getDescent());
		bundle.putNumber("LineGap", fontFile.getLineGap());
		bundle.putNumber("LineHeight", fontFile.getLineHeight());
		bundle.putNumber("UnitsPerEm", fontFile.getUnitsPerEm());
		bundle.putNumber("CapHeight", 715);
		bundle.putArray("FontBBox", Array.of(fontFile.getFontBBox()));
		bundle.putBundle("GlyphIndex", glyphIndex);
		bundle.putArray("GlyphMetrics", glyphMetrics);

		Streams.transfer(bundle.marshalJSON(!false).getBytes(), new File(aOutputFolder, aInputTTF.getName() + ".json"));
	}
}
