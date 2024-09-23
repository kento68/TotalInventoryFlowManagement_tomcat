<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<InventoryLog> list=(List<InventoryLog>)request.getAttribute("list");
	InventoryLog inventorylog=(InventoryLog)request.getAttribute("inventorylog");
	
	String recorddate = inventorylog == null ? "" : String.valueOf(inventorylog.getRecorddate());
	String recordtime = inventorylog == null ? "" : String.valueOf(inventorylog.getRecordtime());
	String devicename = inventorylog == null ? "" : inventorylog.getDevicename();
	String importdate = inventorylog == null ? "" : String.valueOf(inventorylog.getImportdate());
	String personinchargeno = inventorylog == null ? "" : inventorylog.getPersoninchargeno();
	String locationnumber = inventorylog == null ? "" : inventorylog.getLocationnumber();
	String locationnumberdestination = inventorylog == null ? "" : inventorylog.getLocationnumberdestination();
	String productname = inventorylog == null ? "" : inventorylog.getProductname();
	String importquantity = inventorylog == null ? "" : String.valueOf(inventorylog.getImportquantity());
	String status = inventorylog == null ? "" : inventorylog.getStatus();
	String inventory_status = inventorylog == null ? "" : inventorylog.getInventory_status();
	String locationnumber_m1 = inventorylog == null ? "" : inventorylog.getLocationnumber_m1();
	String status_m1 = inventorylog == null ? "" : inventorylog.getStatus_m1();
	String quantity = inventorylog == null ? "" : String.valueOf(inventorylog.getQuantity());
	String status2 = inventorylog == null ? "" : inventorylog.getStatus2();
	String locationnumber_m2 = inventorylog == null ? "" : inventorylog.getLocationnumber_m2();
	String status_m2 = inventorylog == null ? "" : inventorylog.getStatus_m2();
	String inventory = inventorylog == null ? "" : String.valueOf(inventorylog.getInventory());
	String storagelocation = inventorylog == null ? "" : inventorylog.getStoragelocation();
	String identificationnumber = inventorylog == null ? "" : inventorylog.getIdentificationnumber();
	String inventorymanagementclassification = inventorylog == null ? "" : inventorylog.getInventorymanagementclassification();
	String remarks = inventorylog == null ? "" : inventorylog.getRemarks();

	String id=inventorylog==null ? "":String.valueOf(inventorylog.getId());
	
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Inventoryloglist.css">
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

<form action="<%= request.getContextPath() %>/InventoryLogList" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面戻る</a></li>
    	<div style="margin-left: auto; display: flex; align-items: center;">
    	</div>

    	<!-- loginUser分岐処理 -->
    	<% if (loginUser != null) { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="${loginUser.name}"/>:ﾛｸﾞｲﾝ中</li>
    	<% } else { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="localuser"/>:ﾛｸﾞｲﾝ中</li>
    	<% } %>
	</ul>
</header>

<form action="<%= request.getContextPath() %>/InventoryLogList" method="GET" class="mt-4 form-inline custom-margin">
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
                <th>記録日時</th>
                <th>記録時間</th>
                <th>取込日付</th>
                <th>担当者No.</th>
                <th>ロケ番号</th>
                <th>ロケ番号</th>
                <th>品名</th>
                <th>取込個数</th>
                <th>状態</th>
                <th>在庫/状態</th>
                <th>ロケ番号</th>
                <th>状態</th>
                <th>在庫</th>
                <th>状態</th>
                <th>ロケ番号</th>
                <th>状態</th>
                <th>在庫</th>
                <th>保管先</th>
                <th>登録No.</th>
            </tr>
        </thead>
        <tbody>
            <% for (InventoryLog l : list) { %>
                <tr>
                    <td><%= l.getRecorddate() %></td>
                    <td><%= l.getRecordtime() %></td>
                    <td><%= l.getImportdate() %></td>
                    <td><%= l.getPersoninchargeno() %></td>
                    <td><%= l.getLocationnumber() %></td>
                    <td><%= l.getLocationnumberdestination() %></td>
                    <td><%= l.getProductname() %></td>
                    <td><%= l.getImportquantity() %></td>
                    <td><%= l.getStatus() %></td>
                    <td><%= l.getInventory_status() %></td>
                    <td><%= l.getLocationnumber_m1() %></td>
                    <td><%= l.getStatus_m1() %></td>
                    <td><%= l.getQuantity() %></td>
                    <td><%= l.getStatus2() %></td>
                    <td><%= l.getLocationnumber_m2() %></td>
                    <td><%= l.getStatus_m2() %></td>
                    <td><%= l.getInventory() %></td>
                    <td><%= l.getStoragelocation() %></td>
                    <td><%= l.getIdentificationnumber() %></td>
                    <td class="text-right"> <!-- 右寄せのセル -->
                    <!-- loginUser分岐処理 -->
                    <% if (loginUser != null) { %>
                        <a href="<%= request.getContextPath() %>/InventoryLogList?action=delete&id=<%= String.valueOf(l.getId()) %>" class="btn btn-danger" onclick="return confirm('削除してよろしいですか？');">削除</a>
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
            <form action="<%= request.getContextPath() %>/InventoryLogList" method="POST" enctype="multipart/form-data" class="form-inline">
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

</script>
</body>