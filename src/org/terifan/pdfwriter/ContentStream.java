package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;


public class ContentStream implements Content
{
	private ArrayList<Content> mContents;


	public ContentStream()
	{
		mContents = new ArrayList<>();
	}


	public ContentStream add(Content aContent)
	{
		mContents.add(aContent);
		return this;
	}


	@Override
	public boolean isConsumed()
	{
		for (Content content : mContents)
		{
			if (!content.isConsumed())
			{
				return false;
			}
		}
		return true;
	}


	@Override
	public void reuseContent()
	{
		for (Content content : mContents)
		{
			content.reuseContent();
		}
	}


	@Override
	public void layout(double aX0, double aX1)
	{
		for (Content content : mContents)
		{
			content.layout(aX0, aX1);
		}
	}


//	@Override
	public double getWidth()
	{
		double width = 0;
		for (Content content : mContents)
		{
			width = Math.max(width, content.getWidth());
		}
		return width;
	}


	@Override
	public double getHeight()
	{
		double height = 0;
		for (Content content : mContents)
		{
			height += content.getHeight();
		}
		return height;
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aY0, double aX0, double aY1, double aX1) throws IOException
	{
		for (Content content : mContents)
		{
			aY0 = content.produce(aPDFWriter, aOutput, aPage, aY0, aX0, aY1, aX1);
		}

		return aY0;
	}
}
