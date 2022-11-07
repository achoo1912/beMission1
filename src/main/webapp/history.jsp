<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.history2dbVer01"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {width: 100%;}
th, td {border:solid 1px #000;}
</style>
</head>
<body>
<%

	
	history2dbVer01 hiSel = new history2dbVer01();
	List<String> result = hiSel.dbSelect();
%>
<h1> 와이파이 정보 구하기 </h1>
	<h3> 
	<a href="index.html">홈</a> | 
	<a href="history.jsp">위치 히스토리 목록</a> | 
	<a href ="getinfo.jsp">Open API 와이파이 정보 가져오기</a>
	</h3>
	
<table>
	<thead>
	<tr bgcolor="#32CD32" style="color:white">
	<th>ID</th>
	<th>X좌표</th>
	<th>Y좌표</th>
	<th>조회일자</th>
	<th>비고</th>
	</tr>
	</thead>
	<tbody>
	<%
		int tmp = 0;
		int cnt = result.size() / 4;
		for (int i = 0; i < result.size(); i++) {
			if(i % 4 == 0) {
				out.write("<tr>");
				tmp = i;
			}
			out.write("<td>" + result.get(i).toString() + "</td>");
			if (i - 3 == tmp) {
				out.write("<td style='text-align:center;'>" + 
			"<form action='historydel.jsp' method='post'>" +
				"<input type='hidden' name='hiddenvalue' value=" + "'" + String.valueOf(cnt) +"'>" +
				"<input type ='submit' value='삭제'>" + 
				"</form></td>");
				out.write("</tr>");
				tmp = 0;
				cnt--;
			}
		}
	%>
	</tbody>
	</table>
</body>
</html>