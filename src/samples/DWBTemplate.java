package samples;

import java.io.IOException;
import java.io.OutputStream;
import org.terifan.pdfwriter.Alignment;
import org.terifan.pdfwriter.Anchor;
import org.terifan.pdfwriter.Color;
import org.terifan.pdfwriter.ContentArea;
import org.terifan.pdfwriter.ContentStream;
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
import org.terifan.pdfwriter.VerticalAlignment;
import samples.CMRDocument.InstructionElement;
import samples.CMRDocument.InstructionHeader;


public class DWBTemplate implements Template<DWBDocument>
{
	@Override
	public void generate(OutputStream aOutputStream, String aLanguage, DWBDocument aDocument) throws IOException
	{
		Font font1 = new ExtendedFont(PDFWriter.class.getResourceAsStream("segoeui.ttf").readAllBytes());

		try (PDFWriter pdf = new PDFWriter(aOutputStream).setCompress(true))
		{
			Style style0 = new Style(font1, 7);
			Style style1 = new Style(font1, 9);
			Style style2 = new Style(font1, 13);
			Style style4 = new Style(font1, 11);

			Insets margins1 = new Insets(0, 4, 0, 4);
			Insets margins2 = new Insets(2, 2, 2, 2);
			Insets margins3 = new Insets(1, 2, 1, 2);
			Insets margins4 = new Insets(2, 4, 2, 4);
			Insets margins5 = new Insets(6, 6, 6, 6);
			Insets margins6 = new Insets(6, 15, 6, 15);
			Insets margins7 = new Insets(0, 4, 0, 4);
			Insets margins8 = new Insets(4, 8, 0, 4);

			ContentStream transportInfo = new ContentStream();
			for (InstructionElement s : aDocument.getInstructions())
			{
				if (s instanceof InstructionHeader)
				{
					transportInfo.add(new Paragraph(style0, label(s.toString())).setMargins(margins2));
				}
				else
				{
					transportInfo.add(new Paragraph(style1, s.toString()).setMargins(margins8));
				}
			}

			Table productTable = new Table(0.25, 0.35, 0.1, 0.13, 0.1, 0.07)
				.setRepeatHeader(true)
				.setVerticalGridColor(Color.BLACK)
				.setVerticalGridThickness(1.0)
				.setHeaderGridThickness(1.0)
				.setHeaderGridColor(Color.BLACK)
				.setHeader(new TableRow(
					new Paragraph(style0, "Marks and numbers").setMargins(margins2),
					new Paragraph(style0, "Goods description / reference").setMargins(margins2),
					new Paragraph(new Span(style0, "Qty."), new Span(style0, "Pkg.")).setAlignment(Alignment.SPLIT).setMargins(margins2),
					new Paragraph(style0, "Gross weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins2),
					new Paragraph(style0, "Volume, m³").setAlignment(Alignment.RIGHT).setMargins(margins2),
					new Paragraph(style0, "LDM").setAlignment(Alignment.RIGHT).setMargins(margins2)
				));

			for (int i = 0; i < aDocument.getGoodsGrossWeight().size(); i++)
			{
				ContentStream cs = new ContentStream();
				for (String s : aDocument.getGoodsNatureOfGoods().get(i).split("\n"))
				{
					cs.add(new Paragraph(style1, s).setMargins(margins2));
				}

				productTable.addRow(
					new Paragraph(style1, aDocument.getGoodsMarksAndNos().get(i)).setMargins(margins2),
					cs,
					new Paragraph(new Span(style1, aDocument.getGoodsNumberOfPackages().get(i)), new Span(style1, aDocument.getGoodsMethodOfPacking().get(i))).setAlignment(Alignment.SPLIT).setMargins(margins2),
					new Paragraph(style1, aDocument.getGoodsGrossWeight().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2),
					new Paragraph(style1, aDocument.getGoodsVolume().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2),
					new Paragraph(style1, aDocument.getGoodsLoadSpace().get(i)).setAlignment(Alignment.RIGHT).setMargins(margins2)
				);
			}

			if (!aDocument.getLoadPackageNumbers().isEmpty())
			{
				productTable.addRow(new Paragraph(style0, label("pickup_package_list")));
				for (String s : aDocument.getLoadPackageNumbers())
				{
					productTable.addRow(new Paragraph(style0, s));
				}
			}
			if (!aDocument.getUnloadPackageNumbers().isEmpty())
			{
				productTable.addRow(new Paragraph(style0, label("delivery_package_list")));
				for (String s : aDocument.getUnloadPackageNumbers())
				{
					productTable.addRow(new Paragraph(style0, s));
				}
			}
			if (!aDocument.getExtraMarksAndNos().isEmpty())
			{
				productTable.addRow(new Paragraph(style0, label("extra_references")));
				for (String s : aDocument.getExtraMarksAndNos())
				{
					productTable.addRow(new Paragraph(style0, s));
				}
			}

			Table footerTable = new Table(0.25, 0.175, 0.175, 0.1, 0.13, 0.1, 0.07)
				.setRepeatHeader(true)
				.setVerticalGridColor(Color.BLACK)
				.setVerticalGridThickness(1.0)
				.setHeader(new TableRow(
					new Paragraph(style0, "Delivery terms").setMargins(margins3),
					new Paragraph(style0, "Calculated weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style0, "Total").setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style0, "Quantity").setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style0, "Gross weight, kg").setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style0, "Volume, m³").setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style0, "LDM").setAlignment(Alignment.RIGHT).setMargins(margins3)
				))
				.addRow(
					new Paragraph(style1, aDocument.getConditionsOfDelivery()).setMargins(margins3),
					new Paragraph(style1, aDocument.getTaxWeight()).setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style1, aDocument.getTotal()).setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style1, aDocument.getTotalQuantity()).setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style1, aDocument.getTotalGrossWeight()).setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style1, aDocument.getTotalVolume()).setAlignment(Alignment.RIGHT).setMargins(margins3),
					new Paragraph(style1, aDocument.getTotalLoadSpace()).setAlignment(Alignment.RIGHT).setMargins(margins3)
				);

