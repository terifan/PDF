package org.terifan.pdfwriter;

import org.terifan.font.FontFile;
import java.io.IOException;


public class StandardFont extends Font
{
	private String mTypeFace;


	public StandardFont(String aTypeFace)
	{
		mTypeFace = aTypeFace;
	}


	@Override
	public void reuse()
	{
	}


	@Override
	public void registerGlyph(int aGlyph, int aCharacter)
	{
	}


	@Override
	public FontFile getFontFile()
	{
		return null;
	}


	@Override
	public Ref print(PDFWriter aWriter) throws IOException
	{
		return aWriter.print(new Obj(new Dictionary().put("/Type", "/Font").put("/Subtype", "/Type1").put("/BaseFont", "/" + mTypeFace)));
	}
}
