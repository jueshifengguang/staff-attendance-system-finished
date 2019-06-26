<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
<%@page import="com.as.service.impl.MessageServiceImpl"%>
<%@page import="com.as.service.MessageService"%>
<%@page import="com.as.entity.Message"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.as.entity.Staff"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.as.service.impl.ShiftWorkServiceImpl"%>
<%@page import="com.as.service.ShiftWorkService"%>
<%@page import="com.as.entity.Department"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.as.entity.ShiftWork"%>
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
    
    <title>selectAllShiftWork.jsp</title>
    
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

  	<title>主管查询工作班次</title>

  <!-- Custom fonts for this template -->
  <link href="src/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="src/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="src/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">	
	
	
	<script language="javascript" type="text/javascript" src="js/jquery-3.2.1.js"></script>
	
	
	<!-- ajax -->
	<script type="text/javascript">
	var xmlReq=null;
	function checkTime(){
		//alert("aaa");
		//创建ajax对象
		xmlReq = new XMLHttpRequest();
		//设置xmlReq的参数
		var start_hour = $("#start_hour").val();
		var end_hour = $("#end_hour").val();
		var start_minute = $("#start_minute").val();
		var end_minute = $("#end_minute").val();
		if(start_hour=="" || end_hour=="" || start_minute=="" || end_minute==""){
			return;
		}
		
		var url = "main/checkTime?start_hour="+start_hour+"&end_hour="+end_hour+"&start_minute="+start_minute+"&end_minute="+end_minute;
		//var url = "main/test";
		xmlReq.open("POST",url,true);
		//设置回调函数(状态发生变化的时候调用回调函数  0-4)
		xmlReq.onreadystatechange=echoresult;
		//发送请求
		xmlReq.send();
	}
	//回调
	function echoresult(){
		//xmlReq.readyState的值为4表示响应完毕
		//xmlReq.status的值200表示响应的结果是正确的
		//alert("回调了么");
		if(xmlReq.readyState==4 && xmlReq.status==200){
			//获取服务器返回的结果
			var resu = xmlReq.responseText;
			//alert("resu:" + resu);
			if(resu=="true"){
				document.getElementById("namespan").innerHTML="";
				document.getElementById("submitbutton").disabled="";
			}else{
				document.getElementById("namespan").innerHTML="时间输入不正确！";
				document.getElementById("submitbutton").disabled="disabled";
			}
		}
	}
	</script>
	
  </head>
  