			boolean firstPage = true;

			while (!transportInfo.isConsumed() || !productTable.isConsumed() || firstPage)
			{
				try (Page page = pdf.addPage())
				{
					page.append(new Rectangle(40, 775, 555, 35, 1.0, 10, Color.BLACK, null));
					page.append(new Line(40, 697, 300, 697, 1.0, Color.BLACK));
					page.append(new Line(40, 619, 300, 619, 1.0, Color.BLACK));
					page.append(new Line(40, 541, 555, 541, 1.0, Color.BLACK));
					page.append(new Line(40, 461, 555, 461, 2.0, Color.BLACK));
					page.append(new Line(300, 755, 555, 755, 1.0, Color.BLACK));
					page.append(new Line(300, 670, 555, 670, 1.0, Color.BLACK));
					page.append(new Line(300, 590, 555, 590, 1.0, Color.BLACK));
					page.append(new Line(300, 775, 300, 460, 1.0, Color.BLACK));
					page.append(new Line(425, 775, 425, 755, 1.0, Color.BLACK));

					page.append(new ImageArea(40, 805, 150, 785, new Image(aDocument.getCompanyLogo(), Image.Format.PNG)));

					page.append(new TextArea(40, 805, 555, 775, new Paragraph(style2, "Domestic Waybill")).setAnchor(Anchor.EAST));

					page.append(new TextArea(40, 775, 300, 697,
						new Paragraph(style1, "Shipper:").setMargins(margins4),
						new Paragraph(style2, aDocument.getShipperAddress()).setMargins(margins1),
						new Paragraph(style2, aDocument.getShipperCity()).setMargins(margins1),
						new Paragraph(style2, aDocument.getShipperStreet()).setMargins(margins1)
					));

					page.append(new TextArea(40, 775, 300, 697,
						new Paragraph(style1, " ").setMargins(margins4),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, aDocument.getShipperPostalCode(), " ", aDocument.getShipperCountryCode()).setAlignment(Alignment.RIGHT).setMargins(margins1)
					).setAnchor(Anchor.NORTH_EAST));

					page.append(new TextArea(40, 697, 300, 619,
						new Paragraph(style1, "Loading address:").setMargins(margins4),
						new Paragraph(style2, aDocument.getLoadAddress()).setMargins(margins1),
						new Paragraph(style2, aDocument.getLoadCity()).setMargins(margins1),
						new Paragraph(style2, aDocument.getLoadStreet()).setMargins(margins1)
					));

					page.append(new TextArea(40, 697, 300, 619,
						new Paragraph(style1, " ").setMargins(margins4),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, aDocument.getLoadPostalCode(), " ", aDocument.getLoadCountryCode()).setAlignment(Alignment.RIGHT).setMargins(margins1)
					).setAnchor(Anchor.NORTH_EAST));

					page.append(new TextArea(40, 619, 300, 541,
						new Paragraph(style1, "Receiver:").setMargins(margins4),
						new Paragraph(style2, aDocument.getConsigneeAddress()).setMargins(margins1),
						new Paragraph(style2, aDocument.getConsigneeCity()).setMargins(margins1),
						new Paragraph(style2, aDocument.getConsigneeStreet()).setMargins(margins1)
					));

