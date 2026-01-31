package model;

import java.io.Serializable;

public class Masteritemtag implements Serializable{
	private int    id;
	private String identifier;  //識別種類
	private String identificationnumber;  //識別番号
	private String vendor; //仕入先
	private String productname_asplus; //品名（Asplus）
	private String productname_emphasisColor; //品名強調カラー
	private String productnumber_asplus; //品番（Asplus）
	private String productnumber_asplussub1; //品番（Asplus）1
	private String productnumber_asplus_emphasissub1; //品番（Asplus）_強調1
	private String productnumber_asplus2sub; //品番（Asplus）2
	private String productnumber_asplus_emphasis2sub; //品番（Asplus）_強調2
	private String productnumber_asplus3sub; //品番（Asplus）3
	private String productnumber_asplus_emphasis3sub; //品番（Asplus）_強調3
	private String productnumber_asplus4sub; //品番（Asplus）4
	private String productnumber_asplus5sub; //品番（Asplus）5
	private String productnumber_asplus6sub; //品番（Asplus）6
	private String productnumber_asplus7sub; //品番（Asplus）7
	private String productnumber_asplus8sub; //品番（Asplus）8
	private String productnumber_asplus9sub; //品番（Asplus）9
	private String productnumber_Color; //品番カラー
	private String qrcodeinformation; //QRCode情報
	private String storagelocation; //保管先
	private String inventorymanagementclassification; //在庫管理区分
	private String quantity; // 数量
	private String outline; // 白抜き
	private String paperColor; // 用紙色
	private String quantitySelectionAvailability; // 数量選択有無
	private String quantitySelectionRange; // 数量選択幅
	private String pattern; // パターン
	private String photoAvailability; // 写真有無
	private float    rate; //単価
	
	public Masteritemtag() {}
	public Masteritemtag(String identifier, String identificationnumber, String vendor, String productname_asplus, String productnumber_asplus,
	                     String productname_emphasisColor, String productnumber_asplussub1, String productnumber_asplus_emphasissub1,
	                     String productnumber_asplus2sub, String productnumber_asplus_emphasis2sub, String productnumber_asplus3sub,
	                     String productnumber_asplus_emphasis3sub, String productnumber_asplus4sub, String productnumber_asplus5sub,
	                     String productnumber_asplus6sub, String productnumber_asplus7sub, String productnumber_asplus8sub,
	                     String productnumber_asplus9sub, String productnumber_Color, String qrcodeinformation, String storagelocation,
	                     String inventorymanagementclassification, String quantity, String outline, String paperColor, 
	                     String quantitySelectionAvailability, String quantitySelectionRange, String pattern, String photoAvailability,float rate) {

	    this.identifier = identifier;
	    this.identificationnumber = identificationnumber;
	    this.vendor = vendor;
	    this.productname_asplus = productname_asplus;
	    this.productnumber_asplus = productnumber_asplus;
	    this.productname_emphasisColor = productname_emphasisColor;
	    this.productnumber_asplussub1 = productnumber_asplussub1;
	    this.productnumber_asplus_emphasissub1 = productnumber_asplus_emphasissub1;
	    this.productnumber_asplus2sub = productnumber_asplus2sub;
	    this.productnumber_asplus_emphasis2sub = productnumber_asplus_emphasis2sub;
	    this.productnumber_asplus3sub = productnumber_asplus3sub;
	    this.productnumber_asplus_emphasis3sub = productnumber_asplus_emphasis3sub;
	    this.productnumber_asplus4sub = productnumber_asplus4sub;
	    this.productnumber_asplus5sub = productnumber_asplus5sub;
	    this.productnumber_asplus6sub = productnumber_asplus6sub;
	    this.productnumber_asplus7sub = productnumber_asplus7sub;
	    this.productnumber_asplus8sub = productnumber_asplus8sub;
	    this.productnumber_asplus9sub = productnumber_asplus9sub;
	    this.productnumber_Color = productnumber_Color;
	    this.qrcodeinformation = qrcodeinformation;
	    this.storagelocation = storagelocation;
	    this.inventorymanagementclassification = inventorymanagementclassification;
	    this.quantity = quantity;
	    this.outline = outline;
	    this.paperColor = paperColor;
	    this.quantitySelectionAvailability = quantitySelectionAvailability;
	    this.quantitySelectionRange = quantitySelectionRange;
	    this.pattern = pattern;
	    this.photoAvailability = photoAvailability;
	    this.rate=rate;
	}

