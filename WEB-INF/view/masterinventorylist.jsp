<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<Masterinventory> list=(List<Masterinventory>)request.getAttribute("list");
	Masterinventory masterinventory=(Masterinventory)request.getAttribute("masterinventory");
	
	String identifier=masterinventory == null ? "":masterinventory.getIdentifier();
	String identificationnumber=masterinventory == null ? "":String.valueOf(masterinventory.getIdentificationnumber());
	String vendor=masterinventory == null ? "":String.valueOf(masterinventory.getVendor());
	String productname_asplus=masterinventory == null ? "":masterinventory.getProductname_asplus();
	String productnumber_asplus=masterinventory == null ? "":masterinventory.getProductnumber_asplus();
	String productname_correction=masterinventory == null ? "":masterinventory.getProductname_correction();
	String productnumber_correction=masterinventory == null ? "":masterinventory.getProductnumber_correction();
	String qrcodeinformation=masterinventory == null ? "":masterinventory.getQrcodeinformation();
	String storagelocation=masterinventory == null ? "":masterinventory.getStoragelocation();
	String inventorymanagementclassification=masterinventory == null ? "":masterinventory.getInventorymanagementclassification();
	
	String id=masterinventory==null ? "":String.valueOf(masterinventory.getId());
	
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Masterinventorylist.css">
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

<form action="<%= request.getContextPath() %>/MasterinventoryList" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面戻る</a></li>
    	<div style="margin-left: auto; display: flex; align-items: center;">
    	    <% if (loginUser != null) { %>
           		<button type="submit" name="transition" class="btn btn-primary custom-btn" style="margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" id="MasterinventoryForm"  value="MasterinventoryForm">ﾏｽﾀｰﾃﾞｰﾀ登録</button>
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

<form action="<%= request.getContextPath() %>/MasterinventoryList" method="GET" class="mt-4 form-inline custom-margin">
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
                <th>識別番号</th>
                <th>品名（Asplus)</th>
                <th>品番（Asplus)</th>
                <th>品名（Correction)</th>
                <th>品番（Correction)</th>
                <th>保管先</th>
                <th>在庫管理区分</th>
                 <th>QRCode情報</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <% for (Masterinventory m : list) { %>
                <tr>
                    <td><%= m.getIdentificationnumber() %></td>
                    <td><%= m.getProductname_asplus() %></td>
                    <td><%= m.getProductnumber_asplus() %></td>
                    <td><%= m.getProductname_correction() %></td>
                    <td><%= m.getProductnumber_correction() %></td>
                    <td><%= m.getStoragelocation() %></td>
                    <td><%= m.getInventorymanagementclassification() %></td>
                    <td><%= m.getQrcodeinformation() %></td>
                    <td class="text-right"> <!-- 右寄せのセル -->
                    <!-- loginUser分岐処理 -->
                    <a href="<%= request.getContextPath() %>/MasterinventoryQRcode?action=qrcode&id=<%= String.valueOf(m.getId()) %>" class="btn btn-success">QRcode</a>
                    <% if (loginUser != null) { %>
                        <a href="<%= request.getContextPath() %>/MasterinventoryForm?action=update&id=<%= String.valueOf(m.getId()) %>" class="btn btn-primary">更新</a>
                        <a href="<%= request.getContextPath() %>/MasterinventoryList?action=delete&id=<%= String.valueOf(m.getId()) %>" class="btn btn-danger" onclick="return confirm('削除してよろしいですか？');">削除</a>
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
            <form action="<%= request.getContextPath() %>/MasterinventoryList" method="POST" enctype="multipart/form-data" class="form-inline">
                <label for="csvFile" class="mr-2" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;">CSVｲﾝﾎﾟｰﾄ:</label>
                <div class="form-group mr-2">
                    <input type="file" id="csvFile" name="csvFile" class="form-control-file" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" multiple>
                </div>
                <button type="submit" class="btn btn-success" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" name="action" value="import">ｲﾝﾎﾟｰﾄ</button>
                <button type="submit" class="btn btn-danger" style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" name="action" value="download">ﾀﾞｳﾝﾛｰﾄﾞ</button>
            </form>
        </div>
    </ul>
</footer>
</form>
</tr>

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
if (!sessionStorage.getItem('reloaded')) {
  sessionStorage.setItem('reloaded', 'true');
  window.location.reload();
} else {
  sessionStorage.removeItem('reloaded');
}
  
<!-- QRcode制御 -->
  function openQRCodeWindow(id) {
      if (id) {
          var url = '<%= request.getContextPath() %>/MasterinventoryQRcode?action=qrcode&id=' + id;
          var width = 400;
          var height = 400;
          var left = (screen.width - width) / 2;
          var top = (screen.height - height) / 2;
          window.open(url, 'QR Code', 'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left);
      } else {
          alert('IDが無効です。');
      }
  }
</script>
</body>