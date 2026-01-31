package dao;

import java.io.BufferedWriter;
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

	// DB接続オブジェクト（JNDIで取得したDataSourceから生成）
	private Connection db;

	// SQL実行用（パラメータ付きSQL）
	private PreparedStatement ps;

	// SELECT結果を受け取るResultSet
	private ResultSet rs;

	// （※このコード内では未使用）アップロードされたファイル部品配列を保持する想定
	private Part[] fileParts;

	// -----------------------------
	// DB接続を取得する（JNDI経由）
	// -----------------------------
	private void getConnection() throws NamingException, SQLException{
			// JNDIコンテキスト取得
			Context context=new InitialContext();
			// Tomcat等に設定されたDataSourceを参照
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/jsp");
			// DB接続を生成
			this.db=ds.getConnection();
	}

	// -----------------------------
	// DBリソースをクローズする
	// -----------------------------
	private void disconnect(){
		try{
			if(rs != null){rs.close();}   // SELECT結果を閉じる
			if(ps != null){ps.close();}   // SQL実行オブジェクトを閉じる
			if(db != null){db.close();}   // DB接続を閉じる
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	// -----------------------------
	// inventorys 全件取得（新しい順）
	// -----------------------------
	public List<Inventorymanagementinquiry> findAll(){
		List<Inventorymanagementinquiry> inventorymanagementinquiryList=new ArrayList<>();
		try {
			this.getConnection();
			// inventorysをid降順で取得
			ps=db.prepareStatement("SELECT * FROM inventorys ORDER BY id DESC");
			rs=ps.executeQuery();
			while(rs.next()){
				// DBカラムをモデルに詰め替え
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
				float    rate=rs.getFloat("rate");

				Inventorymanagementinquiry inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes,rate);

				// 取得した1行分をリストへ追加
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

	// -----------------------------
	// inventorys 1件追加（重複チェックあり）
	// -----------------------------
	public boolean insertOne(Inventorymanagementinquiry inventorymanagementinquiry){
	    try {
	        this.getConnection();

	        // 追加前に「全項目一致」の重複をチェック
	        ps = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE identificationnumber = ? AND productname_productnumber = ? "
	                + "AND qrcodeinformation = ? AND locationnumber = ? AND locationnumberdestination = ? AND initialinventory = ? "
	                + "AND theoreticalinventory = ? AND inventorymanagementclassification = ? "
	                + "AND inventorystock = ? AND investigationrequired = ? AND decision = ? AND remarks = ? AND fileattributes = ? AND rate = ?");
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
	        ps.setFloat   (14, inventorymanagementinquiry.getRate());

	        ResultSet rs = ps.executeQuery();
	        // 既に同じレコードがあるなら挿入しない
	        if (rs.next() && rs.getInt(1) > 0) {
	            return false;
	        }
	        rs.close();
	        ps.close();

	        // 重複がなければINSERT
	        ps = db.prepareStatement("INSERT INTO inventorys(identificationnumber,productname_productnumber,qrcodeinformation,"
	                + "locationnumber,locationnumberdestination,initialinventory,theoreticalinventory,inventorymanagementclassification,"
	                + "inventorystock,investigationrequired,decision,remarks,fileattributes,rate) "
	                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
	        ps.setFloat   (14, inventorymanagementinquiry.getRate());

	        // 実行結果（更新行数）チェック
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

	// -----------------------------
	// id指定でinventorys 1件取得
	// -----------------------------
	public Inventorymanagementinquiry findOne(int id){
		Inventorymanagementinquiry inventorymanagementinquiry=null;
		try{
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM inventorys WHERE id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				// 1件分をモデルに詰め替え
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
				float    rate=rs.getFloat("rate");

				inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes,rate);
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

	// -----------------------------
	// inventorys 1件更新（id指定）
	// -----------------------------
	public boolean updateOne(Inventorymanagementinquiry inventorymanagementinquiry){
	    try{
	        this.getConnection();
	        // 既存レコードを全項目更新
	        ps = db.prepareStatement("UPDATE inventorys SET identificationnumber=?,productname_productnumber=?,qrcodeinformation=?,"
	                + "locationnumber=?,locationnumberdestination=?,initialinventory=?,theoreticalinventory=?,"
	                + "inventorymanagementclassification=?,inventorystock=?,investigationrequired=?,decision=?,remarks=?,fileattributes=?,rate=? "
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
	        ps.setFloat(14, inventorymanagementinquiry.getRate());

	        ps.setInt(15, inventorymanagementinquiry.getId());
	        int result = ps.executeUpdate();
	        if(result != 1){
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // SQLエラー出力
	    } catch (NamingException e) {
	        e.printStackTrace();  // JNDIエラー出力
	    } finally {
	        this.disconnect();
	    }
	    return true;
	}

	// -----------------------------
	// id指定でinventorys 1件削除
	// -----------------------------
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

	// -----------------------------
	// 検索文字列でinventorysを部分一致検索（OR条件）
	// -----------------------------
	public List<Inventorymanagementinquiry> searchOne(String search) {
	    List<Inventorymanagementinquiry> inventorymanagementinquiryList = new ArrayList<>();
	    try {
	        this.getConnection();
	        // 主要カラムに対しLIKE検索
	        ps = db.prepareStatement("SELECT * FROM inventorys WHERE "
	        		+ "identificationnumber LIKE ? OR productname_productnumber LIKE ? OR qrcodeinformation LIKE ? OR locationnumber LIKE ? OR "
	        		+ "locationnumberdestination LIKE ? OR initialinventory LIKE ? OR theoreticalinventory LIKE ? OR inventorymanagementclassification LIKE ? OR "
	        		+ "inventorystock LIKE ? OR investigationrequired LIKE ? OR decision LIKE ? OR remarks LIKE ? OR fileattributes LIKE ? OR rate LIKE ?");

	        String searchPattern = "%" + search + "%";

	        // すべて同じ検索パターンを流し込む
	        ps.setString(1, searchPattern);
	        ps.setString(2, searchPattern);
	        ps.setString(3, searchPattern);
	        ps.setString(4, searchPattern);
	        ps.setString(5, searchPattern);
	        ps.setString(6, searchPattern);
	        ps.setString(7, searchPattern);
	        ps.setString(8, searchPattern);
	        ps.setString(9, searchPattern);
	        ps.setString(10, searchPattern);
	        ps.setString(11, searchPattern);
	        ps.setString(12, searchPattern);
	        ps.setString(13, searchPattern);
	        ps.setString(14, searchPattern);

	        rs = ps.executeQuery();
	        while (rs.next()) {
	            // ヒットした行をモデルに変換して返却リストへ
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
				float    rate=rs.getFloat("rate");

				Inventorymanagementinquiry inventorymanagementinquiry=new Inventorymanagementinquiry(
						id,identificationnumber,productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						initialinventory,theoreticalinventory,inventorymanagementclassification,inventorystock,
						investigationrequired,decision,remarks,fileattributes,rate);

				inventorymanagementinquiryList.add(inventorymanagementinquiry);
	        }
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	    } finally {
	        this.disconnect();
	    }
	    return inventorymanagementinquiryList;
	}

	// -----------------------------
	// CSV（行リスト）をInventorymanagementinquiryのリストへ変換（インポート前処理）
	// -----------------------------
	public List<Inventorymanagementinquiry> importOne(List<String> lines) {
	    List<Inventorymanagementinquiry> inventoryManagementInquiryList = new ArrayList<>();

	    for (String line : lines) {
	        try {
	            line = line.replace("\uFEFF", ""); // BOMを除去（CSV先頭の不可視文字対策）
	            String[] values = line.split(",");

	            // 列数不足のとき、固定値で補完して配列長を14に揃える
	            if (values.length < 14) {
	                String[] tempValues = new String[14];
	                System.arraycopy(values, 0, tempValues, 0, values.length);
	                for (int i = values.length; i < 14; i++) {
	                    tempValues[i] = (i == 6 || i == 7 || i == 9 || i == 10 || i == 11 || i == 12) ? "0" : "-";
	                }
	                values = tempValues;
	            }

	            // Log種別ごとに行を解析してInventorymanagementinquiryへ変換
	            if ("StorageLog".equals(values[2]) || "RetrievalLog".equals(values[2])) {
	                try {
	                    String InventorymanagementinquiryValue1 = null;
	                    String InventorymanagementinquiryValue2 = null;
	                    String InventorymanagementinquiryValue8 = null;

	                    // 部品名っぽい文字列から「商品名」「区分」「識別番号」を抽出する想定
	                    if (values[5].matches(".*\\*.*\\*\\{.*\\}")) {
	                        String[] parts = values[5].split("\\*|\\{|\\}");

	                        // partsの長さチェック
	                        if (parts.length >= 3) {
	                            if (parts[0] != null && parts[1] != null && parts[3] != null) {
	                                InventorymanagementinquiryValue2 = parts[0]; // sql← csv:productname_productnumber
	                                InventorymanagementinquiryValue1 = parts[3]; // sql← csv:identificationnumber

	                                // 在庫管理区分（4桁）チェック
	                                if (parts[1].length() == 4) {
	                                    InventorymanagementinquiryValue8 = parts[1]; // sql← csv:inventorymanagementclassification
	        	                    } else {
	        	                        System.err.println("部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1]);
	        	                        String errorMessage = "部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1];
	        	                        throw new IllegalArgumentException(errorMessage);
	        	                    }
	        	                } else {
	        	                    // 必要要素が欠けている場合
	        	                    System.err.println("部品の解析中にエラーが発生しました。部品データが不完全です");
	                                String errorMessage = "部品の解析中にエラーが発生しました。部品データが不完全です";
	                                throw new IllegalArgumentException(errorMessage);
	        	                }
	        	            } else {
	        	                // partsの長さ不足
	        	                System.err.println("部品の解析中にエラーが発生しました。parts の長さが足りません");
	                            String errorMessage = "部品の解析中にエラーが発生しました。parts の長さが足りません";
	                            throw new IllegalArgumentException(errorMessage);
	        	            }
	                    } else {
	                    	// 期待するパターンでない場合
	                        System.out.println("部品名のパターンに一致しませんでした: " + values[5]);
	                        String errorMessage = "部品名のパターンに一致しませんでした:";
	                        throw new IllegalArgumentException(errorMessage);
	                    }

	                    // CSV列から各フィールドを割り当て（Log種別の共通処理）
	                    String InventorymanagementinquiryValue3 = values[5]; // sql← csv: initialinventory（※コメントと変数名の整合は元コード準拠）
	                    String InventorymanagementinquiryValue4 = values[4]; // sql← csv: locationnumber
	                    String InventorymanagementinquiryValue5 = "-";       // sql← csv: locationnumberdestination（固定）
	                    int InventorymanagementinquiryValue6 = 0;           // sql← csv: initialinventory（固定）
	                    int InventorymanagementinquiryValue7 = Integer.parseInt(values[6]); // sql← csv: theoreticalinventory
	                    int InventorymanagementinquiryValue9 = 0;           // sql← csv: inventorystock
	                    int InventorymanagementinquiryValue10 = 0;          // sql← csv: investigationrequired
	                    int InventorymanagementinquiryValue11 = 0;          // sql← csv: decision
	                    String InventorymanagementinquiryValue12 = values[0] + " " + values[1]; // 備考に日時等を合成（ユニークキーにも使用）
	                    String InventorymanagementinquiryValue13 = values[2];  // Log種別をfileattributesに入れる
	                    float InventorymanagementinquiryValue14 = 0;         // rate固定

	                    // 1行をInventorymanagementinquiryに変換してリストへ
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
	                            InventorymanagementinquiryValue13,
	                            InventorymanagementinquiryValue14
	                    );

	                    inventoryManagementInquiryList.add(inventorymanagementinquiry);

	                } catch (NumberFormatException e) {
	                    System.err.println("行の解析中にエラーが発生しました: " + line + ". " + e.getMessage());
	                }
	            } else if ("MoveLog".equals(values[2])) {
	                // MoveLog専用の解析へ
	                processMoveLog(values, inventoryManagementInquiryList);
	            } else if ("InventoryLog".equals(values[2])) {
	                // InventoryLog専用の解析へ
	                processInventoryLog(values, inventoryManagementInquiryList);
	            } else {
	                // 未知のログ種別
	                System.out.println("Unknown log type in data: " + values[2]);
	            }
	        } catch (Exception e) {
	            // 解析中の全例外をログ出力して続行
	            System.err.println("データの解析中にエラーが発生しました: " + line + ". " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    return inventoryManagementInquiryList;
	}

	// -----------------------------
	// MoveLog行をInventorymanagementinquiryへ変換してリストへ追加（CSV解析）
	// -----------------------------
	private void processMoveLog(String[] values, List<Inventorymanagementinquiry> inventoryManagementInquiryList) {
	    try {
	        String InventorymanagementinquiryValue1 = null;
	        String InventorymanagementinquiryValue2 = null;
	        String InventorymanagementinquiryValue8 = null;

	        // MoveLogの部品名欄（values[6]）から情報抽出
	        if (values[6].matches(".*\\*.*\\*\\{.*\\}")) {
	            String[] parts = values[6].split("\\*|\\{|\\}");

	            if (parts.length >= 3) {
	                if (parts[0] != null && parts[1] != null && parts[3] != null) {
	                	InventorymanagementinquiryValue2 = parts[0];
	                	InventorymanagementinquiryValue1 = parts[3];

	                    if (parts[1].length() == 4) {
	                    	InventorymanagementinquiryValue8 = parts[1];
	                    } else {
	                        System.err.println("部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1]);
	                        String errorMessage = "部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1];
	                        throw new IllegalArgumentException(errorMessage);
	                    }
	                } else {
	                    System.err.println("部品の解析中にエラーが発生しました。部品データが不完全です");
                        String errorMessage = "部品の解析中にエラーが発生しました。部品データが不完全です";
                        throw new IllegalArgumentException(errorMessage);
	                }
	            } else {
	                System.err.println("部品の解析中にエラーが発生しました。parts の長さが足りません");
                    String errorMessage = "部品の解析中にエラーが発生しました。parts の長さが足りません";
                    throw new IllegalArgumentException(errorMessage);
	            }
            } else {
                System.out.println("部品名のパターンに一致しませんでした: " + values[5]);
                String errorMessage = "部品名のパターンに一致しませんでした:";
                throw new IllegalArgumentException(errorMessage);
            }

	        // MoveLogの列割当（移動元・移動先など）
	        String InventorymanagementinquiryValue3 = values[6];
	        String InventorymanagementinquiryValue4 = values[4];
	        String InventorymanagementinquiryValue5 = values[5]; // 移動先
	        int InventorymanagementinquiryValue6 = 0;
	        int InventorymanagementinquiryValue7 = Integer.parseInt(values[7]);
	        int InventorymanagementinquiryValue9 = 0;
	        int InventorymanagementinquiryValue10 = 0;
	        int InventorymanagementinquiryValue11 = 0;
	        String InventorymanagementinquiryValue12 = values[0] + " " + values[1];
	        String InventorymanagementinquiryValue13 = values[2];
	        float InventorymanagementinquiryValue14 = 0;

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
	            InventorymanagementinquiryValue13,
	            InventorymanagementinquiryValue14
	        );

	        inventoryManagementInquiryList.add(inventorymanagementinquiry);

        } catch (NumberFormatException e) {
            System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        }
	 }

	// -----------------------------
	// InventoryLog行をInventorymanagementinquiryへ変換（CSV解析）
	// -----------------------------
	private void processInventoryLog(String[] values, List<Inventorymanagementinquiry> inventoryManagementInquiryList) {
	    try {
	        String InventorymanagementinquiryValue1 = null;
	        String InventorymanagementinquiryValue2 = null;
	        String InventorymanagementinquiryValue8 = null;

	     // 部品名欄（values[5]）から情報抽出
            if (values[5].matches(".*\\*.*\\*\\{.*\\}")) {
                String[] parts = values[5].split("\\*|\\{|\\}");

                if (parts.length >= 3) {
                    if (parts[0] != null && parts[1] != null && parts[3] != null) {
                        InventorymanagementinquiryValue2 = parts[0];
                        InventorymanagementinquiryValue1 = parts[3];

	                    if (parts[1].length() == 4) {
	                    	InventorymanagementinquiryValue8 = parts[1];
	                    } else {
	                        System.err.println("部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1]);
	                        String errorMessage = "部品の解析中にエラーが発生しました。value8 の長さが4桁ではありません: " + parts[1];
	                        throw new IllegalArgumentException(errorMessage);
	                    }
	                } else {
	                    System.err.println("部品の解析中にエラーが発生しました。部品データが不完全です");
                        String errorMessage = "部品の解析中にエラーが発生しました。部品データが不完全です";
                        throw new IllegalArgumentException(errorMessage);
	                }
	            } else {
	                System.err.println("部品の解析中にエラーが発生しました。parts の長さが足りません");
                    String errorMessage = "部品の解析中にエラーが発生しました。parts の長さが足りません";
                    throw new IllegalArgumentException(errorMessage);
	            }
            } else {
                System.out.println("部品名のパターンに一致しませんでした: " + values[5]);
                String errorMessage = "部品名のパターンに一致しませんでした:";
                throw new IllegalArgumentException(errorMessage);
            }

	        // InventoryLogの列割当（棚卸系）
	        String InventorymanagementinquiryValue3 = values[5];
            String InventorymanagementinquiryValue4 = values[4];
            String InventorymanagementinquiryValue5 = "-";
            int InventorymanagementinquiryValue6 = 0;
            int InventorymanagementinquiryValue7 = 0;
            int InventorymanagementinquiryValue9 = Integer.parseInt(values[6]); // 棚卸数
            int InventorymanagementinquiryValue10 = 0;
            int InventorymanagementinquiryValue11 = Integer.parseInt(values[6]); // decisionに同値
            String InventorymanagementinquiryValue12 = values[0] + " " + values[1];
            String InventorymanagementinquiryValue13 = values[2];
            float InventorymanagementinquiryValue14 = 0;

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
	            InventorymanagementinquiryValue13,
	            InventorymanagementinquiryValue14
	        );

	        inventoryManagementInquiryList.add(inventorymanagementinquiry);

        } catch (NumberFormatException e) {
            System.err.println("行の解析中にエラーが発生しました: " + Arrays.toString(values) + ". " + e.getMessage());
        }
	}

	// -----------------------------
	// インポート結果（inventorymanagementinquiries）をDBに保存する
	//  - inventorys更新/追加
	//  - logsのstatus更新
	//  - トランザクションで一括処理
	// -----------------------------
	public void save(List<Inventorymanagementinquiry> inventorymanagementinquiries,List<InventoryLog> inventorylogList,  List<String> allLines) throws NamingException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        this.getConnection();

	        // remarks（日時文字列）をDateに変換してソートするためのフォーマット
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	        // InventoryLogをremarksキーで引けるMapにする（照合用）
	        Map<String, InventoryLog> inquiryMap = new HashMap<>();
	        for (InventoryLog inquiry : inventorylogList) {
	            inquiryMap.put(inquiry.getRemarks(), inquiry);
	        }

	        // remarks（日付）で昇順ソートして、処理順を時系列に揃える
	        Collections.sort(inventorymanagementinquiries, new Comparator<Inventorymanagementinquiry>() {
	            @Override
	            public int compare(Inventorymanagementinquiry o1, Inventorymanagementinquiry o2) {
	                try {
	                    Date date1 = sdf.parse(o1.getRemarks());
	                    Date date2 = sdf.parse(o2.getRemarks());
	                    return date1.compareTo(date2);
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                    return 0;
	                }
	            }
	        });

	        // トランザクション開始（まとめてcommit/rollback）
	        db.setAutoCommit(false);

	        for (Inventorymanagementinquiry inventorymanagementinquiry : inventorymanagementinquiries) {

	            // ※このフラグは現状常にtrue（ファイル検証等を入れる想定かも）
	            boolean validFiles = true;

	            // 同じremarksを持つlogを取得して、status更新等に使う
	            InventoryLog matchingInquiry = inquiryMap.get(inventorymanagementinquiry.getRemarks());

	            if (validFiles) {

	            	 // インポート重複（logsのremarks重複）チェック
	                boolean existingRecord1 = existsInDatabase1(inventorymanagementinquiry.getRemarks());
	                if (existingRecord1) {
	                    // 重複ならログにエラーを付けて次へ
	                    processRepetition(db, ps, matchingInquiry);
	                    continue;
	                }

	                // inventorys側で「QRCode情報+ロケ番」が既にあるかチェック
	                boolean existingRecord2 = existsInDatabase2(inventorymanagementinquiry.getIdentificationnumber(), inventorymanagementinquiry.getLocationnumber());

	                // Log種別に応じて在庫処理を分岐
	                if ("StorageLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // 入庫：理論在庫に加算
	                    processStorageLog(db, ps, inventorymanagementinquiry, matchingInquiry, existingRecord2);
	                } else if ("RetrievalLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // 出庫：理論在庫に減算
	                    processRetrievalLog(db, ps, inventorymanagementinquiry, matchingInquiry, existingRecord2);
	                } else if ("MoveLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // 移動：移動元から減算し、移動先へ加算/新規作成
	                    processMoveLog(db, ps, inventorymanagementinquiry,matchingInquiry, existingRecord2);
	                } else if ("InventoryLog".equals(inventorymanagementinquiry.getFileattributes())) {
	                    // 棚卸：実績在庫 inventorystock を反映し差異(investigationrequired)を計算
	                    processInventoryLog(db, ps, inventorymanagementinquiry,matchingInquiry, existingRecord2);
	                    // 棚卸サブ処理：特定条件のレコードに investigationrequired を設定
	                    processInventoryLogSub(db, ps, inventorymanagementinquiry,matchingInquiry, existingRecord2);
	                } else {
	                	System.out.println("ファイル名に'RetrievalLog'または'StorageLog'が含まれていないファイルがあります。処理を中止します。");
	                }
	            }
	        }

	        // すべて成功したらコミット
	        db.commit();

	    } catch (SQLException e) {
	        // 途中で失敗したらロールバック
	        try {
	            if (db != null && !db.getAutoCommit()) {
	                db.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        // リソースクローズ＆AutoCommit復元
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (db != null) {
	            try {
	                if (!db.getAutoCommit()) {
	                    db.setAutoCommit(true);
	                }
	                db.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	// -----------------------------
	// インポート重複時：logsにエラー表示を付与する
	// -----------------------------
	private void processRepetition(Connection db, PreparedStatement ps, InventoryLog inventoryLog) throws SQLException {
	    // inventory_statusが"-"のレコードに対してエラーを設定するルール
	    if ("-".equals(inventoryLog.getInventory_status())) {
	        inventoryLog.setStatus("Error: 同じデータが読み込まれています");

	        // logsテーブルへ反映
	        String updateQuery = "UPDATE logs SET status = ? WHERE remarks = ? AND inventory_status = ?";
	        ps = db.prepareStatement(updateQuery);
	        ps.setString(1, inventoryLog.getStatus());
	        ps.setString(2, inventoryLog.getRemarks());
	        ps.setString(3, inventoryLog.getInventory_status());

	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Updated " + rowsAffected + " rows with error status.");
	            return;
	        }
	    }
	}

	// -----------------------------
	// StorageLog（入庫）処理：
	//  - inventorys: 理論在庫を加算（存在しなければ新規作成）
	//  - logs: 在庫状態(inventory_status)とstatusを更新
	// -----------------------------
	private void processStorageLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog, boolean existingRecord2) throws SQLException {
		try {
		    // inventorys側：既存なら加算、なければINSERT
		    if (existingRecord2) {
		        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory + ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory());
		        ps.setString(2, inventorymanagementinquiry.getRemarks());
		        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber());
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
		        ps.executeUpdate();
		    } else {
		        ps = db.prepareStatement("INSERT INTO inventorys(identificationnumber, productname_productnumber, qrcodeinformation, "
		                + "locationnumber, locationnumberdestination, initialinventory, theoreticalinventory, inventorymanagementclassification, "
		                + "inventorystock, investigationrequired, decision, remarks, fileattributes) "
		                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
		        ps.setString(2, inventorymanagementinquiry.getProductname_productnumber());
		        ps.setString(3, inventorymanagementinquiry.getQrcodeinformation());
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
		        ps.setString(5, inventorymanagementinquiry.getLocationnumber()); // destinationに同値を入れる設計
		        ps.setInt(6, inventorymanagementinquiry.getInitialinventory());
		        ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory());
		        ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification());
		        ps.setInt(9, inventorymanagementinquiry.getInventorystock());
		        ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired());
		        ps.setInt(11, inventorymanagementinquiry.getDecision());
		        ps.setString(12, inventorymanagementinquiry.getRemarks());
		        ps.setString(13, inventorymanagementinquiry.getFileattributes());
		        ps.executeUpdate();
		    }

		    // logs側：inventorysから理論在庫を取得し、ステータスを更新
		    ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
		    ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
		    ps.setString(2, inventorymanagementinquiry.getLocationnumber());
		    ResultSet rs = ps.executeQuery();

		    int theoreticalInventory = 0;
		    boolean updatedStatus = true;

		    if (rs.next()) {
		        // 取得した理論在庫をlogsのinventory_statusへ入れる
		        theoreticalInventory = rs.getInt("theoreticalInventory");
		        inventoryLog.setInventory_status(String.valueOf(theoreticalInventory));
		    }
		    rs.close();

		    // importquantity と在庫が一致なら「新規作成」扱いにするルール
		    if (inventoryLog.getInventory_status().equals(String.valueOf(inventoryLog.getImportquantity()))) {
		        updatedStatus = false;
		    }

		    inventoryLog.setStatus(updatedStatus ? "+加算" : "新規作成");

		    ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
		    ps.setString(1, inventoryLog.getInventory_status());
		    ps.setString(2, inventoryLog.getStatus());
		    ps.setString(3, inventoryLog.getRemarks());
		    ps.executeUpdate();

		} finally {
		    if (ps != null) ps.close();
		}
	}

	// -----------------------------
	// RetrievalLog（出庫）処理：
	//  - inventorys: 理論在庫を減算
	//  - logs: 在庫状態とステータスを更新（存在しない場合はエラー）
	// -----------------------------
	private void processRetrievalLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog,  boolean existingRecord2) throws SQLException {
		try {
		    if (existingRecord2) {
		        // inventorys側：理論在庫を減算
		        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory - ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory());
		        ps.setString(2, inventorymanagementinquiry.getRemarks());
		        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber());
		        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
		        ps.executeUpdate();

		        // logs側：更新後の理論在庫を読み直してinventory_statusへ
		        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
		        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
		        ps.setString(2, inventorymanagementinquiry.getLocationnumber());
		        ResultSet rs = ps.executeQuery();

		        int theoreticalInventory = 0;
		        boolean updatedStatus = false;

		        if (rs.next()) {
		            theoreticalInventory = rs.getInt("theoreticalinventory");
		            inventoryLog.setInventory_status(String.valueOf(theoreticalInventory));
		            updatedStatus = true;
		        }
		        rs.close();

		        // 成功なら-減算、失敗ならエラー
		        inventoryLog.setStatus(updatedStatus ? "-減算" : "Error:減算先データがありません。");

		        ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
		        ps.setString(1, inventoryLog.getInventory_status());
		        ps.setString(2, inventoryLog.getStatus());
		        ps.setString(3, inventoryLog.getRemarks());
		        ps.executeUpdate();

		    } else {
		        // 減算対象が存在しない場合はlogsにエラーを設定（inventory_statusが"-"のもの）
		        if ("-".equals(inventoryLog.getInventory_status())) {
		            inventoryLog.setStatus("Error:減算先データがありません。");

		            String updateQuery = "UPDATE logs SET status = ? WHERE remarks = ? AND inventory_status = ?";
		            ps = db.prepareStatement(updateQuery);
		            ps.setString(1, inventoryLog.getStatus());
		            ps.setString(2, inventoryLog.getRemarks());
		            ps.setString(3, inventoryLog.getInventory_status());

		            int rowsAffected = ps.executeUpdate();
		            if (rowsAffected > 0) {
		                System.out.println("Updated " + rowsAffected + " rows with error status.");
		            } else {
		                System.out.println("No rows updated. Ensure the record exists and inventory_status is '-'.");
		            }
		        }
		    }

		} finally {
		    if (ps != null) {
		        try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
		    }
		}
	}

	// -----------------------------
	// MoveLog（ロケ移動）処理：
	//  - 移動元：減算
	//  - 移動先：存在すれば加算、なければ新規作成
	//  - logs：移動元/移動先の結果をまとめてstatus等に反映
	// -----------------------------
	private void processMoveLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog, boolean existingRecord2) throws SQLException {
	    ResultSet rs = null;
	    ResultSet rs2 = null;

	    try {
	    	if (existingRecord2) {
	        // 移動元の理論在庫を減算
	        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory - ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
	        ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory());
	        ps.setString(2, inventorymanagementinquiry.getRemarks());
	        ps.setString(3, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(4, inventorymanagementinquiry.getLocationnumber());
	        ps.executeUpdate();

	        // 移動先が既存か判定
	        boolean destinationExists = existsInDatabase2(inventorymanagementinquiry.getIdentificationnumber(), inventorymanagementinquiry.getLocationnumberdestination());
	        System.out.println("destinationExists:" + destinationExists);

	        if (destinationExists) {
	            // 移動先：既存なら理論在庫を加算
	            ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = theoreticalinventory + ?, remarks = ? WHERE identificationnumber = ? AND locationnumber = ?");
	            ps.setInt(1, inventorymanagementinquiry.getTheoreticalinventory());
	            ps.setString(2, inventorymanagementinquiry.getRemarks());
	            ps.setString(3, inventorymanagementinquiry.getIdentificationnumber());
	            ps.setString(4, inventorymanagementinquiry.getLocationnumberdestination());
	            ps.executeUpdate();
	        } else {
	            // 移動先：無ければ新規作成
	            ps = db.prepareStatement("INSERT INTO inventorys (identificationnumber, productname_productnumber, qrcodeinformation, locationnumber, locationnumberdestination, initialinventory, theoreticalinventory, inventorymanagementclassification, inventorystock, investigationrequired, decision, remarks, fileattributes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	            ps.setString(2, inventorymanagementinquiry.getProductname_productnumber());
	            ps.setString(3, inventorymanagementinquiry.getQrcodeinformation());
	            ps.setString(4, inventorymanagementinquiry.getLocationnumberdestination());
	            ps.setString(5, inventorymanagementinquiry.getLocationnumberdestination());
	            ps.setInt(6, inventorymanagementinquiry.getInitialinventory());
	            ps.setInt(7, inventorymanagementinquiry.getTheoreticalinventory());
	            ps.setString(8, inventorymanagementinquiry.getInventorymanagementclassification());
	            ps.setInt(9, inventorymanagementinquiry.getInventorystock());
	            ps.setInt(10, inventorymanagementinquiry.getInvestigationrequired());
	            ps.setInt(11, inventorymanagementinquiry.getDecision());
	            ps.setString(12, inventorymanagementinquiry.getRemarks());
	            ps.setString(13, inventorymanagementinquiry.getFileattributes());
	            ps.executeUpdate();
	        }

	        // logs表示用：移動元/移動先それぞれの理論在庫を取得
	        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumber = ?");
	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(2, inventorymanagementinquiry.getLocationnumber());
	        rs = ps.executeQuery();

	        ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE identificationnumber = ? AND locationnumberdestination = ?");
	        ps.setString(1, inventorymanagementinquiry.getIdentificationnumber());
	        ps.setString(2, inventorymanagementinquiry.getLocationnumberdestination());
	        rs2 = ps.executeQuery();

	        int theoreticalInventory = 0;
	        int theoreticalInventory2 = 0;
	        boolean updatedStatus = false;
	        boolean updatedStatus2 = false;

	        if (rs.next()) {
	            theoreticalInventory = rs.getInt("theoreticalinventory");
	            inventoryLog.setQuantity(theoreticalInventory);
	            updatedStatus = true;
	        }

	        if (rs2.next()) {
	            theoreticalInventory2 = rs2.getInt("theoreticalinventory");
	            inventoryLog.setInventory(theoreticalInventory2);
	            updatedStatus2 = true;
	        }

	        System.out.println("updatedStatus:" + updatedStatus);
	        System.out.println("updatedStatus2:" + updatedStatus2);

	        // ここからlogsの表示を条件分岐で作る（移動元/先が成功したかで文言が変わる）
	        if (updatedStatus && updatedStatus2) {
	            inventoryLog.setStatus("移動");
	            inventoryLog.setInventory_status("[移動元]:");
	            inventoryLog.setStatus_m1("-減算");
	            inventoryLog.setStatus2("[移動先]:");
	            inventoryLog.setStatus_m2("+加算");

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
	            ps.setString(8, inventoryLog.getRemarks());
	            ps.executeUpdate();

	        } else if (updatedStatus && !updatedStatus2) {
	        	// 移動先が無く新規作成扱い
	        	inventoryLog.setStatus("移動");
	            inventoryLog.setInventory_status("[移動元]:");
	            inventoryLog.setStatus_m1("-減算");
	            inventoryLog.setStatus2("[移動先]:");
	            inventoryLog.setStatus_m2("新規作成");

	            int importquantity = inventoryLog.getImportquantity();
	            inventoryLog.setInventory(importquantity);

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
	            ps.setString(8, inventoryLog.getRemarks());
	            ps.executeUpdate();

	        } else if (!updatedStatus && updatedStatus2) {
	        	// 移動元が存在しない＝減算先なし
	        	inventoryLog.setStatus("Error:減算先データがありません。");
	        	inventoryLog.setImportquantity(0);
	        	inventoryLog.setInventory_status("0");
	        	inventoryLog.setLocationnumber_m1("-");
	        	inventoryLog.setLocationnumber_m2("-");
	        	inventoryLog.setRemarks("-");

	        	String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	        	    + "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";
	        	ps = db.prepareStatement(sql);
	        	ps.setString(1, inventoryLog.getStatus());
	        	ps.setInt(2, inventoryLog.getImportquantity());
	        	ps.setString(3, inventoryLog.getInventory_status());
	        	ps.setString(4, inventoryLog.getLocationnumber_m1());
	        	ps.setString(5, inventoryLog.getLocationnumber_m2());
	        	ps.setString(6, inventoryLog.getRemarks());
	        	ps.setInt(7, inventoryLog.getQuantity());
	        	ps.setInt(8, inventoryLog.getInventory());
	        	ps.setString(9, inventoryLog.getRemarks());
	            ps.executeUpdate();

	        } else {
	            // その他：処理不能
	            inventoryLog.setStatus("Error:データ処理が出来ませんでした。");
	            inventoryLog.setImportquantity(0);
	            inventoryLog.setInventory_status("0");
	            inventoryLog.setLocationnumber_m1("-");
	            inventoryLog.setLocationnumber_m2("-");
	            inventoryLog.setRemarks("-");

	            String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	            		+ "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";
	            ps = db.prepareStatement(sql);
	            ps.setString(1, inventoryLog.getStatus());
	            ps.setInt(2, inventoryLog.getImportquantity());
	            ps.setString(3, inventoryLog.getInventory_status());
	            ps.setString(4, inventoryLog.getLocationnumber_m1());
	            ps.setString(5, inventoryLog.getLocationnumber_m2());
	            ps.setString(6, inventoryLog.getRemarks());
	            ps.setInt(7, inventoryLog.getQuantity());
	            ps.setInt(8, inventoryLog.getInventory());
	            ps.setString(9, inventoryLog.getRemarks());
	            ps.executeUpdate();
	        }

		    // 移動先inventoryとimportquantityが一致なら「新規作成」へ上書きするルール
		    if (String.valueOf(inventoryLog.getInventory()).equals(String.valueOf(inventoryLog.getImportquantity()))) {
		    	 inventoryLog.setStatus_m2("新規作成");
		    }

		    // logsへ反映
		    ps = db.prepareStatement("UPDATE logs SET status_m2 = ? WHERE remarks = ?");
		    ps.setString(1, inventoryLog.getStatus_m2());
		    ps.setString(2, inventoryLog.getRemarks());
		    ps.executeUpdate();

	    	 }

	    	// 移動元が存在しない場合のエラー処理（logs更新）
	    	if (!existingRecord2) {
	    	    inventoryLog.setStatus("Error:減算先データがありません。");
	    	    inventoryLog.setImportquantity(0);
	    	    inventoryLog.setInventory_status("0");
	    	    inventoryLog.setLocationnumber_m1("-");
	    	    inventoryLog.setLocationnumber_m2("-");

	    	    String originalRemarks = inventoryLog.getRemarks();
	    	    inventoryLog.setRemarks("-");

	    	    String sql = "UPDATE logs SET status = ?, importquantity = ?, inventory_status = ?, locationnumber_m1 = ?, "
	    	        + "locationnumber_m2 = ?, remarks = ?, quantity = ?, inventory = ? WHERE remarks = ?";

	    	    ps = db.prepareStatement(sql);
	    	    ps.setString(1, inventoryLog.getStatus());
	    	    ps.setInt(2, inventoryLog.getImportquantity());
	    	    ps.setString(3, inventoryLog.getInventory_status());
	    	    ps.setString(4, inventoryLog.getLocationnumber_m1());
	    	    ps.setString(5, inventoryLog.getLocationnumber_m2());
	    	    ps.setString(6, inventoryLog.getRemarks());
	    	    ps.setInt(7, inventoryLog.getQuantity());
	    	    ps.setInt(8, inventoryLog.getInventory());
	    	    ps.setString(9, originalRemarks);
	    	    ps.executeUpdate();
	    	}

	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (rs2 != null) {
	            try { rs2.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}

	// -----------------------------
	// InventoryLog（棚卸）処理（DB更新側）
	//  - existingRecord2: 同一QRCode+ロケがあるか
	//  - inventorystock(実績)を反映し decision を揃え、
	//    theoreticalinventoryとの差を investigationrequired に入れる
	//  - logsには status="棚卸" を付与
	// -----------------------------
	private void processInventoryLog(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog, boolean existingRecord2) throws SQLException {
	    try {
	        if (existingRecord2) {
	            // 既存レコード更新

	            // 現在の棚卸実績(inventorystock)を取得
	            ps = db.prepareStatement("SELECT inventorystock FROM inventorys WHERE locationnumber = ? AND identificationnumber = ?");
	            ps.setString(1, inventorymanagementinquiry.getLocationnumber());
	            ps.setString(2, inventorymanagementinquiry.getIdentificationnumber());

	            ResultSet rs = ps.executeQuery();
	            int currentStock = 0;

	            if (rs.next()) {
	                currentStock = rs.getInt("inventorystock");
	            }
	            rs.close();

	            // 棚卸数を加算して最終在庫を作る
	            int finalStock = currentStock + inventorymanagementinquiry.getInventorystock();
	            inventorymanagementinquiry.setInventorystock(finalStock);

	            // decisionは棚卸在庫と一致させる
	            inventorymanagementinquiry.setDecision(finalStock);

	            // inventorys更新（inventorystock/decision/remarks）
	            ps = db.prepareStatement("UPDATE inventorys SET inventorystock = ?, decision = ?, remarks = ? WHERE locationnumber = ?  AND identificationnumber = ?");
	            ps.setInt(1, inventorymanagementinquiry.getInventorystock());
	            ps.setInt(2, inventorymanagementinquiry.getDecision());
	            ps.setString(3, inventorymanagementinquiry.getRemarks());
	            ps.setString(4, inventorymanagementinquiry.getLocationnumber());
	            ps.setString(5, inventorymanagementinquiry.getIdentificationnumber());
	            ps.executeUpdate();

	            // theoreticalinventory取得
	            ps = db.prepareStatement("SELECT theoreticalinventory FROM inventorys WHERE locationnumber = ? AND identificationnumber = ?");
	            ps.setString(1, inventorymanagementinquiry.getLocationnumber());
	            ps.setString(2, inventorymanagementinquiry.getIdentificationnumber());
	            rs = ps.executeQuery();

	            int theoreticalInventory = 0;
	            if (rs.next()) {
	                theoreticalInventory = rs.getInt("theoreticalinventory");
	            }
	            rs.close();

	            // 差異（理論-実績）を要調査に設定
	            inventorymanagementinquiry.setInvestigationrequired(theoreticalInventory - inventorymanagementinquiry.getInventorystock());

	            // investigationrequired更新
	            ps = db.prepareStatement("UPDATE inventorys SET investigationrequired = ? WHERE locationnumber = ? AND identificationnumber = ?");
	            ps.setInt(1, inventorymanagementinquiry.getInvestigationrequired());
	            ps.setString(2, inventorymanagementinquiry.getLocationnumber());
	            ps.setString(3, inventorymanagementinquiry.getIdentificationnumber());
	            ps.executeUpdate();

	        } else {
	            // 新規レコード挿入（棚卸のみ）

	            // decisionは棚卸在庫と同値、investigationrequiredも同値で開始
	            inventorymanagementinquiry.setDecision(inventorymanagementinquiry.getInventorystock());
	            inventorymanagementinquiry.setInvestigationrequired(inventorymanagementinquiry.getInventorystock());

	            ps = db.prepareStatement(
	                "INSERT INTO inventorys(identificationnumber, productname_productnumber, qrcodeinformation, locationnumber, locationnumberdestination, " +
	                "initialinventory, theoreticalinventory, inventorymanagementclassification, inventorystock, investigationrequired, decision, remarks, fileattributes) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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

	            ps.executeUpdate();
	        }

	        // logsへ「棚卸」ステータスを付与
        	inventoryLog.setStatus("棚卸");

        	ps = db.prepareStatement("UPDATE logs SET inventory_status = ?, status = ? WHERE remarks = ?");
        	ps.setString(1, inventoryLog.getInventory_status());
        	ps.setString(2, inventoryLog.getStatus());
        	ps.setString(3, inventoryLog.getRemarks());
        	ps.executeUpdate();

	    } finally {
	        if (ps != null) ps.close();
	    }
	}

	// -----------------------------
	// 棚卸サブ処理：
	//  - theoreticalinventory != 0 かつ inventorystock == 0 の行を対象に
	//    investigationrequiredへtheoreticalinventoryをコピーする
	// -----------------------------
	private void processInventoryLogSub(Connection db, PreparedStatement ps, Inventorymanagementinquiry inventorymanagementinquiry, InventoryLog inventoryLog,  boolean existingRecord2) throws SQLException {
		try {
			if (existingRecord2) {

				// 更新対象を一旦メモリへ取り込み（同時更新でResultSetを壊さないため）
				List<Map<String, Integer>> inventoryData = new ArrayList<>();

				ps = db.prepareStatement("SELECT id, theoreticalinventory FROM inventorys WHERE theoreticalinventory != 0 AND inventorystock = 0");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
				    Map<String, Integer> row = new HashMap<>();
				    row.put("id", rs.getInt("id"));
				    row.put("theoreticalinventory", rs.getInt("theoreticalinventory"));
				    inventoryData.add(row);
				}

				rs.close();
				ps.close();

				// 取得した対象を順番にUPDATE
				for (Map<String, Integer> row : inventoryData) {
				    try (PreparedStatement updatePs = db.prepareStatement("UPDATE inventorys SET investigationrequired = ? WHERE id = ?")) {
				        updatePs.setInt(1, row.get("theoreticalinventory"));
				        updatePs.setInt(2, row.get("id"));
				        updatePs.executeUpdate();
				    }
				}
		    }

		} finally {
		    if (ps != null) ps.close();
		}
	}

    // -----------------------------
    // インポート重複防止①：
    // logsのremarksが同じものが複数あるかチェック（>1）
    // -----------------------------
	private boolean existsInDatabase1(String remarks) throws SQLException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM logs WHERE remarks = ?");
	        ps.setString(1, remarks);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 1; // 自身を含む2件目以降がある＝重複
	        }
	        return false;
	    } finally {
	        if (rs != null) { rs.close(); }
	        if (ps != null) { ps.close(); }
	    }
	}

    // -----------------------------
    // インポート重複防止②：
    // inventorysのQRCode情報+ロケ番が既に存在するか
    // -----------------------------
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
	        if (rs != null) { rs.close(); }
	        if (ps != null) { ps.close(); }
	    }
	}

	// -----------------------------
	// 棚卸確定処理（inventorys全体を整理する）
	//  - 棚卸実績が全部0なら中止
	//  - decisionがNULLがあれば中止
	//  - decision=0削除、initial/theoreticalの再セット等
	// -----------------------------
	public String InventoryProcessingAll() throws NamingException {
	    String message = "";

	    try {
	        this.getConnection();

	        // inventorystockが全て0なら棚卸処理を中止
	        try (PreparedStatement ps1 = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE inventorystock != 0");
	             ResultSet rs1 = ps1.executeQuery()) {

	            if (rs1.next()) {
	                int count = rs1.getInt(1);
	                if (count == 0) {
	                    System.out.println("在庫がすべて 0 なので処理を中断します。");
	                    return "棚卸処理は中止されました。ログアウト後、棚卸実績をインポートしてください。";
	                }
	            }
	        }

	        // decisionがNULLのレコードがあると中止（確定漏れ対策）
	        int count = 0;
	        try (PreparedStatement ps2 = db.prepareStatement("SELECT COUNT(*) FROM inventorys WHERE decision IS NULL");
	             ResultSet rs2 = ps2.executeQuery()) {

	            if (rs2.next()) {
	                count = rs2.getInt(1);
	            }
	        }

	        System.out.println("カウント結果: " + count);

	        if (count > 0) {
	            return "棚卸処理は中止されました。確定項目が null のレコードが存在します。";
	        }

	        // decision=0は削除
	        ps = db.prepareStatement("DELETE FROM inventorys WHERE decision = 0");
	        ps.executeUpdate();

	        // decisionをinitialinventoryへ反映（SQLは元コードのまま）
	        ps = db.prepareStatement("UPDATE inventorys SET initialinventory = decision WHERE decision");
	        ps.executeUpdate();

	        // initialinventoryをtheoreticalinventoryへ反映（SQLは元コードのまま）
	        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = initialinventory WHERE initialinventory");
	        ps.executeUpdate();

	        // その他フィールドをリセットして棚卸確定状態へ
	        ps = db.prepareStatement("UPDATE inventorys SET theoreticalinventory = initialinventory, "
	                + "inventorystock = 0, "
	                + "investigationrequired = 0, "
	                + "decision = 0 WHERE decision <> 0");
	        ps.executeUpdate();

	        // initial/theoreticalが両方0は削除
	        ps = db.prepareStatement("DELETE FROM inventorys WHERE initialinventory = 0 AND theoreticalinventory = 0");
	        ps.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "エラーが発生しました。";

	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (db != null) db.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return message;
	}

	// -----------------------------
	// inventorysの指定id 1件をCSVとして書き出す
	//  - BufferedWriterでバッファリングし高速化
	// -----------------------------
	public void downloadOneToCsv(int id, PrintWriter csvWriter) throws NamingException, IOException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    BufferedWriter bufferedWriter = new BufferedWriter(csvWriter, 8192); // 8KBバッファ

	    try {
	        this.getConnection();

	        ps = db.prepareStatement("SELECT * FROM inventorys WHERE id=?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();

	        int rowCount = 0;
	        while (rs.next()) {
	            // CSVの1行を書き出し（カンマ区切り）
	            bufferedWriter.write(escapeCsv(rs.getString("identificationnumber")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("productname_productnumber")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("qrcodeinformation")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("locationnumber")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("locationnumberdestination")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("initialinventory")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("theoreticalinventory")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("inventorymanagementclassification")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("inventorystock")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("investigationrequired")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("decision")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("remarks")) + ",");
	            bufferedWriter.write(escapeCsv(rs.getString("rate")));
	            bufferedWriter.write("\n");

	            rowCount++;
	            if (rowCount % 100 == 0) {
	                bufferedWriter.flush();
	            }
	        }
	        bufferedWriter.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (db != null) db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // -----------------------------
    // CSVエスケープ：
    //  - カンマ/ダブルクォート/改行を含む場合はダブルクォートで囲む
    // -----------------------------
    private String escapeCsv(String data) {
        if (data == null) return "";
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            data = data.replace("\"", "\"\"");
            return "\"" + data + "\"";
        }
        return data;
	}

    // -----------------------------
    // masterinventorysからrateを取得してinventorysへ反映する
    //  - identificationnumberで突合してrateを更新
    // -----------------------------
    public boolean updateRate() throws NamingException {
        try {
            this.getConnection();

            // masterinventorysのidentificationnumberとrateを全件取得
            String sql = "SELECT identificationnumber, rate FROM masterinventorys";
            ps = db.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String identificationNumber = rs.getString("identificationnumber");
                float rate = rs.getFloat("rate");

                System.out.println("identificationNumber: " + identificationNumber + ", rate: " + rate);

                // inventorys側のrateを更新
                String updateSql = "UPDATE inventorys SET rate = ? WHERE identificationnumber = ?";
                try (PreparedStatement updatePs = db.prepareStatement(updateSql)) {
                    updatePs.setFloat(1, rate);
                    updatePs.setString(2, identificationNumber);

                    int result = updatePs.executeUpdate();
                    if (result != 1) {
                        System.out.println("更新失敗: " + identificationNumber);
                        continue;
                    }
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (db != null) db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
