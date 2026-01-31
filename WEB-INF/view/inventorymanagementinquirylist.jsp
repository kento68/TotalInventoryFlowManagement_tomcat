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

<% if(err != null){ %>
    <div class="alert alert-danger" role="alert" id="errMsg">
        <%= err %>
    </div>
<% } %>
<% if (msg != null) { %>
    <div class="alert alert-success" role="alert" id="successMsg">
        <%= msg %>
    </div>
<% } %>

<div class="container-fluid" style="margin-top: 40px;">

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="post">

<header>
    <ul style="display: flex; align-items: center; width: 100%;">
        <li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面戻る</a></li>
        <div style="margin-left: auto; display: flex; align-items: center;">
            <% if (loginUser != null) { %>
                <li class="contact" style="margin-left: 5px;">
                    <c:out value="${loginUser.name}" />:ﾛｸﾞｲﾝ中
                </li>
                <button type="submit" name="action" class="btn btn-primary custom-btn" 
                    style="margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px; margin-left: 10px;" 
                    id="InventoryProcessing" value="InventoryProcessing"onclick="return confirm('棚卸処理してよろしいですか？');">
                    棚卸処理
                </button>
            <% } else { %>
                <li class="contact" style="margin-left: 5px;">
                    <c:out value="localuser" />:ﾛｸﾞｲﾝ中
                </li>
            <% } %>
        </div>
    </ul>
</header>

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="GET" class="mt-4 form-inline custom-margin">
    <div class="form-row align-items-center">
        <div class="form-group mr-3">
            <input type="text" id="searchInput" name="searchKeyword" class="form-control" 
                style="width: 600px;" placeholder="検索キーワードを入力してください">
        </div>
        <div class="form-group mx-sm-3">
            <button type="submit" class="btn btn-primary">検索</button>
        </div>
    </div>
</form>

<form action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="POST" class="mt-4 form-inline custom-margin">

</form>

<div class="d-flex align-items-center gap-2">
  <label for="startIndex" class="me-2">ﾀﾞｳﾝﾛｰﾄﾞ開始位置:</label>
  <input type="number" id="startIndex" name="startIndex" class="form-control" min="0" required oninput="checkRange()" style="width: 100px;">

  <span class="mx-2">～</span>

  <label for="endIndex" class="me-2">ﾀﾞｳﾝﾛｰﾄﾞ終了位置:</label>
  <input type="number" id="endIndex" name="endIndex" class="form-control" min="0" required oninput="checkRange()" style="width: 100px;">

</div>

<form id="myForm" action="<%= request.getContextPath() %>/InventorymanagementinquiryList" method="POST" enctype="multipart/form-data" class="form-inline">

<%-- テーブル --%>
<% if (list != null && list.size() > 0) { %>
    <table class="table table-striped mt-4">
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll" onclick="toggleSelectAll(this)"> 全選択</th>
                <th>識別番号</th>
                <th>QRCode情報</th>
                <th>ロケ番号</th>
                <th>期初在庫</th>
                <th>理論在庫</th>
                <th>在庫管理区分</th>
                <th>棚卸在庫</th>
                <th>要調査</th>
                <th>確定</th>
                <th>単価</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <% int rowIndex = 0; %>
            <% for (Inventorymanagementinquiry i : list) { %>
                <tr data-index="<%= rowIndex %>">
                    <td>
                        <input type="checkbox" name="selectedIds" value="<%= i.getId() %>" class="rowCheckbox">
                    </td>
                    <td><%= i.getIdentificationnumber() %></td>
                    <td><%= i.getQrcodeinformation() %></td>
                    <td><%= i.getLocationnumber() %></td>
                    <td><%= i.getInitialinventory() %></td>
                    <td><%= i.getTheoreticalinventory() %></td>
                    <td><%= i.getInventorymanagementclassification() %></td>
                    <td><%= i.getInventorystock() %></td>
                    <td><%= i.getInvestigationrequired() %></td>
                    <td><%= i.getDecision() %></td>
                    <td><%= i.getRate() %></td>
                    <td class="text-right">
                        <% if (loginUser != null) { %>
                            <a href="<%= request.getContextPath() %>/InventorymanagementinquiryForm?action=update&id=<%= String.valueOf(i.getId()) %>" 
                               class="btn btn-primary">更新</a>
                            <a href="<%= request.getContextPath() %>/InventorymanagementinquiryList?action=delete&id=<%= String.valueOf(i.getId()) %>" 
                               class="btn btn-danger" onclick="return confirm('削除してよろしいですか？');">削除</a>
                        <% } %>
                    </td>
                </tr>
            <% rowIndex++; %>
            <% } %>
        </tbody>
    </table>
<% } %>

<footer class="mt-5">
    <ul class="d-flex justify-content-between align-items-center">
        <!-- ログアウトリンク -->
        <li><a href="<%= request.getContextPath() %>/Logout">Logout</a></li>
        <!-- フォーム部分 -->
        <div class="d-flex align-items-center">
            <!-- CSVインポート -->
            <label for="csvFiles" class="mr-2">CSVｲﾝﾎﾟｰﾄ:</label>
            <div class="form-group mr-2">
                <input type="file" id="csvFiles" name="csvFiles" class="form-control-file" 
                       style="margin-right: 5px; margin-bottom: 5px; padding-top: 1px; padding-bottom: 1px;" 
                       multiple>
            </div>
            <!-- ボタン -->
            <button type="submit" class="btn btn-success mr-2" name="action" value="import" 
                    style="padding-top: 1px; padding-bottom: 1px;"
                    onclick="return confirm('選択した項目をｲﾝﾎﾟｰﾄしてよろしいですか？');">
                ｲﾝﾎﾟｰﾄ
            </button>
            
            <button type="submit" class="btn btn-warning mr-2" name="action" value="downloadSelected" 
                    style="padding-top: 1px; padding-bottom: 1px;" 
                    onclick="return confirm('選択した項目をﾀﾞｳﾝﾛｰﾄﾞしてよろしいですか？');">
                ﾀﾞｳﾝﾛｰﾄﾞ
            </button>
            
            <button type="submit" class="btn btn-danger mr-2" name="action" value="deleteSelected"
            		style="padding-top: 1px; padding-bottom: 1px;" 
            		onclick="return confirm('選択した項目を削除してよろしいですか？');">
				削除
			</button>
			
            <button type="submit" class="btn btn-info" name="action" value="rate" 
                    style="padding-top: 1px; padding-bottom: 1px;">
                単価
            </button>
        </div>
    </ul>
</footer>
</form>
</tr>

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

//チェックボックスの全選択/全解除用
function toggleSelectAll(source) {
  const checkboxes = document.getElementsByName('selectedIds');
  for (let i = 0; i < checkboxes.length; i++) {
    checkboxes[i].checked = source.checked;
  }
}

//チェックボックスの任意選択
function checkRange() {
    let start = parseInt(document.getElementById("startIndex").value) || 0;
    let end = parseInt(document.getElementById("endIndex").value) || 0;

    document.querySelectorAll(".rowCheckbox").forEach(checkbox => {
        let rowIndex = parseInt(checkbox.closest("tr").dataset.index);
        checkbox.checked = (rowIndex >= start && rowIndex <= end);
    });
}

</script>
</body>