package pdf;

import font.FontFile;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;
import pdf.writer.Output;


public class FontRef implements Value
{
	private FontFile mFontFile;
	private String mIdentity;
	private TreeMap<Character, Integer> mSymbolMap;


	public FontRef(FontFile aFontFile, String aIdentity)
	{
		mFontFile = aFontFile;
		mIdentity = aIdentity;
		mSymbolMap = new TreeMap<>();
	}


	public FontFile getFontFile()
	{
		return mFontFile;
	}


	public String getIdentity()
	{
		return mIdentity;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		aOutput.println("/CIDInit /ProcSet findresource begin");
		aOutput.println("12 dict begin");
		aOutput.println("begincmap");
		aOutput.println("/CIDSystemInfo");
		aOutput.println("<< /Registry (Adobe) /Ordering (UCS) /Supplement 0 >> def");
		aOutput.println("/CMapName /Adobe-Identity-UCS def");
		aOutput.println("/CMapType 2 def");
		aOutput.println("1 begincodespacerange");
		aOutput.println("<0000> <FFFF>");
		aOutput.println("endcodespacerange");
		aOutput.println(mSymbolMap.size() + " beginbfchar");

		for (Entry<Character, Integer> entry : mSymbolMap.entrySet())
		{
			aOutput.println(String.format("<%04x> <%04x>", entry.getValue(), (int)entry.getKey()));
		}

		aOutput.println("endbfchar");
		aOutput.println("endcmap");
		aOutput.println("CMapName currentdict /CMap defineresource pop");
		aOutput.println("end");
		aOutput.println("end");
	}


	public int lookup(Symbol aSymbol)
	{
		int symbol = aSymbol.getSymbol();
		mSymbolMap.put(aSymbol.getCharacter(), symbol);
		return symbol;
	}
}
