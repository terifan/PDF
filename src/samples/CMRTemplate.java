package samples;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Anchor;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.Style;
import org.terifan.pdfwriter.ExtendedFont;
import org.terifan.pdfwriter.Font;
import org.terifan.pdfwriter.Image;
import org.terifan.pdfwriter.Image.Format;
import org.terifan.pdfwriter.ImageArea;
import org.terifan.pdfwriter.Line;
import org.terifan.pdfwriter.Insets;
import org.terifan.pdfwriter.Paragraph;
import org.terifan.pdfwriter.PDFWriter;
import org.terifan.pdfwriter.Page;
import org.terifan.pdfwriter.Rectangle;
import org.terifan.pdfwriter.Span;
import org.terifan.pdfwriter.Table;
import org.terifan.pdfwriter.TableArea;
import org.terifan.pdfwriter.TableRow;
import org.terifan.pdfwriter.TextArea;


public class CMRTemplate
{
	public void generate(OutputStream aOutputStream, String aLanguage, CMRDocument aDocument)
	{
		try
		{
			Font font1 = new ExtendedFont(CMRTest.class.getResourceAsStream("resources/DMMono-Regular.ttf").readAllBytes());
//			Font font1 = new ExtendedFont(CMRTest.class.getResourceAsStream("resources/Segoeui-Regular.ttf").readAllBytes());
//			Font font2 = new ExtendedFont(CMRTest.class.getResourceAsStream("resources/Segoeui-Bold.ttf").readAllBytes());

			try (PDFWriter pdf = new PDFWriter(aOutputStream).setCompress(!true))
			{
				Style style0 = new Style(font1, 18.666).setLineExtra(2);
//				Style style1 = new Style(font1, 6);
//				Style style2 = new Style(font1, 10);
//				Style style5 = new Style(font2, 13).setLineExtra(4);
//				Style style7 = new Style(font2, 11).setCharacterSpacing(0.9);
//
//				Insets margins1 = new Insets(1, 4, 1, 4);
//				Insets margins2 = new Insets(2, 2, 2, 2);
//				Insets margins3 = new Insets(6, 15, 6, 15);

//				Table productTable = new Table(1.3, 1.3, 1.1, 1.3, 1.2, 1.1, 1)
//					.setExtendTableEnabled(true)
//					.setRepeatHeader(true)
//					.setVerticalGridColor(Color.BLACK)
//					.setVerticalGridThickness(1.0)
//					.setHeaderSpacing(5)
//					.setHeader(new TableRow(
//						createHeading(style1, style5, 6, "Marks and number", "Kennzeichen nummern", false, 6),
//						createHeading(style1, style5, 7, "Number of packages", "Anzahl der packstücke", false, 6),
//						createHeading(style1, style5, 8, "Method of packing", "Art der verpackung", false, 6),
//						createHeading(style1, style5, 9, "Nature of the goods", "Bezeichnung des gutes", false, 6),
//						createHeading(style1, style5, 10, "Statistical number", "Statistiknumer", false, 3),
//						createHeading(style1, style5, 11, "Gross weight kg.", "Bruttogew. in kg", false, 3),
//						createHeading(style1, style5, 12, "Volume in m³", "Umfang in m³", false, 3)
//					));
//
//				if (aDocument.getGoodsMarksAndNos() != null)
//				{
//					for (int i = 0; i < aDocument.getGoodsMarksAndNos().size(); i++)
//					{
//						productTable.addRow(
//							new Paragraph(style1, aDocument.getGoodsMarksAndNos().get(i)).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsNumberOfPackages().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsMethodOfPacking().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsNatureOfGoods().get(i)).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsStatisticsNo().get(i)).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsGrossWeight().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2),
//							new Paragraph(style1, aDocument.getGoodsVolume().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2)
//						);
//					}
//				}
//
//				Paragraph senderInstructions;
//				if (aDocument.getInstructions() == null || aDocument.getInstructions().isEmpty())
//				{
//					senderInstructions = new Paragraph(style2, "");
//				}
//				else
//				{
//					senderInstructions = new Paragraph(style2, aDocument.getInstructions() == null ? null : aDocument.getInstructions().stream().map(e -> e.text).collect(Collectors.toList()));
//				}

				boolean firstPage = true;

//				while (!productTable.isConsumed() || !senderInstructions.isConsumed())
				{
					try (Page page = pdf.addPage())
					{
//						page.append(new Rectangle(40, 775, 555, 35, 1.0, 10, Color.BLACK, null));
//						page.append(new Line(40, 712, 555, 712, 1.0, Color.BLACK));
//						page.append(new Line(40, 651, 555, 651, 1.0, Color.BLACK));
//						page.append(new Line(40, 587, 555, 587, 1.0, Color.BLACK));
//						page.append(new Line(40, 524, 555, 524, 1.0, Color.BLACK));
//						page.append(new Line(40, 461, 555, 461, 2.0, Color.BLACK));
//						page.append(new Line(300, 775, 300, 460, 1.0, Color.BLACK));
//
//						page.append(new ImageArea(40, 805, 150, 785, new Image(aDocument.getCompanyLogo(), Image.Format.PNG)));
//
//						page.append(new TextArea(40, 805, 555, 775,
//							new Paragraph(style7, "INTERNATIONAL CONSIGNMENT NOTE").setAlignment(Alignment.RIGHT).setMargins(margins2),
//							new Paragraph(style7, "LETTRE DE VOITURE INTERNATIONALE").setAlignment(Alignment.RIGHT).setMargins(margins2)
//						).setAnchor(Anchor.EAST));
//
//						page.append(new TextArea(40, 35, 555, 0,
//							new Paragraph(style0, "* In case of dangerous goods mention, besides the possible certification, on the last line of the column the particulars of the class, the number and the letter, if any.").setMargins(margins2),
//							new Paragraph(style0, "* Bei gefährlichen Gütem ist, ausser der eventuellen Bescheinigung, auf der letzen Linie der Rubrik anzugeben: die Klasse, die Ziffer, sowie gegenfalls der Buchstabe.").setMargins(margins2)
//						));
//
//						page.append(new TableArea(300 + 5, 775 - 5, 555 - 10, 712 - 10, new Table(0.4, 1)
//							.setExtendTableEnabled(true)
//							.setRowSpacing(3)
//							.addRow(new Paragraph(style0, "INTERNATIONALER FRACHTBRIEF"), new Paragraph(style0, "Diese Beförderung unterliegt trotz einer gegenteiligen Abmachung den Bestimmungen des Übereinkommens über den Beförderungsvertrag im internat. Straßengüterverkehr (CMR)"))
//							.addRow(new Paragraph(style0, "LETTRE DE VOITURE INTERNATIONAL"), new Paragraph(style0, "Ce transport est soumis, nonobstant toute clause contraire, á la Convention relative au contrat de transport international de marchandises par route (CMR)"))
//							.addRow(new Paragraph(style0, "INTERNATIONAL WAYBILL"), new Paragraph(style0, "This transport is subject despite a contrary agreement that Regulations of the convention over the transport contract in internat. Road haulage (CMR)"))
//						));
//
//						page.append(new TableArea(300, 712, 555, 651, new Table(1, 1, 1)
//							.setExtendTableEnabled(true)
//							.addRow(new Paragraph(style1, "Consignment ID / Sendungs ID").setMargins(margins1), new Paragraph(style1, "Trip No / Reise No").setMargins(margins1), new Paragraph(style1, "Domestic Waybill No / Frachtbrief Nr").setMargins(margins1))
//							.addRow(new Paragraph(style2, aDocument.getConsignmentId()).setMargins(margins1), new Paragraph(style2, aDocument.getTripNo()).setMargins(margins1), new Paragraph(style2, aDocument.getWaybill()).setMargins(margins1))
//						));
//
//						page.append(new ImageArea(300, 685, 555, 651, new Image(aDocument.getBarcode(), Format.PNG).setMargins(margins3)));
//
//						appendSectionHeader(page, style1, style5, 1, "Sender (name, addresse, country)", "Absender (name, anschrift, land)", 40, 775, 300, 712,
//							new Paragraph(style2, aDocument.getShipperAddress()).setMargins(margins1),
//							new Paragraph(style2, aDocument.getShipperCity()).setMargins(margins1),
//							new Paragraph(new Span(style2, aDocument.getShipperStreet()), new Span(style2, aDocument.getShipperPostalCode() + " " + aDocument.getShipperCountryCode())).setAlignment(Alignment.SPLIT).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 2, "Consignee (name, address, country)", "Empfänger (name, anschrift, land)", 40, 712, 300, 651,
//							new Paragraph(style2, aDocument.getLoadAddress()).setMargins(margins1),
//							new Paragraph(style2, aDocument.getLoadCity()).setMargins(margins1),
//							new Paragraph(new Span(style2, aDocument.getLoadStreet()), new Span(style2, aDocument.getLoadPostalCode() + " " + aDocument.getLoadCountryCode())).setAlignment(Alignment.SPLIT).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 3, "Place of delivery (place, country)", "Auslieferungsort (ort, land)", 40, 651, 300, 587,
//							new Paragraph(style2, aDocument.getConsigneeAddress()).setMargins(margins1),
//							new Paragraph(style2, aDocument.getConsigneeCity()).setMargins(margins1),
//							new Paragraph(new Span(style2, aDocument.getConsigneeStreet()), new Span(style2, aDocument.getConsigneePostalCode() + " " + aDocument.getConsigneeCountryCode())).setAlignment(Alignment.SPLIT).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 4, "Place of taking over the goods (place, country)", "Ort der übernahme des gutes (ort, land)", 40, 587, 300, 524,
//							new Paragraph(style2, aDocument.getUnloadAddress()).setMargins(margins1),
//							new Paragraph(style2, aDocument.getUnloadCity()).setMargins(margins1),
//							new Paragraph(new Span(style2, aDocument.getUnloadStreet()), new Span(style2, aDocument.getUnloadPostalCode() + " " + aDocument.getUnloadCountryCode())).setAlignment(Alignment.SPLIT).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 5, "Documents attached", "Beigefügte dokumente", 40, 524, 300, 461,
//							new Paragraph(style2, aDocument.getAnnexDocuments()).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 16, "Carrier (name, address, country)", "Frachtführer (name, anschrift, land)", 300, 651, 555, 587,
//							new Paragraph(style2, aDocument.getCarrier()).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 17, "Successive carriers (name, address, country)", "Nachfolgende frachtführer (name, anschrift, land)", 300, 587, 555, 524,
//							new Paragraph(style2, aDocument.getSuccessiveCarriers()).setMargins(margins1)
//						);
//						appendSectionHeader(page, style1, style5, 18, "Carrer's reservations and observations", "Vorbehalte und bemerkungen der frachtführer", 300, 524, 555, 461,
//							new Paragraph(style2, aDocument.getShipmentRemarks()).setMargins(margins1)
//						);
//
//						if (firstPage)
						{
							firstPage = false;

//							page.append(new Line(40, 318, 555, 318, 1.0, Color.BLACK));
//							page.append(new Line(300, 283, 555, 283, 1.0, Color.BLACK));
//							page.append(new Line(300, 247, 555, 247, 1.0, Color.BLACK));
//							page.append(new Line(300, 318, 300, 175, 1.0, Color.BLACK));
//							page.append(new Line(40, 210, 555, 210, 1.0, Color.BLACK));
//							page.append(new Line(40, 175, 555, 175, 1.0, Color.BLACK));
//							page.append(new Line(210, 175, 210, 35, 1.0, Color.BLACK));
//							page.append(new Line(380, 175, 380, 35, 1.0, Color.BLACK));
//							page.append(new Line(40, 80, 555, 80, 0.5, Color.GRAY));
//
//							appendSectionHeader(page, style1, style5, 13, "Sender's instructions", "Anweisungen des absenders", 40, 318, 300, 210,
//								senderInstructions.setMargins(margins1)
//							);
//							appendSectionHeader(page, style1, style5, 14, "Instructions as to payment for carriage", "Anweisungen, wie die zahlung für die beförderung", 300, 318, 555, 283,
//								new Paragraph(style2, aDocument.getPaymentInstructions()).setMargins(margins1)
//							);
//							appendSectionHeader(page, style1, style5, 15, "The liability of the carriage is covered by the CMR", "Die haftung des wagens wird durch die CMR dachte", 300, 283, 555, 247,
//								new Paragraph(style2, aDocument.getLiability()).setMargins(margins1)
//							);
//							appendSectionHeader(page, style1, style5, 19, "Conditions of delivery", "Lieferbedingungen", 300, 247, 555, 210,
//								new Paragraph(style2, aDocument.getConditionsOfDelivery()).setMargins(margins1)
//							);
//							appendSectionHeader(page, style1, style5, 20, "Special agreement", "Besondere vereinbarungen", 40, 210, 300, 175,
//								new Paragraph(style2, aDocument.getSpecialAgreement()).setMargins(margins1)
//							);
//							appendSectionHeader(page, style1, style5, 21, "Established in", "Ausgefertigt in", 300, 210, 555, 175,
//								new Paragraph(style2, aDocument.getEstablished()).setMargins(margins1)
//							);
//
//							page.append(new TableArea(40, 460, 555, 318, productTable));
//
//							appendSectionHeader(page, style1, style5, 22, "Signature and stamp of the sender", "Unterschrift und stempel des absenders", 40 + 7, 175, 210, 35);
//							appendSectionHeader(page, style1, style5, 23, "Signature and stamp of the driver", "Unterschrift und stempel des frachtführers", 210 + 7, 175, 380, 35);
//							appendSectionHeader(page, style1, style5, 24, "Signature and stamp of the consignee", "Unterschrift und stempel des empfängers", 380 + 7, 175, 555, 35);

							page.append(new TableArea(40, 797, 210, 35, new Table(2, 3)
								.setExtendTableEnabled(true)
								.addRow(
									new Paragraph(style0, "Name " + aDocument.getSenderName())
								)
								.setCellPadding(new Insets(0, 5, 0, 5))
								.setRowSpacing(2)
							));

//							page.append(new TableArea(40, 97, 210, 35, new Table(2, 3)
//								.setExtendTableEnabled(true)
//								.addRow(new Paragraph(style0, "Name").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderName()))
//								.addRow(new Paragraph(style0, "Date/Datum").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderDateTime()))
//								.addRow(new Paragraph(style0, " ").setAlignment(Alignment.RIGHT), new Paragraph(style0, " "))
//								.addRow(new Paragraph(style0, "Device ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderDeviceNo()))
//								.addRow(new Paragraph(style0, "Truck ID / Lastwagen ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderTruckNo()))
//								.addRow(new Paragraph(style0, "Carrier / Spediteur").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderHaulierName()))
//								.addRow(new Paragraph(style0, "Driver / Fahrer").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderDriverName()))
//								.addRow(new Paragraph(style0, "Remark / Bemerkung").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getSenderRemark()))
//								.setCellPadding(new Insets(0, 5, 0, 5))
//								.setRowSpacing(2)
//							));

//							page.append(new TableArea(210, 97, 380, 35, new Table(2, 3)
//								.setExtendTableEnabled(true)
//								.addRow(new Paragraph(style0, "Name").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierName()))
//								.addRow(new Paragraph(style0, "Date/Datum").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierDateTime()))
//								.addRow(new Paragraph(style0, " ").setAlignment(Alignment.RIGHT), new Paragraph(style0, " "))
//								.addRow(new Paragraph(style0, "Device ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierDeviceNo()))
//								.addRow(new Paragraph(style0, "Truck ID / Lastwagen ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierTruckNo()))
//								.addRow(new Paragraph(style0, "Carrier / Spediteur").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierHaulierName()))
//								.addRow(new Paragraph(style0, "Driver / Fahrer").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierDriverName()))
//								.addRow(new Paragraph(style0, "Remark / Bemerkung").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getCarrierRemark()))
//								.setCellPadding(new Insets(0, 5, 0, 5))
//								.setRowSpacing(2)
//							));
//
//							page.append(new TableArea(380, 97, 555, 35, new Table(2, 3)
//								.setExtendTableEnabled(true)
//								.addRow(new Paragraph(style0, "Name").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeName()))
//								.addRow(new Paragraph(style0, "Date/Datum").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeDateTime()))
//								.addRow(new Paragraph(style0, " ").setAlignment(Alignment.RIGHT), new Paragraph(style0, " "))
//								.addRow(new Paragraph(style0, "Device ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeDeviceNo()))
//								.addRow(new Paragraph(style0, "Truck ID / Lastwagen ID").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeTruckNo()))
//								.addRow(new Paragraph(style0, "Carrier / Spediteur").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeHaulierName()))
//								.addRow(new Paragraph(style0, "Driver / Fahrer").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeDriverName()))
//								.addRow(new Paragraph(style0, "Remark / Bemerkung").setAlignment(Alignment.RIGHT), new Paragraph(style0, aDocument.getConsigneeRemark()))
//								.setCellPadding(new Insets(0, 5, 0, 5))
//								.setRowSpacing(2)
//							));
//
//							page.append(new ImageArea(40, 155, 210, 100, new Image(aDocument.getSenderSignature(), Format.PNG).setMargins(margins3)));
//							page.append(new ImageArea(210, 155, 380, 100, new Image(aDocument.getDelivererSignature(), Format.PNG).setMargins(margins3)));
//							page.append(new ImageArea(380, 155, 555, 100, new Image(aDocument.getReceiverSignature(), Format.PNG).setMargins(margins3)));
						}
//						else
//						{
//							page.append(new TableArea(40, 460, 555, 318, productTable));
//
//							page.append(new Line(40, 318, 555, 318, 1.0, Color.BLACK));
//
//							appendSectionHeader(page, style1, style5, 13, "Sender's instructions", "Anweisungen des absenders", 40, 318, 300, 175);
//							page.append(new TextArea(40, 318 - 18, 555, 35, senderInstructions.setMargins(margins1)));
//						}
					}
				}
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}


//	private static void appendSectionHeader(Page page, Style style1, Style style5, int aNumber, String aLine1, String aLine2, double aX0, double aY0, double aX1, double aY1, Paragraph... aContents) throws IOException
//	{
//		page.append(new TableArea(aX0, aY0, aX1, aY1, createHeading(style1, style5, aNumber, aLine1, aLine2, true, 12)));
//		page.append(new TextArea(aX0, aY0 - 18, aX1, aY1, aContents));
//	}
//
//
//	private static Table createHeading(Style style1, Style style5, int aNumber, String aLine1, String aLine2, boolean aWide, int aNumberScale) throws IOException
//	{
//		return new Table(1, aNumberScale)
//			.setExtendTableEnabled(true)
//			.setCellPadding(new Insets(0, 0, 0, 2))
//			.addRow(
//				new Paragraph(style5, "" + aNumber)
//					.setMarginLeft(2)
//					.setAlignment(Alignment.RIGHT),
//				new Table(1)
//					.setExtendTableEnabled(true)
//					.addRow(new Paragraph(style1, aLine1).setMarginTop(2))
//					.addRow(new Paragraph(style1, aLine2).setMarginTop(2))
//			);
//	}
}
