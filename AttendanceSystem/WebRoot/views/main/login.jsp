<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<!-- 套模板 -->
	  <meta charset="utf-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	  <meta name="description" content="">
	  <meta name="author" content="">
	
	  <title>Login</title>
	
	  <!-- Custom fonts for this template-->
	  <link href="src/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	
	  <!-- Custom styles for this template-->
	  <link href="src/css/sb-admin-2.min.css" rel="stylesheet">

  </head>
   <style>
.bg{background-image:url(views/4.jpg)}
</style>
  
  <body class="bg-gradient-primary">
  <%
  	request.setCharacterEncoding("UTF-8");
  	
  	String message = "";
  	if(request.getAttribute("message") != null){
  		message = (String)request.getAttribute("message");
  	}
  //年月日初值
  	Calendar now = Calendar.getInstance();
	Integer year = now.get(Calendar.YEAR);
	Integer month = now.get(Calendar.MONTH);
	Integer day = now.get(Calendar.DATE);
   %>
  
  
  

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">今天是<%=year %>-<%=month+1%>-<%=day %></h1>
                  </div>
                  <div class="text-center">
                    <h1 class="h3 text-gray-900 mb-4">欢迎回来!</h1>
                  </div>
                  
                  <form class="user" action="main/login" action="post">
                    <div class="form-group">
                    	&nbsp;&nbsp;
                      <input type="text" name="s_id" class="form-control form-control-user"  placeholder="请输入ID">
                    </div>
                    
				                   
                    <div class="form-group">
                    	&nbsp;&nbsp;
                      <input type="password" name="s_pass" class="form-control form-control-user" placeholder="请输入密码">
                    </div>
                   &nbsp;&nbsp;
                   <input type="submit" class="btn btn-primary btn-user btn-block" value="登录" style="font-size:18px;"/>
                    <c:if test="${message!=''}">
					   <%=message %>
			    	</c:if>
                  
                  </form>  
              
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

  </body>
</html>