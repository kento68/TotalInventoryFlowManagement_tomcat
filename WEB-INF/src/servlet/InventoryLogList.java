package servlet;

import java.io.IOException;
import java.util.ArrayList;
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

            // ダウンロード処理の追加
            if ("download".equals(action)) {
                synchronized (this) { // 同期化してリクエストを1回だけ処理する
                    if (session.getAttribute("downloadProcessed") == null) {
                        try {
                            dao.downloadAll();
                            session.setAttribute("downloadProcessed", true);
                            request.setAttribute("msg", "CSVファイルをダウンロードしました。");
                        } catch (IOException e) {
                            e.printStackTrace();
                            request.setAttribute("err", "CSVファイルの生成中にエラーが発生しました。");
                        }
                    }
                }
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