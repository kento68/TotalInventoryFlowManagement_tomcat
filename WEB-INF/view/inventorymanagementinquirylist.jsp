<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
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

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Inventorymanagementinquirylist.css">
<title>TotalInventoryFlowManagement</title>
</head>
<body>

<% if(err !=null){%>
	<div class="alert alert-danger" role="alert">
		<%=err %>
	</div>
<%} %>
<% if (msg != null) { %>
  <div class="alert alert-success" role="alert">
    <%= msg %>
  </div>
<%} %>

<div class="container-fluid" style="margin-top: 40px;">

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面戻る</a></li>
    	<div style="margin-left: auto; display: flex; align-items: center;">
    		<% if (loginUser != null) { %>
           		<button type="submit" name="action" class="btn btn-primary custom-btn" style="margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" id="InventoryProcessing"  value="InventoryProcessing">棚卸処理</button>
            <% } %>
    	</div>

    	<!-- loginUser分岐処理 -->
    	<% if (loginUser != null) { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="${loginUser.name}"/>:ﾛｸﾞｲﾝ中</li>
    	<% } else { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="localuser"/>:ﾛｸﾞｲﾝ中</li>
    	<% } %>
	</ul>
</header>

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="GET" class="mt-4 form-inline custom-margin">
  <div class="form-row align-items-center">
    <div class="form-group mr-3">
      <input type="text" id="searchInput" name="searchKeyword" class="form-control" style="width: 600px;" placeholder="検索キーワードを入力してください">
    </div>
    <div class="form-group mx-sm-3">
      <button type="submit" class="btn btn-primary">検索</button>
    </div>
  </div>
</form>

<%-- テーブル --%>
<% if (list != null && list.size() > 0) { %>
    <table class="table table-striped" style="margin-top: 0px;">
        <thead>
            <tr>
                <th>識別番号</th><th>QRCode情報</th><th>ロケ番号</th>
                <th>期初在庫</th><th>理論在庫</th><th>在庫管理区分</th>
                <th>棚卸在庫</th><th>要調査</th><th>確定</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
             <% for (Inventorymanagementinquiry i : list) { %>
                <tr>
                    <td><%=i.getIdentificationnumber() %></td>
                    <td><%=i.getQrcodeinformation() %></td>
                    <td><%=i.getLocationnumber() %></td>
                    <td><%=i.getInitialinventory() %></td>
                    <td><%=i.getTheoreticalinventory() %></td>
                    <td><%=i.getInventorymanagementclassification() %></td>
                    <td><%=i.getInventorystock() %></td>
                    <td><%=i.getInvestigationrequired() %></td>
                    <td><%=i.getDecision() %></td>
                    <td class="text-right"> <!-- 右寄せのセル -->
                    <!-- loginUser分岐処理 -->
                    <% if (loginUser != null) { %>
                    	<a href="<%= request.getContextPath() %>/InventorymanagementinquiryForm?action=update&id=<%= String.valueOf(i.getId()) %>" class="btn btn-primary">更新</a>
                    	<a href="<%= request.getContextPath() %>/InventorymanagementinquiryList?action=delete&id=<%= String.valueOf(i.getId()) %>" class="btn btn-danger" onclick="return confirm('削除してよろしいですか？');">削除</a>
                    <% } %>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
<% } %>

<footer class="mt-5">
    <ul class="d-flex justify-content-between align-items-center">
        <li><a href="<%= request.getContextPath() %>/Logout">Logout</a></li>
        <div>
        	<form id="myForm" action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="POST" enctype="multipart/form-data" class="form-inline">
        		<label for="csvFiles" class="mr-2">CSVｲﾝﾎﾟｰﾄ:</label>
        	<div class="form-group mr-2">
            	<input type="file" id="csvFiles" name="csvFiles" class="form-control-file" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" multiple>
        	</div>
        		<button type="submit" class="btn btn-success" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" name="action" value="import">ｲﾝﾎﾟｰﾄ</button>
        		<button type="submit" class="btn btn-danger" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" name="action" value="download">ﾀﾞｳﾝﾛｰﾄﾞ</button>
    		</form>
        </div>
    </ul>
</footer>

<input type="hidden" name="form_token" value="${formToken}">

<script>
const forms=document.getElementsByClassName("form-control");
const alerts=document.getElementsByClassName("alert");
for(let i=0;i<forms.length;i++){
	forms[i].addEventListener("focus",()=>{
		for(let j=0;j<alerts.length;j++){
			alerts[j].style.display="none";
		}
	});
}
<!-- リロード制御 -->
document.addEventListener('DOMContentLoaded', function() {
    // セッションストレージからリロードフラグを取得
    var isReloaded = sessionStorage.getItem('reloaded');

    if (!isReloaded) {
        // リロードフラグが存在しない場合、初回ロード
        sessionStorage.setItem('reloaded', 'true'); // リロードフラグを設定
        window.location.reload(); // ページをリロード
    } else {
        // リロード後の処理
        sessionStorage.removeItem('reloaded'); // リロードフラグを削除
        // 初回ロード後に実行する処理をここに記述
        console.log("初回ロード処理が完了しました。");
    }
});
</script>
</body>