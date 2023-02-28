package samples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;


public class BarcodeGenerator
{
	private final static String[] CODESET =
	{
		"3113111131", "1133111131", "3133111111", "1113311131", "3113311111", "1133311111", "1113113131", "3113113111", "1133113111",
		"1113313111", "3111131131", "1131131131", "3131131111", "1111331131", "3111331111", "1131331111", "1111133131", "3111133111",
		"1131133111", "1111333111", "3111111331", "1131111331", "3131111311", "1111311331", "3111311311", "1131311311", "1111113331",
		"3111113311", "1131113311", "1111313311", "3311111131", "1331111131", "3331111111", "1311311131", "3311311111", "1331311111",
		"1311113131", "3311113111", "1331113111", "1313131111", "1313111311", "1311131311", "1113131311", "1311313111"
	};

	private static String ALPHABET = "1234567890" + "ABCDEFGHIJ" + "KLMNOPQRST" + "UVWXYZ-. $" + "/+%*";


	public static byte[] generate(String aMessage, int aHorizontalScale, int aHeight) throws IOException
	{
		Bitmap bitmap;

		if (aMessage == null)
		{
			bitmap = Bitmap.createBitmap(100, aHeight, Config.ARGB_8888);

			Paint paint = new Paint();
			paint.setColor(Color.WHITE);

			Canvas canvas = new Canvas(bitmap);
			canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
			canvas.isOpaque(); // fake call to make code compatible in MDS with Android
		}
		else
		{
			aMessage = "*" + aMessage.toUpperCase() + "*";

			bitmap = Bitmap.createBitmap((aMessage.length() * 16 - 1) * aHorizontalScale, aHeight, Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmap);

			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

			paint.setStyle(Paint.Style.STROKE);

			int x = 0;
			int y = 0;
			int h = bitmap.getHeight();

			for (int symbolIndex = 0; symbolIndex < aMessage.length(); symbolIndex++)
			{
				int ch = ALPHABET.indexOf(aMessage.charAt(symbolIndex));
				if (ch == -1)
				{
					throw new IllegalStateException("Unsupported symbol: " + aMessage.charAt(symbolIndex));
				}
				String code = CODESET[ch];
				for (int j = 0; j < code.length(); j++)
				{
					int w = code.charAt(j) - '0';
					paint.setColor((j & 1) == 0 ? Color.BLACK : Color.WHITE);
					canvas.drawRect(x, y, x + w, y + h, paint);
					x += w;
				}
			}

			canvas.isOpaque(); // fake call to make code compatible in MDS with Android
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
