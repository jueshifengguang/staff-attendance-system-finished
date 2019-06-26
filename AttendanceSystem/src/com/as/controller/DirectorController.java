package com.as.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.as.entity.Askforleave;
import com.as.entity.Attendance;
import com.as.entity.Department;
import com.as.entity.Message;
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
import com.as.util.ShowCamera;

/**
 * 部门主管控制器
 * @author dzz
 * 2019年6月6日 上午9:25:20
 * AttendanceSystem
 */
@Controller
@RequestMapping("/director")
public class DirectorController {

	//依赖service
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
	private AskforleaveService askforleaveService = new AskforleaveServiceImpl();
	private MessageService messageService = new MessageServiceImpl();
	private DepartmentService departmentService = new DepartmentServiceImpl();
	
	//获得当前日期的下一天，并且显示为"yyyy-MM-dd 00:00:00"
	public static String getNextDate(Date nowDate){
		//获得当前日期的下一天的日期
		Calendar calendar_overtime = new GregorianCalendar();
		calendar_overtime.setTime(nowDate);
		calendar_overtime.add(Calendar.DATE, 1);
		Date nextDate = calendar_overtime.getTime();
		
		//格式化处理
		SimpleDateFormat sdf_riqi = new SimpleDateFormat("yyyy-MM-dd");
		String next_date = sdf_riqi.format(nextDate);
		next_date += " 00:00:00";
		
		return next_date;
	}
	
	//获得当前日期的开始，并且显示为"yyyy-MM-dd 00:00:00"
	public static String getBeginDate(Date nowDate){
		//格式化处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String begin_date = sdf.format(nowDate);
		begin_date += " 00:00:00";
		
		return begin_date;
	}
	
	//————————————员工信息部分——————————————
	
	@RequestMapping("/directorSelectStaffInfo")
	public String directorSelectStaffInfo(HttpServletRequest request)throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");}
		//调用查询方法
		Staff staff = staffService.findStaffById(sId);
		
