<%@page import="db.history2dbVer01"%>
<%@page import="db.History"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="db.public2web_ver01"%>
<%@page import="db.public2db_ver02"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전송받은 위치정보</title>
<style>
table {width: 100%;}
th, td {border:solid 1px #000;}
</style>
</head>
<body>
	<%
	Double lat = Double.valueOf(request.getParameter("lat"));
	Double lnt = Double.valueOf(request.getParameter("lnt"));
	LocalDateTime now = LocalDateTime.now();
	
	String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T" +
			now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	
	History hi = new History();
	hi.setLaat(request.getParameter("lat"));
	hi.setLnnt(request.getParameter("lnt"));
	hi.setWheen(formatedNow);
	history2dbVer01 hiInsert = new history2dbVer01();
	hiInsert.dbInsert(hi);
	
	public2db_ver02 de = new public2db_ver02();
	de.dbFlush();
	de.dbInit(lat, lnt);
	
	public2web_ver01 we = new public2web_ver01();
	List<String> wifilist = we.list();
	%>
	
	<h1> 와이파이 정보 구하기 </h1>
	<h3> 
	<a href="index.html">홈</a> | 
	<a href="history.jsp">위치 히스토리 목록</a> | 
	<a href ="getinfo.jsp">Open API 와이파이 정보 가져오기</a>
	</h3>
	<p></p>
	<form action="test1.jsp" method="post">
	LAT : <input type="number" step="0.000000001" id = "lat" name ="lat">, 
	LNT : <input type="number" step="0.000000001" id = "lnt" name ="lnt">
	<input type ="submit" value="근처 WIFI 정보 보기">
	</form>
	
	<table>
	<thead>
	<tr bgcolor="#32CD32" style="color:white">
	<th>거리</th>
	<th>관리번호</th>
	<th>자치구</th>
	<th>와이파이명</th>
	<th>도로명주소</th>
	<th>상세주소</th>
	<th>설치위치(층)</th>
	<th>설치유형</th>
	<th>설치기관</th>
	<th>서비스구분</th>
	<th>망종류</th>
	<th>설치년도</th>
	<th>실내외구분</th>
	<th>WIFI접속환경</th>
	<th>X좌표</th>
	<th>Y좌표</th>
	<th>작업일자</th>
	</tr>
	</thead>
	<tbody>
	<tr>
	<%
	int tmp = 0;
	for (int i = 0; i < wifilist.size(); i++) {
		if (i % 17 == 0) {
			out.write("<tr>");
			tmp = i;
		}
		out.write("<td>" + wifilist.get(i).toString() + "</td>");
		if (i == tmp + 16) {
			out.write("</tr>");
			tmp = 0;
		}
	}
	
	
	%>
		
	</tr>
	</tbody>
	</table>
</body>
</html>