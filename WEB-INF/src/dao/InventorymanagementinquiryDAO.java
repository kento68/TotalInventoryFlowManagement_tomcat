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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.InventoryLog;
import model.Inventorymanagementinquiry;

/**
 * Servlet implementation class InventoryDAO
 */
@WebServlet("/InventorymanagementinquiryDAO")
public class InventorymanagementinquiryDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;
	private Part[] fileParts;

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
	public List<Inventorymanagementinquiry> findAll(){
		List<Inventorymanagementinquiry> inventorymanagementinquiryList=new ArrayList<>();
		try {
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM inventorys ORDER BY id DESC");
			rs=ps.executeQuery();
			while(rs.next()){
				int id=rs.getInt("id");
				String identificationnumber=rs.getString("identificationnumber");
				String productname_productnumber=rs.getString("productname_productnumber");
				String qrcodeinformation=rs.getString("qrcodeinformation");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				int    initialinventory=rs.getInt("initialinventory");
				int    theoreticalinventory=rs.getInt("theoreticalinventory");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				int    inventorystock=rs.getInt("inventorystock");
				int    investigationrequired=rs.getInt("investigationrequired");
				int    decision=rs.getInt("decision");
				String remarks=rs.getString("remarks");
				String fileattributes=rs.getString("fileattributes");

				Inventorymanagementinquiry inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes);
				
				inventorymanagementinquiryList.add(inventorymanagementinquiry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return inventorymanagementinquiryList;
	}
	public boolean insertOne(Inventorymanagementinquiry inventorymanagementinquiry){
	    try {
	        this.getConnection();

	        // 挿入前の重複チェック
	        ps = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE identificationnumber = ? AND productname_productnumber = ? "
	                + "AND qrcodeinformation = ? AND locationnumber = ? AND locationnumberdestination = ? AND initialinventory = ? "
	                + "AND theoreticalinventory = ? AND inventorymanagementclassification = ? "
	                + "AND inventorystock = ? AND investigationrequired = ? AND decision = ? AND remarks = ? AND fileattributes = ?");
	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber());
	        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation());
	        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
	        ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination());
	        ps.setInt   (6, inventorymanagementinquiry.getInitialinventory());
	        ps.setInt   (7, inventorymanagementinquiry.getTheoreticalinventory());
	        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification());
	        ps.setInt   (9, inventorymanagementinquiry.getInventorystock());
	        ps.setInt   (10, inventorymanagementinquiry.getInvestigationrequired());
	        ps.setInt   (11, inventorymanagementinquiry.getDecision());
	        ps.setString(12, inventorymanagementinquiry.getRemarks());
	        ps.setString(13, inventorymanagementinquiry.getFileattributes());
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return false;
	        }
	        rs.close();
	        ps.close();

	        // データの挿入
	        ps = db.prepareStatement("INSERT INTO inventorys(identificationnumber,productname_productnumber,qrcodeinformation,"
	                + "locationnumber,locationnumberdestination,initialinventory,theoreticalinventory,inventorymanagementclassification,"
	                + "inventorystock,investigationrequired,decision,remarks,fileattributes) "
	                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber());
	        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation());
	        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
	        ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination());
	        ps.setInt   (6, inventorymanagementinquiry.getInitialinventory());
	        ps.setInt   (7, inventorymanagementinquiry.getTheoreticalinventory());
	        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification());
	        ps.setInt   (9, inventorymanagementinquiry.getInventorystock());
	        ps.setInt   (10, inventorymanagementinquiry.getInvestigationrequired());
	        ps.setInt   (11, inventorymanagementinquiry.getDecision());
	        ps.setString(12, inventorymanagementinquiry.getRemarks());
	        ps.setString(13, inventorymanagementinquiry.getFileattributes());

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
	public Inventorymanagementinquiry findOne(int id){
		Inventorymanagementinquiry inventorymanagementinquiry=null;
		try{
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM inventorys WHERE id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				String identificationnumber=rs.getString("identificationnumber");
				String productname_productnumber=rs.getString("productname_productnumber");
				String qrcodeinformation=rs.getString("qrcodeinformation");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				int    initialinventory=rs.getInt("initialinventory");
				int    theoreticalinventory=rs.getInt("theoreticalinventory");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				int    inventorystock=rs.getInt("inventorystock");
				int    investigationrequired=rs.getInt("investigationrequired");
				int    decision=rs.getInt("decision");
				String remarks=rs.getString("remarks");
				String fileattributes=rs.getString("fileattributes");
				
				inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return inventorymanagementinquiry;
	}
	
	public boolean updateOne(Inventorymanagementinquiry inventorymanagementinquiry){
	    try{
	        this.getConnection();
	        ps = db.prepareStatement("UPDATE inventorys SET identificationnumber=?,productname_productnumber=?,qrcodeinformation=?,"
	                + "locationnumber=?,locationnumberdestination=?,initialinventory=?,theoreticalinventory=?,"
	                + "inventorymanagementclassification=?,inventorystock=?,investigationrequired=?,decision=?,remarks=?,fileattributes=? "
	                + "WHERE id=?");

	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber());
	        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation());
	        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
	        ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination());

	        ps.setInt(6, inventorymanagementinquiry.getInitialinventory());
	        ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory());
	        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification());
	        ps.setInt(9, inventorymanagementinquiry.getInventorystock());
	        ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired());
	        ps.setInt(11, inventorymanagementinquiry.getDecision());
	        ps.setString(12, inventorymanagementinquiry.getRemarks());
	        ps.setString(13, inventorymanagementinquiry.getFileattributes());

	        ps.setInt(14, inventorymanagementinquiry.getId());
	        int result = ps.executeUpdate();
	        if(result != 1){
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // エラーメッセージを表示
	    } catch (NamingException e) {
	        e.printStackTrace();  // エラーメッセージを表示
	    } finally {
	        this.disconnect();
	    }
	    return true;
	}
	
	public boolean deleteOne(int id){

		try{
			this.getConnection();
			ps=db.prepareStatement("DELETE FROM inventorys WHERE id=?");
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
	
	public List<Inventorymanagementinquiry> searchOne(String search) {
	    List<Inventorymanagementinquiry> inventorymanagementinquiryList = new ArrayList<>();
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM inventorys WHERE "
	        		+ "identificationnumber LIKE ? OR productname_productnumber LIKE ? OR qrcodeinformation LIKE ? OR locationnumber LIKE ? OR "
	        		+ "locationnumberdestination LIKE ? OR initialinventory LIKE ? OR theoreticalinventory LIKE ? OR inventorymanagementclassification LIKE ? OR "
	        		+ "inventorystock LIKE ? OR investigationrequired LIKE ? OR decision LIKE ? OR remarks LIKE ? OR fileattributes LIKE ?");

	        String searchPattern = "%" + search + "%";
	        
	        ps.setString(1, searchPattern); // identificationnumber LIKE ?
	        ps.setString(2, searchPattern); // productname_productnumber LIKE ?
	        ps.setString(3, searchPattern); // qrcodeinformation LIKE ?
	        ps.setString(4, searchPattern); // locationnumber LIKE ?
	        ps.setString(5, searchPattern); // locationnumberdestination LIKE ?
	        ps.setString(6, searchPattern); // initialinventory LIKE ?
	        ps.setString(7, searchPattern); // theoreticalinventory LIKE ?
	        ps.setString(8, searchPattern); // inventorymanagementclassification LIKE ?
	        ps.setString(9, searchPattern); // inventorystock LIKE ?
	        ps.setString(10, searchPattern); // investigationrequired LIKE ?
	        ps.setString(11, searchPattern); // decision LIKE ?
	        ps.setString(12, searchPattern); // remarks LIKE ?
	        ps.setString(13, searchPattern); // fileattributes LIKE ?
	        
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
				String identificationnumber=rs.getString("identificationnumber");
				String productname_productnumber=rs.getString("productname_productnumber");
				String qrcodeinformation=rs.getString("qrcodeinformation");
				String locationnumber=rs.getString("locationnumber");
				String locationnumberdestination=rs.getString("locationnumberdestination");
				int    initialinventory=rs.getInt("initialinventory");
				int    theoreticalinventory=rs.getInt("theoreticalinventory");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				int    inventorystock=rs.getInt("inventorystock");
				int    investigationrequired=rs.getInt("investigationrequired");
				int    decision=rs.getInt("decision");
				String remarks=rs.getString("remarks");
				String fileattributes=rs.getString("fileattributes");

	            // Create Masterinventory object with retrieved values
				Inventorymanagementinquiry inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes);
				
				inventorymanagementinquiryList.add(inventorymanagementinquiry);
	        }
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return inventorymanagementinquiryList;
	}
	
	public List<Inventorymanagementinquiry> importOne(List<String> lines) {
	    List<Inventorymanagementinquiry> inventoryManagementInquiryList = new ArrayList<>();

	    for (String line : lines) {
	        try {
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
	            }

	            if ("StorageLog".equals(values[2]) || "RetrievalLog".equals(values[2])) {
	                try {
	                    String InventorymanagementinquiryValue1 = null;
	                    String InventorymanagementinquiryValue2 = null;
	                    String InventorymanagementinquiryValue8 = null;

	                    // 正規表現を使って分割する
	                    if (values[5].matches(".*\\*.*\\*\\{.*\\}")) {
	                        String[] parts = values[5].split("\\*|\\{|\\}");

	                        // parts の長さが少なくとも 3 以上であることを確認
	                        if (parts.length >= 3) {
	                            if (parts[0] != null && parts[1] != null && parts[3] != null) {
	                                InventorymanagementinquiryValue2 = parts[0]; //sql← csv:productname_productnumber
	                                InventorymanagementinquiryValue1 = parts[3]; //sql← csv:identificationnumber

	                                // value8 の確認
	                                if (parts[1].length() == 4) {
	                                    InventorymanagementinquiryValue8 = parts[1]; //sql← csv:inventorymanagementclassification
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

	                    String InventorymanagementinquiryValue3 = values[5]; //sql← csv: initialinventory
	                    String InventorymanagementinquiryValue4 = values[4]; //sql← csv:	locationnumber
	                    String InventorymanagementinquiryValue5 = "-"; //sql← csv:initialinventory
	                    int InventorymanagementinquiryValue6 = 0; //sql← csv:theoreticalinventory
	                    int InventorymanagementinquiryValue7 = Integer.parseInt(values[6]); //sql← csv:theoreticalinventory
	                    int InventorymanagementinquiryValue9 = 0; //sql← csv:inventorystock
	                    int InventorymanagementinquiryValue10 = 0; //sql← csv:investigationrequired
	                    int InventorymanagementinquiryValue11 = 0; //sql← csv:decision
	                    String InventorymanagementinquiryValue12 = values[0] + " " + values[1]; // 行数を一意のキーとして使用 //sql← csv:remarks
	                    String InventorymanagementinquiryValue13 = values[2];  //← csv:fileattributes

	                    Inventorymanagementinquiry inventorymanagementinquiry = new Inventorymanagementinquiry(
	                            0,
	                            InventorymanagementinquiryValue1,
	                            InventorymanagementinquiryValue2,
	                            InventorymanagementinquiryValue3,
	                            InventorymanagementinquiryValue4,
	                            InventorymanagementinquiryValue5,
	                            InventorymanagementinquiryValue6,
	                            InventorymanagementinquiryValue7,
	                            InventorymanagementinquiryValue8,
	                            InventorymanagementinquiryValue9,
	                            InventorymanagementinquiryValue10,
	                            InventorymanagementinquiryValue11,
	                            InventorymanagementinquiryValue12,
	                            InventorymanagementinquiryValue13
	                    );
	                    
	                    inventoryManagementInquiryList.add(inventorymanagementinquiry);

	                } catch (NumberFormatException e) {
	                    System.err.println("行の解析中にエラーが発生しました: " + line + ". " + e.getMessage());
	                }
	            } else if ("MoveLog".equals(values[2])) {
	                processMoveLog(values, inventoryManagementInquiryList);
	            } else if ("InventoryLog".equals(values[2])) {
	                processInventoryLog(values, inventoryManagementInquiryList);
	            } else {
	                System.out.println("Unknown log type in data: " + values[2]);
	            }
	        } catch (Exception e) {
	            System.err.println("データの解析中にエラーが発生しました: " + line + ". " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    return inventoryManagementInquiryList;
	}
	
	private void processMoveLog(String[] values, List<Inventorymanagementinquiry> inventoryManagementInquiryList) {
	    try {
	        // MoveLog 特有の処理
	        String InventorymanagementinquiryValue1 = null;
	        String InventorymanagementinquiryValue2 = null;
	        String InventorymanagementinquiryValue8 = null;

	        // 正規表現を使って分割する
	        if (values[6].matches(".*\\*.*\\*\\{.*\\}")) {
	            String[] parts = values[6].split("\\*|\\{|\\}");

	            // parts の長さが少なくとも 3 以上であることを確認
	            if (parts.length >= 3) {
	                if (parts[0] != null && parts[1] != null && parts[3] != null) {
	                	InventorymanagementinquiryValue2 = parts[0]; //sql← csv:productname_productnumber
	                	InventorymanagementinquiryValue1 = parts[3]; //sql← csv:identificationnumber

	                    // value8 の確認
	                    if (parts[1].length() == 4) {
	                    	InventorymanagementinquiryValue8 = parts[1]; //sql← csv:inventorymanagementclassification
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

	        String InventorymanagementinquiryValue3 = values[6]; //sql← csv: initialinventory
	        String InventorymanagementinquiryValue4 = values[4]; //sql← csv:	locationnumber
	        String InventorymanagementinquiryValue5 = values[5]; //sql← csv:locationnumberdestination
	        int InventorymanagementinquiryValue6 = 0; //sql← csv:initialinventory
	        int InventorymanagementinquiryValue7 = Integer.parseInt(values[7]);  //sql← csv:theoreticalinventory
	        int InventorymanagementinquiryValue9 = 0; //sql← csv:inventorystock
	        int InventorymanagementinquiryValue10 = 0; //sql← csv:investigationrequired
	        int InventorymanagementinquiryValue11 = 0; //sql← csv:decision
	        String InventorymanagementinquiryValue12 = values[0] + " " + values[1]; // 行数を一意のキーとして使用 //sql← csv:remarks
	        String InventorymanagementinquiryValue13 = values[2];  //← csv:fileattributes
	        

	        Inventorymanagementinquiry inventorymanagementinquiry = new Inventorymanagementinquiry(
	            0,
	            InventorymanagementinquiryValue1,
	            InventorymanagementinquiryValue2,
	            InventorymanagementinquiryValue3,
	            InventorymanagementinquiryValue4,
	            InventorymanagementinquiryValue5,
	            InventorymanagementinquiryValue6,
	            InventorymanagementinquiryValue7,
	            InventorymanagementinquiryValue8,
	            InventorymanagementinquiryValue9,
	            InventorymanagementinquiryValue10,
	            InventorymanagementinquiryValue11,
	            InventorymanagementinquiryValue12,
	            InventorymanagementinquiryValue13
	        );

	        // ここで MoveLog 特有の処理を実行
	        inventoryManagementInquiryList.add(inventorymanagementinquiry);

        } catch (NumberFormatException e) {
            System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        }
	 }
	
	private void processInventoryLog(String[] values, List<Inventorymanagementinquiry> inventoryManagementInquiryList) {
	    try {
	        // MoveLog 特有の処理
	        String InventorymanagementinquiryValue1 = null;
	        String InventorymanagementinquiryValue2 = null;
	        String InventorymanagementinquiryValue8 = null;

	     // 正規表現を使って分割する
            if (values[5].matches(".*\\*.*\\*\\{.*\\}")) {
                String[] parts = values[5].split("\\*|\\{|\\}");

                // parts の長さが少なくとも 3 以上であることを確認
                if (parts.length >= 3) {
                    if (parts[0] != null && parts[1] != null && parts[3] != null) {
                        InventorymanagementinquiryValue2 = parts[0]; //sql← csv:productname_productnumber
                        InventorymanagementinquiryValue1 = parts[3]; //sql← csv:identificationnumber

	                    // value8 の確認
	                    if (parts[1].length() == 4) {
	                    	InventorymanagementinquiryValue8 = parts[1]; //sql← csv:inventorymanagementclassification
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

	        String InventorymanagementinquiryValue3 = values[5]; //sql← csv: initialinventory
            String InventorymanagementinquiryValue4 = values[4]; //sql← csv:	locationnumber
            String InventorymanagementinquiryValue5 = "-"; //sql← csv:locationnumberdestination
            int InventorymanagementinquiryValue6 = 0; //sql← csv:initialinventory
            int InventorymanagementinquiryValue7 = 0; //sql← csv:theoreticalinventory
            int InventorymanagementinquiryValue9 = Integer.parseInt(values[6]); //sql← csv:inventorystock
            int InventorymanagementinquiryValue10 = 0; //sql← csv:investigationrequired
            int InventorymanagementinquiryValue11 = Integer.parseInt(values[6]); //sql← csv:decision
            String InventorymanagementinquiryValue12 = values[0] + " " + values[1]; // 行数を一意のキーとして使用 //sql← csv:remarks
            String InventorymanagementinquiryValue13 = values[2];  //← csv:fileattributes	        

	        Inventorymanagementinquiry inventorymanagementinquiry = new Inventorymanagementinquiry(
	            0,
	            InventorymanagementinquiryValue1,
	            InventorymanagementinquiryValue2,
	            InventorymanagementinquiryValue3,
	            InventorymanagementinquiryValue4,
	            InventorymanagementinquiryValue5,
	            InventorymanagementinquiryValue6,
	            InventorymanagementinquiryValue7,
	            InventorymanagementinquiryValue8,
	            InventorymanagementinquiryValue9,
	            InventorymanagementinquiryValue10,
	            InventorymanagementinquiryValue11,
	            InventorymanagementinquiryValue12,
	            InventorymanagementinquiryValue13
	        );

	        // ここで MoveLog 特有の処理を実行
	        inventoryManagementInquiryList.add(inventorymanagementinquiry);

        } catch (NumberFormatException e) {
            System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        }
	}

	public void save(List<Inventorymanagementinquiry> inventorymanagementinquiries,List<InventoryLog> inventorylogList,  List<String> allLines) throws NamingException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        // データベース接続の取得
	        this.getConnection();
	        
	        // 日付フォーマットの定義
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        
	        // InventoryLog リストを Map に変換
	        Map<String, InventoryLog> inquiryMap = new HashMap<>();
	        for (InventoryLog inquiry : inventorylogList) {
	            inquiryMap.put(inquiry.getRemarks(), inquiry);
	        }
	        
	        // 日付と時間でソート
	        Collections.sort(inventorymanagementinquiries, new Comparator<Inventorymanagementinquiry>() {
	            @Override
	            public int compare(Inventorymanagementinquiry o1, Inventorymanagementinquiry o2) {
	                try {
	                    Date date1 = sdf.parse(o1.getRemarks());
	                    Date date2 = sdf.parse(o2.getRemarks());
	                    return date1.compareTo(date2);
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                    return 0; // 例外が発生した場合は0を返してソートを中断しない
	                }
	            }
	        });

	        // トランザクション開始
	        db.setAutoCommit(false);
	        
	        for (Inventorymanagementinquiry inventorymanagementinquiry : inventorymanagementinquiries) {
	            boolean validFiles = true;

	            InventoryLog matchingInquiry = inquiryMap.get(inventorymanagementinquiry.getRemarks());

	            if (validFiles) {
	            	
	            	 //インポート時の2重登録を防ぐ
	                boolean existingRecord1 = existsInDatabase1(inventorymanagementinquiry.getRemarks());
	                if (existingRecord1) {
	                    // 既存のデータに対してエラーステータスを設定
	                    processRepetition(db, ps, matchingInquiry);
	                    continue;
	                }
	                
	                //インポート時 identificationnumber(識別番号)&locationnumber(ロケ番号)がかぶっているか確認
	                boolean existingRecord2 = existsInDatabase2(inventorymanagementinquiry.getIdentificationnumber(), inventorymanagementinquiry.getLocationnumber());

	                // 各ファイルに対して処理を行う
	                if ("StorageLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // StorageLog の場合は足し算
	                    processStorageLog(db, ps, inventorymanagementinquiry, matchingInquiry, existingRecord2);
	                } else if ("RetrievalLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // RetrievalLog の場合は引き算
	                    processRetrievalLog(db, ps, inventorymanagementinquiry, matchingInquiry, existingRecord2);
	                } else if ("MoveLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // MoveLog の場合は移動
	                    processMoveLog(db, ps, inventorymanagementinquiry,matchingInquiry, existingRecord2);
	                } else if ("InventoryLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // InventoryLog の場合は棚卸
	                    processInventoryLog(db, ps, inventorymanagementinquiry,matchingInquiry, existingRecord2);
	                } else {
	                	System.out.println("ファイル名に'RetrievalLog'または'StorageLog'が含まれていないファイルがあります。処理を中止します。");
	                }
	            }
	        }
	        // トランザクションをコミット
	        db.commit();
	        
	    } catch (SQLException e) {
	        // ロールバック
	        try {
	            if (db != null && !db.getAutoCommit()) {
	                db.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        // ロギングフレームワークを使用することを検討
	        e.printStackTrace();
	    } finally {
	        // リソースを閉じることを確認
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (db != null) {
	            try {
	                if (!db.getAutoCommit()) {
	                    db.setAutoCommit(true); // 自動コミットを復元
	                }
	                db.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	private void processRepetition(Connection db, PreparedStatement ps, InventoryLog inventoryLog) throws SQLException {
	    // inventory_status が "-" の場合にエラーステータスを設定
	    if ("-".equals(inventoryLog.getInventory_status())) {
	        // エラーステータスを設定
	        inventoryLog.setStatus("Error: 同じデータが読み込まれています");

	        // データベースにステータスを反映
	        String updateQuery = "UPDATE logs SET status = ? WHERE remarks = ? AND inventory_status = ?";
	        ps = db.prepareStatement(updateQuery);
	        ps.setString(1, inventoryLog.getStatus()); // 新しいステータス
	        ps.setString(2, inventoryLog.getRemarks()); // 対象の remarks
	        ps.setString(3, inventoryLog.getInventory_status()); // inventory_status が "-" のレコードのみ更新

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Updated " + rowsAffected + " rows with error status.");
	        }
	    }
	}

	private void processStorageLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog, boolean existingRecord) throws SQLException {
		try {
		    // ------------------------------------Inventorymanagementinquiry処理------------------------------------
		    if (existingRecord) {
		        // 既存のInventorymanagementinquiryレコードを更新
		        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory + ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory()); // 加算するtheoreticalinventoryをセット
		        ps.setString(2, inventorymanagementinquiry.getRemarks()); // remarksをセット
		        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        
		        ps.executeUpdate(); // データベースを更新
		    } else {
		        // 新規Inventorymanagementinquiryレコードの挿入
		        ps = db.prepareStatement("INSERT INTO inventorys(identificationnumber, productname_productnumber, qrcodeinformation, "
		                + "locationnumber, locationnumberdestination, initialinventory, theoreticalinventory, inventorymanagementclassification, "
		                + "inventorystock, investigationrequired, decision, remarks, fileattributes) "
		                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		        // 各フィールドに対応する値を設定
		        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber()); // productname_productnumberをセット
		        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation()); // qrcodeinformationをセット
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination()); // locationnumberdestinationをセット
		        ps.setInt(6, inventorymanagementinquiry.getInitialinventory()); // initialinventoryをセット
		        ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory()); // theoreticalinventoryをセット
		        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification()); // inventorymanagementclassificationをセット
		        ps.setInt(9, inventorymanagementinquiry.getInventorystock()); // inventorystockをセット
		        ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired()); // investigationrequiredをセット
		        ps.setInt(11, inventorymanagementinquiry.getDecision()); // decisionをセット
		        ps.setString(12, inventorymanagementinquiry.getRemarks()); // remarksをセット
		        ps.setString(13, inventorymanagementinquiry.getFileattributes()); // fileattributesをセット

		        ps.executeUpdate(); // 新規レコードをデータベースに挿入
		    }
		    // ------------------------------------Inventorymanagementinquiry処理終了--------------------------------

		    // -------------------------------------------inventoryLog処理-------------------------------------------
		    // inventorysテーブルからtheoreticalinventoryを取得 (登録No. & ロケ番)
		    ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
		    ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		    ps.setString(2, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		    ResultSet rs = ps.executeQuery(); // クエリ実行してデータベースから結果を取得

		    int theoreticalInventory = 0; // 登録No. & ロケ番に対応するtheoreticalInventory
		    boolean updatedStatus = true; // ステータスの更新が必要かどうかのフラグ

		    if (rs.next()) {
		        // レコードが存在すればtheoreticalInventoryを取得し、inventoryLogのステータスを更新
		        theoreticalInventory = rs.getInt("theoreticalInventory");
		        inventoryLog.setInventory_status(String.valueOf(theoreticalInventory)); // inventory_statusにtheoreticalInventoryをセット
		    }
		    rs.close(); // 結果セットをクローズ

		    // inventory_statusとimportquantityが一致するかを確認
		    if (inventoryLog.getInventory_status().equals(String.valueOf(inventoryLog.getImportquantity()))) {
		        updatedStatus = false; // 一致する場合、ステータスの更新は不要
		    }

		    // ステータスを設定 (加算または新規作成)
		    inventoryLog.setStatus(updatedStatus ? "+加算" : "新規作成");
		    
		    // logsテーブルのInventory_statusとStatusを更新
		    ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
		    ps.setString(1, inventoryLog.getInventory_status()); // inventory_statusをセット
		    ps.setString(2, inventoryLog.getStatus()); // statusをセット
		    ps.setString(3, inventoryLog.getRemarks()); // remarksをセット
		    
		    ps.executeUpdate(); // データベースのレコードを更新
		    // -------------------------------------------inventoryLog処理終了-------------------------------------------

		} finally {
		    // PreparedStatementがnullでない場合にクローズ処理を実行
		    if (ps != null) ps.close();
		}
	}

	private void processRetrievalLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog,  boolean existingRecord) throws SQLException {
		try {
		    // ------------------------------------Inventorymanagementinquiry処理------------------------------------
		    if (existingRecord) {
		        // 既存のレコードの theoreticalinventory と remarks を更新する
		        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory - ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory()); // 減算する値をセット
		        ps.setString(2, inventorymanagementinquiry.getRemarks()); // remarksをセット
		        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        
		        ps.executeUpdate(); // データベースを更新
		        // ------------------------------------Inventorymanagementinquiry処理終了------------------------------------

		        // -------------------------------------------inventoryLog処理-------------------------------------------
		        // inventorysテーブルからtheoreticalinventoryを取得 (登録No. & ロケ番)
		        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		        ps.setString(2, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        ResultSet rs = ps.executeQuery(); // データベースから結果を取得
		        
		        int theoreticalInventory = 0; // theoreticalinventoryの初期値
		        boolean updatedStatus = false; // ステータスが更新されたかどうかのフラグ
		    
		        if (rs.next()) {
		            // レコードが存在する場合、theoreticalinventoryを取得し、ステータスを更新
		            theoreticalInventory = rs.getInt("theoreticalinventory");  
		            inventoryLog.setInventory_status(String.valueOf(theoreticalInventory)); // Inventory_statusを設定
		            updatedStatus = true; // ステータスが更新されたことをマーク
		        }
		        rs.close(); // 結果セットをクローズ
		        
		        // ステータスが更新された場合は減算ステータスを設定、それ以外の場合はエラーメッセージを設定
		        inventoryLog.setStatus(updatedStatus ? "-減算" : "Error:減算先データがありません。");
		        
		        // Inventory_status と status の更新をデータベースに反映
		        ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
		        ps.setString(1, inventoryLog.getInventory_status()); // Inventory_statusをセット
		        ps.setString(2, inventoryLog.getStatus()); // Statusをセット
		        ps.setString(3, inventoryLog.getRemarks()); // Remarksをセット
		        
		        ps.executeUpdate(); // logsテーブルを更新
		        // -------------------------------------------inventoryLog処理終了-------------------------------------------
		    } else {
		        // inventory_status が "-" の場合にエラーステータスを設定
		        if ("-".equals(inventoryLog.getInventory_status())) {
		            // エラーステータスを設定
		            inventoryLog.setStatus("Error:減算先データがありません。");

		            // データベースにエラーステータスを反映
		            String updateQuery = "UPDATE logs SET status = ? WHERE remarks = ? AND inventory_status = ?";
		            ps = db.prepareStatement(updateQuery);
		            ps.setString(1, inventoryLog.getStatus()); // エラーステータスをセット
		            ps.setString(2, inventoryLog.getRemarks()); // 対象のremarksをセット
		            ps.setString(3, inventoryLog.getInventory_status()); // inventory_status が "-" のレコードのみ更新

		            int rowsAffected = ps.executeUpdate(); // データベースのレコードを更新
		            if (rowsAffected > 0) {
		                // 更新された行数を出力
		                System.out.println("Updated " + rowsAffected + " rows with error status.");
		            } else {
		                // 更新されなかった場合のメッセージを出力
		                System.out.println("No rows updated. Ensure the record exists and inventory_status is '-'.");
		            }
		        }
		    }

		} finally {
		    // PreparedStatement が null でない場合はクローズ処理を行う
		    if (ps != null) {
		        try {
		            ps.close(); // PreparedStatementをクローズ
		        } catch (SQLException e) {
		            e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
		        }
		    }
		}
	}
	
	private void processMoveLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog, boolean existingRecord) throws SQLException {
	    ResultSet rs = null;
	    ResultSet rs2 = null;
	    
	    try {
	        // ------------------------------------Inventorymanagementinquiry処理------------------------------------
	    	if (existingRecord) {
	        // 1. identificationnumber と locationnumber が一致するレコードの theoreticalinventory を引く
	        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory - ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
	        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory()); // 差し引く理論在庫数をセット
	        ps.setString(2, inventorymanagementinquiry.getRemarks()); // 備考をセット
	        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber()); // 認識番号をセット
	        ps.setString(4, inventorymanagementinquiry.getLocationnumber()); // 位置番号をセット
	        
	        ps.executeUpdate(); // 理論在庫数を減算

	        // 2. identificationnumber と locationnumberdestination が一致するレコードの theoreticalinventory を足す
	        boolean destinationExists = existsInDatabase2(inventorymanagementinquiry.getIdentificationnumber(), inventorymanagementinquiry.getLocationnumberdestination());
	        if (destinationExists) {
	            // 移動先が存在する場合、そのレコードの theoreticalinventory を更新
	            ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory + ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
	            ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory()); // 加算する理論在庫数をセット
	            ps.setString(2, inventorymanagementinquiry.getRemarks()); // 備考をセット
	            ps.setString(3, inventorymanagementinquiry.getIdentificationnumber()); // 認識番号をセット
	            ps.setString(4, inventorymanagementinquiry.getLocationnumberdestination()); // 移動先の位置番号をセット
	            
	            ps.executeUpdate(); // 理論在庫数を加算
	        } else {
	            // 3. 移動先が存在しない場合、新規レコードを挿入
	            ps = db.prepareStatement("INSERT INTO inventorys (identificationnumber, productname_productnumber, qrcodeinformation, locationnumber, locationnumberdestination, initialinventory, theoreticalinventory, inventorymanagementclassification, inventorystock, investigationrequired, decision, remarks, fileattributes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // 認識番号をセット
	            ps.setString(2, inventorymanagementinquiry.getProductname_productnumber()); // 商品名・商品番号をセット
	            ps.setString(3, inventorymanagementinquiry.getQrcodeinformation()); // QRコード情報をセット
	            ps.setString(4, inventorymanagementinquiry.getLocationnumberdestination()); // 新しい位置番号をセット
	            ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination()); // 移動先位置番号をセット
	            ps.setInt(6, inventorymanagementinquiry.getInitialinventory()); // 初期在庫数をセット
	            ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory()); // 理論在庫数をセット
	            ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification()); // 在庫管理区分をセット
	            ps.setInt(9, inventorymanagementinquiry.getInventorystock()); // 実際の在庫数をセット
	            ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired()); // 調査が必要かどうかをセット
	            ps.setInt(11, inventorymanagementinquiry.getDecision()); // 判断結果をセット
	            ps.setString(12, inventorymanagementinquiry.getRemarks()); // 備考をセット
	            ps.setString(13, inventorymanagementinquiry.getFileattributes()); // ファイル属性をセット
	            
	            ps.executeUpdate(); // 新規レコードを挿入
	        }
	        // ------------------------------------Inventorymanagementinquiry処理終了------------------------------------

	        // -------------------------------------------inventoryLog処理-------------------------------------------
	        // inventorysテーブルから theoreticalinventory を取得 (登録No. & ロケ番)
	        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // 認識番号をセット
	        ps.setString(2, inventorymanagementinquiry.getLocationnumber()); // ロケ番をセット
	        
	        rs = ps.executeQuery(); // クエリを実行

	        // inventorysテーブルから theoreticalinventory を取得 (登録No. & ロケ番_移動先)
	        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumberdestination = ?");
	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // 認識番号をセット
	        ps.setString(2, inventorymanagementinquiry.getLocationnumberdestination()); // 移動先のロケ番をセット
	        
	        rs2 = ps.executeQuery(); // クエリを実行

	        int theoreticalInventory = 0; // 移動元の理論在庫
	        int theoreticalInventory2 = 0; // 移動先の理論在庫
	        boolean updatedStatus = false; // 移動元の更新状態
	        boolean updatedStatus2 = false; // 移動先の更新状態

	        // 移動元の theoreticalinventory を設定
	        if (rs.next()) {
	            theoreticalInventory = rs.getInt("theoreticalinventory"); // 理論在庫を取得
	            inventoryLog.setQuantity(theoreticalInventory); // 移動元の在庫数を設定
	            updatedStatus = true;
	        }
	        
	        // 移動先の theoreticalinventory を設定
	        if (rs2.next()) {
	            theoreticalInventory2 = rs2.getInt("theoreticalinventory"); // 理論在庫を取得
	            inventoryLog.setInventory(theoreticalInventory2); // 移動先の在庫数を設定
	            updatedStatus2 = true;
	        }
	        
	        // 移動の状態に応じてステータスを設定
	        if (updatedStatus && updatedStatus2) {
	            // 状態が両方更新されている場合
	            inventoryLog.setStatus("移動");
	            inventoryLog.setInventory_status("[移動元]:");
	            inventoryLog.setStatus_m1("-減算");
	            inventoryLog.setStatus2("[移動先]:");
	            inventoryLog.setStatus_m2("+加算");
	            
	            // SQL 文とパラメータの設定
	            String sql = "UPDATE logs SET status = ?, inventory_status = ?, status_m1 = ?, status2 = ?, status_m2 = ?, "
	            		+ "quantity = ?, inventory = ?  WHERE remarks = ?";
	            ps = db.prepareStatement(sql);
	            ps.setString(1, inventoryLog.getStatus());
	            ps.setString(2, inventoryLog.getInventory_status());
	            ps.setString(3, inventoryLog.getStatus_m1());
	            ps.setString(4, inventoryLog.getStatus2());
	            ps.setString(5, inventoryLog.getStatus_m2());
	            ps.setInt(6, inventoryLog.getQuantity());
	            ps.setInt(7, inventoryLog.getInventory());
	            ps.setString(8, inventoryLog.getRemarks()); // WHERE 節の条件
	            
	            ps.executeUpdate(); // 新規レコードを挿入
	            
	        } else if (updatedStatus && !updatedStatus2) {
	        	// 加算先のデータが存在しない場合新規作成
	        	inventoryLog.setStatus("移動");
	            inventoryLog.setInventory_status("[移動元]:");
	            inventoryLog.setStatus_m1("-減算");
	            inventoryLog.setStatus2("[移動先]:");
	            inventoryLog.setStatus_m2("新規作成");
	            
	            int importquantity = inventoryLog.getImportquantity(); 
	            inventoryLog.setInventory(importquantity);
	        	            
	            // SQL 文とパラメータの設定
	            String sql = "UPDATE logs SET status = ?, inventory_status = ?, status_m1 = ?, status2 = ?, status_m2 = ?, "
	            		+ "quantity = ?, inventory = ?  WHERE remarks = ?";
	            ps = db.prepareStatement(sql);
	            ps.setString(1, inventoryLog.getStatus());
	            ps.setString(2, inventoryLog.getInventory_status());
	            ps.setString(3, inventoryLog.getStatus_m1());
	            ps.setString(4, inventoryLog.getStatus2());
	            ps.setString(5, inventoryLog.getStatus_m2());
	            ps.setInt(6, inventoryLog.getQuantity());
	            ps.setInt(7, inventoryLog.getInventory());
	            ps.setString(8, inventoryLog.getRemarks()); // WHERE 節の条件
	            
	            ps.executeUpdate(); // 新規レコードを挿入

	        } else if (!updatedStatus && updatedStatus2) {
	        	// 減算先のデータが存在しない場合
	        	inventoryLog.setStatus("Error:減算先データがありません。");
	        	inventoryLog.setImportquantity(0);
	        	inventoryLog.setInventory_status("0");
	        	inventoryLog.setLocationnumber_m1("-");
	        	inventoryLog.setLocationnumber_m2("-");
	        	inventoryLog.setRemarks("-");
	        	            
	        	// SQL 文とパラメータの設定
	        	String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	        	    + "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";
	        	ps = db.prepareStatement(sql);
	        	ps.setString(1, inventoryLog.getStatus());
	        	ps.setInt(2, inventoryLog.getImportquantity());
	        	ps.setString(3, inventoryLog.getInventory_status());
	        	ps.setString(4, inventoryLog.getLocationnumber_m1());
	        	ps.setString(5, inventoryLog.getLocationnumber_m2());
	        	ps.setString(6, inventoryLog.getRemarks()); // 備考のパラメータ
	        	ps.setInt(7, inventoryLog.getQuantity()); // 在庫数のパラメータ
	        	ps.setInt(8, inventoryLog.getInventory()); // 在庫数のパラメータ
	        	ps.setString(9, inventoryLog.getRemarks()); // WHERE 節の条件
	            
	            ps.executeUpdate(); // 新規レコードを挿入

	        } else {
	            // 処理が失敗した場合
	            inventoryLog.setStatus("Error:データ処理が出来ませんでした。");
	            inventoryLog.setImportquantity(0);
	            inventoryLog.setInventory_status("0");
	            inventoryLog.setLocationnumber_m1("-");
	            inventoryLog.setLocationnumber_m2("-");
	            inventoryLog.setRemarks("-");
	            
	            // SQL 文とパラメータの設定
	            String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	            		+ "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";
	            ps = db.prepareStatement(sql);
	            ps.setString(1, inventoryLog.getStatus());
	            ps.setInt(2, inventoryLog.getImportquantity());
	            ps.setString(3, inventoryLog.getInventory_status());
	            ps.setString(4, inventoryLog.getLocationnumber_m1());
	            ps.setString(5, inventoryLog.getLocationnumber_m2());
	            ps.setInt(6, inventoryLog.getQuantity());
	            ps.setInt(7, inventoryLog.getInventory());
	            ps.setString(8, inventoryLog.getRemarks()); // WHERE 節の条件
	            
	            ps.executeUpdate(); // 新規レコードを挿入
	        }

		    // status_m2とinventoryが一致するかを確認
		    if (String.valueOf(inventoryLog.getInventory()).equals(String.valueOf(inventoryLog.getImportquantity()))) {
		    	 inventoryLog.setStatus_m2("新規作成");
		    }
		    
		    // logsテーブルのstatus_m2を更新
		    ps = db.prepareStatement("UPDATE logs SET status_m2 = ? WHERE remarks = ?");
		    ps.setString(1, inventoryLog.getStatus_m2()); // statusをセット
		    ps.setString(2, inventoryLog.getRemarks()); // remarksをセット
		    
		    ps.executeUpdate(); // データベースのレコードを更新

	    	 }
	    	
	    	if (!existingRecord) {
	    	    // 加算先のデータが存在しない場合の処理
	    	    inventoryLog.setStatus("Error:減算先データがありません。"); // エラーメッセージを設定
	    	    inventoryLog.setImportquantity(0); // 加算数を 0 に設定
	    	    inventoryLog.setInventory_status("0"); // 在庫状態を 0 に設定
	    	    inventoryLog.setLocationnumber_m1("-"); // 元の位置番号を "-" に設定
	    	    inventoryLog.setLocationnumber_m2("-"); // 移動先位置番号を "-" に設定

	    	    // もし元の remarks が存在する場合、それを WHERE 節に使う
	    	    String originalRemarks = inventoryLog.getRemarks(); // 元の備考を保存
	    	    inventoryLog.setRemarks("-"); // 備考を "-" に設定

	    	    // SQL 文とパラメータの設定
	    	    String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	    	        + "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";

	    	    // PreparedStatement を作成
	    	    ps = db.prepareStatement(sql);
	    	    ps.setString(1, inventoryLog.getStatus()); // ステータスをセット
	    	    ps.setInt(2, inventoryLog.getImportquantity()); // 加算数をセット
	    	    ps.setString(3, inventoryLog.getInventory_status()); // 在庫状態をセット
	    	    ps.setString(4, inventoryLog.getLocationnumber_m1()); // 元の位置番号をセット
	    	    ps.setString(5, inventoryLog.getLocationnumber_m2()); // 移動先位置番号をセット
	    	    ps.setString(6, inventoryLog.getRemarks()); // 更新後の備考をセット
	    	    ps.setInt(7, inventoryLog.getQuantity()); // 在庫数をセット
	    	    ps.setInt(8, inventoryLog.getInventory()); // 在庫数をセット
	    	    ps.setString(9, originalRemarks); // WHERE 節で元の備考を使用

	    	    // UPDATE 文の実行
	    	    ps.executeUpdate();
	    	    
	    	    System.out.println("33333existingRecord*"); // 処理完了のログ出力
	    	}
	    	
		    // -------------------------------------------inventoryLog処理終了-------------------------------------------
	    	 
	    } finally {
	        // PreparedStatement が null でない場合はクローズ処理を行う
	        if (ps != null) {
	            try {
	                ps.close(); // PreparedStatementをクローズ
	            } catch (SQLException e) {
	                e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
	            }
	        }
	        // ResultSet が null でない場合はクローズ処理を行う
	        if (rs != null) {
	            try {
	                rs.close(); // ResultSetをクローズ
	            } catch (SQLException e) {
	                e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
	            }
	        }
	        if (rs2 != null) {
	            try {
	                rs2.close(); // ResultSetをクローズ
	            } catch (SQLException e) {
	                e.printStackTrace(); // エラーが発生した場合はスタックトレースを出力
	            }
	        }
	    }
	}
	
	private void processInventoryLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog,  boolean existingRecord) throws SQLException {
		try {
		    // ------------------------------------Inventorymanagementinquiry処理------------------------------------
			if (existingRecord) {
				
			    // 既存のInventorymanagementinquiryレコードを更新
			    ps = db.prepareStatement("UPDATE inventorys SET inventorystock = ?,  decision = ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
			    ps.setInt(1, inventorymanagementinquiry.getInventorystock()); // inventorystockをセット
			    ps.setInt(2, inventorymanagementinquiry.getDecision()); // decisionをセット
			    ps.setString(3, inventorymanagementinquiry.getRemarks()); // remarksをセット
			    ps.setString(4, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
			    ps.setString(5, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        
		        ps.executeUpdate(); // データベースを更新
		        
		        // inventorysテーブルからtheoreticalinventoryを取得 (登録No. & ロケ番)
			    ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
			    ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
			    ps.setString(2, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
			    ResultSet rs = ps.executeQuery(); // クエリ実行してデータベースから結果を取得

			    int theoreticalInventory = 0; // 登録No. & ロケ番に対応するtheoreticalInventory

			    if (rs.next()) {
			        // レコードが存在すればtheoreticalInventoryを取得し、inventoryLogのステータスを更新
			        theoreticalInventory = rs.getInt("theoreticalInventory");
			        // investigationrequiredにセットする差分を計算しInventorymanagementinquiryレコードを更新
			        inventorymanagementinquiry.setInvestigationrequired( theoreticalInventory - inventorymanagementinquiry.getInventorystock()); 
			    }
			    rs.close(); // 結果セットをクローズ

			    ps = db.prepareStatement("UPDATE inventorys SET investigationrequired = ? WHERE identificationnumber = ? AND locationnumber = ?");

			    ps.setInt(1, inventorymanagementinquiry.getInvestigationrequired()); // investigationrequiredをセット
			    ps.setString(2, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
			    ps.setString(3, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
			    

		        ps.executeUpdate(); // データベースを更新
		    } else {
		        // 新規Inventorymanagementinquiryレコードの挿入
		        ps = db.prepareStatement("INSERT INTO inventorys(identificationnumber, productname_productnumber, qrcodeinformation, "
		                + "locationnumber, locationnumberdestination, initialinventory, theoreticalinventory, inventorymanagementclassification, "
		                + "inventorystock, investigationrequired, decision, remarks, fileattributes) "
		                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		        // 各フィールドに対応する値を設定
		        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber()); // productname_productnumberをセット
		        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation()); // qrcodeinformationをセット
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		        ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination()); // locationnumberdestinationをセット
		        ps.setInt(6, inventorymanagementinquiry.getInitialinventory()); // initialinventoryをセット
		        ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory()); // theoreticalinventoryをセット
		        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification()); // inventorymanagementclassificationをセット
		        ps.setInt(9, inventorymanagementinquiry.getInventorystock()); // inventorystockをセット
		        ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired()); // investigationrequiredをセット
		        ps.setInt(11, inventorymanagementinquiry.getDecision()); // decisionをセット
		        ps.setString(12, inventorymanagementinquiry.getRemarks()); // remarksをセット
		        ps.setString(13, inventorymanagementinquiry.getFileattributes()); // fileattributesをセット

		        ps.executeUpdate(); // 新規レコードをデータベースに挿入
		    }
		    // ------------------------------------Inventorymanagementinquiry処理終了--------------------------------

		    // -------------------------------------------inventoryLog処理-------------------------------------------
		    // inventorysテーブルからtheoreticalinventoryを取得 (登録No. & ロケ番)
		    ps = db.prepareStatement("SELECT inventorystock FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
		    ps.setString(1, inventorymanagementinquiry.getIdentificationnumber()); // identificationnumberをセット
		    ps.setString(2, inventorymanagementinquiry.getLocationnumber()); // locationnumberをセット
		    ResultSet rs = ps.executeQuery(); // クエリ実行してデータベースから結果を取得

		    int inventorystock = 0; // 登録No. & ロケ番に対応するtheoreticalInventory
		    boolean updatedStatus = true; // ステータスの更新が必要かどうかのフラグ

		    if (rs.next()) {
		        // レコードが存在すればinventorystockを取得し、inventoryLogのステータスを更新
		    	inventorystock = rs.getInt("inventorystock");
		        inventoryLog.setInventory_status(String.valueOf(inventorystock)); // inventory_statusにtheoreticalInventoryをセット
		    }
		    rs.close(); // 結果セットをクローズ

		    // ステータスを設定 (棚卸)
		    inventoryLog.setStatus("棚卸");
		    
		    // logsテーブルのInventory_statusとStatusを更新
		    ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
		    ps.setString(1, inventoryLog.getInventory_status()); // inventory_statusをセット
		    ps.setString(2, inventoryLog.getStatus()); // statusをセット
		    ps.setString(3, inventoryLog.getRemarks()); // remarksをセット
		    
		    ps.executeUpdate(); // データベースのレコードを更新
		    // -------------------------------------------inventoryLog処理終了-------------------------------------------

		} finally {
		    // PreparedStatementがnullでない場合にクローズ処理を実行
		    if (ps != null) ps.close();
		}
	}
	
    //インポート時の2重登録を防ぐ
	private boolean existsInDatabase1(String remarks) throws SQLException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE remarks = ?"); // Remarksフィールドに一意のキーを設定
	        ps.setString(1, remarks);
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
    //インポート時 identificationnumber(識別番号)&locationnumber(ロケ番号)がかぶっているか確認
	private boolean existsInDatabase2(String identificationnumber, String locationnumber) throws SQLException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
	        ps.setString(1, identificationnumber);
	        ps.setString(2, locationnumber);
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
	        ps = db.prepareStatement("SELECT * FROM inventorys");
	        rs = ps.executeQuery();
	        
	        // 現在の日時を取得
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateAndTime = sdf.format(new Date());

            // CSV のファイル名
            String fileName = "inventorymanagementinquirylist_DL " + currentDateAndTime + ".csv";

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
	        csvWriter.append("登録No.,W品名コード(Asplus)/W品名(Asplus),QRCode情報（W品名コード+W品名(品名仕様)）,"
	        		+ "ロケ番,ロケ番_移動先,期初在庫,理論在庫,在庫管理区分,棚卸在庫(実績),要調査（誤差）,確定,備考");
	        csvWriter.append("\n");

	        // データ行の書き出し
	        while (rs.next()) {
	            csvWriter.append(rs.getString("identificationnumber") + ",");
	            csvWriter.append(rs.getString("productname_productnumber") + ",");
	            csvWriter.append(rs.getString("qrcodeinformation") + ",");
	            csvWriter.append(rs.getString("locationnumber") + ",");
	            csvWriter.append(rs.getString("locationnumberdestination") + ",");
	            csvWriter.append(rs.getString("initialinventory") + ",");
	            csvWriter.append(rs.getString("theoreticalinventory") + ",");
	            csvWriter.append(rs.getString("inventorymanagementclassification") + ",");
	            csvWriter.append(rs.getString("inventorystock") + ",");
	            csvWriter.append(rs.getString("investigationrequired") + ",");
	            csvWriter.append(rs.getString("decision") + ",");
	            csvWriter.append(rs.getString("remarks"));
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
	
	public String InventoryProcessingAll() throws NamingException {
	    String message = ""; // メッセージ変数を初期化
	   
	    try {
	        // データベース接続を取得
	        this.getConnection();
	        
	        // decision = 0 のレコードをカウントするクエリ
	        ps = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE decision = 0");
	        ResultSet rs = ps.executeQuery();
	        
	        int count = 0;
	        
	        if (rs.next()) {
	            count = rs.getInt(1); // カウント結果を取得
	        }
	        
	        // カウント結果をログに出力
	        System.out.println("カウント結果: " + count);
	        
	        // レコードが1件以上存在する場合は処理を破棄してメッセージを設定
	        if (count > 0) {
	            return "棚卸処理は中止されました。確定項目が未処理のレコードが存在します。";
	        }
	        
	        // decision の値を initialinventory に設定するクエリ
	        ps = db.prepareStatement("UPDATE inventorys SET initialinventory = decision WHERE decision <> 0");
	        ps.executeUpdate(); // データベースを更新
	        
	        // theoreticalinventory を initialinventory に設定し、他のフィールドも更新するクエリ
	        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = initialinventory, "
	                + "inventorystock = 0, "
	                + "investigationrequired = 0, "
	                + "decision = 0 WHERE decision <> 0");
	        ps.executeUpdate(); // データベースを更新
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); // 例外処理
	        return "エラーが発生しました。";
	        
	    } finally {
	        try {
	            if (ps != null) ps.close(); // PreparedStatement をクローズ
	            if (db != null) db.close(); // データベース接続をクローズ
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return message; // 処理が正常に終了したことを示すメッセージを返す
	}
 
}