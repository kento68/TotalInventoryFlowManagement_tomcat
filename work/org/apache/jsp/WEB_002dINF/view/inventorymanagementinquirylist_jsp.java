/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.70
 * Generated at: 2024-09-01 10:51:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model.*;
import java.util.*;

public final class inventorymanagementinquirylist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/E:/Program%20Files/eclipse/workspace/totalInventoryFlowManagement_tomcat/WEB-INF/lib/jstl-impl-1.2.jar!/META-INF/c.tld", Long.valueOf(1308094570000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-impl-1.2.jar", Long.valueOf(1725112758063L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("model");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPではGET、POST、またはHEADのみが許可されます。 JasperはOPTIONSも許可しています。");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write('\n');

	List<Inventorymanagementinquiry> list=(List<Inventorymanagementinquiry>)request.getAttribute("list");
    Inventorymanagementinquiry inventorymanagementinquiry=(Inventorymanagementinquiry)request.getAttribute("inventorymanagementinquiry");
	
    String identificationnumber = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getIdentificationnumber();
    String productname_productnumber = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getProductname_productnumber();
    String qrcodeinformation = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getQrcodeinformation();
    String locationnumber = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getLocationnumber();
    String locationnumberdestination = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getLocationnumberdestination();
    String initialinventory = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getInitialinventory());
    String theoreticalinventory = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getTheoreticalinventory());
    String inventorymanagementclassification = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getInventorymanagementclassification();
    String inventorystock = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getInventorystock());
    String investigationrequired = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getInvestigationrequired());
    String decision = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getDecision());
    String remarks = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getRemarks();

    String id = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getId());
	
	String err=(String)request.getAttribute("err");
	String msg=(String)request.getAttribute("msg");
	// セッションスコープからユーザー情報を取得
	User loginUser = (User) session.getAttribute("loginUser");
	
    // 検索キーワードを取得
    String searchKeyword = request.getParameter("searchKeyword");


      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"UTF-8\">\n");
      out.write("\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n");
      out.write("<!-- Bootstrap CSS -->\n");
      out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/css/Inventorymanagementinquirylist.css\">\n");
      out.write("<title>TotalInventoryFlowManagement</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
 if(err !=null){
      out.write("\n");
      out.write("	<div class=\"alert alert-danger\" role=\"alert\">\n");
      out.write("		");
      out.print(err );
      out.write("\n");
      out.write("	</div>\n");
} 
      out.write('\n');
 if (msg != null) { 
      out.write("\n");
      out.write("  <div class=\"alert alert-success\" role=\"alert\">\n");
      out.write("    ");
      out.print( msg );
      out.write("\n");
      out.write("  </div>\n");
} 
      out.write("\n");
      out.write("\n");
      out.write("<div class=\"container-fluid\" style=\"margin-top: 40px;\">\n");
      out.write("\n");
      out.write("<form action=\"");
      out.print( request.getContextPath() );
      out.write("/InventorymanagementinquiryList\" method=\"post\" >\n");
      out.write("\n");
      out.write("<header>\n");
      out.write("	<ul style=\"display: flex; align-items: center; width: 100%;\">\n");
      out.write("    	<li><a href=\"");
      out.print( request.getContextPath() );
      out.write("/Main\">ﾒｲﾝ画面戻る</a></li>\n");
      out.write("    	<div style=\"margin-left: auto; display: flex; align-items: center;\">\n");
      out.write("    		");
 if (loginUser != null) { 
      out.write("\n");
      out.write("           		<button type=\"submit\" name=\"action\" class=\"btn btn-primary custom-btn\" style=\"margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;\" id=\"InventoryProcessing\"  value=\"InventoryProcessing\">棚卸処理</button>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("    	</div>\n");
      out.write("\n");
      out.write("    	<!-- loginUser分岐処理 -->\n");
      out.write("    	");
 if (loginUser != null) { 
      out.write("\n");
      out.write("    	<li class=\"contact\" style=\"margin-left: 5px;\">");
      if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
        return;
      out.write(":ﾛｸﾞｲﾝ中</li>\n");
      out.write("    	");
 } else { 
      out.write("\n");
      out.write("    	<li class=\"contact\" style=\"margin-left: 5px;\">");
      if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
        return;
      out.write(":ﾛｸﾞｲﾝ中</li>\n");
      out.write("    	");
 } 
      out.write("\n");
      out.write("	</ul>\n");
      out.write("</header>\n");
      out.write("\n");
      out.write("<form action=\"");
      out.print( request.getContextPath() );
      out.write("/InventorymanagementinquiryList\" method=\"GET\" class=\"mt-4 form-inline custom-margin\">\n");
      out.write("  <div class=\"form-row align-items-center\">\n");
      out.write("    <div class=\"form-group mr-3\">\n");
      out.write("      <input type=\"text\" id=\"searchInput\" name=\"searchKeyword\" class=\"form-control\" style=\"width: 600px;\" placeholder=\"検索キーワードを入力してください\">\n");
      out.write("    </div>\n");
      out.write("    <div class=\"form-group mx-sm-3\">\n");
      out.write("      <button type=\"submit\" class=\"btn btn-primary\">検索</button>\n");
      out.write("    </div>\n");
      out.write("  </div>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write('\n');
 if (list != null && list.size() > 0) { 
      out.write("\n");
      out.write("    <table class=\"table table-striped\" style=\"margin-top: 0px;\">\n");
      out.write("        <thead>\n");
      out.write("            <tr>\n");
      out.write("                <th>識別番号</th><th>QRCode情報</th><th>ロケ番号</th>\n");
      out.write("                <th>期初在庫</th><th>理論在庫</th><th>在庫管理区分</th>\n");
      out.write("                <th>棚卸在庫</th><th>要調査</th><th>確定</th>\n");
      out.write("                <th></th>\n");
      out.write("            </tr>\n");
      out.write("        </thead>\n");
      out.write("        <tbody>\n");
      out.write("             ");
 for (Inventorymanagementinquiry i : list) { 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td>");
      out.print(i.getIdentificationnumber() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getQrcodeinformation() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getLocationnumber() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getInitialinventory() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getTheoreticalinventory() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getInventorymanagementclassification() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getInventorystock() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getInvestigationrequired() );
      out.write("</td>\n");
      out.write("                    <td>");
      out.print(i.getDecision() );
      out.write("</td>\n");
      out.write("                    <td class=\"text-right\"> <!-- 右寄せのセル -->\n");
      out.write("                    <!-- loginUser分岐処理 -->\n");
      out.write("                    ");
 if (loginUser != null) { 
      out.write("\n");
      out.write("                    	<a href=\"");
      out.print( request.getContextPath() );
      out.write("/InventorymanagementinquiryForm?action=update&id=");
      out.print( String.valueOf(i.getId()) );
      out.write("\" class=\"btn btn-primary\">更新</a>\n");
      out.write("                    	<a href=\"");
      out.print( request.getContextPath() );
      out.write("/InventorymanagementinquiryList?action=delete&id=");
      out.print( String.valueOf(i.getId()) );
      out.write("\" class=\"btn btn-danger\" onclick=\"return confirm('削除してよろしいですか？');\">削除</a>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("        </tbody>\n");
      out.write("    </table>\n");
 } 
      out.write("\n");
      out.write("\n");
      out.write("<footer class=\"mt-5\">\n");
      out.write("    <ul class=\"d-flex justify-content-between align-items-center\">\n");
      out.write("        <li><a href=\"");
      out.print( request.getContextPath() );
      out.write("/Logout\">Logout</a></li>\n");
      out.write("        <div>\n");
      out.write("        	<form id=\"myForm\" action=\"");
      out.print( request.getContextPath() );
      out.write("/InventorymanagementinquiryList\" method=\"POST\" enctype=\"multipart/form-data\" class=\"form-inline\">\n");
      out.write("        		<label for=\"csvFiles\" class=\"mr-2\">CSVｲﾝﾎﾟｰﾄ:</label>\n");
      out.write("        	<div class=\"form-group mr-2\">\n");
      out.write("            	<input type=\"file\" id=\"csvFiles\" name=\"csvFiles\" class=\"form-control-file\" style=\"margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;\" multiple>\n");
      out.write("        	</div>\n");
      out.write("        		<button type=\"submit\" class=\"btn btn-success\" style=\"margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;\" name=\"action\" value=\"import\">ｲﾝﾎﾟｰﾄ</button>\n");
      out.write("        		<button type=\"submit\" class=\"btn btn-danger\" style=\"margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;\" name=\"action\" value=\"download\">ﾀﾞｳﾝﾛｰﾄﾞ</button>\n");
      out.write("    		</form>\n");
      out.write("        </div>\n");
      out.write("    </ul>\n");
      out.write("</footer>\n");
      out.write("\n");
      out.write("<input type=\"hidden\" name=\"form_token\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${formToken}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("const forms=document.getElementsByClassName(\"form-control\");\n");
      out.write("const alerts=document.getElementsByClassName(\"alert\");\n");
      out.write("for(let i=0;i<forms.length;i++){\n");
      out.write("	forms[i].addEventListener(\"focus\",()=>{\n");
      out.write("		for(let j=0;j<alerts.length;j++){\n");
      out.write("			alerts[j].style.display=\"none\";\n");
      out.write("		}\n");
      out.write("	});\n");
      out.write("}\n");
      out.write("<!-- リロード制御 -->\n");
      out.write("document.addEventListener('DOMContentLoaded', function() {\n");
      out.write("    // セッションストレージからリロードフラグを取得\n");
      out.write("    var isReloaded = sessionStorage.getItem('reloaded');\n");
      out.write("\n");
      out.write("    if (!isReloaded) {\n");
      out.write("        // リロードフラグが存在しない場合、初回ロード\n");
      out.write("        sessionStorage.setItem('reloaded', 'true'); // リロードフラグを設定\n");
      out.write("        window.location.reload(); // ページをリロード\n");
      out.write("    } else {\n");
      out.write("        // リロード後の処理\n");
      out.write("        sessionStorage.removeItem('reloaded'); // リロードフラグを削除\n");
      out.write("        // 初回ロード後に実行する処理をここに記述\n");
      out.write("        console.log(\"初回ロード処理が完了しました。\");\n");
      out.write("    }\n");
      out.write("});\n");
      out.write("</script>\n");
      out.write("</body>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    boolean _jspx_th_c_005fout_005f0_reused = false;
    try {
      _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fout_005f0.setParent(null);
      // /WEB-INF/view/inventorymanagementinquirylist.jsp(71,51) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginUser.name}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
      if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      _jspx_th_c_005fout_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fout_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fout_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    boolean _jspx_th_c_005fout_005f1_reused = false;
    try {
      _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fout_005f1.setParent(null);
      // /WEB-INF/view/inventorymanagementinquirylist.jsp(73,51) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fout_005f1.setValue("localuser");
      int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
      if (_jspx_th_c_005fout_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
      _jspx_th_c_005fout_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fout_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fout_005f1_reused);
    }
    return false;
  }
}
