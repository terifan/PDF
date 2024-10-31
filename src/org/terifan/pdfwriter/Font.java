package org.terifan.pdfwriter;

import org.terifan.font.FontFile;


public abstract class Font extends Resource implements Cloneable
{
	abstract FontFile getFontFile();


	abstract void registerGlyph(int aGlyph, int aCharacter);
}
