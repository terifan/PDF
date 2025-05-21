package samples;

import java.io.FileOutputStream;
import java.util.Arrays;


public class TestGenerateCMR
{
	public static void main(String ... args)
	{
		try
		{
			CMRDocument document = new CMRDocument();
			document.setCompanyLogo(TestGenerateCMR.class.getResourceAsStream("logo.png").readAllBytes());

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

			document.setConsignmentId("CPH5002074");
			document.setTripNo("CPHX17454");
			document.setWaybill("144017595972");
			document.setCarrier("olles bil");

			document.setConditionsOfDelivery("001");

			for (int i = 0; i < 25; i++)
			{
				document.setGoodsMarksAndNos(Arrays.asList("CPH5002074"));
				document.setGoodsNatureOfGoods(Arrays.asList("50548 qwerty qwerty qwerty qwerty qwerty qwerty"));
				document.setGoodsNumberOfPackages(Arrays.asList("1"));
				document.setGoodsMethodOfPacking(Arrays.asList("XP"));
				document.setGoodsGrossWeight(Arrays.asList("6.00"));
				document.setGoodsVolume(Arrays.asList("0.04"));
				document.setGoodsLoadSpace(Arrays.asList("-"));
				document.setGoodsStatisticsNo(Arrays.asList("-"));
			}

			document.setSenderDateTime("2015-04-21 20:26:09");
			document.setSenderName("Olle");
			document.setCarrierRemark("qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty");

			document.setConsigneeDateTime("2015-04-27 20:26:09");
			document.setConsigneeName("Sven");
			document.setConsigneeRemark("ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq ytrewq");

			document.setSenderSignature(TestGenerateDWB.class.getResourceAsStream("signature.png").readAllBytes());
			document.setRecevierSignature(TestGenerateDWB.class.getResourceAsStream("signature.png").readAllBytes());

			document.setBarcode(TestGenerateCMR.class.getResourceAsStream("barcode.png").readAllBytes());

			try (FileOutputStream out = new FileOutputStream("c:/temp/output.pdf"))
			{
				CMRTemplate template = new CMRTemplate();
				template.generate(out, "en", document);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