					page.append(new TextArea(40, 619, 300, 541,
						new Paragraph(style1, " ").setMargins(margins4),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, aDocument.getConsigneePostalCode(), " ", aDocument.getConsigneeCountryCode()).setAlignment(Alignment.RIGHT).setMargins(margins1)
					).setAnchor(Anchor.NORTH_EAST));

					page.append(new TextArea(40, 541, 300, 461,
						new Paragraph(style1, "Delivery address:").setMargins(margins4),
						new Paragraph(style2, aDocument.getUnloadAddress()).setMargins(margins1),
						new Paragraph(style2, aDocument.getUnloadCity()).setMargins(margins1),
						new Paragraph(style2, aDocument.getUnloadStreet()).setMargins(margins1)
					));

					page.append(new TextArea(40, 541, 300, 461,
						new Paragraph(style1, " ").setMargins(margins4),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, aDocument.getUnloadPostalCode(), " ", aDocument.getUnloadCountryCode()).setAlignment(Alignment.RIGHT).setMargins(margins1)
					).setAnchor(Anchor.NORTH_EAST));

					page.append(new TextArea(300, 541, 555, 461,
						new Paragraph(style1, "Terminal:").setMargins(margins4),
						new Paragraph(style2, aDocument.getCrossDockAddress()).setMargins(margins1),
						new Paragraph(style2, aDocument.getCrossDockCity()).setMargins(margins1),
						new Paragraph(style2, aDocument.getCrossDockStreet()).setMargins(margins1)
					));

					page.append(new TextArea(300, 541, 555, 461,
						new Paragraph(style1, " ").setMargins(margins4),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, " ").setMargins(margins1),
						new Paragraph(style2, aDocument.getCrossDockPostalCode(), " ", aDocument.getCrossDockCountryCode()).setAlignment(Alignment.RIGHT).setMargins(margins1)
					).setAnchor(Anchor.NORTH_EAST));

					page.append(new TableArea(300, 775-2, 555, 755-2, new Table(1, 1)
						.addRow(
							new Paragraph(new Span(style1, "Date: "), new Span(style2, aDocument.getDocumentDate())).setMargins(margins7).setVerticalAlignment(VerticalAlignment.CENTER),
							new Paragraph(new Span(style1, "Order type: "), new Span(style2, aDocument.getOrderType())).setMargins(margins7).setVerticalAlignment(VerticalAlignment.CENTER)
						)
					));

					page.append(new TableArea(300, 755, 555, 670, new Table(1, 1)
						.addRow(new Paragraph(style1, "Consignment ID:").setMargins(margins4), new Paragraph(style1, "Trip No:").setMargins(margins4))
						.addRow(new Paragraph(style2, aDocument.getConsignmentId()).setMargins(margins7), new Paragraph(style2, aDocument.getTripNo()).setMargins(margins7))
					));

					page.append(new ImageArea(300, 725, 555, 685, new Image(aDocument.getBarcode(), Format.PNG).setMargins(margins6)));
					page.append(new TextArea(300, 755, 555, 670, new Paragraph(new Span(style1, "Waybill: "), new Span(style4, aDocument.getWaybill())).setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));

					page.append(new TableArea(300, 670, 555, 590, new Table(1, 1)
						.addRow(new Paragraph(style1, "Carrier:").setMargins(margins4), new Paragraph(style1, "Unit:").setMargins(margins4))
						.addRow(new Paragraph(style2, aDocument.getCarrier()).setMargins(margins7), new Paragraph(style2, aDocument.getUnit()).setMargins(margins7))
						.addRow(new Paragraph(style1, "Phone:").setMargins(margins4))
						.addRow(new Paragraph(style2, aDocument.getPhone()).setMargins(margins7))
					));

					if (aDocument.getEquipments() != null && !aDocument.getEquipments().isEmpty())
					{
						page.append(new TableArea(300, 590, 555, 465, new Table(1, 1, 1)
							.addRow(new Paragraph(style1, "Equipment:").setMargins(margins4), new Paragraph(style1, "ID:").setMargins(margins4), new Paragraph(style1, "License plate:").setMargins(margins4))
							.addRow(new Paragraph(style2, aDocument.getEquipments().get(0)[0]).setMargins(margins7), new Paragraph(style2, aDocument.getEquipments().get(0)[1]).setMargins(margins7), new Paragraph(style2, aDocument.getEquipments().get(0)[2]).setMargins(margins7))
						));
					}

					if (firstPage)
					{
						page.append(new Line(40, 200, 555, 200, 1.0, Color.BLACK));
						page.append(new Line(40, 115, 555, 115, 1.0, Color.BLACK));
						page.append(new Line(300, 200, 300, 35, 1.0, Color.BLACK));
						page.append(new Line(425, 200, 425, 35, 1.0, Color.BLACK));

						page.append(new TextArea(40, 200, 300, 115, new Paragraph(style1, "Loading date and time:").setMargins(margins1), new Paragraph(style4, aDocument.getSenderDateTime()).setMargins(margins4)));
						page.append(new TextArea(170, 200, 300, 115, new Paragraph(style1, "Remarks reason:").setMargins(margins1), new Paragraph(style4, aDocument.getCarrierRemarkReason()).setMargins(margins4)));
						page.append(new TextArea(40, 170, 300, 115, new Paragraph(style1, "Remarks:").setMargins(margins1), new Paragraph(style1, aDocument.getCarrierRemark()).setMargins(margins1)));

						page.append(new TextArea(40, 115, 300, 35, new Paragraph(style1, "Delivery date and time:").setMargins(margins1), new Paragraph(style4, aDocument.getConsigneeDateTime()).setMargins(margins4)));
						page.append(new TextArea(170, 115, 300, 35, new Paragraph(style1, "Remarks reason:").setMargins(margins1), new Paragraph(style4, aDocument.getConsigneeRemarkReason()).setMargins(margins4)));
						page.append(new TextArea(40, 85, 300, 35, new Paragraph(style1, "Remarks:").setMargins(margins1), new Paragraph(style1, aDocument.getConsigneeRemark()).setMargins(margins1)));

						page.append(new TextArea(300, 200, 425, 115, new Paragraph(style1, "Shipper:").setMargins(margins1)));
						page.append(new TextArea(300, 200, 425, 115, new Paragraph(style1, "Name: ", aDocument.getSenderName()).setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));
						page.append(new ImageArea(300, 190, 425, 130, new Image(aDocument.getSenderSignature(), Format.PNG).setMargins(margins5)));

						page.append(new TextArea(300, 115, 425, 35, new Paragraph(style1, "Receiver:").setMargins(margins1)));
						page.append(new TextArea(300, 115, 425, 35, new Paragraph(style1, "Name: ", aDocument.getConsigneeName()).setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));
						page.append(new ImageArea(300, 105, 425, 50, new Image(aDocument.getReceiverSignature(), Format.PNG).setMargins(margins5)));

						page.append(new TextArea(425, 200, 555, 115, new Paragraph(style1, "Carrier:").setMargins(margins1)));
						page.append(new TextArea(425, 200, 555, 115, new Paragraph(style1, "Name: ", aDocument.getCarrierName()).setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));
						page.append(new ImageArea(425, 190, 555, 130, new Image(aDocument.getCarrierSignature(), Format.PNG).setMargins(margins5)));

						page.append(new TextArea(425, 115, 555, 35, new Paragraph(style1, "Deliverer:").setMargins(margins1)));
						page.append(new TextArea(425, 115, 555, 35, new Paragraph(style1, "Name: ", aDocument.getDelivererName()).setMargins(margins1)).setAnchor(Anchor.SOUTH_WEST));
						page.append(new ImageArea(425, 105, 555, 50, new Image(aDocument.getDelivererSignature(), Format.PNG).setMargins(margins5)));

						firstPage = false;

						page.append(new Line(40, 295, 555, 295, 1.0, Color.BLACK));
						page.append(new Line(40, 270, 555, 270, 1.0, Color.BLACK));

						page.append(new TableArea(40, 460, 555, 295, productTable));
						page.append(new TableArea(40, 295, 555, 270, footerTable));

						page.append(new ContentArea(40, 270, 555, 200, transportInfo));
					}
					else if (!productTable.isConsumed() && !transportInfo.isConsumed())
					{
						footerTable.reuseContent();

						page.append(new Line(40, 295, 555, 295, 1.0, Color.BLACK));
						page.append(new Line(40, 270, 555, 270, 1.0, Color.BLACK));

						page.append(new TableArea(40, 460, 555, 295, productTable));
						page.append(new TableArea(40, 295, 555, 270, footerTable));

						page.append(new ContentArea(40, 270, 555, 45, transportInfo));
					}
					else if (!productTable.isConsumed())
					{
						footerTable.reuseContent();

						page.append(new Line(40, 60, 555, 60, 1.0, Color.BLACK));

						page.append(new TableArea(40, 460, 555, 60, productTable));
						page.append(new TableArea(40, 60, 555, 35, footerTable));
					}
					else
					{
						page.append(new ContentArea(40, 460, 555, 200, transportInfo));
					}
				}
			}
		}
	}


	private String label(String aText)
	{
		return aText;
	}
}
