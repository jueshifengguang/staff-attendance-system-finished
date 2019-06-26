package com.as.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
 * 普通员工控制器
 * @author dzz
 * 2019年6月8日 上午3:03:21
 * AttendanceSystem
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

	//依赖的service对象
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
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
	
	
	//————————————————————————————————————————员工部分———————————————————————————————
	
	@RequestMapping("/staffSelectStaffInfoJump")
	public String staffSelectStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		//传参
		request.setAttribute("s_id", sId);
		
		return "/staff/staffSelectStaffInfo";
	}
	@RequestMapping("/staffSelectStaffInfo")
	public String findStaffById(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		//查看自己的所有账户信息
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
        //调用查询方法
    	Staff staff = staffService.findStaffById(sId);
						
		//传参
		request.setAttribute("staff", staff);
        request.setAttribute("s_id", sId);
		
		//跳转
		return "/staff/staffSelectStaffInfo";
	}
	
	
	@RequestMapping("/staffUpdateStaffInfoJump")
	public String staffUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "/staff/staffUpdateStaffInfo";
	}
	
	//员工更新自己的账户信息	
	@RequestMapping("/staffUpdateStaffInfo")
	public String updateStaffInfo(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
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
		return "redirect:/staff/staffSelectStaffInfo";
	}
	
	
	//——————————————————————————————————————员工部分结束——————————————————————————————
	
	
	
	//————————————————————————————————————————————————请假部分——————————————————————————
	@RequestMapping("/addNewAskforleaveJump")
	public String addNewAskforleaveJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//获得参数
		Integer s_id = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			s_id = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			s_id = (Integer)session.getAttribute("s_id");
		}
				
		//获得已选择的请假信息
		request.setAttribute("s_id", s_id);
		
		return "staff/addNewAskforleave";
	}
	
	@RequestMapping("/addNewAskforleave")
	public String test(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
				
		//获得页面传来的参数
		Integer getSId = Integer.parseInt(request.getParameter("s_id")); 
		Integer getIsApproved = Integer.parseInt(request.getParameter("is_approved"));
		Integer getIsResumed = Integer.parseInt(request.getParameter("is_resumed"));
		Integer getLeaveType = Integer.parseInt(request.getParameter("leave_type"));
		String getStartTime = request.getParameter("starting_date");
		String getEndTime = request.getParameter("ending_date");
		String leave_reason = request.getParameter("leave_reason");
		String approved_reason = request.getParameter("approved_reason");
		
		//把参数放到Map中
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("s_id", getSId);
		toMap.put("is_approved", getIsApproved);
		toMap.put("is_resumed", getIsResumed);
		toMap.put("starting_date", getStartTime);
		toMap.put("ending_date", getEndTime);
		toMap.put("leave_reason", leave_reason);
		toMap.put("approved_reason", approved_reason);
		toMap.put("leave_type", getLeaveType);
		
		//调用新增的方法
		askforleaveService.insertNewAskforApply(toMap);
		request.setAttribute("s_id", getSId);
		
		HttpSession session = request.getSession();
		session.setAttribute("s_id", getSId);
		
		//新增消息，提醒主管有新的请假申请了
		System.out.println("新增请假申请时的getSid："+getSId);
		String a = addNewMessageBeforeApprovedForStaff(getSId);
		
		//重定向，到查看请假申请列表页面
		return "redirect:/staff/listAllAskforleaveBySid";
		//return "staff/homepage";
	}
	
	//查看自己的未销假的请假申请
	@RequestMapping("/listAllAskforleaveBySid")
	public String listAllAskforleaveBySid(HttpServletRequest request) throws ServletException, IOException{
	
		request.setCharacterEncoding("UTF-8");
		
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//页面传来的参数
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller之间通过session传参的
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		} 
		
		//查看自己的请假申请List
		//这里
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(sId);
		
		//传参
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", sId);
		
		//跳转
		return "staff/listAllAskforleaveBySid";
	}
	
	//查看自己的已销假的请假申请
	@RequestMapping("/listAllAskforleaveBySidChanged")
	public String listAllAskforleaveBySidChanged(HttpServletRequest request) throws ServletException, IOException{		
		request.setCharacterEncoding("UTF-8");
		
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));		
		
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(s_id);
		
		//传参
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", s_id);
		
		//跳转
		return "staff/listAllAskforleaveBySidChanged";
	}
	
	@RequestMapping("/listAllAskforleaveBySidJump")
	public String listAllAskforleaveBySidJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		//String is_approved =request.getParameter("is_approved");
				
		//获得已选择的请假信息
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		request.setAttribute("s_id", s_id);
		
		return "staff/updateAskforleaveByresumed";
	}
	
	//销假
	@RequestMapping("/updateAskforleaveByresumed")
	public String updateAskforleaveByresumed(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//获得参数
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		String is_resumed =request.getParameter("is_resumed");
		
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
		return "redirect:/staff/listAllAskforleaveBySid";
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
		return "staff/message";
	}
	//已读消息
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
		return "staff/message";
	}
	//把未读的变成已读消息
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
		
		return "redirect:/staff/selectNoReadMessageApply";
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
		return "redirect:/staff/selectReadMessageApply";
	}
	
	//员工申请了新的请假后，发消息给他的主管
	public String addNewMessageBeforeApprovedForStaff(Integer s_id)	{
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    
	    //获得该普通员工的部门id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //获得该部门的所有员工List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//找到主管了
	  			System.out.println("发消息时，找到主管了么？？？");
	  			 GetsId=message.getS_id();
	  			 String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"提交了新的的请假申请，请尽快进行审批";
	  		    
	  		    HashMap<String, Object> toMap = new HashMap<String, Object>();
	  		    
	  		    toMap.put("s_id", GetsId);
	  		    toMap.put("m_time", m_time);
	  		    toMap.put("is_read", is_read);
	  		    toMap.put("m_content", m_content);
	  		    messageService.insertNewMessage(toMap);	
	  		    break;
	  		}
	  	}	  							
		return null;
	}
	
	
	//_______________________
	
	//员工新增了加班申请后，给他部门的主管发消息提醒他审批
	public String addMessageToDirectorForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());

	    //获得该普通员工的部门id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //获得该部门的所有员工List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//找到主管了
	  			 GetsId=message.getS_id();
	  			 
	  			String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"提交了新的的加班申请，请尽快进行审批";
	  		    
	  		    HashMap<String, Object> toMap = new HashMap<String, Object>();
	  		    
	  		    toMap.put("s_id", GetsId);
	  		    toMap.put("m_time", m_time);
	  		    toMap.put("is_read", is_read);
	  		    toMap.put("m_content", m_content);
	  		    messageService.insertNewMessage(toMap);
	  		    break;
	  		}
	  	}		  							
		return null;
	}
	
	//员工申请了临时加班后，给他的主管发消息，通知他一下（毕竟他不需要审批）
	public String addMessageToDirectorForTemporaryOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    
	    //获得该普通员工的部门id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //获得该部门的所有员工List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//找到主管了
	  			 GetsId=message.getS_id();
	  			 
	  			String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"申请了新的临时加班";
	  		    
	  		    HashMap<String, Object> toMap = new HashMap<String, Object>();
	  		    
	  		    toMap.put("s_id", GetsId);
	  		    toMap.put("m_time", m_time);
	  		    toMap.put("is_read", is_read);
	  		    toMap.put("m_content", m_content);
	  		    messageService.insertNewMessage(toMap);	
	  		} 
	  	}	  							
		return null;
	}
	
	//————————————————————————————————————————————————消息部分结束——————————————————————————
	
	//————————工作班次部分————————————————
	
	//从timestamo类型取出int类型的日期
	public static int getDay(Timestamp timestamp) {
		String tString = timestamp.toString();
		//System.out.println("controller.时间转换得到timestamp类型的日期tString"+tString);
		Integer tInteger = Integer.parseInt(tString.substring(8,10));
		//System.out.println("controller.时间转换得到timestamp类型的日期：tInteger"+tInteger);
		return tInteger;
	}

	//查找某部门全部员工工作班次
	@RequestMapping("/firstSelectMyShiftWork")
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
		
		System.out.println("接到的usr_id是什么："+usrid);
		
		Staff staff = staffService.findStaffById(usrid);
		Integer depid = staff.getDep_id();
		
		
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
		HashMap<String, Object> soneMap = new HashMap<String,Object>();
		soneMap.put("s_id", usrid);
		soneMap.put("dep_id", depid);
		soneMap.put("working_start_start",nowDate);
		soneMap.put("working_start_end", nextDate);
		
		//System.out.println("controller.selectall.initial:初始值sallMap:开始日期"+nowDate+"结束日期"+nextDate);
		
		
		//将得到参数传给jsp
		List <ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
		//List<Staff> sList = staffService.selectStaffByDepId(depid);
		