		//System.out.println(sId);
		//传参
		request.setAttribute("staff", staff);
		request.setAttribute("s_id", sId);
		return "director/directorSelectStaffInfo";
		
		
	}
	@RequestMapping("/directorUpdateStaffInfoJump")
	public String directorUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
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
		request.setAttribute("s_id", sId);
		
		return "director/directorUpdateStaffInfo";
	}
	
	//员工更新自己的账户信息	
	@RequestMapping("/directorUpdateStaffInfo")
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
		return "redirect:/director/directorSelectStaffInfo";
	}	
	
	//—————————————员工信息部分结束——————————————
	
	
	//————————————————————————————————————————————————请假部分——————————————————————————
	//跳转到审批请假的页面
	@RequestMapping("/updateAskforleaveJump")
	public String test2(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
				
		//获得已选择的请假信息
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		request.setAttribute("s_id", s_id);
		
		return "director/updateAskforleave";
	}
	//主管审批请假申请，提交修改到数据库
	@RequestMapping("/updateAskforleave")
	public String test(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		Integer apply_id = Integer.parseInt(request.getParameter("apply_id"));
		Integer is_approved =Integer.parseInt(request.getParameter("is_approved"));
		String approved_reason = request.getParameter("approved_reason");
		
		System.out.println("接到的sid："+s_id);
		System.out.println("接到的apply_id："+apply_id);
		
		if(is_approved == 1){	//审批通过，给主管发消息，提醒他跳转工作班次
			String a = addNewMessageAfterApproved(s_id);
		}
		Staff applyStaff = staffService.findStaffById(apply_id);
		Integer staff_Id = applyStaff.getS_id();
		if(is_approved != 0){	//审批过了，给员工发消息
			String b = addNewMessageAfterApprovedForStaff(staff_Id);
		}		
		
		//传参
		oaJspMap.put("afl_id", afl_id);
		oaJspMap.put("is_approved", is_approved);
		oaJspMap.put("approved_reason", approved_reason);
				
		//调用新增方法
		askforleaveService.updateAskApplyforByAflid(oaJspMap);
				
		//传参
		request.setAttribute("s_id", s_id);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
		System.out.println(s_id);
				
		//return "director/selectOvertimeRecord";
		return "redirect:/director/selectNoncheckedAskforApplyByDep";
	}
	
	@RequestMapping("/addNewAskforleaveJump")
	public String addNewAskforleaveJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");

		//获得参数
	    Integer s_id = Integer.parseInt(request.getParameter("s_id"));

		//获得已选择的请假信息
		request.setAttribute("s_id", s_id);
		
		return "director/addNewAskforleave";
	}

	//新增请假申请
	@RequestMapping("/addNewAskforleave")
	public String addNewAskforleave(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
			
		//获得页面传来的参数
		Integer getSId = Integer.parseInt(request.getParameter("s_id")); 
		Integer getIsApproved = Integer.parseInt(request.getParameter("is_approved"));
		Integer getIsResumed = Integer.parseInt(request.getParameter("is_resumed"));
		String getStartTime = request.getParameter("starting_date");
		String getEndTime = request.getParameter("ending_date");
		String leave_reason = request.getParameter("leave_reason");
		String approved_reason = request.getParameter("approved_reason");
		Integer leave_type = Integer.parseInt(request.getParameter("leave_type"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//把参数放到Map中
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("s_id", getSId);
		toMap.put("is_approved", getIsApproved);
		toMap.put("is_resumed", getIsResumed);
		toMap.put("starting_date", getStartTime);
		toMap.put("ending_date", getEndTime);
		toMap.put("leave_reason", leave_reason);
		toMap.put("approved_reason", approved_reason);
		toMap.put("leave_type", leave_type);
		//传递参数
		request.setAttribute("s_id", getSId);
		//调用新增的方法
		askforleaveService.insertNewAskforApply(toMap);
		//调用方法新增消息
		String a = addNewMessageBeforeApproved(getSId);
		
		HttpSession session = request.getSession();
		session.setAttribute("s_id", getSId);
		
		//return "director/homepage";
		return "redirect:/director/listAllAskforleaveBySid";
    }
	
	//主管查看本部门所有的请假申请（默认显示未审批的）
	@RequestMapping("/selectNoncheckedAskforApplyByDep")
	public String selectNoncheckedAskforApplyByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		//获得该部门所有的未审批加班申请
		//先获得这个主管的dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//再根据部门id查找该部门所有未审批的加班申请
		List<Askforleave> AskforleaveList = askforleaveService.selectNoncheckedAskforApplyByDep(depId);
		//传参给页面
		request.setAttribute("s_id", sId);
		request.setAttribute("AskforleaveList", AskforleaveList);
		return "director/listAllAskforleave";
	}
	//查看已审批的请假
	@RequestMapping("/selectcheckedAskforApplyByDep")
	public String selectcheckedAskforApplyByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		//获得该部门所有的未审批加班申请
		//先获得这个主管的dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//再根据部门id查找该部门所有未审批的加班申请
		List<Askforleave> AskforleaveList = askforleaveService.selectNoncheckedAskforApplyByDep(depId);
		//System.out.println(AskforleaveList.get(0).getLeave_reason());
		//传参给页面
		request.setAttribute("s_id", sId);
		request.setAttribute("AskforleaveList", AskforleaveList);
		//
		return "director/listAllAskforleaveChanged";
	}	

	//查看自己的未销假的请假列表
	@RequestMapping("/listAllAskforleaveBySid")
	public String listAllAskforleaveBySid(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
				
		Integer s_id = 0;
		if(request.getParameter("s_id") != null) {
			s_id = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				s_id = (Integer)session.getAttribute("s_id");
			}
		}
		
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(s_id);
		
		//传参
		request.setAttribute("s_id", s_id);
		request.setAttribute("aflList", aflList);
		
		//跳转到自己的未销假列表
		return "director/listAllAskforleaveBySid";
	}
	
	//查看自己已销假的请假列表
	@RequestMapping("/listAllAskforleaveBySidChanged")
	public String listAllAskforleaveBySidChanged(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
	
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(s_id);
		
		//传参
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", s_id);
		
		//跳转到自己的已销假列表
		return "director/listAllAskforleaveBySidChanged";
	}

	//跳转到修改页面？主管审批？？跳转用
	@RequestMapping("/listAllAskforleaveBySidJump")
	public String listAllAskforleaveBySidJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		//String is_approved =request.getParameter("is_approved");
				
		//获得已选择的请假信息
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		//传参
		request.setAttribute("s_id", s_id);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
		session.setAttribute("updateAfl", updateAfl);
		System.out.println("1111");		
		//return "director/selectOvertimeRecord";
		//return "redirect:/director/selectOvertimeRecord";	//重定向
		//跳转到审批（修改）页面
		return "director/updateAskforleaveByresumed";
	}
	
	//自己销假
	@RequestMapping("/updateAskforleaveByresumed")
	public String updateAskforleaveByresumed(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		String is_resumed =request.getParameter("is_resumed");
		System.out.println("controller1的sid:"+s_id);
				
		//获得已选择的临时加班信息
		//Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
				
		//System.out.println(afl_id);
		System.out.println("2222");
		//传参
		oaJspMap.put("afl_id", afl_id);
		oaJspMap.put("is_resumed", is_resumed);
				
		//调用新增方法
		askforleaveService.updateAskApplyforByAflid(oaJspMap);
				
		//传参
		request.setAttribute("s_id", s_id);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
				
		//return "director/selectOvertimeRecord";
		//return "redirect:/director/selectOvertimeRecord";	//重定向
		return "redirect:/director/listAllAskforleaveBySid";
	}	
	
	
	//————————————————————————————————————————————————请假部分结束——————————————————————————
	
	
	
	//————————————————————————————————————————————————消息部分——————————————————————————
	
	//查看未读消息
	@RequestMapping("/selectNoReadMessageApply")
	public String selectNoReadedMessageApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
        Integer s_id = 0;
		if(request.getParameter("s_id") != null) {
			s_id = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				s_id = (Integer)session.getAttribute("s_id");
			}
		}
		
		List<Message> messageList = messageService.selectNoReadMessageApply(s_id);
		
		request.setAttribute("s_id", s_id);
		request.setAttribute("messageList", messageList);
		
		//跳转
		return "director/message";
	}
	
	//查看已读消息
	@RequestMapping("/selectReadMessageApply")
	public String selectReadedMessageApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
        
        Integer s_id = 0;
		if(request.getParameter("s_id") != null) {
			s_id = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				s_id = (Integer)session.getAttribute("s_id");
			}
		}
		
		List<Message> messageList = messageService.selectReadMessageApply(s_id);
		
	//	request.setAttribute("is_read", is_read);
		request.setAttribute("s_id", s_id);
		request.setAttribute("messageList", messageList);
		request.setAttribute("is_read", 1);
		
		//跳转
		return "director/message";
	}
	//更新未读消息为已读消息
	@RequestMapping("/updateMessage")
	public String updateMessage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer m_id = Integer.parseInt(request.getParameter("m_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		Integer is_read = 1;
		//获得已选择的请假信息
		oaJspMap.put("m_id", m_id);
		oaJspMap.put("is_read", is_read);
		
		Message updateMessage = messageService.findMessageByMid(m_id);
		
		request.setAttribute("updateMessage", updateMessage);
		request.setAttribute("s_id", s_id);
		request.setAttribute("is_read", is_read);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
		session.setAttribute("is_read", is_read);
		session.setAttribute("updateMessage", updateMessage);
	//	session.setAttribute("m_id", m_id);
		
		messageService.updateMessage(oaJspMap);
		
		return "redirect:/director/selectNoReadMessageApply";
	}
	
	//删除已读消息
	@RequestMapping("/deleteMessage")
	public String deleteMessage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		Integer m_id = 0;
		if(request.getParameter("m_id") != null){
			m_id = Integer.parseInt(request.getParameter("m_id"));
		}
		
		//调用删除方法
		//先判断有没有这个活动
		Message selectMessage = messageService.findMessageByMid(m_id);
		if(selectMessage !=null) {
			messageService.deleteMessage(m_id);
		}
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "manager/selectAllTemporaryOvertime";
		return "redirect:/director/selectReadMessageApply";
	}
	
	//主管审批请假后，再给主管发消息，提醒他修改工作班次安排
	public String addNewMessageAfterApproved(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "你审批通过了"+s_name+"的请假记录，请尽快进行工作安排";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//主管自己新增了请假申请后，也要调用这个方法，给他发消息提醒他审批
	public String addNewMessageBeforeApproved(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"提交了新的的请假表，请尽快进行审批";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//给员工发的，提醒他请假申请已经被审批过了
	public String addNewMessageAfterApprovedForStaff(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "你的请假申请已经被审批了，快去查看吧！！";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
	
	//_______________________
	
	//主管自己新增了加班申请后，也要调用这个方法，给他发消息提醒他审批
	public String addMessageToDirectorForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"提交了新的的加班申请，请尽快进行审批";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
	
	//主管自己申请了临时加班后，也要调用这个方法，给他发消息调整工作班次？
	public String addMessageToDirectorForTemporaryOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"申请了新的临时性加班";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//给员工发的，提醒他加班申请已经被审批过了
	public String addMessageToStaffForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "你的加班申请已经被审批了，快去查看吧！！";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
		
	
	//————————————————————————————————————————————————消息部分结束——————————————————————————
	
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
		return "director/selectAllTemporaryOvertime";
	}
	
	//申请临时加班
	@RequestMapping("/applyForTempOvertime")
	public String applyForTempOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer toId = Integer.parseInt(request.getParameter("to_id"));
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = "2019-06-01 17:00:00";
		String overtime_end = "2019-06-01 19:00:00";
		String overtime_reason = "临时加班。原因：";
		Integer is_approved = 1;
		Integer is_temporary = 1;
		String examination = "临时加班，无需审批";
		Integer is_sign = 0;
		
		//获得已选择的临时加班信息
		Temporaryovertime selectTmpOvertime = tmpOvertimeService.findTempOvertimeByToid(toId);
		if(selectTmpOvertime != null){
			overtime_start = selectTmpOvertime.getT_overtime_start().toString();
			overtime_end = selectTmpOvertime.getT_overtime_end().toString();
			overtime_reason += selectTmpOvertime.getT_o_reason();
		}
		
		//传参
		oaJspMap.put("s_id", sId);
		oaJspMap.put("overtime_start", overtime_start);
		oaJspMap.put("overtime_end", overtime_end);
		oaJspMap.put("overtime_reason", overtime_reason);
		oaJspMap.put("is_approved", is_approved);
		oaJspMap.put("is_temporary", is_temporary);
		oaJspMap.put("examination", examination);
		oaJspMap.put("is_sign", is_sign);
		
		//调用新增方法
		if(selectTmpOvertime != null){	//保证没刷新时，经理有可能删除了这项活动
			overtimeService.insertOvertimeApply(oaJspMap);
			
			//给主管发消息，提醒他申请了一个不用他审批的临时性加班
			addMessageToDirectorForTemporaryOvertime(sId);
		}
		
		//传参
		//request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "director/selectOvertimeRecord";
		return "redirect:/director/selectOvertimeRecord";	//重定向
	}
	
	//查看自己的所有加班记录
	@RequestMapping("selectOvertimeRecord")
	public String selectOvertimeRecord(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//调用查询方法
		List<Overtimeapplication> myOvertimeApplyList = overtimeService.selectAllOvertimeApplyBySid(sId);
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("myOvertimeApplyList", myOvertimeApplyList);
		
		//跳转
		return "director/selectOvertimeRecord";
	}
	
	//点击新增后，跳转到新增（申请）加班记录的页面
	@RequestMapping("/insertOvertimeRecord")
	public String insertOvertimeRecord(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = null;
		if(request.getParameter("overtime_start") != null){
			overtime_start = request.getParameter("overtime_start");
		}
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("overtime_start", overtime_start);
		
		return "director/insertOvertimeRecord";
	}
	
	@RequestMapping("/insertOvertimeDB")
	public String insertOvertimeDB(HttpServletRequest request) throws ServletException, IOException{
		//填写好申请加班记录的信息后，表单提交到这里，新增申请到数据库
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = "2019-06-01 17:00:00";
		if(request.getParameter("overtime_start") != null){
			overtime_start = request.getParameter("overtime_start");
		}
		String overtime_end = "2019-06-01 19:00:00";
		if(request.getParameter("overtime_end") != null){
			overtime_end = request.getParameter("overtime_end");
		}
		String overtime_reason = "无";
		if(request.getParameter("overtime_reason") != null){
			overtime_reason = request.getParameter("overtime_reason");
		}
		Integer is_approved = 0;
		Integer is_temporary = 0;
		String examination = "无";
		Integer is_sign = 0;
		
		//调用新增的service方法
		HashMap<String, Object> insertMap = new HashMap<String, Object>();
		insertMap.put("s_id", sId);
		insertMap.put("overtime_start", overtime_start);
		insertMap.put("overtime_end", overtime_end);
		insertMap.put("overtime_reason", overtime_reason);
		insertMap.put("is_approved", is_approved);
		insertMap.put("is_temporary", is_temporary);
		insertMap.put("examination", examination);
		insertMap.put("is_sign", is_sign);
		
		//调用
		overtimeService.insertOvertimeApply(insertMap);
		
		//给主管（自己）发消息提醒他申请了一个需要审批的加班申请
		addMessageToDirectorForOvertime(sId);
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//重定向，回到查看所有加班申请界面
		return "redirect:/director/selectOvertimeRecord";
	}
	
	
	//主管查看本部门所有的加班申请（默认显示未审批的）
	@RequestMapping("/selectNoncheckedOvertimeApply")
	public String selectNoncheckedOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
			System.out.println("看看有sid了么："+sId);
		}
		
		//获得该部门所有的未审批加班申请
		//先获得这个主管的dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//再根据部门id查找该部门所有未审批的加班申请
		List<Overtimeapplication> noncheckedOvertimeList = overtimeService.selectNoncheckedOvertimeApplyByDep(depId);

		//传参给页面
		request.setAttribute("s_id", sId);
		request.setAttribute("overtimeApplyList", noncheckedOvertimeList);
		
		return "director/selectDepOvertimeApply";
	}
	
	//点击“审批”某条加班申请记录后，跳转到修改（审批）页面
	@RequestMapping("/examineOvertimeApplyJump")
	public String examineOvertimeApplyJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		Integer oaId = Integer.parseInt(request.getParameter("oa_id"));
		
		//获得这条申请记录
		Overtimeapplication selectOvertimeApply = overtimeService.findOvertimeApplyByOaid(oaId);
		//获得申请的员工对象
		Staff applyStaff = staffService.findStaffById(selectOvertimeApply.getS_id());
		
		//传参给页面
		request.setAttribute("s_id", sId);
		request.setAttribute("selectOvertimeApply", selectOvertimeApply);
		request.setAttribute("applyStaff", applyStaff);
		
		return "director/examineOvertimeApply";
	}
	
	//审批好某一条加班申请后，修改到数据库中
	@RequestMapping("/examineOvertimeApply")
	public String examineOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		Integer oaId = Integer.parseInt(request.getParameter("oa_id"));
		Integer is_approved = Integer.parseInt(request.getParameter("is_approved"));
		String examination = "无";
		
		HashMap<String, Object> updateMap = new HashMap<String, Object>();
		
		if(request.getParameter("examination") != null){
			examination = request.getParameter("examination");
		}
		
		//调用修改的方法
		updateMap.put("oa_id", oaId);
		updateMap.put("is_approved", is_approved);
		updateMap.put("examination", examination);
		overtimeService.updateOvertimeApplyByOaid(updateMap);
		
		//同oaId，获得sid
		Overtimeapplication exam_overtime = overtimeService.findOvertimeApplyByOaid(oaId);
		//获得申请的员工对象
		Staff exam_staff = staffService.findStaffById(exam_overtime.getS_id());
		//给那个员工发消息
		addMessageToStaffForOvertime(exam_staff.getS_id());

		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//重定向，回到查看所有加班申请界面
		return "redirect:/director/selectNoncheckedOvertimeApply";
	}
	
	//主管查看本部门所有的加班申请（默认显示未审批的）
	@RequestMapping("/selectCheckedOvertimeApply")
	public String selectCheckedOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
			System.out.println("看看有sid了么："+sId);
		}
		Integer is_examined_flag = 1;
		if(request.getParameter("is_examined_flag") != null){
			is_examined_flag = Integer.parseInt(request.getParameter("is_examined_flag"));
		}
		
		//获得该部门所有已审批的加班申请
		//先获得这个主管的dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//再根据部门id查找该部门所有已审批的加班申请
		List<Overtimeapplication> checkedOvertimeList = overtimeService.selectCheckedOvertimeApplyByDep(depId);

		//传参给页面
		request.setAttribute("is_examined_flag", is_examined_flag);
		request.setAttribute("s_id", sId);
		request.setAttribute("overtimeApplyList", checkedOvertimeList);
		
		return "director/selectDepOvertimeApply";
	}
	
	//返回首页的方法，要先获得首页需要显示的当天可以打卡、签退的工作班次和加班列表
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
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "redirect:/director/selectTodayWorkHomepage";	//回首页前，先获得当天可以打卡、签退的工作班次和加班
		//return "director/homepage";
	}
	
	//获得当天可以打卡、签退的班次和加班列表，返回首页
	@RequestMapping("/selectTodayWorkHomepage")
	public String selectTodayWorkHomepage(HttpServletRequest request) throws ServletException, IOException{
		try {
			//获得参数
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			
			int is_remind = 0;	//是否提醒员工申请加班
			
			//获得今天的日期
			Date todayDate = new Date();
			SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now_date = sdf_overtime.format(todayDate);	//当前时间
			String next_date = getNextDate(todayDate);	//下一天的零点的日期
			String begin_date = getBeginDate(todayDate);	//当前日期的开始时间
			
			//工作班次列表（只有一条）
			HashMap<String, Object> shiftWorkMap = new HashMap<String, Object>();
			shiftWorkMap.put("s_id", sId);
			shiftWorkMap.put("date", now_date);
			//根据sId和今天的日期查找今天的工作班次
			ShiftWork shiftWorkToday = shiftWorkService.getAttenStatus(shiftWorkMap);
			if(shiftWorkToday != null){
				//今天确实有班次
				System.out.println("今天有班次！！！");
				System.out.println(shiftWorkToday.getWorking_start().toString()+"----"+shiftWorkToday.getWorking_end());
			}
					
			//传参
			request.setAttribute("shiftWorkToday", shiftWorkToday);
			
			
			//加班列表
			HashMap<String, Object> overtimeMap = new HashMap<String, Object>();
			overtimeMap.put("s_id", sId);
			overtimeMap.put("now_date", now_date);
			overtimeMap.put("next_date", next_date);
			overtimeMap.put("begin_date", begin_date);
			//获得可以签到的临时加班列表
			List<Overtimeapplication> overtimeSignInList = overtimeService.selectSignInOvertimeApply(overtimeMap);
			//获得可以签退的临时加班列表
			List<Overtimeapplication> overtimeSignOffList = overtimeService.selectSignOffOvertimeApply(overtimeMap);
			
			//获得正常申请通过的加班列表
			HashMap<String, Object> selectTodayOvertimeMap = new HashMap<String, Object>();
			selectTodayOvertimeMap.put("s_id", sId);
			selectTodayOvertimeMap.put("date", now_date);
			List<Overtimeapplication> overtimeApplyList = overtimeService.selectTodayOvertimeApply(selectTodayOvertimeMap);
			
			//传参
			request.setAttribute("overtimeApplyList", overtimeApplyList);
			request.setAttribute("overtimeSignInList", overtimeSignInList);
			request.setAttribute("overtimeSignOffList", overtimeSignOffList);
			request.setAttribute("s_id", sId);
			
			
			//比较当前时间是否晚于下班时间30分钟
			if(shiftWorkToday != null){	//存在要打下班卡的工作班次
				if(shiftWorkToday.getAttendence_status() == 1){
					Calendar after_time = Calendar.getInstance();
					//before_time.setTime(todayDate);
					after_time.setTime(shiftWorkToday.getWorking_end());	//获得下班打卡时间之后的1小时的时间
					after_time.add(Calendar.HOUR, 1);	//获得1小时后的时间
					String after_time_str = sdf_overtime.format(after_time.getTime());
					Date after_time_date = sdf_overtime.parse(after_time_str);	//格式化后的date
					Date todayDateSdf = sdf_overtime.parse(now_date);	//格式化后的现在时间
					
					//比较时间先后
					if(shiftWorkToday != null){
						if(shiftWorkToday.getAttendence_status() == 2){
							
						}
					}
					if( after_time_date.before(todayDateSdf) ){
						//说明现在已经过了下班时间1小时了，该提醒员工申请加班
						//先判断当前是否已经申请过加班了
						HashMap<String, Object> selectNowMap = new HashMap<String, Object>();
						selectNowMap.put("s_id", sId);
						selectNowMap.put("now_date", now_date);
						List<Overtimeapplication> searchOvertimeApplyList = overtimeService.selectOvertimeApplyByNowDate(selectNowMap);
						if( searchOvertimeApplyList.size() > 0 ){
							//说明当前已经申请过加班了，不用再提醒了
							is_remind = 0;
						}else{
							//没有找到加班记录，说明还没有申请过，提醒
							is_remind = 1;
						}
						request.setAttribute("is_remind", is_remind);
					}
				}
			}
			
			return "director/homepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	
	
	
	//还有打卡的方法
	//打卡！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！上班！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	//临时加班打卡，打上班的卡
	@RequestMapping("/signInOvertime")
	public String signInOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//获得参数
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer oa_id = 0;
			if(request.getParameter("oa_id") != null){
				oa_id = Integer.parseInt(request.getParameter("oa_id"));
			}
			
			boolean check_flag = false;	//人脸识别比对结果
			
			//调用摄像头，调用人脸识别，最多识别4次
			//开两个线程
			//开启摄像头
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//传参
			String basePath = request.getServletContext().getRealPath("/");
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//仍未比对成功，继续比对
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//结束camera线程了
					break;
				}
				try {
					Thread.sleep(100);	//100毫秒判断一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//关闭线程
			
			
			//传参
			request.setAttribute("s_id", sId);
			if(check_flag){	//比对成功了，进行考勤，返回首页
				System.out.println("人脸识别成功！");
				
				//1、修改这条记录的签到状态
				Overtimeapplication signInOvertime = overtimeService.findOvertimeApplyByOaid(oa_id);
				HashMap<String, Object> updateOvertimeMap = new HashMap<String, Object>();
				updateOvertimeMap.put("oa_id", oa_id);
				updateOvertimeMap.put("is_sign", 1);	//打了开始的卡
				//调用修改方法
				overtimeService.updateOvertimeApplyByOaid(updateOvertimeMap);
				
				
				//获得今天的日期
				Date todayDate = new Date();
				SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_overtime.format(todayDate);	//当前时间
				Date todayDateSdf = sdf_overtime.parse(now_date);	//格式化后的现在时间
				
				//2、添加一条新的考勤记录（添加开始时间，是加班标识，记录这条加班的oa_id）
				HashMap<String, Object> insertAttendanceMap = new HashMap<String, Object>();
				insertAttendanceMap.put("s_id", sId);
				Staff myself = staffService.findStaffById(sId);
				insertAttendanceMap.put("dep_id", myself.getDep_id());
				//判断打卡时间是否早于开始时间
				Date overtime_start_date = signInOvertime.getOvertime_start();
				
				//比较时间先后
				if( todayDateSdf.before(overtime_start_date) ){
					//现在的时间早于加班开始时间，正常签到
					insertAttendanceMap.put("attendance_status", 1);
					
				}else{
					//现在的时间晚于加班开始时间，迟到了
					insertAttendanceMap.put("attendance_status", 2);
				}
				insertAttendanceMap.put("clock_in", now_date);
				insertAttendanceMap.put("is_overtime", 1);		//是加班
				insertAttendanceMap.put("record_id", signInOvertime.getOa_id());
				
				//调用新增方法
				attendanceService.insertNewAtt(insertAttendanceMap);
				
			}else{	//比对失败，返回首页
				System.out.println("人脸识别失败！");
			}
			
			//传参，回首页
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/director/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
		
	//正常工作班次打卡，打上班的卡
	@RequestMapping("/signInShiftWork")
	public String signInShiftWork(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		try {
			//获得参数
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer sw_id = 0;
			if(request.getParameter("sw_id") != null){
				sw_id = Integer.parseInt(request.getParameter("sw_id"));
			}
			
			boolean check_flag = false;	//人脸识别比对结果
			
			//调用摄像头，调用人脸识别，最多识别4次
			//开两个线程
			//开启摄像头
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//传参
			String basePath = request.getServletContext().getRealPath("/");
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//仍未比对成功，继续比对
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//结束camera线程了
					break;
				}
				try {
					Thread.sleep(100);	//100毫秒判断一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			threadCamera.stop();	//关闭线程
			
			
			//传参
			request.setAttribute("s_id", sId);
			if(check_flag){	//比对成功了，进行考勤，返回首页
				System.out.println("人脸识别成功！");
				
				//1、修改这条记录的签到状态
				ShiftWork signInShiftWork = shiftWorkService.findShiftWorkByswid(sw_id);
				HashMap<String, Object> updateShiftWorkMap = new HashMap<String, Object>();
				updateShiftWorkMap.put("sw_id", sw_id);
				updateShiftWorkMap.put("attendence_status", 1);	//打了开始的卡
				//调用修改方法
				shiftWorkService.updateShiftWork(updateShiftWorkMap);
				
				//获得今天的日期
				Date todayDate = new Date();
				SimpleDateFormat sdf_shiftWork = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_shiftWork.format(todayDate);	//当前时间
				Date todayDateSdf = sdf_shiftWork.parse(now_date);	//格式化后的现在时间
							
				//2、添加一条新的考勤记录（添加开始时间，不是加班标识，记录这条工作班次的sw_id）
				HashMap<String, Object> insertAttendanceMap = new HashMap<String, Object>();
				insertAttendanceMap.put("s_id", sId);
				Staff myself = staffService.findStaffById(sId);
				insertAttendanceMap.put("dep_id", myself.getDep_id());
				//判断打卡时间是否早于开始时间
				Date overtime_start_date = signInShiftWork.getWorking_start();
				
				//比较时间先后
				if( todayDateSdf.before(overtime_start_date) ){
					//现在的时间早于加班开始时间，正常签到
					insertAttendanceMap.put("attendance_status", 1);
					
				}else{
					//现在的时间晚于加班开始时间，迟到了
					insertAttendanceMap.put("attendance_status", 2);
				}
				insertAttendanceMap.put("clock_in", now_date);
				insertAttendanceMap.put("is_overtime", 0);	//不是加班
				insertAttendanceMap.put("record_id", signInShiftWork.getSw_id());
				
				//调用新增方法
				attendanceService.insertNewAtt(insertAttendanceMap);
				
			}else{	//比对失败，返回首页
				System.out.println("人脸识别失败！");
			}
			
			//传参，回首页
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/director/toHomepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	//打卡！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！下班！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	//临时加班打卡，打上班的卡
	@RequestMapping("/signOffOvertime")
	public String SignOffOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//获得参数
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer oa_id = 0;
			if(request.getParameter("oa_id") != null){
				oa_id = Integer.parseInt(request.getParameter("oa_id"));
			}
			
			boolean check_flag = false;	//人脸识别比对结果
			
			//调用摄像头，调用人脸识别，最多识别4次
			//开两个线程
			//开启摄像头
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//传参
			String basePath = request.getServletContext().getRealPath("/");
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//仍未比对成功，继续比对
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//结束camera线程了
					break;
				}
				try {
					Thread.sleep(100);	//100毫秒判断一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//关闭线程
			
			
			//传参
			request.setAttribute("s_id", sId);
			if(check_flag){	//比对成功了，进行考勤，返回首页
				System.out.println("人脸识别成功！");
				
				//1、修改这条记录的签到状态
				Overtimeapplication signOffOvertime = overtimeService.findOvertimeApplyByOaid(oa_id);
				HashMap<String, Object> updateOvertimeMap = new HashMap<String, Object>();
				updateOvertimeMap.put("oa_id", oa_id);
				updateOvertimeMap.put("is_sign", 2);	//打了结束的卡了
				//调用修改方法
				overtimeService.updateOvertimeApplyByOaid(updateOvertimeMap);
				
				
				//获得今天的日期
				Date todayDate = new Date();
				SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_overtime.format(todayDate);	//当前时间
				Date todayDateSdf = sdf_overtime.parse(now_date);	//格式化后的现在时间
				
				//2、寻找这条对应的考勤记录（根据记录id，修改结束时间、考勤状态
				HashMap<String, Object> searchAttendanceMap = new HashMap<String, Object>();
				searchAttendanceMap.put("is_overtime", 1);
				searchAttendanceMap.put("record_id", signOffOvertime.getOa_id());
				Attendance selectAttendance = attendanceService.findAttendanceByRecordId(searchAttendanceMap);
				//开始修改这条考勤记录的结束时间、考勤状态
				HashMap<String, Object> updateAttendanceMap = new HashMap<String, Object>();
				updateAttendanceMap.put("at_id", selectAttendance.getAt_id());	//根据考勤记录id查找这条记录
				updateAttendanceMap.put("clock_off", now_date);	//打签退卡的当前时间
				
				//判断打卡时间是否早于结束时间
				Date overtime_end_date = signOffOvertime.getOvertime_end();
				
				//比较时间先后
				if( todayDateSdf.before(overtime_end_date) ){
					//现在的时间早于加班结束时间，说明早退了
					if(selectAttendance.getAttendance_status() == 1){	//正常签到，说明只是早退
						updateAttendanceMap.put("attendance_status", 3);
					}else{	//说明是2，是迟到的，现在又早退了
						updateAttendanceMap.put("attendance_status", 4);
					}
				}else{
					//现在的时间晚于加班结束时间，说明正常签退了
					if(selectAttendance.getAttendance_status() == 1){	//正常签到，说明只是早退
						updateAttendanceMap.put("attendance_status", 1);
					}else{	//签到时迟到了，现在正常结束
						updateAttendanceMap.put("attendance_status", 2);
					}
				}
				
				//调用修改方法
				attendanceService.updateAttendanceByAtid(updateAttendanceMap);				
			}else{	//比对失败，返回首页
				System.out.println("人脸识别失败！");
			}
			
			//传参，回首页
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/director/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}

	//临时加班打卡，打上班的卡
	@RequestMapping("/signOffShiftWork")
	public String signOffShiftWork(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//获得参数
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//页面传来的参数
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller之间通过session传参的
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer sw_id = 0;
			if(request.getParameter("sw_id") != null){
				sw_id = Integer.parseInt(request.getParameter("sw_id"));
			}
			
			boolean check_flag = false;	//人脸识别比对结果
			
			//调用摄像头，调用人脸识别，最多识别4次
			//开两个线程
			//开启摄像头
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//传参
			String basePath = request.getServletContext().getRealPath("/");
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//仍未比对成功，继续比对
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//结束camera线程了
					break;
				}
				try {
					Thread.sleep(100);	//100毫秒判断一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//关闭线程
			
			
			//传参
			request.setAttribute("s_id", sId);
			if(check_flag){	//比对成功了，进行考勤，返回首页
				System.out.println("人脸识别成功！");
				
				//1、修改这条记录的签到状态
				ShiftWork signOffShiftWork = shiftWorkService.findShiftWorkByswid(sw_id);
				HashMap<String, Object> updateShiftWorkMap = new HashMap<String, Object>();
				updateShiftWorkMap.put("sw_id", sw_id);
				updateShiftWorkMap.put("attendence_status", 2);	//打了结束的卡了
				//调用修改方法
				shiftWorkService.updateShiftWork(updateShiftWorkMap);				
				
				//获得今天的日期
				Date todayDate = new Date();
				SimpleDateFormat sdf_shiftWork = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_shiftWork.format(todayDate);	//当前时间
				Date todayDateSdf = sdf_shiftWork.parse(now_date);	//格式化后的现在时间
				
				//2、寻找这条对应的考勤记录（根据记录id，修改结束时间、考勤状态
				HashMap<String, Object> searchAttendanceMap = new HashMap<String, Object>();
				searchAttendanceMap.put("is_overtime", 0);	//不是加班
				searchAttendanceMap.put("record_id", signOffShiftWork.getSw_id());
				Attendance selectAttendance = attendanceService.findAttendanceByRecordId(searchAttendanceMap);
				//开始修改这条考勤记录的结束时间、考勤状态
				HashMap<String, Object> updateAttendanceMap = new HashMap<String, Object>();
				updateAttendanceMap.put("at_id", selectAttendance.getAt_id());	//根据考勤记录id查找这条记录
				updateAttendanceMap.put("clock_off", now_date);	//打签退卡的当前时间
				
				//判断打卡时间是否早于结束时间
				Date shiftWork_end_date = signOffShiftWork.getWorking_end();				
				//比较时间先后
				if( todayDateSdf.before(shiftWork_end_date) ){
					//现在的时间早于加班结束时间，说明早退了
					if(selectAttendance.getAttendance_status() == 1){	//正常签到，说明只是早退
						updateAttendanceMap.put("attendance_status", 3);
					}else{	//说明是2，是迟到的，现在又早退了
						updateAttendanceMap.put("attendance_status", 4);
					}
				}else{
					//现在的时间晚于加班结束时间，说明正常签退了
					if(selectAttendance.getAttendance_status() == 1){	//正常签到，说明只是早退
						updateAttendanceMap.put("attendance_status", 1);
					}else{	//签到时迟到了，现在正常结束
						updateAttendanceMap.put("attendance_status", 2);
					}
				}
				
				//调用修改方法
				attendanceService.updateAttendanceByAtid(updateAttendanceMap);				
			}else{	//比对失败，返回首页
				System.out.println("人脸识别失败！");
			}
			
			//传参，回首页
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/director/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
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
		Integer dep_id = staffService.findDepidBySid(sId);	//获得该主管对应的dep_id
		//本部门所有员工List
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		//整理出本月所有员工的工作情况List
		//双层循环，外层循环这个月的每一天，内层循环本部门所有员工
		//最终要得到的工作情况list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//该部门该月所有员工的所有工作班次
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("dep_id", dep_id);
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
				selectOneShiftMap.put("dep_id", dep_id);
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
						workCondition.setDep_id(dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(todayAttendanceShift.getClock_in());
						workCondition.setWorking_end(todayAttendanceShift.getClock_off());
						workCondition.setS_name(staff.getS_name());
					}else{	//该员工这一天没有考勤记录
						//记录为旷工
						workCondition.setAttendence_status("旷工");	//6代表旷工
						workCondition.setDep_id(dep_id);
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
						workCondition.setDep_id(dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setAttendence_status("请假");//5代表请假状态
						workCondition.setS_name(staff.getS_name());
					}else{	//也没有请假记录，没有班次安排，也没有请假，说明放假？？周末？？？？
						workCondition.setAttendence_status("无");
						workCondition.setDep_id(dep_id);
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
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("workConditionList", workConditionList);
		request.setAttribute("staffList", staffList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("dep_id", dep_id);
		request.setAttribute("staff_number", staffList.size());
		
		return "director/selectAllStaffWorkConByMonthByDep";
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
		
		//该员工对象
		Staff select_staff = staffService.findStaffById(select_sid);
		//获得部门id
		Integer dep_id = staffService.findDepidBySid(select_sid);	//获得该员工对应的dep_id
		//该部门所有员工列表
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		//整理出本月该员工的工作情况List
		//单层循环，外层循环这个月的每一天
		//最终要得到的工作情况list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//该部门该月所有员工的所有工作班次
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", select_sid);
		selectMap.put("dep_id", dep_id);
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
			selectOneShiftMap.put("dep_id", dep_id);
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
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
				}else{	//该员工这一天没有考勤记录
					//记录为旷工
					workCondition.setAttendence_status("旷工");	//6代表旷工
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
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
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("请假");//5代表请假状态
					workCondition.setS_name(select_staff.getS_name());
				}else{	//也没有请假记录，没有班次安排，也没有请假，说明放假？？周末？？？？
					workCondition.setAttendence_status("无");
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}
			//一次循环结束，将这一条工作情况添加到List中
			workConditionList.add(workCondition);
		}
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("workConditionList", workConditionList);
		request.setAttribute("select_staff", select_staff);
		request.setAttribute("staffList", staffList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("dep_id", dep_id);
		//request.setAttribute("staff_number", staffList.size());
		
		return "director/selectOneStaffWorkConByMonthByDep";
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
		
		return "director/selectOneOvertimeConByMonthByDep";
	}
	
	
	
	//———————————————————工作班次部分——————————
	
	/**
	 * 新增
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	//显示新增工作安排记录页面
	@RequestMapping("/showAddNewShiftWork")
	public String showAddNewShiftWork(HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			
			Integer usr_id = 101;
			HttpSession session = request.getSession();
			if(request.getParameter("usr_id") != null){
				usr_id = Integer.parseInt(request.getParameter("usr_id"));
			}else if(session.getAttribute("usr_id") != null){
				usr_id = (Integer)session.getAttribute("usr_id");
			}
			
			//来自查询jsp的参数
			String getDepId = request.getParameter("dep_id");
			String getSId = request.getParameter("s_id");
			String getSName = request.getParameter("s_name");
			Integer getYear = Integer.parseInt(request.getParameter("work_year"));
			Integer getMonth = Integer.parseInt(request.getParameter("work_month"));
			Integer getDate = Integer.parseInt(request.getParameter("work_date"));
			//当前页面显示的s_id---->s_id_now
			String getSidNow = request.getParameter("s_id_now");
			System.out.println("controller.showadd get from selectjsp:"+"部门ID"+getDepId+"员工ID"+getSId+"年"+getYear+"月"+getMonth+"日"+getDate);
			
			
			//根据jsp传来的参数构造日期
			Calendar workDate = Calendar.getInstance();
			workDate.set(getYear, getMonth, getDate);
			
			//时间格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//calendar转string
			String workDateString = sdf.format(workDate.getTime());
			//string转timestamp
			Timestamp workDateTime = new Timestamp(sdf.parse(workDateString).getTime());
			
			System.out.println("controller.showadd构造工作日期"+workDateTime);//转成timestamp输出
			
			//参数放到map里传给showaddjsp
			HashMap<String,Object> iswMap = new HashMap<String,Object>();
			iswMap.put("dep_id", getDepId);
			iswMap.put("s_id", getSId);
			iswMap.put("s_name", getSName);
			iswMap.put("work_year", getYear);
			iswMap.put("work_month", getMonth);
			iswMap.put("work_date", getDate);
			iswMap.put("s_id_now", getSidNow);
			
			//给showaddNewShiftWork.jsp
			request.setAttribute("iswMap", iswMap);
			request.setAttribute("usr_id", usr_id);
		
			return "director/addNewShiftWork";
		} catch (ParseException e) {
			// 
			e.printStackTrace();
		}
		return "director/addNewShiftWork";
	}
	
	/**
	 * 
	 * addjsp
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	//新增工作安排记录
	@RequestMapping("/addNewShiftWork")
	public String addNewShiftWork(HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			
			Integer usr_id = 101;
			HttpSession session = request.getSession();
			if(request.getParameter("usr_id") != null){
				usr_id = Integer.parseInt(request.getParameter("usr_id"));
			}else if(session.getAttribute("usr_id") != null){
				usr_id = (Integer)session.getAttribute("usr_id");
			}
			
			//来自查询jsp的参数
			String getDepId = request.getParameter("dep_id");
			String getSId = request.getParameter("s_id");
			String getSName = request.getParameter("s_name");
			Integer getYear = Integer.parseInt(request.getParameter("work_year"));
			Integer getMonth = Integer.parseInt(request.getParameter("work_month"));
			Integer getDate = Integer.parseInt(request.getParameter("work_date"));
			String getSidNow = request.getParameter("s_id_now");
			System.out.println("controller.add get from addjsp:"+"部门ID"+getDepId+"员工ID"+
			getSId+"年"+getYear+"月"+getMonth+"日"+getDate+"页面员工显示情况"+getSidNow);
			
			//来自新增jsp的参数
			Integer getsHour = 0;
			Integer getsMinute = 0;
			Integer geteHour = 0;
			Integer geteMinute = 0;
			
			//start_hour
			if(request.getParameter("start_hour")!=null) {
				getsHour = Integer.parseInt(request.getParameter("start_hour"));	
			}
			else {
				getsHour = 0;
			}
			//start_minute
			if(request.getParameter("start_minute")!=null) {
				getsMinute = Integer.parseInt(request.getParameter("start_minute"));			
			}
			else {
				getsMinute = 0;
			}
			//end_hour
			if(request.getParameter("end_hour")!=null) {
				geteHour = Integer.parseInt(request.getParameter("end_hour"));	
			}
			else {
				geteHour = 0;
			}
			//end_minute
			if(request.getParameter("end_minute")!=null) {
				geteMinute = Integer.parseInt(request.getParameter("end_minute"));			
			}
			else {
				geteMinute = 0;
			}
			
			System.out.println("controller.from addjsp"+"开始小时"+getsHour+"开始分钟"+getsMinute);
			System.out.println("controller.from addjsp"+"结束小时"+geteHour+"结束分钟"+geteMinute);
		
			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			workStartT.set(getYear, getMonth, getDate, getsHour, getsMinute, 0);
			workEndT.set(getYear, getMonth, getDate, geteHour, geteMinute, 0);
			
			//时间格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//calendar转string
			String workstartString = sdf.format(workStartT.getTime());
			String workEndString = sdf.format(workEndT.getTime());

			//string转timestamp
			Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
			Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
			
			System.out.println("controller.add:构造开始时间"+workStartTime+"构造结束时间"+workEndTime);//转成timestamp输出
			
			//参数放到map里传给Service
			HashMap<String,Object> iswMap = new HashMap<String,Object>();
			iswMap.put("dep_id", getDepId);
			iswMap.put("s_id", getSId);
			iswMap.put("work_start", workStartTime);
			iswMap.put("work_end", workEndTime);
			//调用新增方法
			shiftWorkService.insertShiftWork(iswMap);
			
			//传参
			session.setAttribute("dep_id", getDepId);
			session.setAttribute("select_year", getYear);
			session.setAttribute("select_month", getMonth);
			session.setAttribute("s_id_now",getSidNow);
			session.setAttribute("usr_id", usr_id);
			
			//重定向，回到查看所有加班申请界面
			return "redirect:/director/selectAllShiftWork";
			
		}catch (ParseException e) {
			e.printStackTrace();
			return "main/login";
		}
	}
		
	/**
	 * update
	 * @param sw_id
	 * @return	updateShiftWork.jsp
	 * @throws ServletException
	 * @throws IOException
	 */
	//显示新增工作安排记录页面
	@RequestMapping("/showUpdateShiftWork")
	public String showUpdateShiftWork(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		Integer usr_id = 101;
		HttpSession session = request.getSession();
		if(request.getParameter("usr_id") != null){
			usr_id = Integer.parseInt(request.getParameter("usr_id"));
		}else if(session.getAttribute("usr_id") != null){
			usr_id = (Integer)session.getAttribute("usr_id");
		}
		
		//来自查询jsp的参数
		String getSwid = request.getParameter("sw_id");
		Integer sw_id = Integer.parseInt(getSwid); 
		//当前页面显示的s_id---->s_id_now
		String getSidNow = request.getParameter("s_id_now");
		
		//根据该sw_id查询sw
		ShiftWork findbySWid = shiftWorkService.findShiftWorkByswid(sw_id);
		
		System.out.println("controller.showupdate from findbySWid"+findbySWid.getSw_id()+" "+findbySWid.getS_id()+" "
		+findbySWid.getWorking_start()+" "+findbySWid.getWorking_end()+" "+findbySWid.getAttendence_status());

		//查询部门
		Department findbydepid = departmentService.findDepById(findbySWid.getDep_id());
		//查询员工
		Staff findbysid = staffService.findStaffById(findbySWid.getS_id());
		//时间格式转换
		//日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(findbySWid.getWorking_start());
		//时间
		SimpleDateFormat t = new SimpleDateFormat("HH:mm");
		String workStart = t.format(findbySWid.getWorking_start());
		String workEnd = t.format(findbySWid.getWorking_end());

		//参数放到map里传给updatejsp
		HashMap<String,Object> uswMap = new HashMap<String,Object>();
		
		uswMap.put("sw_id", sw_id);
		uswMap.put("dep_id", findbySWid.getDep_id());
		uswMap.put("dep_name", findbydepid.getDep_name());
		uswMap.put("s_id",findbySWid.getS_id());
		uswMap.put("s_name", findbysid.getS_name());
		uswMap.put("work_date", date);
		uswMap.put("work_start", workStart);
		uswMap.put("work_end", workEnd);
		uswMap.put("s_id_now", getSidNow);
		
		//给updateShiftWork.jsp
		request.setAttribute("uswMap", uswMap);
		request.setAttribute("usr_id", usr_id);
		return "director/updateShiftWork";

	}
		
		
	//新增工作安排记录
	@RequestMapping("/updateShiftWork")
	public String updateShiftWork(HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			
			//来自查询jsp的参数
			Integer usr_id = 101;
			HttpSession session = request.getSession();
			if(request.getParameter("usr_id") != null){
				usr_id = Integer.parseInt(request.getParameter("usr_id"));
			}else if(session.getAttribute("usr_id") != null){
				usr_id = (Integer)session.getAttribute("usr_id");
			}
			
			String getSwId = request.getParameter("sw_id");
			String getSidNow = request.getParameter("s_id_now");
			String getDate = request.getParameter("work_date");
			String getStart = request.getParameter("work_start");
			String getEnd = request.getParameter("work_end");
			
			Integer sw_id = Integer.parseInt(getSwId);
			
			System.out.println("controller.update from updatejsp"+"记录ID"+sw_id+"员工显示情况"+getSidNow+"日期"
			+getDate+"开始时间"+getStart+"结束时间"+getEnd);
			
			//来自修改jsp的参数
			Integer getsHour = 0;
			Integer getsMinute = 0;
			Integer geteHour = 0;
			Integer geteMinute = 0;
			
			//start_hour
			if(request.getParameter("start_hour")!=null) {
				getsHour = Integer.parseInt(request.getParameter("start_hour"));	
			}
			else {
				getsHour = 0;
			}
			//start_minute
			if(request.getParameter("start_minute")!=null) {
				getsMinute = Integer.parseInt(request.getParameter("start_minute"));			
			}
			else {
				getsMinute = 0;
			}
			//end_hour
			if(request.getParameter("end_hour")!=null) {
				geteHour = Integer.parseInt(request.getParameter("end_hour"));	
			}
			else {
				geteHour = 0;
			}
			//end_minute
			if(request.getParameter("end_minute")!=null) {
				geteMinute = Integer.parseInt(request.getParameter("end_minute"));			
			}
			else {
				geteMinute = 0;
			}
			//date分解
			String yearString = getDate.substring(0,4);
			String monthString = getDate.substring(5,7);
			String dateString = getDate.substring(8,10);
			Integer getYear = Integer.parseInt(yearString);
			Integer getMonth = Integer.parseInt(monthString)-1;
			Integer getDate1 = Integer.parseInt(dateString);
			
			System.out.println("controller.update:获取 date分解(string)"+yearString+" y"+monthString+" m"+dateString+" m");
			System.out.println("controller.update:获取date分解(Integer)"+getYear+" y"+getMonth+" m"+getDate1+" m");

			System.out.println("controller.update:from updatejsp"+"日期"+getDate+"开始小时"+getsHour+"开始分钟"+getsMinute);
			System.out.println("controller.update:from updatejsp"+"日期"+getDate+"结束小时"+geteHour+"结束分钟"+geteMinute);

			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			workStartT.set(getYear, getMonth, getDate1, getsHour, getsMinute, 0);
			workEndT.set(getYear, getMonth, getDate1, geteHour, geteMinute, 0);
			
			//时间格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//calendar转string
			String workstartString = sdf.format(workStartT.getTime());
			String workEndString = sdf.format(workEndT.getTime());

			//string转timestamp
			Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
			Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
			
			System.out.println("controller.update:构造开始时间"+workStartTime+"构造结束时间"+workEndTime);//转成timestamp输出
			
			//参数放到map里传给Service
			HashMap<String,Object> uswMap = new HashMap<String,Object>();
			uswMap.put("sw_id", sw_id);
			uswMap.put("work_start", workStartTime.toString());
			uswMap.put("work_end", workEndTime.toString());
			//调用新增方法
			shiftWorkService.updateShiftWork(uswMap);
			
			ShiftWork updatesw = shiftWorkService.findShiftWorkByswid(sw_id);
			
			//传参
			String depid = updatesw.getDep_id().toString();
			session.setAttribute("dep_id", depid);
			session.setAttribute("select_year", getYear);
			session.setAttribute("select_month", getMonth);
			session.setAttribute("s_id_now",getSidNow);
			session.setAttribute("usr_id", usr_id);
			
			//重定向，回到查看所有加班申请界面
			return "redirect:/director/selectAllShiftWork";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "main/login";
		} catch (ParseException e) {
			e.printStackTrace();
			return "main/login";
		}
	}	
			
	@RequestMapping("/deleteShiftWork")
	public String deleteShiftWork (HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//来自查询jsp的参数
		Integer usr_id = 101;
		HttpSession session = request.getSession();
		if(request.getParameter("usr_id") != null){
			usr_id = Integer.parseInt(request.getParameter("usr_id"));
		}else if(session.getAttribute("usr_id") != null){
			usr_id = (Integer)session.getAttribute("usr_id");
		}
		
		String getSwId = request.getParameter("sw_id");
		String getdepId = request.getParameter("dep_id");
		Integer getYear = Integer.parseInt(request.getParameter("select_year"));
		Integer getMonth = Integer.parseInt(request.getParameter("select_month"));
		String getSidNow = request.getParameter("s_id_now");
		System.out.println("controller.delete 来自selectjsp:记录id"+getSwId+"部门ID"+getdepId
				+"年"+getYear+"月"+getMonth+"当前页面员工显示情况"+getSidNow);
		
		Integer sw_id = Integer.parseInt(getSwId);
		//Integer year = Integer.parseInt(getYear);
		//Integer month = Integer.parseInt(getMonth);
		
		shiftWorkService.deleteShiftWork(sw_id);
		
		System.out.println("controller.delete：删除记录ID为"+sw_id);
		//重定向参数		
		session.setAttribute("dep_id", getdepId);
		session.setAttribute("select_year", getYear);
		session.setAttribute("select_month", getMonth);
		session.setAttribute("s_id_now",getSidNow);
		session.setAttribute("usr_id", usr_id);
		System.out.println("controller.delete重定向传参： 部门ID"+getdepId+"当前页面员工显示情况"+getSidNow);
		
		return "redirect:/director/selectAllShiftWork";
	}
						
	/**
	 * 全选修改
	 * 
	 * 		
	 */
	@RequestMapping("/changeAllShiftWork")
	public String changeAllShiftWork (HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			//接收来自jsp
			Integer usr_id = 101;
			HttpSession session = request.getSession();
			if(request.getParameter("usr_id") != null){
				usr_id = Integer.parseInt(request.getParameter("usr_id"));
			}else if(session.getAttribute("usr_id") != null){
				usr_id = (Integer)session.getAttribute("usr_id");
			}
			
			//星期几选中
			String []weekday = request.getParameterValues("weekday");
			int []workday = new int[7];
			
			for (int i = 0; i < 7; i++) {
				workday[i]=0;
				System.out.println("workday"+i+"   ="+workday[i]);
			}

			for (int i = 0; i <weekday.length; i++) {
				System.out.println("weekday    "+weekday[i]);
				Integer tempweekday = Integer.parseInt(weekday[i]);
				workday[tempweekday-1] = 1;
			}
			
			for (int i = 0; i < 7; i++) {
				System.out.println("workday"+i+"   ="+workday[i]);
			}
			
			Integer getDepid = Integer.parseInt(request.getParameter("dep_id"));
			Integer getYear = Integer.parseInt(request.getParameter("select_year"));
			Integer getMonth = Integer.parseInt(request.getParameter("select_month"));
			Integer getSidNow = Integer.parseInt(request.getParameter("s_id"));
			
			System.out.println("changeAll:  部门ID"+getDepid+"年"+getYear+"月"+getMonth+"员工IDnow"+getSidNow);
			//时间
			//来自修改jsp的参数
			Integer getsHour = 0;
			Integer getsMinute = 0;
			Integer geteHour = 0;
			Integer geteMinute = 0;
			
			//start_hour
			if(request.getParameter("start_hour")!=null) {
				getsHour = Integer.parseInt(request.getParameter("start_hour"));	
			} else {
				getsHour = 0;
			}
			//start_minute
			if(request.getParameter("start_minute")!=null) {
				getsMinute = Integer.parseInt(request.getParameter("start_minute"));			
			} else {
				getsMinute = 0;
			}
			//end_hour
			if(request.getParameter("end_hour")!=null) {
				geteHour = Integer.parseInt(request.getParameter("end_hour"));	
			} else {
				geteHour = 0;
			}
			//end_minute
			if(request.getParameter("end_minute")!=null) {
				geteMinute = Integer.parseInt(request.getParameter("end_minute"));			
			} else {
				geteMinute = 0;
			}
			
			//定义开始结束时间
			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			//时间格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
				
			//构造日期段----查询
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(getYear, getMonth, 1);
					
			//calendar1.add(calendar1.MONTH, -1);
			calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));

			//下一个月
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(getYear, getMonth, 1);

			calendar2.add(calendar1.MONTH, 1);
			calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String nowDate = sdf1.format(calendar1.getTime());
			String nextDate = sdf1.format(calendar2.getTime());
			System.out.println("controller.changeAll:构造开始日期"+nowDate+"构造结束日期"+nextDate);//转成timestamp输出
			

			if (getSidNow.equals(0)) {
				//全体员工
				//传给service的Map
				HashMap<String, Object> sallMap = new HashMap<String,Object>();
				sallMap.put("dep_id", getDepid);
				sallMap.put("working_start_start",nowDate);
				sallMap.put("working_start_end", nextDate);
				//得到参数List
				List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
				//List<Department> depList = departmentService.selectAllDepartment();
				List<Staff> sList = staffService.selectStaffByDepId(getDepid);
				
				/**
				*新增或者更新
				*/
				Integer i = 0;
				//记录swallList长度
				Integer swalllenth = swallList.size();//123---012
				//得到该月的最后一天
				int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int x = 1; x <=lastDay; x++) {
					//时间段构造
					workStartT.set(getYear, getMonth, x, getsHour, getsMinute, 0);
					workEndT.set(getYear, getMonth, x, geteHour, geteMinute, 0);
					//calendar转string
					String workstartString = sdf.format(workStartT.getTime());
					String workEndString = sdf.format(workEndT.getTime());
					//string转timestamp
					Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
					Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
					System.out.println("changeAll:开始时间"+workstartString+"结束时间"+workEndString);
					
					//内层遍历员工
					for (int j=0; j<sList.size(); j++) {
					
						Integer staffsid   = sList.get(j).getS_id();
						
						if (i<=swalllenth-1) {
							//取swallList里的s_id
							Integer sw_sid = swallList.get(i).getS_id();
							//取swallList里的sw_id
							Integer swid = swallList.get(i).getSw_id();
							//取swallList里的日期中的日（int）
							Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());
							System.out.println("controller.selectall.changeall补空List当前日期: "+x+"当前员工id: "+staffsid+
									"swallList里的日期:"+nowDayInteger+"swallList里s_id:"+sw_sid);
							
							
							if ((nowDayInteger.equals(x))&&(staffsid.equals(sw_sid))) {
								//相等调用修改
								//星期几选中
								int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
								if (workday[weekdaynow-1]==0) {//未选中跳出继续
									i++;
									continue;
								}
								//参数放到map里传给Service
								HashMap<String,Object> uswMap = new HashMap<String,Object>();
								uswMap.put("sw_id",swid);
								uswMap.put("work_start", workStartTime.toString());
								uswMap.put("work_end", workEndTime.toString());
								//调用更新方法
								shiftWorkService.updateShiftWork(uswMap);
								
								System.out.println("controller.selectall.chanAll:swallList修改:当前日期"+nowDayInteger+"sw表中员工ID"+sw_sid);
								//swAllList计数
								i++;
							}else {
								//星期几选中
								int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
								if (workday[weekdaynow-1]==0) {//未选中跳出继续
									continue;
								}
								
								HashMap<String,Object> iswMap = new HashMap<String,Object>();
								iswMap.put("dep_id", getDepid);
								iswMap.put("s_id", staffsid);
								iswMap.put("work_start", workStartTime);
								iswMap.put("work_end", workEndTime);
								
								System.out.println("这是controller1！！！+work_start+"+workStartTime+"work_end"+workEndTime);
								
								//调用新增方法
								shiftWorkService.insertShiftWork(iswMap);
							}
						}else {
							//星期几选中
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//未选中跳出继续
								//i++;
								continue;
							}
							//此时得到的swAllList已经遍历完毕
							HashMap<String,Object> iswMap = new HashMap<String,Object>();
							iswMap.put("dep_id", getDepid);
							iswMap.put("s_id", staffsid);
							iswMap.put("work_start", workStartTime);
							iswMap.put("work_end", workEndTime);
							
							System.out.println("这是controller2！！！+work_start+"+workStartTime+"work_end"+workEndTime);
							
							//调用新增方法
							shiftWorkService.insertShiftWork(iswMap);	
						}		
					}
				}
			}else {
				//某个员工
				HashMap<String, Object> soneMap = new HashMap<String,Object>();
				soneMap.put("s_id", getSidNow);
				soneMap.put("dep_id", getDepid);
				soneMap.put("working_start_start", nowDate);
				soneMap.put("working_start_end", nextDate);
				System.out.println("controller.selectone: 员工ID"+getSidNow+"部门ID"+getDepid+"开始日期"+nowDate+"结束日期"+nextDate);
				List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
				
				//遍历swoneList
				int i=0;
				//记录swoneList长度
				Integer swonelenth = swoneList.size();//123---012
				//得到该月的最后一天
				int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				//按日期遍历
				for (int x=1;x<=lastDay;x++){
					//时间段构造
					workStartT.set(getYear, getMonth, x, getsHour, getsMinute, 0);
					workEndT.set(getYear, getMonth, x, geteHour, geteMinute, 0);
					//calendar转string
					String workstartString = sdf.format(workStartT.getTime());
					String workEndString = sdf.format(workEndT.getTime());
					//string转timestamp
					Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
					Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
					System.out.println("changeOne:开始时间"+workstartString+"结束时间"+workEndString);
					
					if (i<=swonelenth-1) {
						//swpneList遍历未结束
						//取swoneList里的日期中的日（int）
						Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	
						//获取员工ID
						Integer sw_sid = swoneList.get(i).getS_id();
						//获取记录ID
						Integer swid = swoneList.get(i).getSw_id();
						System.out.println("controller.selectone补空表:      当前日期"+x+"当前员工id"+getSidNow+"swoneList里的日期"+nowDayInteger);

						if (nowDayInteger.equals(x)) {
							//若该条记录存在,修改
							//星期几选中
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//未选中跳出继续
								i++;
								continue;
							}
							
							//参数放到map里传给Service
							HashMap<String,Object> uswMap = new HashMap<String,Object>();
							uswMap.put("sw_id",swid);
							uswMap.put("work_start", workStartTime.toString());
							uswMap.put("work_end", workEndTime.toString());
							//调用更新方法
							shiftWorkService.updateShiftWork(uswMap);
							
							System.out.println("controller.selectall.chanAll:swallList修改:当前日期"+nowDayInteger+"sw表中员工ID"+sw_sid);
							i++;
						}else {
							//星期几选中
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//未选中跳出继续
								continue;
							}
							//若该条记录不存在，新增
							HashMap<String,Object> iswMap = new HashMap<String,Object>();
							iswMap.put("dep_id", getDepid);
							iswMap.put("s_id", getSidNow);
							iswMap.put("work_start", workStartTime);
							iswMap.put("work_end", workEndTime);
							//调用新增方法
							shiftWorkService.insertShiftWork(iswMap);
						}
					}else {
						//星期几选中
						int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
						if (workday[weekdaynow-1]==0) {//未选中跳出继续
							//i++;
							continue;
						}
						//swone遍历结束
						HashMap<String,Object> iswMap = new HashMap<String,Object>();
						iswMap.put("dep_id", getDepid);
						iswMap.put("s_id", getSidNow);
						iswMap.put("work_start", workStartTime);
						iswMap.put("work_end", workEndTime);
						//调用新增方法
						shiftWorkService.insertShiftWork(iswMap);
					}
				}
			}	
				
				
				
			//重定向参数
			String depid = getDepid.toString();
			String sid = getSidNow.toString();
			
			session.setAttribute("dep_id",depid );
			session.setAttribute("select_year", getYear);
			session.setAttribute("select_month", getMonth);
			session.setAttribute("s_id_now",sid);
			session.setAttribute("usr_id", usr_id);
			
			return "redirect:/director/selectAllShiftWork";
			
		} catch (NumberFormatException e) { 
			e.printStackTrace();
			return "main/login";
		} catch (ParseException e) {
			e.printStackTrace();
			return "main/login";
		}
	}
		
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
				
		Staff staff = staffService.findStaffById(usrid);
		
		Integer depid = staff.getDep_id();
		Department department = departmentService.findDepById(depid);
		
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
		sallMap.put("dep_id", depid);
		sallMap.put("working_start_start",nowDate);
		sallMap.put("working_start_end", nextDate);
		
		System.out.println("controller.selectall.initial:初始值sallMap:开始日期"+nowDate+"结束日期"+nextDate);
		
		
		//将得到参数传给jsp
		List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
		List<Department> depList = departmentService.selectAllDepartment();
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		
		if (swallList.size()<=0) {
			System.out.println("controller.selectall.initial:swallList-----null\n");
		} else {
			for (ShiftWork shiftWork : swallList) {
				System.out.println("controller.selectall.initial:swallList初始值：员工ID"+shiftWork.getS_id()+"**\n");
			}
		}
		
		
		if (sList.size()<=0) {
			System.out.println("controller.selectall.initial:sList-----null\n");
		} else {
			for (Staff staff1 : sList) {
				System.out.println("controller.selectall.initial:sList初始值：员工ID"+staff1.getS_id()+"**\n");
			}
		}
		
		for (Staff staff1 : sList) {
			System.out.println("controller.selectall.initial初始页面得到员工姓名");
			System.out.println(staff1.getS_name());
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
		request.setAttribute("s_id", 0);
		request.setAttribute("sList", sList);
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("depart", department);
		request.setAttribute("usr_id", usrid);
		//跳回
		return "director/selectAllShiftWork";
	
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
		//List<Department> depList = departmentService.selectAllDepartment();
		//获得参数----重定向或者jsp
		String getDepid = "1001";
		if (request.getParameter("dep_id")!=null) {
			getDepid = request.getParameter("dep_id");
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("dep_id") != null) {
				getDepid =(String) session.getAttribute("dep_id");
			}
		}
		
		//登录的用户id
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
		
		String getSid = "0";
		if (request.getParameter("s_id")!=null) {
			getSid = request.getParameter("s_id");
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id_now") != null) {
				getSid= (String) session.getAttribute("s_id_now");
			}
		}
		
		Integer year=0;
		String getYearid = "0";
		if (request.getParameter("select_year")!=null) {
			getYearid = request.getParameter("select_year");
			year = Integer.parseInt(getYearid);
			
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("select_year") != null) {
				year = (Integer) session.getAttribute("select_year");
			}
		}
		
		Integer month=0;
		String getMonth = "0";
		if (request.getParameter("select_month")!=null) {
			getMonth = request.getParameter("select_month");
			month = Integer.parseInt(getMonth);
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if (session.getAttribute("select_month") != null) {
				month = (Integer) session.getAttribute("select_month");
			}
		}

		System.out.println("controller.selectall.  from jsp:年"+year+"月"+month+"部门ID"+getDepid+"员工ID"+getSid);
		
		
		//把该部门所有的staff传给jsp
		Integer depid = Integer.parseInt(getDepid);
		Integer sid  = Integer.parseInt(getSid);
		
		System.out.println("看看controller拿到的depId是啥："+depid);
		
		
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		Department department = departmentService.findDepById(depid);
		
		if(department == null){
			System.out.println("department是空啊！！！！！！！！！！！！！！！！！！！！！");
		}
		
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
		} else {
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
			request.setAttribute("swList", jspswList);
		}
		
		
		//for (Staff st : sList) {
		//	System.out.println("这是controller");
		//	System.out.println("测staff："+st.getS_name()+"---------------------\n");
		//}
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", sid);
		request.setAttribute("depart", department);
		request.setAttribute("sList", sList);
		request.setAttribute("usr_id", usrid);
		
		for (Staff staff : sList) {
			System.out.println(staff.getS_name()+staff.getS_pass());
		}
		System.out.println("s_id------"+sid);
		System.out.println("看看department"+department.getDep_name());
		
		return "director/selectAllShiftWork";
	}
	
	
	//————————————工作班次部分结束————————————
	
}