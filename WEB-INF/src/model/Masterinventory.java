package model;
import java.io.Serializable;

public class Masterinventory implements Serializable{
	private int    id;
	private String identifier;  //識別種類
	private String identificationnumber;  //識別番号
	private String vendor; //仕入先
	private String productname_asplus; //品名（Asplus）
	private String productnumber_asplus; //品番（Asplus）
	private String productname_correction; //品名（Correction）
	private String productnumber_correction; //品番（Correction）
	private String qrcodeinformation; //QRCode情報
	private String storagelocation; //保管先
	private String inventorymanagementclassification; //在庫管理区分
	private float    rate; //単価
	
	public Masterinventory(){}
	public Masterinventory(String identifier,String identificationnumber,String vendor,String productname_asplus,String productnumber_asplus,
			       String productname_correction,String productnumber_correction,
			       String qrcodeinformation,String storagelocation,String inventorymanagementclassification,float rate){
		
		this.identifier=identifier;
		this.identificationnumber=identificationnumber;
		this.vendor=vendor;
		this.productname_asplus=productname_asplus;
		this.productnumber_asplus=productnumber_asplus;
		this.productname_correction=productname_correction;
		this.productnumber_correction=productnumber_correction;
		this.qrcodeinformation=qrcodeinformation;
		this.storagelocation=storagelocation;
		this.inventorymanagementclassification=inventorymanagementclassification;
		this.rate=rate;
	}
	public Masterinventory(int id,String identifier,String identificationnumber,String vendor,String productname_asplus,String productnumber_asplus,
		       String productname_correction,String productnumber_correction,
		       String qrcodeinformation,String storagelocation,String inventorymanagementclassification,float rate){
		
		this(identifier,identificationnumber,vendor,productname_asplus,productnumber_asplus,productname_correction,productnumber_correction,
				qrcodeinformation,storagelocation,inventorymanagementclassification,rate);
		
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getIdentificationnumber() {
		return identificationnumber;
	}
	public void setIdentificationnumber(String identificationnumber) {
		this.identificationnumber = identificationnumber;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getProductname_asplus() {
		return productname_asplus;
	}
	public void setProductname_asplus(String productname_asplus) {
		this.productname_asplus = productname_asplus;
	}
	public String getProductnumber_asplus() {
		return productnumber_asplus;
	}
	public void setProductnumber_asplus(String productnumber_asplus) {
		this.productnumber_asplus = productnumber_asplus;
	}
	public String getProductname_correction() {
		return productname_correction;
	}
	public void setProductname_correction(String productname_correction) {
		this.productname_correction = productname_correction;
	}
	public String getProductnumber_correction() {
		return productnumber_correction;
	}
	public void setProductnumber_correction(String productnumber_correction) {
		this.productnumber_correction = productnumber_correction;
	}
	public String getQrcodeinformation() {
		return qrcodeinformation;
	}
	public void setQrcodeinformation(String qrcodeinformation) {
		this.qrcodeinformation = qrcodeinformation;
	}
	public String getStoragelocation() {
		return storagelocation;
	}
	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}
	public String getInventorymanagementclassification() {
		return inventorymanagementclassification;
	}
	public void setInventorymanagementclassification(String inventorymanagementclassification) {
		this.inventorymanagementclassification = inventorymanagementclassification;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
}