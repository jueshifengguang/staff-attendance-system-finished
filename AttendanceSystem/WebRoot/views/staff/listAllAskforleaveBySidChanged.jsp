<%@page import="com.as.entity.Askforleave"%>
<%@page import="com.as.service.AskforleaveService"%>
<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Staff"%>
<%@page import="com.as.entity.Message"%>
<%@page import="com.as.entity.Department"%>
<%@page import="com.as.service.impl.DepartmentServiceImpl"%>
<%@page import="com.as.service.DepartmentService"%>
<%@page import="com.as.service.impl.MessageServiceImpl"%>
<%@page import="com.as.service.MessageService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'listAllAskforleaveBySidChanged.jsp' starting page</title>
    
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
  	
  	<title>查看自己已销假的请假申请</title>
  	
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
    List<Askforleave> aflList = (List<Askforleave>)request.getAttribute("aflList");
    Integer sId = 2;
  	if(request.getParameter("s_id") != null){
  		sId = Integer.parseInt(request.getParameter("s_id"));
  	}else if(request.getAttribute("s_id") != null){
  		sId = (Integer)request.getAttribute("s_id");
  	}
  	
  //获得登录用户的对象
  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(sId);
  	
  	//消息部分
  	MessageService messageService = new MessageServiceImpl();
	int message_num = 0;
  	List<Message> messageList = messageService.selectNoReadMessageApply(sId);
  	message_num = messageList.size();
  	
  	//经理获得部门名字和部门id
  	Staff staff = staffService.findStaffById(sId);
  	Integer dep_id = 0;
  	if(staff != null){
  		dep_id = staff.getDep_id();
  	}
  	
  	DepartmentService departmentService = new  DepartmentServiceImpl();
  	Department department = departmentService.findDepById(dep_id);
  	String dep_name = department.getDep_name();
  	
   %>
   
 <!-- Page Wrapper -->
  <div id="wrapper">
  	<!-- 侧边导航栏Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
		
	<!-- 带笑脸的标题位置 -->	
      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="staff/toHomepage?s_id=<%=sId%>">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink">员工</i>
        </div>
        <!-- 此处可加标题之类 -->
	<div class="sidebar-brand-text mx-3"><%=dep_name %><%=dep_id %><sup></sup></div>
      </a>

 <!-- Divider -->
      <hr class="sidebar-divider my-0">
	
	<!-- 首页链接-->
      <li class="nav-item">
      <!-- 写超链处 当前页-->
        <a class="nav-link" href="staff/toHomepage?s_id=<%=sId%>">
        <!-- 引用图标-->
          <i class="fas fa-fw fa-tachometer-alt  active"></i>
          <span style="font-size:24px">员工首页</span></a>
      </li>

      <!-- 分栏线 -->
      <hr class="sidebar-divider">

       <!-- Heading -->
      <div class="sidebar-heading">
        	个人信息管理
      </div>

      <!-- 个人申请 -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
          <i class="fas fa-user fa-sm fa-fw"></i>
          <span>个人信息</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">信息管理:</h6>
            <a class="collapse-item" href="staff/selectOvertimeRecord?s_id=<%=sId%>">个人加班信息</a>
            <a class="collapse-item" href="staff/listAllAskforleaveBySid?s_id=<%=sId%>">个人请假信息</a>
            <a class="collapse-item" href="staff/staffSelectStaffInfo?s_id=<%=sId%>">个人账户信息</a>
            <a class="collapse-item" href="staff/listAllAskforleaveBySidChanged?s_id=<%=sId%>">个人销假信息</a>
          </div>
        </div>
      </li>
      

      <!-- 部门工作管理相关 -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>工作班次和情况</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">工作安排与工作情况:</h6>
            <a class="collapse-item " href="staff/firstSelectMyShiftWork?usr_id=<%=sId%>">查看工作班次</a>
            <a class="collapse-item" href="staff/selectOneStaffWorkConByMonthByDep?s_id=<%=sId%>">查看工作情况</a>
          </div>
        </div>
      </li>
      	
      <!-- 分栏线 -->
      <hr class="sidebar-divider">

       <!-- Heading -->
      <div class="sidebar-heading">
        	加班申请
      </div>
            <!-- 加班相关 -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-wrench"></i>
          <span>临时加班和超时加班</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">加班:</h6>
            <a class="collapse-item " href="staff/selectAllTemporaryOvertime?s_id=<%=sId%>">临时加班</a>
            <a class="collapse-item" href="staff/insertOvertimeRecord?s_id=<%=sId%>">超时加班</a>
          </div>
        </div>
      </li>


      <!---退出登录-->
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

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- 不知道干啥的 -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

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
          
         
            <!-- 信封消息超链-->
            <li class="nav-item dropdown no-arrow mx-1">
            	<c:if test="<%=message_num == 0%>">
              		<a class="nav-link dropdown-toggle" href="staff/selectNoReadMessageApply?s_id=<%=sId%>">
                		<i class="fas fa-envelope fa-fw"></i>
              		</a>
              	</c:if>
              	<c:if test="<%=message_num != 0 %>">
              		<a class="nav-link dropdown-toggle" href="staff/selectNoReadMessageApply?s_id=<%=sId%>">
                		<i class="fas fa-envelope fa-fw"></i>
                		<!-- 消息数量-->
                		<span class="badge badge-danger badge-counter"><%=message_num %></span>
              		</a>
              	</c:if>
            </li>

                                            
            <!-- 用户信息列表弹窗- User Information -->
            <!-- 可以把导航栏的超链再写一遍？？？ -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=login_staff.getS_name() %></span>
              </a>
              <!-- Dropdown - User Information -->
              <!-- 已删除 -->
            </li>
          </ul>
        </nav>
        <!-- 顶部导航栏结束-->
    
        
        	<!-- Begin Page Content -->
        <div class="container-fluid">
   			<h1 class="h3 mb-2 text-gray-800">显示自己的请假申请（员工）</h1>
   			<a href="staff/addNewAskforleaveJump?s_id=<%=sId%>">新增请假申请</a>
   			 &nbsp;&nbsp;&nbsp;&nbsp;
   			<a href="staff/listAllAskforleaveBySid?s_id=<%=sId%>">显示未销假请假申请</a>
   		
   		 <!-- DataTales Example -->
          	<div class="card shadow mb-4">
            	<div class="card-header py-3">
              		<h6 class="m-0 font-weight-bold text-primary">已销假请假申请</h6>
            </div>
            
             <div class="card-body">
              		<div class="table-responsive">
                		<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  			<thead>
                    			<tr>
                    				 <th>请假开始时间</th>
    								 <th>请假结束时间</th>
    								 <th>请假类型</th>
    								 <th>请假原因</th>
   									 <th>审批状态</th>
   									 <th>审批原因</th>
  									 <th>销假状态</th>
  								</tr>
                  			</thead>
                  			<tbody>
                  					<%
    									for(Askforleave afl:aflList){   
    									Integer IsResumed = afl.getIs_resumed();
    									if(IsResumed !=0){		
    								 %>
    								  <tr>
    										<td><%=afl.getStarting_date() %></td>
    	 									<td><%=afl.getEnding_date() %></td>
    	 										<c:if test="<%=afl.getLeave_type() == 0 %>"><td>事假</td></c:if>
    	 										<c:if test="<%=afl.getLeave_type() == 1 %>"><td>病假</td></c:if>
    	 										<c:if test="<%=afl.getLeave_type() == 2 %>"><td>其他</td></c:if>
    	 									<td><%=afl.getLeave_reason() %></td>
    	 										<c:if test="<%=afl.getIs_approved() ==0 %>"><td>未审批</td></c:if>
    	 										<c:if test="<%=afl.getIs_approved() ==1 %>"><td>审批通过</td></c:if>
    	 										<c:if test="<%=afl.getIs_approved() ==2 %>"><td>审批未通过</td></c:if>
    	 									<td><%=afl.getApproved_reason()%></td>
         										<c:if test="<%=afl.getIs_resumed() ==0 %>"><td>未销假</td></c:if>
         										<c:if test="<%=afl.getIs_resumed() ==1 %>"><td>已销假</td></c:if>         
    	 								</tr>
    	 								<%}%>
    	 								<%}%>
    	 								</tbody>
                		</table>
              		</div><!-- <div class="table-responsive"> -->
            	</div><!-- <div class="card-body"> -->
          	</div><!-- <div class="card shadow mb-4"> -->
   		</div>
        <!-- /.container-fluid -->
        <!-- 主体部分显示结束 -->
      
    
   	  </div>
      <!-- End of Main Content -->

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