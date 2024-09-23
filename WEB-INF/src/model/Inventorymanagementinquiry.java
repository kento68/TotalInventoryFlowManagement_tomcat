package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inventorymanagementinquiry implements Serializable{
	private int    id;
	private String identificationnumber;  //登録No.
	private String productname_productnumber;  //W品名コード(Asplus)/W品名(Asplus)
	private String qrcodeinformation; //QRCode情報（W品名コード+W品名(品名仕様)）
	private String locationnumber; //ロケ番
	private String locationnumberdestination; //ロケ番_移動先
	private int    initialinventory; //期初在庫
	private int    theoreticalinventory; //理論在庫
	private String inventorymanagementclassification; //在庫管理区分
	private int    inventorystock; //棚卸在庫(実績)
	private int    investigationrequired; //要調査（誤差）
	private int    decision; //確定
	private String remarks; //備考
	private String fileattributes; //ファイル属性
	
    private String date;
    private String time;
    private LocalDateTime timestamp;
	
	public Inventorymanagementinquiry(){}
	public Inventorymanagementinquiry(String identificationnumber,String productname_productnumber,String qrcodeinformation,
			                          String locationnumber,String locationnumberdestination,int initialinventory,
			                          int theoreticalinventory,String inventorymanagementclassification,
			                          int inventorystock,int investigationrequired,int decision,String remarks,String fileattributes){
		
		this.identificationnumber=identificationnumber;
		this.productname_productnumber=productname_productnumber;
		this.qrcodeinformation=qrcodeinformation;
		this.locationnumber=locationnumber;
		this.locationnumberdestination=locationnumberdestination;
		this.initialinventory=initialinventory;
		this.theoreticalinventory=theoreticalinventory;
		this.inventorymanagementclassification=inventorymanagementclassification;
		this.inventorystock=inventorystock;
		this.investigationrequired=investigationrequired;
		this.decision=decision;
		this.remarks=remarks;
		this.fileattributes=fileattributes;
	}
	public Inventorymanagementinquiry(int id,String identificationnumber,String productname_productnumber,String qrcodeinformation,
			                          String locationnumber,String locationnumberdestination,int initialinventory,
                                      int theoreticalinventory,String inventorymanagementclassification,
                                      int inventorystock,int investigationrequired,int decision,String remarks,String fileattributes){
		
		this(identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,initialinventory,theoreticalinventory,inventorymanagementclassification,
				inventorystock,investigationrequired,decision,remarks,fileattributes);
		
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentificationnumber() {
		return identificationnumber;
	}
	public void setIdentificationnumber(String identificationnumber) {
		this.identificationnumber = identificationnumber;
	}
	public String getProductname_productnumber() {
		return productname_productnumber;
	}
	public void setProductname_productnumber(String productname_productnumber) {
		this.productname_productnumber = productname_productnumber;
	}
	public String getQrcodeinformation() {
		return qrcodeinformation;
	}
	public void setQrcodeinformation(String qrcodeinformation) {
		this.qrcodeinformation = qrcodeinformation;
	}
	public String getLocationnumber() {
		return locationnumber;
	}
	public void setLocationnumber(String locationnumber) {
		this.locationnumber = locationnumber;
	}
	public String getLocationnumberdestination() {
		return locationnumberdestination;
	}
	public void setLocationnumberdestination(String locationnumberdestination) {
		this.locationnumberdestination = locationnumberdestination;
	}
	public int getInitialinventory() {
		return initialinventory;
	}
	public void setInitialinventory(int initialinventory) {
		this.initialinventory = initialinventory;
	}
	public int getTheoreticalinventory() {
		return theoreticalinventory;
	}
	public void setTheoreticalinventory(int theoreticalinventory) {
		this.theoreticalinventory = theoreticalinventory;
	}
	public String getInventorymanagementclassification() {
		return inventorymanagementclassification;
	}
	public void setInventorymanagementclassification(String inventorymanagementclassification) {
		this.inventorymanagementclassification = inventorymanagementclassification;
	}
	public int getInventorystock() {
		return inventorystock;
	}
	public void setInventorystock(int inventorystock) {
		this.inventorystock = inventorystock;
	}
	public int getInvestigationrequired() {
		return investigationrequired;
	}
	public void setInvestigationrequired(int investigationrequired) {
		this.investigationrequired = investigationrequired;
	}
	public int getDecision() {
		return decision;
	}
	public void setDecision(int decision) {
		this.decision = decision;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFileattributes() {
		return fileattributes;
	}
	public void setFileattributes(String fileattributes) {
		this.fileattributes = fileattributes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setColumn7(String string) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	public void setColumn8(String string) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	public void setLogType(String logType) {
		// TODO 自動生成されたメソッド・スタブ
	}
	
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    // DateTimeFormatterを用意
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // 日付と時間からLocalDateTimeを更新するメソッド
    private void updateTimestamp() {
        if (date != null && time != null) {
            try {
                this.timestamp = LocalDateTime.parse(date + " " + time, formatter);
            } catch (Exception e) {
                e.printStackTrace();
                // フォーマットエラー時の処理
            }
        }
    }
}