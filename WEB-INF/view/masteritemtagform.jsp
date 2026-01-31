<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<Masteritemtag> list=(List<Masteritemtag>)request.getAttribute("list");
    Masteritemtag masteritemtag=(Masteritemtag)request.getAttribute("masteritemtag");
	
    String identifier = masteritemtag == null ? "" : masteritemtag.getIdentifier();
    String identificationnumber = masteritemtag == null ? "" : masteritemtag.getIdentificationnumber();
    String vendor = masteritemtag == null ? "" : masteritemtag.getVendor();
    String productname_asplus = masteritemtag == null ? "" : masteritemtag.getProductname_asplus();
    String productnumber_asplus = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus();
    String productname_emphasisColor = masteritemtag == null ? "" : masteritemtag.getProductname_emphasisColor();
    String productnumber_asplussub1 = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplussub1();
    String productnumber_asplus_emphasissub1 = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus_emphasissub1();
    String productnumber_asplus2sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus2sub();
    String productnumber_asplus_emphasis2sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus_emphasis2sub();
    String productnumber_asplus3sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus3sub();
    String productnumber_asplus_emphasis3sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus_emphasis3sub();
    String productnumber_asplus4sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus4sub();
    String productnumber_asplus5sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus5sub();
    String productnumber_asplus6sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus6sub();
    String productnumber_asplus7sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus7sub();
    String productnumber_asplus8sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus8sub();
    String productnumber_asplus9sub = masteritemtag == null ? "" : masteritemtag.getProductnumber_asplus9sub();
    String productnumber_Color = masteritemtag == null ? "" : masteritemtag.getProductnumber_Color();
    String qrcodeinformation = masteritemtag == null ? "" : masteritemtag.getQrcodeinformation();
    String storagelocation = masteritemtag == null ? "" : masteritemtag.getStoragelocation();
    String inventorymanagementclassification = masteritemtag == null ? "" : masteritemtag.getInventorymanagementclassification();
    String quantity = masteritemtag == null ? "" : masteritemtag.getQuantity();
    String outline = masteritemtag == null ? "" : masteritemtag.getOutline();
    String paperColor = masteritemtag == null ? "" : masteritemtag.getPaperColor();
    String quantitySelectionAvailability = masteritemtag == null ? "" : masteritemtag.getQuantitySelectionAvailability();
    String quantitySelectionRange = masteritemtag == null ? "" : masteritemtag.getQuantitySelectionRange();
    String pattern = masteritemtag == null ? "" : masteritemtag.getPattern();
    String photoAvailability = masteritemtag == null ? "" : masteritemtag.getPhotoAvailability();
    String rate = masteritemtag == null ? "" : String.valueOf(masteritemtag.getRate());
	
	String id=masteritemtag==null ? "":String.valueOf(masteritemtag.getId());
	
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Masteritemtagform.css">
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

<form action="<%= request.getContextPath() %>/MasteritemtagForm" method="post" >

