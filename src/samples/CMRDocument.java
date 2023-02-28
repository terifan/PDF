package samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import javax.xml.bind.annotation.XmlRootElement;


//@XmlRootElement(name = "CMRDocument")
public class CMRDocument implements Document
{
	private String shipperCountryCode;
	private String shipperPostalCode;
	private String shipperAddress;
	private String shipperStreet;
	private String shipperCity;

	private String loadCountryCode;
	private String loadPostalCode;
	private String loadAddress;
	private String loadStreet;
	private String loadCity;

	private String consigneeCountryCode;
	private String consigneePostalCode;
	private String consigneeAddress;
	private String consigneeStreet;
	private String consigneeCity;

	private String unloadCountryCode;
	private String unloadPostalCode;
	private String unloadAddress;
	private String unloadStreet;
	private String unloadCity;

	private String carrier;
	private String successiveCarriers;
	private String shipmentRemarks;
	private String conditionsOfDelivery;
	private ArrayList<String> goodsMarksAndNos = new ArrayList<String>();
	private ArrayList<String> goodsNumberOfPackages = new ArrayList<String>();
	private ArrayList<String> goodsMethodOfPacking = new ArrayList<String>();
	private ArrayList<String> goodsNatureOfGoods = new ArrayList<String>();
	private ArrayList<String> goodsStatisticsNo = new ArrayList<String>();
	private ArrayList<String> goodsGrossWeight = new ArrayList<String>();
	private ArrayList<String> goodsVolume = new ArrayList<String>();
	private ArrayList<String> goodsLoadSpace = new ArrayList<String>();
	private ArrayList<InstructionElement> instructions = new ArrayList<InstructionElement>();
	private ArrayList<String> loadPackageNumbers = new ArrayList<String>();
	private ArrayList<String> unloadPackageNumbers = new ArrayList<String>();
	private ArrayList<String> extraMarksAndNos = new ArrayList<String>();
	private String paymentInstructions;
	private String liability;
	private String specialAgreement;
	private String established;
	private String annexDocuments;

	private String senderName;
	private String senderDateTime;
	private String senderRemark;
	private String senderHaulierName;
	private String senderDriverName;
	private String senderTruckNo;
	private String senderDeviceNo;
	private String carrierName;
	private String carrierDateTime;
	private String carrierRemark;
	private String carrierHaulierName;
	private String carrierDriverName;
	private String carrierTruckNo;
	private String carrierDeviceNo;
	private String consigneeName;
	private String consigneeDateTime;
	private String consigneeRemark;
	private String consigneeHaulierName;
	private String consigneeDriverName;
	private String consigneeTruckNo;
	private String consigneeDeviceNo;

	private String delivererName;

	private String consignmentId;
	private String tripNo;
	private String waybill;

	private byte [] senderSignature;
	private byte [] carrierSignature;
	private byte [] receiverSignature;
	private byte [] delivererSignature;

	protected Properties labels = new Properties();
	private byte [] barcode;
	private byte [] companyLogo;
	private byte [] cmrLogo;


	public CMRDocument() throws IOException
	{
		InputStream in = CMRDocument.class.getResourceAsStream("cmr_labels.properties");
		try
		{
			labels.load(in);
		}
		finally
		{
			in.close();
		}
	}


	public String getWaybill()
	{
		return waybill;
	}


	public void setWaybill(String aWaybill)
	{
		this.waybill = aWaybill;
	}


	public ArrayList<String> getLoadPackageNumbers()
	{
		return loadPackageNumbers;
	}


	public ArrayList<String> getUnloadPackageNumbers()
	{
		return unloadPackageNumbers;
	}


	public ArrayList<String> getExtraMarksAndNos()
	{
		return extraMarksAndNos;
	}


	public String getShipperCountryCode()
	{
		return shipperCountryCode;
	}


