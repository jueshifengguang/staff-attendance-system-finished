<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Askforleave"%>
<%@page import="com.as.service.impl.MessageServiceImpl"%>
<%@page import="com.as.service.MessageService"%>
<%@page import="com.as.entity.Message"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Staff"%>
<%@page import="com.as.entity.Department"%>
<%@page import="com.as.service.impl.DepartmentServiceImpl"%>
<%@page import="com.as.service.DepartmentService"%>
<%@page import="com.as.service.AskforleaveService"%>
<%@page import="com.as.entity.Temporaryovertime"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateAskforleave.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- 以下套模板 -->
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">

  	<title>主管审批请假申请</title>

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
        Integer sId = 12;
  		if(request.getParameter("s_id") != null){
  			sId = Integer.parseInt(request.getParameter("s_id"));
  		}
  		
  		Integer apply_id = 12;
  		if(request.getParameter("apply_id") != null){
  			apply_id = Integer.parseInt(request.getParameter("apply_id"));
  		}
  		
  		Askforleave updateAfl = (Askforleave)request.getAttribute("updateAfl");
  		
  		Integer afl_id = updateAfl.getAfl_id();
        
  		
  		String starting_date = "2019-12-12 10:00:00";
  		if(updateAfl.getStarting_date() != null){
  			starting_date = updateAfl.getStarting_date().toString();
  		}
  		
  		String  ending_date = "2019-12-12 20:00:00";
  		if(updateAfl.getEnding_date() != null){
  			ending_date = updateAfl.getEnding_date().toString();
  		}
  				
  		String  leave_reason = "无";
  		if(updateAfl.getLeave_reason() != null){
  			leave_reason = updateAfl.getLeave_reason();
  		}
  		
  		Integer leave_type = 0;
  		if(request.getParameter("leave_type") != null){
  			leave_type = Integer.parseInt(request.getParameter("leave_type"));
  		}
  	    //依赖Service
  	    StaffService staffService = new StaffServiceImpl();
  	    
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

  		//获得登录用户的对象
  	  	Staff login_staff = staffService.findStaffById(sId);
  	  	
  	  	Staff apply_staff = staffService.findStaffById(apply_id);
  	  	
  	  	DepartmentService departmentService = new  DepartmentServiceImpl();
  	  	Department department = departmentService.findDepById(dep_id);
  	  	String dep_name = department.getDep_name();
  	  	
  	  	//获得今日日期
  	Calendar today = Calendar.getInstance();
  	 String yearStr = today.get(Calendar.YEAR)+"";//获取年份
     int month = today.get(Calendar.MONTH);//获取月份
     int year = today.get(Calendar.YEAR); 
  	  	
  %>
     <!-- Page Wrapper -->
  <div id="wrapper">
  	<!-- 侧边导航栏Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
		
	<!-- 带笑脸的标题位置 -->	
      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="director/toHomepage?s_id=<%=sId%>">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink">主管</i>
        </div>
        <!-- 此处可加标题之类 -->
	<div class="sidebar-brand-text mx-3"><%=dep_name %><%=dep_id %><sup></sup></div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">
	
	<!-- 首页链接-->
      <li class="nav-item">
      <!-- 写超链处 当前页-->
        <a class="nav-link" href="director/toHomepage?s_id=<%=sId%>">
        <!-- 引用图标-->
          <i class="fas fa-fw fa-tachometer-alt  active"></i>
          <span style="font-size:24px">首页</span></a>
      </li>

      <!-- 分栏线 -->
      <hr class="sidebar-divider">

      <!-- 分栏标题 -->
      <div class="sidebar-heading">
        	部门管理
      </div>

      <!-- 部门工作管理相关 -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>管理与查看</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">工作安排与工作情况:</h6>
            <a class="collapse-item " href="director/firstSelectAllShiftWork?usr_id=<%=sId%>">工作班次安排</a>
            <a class="collapse-item" href="director/selectAllStaffWorkConByMonthByDep?s_id=<%=sId%>">查看工作情况</a>
          </div>
        </div>
      </li>

      <!-- 员工审批相关 -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-wrench"></i>
          <span>审批</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">请假与加班:</h6>
            <a class="collapse-item" href="director/selectNoncheckedOvertimeApply?s_id=<%=sId%>">加班审批</a>
            <a class="collapse-item" href="director/selectNoncheckedAskforApplyByDep?s_id=<%=sId%>">请假审批</a>
          </div>
        </div>
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
            <a class="collapse-item" href="director/selectAllShiftWork?s_id=<%=sId%>&usr_id=<%=sId %>&dep_id=<%=dep_id%>&select_year=<%=year%>&select_month=<%=month%>">个人工作班次信息</a>
            <a class="collapse-item" href="director/selectOneStaffWorkConByMonthByDep?s_id=<%=sId %>&select_sid=<%=sId%>">个人工作情况信息</a>
            <a class="collapse-item" href="director/selectOvertimeRecord?s_id=<%=sId%>">个人加班信息</a>
            <a class="collapse-item" href="director/listAllAskforleaveBySid?s_id=<%=sId%>">个人请假信息</a>
            <a class="collapse-item" href="director/directorSelectStaffInfo?s_id=<%=sId%>">用户信息修改</a>
          </div>
        </div>
      </li>
      
       <!-- 临时加班-->
      <li class="nav-item">
        <a class="nav-link" href="director/selectAllTemporaryOvertime?s_id=<%=sId%>">
          <i class="fas fa-fw fa-table"></i>
          <span>临时加班</span></a>
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
              		<a class="nav-link dropdown-toggle" href="director/selectNoReadMessageApply?s_id=<%=sId%>">
                		<i class="fas fa-envelope fa-fw"></i>
              		</a>
              	</c:if>
              	<c:if test="<%=message_num != 0 %>">
              		<a class="nav-link dropdown-toggle" href="director/selectNoReadMessageApply?s_id=<%=sId%>">
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
        
       <!-- 真正的显示部分 -->
      <div class="container">

	    <div class="card o-hidden border-0 shadow-lg my-5">
	      <div class="card-body p-0">
	        <!-- Nested Row within Card Body -->
	        <div class="row">
	          
	          <div class="col-lg-7">
	            <div class="p-5">
	              <div class="text-center">
	                <h1 class="h4 text-gray-900 mb-4">审批请假申请</h1>
	              </div>
	              
	           <form action="director/updateAskforleave?s_id=<%=sId %>" method="post">  
	           	<input type="hidden" name="s_id" value="<%=sId%>">  	
  				<input type="hidden" name="afl_id" value="<%=afl_id%>">
  				<div class="form-group row">
  						<div class="col-sm-6 mb-3 mb-sm-0">
  								<input type="text" class="form-control form-control-user" readonly="readonly" name="apply_id" value="<%=apply_id%>" placeholder="员工编号">
  						</div>
  				
  						<div class="col-sm-6">
                    		<input type="text" class="form-control form-control-user" name="s_name" value="<%=apply_staff.getS_name()%>" readonly="readonly" id="exampleLastName" placeholder="员工姓名">
                  		</div>
                 </div>
                 <div class="form-group row">
                  		<div class="col-sm-6">
                    		请假开始时间：<input type="text" class="form-control form-control-user" readonly="readonly" name="starting_date" value="<%=starting_date%>" placeholder="请假开始时间">
                  		</div>
                  		<div class="col-sm-6">
                    		请假结束时间：<input type="text" class="form-control form-control-user" readonly="readonly" name="ending_date" value="<%=ending_date%>">
                  		</div>  						
  				</div>
  				<%String s = null; %>
  				 <div class="form-group row">
                  		<div class="col-sm-6">
                    		<c:if test="<%=leave_type == 0 %>"><%s="事假";%></c:if>
    	                    <c:if test="<%=leave_type == 1 %>"><%s="事假";%></c:if>
    	                    <c:if test="<%=leave_type == 2 %>"><%s="事假";%></c:if>
                    		
                    		
                    	请假类型：<input type="text" readonly="readonly" class="form-control form-control-user" value="<%=s%>">
                  		 
                  		</div>
                  		<div class="col-sm-6">                    		    
                  			请假原因：<input type="text" readonly="readonly" class="form-control form-control-user" name="leave_reason" value="<%=leave_reason%>"><br />						
  						</div> 
  				</div>
  				
  				
	                <div class="form-group">
	                	请选择是否通过：<input type="radio" checked="checked" name="is_approved" value="1" />通过
    									<input type="radio"  name="is_approved" value="2" />不通过 
	                </div>
	                
	                <div class="form-group">
	                	<input type="text" name="approved_reason"class="form-control form-control-user" id="exampleInputEmail" placeholder="请输入审批原因">
	                </div>
  						
  					 <div class="form-group">
	                	<input type="submit" class="btn btn-primary btn-user btn-block" id="submitbutton" value="提交"/>
	                </div>
	                 
	                 
	                 <hr>
	                <a href="director/selectNoncheckedAskforApplyByDep?s_id=<%=sId%>" class="btn btn-primary btn-user btn-block">
	                 	 返回
	                </a>
	              </form>

	            </div><!-- <div class="p-5"> -->
	          </div><!-- <div class="col-lg-7"> -->
	          
	          <!-- 右侧图片显示 -->
	          <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
	          
	        </div><!-- <div class="row"> -->
	      </div><!-- <div class="card-body p-0"> -->
	    </div><!-- <div class="card o-hidden border-0 shadow-lg my-5"> -->
	  </div><!-- <div class="container"> -->
  
  
  
  
  
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