<header>
	<ul style="display: flex; align-items: center; width: 100%;">
    	<li><a href="<%= request.getContextPath() %>/Main">ﾒｲﾝ画面 戻る</a></li>
    	<li><a href="<%= request.getContextPath() %>/MasteritemtagList">製品管理/ﾏｽﾀｰﾘｽﾄ 戻る</a></li>
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
    	<label for="identifier" class="required-label" id="identifier_label"><b>識別種類:</b></label>
        <select id="identifier" class="form-control" name="identifier" required>
        <option value=''>識別種類を選択してください</option>
        <option value="KP-">製品　:KP-</option>
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
    <label for="productname_emphasisColor" class="" id="productname_emphasisColor_label"><b>品名強調カラー:</b></label>
    <input type="text" class="form-control" id="productname_emphasisColor" name="productname_emphasisColor" value="<%=productname_emphasisColor%>" placeholder="品名強調カラーを入力してください">
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="productnumber_asplussub1" class="required-label" id="productnumber_asplussub1_label"><b>品番（Asplus）1:</b></label>
    <input type="text" class="form-control" id="productnumber_asplussub1" name="productnumber_asplussub1" value="<%=productnumber_asplussub1%>" placeholder="品番（Asplus）1を入力してください" required>
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus_emphasissub1" class="" id="productnumber_asplus_emphasissub1_label"><b>品番（Asplus）_強調1:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus_emphasissub1" name="productnumber_asplus_emphasissub1" value="<%=productnumber_asplus_emphasissub1%>" placeholder="品番（Asplus）_強調1を入力してください">
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_asplus2sub" class="" id="productnumber_asplus2sub_label"><b>品番（Asplus）2:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus2sub" name="productnumber_asplus2sub" value="<%=productnumber_asplus2sub%>" placeholder="品番（Asplus）2を入力してください">
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus_emphasis2sub" class="" id="productnumber_asplus_emphasis2sub_label"><b>品番（Asplus）_強調2:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus_emphasis2sub" name="productnumber_asplus_emphasis2sub" value="<%=productnumber_asplus_emphasis2sub%>" placeholder="品番（Asplus）_強調2を入力してください">
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_asplus3sub" class="" id="productnumber_asplus3sub_label"><b>品番（Asplus）3:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus3sub" name="productnumber_asplus3sub" value="<%=productnumber_asplus3sub%>" placeholder="品番（Asplus）3を入力してください">
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus_emphasis3sub" class="" id="productnumber_asplus_emphasis3sub_label"><b>品番（Asplus）_強調3:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus_emphasis3sub" name="productnumber_asplus_emphasis3sub" value="<%=productnumber_asplus_emphasis3sub%>" placeholder="品番（Asplus）_強調3を入力してください">
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_asplus4sub" class="" id="productnumber_asplus4sub_label"><b>品番（Asplus）4:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus4sub" name="productnumber_asplus4sub" value="<%=productnumber_asplus4sub%>" placeholder="品番（Asplus）4を入力してください">
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus5sub" class="" id="productnumber_asplus4sub_label"><b>品番（Asplus）5:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus5sub" name="productnumber_asplus5sub" value="<%=productnumber_asplus5sub%>" placeholder="品番（Asplus）5を入力してください">
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_asplus6sub" class="" id="productnumber_asplus6sub_label"><b>品番（Asplus）6:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus6sub" name="productnumber_asplus6sub" value="<%=productnumber_asplus6sub%>" placeholder="品番（Asplus）6を入力してください">
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus7sub" class="" id="productnumber_asplus7sub_label"><b>品番（Asplus）7:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus7sub" name="productnumber_asplus7sub" value="<%=productnumber_asplus7sub%>" placeholder="品番（Asplus）7を入力してください">
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_asplus8sub" class="" id="productnumber_asplus8sub_label"><b>品番（Asplus）8:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus8sub" name="productnumber_asplus8sub" value="<%=productnumber_asplus8sub%>" placeholder="品番（Asplus）8を入力してください">
  </div>
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus9sub" class="" id="productnumber_asplus9sub_label"><b>品番（Asplus）9:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus9sub" name="productnumber_asplus9sub" value="<%=productnumber_asplus9sub%>" placeholder="品番（Asplus）9を入力してください">
  </div>
</div>

<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="productnumber_asplus" class="required-label" id="productnumber_asplus_label"><b>品番（Asplus）:</b></label>
    <input type="text" class="form-control" id="productnumber_asplus" name="productnumber_asplus" value="<%=productnumber_asplus%>" placeholder="品番（Asplus）を入力してください"  readonly required>
  </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="productnumber_Color" class="" id="productnumber_Color_label"><b>品番カラー:</b></label>
    <input type="text" class="form-control" id="productnumber_Color" name="productnumber_Color" value="<%=productnumber_Color%>" placeholder="品番カラーを入力してください">
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
        <option value="不明">不明</option>
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
        <option value="9144">9144:外注部品</option>
        <option value="9142">9142:外注仕掛品</option>
        <option value="9141">9141:外注完成品</option>
        <option value="9999">9999:/</option>
        <option value="0">0000：不明</option>
    </select>
 </div>
</div>

