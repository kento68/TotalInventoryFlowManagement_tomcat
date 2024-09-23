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

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Masterinventoryform.css">
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

<form action="<%= request.getContextPath() %>/MasterinventoryForm" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面 戻る</a></li>
    	<li><a href="<%= request.getContextPath() %>/MasterinventoryList">在庫管理/ﾏｽﾀｰﾘｽﾄ 戻る</a></li>
    	<!-- loginUser分岐処理 -->
    	<% if(loginUser != null) {%>
    	<li class="contact"><c:out value="${loginUser.name}"/>:ﾛｸﾞｲﾝ中</li>
    	<% } else { %>
    	<li class="contact"><c:out value="localuser"/>:ﾛｸﾞｲﾝ中</li>
    	<% } %>
	</ul>
</header>

<div class="form-row">
	<div class="form-group col-sm-6">
    	<label for="identifier" class="" id="identifier_label"><b>識別種類:</b></label>
        <select id="identifier" class="form-control" name="identifier">
        <option value=''>識別種類を選択してください</option>
        <option value="KM-">材料　:KM-</option>
        <option value="KI-">副資材:KI-</option>
        </select>
     </div>
     <div class="form-group col-sm-6">
     	<label for="identificationnumber" class="required-label" id="identificationnumber_label"><b>識別番号:</b></label>
        <input type="text" class="form-control" id="identificationnumber" name="identificationnumber" readonly required>
     </div>
</div>
    
<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="vendor" class="" id="vendor_label"><b>仕入先:</b></label>
    <input type="text" class="form-control" id="vendor" name="vendor" value="<%=vendor%>" placeholder="仕入先を入力してください">
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="productname_asplus" class="required-label" id="productname_asplus_label"><b>品名（Asplus）:</b></label>
    <input type="text" class="form-control" id="productname_asplus" name="productname_asplus" value="<%=productname_asplus%>" placeholder="品名（Asplus）を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus" class="required-label" id="productnumber_asplus_label"><b>品番（Asplus）:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus" name="productnumber_asplus" value="<%=productnumber_asplus%>" placeholder="品番（Asplus）を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="productname_correction" class="required-label" id="productname_correction_label"><b>品名（Correction）:</b></label>
    <input type="text" class="form-control" id="productname_correction" name="productname_correction" value="<%=productname_correction%>" placeholder="品名（Correction）を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_correction" class="required-label" id="productnumber_correction_label"><b>品番（Correction）:</b></label>
    <input type="text" class="form-control" id="productnumber_correction" name="productnumber_correction" value="<%=productnumber_correction%>" placeholder="品番（Correction）を入力してください" required>
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
 	<label for="storagelocation"  class="required-label"  id="storagelocation_label"><b>保管先:</b></label>
 		<select class="form-control" name="storagelocation"  value="<%=storagelocation%>" required>
        <option id="storagelocation_comment" value=''>保管先を選択してください</option>
        <option value="自動倉庫">自動倉庫</option>
        <option value="テント倉庫">テント倉庫</option>
        <option value="工場内">工場内</option>
    </select>
 </div>
 <div class="form-group col-sm-6">
 	<label for="inventorymanagementclassification"  class="required-label"  id="inventorymanagementclassification_label"><b>在庫管理区分:</b></label>
 		<select class="form-control" name="inventorymanagementclassification" id="inventorymanagementclassification" value="<%=inventorymanagementclassification%>" required>
        <option  id="inventorymanagementclassification" value=''>在庫管理区分を選択してください</option>
        <option value="9181">9181:完成品</option>
        <option value="9182">9182:仕掛品</option>
        <option value="9182">9184:部品</option>
        <option value="9185">9185:材料</option>
        <option value="8888">8888:副資材</option>
        <option value="9999">9999:/</option>
    </select>
 </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="qrcodeinformation" class="required-label" id="qrcodeinformation_label"><b>QRCode情報:</b></label>
    <input type="text" class="form-control" id="qrcodeinformation" name="qrcodeinformation" value="<%=qrcodeinformation%>" placeholder="QRCode情報を入力してください" readonly required>
  </div>
  <div class="form-group col-sm-6">
  	<button type="button" class="btn btn-primary custom-btn" id="qrcodegenerationinformation" style="margin-top: 32px;">QRCode生成</button>
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

<!-- name:品名（Asplus）- 品名（Correction）の転記処理 -->
//productname_asplusフィールドにinputイベントリスナーを追加。
document.getElementById('productname_asplus').addEventListener('input', function() {
	// ラベルの作成
    const productname_asplusValue = document.getElementById('productname_asplus').value;
    const productname_correctionValue = productname_asplusValue + '/';
    // 転記処理
    document.getElementById('productname_correction').value = productname_correctionValue;
});
  
<!-- number:品名（Asplus）- 品名（Correction）の転記処理 -->
//productnumber_asplusフィールドにinputイベントリスナーを追加。
document.getElementById('productnumber_asplus').addEventListener('input', function() {
	// ラベルの作成
	const productnumber_asplusValue = document.getElementById('productnumber_asplus').value;
	const productnumber_correctionValue = productnumber_asplusValue;
	// 転記処理
	document.getElementById('productnumber_correction').value = productnumber_correctionValue;
});

