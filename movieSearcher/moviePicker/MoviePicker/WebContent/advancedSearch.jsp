<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="content-language" content="zh-CN" />
	<title>电影影评高级检索</title>
	<meta name="Keywords" content="电影,爬虫"/>
	<meta name="Description" content="电影,爬虫"/>
	
	<style type="text/css">
		*{
			margin: 0 auto;
			padding: 0 auto;
			text-align: center;
		}
		
		a{
			text-decoration: none;
		}
		
		.div-spacing{
			height: 40px;
		}
					
		#title div{
			width: 100%;
			height: 60px;
			background-color:  #3385ff;
			font-size: 30px;
			color: white;
			line-height: 60px;
		}
		
		#search{
			margin: 80px 0;
		}
		#search div{
			margin: 0 auto;
		}
		#searchIndex{
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
		.td-index{
			width: 100px;
			height: 50px;
			text-align: right;
			font-weight: 600;
		}
		.td-main{
			width: 300px;
			height: 50px;
			text-align: left;
		}
		.td-other{
			height: 50px;
			text-align: center;
		}
		.td-other button{
			height: 30px;
			width: 50px;
			text-align: center;
		}
		#searchCondition{
			width: 700px;
			padding: 20px;
		}
		
		#searchCondition fieldset{
			border: 1px solid #3385ff;
		}
		#searchCondition fieldset legend{
			color: #3385FF;
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
	</script>
</head>
<body>
	<div id="title">
		<div>电影影评-高级检索</div>
	</div>
	
	<!--检索条件-->
	<div id="search">
		<div class="div-spacing">
			<a href="index.jsp">返回普通检索</a>
		</div>
		
		<form action="AdvancedSearch" method="post">
			<div id="searchIndex">
				<input id="search1" type="text" name="txt" placeholder="请输入新闻内容"/>
				<input id="search2" type="submit" value="开始检索"/>		
			</div>
			
			<div id="searchCondition">
				<fieldset>
						<legend>筛选条件</legend>
						<table>
								<tr>
									<td class="td-index">选择类型：</td>
									<td class="td-main">
										<input type="radio" name="type" value="1" selected="selected">前缀检索</input>
    									<input type="radio" name="type" value="2">通配符检索</input>
									</td>
								</tr>
								<tr>
									<td class="td-index">选择时间：</td>
									<td class="td-main">
										<input type="date" name="timeDown"></input>
										-
										<input type="date" name="timeUp"></input>
									</td>
								</tr>
								<tr>
									<td class="td-index">作者：</td>
									<td class="td-main">
										<input type="text" name="author" placeholder="请输入作者姓名"/>
										<select id="boolean3" name="authorType">
											<option value='1' selected=""> 精确 </option>
											<option value='2'> 模糊 </option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="td-index">好评：</td>
									<td class="td-main">
										<input type="text" name="good"/>
										<select id="boolean3" name="goodType">
											<option value='1' selected="selected"> &gt; </option>
											<option value='2'> = </option>
											<option value='3'> &lt; </option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2" class="td-other">
										<button type="reset">重置</button>
									</td>
								</tr>
								
						</table>
				</fieldset>
			</div>
		</form>
	</div>
</body>
</html>