<div class="form-row">
    <div class="form-group col-sm-6">
    <label for="quantity" class="required-label" id="quantity_label"><b>数量:</b></label>
    <input type="int" class="form-control" id="quantity" name="quantity" value="<%=quantity%>" placeholder="数量を入力してください" required>
  </div>
    <div class="form-group col-sm-6">
        <label for="pattern" class="required-label" id="pattern_label"><b>ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝ:</b></label>
        <select id="pattern" class="form-control" name="pattern" required>
            <option value=''>ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝを選択してください</option>
            
            <option value=''>-----------ﾌｫｰﾏｯﾄ構成ﾊﾟﾀｰﾝ1-----------</option>
            <option value="1">Format Pattern_1 白抜無_組立Ver1</option>
            <option value="2">Format Pattern_2 白抜無_組立Ver2</option>
            <option value="3">Format Pattern_3 白抜無_無地Ver1</option>
            <option value="4">Format Pattern_4 白抜無_無地Ver2</option>
            <option value="5">Format Pattern_5 白抜無_(工)ｼﾙｸ印刷</option>
            <option value="6">Format Pattern_6 白抜無_(工)導電塗装</option>
            <option value="7">Format Pattern_7 白抜無_(工)仕上検査</option>

            <option value="20">Format Pattern_20 白抜有_組立Ver1</option>
            <option value="21">Format Pattern_21 白抜有_組立Ver2</option>
            <option value="22">Format Pattern_22 白抜有_無地Ver1</option>
            <option value="23">Format Pattern_23 白抜有_無地Ver2</option>
            <option value="24">Format Pattern_24 白抜有_(工)ｼﾙｸ印刷</option>
            <option value="25">Format Pattern_25 白抜有_(工)導電塗装</option>
            <option value="26">Format Pattern_26 白抜有_(工)仕上検査</option>
            
            <option value="40">Format Pattern_40 白抜無_ﾃﾝﾌﾟﾚVer1</option>
            <option value="41">Format Pattern_41 白抜無_ﾃﾝﾌﾟﾚVer2</option>
            
            <option value="50">Format Pattern_50 白抜有_ﾃﾝﾌﾟﾚVer1</option>
            <option value="51">Format Pattern_51 白抜有_ﾃﾝﾌﾟﾚVer2</option>
            
            <option value=''>-----------ﾌｫｰﾏｯﾄ構成ﾊﾟﾀｰﾝ2-----------</option>
            <option value="60">Format Pattern_60 白抜無_無地Ver1</option>
            
            <option value="70">Format Pattern_70 白抜有_無地Ver1</option>
            
            <option value=''>-----------ﾌｫｰﾏｯﾄ構成ﾊﾟﾀｰﾝ3-----------</option>
            <option value="80">Format Pattern_80 白抜無_無地Ver1</option>
            
            <option value="90">Format Pattern_90 白抜有_無地Ver1</option>
            
        </select>
    </div>
</div>

<!-- <label for="flag"><b>数量選択幅:　</b></label> -->
<input type="hidden" class="form-control" id="quantitySelectionRange" name="quantitySelectionRange" value="0"required>
<!-- <label for="flag"><b>数量選択有無:　</b></label> -->
<input type="hidden" class="form-control" id="quantitySelectionAvailability" name="quantitySelectionAvailability" value="無"required>
<!-- <label for="flag"><b>用紙色:　</b></label> -->
<input type="hidden" class="form-control" id="paperColor" name="paperColor" value="無"required>
<!-- <label for="flag"><b>写真有無:　</b></label> -->
<input type="hidden" class="form-control" id="photoAvailability" name="photoAvailability" value="無"required>
<!-- <label for="flag"><b>白抜き有無:　</b></label> -->
<input type="hidden" class="form-control" id="outline" name="outline" value="無"required>


<div class="form-row">
  <div class="form-group col-sm-6">
    <label for="qrcodeinformation" class="required-label" id="qrcodeinformation_label"><b>QRCode情報:</b></label>
    <input type="text" class="form-control" id="qrcodeinformation" name="qrcodeinformation" value="<%=qrcodeinformation%>" placeholder="QRCode情報を入力してください" readonly required>
  </div>
  <div class="form-group col-sm-6">
  	<button type="button" class="btn btn-primary custom-btn" id="qrcodegenerationinformation" style="margin-top: 32px;">QRCode生成</button>
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

<!-- 識別番号:ユニーク番号の処理 -->
//ユニーク番号の初期化
let uniqueNumber_KP = localStorage.getItem('uniqueNumber_KP') ? parseInt(localStorage.getItem('uniqueNumber_KP')) : 1;
let uniquekey = ''; // ユニークキーの初期値

function updateIdentificationNumber() {
 let identifier = document.getElementById('identifier').value;
 let identificationNumberField = document.getElementById('identificationnumber');
 
 // 識別種類に応じて識別番号を生成
 if (identifier === 'KP-') {
     identificationNumberField.value = 'KP-' + pad(uniqueNumber_KP);
     uniquekey = 'KP';
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
     if (uniquekey === 'KP') {
         uniqueNumber_KP++;  // uniqueNumber_KPをインクリメント
         localStorage.setItem('uniqueNumber_KP', uniqueNumber_KP);  // 更新されたユニーク番号を保存
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

<!-- ﾌｫｰﾏｯﾄﾊﾟﾀｰﾝ:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var patternValue = '<%=pattern%>';
    var selectElement = document.getElementsByName('pattern')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!patternValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = patternValue;
    }
});


