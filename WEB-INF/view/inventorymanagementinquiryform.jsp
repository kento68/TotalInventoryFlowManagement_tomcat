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
    String fileattributes = inventorymanagementinquiry == null ? "" : inventorymanagementinquiry.getFileattributes();
    String rate = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getRate());

    String id = inventorymanagementinquiry == null ? "" : String.valueOf(inventorymanagementinquiry.getId());
	
	String err=(String)request.getAttribute("err");
	String msg=(String)request.getAttribute("msg");
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Inventorymanagementinquiryform.css">
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

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryForm" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面戻る</a></li>
    	<div style="margin-left: auto; display: flex; align-items: center;">
    		<button type="submit" name="transition" class="btn btn-primary custom-btn" style="margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" id="MasterinventoryForm"  value="MasterinventoryForm">ﾏｽﾀｰﾃﾞｰﾀ登録</button>
    		
    	</div>

    	<!-- loginUser分岐処理 -->
    	<% if (loginUser != null) { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="${loginUser.name}"/>:ﾛｸﾞｲﾝ中</li>
    	<% } else { %>
    	<li class="contact" style="margin-left: 5px;"><c:out value="localuser"/>:ﾛｸﾞｲﾝ中</li>
    	<% } %>
	</ul>
</header>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="identificationnumber" class="required-label" id="identificationnumber_label"><b>登録No.:</b></label>
    <input type="text" class="form-control" id="identificationnumber" name="identificationnumber" value="<%=identificationnumber%>" placeholder="登録No.を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="productname_productnumber" class="required-label" id="productname_productnumber_label"><b>W品名コード(Asplus)/W品名(Asplus):</b></label>
    <input type="text" class="form-control" id="productname_productnumber" name="productname_productnumber" value="<%=productname_productnumber%>" placeholder="W品名コード(Asplus)/W品名(Asplus)を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="qrcodeinformation" class="required-label" id="qrcodeinformation_label"><b>QRCode情報:</b></label>
    <input type="text" class="form-control" id="qrcodeinformation" name="qrcodeinformation" value="<%=qrcodeinformation%>" placeholder="QRCode情報を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="locationnumber" class="required-label" id="locationnumber_label"><b>ロケ番:</b></label>
    <input type="text" class="form-control" id="locationnumber" name="locationnumber" value="<%=locationnumber%>" placeholder="ロケ番を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="locationnumberdestination" class="required-label" id="locationnumberdestination_label"><b>ロケ番_移動先:</b></label>
    <input type="text" class="form-control" id="locationnumberdestination" name="locationnumberdestination" value="<%=locationnumberdestination%>" placeholder="ロケ番_移動先を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="initialinventory" class="required-label" id="initialinventory_label"><b>期初在庫:</b></label>
    <input type="number" class="form-control" id="initialinventory" name="initialinventory" value="<%=initialinventory%>" placeholder="期初在庫を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="theoreticalinventory" class="required-label" id="theoreticalinventory_label"><b>理論在庫:</b></label>
    <input type="number" class="form-control" id="theoreticalinventory" name="theoreticalinventory" value="<%=theoreticalinventory%>" placeholder="理論在庫を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="inventorymanagementclassification" class="required-label" id="inventorymanagementclassification_label"><b>在庫管理区分:</b></label>
    <input type="text" class="form-control" id="inventorymanagementclassification" name="inventorymanagementclassification" value="<%=inventorymanagementclassification%>" placeholder="在庫管理区分を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="inventorystock" class="required-label" id="inventorystock_label"><b>棚卸在庫(実績):</b></label>
    <input type="number" class="form-control" id="inventorystock" name="inventorystock" value="<%=inventorystock%>" placeholder="棚卸在庫(実績)を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="investigationrequired" class="required-label" id="investigationrequired_label"><b>要調査（誤差）:</b></label>
    <input type="number" class="form-control" id="investigationrequired" name="investigationrequired" value="<%=investigationrequired%>" placeholder="要調査（誤差）を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="decision" class="required-label" id="decision_label"><b>確定:</b></label>
    <input type="number" class="form-control" id="decision" name="decision" value="<%=decision%>" placeholder="確定を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="remarks" class="required-label" id="remarks_label"><b>備考:</b></label>
    <input type="text" class="form-control" id="remarks" name="remarks" value="<%=remarks%>" placeholder="備考を入力してください" >
  </div>
  <div class="form-group col-sm-6">
    <label for="fileattributes" class="required-label" id="fileattributes_label"><b>ファイル属性:</b></label>
    <input type="text" class="form-control" id="fileattributes" name="fileattributes" value="<%=fileattributes%>" placeholder="ファイル属性を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="rate" class="required-label" id="rate_label"><b>単価:</b></label>
    <input type="float" class="form-control" id="rate" name="rate" value="<%=rate%>" placeholder="単価を入力してください" >
  </div>
</div>

<%if(!id.isEmpty()) {%>
<input type="hidden" name="id" value="<%=id %>">
<%} %>

<button type="submit" class="btn btn-primary custom-btn" id="registration" style="margin-left: 15px;" onclick="submitForm()"><%=id.isEmpty()?"登録":"更新" %></button>
 

<footer>
	<ul>
    	<li><a href="<%= request.getContextPath() %>/Logout">Logout</a></li>
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