	public void setShipperCountryCode(String aShipperCountryCode)
	{
		shipperCountryCode = aShipperCountryCode;
	}


	public String getShipperPostalCode()
	{
		return shipperPostalCode;
	}


	public void setShipperPostalCode(String aShipperPostalCode)
	{
		shipperPostalCode = aShipperPostalCode;
	}


	public String getShipperAddress()
	{
		return shipperAddress;
	}


	public void setShipperAddress(String aShipperAddress)
	{
		shipperAddress = aShipperAddress;
	}


	public String getShipperStreet()
	{
		return shipperStreet;
	}


	public void setShipperStreet(String aShipperStreet)
	{
		shipperStreet = aShipperStreet;
	}


	public String getShipperCity()
	{
		return shipperCity;
	}


	public void setShipperCity(String aShipperCity)
	{
		shipperCity = aShipperCity;
	}


	public String getLoadCountryCode()
	{
		return loadCountryCode;
	}


	public void setLoadCountryCode(String aLoadCountryCode)
	{
		loadCountryCode = aLoadCountryCode;
	}


	public String getLoadPostalCode()
	{
		return loadPostalCode;
	}


	public void setLoadPostalCode(String aLoadPostalCode)
	{
		loadPostalCode = aLoadPostalCode;
	}


	public String getLoadAddress()
	{
		return loadAddress;
	}


	public void setLoadAddress(String aLoadAddress)
	{
		loadAddress = aLoadAddress;
	}


	public String getLoadStreet()
	{
		return loadStreet;
	}


	public void setLoadStreet(String aLoadStreet)
	{
		loadStreet = aLoadStreet;
	}


	public String getLoadCity()
	{
		return loadCity;
	}


	public void setLoadCity(String aLoadCity)
	{
		loadCity = aLoadCity;
	}


	public String getConsigneeCountryCode()
	{
		return consigneeCountryCode;
	}


	public void setConsigneeCountryCode(String aConsigneeCountryCode)
	{
		consigneeCountryCode = aConsigneeCountryCode;
	}


	public String getConsigneePostalCode()
	{
		return consigneePostalCode;
	}


	public void setConsigneePostalCode(String aConsigneePostalCode)
	{
		consigneePostalCode = aConsigneePostalCode;
	}


	public String getConsigneeAddress()
	{
		return consigneeAddress;
	}


	public void setConsigneeAddress(String aConsigneeAddress)
	{
		consigneeAddress = aConsigneeAddress;
	}


	public String getConsigneeStreet()
	{
		return consigneeStreet;
	}


	public void setConsigneeStreet(String aConsigneeStreet)
	{
		consigneeStreet = aConsigneeStreet;
	}


	public String getConsigneeCity()
	{
		return consigneeCity;
	}


	public void setConsigneeCity(String aConsigneeCity)
	{
		consigneeCity = aConsigneeCity;
	}


	public String getUnloadCountryCode()
	{
		return unloadCountryCode;
	}


	public void setUnloadCountryCode(String aUnloadCountryCode)
	{
		unloadCountryCode = aUnloadCountryCode;
	}


	public String getUnloadPostalCode()
	{
		return unloadPostalCode;
	}


	public void setUnloadPostalCode(String aUnloadPostalCode)
	{
		unloadPostalCode = aUnloadPostalCode;
	}


	public String getUnloadAddress()
	{
		return unloadAddress;
	}


	public void setUnloadAddress(String aUnloadAddress)
	{
		unloadAddress = aUnloadAddress;
	}


	public String getUnloadStreet()
	{
		return unloadStreet;
	}


	public void setUnloadStreet(String aUnloadStreet)
	{
		unloadStreet = aUnloadStreet;
	}


	public String getUnloadCity()
	{
		return unloadCity;
	}


	public void setUnloadCity(String aUnloadCity)
	{
		unloadCity = aUnloadCity;
	}


	public String getCarrier()
	{
		return carrier;
	}


