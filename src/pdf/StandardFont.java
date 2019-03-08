package pdf;

import font.FontFile;
import java.io.IOException;


public class StandardFont extends Font
{
	private final String mIdentity;
	private final String mTypeFace;


	public StandardFont(String aIdentity, String aTypeFace)
	{
		mIdentity = aIdentity.startsWith("/") ? aIdentity : "/" + aIdentity;
		mTypeFace = aTypeFace;
	}


	@Override
	public String getIdentity()
	{
		return mIdentity;
	}


	@Override
	FontFile getFontFile()
	{
		return null;
	}


	public String getTypeFace()
	{
		return mTypeFace;
	}


	@Override
	public Ref print(PDFWriter aWriter) throws IOException
	{
		return aWriter.print(new Obj(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type1").put("/BaseFont", "/" + mTypeFace)));
	}
}
