package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import dao.InventoryLogDAO;
import dao.InventorymanagementinquiryDAO;
import model.InventoryLog;
import model.Inventorymanagementinquiry;
import model.User;

/**
 * Servlet implementation class Masterinventory
 */
@WebServlet("/InventorymanagementinquiryList")
@MultipartConfig
public class InventorymanagementinquiryList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        InventorymanagementinquiryDAO inventorymanagementinquiry_dao=new InventorymanagementinquiryDAO();
        InventoryLogDAO inventoryLog_dao=new InventoryLogDAO();
        
		String action=request.getParameter("action");	
		String searchKeyword = request.getParameter("searchKeyword"); // 検索キーワードを取得;

		// 検索処理
		List<Inventorymanagementinquiry> list;
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			list = inventorymanagementinquiry_dao.searchOne(searchKeyword); // DAOで検索を実行
		} else {
			list = inventorymanagementinquiry_dao.findAll(); // 検索キーワードが指定されていない場合は全件取得
		}
		request.setAttribute("list", list);
		
	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");

	    if ("import".equals(action)) {
	        List<Part> fileParts = request.getParts().stream()
	            .filter(part -> "csvFiles".equals(part.getName()) && part.getSize() > 0)
	            .limit(3)
	            .collect(Collectors.toList());

	        if (!fileParts.isEmpty()) {
	            List<Inventorymanagementinquiry> inventorymanagementinquiry_importedList = new ArrayList<>();
	            List<InventoryLog> inventoryLog_importedList = new ArrayList<>();
	            List<String> allLines = new ArrayList<>(); // allLines をここで初期化


	            boolean validFiles = true;

	            for (Part filePart : fileParts) {
	                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	                if (!fileName.contains("RetrievalLog") && !fileName.contains("MoveLog") && !fileName.contains("StorageLog")&& !fileName.contains("InventoryLog")) {
	                    validFiles = false;
	                    break;
	                }
	            }
	            
	            if (validFiles) {
	                try {
	                	
	            	    // セッションから処理済みファイルリストを取得
	            	    @SuppressWarnings("unchecked")
	            	    Set<String> processedFiles = (Set<String>) session.getAttribute("processedFiles");
	            	    if (processedFiles == null) {
	            	        processedFiles = new HashSet<>();
	            	        session.setAttribute("processedFiles", processedFiles);
	            	    }
                      
	                    if (fileParts.size() >= 1) {
	                        for (Part filePart : fileParts) {
	                            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	                            
	                            // ファイルがまだ処理されていない場合に処理を実行
	                            // 既に処理済みのファイルならスキップ
	                            if (processedFiles.contains(fileName)) {
	                                continue;
	                            }
	                            if (!processedFiles.contains(fileName)) {
	                                System.out.println("Processing file: " + fileName);
	                                processedFiles.add(fileName); // 処理済みファイルとして追加
	                                
	                                try (BufferedReader br = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
	                                    String line;
	                                    while ((line = br.readLine()) != null) {
	                                        allLines.add(line);
	                                    }
	                                } catch (IOException e) {
	                                    e.printStackTrace();
	                                }
	                            }
	                        }
	                        
	                        // ファイルからInventoryLogリストをインポート
	                        List<InventoryLog> fileImportedList2 = inventoryLog_dao.importOne(allLines);
	                        inventoryLog_importedList.addAll(fileImportedList2);
	                        // ファイルからInventorymanagementinquiryリストをインポート
	                        List<Inventorymanagementinquiry> fileImportedList1 = inventorymanagementinquiry_dao.importOne(allLines);
	                        inventorymanagementinquiry_importedList.addAll(fileImportedList1);

		                    // Inventorymanagementinquiryオブジェクトをデータベースに保存
		                    // allLines を渡すように変更
	                        inventoryLog_dao.save(inventoryLog_importedList, inventorymanagementinquiry_importedList, allLines);
		                    inventorymanagementinquiry_dao.save(inventorymanagementinquiry_importedList,inventoryLog_importedList, allLines);
		                    
	                    }

	                    request.setAttribute("inventorymanagementinquiry_importedList", inventorymanagementinquiry_importedList);
	                    request.setAttribute("inventoryLog_importedList", inventoryLog_importedList);
	                    request.setAttribute("msg", "CSVファイルをインポートしました。");
	                    
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    request.setAttribute("err", "データベース保存中にエラーが発生しました。");
	                }
	            } else {
	                request.setAttribute("err", "ファイル名に'RetrievalLog'、'MoveLog'、'StorageLog'、'InventoryLog'が含まれていないファイルがあります。");
	            }
	        } else {
	        	request.setAttribute("err", "ファイルが選択されていません。");
	        }
	    }

	    if(action != null && action.equals("delete")){
	        inventorymanagementinquiry_dao.deleteOne(Integer.parseInt(request.getParameter("id")));
	        request.setAttribute("msg", "1件削除しました。");
	    }

        // ダウンロード処理の追加
        if ("download".equals(action)) {
            try {
            	inventorymanagementinquiry_dao.downloadAll();
                request.setAttribute("msg", "CSVファイルをダウンロードしました。");
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("err", "CSVファイルの生成中にエラーが発生しました。");
            }
        }
        
     // InventoryProcessing が実行済みかどうかを確認
        Boolean isProcessingDone = (Boolean) session.getAttribute("processingDone");
        Integer count = (Integer) session.getAttribute("count"); // セッションからカウント数を取得

        if (count == null) {
            count = 0; // 初期値として0を設定
        }
        if (isProcessingDone == null || !isProcessingDone) { // 処理が未完了またはフラグが設定されていない場合
            if ("InventoryProcessing".equals(action)) { // アクションが "InventoryProcessing" の場合
                // InventoryProcessingAll メソッドからメッセージを取得
                String message;
                try {
                    message = inventorymanagementinquiry_dao.InventoryProcessingAll();
                } catch (NamingException e) {
                    e.printStackTrace();
                    message = "エラーが発生しました。";
                }
                // メッセージが存在する場合は適切なリクエスト属性にセット
                if (message.isEmpty()) {
                    session.setAttribute("processingDone", true); // フラグを設定
                    session.setAttribute("count", count + 1); // カウントをインクリメント
                } else {
                    request.setAttribute("err", message); // エラーメッセージを "err" 属性にセット
                    session.setAttribute("processingDone", false); // フラグを設定
                }
            }
        } else {
            if (count <= 1) { // カウントが1以下の場合
                request.setAttribute("msg", "棚卸処理を正常に完了しました。");
            } else { // カウントがそれ以上の場合
                request.setAttribute("msg", "棚卸処理はすでに完了しています。");
            }
            session.setAttribute("count", count + 1); // カウントをインクリメント
        }

	    request.setAttribute("list", list);
	    // フォワード
	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/inventorymanagementinquirylist.jsp");
	    rd.forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
	}
}