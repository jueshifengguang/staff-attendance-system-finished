package com.as.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.as.entity.Askforleave;
import com.as.entity.Attendance;
import com.as.entity.Department;
import com.as.entity.Overtimeapplication;
import com.as.entity.ShiftWork;
import com.as.entity.Staff;
import com.as.entity.Temporaryovertime;
import com.as.entity.WorkCondition;
import com.as.service.AskforleaveService;
import com.as.service.AttendanceService;
import com.as.service.DepartmentService;
import com.as.service.MessageService;
import com.as.service.OvertimeapplicationService;
import com.as.service.ShiftWorkService;
import com.as.service.StaffService;
import com.as.service.TemporaryovertimeService;
import com.as.service.impl.AskforleaveServiceImpl;
import com.as.service.impl.AttendanceServiceImpl;
import com.as.service.impl.DepartmentServiceImpl;
import com.as.service.impl.MessageServiceImpl;
import com.as.service.impl.OvertimeapplicationServiceImpl;
import com.as.service.impl.ShiftWorkServiceImpl;
import com.as.service.impl.StaffServiceImpl;
import com.as.service.impl.TemporaryovertimeServiceImpl;

/**
 * 经理控制器
 * @author dzz
 * 2019年6月5日 下午8:47:14
 * AttendanceSystem
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	//依赖service
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
	private AskforleaveService askforleaveService = new AskforleaveServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private DepartmentService departmentService = new DepartmentServiceImpl();
	private MessageService messageService = new MessageServiceImpl();
	
	//————————————员工部分————————————
	
	//经理跳转到查看自己的信息的页面
	@RequestMapping("/managerSelectSelfStaffInfoJump")
	public String managerSelectSelfStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//传参
		request.setAttribute("s_id", sId);
		
		return "/manager/managerSelectSelfStaffInfo";
	}
	
	//查看自己的所有账户信息
	@RequestMapping("/managerSelectSelfStaffInfo")
	public String findStaffById(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
        //调用查询方法
    	Staff staff = staffService.findStaffById(sId);
						
		//传参
		request.setAttribute("staff", staff);
        request.setAttribute("s_id", sId);
		
		//跳转
		return "/manager/managerSelectSelfStaffInfo";
	}
	
	//跳转到修改自己的信息的页面的方法
	@RequestMapping("/managerUpdateSelfStaffInfoJump")
	public String managerUpdateSelfStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
			
		}
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "/manager/managerUpdateSelfStaffInfo";
	}
	
	//更新自己的账户信息	
	@RequestMapping("/managerUpdateSelfStaffInfo")
	public String updateStaffInfo(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		
		//获得有关员工信息页面传来的参数
		String getS_pass = request.getParameter("s_pass");
		
		//把参数放到Map中
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",sId);
		staffMap.put("s_pass", getS_pass);

		System.out.println(getS_pass);
		
		//调用更新的方法
		staffService.updateStaffInfoReturnId(staffMap);
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		return "redirect:/manager/managerSelectSelfStaffInfo";
	}
	
	//经理查看员工信息
	@RequestMapping("/managerSelectStaffInfo")
	public String selectAllStaff(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
			request.setCharacterEncoding("UTF-8");
	        
	        Integer dep_id = 1001;

			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
	        if(request.getParameter("dep_id") != null){	//页面传来的参数
	        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				if(session.getAttribute("dep_id") != null) {
					//dep_id = (Integer)session.getAttribute("dep_id");
					if(session.getAttribute("dep_id") != null){
						dep_id = Integer.parseInt((String )session.getAttribute("dep_id"));
					}
				}
			}
			//按部门id查找该部门所有员工列表
			List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
			
			//查找所有部门的list
			List<Department> departmentList = departmentService.selectAllDepartment();
			
			//System.out.println("       "+dep_id);
	        //传参
	        request.setAttribute("staffList", staffList);
	        request.setAttribute("departmentList", departmentList);
	        request.setAttribute("s_id", sId);
	        //跳转
		    return "/manager/managerSelectStaffInfo";
	}
	
	//经理通过部门id查看员工信息
	@RequestMapping("/managerSelectStaffInfoByDepid")
	public String managerSelectStaffInfoByDepid(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
			request.setCharacterEncoding("UTF-8");

			//获得员工信息相关页面传来的参数
			String getId = request.getParameter("s_id");
			String getS_name = request.getParameter("s_name");
			String getEntry_time = request.getParameter("entry_time");
			String getDep_id = request.getParameter("dep_id");
			String getIdentity = request.getParameter("identity");
			String getS_pass = request.getParameter("s_pass");
			
			//把参数放到Map中
			HashMap<String, Object> staffMap = new HashMap<String, Object>();
			staffMap.put("s_id",getId);
			staffMap.put("s_name", getS_name);
			staffMap.put("entry_time", getEntry_time);
			staffMap.put("dep_id", getDep_id);
			staffMap.put("identity", getIdentity);
			staffMap.put("s_pass", getS_pass);
			
			System.out.println("controller:"+getIdentity);
			
			//获得session的参数
	        Integer dep_id = 1001;
	        if(request.getParameter("dep_id") != null){	//页面传来的参数
	        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				dep_id = (Integer)session.getAttribute("dep_id");
			}

			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}

			//查找所有部门的list
			List<Department> departmentList = departmentService.selectAllDepartment();
			//按部门id查找该部门所有员工列表
			List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
			
	        //传参
	        request.setAttribute("staffList", staffList);
	        request.setAttribute("departmentList", departmentList);
	        request.setAttribute("s_id", sId);
	        request.setAttribute("dep_id", dep_id);
			HttpSession session = request.getSession();
			session.setAttribute("dep_id", dep_id);
	        //跳转
		    return "/manager/managerSelectStaffInfo";
	}
	
	@RequestMapping("/insertNewStaffInfoJump")
	public String insertNewStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}     
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//页面传来的参数
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
		System.out.println(dep_id);
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("dep_id", dep_id);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		
		return "/manager/insertNewStaffInfo";
	}
	
	//新增员工信息
	@RequestMapping("/insertNewStaffInfo")
	public String insertNewStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//获得参数
		Integer select_id = 0;
		if(request.getParameter("select_id") != null){	//页面传来的参数
			select_id = Integer.parseInt(request.getParameter("select_id"));
		}
        
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//页面传来的参数
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
		
		//获得员工信息相关页面传来的参数
		//String getId = request.getParameter("s_id");
		String getS_name = request.getParameter("s_name");
		String getEntry_time = request.getParameter("entry_time");
		String getIdentity = request.getParameter("identity");
		String getS_pass = request.getParameter("s_pass");
		
		//把参数放到Map中
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",select_id);
		staffMap.put("s_name", getS_name);
		staffMap.put("entry_time", getEntry_time);
		staffMap.put("identity", getIdentity);
		staffMap.put("s_pass", getS_pass);
		//按部门id查找该部门所有员工列表
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		Integer identity_int = Integer.parseInt(getIdentity);
		if(identity_int.equals(2)){
			//给已有主管降职
			for(Staff staff: staffList) {
				if(staff.getIdentity()==2) {
					//break;	
					HashMap<String, Object> updateMap = new HashMap<String, Object>();
					updateMap.put("identity", 1);
					updateMap.put("s_id", staff.getS_id());
					staffService.updateStaffInfoReturnId(updateMap);					
					
				}
			}
		}
		
		
		System.out.println("controller拿到的名字是什么："+getS_name);
		
		//调用新增员工信息的方法
		staffService.insertNewStaffInfoReturnId(staffMap);
		request.setAttribute("dep_id", dep_id);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		session.setAttribute("s_id", sId);
		
		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	//删除员工信息接参
	@RequestMapping("/deleteStaffInfoJump")
	public String deleteStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//传参
		request.setAttribute("s_id", sId);
		
		return "/manager/deleteStaffInfo";
	}
	
	//删除员工信息
	@RequestMapping("/deleteStaffInfo")
	public String deleteStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//获得员工信息相关页面传来的参数
		String getId = request.getParameter("s_id");
		
		//调用删除的方法
		Integer s_id1 = Integer.parseInt(getId);
		staffService.deleteStaffInfoReturnId(s_id1);
		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	//更新员工信息接参
	@RequestMapping("/managerUpdateStaffInfoJump")
	public String managerUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
        Integer select_sId = 101;
        if(request.getParameter("select_sId") != null){	//页面传来的参数
        	select_sId = Integer.parseInt(request.getParameter("select_sId"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			select_sId = (Integer)session.getAttribute("select_sId");
		}
        
        //获得要修改的对象
        Staff select_staff = staffService.findStaffById(select_sId);
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("staff1", select_staff);
		
		return "/manager/managerUpdateStaffInfo";
	}
	
	//更新员工信息
	@RequestMapping("/managerUpdateStaffInfo")
	public String updateStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");

		//获得员工信息相关页面传来的参数
		String getId = request.getParameter("s_id");
		String getS_name = request.getParameter("s_name");
		String getEntry_time = request.getParameter("entry_time");
		String getDep_id = request.getParameter("dep_id");
		String getIdentity = request.getParameter("identity");
		String getS_pass = request.getParameter("s_pass");
		System.out.println(getS_pass);
		
		System.out.println("sname是什么啊！！！！！！！！！！！！！！！！");
		System.out.println("得到的sName是什么："+getS_name);
		
		
		//System.out.println("接到的identity："+getIdentity);
		
		//把参数放到Map中
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",getId);
		//staffMap.put("s_name", getS_name);
		staffMap.put("entry_time", getEntry_time);
		staffMap.put("dep_id", getDep_id);
		staffMap.put("identity", getIdentity);
		staffMap.put("s_pass", getS_pass);
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//页面传来的参数
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
        Staff staff1 = staffService.findStaffById(sId);
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		Integer identity_int = Integer.parseInt(getIdentity);
		
		if(identity_int.equals(2)){
			//给已有主管降职
			for(Staff staff: staffList) {
				if(staff.getIdentity()==2) {	
					HashMap<String, Object> updateMap = new HashMap<String, Object>();
					updateMap.put("identity", 1);
					updateMap.put("s_id", staff.getS_id());
					staffService.updateStaffInfoReturnId(updateMap);					
					
				}
			}
		}
		
		//调用新增的方法
		staffService.updateStaffInfoReturnId(staffMap);
		request.setAttribute("staff1", staff1);
		request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		session.setAttribute("s_id", sId);
		request.setAttribute("dep_id", dep_id);

		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	
	//————————————员工部分结束————————————
	
	
	//给员工发的，提醒他们发布了新的临时性加班
	public String addMessageToAllStaffForTmpOvertime(){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    //Staff applyStaff = staffService.findStaffById(s_id);
	    //String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "经理发布了新的全单位临时性加班，快去查看吧！！";
	    
	    //获得全单位的员工
	    List<Staff> staffAllList = staffService.selectAllStaff();
	    for (Staff staff : staffAllList) {
	    	HashMap<String, Object> toMap = new HashMap<String, Object>();
		    
		    toMap.put("s_id", staff.getS_id());
		    toMap.put("m_time", m_time);
		    toMap.put("is_read", is_read);
		    toMap.put("m_content", m_content);
		    messageService.insertNewMessage(toMap);
		}
	    	  							
		return null;
	}
		
		
	
	//查看所有可以申请的临时加班
	@RequestMapping("/selectAllTemporaryOvertime")
	public String selectAllTemporaryOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		List<Temporaryovertime> tmpOvertimeNowList = tmpOvertimeService.selectTmpOvertimeByNowDate();
		
		//传参
		request.setAttribute("tmpOvertimeNowList", tmpOvertimeNowList);
		request.setAttribute("s_id", sId);
		
		//跳转
		return "manager/selectAllTemporaryOvertime";
	}
	
	@RequestMapping("/addNewTemporaryOvertimeJump")
	public String addNewTemporaryOvertimeJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//传参
		request.setAttribute("s_id", sId);
		
		return "manager/addNewTemporaryOvertime";
	}
	
	@RequestMapping("/addNewTemporaryOvertime")
	public String addNewTemporaryOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//获得页面传来的参数
		String getStartTime = request.getParameter("t_overtime_start");
		String getEndTime = request.getParameter("t_overtime_end");
		String t_o_reason = request.getParameter("t_o_reason");
		
		//把参数放到Map中
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("t_overtime_start", getStartTime);
		toMap.put("t_overtime_end", getEndTime);
		toMap.put("t_o_reason", t_o_reason);
		
		//调用新增的方法
		tmpOvertimeService.insertTempOvertime(toMap);
		
		//调用发消息的方法，通知全单位员工
		addMessageToAllStaffForTmpOvertime();
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "redirect:/manager/selectAllTemporaryOvertime";
		//return "manager/selectAllTemporaryOvertime";
	}
	
	@RequestMapping("/deleteTempOvertime")
	public String deleteTempOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		Integer to_id = 0;
		if(request.getParameter("to_id") != null){
			to_id = Integer.parseInt(request.getParameter("to_id"));
		}
		
		//调用删除方法
		//先判断有没有这个活动
		Temporaryovertime selectTmpOvertime = tmpOvertimeService.findTempOvertimeByToid(to_id);
		if(selectTmpOvertime != null){	//确认删除
			tmpOvertimeService.deleteTempovertimeByToid(to_id);
		}
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "manager/selectAllTemporaryOvertime";
		return "redirect:/manager/selectAllTemporaryOvertime";
	}
	
	//返回首页的方法
	@RequestMapping("/toHomepage")
	public String toHomepage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//传参
		request.setAttribute("s_id", sId);
		
		return "manager/homepage";
	}
	
	
	
	
	
	//根据年月日属性，拼接成规范格式的日期形式
	public static String getDateStrByYearMonthDay(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		//↓所以到底为什么要-1啊？？？？？？？？？？？？？？？？？？？？？？？？？
		cal.set(year, month-1, day);	//设置日期
		//进行格式化
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		String format_date = sdf_date.format(cal.getTime());
		return format_date;
	}
	
	//点击查看本部门工作情况后，在这里整理本部门所有的工作情况，跳转到显示工作情况的页面
	@RequestMapping("/selectAllStaffWorkConByMonthByDep")
	public String selectAllStaffWorkConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 101;
		if(request.getParameter("s_id") != null){
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		Integer select_dep_id = staffService.findDepidBySid(sId);
		if(request.getParameter("select_dep_id") != null){
			select_dep_id = Integer.parseInt(request.getParameter("select_dep_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("select_dep_id") != null){
				select_dep_id = (Integer)session.getAttribute("select_dep_id");
			}
		}
		
		Calendar cal = Calendar.getInstance();
		
		//默认当前年
		Integer year = cal.get(Calendar.YEAR);
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		}
		//默认当前月
		Integer month = cal.get(Calendar.MONTH)+1;
		if(request.getParameter("month") != null){
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		cal.set(year, month, 0);
		//这个是对的，天数
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//获得这一年这个月一共有多少天
		
		int out_i = 1;
		int inner_i = 1;
		int index = 0;
		
		//得到该月第一天和下个月第一天
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar month_begin_cal = Calendar.getInstance();
		//month_begin_cal.set(year, month, 1);	//这句按理说才是对的啊
		month_begin_cal.set(year, month-1, 1);
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//该月第一天的String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//月份+1		//按理说应该有这句话啊
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//下个月第一天的String
		
		System.out.println("该月第一天："+begin_date_str+"，下个月第一天："+end_date_str);
		
		//给jsp判断的空值
		String emptyStr = "2019-01-01 00:00:00";
		Timestamp empty_date = Timestamp.valueOf(emptyStr);
		
		
		//获得部门id
		//Integer dep_id = staffService.findDepidBySid(sId);	//获得该主管对应的dep_id
		//该部门所有员工List
		List<Staff> staffList = staffService.selectStaffByDepId(select_dep_id);
		
		//整理出本月所有员工的工作情况List
		//双层循环，外层循环这个月的每一天，内层循环本部门所有员工
		//最终要得到的工作情况list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//该部门该月所有员工的所有工作班次
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("dep_id", select_dep_id);
		selectMap.put("working_start_start", begin_date_str);
		selectMap.put("working_start_end", end_date_str);
		//查找
		List<ShiftWork> shiftWorkList = shiftWorkService.selectAllStaffWorkConByMonthByDep(selectMap);
		
		for(out_i=1; out_i <= day; out_i++){	//外层按照该月一共的天数进行循环
			for (Staff staff : staffList) {	//内层根据该部门所有员工的列表进行循环
				WorkCondition workCondition = new WorkCondition();	//这次要添加进去的工作情况对象
				//先判断这一天这个员工有没有工作班次
				String day_begin_str = getDateStrByYearMonthDay(year,month,out_i);	//这一天0点
				String day_end_str = getDateStrByYearMonthDay(year,month,out_i+1);	//下一天0点
				HashMap<String, Object> selectOneShiftMap = new HashMap<String, Object>();
				selectOneShiftMap.put("s_id", staff.getS_id());
				selectOneShiftMap.put("dep_id", select_dep_id);
				selectOneShiftMap.put("working_start_start", day_begin_str);
				selectOneShiftMap.put("working_start_end", day_end_str);
				List<ShiftWork> todayWork = shiftWorkService.selectOneStaffWorkConByMonthByDep(selectOneShiftMap);
				if(todayWork.size() > 0){	//该员工该天有工作班次安排
					//看考勤表有没有这条工作班次的记录
					HashMap<String, Object> selectAttendanceMap = new HashMap<String, Object>();
					selectAttendanceMap.put("is_overtime", 0);	//不是加班
					selectAttendanceMap.put("record_id", todayWork.get(0).getSw_id());	//获得记录id
					//获得当天的考勤记录
					Attendance todayAttendanceShift = attendanceService.findAttendanceByRecordId(selectAttendanceMap);
					if(todayAttendanceShift != null){	//该员工这一天有考勤记录，记录工作情况
						int attendance_status = todayAttendanceShift.getAttendance_status();
						if(attendance_status == 1){
							workCondition.setAttendence_status("正常");
						}else if(attendance_status == 2){
							workCondition.setAttendence_status("迟到");
						}else if(attendance_status == 3){
							workCondition.setAttendence_status("早退");
						}else{
							workCondition.setAttendence_status("既迟到又早退");
						}
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(todayAttendanceShift.getClock_in());
						workCondition.setWorking_end(todayAttendanceShift.getClock_off());
						workCondition.setS_name(staff.getS_name());
					}else{	//该员工这一天没有考勤记录
						//记录为旷工
						workCondition.setAttendence_status("旷工");	//6代表旷工
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setS_name(staff.getS_name());
					}
				}else{	//该员工该天没有工作班次安排
					//看请假表有没有他
					HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
					//查询的当前日期
					String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
					
					selectAskforleaveMap.put("s_id", staff.getS_id());
					selectAskforleaveMap.put("now_date", searchAskDate);
					//调用查询方法
					List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
					if(todayAskforleaveList.size() > 0){	//有请假记录，记为请假状态
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setAttendence_status("请假");//5代表请假状态
						workCondition.setS_name(staff.getS_name());
					}else{	//也没有请假记录，没有班次安排，也没有请假，说明放假？？周末？？？？
						workCondition.setAttendence_status("无");
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setS_name(staff.getS_name());
					}
				}
				//一次循环结束，将这一条工作情况添加到List中
				workConditionList.add(workCondition);
			}
		}
		
		//获得所有的部门列表
		List<Department> departmentList = departmentService.selectAllDepartment();
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("workConditionList", workConditionList);
		request.setAttribute("staffList", staffList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("select_dep_id", select_dep_id);
		request.setAttribute("staff_number", staffList.size());
		request.setAttribute("departmentList", departmentList);
		
		return "manager/selectAllStaffWorkConByMonthByDep";
	}
	
	//在查看本部门工作情况页面中，点击某个员工后，在这里整理该员工该月所有的工作情况，跳转到显示单个工作情况的页面
	@RequestMapping("/selectOneStaffWorkConByMonthByDep")
	public String selectOneStaffWorkConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 101;
		if(request.getParameter("s_id") != null){
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		Integer select_sid = 101;
		if(request.getParameter("select_sid") != null){
			select_sid = Integer.parseInt(request.getParameter("select_sid"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("select_sid") != null){
				select_sid = (Integer)session.getAttribute("select_sid");
			}
		}
		
		Integer select_dep_id = staffService.findDepidBySid(sId);
		if(request.getParameter("select_dep_id") != null){
			select_dep_id = Integer.parseInt(request.getParameter("select_dep_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("select_dep_id") != null){
				select_dep_id = (Integer)session.getAttribute("select_dep_id");
			}
		}
		
		Calendar cal = Calendar.getInstance();
		
		//默认当前年
		Integer year = cal.get(Calendar.YEAR);
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		}
		//默认当前月
		Integer month = cal.get(Calendar.MONTH)+1;
		if(request.getParameter("month") != null){
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		cal.set(year, month, 0);
		//这个是对的，天数
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//获得这一年这个月一共有多少天
		
		int out_i = 1;
		
		//得到该月第一天和下个月第一天
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar month_begin_cal = Calendar.getInstance();
		//month_begin_cal.set(year, month, 1);	//这句按理说才是对的啊
		month_begin_cal.set(year, month-1, 1);
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//该月第一天的String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//月份+1		//按理说应该有这句话啊
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//下个月第一天的String
		
		System.out.println("该月第一天："+begin_date_str+"，下个月第一天："+end_date_str);
		
		//给jsp判断的空值
		String emptyStr = "2019-01-01 00:00:00";
		Timestamp empty_date = Timestamp.valueOf(emptyStr);
		
		//加班时长
		long last_shiftwork_hour = 0;
		int late_count = 0;	//迟到计数
		int early_count = 0;	//早退计数
		int miss_count = 0;	//旷工计数
		
		//该员工对象
		Staff select_staff = staffService.findStaffById(select_sid);
		//获得部门id
		//Integer dep_id = staffService.findDepidBySid(select_sid);	//获得该员工对应的dep_id
		//该部门所有员工列表
		List<Staff> staffList = staffService.selectStaffByDepId(select_dep_id);
		
		//整理出本月该员工的工作情况List
		//单层循环，外层循环这个月的每一天
		//最终要得到的工作情况list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//该部门该月所有员工的所有工作班次
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", select_sid);
		selectMap.put("dep_id", select_dep_id);
		selectMap.put("working_start_start", begin_date_str);
		selectMap.put("working_start_end", end_date_str);
		//查找
		List<ShiftWork> shiftWorkList = shiftWorkService.selectOneStaffWorkConByMonthByDep(selectMap);

				
		for(out_i=1; out_i <= day; out_i++){	//外层按照该月一共的天数进行循环
			WorkCondition workCondition = new WorkCondition();	//这次要添加进去的工作情况对象
			//先判断这一天这个员工有没有工作班次
			String day_begin_str = getDateStrByYearMonthDay(year,month,out_i);	//这一天0点
			String day_end_str = getDateStrByYearMonthDay(year,month,out_i+1);	//下一天0点
			HashMap<String, Object> selectOneShiftMap = new HashMap<String, Object>();
			selectOneShiftMap.put("s_id", select_staff.getS_id());
			selectOneShiftMap.put("dep_id", select_dep_id);
			selectOneShiftMap.put("working_start_start", day_begin_str);
			selectOneShiftMap.put("working_start_end", day_end_str);
			List<ShiftWork> todayWork = shiftWorkService.selectOneStaffWorkConByMonthByDep(selectOneShiftMap);
			if(todayWork.size() > 0){	//该员工该天有工作班次安排
				//看考勤表有没有这条工作班次的记录
				HashMap<String, Object> selectAttendanceMap = new HashMap<String, Object>();
				selectAttendanceMap.put("is_overtime", 0);	//不是加班
				selectAttendanceMap.put("record_id", todayWork.get(0).getSw_id());	//获得记录id
				//获得当天的考勤记录
				Attendance todayAttendanceShift = attendanceService.findAttendanceByRecordId(selectAttendanceMap);
				if(todayAttendanceShift != null){	//该员工这一天有考勤记录，记录工作情况
					int attendance_status = todayAttendanceShift.getAttendance_status();
					if(attendance_status == 1){
						workCondition.setAttendence_status("正常");
					}else if(attendance_status == 2){
						workCondition.setAttendence_status("迟到");
						late_count++;
					}else if(attendance_status == 3){
						workCondition.setAttendence_status("早退");
						early_count++;
					}else{
						workCondition.setAttendence_status("既迟到又早退");
						late_count++;
						early_count++;
					}
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
					if(todayAttendanceShift.getClock_off() != null){
						//计算时长
						String shiftwork_begin_str = sdf_detail.format(todayAttendanceShift.getClock_in());
						String shiftwork_end_str = sdf_detail.format(todayAttendanceShift.getClock_off());
						
						//计算时间差
						long tmp_last_hour = getLastHour(shiftwork_begin_str, shiftwork_end_str);
						
						last_shiftwork_hour += tmp_last_hour;
					}
				}else{	//该员工这一天没有考勤记录
					//记录为旷工
					workCondition.setAttendence_status("旷工");	//6代表旷工
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
					
					miss_count++;
				}
			}else{	//该员工该天没有工作班次安排
				//看请假表有没有他
				HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
				//查询的当前日期
				String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
				
				selectAskforleaveMap.put("s_id", select_staff.getS_id());
				selectAskforleaveMap.put("now_date", searchAskDate);
				//调用查询方法
				List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
				if(todayAskforleaveList.size() > 0){	//有请假记录，记为请假状态
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("请假");//5代表请假状态
					workCondition.setS_name(select_staff.getS_name());
				}else{	//也没有请假记录，没有班次安排，也没有请假，说明放假？？周末？？？？
					workCondition.setAttendence_status("无");
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}
			//一次循环结束，将这一条工作情况添加到List中
			workConditionList.add(workCondition);
		}
		
		//获得所有的部门列表
		List<Department> departmentList = departmentService.selectAllDepartment();
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("workConditionList", workConditionList);
		request.setAttribute("select_staff", select_staff);
		request.setAttribute("staffList", staffList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("select_dep_id", select_dep_id);
		request.setAttribute("departmentList", departmentList);
		//request.setAttribute("staff_number", staffList.size());
		
		request.setAttribute("last_shiftwork_hour", last_shiftwork_hour);
		request.setAttribute("late_count", late_count);
		request.setAttribute("early_count", early_count);
		request.setAttribute("miss_count", miss_count);
		
		return "manager/selectOneStaffWorkConByMonthByDep";
	}
	
	public static long getLastHour(String begin_str , String end_str){
		try {
			Double last_double = 0.0;
			
			//小时的毫秒数
			long nh = 1000*60*60;
			
			//String转Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//
			Date begin_date = sdf.parse(begin_str);
			Date end_date = sdf.parse(end_str);
			
			//计算时间差
			long last_time = end_date.getTime() - begin_date.getTime();
			
			//计算差多少小时
			long last_hour = last_time / nh;
						
			System.out.println(begin_str+"和"+end_str+"相差"+last_hour+"个小时");
			
			return last_hour;
			
		} catch (Exception e) {
			e.printStackTrace();
			return (long) 0.0;
		}
	}
	
	
	//点击查看工作情况后，在这里整理该员工的工作情况，跳转到显示工作情况的页面
	@RequestMapping("/selectOneOvertimeConByMonthByDep")
	public String selectOneOvertimeConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 101;
		if(request.getParameter("s_id") != null){
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		Integer select_sid = 101;
		if(request.getParameter("select_sid") != null){
			select_sid = Integer.parseInt(request.getParameter("select_sid"));
		}
		
		Calendar cal = Calendar.getInstance();
		
		//默认当前年
		Integer year = cal.get(Calendar.YEAR);
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		}
		//默认当前月
		Integer month = cal.get(Calendar.MONTH)+1;
		if(request.getParameter("month") != null){
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		cal.set(year, month, 0);
		//这个是对的，天数
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//获得这一年这个月一共有多少天
		
		//得到该月第一天和下个月第一天
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar month_begin_cal = Calendar.getInstance();
		//month_begin_cal.set(year, month, 1);	//这句按理说才是对的啊
		month_begin_cal.set(year, month-1, 1);
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//该月第一天的String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//月份+1		//按理说应该有这句话啊
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//下个月第一天的String
		
		//具体化
		String begin_date_str_detail = begin_date_str + " 00:00:00";
		String end_date_str_detail = end_date_str + " 00:00:00";
		
		System.out.println("查看加班情况的方法，该月第一天："+begin_date_str_detail+"，下个月第一天："+end_date_str_detail);
		
		//给jsp判断的空值
		String emptyStr = "2019-01-01 00:00:00";
		Timestamp empty_date = Timestamp.valueOf(emptyStr);
		
		//加班时长
		long last_overtime_hour = 0;
		
		//该员工对象
		Staff select_staff = staffService.findStaffById(select_sid);
		//获得部门id
		Integer dep_id = staffService.findDepidBySid(sId);	//获得该员工对应的dep_id
		
		//得到本月的加班List
		HashMap<String, Object> selectOvertimeMap = new HashMap<String, Object>();
		selectOvertimeMap.put("s_id", select_sid);
		selectOvertimeMap.put("begin_date", begin_date_str_detail);
		selectOvertimeMap.put("end_date", end_date_str_detail);
		List<Overtimeapplication> overtimeList = overtimeService.selectOvertimeByMonth(selectOvertimeMap);
		
		System.out.println("正常加班列表的数量是："+overtimeList.size());
		
		//得到加班时长
		for (Overtimeapplication overtimeApply1 : overtimeList) {
			String overtime_begin_str = sdf_detail.format(overtimeApply1.getOvertime_start());
			String overtime_end_str = sdf_detail.format(overtimeApply1.getOvertime_end());
			
			//计算时间差
			long tmp_last_hour = getLastHour(overtime_begin_str, overtime_end_str);
			
			last_overtime_hour += tmp_last_hour;
		}
		
		//得到本月所有有考勤记录的临时性加班
		List<Overtimeapplication> tmpMonthList = overtimeService.selectTmpOvertimeByMonth(selectOvertimeMap);
		
		//开始根据考勤表中的记录构建真实的临时性加班记录
		List<Overtimeapplication> tmpOvertimeList = new ArrayList<Overtimeapplication>();
		for (Overtimeapplication selectTmpOvertime : tmpMonthList) {
			//要放入list的临时加班记录
			Overtimeapplication tmpOvertime = new Overtimeapplication();
			//tmpOvertime.setExamination(selectTmpOvertime.getExamination());
			tmpOvertime.setIs_approved(1);
			tmpOvertime.setIs_sign(2);
			tmpOvertime.setIs_temporary(1);
			tmpOvertime.setOa_id(selectTmpOvertime.getOa_id());
			tmpOvertime.setS_id(select_sid);
			
			//查找考勤记录
			HashMap<String, Object> selectAttMap = new HashMap<String, Object>();
			selectAttMap.put("is_overtime", 1);
			selectAttMap.put("record_id", selectTmpOvertime.getOa_id());
			Attendance selectAttendance = attendanceService.findAttendanceByRecordId(selectAttMap);
			if(selectAttendance.getClock_off() != null){
				continue;
			}
					
			tmpOvertime.setOvertime_start(selectAttendance.getClock_in());
			tmpOvertime.setOvertime_end(selectAttendance.getClock_off());
			
			//计算时间差
			String tmp_overtime_begin_str = sdf_detail.format(selectAttendance.getClock_in());
			String tmp_overtime_end_str = sdf_detail.format(selectAttendance.getClock_off());
			
			//计算时间差
			long tmp_tmp_last_hour = getLastHour(tmp_overtime_begin_str, tmp_overtime_end_str);
			
			last_overtime_hour += tmp_tmp_last_hour;
			
			//放进List
			tmpOvertimeList.add(tmpOvertime);
		}
		
		System.out.println("临时性加班表的数量是："+tmpOvertimeList.size());
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("tmpOvertimeList", tmpOvertimeList);
		request.setAttribute("overtimeList", overtimeList);
		request.setAttribute("select_staff", select_staff);
		request.setAttribute("select_sid", select_sid);
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		//request.setAttribute("dep_id", dep_id);
		
		request.setAttribute("last_overtime_hour", last_overtime_hour);
		
		return "manager/selectOneOvertimeConByMonthByDep";
	}
	
	
	
	
	//————————————————工作班次部分——————————
	
	/**
	 * 首次进入查询界面
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	//查询某部门所有员工的工作安排记录
	@RequestMapping("/firstSelectAllShiftWork")
	public String selectAllswByMonthByDepgetFromJsp (HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//记得这个超链接的名字是usr_id  ！！！！！！！！
		Integer usrid = 101;
		if(request.getParameter("usr_id") != null){
			usrid = Integer.parseInt(request.getParameter("usr_id"));
		}else if(request.getSession() != null){
			HttpSession session = request.getSession();
			if(session.getAttribute("usr_id") != null){
				usrid = (Integer)session.getAttribute("usr_id");
			}else if(session.getAttribute("s_id") != null){
				usrid = (Integer)session.getAttribute("s_id");
			}
		}
		
		
		Calendar now = Calendar.getInstance();//Calender是一个抽象类,
		
		Integer year = now.get(Calendar.YEAR);//获取当前年月日
		Integer month = now.get(Calendar.MONTH);
		Integer date = now.get(Calendar.DATE);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
				
		//calendar1.add(calendar1.MONTH, -1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));

		//下一个月
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);

		calendar2.add(calendar1.MONTH, 1);
		calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(calendar1.getTime());
		String nextDate = sdf.format(calendar2.getTime());
		
		//赋个初始值
		HashMap<String, Object> sallMap = new HashMap<String,Object>();
		sallMap.put("dep_id", 1001);
		sallMap.put("working_start_start",nowDate);
		sallMap.put("working_start_end", nextDate);
		
		System.out.println("controller.selectall.initial:初始值sallMap:开始日期"+nowDate+"结束日期"+nextDate);
		
		
		//将得到参数传给jsp
		List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
		List<Department> depList = departmentService.selectAllDepartment();
		List<Staff> sList = staffService.selectStaffByDepId(1001);
		
		if (swallList.size()<=0) {
			System.out.println("controller.selectall.initial:swallList-----null\n");
		} else {
			for (ShiftWork shiftWork : swallList) {
				System.out.println("controller.selectall.initial:swallList初始值：员工ID"+shiftWork.getS_id()+"**\n");
			}
		}
		
		if (depList.size()<=0) {
			System.out.println("controller.selectall.initial:depList-----null\n");
		} else {
			for (Department department : depList) {
				System.out.println("controller.selectall.initial:depList初始值：部门ID"+department.getDep_id()+"**\n");
			}
		}
		
		if (sList.size()<=0) {
			System.out.println("controller.selectall.initial:sList-----null\n");
		} else {
			for (Staff staff : sList) {
				System.out.println("controller.selectall.initial:sList初始值：员工ID"+staff.getS_id()+"**\n");
			}
		}
		
		for (Staff staff : sList) {
			System.out.println("controller.selectall.initial初始页面得到员工姓名");
			System.out.println(staff.getS_name());
		}
		
		

		List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
		/*****填空*****/
		/**
		 * 当swallList有值时，将该值和当前天比较
		 * 相同则继续比较员工id，若不同，则填空将改天的jspswList填满
		 * 比较员工id,相同直接放入jspswList里
		 * 不同则把则把jspswList里当前员工id填空
		 * 若swallList无值，即遍历结束
		 * 直接给jspswList里给当前日期和员工id的填空
		 */
		//遍历swallList
		int i=0;
		//记录swallList长度
		Integer swalllenth = swallList.size();//123---012
		//得到该月的最后一天
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//遍历填空
		//外层循环
		for (int x=1;x<=lastDay;x++){
			//内层循环
			for (int j=0; j<sList.size(); j++) {
				//给jspswList的对象
				ShiftWork jspsw = new ShiftWork();
			
				//取sList里的s_id
				Integer staffsid = sList.get(j).getS_id();
				//depid不变
				jspsw.setDep_id(1001);
				//s_id直接放入
				jspsw.setS_id(staffsid);

				//若swallList未遍历结束
				if (i<=swalllenth-1) {
			
					//取swallList里的s_id
					Integer swsid = swallList.get(i).getS_id();
					//取swallList里的日期中的日（int）
					Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());	

					System.out.println("controller.selectall.initial补空List当前日期: "+x+"当前员工id: "+staffsid+
							"swallList里的日期:"+nowDayInteger+"swallList里s_id:"+swsid);

						
					//判断是否放入working_start和working_end
					if ((nowDayInteger.equals(x))&&(staffsid.equals(swsid))) {
						System.out.println("controller.selectall.initial:swallList放入补空List:当前日期"+nowDayInteger+"sw表中员工ID"+swsid);
						
						Timestamp swStart = swallList.get(i).getWorking_start();
						Timestamp swEnd = swallList.get(i).getWorking_end(); 
						
						jspsw.setSw_id(swallList.get(i).getSw_id());
						jspsw.setWorking_start(swStart);
						jspsw.setWorking_end(swEnd);
						
						i++;
					}
					else {
						//System.err.println("空值放入"+x+" "+staffsid);
						jspsw.setWorking_start(null);
						jspsw.setWorking_end(null);
					}	
				}
				else {
					//System.err.println("空值放入"+x+" "+staffsid);
					jspsw.setWorking_start(null);
					jspsw.setWorking_end(null);
				}
				jspswList.add(jspsw);
			}
		}		
		
		for (ShiftWork shiftWork : jspswList) {
			//System.out.println("jspswList :"+shiftWork.getDep_id()+" "+shiftWork.getS_id()+" "+shiftWork.getWorking_start());
			//System.out.println("------swid:"+shiftWork.getSw_id());
		}
		request.setAttribute("swList", jspswList);
		request.setAttribute("depList",depList);
		request.setAttribute("sList", sList);
		request.setAttribute("dep_id", 1001);
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", 0);
		request.setAttribute("usr_id", usrid);
		//跳回
		return "manager/selectAllShiftWork";
	
	}
	//从timestamo类型取出int类型的日期
	public static int getDay(Timestamp timestamp) {
		String tString = timestamp.toString();
		System.out.println("controller.时间转换得到timestamp类型的日期tString"+tString);
		Integer tInteger = Integer.parseInt(tString.substring(8,10));
		System.out.println("controller.时间转换得到timestamp类型的日期：tInteger"+tInteger);
		return tInteger;
	}
	/**
	 * 查找某部门全部员工ID
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/selectAllShiftWork")
	public String showSelectAllswByMonthByDep (HttpServletRequest request) throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		//把所有的dep_id传给jsp
		List<Department> depList = departmentService.selectAllDepartment();
		
		Integer usr_id = 101;
		if (request.getParameter("usr_id")!=null) {
			usr_id = Integer.parseInt(request.getParameter("usr_id"));
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("usr_id") != null){
				usr_id =(Integer) session.getAttribute("usr_id");
			}
		}
		
		
		//获得参数----重定向或者jsp
		String getDepid = "0";
		if (request.getParameter("dep_id")!=null) {
			getDepid = request.getParameter("dep_id");
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			getDepid =(String) session.getAttribute("dep_id");
		}
		
		
		String getSid = "0";
		if (request.getParameter("s_id")!=null) {
			getSid = request.getParameter("s_id");
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			getSid= (String) session.getAttribute("s_id_now");
		}
		
		Integer year=0;
		String getYearid = "0";
		if (request.getParameter("select_year")!=null) {
			getYearid = request.getParameter("select_year");
			year = Integer.parseInt(getYearid);
			
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			year = (Integer) session.getAttribute("select_year");
		}
		
		Integer month=0;
		String getMonth = "0";
		if (request.getParameter("select_month")!=null) {
			getMonth = request.getParameter("select_month");
			month = Integer.parseInt(getMonth);
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			month = (Integer) session.getAttribute("select_month");
		}

		System.out.println("controller.selectall.  from jsp:年"+year+"月"+month+"部门ID"+getDepid+"员工ID"+getSid);
		
		
		//把该部门所有的staff传给jsp
		Integer depid = Integer.parseInt(getDepid);
		Integer sid  = Integer.parseInt(getSid);
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		
		
		//起始时间转换
		
		Integer date = 1;
		
		//这一个月
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
				
		//calendar1.add(calendar1.MONTH, 1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));

		//下一个月
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);
		
		calendar2.add(calendar1.MONTH, 1);
		calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String nowDate = sdf.format(calendar1.getTime());
		String nextDate = sdf.format(calendar2.getTime());
		
		
		//调用selectall
		if (sid==0) {
			//参数传给Serivce
			HashMap<String, Object> sallMap = new HashMap<String,Object>();
			sallMap.put("dep_id", getDepid);
			sallMap.put("working_start_start",nowDate);
			sallMap.put("working_start_end", nextDate);
			
			//将从jsp里得到的参数给service,同时得到参数传给jsp
			//shiftwork
			List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );

			//输出测试
			if (swallList.size()<=0) {
				System.out.println("controller.selectall:swallList-----null");
			} else {
				for (ShiftWork sw : swallList) {
					System.out.println("controller.selectall:sallList：sw表内的员工id"+sw.getS_id());
				}
			}
			
			List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
			/*****填空*****/
			/**
			 * 当swallList有值时，将该值和当前天比较
			 * 相同则继续比较员工id，若不同，则填空将改天的jspswList填满
			 * 比较员工id,相同直接放入jspswList里
			 * 不同则把则把jspswList里当前员工id填空
			 * 若swallList无值，即遍历结束
			 * 直接给jspswList里给当前日期和员工id的填空
			 */
			//遍历swallList
			int i=0;
			//记录swallList长度
			Integer swalllenth = swallList.size();//123---012
			//得到该月的最后一天
			int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
			//遍历填空
			//外层循环
			for (int x=1;x<=lastDay;x++){
				//内层循环
				for (int j=0; j<sList.size(); j++) {
					//给jspswList的对象
					ShiftWork jspsw = new ShiftWork();
				
					//取sList里的s_id
					Integer staffsid = sList.get(j).getS_id();
					//depid不变
					jspsw.setDep_id(depid);
					//s_id直接放入
					jspsw.setS_id(staffsid);

					//若swallList未遍历结束
					if (i<=swalllenth-1) {
				
						//取swallList里的s_id
						Integer swsid = swallList.get(i).getS_id();
						//取swallList里的日期中的日（int）
						Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());	

						System.out.println("controller.selectall 补空：  当前日期: "+x+"当前员工id: "+staffsid+
								"swallList里的日期："+nowDayInteger+"swallList里s_id:"+swsid);

							
						//判断是否放入working_start和working_end
						if ((nowDayInteger.equals(x))&&(staffsid.equals(swsid))) {
							System.out.println("controller.selectall:swallList放入补空表"+"当前日期"+nowDayInteger+"sw表里员工ID"+swsid);
							
							Timestamp swStart = swallList.get(i).getWorking_start();
							Timestamp swEnd = swallList.get(i).getWorking_end(); 
							jspsw.setSw_id(swallList.get(i).getSw_id());
							jspsw.setWorking_start(swStart);
							jspsw.setWorking_end(swEnd);
							
							i++;
						}
						else {
							//System.err.println("空值放入"+x+" "+staffsid);
							jspsw.setWorking_start(null);
							jspsw.setWorking_end(null);
						}	
					}
					else {
						//System.err.println("空值放入"+x+" "+staffsid);
						jspsw.setWorking_start(null);
						jspsw.setWorking_end(null);
					}
					jspswList.add(jspsw);
				}
			}		
			
			for (ShiftWork shiftWork : jspswList) {
				//System.out.println("jspswList :"+shiftWork.getDep_id()+" "+shiftWork.getS_id()+" "+shiftWork.getWorking_start());
			}
			request.setAttribute("swList", jspswList);
		}
		else {
			//查询某员工
			//调用selectone
			HashMap<String, Object> soneMap = new HashMap<String,Object>();
			soneMap.put("s_id", getSid);
			soneMap.put("dep_id", getDepid);
			soneMap.put("working_start_start", nowDate);
			soneMap.put("working_start_end", nextDate);
			System.out.println("controller.selectone: 员工ID"+getSid+"部门ID"+getDepid+"开始日期"+nowDate+"结束日期"+nextDate);
			List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
			
			if (swoneList.size()<=0) {
				//System.out.println("swoneList-----null\n");
			} else {
				for (ShiftWork sw : swoneList) {
					//System.out.println("soneList："+sw.getS_id()+"**\n");
				}
			}
			
			
			List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
			/*****填空*****/
			/**
			 * 当swonelList有值时，将该值和当前天比较
			 * 相同放入，不同补空
			 * 若swoneList无值，即遍历结束
			 * 直接给jspswList里给当前日期和员工id的填空
			 */
			//遍历swoneList
			int i=0;
			//记录swoneList长度
			Integer swonelenth = swoneList.size();//123---012
			//得到该月的最后一天
			int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
			//遍历填空
			//外层循环
			for (int x=1;x<=lastDay;x++){

				//给jspswList的对象
				ShiftWork jspsw = new ShiftWork();
				//depid不变
				jspsw.setDep_id(depid);
				//s_id直接放入
				jspsw.setS_id(sid);

				//若swoneList未遍历结束
				if (i<=swonelenth-1) {
				
					//取swoneList里的日期中的日（int）
					Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	

					System.out.println("controller.selectone补空表:      当前日期"+x+"当前员工id"+sid+"swoneList里的日期"+nowDayInteger);

					//判断是否放入working_start和working_end
					if (nowDayInteger.equals(x)) {
						System.out.println("controller.selectone补空：swoneList放入补空表"+nowDayInteger+" "+sid);
							
						Timestamp swStart = swoneList.get(i).getWorking_start();
						Timestamp swEnd = swoneList.get(i).getWorking_end(); 
						jspsw.setSw_id(swoneList.get(i).getSw_id());	
						jspsw.setWorking_start(swStart);
						jspsw.setWorking_end(swEnd);
							
						i++;
					}
					else {
						//System.err.println("空值放入"+x+" "+staffsid);
						jspsw.setWorking_start(null);
						jspsw.setWorking_end(null);
					}
				}
				else {
					//System.err.println("空值放入"+x+" "+staffsid);
					jspsw.setWorking_start(null);
					jspsw.setWorking_end(null);
				}
				jspswList.add(jspsw);
			}		
			
			for (ShiftWork shiftWork : jspswList) {
				//System.out.println("jspswList :"+shiftWork.getDep_id()+" "+shiftWork.getS_id()+" "+shiftWork.getWorking_start());
			}
			System.out.println("controller.soneList补空表长:jspswList.size()"+jspswList.size());
			
			//传参
			request.setAttribute("swList", jspswList);
		}
		//for (Staff st : sList) {
				//	System.out.println("这是controller");
				//	System.out.println("测staff："+st.getS_name()+"---------------------\n");
				//}
		
		
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", sid);
		request.setAttribute("depList", depList);
		request.setAttribute("sList", sList);
		request.setAttribute("usr_id", usr_id);
		
		return "manager/selectAllShiftWork";
	}
	
	
	//——————————工作班次部分结束——————————————
	
	
}