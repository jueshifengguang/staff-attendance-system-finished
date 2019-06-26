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
 * �������ܿ�����
 * @author dzz
 * 2019��6��6�� ����9:25:20
 * AttendanceSystem
 */
@Controller
@RequestMapping("/director")
public class DirectorController {

	//����service
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
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
	
	//������������������������Ա����Ϣ���֡���������������������������
	
	@RequestMapping("/directorSelectStaffInfo")
	public String directorSelectStaffInfo(HttpServletRequest request)throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");}
		//���ò�ѯ����
		Staff staff = staffService.findStaffById(sId);
		
		//System.out.println(sId);
		//����
		request.setAttribute("staff", staff);
		request.setAttribute("s_id", sId);
		return "director/directorSelectStaffInfo";
		
		
	}
	@RequestMapping("/directorUpdateStaffInfoJump")
	public String directorUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
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
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		request.setAttribute("s_id", sId);
		
		return "director/directorUpdateStaffInfo";
	}
	
	//Ա�������Լ����˻���Ϣ	
	@RequestMapping("/directorUpdateStaffInfo")
	public String updateStaffInfo(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
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
		return "redirect:/director/directorSelectStaffInfo";
	}	
	
	//��������������������������Ա����Ϣ���ֽ�������������������������������
	
	
	//��������������������������������������������������������������������������������������������������ٲ��֡���������������������������������������������������
	//��ת��������ٵ�ҳ��
	@RequestMapping("/updateAskforleaveJump")
	public String test2(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
				
		//�����ѡ��������Ϣ
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		request.setAttribute("s_id", s_id);
		
		return "director/updateAskforleave";
	}
	//��������������룬�ύ�޸ĵ����ݿ�
	@RequestMapping("/updateAskforleave")
	public String test(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		Integer apply_id = Integer.parseInt(request.getParameter("apply_id"));
		Integer is_approved =Integer.parseInt(request.getParameter("is_approved"));
		String approved_reason = request.getParameter("approved_reason");
		
		System.out.println("�ӵ���sid��"+s_id);
		System.out.println("�ӵ���apply_id��"+apply_id);
		
		if(is_approved == 1){	//����ͨ���������ܷ���Ϣ����������ת�������
			String a = addNewMessageAfterApproved(s_id);
		}
		Staff applyStaff = staffService.findStaffById(apply_id);
		Integer staff_Id = applyStaff.getS_id();
		if(is_approved != 0){	//�������ˣ���Ա������Ϣ
			String b = addNewMessageAfterApprovedForStaff(staff_Id);
		}		
		
		//����
		oaJspMap.put("afl_id", afl_id);
		oaJspMap.put("is_approved", is_approved);
		oaJspMap.put("approved_reason", approved_reason);
				
		//������������
		askforleaveService.updateAskApplyforByAflid(oaJspMap);
				
		//����
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

		//��ò���
	    Integer s_id = Integer.parseInt(request.getParameter("s_id"));

		//�����ѡ��������Ϣ
		request.setAttribute("s_id", s_id);
		
		return "director/addNewAskforleave";
	}

	//�����������
	@RequestMapping("/addNewAskforleave")
	public String addNewAskforleave(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
			
		//���ҳ�洫���Ĳ���
		Integer getSId = Integer.parseInt(request.getParameter("s_id")); 
		Integer getIsApproved = Integer.parseInt(request.getParameter("is_approved"));
		Integer getIsResumed = Integer.parseInt(request.getParameter("is_resumed"));
		String getStartTime = request.getParameter("starting_date");
		String getEndTime = request.getParameter("ending_date");
		String leave_reason = request.getParameter("leave_reason");
		String approved_reason = request.getParameter("approved_reason");
		Integer leave_type = Integer.parseInt(request.getParameter("leave_type"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//�Ѳ����ŵ�Map��
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("s_id", getSId);
		toMap.put("is_approved", getIsApproved);
		toMap.put("is_resumed", getIsResumed);
		toMap.put("starting_date", getStartTime);
		toMap.put("ending_date", getEndTime);
		toMap.put("leave_reason", leave_reason);
		toMap.put("approved_reason", approved_reason);
		toMap.put("leave_type", leave_type);
		//���ݲ���
		request.setAttribute("s_id", getSId);
		//���������ķ���
		askforleaveService.insertNewAskforApply(toMap);
		//���÷���������Ϣ
		String a = addNewMessageBeforeApproved(getSId);
		
		HttpSession session = request.getSession();
		session.setAttribute("s_id", getSId);
		
		//return "director/homepage";
		return "redirect:/director/listAllAskforleaveBySid";
    }
	
	//���ܲ鿴���������е�������루Ĭ����ʾδ�����ģ�
	@RequestMapping("/selectNoncheckedAskforApplyByDep")
	public String selectNoncheckedAskforApplyByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		//��øò������е�δ�����Ӱ�����
		//�Ȼ��������ܵ�dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//�ٸ��ݲ���id���Ҹò�������δ�����ļӰ�����
		List<Askforleave> AskforleaveList = askforleaveService.selectNoncheckedAskforApplyByDep(depId);
		//���θ�ҳ��
		request.setAttribute("s_id", sId);
		request.setAttribute("AskforleaveList", AskforleaveList);
		return "director/listAllAskforleave";
	}
	//�鿴�����������
	@RequestMapping("/selectcheckedAskforApplyByDep")
	public String selectcheckedAskforApplyByDep(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		//��øò������е�δ�����Ӱ�����
		//�Ȼ��������ܵ�dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//�ٸ��ݲ���id���Ҹò�������δ�����ļӰ�����
		List<Askforleave> AskforleaveList = askforleaveService.selectNoncheckedAskforApplyByDep(depId);
		//System.out.println(AskforleaveList.get(0).getLeave_reason());
		//���θ�ҳ��
		request.setAttribute("s_id", sId);
		request.setAttribute("AskforleaveList", AskforleaveList);
		//
		return "director/listAllAskforleaveChanged";
	}	

	//�鿴�Լ���δ���ٵ�����б�
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
		
		//����
		request.setAttribute("s_id", s_id);
		request.setAttribute("aflList", aflList);
		
		//��ת���Լ���δ�����б�
		return "director/listAllAskforleaveBySid";
	}
	
	//�鿴�Լ������ٵ�����б�
	@RequestMapping("/listAllAskforleaveBySidChanged")
	public String listAllAskforleaveBySidChanged(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
	
		List<Askforleave> aflList = askforleaveService.selectAllAskforApplyBySid(s_id);
		
		//����
		request.setAttribute("aflList", aflList);
		request.setAttribute("s_id", s_id);
		
		//��ת���Լ����������б�
		return "director/listAllAskforleaveBySidChanged";
	}

	//��ת���޸�ҳ�棿��������������ת��
	@RequestMapping("/listAllAskforleaveBySidJump")
	public String listAllAskforleaveBySidJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		//String is_approved =request.getParameter("is_approved");
				
		//�����ѡ��������Ϣ
		Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
		
		request.setAttribute("updateAfl", updateAfl);
		//����
		request.setAttribute("s_id", s_id);
		HttpSession session = request.getSession();			
		session.setAttribute("s_id", s_id);
		session.setAttribute("updateAfl", updateAfl);
		System.out.println("1111");		
		//return "director/selectOvertimeRecord";
		//return "redirect:/director/selectOvertimeRecord";	//�ض���
		//��ת���������޸ģ�ҳ��
		return "director/updateAskforleaveByresumed";
	}
	
	//�Լ�����
	@RequestMapping("/updateAskforleaveByresumed")
	public String updateAskforleaveByresumed(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		HashMap<String, Object> oaJspMap = new HashMap<String, Object>();
		//��ò���
		Integer afl_id = Integer.parseInt(request.getParameter("afl_id"));
		Integer s_id = Integer.parseInt(request.getParameter("s_id"));
		String is_resumed =request.getParameter("is_resumed");
		System.out.println("controller1��sid:"+s_id);
				
		//�����ѡ�����ʱ�Ӱ���Ϣ
		//Askforleave updateAfl = askforleaveService.findAskforApplyByAflid(afl_id);
				
		//System.out.println(afl_id);
		System.out.println("2222");
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
		//return "redirect:/director/selectOvertimeRecord";	//�ض���
		return "redirect:/director/listAllAskforleaveBySid";
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
		return "director/message";
	}
	
	//�鿴�Ѷ���Ϣ
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
		return "director/message";
	}
	//����δ����ϢΪ�Ѷ���Ϣ
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
		
		return "redirect:/director/selectNoReadMessageApply";
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
		return "redirect:/director/selectReadMessageApply";
	}
	
	//����������ٺ��ٸ����ܷ���Ϣ���������޸Ĺ�����ΰ���
	public String addNewMessageAfterApproved(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "������ͨ����"+s_name+"����ټ�¼���뾡����й�������";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//�����Լ���������������ҲҪ���������������������Ϣ����������
	public String addNewMessageBeforeApproved(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"�ύ���µĵ���ٱ��뾡���������";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//��Ա�����ģ���������������Ѿ�����������
	public String addNewMessageAfterApprovedForStaff(Integer s_id){
	  //  Data m_time_n = new Data();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
		//String m_time = formatter.format(DateFormat.getDateInstance().parse(m_time_n));
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "�����������Ѿ��������ˣ���ȥ�鿴�ɣ���";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
	
	//_______________________
	
	//�����Լ������˼Ӱ������ҲҪ���������������������Ϣ����������
	public String addMessageToDirectorForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"�ύ���µĵļӰ����룬�뾡���������";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
	
	//�����Լ���������ʱ�Ӱ��ҲҪ���������������������Ϣ����������Σ�
	public String addMessageToDirectorForTemporaryOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = s_name+"�������µ���ʱ�ԼӰ�";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}

	//��Ա�����ģ��������Ӱ������Ѿ�����������
	public String addMessageToStaffForOvertime(Integer s_id){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    Staff applyStaff = staffService.findStaffById(s_id);
	    String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "��ļӰ������Ѿ��������ˣ���ȥ�鿴�ɣ���";
	    
	    HashMap<String, Object> toMap = new HashMap<String, Object>();
	    
	    toMap.put("s_id", s_id);
	    toMap.put("m_time", m_time);
	    toMap.put("is_read", is_read);
	    toMap.put("m_content", m_content);
	    messageService.insertNewMessage(toMap);		  							
		return null;
	}
		
	
	//��������������������������������������������������������������������������������������������������Ϣ���ֽ�������������������������������������������������������
	
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
		return "director/selectAllTemporaryOvertime";
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
			
			//�����ܷ���Ϣ��������������һ����������������ʱ�ԼӰ�
			addMessageToDirectorForTemporaryOvertime(sId);
		}
		
		//����
		//request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "director/selectOvertimeRecord";
		return "redirect:/director/selectOvertimeRecord";	//�ض���
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
		return "director/selectOvertimeRecord";
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
		
		return "director/insertOvertimeRecord";
	}
	
	@RequestMapping("/insertOvertimeDB")
	public String insertOvertimeDB(HttpServletRequest request) throws ServletException, IOException{
		//��д������Ӱ��¼����Ϣ�󣬱��ύ������������뵽���ݿ�
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
		
		//�����ܣ��Լ�������Ϣ������������һ����Ҫ�����ļӰ�����
		addMessageToDirectorForOvertime(sId);
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//�ض��򣬻ص��鿴���мӰ��������
		return "redirect:/director/selectOvertimeRecord";
	}
	
	
	//���ܲ鿴���������еļӰ����루Ĭ����ʾδ�����ģ�
	@RequestMapping("/selectNoncheckedOvertimeApply")
	public String selectNoncheckedOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
			System.out.println("������sid��ô��"+sId);
		}
		
		//��øò������е�δ�����Ӱ�����
		//�Ȼ��������ܵ�dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//�ٸ��ݲ���id���Ҹò�������δ�����ļӰ�����
		List<Overtimeapplication> noncheckedOvertimeList = overtimeService.selectNoncheckedOvertimeApplyByDep(depId);

		//���θ�ҳ��
		request.setAttribute("s_id", sId);
		request.setAttribute("overtimeApplyList", noncheckedOvertimeList);
		
		return "director/selectDepOvertimeApply";
	}
	
	//�����������ĳ���Ӱ������¼����ת���޸ģ�������ҳ��
	@RequestMapping("/examineOvertimeApplyJump")
	public String examineOvertimeApplyJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		Integer oaId = Integer.parseInt(request.getParameter("oa_id"));
		
		//������������¼
		Overtimeapplication selectOvertimeApply = overtimeService.findOvertimeApplyByOaid(oaId);
		//��������Ա������
		Staff applyStaff = staffService.findStaffById(selectOvertimeApply.getS_id());
		
		//���θ�ҳ��
		request.setAttribute("s_id", sId);
		request.setAttribute("selectOvertimeApply", selectOvertimeApply);
		request.setAttribute("applyStaff", applyStaff);
		
		return "director/examineOvertimeApply";
	}
	
	//������ĳһ���Ӱ�������޸ĵ����ݿ���
	@RequestMapping("/examineOvertimeApply")
	public String examineOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = Integer.parseInt(request.getParameter("s_id"));
		Integer oaId = Integer.parseInt(request.getParameter("oa_id"));
		Integer is_approved = Integer.parseInt(request.getParameter("is_approved"));
		String examination = "��";
		
		HashMap<String, Object> updateMap = new HashMap<String, Object>();
		
		if(request.getParameter("examination") != null){
			examination = request.getParameter("examination");
		}
		
		//�����޸ĵķ���
		updateMap.put("oa_id", oaId);
		updateMap.put("is_approved", is_approved);
		updateMap.put("examination", examination);
		overtimeService.updateOvertimeApplyByOaid(updateMap);
		
		//ͬoaId�����sid
		Overtimeapplication exam_overtime = overtimeService.findOvertimeApplyByOaid(oaId);
		//��������Ա������
		Staff exam_staff = staffService.findStaffById(exam_overtime.getS_id());
		//���Ǹ�Ա������Ϣ
		addMessageToStaffForOvertime(exam_staff.getS_id());

		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//�ض��򣬻ص��鿴���мӰ��������
		return "redirect:/director/selectNoncheckedOvertimeApply";
	}
	
	//���ܲ鿴���������еļӰ����루Ĭ����ʾδ�����ģ�
	@RequestMapping("/selectCheckedOvertimeApply")
	public String selectCheckedOvertimeApply(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
			System.out.println("������sid��ô��"+sId);
		}
		Integer is_examined_flag = 1;
		if(request.getParameter("is_examined_flag") != null){
			is_examined_flag = Integer.parseInt(request.getParameter("is_examined_flag"));
		}
		
		//��øò��������������ļӰ�����
		//�Ȼ��������ܵ�dep_id
		Integer depId = staffService.findDepidBySid(sId);
		
		//�ٸ��ݲ���id���Ҹò��������������ļӰ�����
		List<Overtimeapplication> checkedOvertimeList = overtimeService.selectCheckedOvertimeApplyByDep(depId);

		//���θ�ҳ��
		request.setAttribute("is_examined_flag", is_examined_flag);
		request.setAttribute("s_id", sId);
		request.setAttribute("overtimeApplyList", checkedOvertimeList);
		
		return "director/selectDepOvertimeApply";
	}
	
	//������ҳ�ķ�����Ҫ�Ȼ����ҳ��Ҫ��ʾ�ĵ�����Դ򿨡�ǩ�˵Ĺ�����κͼӰ��б�
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
		
		return "redirect:/director/selectTodayWorkHomepage";	//����ҳǰ���Ȼ�õ�����Դ򿨡�ǩ�˵Ĺ�����κͼӰ�
		//return "director/homepage";
	}
	
	//��õ�����Դ򿨡�ǩ�˵İ�κͼӰ��б�������ҳ
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
			if(shiftWorkToday != null){	//����Ҫ���°࿨�Ĺ������
				if(shiftWorkToday.getAttendence_status() == 1){
					Calendar after_time = Calendar.getInstance();
					//before_time.setTime(todayDate);
					after_time.setTime(shiftWorkToday.getWorking_end());	//����°��ʱ��֮���1Сʱ��ʱ��
					after_time.add(Calendar.HOUR, 1);	//���1Сʱ���ʱ��
					String after_time_str = sdf_overtime.format(after_time.getTime());
					Date after_time_date = sdf_overtime.parse(after_time_str);	//��ʽ�����date
					Date todayDateSdf = sdf_overtime.parse(now_date);	//��ʽ���������ʱ��
					
					//�Ƚ�ʱ���Ⱥ�
					if(shiftWorkToday != null){
						if(shiftWorkToday.getAttendence_status() == 2){
							
						}
					}
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
			
			return "director/homepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	
	
	
	//���д򿨵ķ���
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
			
			return "redirect:/director/toHomepage";
		
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
			
			return "redirect:/director/toHomepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "main/login";
		}
	}
	
	//�򿨣����������������������������������������������������������������°࣡������������������������������������������������������
	//��ʱ�Ӱ�򿨣����ϰ�Ŀ�
	@RequestMapping("/signOffOvertime")
	public String SignOffOvertime(HttpServletRequest request) throws ServletException, IOException{
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
			
			return "redirect:/director/toHomepage";
		
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
			
			return "redirect:/director/toHomepage";
		
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
	
	//����鿴�����Ź�������������������������еĹ����������ת����ʾ���������ҳ��
	@RequestMapping("/selectAllStaffWorkConByMonthByDep")
	public String selectAllStaffWorkConByMonthByDep(HttpServletRequest request) throws ServletException, IOException{
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
		
		
		//��ò���id
		Integer dep_id = staffService.findDepidBySid(sId);	//��ø����ܶ�Ӧ��dep_id
		//����������Ա��List
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		//�������������Ա���Ĺ������List
		//˫��ѭ�������ѭ������µ�ÿһ�죬�ڲ�ѭ������������Ա��
		//����Ҫ�õ��Ĺ������list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//�ò��Ÿ�������Ա�������й������
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("dep_id", dep_id);
		selectMap.put("working_start_start", begin_date_str);
		selectMap.put("working_start_end", end_date_str);
		//����
		List<ShiftWork> shiftWorkList = shiftWorkService.selectAllStaffWorkConByMonthByDep(selectMap);
		
		for(out_i=1; out_i <= day; out_i++){	//��㰴�ո���һ������������ѭ��
			for (Staff staff : staffList) {	//�ڲ���ݸò�������Ա�����б����ѭ��
				WorkCondition workCondition = new WorkCondition();	//���Ҫ��ӽ�ȥ�Ĺ����������
				//���ж���һ�����Ա����û�й������
				String day_begin_str = getDateStrByYearMonthDay(year,month,out_i);	//��һ��0��
				String day_end_str = getDateStrByYearMonthDay(year,month,out_i+1);	//��һ��0��
				HashMap<String, Object> selectOneShiftMap = new HashMap<String, Object>();
				selectOneShiftMap.put("s_id", staff.getS_id());
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
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(todayAttendanceShift.getClock_in());
						workCondition.setWorking_end(todayAttendanceShift.getClock_off());
						workCondition.setS_name(staff.getS_name());
					}else{	//��Ա����һ��û�п��ڼ�¼
						//��¼Ϊ����
						workCondition.setAttendence_status("����");	//6�������
						workCondition.setDep_id(dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setS_name(staff.getS_name());
					}
				}else{	//��Ա������û�й�����ΰ���
					//����ٱ���û����
					HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
					//��ѯ�ĵ�ǰ����
					String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
					
					selectAskforleaveMap.put("s_id", staff.getS_id());
					selectAskforleaveMap.put("now_date", searchAskDate);
					//���ò�ѯ����
					List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
					if(todayAskforleaveList.size() > 0){	//����ټ�¼����Ϊ���״̬
						workCondition.setDep_id(dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setAttendence_status("���");//5�������״̬
						workCondition.setS_name(staff.getS_name());
					}else{	//Ҳû����ټ�¼��û�а�ΰ��ţ�Ҳû����٣�˵���ż٣�����ĩ��������
						workCondition.setAttendence_status("��");
						workCondition.setDep_id(dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setS_name(staff.getS_name());
					}
				}
				//һ��ѭ������������һ�����������ӵ�List��
				workConditionList.add(workCondition);
			}
		}
		
		//����
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
	
	//�ڲ鿴�����Ź������ҳ���У����ĳ��Ա���������������Ա���������еĹ����������ת����ʾ�������������ҳ��
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
		Staff select_staff = staffService.findStaffById(select_sid);
		//��ò���id
		Integer dep_id = staffService.findDepidBySid(select_sid);	//��ø�Ա����Ӧ��dep_id
		//�ò�������Ա���б�
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		//��������¸�Ա���Ĺ������List
		//����ѭ�������ѭ������µ�ÿһ��
		//����Ҫ�õ��Ĺ������list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//�ò��Ÿ�������Ա�������й������
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", select_sid);
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
			selectOneShiftMap.put("s_id", select_staff.getS_id());
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
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
				}else{	//��Ա����һ��û�п��ڼ�¼
					//��¼Ϊ����
					workCondition.setAttendence_status("����");	//6�������
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}else{	//��Ա������û�й�����ΰ���
				//����ٱ���û����
				HashMap<String, Object> selectAskforleaveMap = new HashMap<String, Object>();
				//��ѯ�ĵ�ǰ����
				String searchAskDate = getDateStrByYearMonthDay(year, month, out_i)+" 10:00:00";
				
				selectAskforleaveMap.put("s_id", select_staff.getS_id());
				selectAskforleaveMap.put("now_date", searchAskDate);
				//���ò�ѯ����
				List<Askforleave> todayAskforleaveList = askforleaveService.selectAskforleaveByNowDate(selectAskforleaveMap);
				if(todayAskforleaveList.size() > 0){	//����ټ�¼����Ϊ���״̬
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("���");//5�������״̬
					workCondition.setS_name(select_staff.getS_name());
				}else{	//Ҳû����ټ�¼��û�а�ΰ��ţ�Ҳû����٣�˵���ż٣�����ĩ��������
					workCondition.setAttendence_status("��");
					workCondition.setDep_id(dep_id);
					workCondition.setS_id(select_staff.getS_id());
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
			
			//Сʱ�ĺ�����
			long nh = 1000*60*60;
			
			//StringתDate
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//
			Date begin_date = sdf.parse(begin_str);
			Date end_date = sdf.parse(end_str);
			
			//����ʱ���
			long last_time = end_date.getTime() - begin_date.getTime();
			
			//��������Сʱ
			long last_hour = last_time / nh;
						
			System.out.println(begin_str+"��"+end_str+"���"+last_hour+"��Сʱ");
			
			return last_hour;
			
		} catch (Exception e) {
			e.printStackTrace();
			return (long) 0.0;
		}
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
		
		Integer select_sid = 101;
		if(request.getParameter("select_sid") != null){
			select_sid = Integer.parseInt(request.getParameter("select_sid"));
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
		
		//�Ӱ�ʱ��
		long last_overtime_hour = 0;
		
		//��Ա������
		Staff select_staff = staffService.findStaffById(select_sid);
		//��ò���id
		Integer dep_id = staffService.findDepidBySid(sId);	//��ø�Ա����Ӧ��dep_id
		
		//�õ����µļӰ�List
		HashMap<String, Object> selectOvertimeMap = new HashMap<String, Object>();
		selectOvertimeMap.put("s_id", select_sid);
		selectOvertimeMap.put("begin_date", begin_date_str_detail);
		selectOvertimeMap.put("end_date", end_date_str_detail);
		List<Overtimeapplication> overtimeList = overtimeService.selectOvertimeByMonth(selectOvertimeMap);
		
		System.out.println("�����Ӱ��б�������ǣ�"+overtimeList.size());
		
		//�õ��Ӱ�ʱ��
		for (Overtimeapplication overtimeApply1 : overtimeList) {
			String overtime_begin_str = sdf_detail.format(overtimeApply1.getOvertime_start());
			String overtime_end_str = sdf_detail.format(overtimeApply1.getOvertime_end());
			
			//����ʱ���
			long tmp_last_hour = getLastHour(overtime_begin_str, overtime_end_str);
			
			last_overtime_hour += tmp_last_hour;
		}
		
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
			tmpOvertime.setS_id(select_sid);
			
			//���ҿ��ڼ�¼
			HashMap<String, Object> selectAttMap = new HashMap<String, Object>();
			selectAttMap.put("is_overtime", 1);
			selectAttMap.put("record_id", selectTmpOvertime.getOa_id());
			Attendance selectAttendance = attendanceService.findAttendanceByRecordId(selectAttMap);
					
			tmpOvertime.setOvertime_start(selectAttendance.getClock_in());
			tmpOvertime.setOvertime_end(selectAttendance.getClock_off());
			
			//����ʱ���
			String tmp_overtime_begin_str = sdf_detail.format(selectAttendance.getClock_in());
			String tmp_overtime_end_str = sdf_detail.format(selectAttendance.getClock_off());
			
			//����ʱ���
			long tmp_tmp_last_hour = getLastHour(tmp_overtime_begin_str, tmp_overtime_end_str);
			
			last_overtime_hour += tmp_tmp_last_hour;
			
			//�Ž�List
			tmpOvertimeList.add(tmpOvertime);
		}
		
		System.out.println("��ʱ�ԼӰ��������ǣ�"+tmpOvertimeList.size());
		
		//����
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
	
	
	
	//��������������������������������������������β��֡�������������������
	
	/**
	 * ����
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	//��ʾ�����������ż�¼ҳ��
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
			
			//���Բ�ѯjsp�Ĳ���
			String getDepId = request.getParameter("dep_id");
			String getSId = request.getParameter("s_id");
			String getSName = request.getParameter("s_name");
			Integer getYear = Integer.parseInt(request.getParameter("work_year"));
			Integer getMonth = Integer.parseInt(request.getParameter("work_month"));
			Integer getDate = Integer.parseInt(request.getParameter("work_date"));
			//��ǰҳ����ʾ��s_id---->s_id_now
			String getSidNow = request.getParameter("s_id_now");
			System.out.println("controller.showadd get from selectjsp:"+"����ID"+getDepId+"Ա��ID"+getSId+"��"+getYear+"��"+getMonth+"��"+getDate);
			
			
			//����jsp�����Ĳ�����������
			Calendar workDate = Calendar.getInstance();
			workDate.set(getYear, getMonth, getDate);
			
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//calendarתstring
			String workDateString = sdf.format(workDate.getTime());
			//stringתtimestamp
			Timestamp workDateTime = new Timestamp(sdf.parse(workDateString).getTime());
			
			System.out.println("controller.showadd���칤������"+workDateTime);//ת��timestamp���
			
			//�����ŵ�map�ﴫ��showaddjsp
			HashMap<String,Object> iswMap = new HashMap<String,Object>();
			iswMap.put("dep_id", getDepId);
			iswMap.put("s_id", getSId);
			iswMap.put("s_name", getSName);
			iswMap.put("work_year", getYear);
			iswMap.put("work_month", getMonth);
			iswMap.put("work_date", getDate);
			iswMap.put("s_id_now", getSidNow);
			
			//��showaddNewShiftWork.jsp
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
	//�����������ż�¼
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
			
			//���Բ�ѯjsp�Ĳ���
			String getDepId = request.getParameter("dep_id");
			String getSId = request.getParameter("s_id");
			String getSName = request.getParameter("s_name");
			Integer getYear = Integer.parseInt(request.getParameter("work_year"));
			Integer getMonth = Integer.parseInt(request.getParameter("work_month"));
			Integer getDate = Integer.parseInt(request.getParameter("work_date"));
			String getSidNow = request.getParameter("s_id_now");
			System.out.println("controller.add get from addjsp:"+"����ID"+getDepId+"Ա��ID"+
			getSId+"��"+getYear+"��"+getMonth+"��"+getDate+"ҳ��Ա����ʾ���"+getSidNow);
			
			//��������jsp�Ĳ���
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
			
			System.out.println("controller.from addjsp"+"��ʼСʱ"+getsHour+"��ʼ����"+getsMinute);
			System.out.println("controller.from addjsp"+"����Сʱ"+geteHour+"��������"+geteMinute);
		
			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			workStartT.set(getYear, getMonth, getDate, getsHour, getsMinute, 0);
			workEndT.set(getYear, getMonth, getDate, geteHour, geteMinute, 0);
			
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//calendarתstring
			String workstartString = sdf.format(workStartT.getTime());
			String workEndString = sdf.format(workEndT.getTime());

			//stringתtimestamp
			Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
			Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
			
			System.out.println("controller.add:���쿪ʼʱ��"+workStartTime+"�������ʱ��"+workEndTime);//ת��timestamp���
			
			//�����ŵ�map�ﴫ��Service
			HashMap<String,Object> iswMap = new HashMap<String,Object>();
			iswMap.put("dep_id", getDepId);
			iswMap.put("s_id", getSId);
			iswMap.put("work_start", workStartTime);
			iswMap.put("work_end", workEndTime);
			//������������
			shiftWorkService.insertShiftWork(iswMap);
			
			//����
			session.setAttribute("dep_id", getDepId);
			session.setAttribute("select_year", getYear);
			session.setAttribute("select_month", getMonth);
			session.setAttribute("s_id_now",getSidNow);
			session.setAttribute("usr_id", usr_id);
			
			//�ض��򣬻ص��鿴���мӰ��������
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
	//��ʾ�����������ż�¼ҳ��
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
		
		//���Բ�ѯjsp�Ĳ���
		String getSwid = request.getParameter("sw_id");
		Integer sw_id = Integer.parseInt(getSwid); 
		//��ǰҳ����ʾ��s_id---->s_id_now
		String getSidNow = request.getParameter("s_id_now");
		
		//���ݸ�sw_id��ѯsw
		ShiftWork findbySWid = shiftWorkService.findShiftWorkByswid(sw_id);
		
		System.out.println("controller.showupdate from findbySWid"+findbySWid.getSw_id()+" "+findbySWid.getS_id()+" "
		+findbySWid.getWorking_start()+" "+findbySWid.getWorking_end()+" "+findbySWid.getAttendence_status());

		//��ѯ����
		Department findbydepid = departmentService.findDepById(findbySWid.getDep_id());
		//��ѯԱ��
		Staff findbysid = staffService.findStaffById(findbySWid.getS_id());
		//ʱ���ʽת��
		//����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(findbySWid.getWorking_start());
		//ʱ��
		SimpleDateFormat t = new SimpleDateFormat("HH:mm");
		String workStart = t.format(findbySWid.getWorking_start());
		String workEnd = t.format(findbySWid.getWorking_end());

		//�����ŵ�map�ﴫ��updatejsp
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
		
		//��updateShiftWork.jsp
		request.setAttribute("uswMap", uswMap);
		request.setAttribute("usr_id", usr_id);
		return "director/updateShiftWork";

	}
		
		
	//�����������ż�¼
	@RequestMapping("/updateShiftWork")
	public String updateShiftWork(HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			
			//���Բ�ѯjsp�Ĳ���
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
			
			System.out.println("controller.update from updatejsp"+"��¼ID"+sw_id+"Ա����ʾ���"+getSidNow+"����"
			+getDate+"��ʼʱ��"+getStart+"����ʱ��"+getEnd);
			
			//�����޸�jsp�Ĳ���
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
			//date�ֽ�
			String yearString = getDate.substring(0,4);
			String monthString = getDate.substring(5,7);
			String dateString = getDate.substring(8,10);
			Integer getYear = Integer.parseInt(yearString);
			Integer getMonth = Integer.parseInt(monthString)-1;
			Integer getDate1 = Integer.parseInt(dateString);
			
			System.out.println("controller.update:��ȡ date�ֽ�(string)"+yearString+" y"+monthString+" m"+dateString+" m");
			System.out.println("controller.update:��ȡdate�ֽ�(Integer)"+getYear+" y"+getMonth+" m"+getDate1+" m");

			System.out.println("controller.update:from updatejsp"+"����"+getDate+"��ʼСʱ"+getsHour+"��ʼ����"+getsMinute);
			System.out.println("controller.update:from updatejsp"+"����"+getDate+"����Сʱ"+geteHour+"��������"+geteMinute);

			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			workStartT.set(getYear, getMonth, getDate1, getsHour, getsMinute, 0);
			workEndT.set(getYear, getMonth, getDate1, geteHour, geteMinute, 0);
			
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//calendarתstring
			String workstartString = sdf.format(workStartT.getTime());
			String workEndString = sdf.format(workEndT.getTime());

			//stringתtimestamp
			Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
			Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
			
			System.out.println("controller.update:���쿪ʼʱ��"+workStartTime+"�������ʱ��"+workEndTime);//ת��timestamp���
			
			//�����ŵ�map�ﴫ��Service
			HashMap<String,Object> uswMap = new HashMap<String,Object>();
			uswMap.put("sw_id", sw_id);
			uswMap.put("work_start", workStartTime.toString());
			uswMap.put("work_end", workEndTime.toString());
			//������������
			shiftWorkService.updateShiftWork(uswMap);
			
			ShiftWork updatesw = shiftWorkService.findShiftWorkByswid(sw_id);
			
			//����
			String depid = updatesw.getDep_id().toString();
			session.setAttribute("dep_id", depid);
			session.setAttribute("select_year", getYear);
			session.setAttribute("select_month", getMonth);
			session.setAttribute("s_id_now",getSidNow);
			session.setAttribute("usr_id", usr_id);
			
			//�ض��򣬻ص��鿴���мӰ��������
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
		
		//���Բ�ѯjsp�Ĳ���
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
		System.out.println("controller.delete ����selectjsp:��¼id"+getSwId+"����ID"+getdepId
				+"��"+getYear+"��"+getMonth+"��ǰҳ��Ա����ʾ���"+getSidNow);
		
		Integer sw_id = Integer.parseInt(getSwId);
		//Integer year = Integer.parseInt(getYear);
		//Integer month = Integer.parseInt(getMonth);
		
		shiftWorkService.deleteShiftWork(sw_id);
		
		System.out.println("controller.delete��ɾ����¼IDΪ"+sw_id);
		//�ض������		
		session.setAttribute("dep_id", getdepId);
		session.setAttribute("select_year", getYear);
		session.setAttribute("select_month", getMonth);
		session.setAttribute("s_id_now",getSidNow);
		session.setAttribute("usr_id", usr_id);
		System.out.println("controller.delete�ض��򴫲Σ� ����ID"+getdepId+"��ǰҳ��Ա����ʾ���"+getSidNow);
		
		return "redirect:/director/selectAllShiftWork";
	}
						
	/**
	 * ȫѡ�޸�
	 * 
	 * 		
	 */
	@RequestMapping("/changeAllShiftWork")
	public String changeAllShiftWork (HttpServletRequest request) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
			//��������jsp
			Integer usr_id = 101;
			HttpSession session = request.getSession();
			if(request.getParameter("usr_id") != null){
				usr_id = Integer.parseInt(request.getParameter("usr_id"));
			}else if(session.getAttribute("usr_id") != null){
				usr_id = (Integer)session.getAttribute("usr_id");
			}
			
			//���ڼ�ѡ��
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
			
			System.out.println("changeAll:  ����ID"+getDepid+"��"+getYear+"��"+getMonth+"Ա��IDnow"+getSidNow);
			//ʱ��
			//�����޸�jsp�Ĳ���
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
			
			//���忪ʼ����ʱ��
			Calendar workStartT = Calendar.getInstance();
			Calendar workEndT = Calendar.getInstance();
			
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
				
			//�������ڶ�----��ѯ
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(getYear, getMonth, 1);
					
			//calendar1.add(calendar1.MONTH, -1);
			calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));

			//��һ����
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(getYear, getMonth, 1);

			calendar2.add(calendar1.MONTH, 1);
			calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String nowDate = sdf1.format(calendar1.getTime());
			String nextDate = sdf1.format(calendar2.getTime());
			System.out.println("controller.changeAll:���쿪ʼ����"+nowDate+"�����������"+nextDate);//ת��timestamp���
			

			if (getSidNow.equals(0)) {
				//ȫ��Ա��
				//����service��Map
				HashMap<String, Object> sallMap = new HashMap<String,Object>();
				sallMap.put("dep_id", getDepid);
				sallMap.put("working_start_start",nowDate);
				sallMap.put("working_start_end", nextDate);
				//�õ�����List
				List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
				//List<Department> depList = departmentService.selectAllDepartment();
				List<Staff> sList = staffService.selectStaffByDepId(getDepid);
				
				/**
				*�������߸���
				*/
				Integer i = 0;
				//��¼swallList����
				Integer swalllenth = swallList.size();//123---012
				//�õ����µ����һ��
				int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int x = 1; x <=lastDay; x++) {
					//ʱ��ι���
					workStartT.set(getYear, getMonth, x, getsHour, getsMinute, 0);
					workEndT.set(getYear, getMonth, x, geteHour, geteMinute, 0);
					//calendarתstring
					String workstartString = sdf.format(workStartT.getTime());
					String workEndString = sdf.format(workEndT.getTime());
					//stringתtimestamp
					Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
					Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
					System.out.println("changeAll:��ʼʱ��"+workstartString+"����ʱ��"+workEndString);
					
					//�ڲ����Ա��
					for (int j=0; j<sList.size(); j++) {
					
						Integer staffsid   = sList.get(j).getS_id();
						
						if (i<=swalllenth-1) {
							//ȡswallList���s_id
							Integer sw_sid = swallList.get(i).getS_id();
							//ȡswallList���sw_id
							Integer swid = swallList.get(i).getSw_id();
							//ȡswallList��������е��գ�int��
							Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());
							System.out.println("controller.selectall.changeall����List��ǰ����: "+x+"��ǰԱ��id: "+staffsid+
									"swallList�������:"+nowDayInteger+"swallList��s_id:"+sw_sid);
							
							
							if ((nowDayInteger.equals(x))&&(staffsid.equals(sw_sid))) {
								//��ȵ����޸�
								//���ڼ�ѡ��
								int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
								if (workday[weekdaynow-1]==0) {//δѡ����������
									i++;
									continue;
								}
								//�����ŵ�map�ﴫ��Service
								HashMap<String,Object> uswMap = new HashMap<String,Object>();
								uswMap.put("sw_id",swid);
								uswMap.put("work_start", workStartTime.toString());
								uswMap.put("work_end", workEndTime.toString());
								//���ø��·���
								shiftWorkService.updateShiftWork(uswMap);
								
								System.out.println("controller.selectall.chanAll:swallList�޸�:��ǰ����"+nowDayInteger+"sw����Ա��ID"+sw_sid);
								//swAllList����
								i++;
							}else {
								//���ڼ�ѡ��
								int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
								if (workday[weekdaynow-1]==0) {//δѡ����������
									continue;
								}
								
								HashMap<String,Object> iswMap = new HashMap<String,Object>();
								iswMap.put("dep_id", getDepid);
								iswMap.put("s_id", staffsid);
								iswMap.put("work_start", workStartTime);
								iswMap.put("work_end", workEndTime);
								
								System.out.println("����controller1������+work_start+"+workStartTime+"work_end"+workEndTime);
								
								//������������
								shiftWorkService.insertShiftWork(iswMap);
							}
						}else {
							//���ڼ�ѡ��
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//δѡ����������
								//i++;
								continue;
							}
							//��ʱ�õ���swAllList�Ѿ��������
							HashMap<String,Object> iswMap = new HashMap<String,Object>();
							iswMap.put("dep_id", getDepid);
							iswMap.put("s_id", staffsid);
							iswMap.put("work_start", workStartTime);
							iswMap.put("work_end", workEndTime);
							
							System.out.println("����controller2������+work_start+"+workStartTime+"work_end"+workEndTime);
							
							//������������
							shiftWorkService.insertShiftWork(iswMap);	
						}		
					}
				}
			}else {
				//ĳ��Ա��
				HashMap<String, Object> soneMap = new HashMap<String,Object>();
				soneMap.put("s_id", getSidNow);
				soneMap.put("dep_id", getDepid);
				soneMap.put("working_start_start", nowDate);
				soneMap.put("working_start_end", nextDate);
				System.out.println("controller.selectone: Ա��ID"+getSidNow+"����ID"+getDepid+"��ʼ����"+nowDate+"��������"+nextDate);
				List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
				
				//����swoneList
				int i=0;
				//��¼swoneList����
				Integer swonelenth = swoneList.size();//123---012
				//�õ����µ����һ��
				int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				//�����ڱ���
				for (int x=1;x<=lastDay;x++){
					//ʱ��ι���
					workStartT.set(getYear, getMonth, x, getsHour, getsMinute, 0);
					workEndT.set(getYear, getMonth, x, geteHour, geteMinute, 0);
					//calendarתstring
					String workstartString = sdf.format(workStartT.getTime());
					String workEndString = sdf.format(workEndT.getTime());
					//stringתtimestamp
					Timestamp workStartTime = new Timestamp(sdf.parse(workstartString).getTime());
					Timestamp workEndTime = new Timestamp(sdf.parse(workEndString).getTime());
					System.out.println("changeOne:��ʼʱ��"+workstartString+"����ʱ��"+workEndString);
					
					if (i<=swonelenth-1) {
						//swpneList����δ����
						//ȡswoneList��������е��գ�int��
						Integer nowDayInteger = getDay(swoneList.get(i).getWorking_start());	
						//��ȡԱ��ID
						Integer sw_sid = swoneList.get(i).getS_id();
						//��ȡ��¼ID
						Integer swid = swoneList.get(i).getSw_id();
						System.out.println("controller.selectone���ձ�:      ��ǰ����"+x+"��ǰԱ��id"+getSidNow+"swoneList�������"+nowDayInteger);

						if (nowDayInteger.equals(x)) {
							//��������¼����,�޸�
							//���ڼ�ѡ��
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//δѡ����������
								i++;
								continue;
							}
							
							//�����ŵ�map�ﴫ��Service
							HashMap<String,Object> uswMap = new HashMap<String,Object>();
							uswMap.put("sw_id",swid);
							uswMap.put("work_start", workStartTime.toString());
							uswMap.put("work_end", workEndTime.toString());
							//���ø��·���
							shiftWorkService.updateShiftWork(uswMap);
							
							System.out.println("controller.selectall.chanAll:swallList�޸�:��ǰ����"+nowDayInteger+"sw����Ա��ID"+sw_sid);
							i++;
						}else {
							//���ڼ�ѡ��
							int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
							if (workday[weekdaynow-1]==0) {//δѡ����������
								continue;
							}
							//��������¼�����ڣ�����
							HashMap<String,Object> iswMap = new HashMap<String,Object>();
							iswMap.put("dep_id", getDepid);
							iswMap.put("s_id", getSidNow);
							iswMap.put("work_start", workStartTime);
							iswMap.put("work_end", workEndTime);
							//������������
							shiftWorkService.insertShiftWork(iswMap);
						}
					}else {
						//���ڼ�ѡ��
						int weekdaynow = workStartT.get(Calendar.DAY_OF_WEEK);
						if (workday[weekdaynow-1]==0) {//δѡ����������
							//i++;
							continue;
						}
						//swone��������
						HashMap<String,Object> iswMap = new HashMap<String,Object>();
						iswMap.put("dep_id", getDepid);
						iswMap.put("s_id", getSidNow);
						iswMap.put("work_start", workStartTime);
						iswMap.put("work_end", workEndTime);
						//������������
						shiftWorkService.insertShiftWork(iswMap);
					}
				}
			}	
				
				
				
			//�ض������
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
	 * �״ν����ѯ����
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	//��ѯĳ��������Ա���Ĺ������ż�¼
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
		HashMap<String, Object> sallMap = new HashMap<String,Object>();
		sallMap.put("dep_id", depid);
		sallMap.put("working_start_start",nowDate);
		sallMap.put("working_start_end", nextDate);
		
		System.out.println("controller.selectall.initial:��ʼֵsallMap:��ʼ����"+nowDate+"��������"+nextDate);
		
		
		//���õ���������jsp
		List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
		List<Department> depList = departmentService.selectAllDepartment();
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		
		if (swallList.size()<=0) {
			System.out.println("controller.selectall.initial:swallList-----null\n");
		} else {
			for (ShiftWork shiftWork : swallList) {
				System.out.println("controller.selectall.initial:swallList��ʼֵ��Ա��ID"+shiftWork.getS_id()+"**\n");
			}
		}
		
		
		if (sList.size()<=0) {
			System.out.println("controller.selectall.initial:sList-----null\n");
		} else {
			for (Staff staff1 : sList) {
				System.out.println("controller.selectall.initial:sList��ʼֵ��Ա��ID"+staff1.getS_id()+"**\n");
			}
		}
		
		for (Staff staff1 : sList) {
			System.out.println("controller.selectall.initial��ʼҳ��õ�Ա������");
			System.out.println(staff1.getS_name());
		}
		
		

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
		Integer swalllenth = swallList.size();//123---012
		//�õ����µ����һ��
		int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		//�������
		//���ѭ��
		for (int x=1;x<=lastDay;x++){
			//�ڲ�ѭ��
			for (int j=0; j<sList.size(); j++) {
				//��jspswList�Ķ���
				ShiftWork jspsw = new ShiftWork();
			
				//ȡsList���s_id
				Integer staffsid = sList.get(j).getS_id();
				//depid����
				jspsw.setDep_id(1001);
				//s_idֱ�ӷ���
				jspsw.setS_id(staffsid);

				//��swallListδ��������
				if (i<=swalllenth-1) {
			
					//ȡswallList���s_id
					Integer swsid = swallList.get(i).getS_id();
					//ȡswallList��������е��գ�int��
					Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());	

					System.out.println("controller.selectall.initial����List��ǰ����: "+x+"��ǰԱ��id: "+staffsid+
							"swallList�������:"+nowDayInteger+"swallList��s_id:"+swsid);

						
					//�ж��Ƿ����working_start��working_end
					if ((nowDayInteger.equals(x))&&(staffsid.equals(swsid))) {
						System.out.println("controller.selectall.initial:swallList���벹��List:��ǰ����"+nowDayInteger+"sw����Ա��ID"+swsid);
						
						Timestamp swStart = swallList.get(i).getWorking_start();
						Timestamp swEnd = swallList.get(i).getWorking_end(); 
						
						jspsw.setSw_id(swallList.get(i).getSw_id());
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
		//����
		return "director/selectAllShiftWork";
	
	}
	//��timestamo����ȡ��int���͵�����
	public static int getDay(Timestamp timestamp) {
		String tString = timestamp.toString();
		System.out.println("controller.ʱ��ת���õ�timestamp���͵�����tString"+tString);
		Integer tInteger = Integer.parseInt(tString.substring(8,10));
		System.out.println("controller.ʱ��ת���õ�timestamp���͵����ڣ�tInteger"+tInteger);
		return tInteger;
	}
	/**
	 * ����ĳ����ȫ��Ա��ID
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/selectAllShiftWork")
	public String showSelectAllswByMonthByDep (HttpServletRequest request) throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		//�����е�dep_id����jsp
		//List<Department> depList = departmentService.selectAllDepartment();
		//��ò���----�ض������jsp
		String getDepid = "1001";
		if (request.getParameter("dep_id")!=null) {
			getDepid = request.getParameter("dep_id");
		} else if (request.getSession()!=null) {
			HttpSession session = request.getSession();
			if(session.getAttribute("dep_id") != null) {
				getDepid =(String) session.getAttribute("dep_id");
			}
		}
		
		//��¼���û�id
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

		System.out.println("controller.selectall.  from jsp:��"+year+"��"+month+"����ID"+getDepid+"Ա��ID"+getSid);
		
		
		//�Ѹò������е�staff����jsp
		Integer depid = Integer.parseInt(getDepid);
		Integer sid  = Integer.parseInt(getSid);
		
		System.out.println("����controller�õ���depId��ɶ��"+depid);
		
		
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		Department department = departmentService.findDepById(depid);
		
		if(department == null){
			System.out.println("department�ǿհ�������������������������������������������");
		}
		
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
		
		
		//����selectall
		if (sid==0) {
			//��������Serivce
			HashMap<String, Object> sallMap = new HashMap<String,Object>();
			sallMap.put("dep_id", getDepid);
			sallMap.put("working_start_start",nowDate);
			sallMap.put("working_start_end", nextDate);
			
			//����jsp��õ��Ĳ�����service,ͬʱ�õ���������jsp
			//shiftwork
			List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );

			//�������
			if (swallList.size()<=0) {
				System.out.println("controller.selectall:swallList-----null");
			} else {
				for (ShiftWork sw : swallList) {
					System.out.println("controller.selectall:sallList��sw���ڵ�Ա��id"+sw.getS_id());
				}
			}
			
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
			Integer swalllenth = swallList.size();//123---012
			//�õ����µ����һ��
			int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
			//�������
			//���ѭ��
			for (int x=1;x<=lastDay;x++){
				//�ڲ�ѭ��
				for (int j=0; j<sList.size(); j++) {
					//��jspswList�Ķ���
					ShiftWork jspsw = new ShiftWork();
				
					//ȡsList���s_id
					Integer staffsid = sList.get(j).getS_id();
					//depid����
					jspsw.setDep_id(depid);
					//s_idֱ�ӷ���
					jspsw.setS_id(staffsid);

					//��swallListδ��������
					if (i<=swalllenth-1) {
				
						//ȡswallList���s_id
						Integer swsid = swallList.get(i).getS_id();
						//ȡswallList��������е��գ�int��
						Integer nowDayInteger = getDay(swallList.get(i).getWorking_start());	

						System.out.println("controller.selectall ���գ�  ��ǰ����: "+x+"��ǰԱ��id: "+staffsid+
								"swallList������ڣ�"+nowDayInteger+"swallList��s_id:"+swsid);

							
						//�ж��Ƿ����working_start��working_end
						if ((nowDayInteger.equals(x))&&(staffsid.equals(swsid))) {
							System.out.println("controller.selectall:swallList���벹�ձ�"+"��ǰ����"+nowDayInteger+"sw����Ա��ID"+swsid);
							
							Timestamp swStart = swallList.get(i).getWorking_start();
							Timestamp swEnd = swallList.get(i).getWorking_end(); 
							jspsw.setSw_id(swallList.get(i).getSw_id());
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
			}		
			
			for (ShiftWork shiftWork : jspswList) {
				//System.out.println("jspswList :"+shiftWork.getDep_id()+" "+shiftWork.getS_id()+" "+shiftWork.getWorking_start());
			}
			request.setAttribute("swList", jspswList);
		} else {
			//��ѯĳԱ��
			//����selectone
			HashMap<String, Object> soneMap = new HashMap<String,Object>();
			soneMap.put("s_id", getSid);
			soneMap.put("dep_id", getDepid);
			soneMap.put("working_start_start", nowDate);
			soneMap.put("working_start_end", nextDate);
			System.out.println("controller.selectone: Ա��ID"+getSid+"����ID"+getDepid+"��ʼ����"+nowDate+"��������"+nextDate);
			List<ShiftWork> swoneList = shiftWorkService.selectOneStaffWorkConByMonthByDep(soneMap);
			
			if (swoneList.size()<=0) {
				//System.out.println("swoneList-----null\n");
			} else {
				for (ShiftWork sw : swoneList) {
					//System.out.println("soneList��"+sw.getS_id()+"**\n");
				}
			}
			
			
			List<ShiftWork> jspswList = new ArrayList<ShiftWork>();
			/*****���*****/
			/**
			 * ��swonelList��ֵʱ������ֵ�͵�ǰ��Ƚ�
			 * ��ͬ���룬��ͬ����
			 * ��swoneList��ֵ������������
			 * ֱ�Ӹ�jspswList�����ǰ���ں�Ա��id�����
			 */
			//����swoneList
			int i=0;
			//��¼swoneList����
			Integer swonelenth = swoneList.size();//123---012
			//�õ����µ����һ��
			int lastDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
			//�������
			//���ѭ��
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

					System.out.println("controller.selectone���ձ�:      ��ǰ����"+x+"��ǰԱ��id"+sid+"swoneList�������"+nowDayInteger);

					//�ж��Ƿ����working_start��working_end
					if (nowDayInteger.equals(x)) {
						System.out.println("controller.selectone���գ�swoneList���벹�ձ�"+nowDayInteger+" "+sid);
							
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
			
			for (ShiftWork shiftWork : jspswList) {
				//System.out.println("jspswList :"+shiftWork.getDep_id()+" "+shiftWork.getS_id()+" "+shiftWork.getWorking_start());
			}
			System.out.println("controller.soneList���ձ�:jspswList.size()"+jspswList.size());
			request.setAttribute("swList", jspswList);
		}
		
		
		//for (Staff st : sList) {
		//	System.out.println("����controller");
		//	System.out.println("��staff��"+st.getS_name()+"---------------------\n");
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
		System.out.println("����department"+department.getDep_name());
		
		return "director/selectAllShiftWork";
	}
	
	
	//������������������������������β��ֽ���������������������������
	
}