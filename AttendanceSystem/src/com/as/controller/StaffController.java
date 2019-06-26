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
 * ��ͨԱ��������
 * @author dzz
 * 2019��6��8�� ����3:03:21
 * AttendanceSystem
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

	//������service����
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
	private AskforleaveService askforleaveService = new AskforleaveServiceImpl();
	private MessageService messageService = new MessageServiceImpl();
	private DepartmentService departmentService = new DepartmentServiceImpl();
	
	//��õ�ǰ���ڵ���һ�죬������ʾΪ"yyyy-MM-dd 00:00:00"
	public static String getNextDate(Date nowDate){
		//��õ�ǰ���ڵ���һ�������
		Calendar calendar_overtime = new GregorianCalendar();
		calendar_overtime.setTime(nowDate);
		calendar_overtime.add(Calendar.DATE, 1);
		Date nextDate = calendar_overtime.getTime();
		
		//��ʽ������
		SimpleDateFormat sdf_riqi = new SimpleDateFormat("yyyy-MM-dd");
		String next_date = sdf_riqi.format(nextDate);
		next_date += " 00:00:00";
		
		return next_date;
	}
	
	//��õ�ǰ���ڵĿ�ʼ��������ʾΪ"yyyy-MM-dd 00:00:00"
	public static String getBeginDate(Date nowDate){
		//��ʽ������
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String begin_date = sdf.format(nowDate);
		begin_date += " 00:00:00";
		
		return begin_date;
	}
	
	
	//��������������������������������������������������������������������������������Ա�����֡�������������������������������������������������������������
	
	@RequestMapping("/staffSelectStaffInfoJump")
	public String staffSelectStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		//����
		request.setAttribute("s_id", sId);
		
		return "/staff/staffSelectStaffInfo";
	}
	@RequestMapping("/staffSelectStaffInfo")
	public String findStaffById(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		//�鿴�Լ��������˻���Ϣ
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
        //���ò�ѯ����
    	Staff staff = staffService.findStaffById(sId);
						
		//����
		request.setAttribute("staff", staff);
        request.setAttribute("s_id", sId);
		
		//��ת
		return "/staff/staffSelectStaffInfo";
	}
	
	
	@RequestMapping("/staffUpdateStaffInfoJump")
	public String staffUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "/staff/staffUpdateStaffInfo";
	}
	
	//Ա�������Լ����˻���Ϣ	
	@RequestMapping("/staffUpdateStaffInfo")
	public String updateStaffInfo(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			if(session.getAttribute("s_id") != null){
				sId = (Integer)session.getAttribute("s_id");
			}
		}
		
		
		//����й�Ա����Ϣҳ�洫���Ĳ���
		String getS_pass = request.getParameter("s_pass");
		
		//�Ѳ����ŵ�Map��
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",sId);
		staffMap.put("s_pass", getS_pass);

		System.out.println(getS_pass);
		
		//���ø��µķ���
		staffService.updateStaffInfoReturnId(staffMap);
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		return "redirect:/staff/staffSelectStaffInfo";
	}
	
	
	//����������������������������������������������������������������������������Ա�����ֽ���������������������������������������������������������������
	
	
	
	//��������������������������������������������������������������������������������������������������ٲ��֡���������������������������������������������������
	@RequestMapping("/addNewAskforleaveJump")
	public String addNewAskforleaveJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer s_id = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			s_id = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			s_id = (Integer)session.getAttribute("s_id");
		}
				
		//�����ѡ��������Ϣ
		request.setAttribute("s_id", s_id);
		
		return "staff/addNewAskforleave";
	}
	
	@RequestMapping("/addNewAskforleave")
	public String test(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
				
		//���ҳ�洫���Ĳ���
		Integer getSId = Integer.parseInt(request.getParameter("s_id")); 
		Integer getIsApproved = Integer.parseInt(request.getParameter("is_approved"));
		Integer getIsResumed = Integer.parseInt(request.getParameter("is_resumed"));
		Integer getLeaveType = Integer.parseInt(request.getParameter("leave_type"));
		String getStartTime = request.getParameter("starting_date");
		String getEndTime = request.getParameter("ending_date");
		String leave_reason = request.getParameter("leave_reason");
		String approved_reason = request.getParameter("approved_reason");
		
		//�Ѳ����ŵ�Map��
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("s_id", getSId);
		toMap.put("is_approved", getIsApproved);
		toMap.put("is_resumed", getIsResumed);
		toMap.put("starting_date", getStartTime);
		toMap.put("ending_date", getEndTime);
		toMap.put("leave_reason", leave_reason);
		toMap.put("approved_reason", approved_reason);
		toMap.put("leave_type", getLeaveType);
		
		//���������ķ���
		askforleaveService.insertNewAskforApply(toMap);
		request.setAttribute("s_id", getSId);
		
		HttpSession session = request.getSession();
		session.setAttribute("s_id", getSId);
		
		//������Ϣ�������������µ����������
		System.out.println("�����������ʱ��getSid��"+getSId);
		String a = addNewMessageBeforeApprovedForStaff(getSId);
		
		//�ض��򣬵��鿴��������б�ҳ��
		return "redirect:/staff/listAllAskforleaveBySid";
		//return "staff/homepage";
	}
	
	//�鿴�Լ���δ���ٵ��������
	@RequestMapping("/listAllAskforleaveBySid")
	public String listAllAskforleaveBySid(HttpServletRequest request) throws ServletException, IOException{
	
		request.setCharacterEncoding("UTF-8");
		
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		} 
		
		//�鿴�Լ����������List
		//����
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(sId);
		
		//����
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", sId);
		
		//��ת
		return "staff/listAllAskforleaveBySid";
	}
	
	//�鿴�Լ��������ٵ��������
	@RequestMapping("/listAllAskforleaveBySidChanged")
	public String listAllAskforleaveBySidChanged(HttpServletRequest request) throws ServletException, IOException{		
		request.setCharacterEncoding("UTF-8");
		
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));		
		
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(s_id);
		
		//����
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", s_id);
		
		//��ת
		return "staff/listAllAskforleaveBySidChanged";
	}
	
	@RequestMapping("/listAllAskforleaveBySidJump")
	public String listAllAskforleaveBySidJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		//String is_approved =request.getParameter("is_approved");
				
		//�����ѡ��������Ϣ
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		request.setAttribute("s_id", s_id);
		
		return "staff/updateAskforleaveByresumed";
	}
	
	//����
	@RequestMapping("/updateAskforleaveByresumed")
	public String updateAskforleaveByresumed(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		String is_resumed =request.getParameter("is_resumed");
		
		//����
		oaJspMap.put("afl_id", afl_id);
		oaJspMap.put("is_resumed", is_resumed);
				
		//������������
		askforleaveService.updateAskApplyforByAflid(oaJspMap);
				
		//����
		request.setAttribute("s_id", s_id);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
				
		//return "director/selectOvertimeRecord";
		return "redirect:/staff/listAllAskforleaveBySid";
	}	
		
	
	//��������������������������������������������������������������������������������������������������ٲ��ֽ�������������������������������������������������������
	
	//��������������������������������������������������������������������������������������������������Ϣ���֡���������������������������������������������������
	
	//�鿴δ����Ϣ
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
		
		//��ת
		return "staff/message";
	}
	//�Ѷ���Ϣ
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
		
		//��ת
		return "staff/message";
	}
	//��δ���ı���Ѷ���Ϣ
	@RequestMapping("/updateMessage")
	public String updateMessage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer m_id = Integer.parseInt(request.getParameter("m_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		Integer is_read = 1;
		//�����ѡ��������Ϣ
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
	
	//ɾ���Ѷ���Ϣ
	@RequestMapping("/deleteMessage")
	public String deleteMessage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		Integer m_id = 0;
		if(request.getParameter("m_id") != null){
			m_id = Integer.parseInt(request.getParameter("m_id"));
		}
		
		//����ɾ������
		//���ж���û������
		Message selectMessage = messageService.findMessageByMid(m_id);
		if(selectMessage !=null) {
			messageService.deleteMessage(m_id);
		}
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "manager/selectAllTemporaryOvertime";
		return "redirect:/staff/selectReadMessageApply";
	}
	
	//Ա���������µ���ٺ󣬷���Ϣ����������
	public String addNewMessageBeforeApprovedForStaff(Integer s_id)	{
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    
	    //��ø���ͨԱ���Ĳ���id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //��øò��ŵ�����Ա��List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//�ҵ�������
	  			System.out.println("����Ϣʱ���ҵ�������ô������");
	  			 GetsId=message.getS_id();
	  			 String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"�ύ���µĵ�������룬�뾡���������";
	  		    
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
	
	//Ա�������˼Ӱ�����󣬸������ŵ����ܷ���Ϣ����������
	public String addMessageToDirectorForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());

	    //��ø���ͨԱ���Ĳ���id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //��øò��ŵ�����Ա��List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//�ҵ�������
	  			 GetsId=message.getS_id();
	  			 
	  			String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"�ύ���µĵļӰ����룬�뾡���������";
	  		    
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
	
	//Ա����������ʱ�Ӱ�󣬸��������ܷ���Ϣ��֪ͨ��һ�£��Ͼ�������Ҫ������
	public String addMessageToDirectorForTemporaryOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    
	    //��ø���ͨԱ���Ĳ���id
	    Staff applyStaff = staffService.findStaffById(s_id);
	    Integer staffDep_id=applyStaff.getDep_id();
	    //��øò��ŵ�����Ա��List
	    List<Staff> newList = staffService.selectStaffByDepId(staffDep_id);
	    Integer GetsId=0;
	    for(Staff message:newList){ 
	  		if(message.getIdentity() == 2){	//�ҵ�������
	  			 GetsId=message.getS_id();
	  			 
	  			String s_name = applyStaff.getS_name();
	  		    Integer is_read = 0;
	  		    String m_content = s_name+"�������µ���ʱ�Ӱ�";
	  		    
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
	
	//��������������������������������������������������������������������������������������������������Ϣ���ֽ�������������������������������������������������������
	
	//����������������������β��֡�������������������������������
	
	//��timestamo����ȡ��int���͵�����
	public static int getDay(Timestamp timestamp) {
		String tString = timestamp.toString();
		//System.out.println("controller.ʱ��ת���õ�timestamp���͵�����tString"+tString);
		Integer tInteger = Integer.parseInt(tString.substring(8,10));
		//System.out.println("controller.ʱ��ת���õ�timestamp���͵����ڣ�tInteger"+tInteger);
		return tInteger;
	}

	//����ĳ����ȫ��Ա���������
	@RequestMapping("/firstSelectMyShiftWork")
	public String selectAllswByMonthByDepgetFromJsp (HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//�ǵ���������ӵ�������usr_id  ����������������
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
		
		System.out.println("�ӵ���usr_id��ʲô��"+usrid);
		
		Staff staff = staffService.findStaffById(usrid);
		Integer depid = staff.getDep_id();
		
		
		Calendar now = Calendar.getInstance();//Calender��һ��������,
		
		Integer year = now.get(Calendar.YEAR);//��ȡ��ǰ������
		Integer month = now.get(Calendar.MONTH);
		Integer date = now.get(Calendar.DATE);
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
				
		//calendar1.add(calendar1.MONTH, -1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));

		//��һ����
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);

		calendar2.add(calendar1.MONTH, 1);
		calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(calendar1.getTime());
		String nextDate = sdf.format(calendar2.getTime());
		
		//������ʼֵ
		HashMap<String, Object> soneMap = new HashMap<String,Object>();
		soneMap.put("s_id", usrid);
		soneMap.put("dep_id", depid);
		soneMap.put("working_start_start",nowDate);
		soneMap.put("working_start_end", nextDate);
		
		//System.out.println("controller.selectall.initial:��ʼֵsallMap:��ʼ����"+nowDate+"��������"+nextDate);
		
		
		//���õ���������jsp
		List <ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
		//List<Staff> sList = staffService.selectStaffByDepId(depid);
		
//		if (swoneList.size()<=0) {
//			System.out.println("controller.selectall.initial:swallList-----null\n");
//		} else {
//			for (ShiftWork shiftWork : swoneList) {
//				System.out.println("controller.selectall.initial:swallList��ʼֵ��Ա��ID"+shiftWork.getS_id()+"**\n");
//			}
//		}

		List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
		/*****���*****/
		/**
		 * ��swallList��ֵʱ������ֵ�͵�ǰ��Ƚ�
		 * ��ͬ������Ƚ�Ա��id������ͬ������ս������jspswList����
		 * �Ƚ�Ա��id,��ֱͬ�ӷ���jspswList��
		 * ��ͬ������jspswList�ﵱǰԱ��id���
		 * ��swallList��ֵ������������
		 * ֱ�Ӹ�jspswList�����ǰ���ں�Ա��id�����
		 */
		//����swallList
		int i=0;
		//��¼swallList����
		Integer swonelenth = swoneList.size();//123---012
		//�õ����µ����һ��
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//�������
		//���ѭ��
		for (int x=1;x<=lastDay;x++){
			//�ڲ�ѭ��
			//��jspswList�Ķ���
			ShiftWork jspsw = new ShiftWork();
			jspsw.setDep_id(depid);
			//s_idֱ�ӷ���
			jspsw.setS_id(usrid);

			if (i<=swonelenth-1) {
	
				Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	

				System.out.println("controller.selectall.initial����List��ǰ����: "+x+"��ǰԱ��id: "+usrid+
						"swoneList�������:"+nowDayInteger);

					
				//�ж��Ƿ����working_start��working_end
				if (nowDayInteger.equals(x)) {
					System.out.println("controller.selectall.initial:swallList���벹��List:��ǰ����"+nowDayInteger);
					
					Timestamp swStart = swoneList.get(i).getWorking_start();
					Timestamp swEnd = swoneList.get(i).getWorking_end(); 
					
					jspsw.setSw_id(swoneList.get(i).getSw_id());
					jspsw.setWorking_start(swStart);
					jspsw.setWorking_end(swEnd);
					
					i++;
				}
				else {
					//System.err.println("��ֵ����"+x+" "+staffsid);
					jspsw.setWorking_start(null);
					jspsw.setWorking_end(null);
				}
			}
			else {
				//System.err.println("��ֵ����"+x+" "+staffsid);
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
		//����
		return "staff/selectMyShiftWork";
	
	}

	@RequestMapping("/selectMyShiftWork")
	public String showSelectOneswByMonthByDep (HttpServletRequest request) throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		//�����е�dep_id����jsp
		//��ò���----�ض������jsp
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
	
		System.out.println("controller.selectall.  from jsp:��"+year+"��"+month+"����ID"+getDepid+"Ա��ID"+getSid);
		
		
		//�Ѹò������е�staff����jsp
		Integer depid = Integer.parseInt(getDepid);
		Integer sid  = Integer.parseInt(getSid);
		
		
		Staff staff = staffService.findStaffById(sid);
		//��ʼʱ��ת��		
		Integer date = 1;
		
		//��һ����
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
				
		//calendar1.add(calendar1.MONTH, 1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));
	
		//��һ����
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);
		
		calendar2.add(calendar1.MONTH, 1);
		calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String nowDate = sdf.format(calendar1.getTime());
		String nextDate = sdf.format(calendar2.getTime());
		
		//���������˵�MapList
		HashMap<String, Object> soneMap = new HashMap<String,Object>();
		soneMap.put("s_id", getSid);
		soneMap.put("dep_id", getDepid);
		soneMap.put("working_start_start", nowDate);
		soneMap.put("working_start_end", nextDate);
		System.out.println("controller.selectone: Ա��ID"+getSid+"����ID"+getDepid+"��ʼ����"+nowDate+"��������"+nextDate);
		List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
		//���
		List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
		//����swoneList
		int i=0;
		//��¼swoneList����
		Integer swonelenth = swoneList.size();//123---012
		//�õ����µ����һ��
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//�������
		//���ѭ��
	
		//��ѯĳԱ��
		//����selectone
//		if (swoneList.size()<=0) {
//			//System.out.println("swoneList-----null\n");
//		} else {
//			for (ShiftWork sw : swoneList) {
//				//System.out.println("soneList��"+sw.getS_id()+"**\n");
//			}
//		}
		
		for (int x=1;x<=lastDay;x++){
			//��jspswList�Ķ���
			ShiftWork jspsw = new ShiftWork();
			//depid����
			jspsw.setDep_id(depid);
			//s_idֱ�ӷ���
			jspsw.setS_id(sid);
	
			//��swoneListδ��������
			if (i<=swonelenth-1) {
				//ȡswoneList��������е��գ�int��
				Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	
	
					//System.out.println("controller.selectone���ձ�:      ��ǰ����"+x+"��ǰԱ��id"+sid+"swoneList�������"+nowDayInteger);
	
					//�ж��Ƿ����working_start��working_end
					if (nowDayInteger.equals(x)) {
						//System.out.println("controller.selectone���գ�swoneList���벹�ձ�"+nowDayInteger+" "+sid);
							
						Timestamp swStart = swoneList.get(i).getWorking_start();
						Timestamp swEnd = swoneList.get(i).getWorking_end(); 
						jspsw.setSw_id(swoneList.get(i).getSw_id());	
						jspsw.setWorking_start(swStart);
						jspsw.setWorking_end(swEnd);
							
						i++;
					}
					else {
						//System.err.println("��ֵ����"+x+" "+staffsid);
						jspsw.setWorking_start(null);
						jspsw.setWorking_end(null);
					}
				}
				else {
					//System.err.println("��ֵ����"+x+" "+staffsid);
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
	
	
	
	//����������������������β��ֽ�������������������������������
	
	
	
	
	//�鿴���п����������ʱ�Ӱ�
	@RequestMapping("/selectAllTemporaryOvertime")
	public String selectAllTemporaryOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		List<Temporaryovertime> tmpOvertimeNowList = tmpOvertimeService.selectTmpOvertimeByNowDate();
		
		//����
		request.setAttribute("tmpOvertimeNowList", tmpOvertimeNowList);
		request.setAttribute("s_id", sId);
		
		//��ת
		return "staff/selectAllTemporaryOvertime";
	}
	
	//������ʱ�Ӱ�
	@RequestMapping("/applyForTempOvertime")
	public String applyForTempOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer toId = Integer.parseInt(request.getParameter("to_id"));
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = "2019-06-01 17:00:00";
		String overtime_end = "2019-06-01 19:00:00";
		String overtime_reason = "��ʱ�Ӱࡣԭ��";
		Integer is_approved = 1;
		Integer is_temporary = 1;
		String examination = "��ʱ�Ӱ࣬��������";
		Integer is_sign = 0;
		
		//�����ѡ�����ʱ�Ӱ���Ϣ
		Temporaryovertime selectTmpOvertime = tmpOvertimeService.findTempOvertimeByToid(toId);
		if(selectTmpOvertime != null){
			overtime_start = selectTmpOvertime.getT_overtime_start().toString();
			overtime_end = selectTmpOvertime.getT_overtime_end().toString();
			overtime_reason += selectTmpOvertime.getT_o_reason();
		}
		
		//����
		oaJspMap.put("s_id", sId);
		oaJspMap.put("overtime_start", overtime_start);
		oaJspMap.put("overtime_end", overtime_end);
		oaJspMap.put("overtime_reason", overtime_reason);
		oaJspMap.put("is_approved", is_approved);
		oaJspMap.put("is_temporary", is_temporary);
		oaJspMap.put("examination", examination);
		oaJspMap.put("is_sign", is_sign);
		
		//������������
		if(selectTmpOvertime != null){	//��֤ûˢ��ʱ�������п���ɾ��������
			overtimeService.insertOvertimeApply(oaJspMap);
			
			//���������ܷ���Ϣ
			addMessageToDirectorForTemporaryOvertime(sId);
		}
		
		//����
		//request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "director/selectOvertimeRecord";
		return "redirect:/staff/selectOvertimeRecord";	//�ض���
	}
	
	//�鿴�Լ������мӰ��¼
	@RequestMapping("selectOvertimeRecord")
	public String selectOvertimeRecord(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//���ò�ѯ����
		List<Overtimeapplication> myOvertimeApplyList = overtimeService.selectAllOvertimeApplyBySid(sId);
		
		//����
		request.setAttribute("s_id", sId);
		request.setAttribute("myOvertimeApplyList", myOvertimeApplyList);
		
		//��ת
		return "staff/selectOvertimeRecord";
	}

	//�����������ת�����������룩�Ӱ��¼��ҳ��
	@RequestMapping("/insertOvertimeRecord")
	public String insertOvertimeRecord(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = null;
		if(request.getParameter("overtime_start") != null){
			overtime_start = request.getParameter("overtime_start");
		}
		
		
		//����
		request.setAttribute("s_id", sId);
		request.setAttribute("overtime_start", overtime_start);
		
		return "staff/insertOvertimeRecord";
	}
	
	//��д������Ӱ��¼����Ϣ�󣬱��ύ������������뵽���ݿ�
	@RequestMapping("/insertOvertimeDB")
	public String insertOvertimeDB(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		String overtime_start = "2019-06-01 17:00:00";
		if(request.getParameter("overtime_start") != null){
			overtime_start = request.getParameter("overtime_start");
		}
		String overtime_end = "2019-06-01 19:00:00";
		if(request.getParameter("overtime_end") != null){
			overtime_end = request.getParameter("overtime_end");
		}
		String overtime_reason = "��";
		if(request.getParameter("overtime_reason") != null){
			overtime_reason = request.getParameter("overtime_reason");
		}
		Integer is_approved = 0;
		Integer is_temporary = 0;
		String examination = "��";
		Integer is_sign = 0;
		
		//����������service����
		HashMap<String, Object> insertMap = new HashMap<String, Object>();
		insertMap.put("s_id", sId);
		insertMap.put("overtime_start", overtime_start);
		insertMap.put("overtime_end", overtime_end);
		insertMap.put("overtime_reason", overtime_reason);
		insertMap.put("is_approved", is_approved);
		insertMap.put("is_temporary", is_temporary);
		insertMap.put("examination", examination);
		insertMap.put("is_sign", is_sign);
		
		//����
		overtimeService.insertOvertimeApply(insertMap);
		
		//����Ϣ
		addMessageToDirectorForOvertime(sId);
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//�ض��򣬻ص��鿴���мӰ��������
		return "redirect:/staff/selectOvertimeRecord";
	}
	
	//������ҳ�ķ���
	@RequestMapping("/toHomepage")
	public String toHomepage(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//����
		request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "staff/homepage";
		return "redirect:/staff/selectTodayWorkHomepage";	//����ҳǰ���Ȼ�õ�����Դ򿨡�ǩ�˵Ĺ�����κͼӰ�
	}
	
	//��õ���Ҫ����ҳ����ʾ�Ŀɴ򿨵Ĺ�����λ�Ӱ࣬��ת����ҳ
	@RequestMapping("/selectTodayWorkHomepage")
	public String selectTodayWorkHomepage(HttpServletRequest request) throws ServletException, IOException{
		try {
			//��ò���
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			
			int is_remind = 0;	//�Ƿ�����Ա������Ӱ�
			
			//��ý��������
			Date todayDate = new Date();
			SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now_date = sdf_overtime.format(todayDate);	//��ǰʱ��
			String next_date = getNextDate(todayDate);	//��һ�����������
			String begin_date = getBeginDate(todayDate);	//��ǰ���ڵĿ�ʼʱ��
			
			//��������б�ֻ��һ����
			HashMap<String, Object> shiftWorkMap = new HashMap<String, Object>();
			shiftWorkMap.put("s_id", sId);
			shiftWorkMap.put("date", now_date);
			//����sId�ͽ�������ڲ��ҽ���Ĺ������
			ShiftWork shiftWorkToday = shiftWorkService.getAttenStatus(shiftWorkMap);
			if(shiftWorkToday != null){
				//����ȷʵ�а��
				System.out.println("�����а�Σ�����");
				System.out.println(shiftWorkToday.getWorking_start().toString()+"----"+shiftWorkToday.getWorking_end());
			}
					
			//����
			request.setAttribute("shiftWorkToday", shiftWorkToday);
			
			//�Ӱ��б�
			HashMap<String, Object> overtimeMap = new HashMap<String, Object>();
			overtimeMap.put("s_id", sId);
			overtimeMap.put("now_date", now_date);
			overtimeMap.put("next_date", next_date);
			overtimeMap.put("begin_date", begin_date);
			//��ÿ���ǩ������ʱ�Ӱ��б�
			List<Overtimeapplication> overtimeSignInList = overtimeService.selectSignInOvertimeApply(overtimeMap);
			//��ÿ���ǩ�˵���ʱ�Ӱ��б�
			List<Overtimeapplication> overtimeSignOffList = overtimeService.selectSignOffOvertimeApply(overtimeMap);
			
			//�����������ͨ���ļӰ��б�
			HashMap<String, Object> selectTodayOvertimeMap = new HashMap<String, Object>();
			selectTodayOvertimeMap.put("s_id", sId);
			selectTodayOvertimeMap.put("date", now_date);
			List<Overtimeapplication> overtimeApplyList = overtimeService.selectTodayOvertimeApply(selectTodayOvertimeMap);
			
			//����
			request.setAttribute("overtimeApplyList", overtimeApplyList);
			request.setAttribute("overtimeSignInList", overtimeSignInList);
			request.setAttribute("overtimeSignOffList", overtimeSignOffList);
			request.setAttribute("s_id", sId);
			
			
			//�Ƚϵ�ǰʱ���Ƿ������°�ʱ��30����
			if(shiftWorkToday != null){//����Ҫ���°࿨�Ĺ������
				if(shiftWorkToday.getAttendence_status() == 1){
					Calendar after_time = Calendar.getInstance();
					//before_time.setTime(todayDate);
					after_time.setTime(shiftWorkToday.getWorking_end());	//����°��ʱ��֮���1Сʱ��ʱ��
					after_time.add(Calendar.HOUR, 1);	//���1Сʱ���ʱ��
					String after_time_str = sdf_overtime.format(after_time.getTime());
					Date after_time_date = sdf_overtime.parse(after_time_str);	//��ʽ�����date
					Date todayDateSdf = sdf_overtime.parse(now_date);	//��ʽ���������ʱ��
					
					//�Ƚ�ʱ���Ⱥ�
					if( after_time_date.before(todayDateSdf) ){
						//˵�������Ѿ������°�ʱ��1Сʱ�ˣ�������Ա������Ӱ�
						//���жϵ�ǰ�Ƿ��Ѿ�������Ӱ���
						HashMap<String, Object> selectNowMap = new HashMap<String, Object>();
						selectNowMap.put("s_id", sId);
						selectNowMap.put("now_date", now_date);
						List<Overtimeapplication> searchOvertimeApplyList = overtimeService.selectOvertimeApplyByNowDate(selectNowMap);
						if( searchOvertimeApplyList.size() > 0 ){
							//˵����ǰ�Ѿ�������Ӱ��ˣ�������������
							is_remind = 0;
						}else{
							//û���ҵ��Ӱ��¼��˵����û�������������
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
	
	
	//�򿨣����������������������������������������������������������������ϰ࣡������������������������������������������������������
	//��ʱ�Ӱ�򿨣����ϰ�Ŀ�
	@RequestMapping("/signInOvertime")
	public String signInOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//��ò���
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer oa_id = 0;
			if(request.getParameter("oa_id") != null){
				oa_id = Integer.parseInt(request.getParameter("oa_id"));
			}
			
			boolean check_flag = false;	//����ʶ��ȶԽ��
			
			//��������ͷ����������ʶ�����ʶ��4��
			//�������߳�
			//��������ͷ
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//����
			String basePath = request.getServletContext().getRealPath("/");
			//System.out.println("controller���imageDB·����"+basePath);
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//��δ�ȶԳɹ��������ȶ�
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//����camera�߳���
					break;
				}
				try {
					Thread.sleep(100);	//100�����ж�һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//�ر��߳�
			
			
			//����
			request.setAttribute("s_id", sId);
			if(check_flag){	//�ȶԳɹ��ˣ����п��ڣ�������ҳ
				System.out.println("����ʶ��ɹ���");
				
				//1���޸�������¼��ǩ��״̬
				Overtimeapplication signInOvertime = overtimeService.findOvertimeApplyByOaid(oa_id);
				HashMap<String, Object> updateOvertimeMap = new HashMap<String, Object>();
				updateOvertimeMap.put("oa_id", oa_id);
				updateOvertimeMap.put("is_sign", 1);	//���˿�ʼ�Ŀ�
				//�����޸ķ���
				overtimeService.updateOvertimeApplyByOaid(updateOvertimeMap);
				
				
				//��ý��������
				Date todayDate = new Date();
				SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_overtime.format(todayDate);	//��ǰʱ��
				Date todayDateSdf = sdf_overtime.parse(now_date);	//��ʽ���������ʱ��
				
				//2�����һ���µĿ��ڼ�¼����ӿ�ʼʱ�䣬�ǼӰ��ʶ����¼�����Ӱ��oa_id��
				HashMap<String, Object> insertAttendanceMap = new HashMap<String, Object>();
				insertAttendanceMap.put("s_id", sId);
				Staff myself = staffService.findStaffById(sId);
				insertAttendanceMap.put("dep_id", myself.getDep_id());
				//�жϴ�ʱ���Ƿ����ڿ�ʼʱ��
				Date overtime_start_date = signInOvertime.getOvertime_start();
				
				//�Ƚ�ʱ���Ⱥ�
				if( todayDateSdf.before(overtime_start_date) ){
					//���ڵ�ʱ�����ڼӰ࿪ʼʱ�䣬����ǩ��
					insertAttendanceMap.put("attendance_status", 1);
					
				}else{
					//���ڵ�ʱ�����ڼӰ࿪ʼʱ�䣬�ٵ���
					insertAttendanceMap.put("attendance_status", 2);
				}
				insertAttendanceMap.put("clock_in", now_date);
				insertAttendanceMap.put("is_overtime", 1);		//�ǼӰ�
				insertAttendanceMap.put("record_id", signInOvertime.getOa_id());
				
				//������������
				attendanceService.insertNewAtt(insertAttendanceMap);
				
			}else{	//�ȶ�ʧ�ܣ�������ҳ
				System.out.println("����ʶ��ʧ�ܣ�");
			}
			
			//���Σ�����ҳ
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/staff/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
		
	//����������δ򿨣����ϰ�Ŀ�
	@RequestMapping("/signInShiftWork")
	public String signInShiftWork(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		try {
			//��ò���
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer sw_id = 0;
			if(request.getParameter("sw_id") != null){
				sw_id = Integer.parseInt(request.getParameter("sw_id"));
			}
			
			boolean check_flag = false;	//����ʶ��ȶԽ��
			
			//��������ͷ����������ʶ�����ʶ��4��
			//�������߳�
			//��������ͷ
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//����
			String basePath = request.getServletContext().getRealPath("/");
			//System.out.println("controller���imageDB·����"+basePath);
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//��δ�ȶԳɹ��������ȶ�
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//����camera�߳���
					break;
				}
				try {
					Thread.sleep(100);	//100�����ж�һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			threadCamera.stop();	//�ر��߳�
			
			
			//����
			request.setAttribute("s_id", sId);
			if(check_flag){	//�ȶԳɹ��ˣ����п��ڣ�������ҳ
				System.out.println("����ʶ��ɹ���");
				
				//1���޸�������¼��ǩ��״̬
				ShiftWork signInShiftWork = shiftWorkService.findShiftWorkByswid(sw_id);
				HashMap<String, Object> updateShiftWorkMap = new HashMap<String, Object>();
				updateShiftWorkMap.put("sw_id", sw_id);
				updateShiftWorkMap.put("attendence_status", 1);	//���˿�ʼ�Ŀ�
				//�����޸ķ���
				shiftWorkService.updateShiftWork(updateShiftWorkMap);
				
				//��ý��������
				Date todayDate = new Date();
				SimpleDateFormat sdf_shiftWork = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_shiftWork.format(todayDate);	//��ǰʱ��
				Date todayDateSdf = sdf_shiftWork.parse(now_date);	//��ʽ���������ʱ��
							
				//2�����һ���µĿ��ڼ�¼����ӿ�ʼʱ�䣬���ǼӰ��ʶ����¼����������ε�sw_id��
				HashMap<String, Object> insertAttendanceMap = new HashMap<String, Object>();
				insertAttendanceMap.put("s_id", sId);
				Staff myself = staffService.findStaffById(sId);
				insertAttendanceMap.put("dep_id", myself.getDep_id());
				//�жϴ�ʱ���Ƿ����ڿ�ʼʱ��
				Date overtime_start_date = signInShiftWork.getWorking_start();
				
				//�Ƚ�ʱ���Ⱥ�
				if( todayDateSdf.before(overtime_start_date) ){
					//���ڵ�ʱ�����ڼӰ࿪ʼʱ�䣬����ǩ��
					insertAttendanceMap.put("attendance_status", 1);
					
				}else{
					//���ڵ�ʱ�����ڼӰ࿪ʼʱ�䣬�ٵ���
					insertAttendanceMap.put("attendance_status", 2);
				}
				insertAttendanceMap.put("clock_in", now_date);
				insertAttendanceMap.put("is_overtime", 0);	//���ǼӰ�
				insertAttendanceMap.put("record_id", signInShiftWork.getSw_id());
				
				//������������
				attendanceService.insertNewAtt(insertAttendanceMap);
				
			}else{	//�ȶ�ʧ�ܣ�������ҳ
				System.out.println("����ʶ��ʧ�ܣ�");
			}
			
			//���Σ�����ҳ
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/staff/toHomepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	//�򿨣����������������������������������������������������������������°࣡������������������������������������������������������
	//��ʱ�Ӱ�򿨣����ϰ�Ŀ�
	@RequestMapping("/signOffOvertime")
	public String signOffOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//��ò���
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer oa_id = 0;
			if(request.getParameter("oa_id") != null){
				oa_id = Integer.parseInt(request.getParameter("oa_id"));
			}
			
			boolean check_flag = false;	//����ʶ��ȶԽ��
			
			//��������ͷ����������ʶ�����ʶ��4��
			//�������߳�
			//��������ͷ
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//����
			String basePath = request.getServletContext().getRealPath("/");
			//System.out.println("controller���imageDB·����"+basePath);
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//��δ�ȶԳɹ��������ȶ�
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//����camera�߳���
					break;
				}
				try {
					Thread.sleep(100);	//100�����ж�һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//�ر��߳�
			
			
			//����
			request.setAttribute("s_id", sId);
			if(check_flag){	//�ȶԳɹ��ˣ����п��ڣ�������ҳ
				System.out.println("����ʶ��ɹ���");
				
				//1���޸�������¼��ǩ��״̬
				Overtimeapplication signOffOvertime = overtimeService.findOvertimeApplyByOaid(oa_id);
				HashMap<String, Object> updateOvertimeMap = new HashMap<String, Object>();
				updateOvertimeMap.put("oa_id", oa_id);
				updateOvertimeMap.put("is_sign", 2);	//���˽����Ŀ���
				//�����޸ķ���
				overtimeService.updateOvertimeApplyByOaid(updateOvertimeMap);
				
				
				//��ý��������
				Date todayDate = new Date();
				SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_overtime.format(todayDate);	//��ǰʱ��
				Date todayDateSdf = sdf_overtime.parse(now_date);	//��ʽ���������ʱ��
				
				//2��Ѱ��������Ӧ�Ŀ��ڼ�¼�����ݼ�¼id���޸Ľ���ʱ�䡢����״̬
				HashMap<String, Object> searchAttendanceMap = new HashMap<String, Object>();
				searchAttendanceMap.put("is_overtime", 1);
				searchAttendanceMap.put("record_id", signOffOvertime.getOa_id());
				Attendance selectAttendance = attendanceService.findAttendanceByRecordId(searchAttendanceMap);
				//��ʼ�޸��������ڼ�¼�Ľ���ʱ�䡢����״̬
				HashMap<String, Object> updateAttendanceMap = new HashMap<String, Object>();
				updateAttendanceMap.put("at_id", selectAttendance.getAt_id());	//���ݿ��ڼ�¼id����������¼
				updateAttendanceMap.put("clock_off", now_date);	//��ǩ�˿��ĵ�ǰʱ��
				
				//�жϴ�ʱ���Ƿ����ڽ���ʱ��
				Date overtime_end_date = signOffOvertime.getOvertime_end();
				
				//�Ƚ�ʱ���Ⱥ�
				if( todayDateSdf.before(overtime_end_date) ){
					//���ڵ�ʱ�����ڼӰ����ʱ�䣬˵��������
					if(selectAttendance.getAttendance_status() == 1){	//����ǩ����˵��ֻ������
						updateAttendanceMap.put("attendance_status", 3);
					}else{	//˵����2���ǳٵ��ģ�������������
						updateAttendanceMap.put("attendance_status", 4);
					}
				}else{
					//���ڵ�ʱ�����ڼӰ����ʱ�䣬˵������ǩ����
					if(selectAttendance.getAttendance_status() == 1){	//����ǩ����˵��ֻ������
						updateAttendanceMap.put("attendance_status", 1);
					}else{	//ǩ��ʱ�ٵ��ˣ�������������
						updateAttendanceMap.put("attendance_status", 2);
					}
				}
				
				//�����޸ķ���
				attendanceService.updateAttendanceByAtid(updateAttendanceMap);				
			}else{	//�ȶ�ʧ�ܣ�������ҳ
				System.out.println("����ʶ��ʧ�ܣ�");
			}
			
			//���Σ�����ҳ
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/staff/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}

	//��ʱ�Ӱ�򿨣����ϰ�Ŀ�
	@RequestMapping("/signOffShiftWork")
	public String signOffShiftWork(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			//��ò���
			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
			Integer sw_id = 0;
			if(request.getParameter("sw_id") != null){
				sw_id = Integer.parseInt(request.getParameter("sw_id"));
			}
			
			boolean check_flag = false;	//����ʶ��ȶԽ��
			
			//��������ͷ����������ʶ�����ʶ��4��
			//�������߳�
			//��������ͷ
			ShowCamera showCamera = new ShowCamera();
			showCamera.sId_camera = sId;	//����
			String basePath = request.getServletContext().getRealPath("/");
			//System.out.println("controller���imageDB·����"+basePath);
			basePath += "imageDB\\";
			showCamera.basePath = basePath;
			
			Thread threadCamera = new Thread(showCamera);
			threadCamera.start();
			
			while(!check_flag){	//��δ�ȶԳɹ��������ȶ�
				check_flag = showCamera.check_flag_camera;
				if(showCamera.is_over_camera){
					//����camera�߳���
					break;
				}
				try {
					Thread.sleep(100);	//100�����ж�һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				check_flag = showCamera.check_flag_camera;
			}
			//threadCamera.stop();	//�ر��߳�
			
			
			//����
			request.setAttribute("s_id", sId);
			if(check_flag){	//�ȶԳɹ��ˣ����п��ڣ�������ҳ
				System.out.println("����ʶ��ɹ���");
				
				//1���޸�������¼��ǩ��״̬
				ShiftWork signOffShiftWork = shiftWorkService.findShiftWorkByswid(sw_id);
				HashMap<String, Object> updateShiftWorkMap = new HashMap<String, Object>();
				updateShiftWorkMap.put("sw_id", sw_id);
				updateShiftWorkMap.put("attendence_status", 2);	//���˽����Ŀ���
				//�����޸ķ���
				shiftWorkService.updateShiftWork(updateShiftWorkMap);				
				
				//��ý��������
				Date todayDate = new Date();
				SimpleDateFormat sdf_shiftWork = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now_date = sdf_shiftWork.format(todayDate);	//��ǰʱ��
				Date todayDateSdf = sdf_shiftWork.parse(now_date);	//��ʽ���������ʱ��
				
				//2��Ѱ��������Ӧ�Ŀ��ڼ�¼�����ݼ�¼id���޸Ľ���ʱ�䡢����״̬
				HashMap<String, Object> searchAttendanceMap = new HashMap<String, Object>();
				searchAttendanceMap.put("is_overtime", 0);	//���ǼӰ�
				searchAttendanceMap.put("record_id", signOffShiftWork.getSw_id());
				Attendance selectAttendance = attendanceService.findAttendanceByRecordId(searchAttendanceMap);
				//��ʼ�޸��������ڼ�¼�Ľ���ʱ�䡢����״̬
				HashMap<String, Object> updateAttendanceMap = new HashMap<String, Object>();
				updateAttendanceMap.put("at_id", selectAttendance.getAt_id());	//���ݿ��ڼ�¼id����������¼
				updateAttendanceMap.put("clock_off", now_date);	//��ǩ�˿��ĵ�ǰʱ��
				
				//�жϴ�ʱ���Ƿ����ڽ���ʱ��
				Date shiftWork_end_date = signOffShiftWork.getWorking_end();				
				//�Ƚ�ʱ���Ⱥ�
				if( todayDateSdf.before(shiftWork_end_date) ){
					//���ڵ�ʱ�����ڼӰ����ʱ�䣬˵��������
					if(selectAttendance.getAttendance_status() == 1){	//����ǩ����˵��ֻ������
						updateAttendanceMap.put("attendance_status", 3);
					}else{	//˵����2���ǳٵ��ģ�������������
						updateAttendanceMap.put("attendance_status", 4);
					}
				}else{
					//���ڵ�ʱ�����ڼӰ����ʱ�䣬˵������ǩ����
					if(selectAttendance.getAttendance_status() == 1){	//����ǩ����˵��ֻ������
						updateAttendanceMap.put("attendance_status", 1);
					}else{	//ǩ��ʱ�ٵ��ˣ�������������
						updateAttendanceMap.put("attendance_status", 2);
					}
				}
				
				//�����޸ķ���
				attendanceService.updateAttendanceByAtid(updateAttendanceMap);				
			}else{	//�ȶ�ʧ�ܣ�������ҳ
				System.out.println("����ʶ��ʧ�ܣ�");
			}
			
			//���Σ�����ҳ
			HttpSession session = request.getSession();
			session.setAttribute("s_id", sId);
			
			return "redirect:/staff/toHomepage";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	
	
	
	//�������������ԣ�ƴ�ӳɹ淶��ʽ��������ʽ
	public static String getDateStrByYearMonthDay(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		//�����Ե���ΪʲôҪ-1����������������������������������������������������
		cal.set(year, month-1, day);	//��������
		//���и�ʽ��
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		String format_date = sdf_date.format(cal.getTime());
		return format_date;
	}
	
	//����鿴��������������������Ա���Ĺ����������ת����ʾ���������ҳ��
	@RequestMapping("/selectOneStaffWorkConByMonthByDep")
	public String selectOneStaffWorkConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
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
		
		//Ĭ�ϵ�ǰ��
		Integer year = cal.get(Calendar.YEAR);
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		}
		//Ĭ�ϵ�ǰ��
		Integer month = cal.get(Calendar.MONTH)+1;
		if(request.getParameter("month") != null){
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		cal.set(year, month, 0);
		//����ǶԵģ�����
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//�����һ�������һ���ж�����
		
		int out_i = 1;
		int inner_i = 1;
		int index = 0;
		
		//�õ����µ�һ����¸��µ�һ��
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar month_begin_cal = Calendar.getInstance();
		//month_begin_cal.set(year, month, 1);	//��䰴��˵���ǶԵİ�
		month_begin_cal.set(year, month-1, 1);
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//���µ�һ���String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//�·�+1		//����˵Ӧ������仰��
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//�¸��µ�һ���String
		
		System.out.println("���µ�һ�죺"+begin_date_str+"���¸��µ�һ�죺"+end_date_str);
		
		//��jsp�жϵĿ�ֵ
		String emptyStr = "2019-01-01 00:00:00";
		Timestamp empty_date = Timestamp.valueOf(emptyStr);
		
		//��Ա������
		Staff select_staff = staffService.findStaffById(sId);
		//��ò���id
		Integer dep_id = staffService.findDepidBySid(sId);	//��ø�Ա����Ӧ��dep_id
		
		//��������¸�Ա���Ĺ������List
		//����ѭ�������ѭ������µ�ÿһ��
		//����Ҫ�õ��Ĺ������list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//�ò��Ÿ�������Ա�������й������
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", sId);
		selectMap.put("dep_id", dep_id);
		selectMap.put("working_start_start", begin_date_str);
		selectMap.put("working_start_end", end_date_str);
		//����
		List<ShiftWork> shiftWorkList = shiftWorkService.selectOneStaffWorkConByMonthByDep(selectMap);

				
		for(out_i=1; out_i <= day; out_i++){	//��㰴�ո���һ������������ѭ��
			WorkCondition workCondition = new WorkCondition();	//���Ҫ��ӽ�ȥ�Ĺ����������
			//���ж���һ�����Ա����û�й������
			String day_begin_str = getDateStrByYearMonthDay(year,month,out_i);	//��һ��0��
			String day_end_str = getDateStrByYearMonthDay(year,month,out_i+1);	//��һ��0��
			HashMap<String, Object> selectOneShiftMap = new HashMap<String, Object>();
			selectOneShiftMap.put("s_id", sId);
			selectOneShiftMap.put("dep_id", dep_id);
			selectOneShiftMap.put("working_start_start", day_begin_str);
			selectOneShiftMap.put("working_start_end", day_end_str);
			List<ShiftWork> todayWork = shiftWorkService.selectOneStaffWorkConByMonthByDep(selectOneShiftMap);
			if(todayWork.size() > 0){	//��Ա�������й�����ΰ���
				//�����ڱ���û������������εļ�¼
				HashMap<String, Object> selectAttendanceMap = new HashMap<String, Object>();
				selectAttendanceMap.put("is_overtime", 0);	//���ǼӰ�
				selectAttendanceMap.put("record_id", todayWork.get(0).getSw_id());	//��ü�¼id
				//��õ���Ŀ��ڼ�¼
				Attendance todayAttendanceShift = attendanceService.findAttendanceByRecordId(selectAttendanceMap);
				if(todayAttendanceShift != null){	//��Ա����һ���п��ڼ�¼����¼�������
					int attendance_status = todayAttendanceShift.getAttendance_status();
					if(attendance_status == 1){
						workCondition.setAttendence_status("����");
					}else if(attendance_status == 2){
						workCondition.setAttendence_status("�ٵ�");
					}else if(attendance_status == 3){
						workCondition.setAttendence_status("����");
					}else{
						workCondition.setAttendence_status("�ȳٵ�������");
					}
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
				}else{	//��Ա����һ��û�п��ڼ�¼
					//��¼Ϊ����
					workCondition.setAttendence_status("����");	//6�������
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}else{	//��Ա������û�й�����ΰ���
				//����ٱ���û����
				HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
				//��ѯ�ĵ�ǰ����
				String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
				
				selectAskforleaveMap.put("s_id", sId);
				selectAskforleaveMap.put("now_date", searchAskDate);
				//���ò�ѯ����
				List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
				if(todayAskforleaveList.size() > 0){	//����ټ�¼����Ϊ���״̬
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("���");//5�������״̬
					workCondition.setS_name(select_staff.getS_name());
				}else{	//Ҳû����ټ�¼��û�а�ΰ��ţ�Ҳû����٣�˵���ż٣�����ĩ��������
					workCondition.setAttendence_status("��");
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(sId);
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}
			//һ��ѭ������������һ�����������ӵ�List��
			workConditionList.add(workCondition);
		}
		
		//����
		request.setAttribute("s_id", sId);
		request.setAttribute("workConditionList", workConditionList);
		request.setAttribute("select_staff", select_staff);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("dep_id", dep_id);
		
		return "staff/selectOneStaffWorkConByMonthByDep";
	}
	
	
	//����鿴��������������������Ա���Ĺ����������ת����ʾ���������ҳ��
	@RequestMapping("/selectOneOvertimeConByMonthByDep")
	public String selectOneOvertimeConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
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
		
		//Ĭ�ϵ�ǰ��
		Integer year = cal.get(Calendar.YEAR);
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		}
		//Ĭ�ϵ�ǰ��
		Integer month = cal.get(Calendar.MONTH)+1;
		if(request.getParameter("month") != null){
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		cal.set(year, month, 0);
		//����ǶԵģ�����
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//�����һ�������һ���ж�����
		
		//�õ����µ�һ����¸��µ�һ��
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar month_begin_cal = Calendar.getInstance();
		//month_begin_cal.set(year, month, 1);	//��䰴��˵���ǶԵİ�
		month_begin_cal.set(year, month-1, 1);
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//���µ�һ���String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//�·�+1		//����˵Ӧ������仰��
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//�¸��µ�һ���String
		
		//���廯
		String begin_date_str_detail = begin_date_str + " 00:00:00";
		String end_date_str_detail = end_date_str + " 00:00:00";
		
		System.out.println("�鿴�Ӱ�����ķ��������µ�һ�죺"+begin_date_str_detail+"���¸��µ�һ�죺"+end_date_str_detail);
		
		//��jsp�жϵĿ�ֵ
		String emptyStr = "2019-01-01 00:00:00";
		Timestamp empty_date = Timestamp.valueOf(emptyStr);
		
		//��Ա������
		Staff select_staff = staffService.findStaffById(sId);
		//��ò���id
		Integer dep_id = staffService.findDepidBySid(sId);	//��ø�Ա����Ӧ��dep_id
		
		//�õ����µļӰ�List
		HashMap<String, Object> selectOvertimeMap = new HashMap<String, Object>();
		selectOvertimeMap.put("s_id", sId);
		selectOvertimeMap.put("begin_date", begin_date_str_detail);
		selectOvertimeMap.put("end_date", end_date_str_detail);
		List<Overtimeapplication> overtimeList = overtimeService.selectOvertimeByMonth(selectOvertimeMap);
		
		System.out.println("�����Ӱ��б�������ǣ�"+overtimeList.size());
		
		//�õ����������п��ڼ�¼����ʱ�ԼӰ�
		List<Overtimeapplication> tmpMonthList = overtimeService.selectTmpOvertimeByMonth(selectOvertimeMap);
		
		//��ʼ���ݿ��ڱ��еļ�¼������ʵ����ʱ�ԼӰ��¼
		List<Overtimeapplication> tmpOvertimeList = new ArrayList<Overtimeapplication>();
		for (Overtimeapplication selectTmpOvertime : tmpMonthList) {
			//Ҫ����list����ʱ�Ӱ��¼
			Overtimeapplication tmpOvertime = new Overtimeapplication();
			//tmpOvertime.setExamination(selectTmpOvertime.getExamination());
			tmpOvertime.setIs_approved(1);
			tmpOvertime.setIs_sign(2);
			tmpOvertime.setIs_temporary(1);
			tmpOvertime.setOa_id(selectTmpOvertime.getOa_id());
			
			//���ҿ��ڼ�¼
			HashMap<String, Object> selectAttMap = new HashMap<String, Object>();
			selectAttMap.put("is_overtime", 1);
			selectAttMap.put("record_id", selectTmpOvertime.getOa_id());
			Attendance selectAttendance = attendanceService.findAttendanceByRecordId(selectAttMap);
					
			tmpOvertime.setOvertime_start(selectAttendance.getClock_in());
			tmpOvertime.setOvertime_end(selectAttendance.getClock_off());
			
			//�Ž�List
			tmpOvertimeList.add(tmpOvertime);
		}
		
		System.out.println("��ʱ�ԼӰ��������ǣ�"+tmpOvertimeList.size());
		
		//����
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