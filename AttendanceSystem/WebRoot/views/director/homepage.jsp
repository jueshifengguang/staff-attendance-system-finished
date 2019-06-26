<%@page import="com.as.entity.Department"%>
<%@page import="com.as.service.impl.DepartmentServiceImpl"%>
<%@page import="com.as.service.DepartmentService"%>
<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.entity.Staff"%>
<%@page import="com.as.entity.ShiftWork"%>
<%@page import="com.as.entity.Overtimeapplication"%>
<%@page import="com.as.service.impl.MessageServiceImpl"%>
<%@page import="com.as.service.MessageService"%>
<%@page import="com.as.entity.Message"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>director.homepage.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 以下套模板 -->
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">

  	<title>主管的首页</title>
  	
	  <!-- Custom fonts for this template -->
	  <link href="src/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	
	  <!-- Custom styles for this template -->
	  <link href="src/css/sb-admin-2.min.css" rel="stylesheet">
	
	  <!-- Custom styles for this page -->
	  <link href="src/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">	
  
  </head>
  
  <body  id="page-top">
  <%
  	request.setCharacterEncoding("UTF-8");
  	
  	//获得参数
  	Integer sId = 0;
  	if(request.getParameter("s_id") != null){
  		sId = Integer.parseInt(request.getParameter("s_id"));
  	}else if(request.getAttribute("s_id") != null){
  		sId = (Integer)request.getAttribute("s_id");
  	}
  	int overtimeSignInListSize = 0;
  	int overtimeSignOffListSize = 0;
  	int overtimeApplyListSize = 0;
  	
  	//获得可以签到的加班列表
  	List<Overtimeapplication> overtimeSignInList = null;
  	if(request.getAttribute("overtimeSignInList") != null){
  		overtimeSignInList = (List<Overtimeapplication>)request.getAttribute("overtimeSignInList");
  		if(overtimeSignInList != null){
  			overtimeSignInListSize = overtimeSignInList.size();
  		}
  	}
  	//获得可以签退的加班列表
  	List<Overtimeapplication> overtimeSignOffList = null;
  	if(request.getAttribute("overtimeSignOffList") != null){
  		overtimeSignOffList = (List<Overtimeapplication>)request.getAttribute("overtimeSignOffList");
  		if(overtimeSignOffList != null){
  			overtimeSignOffListSize = overtimeSignOffList.size();
  		}
  	}

  	//获得今天的工作班次
  	ShiftWork shiftWorkToday = null;
  	if(request.getAttribute("shiftWorkToday") != null){
  		shiftWorkToday = (ShiftWork)request.getAttribute("shiftWorkToday");
  	}
  	
  	//获得今天正常申请并且已经通过的加班列表
  	List<Overtimeapplication> overtimeApplyList = null;
  	if(request.getAttribute("overtimeApplyList") != null){
  		overtimeApplyList = (List<Overtimeapplication>)request.getAttribute("overtimeApplyList");
  		if(overtimeApplyList != null){
  			overtimeApplyListSize = overtimeApplyList.size();
  		}
  	}
  	
  	//是否提醒员工申请加班
  	int is_remind = 0;
  	if(request.getParameter("is_remind") != null){
  		is_remind = Integer.parseInt(request.getParameter("is_remind"));
   	}else if(request.getAttribute("is_remind") != null){
  		is_remind = (Integer)request.getAttribute("is_remind");
  	}
  	
  	//获得登录用户的对象
  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(sId);
  	
  	//消息部分
  	MessageService messageService = new MessageServiceImpl();
	int message_num = 0;
  	List<Message> messageList = messageService.selectNoReadMessageApply(sId);
  	message_num = messageList.size();
  	
  	//主管获得部门名字和部门id
  	Staff staff = staffService.findStaffById(sId);
  	Integer dep_id = 0;
  	if(staff != null){
  		dep_id = staff.getDep_id();
  	}
  	
  	DepartmentService departmentService = new  DepartmentServiceImpl();
  	Department department = departmentService.findDepById(dep_id);
  	String dep_name = department.getDep_name();
  	
  	//获得今日日期
  	Calendar today = Calendar.getInstance();
  	 String yearStr = today.get(Calendar.YEAR)+"";//获取年份
     int month = today.get(Calendar.MONTH);//获取月份
     int year = today.get(Calendar.YEAR); 
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
        
        
     	<!-- 显示主体 -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">今日事   </h1>
            	<h3  class="h5 mb-0 text-gray-800"> <%=yearStr %>年<%=month%>月<%=day %>日 <%=weekStr %></h3>
          </div>
          
          <div class="row">
           <!-- 左边 -->
           	<div class="col-lg-6">
           	
           		 <!-- 工作安排card -->
	              <div class="card mb-4">
	                <div class="card-header">
	                  	今日安排&nbsp;&nbsp;&nbsp;
	                  	<%=month %>.<%=day %>  <%=weekEn %>
	                </div>
	                <div class="card-body">
	                	<c:if test="<%=shiftWorkToday != null %>">
	                	
    					<table>
    						<tr>
   							 	<td>
   							 	开始时间
   							 	&nbsp;&nbsp;&nbsp;&nbsp;
   							 	</td>
   							 	<td>
   							 	结束时间
   							 	&nbsp;&nbsp;&nbsp;&nbsp;
   							 	</td>
    							<td>
    							操作
    							&nbsp;&nbsp;&nbsp;&nbsp;
    							</td>
    						</tr>
    	
    						<tr>
    							<td>
	    							<c:set var="workEnd" value="<%=shiftWorkToday.getWorking_start() %>"></c:set>					
										<fmt:formatDate value="${workEnd}" pattern="HH:mm"/>
								</td>
								<td>				
	    							<c:set var="workEnd" value="<%=shiftWorkToday.getWorking_end() %>"></c:set>					
										<fmt:formatDate value="${workEnd}" pattern="HH:mm"/>
    							</td>
    							
 						  		 <td>
 					   				<c:if test="<%=shiftWorkToday.getAttendence_status() == 0 %>">
    									<a href="director/signInShiftWork?s_id=<%=sId%>&sw_id=<%=shiftWorkToday.getSw_id()%>">打卡</a>
    								</c:if>
   					 				<c:if test="<%=shiftWorkToday.getAttendence_status() == 1 %>">
    									<a href="director/signOffShiftWork?s_id=<%=sId%>&sw_id=<%=shiftWorkToday.getSw_id()%>">签退</a>
    								</c:if>
    								<c:if test="<%=shiftWorkToday.getAttendence_status() == 2 %>">
    									已完成今日的工作
    								</c:if>
   					 			</td>
   				 			</tr>
   				 			
   				 		</table>
  			  		</c:if>
	                </div>
	              </div>
	              
	              
	              <!-- 加班提醒card -->
	              <div class="card shadow mb-4"> 
					<div class="card-header py-3">
                  		<h6 class="m-0 font-weight-bold text-primary">
                  		加班提醒&nbsp;&nbsp;&nbsp;
                  		<%=month %>.<%=day %>  <%=weekEn %>
                  		</h6>
                	</div>
					<div class="card-body">
						<c:if test="<%=is_remind == 1 %>">
  							  已经超过下班时间1小时了，
 						   <a href="director/insertOvertimeRecord?s_id=<%=sId%>&overtime_start=<%=shiftWorkToday.getWorking_end().toString() %>">申请加班</a>
    					</c:if>
			   		</div>  
			 	</div><!-- card shadow mb-4 -->
           	
           	</div><!-- col-lg-6 左边结束-->
           	
           <!-- 右边 -->	
           	<div class="col-lg-6">
	           	<div class="card shadow mb-4"> 
					<div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">
	                  	加班打卡
	                  	&nbsp;&nbsp;&nbsp;<%=month %>.<%=day %>  <%=weekEn %>
	                  	</h6>
	                </div>
					<div class="card-body">
					 	
					 	
					    <table>
					    	<c:if test="<%=overtimeSignInListSize != 0 %>">
						    	<tr>
						    	<td>开始时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>结束时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>操作&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	</tr>
					    	<%
					    		for(Overtimeapplication overtimeSignIn : overtimeSignInList){
					    	 %>
					    	 	<tr>
					    	 	<td>
					    	 	<c:set var="OworkStart" value="<%=overtimeSignIn.getOvertime_start() %>"></c:set>					
									<fmt:formatDate value="${OworkStart}" pattern="HH:mm"/>
					    	 	</td>
					    	 	<td>
					    	 	<c:set var="OworkEnd" value="<%=overtimeSignIn.getOvertime_end() %>"></c:set>					
									<fmt:formatDate value="${OworkEnd}" pattern="HH:mm"/>
					    	 	</td>
					    	 	<td><a href="director/signInOvertime?s_id=<%=sId%>&oa_id=<%=overtimeSignIn.getOa_id()%>">打卡</a></td>
					    	 	</tr>
					    	 <%
					    	 	}
					    	  %>
					    	  </c:if>
					    	  
					    	  <c:if test="<%=overtimeSignOffListSize != 0 %>">
					    	  <tr>
						    	<td>开始时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>结束时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>操作&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	</tr>
					    	 <%
					    		for(Overtimeapplication overtimeSignOff : overtimeSignOffList){
					    	 %>
					    	 	<tr>
					    	 	<td>
					    	 	<c:set var="OOworkStart" value="<%=overtimeSignOff.getOvertime_start() %>"></c:set>					
									<fmt:formatDate value="${OOworkStart}" pattern="HH:mm"/>
					    	 	</td>
					    	 	<td>
					    	 	<c:set var="OOworkEnd" value="<%=overtimeSignOff.getOvertime_end() %>"></c:set>					
									<fmt:formatDate value="${OOworkEnd}" pattern="HH:mm"/>
					    	 	</td>
					    	 	<td><a href="director/signOffOvertime?s_id=<%=sId%>&oa_id=<%=overtimeSignOff.getOa_id()%>">签退</a></td>
					    	 	</tr>
					    	 <%
					    	 	}
					    	  %>
					    	  </c:if>
					    </table>
					 	
				   </div>  
				 </div><!-- card shadow mb-4 -->
				 
				  <!-- 无需打卡的加班 -->
	              <div class="card shadow mb-4">
	                <!-- Card Header - Accordion -->
	                <a href="#collapseCardExample" class="d-block card-header py-3" data-toggle="collapse" role="button" aria-expanded="true" aria-controls="collapseCardExample">
	                  <h6 class="m-0 font-weight-bold text-primary">
	                  	全部加班&nbsp;&nbsp;&nbsp;
	                  	<%=month %>.<%=day %>  <%=weekEn %>
	                  	</h6>
	                </a>
	                <div class="collapse show" id="collapseCardExample">
	           
	                  <div class="card-body">
	                  		
	                	 <c:if test="<%=overtimeApplyListSize != 0 %>">
	                	 	<table>
	                	 		<tr>
						    	<td>开始时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>结束时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	<td>加班属性&nbsp;&nbsp;&nbsp;&nbsp;</td>
						    	</tr>
	                	 		 <%
					    		for(Overtimeapplication overtimeApply : overtimeApplyList){
					    		 %>
					    	 
					    	 	<tr>
					    	 	<td>
					    	 	<c:set var="OAworkStart" value="<%=overtimeApply.getOvertime_start() %>"></c:set>					
									<fmt:formatDate value="${OAworkStart}" pattern="HH:mm"/>
					    	 	</td>
					    	 	
					    	 	<td>
					    	 	<c:set var="OAworkEnd" value="<%=overtimeApply.getOvertime_end() %>"></c:set>					
									<fmt:formatDate value="${OAworkEnd}" pattern="HH:mm"/>
					    	 	</td>
					    	 	
					    	 	<c:if test="<%=overtimeApply.getIs_temporary() == 0 %>">
					    	 	<td>申请的加班</td>
					    	 	</c:if>
					    	 	<c:if test="<%=overtimeApply.getIs_temporary() != 0 %>">
					    	 	<td>临时性加班</td>
					    	 	</c:if>
					    	 	</tr>
					    	 <%
					    	 	}
					    	  %>
	                	 	</table>
	                	  </c:if>	
	                  </div>
	                </div>
	              </div>
           </div><!-- col-lg-6 -->
          </div><!-- row -->	
       </div><!-- container-fluid -->             
             
                	
   
			
			
 
  
  </div><!-- wrapper -->
  
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