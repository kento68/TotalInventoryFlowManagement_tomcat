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

import dao.MasteritemtagDAO;
import model.Masteritemtag;
import model.User;

/**
 * Servlet implementation class MasterinventoryForm
 */
@WebServlet("/MasteritemtagForm")
public class MasteritemtagForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MasteritemtagDAO dao=new MasteritemtagDAO();
		String action=request.getParameter("action");

	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
		
		if(action != null && action.equals("update")){
			Masteritemtag masteritemtag=dao.findOne(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("masteritemtag", masteritemtag);
			request.setAttribute("msg", "項目を編集してください。");
		}
		
		List<Masteritemtag> list=dao.findAll();
		request.setAttribute("list", list);
	    // フォワード
	    RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/masteritemtagform.jsp");
	    rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String identifier = request.getParameter("identifier");
        String identificationnumber = request.getParameter("identificationnumber");
        String vendor = request.getParameter("vendor");
        String productname_asplus = request.getParameter("productname_asplus");
        String productnumber_asplus = request.getParameter("productnumber_asplus");
        String productname_emphasisColor = request.getParameter("productname_emphasisColor");
        String productnumber_asplussub1 = request.getParameter("productnumber_asplussub1");
        String productnumber_asplus_emphasissub1 = request.getParameter("productnumber_asplus_emphasissub1");
        String productnumber_asplus2sub = request.getParameter("productnumber_asplus2sub");
        String productnumber_asplus_emphasis2sub = request.getParameter("productnumber_asplus_emphasis2sub");
        String productnumber_asplus3sub = request.getParameter("productnumber_asplus3sub");
        String productnumber_asplus_emphasis3sub = request.getParameter("productnumber_asplus_emphasis3sub");
        String productnumber_asplus4sub = request.getParameter("productnumber_asplus4sub");
        String productnumber_asplus5sub = request.getParameter("productnumber_asplus5sub");
        String productnumber_asplus6sub = request.getParameter("productnumber_asplus6sub");
        String productnumber_asplus7sub = request.getParameter("productnumber_asplus7sub");
        String productnumber_asplus8sub = request.getParameter("productnumber_asplus8sub");
        String productnumber_asplus9sub = request.getParameter("productnumber_asplus9sub");
        String productnumber_Color = request.getParameter("productnumber_Color");
        String qrcodeinformation = request.getParameter("qrcodeinformation");
        String storagelocation = request.getParameter("storagelocation");
        String inventorymanagementclassification = request.getParameter("inventorymanagementclassification");
        String quantity =request.getParameter("quantity");
        String outline = request.getParameter("outline");
        String paperColor = request.getParameter("paperColor");
        String quantitySelectionAvailability = request.getParameter("quantitySelectionAvailability");
        String quantitySelectionRange = request.getParameter("quantitySelectionRange");
        String storageLocation = request.getParameter("storageLocation");
        String pattern = request.getParameter("pattern");
        String photoAvailability = request.getParameter("photoAvailability");
	
		if(storagelocation.isEmpty() || qrcodeinformation.isEmpty()){
			request.setAttribute("err","未記入の項目があります！");
		}else{
			MasteritemtagDAO dao=new MasteritemtagDAO();
			String id=request.getParameter("id");
			if(id != null){
				dao.updateOne(new Masteritemtag(Integer.parseInt(id),identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
			   	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
				         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
				         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
				         qrcodeinformation, storagelocation, inventorymanagementclassification,Integer.parseInt(quantity), outline, paperColor, 
				         quantitySelectionAvailability, Integer.parseInt(quantitySelectionRange), pattern, photoAvailability));
				request.setAttribute("msg","1件更新しました。");
			}else{
				dao.insertOne(new Masteritemtag(identifier, identificationnumber, vendor, productname_asplus, productnumber_asplus, productname_emphasisColor, 
			   	         productnumber_asplussub1, productnumber_asplus_emphasissub1, productnumber_asplus2sub, productnumber_asplus_emphasis2sub, 
				         productnumber_asplus3sub, productnumber_asplus_emphasis3sub, productnumber_asplus4sub, productnumber_asplus5sub, 
				         productnumber_asplus6sub, productnumber_asplus7sub, productnumber_asplus8sub, productnumber_asplus9sub, productnumber_Color, 
				         qrcodeinformation, storagelocation, inventorymanagementclassification,Integer.parseInt(quantity), outline, paperColor, 
				         quantitySelectionAvailability, Integer.parseInt(quantitySelectionRange), pattern, photoAvailability));
				request.setAttribute("msg","1件登録しました。");
			}
		}
		MasteritemtagDAO dao=new MasteritemtagDAO();
		String action=request.getParameter("action");
		
		List<Masteritemtag> list=dao.findAll();
		request.setAttribute("list", list);
	    // セッションスコープからユーザー情報を取得
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");
	    
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/masteritemtaglist.jsp");
	    rd.forward(request, response);
	}
}