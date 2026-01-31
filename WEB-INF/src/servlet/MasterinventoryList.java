package servlet;

import java.io.IOException;
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
import javax.servlet.http.Part;

import dao.MasterinventoryDAO;
import model.Masterinventory;
import model.User;

/**
 * Servlet implementation class Masterinventory
 */
@WebServlet("/MasterinventoryList")
@MultipartConfig
public class MasterinventoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
		MasterinventoryDAO dao=new MasterinventoryDAO();
		String transition=request.getParameter("transition");
		String action=request.getParameter("action");	
		String searchKeyword = request.getParameter("searchKeyword"); // 検索キーワードを取得

		// 検索処理
		List<Masterinventory> list;
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			list = dao.searchOne(searchKeyword); // DAOで検索を実行
		} else {
			list = dao.findAll(); // 検索キーワードが指定されていない場合は全件取得
		}
		request.setAttribute("list", list);

	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
		
        if ("MasterinventoryForm".equals(transition)) {
        	// フォワード
    	    RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/masterinventoryform.jsp");
    	    rd.forward(request, response);
    	    return;
        }
		
		if(action != null && action.equals("delete")){
			dao.deleteOne(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("msg", "1件削除しました。");
		}
		
		if ("import".equals(action)) {
		    synchronized (this) { // 同期化してリクエストを1回だけ処理する
		        if (session.getAttribute("importProcessed") == null) { // セッション内でインポートが未処理かどうかを確認
		            // CSVファイルのアップロード処理
		            Part filePart = request.getPart("csvFile"); // リクエストからCSVファイルを取得
		            if (filePart != null && filePart.getSize() > 0) { // ファイルがアップロードされているか確認
		                List<Masterinventory> importedList = null;
		                try {
		                    importedList = dao.importOne(filePart); // CSVファイルをインポートしてリストに変換
		                    session.setAttribute("importProcessed", true); // インポート処理済みフラグをセッションに設定
		                } catch (NamingException e1) {
		                    e1.printStackTrace(); // インポート処理中にエラーが発生した場合の処理
		                }
		                for (Masterinventory masterinventory : importedList) { // インポートされた各データをデータベースに保存
		                    try {
		                        dao.save(masterinventory); // データベースに保存
		                    } catch (Exception e) {
		                        e.printStackTrace(); // 保存処理中にエラーが発生した場合の処理
		                        request.setAttribute("err", "データベース保存中にエラーが発生しました。"); // エラーメッセージをリクエストに設定
		                    }
		                }
		                request.setAttribute("importedList", importedList); // インポートされたリストをリクエストに設定
		                request.setAttribute("msg", "CSVファイルをインポートしました。"); // 成功メッセージをリクエストに設定
		            } else {
		                request.setAttribute("err", "ファイルが選択されていません。"); // ファイルが選択されていない場合のエラーメッセージを設定
		            }
		        }
		    }
		}

		
        // ダウンロード処理の追加
        if ("download".equals(action)) {
            try {
            	dao.downloadAll();
                request.setAttribute("msg", "CSVファイルをダウンロードしました。");
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("err", "CSVファイルの生成中にエラーが発生しました。");
            }
        }

        request.setAttribute("list", list);
        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/masterinventorylist.jsp");
        rd.forward(request, response);
    }
	

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
	}
}