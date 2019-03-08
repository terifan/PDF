package pdf;

import font.FontFile;
import java.io.IOException;


public abstract class Font
{
	Font()
	{
	}

	abstract String getIdentity();

	abstract Ref print(PDFWriter aWriter) throws IOException;

	abstract FontFile getFontFile();
}
