package pdf.writer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import pdf.Array;
import pdf.Dictionary;
import pdf.Ref;
import pdf.Struct;


public class PDFWriter implements AutoCloseable
{
	private ByteArrayOutputStream mOutput;
	private LinkedHashMap<Integer, Integer> mObjectOffsets;
	private Array mPages;


	public PDFWriter(ByteArrayOutputStream aOutput) throws IOException
	{
		mOutput = aOutput;
		mObjectOffsets = new LinkedHashMap<>();
		mPages = new Array();

		println("%PDF-1.3");
	}


	public void addPage(Ref aReference) throws IOException
	{
		mPages.add(aReference);
	}


	public Ref print(Struct aStruct) throws IOException
	{
		int reference = 1 + mObjectOffsets.size();
		mObjectOffsets.put(reference, mOutput.size());

		println(reference + " 0 obj");
		if (aStruct.getDictionary() != null)
		{
			println(aStruct.getDictionary());
		}
		if (aStruct.getContent() != null)
		{
			println("stream");
			mOutput.write(aStruct.getContent());
			println("endstream");
		}
		println("endobj");

		return new Ref(reference);
	}


	@Override
	public void close() throws IOException
	{
		Ref pageList = print(new Struct().setDictionary(new Dictionary().put("/Count", 1).put("/Type", "/Pages").put("/Kids", mPages)));

		Ref root = print(new Struct().setDictionary(new Dictionary().put("/Type", "/Catalog").put("/Pages", pageList)));

		int offset = mOutput.size();

		println("xref");
		println("0 " + (1 + mObjectOffsets.size()));
		println("0000000000 65535 f");
		for (Integer i : mObjectOffsets.values())
		{
			println(String.format("%010d", i) + " 00000 n");
		}
		println("trailer");
		println(new Dictionary().put("/Size", 1 + mObjectOffsets.size()).put("/Root", root));
		println("startxref");
		println("" + offset);
		println("%%EOF");

		mOutput.close();
	}


	public void writeTo(String aFile) throws IOException
	{
		try (FileOutputStream f = new FileOutputStream(aFile))
		{
			mOutput.writeTo(f);
		}
	}


	private void println(Object aText) throws IOException
	{
		mOutput.write(aText.toString().getBytes());
		mOutput.write('\n');
	}
}
