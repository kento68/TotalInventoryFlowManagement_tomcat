package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import model.Masteritemtag;

/**
 * Servlet implementation class InventoryDAO
 */
@WebServlet("/MasteritemtagDAO")
public class MasteritemtagDAO {
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
	public List<Masteritemtag> findAll(){
		List<Masteritemtag> masteritemtagList=new ArrayList<>();
		try {
			this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM masteritemtags ORDER BY id DESC");
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String identifier = rs.getString("identifier");
	            String identificationnumber = rs.getString("identificationnumber");
	            String vendor = rs.getString("vendor");
	            String productname_asplus = rs.getString("productname_asplus");
	            String productnumber_asplus = rs.getString("productnumber_asplus");
	            String productname_emphasisColor = rs.getString("productname_emphasisColor");
	            String productnumber_asplussub1 = rs.getString("productnumber_asplussub1");
	            String productnumber_asplus_emphasissub1 = rs.getString("productnumber_asplus_emphasissub1");
	            String productnumber_asplus2sub = rs.getString("productnumber_asplus2sub");
	            String productnumber_asplus_emphasis2sub = rs.getString("productnumber_asplus_emphasis2sub");
	            String productnumber_asplus3sub = rs.getString("productnumber_asplus3sub");
	            String productnumber_asplus_emphasis3sub = rs.getString("productnumber_asplus_emphasis3sub");
	            String productnumber_asplus4sub = rs.getString("productnumber_asplus4sub");
	            String productnumber_asplus5sub = rs.getString("productnumber_asplus5sub");
	            String productnumber_asplus6sub = rs.getString("productnumber_asplus6sub");
	            String productnumber_asplus7sub = rs.getString("productnumber_asplus7sub");
	            String productnumber_asplus8sub = rs.getString("productnumber_asplus8sub");
	            String productnumber_asplus9sub = rs.getString("productnumber_asplus9sub");
	            String productnumber_Color = rs.getString("productnumber_Color");
	            String qrcodeinformation = rs.getString("qrcodeinformation");
	            String storagelocation = rs.getString("storagelocation");
	            String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
	            int quantity = rs.getInt("quantity");
	            String outline = rs.getString("outline");
	            String paperColor = rs.getString("paperColor");
	            String quantitySelectionAvailability = rs.getString("quantitySelectionAvailability");
	            int quantitySelectionRange = rs.getInt("quantitySelectionRange");
	            String storageLocation = rs.getString("storageLocation");
	            String pattern = rs.getString("pattern");
	            String photoAvailability = rs.getString("photoAvailability");

	            Masteritemtag masteritemtag = new Masteritemtag(id,identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
	       	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
	    	         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
	    	         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
	    	         qrcodeinformation, storagelocation, inventorymanagementclassification, quantity, outline, paperColor, 
	    	         quantitySelectionAvailability, quantitySelectionRange, pattern, photoAvailability);
	            
	            masteritemtagList.add(masteritemtag);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return masteritemtagList;
	}
	
	public boolean insertOne(Masteritemtag masteritemtag) {
	    try {
	        this.getConnection();

	        // 挿入前の重複チェック
	        ps = db.prepareStatement("SELECT COUNT(*) FROM masteritemtags WHERE identifier = ? AND identificationnumber = ? "
	                + "AND vendor = ? AND productname_asplus = ? AND productnumber_asplus = ? "
	                + "AND productname_emphasisColor = ? AND productnumber_asplussub1 = ? "
	                + "AND productnumber_asplus_emphasissub1 = ? AND productnumber_asplus2sub = ? "
	                + "AND productnumber_asplus_emphasis2sub = ? AND productnumber_asplus3sub = ? "
	                + "AND productnumber_asplus_emphasis3sub = ? AND productnumber_asplus4sub = ? "
	                + "AND productnumber_asplus5sub = ? AND productnumber_asplus6sub = ? "
	                + "AND productnumber_asplus7sub = ? AND productnumber_asplus8sub = ? "
	                + "AND productnumber_asplus9sub = ? AND productnumber_Color = ? "
	                + "AND qrcodeinformation = ? AND storagelocation = ? AND inventorymanagementclassification = ?");

	        ps.setString(1, masteritemtag.getIdentifier());
	        ps.setString(2, masteritemtag.getIdentificationnumber());
	        ps.setString(3, masteritemtag.getVendor());
	        ps.setString(4, masteritemtag.getProductname_asplus());
	        ps.setString(5, masteritemtag.getProductnumber_asplus());
	        ps.setString(6, masteritemtag.getProductname_emphasisColor());
	        ps.setString(7, masteritemtag.getProductnumber_asplussub1());
	        ps.setString(8, masteritemtag.getProductnumber_asplus_emphasissub1());
	        ps.setString(9, masteritemtag.getProductnumber_asplus2sub());
	        ps.setString(10, masteritemtag.getProductnumber_asplus_emphasis2sub());
	        ps.setString(11, masteritemtag.getProductnumber_asplus3sub());
	        ps.setString(12, masteritemtag.getProductnumber_asplus_emphasis3sub());
	        ps.setString(13, masteritemtag.getProductnumber_asplus4sub());
	        ps.setString(14, masteritemtag.getProductnumber_asplus5sub());
	        ps.setString(15, masteritemtag.getProductnumber_asplus6sub());
	        ps.setString(16, masteritemtag.getProductnumber_asplus7sub());
	        ps.setString(17, masteritemtag.getProductnumber_asplus8sub());
	        ps.setString(18, masteritemtag.getProductnumber_asplus9sub());
	        ps.setString(19, masteritemtag.getProductnumber_Color());
	        ps.setString(20, masteritemtag.getQrcodeinformation());
	        ps.setString(21, masteritemtag.getStoragelocation());
	        ps.setString(22, masteritemtag.getInventorymanagementclassification());

	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return false;  // 重複あり
	        }
	        rs.close();
	        ps.close();

	        // データの挿入
	        ps = db.prepareStatement("INSERT INTO masteritemtags(identifier, identificationnumber, vendor, "
	                + "productname_asplus, productnumber_asplus, productname_emphasisColor, productnumber_asplussub1, "
	                + "productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, "
	                + "productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, "
	                + "productnumber_asplus5sub, productnumber_asplus6sub, productnumber_asplus7sub, "
	                + "productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, qrcodeinformation, "
	                + "storagelocation, inventorymanagementclassification) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        ps.setString(1, masteritemtag.getIdentifier());
	        ps.setString(2, masteritemtag.getIdentificationnumber());
	        ps.setString(3, masteritemtag.getVendor());
	        ps.setString(4, masteritemtag.getProductname_asplus());
	        ps.setString(5, masteritemtag.getProductnumber_asplus());
	        ps.setString(6, masteritemtag.getProductname_emphasisColor());
	        ps.setString(7, masteritemtag.getProductnumber_asplussub1());
	        ps.setString(8, masteritemtag.getProductnumber_asplus_emphasissub1());
	        ps.setString(9, masteritemtag.getProductnumber_asplus2sub());
	        ps.setString(10, masteritemtag.getProductnumber_asplus_emphasis2sub());
	        ps.setString(11, masteritemtag.getProductnumber_asplus3sub());
	        ps.setString(12, masteritemtag.getProductnumber_asplus_emphasis3sub());
	        ps.setString(13, masteritemtag.getProductnumber_asplus4sub());
	        ps.setString(14, masteritemtag.getProductnumber_asplus5sub());
	        ps.setString(15, masteritemtag.getProductnumber_asplus6sub());
	        ps.setString(16, masteritemtag.getProductnumber_asplus7sub());
	        ps.setString(17, masteritemtag.getProductnumber_asplus8sub());
	        ps.setString(18, masteritemtag.getProductnumber_asplus9sub());
	        ps.setString(19, masteritemtag.getProductnumber_Color());
	        ps.setString(20, masteritemtag.getQrcodeinformation());
	        ps.setString(21, masteritemtag.getStoragelocation());
	        ps.setString(22, masteritemtag.getInventorymanagementclassification());

	        int result = ps.executeUpdate();

	        if (result != 1) {
	            return false;  // 挿入に失敗
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
	    return true;  // 挿入成功
	}
	
	public Masteritemtag findOne(int id) {
		Masteritemtag masteritemtag = null;
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM masteritemtags WHERE id=?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            String identifier = rs.getString("identifier");
	            String identificationnumber = rs.getString("identificationnumber");
	            String vendor = rs.getString("vendor");
	            String productname_asplus = rs.getString("productname_asplus");
	            String productnumber_asplus = rs.getString("productnumber_asplus");
	            String productname_emphasisColor = rs.getString("productname_emphasisColor");
	            String productnumber_asplussub1 = rs.getString("productnumber_asplussub1");
	            String productnumber_asplus_emphasissub1 = rs.getString("productnumber_asplus_emphasissub1");
	            String productnumber_asplus2sub = rs.getString("productnumber_asplus2sub");
	            String productnumber_asplus_emphasis2sub = rs.getString("productnumber_asplus_emphasis2sub");
	            String productnumber_asplus3sub = rs.getString("productnumber_asplus3sub");
	            String productnumber_asplus_emphasis3sub = rs.getString("productnumber_asplus_emphasis3sub");
	            String productnumber_asplus4sub = rs.getString("productnumber_asplus4sub");
	            String productnumber_asplus5sub = rs.getString("productnumber_asplus5sub");
	            String productnumber_asplus6sub = rs.getString("productnumber_asplus6sub");
	            String productnumber_asplus7sub = rs.getString("productnumber_asplus7sub");
	            String productnumber_asplus8sub = rs.getString("productnumber_asplus8sub");
	            String productnumber_asplus9sub = rs.getString("productnumber_asplus9sub");
	            String productnumber_Color = rs.getString("productnumber_Color");
	            String qrcodeinformation = rs.getString("qrcodeinformation");
	            String storagelocation = rs.getString("storagelocation");
	            String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
	            int quantity = rs.getInt("quantity");
	            String outline = rs.getString("outline");
	            String paperColor = rs.getString("paperColor");
	            String quantitySelectionAvailability = rs.getString("quantitySelectionAvailability");
	            int quantitySelectionRange = rs.getInt("quantitySelectionRange");
	            String storageLocation = rs.getString("storageLocation");
	            String pattern = rs.getString("pattern");
	            String photoAvailability = rs.getString("photoAvailability");

	            masteritemtag = new Masteritemtag(id,identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
	       	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
	    	         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
	    	         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
	    	         qrcodeinformation, storagelocation, inventorymanagementclassification, quantity, outline, paperColor, 
	    	         quantitySelectionAvailability, quantitySelectionRange, pattern, photoAvailability);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return masteritemtag;
	}

	public boolean updateOne(Masteritemtag masteritemtag) {
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("UPDATE masteritemtags SET identifier=?, identificationnumber=?, vendor=?,"
	                + "productname_asplus=?, productnumber_asplus=?, productname_emphasisColor=?, productnumber_asplussub1=?,"
	                + "productnumber_asplus_emphasissub1=?, productnumber_asplus2sub=?, productnumber_asplus_emphasis2sub=?,"
	                + "productnumber_asplus3sub=?, productnumber_asplus_emphasis3sub=?, productnumber_asplus4sub=?,"
	                + "productnumber_asplus5sub=?, productnumber_asplus6sub=?, productnumber_asplus7sub=?, productnumber_asplus8sub=?,"
	                + "productnumber_asplus9sub=?, productnumber_Color=?, qrcodeinformation=?, storagelocation=?,"
	                + "inventorymanagementclassification=?, quantity=?, outline=?, paperColor=?, quantitySelectionAvailability=?,"
	                + "quantitySelectionRange=?, pattern=?, photoAvailability=? WHERE id=?");

	        ps.setString(1, masteritemtag.getIdentifier());
	        ps.setString(2, masteritemtag.getIdentificationnumber());
	        ps.setString(3, masteritemtag.getVendor());
	        ps.setString(4, masteritemtag.getProductname_asplus());
	        ps.setString(5, masteritemtag.getProductnumber_asplus());
	        ps.setString(6, masteritemtag.getProductname_emphasisColor());
	        ps.setString(7, masteritemtag.getProductnumber_asplussub1());
	        ps.setString(8, masteritemtag.getProductnumber_asplus_emphasissub1());
	        ps.setString(9, masteritemtag.getProductnumber_asplus2sub());
	        ps.setString(10, masteritemtag.getProductnumber_asplus_emphasis2sub());
	        ps.setString(11, masteritemtag.getProductnumber_asplus3sub());
	        ps.setString(12, masteritemtag.getProductnumber_asplus_emphasis3sub());
	        ps.setString(13, masteritemtag.getProductnumber_asplus4sub());
	        ps.setString(14, masteritemtag.getProductnumber_asplus5sub());
	        ps.setString(15, masteritemtag.getProductnumber_asplus6sub());
	        ps.setString(16, masteritemtag.getProductnumber_asplus7sub());
	        ps.setString(17, masteritemtag.getProductnumber_asplus8sub());
	        ps.setString(18, masteritemtag.getProductnumber_asplus9sub());
	        ps.setString(19, masteritemtag.getProductnumber_Color());
	        ps.setString(20, masteritemtag.getQrcodeinformation());
	        ps.setString(21, masteritemtag.getStoragelocation());
	        ps.setString(22, masteritemtag.getInventorymanagementclassification());
	        ps.setInt(23, masteritemtag.getQuantity());
	        ps.setString(24, masteritemtag.getOutline());
	        ps.setString(25, masteritemtag.getPaperColor());
	        ps.setString(26, masteritemtag.getQuantitySelectionAvailability());
	        ps.setInt(27, masteritemtag.getQuantitySelectionRange());
	        ps.setString(28, masteritemtag.getPattern());
	        ps.setString(29, masteritemtag.getPhotoAvailability());

	        ps.setInt(30, masteritemtag.getId());

	        int result = ps.executeUpdate();
	        if (result != 1) {
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return true;
	}
	
	public boolean deleteOne(int id){

		try{
			this.getConnection();
			ps=db.prepareStatement("DELETE FROM masteritemtags WHERE id=?");
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
	
	public List<Masteritemtag> searchOne(String search) {
	    List<Masteritemtag> masteritemtagList = new ArrayList<>();
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM masteritemtags WHERE "
	                + "identifier LIKE ? OR vendor LIKE ? OR productname_asplus LIKE ? OR identificationnumber LIKE ? OR "
	                + "productnumber_asplus LIKE ? OR productname_emphasisColor LIKE ? OR productnumber_asplussub1 LIKE ? OR "
	                + "productnumber_asplus_emphasissub1 LIKE ? OR productnumber_asplus2sub LIKE ? OR productnumber_asplus_emphasis2sub LIKE ? OR "
	                + "productnumber_asplus3sub LIKE ? OR productnumber_asplus_emphasis3sub LIKE ? OR productnumber_asplus4sub LIKE ? OR "
	                + "productnumber_asplus5sub LIKE ? OR productnumber_asplus6sub LIKE ? OR productnumber_asplus7sub LIKE ? OR "
	                + "productnumber_asplus8sub LIKE ? OR productnumber_asplus9sub LIKE ? OR productnumber_Color LIKE ? OR "
	                + "qrcodeinformation LIKE ? OR storagelocation LIKE ? OR inventorymanagementclassification LIKE ? OR "
	                + "quantity LIKE ? OR outline LIKE ? OR paperColor LIKE ? OR quantitySelectionAvailability LIKE ? OR "
	                + "quantitySelectionRange LIKE ? OR pattern LIKE ? OR photoAvailability LIKE ?");

	        String searchPattern = "%" + search + "%";

	        // Set parameters for the prepared statement
	        ps.setString(1, searchPattern); // identifier LIKE ?
	        ps.setString(2, searchPattern); // vendor LIKE ?
	        ps.setString(3, searchPattern); // productname_asplus LIKE ?
	        ps.setString(4, searchPattern); // identificationnumber LIKE ?
	        ps.setString(5, searchPattern); // productnumber_asplus LIKE ?
	        ps.setString(6, searchPattern); // productname_emphasisColor LIKE ?
	        ps.setString(7, searchPattern); // productnumber_asplussub1 LIKE ?
	        ps.setString(8, searchPattern); // productnumber_asplus_emphasissub1 LIKE ?
	        ps.setString(9, searchPattern); // productnumber_asplus2sub LIKE ?
	        ps.setString(10, searchPattern); // productnumber_asplus_emphasis2sub LIKE ?
	        ps.setString(11, searchPattern); // productnumber_asplus3sub LIKE ?
	        ps.setString(12, searchPattern); // productnumber_asplus_emphasis3sub LIKE ?
	        ps.setString(13, searchPattern); // productnumber_asplus4sub LIKE ?
	        ps.setString(14, searchPattern); // productnumber_asplus5sub LIKE ?
	        ps.setString(15, searchPattern); // productnumber_asplus6sub LIKE ?
	        ps.setString(16, searchPattern); // productnumber_asplus7sub LIKE ?
	        ps.setString(17, searchPattern); // productnumber_asplus8sub LIKE ?
	        ps.setString(18, searchPattern); // productnumber_asplus9sub LIKE ?
	        ps.setString(19, searchPattern); // productnumber_Color LIKE ?
	        ps.setString(20, searchPattern); // qrcodeinformation LIKE ?
	        ps.setString(21, searchPattern); // storagelocation LIKE ?
	        ps.setString(22, searchPattern); // inventorymanagementclassification LIKE ?
	        ps.setString(23, searchPattern); // quantity LIKE ?
	        ps.setString(24, searchPattern); // outline LIKE ?
	        ps.setString(25, searchPattern); // paperColor LIKE ?
	        ps.setString(26, searchPattern); // quantitySelectionAvailability LIKE ?
	        ps.setString(27, searchPattern); // quantitySelectionRange LIKE ?
	        ps.setString(28, searchPattern); // pattern LIKE ?
	        ps.setString(29, searchPattern); // photoAvailability LIKE ?

	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String identifier = rs.getString("identifier");
	            String identificationnumber = rs.getString("identificationnumber");
	            String vendor = rs.getString("vendor");
	            String productname_asplus = rs.getString("productname_asplus");
	            String productnumber_asplus = rs.getString("productnumber_asplus");
	            String productname_emphasisColor = rs.getString("productname_emphasisColor");
	            String productnumber_asplussub1 = rs.getString("productnumber_asplussub1");
	            String productnumber_asplus_emphasissub1 = rs.getString("productnumber_asplus_emphasissub1");
	            String productnumber_asplus2sub = rs.getString("productnumber_asplus2sub");
	            String productnumber_asplus_emphasis2sub = rs.getString("productnumber_asplus_emphasis2sub");
	            String productnumber_asplus3sub = rs.getString("productnumber_asplus3sub");
	            String productnumber_asplus_emphasis3sub = rs.getString("productnumber_asplus_emphasis3sub");
	            String productnumber_asplus4sub = rs.getString("productnumber_asplus4sub");
	            String productnumber_asplus5sub = rs.getString("productnumber_asplus5sub");
	            String productnumber_asplus6sub = rs.getString("productnumber_asplus6sub");
	            String productnumber_asplus7sub = rs.getString("productnumber_asplus7sub");
	            String productnumber_asplus8sub = rs.getString("productnumber_asplus8sub");
	            String productnumber_asplus9sub = rs.getString("productnumber_asplus9sub");
	            String productnumber_Color = rs.getString("productnumber_Color");
	            String qrcodeinformation = rs.getString("qrcodeinformation");
	            String storagelocation = rs.getString("storagelocation");
	            String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
	            int quantity = rs.getInt("quantity");
	            String outline = rs.getString("outline");
	            String paperColor = rs.getString("paperColor");
	            String quantitySelectionAvailability = rs.getString("quantitySelectionAvailability");
	            int quantitySelectionRange = rs.getInt("quantitySelectionRange");
	            String pattern = rs.getString("pattern");
	            String photoAvailability = rs.getString("photoAvailability");

	            // Create Masterinventory object with retrieved values
	            Masteritemtag masteritemtag = new Masteritemtag(id,identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
		       	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
		    	         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
		    	         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
		    	         qrcodeinformation, storagelocation, inventorymanagementclassification, quantity, outline, paperColor, 
		    	         quantitySelectionAvailability, quantitySelectionRange, pattern, photoAvailability);
		            
		            masteritemtagList.add(masteritemtag);
	        }
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return masteritemtagList;
	}
	
	public List<Masteritemtag> importOne(Part filePart) throws NamingException {
	    List<Masteritemtag> masteritemtagList = new ArrayList<>();
	    try {
	        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "SJIS"))) {

	            String line;
	            int lineNumber = 0; // 行番号をカウント
	            while ((line = buffer.readLine()) != null) {
	                lineNumber++; // 行番号をインクリメント
	                System.out.println("Processing line " + lineNumber); // デバッグ用出力

	                String[] columns = line.split(",", -1);

	                if (columns.length == 29) {
	                	Masteritemtag masteritemtag = new Masteritemtag(
	                        columns[0],columns[1],columns[2],columns[3],
	                        columns[4],columns[5],columns[6],columns[7],
	                        columns[8],columns[9],columns[10],columns[11],
	                        columns[12],columns[13],columns[14],columns[15],
	                        columns[16],columns[17],columns[18],columns[19],
	                        columns[20],columns[21],Integer.parseInt(columns[22]),columns[23],
	                        columns[24],columns[25],Integer.parseInt(columns[26]),columns[27],
	                        columns[28]
	                    );
	                	masteritemtagList.add(masteritemtag);
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
            PreparedStatement ps = db.prepareStatement(
	                "INSERT INTO masteritemtags(identifier, identificationnumber, vendor, "
	                + "productname_asplus, productnumber_asplus, productname_emphasisColor, "
	                + "productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, "
	                + "productnumber_asplus_emphasis2sub, productnumber_asplus3sub, productnumber_asplus_emphasis3sub, "
	                + "productnumber_asplus4sub, productnumber_asplus5sub, productnumber_asplus6sub, "
	                + "productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, "
	                + "productnumber_Color, qrcodeinformation, storagelocation, inventorymanagementclassification, "
	                + "quantity, outline, paperColor, quantitySelectionAvailability, quantitySelectionRange, "
	                + "pattern, photoAvailability) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        for (Masteritemtag masteritemtag : masteritemtagList) {
		        // プリペアドステートメントのパラメータを設定
		        ps.setString(1, masteritemtag.getIdentifier());
		        ps.setString(2, masteritemtag.getIdentificationnumber());
		        ps.setString(3, masteritemtag.getVendor());
		        ps.setString(4, masteritemtag.getProductname_asplus());
		        ps.setString(5, masteritemtag.getProductnumber_asplus());
		        ps.setString(6, masteritemtag.getProductname_emphasisColor());
		        ps.setString(7, masteritemtag.getProductnumber_asplussub1());
		        ps.setString(8, masteritemtag.getProductnumber_asplus_emphasissub1());
		        ps.setString(9, masteritemtag.getProductnumber_asplus2sub());
		        ps.setString(10, masteritemtag.getProductnumber_asplus_emphasis2sub());
		        ps.setString(11, masteritemtag.getProductnumber_asplus3sub());
		        ps.setString(12, masteritemtag.getProductnumber_asplus_emphasis3sub());
		        ps.setString(13, masteritemtag.getProductnumber_asplus4sub());
		        ps.setString(14, masteritemtag.getProductnumber_asplus5sub());
		        ps.setString(15, masteritemtag.getProductnumber_asplus6sub());
		        ps.setString(16, masteritemtag.getProductnumber_asplus7sub());
		        ps.setString(17, masteritemtag.getProductnumber_asplus8sub());
		        ps.setString(18, masteritemtag.getProductnumber_asplus9sub());
		        ps.setString(19, masteritemtag.getProductnumber_Color());
		        ps.setString(20, masteritemtag.getQrcodeinformation());
		        ps.setString(21, masteritemtag.getStoragelocation());
		        ps.setString(22, masteritemtag.getInventorymanagementclassification());
		        ps.setInt(23, masteritemtag.getQuantity());
		        ps.setString(24, masteritemtag.getOutline());
		        ps.setString(25, masteritemtag.getPaperColor());
		        ps.setString(26, masteritemtag.getQuantitySelectionAvailability());
		        ps.setInt(27, masteritemtag.getQuantitySelectionRange());
		        ps.setString(28, masteritemtag.getPattern());
		        ps.setString(29, masteritemtag.getPhotoAvailability());

	            try {
	                ps.executeUpdate(); // データベースに挿入
	                db.commit(); // コミット
	            } catch (SQLException e) {
	                System.err.println("Failed to insert row: " + masteritemtag.toString());
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

	    return masteritemtagList;
	}
	
	public void save(Masteritemtag masteritemtag) throws NamingException {
	    try {
	        this.getConnection();

	        // データベースにレコードが存在しないかをチェック
	        if (!existsInDatabase(masteritemtag.getIdentificationnumber())) {
	            // 挿入用の準備
	            PreparedStatement ps = db.prepareStatement(
	                "INSERT INTO masteritemtags(identifier, identificationnumber, vendor, "
	                + "productname_asplus, productnumber_asplus, productname_emphasisColor, "
	                + "productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, "
	                + "productnumber_asplus_emphasis2sub, productnumber_asplus3sub, productnumber_asplus_emphasis3sub, "
	                + "productnumber_asplus4sub, productnumber_asplus5sub, productnumber_asplus6sub, "
	                + "productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, "
	                + "productnumber_Color, qrcodeinformation, storagelocation, inventorymanagementclassification, "
	                + "quantity, outline, paperColor, quantitySelectionAvailability, quantitySelectionRange, "
	                + "pattern, photoAvailability) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        // プリペアドステートメントのパラメータを設定
	        ps.setString(1, masteritemtag.getIdentifier());
	        ps.setString(2, masteritemtag.getIdentificationnumber());
	        ps.setString(3, masteritemtag.getVendor());
	        ps.setString(4, masteritemtag.getProductname_asplus());
	        ps.setString(5, masteritemtag.getProductnumber_asplus());
	        ps.setString(6, masteritemtag.getProductname_emphasisColor());
	        ps.setString(7, masteritemtag.getProductnumber_asplussub1());
	        ps.setString(8, masteritemtag.getProductnumber_asplus_emphasissub1());
	        ps.setString(9, masteritemtag.getProductnumber_asplus2sub());
	        ps.setString(10, masteritemtag.getProductnumber_asplus_emphasis2sub());
	        ps.setString(11, masteritemtag.getProductnumber_asplus3sub());
	        ps.setString(12, masteritemtag.getProductnumber_asplus_emphasis3sub());
	        ps.setString(13, masteritemtag.getProductnumber_asplus4sub());
	        ps.setString(14, masteritemtag.getProductnumber_asplus5sub());
	        ps.setString(15, masteritemtag.getProductnumber_asplus6sub());
	        ps.setString(16, masteritemtag.getProductnumber_asplus7sub());
	        ps.setString(17, masteritemtag.getProductnumber_asplus8sub());
	        ps.setString(18, masteritemtag.getProductnumber_asplus9sub());
	        ps.setString(19, masteritemtag.getProductnumber_Color());
	        ps.setString(20, masteritemtag.getQrcodeinformation());
	        ps.setString(21, masteritemtag.getStoragelocation());
	        ps.setString(22, masteritemtag.getInventorymanagementclassification());
	        ps.setInt(23, masteritemtag.getQuantity());
	        ps.setString(24, masteritemtag.getOutline());
	        ps.setString(25, masteritemtag.getPaperColor());
	        ps.setString(26, masteritemtag.getQuantitySelectionAvailability());
	        ps.setInt(27, masteritemtag.getQuantitySelectionRange());
	        ps.setString(28, masteritemtag.getPattern());
	        ps.setString(29, masteritemtag.getPhotoAvailability());

	        // 挿入を実行
	        ps.executeUpdate();
	        ps.close(); // Close PreparedStatement to free up resources
	    }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect(); // Ensure connection is closed
	    }
	}

	private boolean existsInDatabase(String identificationnumber) throws SQLException {
	    PreparedStatement ps = db.prepareStatement("SELECT COUNT(*) FROM masteritemtags WHERE identificationnumber = ?");
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

	    try {
	        // データベース接続の取得
	        this.getConnection();
	        
	        // SQL クエリの準備
	        ps = db.prepareStatement("SELECT * FROM masteritemtags");
	        rs = ps.executeQuery();
	        
	        // 現在の日時を取得
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateAndTime = sdf.format(new Date());

            // CSV のファイル名
            String fileName = "masteritemtaglist_DL " + currentDateAndTime + ".csv";

	        // 適切なパスを選んでください
	        String filePath = "E:\\Program Files/"+ fileName;
            
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
	        csvWriter.append("識別種類,識別番号,仕入先,品名（Asplus）,品名強調カラー,品番（Asplus）,品番（Asplus）1,品番（Asplus）_強調1,"
	        		+ "品番（Asplus）2,品番（Asplus）_強調2,品番（Asplus）3,品番（Asplus）_強調3,品番（Asplus）4,品番（Asplus）5,品番（Asplus）6,"
	        		+ "品番（Asplus）7,品番（Asplus）8,品番（Asplus）9,品番カラー,QRCode情報,保管先,在庫管理区分,数量,白抜き,用紙色,"
	        		+ "数量選択有無,数量選択幅,パターン,写真有無");
	        csvWriter.append("\n");

	        // データ行の書き出し
	        while (rs.next()) {
	            csvWriter.append(rs.getString("identifier") + ",");
	            csvWriter.append(rs.getString("identificationnumber") + ",");
	            csvWriter.append(rs.getString("vendor") + ",");
	            csvWriter.append(rs.getString("productname_asplus") + ",");
	            csvWriter.append(rs.getString("productname_emphasisColor") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus") + ",");
	            csvWriter.append(rs.getString("productnumber_asplussub1") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus_emphasissub1") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus2sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus_emphasis2sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus3sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus_emphasis3sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus4sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus5sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus6sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus7sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus8sub") + ",");
	            csvWriter.append(rs.getString("productnumber_asplus9sub") + ",");
	            csvWriter.append(rs.getString("productnumber_Color") + ",");
	            csvWriter.append(rs.getString("qrcodeinformation") + ",");
	            csvWriter.append(rs.getString("storagelocation") + ",");
	            csvWriter.append(rs.getString("inventorymanagementclassification") + ",");
	            csvWriter.append(String.valueOf(rs.getInt("quantity")) + ",");
	            csvWriter.append(rs.getString("outline") + ",");
	            csvWriter.append(rs.getString("paperColor") + ",");
	            csvWriter.append(rs.getString("quantitySelectionAvailability") + ",");
	            csvWriter.append(String.valueOf(rs.getInt("quantitySelectionRange")) + ",");
	            csvWriter.append(rs.getString("pattern") + ",");
	            csvWriter.append(rs.getString("photoAvailability"));
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
	
	public Masteritemtag qrCreate(int id) {
		Masteritemtag masteritemtag = null;
	    try {
	        this.getConnection();
	        ps = db.prepareStatement("SELECT * FROM masteritemtags WHERE id=?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            String identifier = rs.getString("identifier");
	            String identificationnumber = rs.getString("identificationnumber");
	            String vendor = rs.getString("vendor");
	            String productname_asplus = rs.getString("productname_asplus");
	            String productnumber_asplus = rs.getString("productnumber_asplus");
	            String productname_emphasisColor = rs.getString("productname_emphasisColor");
	            String productnumber_asplussub1 = rs.getString("productnumber_asplussub1");
	            String productnumber_asplus_emphasissub1 = rs.getString("productnumber_asplus_emphasissub1");
	            String productnumber_asplus2sub = rs.getString("productnumber_asplus2sub");
	            String productnumber_asplus_emphasis2sub = rs.getString("productnumber_asplus_emphasis2sub");
	            String productnumber_asplus3sub = rs.getString("productnumber_asplus3sub");
	            String productnumber_asplus_emphasis3sub = rs.getString("productnumber_asplus_emphasis3sub");
	            String productnumber_asplus4sub = rs.getString("productnumber_asplus4sub");
	            String productnumber_asplus5sub = rs.getString("productnumber_asplus5sub");
	            String productnumber_asplus6sub = rs.getString("productnumber_asplus6sub");
	            String productnumber_asplus7sub = rs.getString("productnumber_asplus7sub");
	            String productnumber_asplus8sub = rs.getString("productnumber_asplus8sub");
	            String productnumber_asplus9sub = rs.getString("productnumber_asplus9sub");
	            String productnumber_Color = rs.getString("productnumber_Color");
	            String qrcodeinformation = rs.getString("qrcodeinformation");
	            String storagelocation = rs.getString("storagelocation");
	            String inventorymanagementclassification = rs.getString("inventorymanagementclassification");
	            int quantity = rs.getInt("quantity");
	            String outline = rs.getString("outline");
	            String paperColor = rs.getString("paperColor");
	            String quantitySelectionAvailability = rs.getString("quantitySelectionAvailability");
	            int quantitySelectionRange = rs.getInt("quantitySelectionRange");
	            String storageLocation = rs.getString("storageLocation");
	            String pattern = rs.getString("pattern");
	            String photoAvailability = rs.getString("photoAvailability");

	            masteritemtag = new Masteritemtag(id,identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
	       	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
	    	         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
	    	         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
	    	         qrcodeinformation, storagelocation, inventorymanagementclassification, quantity, outline, paperColor, 
	    	         quantitySelectionAvailability, quantitySelectionRange, pattern, photoAvailability);
	        }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return masteritemtag;
    }
	
}
