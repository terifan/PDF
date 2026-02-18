package org.terifan.font.truetype;


interface CMap
{
	int getEntryCount();

	int findGlyphIndex(int aCharacter);
}
