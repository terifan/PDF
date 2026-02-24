package samples;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;


public class _UnpackPDFObjects
{
	public static void main(String... args)
	{
		try
		{
			try (FileInputStream in = new FileInputStream("c:\\home\\downloads\\Untitled document-5.pdf"))
			{
//				in.skip(13464);
				in.skip(197);
//				System.out.println(new String(unpack(in.readNBytes(305))));
				System.out.println(new String(unpack(in.readNBytes(333))));
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}


	private static byte[] unpack(byte[] aReadNBytes) throws IOException
	{
		try (InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(aReadNBytes)))
		{
			return in.readAllBytes();
		}
	}
}
