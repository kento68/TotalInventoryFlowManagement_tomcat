<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// セッションスコープからユーザー情報を取得
	User loginUser = (User) session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Main.css">
<title>TotalInventoryFlowManagement</title>
</head>
<body>

<div class="container-fluid" style="margin-top: 40px;">

<form action="<%= request.getContextPath() %>/Main" method="post" >

<header>
	<ul> 
	    <!-- loginUser分岐処理 -->
    	<% if(loginUser != null) {%>
    	<li class="contact"><c:out value="${loginUser.name}"/>:ﾛｸﾞｲﾝ中</li>
    	<% } else { %>
    	<li class="contact"><c:out value="localuser"/>:ﾛｸﾞｲﾝ中</li>
    	<% } %>
	</ul>
</header>


<h2>在庫管理照会</h2>
<div class="form-group col-sm-6">
	<button type="submit" name="transition" class="btn btn-info" id="InventorymanagementinquiryList" value="InventorymanagementinquiryList">在庫管理実績</button>
	<button type="submit" name="transition" class="btn btn-info" id="InventoryLogList" value="InventoryLogList">在庫取込記録</button>
</div>

<h2>在庫管理ﾏｽﾀｰ</h2>
<div class="form-group col-sm-6">
	<button type="submit" name="transition" class="btn btn-info" id="MasterinventoryList" value="MasterinventoryList">在庫管理/ﾏｽﾀｰﾘｽﾄ</button>
</div>

<h2>製品管理ﾏｽﾀｰ</h2>
<div class="form-group col-sm-6">
	<button type="submit" name="transition" class="btn btn-info" id="MasteritemtagList" value="MasteritemtagList">製品管理/ﾏｽﾀｰﾘｽﾄ</button>
</div>

<footer>
	<ul>
    	<li><a href="<%= request.getContextPath() %>/Logout">Logout</a></li>
	</ul>
</footer>
 
 
</form>

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

</script>
</body>