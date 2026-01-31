package model;

import java.io.Serializable;

public class InventoryLog implements Serializable {
    private int id;
    private java.sql.Date  recorddate; // 記録日時
    private java.sql.Time  recordtime; // 記録時間
    private String devicename; // 機器名
    private String importdate; // 取込日付
    private String personinchargeno; // 担当者No.
    private String locationnumber; //ロケ番
    private String locationnumberdestination; //ロケ番_移動先
    private String productname; // 品名
    private int importquantity; // 取込個数
    private String status; // 状態
    private String inventory_status; // 在庫/状態
    private String locationnumber_m1; // ロケ番_移動元
    private String status_m1; // 状態_-減算
    private int quantity; // 在庫_-減算
    private String status2; // 状態_移動先
    private String locationnumber_m2; // ロケ番_移動先
    private String status_m2; // 状態_+加算
    private int inventory; // 在庫_+加算
    private String storagelocation; // 保管先
    private String identificationnumber; //登録No.
    private String inventorymanagementclassification; // 在庫管理区分
	private String remarks; //備考

    public InventoryLog() {}
    public InventoryLog(java.sql.Date  recorddate, java.sql.Time recordtime, String devicename, String importdate, String personinchargeno,
                        String locationnumber, String locationnumberdestination, String productname, int importquantity, String status,
                        String inventory_status, String locationnumber_m1, String status_m1, int quantity, String status2, String locationnumber_m2,
                        String status_m2, int inventory, String storagelocation, String identificationnumber, String inventorymanagementclassification, String remarks) {

        this.recorddate = recorddate;
        this.recordtime = recordtime;
        this.devicename = devicename;
        this.importdate = importdate;
        this.personinchargeno = personinchargeno;
        this.locationnumber = locationnumber;
        this.locationnumberdestination = locationnumberdestination;
        this.productname = productname;
        this.importquantity = importquantity;
        this.status = status;
        this.inventory_status = inventory_status;
        this.locationnumber_m1 = locationnumber_m1;
        this.status_m1 = status_m1;
        this.quantity = quantity;
        this.status2 = status2;
        this.locationnumber_m2 = locationnumber_m2;
        this.status_m2 = status_m2;
        this.inventory = inventory;
        this.storagelocation = storagelocation;
        this.identificationnumber = identificationnumber;
        this.inventorymanagementclassification = inventorymanagementclassification;
        this.remarks=remarks;
    }

    public InventoryLog(int id, java.sql.Date recorddate, java.sql.Time recordtime, String devicename, String importdate, String personinchargeno,
                        String locationnumber, String locationnumberdestination, String productname, int importquantity, String status,
                        String inventory_status, String locationnumber_m1, String status_m1, int quantity, String status2, String locationnumber_m2,
                        String status_m2, int inventory, String storagelocation, String identificationnumber, String inventorymanagementclassification, String remarks) {

        this(recorddate, recordtime, devicename, importdate, personinchargeno, locationnumber, locationnumberdestination,
             productname, importquantity, status, inventory_status, locationnumber_m1, status_m1, quantity, status2, locationnumber_m2,
             status_m2, inventory, storagelocation, identificationnumber, inventorymanagementclassification,remarks);

        this.id = id;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.sql.Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(java.sql.Date  recorddate) {
		this.recorddate = recorddate;
	}
	public java.sql.Time getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(java.sql.Time recordtime) {
		this.recordtime = recordtime;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
    public String getImportdate() {
        return importdate;
    }
    public void setImportdate(String importdate) {
        this.importdate = importdate;
    }
	public String getPersoninchargeno() {
		return personinchargeno;
	}
	public void setPersoninchargeno(String personinchargeno) {
		this.personinchargeno = personinchargeno;
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
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getImportquantity() {
		return importquantity;
	}
	public void setImportquantity(int importquantity) {
		this.importquantity = importquantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInventory_status() {
		return inventory_status;
	}
	public void setInventory_status(String inventory_status) {
		this.inventory_status = inventory_status;
	}
	public String getLocationnumber_m1() {
		return locationnumber_m1;
	}
	public void setLocationnumber_m1(String locationnumber_m1) {
		this.locationnumber_m1 = locationnumber_m1;
	}
	public String getStatus_m1() {
		return status_m1;
	}
	public void setStatus_m1(String status_m1) {
		this.status_m1 = status_m1;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getLocationnumber_m2() {
		return locationnumber_m2;
	}
	public void setLocationnumber_m2(String locationnumber_m2) {
		this.locationnumber_m2 = locationnumber_m2;
	}
	public String getStatus_m2() {
		return status_m2;
	}
	public void setStatus_m2(String status_m2) {
		this.status_m2 = status_m2;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public String getStoragelocation() {
		return storagelocation;
	}
	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}
	public String getIdentificationnumber() {
		return identificationnumber;
	}
	public void setIdentificationnumber(String identificationnumber) {
		this.identificationnumber = identificationnumber;
	}
	public String getInventorymanagementclassification() {
		return inventorymanagementclassification;
	}
	public void setInventorymanagementclassification(String inventorymanagementclassification) {
		this.inventorymanagementclassification = inventorymanagementclassification;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}