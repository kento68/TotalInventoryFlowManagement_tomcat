<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<html lang="en-US">
<head>
  <meta charset="utf-8">
    <title>Login</title>
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,700">

</head>
    <div id="login">
      <form action="${pageContext.request.contextPath}/Login" method="post" name='form-login'>
        <span class="fontawesome-user"></span>
          <input type="text" id="user" placeholder="Username" name="name">
       
        <span class="fontawesome-lock"></span>
          <input type="password" id="pass" placeholder="Password" name="pass">
        
        <input type="submit" value="Login">

</form>
<jsp:include page="/footer.jsp"/>
