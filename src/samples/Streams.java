package samples;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.nio.ByteBuffer;


/*
 * Utility class that simplifies stream handling. The transfer methods accept:
 * <p>
 * Input:
 * <ol>
 *  <li>java.io.InputStream</li>
 *  <li>java.io.File</li>
 *  <li>java.io.CharSequence</li>
 *  <li>java.lang.String (file path)</li>
 *  <li>java.net.URL</li>
 *  <li>java.nio.ByteBuffer</li>
 *  <li>java.io.RandomAccessFile</li>
 *  <li>byte array</li>
 * </ol>
 *
 * Output:
 * <ol>
 *  <li>java.io.OutputStream</li>
 *  <li>java.io.File</li>
 *  <li>java.lang.String (file path)</li>
 *  <li>java.nio.ByteBuffer</li>
 *  <li>org.w3c.dom.Document</li>
 *  <li>byte array</li>
 * </ol>
 *
 * This sample will copy a file:
 *
 * <pre>
 * Streams.transfer("myfile.txt", "copy of myfile.txt"));
 * </pre>
 * </p>
 */
public final class Streams
{
	private Streams()
	{
	}


	public static long transfer(Object aInput, Object aOutput)
	{
		return transfer(Long.MAX_VALUE, true, true, aInput, aOutput);
	}


	public static long transfer(boolean aCloseInput, boolean aCloseOutput, Object aInput, Object aOutput)
	{
		return transfer(Long.MAX_VALUE, aCloseInput, aCloseOutput, aInput, aOutput);
	}


	public static long transfer(long aLimit, Object aInput, Object aOutput)
	{
		return transfer(aLimit, true, true, aInput, aOutput);
	}


	public static long transfer(long aLimit, boolean aCloseInput, boolean aCloseOutput, Object aInput, Object aOutput)
	{
		boolean doCloseInput = true;
		boolean doCloseOutput = true;

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try
		{
			// setup input

			if (aInput instanceof InputStream)
			{
				inputStream = (InputStream)aInput;
				doCloseInput = aCloseInput;
			}
			else if (aInput instanceof URL) inputStream = ((URL)aInput).openStream();
			else if (aInput instanceof File) inputStream = new BufferedInputStream(new FileInputStream((File)aInput));
			else if (aInput instanceof String) inputStream = new FileInputStream((String)aInput);
			else if (aInput instanceof CharSequence) inputStream = new ByteArrayInputStream(((CharSequence)aInput).toString().getBytes());
			else if (aInput instanceof byte[]) inputStream = new ByteArrayInputStream((byte[])aInput);
			else if (aInput == null) throw new IOException("Unsupported input type: null");
			else throw new IOException("Unsupported input type: " + aInput.getClass());

			// setup output

			if (aOutput instanceof OutputStream)
			{
				outputStream = (OutputStream)aOutput;
				doCloseOutput = aCloseOutput;
			}
			else if (aOutput instanceof File) outputStream = new FileOutputStream((File)aOutput);
			else if (aOutput instanceof String) outputStream = new FileOutputStream((String)aOutput);
			else if (aOutput instanceof Document) outputStream = new ByteArrayOutputStream();
			else if (aOutput == null) throw new IOException("Unsupported output type: null");
			else throw new IOException("Unsupported output type: " + aOutput.getClass());

			// perform transfer

			long total = 0;
			byte [] buffer = new byte[4096];

			while (aLimit > 0)
			{
				int len = inputStream.read(buffer, 0, (int)Math.min(buffer.length, aLimit));

				if (len <= 0)
				{
					break;
				}

				outputStream.write(buffer, 0, len);

				total += len;
				aLimit -= len;
			}

			// post processing of output data

			if (aOutput instanceof Node)
			{
				try
				{
					Document sourceDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(((ByteArrayOutputStream)outputStream).toByteArray()));
					Document targetDoc = (Document)aOutput;

					Transformer tx   = TransformerFactory.newInstance().newTransformer();
					DOMSource source = new DOMSource(sourceDoc);
					DOMResult result = new DOMResult(targetDoc);
					tx.transform(source,result);
				}
				catch (RuntimeException e)
				{
					throw e;
				}
				catch (Error | Exception e)
				{
					throw new RuntimeException("Error transforming XML", e);
				}
			}

			return total;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Unhandled exception", e);
		}
		finally
		{
			// ensure all streams are closed

			if (doCloseInput && inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (Error | Exception e)
				{
				}
			}
			if (doCloseOutput && outputStream != null)
			{
				try
				{
					outputStream.close();
				}
				catch (Error | Exception e)
				{
				}
			}
		}
	}


	public static void transfer(InputStream aIn, OutputStream aOut, boolean aClose)
	{
		transfer(aClose, aClose, aIn, aOut);
	}


	public static byte [] readAll(Object aInput)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		transfer(aInput, baos);
		return baos.toByteArray();
	}
}