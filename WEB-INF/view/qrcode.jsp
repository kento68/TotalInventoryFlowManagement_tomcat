<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>QR Code</title>
    <style>
        body {
            text-align: center;
            padding-top: 50px;
        }
    </style>
</head>
<body>
    <img src="<%= request.getContextPath() %>/MasterinventoryQRcode?action=qrcode&id=<%= request.getParameter("id") %>" alt="QR Code">
</body>
</html>