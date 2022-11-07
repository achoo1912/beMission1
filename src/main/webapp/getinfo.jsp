<%@page import="db.getinfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 style="text-align:center">
	<%
	getinfo get = new getinfo();
	String result = get.getcount();
	out.write(result + "개의 WIFI 정보를 대상으로 검색을 시작합니다.");
	%>
</h1>
<h3 style='text-align:center'>
	<a href='index.html' > 홈으로 돌아가기</a>
</h3>
</body>
</html>