	public void setCarrier(String aCarrier)
	{
		carrier = aCarrier;
	}


	public String getSuccessiveCarriers()
	{
		return successiveCarriers;
	}


	public void setSuccessiveCarriers(String aSuccessiveCarriers)
	{
		successiveCarriers = aSuccessiveCarriers;
	}


	public String getShipmentRemarks()
	{
		return shipmentRemarks;
	}


	public void setShipmentRemarks(String aShipmentRemarks)
	{
		shipmentRemarks = aShipmentRemarks;
	}


	public String getConditionsOfDelivery()
	{
		return conditionsOfDelivery;
	}


	public void setConditionsOfDelivery(String aConditionsOfDelivery)
	{
		conditionsOfDelivery = aConditionsOfDelivery;
	}


	public ArrayList<String> getGoodsMarksAndNos()
	{
		return goodsMarksAndNos;
	}


	public void setGoodsMarksAndNos(List<String> aGoodsMarksAndNos)
	{
		goodsMarksAndNos = new ArrayList<>(aGoodsMarksAndNos);
	}


	public ArrayList<String> getGoodsNumberOfPackages()
	{
		return goodsNumberOfPackages;
	}


	public void setGoodsNumberOfPackages(List<String> aGoodsNumberOfPackages)
	{
		goodsNumberOfPackages = new ArrayList<>(aGoodsNumberOfPackages);
	}


	public ArrayList<String> getGoodsMethodOfPacking()
	{
		return goodsMethodOfPacking;
	}


	public void setGoodsMethodOfPacking(List<String> aGoodsMethodOfPacking)
	{
		goodsMethodOfPacking = new ArrayList<>(aGoodsMethodOfPacking);
	}


	public ArrayList<String> getGoodsNatureOfGoods()
	{
		return goodsNatureOfGoods;
	}


	public void setGoodsNatureOfGoods(List<String> aGoodsNatureOfGoods)
	{
		goodsNatureOfGoods = new ArrayList<>(aGoodsNatureOfGoods);
	}


	public ArrayList<String> getGoodsStatisticsNo()
	{
		return goodsStatisticsNo;
	}


	public void setGoodsStatisticsNo(List<String> aGoodsStatisticsNo)
	{
		goodsStatisticsNo = new ArrayList<>(aGoodsStatisticsNo);
	}


	public ArrayList<String> getGoodsGrossWeight()
	{
		return goodsGrossWeight;
	}


	public void setGoodsGrossWeight(List<String> aGoodsGrossWeight)
	{
		goodsGrossWeight = new ArrayList<>(aGoodsGrossWeight);
	}


	public ArrayList<String> getGoodsVolume()
	{
		return goodsVolume;
	}


	public void setGoodsVolume(List<String> aGoodsVolume)
	{
		goodsVolume = new ArrayList<>(aGoodsVolume);
	}


	public ArrayList<String> getGoodsLoadSpace()
	{
		return goodsLoadSpace;
	}


	public void setGoodsLoadSpace(List<String> aGoodsLoadSpace)
	{
		this.goodsLoadSpace = new ArrayList<>(aGoodsLoadSpace);
	}


	public ArrayList<InstructionElement> getInstructions()
	{
		return instructions;
	}


	public void setInstructions(List<InstructionElement> aInstructions)
	{
		instructions = new ArrayList<>(aInstructions);
	}


	public String getPaymentInstructions()
	{
		return paymentInstructions;
	}


	public void setPaymentInstructions(String aPaymentInstructions)
	{
		paymentInstructions = aPaymentInstructions;
	}


	public String getLiability()
	{
		return liability;
	}


	public void setLiability(String aLiability)
	{
		liability = aLiability;
	}


	public String getSpecialAgreement()
	{
		return specialAgreement;
	}


	public void setSpecialAgreement(String aSpecialAgreement)
	{
		specialAgreement = aSpecialAgreement;
	}


