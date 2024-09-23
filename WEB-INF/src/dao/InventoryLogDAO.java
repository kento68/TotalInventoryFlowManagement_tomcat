package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import model.InventoryLog;
import model.Inventorymanagementinquiry;

/**
 * Servlet implementation class LogDAO
 */
@WebServlet("/InventoryLogDAO")
public class InventoryLogDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

private void getConnection() throws NamingException, SQLException{
			Context context=new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/jsp");
			this.db=ds.getConnection();
	}
	private void disconnect(){
		try{
			if(rs != null){rs.close();}
			if(ps != null){ps.close();}
			if(db != null){db.close();}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public List<InventoryLog> findAll(){
		List<InventoryLog> inventorylogList=new ArrayList<>();
		try {
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM logs ORDER BY id DESC");
			rs=ps.executeQuery();
			while(rs.next()){
				int id=rs.getInt("id");
				java.sql.Date recorddate=rs.getDate("recorddate");
				java.sql.Time recordtime=rs.getTime("recordtime");
				String devicename=rs.getString("devicename");
				String importdate=rs.getString("importdate");
				String personinchargeno=rs.getString("personinchargeno");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				String productname=rs.getString("productname");
				int importquantity=rs.getInt("importquantity");
				String status=rs.getString("status");
				String inventory_status=rs.getString("inventory_status");
				String locationnumber_m1=rs.getString("locationnumber_m1");
				String status_m1=rs.getString("status_m1");
				int quantity=rs.getInt("quantity");
				String status2=rs.getString("status2");
				String locationnumber_m2=rs.getString("locationnumber_m2");
				String status_m2=rs.getString("status_m2");
				int inventory=rs.getInt("inventory");
				String storagelocation=rs.getString("storagelocation");
				String identificationnumber=rs.getString("identificationnumber");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				String remarks=rs.getString("remarks");
				
				InventoryLog inventorylog=new InventoryLog(id,recorddate,recordtime,devicename,importdate,personinchargeno,locationnumber,locationnumberdestination,
						productname,importquantity,status,inventory_status,locationnumber_m1,status_m1,quantity,status2,locationnumber_m2,
						status_m2,inventory,storagelocation,identificationnumber,inventorymanagementclassification,remarks);
				
			inventorylogList.add(inventorylog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return inventorylogList;
	}
	public boolean insertOne(InventoryLog inventorylog){
	    try {
	        this.getConnection();

	        // 挿入前の重複チェック
	        ps = db.prepareStatement("SELECT COUNT(*) FROM logs WHERE recorddate = ? "
	                + "AND recordtime = ? AND devicename = ? AND importdate = ? "
	                + "AND personinchargeno = ? AND locationnumber = ? AND locationnumberdestination = ? "
	                + "AND productname = ? AND importquantity = ? AND status = ? "
	                + "AND inventory_status = ? AND locationnumber_m1 = ? AND status_m1 = ? "
	                + "AND quantity = ? AND status2 = ? AND locationnumber_m2 = ? "
	                + "AND status_m2 = ? AND inventory = ? AND storagelocation = ? "
	                + "AND identificationnumber = ? AND inventorymanagementclassification = ?  AND remarks = ?");

	        ps.setDate(1, inventorylog.getRecorddate());
	        ps.setTime(2, inventorylog.getRecordtime());
	        ps.setString(3, inventorylog.getDevicename());
	        ps.setString(4, inventorylog.getImportdate());
	        ps.setString(5, inventorylog.getPersoninchargeno());
	        ps.setString(6, inventorylog.getLocationnumber());
	        ps.setString(7, inventorylog.getLocationnumberdestination());
	        ps.setString(8, inventorylog.getProductname());
	        ps.setInt(9, inventorylog.getImportquantity());
	        ps.setString(10, inventorylog.getStatus());
	        ps.setString(11, inventorylog.getInventory_status());
	        ps.setString(12, inventorylog.getLocationnumber_m1());
	        ps.setString(13, inventorylog.getStatus_m1());
	        ps.setInt(14, inventorylog.getQuantity());
	        ps.setString(15, inventorylog.getStatus2());
	        ps.setString(16, inventorylog.getLocationnumber_m2());
	        ps.setString(17, inventorylog.getStatus_m2());
	        ps.setInt(18, inventorylog.getInventory());
	        ps.setString(19, inventorylog.getStoragelocation());
	        ps.setString(20, inventorylog.getIdentificationnumber());
	        ps.setString(21, inventorylog.getInventorymanagementclassification());
	        ps.setString(22, inventorylog.getRemarks());
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return false;
	        }
	        rs.close();
	        ps.close();

	        // データの挿入
	        ps = db.prepareStatement("INSERT INTO logs(recorddate, recordtime, devicename, importdate, "
	                + "personinchargeno, locationnumber, locationnumberdestination, productname, importquantity, status, "
	                + "inventory_status, locationnumber_m1, status_m1, quantity, status2, locationnumber_m2, status_m2, "
	                + "inventory, storagelocation, identificationnumber, inventorymanagementclassification, remarks) "
	                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

	        ps.setDate(1, inventorylog.getRecorddate());
	        ps.setTime(2, inventorylog.getRecordtime());
	        ps.setString(3, inventorylog.getDevicename());
	        ps.setString(4, inventorylog.getImportdate());
	        ps.setString(5, inventorylog.getPersoninchargeno());
	        ps.setString(6, inventorylog.getLocationnumber());
	        ps.setString(7, inventorylog.getLocationnumberdestination());
	        ps.setString(8, inventorylog.getProductname());
	        ps.setInt(9, inventorylog.getImportquantity());
	        ps.setString(10, inventorylog.getStatus());
	        ps.setString(11, inventorylog.getInventory_status());
	        ps.setString(12, inventorylog.getLocationnumber_m1());
	        ps.setString(13, inventorylog.getStatus_m1());
	        ps.setInt(14, inventorylog.getQuantity());
	        ps.setString(15, inventorylog.getStatus2());
	        ps.setString(16, inventorylog.getLocationnumber_m2());
	        ps.setString(17, inventorylog.getStatus_m2());
	        ps.setInt(18, inventorylog.getInventory());
	        ps.setString(19, inventorylog.getStoragelocation());
	        ps.setString(20, inventorylog.getIdentificationnumber());
	        ps.setString(21, inventorylog.getInventorymanagementclassification());
	        ps.setString(22, inventorylog.getRemarks());

	        int result = ps.executeUpdate();

	        if(result != 1){
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } catch (NamingException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        this.disconnect();
	    }
	    return true;
	}
	public InventoryLog findOne(int id){
		InventoryLog inventorylog=null;
		try{
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM logs WHERE id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				java.sql.Date recorddate=rs.getDate("recorddate");
				java.sql.Time recordtime=rs.getTime("recordtime");
				String devicename=rs.getString("devicename");
				String importdate=rs.getString("importdate");
				String personinchargeno=rs.getString("personinchargeno");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				String productname=rs.getString("productname");
				int importquantity=rs.getInt("importquantity");
				String status=rs.getString("status");
				String inventory_status=rs.getString("inventory_status");
				String locationnumber_m1=rs.getString("locationnumber_m1");
				String status_m1=rs.getString("status_m1");
				int quantity=rs.getInt("quantity");
				String status2=rs.getString("status2");
				String locationnumber_m2=rs.getString("locationnumber_m2");
				String status_m2=rs.getString("status_m2");
				int inventory=rs.getInt("inventory");
				String storagelocation=rs.getString("storagelocation");
				String identificationnumber=rs.getString("identificationnumber");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				String remarks=rs.getString("remarks");
				
				inventorylog=new InventoryLog(id,recorddate,recordtime,devicename,importdate,personinchargeno,locationnumber,locationnumberdestination,
						productname,importquantity,status,inventory_status,locationnumber_m1,status_m1,quantity,status2,locationnumber_m2,
						status_m2,inventory,storagelocation,identificationnumber,inventorymanagementclassification,remarks);
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return inventorylog;
	}
	public boolean updateOne(InventoryLog inventorylog){
		try{
			this.getConnection();
			ps = db.prepareStatement("UPDATE logs SET recorddate=?, recordtime=?, devicename=?, importdate=?, "
			        + "personinchargeno=?, locationnumber=?, locationnumberdestination=?, productname=?, importquantity=?, status=?, "
			        + "inventory_status=?, locationnumber_m1=?, status_m1=?, quantity=?, status2=?, locationnumber_m2=?, status_m2=?, "
			        + "inventory=?, storagelocation=?, identificationnumber=?, inventorymanagementclassification=?, remarks=? "
			        + "WHERE id=?");

	        ps.setDate(1, inventorylog.getRecorddate());
	        ps.setTime(2, inventorylog.getRecordtime());
	        ps.setString(3, inventorylog.getDevicename());
	        ps.setString(4, inventorylog.getImportdate());
	        ps.setString(5, inventorylog.getPersoninchargeno());
	        ps.setString(6, inventorylog.getLocationnumber());
	        ps.setString(7, inventorylog.getLocationnumberdestination());
	        ps.setString(8, inventorylog.getProductname());
	        ps.setInt(9, inventorylog.getImportquantity());
	        ps.setString(10, inventorylog.getStatus());
	        ps.setString(11, inventorylog.getInventory_status());
	        ps.setString(12, inventorylog.getLocationnumber_m1());
	        ps.setString(13, inventorylog.getStatus_m1());
	        ps.setInt(14, inventorylog.getQuantity());
	        ps.setString(15, inventorylog.getStatus2());
	        ps.setString(16, inventorylog.getLocationnumber_m2());
	        ps.setString(17, inventorylog.getStatus_m2());
	        ps.setInt(18, inventorylog.getInventory());
	        ps.setString(19, inventorylog.getStoragelocation());
	        ps.setString(20, inventorylog.getIdentificationnumber());
	        ps.setString(21, inventorylog.getInventorymanagementclassification());
	        ps.setString(22, inventorylog.getRemarks());

			ps.setInt(23, inventorylog.getId());
			int result=ps.executeUpdate();
			if(result != 1){
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return true;
	}
	
	public boolean deleteOne(int id){

		try{
			this.getConnection();
			ps=db.prepareStatement("DELETE FROM logs WHERE id=?");
			ps.setInt(1, id);
			int result=ps.executeUpdate();
			if(result != 1){
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return true;
	}
	public List<InventoryLog> searchOne(String search) throws NamingException {
	    List<InventoryLog> inventorylogList = new ArrayList<>();
	    try {
	    	this.getConnection();
	    	ps = db.prepareStatement("SELECT * FROM logs WHERE "
	    	        + "recorddate LIKE ? OR recordtime LIKE ? OR devicename LIKE ? OR importdate LIKE ? OR "
	    	        + "personinchargeno LIKE ? OR locationnumber LIKE ? OR locationnumberdestination LIKE ? OR "
	    	        + "productname LIKE ? OR importquantity LIKE ? OR status LIKE ? OR inventory_status LIKE ? OR "
	    	        + "locationnumber_m1 LIKE ? OR status_m1 LIKE ? OR quantity LIKE ? OR status2 LIKE ? OR "
	    	        + "locationnumber_m2 LIKE ? OR status_m2 LIKE ? OR inventory LIKE ? OR storagelocation LIKE ? OR "
	    	        + "identificationnumber LIKE ? OR inventorymanagementclassification LIKE ? OR remarks LIKE ?");

	    	String searchPattern = "%" + search + "%";

	    	ps.setString(1, searchPattern);  // recorddate LIKE ?
	    	ps.setString(2, searchPattern);  // recordtime LIKE ?
	    	ps.setString(3, searchPattern);  // devicename LIKE ?
	    	ps.setString(4, searchPattern);  // importdate LIKE ?
	    	ps.setString(5, searchPattern);  // personinchargeno LIKE ?
	    	ps.setString(6, searchPattern);  // locationnumber LIKE ?
	    	ps.setString(7, searchPattern);  // locationnumberdestination LIKE ?
	    	ps.setString(8, searchPattern);  // productname LIKE ?
	    	ps.setString(9, searchPattern);  // importquantity LIKE ?
	    	ps.setString(10, searchPattern); // status LIKE ?
	    	ps.setString(11, searchPattern); // inventory_status LIKE ?
	    	ps.setString(12, searchPattern); // locationnumber_m1 LIKE ?
	    	ps.setString(13, searchPattern); // status_m1 LIKE ?
	    	ps.setString(14, searchPattern); // quantity LIKE ?
	    	ps.setString(15, searchPattern); // status2 LIKE ?
	    	ps.setString(16, searchPattern); // locationnumber_m2 LIKE ?
	    	ps.setString(17, searchPattern); // status_m2 LIKE ?
	    	ps.setString(18, searchPattern); // inventory LIKE ?
	    	ps.setString(19, searchPattern); // storagelocation LIKE ?
	    	ps.setString(20, searchPattern); // identificationnumber LIKE ?
	    	ps.setString(21, searchPattern); // inventorymanagementclassification LIKE ?
	    	ps.setString(22, searchPattern); // remarks LIKE ?
	        
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            java.sql.Date recorddate=rs.getDate("recorddate");
	            java.sql.Time recordtime=rs.getTime("recordtime");
				String devicename=rs.getString("devicename");
				String importdate=rs.getString("importdate");
				String personinchargeno=rs.getString("personinchargeno");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				String productname=rs.getString("productname");
				int importquantity=rs.getInt("importquantity");
				String status=rs.getString("status");
				String inventory_status=rs.getString("inventory_status");
				String locationnumber_m1=rs.getString("locationnumber_m1");
				String status_m1=rs.getString("status_m1");
				int quantity=rs.getInt("quantity");
				String status2=rs.getString("status2");
				String locationnumber_m2=rs.getString("locationnumber_m2");
				String status_m2=rs.getString("status_m2");
				int inventory=rs.getInt("inventory");
				String storagelocation=rs.getString("storagelocation");
				String identificationnumber=rs.getString("identificationnumber");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				String remarks=rs.getString("remarks");
				
				// Create Masterinventory object with retrieved values
				InventoryLog inventorylog=new InventoryLog(id,recorddate,recordtime,devicename,importdate,personinchargeno,locationnumber,locationnumberdestination,
						productname,importquantity,status,inventory_status,locationnumber_m1,status_m1,quantity,status2,locationnumber_m2,
						status_m2,inventory,storagelocation,identificationnumber,inventorymanagementclassification,remarks);
				
				
				inventorylogList.add(inventorylog);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return inventorylogList;
	}
	
	public List<InventoryLog> importOne(List<String> lines) throws ParseException, SQLException {
	    List<InventoryLog> inventoryLogList = new ArrayList<>();

	    for (String line : lines) {
	        line = line.replace("\uFEFF", ""); // BOMを除去
			String[] values = line.split(",");

       // 列数が足りない場合、固定値で補完
			if (values.length < 13) {
			    String[] tempValues = new String[13];
			    System.arraycopy(values, 0, tempValues, 0, values.length);
			    for (int i = values.length; i < 13; i++) {
			        tempValues[i] = (i == 6 || i == 7 || i == 9 || i == 10 || i == 11) ? "0" : "-";
			    }
			    values = tempValues;
			};
			
			
			if ("StorageLog".equals(values[2]) || "RetrievalLog".equals(values[2])|| "InventoryLog".equals(values[2])) {
			    try {
			    	
			    	 // 日付フォーマット
			    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			    	// 時刻フォーマット
			        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

			        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			        String currentDateAndTime = sdf.format(new Date());
			        
			        String InventoryLogValue8 = null; 
			        String InventoryLogValue20 = null; 
			        String InventoryLogValue21 = null; 

			        // 正規表現を使って分割する
			        if (values[5].matches(".*\\*.*\\*\\{.*\\}")) {
			            String[] parts = values[5].split("\\*|\\{|\\}");

			            // parts の長さが少なくとも 3 以上であることを確認
			            if (parts.length >= 3) {
			                if (parts[0] != null && parts[1] != null && parts[3] != null) {
			                	InventoryLogValue8 = parts[0]; //sql← csv:productname_productnumber
			                	InventoryLogValue20 = parts[3]; //sql← csv:identificationnumber

			                    // value8 の確認
			                    if (parts[1].length() == 4) {
			                    	InventoryLogValue21 = parts[1]; //sql← csv:inventorymanagementclassification
			                    } else {
			                        System.err.println("部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1]);
			                        continue; // あるいはエラー処理などを行う
			                    }
			                } else {
			                    // どれかの要素が null の場合は処理を中断する
			                    System.err.println("部品の解析中にエラーが発生しました。部品データが不完全です");
			                    continue; // あるいはエラー処理などを行う
			                }
			            } else {
			                // parts の長さが足りない場合の処理
			                System.err.println("部品の解析中にエラーが発生しました。parts の長さが足りません");
			                continue; // あるいはエラー処理などを行う
			            }
			        }

			        // 各フィールドの値を変数に代入
			        java.sql.Date InventoryLogValue1 = new java.sql.Date(dateFormat.parse(values[0]).getTime());
			        java.sql.Time InventoryLogValue2 = new java.sql.Time(timeFormat.parse(values[1]).getTime());
			        String InventoryLogValue3 = values[2];
			        String InventoryLogValue4 = currentDateAndTime;
			        String InventoryLogValue5 = values[3];
			        String InventoryLogValue6 = values[4];
			        String InventoryLogValue7 = "-";
			        int InventoryLogValue9 = Integer.parseInt(values[6]);
			        String InventoryLogValue10 = "-";
			        String InventoryLogValue11 = "-";
			        String InventoryLogValue12 = "-";
			        String InventoryLogValue13 = "-";
			        int InventoryLogValue14 = 0;
			        String InventoryLogValue15 = "-";
			        String InventoryLogValue16 = "-";
			        String InventoryLogValue17 = "-";
			        int InventoryLogValue18 = 0;
			        String InventoryLogValue19 = "-";
			        String InventoryLogValue22 = values[0] + " " + values[1]; // 行数を一意のキーとして使用 //sql← csv:remarks

			        // InventoryLog オブジェクトを生成
			        InventoryLog inventoryLog = new InventoryLog(
			            0, // ここはIDのようなものが必要であれば適宜設定
			            InventoryLogValue1, // RecordDate
			            InventoryLogValue2, // Recordtime
			            InventoryLogValue3, // DeviceName
			            InventoryLogValue4, // 現在の日時をRecordDateに設定
			            InventoryLogValue5, // PersonInChargeNo
			            InventoryLogValue6, // LocationNumber
			            InventoryLogValue7, // LocationNumberDestination
			            InventoryLogValue8, // ImportQuantity
			            InventoryLogValue9, // Status
			            InventoryLogValue10, // InventoryStatus
			            InventoryLogValue11, // LocationNumber_M1
			            InventoryLogValue12, // Status_M1
			            InventoryLogValue13, // Quantity
			            InventoryLogValue14, // Status2
			            InventoryLogValue15, // LocationNumber_M2
			            InventoryLogValue16, // Status_M2
			            InventoryLogValue17, // Inventory
			            InventoryLogValue18, // StorageLocation
			            InventoryLogValue19, // IdentificationNumber 
			            InventoryLogValue20, // InventoryManagementClassification
			            InventoryLogValue21, // InventoryManagementClassification
			            InventoryLogValue22
			        );
			        
			        // リストに追加
			        inventoryLogList.add(inventoryLog); 

			    } catch (ParseException e) {
			        System.err.println("日付の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
			    } catch (NumberFormatException e) {
			        System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
			    }
			} else if ("MoveLog".equals(values[2])) {
			    processMoveLog(values, inventoryLogList);
			} else {
			    System.out.println("Unknown log type in data: " + values[2]);
			}

	    }
	    return inventoryLogList;
	}

	
	private void processMoveLog(String[] values, List<InventoryLog> inventoryLogList) throws ParseException {
	    try {
       	    // 日付フォーマット
       	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
       	    // 時刻フォーマット
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateAndTime = sdf.format(new Date());
	        // MoveLog 特有の処理
            String InventoryLogValue8 = null; 
            String InventoryLogValue20 = null; 
            String InventoryLogValue21 = null; 

         // 正規表現を使って分割する
            if (values[6].matches(".*\\*.*\\*\\{.*\\}")) {
                String[] parts = values[6].split("\\*|\\{|\\}");

                // parts の長さが少なくとも 3 以上であることを確認
                if (parts.length >= 3) {
                    if (parts[0] != null && parts[1] != null && parts[3] != null) {
                    	InventoryLogValue8 = parts[0]; //sql← csv:productname_productnumber
                    	InventoryLogValue20 = parts[3]; //sql← csv:identificationnumber

                        // value8 の確認
                        if (parts[1].length() == 4) {
                        	InventoryLogValue21 = parts[1]; //sql← csv:inventorymanagementclassification
                        } else {
	                        System.err.println("部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1]);
	                        return; // あるいはエラー処理などを行う
	                    }
	                } else {
	                    // どれかの要素が null の場合は処理を中断する
	                    System.err.println("部品の解析中にエラーが発生しました。部品データが不完全です");
	                    return; // あるいはエラー処理などを行う
	                }
	            } else {
	                // parts の長さが足りない場合の処理
	                System.err.println("部品の解析中にエラーが発生しました。parts の長さが足りません");
	                return; // あるいはエラー処理などを行う
	            }
	        }
            // 各フィールドの値を変数に代入
            java.sql.Date InventoryLogValue1 = new java.sql.Date(dateFormat.parse(values[0]).getTime());
            java.sql.Time InventoryLogValue2 = new java.sql.Time(timeFormat.parse(values[1]).getTime());       
            String InventoryLogValue3 = values[2];
            String InventoryLogValue4 = currentDateAndTime;
            String InventoryLogValue5 = values[3];
            String InventoryLogValue6 = values[4];
            String InventoryLogValue7 = values[5];
            int InventoryLogValue9 = Integer.parseInt(values[7]);
            String InventoryLogValue10 = "-";
            String InventoryLogValue11 = "-";
            String InventoryLogValue12 = values[4];
            String InventoryLogValue13 = "-";
            int InventoryLogValue14 = 0;
            String InventoryLogValue15 = "-";
            String InventoryLogValue16 = values[5];
            String InventoryLogValue17 = "-";
            int InventoryLogValue18 = 0;
            String InventoryLogValue19 = "-";
            String InventoryLogValue22 = values[0] + " " + values[1]; // 行数を一意のキーとして使用 //sql← csv:remarks


         // InventoryLog オブジェクトを生成
            InventoryLog inventoryLog = new InventoryLog(
                0, // ここはIDのようなものが必要であれば適宜設定
                InventoryLogValue1, // RecordDate
                InventoryLogValue2, // Time
                InventoryLogValue3, // DeviceName
                InventoryLogValue4, // 現在の日時をRecordDateに設定
                InventoryLogValue5, // PersonInChargeNo
                InventoryLogValue6, // LocationNumber
                InventoryLogValue7, // LocationNumberDestination
                InventoryLogValue8, // ImportQuantity
                InventoryLogValue9, // Status
                InventoryLogValue10, // InventoryStatus
                InventoryLogValue11, // LocationNumber_M1
                InventoryLogValue12, // Status_M1
                InventoryLogValue13, // Quantity
                InventoryLogValue14, // Status2
                InventoryLogValue15, // LocationNumber_M2
                InventoryLogValue16, // Status_M2
                InventoryLogValue17, // Inventory
                InventoryLogValue18, // StorageLocation
                InventoryLogValue19, // IdentificationNumber
                InventoryLogValue20, // InventoryManagementClassification
                InventoryLogValue21, // InventoryManagementClassification
                InventoryLogValue22
            );

	        // ここで MoveLog 特有の処理を実行
	        inventoryLogList.add(inventoryLog);

        } catch (ParseException e) {
            System.err.println("日付の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        }
	}
	
	public void save(List<InventoryLog> inventorylogList, List<Inventorymanagementinquiry> inventorymanagementinquiries, List<String> allLines) throws NamingException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        this.getConnection();

	        // 日付フォーマットの定義
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	        // Inventorymanagementinquiry リストを Map に変換
	        Map<String, Inventorymanagementinquiry> inquiryMap = new HashMap<>();
	        for (Inventorymanagementinquiry inquiry : inventorymanagementinquiries) {
	            inquiryMap.put(inquiry.getRemarks(), inquiry);
	        }
	        
	     // InventoryLog リストをソートする
	        List<InventoryLog> sortedList = new ArrayList<>(inventorylogList);
	        sortedList.sort((o1, o2) -> {
	            try {
	                Date date1 = sdf.parse(o1.getRemarks());
	                Date date2 = sdf.parse(o2.getRemarks());
	                return date1.compareTo(date2);
	            } catch (ParseException e) {
	                e.printStackTrace();
	                return 0; // エラーが発生した場合、順序を変えない
	            }
	        });

	        // トランザクション開始
	        db.setAutoCommit(false);
	        
	        Set<String> processedLogs = new HashSet<>();

	        for (InventoryLog inventoryLog : sortedList) {
	            if (processedLogs.contains(inventoryLog.getRemarks())) {
	                continue; // 既に処理されたログはスキップ
	            }

	            Inventorymanagementinquiry matchingInquiry = inquiryMap.get(inventoryLog.getRemarks());

	            if (matchingInquiry != null) {
	                boolean existingRecord = existsInDatabase1(inventoryLog.getRecorddate(), inventoryLog.getRecordtime(), inventoryLog.getImportdate());

	                if (!existingRecord) {
	                    switch (inventoryLog.getDevicename()) {
	                        case "StorageLog":
	                        case "RetrievalLog":
	                        case "MoveLog":
	                        case "InventoryLog":
	                        	processLog(db, ps, inventoryLog, matchingInquiry);
	                            break;
	                        default:
	                            System.out.println("Unknown device name: " + inventoryLog.getDevicename());
	                    }
	                }
	                processedLogs.add(inventoryLog.getRemarks());
	            } else {
	                System.out.println("No matching Inventorymanagementinquiry for InventoryLog: " + inventoryLog.getRemarks());
	            }
	        }

	        // コミット
	        db.commit();
	    } catch (Exception e) {
	        try {
	            if (db != null) {
	                db.rollback();
	            }
	        } catch (SQLException se) {
	            se.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	            if (db != null) db.close();
	        } catch (SQLException se) {
	            se.printStackTrace();
	        }
	    }
	}
	
	private void processLog(Connection db, PreparedStatement ps, InventoryLog inventoryLog, Inventorymanagementinquiry inventorymanagementinquiry) throws SQLException {

	        // InventoryLog の新規レコードを挿入する
	        ps = db.prepareStatement("INSERT INTO logs (recorddate, recordtime, devicename, importdate, personinchargeno, locationnumber, locationnumberdestination, "
	        		+ "productname, importquantity, status, inventory_status, locationnumber_m1, status_m1, quantity, status2, locationnumber_m2, status_m2, inventory, "
	        		+ "storagelocation, identificationnumber, inventorymanagementclassification, remarks) "
	        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
	        ps.setDate(1, inventoryLog.getRecorddate());
	        ps.setTime(2, inventoryLog.getRecordtime());
	        ps.setString(3, inventoryLog.getDevicename());
	        ps.setString(4, inventoryLog.getImportdate());
	        ps.setString(5, inventoryLog.getPersoninchargeno());
	        ps.setString(6, inventoryLog.getLocationnumber());
	        ps.setString(7, inventoryLog.getLocationnumberdestination());
	        ps.setString(8, inventoryLog.getProductname());
        	ps.setInt(9, inventoryLog.getImportquantity());
        	ps.setString(10, inventoryLog.getStatus());
        	ps.setString(11, inventoryLog.getInventory_status());
        	ps.setString(12, inventoryLog.getLocationnumber_m1());
        	ps.setString(13, inventoryLog.getStatus_m1());
        	ps.setInt(14, inventoryLog.getQuantity());
        	ps.setString(15, inventoryLog.getStatus2());
        	ps.setString(16, inventoryLog.getLocationnumber_m2());
        	ps.setString(17, inventoryLog.getStatus_m2());
        	ps.setInt(18, inventoryLog.getInventory());
        	ps.setString(19, inventoryLog.getStoragelocation());
        	ps.setString(20, inventoryLog.getIdentificationnumber());
        	ps.setString(21, inventoryLog.getInventorymanagementclassification());
        	ps.setString(22, inventoryLog.getRemarks());
        
        	ps.executeUpdate();
	    }
	
    //インポート時 recorddate & recordtime & importdate がかぶっているか確認
	private boolean existsInDatabase1(java.sql.Date recorddate, java.sql.Time recordtime, String importdate) throws SQLException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM logs WHERE recorddate = ? AND recordtime = ? AND importdate = ?");
	        ps.setDate(1, recorddate);
	        ps.setTime(2, recordtime);
	        ps.setString(3, importdate);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	        return false;
	    } finally {
	        // リソースを閉じる
	        if (rs != null) {
	            rs.close();
	        }
	        if (ps != null) {
	            ps.close();
	        }
	    }
	}
	
	public void downloadAll() throws IOException {
	    PrintWriter csvWriter = null;

	    try {
	        // データベース接続の取得
	        this.getConnection();
	        
	        // SQL クエリの準備
	        ps = db.prepareStatement("SELECT * FROM logs");
	        rs = ps.executeQuery();
	        
	        // 現在の日時を取得
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateAndTime = sdf.format(new java.util.Date());

            // CSV のファイル名
            String fileName = "inventoryloglist_DL " + currentDateAndTime + ".csv";

	        // 適切なパスを選んでください
            String filePath = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName;
			
	        //<!-- UM425QA-KIR915W -->
	        //<!-- DESKTOP-KBUH9GC -->
	        //<!-- String filePath = "E:\\Program Files/"+ fileName; -->

            //<!-- Raspberry Pi(192.168.10.103 ) -->
            //<!-- Raspberry Pi(192.168.10.122 ) -->
	        //<!-- Raspberry Pi(192.168.10.118 ) -->
	        //<!-- String filePath = "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName; -->
	        
	        // CSV Writer の準備
	        csvWriter = new PrintWriter(filePath, "Shift-JIS");

	        // ヘッダー行
	        csvWriter.append("ID,記録日時,記録時間,機器名,取込日付,担当者No.,ロケ番,ロケ番_移動先,品名,取込個数,状態,在庫/状態,ロケ番_移動元,状態_-減算,個数_-減算,状態_移動先,ロケ番_移動先,状態_+加算,在庫_+加算,保管先,登録No.,在庫管理区分");
	        csvWriter.append("\n");

	        // データ行の書き出し
	        while (rs.next()) {
	            csvWriter.append(rs.getInt("id") + ",");
	            csvWriter.append(rs.getDate("recorddate") + ",");
	            csvWriter.append(rs.getTime("recordtime") + ",");
	            csvWriter.append(rs.getString("devicename") + ",");
	            csvWriter.append(rs.getString("importdate") + ",");
	            csvWriter.append(rs.getString("personinchargeno") + ",");
	            csvWriter.append(rs.getString("locationnumber") + ",");
	            csvWriter.append(rs.getString("locationnumberdestination") + ",");
	            csvWriter.append(rs.getString("productname") + ",");
	            csvWriter.append(rs.getInt("importquantity") + ",");
	            csvWriter.append(rs.getString("status") + ",");
	            csvWriter.append(rs.getString("inventory_status") + ",");
	            csvWriter.append(rs.getString("locationnumber_m1") + ",");
	            csvWriter.append(rs.getString("status_m1") + ",");
	            csvWriter.append(rs.getInt("quantity") + ",");
	            csvWriter.append(rs.getString("status2") + ",");
	            csvWriter.append(rs.getString("locationnumber_m2") + ",");
	            csvWriter.append(rs.getString("status_m2") + ",");
	            csvWriter.append(rs.getInt("inventory") + ",");
	            csvWriter.append(rs.getString("storagelocation") + ",");
	            csvWriter.append(rs.getString("identificationnumber") + ",");
	            csvWriter.append(rs.getString("inventorymanagementclassification"));
	            csvWriter.append("\n");
	        }

	        // ファイルへの書き込み
	        csvWriter.flush();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (NamingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        // リソースのクリーンアップ
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (db != null) db.close();
	            if (csvWriter != null) csvWriter.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 }
}


