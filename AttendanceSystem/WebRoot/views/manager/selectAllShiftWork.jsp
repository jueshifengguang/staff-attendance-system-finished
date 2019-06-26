<%@page import="com.as.service.impl.StaffServiceImpl"%>
<%@page import="com.as.service.StaffService"%>
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
	
  </head>
  
<body>
	<%
  	request.setCharacterEncoding("UTF-8");
	//工作安排列表
  	List<ShiftWork>	swList = (List<ShiftWork>)request.getAttribute("swList");
  	//部门列表
	List<Department> depList = (List<Department>)request.getAttribute("depList");
  	//员工列表
  	List<Staff> sList = (List<Staff>)request.getAttribute("sList");
  	Department department;
  	ShiftWork shiftWork;
  	
  	//初值
  	Integer dep_id = 1001;
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
  	//接受depid
  	if(request.getParameter("dep_id")!=null){
  		dep_id=Integer.parseInt(request.getParameter("dep_id"));
  	}else if(request.getAttribute("dep_id")!=null){
  		dep_id=(Integer)request.getAttribute("dep_id");
  	}
  	Integer usr_id = 101;
  	//接受depid
  	if(request.getParameter("usr_id")!=null){
  		usr_id=Integer.parseInt(request.getParameter("usr_id"));
  	}else if(request.getAttribute("usr_id")!=null){
  		usr_id=(Integer)request.getAttribute("usr_id");
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
  	
  	 //获得登录用户的对象	

  	StaffService staffService = new StaffServiceImpl();
  	Staff login_staff = staffService.findStaffById(usr_id);
   	%>
   	<!-- Page Wrapper -->
   <div id="wrapper">
      <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="manager/toHomepage?s_id=<%=usr_id%>">
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
        <a class="nav-link" href="manager/toHomepage?s_id=<%=usr_id%>">
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
        <a class="nav-link collapsed" href="manager/managerSelectSelfStaffInfo?s_id=<%=usr_id%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>我的账户信息</span>
        </a>
     </li>
     <li class="nav-item">
        <a class="nav-link collapsed" href="manager/managerSelectStaffInfo?s_id=<%=usr_id%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>查看员工信息</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="manager/firstSelectAllShiftWork?usr_id=<%=usr_id%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>员工工作班次</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="manager/selectAllStaffWorkConByMonthByDep?s_id=<%=usr_id%>">
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
        <a class="nav-link collapsed" href="manager/selectAllTemporaryOvertime?s_id=<%=usr_id%>">
          <i class="fas fa-fw fa-wrench"></i>
          <span>查看临时加班</span>
        </a>
     </li>
     <li class="nav-item">
        <a class="nav-link collapsed" href="manager/addNewTemporaryOvertimeJump?s_id=<%=usr_id%>">
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
          <!-- 1.select_s_id  2.若不为null更新list，为0是全部 -->	
   	<!-- 输入dep_id month -->
   
          <form action="manager/selectAllShiftWork"  method="post" class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            	<!-- 员工id隐藏于传值 -->
		<input type="hidden" name="s_id" value="<%=s_id%>"/>
		<input type="hidden" name="usr_id" value="<%=usr_id%>">
		<!-- 月份隐藏于传值 -->
		<input type="hidden" name="select_month" value="<%=month%>"/><br>
	
		<div class="container" style="padding-left:0px;margin-left:0px">
			<div class="row"  style="padding-right:0px;margin-right:0px">
				
				<div class="col-5">
					<!-- 部门选择 -->
					<c:set var="depidNow" value="<%=dep_id %>"></c:set>
					
					<select name="dep_id"  class="form-control" style="font-size:14px;width:130px;height:32px;padding-top:5px;padding-left:2px;">
						<c:forEach var="department" items="${depList}">
							<c:if test="${department.dep_id == depidNow}">
								<option value="${department.dep_id }" selected="selected">${department.dep_name }</option>
							</c:if>
							<c:if test="${department.dep_id!=depidNow}">
								<option value="${department.dep_id }">${department.dep_name }</option>
							</c:if>
						</c:forEach> 
					</select>
				</div>
				
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
				
				<div class="col-1">
					<input type="submit" value="搜索"  class="btn btn-secondary" style="margin-top:2px;font-size:18px;height:28px;padding-top:0px"/>
				</div>
			</div>
		</div>		
		
          </form>



		
          
          
 	
    



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
              <a class="nav-link dropdown-toggle" href="manager/toHomepage?s_id=<%=usr_id%>" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=login_staff.getS_name() %></span>
              </a>
              <!-- Dropdown - User Information -->
              <!-- 已删除 -->
            </li>

          </ul>

        </nav><!-- 顶部导航栏结束-->
   	
  
 	
	
    
    
    
    <!-- 主体显示 -->
      <!-- 主体显示开始 -->
     <div class="container-fluid">
     	<!-- Page Heading -->
     		<div  class="container">
				<div class="row" style="">
					
					<div calss="col-6" >
          				<h1 class="h3 mb-2 text-gray-800"><%=month+1%>月 </h1>
          				<h6 class="h6 text-gray-800">查看工作班次</h6>					
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
							 		<a href="manager/selectAllShiftWork?select_year=<%=year%>&select_month=<%=month_i%>&dep_id=<%=dep_id%>&s_id=<%=s_id%>&usr_id=<%=usr_id%>"><%=month_i+1%>月</a>
							 		<%
							 		}
							 		%>
							</div><!-- col -->
							
							<div class="col-6" style="padding-right:0px;margin-right:0px;padding-left:250px">
						</div>
					</div><!-- row -->
				</div><!-- container -->		
            </div><!-- card-header py-3 -->
           
            <!-- 输出 -->
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
					<%for(int i=1;i<=lastDay;i++){
						weekDay++;
						weekDay=weekDay%7;
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
									<%=week[weekDay]%>
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
									<%=week[weekDay]%>
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