package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.Masterinventory;

/**
 * Servlet implementation class InventoryDAO
 */
@WebServlet("/MasterinventoryDAO")
public class MasterinventoryDAO {
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
	public List<Masterinventory> findAll(){
		List<Masterinventory> masterinventoryList=new ArrayList<>();
		try {
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM masterinventorys ORDER BY id DESC");
			rs=ps.executeQuery();
			while(rs.next()){
				int id=rs.getInt("id");
				String identifier=rs.getString("identifier");
				String identificationnumber=rs.getString("identificationnumber");
				String vendor=rs.getString("vendor");
				String productname_asplus=rs.getString("productname_asplus");
				String productnumber_asplus=rs.getString("productnumber_asplus");
				String productname_correction=rs.getString("productname_correction");
				String productnumber_correction=rs.getString("productnumber_correction");
				String qrcodeinformation=rs.getString("qrcodeinformation");
				String storagelocation=rs.getString("storagelocation");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				float rate=rs.getFloat("rate");

				Masterinventory masterinventory=new Masterinventory(id,identifier,identificationnumber,vendor,productname_asplus,
						productnumber_asplus,productname_correction,productnumber_correction,qrcodeinformation,
						storagelocation,inventorymanagementclassification,rate);
				
				masterinventoryList.add(masterinventory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return masterinventoryList;
	}
	public boolean insertOne(Masterinventory masterinventory){
	    try {
	        this.getConnection();

	        // 挿入前の重複チェック
	        ps = db.prepareStatement("SELECT COUNT(*) FROM masterinventorys WHERE identifier = ? AND identificationnumber = ? "
	                + "AND vendor = ? AND productname_asplus = ? AND productnumber_asplus = ? "
	                + "AND productname_correction = ? AND productnumber_correction = ? "
	                + "AND qrcodeinformation = ? AND storagelocation = ? AND inventorymanagementclassification = ? AND rate = ?");
	        ps.setString(1, masterinventory.getIdentifier());
	        ps.setString(2, masterinventory.getIdentificationnumber());
	        ps.setString(3, masterinventory.getVendor());
	        ps.setString(4, masterinventory.getProductname_asplus());
	        ps.setString(5, masterinventory.getProductnumber_asplus());
	        ps.setString(6, masterinventory.getProductname_correction());
	        ps.setString(7, masterinventory.getProductnumber_correction());
	        ps.setString(8, masterinventory.getQrcodeinformation());
	        ps.setString(9, masterinventory.getStoragelocation());
	        ps.setString(10, masterinventory.getInventorymanagementclassification());
	        ps.setFloat(11, masterinventory.getRate());
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return false;
	        }
	        rs.close();
	        ps.close();

	        // データの挿入
	        ps = db.prepareStatement("INSERT INTO masterinventorys(identifier,identificationnumber,vendor,"
	                + "productname_asplus,productnumber_asplus,productname_correction,productnumber_correction,"
	                + "qrcodeinformation,storagelocation,inventorymanagementclassification,rate) "
	                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");

	        ps.setString(1, masterinventory.getIdentifier());
	        ps.setString(2, masterinventory.getIdentificationnumber());
	        ps.setString(3, masterinventory.getVendor());
	        ps.setString(4, masterinventory.getProductname_asplus());
	        ps.setString(5, masterinventory.getProductnumber_asplus());
	        ps.setString(6, masterinventory.getProductname_correction());
	        ps.setString(7, masterinventory.getProductnumber_correction());
	        ps.setString(8, masterinventory.getQrcodeinformation());
	        ps.setString(9, masterinventory.getStoragelocation());
	        ps.setString(10, masterinventory.getInventorymanagementclassification());
	        ps.setFloat(11, masterinventory.getRate());

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
	public Masterinventory findOne(int id){
		Masterinventory masterinventory=null;
		try{
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM masterinventorys WHERE id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				String identifier=rs.getString("identifier");
				String identificationnumber=rs.getString("identificationnumber");
				String vendor=rs.getString("vendor");
				String productname_asplus=rs.getString("productname_asplus");
				String productnumber_asplus=rs.getString("productnumber_asplus");
				String productname_correction=rs.getString("productname_correction");
				String productnumber_correction=rs.getString("productnumber_correction");
				String qrcodeinformation=rs.getString("qrcodeinformation");
				String storagelocation=rs.getString("storagelocation");
				String inventorymanagementclassification=rs.getString("inventorymanagementclassification");
				float rate=rs.getFloat("rate");
				
				masterinventory=new Masterinventory(id,identifier,identificationnumber,vendor,productname_asplus,
						productnumber_asplus,productname_correction,productnumber_correction,qrcodeinformation,
						storagelocation,inventorymanagementclassification,rate);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return masterinventory;
	}
	public boolean updateOne(Masterinventory masterinventory){
		try{
			this.getConnection();
			ps=db.prepareStatement("UPDATE masterinventorys SET identifier=?,identificationnumber=?,vendor=?,"
					+ "productname_asplus=?,productnumber_asplus=?,productname_correction=?,productnumber_correction=?,"
					+ "qrcodeinformation=?,storagelocation=?,inventorymanagementclassification=?,rate=? "
			        + "WHERE id=?");
			
			ps.setString(1, masterinventory.getIdentifier());
			ps.setString(2, masterinventory.getIdentificationnumber());
			ps.setString(3, masterinventory.getVendor());
			ps.setString(4, masterinventory.getProductname_asplus());
			ps.setString(5, masterinventory.getProductnumber_asplus());
			ps.setString(6, masterinventory.getProductname_correction());
			ps.setString(7, masterinventory.getProductnumber_correction());
			ps.setString(8, masterinventory.getQrcodeinformation());
			ps.setString(9, masterinventory.getStoragelocation());
			ps.setString(10, masterinventory.getInventorymanagementclassification());
			ps.setFloat(11, masterinventory.getRate());

			ps.setInt(12, masterinventory.getId());
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
			ps=db.prepareStatement("DELETE FROM masterinventorys WHERE id=?");
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
	public List<Masterinventory> searchOne(String search) {
	    List<Masterinventory> masterinventoryList = new ArrayList<>();
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM masterinventorys WHERE "
	        		+ "identifier LIKE ? OR vendor LIKE ? OR productname_asplus LIKE ? OR identificationnumber LIKE ? OR "
	        		+ "productnumber_asplus LIKE ? OR productname_correction LIKE ? OR productnumber_correction LIKE ? OR "
	        		+ "qrcodeinformation LIKE ? OR storagelocation LIKE ? OR inventorymanagementclassification LIKE ? OR rate LIKE ?");

	        String searchPattern = "%" + search + "%";
	        
	        ps.setString(1, searchPattern); // identifier LIKE ?
	        ps.setString(2, searchPattern); // vendor LIKE ?
	        ps.setString(3, searchPattern); // productname_asplus LIKE ?
	        ps.setString(4, searchPattern); // identificationnumber LIKE ?
	        ps.setString(5, searchPattern); // productnumber_asplus LIKE ?
	        ps.setString(6, searchPattern); // productname_correction LIKE ?
	        ps.setString(7, searchPattern); // productnumber_correction LIKE ?
	        ps.setString(8, searchPattern); // qrcodeinformation LIKE ?
	        ps.setString(9, searchPattern); // storagelocation LIKE ?
	        ps.setString(10, searchPattern); // inventorymanagementclassification LIKE ?
	        ps.setString(11, searchPattern); // rate ?
	        
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String identifier = rs.getString("identifier");
	            String identificationnumber = rs.getString("identificationnumber");
	            String vendor = rs.getString("vendor");
	            String productname_asplus = rs.getString("productname_asplus");
	            String productnumber_asplus = rs.getString("productnumber_asplus");
	            String productname_correction = rs.getString("productname_correction");
	            String productnumber_correction = rs.getString("productnumber_correction");
	            String qrcodeinformation = rs.getString("qrcodeinformation");
	            String storagelocation = rs.getString("storagelocation");
	            String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
	            float rate=rs.getFloat("rate");

	            // Create Masterinventory object with retrieved values
				Masterinventory masterinventory=new Masterinventory(id,identifier,identificationnumber,vendor,productname_asplus,
						productnumber_asplus,productname_correction,productnumber_correction,qrcodeinformation,
						storagelocation,inventorymanagementclassification,rate);
				
				masterinventoryList.add(masterinventory);
	        }
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return masterinventoryList;
	}
	
	public List<Masterinventory> importOne(Part filePart) throws NamingException {
	    List<Masterinventory> masterinventoryList = new ArrayList<>();
	    try {
	        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "SJIS"))) {

	            String line;
	            int lineNumber = 0; // 行番号をカウント
	            while ((line = buffer.readLine()) != null) {
	                lineNumber++; // 行番号をインクリメント
	                System.out.println("Processing line " + lineNumber); // デバッグ用出力

	                String[] columns = line.split(",", -1);

	                if (columns.length == 11) {
	                	float column10 = Float.parseFloat(columns[10]);
	                    Masterinventory masterinventory = new Masterinventory(
	                        columns[0],
	                        columns[1],
	                        columns[2],
	                        columns[3],
	                        columns[4],
	                        columns[5],
	                        columns[6],
	                        columns[7],
	                        columns[8],
	                        columns[9],
	                        column10 // 変換後の int を使用
	                    );
	                    masterinventoryList.add(masterinventory);
	                } else {
	                    System.err.println("Unexpected number of columns on line " + lineNumber + ": " + columns.length);
	                }
	            }

	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    try {
	    	this.getConnection(); // DB接続を取得
	        db.setAutoCommit(false); // 自動コミットを無効化

	        // 挿入用の準備
	        PreparedStatement ps = db.prepareStatement("INSERT INTO masterinventorys(identifier, identificationnumber, vendor, "
	                + "productname_asplus, productnumber_asplus, productname_correction, productnumber_correction, "
	                + "qrcodeinformation, storagelocation, inventorymanagementclassification,rate) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        for (Masterinventory masterinventory : masterinventoryList) {
	            // プリペアドステートメントのパラメータを設定
	            ps.setString(1, masterinventory.getIdentifier());
	            ps.setString(2, masterinventory.getIdentificationnumber());
	            ps.setString(3, masterinventory.getVendor());
	            ps.setString(4, masterinventory.getProductname_asplus());
	            ps.setString(5, masterinventory.getProductnumber_asplus());
	            ps.setString(6, masterinventory.getProductname_correction());
	            ps.setString(7, masterinventory.getProductnumber_correction());
	            ps.setString(8, masterinventory.getQrcodeinformation());
	            ps.setString(9, masterinventory.getStoragelocation());
	            ps.setString(10, masterinventory.getInventorymanagementclassification());
	            ps.setFloat(11, masterinventory.getRate());

	            try {
	                ps.executeUpdate(); // データベースに挿入
	                db.commit(); // コミット
	            } catch (SQLException e) {
	                System.err.println("Failed to insert row: " + masterinventory.toString());
	                e.printStackTrace();
	                db.rollback(); // 挿入失敗時にロールバック
	            }
	        }
	        
	        db.commit(); // すべての行の挿入後にコミット

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (db != null) db.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (db != null) db.close();
	        } catch (SQLException closeEx) {
	            closeEx.printStackTrace();
	        }
	    }

	    return masterinventoryList;
	}
	public void save(Masterinventory masterinventory) throws NamingException {
	    try {
	        this.getConnection();

	        // データベースにレコードが存在しないかをチェック
	        if (!existsInDatabase(masterinventory.getIdentificationnumber())) {
	            // 挿入用の準備
	            PreparedStatement ps = db.prepareStatement("INSERT INTO masterinventorys(identifier, identificationnumber, vendor, "
	                    + "productname_asplus, productnumber_asplus, productname_correction, productnumber_correction, "
	                    + "qrcodeinformation, storagelocation, inventorymanagementclassification,rate) "
	                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	            // プリペアドステートメントのパラメータを設定
	            ps.setString(1, masterinventory.getIdentifier());
	            ps.setString(2, masterinventory.getIdentificationnumber());
	            ps.setString(3, masterinventory.getVendor());
	            ps.setString(4, masterinventory.getProductname_asplus());
	            ps.setString(5, masterinventory.getProductnumber_asplus());
	            ps.setString(6, masterinventory.getProductname_correction());
	            ps.setString(7, masterinventory.getProductnumber_correction());
	            ps.setString(8, masterinventory.getQrcodeinformation());
	            ps.setString(9, masterinventory.getStoragelocation());
	            ps.setString(10, masterinventory.getInventorymanagementclassification());
	            ps.setFloat(11, masterinventory.getRate());

	            // 挿入を実行
	            ps.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private boolean existsInDatabase(String identificationnumber) throws SQLException {
	    PreparedStatement ps = db.prepareStatement("SELECT COUNT(*) FROM masterinventorys WHERE identificationnumber = ?");
	    ps.setString(1, identificationnumber);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        int count = rs.getInt(1);
	        return count > 0;
	    }
	    return false;
	
    }
	
	public void downloadAll() throws IOException {
	    PrintWriter csvWriter = null;
	    BufferedWriter bufferedWriter = null;

	    try {
	        // データベース接続の取得
	        this.getConnection();
	        
	        // SQL クエリの準備
	        ps = db.prepareStatement("SELECT * FROM masterinventorys");
	        rs = ps.executeQuery();
	        
	        // 現在の日時を取得
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateAndTime = sdf.format(new Date());

            // CSV のファイル名
            String fileName = "masterinventorylist_DL " + currentDateAndTime + ".csv";

	        // 適切なパスを選んでください
            String filePath =  "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName;
            
	        //<!-- UM425QA-KIR915W -->
	        //<!-- DESKTOP-KBUH9GC -->
	        //<!-- String filePath = "E:\\Program Files/"+ fileName; -->

            //<!-- Raspberry Pi(192.168.10.103 ) -->	        
            //<!-- Raspberry Pi(192.168.10.122 ) -->
            //<!-- Raspberry Pi(192.168.10.118 ) -->
	        //<!-- String filePath =  "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName; -->
	        
	        // CSV Writer の準備（BufferedWriter + Shift-JIS）
	        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "Shift-JIS"), 8192); 

	        // ヘッダー行
	        csvWriter.append("識別種類,識別番号,仕入先,品名（Asplus）,品番（Asplus）,品名（Correction）,品番（Correction）,QRCode情報,保管先,在庫管理区分,単価");
	        csvWriter.append("\n");

	        int rowCount = 0;
	        StringBuilder sb = new StringBuilder();
	        
	        // データ行の書き出し
	        while (rs.next()) {
	            csvWriter.append(rs.getString("identifier") + ",");
	            csvWriter.append(rs.getString("identificationnumber") + ",");
	            csvWriter.append(rs.getString("vendor") + ",");
	            csvWriter.append(rs.getString("productname_asplus") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus") + ",");
	            csvWriter.append(rs.getString("productname_correction") + ",");
	            csvWriter.append(rs.getString("productnumber_correction") + ",");
	            csvWriter.append(rs.getString("qrcodeinformation") + ",");
	            csvWriter.append(rs.getString("storagelocation") + ",");
	            csvWriter.append(rs.getString("inventorymanagementclassification") + ",");
	            csvWriter.append(rs.getString("rate"));
	            csvWriter.append("\n");
	            
	            bufferedWriter.write(sb.toString());

	            rowCount++;
	            if (rowCount % 100 == 0) { // 100行ごとに flush
	                bufferedWriter.flush();
	            }
	        }

	        bufferedWriter.flush(); // 最後にflush
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
	
	public Masterinventory qrCreate(int id) {
        Masterinventory masterinventory = null;
        try {
            this.getConnection();
            ps = db.prepareStatement("SELECT * FROM masterinventorys WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String identifier = rs.getString("identifier");
                String identificationnumber = rs.getString("identificationnumber");
                String vendor = rs.getString("vendor");
                String productname_asplus = rs.getString("productname_asplus");
                String productnumber_asplus = rs.getString("productnumber_asplus");
                String productname_correction = rs.getString("productname_correction");
                String productnumber_correction = rs.getString("productnumber_correction");
                String qrcodeinformation = rs.getString("qrcodeinformation");
                String storagelocation = rs.getString("storagelocation");
                String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
                float rate = rs.getFloat("rate");

                masterinventory = new Masterinventory(id, identifier, identificationnumber, vendor, productname_asplus,
                        productnumber_asplus, productname_correction, productnumber_correction, qrcodeinformation,
                        storagelocation, inventorymanagementclassification,rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return masterinventory;
    }
	
}


