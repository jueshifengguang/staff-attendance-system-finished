<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'home.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="js/jquery-3.2.1.js"></script>  
    <script type="text/javascript">  
    $(function() {  
          
        /* Ajax post */  
        $('#submitbt').click(function() {  
            var Username = $("#username").val();  
            $.post("ajax.action", {  
                username : Username,  
            }, function(data) {  
                //从后台传过来的数据  
                alert(data);          
            });  
        });  
          /* Ajax post */  
    });  
          
          
    </script> 

  </head>
  
  <body>
  <%
  	request.setCharacterEncoding("UTF-8");
  	Integer sId = Integer.parseInt(request.getParameter("s_id"));
  %>
    这是home.jsp <br>
    <h1>这是home</h1>
    <h2><%=sId %></h2>
    <h2>home</h2>
    <table>
    <tr>
    <td>
    <a href="index.jsp">测试</a>
    </td>
    </tr>
    </table>
    
    <!-- 
    <button id="ajaxlist">获取List</button>
     -->
  </body>
</html>