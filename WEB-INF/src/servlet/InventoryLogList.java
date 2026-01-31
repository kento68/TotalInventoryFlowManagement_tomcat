package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.InventoryLogDAO;
import model.InventoryLog;
import model.User;

/**
 * Servlet implementation class Masterinventory
 */
@WebServlet("/InventoryLogList")
@MultipartConfig
public class InventoryLogList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<InventoryLog> logList; // インベントリログのリスト

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        InventoryLogDAO dao=new InventoryLogDAO();
		String transition=request.getParameter("transition");
		String action=request.getParameter("action");	
		String searchKeyword = request.getParameter("searchKeyword"); // 検索キーワードを取得

        try {
            // 検索キーワードに基づいて検索を実行
            List<InventoryLog> list;
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                // 検索キーワードが指定されている場合はDAOで検索を実行
                list = dao.searchOne(searchKeyword);
            } else {
                // 検索キーワードが指定されていない場合は全件取得
                list = dao.findAll();
            }

            // 検索結果または全件のリストをリクエスト属性に設定
            request.setAttribute("list", list);

            // セッションスコープからユーザー情報を取得
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");

            if (action != null && action.equals("delete")) {
                dao.deleteOne(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("msg", "1件削除しました。");
            }
            
        } catch (NamingException e) {
            e.printStackTrace(); // エラーをコンソールに出力（デバッグ目的）
            request.setAttribute("errorMessage", "データベース接続時にエラーが発生しました。管理者に連絡してください。");
        } catch (Exception e) {
            e.printStackTrace(); // エラーをコンソールに出力（デバッグ目的）
            request.setAttribute("errorMessage", "予期しないエラーが発生しました。管理者に連絡してください。");
        }

        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/inventoryloglist.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	InventoryLogDAO dao=new InventoryLogDAO();
	    String action = request.getParameter("action");
	    String[] selectedIds = request.getParameterValues("selectedIds"); // 選択されたIDを取得
	    
	    System.out.println(selectedIds);

	    // 最初にアクションが "downloadSelected" かをチェック
	    if ("downloadSelected".equals(action)) {
	        if (selectedIds != null && selectedIds.length > 0) {
	            PrintWriter csvWriter = null;
	            try {
	                // 現在の日時を取得
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	                String currentDateAndTime = sdf.format(new Date());

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
	    	        
	                csvWriter = new PrintWriter(filePath, "Shift-JIS");

	    	        // ヘッダー行
	    	        csvWriter.append("ID,記録日時,記録時間,機器名,取込日付,担当者No.,ロケ番,ロケ番_移動先,品名,取込個数,状態,"
	    	        		+ "在庫/状態,ロケ番_移動元,状態_-減算,個数_-減算,状態_移動先,ロケ番_移動先,状態_+加算,在庫_+加算,保管先,登録No.,在庫管理区分");
	    	        csvWriter.append("\n");

	                // 選択されたIDごとにデータをCSVに追加
	                for (String id : selectedIds) {
	                	dao.downloadOneToCsv(Integer.parseInt(id), csvWriter);
	                }
	                csvWriter.flush();
	                request.setAttribute("msg", selectedIds.length + "件のデータをCSVに出力しました。");
	            } catch (Exception e) {
	                e.printStackTrace();
	                request.setAttribute("err", "CSV出力中にエラーが発生しました。");
	            } finally {
	                if (csvWriter != null) csvWriter.close();
	            }
	        } else {
	            if (selectedIds == null) {
	                request.setAttribute("err", "選択されたデータがありません。(null)");
	            } else {
	                request.setAttribute("err", "選択されたデータがありません。(空配列)");
	            }
	        }
	    }
	    
	    System.out.println(selectedIds);
	    // 最初にアクションが "deleteSelected" かをチェック
	    if ("deleteSelected".equals(action)) {
	    	
	        if (selectedIds != null && selectedIds.length > 0) {
	            for (String id : selectedIds) {
	            	dao.deleteOne(Integer.parseInt(id)); // IDに基づいて削除
	            }
	            request.setAttribute("msg", selectedIds.length + "件削除しました。");
	        }
	        
	        // 最新のリストを再取得してJSPに渡す
	        List<InventoryLog> list = dao.findAll();
	        request.setAttribute("list", list);
	        
	        // 完了メッセージを設定
	        request.setAttribute("msg", selectedIds.length + "件削除しました。");
	      }
	    
        doGet(request, response);
	}
    // インベントリログをリストに追加するメソッド
    public static void add(InventoryLog log) {
        try {
			if (logList == null) {
			    logList = new ArrayList<>();
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        logList.add(log);
    }
}