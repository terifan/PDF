package font.truetype;


class ByteBufferReader
{
	private byte[] mData;
	private int mPosition;


	public ByteBufferReader(byte[] aData)
	{
		mData = aData;
	}


	public int length()
	{
		return mData.length;
	}


	public void position(int aPosition)
	{
		mPosition = aPosition;
	}


	public int position()
	{
		return mPosition;
	}


	public double getFixed()
	{
		return getInt32() / 65536.0;
	}


	public int getUint8()
	{
		try
		{
			return 0xFF & mData[mPosition++];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
	}


	public int getInt16()
	{
		int result = getUint16();
		if ((result & 0x8000) != 0)
		{
			result -= (1 << 16);
		}
		return result;
	}


	public int getUint16()
	{
		return (getUint8() << 8) | getUint8();
	}


	public int getInt32()
	{
		return (getUint8() << 24) | (getUint8() << 16) | (getUint8() << 8) | getUint8();
	}


	public long getUint32()
	{
		return getInt32() & 0xffffffffL;
	}


	public float getFword()
	{
		return getInt16() / 128f;
	}


	public long getDate()
	{
		return (getUint32() << 32) | getUint32();
	}


	public String getString(int aLength)
	{
		byte[] buf = new byte[aLength];

		for (int i = 0; i < aLength; i++)
		{
			buf[i] = mData[mPosition++];
		}

		if ((buf.length & 1) == 0 && buf[0] == 0)
		{
			char[] chars = new char[buf.length / 2];
			for (int i = 0, j = 0; i < buf.length; i+=2)
			{
				chars[j++] = (char)(256 * (0xff & buf[i + 0]) + (0xff & buf[i + 1]));
			}
			return new String(chars);
		}

		return new String(buf);
	}


	public int[] getUint16array(int aLength)
	{
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getUint16();
		}
		return buffer;
	}


	public int[] getInt16array(int aLength)
	{
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getInt16();
		}
		return buffer;
	}
}
