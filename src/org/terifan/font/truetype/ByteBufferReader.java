package org.terifan.font.truetype;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


class ByteBufferReader
{
	private final byte[] mData;
	private int mPosition;


	public ByteBufferReader(byte[] aData)
	{
		mData = Objects.requireNonNull(aData, "aData");
	}

	public int length()
	{
		return mData.length;
	}


	public void position(int aPosition)
	{
		if (aPosition < 0 || aPosition > mData.length)
		{
			throw new IllegalArgumentException("Invalid buffer position: " + aPosition);
		}
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


	public int getInt8()
	{
		requireAvailable(1);
		return mData[mPosition++];
	}


	public int getUint8()
	{
		requireAvailable(1);
		return 0xFF & mData[mPosition++];
	}


	public int getInt16()
	{
		int result = getUint16();
		int s = result;

		if ((result & 0x8000) != 0)
		{
			result -= (1 << 16);
		}

		assert result == (short)s;

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
		return getInt16();
	}


	public long getDate()
	{
		return (getUint32() << 32) | getUint32();
	}


	public String getString(int aLength)
	{
		return new String(getByteArray(aLength), StandardCharsets.ISO_8859_1);
	}


	public int[] getUint16Array(int aLength)
	{
		requireArrayLength(aLength, 2);
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getUint16();
		}
		return buffer;
	}


	public int[] getInt16Array(int aLength)
	{
		requireArrayLength(aLength, 2);
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getInt16();
		}
		return buffer;
	}


	public int[] getUint8Array(int aLength)
	{
		requireArrayLength(aLength, 1);
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getUint8();
		}
		return buffer;
	}


	public byte[] getByteArray(int aLength)
	{
		requireArrayLength(aLength, 1);
		byte[] buffer = new byte[aLength];
		System.arraycopy(mData, mPosition, buffer, 0, aLength);
		mPosition += aLength;
		return buffer;
	}


	public int[] getInt8Array(int aLength)
	{
		requireArrayLength(aLength, 1);
		int[] buffer = new int[aLength];
		for (int i = 0; i < buffer.length; i++)
		{
			buffer[i] = getInt8();
		}
		return buffer;
	}


	private void requireArrayLength(int aLength, int aElementSize)
	{
		if (aLength < 0 || aLength > Integer.MAX_VALUE / aElementSize)
		{
			throw new IllegalArgumentException("Invalid array length: " + aLength);
		}
		requireAvailable(aLength * aElementSize);
	}


	private void requireAvailable(int aLength)
	{
		if (aLength < 0 || mPosition > mData.length - aLength)
		{
			throw new IllegalStateException("Unexpected end of font data at offset " + mPosition + ", requested " + aLength + " bytes");
		}
	}
}
