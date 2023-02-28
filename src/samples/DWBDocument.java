package samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
//import javax.xml.bind.annotation.XmlRootElement;


//@XmlRootElement(name = "DWBDocument")
public class DWBDocument extends CMRDocument
{
	private String crossDockCountryCode;
	private String crossDockPostalCode;
	private String crossDockAddress;
	private String crossDockStreet;
	private String crossDockCity;
	private String carrierRemarkReason;
	private String senderRemarkReason;
	private String consigneeRemarkReason;
	private String orderType;
	private String documentDate;
	private String unit;
	private String phone;
	private String taxWeight;
	private String total;
	private String totalQuantity;
	private String totalGrossWeight;
	private String totalVolume;
	private String totalLoadSpace;
	private ArrayList<String[]> equipments;


	public DWBDocument() throws IOException
	{
		super();

		InputStream in = CMRDocument.class.getResourceAsStream("dwb_labels.properties");
		try
		{
			labels.load(in);
		}
		finally
		{
			in.close();
		}
	}


	public String getCrossDockCountryCode()
	{
		return crossDockCountryCode;
	}


	public void setCrossDockCountryCode(String aCrossDockCountryCode)
	{
		this.crossDockCountryCode = aCrossDockCountryCode;
	}


	public String getCrossDockPostalCode()
	{
		return crossDockPostalCode;
	}


	public void setCrossDockPostalCode(String aCrossDockPostalCode)
	{
		this.crossDockPostalCode = aCrossDockPostalCode;
	}


	public String getCrossDockAddress()
	{
		return crossDockAddress;
	}


	public void setCrossDockAddress(String aCrossDockAddress)
	{
		this.crossDockAddress = aCrossDockAddress;
	}


	public String getCrossDockStreet()
	{
		return crossDockStreet;
	}


	public void setCrossDockStreet(String aCrossDockStreet)
	{
		this.crossDockStreet = aCrossDockStreet;
	}


	public String getCrossDockCity()
	{
		return crossDockCity;
	}


	public void setCrossDockCity(String aCrossDockCity)
	{
		this.crossDockCity = aCrossDockCity;
	}


	public String getCarrierRemarkReason()
	{
		return carrierRemarkReason;
	}


	public void setCarrierRemarkReason(String aCarrierRemarkReason)
	{
		this.carrierRemarkReason = aCarrierRemarkReason;
	}


	public String getSenderRemarkReason()
	{
		return senderRemarkReason;
	}


	public void setSenderRemarkReason(String aSenderRemarkReason)
	{
		this.senderRemarkReason = aSenderRemarkReason;
	}


	public String getConsigneeRemarkReason()
	{
		return consigneeRemarkReason;
	}


	public void setConsigneeRemarkReason(String aConsigneeRemarkReason)
	{
		this.consigneeRemarkReason = aConsigneeRemarkReason;
	}


	public String getOrderType()
	{
		return orderType;
	}


	public void setOrderType(String aOrderType)
	{
		this.orderType = aOrderType;
	}


	public String getDocumentDate()
	{
		return documentDate;
	}


	public void setDocumentDate(String aDocumentDate)
	{
		this.documentDate = aDocumentDate;
	}


	public String getUnit()
	{
		return unit;
	}


	public void setUnit(String aUnit)
	{
		this.unit = aUnit;
	}


	public String getPhone()
	{
		return phone;
	}


	public void setPhone(String aPhone)
	{
		this.phone = aPhone;
	}


	public String getTaxWeight()
	{
		return taxWeight;
	}


	public void setTaxWeight(String aTaxWeight)
	{
		this.taxWeight = aTaxWeight;
	}


	public String getTotal()
	{
		return total;
	}


	public void setTotal(String aTotal)
	{
		this.total = aTotal;
	}


	public String getTotalQuantity()
	{
		return totalQuantity;
	}


	public void setTotalQuantity(String aTotalQuantity)
	{
		this.totalQuantity = aTotalQuantity;
	}


	public String getTotalGrossWeight()
	{
		return totalGrossWeight;
	}


	public void setTotalGrossWeight(String aTotalGrossWeight)
	{
		this.totalGrossWeight = aTotalGrossWeight;
	}


	public String getTotalVolume()
	{
		return totalVolume;
	}


	public void setTotalVolume(String aTotalVolume)
	{
		this.totalVolume = aTotalVolume;
	}


	public String getTotalLoadSpace()
	{
		return totalLoadSpace;
	}


	public void setTotalLoadSpace(String aTotalLoadSpace)
	{
		this.totalLoadSpace = aTotalLoadSpace;
	}


	public ArrayList<String[]> getEquipments()
	{
		return equipments;
	}


	public void setEquipments(List<String[]> aEquipments)
	{
		this.equipments = new ArrayList<>(aEquipments);
	}
}