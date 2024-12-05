package org.terifan.pdfwriter;

import org.terifan.font.FontFile;


public abstract class Font extends Resource implements Cloneable
{
	public abstract void reuse();


	abstract FontFile getFontFile();


	abstract void registerGlyph(int aGlyph, int aCharacter);
}
