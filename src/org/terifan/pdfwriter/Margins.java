package org.terifan.pdfwriter;


public class Margins
{
	private double mWest, mNorth, mEast, mSouth;


	public Margins()
	{
		this(0, 0, 0, 0);
	}


	public Margins(double aMargin)
	{
		this(aMargin, aMargin, aMargin, aMargin);
	}


	public Margins(double aWest, double aNorth, double aEast, double aSouth)
	{
		mWest = aWest;
		mNorth = aNorth;
		mEast = aEast;
		mSouth = aSouth;
	}


	public double getWest()
	{
		return mWest;
	}


	public double getEast()
	{
		return mEast;
	}


	public double getNorth()
	{
		return mNorth;
	}


	public double getSouth()
	{
		return mSouth;
	}


	@Override
	public String toString()
	{
		return "west=" + mWest + ", north=" + mNorth + ", east=" + mEast + ", south=" + mSouth;
	}
}
