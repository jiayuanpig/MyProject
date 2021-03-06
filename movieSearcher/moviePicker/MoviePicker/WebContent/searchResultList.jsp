<%@page import="ccnu.entity.Movie"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="ccnu.controller.MovieController"%>
<%@ page language="java" import="org.apache.lucene.search.Hits"%>
<%@ page language="java" import="org.apache.lucene.document.Document"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="content-language" content="zh-CN" />  
	<title>搜索引擎检索结果</title>
	<base href="<%=basePath%>"/>
	<meta name="Keywords" content="电影影评,爬虫"/>
	<meta name="Description" content="电影影评,爬虫"/>
	
	<style type="text/css">
		*{
			margin: 0 auto;
			padding: 0 auto;
			text-align: center;
		}
		
		a{
			text-decoration: none;
			color: #3385FF;
			font-size: 20px;
		}
		
		#nav{
			height: 100px;
			font-size: 40px;
			color: #3385FF;
			position: absolute;
			float: left;
			padding: 10px 0 0 40px;
			text-align: right;
		}
		.nav1{
			position: absolute;
			margin: 10px 0 0 10px;
		}
		.nav2{
			width: 1450px;
			height: 2px;
			background-color: #B6B6B6;
			margin: 50px 0 0 10px;
		}
		
		#title{
			width: 100%;
			height: 60px;
			background-color:  #3385ff;
			font-size: 30px;
			color: white;
			line-height: 60px;
		}
		
		#tableResult{
			margin: 100px auto;
		}
		table.altrowstable {
			width: 1500px;
			font-family: verdana,arial,sans-serif;
			font-size:25px;
			color:#333333;
			border-width: 1px;
			border-color: #a9c6c9;
			border-collapse: collapse;
		}
		table.altrowstable th {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #B6B6B6;
			color: white;
			background-color: #3385FF;
		}
		table.altrowstable td {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #a9c6c9;
		}
		.oddrowcolor{
			background-color:#FFFFFF;
		}
		.evenrowcolor{
			background-color:#E0E0E0;
		}
	</style>
	
	<script type="text/javascript">
		function altRows(id){
			if(document.getElementsByTagName){  
				
				var table = document.getElementById(id);  
				var rows = table.getElementsByTagName("tr"); 
				 
				for(i = 0; i < rows.length; i++){          
					if(i % 2 == 0){
						rows[i].className = "evenrowcolor";
					}else{
						rows[i].className = "oddrowcolor";
					}      
				}
			}
		}

		window.onload=function(){
			altRows('alternatecolor');
		}
	</script>
</head>
<body>
	<div id="title">
		<div>电影影评检索系统</div>
	</div>
	
	<div id="nav">
		<div class="nav1">检索结果</div>
		<a href="index.jsp">返回首页</a>
		<center><div class="nav2"/></center>
	</div>
	
	<%
		String flag = (String) request.getAttribute("flag");
		List<Movie> movieList = (ArrayList<Movie>)request.getAttribute("movieList");
	%>
	<%
		if (flag.equals("simpleSearch")) {
	%>
	<span style="color: DarkBlue;color:red;">检索词:
	<%
	 	String word = (String) request.getAttribute("txt");
	 		out.println(word);
	%> <br />
	<%			
		out.println("已为您找到 "+movieList.size()+" 条相关信息");
	%>
	</span>
	<span style="color: DarkBlue;color:red;">检索词:
	<%
		} else if (flag.equals("advancedSearch")) {
	%> <br />
 	<%			
 			out.println("已为您找到"+movieList.size()+"条相关信息");
		}
	%>
	</span>
	
	
	<div id="tableResult">
		<table class="altrowstable" id="alternatecolor">
			<tr>
				<th>#</th>
				<th>标题名</th>
				<th>作者</th>
				<th>时间</th>
				<th>好评</th>
				<th>差评</th>
				<th style="width: 100x;">状态</th>
			</tr>
			<c:if test="${not empty movieList}">
				<c:forEach items="${movieList}" var="movie">  
			        <tr>  
		             	<td>*</td>
						<td>${movie.title}</td>
						<td>${movie.author}</td>
						<td>${movie.date}</td>
						<td>${movie.up}</td>
						<td>${movie.down}</td>
						<td>
							<a href="${movie.url}">查看</a>
							<!-- <a>删除</a> -->
						</td>
			         </tr>  
				</c:forEach> 
			</c:if> 
			
		</table>
	</div>
</body>
</html>