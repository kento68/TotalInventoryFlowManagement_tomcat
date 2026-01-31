package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalArgumentException {
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
	    
	    if(action != null && action.equals("delete")){
	        inventorymanagementinquiry_dao.deleteOne(Integer.parseInt(request.getParameter("id")));
	        request.setAttribute("msg", "1件削除しました。");
	    }

	    // 単価設定手動更新
	    if (action != null && action.equals("rate")) {
	    	try {
	    	    inventorymanagementinquiry_dao.updateRate(); // 引数なしで呼び出し
	    	    request.setAttribute("msg", "単価更新しました。");
	    	} catch (NamingException e) {
	    	    e.printStackTrace();
	    	}
	    }
	    
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
	                if (!fileName.contains("RetrievalLog") && !fileName.contains("MoveLog") && !fileName.contains("StorageLog") && !fileName.contains("InventoryLog")) {
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
	                            if (processedFiles.contains(fileName)) {
	                                continue;
	                            }

	                            System.out.println("Processing file: " + fileName);
	                            processedFiles.add(fileName); // 処理済みファイルとして追加

	                            try (BufferedReader br = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
	                                String line;
	                                while ((line = br.readLine()) != null) {
	                                    allLines.add(line);
	                                }
	                            } catch (IOException e) {
	                                e.printStackTrace();
	                                request.setAttribute("err", "ファイル読み込み中にエラーが発生しました: " + e.getMessage());
	                                // フォワード
		                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
		                            rd.forward(request, response);
	                                return; // 処理を終了
	                            }
	                        }

	                        // ファイルからInventoryLogリストをインポート
	                        try {
	                            List<InventoryLog> fileImportedList2 = inventoryLog_dao.importOne(allLines);
	                            inventoryLog_importedList.addAll(fileImportedList2);
	                        } catch (IllegalArgumentException e) {
	                            request.setAttribute("err", "データの取り込み失敗。エラー内容: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                            
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                            request.setAttribute("err", "InventoryLog のインポート中にエラーが発生しました: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                        }

	                        // ファイルからInventorymanagementinquiryリストをインポート
	                        try {
	                            List<Inventorymanagementinquiry> fileImportedList1 = inventorymanagementinquiry_dao.importOne(allLines);
	                            inventorymanagementinquiry_importedList.addAll(fileImportedList1);
	                        } catch (IllegalArgumentException e) {
	                        	request.setAttribute("err", "データの取り込み失敗。エラー内容: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                            
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                            request.setAttribute("err", "Inventorymanagementinquiry のインポート中にエラーが発生しました: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                        }

	                        // Inventorymanagementinquiryオブジェクトをデータベースに保存
	                        try {
	                            inventoryLog_dao.save(inventoryLog_importedList, inventorymanagementinquiry_importedList, allLines, request);
	                        } catch (IllegalArgumentException e) {
	                        	request.setAttribute("err", "データの取り込み失敗。エラー内容: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                            
	                        } catch (Exception e) {
	                            request.setAttribute("err", "データベース保存中にエラーが発生しました: " + e.getMessage());
	                            e.printStackTrace();
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                        }

	                        // inventoryLog_dao.saveが成功した場合のみ、次の処理を実行
	                        try {
	                            inventorymanagementinquiry_dao.save(inventorymanagementinquiry_importedList, inventoryLog_importedList, allLines);
	                        } catch (IllegalArgumentException e) {
	                        	request.setAttribute("err", "データの取り込み失敗。エラー内容: " + e.getMessage());
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                            
	                        } catch (Exception e) {
	                            request.setAttribute("err", "データベース保存中にエラーが発生しました: " + e.getMessage());
	                            e.printStackTrace();
	                            // フォワード
	                            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
	                            rd.forward(request, response);
	                            return; // 処理を終了
	                        }

	                        // エラーメッセージが設定されていない場合のみ、成功メッセージを設定
	                        if (request.getAttribute("err") == null) {
	                            request.setAttribute("msg", "CSVファイルをインポートしました。");
	                        }
	                        
	                        inventorymanagementinquiry_dao.updateRate(); // 引数なしで呼び出し

	                        // インポートしたリストをリクエスト属性に設定
	                        request.setAttribute("inventorymanagementinquiry_importedList", inventorymanagementinquiry_importedList);
	                        request.setAttribute("inventoryLog_importedList", inventoryLog_importedList);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    request.setAttribute("err", "データベース保存中にエラーが発生しました: " + e.getMessage());
	                }
	            } else {
	                request.setAttribute("err", "ファイル名に'RetrievalLog'、'MoveLog'、'StorageLog'、'InventoryLog'が含まれていないファイルがあります。");
	            }
	        } else {
	            request.setAttribute("err", "ファイルが選択されていません。");
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
	    InventorymanagementinquiryDAO inventorymanagementinquiry_dao = new InventorymanagementinquiryDAO();
	    String action = request.getParameter("action");
	    String[] selectedIds = request.getParameterValues("selectedIds"); 
	    
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
	                String fileName = "inventorymanagementinquirylist_DL " + currentDateAndTime + ".csv";

	                // 適切なパスを選んでください
	                String filePath =  "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName;
	                
	    	        //<!-- UM425QA-KIR915W -->
	    	        //<!-- DESKTOP-KBUH9GC -->
	    	        //<!-- String filePath = "E:\\Program Files/"+ fileName; -->

	                //<!-- Raspberry Pi(192.168.10.103 ) -->	        
	                //<!-- Raspberry Pi(192.168.10.122 ) -->
	                //<!-- Raspberry Pi(192.168.10.118 ) -->
	    	        //<!-- String filePath =  "/var/samba/Data_Transfer/TotalInventoryFlowManagement_tomcat/Download_Files/"+ fileName; -->

	                csvWriter = new PrintWriter(filePath, "Shift-JIS");

	                // ヘッダー行
	                csvWriter.append("登録No.,W品名コード(Asplus)/W品名(Asplus),QRCode情報（W品名コード+W品名(品名仕様)）,"
	                        + "ロケ番,ロケ番_移動先,期初在庫,理論在庫,在庫管理区分,棚卸在庫(実績),要調査（誤差）,確定,備考,単価");
	                csvWriter.append("\n");

	                // 選択されたIDごとにデータをCSVに追加
	                for (String id : selectedIds) {
	                    inventorymanagementinquiry_dao.downloadOneToCsv(Integer.parseInt(id), csvWriter);
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
	            	inventorymanagementinquiry_dao.deleteOne(Integer.parseInt(id)); // IDに基づいて削除
	            }
	            request.setAttribute("msg", selectedIds.length + "件削除しました。");
	        }
	        
	        // 最新のリストを再取得してJSPに渡す
	        List<Inventorymanagementinquiry> list = inventorymanagementinquiry_dao.findAll();
	        request.setAttribute("list", list);
	        
	        // 完了メッセージを設定
	        request.setAttribute("msg", selectedIds.length + "件削除しました。");
	      }
       
	    // 再度一覧を表示
	    doGet(request, response);
	}
}