	public String getEstablished()
	{
		return established;
	}


	public void setEstablished(String aEstablished)
	{
		established = aEstablished;
	}


	public String getAnnexDocuments()
	{
		return annexDocuments;
	}


	public void setAnnexDocuments(String aAnnexDocuments)
	{
		annexDocuments = aAnnexDocuments;
	}


	public String getSenderName()
	{
		return senderName;
	}


	public void setSenderName(String aSenderName)
	{
		senderName = aSenderName;
	}


	public String getSenderDateTime()
	{
		return senderDateTime;
	}


	public void setSenderDateTime(String aSenderDateTime)
	{
		senderDateTime = aSenderDateTime;
	}


	public String getSenderRemark()
	{
		return senderRemark;
	}


	public void setSenderRemark(String aSenderRemark)
	{
		senderRemark = aSenderRemark;
	}


	public String getSenderDriverName()
	{
		return senderDriverName;
	}


	public void setSenderDriverName(String aSenderDriverName)
	{
		this.senderDriverName = aSenderDriverName;
	}


	public String getSenderHaulierName()
	{
		return senderHaulierName;
	}


	public void setSenderHaulierName(String aSenderHaulierName)
	{
		this.senderHaulierName = aSenderHaulierName;
	}


	public String getSenderTruckNo()
	{
		return senderTruckNo;
	}


	public void setSenderTruckNo(String aSenderTruckNo)
	{
		this.senderTruckNo = aSenderTruckNo;
	}


	public String getSenderDeviceNo()
	{
		return senderDeviceNo;
	}


	public void setSenderDeviceNo(String aSenderDeviceNo)
	{
		this.senderDeviceNo = aSenderDeviceNo;
	}


	public String getCarrierName()
	{
		return carrierName;
	}


	public void setCarrierName(String aCarrierName)
	{
		carrierName = aCarrierName;
	}


	public String getCarrierDateTime()
	{
		return carrierDateTime;
	}


	public void setCarrierDateTime(String aCarrierDateTime)
	{
		carrierDateTime = aCarrierDateTime;
	}


	public String getCarrierRemark()
	{
		return carrierRemark;
	}


	public void setCarrierRemark(String aCarrierRemark)
	{
		carrierRemark = aCarrierRemark;
	}


	public String getCarrierDriverName()
	{
		return carrierDriverName;
	}


	public void setCarrierDriverName(String aCarrierDriverName)
	{
		this.carrierDriverName = aCarrierDriverName;
	}


	public String getCarrierHaulierName()
	{
		return carrierHaulierName;
	}


	public void setCarrierHaulierName(String aCarrierHaulierName)
	{
		this.carrierHaulierName = aCarrierHaulierName;
	}


	public String getCarrierTruckNo()
	{
		return carrierTruckNo;
	}


	public void setCarrierTruckNo(String aCarrierTruckNo)
	{
		this.carrierTruckNo = aCarrierTruckNo;
	}


	public String getCarrierDeviceNo()
	{
		return carrierDeviceNo;
	}


	public void setCarrierDeviceNo(String aCarrierDeviceNo)
	{
		this.carrierDeviceNo = aCarrierDeviceNo;
	}


	public String getConsigneeName()
	{
		return consigneeName;
	}


	public void setConsigneeName(String aConsigneeName)
	{
		consigneeName = aConsigneeName;
	}


	public String getConsigneeDateTime()
	{
		return consigneeDateTime;
	}


	public void setConsigneeDateTime(String aConsigneeDateTime)
	{
		consigneeDateTime = aConsigneeDateTime;
	}


	public String getConsigneeRemark()
	{
		return consigneeRemark;
	}


	public void setConsigneeRemark(String aConsigneeRemark)
	{
		consigneeRemark = aConsigneeRemark;
	}


	public String getConsigneeDriverName()
	{
		return consigneeDriverName;
	}