	public Masteritemtag(int id, String identifier, String identificationnumber, String vendor, String productname_asplus, String productnumber_asplus,
	                     String productname_emphasisColor, String productnumber_asplussub1, String productnumber_asplus_emphasissub1,
	                     String productnumber_asplus2sub, String productnumber_asplus_emphasis2sub, String productnumber_asplus3sub,
	                     String productnumber_asplus_emphasis3sub, String productnumber_asplus4sub, String productnumber_asplus5sub,
	                     String productnumber_asplus6sub, String productnumber_asplus7sub, String productnumber_asplus8sub,
	                     String productnumber_asplus9sub, String productnumber_Color, String qrcodeinformation, String storagelocation,
	                     String inventorymanagementclassification, String quantity, String outline, String paperColor, 
	                     String quantitySelectionAvailability, String quantitySelectionRange, String pattern,String photoAvailability,float rate) {

	    this(identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
	         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
	         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
	         qrcodeinformation, storagelocation, inventorymanagementclassification, quantity, outline, paperColor, 
	         quantitySelectionAvailability, quantitySelectionRange, pattern, photoAvailability,rate);

	    this.id = id;
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

	public String getProductname_emphasisColor() {
		return productname_emphasisColor;
	}

	public void setProductname_emphasisColor(String productname_emphasisColor) {
		this.productname_emphasisColor = productname_emphasisColor;
	}

	public String getProductnumber_asplus() {
		return productnumber_asplus;
	}

	public void setProductnumber_asplus(String productnumber_asplus) {
		this.productnumber_asplus = productnumber_asplus;
	}

	public String getProductnumber_asplussub1() {
		return productnumber_asplussub1;
	}

	public void setProductnumber_asplussub1(String productnumber_asplussub1) {
		this.productnumber_asplussub1 = productnumber_asplussub1;
	}

	public String getProductnumber_asplus_emphasissub1() {
		return productnumber_asplus_emphasissub1;
	}

	public void setProductnumber_asplus_emphasissub1(String productnumber_asplus_emphasissub1) {
		this.productnumber_asplus_emphasissub1 = productnumber_asplus_emphasissub1;
	}

	public String getProductnumber_asplus2sub() {
		return productnumber_asplus2sub;
	}

	public void setProductnumber_asplus2sub(String productnumber_asplus2sub) {
		this.productnumber_asplus2sub = productnumber_asplus2sub;
	}

	public String getProductnumber_asplus_emphasis2sub() {
		return productnumber_asplus_emphasis2sub;
	}

	public void setProductnumber_asplus_emphasis2sub(String productnumber_asplus_emphasis2sub) {
		this.productnumber_asplus_emphasis2sub = productnumber_asplus_emphasis2sub;
	}

	public String getProductnumber_asplus3sub() {
		return productnumber_asplus3sub;
	}

	public void setProductnumber_asplus3sub(String productnumber_asplus3sub) {
		this.productnumber_asplus3sub = productnumber_asplus3sub;
	}

	public String getProductnumber_asplus_emphasis3sub() {
		return productnumber_asplus_emphasis3sub;
	}

	public void setProductnumber_asplus_emphasis3sub(String productnumber_asplus_emphasis3sub) {
		this.productnumber_asplus_emphasis3sub = productnumber_asplus_emphasis3sub;
	}

	public String getProductnumber_asplus4sub() {
		return productnumber_asplus4sub;
	}

	public void setProductnumber_asplus4sub(String productnumber_asplus4sub) {
		this.productnumber_asplus4sub = productnumber_asplus4sub;
	}

	public String getProductnumber_asplus5sub() {
		return productnumber_asplus5sub;
	}

	public void setProductnumber_asplus5sub(String productnumber_asplus5sub) {
		this.productnumber_asplus5sub = productnumber_asplus5sub;
	}

	public String getProductnumber_asplus6sub() {
		return productnumber_asplus6sub;
	}

	public void setProductnumber_asplus6sub(String productnumber_asplus6sub) {
		this.productnumber_asplus6sub = productnumber_asplus6sub;
	}

	public String getProductnumber_asplus7sub() {
		return productnumber_asplus7sub;
	}

	public void setProductnumber_asplus7sub(String productnumber_asplus7sub) {
		this.productnumber_asplus7sub = productnumber_asplus7sub;
	}

	public String getProductnumber_asplus8sub() {
		return productnumber_asplus8sub;
	}

	public void setProductnumber_asplus8sub(String productnumber_asplus8sub) {
		this.productnumber_asplus8sub = productnumber_asplus8sub;
	}

	public String getProductnumber_asplus9sub() {
		return productnumber_asplus9sub;
	}

	public void setProductnumber_asplus9sub(String productnumber_asplus9sub) {
		this.productnumber_asplus9sub = productnumber_asplus9sub;
	}

	public String getProductnumber_Color() {
		return productnumber_Color;
	}

	public void setProductnumber_Color(String productnumber_Color) {
		this.productnumber_Color = productnumber_Color;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getPaperColor() {
		return paperColor;
	}

	public void setPaperColor(String paperColor) {
		this.paperColor = paperColor;
	}

	public String getQuantitySelectionAvailability() {
		return quantitySelectionAvailability;
	}

	public void setQuantitySelectionAvailability(String quantitySelectionAvailability) {
		this.quantitySelectionAvailability = quantitySelectionAvailability;
	}

	public String getQuantitySelectionRange() {
		return quantitySelectionRange;
	}

	public void setQuantitySelectionRange(String quantitySelectionRange) {
		this.quantitySelectionRange = quantitySelectionRange;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPhotoAvailability() {
		return photoAvailability;
	}

	public void setPhotoAvailability(String photoAvailability) {
		this.photoAvailability = photoAvailability;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
}
