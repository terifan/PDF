package org.terifan.font.truetype;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.testng.annotations.Test;
import samples.TestMultipleFonts;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TrueTypeFontNGTest
{
	@Test
	public void testCMap4GlyphArrayAndNotdef()
	{
		ByteBuffer data = ByteBuffer.allocate(32);
		data.putShort((short)34);
		data.putShort((short)0);
		data.putShort((short)4);
		data.putShort((short)4);
		data.putShort((short)1);
		data.putShort((short)0);
		data.putShort((short)0x41);
		data.putShort((short)0xFFFF);
		data.putShort((short)0);
		data.putShort((short)0x41);
		data.putShort((short)0xFFFF);
		data.putShort((short)0);
		data.putShort((short)1);
		data.putShort((short)4);
		data.putShort((short)0);
		data.putShort((short)7);

		CMap4 cmap = new CMap4(new ByteBufferReader(data.array()));
		assertEquals(cmap.findGlyphIndex(0x41), 7);
		assertEquals(cmap.findGlyphIndex(0x42), -1);
		assertEquals(cmap.findGlyphIndex(0xFFFF), 0);
		assertEquals(cmap.findGlyphIndex(0x10000), -1);
	}


	@Test
	public void testCMap6RangeAndUnsignedGlyphIds()
	{
		ByteBuffer data = ByteBuffer.allocate(12);
		data.putShort((short)14);
		data.putShort((short)0);
		data.putShort((short)0x100);
		data.putShort((short)2);
		data.putShort((short)0xFFFF);
		data.putShort((short)0x1234);

		CMap6 cmap = new CMap6(new ByteBufferReader(data.array()));
		assertEquals(cmap.findGlyphIndex(0x100), 0xFFFF);
		assertEquals(cmap.findGlyphIndex(0x101), 0x1234);
		assertEquals(cmap.findGlyphIndex(0xFF), -1);
		assertEquals(cmap.findGlyphIndex(0x102), -1);
	}


	@Test
	public void testCMap12SupplementaryCodePoint()
	{
		ByteBuffer data = ByteBuffer.allocate(26);
		data.putShort((short)0);
		data.putInt(28);
		data.putInt(0);
		data.putInt(1);
		data.putInt(0x1F600);
		data.putInt(0x1F600);
		data.putInt(42);

		CMap12 cmap = new CMap12(new ByteBufferReader(data.array()));
		assertEquals(cmap.findGlyphIndex(0x1F600), 42);
		assertEquals(cmap.findGlyphIndex(0x1F601), -1);
	}


	@Test
	public void testCMap10SupplementaryCodePoint()
	{
		ByteBuffer data = ByteBuffer.allocate(20);
		data.putShort((short)0);
		data.putInt(22);
		data.putInt(0);
		data.putInt(0x1F600);
		data.putInt(1);
		data.putShort((short)43);

		CMap10 cmap = new CMap10(new ByteBufferReader(data.array()));
		assertEquals(cmap.findGlyphIndex(0x1F600), 43);
		assertEquals(cmap.findGlyphIndex(0x1F601), -1);
	}


	@Test
	public void testCMap13ConstantGlyphGroup()
	{
		ByteBuffer data = ByteBuffer.allocate(26);
		data.putShort((short)0);
		data.putInt(28);
		data.putInt(0);
		data.putInt(1);
		data.putInt(0x1F600);
		data.putInt(0x1F601);
		data.putInt(42);

		CMap13 cmap = new CMap13(new ByteBufferReader(data.array()));
		assertEquals(cmap.findGlyphIndex(0x1F600), 42);
		assertEquals(cmap.findGlyphIndex(0x1F601), 42);
		assertEquals(cmap.findGlyphIndex(0x1F602), -1);
	}


	@Test
	public void testMacRomanNameRecord()
	{
		ByteBuffer data = ByteBuffer.allocate(13);
		data.putShort((short)1);
		data.putShort((short)0);
		data.putShort((short)0);
		data.putShort((short)4);
		data.putShort((short)1);
		data.putShort((short)0);
		data.put((byte)0x80);

		NameRecord record = new NameRecord(new ByteBufferReader(data.array()), 12, 13);
		assertEquals(record.value, "Ä");
	}


	@Test
	public void testFwordIsFontUnitInteger()
	{
		assertEquals(new ByteBufferReader(new byte[] {(byte)0xFF, (byte)0x88}).getFword(), -120f);
	}


	@Test(expectedExceptions = IllegalStateException.class)
	public void testReaderRejectsTruncatedValues()
	{
		new ByteBufferReader(new byte[] {0}).getUint16();
	}


	@Test
	public void testBundledFontMetrics() throws IOException
	{
		byte[] data;
		try (var input = TestMultipleFonts.class.getResourceAsStream("resources/VendSans-Regular.ttf"))
		{
			data = input.readAllBytes();
		}
		TrueTypeFont font = new TrueTypeFont(data);
		int glyph = font.findGlyphIndex('A');
		assertTrue(glyph > 0);
		assertTrue(font.getGlyphAdvanceWidth(glyph) > 0);
		assertTrue(font.getUnitsPerEm() >= 16);
	}
}