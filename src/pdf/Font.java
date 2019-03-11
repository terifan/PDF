package pdf;

import font.FontFile;
import java.io.IOException;


public abstract class Font
{
	private String mIdentity;


	Font(String aIdentity)
	{
		mIdentity = aIdentity.startsWith("/") ? aIdentity : "/" + aIdentity;
	}


	public String getIdentity()
	{
		return mIdentity;
	}


	abstract FontFile getFontFile();


	abstract void registerGlyph(int aGlyph, int aCharacter);


	abstract Ref print(PDFWriter aWriter) throws IOException;
}
