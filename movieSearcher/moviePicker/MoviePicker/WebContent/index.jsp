<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="content-language" content="zh-CN" />  
	<title>电影影评简单检索</title>
	<meta name="Keywords" content="电影影评,爬虫"/>
	<meta name="Description" content="电影影评,爬虫"/>
	<!-- 将href默认为指定路径，这样后面href可以直接写相对路径 -->    
    <base href="<%=basePath%>"/>
	<style type="text/css">
		*{
			margin: 0 auto;
			padding: 0 auto;
			text-align: center;
		}
		
		a{
			text-decoration: none;
		}
		
		#title{
			width: 100%;
			height: 60px;
			background-color:  #3385ff;
			font-size: 30px;
			color: white;
			line-height: 60px;
		}
		
		#head img{
			border-radius: 50%;
			width: 150px;
			margin: 80px 0;
		}
		
		#search{
			border: 2px solid #b6b6b6;
			width: 600px;
			height: 40px;
			font-size: 15px;
			display: flex;
			justify-content: space-between;
		}
		#search1{
			width: 85%;
			text-align: left;
			text-indent: 10px;
		}
		input::-webkit-input-placeholder {
			color: #b6b6b;
		}
		#search2{
			width: 15%;
			color: white;
			background-color: #3385ff;
			border: hidden;
		}
		
		#QR{
			padding: 80px 0;
		}
		#QR img{
			width: 120px;
		}
	</style>
	<script>
		var request = false;
		try {
			request = new XMLHttpRequest();
		} catch (trymicrosoft) {
			try {
				request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					request = false;
				}
			}
		}
	
		if (!request)
			alert("Error initializing XMLHttpRequest!");
		
		<%
			String information = (String)request.getAttribute("information");         // 获取错误属性
			if(information != null) {
		%>
		alert("<%=information%>");                                           /* 弹出提示信息 */

		window.location.href='index.jsp' ;                            // 跳转到登录界面
		<%
			}
		%>
		
		
	
	</script>
</head>
<body>
	<div id="title">
		<div>电影影评检索系统</div>
	</div>
	
	<!--头像-->
	<div id="head">
		<img src="img/head.jpg" />
	</div>
	
	<!--搜索框-->
	<div>
		<form id="search" action="SimpleSearch" method="post">
			<input id="search1" type="text" placeholder="请输入电影影评内容" name="txt" onclick="this.value=''"/>
			<input id="search2" type="submit" value="搜索一下"/>
		</form>
		
		<div style="margin: 10px 0;boder:0;">
			<form action="FindMovie" method="post">
				<input type="submit" value="搜索影评"/>
			</form>
		</div>
		
		<div style="margin: 10px 0;">
			<form action="CreateIndexs" method="post">
				<input type="submit" value="创建索引"/>
			</form>
		</div>
		<div style="margin: 10px 0;">
			<a href="advancedSearch.jsp">高级检索</a>
		</div>
	</div>
	
	<!--二维码-->
	<div id="QR">
		<img src="img/QR.jpg" />
		<div>关注我</div>
	</div>
	

</body>
</html>