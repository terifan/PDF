package pdf;

import java.io.IOException;


public class StandardFont extends PDFFont
{
	private final String mIdentity;
	private final String mTypeFace;


	public StandardFont(String aIdentity, String aTypeFace)
	{
		mIdentity = aIdentity.startsWith("/") ? aIdentity : "/" + aIdentity;
		mTypeFace = aTypeFace;
	}


	public String getIdentity()
	{
		return mIdentity;
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
