package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MasterinventoryDAO;
import model.Masterinventory;
import model.User;

/**
 * Servlet implementation class MasterinventoryForm
 */
@WebServlet("/MasterinventoryForm")
public class MasterinventoryForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MasterinventoryDAO dao=new MasterinventoryDAO();
		String action=request.getParameter("action");

	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
		
		if(action != null && action.equals("update")){
			Masterinventory masterinventory=dao.findOne(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("masterinventory", masterinventory);
			request.setAttribute("msg", "項目を編集してください。");
		}
		
		List<Masterinventory> list=dao.findAll();
		request.setAttribute("list", list);
	    // フォワード
	    RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/masterinventoryform.jsp");
	    rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String identificationnumber=request.getParameter("identificationnumber");
		String vendor=request.getParameter("vendor");
		String productname_asplus=request.getParameter("productname_asplus");
		String productnumber_asplus=request.getParameter("productnumber_asplus");
		String productname_correction=request.getParameter("productname_correction");
		String productnumber_correction=request.getParameter("productnumber_correction");
		String qrcodeinformation=request.getParameter("qrcodeinformation");
		String storagelocation=request.getParameter("storagelocation");
		String inventorymanagementclassification=request.getParameter("inventorymanagementclassification");
		String identifier=request.getParameter("identifier");
	
		if(storagelocation.isEmpty() || qrcodeinformation.isEmpty()){
			request.setAttribute("err","未記入の項目があります！");
		}else{
			MasterinventoryDAO dao=new MasterinventoryDAO();
			String id=request.getParameter("id");
			if(id != null){
				dao.updateOne(new Masterinventory(Integer.parseInt(id),identifier,identificationnumber,vendor,
						productname_asplus,productnumber_asplus,productname_correction,productnumber_correction,
						qrcodeinformation,storagelocation,inventorymanagementclassification));
				request.setAttribute("msg","1件更新しました。");
			}else{
				dao.insertOne(new Masterinventory(identifier,identificationnumber,vendor,
						productname_asplus,productnumber_asplus,productname_correction,productnumber_correction,
						qrcodeinformation,storagelocation,inventorymanagementclassification));
				request.setAttribute("msg","1件登録しました。");
			}
		}
		MasterinventoryDAO dao=new MasterinventoryDAO();
		String action=request.getParameter("action");
		
		List<Masterinventory> list=dao.findAll();
		request.setAttribute("list", list);
	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/masterinventorylist.jsp");
	    rd.forward(request, response);
	}
}