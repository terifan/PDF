package samples;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import samples.CMRDocument.InstructionHeader;
import samples.CMRDocument.InstructionText;


public class TestGenerateDWB
{
	public static void main(String ... args)
	{
		try
		{
			DWBDocument document = new DWBDocument();
			document.setCompanyLogo(TestGenerateCMR.class.getResourceAsStream("surikat_logo.png").readAllBytes());

			document.setShipperAddress("Meža Mājas ZS");
			document.setShipperStreet("Līvānu nov. Rožupes pag");
			document.setShipperCity("Rožupe, Meža māja");
			document.setShipperPostalCode("50401");
			document.setShipperCountryCode("CZ");

			document.setLoadAddress("если вы имеете");
			document.setLoadStreet("о других, то также произойдет");
			document.setLoadCity("Rožupe, Meža māja");
			document.setLoadPostalCode("50401");
			document.setLoadCountryCode("CZ");

			document.setConsigneeAddress("δικαστὰς περὶ");
			document.setConsigneeStreet("τούτου τοῦ");
			document.setConsigneeCity("Rožupe, Meža māja");
			document.setConsigneePostalCode("20360");
			document.setConsigneeCountryCode("FI");

			document.setUnloadAddress("SANDVIK MINING AND CONSTRUCTION");
			document.setUnloadStreet("Vahdontie 19");
			document.setUnloadCity("Finland");
			document.setUnloadPostalCode("20360");
			document.setUnloadCountryCode("FI");

			document.setCrossDockAddress("SANDVIK MINING AND CONSTRUCTION");
			document.setCrossDockStreet("Vahdontie 19");
			document.setCrossDockCity("Finland");
			document.setCrossDockPostalCode("20360");
			document.setCrossDockCountryCode("FI");

			document.setDocumentDate("2015-01-31");
			document.setOrderType("*CO");
			document.setConsignmentId("CPH5002074");
			document.setTripNo("CPHX17454");
			document.setWaybill("144017595972");
			document.setPhone("32131321321");
			document.setUnit("YE0038");
			document.setCarrier("olles bil");

			document.setConditionsOfDelivery("001");
			document.setTaxWeight("15");
			document.setTotal("1");
			document.setTotalQuantity("1");
			document.setTotalGrossWeight("6.00");
			document.setTotalVolume("0.040");
			document.setTotalLoadSpace("0.00");

			for (int i = 0; i < 25; i++)
			{
				document.getGoodsMarksAndNos().add("CPH5002074");
				document.getGoodsNatureOfGoods().add("50548 qwerty qwerty qwerty qwerty qwerty qwerty");
				document.getGoodsNumberOfPackages().add("1");
				document.getGoodsMethodOfPacking().add("XP");
				document.getGoodsGrossWeight().add("6.00");
				document.getGoodsVolume().add("0.04");
				document.getGoodsLoadSpace().add("-");
			}

			document.setInstructions(Arrays.asList(
				new InstructionHeader("header1"), new InstructionText("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text"),
				new InstructionHeader("header2"), new InstructionText("text text text text text text text text text text text text text text text text text text text text 1text text text text text text text text text text text 1text text text text text text text text text text text text 1text text text text text text text text text text text text text 1text text text text text text text text text text text")
			));

			document.setEquipments(new ArrayList<>());
			document.getEquipments().add(new String[]{"BlueTruck","123","ABC123"});

			document.setSenderDateTime("2015-04-21 20:26:09");
			document.setSenderName("Olle");
			document.setCarrierRemark("qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty");
			document.setCarrierRemarkReason("TM");

			document.setConsigneeDateTime("2016-04-27 20:26:09");
			document.setConsigneeName("Sven");
			document.setConsigneeRemark("ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq");
			document.setConsigneeRemarkReason("FM");

			document.setSenderSignature(TestGenerateDWB.class.getResourceAsStream("siugnature.png").readAllBytes());
			document.setRecevierSignature(TestGenerateDWB.class.getResourceAsStream("siugnature.png").readAllBytes());

			document.setBarcode(TestGenerateCMR.class.getResourceAsStream("barcode.png").readAllBytes());

			try (FileOutputStream out = new FileOutputStream("d:/output.pdf"))
			{
				DWBTemplate template = new DWBTemplate();
				template.generate(out, "en", document);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
