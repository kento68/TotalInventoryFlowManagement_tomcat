package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InventoryLogDAO;
import dao.InventorymanagementinquiryDAO;
import dao.MasterinventoryDAO;
import dao.MasteritemtagDAO;
import model.InventoryLog;
import model.Inventorymanagementinquiry;
import model.Masterinventory;
import model.Masteritemtag;


@WebServlet("/Main")
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MasterinventoryDAO dao = new MasterinventoryDAO();
    private InventorymanagementinquiryDAO dao2 = new InventorymanagementinquiryDAO();
    private InventoryLogDAO dao3 = new InventoryLogDAO();
    private MasteritemtagDAO dao4 = new MasteritemtagDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String transition = request.getParameter("transition");
        String jspPath = "/WEB-INF/view/main.jsp";

        try {
            if ("InventorymanagementinquiryList".equals(transition)) {
                List<Inventorymanagementinquiry> list = dao2.findAll();
                request.setAttribute("list", list);
                jspPath = "/WEB-INF/view/inventorymanagementinquirylist.jsp";
            } else if ("InventoryLogList".equals(transition)) {
                List<InventoryLog> list = dao3.findAll();
                request.setAttribute("list", list);
                jspPath = "/WEB-INF/view/inventoryloglist.jsp";
            } else if ("MasterinventoryList".equals(transition)) {
                List<Masterinventory> list = dao.findAll();
                request.setAttribute("list", list);
                jspPath = "/WEB-INF/view/masterinventorylist.jsp";
            } else if ("MasterinventoryForm".equals(transition)) {
                List<Masterinventory> list = dao.findAll();
                request.setAttribute("list", list);
                jspPath = "/WEB-INF/view/masterinventoryform.jsp";
            } else if ("MasteritemtagList".equals(transition)) {
                List<Masteritemtag> list = dao4.findAll();
                request.setAttribute("list", list);
                jspPath = "/WEB-INF/view/masteritemtaglist.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(jspPath);
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