<!-- qrcodeinformationへの転記処理 -->
//productnumber_asplusフィールドにinputイベントリスナーを追加。
document.getElementById('qrcodegenerationinformation').addEventListener('click', function() {
	// ラベルの作成
	const qrcodeinformationElement = document.getElementById('qrcodeinformation');
	const productname_correctionValue = document.getElementById('productname_correction').value;
	const productnumber_correctionValue = document.getElementById('productnumber_correction').value;
	const inventorymanagementclassificationValue = document.getElementById('inventorymanagementclassification').value;
	const identificationnumberValue = document.getElementById('identificationnumber').value;

  	// 転記処理
	qrcodeinformationElement.value = productname_correctionValue +
	productnumber_correctionValue + '*' + inventorymanagementclassificationValue + '*'
   	+ '{' + identificationnumberValue + '}';
});

<!-- 識別番号:ユニーク番号の処理 -->
// ユニーク番号の初期化
let uniqueNumber_KM = localStorage.getItem('uniqueNumber_KM') ? parseInt(localStorage.getItem('uniqueNumber_KM')) : 1;
let uniqueNumber_KI = localStorage.getItem('uniqueNumber_KI') ? parseInt(localStorage.getItem('uniqueNumber_KI')) : 1;
let uniquekey = ''; // ユニークキーの初期値

function updateIdentificationNumber() {
    let identifier = document.getElementById('identifier').value;
    let identificationNumberField = document.getElementById('identificationnumber');
    
    // 識別種類に応じて識別番号を生成
    if (identifier === 'KM-') {
        identificationNumberField.value = 'KM-' + pad(uniqueNumber_KM);
        uniquekey = 'KM';
    } else if (identifier === 'KI-') {
        identificationNumberField.value = 'KI-' + pad(uniqueNumber_KI);
        uniquekey = 'KI';
    } else {
        identificationNumberField.value = '';  // 識別種類が選択されていない場合は空にする
        uniquekey = '';
    }
}

function pad(number) {
    return String(number).padStart(4, '0');  // 4桁になるようにゼロでパディング
}

function submitForm() {
    // 登録ボタンがクリックされたときの処理
    if (document.getElementById('registration').innerText.includes("登録")) {
        if (uniquekey === 'KM') {
            uniqueNumber_KM++;  // uniqueNumber_KMをインクリメント
            localStorage.setItem('uniqueNumber_KM', uniqueNumber_KM);  // 更新されたユニーク番号を保存
        } else if (uniquekey === 'KI') {
            uniqueNumber_KI++;  // uniqueNumber_KIをインクリメント
            localStorage.setItem('uniqueNumber_KI', uniqueNumber_KI);  // 更新されたユニーク番号を保存
        }
    }
    // その他のフォームの送信処理
    document.getElementById('form').submit();  // フォームを送信
}

document.getElementById('identifier').addEventListener('change', updateIdentificationNumber);

<!-- 識別種類:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var identifierValue = '<%=identifier%>';
    var selectElement = document.getElementsByName('identifier')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!identifierValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = identifierValue;
    }
});

<!-- 識別番号:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var identificationnumberValue = '<%=identificationnumber%>';
    var selectElement = document.getElementsByName('identificationnumber')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!identificationnumberValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = identificationnumberValue;
    }
});

<!-- 保管先:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var storagelocationValue = '<%=storagelocation%>';
    var selectElement = document.getElementsByName('storagelocation')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!storagelocationValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = storagelocationValue;
    }
});

<!-- 在庫管理区分:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var inventorymanagementclassificationValue = '<%=inventorymanagementclassification%>';
    var selectElement = document.getElementsByName('inventorymanagementclassification')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!inventorymanagementclassificationValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = inventorymanagementclassificationValue;
    }
});

<!-- QRCode情報入力制御 -->
//ページが読み込まれたときに実行する処理
document.addEventListener('DOMContentLoaded', function() {
	// 警告ラベルの作成とスタイルの設定
    const inputField = document.getElementById('qrcodeinformation');
    const warningLabel = document.createElement('div');
    warningLabel.className = 'text-danger mt-2';
    warningLabel.style.display = 'none';
    warningLabel.innerText = '無効な文字が含まれています。';
 	// 入力フィールドの親要素に警告ラベルを追加
    inputField.parentNode.appendChild(warningLabel);
    
 	// 入力フィールドに入力されたときに実行する処理
    inputField.addEventListener('input', function() {
    // 日本語と一般的な記号以外を禁止
      const invalidChars = /[^a-zA-Z0-9\s]/; 
      
   		// 禁止文字が含まれている場合
      if (invalidChars.test(inputField.value)) {
    	// 警告ラベルを表示し、入力フィールドの有効性を無効にする
        warningLabel.style.display = 'block';
        inputField.setCustomValidity('無効な文字が含まれています。');
        // 禁止文字が含まれていない場合
      } else {
    	// 警告ラベルを非表示にし、入力フィールドの有効性を有効にする
        warningLabel.style.display = 'none';
        inputField.setCustomValidity('');
      }
    });
  });

</script>
</body>