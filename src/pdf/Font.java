package pdf;

import font.FontFile;
import java.io.IOException;


public abstract class Font
{
	Font()
	{
	}


	public abstract String getIdentity();


	abstract FontFile getFontFile();


	abstract Ref print(PDFWriter aWriter) throws IOException;
}