//		if (swoneList.size()<=0) {
//			System.out.println("controller.selectall.initial:swallList-----null\n");
//		} else {
//			for (ShiftWork shiftWork : swoneList) {
//				System.out.println("controller.selectall.initial:swallList初始值：员工ID"+shiftWork.getS_id()+"**\n");
//			}
//		}

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
		Integer swonelenth = swoneList.size();//123---012
		//得到该月的最后一天
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//遍历填空
		//外层循环
		for (int x=1;x<=lastDay;x++){
			//内层循环
			//给jspswList的对象
			ShiftWork jspsw = new ShiftWork();
			jspsw.setDep_id(depid);
			//s_id直接放入
			jspsw.setS_id(usrid);

			if (i<=swonelenth-1) {
	
				Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	

				System.out.println("controller.selectall.initial补空List当前日期: "+x+"当前员工id: "+usrid+
						"swoneList里的日期:"+nowDayInteger);

					
				//判断是否放入working_start和working_end
				if (nowDayInteger.equals(x)) {
					System.out.println("controller.selectall.initial:swallList放入补空List:当前日期"+nowDayInteger);
					
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
		
		request.setAttribute("swList", jspswList);
		request.setAttribute("staff", staff);
		request.setAttribute("dep_id", depid);
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", usrid);
		//跳回
		return "staff/selectMyShiftWork";
	
	}

	@RequestMapping("/selectMyShiftWork")
	public String showSelectOneswByMonthByDep (HttpServletRequest request) throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		//把所有的dep_id传给jsp
		//获得参数----重定向或者jsp
		String getDepid = "1001";
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
		
		
		Staff staff = staffService.findStaffById(sid);
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
		
		//搜索到个人的MapList
		HashMap<String, Object> soneMap = new HashMap<String,Object>();
		soneMap.put("s_id", getSid);
		soneMap.put("dep_id", getDepid);
		soneMap.put("working_start_start", nowDate);
		soneMap.put("working_start_end", nextDate);
		System.out.println("controller.selectone: 员工ID"+getSid+"部门ID"+getDepid+"开始日期"+nowDate+"结束日期"+nextDate);
		List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
		//填空
		List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
		//遍历swoneList
		int i=0;
		//记录swoneList长度
		Integer swonelenth = swoneList.size();//123---012
		//得到该月的最后一天
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//遍历填空
		//外层循环
	
		//查询某员工
		//调用selectone
//		if (swoneList.size()<=0) {
//			//System.out.println("swoneList-----null\n");
//		} else {
//			for (ShiftWork sw : swoneList) {
//				//System.out.println("soneList："+sw.getS_id()+"**\n");
//			}
//		}
		
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
	
					//System.out.println("controller.selectone补空表:      当前日期"+x+"当前员工id"+sid+"swoneList里的日期"+nowDayInteger);
	
					//判断是否放入working_start和working_end
					if (nowDayInteger.equals(x)) {
						//System.out.println("controller.selectone补空：swoneList放入补空表"+nowDayInteger+" "+sid);
							
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
	
		request.setAttribute("dep_id", getDepid);
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", sid);
		request.setAttribute("swList",jspswList);	
		request.setAttribute("staff",staff);	
		return "staff/selectMyShiftWork";
		
	}	
	
	
	
	//————————工作班次部分结束——————————————
	
	
	
	
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
		return "staff/selectAllTemporaryOvertime";
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
			
			//给他的主管发消息
			addMessageToDirectorForTemporaryOvertime(sId);
		}
		
		//传参
		//request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "director/selectOvertimeRecord";
		return "redirect:/staff/selectOvertimeRecord";	//重定向
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
		return "staff/selectOvertimeRecord";
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
		
		return "staff/insertOvertimeRecord";
	}
	
	//填写好申请加班记录的信息后，表单提交到这里，新增申请到数据库
	@RequestMapping("/insertOvertimeDB")
	public String insertOvertimeDB(HttpServletRequest request) throws ServletException, IOException{
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
		
		//发消息
		addMessageToDirectorForOvertime(sId);
		
		//传参
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//重定向，回到查看所有加班申请界面
		return "redirect:/staff/selectOvertimeRecord";
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
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "staff/homepage";
		return "redirect:/staff/selectTodayWorkHomepage";	//回首页前，先获得当天可以打卡、签退的工作班次和加班
	}
	
	//获得当天要在首页上显示的可打卡的工作班次或加班，跳转到首页
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
			if(shiftWorkToday != null){//存在要打下班卡的工作班次
				if(shiftWorkToday.getAttendence_status() == 1){
					Calendar after_time = Calendar.getInstance();
					//before_time.setTime(todayDate);
					after_time.setTime(shiftWorkToday.getWorking_end());	//获得下班打卡时间之后的1小时的时间
					after_time.add(Calendar.HOUR, 1);	//获得1小时后的时间
					String after_time_str = sdf_overtime.format(after_time.getTime());
					Date after_time_date = sdf_overtime.parse(after_time_str);	//格式化后的date
					Date todayDateSdf = sdf_overtime.parse(now_date);	//格式化后的现在时间
					
					//比较时间先后
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
			
			return "staff/homepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	
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
			//System.out.println("controller里的imageDB路径："+basePath);
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
			
			return "redirect:/staff/toHomepage";
		
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
			//System.out.println("controller里的imageDB路径："+basePath);
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
			
			return "redirect:/staff/toHomepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	//打卡！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！下班！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	//临时加班打卡，打上班的卡
	@RequestMapping("/signOffOvertime")
	public String signOffOvertime(HttpServletRequest request) throws ServletException, IOException{
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
			//System.out.println("controller里的imageDB路径："+basePath);
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
			
			return "redirect:/staff/toHomepage";
		
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
			//System.out.println("controller里的imageDB路径："+basePath);
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
			
			return "redirect:/staff/toHomepage";
		
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
	
	//点击查看工作情况后，在这里整理该员工的工作情况，跳转到显示工作情况的页面
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
		Staff select_staff = staffService.findStaffById(sId);
		//获得部门id
		Integer dep_id = staffService.findDepidBySid(sId);	//获得该员工对应的dep_id
		
		//整理出本月该员工的工作情况List
		//单层循环，外层循环这个月的每一天
		//最终要得到的工作情况list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//该部门该月所有员工的所有工作班次
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", sId);
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
			selectOneShiftMap.put("s_id", sId);
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
					workCondition.setS_id(sId);
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
				}else{	//该员工这一天没有考勤记录
					//记录为旷工
					workCondition.setAttendence_status("旷工");	//6代表旷工
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}else{	//该员工该天没有工作班次安排
				//看请假表有没有他
				HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
				//查询的当前日期
				String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
				
				selectAskforleaveMap.put("s_id", sId);
				selectAskforleaveMap.put("now_date", searchAskDate);
				//调用查询方法
				List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
				if(todayAskforleaveList.size() > 0){	//有请假记录，记为请假状态
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("请假");//5代表请假状态
					workCondition.setS_name(select_staff.getS_name());
				}else{	//也没有请假记录，没有班次安排，也没有请假，说明放假？？周末？？？？
					workCondition.setAttendence_status("无");
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
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
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("dep_id", dep_id);
		
		return "staff/selectOneStaffWorkConByMonthByDep";
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
		
		//该员工对象
		Staff select_staff = staffService.findStaffById(sId);
		//获得部门id
		Integer dep_id = staffService.findDepidBySid(sId);	//获得该员工对应的dep_id
		
		//得到本月的加班List
		HashMap<String, Object> selectOvertimeMap = new HashMap<String, Object>();
		selectOvertimeMap.put("s_id", sId);
		selectOvertimeMap.put("begin_date", begin_date_str_detail);
		selectOvertimeMap.put("end_date", end_date_str_detail);
		List<Overtimeapplication> overtimeList = overtimeService.selectOvertimeByMonth(selectOvertimeMap);
		
		System.out.println("正常加班列表的数量是："+overtimeList.size());
		
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
			
			//查找考勤记录
			HashMap<String, Object> selectAttMap = new HashMap<String, Object>();
			selectAttMap.put("is_overtime", 1);
			selectAttMap.put("record_id", selectTmpOvertime.getOa_id());
			Attendance selectAttendance = attendanceService.findAttendanceByRecordId(selectAttMap);
					
			tmpOvertime.setOvertime_start(selectAttendance.getClock_in());
			tmpOvertime.setOvertime_end(selectAttendance.getClock_off());
			
			//放进List
			tmpOvertimeList.add(tmpOvertime);
		}
		
		System.out.println("临时性加班表的数量是："+tmpOvertimeList.size());
		
		//传参
		request.setAttribute("s_id", sId);
		request.setAttribute("tmpOvertimeList", tmpOvertimeList);
		request.setAttribute("overtimeList", overtimeList);
		request.setAttribute("select_staff", select_staff);
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("dep_id", dep_id);
		
		return "staff/selectOneOvertimeConByMonthByDep";
	}
}