package samples;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Anchor;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Image;
import org.terifan.pdfwriter.ImageArea;
import org.terifan.pdfwriter.Line;
import org.terifan.pdfwriter.Margins;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Rectangle;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Table;
import org.terifan.pdfwriter.TableArea;
import org.terifan.pdfwriter.TextArea;
import org.terifan.pdfwriter.VerticalAlignment;


public class TestWaybill
{
	public static void main(String... args)
	{
		try
		{
			Font font1 = new ExtendedFont(TestWaybill.class.getResourceAsStream("opensans.ttf").readAllBytes());
			Font font2 = new ExtendedFont(TestWaybill.class.getResourceAsStream("malgun.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(new FileOutputStream("d:\\output.pdf")).setCompress(true))
			{
				Style style0 = new Style(font1, 7);
				Style style1 = new Style(font1, 9);
				Style style2 = new Style(font1, 13);
				Style style3 = new Style(font2, 9);
				Style style4 = new Style(font1, 11);

				Margins margins1 = new Margins(4, 4, 4, 4);
				Margins margins2 = new Margins(2, 8, 2, 4);
				Margins margins3 = new Margins(6, 4, 2, 2);
				Margins margins4 = new Margins(2, 2, 2, 2);
				Margins margins5 = new Margins(4, 4, 12, 4);
				Margins margins6 = new Margins(4, 8, 12, 4);

				Paragraph transportInfo = new Paragraph(style1, "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla").setMargins(margins1);
//				Paragraph transportInfo = new Paragraph(style1, "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla").setMargins(margins1);

				Table productTable = new Table(0.25, 0.35, 0.1, 0.13, 0.1, 0.07)
					.setRepeatHeader(true)
					.setVerticalGridColor(Color.BLACK)
					.setVerticalGridThickness(1.0)
					.setHeaderGridThickness(1.0)
					.setHeaderGridColor(Color.BLACK)
					.setHeader(
						new Paragraph(style0, "Marks and numbers").setMargins(margins4),
						new Paragraph(style0, "Goods description / reference").setMargins(margins4),
						new Paragraph(new Span(style0, "Qty."), new Span(style0, "Pkg.")).setAlignment(Alignment.SPLIT).setMargins(margins4),
						new Paragraph(style0, "Gross weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "Volume, m³").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "LDM").setAlignment(Alignment.RIGHT).setMargins(margins4)
					);

				for (int i = 0; i < 35; i++)
				{
					productTable.addRow(
						new Paragraph(style1, "CPH5002074").setMargins(margins4),
						new Paragraph(style1, "50548 qwerty qwerty qwerty qwerty qwerty").setMargins(margins4),
						new Paragraph(new Span(style1, "1"), new Span(style1, "XP")).setAlignment(Alignment.SPLIT).setMargins(margins4),
						new Paragraph(style1, "6.00").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style1, "0.04").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style1, "-").setAlignment(Alignment.RIGHT).setMargins(margins4)
					);
				}

				Table footerTable = new Table(0.25, 0.175, 0.175, 0.1, 0.13, 0.1, 0.07)
					.setRepeatHeader(true)
					.setVerticalGridColor(Color.BLACK)
					.setVerticalGridThickness(1.0)
					.setHeader(
						new Paragraph(style0, "Delivery terms").setMargins(margins4),
						new Paragraph(style0, "Calculated weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "Total").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "Quantity").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "Gross weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "Volume, m³").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "LDM").setAlignment(Alignment.RIGHT).setMargins(margins4)
					)
					.addRow(new Paragraph(style1, "001").setMargins(margins4),
						new Paragraph(style0, "15").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "1").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "1").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "6.00").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "0.040").setAlignment(Alignment.RIGHT).setMargins(margins4),
						new Paragraph(style0, "0.00").setAlignment(Alignment.RIGHT).setMargins(margins4)
					);

				boolean firstPage = true;

				while (!transportInfo.isConsumed() || !productTable.isConsumed())
				{
					try (Page page = pdf.addPage())
					{
						page.append(new Rectangle(40, 775, 550, 35, 1.0, 10, Color.BLACK, null));
						page.append(new Line(300, 775, 300, 460, 1.0, Color.BLACK));
						page.append(new Line(420, 775, 420, 755, 1.0, Color.BLACK));
						page.append(new Line(300, 755, 550, 755, 1.0, Color.BLACK));
						page.append(new Line(300, 670, 550, 670, 1.0, Color.BLACK));
						page.append(new Line(300, 590, 550, 590, 1.0, Color.BLACK));
						page.append(new Line(40, 695, 300, 695, 1.0, Color.BLACK));
						page.append(new Line(40, 620, 300, 620, 1.0, Color.BLACK));
						page.append(new Line(40, 545, 300, 545, 1.0, Color.BLACK));
						page.append(new Line(40, 461, 550, 461, 2.0, Color.BLACK));

						page.append(new ImageArea(40, 805, 150, 785, new Image(Files.readAllBytes(Paths.get("C:\\netbeans\\mds\\mdsserver\\src\\com\\surikat\\dips\\message_queue_consumer\\pdf_template\\schenker_logo.png")), Image.Format.PNG)));

						page.append(new TextArea(40, 805, 540, 775, new Paragraph(style1, "1 of 1").setAlignment(Alignment.RIGHT), new Paragraph(style2, "Domestic Waybill")).setAnchor(Anchor.EAST));

						page.append(new TextArea(40, 775, 300, 695,
							new Paragraph(style1, "Shipper:").setMargins(margins5),
							new Paragraph(style2, "Meža Mājas ZS").setMargins(margins2),
							new Paragraph(style2, "Līvānu nov. Rožupes pag.").setMargins(margins2),
							new Paragraph(new Span(style2, "Rožupe, Meža māja"), new Span(style2, "50401 CZ")).setAlignment(Alignment.SPLIT).setMargins(margins2)
						));

						page.append(new TextArea(40, 695, 300, 620,
							new Paragraph(style1, "Loading address:").setMargins(margins5),
							new Paragraph(style2, "если вы имеете ").setMargins(margins2),
							new Paragraph(style2, "о других, то также произойдет").setMargins(margins2),
							new Paragraph(new Span(style2, "такое же мнение"), new Span(style2, "50401 CZ")).setAlignment(Alignment.SPLIT).setMargins(margins2)
						));

						page.append(new TextArea(40, 620, 300, 545,
							new Paragraph(style1, "Receiver:").setMargins(margins5),
							new Paragraph(style2, "δικαστὰς περὶ").setMargins(margins2),
							new Paragraph(style2, "τούτου τοῦ").setMargins(margins2),
							new Paragraph(new Span(style2, "νώμην"), new Span(style2, "50401 CZ")).setAlignment(Alignment.SPLIT).setMargins(margins2)
						));

						page.append(new TextArea(40, 545, 300, 465,
							new Paragraph(style1, "Delivery address:").setMargins(margins5),
							new Paragraph(style3, "내가 보았노라").setMargins(margins2),
							new Paragraph(style3, "아 이 일을 심").setMargins(margins2),
							new Paragraph(new Span(style3, "나리니 네가 "), new Span(style2, "50401 CZ")).setAlignment(Alignment.SPLIT).setMargins(margins2).setVerticalAlignment(VerticalAlignment.TOP)
						));

						page.append(new TextArea(300, 775, 420, 755, new Paragraph(new Span(style1, "Date: "), new Span(style4, "20202002")).setMargins(margins3)).setAnchor(Anchor.WEST));
						page.append(new TextArea(420, 775, 550, 755, new Paragraph(new Span(style1, "Order type: "), new Span(style4, "*CO")).setMargins(margins3)).setAnchor(Anchor.WEST));
						page.append(new TextArea(300, 755, 420, 670, new Paragraph(style1, "Consignment ID:").setMargins(margins1), new Paragraph(style2, "CPH5002074 ").setMargins(margins6)));
						page.append(new TextArea(420, 755, 550, 670, new Paragraph(style1, "Trip No:").setMargins(margins1), new Paragraph(style2, "CPHX17454").setMargins(margins6)));
						page.append(new TextArea(300, 755, 550, 670, new Paragraph(new Span(style1, "Waybill: "), new Span(style4, "144017595972")).setMargins(margins3)).setAnchor(Anchor.SOUTH_WEST));
						page.append(new TextArea(300, 670, 450, 590, new Paragraph(style1, "Carrier:").setMargins(margins1), new Paragraph(style2, "olles bil").setMargins(margins2)));
						page.append(new TextArea(450, 670, 550, 590, new Paragraph(style1, "Unit:").setMargins(margins1), new Paragraph(style2, "YE0038").setMargins(margins2)));
						page.append(new TextArea(300, 640, 550, 590, new Paragraph(style1, "Phone:").setMargins(margins1), new Paragraph(style2, "32131321321").setMargins(margins2)));
						page.append(new TextArea(300, 590, 375, 465, new Paragraph(style1, "Equipment:").setMargins(margins1), new Paragraph(style2, "BlueTruck").setMargins(margins2)));
						page.append(new TextArea(375, 590, 450, 465, new Paragraph(style1, "ID:").setMargins(margins1), new Paragraph(style2, "123").setMargins(margins2)));
						page.append(new TextArea(450, 590, 530, 465, new Paragraph(style1, "License plate:").setMargins(margins1), new Paragraph(style2, "ABC123").setMargins(margins2)));

						if (firstPage)
						{
							page.append(new Line(40, 200, 550, 200, 1.0, Color.BLACK));
							page.append(new Line(40, 115, 550, 115, 1.0, Color.BLACK));
							page.append(new Line(300, 200, 300, 35, 1.0, Color.BLACK));
							page.append(new Line(420, 200, 420, 35, 1.0, Color.BLACK));

							page.append(new TextArea(40, 200, 300, 115, new Paragraph(style1, "Loading date and time:").setMargins(margins1), new Paragraph(style1, "2015-04-21 20:26:09").setMargins(margins6)));
							page.append(new TextArea(170, 200, 300, 115, new Paragraph(style1, "Remarks reason:").setMargins(margins1), new Paragraph(style1, "FM").setMargins(margins6)));
							page.append(new TextArea(40, 170, 300, 115, new Paragraph(style1, "Remarks:").setMargins(margins1), new Paragraph(style1, "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla").setMargins(margins2)));

							page.append(new TextArea(40, 115, 300, 35, new Paragraph(style1, "Delivery date and time:").setMargins(margins1), new Paragraph(style1, "2015-04-21 20:26:09").setMargins(margins6)));
							page.append(new TextArea(170, 115, 300, 35, new Paragraph(style1, "Remarks reason:").setMargins(margins1), new Paragraph(style1, "FM").setMargins(margins6)));
							page.append(new TextArea(40, 85, 300, 35, new Paragraph(style1, "Remarks:").setMargins(margins1), new Paragraph(style1, "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla").setMargins(margins2)));

							page.append(new TextArea(300, 200, 420, 115, new Paragraph(style1, "Shipper:").setMargins(margins1)));
							page.append(new TextArea(300, 200, 420, 115, new Paragraph(style1, "Name: ", "Olle").setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));

							page.append(new TextArea(300, 115, 420, 35, new Paragraph(style1, "Receiver:").setMargins(margins1)));
							page.append(new TextArea(300, 115, 420, 35, new Paragraph(style1, "Name: ", "Olle").setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));

							page.append(new TextArea(420, 200, 550, 115, new Paragraph(style1, "Carrier:").setMargins(margins1)));
							page.append(new TextArea(420, 200, 550, 115, new Paragraph(style1, "Name: ", "Olle").setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));

							page.append(new TextArea(420, 115, 550, 35, new Paragraph(style1, "Deliverer:").setMargins(margins1)));
							page.append(new TextArea(420, 115, 550, 35, new Paragraph(style1, "Name: ", "Olle").setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));

							firstPage = false;

							page.append(new Line(40, 295, 550, 295, 1.0, Color.BLACK));
							page.append(new Line(40, 270, 550, 270, 1.0, Color.BLACK));

							page.append(new TableArea(40, 460, 550, 295, productTable));
							page.append(new TableArea(40, 295, 550, 270, footerTable));

							page.append(new TextArea(40, 270, 550, 200, transportInfo));
						}
						else if (!productTable.isConsumed() && !transportInfo.isConsumed())
						{
							footerTable.reuseContent();

							page.append(new Line(40, 295, 550, 295, 1.0, Color.BLACK));
							page.append(new Line(40, 270, 550, 270, 1.0, Color.BLACK));

							page.append(new TableArea(40, 460, 550, 295, productTable));
							page.append(new TableArea(40, 295, 550, 270, footerTable));

							page.append(new TextArea(40, 270, 550, 45, transportInfo));
						}
						else if (!productTable.isConsumed())
						{
							footerTable.reuseContent();

							page.append(new Line(40, 60, 550, 60, 1.0, Color.BLACK));

							page.append(new TableArea(40, 460, 550, 60, productTable));
							page.append(new TableArea(40, 60, 550, 35, footerTable));
						}
						else
						{
							page.append(new TextArea(40, 460, 550, 200, transportInfo));
						}
					}
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
