package pdf;

import font.FontFile;


public abstract class Font extends Resource
{
	abstract FontFile getFontFile();


	abstract void registerGlyph(int aGlyph, int aCharacter);
}
