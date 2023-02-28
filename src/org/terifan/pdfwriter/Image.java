package org.terifan.pdfwriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import javax.imageio.ImageIO;


public class Image extends Resource
{
	private byte[] mData;
	private int mWidth;
	private int mHeight;
	private int mComponentCount;
	private int mBitsPerComponent;
	private Ref mResourceRef;
	private Format mFormat;
	private Margins mMargins;


	public enum Format
	{
		PNG,
		JPEG
	}


	public Image(byte[] aData, Format aFormat) throws IOException
	{
		mFormat = aFormat;
		mMargins = new Margins();

		setData(aData);
	}


	public void setData(byte[] aData) throws IOException
	{
		mData = aData;
		if (aData != null)
		{
			if (mFormat == Format.PNG)
			{
				preparePNG();
			}
			else
			{
				prepareJPEG();
			}
		}
	}


	public boolean isReady()
	{
		return mData != null;
	}


	public Margins getMargins()
	{
		return mMargins;
	}


	public Image setMargins(Margins aMargins)
	{
		this.mMargins = aMargins;
		return this;
	}


	@Override
	Ref print(PDFWriter aWriter) throws IOException
	{
		if (mResourceRef == null)
		{
			Dictionary dic = new Dictionary();
			dic.put("/Filter", mFormat == Format.PNG ? "/FlateDecode" : "/DCTDecode");
			dic.put("/Type", "/XObject");
			dic.put("/Subtype", "/Image");
			dic.put("/Width", mWidth);
			dic.put("/Height", mHeight);
			dic.put("/BitsPerComponent", mBitsPerComponent);
			dic.put("/Length", mData.length);

 			switch (mComponentCount)
			{
				case 1:
					dic.put("/ColorSpace", "/DeviceGray");
					break;
				case 3:
					dic.put("/ColorSpace", "/DeviceRGB");
					break;
				case 4:
					dic.put("/ColorSpace", "/DeviceCMYK");
					break;
				default:
					throw new IOException("Unsupported number of color channels in image: " + mComponentCount);
			}

			mResourceRef = aWriter.print(new Obj(false, new ArrayValue(mData), dic));
			mData = null;
		}

		return mResourceRef;
	}


	private void prepareJPEG() throws IOException
	{
		try (ByteArrayInputStream in = new ByteArrayInputStream(mData))
		{
			in.skip(4);

			do
			{
				int segmentLength = (in.read() << 8) + in.read() - 2;

				if (segmentLength < 0)
				{
					throw new IOException("SOF segment not found. File may be corrupt or not a JPEG image.");
				}

				in.skip(segmentLength);
			}
			while (in.read() != 0xff || in.read() != 0xc0);

			in.skip(3);

			mWidth = (in.read() << 8) + in.read();
			mHeight = (in.read() << 8) + in.read();
			mComponentCount = in.read();
			mBitsPerComponent = 8;
		}
	}


	private void preparePNG() throws IOException
	{
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(mData));
		mWidth = image.getWidth();
		mHeight = image.getHeight();

		if (image.getColorModel().getPixelSize() == 1)
		{
			mComponentCount = 1;
			mBitsPerComponent = 1;
		}
		else
		{
			mComponentCount = 3;
			mBitsPerComponent = 8;
		}

		ByteArrayOutputStream dstBuffer = new ByteArrayOutputStream();

		if (mComponentCount == 1)
		{
			byte[] temp = new byte[(image.getWidth() + 7) / 8];

			try (DeflaterOutputStream out = new DeflaterOutputStream(dstBuffer))
			{
				for (int y = 0; y < image.getHeight(); y++)
				{
					for (int x = 0, i = 0; x < image.getWidth(); x += 8, i++)
					{
						int c = 0;
						for (int z = 0; z < Math.min(8, image.getWidth() - x); z++)
						{
							c |= (image.getRGB(x + z, y) & 1) << (7 - z);
						}
						temp[i] = (byte)c;
					}

					out.write(temp);
				}
			}
		}
		else
		{
			try (DeflaterOutputStream out = new DeflaterOutputStream(dstBuffer))
			{
				byte[] temp = new byte[3 * image.getWidth()];

				for (int y = 0; y < image.getHeight(); y++)
				{
					for (int x = 0, i = 0; x < image.getWidth(); x++)
					{
						int c = image.getRGB(x, y);
						temp[i++] = (byte)(c >> 16);
						temp[i++] = (byte)(c >> 8);
						temp[i++] = (byte)(c);
					}

					out.write(temp);
				}
			}
		}

		mData = dstBuffer.toByteArray();
	}
}