<body id="page-top">
	<%
  	request.setCharacterEncoding("UTF-8");
	//工作安排列表
  	List<ShiftWork>	swList = (List<ShiftWork>)request.getAttribute("swList");

  	//员工列表
  	List<Staff> sList = (List<Staff>)request.getAttribute("sList");
  	Department department = null;
  	if(request.getAttribute("depart") != null){
  		department = (Department)request.getAttribute("depart");
  	}
  	
  	Integer usr_id = 101;
  	if(request.getAttribute("usr_id") != null){
  		usr_id = (Integer)request.getAttribute("usr_id");
  	}
  	if(request.getParameter("usr_id") != null){
  		usr_id = Integer.parseInt(request.getParameter("usr_id"));
  	}
  			
  	ShiftWork shiftWork = null;
  	
  	//初值
  	Integer year;
  	Integer month;
  	Integer s_id = 0;
  	String sname = "";
  	
  	//接受sid
  	if(request.getParameter("s_id")!=null){
  		s_id=Integer.parseInt(request.getParameter("s_id"));
  	}else if(request.getAttribute("s_id")!=null){
  		s_id=(Integer)request.getAttribute("s_id");
  	}
  	//
  	Integer dep_id = 1001;
  	if(request.getParameter("dep_id")!=null){
  		dep_id=Integer.parseInt(request.getParameter("dep_id"));
  	}else if(request.getAttribute("dep_id") != null){
  		dep_id = (Integer) request.getAttribute("dep_id");
  		
  	}else if(department != null){
  		dep_id = department.getDep_id();
  	}
  	//年月日初值
  	Calendar now = Calendar.getInstance();
	year = now.get(Calendar.YEAR);
	month = now.get(Calendar.MONTH);
  	
	
	//接受year
  	if(request.getParameter("select_year")!=null){
  		year=Integer.parseInt(request.getParameter("select_year"));
  	}else if(request.getAttribute("select_year")!=null){
  		year=(Integer)request.getAttribute("select_year");
  	}
  	//接受month
  	if(request.getParameter("select_month")!=null){
  		month=Integer.parseInt(request.getParameter("select_month"));
  	}else if(request.getAttribute("select_month")!=null){
  		month=(Integer)request.getAttribute("select_month");
  	}
  	 //消息部分
  	MessageService messageService = new MessageServiceImpl();
	int message_num = 0;
  	List<Message> messageList = messageService.selectNoReadMessageApply(usr_id);
  	message_num = messageList.size();
  	
  //获得登录用户的对象	

  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(usr_id);
	
	//获得今日日期
  	Calendar today = Calendar.getInstance();
  	 String yearStr = today.get(Calendar.YEAR)+"";//获取年份
     int now_month = today.get(Calendar.MONTH);//获取月份
     int now_year = today.get(Calendar.YEAR); 
	
   	%>
   	
   	
   	<!-- Page Wrapper -->
  <div id="wrapper">

    <!-- 侧边导航栏Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
		
	<!-- 带笑脸的标题位置 -->	
      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink">主管</i>
        </div>
        <!-- 此处可加标题之类 -->
        <div class="sidebar-brand-text mx-3"><%=department.getDep_name() %><%=dep_id %><sup></sup></div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">
	
	<!-- 首页链接-->
      <li class="nav-item">
      <!-- 写超链处 -->
        <a class="nav-link" href="director/toHomepage?s_id=<%=usr_id %>">
        <!-- 引用图标-->
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span style="font-size:24px">首页</span></a>
      </li>

      <!-- Divider -->
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
            <a class="collapse-item active" href="director/firstSelectAllShiftWork?usr_id=<%=usr_id%>">工作班次安排</a>
            <a class="collapse-item" href="director/selectAllStaffWorkConByMonthByDep?s_id=<%=usr_id%>">查看工作情况</a>
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
            <a class="collapse-item" href="director/selectNoncheckedOvertimeApply?s_id=<%=usr_id%>">加班审批</a>
            <a class="collapse-item" href="director/selectNoncheckedAskforApplyByDep?s_id=<%=usr_id%>">请假审批</a>
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
            <a class="collapse-item" href="director/selectAllShiftWork?s_id=<%=usr_id%>&usr_id=<%=usr_id %>&dep_id=<%=dep_id%>&select_year=<%=now_year%>&select_month=<%=now_month%>">个人工作班次信息</a>
            <a class="collapse-item" href="director/selectOneStaffWorkConByMonthByDep?s_id=<%=usr_id %>&select_sid=<%=usr_id%>">个人工作情况信息</a>
            <a class="collapse-item" href="director/selectOvertimeRecord?s_id=<%=usr_id%>">个人加班信息</a>
            <a class="collapse-item" href="director/listAllAskforleaveBySid?s_id=<%=usr_id%>">个人请假信息</a>
            <a class="collapse-item" href="director/directorSelectStaffInfo?s_id=<%=usr_id%>">用户信息修改</a>
          </div>
        </div>
      </li>
      
       <!-- 临时加班-->
      <li class="nav-item">
        <a class="nav-link" href="director/selectAllTemporaryOvertime?s_id=<%=usr_id%>">
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


          <!-- 页面上方一键修改 -->
     	<form action="director/changeAllShiftWork" method="post" class="d-sm-inline-block" style="padding-top:5px">
 			<!-- input type="image"  src="src/vendor/datatables/1.jpg"-->
 			<input type="hidden" name="usr_id" value="<%=usr_id%>" />
	 		<!-- 隐藏域----部门 -->
	 		<input type="hidden"  name="dep_id"   value="<%=dep_id%>"/>
	 		<!-- 隐藏域----年月 -->
	 		<input type="hidden"  name="select_year"   value="<%=year%>"/>
	 		<input type="hidden"  name="select_month" value="<%=month%>"/>
	 		<!-- 隐藏域----当前选中员工 -->
	 		<input type="hidden"   name="s_id"  value = "<%=s_id%>"/>
 			<!-- 周几到周几 -->
        	<input type="checkbox" name="weekday" value="2"  checked="checked" style="margin-left:36px;">星期一 &nbsp;  
        	<input type="checkbox" name="weekday" value="3"  checked="checked">星期二 &nbsp; 
        	<input type="checkbox" name="weekday" value="4"  checked="checked">星期三 &nbsp;  
        	<input type="checkbox" name="weekday" value="5"  checked="checked">星期四 &nbsp; 
        	<input type="checkbox" name="weekday" value="6"  checked="checked">星期五 &nbsp;  
        	<input type="checkbox" name="weekday" value="7" >星期六 &nbsp; 
        	<input type="checkbox" name="weekday" value="1" >星期日 &nbsp;
 			<!-- 用户输入时间 -->
 			<!-- 限制输出数字以及大小 -->
 			<script>
  				function shuru(txt){
    				txt.value=txt.value.replace(/\D/g,"");
  				}
			</script>
			<div  class="container">
				<div class="row" style="margin:0px;padding:0px">
					<div class="input-group input-group-sm col" >
						<div class="input-group-prepend " style="height:31px;">
    						<span class="input-group-text" id="">开始时间</span>
  						</div>
						<input type="text" name="start_hour" id="start_hour"   onkeyup="shuru(this);" oninput="if(value>23)value=23;if(value<0)value=0"   class="form-control"  style=";" placeholder="时"/>
						<input type="text" name="start_minute" id="start_minute" onkeyup="shuru(this);" oninput="if(value>59)value=59;if(value<0)value=0"   class="form-control"  style=";"placeholder="分"/>
					</div>
					<div class="input-group input-group-sm col" >
						<div class="input-group-prepend" style="height:31px;">
    						<span class="input-group-text" id="">结束时间</span>
  						</div>
						<input type="text" name="end_hour"  id="end_hour"  onchange="checkTime()" onkeyup="shuru(this);" oninput="if(value>23)value=23;if(value<0)value=0"   class="form-control"  style=";" placeholder="时"/>
						<input type="text" name="end_minute" id="end_minute"  onchange="checkTime()"  onkeyup="shuru(this);" oninput="if(value>59)value=59;if(value<0)value=0"  class="form-control"  style=";" placeholder="分"/>
					</div>

					<div class="col">
 						<input type="submit"   disabled="disabled" id="submitbutton"  value="一键修改"   class="btn btn-secondary" style="height:31px;padding-top:3px;padding-bottom:3px"/>
					</div>
				</div>
			</div>		
			<!-- 错误时间输入 -->
			<span id="namespan" style="height:15px;max-width:100px;font-size:14px;color:red;"></span>						
          </form>   


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
          
         
            <!-- 小铃铛消息超链-->
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-bell fa-fw"></i>
                <!-- Counter - Alerts -->
               <!-- 消息数量显示小红点 -->
                <span class="badge badge-danger badge-counter">3+</span>
              </a>
             
             <!-- 信封消息超链-->
            <li class="nav-item dropdown no-arrow mx-1">
            	<c:if test="<%=message_num == 0 %>">
              		<a class="nav-link dropdown-toggle" href="director/selectReadMessageApply?s_id=<%=usr_id%>">
                		<i class="fas fa-envelope fa-fw"></i>
              		</a>
              	</c:if>
              	<c:if test="<%=message_num != 0 %>">
              		<a class="nav-link dropdown-toggle" href="director/selectNoReadMessageApply?s_id=<%=usr_id%>">
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
   	
   	
   	   	
 	
 	
 	
 
 	 
 	
 	
 	
    
    
    
    <!-- 主体显示开始 -->
     <div class="container-fluid">
     	<!-- Page Heading -->
     		<div  class="container">
				<div class="row" style="">
					
					<div calss="col-6" >
          				<h1 class="h3 mb-2 text-gray-800"><%=month+1%>月 </h1>
          				<h6 class="h6 text-gray-800">工作班次安排</h6>					
					</div>
					
				</div>
			</div>		
					         
        <!-- 表格显示开始 -->
    	<!-- DataTales Example -->
        <div class="card shadow mb-4">
            <div class="card-header py-3 " style="padding-right:0px;margin-right:0px;">
            
            		<div  class="container" style="padding-right:0px;margin-right:0px">
						<div class="row" style="">
					
							<div  class="col-6">
								 <!-- 从页面获取年月 -->
 									<%
							 		for(int month_i=0;month_i<12;month_i++)//1月值为0，calendar里也是
							 		{
							 		%>
							 		<a href="director/selectAllShiftWork?select_year=<%=year%>&select_month=<%=month_i%>&dep_id=<%=dep_id%>&s_id=<%=s_id%>&usr_id=<%=usr_id%>"><%=month_i+1%>月</a>
							 		<%
							 		}
							 		%>
 									<br>
 									<!-- 从页面获取员工id 改成Staff-->
 
									<a href="director/selectAllShiftWork?select_year=<%=year%>&select_month=<%=month%>&dep_id=<%=dep_id%>&s_id=0&usr_id=<%=usr_id%>">全部员工</a> 		
									<%
							 		for (Staff staff : sList) {
							 		%>	
							 		<a href="director/selectAllShiftWork?select_year=<%=year%>&select_month=<%=month%>&dep_id=<%=dep_id%>&s_id=<%=staff.getS_id()%>&usr_id=<%=usr_id%>"><%=staff.getS_id()%><%=staff.getS_name()%></a>
									<%
							 		}
									%>
							</div><!-- col -->
							
							<div class="col-6" style="padding-right:0px;margin-right:0px;padding-left:250px">
								<!-- 1.select_s_id  2.若不为null更新list，为0是全部 -->	
   								<!-- 输入dep_id month -->
   								<form action="director/selectAllShiftWork"  method="post">
								<!-- input type="text" name="dep_id"/><br>
									<!-- 员工id隐藏于传值 -->
									<input type="hidden" name="s_id" value="<%=s_id%>"/>
									<input type="hidden" name="usr_id" value="<%=usr_id%>" />
									<!-- 月份隐藏于传值 -->
									<input type="hidden" name="select_month" value="<%=month%>"/>	
									
									<div class="container" style="margin-leftpadding-right:0px;margin-right:0px">
										<div class="row"  style="padding-right:0px;margin-right:0px">
											<div class="col-3">
												<!-- 年份选择 -->
												<c:set var="yearNow" value="<%=year %>"></c:set>
												<select name="select_year"  class="form-control" style="font-size:14px;width:90px;height:32px;padding-top:5px;padding-left:2px;">
														<c:forEach var="year" begin="1949" end="2049" step="1">
														<c:if test="${year==yearNow}">
															<option value="${year }" selected="selected">${year }年</option>
														</c:if>
														<c:if test="${year!=yearNow}">
															<option value="${year }">${year }年</option>
														</c:if>
											   		</c:forEach>
												</select>
											</div>
											
											<div class="col-1" style="padding-right:0px;margin-right:0px;padding-left:60px;">
												<input type="submit" value="搜索"   class="btn btn-secondary" style="margin-top:2px;font-size:18px;height:28px;padding-top:0px"/>	
											</div>
											
										</div>
									</div>
									
							</form>
						</div>
							
							
						</div><!-- row -->
					</div><!-- container -->		
            </div><!-- card-header py-3 -->
            
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
     			
     				<!-- 表头-->
     				<thead>
                    <tr>
                    <th>编号</th>
                      <th>日期</th>
                      <th>工号</th>
                      <th>姓名</th>
                      <th>开始时间</th>
                      <th>结束时间</th>
                      <th>操作</th>
                    </tr>
                  </thead>
     			  <!-- 页脚 -->		
     			  <tfoot>
                    <tr>
                    <th>编号</th>
                      <th>日期</th>
                      <th>工号</th>
                      <th>姓名</th>
                      <th>开始时间</th>
                      <th>结束时间</th>
                      <th>操作</th>
                    </tr>
                  </tfoot>
                  
                  <!-- 主体显示 -->
                   <tbody>
                     <%
						now.clear();
						now.set(year, month, 1);//1月取出的month是0
						int lastDay = now.getActualMaximum(Calendar.DAY_OF_MONTH); 
						int weekDay = now.get(Calendar.DAY_OF_WEEK);
						String []week = {"星期六","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};//1---星期日  7---星期六
						int Listnum = 0;
						%>
						<%for(int i=1;i<=lastDay;i++){ %>
							<% 
							
							weekDay=weekDay%7;
							weekDay++;
							//全部员工
							if(s_id.equals(0)){
								for(int j=0;j<sList.size();j++){
								%>
								<tr>
								<!-- 编号 -->
								<td><%=Listnum %></td>
								<!-- 日期 -->
								<td>
									<!-- 输出日期的里的day -->
									<%=i%>日
									<!-- 输出星期几 -->
									<%=week[weekDay-1]%>
								</td>	
								
								<!-- 工号 -->
								<td><%=sList.get(j).getS_id()%></td>
								
								<!-- 姓名 -->
								<td><%=sList.get(j).getS_name()%></td>
								
								<!-- 开始时间 -->
								<td>
									<!-- 下午00：00 --> 
									<!--fmt:formatDate value="${workStart}" type="time" timeStyle="short"/-->
									<!-- 开始时间 00：00 -->
									<c:set var="workStart" value="<%=swList.get(Listnum).getWorking_start() %>"></c:set>					
									<c:if test="${workStart!=null}">
										<fmt:formatDate value="${workStart}" pattern="HH:mm"/>
									</c:if>
									<!-- 空值输出 -->
									<c:if test="${workStart==null}">
										null
									</c:if>
								</td>
					
								<!-- 结束时间00：00 -->
								<td>
									<c:set var="workEnd" value="<%=swList.get(Listnum).getWorking_end() %>"></c:set>					
									<c:if test="${workEnd!=null}">
										<fmt:formatDate value="${workEnd}" pattern="HH:mm"/>
									</c:if>
									<c:if test="${workEnd==null}">
										null
									</c:if>	
								</td>
								
								<!-- 操作-->
								<td>
									<c:if test="${workStart==null}">
										<a href="director/showAddNewShiftWork?dep_id=<%=dep_id%>&s_id=<%=sList.get(j).getS_id()%>&s_name=<%=sList.get(j).getS_name()%>&work_year=<%=year%>&work_month=<%=month%>&work_date=<%=i%>&s_id_now=<%=s_id%>&usr_id=<%=usr_id%>">新增</a>
									</c:if>
									<c:if test="${workStart!=null}">
										<a href="director/showUpdateShiftWork?sw_id=<%=swList.get(Listnum).getSw_id()%>&s_id_now=<%=s_id%>&usr_id=<%=usr_id%>">修改</a>
										<a href="director/deleteShiftWork?sw_id=<%=swList.get(Listnum).getSw_id()%>&dep_id=<%=dep_id%>&s_id_now=<%=s_id%>&select_year=<%=year%>&select_month=<%=month%>&usr_id=<%=usr_id%>">删除</a>
									</c:if>
								</td>
							</tr>	
				
							<%
							
							Listnum++;
						}
					}
					else{
						
						for(int j=0;j<sList.size();j++){
							if(s_id.equals(sList.get(j).getS_id())){
								sname = sList.get(j).getS_name();
							}
						}	
						%>
							<tr>
								<!-- 编号 -->
								<td><%=Listnum %></td>
								<!-- 日期 -->
								<td>
									<!-- 输出日期的里的day -->
									<%=i%>日
									<!-- 输出星期几 -->
									<%=week[weekDay-1]%>
								</td>	
								
								<!-- 工号 -->
								<td><%=s_id%></td>
								
								<!-- 姓名 -->
								<td><%=sname%></td>				
								<!-- 下午00：00 --> 
								<!--fmt:formatDate value="${workStart}" type="time" timeStyle="short"/-->
								
								<!-- 开始时间 00：00 -->
								<td>
									<c:set var="workStart" value="<%=swList.get(Listnum).getWorking_start() %>"></c:set>					
									<c:if test="${workStart!=null}">
										<fmt:formatDate value="${workStart}" pattern="HH:mm"/>
									</c:if>
									<!-- 空值输出 -->
									<c:if test="${workStart==null}">
										null
									</c:if>
								</td>
				
				
								<!-- 结束时间 -->
								<td>
									<c:set var="workEnd" value="<%=swList.get(Listnum).getWorking_end()%>"></c:set>					
									<c:if test="${workEnd!=null}">
										<fmt:formatDate value="${workEnd}" pattern="HH:mm"/>
									</c:if>
									<c:if test="${workEnd==null}">
										null
									</c:if>	
								</td>
								
								
								<!-- 删除修改链接 -->
								<td>
									<c:if test="${workStart==null}">
										<a href="director/showAddNewShiftWork?dep_id=<%=dep_id%>&s_id=<%=s_id%>&s_name=<%=sname%>&work_year=<%=year%>&work_month=<%=month%>&work_date=<%=i%>&s_id_now=<%=s_id%>&usr_id=<%=usr_id%>">新增</a>
									</c:if>
									<c:if test="${workStart!=null}">
										<a href="director/showUpdateShiftWork?sw_id=<%=swList.get(Listnum).getSw_id()%>&s_id_now=<%=s_id%>&usr_id=<%=usr_id%>">修改</a>
										<a href="director/deleteShiftWork?sw_id=<%=swList.get(Listnum).getSw_id()%>&dep_id=<%=dep_id%>&s_id_now=<%=s_id%>&select_year=<%=year%>&select_month=<%=month%>&usr_id=<%=usr_id%>">删除</a>
									</c:if>
								</td>
							</tr>	
				
							<%
							
							Listnum++;
						}
					}
					%>
				</tbody>
                     
                    
                  
     			</table><!-- class="table table-bordered -->
     		  </div><!--  table-responsive-->
     		</div><!--  card-body-->
     	</div><!-- card shadow mb-4-->		
     </div><!-- class="container-fluid" -->

 
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