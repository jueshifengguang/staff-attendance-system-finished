<%@page import="com.as.entity.Overtimeapplication"%>
<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Staff"%>
<%@page import="com.as.entity.Temporaryovertime"%>
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
    
    <title>My JSP 'selectAllTemporaryOvertime.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">

  	<title>查看全单位的临时性加班</title>

  	<!-- Custom fonts for this template -->
  	<link href="src/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  	<!-- Custom styles for this template -->
  	<link href="src/css/sb-admin-2.min.css" rel="stylesheet">

  	<!-- Custom styles for this page -->
  	<link href="src/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

  </head>
  
  <body id="page-top">
  <%
  	request.setCharacterEncoding("UTF-8");
  	
  	//获得参数
  	Integer sId = 101;
  	if(request.getParameter("s_id") != null){
  		sId = Integer.parseInt(request.getParameter("s_id"));
  	}else if(request.getAttribute("s_id") != null){
  		sId = (Integer)request.getAttribute("s_id");
  	}
  	
  	Integer select_sid = 101;
  	if(request.getParameter("select_sid") != null){
  		select_sid = Integer.parseInt(request.getParameter("select_sid"));
  	}else if(request.getAttribute("select_sid") != null){
  		select_sid = (Integer)request.getAttribute("select_sid");
  	}
  	
  	//加班时长，这个月的
  	long last_overtime_hour = 0;
  	if(request.getParameter("last_overtime_hour") != null){
  		last_overtime_hour = Long.parseLong(request.getParameter("last_overtime_hour"));
  	}else if(request.getAttribute("last_overtime_hour") != null){
  		last_overtime_hour = Long.valueOf(String.valueOf(request.getAttribute("last_overtime_hour")));
  	}
  	  	
  	//获得登录用户的对象
  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(sId);
  	
  	Integer dep_id = 1001;
  	if(login_staff != null){
  		dep_id = login_staff.getDep_id();
  	}
  	
  	//经理可以查看所有员工
  	List<Staff> staffList = staffService.selectAllStaff();
  	
  	Integer year = 2019;
  	if(request.getParameter("year") != null){
  		year = Integer.parseInt(request.getParameter("year"));
  	}else if(request.getAttribute("year") != null){
  		year = (Integer)request.getAttribute("year");
  	}
  	
  	Integer month = 1;
  	if(request.getParameter("month") != null){
  		month = Integer.parseInt(request.getParameter("month"));
  	}else if(request.getAttribute("month") != null){
  		month = (Integer)request.getAttribute("month");
  	}
  	
  	Integer day = 28;
  	if(request.getParameter("day") != null){
  		day = Integer.parseInt(request.getParameter("day"));
  	}else if(request.getAttribute("day") != null){
  		day = (Integer)request.getAttribute("day");
  	}
  	
  	//要显示的正常加班列表
  	List<Overtimeapplication> overtimeList = null;
  	if(request.getAttribute("overtimeList") != null){
  		overtimeList = (List<Overtimeapplication>)request.getAttribute("overtimeList");
  	}
  	
  	//要显示的临时性加班列表
  	List<Overtimeapplication> tmpOvertimeList = null;
  	if(request.getAttribute("tmpOvertimeList") != null){
  		tmpOvertimeList = (List<Overtimeapplication>)request.getAttribute("tmpOvertimeList");
  	}
  	
  	//该页面的员工对象
  	Staff select_staff = null;
  	if(request.getAttribute("select_staff") != null){
  		select_staff = (Staff)request.getAttribute("select_staff");
  	}
  	
  	Integer nowWeekDay = 1;
  	Calendar cal = Calendar.getInstance();
  	cal.set(year, month-1, 1);	//设置日期
  	nowWeekDay = cal.get(Calendar.DAY_OF_WEEK);	//获得星期几
  	//1：星期日，2：星期一，3：星期二，4：星期三，5：星期四，6：星期五，7：星期六
  	
  	Integer real_day = 1;	//记录打印时实际是几号了
  	
  	Integer select_month = month;
  	
  	//表的编号，便于排序
  	int i = 1;
  	
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
        <div class="sidebar-brand-text mx-3">员工考勤系统</div>
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
   
   
   
   		<!-- 真正的显示部分 -->
	<!-- begin page content -->
	<div class="container-fluid">
	
		<!-- page heading -->
		<h1 class="h3 mb-2 text-gray-800">加班情况</h1>
		<p class="mb-4">
		<%
			for(Staff tmpStaff: staffList){
		 %>
		
			<a href="manager/selectOneOvertimeConByMonthByDep?s_id=<%=sId%>&select_sid=<%=tmpStaff.getS_id()%>"><%=tmpStaff.getS_name() %>&nbsp;&nbsp;</a>
		
		<%
			}
		 %>
		 </p>
		
		<!-- 选择日期 -->
	<form action="manager/selectOneOvertimeConByMonthByDep" action="post">
		<input type="hidden" name="select_sid" value="<%=select_sid%>">
    	<input type="hidden" name="s_id" value="<%=sId%>">
    	请选择年份：
    	<c:set var="yearNow" value="<%=year %>"></c:set>
    	<select name="year">
    		<c:forEach var="year_select" begin="1949" end="2049" step="1">
				<c:if test="${year_select==yearNow}">
					<option value="${year_select }" selected="selected">${year_select }</option>
				</c:if>
				<c:if test="${year_select!=yearNow}">
					<option value="${year_select }">${year_select }</option>
				</c:if>
	   		</c:forEach>
    	</select>
    	请选择月份：
    	<c:set var="monthNow" value="<%=month %>"></c:set>
    	<select name="month">
    		<c:forEach var="month_select" begin="1" end="12" step="1">
				<c:if test="${month_select==monthNow}">
					<option value="${month_select }" selected="selected">${month_select }</option>
				</c:if>
				<c:if test="${month_select!=monthNow}">
					<option value="${month_select }">${month_select }</option>
				</c:if>
	   		</c:forEach>
    	</select>
    	<input type="submit" value="搜索"/>
    </form>
		
		
		<!-- 表 -->
		 <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary"><%=year %>年<%=month %>月</h6>
              <h6 class="m-0 font-weight-bold text-primary">本月，<%=select_staff.getS_name() %>&nbsp;&nbsp;的加班时长为<%=last_overtime_hour %>小时</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
						<thead>
							<tr>
							    <th>编号</th>
								<th>员工编号</th>
								<th>员工姓名</th>
								<th>实际加班开始时间</th>
								<th>实际加班结束时间</th>
								<th>是否是临时性加班</th>
							</tr>
						</thead>
											
						<tbody>

							
    						<c:forEach var="selectOvertime" items="${overtimeList }" >	
    							<tr>
    							<td><%=i++ %></td>

    							<td>${selectOvertime.s_id }&nbsp;&nbsp;</td>
    							
    							<td><%=select_staff.getS_name() %>&nbsp;&nbsp;</td>
    							
    							<td>${selectOvertime.overtime_start }</td>
    							<td>${selectOvertime.overtime_end }</td>
    							
    							<td>否</td>
    						</c:forEach>
    						
    						<c:forEach var="selectTmpOvertime" items="${tmpOvertimeList }" >	
    							<tr>
    							<td><%=i++ %></td>

    							<td><%=select_staff.getS_id() %>&nbsp;&nbsp;</td>
    							
    							<td><%=select_staff.getS_name() %>&nbsp;&nbsp;</td>
    							
    							<td>${selectTmpOvertime.overtime_start }</td>
    							<td>${selectTmpOvertime.overtime_end }</td>
    							
    							<td>是</td>
    						</c:forEach>						
						
							   
						</tbody>
                	</table>
              	 </div>
              </div>
            </div>    
	</div>
</div>
	<!-- end of main content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your Website 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->
  
  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>
  
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