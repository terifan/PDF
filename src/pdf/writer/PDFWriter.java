package pdf.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import pdf.Array;
import pdf.Dictionary;
import pdf.FontRef;
import pdf.Ref;
import pdf.Struct;


public class PDFWriter implements AutoCloseable
{
	private LinkedHashMap<Integer, Integer> mReferences;
	private Array mPages;
	private Output mOutput;


	public PDFWriter(OutputStream aOutput) throws IOException
	{
		mOutput = new Output(aOutput);
		mReferences = new LinkedHashMap<>();
		mPages = new Array();

		mOutput.println("%PDF-1.3");
	}


	public void addPage(Ref aReference) throws IOException
	{
		mPages.add(aReference);
	}


	public Ref print(Struct aStruct) throws IOException
	{
		int reference = 1 + mReferences.size();
		mReferences.put(reference, mOutput.size());

		mOutput.println(reference + " 0 obj");

		Dictionary dictionary = aStruct.getDictionary();
		if (dictionary != null)
		{
			dictionary.writeTo(mOutput);
		}

		aStruct.write(mOutput);

		mOutput.println("endobj");

		return new Ref(reference);
	}


	@Override
	public void close() throws IOException
	{
		Ref pageList = print(new Struct().setDictionary(new Dictionary().put("/Count", 1).put("/Type", "/Pages").put("/Kids", mPages)));

		Ref root = print(new Struct().setDictionary(new Dictionary().put("/Type", "/Catalog").put("/Pages", pageList)));

		int offset = mOutput.size();

		mOutput.println("xref");
		mOutput.println(String.format("0 %d", 1 + mReferences.size()));
		mOutput.println("0000000000 65535 f");
		for (Integer i : mReferences.values())
		{
			mOutput.println(String.format("%010d 00000 n", i));
		}
		mOutput.println("trailer");
		new Dictionary().put("/Size", 1 + mReferences.size()).put("/Root", root).writeTo(mOutput);
		mOutput.println("startxref");
		mOutput.println(Integer.toString(offset));
		mOutput.println("%%EOF");

		mOutput.close();
	}


	public Ref registerFont(FontRef aFontRef) throws IOException
	{
		return print(new Struct(true, aFontRef));
	}
}
