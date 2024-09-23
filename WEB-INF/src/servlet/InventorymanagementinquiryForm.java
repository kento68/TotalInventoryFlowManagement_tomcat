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

import dao.InventorymanagementinquiryDAO;
import model.Inventorymanagementinquiry;
import model.User;

/**
 * Servlet implementation class InventorymanagementinquiryForm
 */
@WebServlet("/InventorymanagementinquiryForm")
public class InventorymanagementinquiryForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InventorymanagementinquiryDAO dao=new InventorymanagementinquiryDAO();
		String action=request.getParameter("action");

	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
		
		if(action != null && action.equals("update")){
			Inventorymanagementinquiry inventorymanagementinquiry=dao.findOne(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("inventorymanagementinquiry", inventorymanagementinquiry);
			request.setAttribute("msg", "項目を編集してください。");
		}
		
		List<Inventorymanagementinquiry> list=dao.findAll();
		request.setAttribute("list", list);
	    // フォワード
	    RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/inventorymanagementinquiryform.jsp");
	    rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String identificationnumber=request.getParameter("identificationnumber");
		String productname_productnumber=request.getParameter("productname_productnumber");
		String qrcodeinformation=request.getParameter("qrcodeinformation");
		String locationnumber=request.getParameter("locationnumber");
		String locationnumberdestination=request.getParameter("locationnumberdestination");
		String initialinventory=request.getParameter("initialinventory");
		String theoreticalinventory=request.getParameter("theoreticalinventory");
		String inventorymanagementclassification=request.getParameter("inventorymanagementclassification");
		String inventorystock=request.getParameter("inventorystock");
		String investigationrequired=request.getParameter("investigationrequired");
		String decision=request.getParameter("decision");
		String remarks=request.getParameter("remarks");
		String fileattributes=request.getParameter("fileattributes");
	
		if(identificationnumber.isEmpty() || qrcodeinformation.isEmpty()){
			request.setAttribute("err","未記入の項目があります！");
		}else{
			InventorymanagementinquiryDAO dao=new InventorymanagementinquiryDAO();
			String id=request.getParameter("id");
			if(id != null){
				dao.updateOne(new Inventorymanagementinquiry(Integer.parseInt(id),identificationnumber,
						productname_productnumber,qrcodeinformation,locationnumber,locationnumberdestination,
						Integer.parseInt(initialinventory),Integer.parseInt(theoreticalinventory),inventorymanagementclassification,
						Integer.parseInt(inventorystock),Integer.parseInt(investigationrequired),Integer.parseInt(decision),remarks,fileattributes));
				request.setAttribute("msg","1件更新しました。");
			}
		}
		InventorymanagementinquiryDAO dao=new InventorymanagementinquiryDAO();
		String action=request.getParameter("action");
		
		List<Inventorymanagementinquiry> list=dao.findAll();
		request.setAttribute("list", list);
	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/inventorymanagementinquirylist.jsp");
	    rd.forward(request, response);
	}
}