<!-- name:品名（Asplus）の転記処理 -->
//各フィールドにinputイベントリスナーを追加
function updateProductNumberAsplus() {
    // 各フィールドの値を取得
    const productnumber_asplussub1Value = document.getElementById('productnumber_asplussub1').value;
    const productnumber_asplus_emphasissub1Value = document.getElementById('productnumber_asplus_emphasissub1').value;
    const productnumber_asplus2subValue = document.getElementById('productnumber_asplus2sub').value;
    const productnumber_asplus_emphasis2subValue = document.getElementById('productnumber_asplus_emphasis2sub').value;
    const productnumber_asplus3subValue = document.getElementById('productnumber_asplus3sub').value;
    const productnumber_asplus_emphasis3subValue = document.getElementById('productnumber_asplus_emphasis3sub').value;
    const productnumber_asplus4subValue = document.getElementById('productnumber_asplus4sub').value;
    const productnumber_asplus5subValue = document.getElementById('productnumber_asplus5sub').value;
    const productnumber_asplus6subValue = document.getElementById('productnumber_asplus6sub').value;
    const productnumber_asplus7subValue = document.getElementById('productnumber_asplus7sub').value;
    const productnumber_asplus8subValue = document.getElementById('productnumber_asplus8sub').value;
    const productnumber_asplus9subValue = document.getElementById('productnumber_asplus9sub').value;

    // 必要なフィールドの値を組み合わせてproductnumber_asplusの値を設定
    const combinedProductNumber = productnumber_asplussub1Value + 
                                  productnumber_asplus_emphasissub1Value + 
                                  productnumber_asplus2subValue + 
                                  productnumber_asplus_emphasis2subValue + 
                                  productnumber_asplus3subValue + 
                                  productnumber_asplus_emphasis3subValue + 
                                  productnumber_asplus4subValue + 
                                  productnumber_asplus5subValue + 
                                  productnumber_asplus6subValue + 
                                  productnumber_asplus7subValue + 
                                  productnumber_asplus8subValue + 
                                  productnumber_asplus9subValue;

    // 転記処理
    document.getElementById('productnumber_asplus').value = combinedProductNumber;
}

// 各サブフィールドにイベントリスナーを追加
const fields = [
    'productnumber_asplussub1',
    'productnumber_asplus_emphasissub1',
    'productnumber_asplus2sub',
    'productnumber_asplus_emphasis2sub',
    'productnumber_asplus3sub',
    'productnumber_asplus_emphasis3sub',
    'productnumber_asplus4sub',
    'productnumber_asplus5sub',
    'productnumber_asplus6sub',
    'productnumber_asplus7sub',
    'productnumber_asplus8sub',
    'productnumber_asplus9sub'
];

fields.forEach(function(fieldId) {
    document.getElementById(fieldId).addEventListener('input', updateProductNumberAsplus);
});

<!-- qrcodeinformationへの転記処理 -->
//productnumber_asplusフィールドにinputイベントリスナーを追加。
document.getElementById('qrcodegenerationinformation').addEventListener('click', function() {
    // 各フィールドの値を取得
    const productnumber_asplusValue = document.getElementById('productnumber_asplus').value;
    const inventorymanagementclassificationValue = document.getElementById('inventorymanagementclassification').value;
    const identificationnumberValue = document.getElementById('identificationnumber').value;
    const productnumber_ColorValue = document.getElementById('productnumber_Color').value;
    const quantityValue = document.getElementById('quantity').value;

    // 条件付き
    const productnumber_ColorWithSlash = productnumber_ColorValue ? '/' + productnumber_ColorValue : '';
    const quantityValueWithSlash = quantityValue ? '/' + quantityValue : '';

    // 転記処理
    const qrcodeinformationElement = document.getElementById('qrcodeinformation');
    qrcodeinformationElement.value = productnumber_asplusValue + productnumber_ColorWithSlash + quantityValueWithSlash + '*' + inventorymanagementclassificationValue + '*' 
                                     + '{' + identificationnumberValue + '}';
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

<!-- 単価:初期値に基づいて分岐 -->
document.addEventListener('DOMContentLoaded', function () {
	var rateValue = '<%=rate%>';
    var selectElement = document.getElementsByName('rate')[0]; 

    // 初期値が空の場合は何も選択しない
    if (!rateValue) {
        selectElement.value = '';
    } else {
        // 初期値が存在する場合は対応するオプションを選択
        selectElement.value = rateValue;
    }
});

</script>
</body>