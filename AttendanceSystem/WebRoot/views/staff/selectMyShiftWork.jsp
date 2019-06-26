<%@page import="com.as.service.impl.MessageServiceImpl"%>
<%@page import="com.as.service.MessageService"%>
<%@page import="com.as.entity.Message"%>
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
    
    <title>selectMyShiftWork.jsp</title>
    
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
	Staff staff = new Staff();
	staff = (Staff)request.getAttribute("staff");
  	ShiftWork shiftWork;
  	
  	//初值
  	Integer dep_id = 1001;
  	Integer year;
  	Integer month;
  	Integer s_id = 0;
  	
  	String sname = staff.getS_name();
  	
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
  	Staff login_staff = staffService.findStaffById(s_id);
  	
	 //消息部分
  	MessageService messageService = new MessageServiceImpl();
	int message_num = 0;
  	List<Message> messageList = messageService.selectNoReadMessageApply(s_id);
  	message_num = messageList.size();
   	%>
   	
   	<!-- Page Wrapper -->
  <div id="wrapper">
  	<!-- 侧边导航栏Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
		
	<!-- 带笑脸的标题位置 -->	
      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="staff/toHomepage?s_id=<%=s_id%>">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink">员工</i>
        </div>
        <!-- 此处可加标题之类 -->
	<div class="sidebar-brand-text mx-3"><%=dep_id %><sup></sup></div>
      </a>

 <!-- Divider -->
      <hr class="sidebar-divider my-0">
	
	<!-- 首页链接-->
      <li class="nav-item">
      <!-- 写超链处 当前页-->
        <a class="nav-link" href="staff/toHomepage?s_id=<%=s_id%>">
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
            <a class="collapse-item" href="staff/selectOvertimeRecord?s_id=<%=s_id%>">个人加班信息</a>
            <a class="collapse-item" href="staff/listAllAskforleaveBySid?s_id=<%=s_id%>">个人请假信息</a>
            <a class="collapse-item" href="staff/staffSelectStaffInfo?s_id=<%=s_id%>">个人账户信息</a>
            <a class="collapse-item" href="staff/listAllAskforleaveBySidChanged?s_id=<%=s_id%>">个人销假信息</a>
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
            <a class="collapse-item " href="staff/firstSelectMyShiftWork?usr_id=<%=s_id%>">查看工作班次</a>
            <a class="collapse-item" href="staff/selectOneStaffWorkConByMonthByDep?s_id=<%=s_id%>">查看工作情况</a>
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
            <a class="collapse-item " href="staff/selectAllTemporaryOvertime?s_id=<%=s_id%>">临时加班</a>
            <a class="collapse-item" href="staff/insertOvertimeRecord?s_id=<%=s_id%>">超时加班</a>
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
              		<a class="nav-link dropdown-toggle" href="staff/selectNoReadMessageApply?s_id=<%=s_id%>">
                		<i class="fas fa-envelope fa-fw"></i>
              		</a>
              	</c:if>
              	<c:if test="<%=message_num != 0 %>">
              		<a class="nav-link dropdown-toggle" href="staff/selectNoReadMessageApply?s_id=<%=s_id%>">
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
							<%
					 		for(int month_i=0;month_i<12;month_i++)//1月值为0，calendar里也是
					 		{
					 		%>
					 		<a href="staff/selectMyShiftWork?select_year=<%=year%>&select_month=<%=month_i%>&dep_id=<%=dep_id%>&s_id=<%=s_id%>"><%=month_i+1%>月</a>
					 		<%
					 		}
					 		%>
						</div><!--  col-6-->
						<div class="col-6" style="padding-right:0px;margin-right:0px;padding-left:250px">
							<!-- 1.select_s_id  2.若不为null更新list，为0是全部 -->	
						   	<!-- 输入dep_id month -->
						   	<form action="staff/selectMyShiftWork"  method="post">
								
								
								<!-- input type="text" name="dep_id"/><br-->
								
								<input type="hidden" name="dep_id" value="<%=dep_id%>"/>
								
								<!-- 员工id隐藏于传值 -->
								<input type="hidden" name="s_id" value="<%=s_id%>"/>
								
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
					</div><!--  row-->
				</div><!-- container -->		
            		
			</div><!-- card-header -->
			
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
					<tr>
					
						<!-- 编号 -->
						<td><%=Listnum %></td>
						<!-- 输出日期的里的day -->
						<td>
						<%=i%>日
						<!-- 输出星期几 -->
						<%=week[weekDay]%>
						</td>
					<% 
					weekDay++;
					weekDay=weekDay%7;
					//全部员工
						%>	
						<td><%=s_id%></td>
						
						<td><%=sname%></td>				
						
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
				%>	
		                  
                  
                  
                  </tbody>
                
                </table>
               </div><!--  table-responsive-->
             </div><!--  card-body-->   
			
		</div><!--  card shadow mb-4-->	
	</div><!-- container-fluid -->

</div>
</div>
</div>
						
   	
 
 	
 	
 	
 	
    
    
    
    
   
		
			

	
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