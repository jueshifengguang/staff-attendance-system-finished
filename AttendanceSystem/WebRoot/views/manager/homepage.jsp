<%@page import="com.as.service.impl.DepartmentServiceImpl"%>
<%@page import="com.as.service.DepartmentService"%>
<%@page import="com.as.entity.Department"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Staff"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'homepage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- 以下套模板！ -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>经理的首页</title>

  <!-- Custom fonts for this template -->
  <link href="src/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="src/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="src/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

  </head>
  
  <body>
  <%
  	request.setCharacterEncoding("UTF-8");
  	
  	//获得参数
  	Integer sId = 0;
  	if(request.getParameter("s_id") != null){
  		sId = Integer.parseInt(request.getParameter("s_id"));
  	}else if(request.getAttribute("s_id") != null){
  		sId = (Integer)request.getAttribute("s_id");
  	}
  	//获得登录用户的对象
  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(sId);
  	
  	//经理获得部门名字和部门id
  	Staff staff = staffService.findStaffById(sId);
  	Integer dep_id = 0;
  	if(staff != null){
  		dep_id = staff.getDep_id();
  	}
  	
  	//获得部门信息
  	DepartmentService departmentService = new  DepartmentServiceImpl();
  	Department department = departmentService.findDepById(dep_id);
  	String dep_name = department.getDep_name();
  	
  	
  	//获得今日日期
  	Calendar today = Calendar.getInstance();
  	 String yearStr = today.get(Calendar.YEAR)+"";//获取年份
  	 int year = today.get(Calendar.YEAR); 
     int month = today.get(Calendar.MONTH) + 1;//获取月份
     int day = today.get(Calendar.DATE);//获取日
     int week = today.get(Calendar.DAY_OF_WEEK);
     String weekStr = "";
     String weekEn = "";
     switch (week) {
     case 1:
         weekStr = "星期日";
         weekEn = "Sun";
         break;
     case 2:
         weekStr = "星期一";
         weekEn = "Mon";
         break;
     case 3:
         weekStr = "星期二";
         weekEn = "Tues";
         break;
     case 4:
         weekStr = "星期三";
         weekEn = "Wed";
         break;
     case 5:
         weekStr = "星期四";
         weekEn = "Thur";
         break;
     case 6:
         weekStr = "星期五";
         weekEn = "Fri";
         break;
     case 7:
         weekStr = "星期六";
         weekEn = "Sat";
         break;
     default:
         break;
     }
  	
   %>
   <!-- Page Wrapper -->
   <div id="wrapper">
      <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="manager/toHomepage?s_id=<%=sId%>">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink">经理</i>
        </div>
        <div class="sidebar-brand-text mx-3"><%=dep_name %><%=dep_id %> </div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- 首页链接 -->
      <li class="nav-item">
       <!-- 写超链处 -->
        <a class="nav-link" href="manager/toHomepage?s_id=<%=sId%>">
        <!-- 引用图标 -->
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span    style="font-size:24px">经理首页</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
               员工信息
      </div>
      
      <!-- 员工信息管理相关 -->
     <li class="nav-item">
        <a class="nav-link collapsed" href="manager/managerSelectSelfStaffInfo?s_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>我的账户信息</span>
        </a>
     </li>
     <li class="nav-item">
        <a class="nav-link collapsed" href="manager/managerSelectStaffInfo?s_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>查看员工信息</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="manager/firstSelectAllShiftWork?usr_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>员工工作班次</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="manager/selectAllStaffWorkConByMonthByDep?s_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>员工工作情况</span>
        </a>
    </li>
    <!-- 分栏线 -->
    <hr class="sidebar-divider">
     <!-- Heading -->
    <div class="sidebar-heading">
        临时加班
    </div>
    <!-- 临时加班相关 -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="manager/selectAllTemporaryOvertime?s_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>查看临时加班</span>
        </a>
     </li>
     <li class="nav-item">
        <a class="nav-link collapsed" href="manager/addNewTemporaryOvertimeJump?s_id=<%=sId%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>新增临时加班</span>
        </a>
     </li>
     
     <!---退出登录 -->
      <li class="nav-item">
      <a class="nav-link" href="main/login" data-toggle="modal" data-target="#logoutModal">
           <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
               <span>退出登录</span>
          	</a>
      </li>
     
     <!-- 结尾分栏 -->
      <hr class="sidebar-divider d-none d-md-block">  
     <!-- 收回导航栏键-->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>
    </ul>
    <!-- 侧边导航栏结束 -->
    
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
    
    <!-- main content -->  
    <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- 不知道干啥的 -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button> 
          
          <!-- Topbar Search -->
          <h1 class="h3 mb-2 text-gray-800"><b>Welcome~</b></h1> 
       <!-- Topbar Navbar -->
           <ul class="navbar-nav ml-auto">

            <!-- 小屏用，网页看不见，不许删Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button" value="我是小屏专用！！！">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>
          
         
  
            <!-- 用户信息列表弹窗- User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="manager/toHomepage?s_id=<%=sId%>" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=login_staff.getS_name() %></span>
              </a>
              <!-- Dropdown - User Information -->
              <!-- 已删除 -->
            </li>

          </ul>

        </nav><!-- 顶部导航栏结束-->
        
        
        
        	<!-- 显示主体 -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">菜单栏 </h1>
            	<h3  class="h5 mb-0 text-gray-800"> <%=yearStr %>年<%=month%>月<%=day %>日 <%=weekStr %></h3>
          </div>
          
          <div class="row">
           <!-- 左边 -->
           	<div class="col-lg-6">
           		<!-- 员工工作 -->
           		<div class="card shadow mb-4">
               
				   <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">员工工作信息</h6>
	                </div>
	               
				   <div class="card-body">
    					<a href="manager/firstSelectAllShiftWork?usr_id=<%=sId%>">查看员工工作班次安排</a><br><br>
    					<a href="manager/selectAllStaffWorkConByMonthByDep?s_id=<%=sId%>">查看员工工作情况</a>
	               </div>
			   
              </div>
              <!-- 员工身份 -->
              <div class="card shadow mb-4">
               
				   <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">员工身份信息</h6>
	                </div>
	               
				   <div class="card-body">
  					  <a href="manager/managerSelectStaffInfo?s_id=<%=sId%>">查看员工信息</a>
	               </div>
			   
              </div>
              
           	</div><!--  col-lg-6-->
           		
           	<!-- 右边 -->
           	<div class="col-lg-6">
           		  <div class="card shadow mb-4">
               
				   <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">临时加班</h6>
	                </div>
	               
				   <div class="card-body">
    					<a href="manager/addNewTemporaryOvertimeJump?s_id=<%=sId%>">新建临时加班</a><br><br>
  						  <a href="manager/selectAllTemporaryOvertime?s_id=<%=sId%>">查看已发布的临时加班</a>
	               </div>
              </div>
              
              
                <!-- 个人信息管理 -->
              <div class="card shadow mb-4">
                <!-- Card Header - Accordion -->
                <a href="#collapseCardExample" class="d-block card-header py-3" data-toggle="collapse" role="button" aria-expanded="true" aria-controls="collapseCardExample">
                  <h6 class="m-0 font-weight-bold text-primary">个人信息</h6>
                </a>
                <!-- Card Content - Collapse -->
                <div class="collapse show" id="collapseCardExample">
                  <div class="card-body">
                    	 <a href="manager/managerSelectSelfStaffInfo?s_id=<%=sId%>">查看自己的账户信息</a>
                  </div>
                </div>
				
              </div>
           	</div>
          
          </div><!--  row-->
          
          
          
         </div><!-- container-fluid -->
    
  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">确定要退出登录？</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">点击"退出登录"返回登录页面</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">取消</button>
          <a class="btn btn-primary" href="main/login">退出登录</a>
        </div>
      </div>
    </div>
  </div>
  
  
  <!-- Bootstrap core JavaScript-->
  <script src="src/vendor/jquery/jquery.min.js"></script>
  <script src="src/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="src/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="src/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="src/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="src/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="src/js/demo/datatables-demo.js"></script>
  
  </body>
</html>