package pdf;

import font.FontFile;
import java.io.IOException;
import java.util.UUID;


public abstract class Font
{
	private final UUID mUUID;
	private String mIdentity;


	Font()
	{
		mUUID = UUID.randomUUID();
	}


	UUID getUUID()
	{
		return mUUID;
	}


	void setIdentity(String aIdentity)
	{
		mIdentity = aIdentity;
	}


	public String getIdentity()
	{
		return mIdentity;
	}


	abstract FontFile getFontFile();


	abstract void registerGlyph(int aGlyph, int aCharacter);


	abstract Ref print(PDFWriter aWriter) throws IOException;
}
