package pdf;

import java.io.IOException;


public abstract class PDFFont
{
	PDFFont()
	{
	}

	abstract String getIdentity();

	abstract Ref print(PDFWriter aWriter) throws IOException;
}
