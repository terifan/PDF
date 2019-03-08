package pdf;

import font.FontFile;
import java.io.IOException;


public class StandardFont extends Font
{
	private String mTypeFace;


	public StandardFont(String aIdentity, String aTypeFace)
	{
		super(aIdentity);

		mTypeFace = aTypeFace;
	}


	@Override
	FontFile getFontFile()
	{
		return null;
	}


	@Override
	Ref print(PDFWriter aWriter) throws IOException
	{
		return aWriter.print(new Obj(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type1").put("/BaseFont", "/" + mTypeFace)));
	}
}