	public void setConsigneeDriverName(String aConsigneeDriverName)
	{
		this.consigneeDriverName = aConsigneeDriverName;
	}


	public String getConsigneeHaulierName()
	{
		return consigneeHaulierName;
	}


	public void setConsigneeHaulierName(String aConsigneeHaulierName)
	{
		this.consigneeHaulierName = aConsigneeHaulierName;
	}


	public String getConsigneeTruckNo()
	{
		return consigneeTruckNo;
	}


	public void setConsigneeTruckNo(String aConsigneeTruckNo)
	{
		this.consigneeTruckNo = aConsigneeTruckNo;
	}


	public String getConsigneeDeviceNo()
	{
		return consigneeDeviceNo;
	}


	public void setConsigneeDeviceNo(String aConsigneeDeviceNo)
	{
		this.consigneeDeviceNo = aConsigneeDeviceNo;
	}


	public String getConsignmentId()
	{
		return consignmentId;
	}


	public void setConsignmentId(String aConsignmentId)
	{
		consignmentId = aConsignmentId;

		if (consignmentId == null || consignmentId.isEmpty() || consignmentId.contains(">"))
		{
			barcode = null;
			return;
		}

		try
		{
			barcode = BarcodeGenerator.generate(consignmentId, 1, 1);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Cannot generate barcode: " + aConsignmentId, e);
		}
	}


	public String getTripNo()
	{
		return tripNo;
	}


	public void setTripNo(String aTripNo)
	{
		tripNo = aTripNo;
	}


	public byte [] getSenderSignature()
	{
		return senderSignature;
	}


	public void setSenderSignature(byte [] aSenderSignature)
	{
		senderSignature = aSenderSignature;
	}


	public byte [] getCarrierSignature()
	{
		return carrierSignature;
	}


	public void setCarrierSignature(byte [] aCarrierSignature)
	{
		carrierSignature = aCarrierSignature;
	}


	public byte [] getReceiverSignature()
	{
		return receiverSignature;
	}


	public void setRecevierSignature(byte [] aReceiverSignature)
	{
		receiverSignature = aReceiverSignature;
	}


	public byte [] getDelivererSignature()
	{
		return delivererSignature;
	}


	public void setDelivererSignature(byte [] aDelivererSignature)
	{
		delivererSignature = aDelivererSignature;
	}


	public Properties getLabels()
	{
		return labels;
	}


	public void setLabels(Properties aLabels)
	{
		labels = aLabels;
	}


	public byte [] getBarcode()
	{
		return barcode;
	}


	public void setBarcode(byte [] aBarcode)
	{
		barcode = aBarcode;
	}


	public byte [] getCompanyLogo()
	{
		return companyLogo;
	}


	public void setCompanyLogo(byte [] aCompanyLogo)
	{
		companyLogo = aCompanyLogo;
	}


	public byte [] getCmrLogo()
	{
		return cmrLogo;
	}


	public void setCmrLogo(byte [] aCmrLogo)
	{
		cmrLogo = aCmrLogo;
	}


	public String getDelivererName()
	{
		return delivererName;
	}


	public void setDelivererName(String aDelivererName)
	{
		this.delivererName = aDelivererName;
	}


	public static abstract class InstructionElement
	{
		String text;


		@Override
		public int hashCode()
		{
			return text.hashCode();
		}


		@Override
		public boolean equals(Object aOther)
		{
			if (aOther instanceof InstructionElement)
			{
				InstructionElement other = (InstructionElement)aOther;
				return aOther == this || text == null && other.text == null || text != null && text.equals(other.text);
			}
			return false;
		}


		@Override
		public String toString()
		{
			return text;
		}
	}


	public static class InstructionHeader extends InstructionElement
	{
		public InstructionHeader(String aText)
		{
			text = aText;
		}
	}


	public static class InstructionText extends InstructionElement
	{
		public InstructionText(String aText)
		{
			text = aText;
		}
	}
}