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
 * ���������
 * @author dzz
 * 2019��6��5�� ����8:47:14
 * AttendanceSystem
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	//����service
	private TemporaryovertimeService tmpOvertimeService = new TemporaryovertimeServiceImpl();
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	private AttendanceService attendanceService = new AttendanceServiceImpl();
	private AskforleaveService askforleaveService = new AskforleaveServiceImpl();
	private ShiftWorkService shiftWorkService = new ShiftWorkServiceImpl();
	private StaffService staffService = new StaffServiceImpl();
	private DepartmentService departmentService = new DepartmentServiceImpl();
	private MessageService messageService = new MessageServiceImpl();
	
	//������������������������Ա�����֡�����������������������
	
	//������ת���鿴�Լ�����Ϣ��ҳ��
	@RequestMapping("/managerSelectSelfStaffInfoJump")
	public String managerSelectSelfStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
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
		
		return "/manager/managerSelectSelfStaffInfo";
	}
	
	//�鿴�Լ��������˻���Ϣ
	@RequestMapping("/managerSelectSelfStaffInfo")
	public String findStaffById(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
        //���ò�ѯ����
    	Staff staff = staffService.findStaffById(sId);
						
		//����
		request.setAttribute("staff", staff);
        request.setAttribute("s_id", sId);
		
		//��ת
		return "/manager/managerSelectSelfStaffInfo";
	}
	
	//��ת���޸��Լ�����Ϣ��ҳ��ķ���
	@RequestMapping("/managerUpdateSelfStaffInfoJump")
	public String managerUpdateSelfStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
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
		
		return "/manager/managerUpdateSelfStaffInfo";
	}
	
	//�����Լ����˻���Ϣ	
	@RequestMapping("/managerUpdateSelfStaffInfo")
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
		return "redirect:/manager/managerSelectSelfStaffInfo";
	}
	
	//����鿴Ա����Ϣ
	@RequestMapping("/managerSelectStaffInfo")
	public String selectAllStaff(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
			request.setCharacterEncoding("UTF-8");
	        
	        Integer dep_id = 1001;

			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}
	        if(request.getParameter("dep_id") != null){	//ҳ�洫���Ĳ���
	        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				if(session.getAttribute("dep_id") != null) {
					//dep_id = (Integer)session.getAttribute("dep_id");
					if(session.getAttribute("dep_id") != null){
						dep_id = Integer.parseInt((String )session.getAttribute("dep_id"));
					}
				}
			}
			//������id���Ҹò�������Ա���б�
			List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
			
			//�������в��ŵ�list
			List<Department> departmentList = departmentService.selectAllDepartment();
			
			//System.out.println("       "+dep_id);
	        //����
	        request.setAttribute("staffList", staffList);
	        request.setAttribute("departmentList", departmentList);
	        request.setAttribute("s_id", sId);
	        //��ת
		    return "/manager/managerSelectStaffInfo";
	}
	
	//����ͨ������id�鿴Ա����Ϣ
	@RequestMapping("/managerSelectStaffInfoByDepid")
	public String managerSelectStaffInfoByDepid(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
			request.setCharacterEncoding("UTF-8");

			//���Ա����Ϣ���ҳ�洫���Ĳ���
			String getId = request.getParameter("s_id");
			String getS_name = request.getParameter("s_name");
			String getEntry_time = request.getParameter("entry_time");
			String getDep_id = request.getParameter("dep_id");
			String getIdentity = request.getParameter("identity");
			String getS_pass = request.getParameter("s_pass");
			
			//�Ѳ����ŵ�Map��
			HashMap<String, Object> staffMap = new HashMap<String, Object>();
			staffMap.put("s_id",getId);
			staffMap.put("s_name", getS_name);
			staffMap.put("entry_time", getEntry_time);
			staffMap.put("dep_id", getDep_id);
			staffMap.put("identity", getIdentity);
			staffMap.put("s_pass", getS_pass);
			
			System.out.println("controller:"+getIdentity);
			
			//���session�Ĳ���
	        Integer dep_id = 1001;
	        if(request.getParameter("dep_id") != null){	//ҳ�洫���Ĳ���
	        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				dep_id = (Integer)session.getAttribute("dep_id");
			}

			Integer sId = 0;
			if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
				HttpSession session = request.getSession();
				sId = (Integer)session.getAttribute("s_id");
			}

			//�������в��ŵ�list
			List<Department> departmentList = departmentService.selectAllDepartment();
			//������id���Ҹò�������Ա���б�
			List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
			
	        //����
	        request.setAttribute("staffList", staffList);
	        request.setAttribute("departmentList", departmentList);
	        request.setAttribute("s_id", sId);
	        request.setAttribute("dep_id", dep_id);
			HttpSession session = request.getSession();
			session.setAttribute("dep_id", dep_id);
	        //��ת
		    return "/manager/managerSelectStaffInfo";
	}
	
	@RequestMapping("/insertNewStaffInfoJump")
	public String insertNewStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}     
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//ҳ�洫���Ĳ���
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
		System.out.println(dep_id);
		//����
		request.setAttribute("s_id", sId);
		request.setAttribute("dep_id", dep_id);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		
		return "/manager/insertNewStaffInfo";
	}
	
	//����Ա����Ϣ
	@RequestMapping("/insertNewStaffInfo")
	public String insertNewStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//��ò���
		Integer select_id = 0;
		if(request.getParameter("select_id") != null){	//ҳ�洫���Ĳ���
			select_id = Integer.parseInt(request.getParameter("select_id"));
		}
        
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//ҳ�洫���Ĳ���
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
		
		//���Ա����Ϣ���ҳ�洫���Ĳ���
		//String getId = request.getParameter("s_id");
		String getS_name = request.getParameter("s_name");
		String getEntry_time = request.getParameter("entry_time");
		String getIdentity = request.getParameter("identity");
		String getS_pass = request.getParameter("s_pass");
		
		//�Ѳ����ŵ�Map��
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",select_id);
		staffMap.put("s_name", getS_name);
		staffMap.put("entry_time", getEntry_time);
		staffMap.put("identity", getIdentity);
		staffMap.put("s_pass", getS_pass);
		//������id���Ҹò�������Ա���б�
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		Integer identity_int = Integer.parseInt(getIdentity);
		if(identity_int.equals(2)){
			//���������ܽ�ְ
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
		
		
		System.out.println("controller�õ���������ʲô��"+getS_name);
		
		//��������Ա����Ϣ�ķ���
		staffService.insertNewStaffInfoReturnId(staffMap);
		request.setAttribute("dep_id", dep_id);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		session.setAttribute("s_id", sId);
		
		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	//ɾ��Ա����Ϣ�Ӳ�
	@RequestMapping("/deleteStaffInfoJump")
	public String deleteStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
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
		
		return "/manager/deleteStaffInfo";
	}
	
	//ɾ��Ա����Ϣ
	@RequestMapping("/deleteStaffInfo")
	public String deleteStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//���Ա����Ϣ���ҳ�洫���Ĳ���
		String getId = request.getParameter("s_id");
		
		//����ɾ���ķ���
		Integer s_id1 = Integer.parseInt(getId);
		staffService.deleteStaffInfoReturnId(s_id1);
		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	//����Ա����Ϣ�Ӳ�
	@RequestMapping("/managerUpdateStaffInfoJump")
	public String managerUpdateStaffInfoJump(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
        Integer select_sId = 101;
        if(request.getParameter("select_sId") != null){	//ҳ�洫���Ĳ���
        	select_sId = Integer.parseInt(request.getParameter("select_sId"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			select_sId = (Integer)session.getAttribute("select_sId");
		}
        
        //���Ҫ�޸ĵĶ���
        Staff select_staff = staffService.findStaffById(select_sId);
		
		//����
		request.setAttribute("s_id", sId);
		request.setAttribute("staff1", select_staff);
		
		return "/manager/managerUpdateStaffInfo";
	}
	
	//����Ա����Ϣ
	@RequestMapping("/managerUpdateStaffInfo")
	public String updateStaffInfo(HttpServletRequest request, Integer s_id) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");

		//���Ա����Ϣ���ҳ�洫���Ĳ���
		String getId = request.getParameter("s_id");
		String getS_name = request.getParameter("s_name");
		String getEntry_time = request.getParameter("entry_time");
		String getDep_id = request.getParameter("dep_id");
		String getIdentity = request.getParameter("identity");
		String getS_pass = request.getParameter("s_pass");
		System.out.println(getS_pass);
		
		System.out.println("sname��ʲô����������������������������������");
		System.out.println("�õ���sName��ʲô��"+getS_name);
		
		
		//System.out.println("�ӵ���identity��"+getIdentity);
		
		//�Ѳ����ŵ�Map��
		HashMap<String, Object> staffMap = new HashMap<String, Object>();
		staffMap.put("s_id",getId);
		//staffMap.put("s_name", getS_name);
		staffMap.put("entry_time", getEntry_time);
		staffMap.put("dep_id", getDep_id);
		staffMap.put("identity", getIdentity);
		staffMap.put("s_pass", getS_pass);
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
        Integer dep_id = 1001;
        if(request.getParameter("dep_id") != null){	//ҳ�洫���Ĳ���
        	dep_id = Integer.parseInt(request.getParameter("dep_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			dep_id = (Integer)session.getAttribute("dep_id");
		}
        Staff staff1 = staffService.findStaffById(sId);
		List<Staff> staffList = staffService.selectStaffByDepId(dep_id);
		
		Integer identity_int = Integer.parseInt(getIdentity);
		
		if(identity_int.equals(2)){
			//���������ܽ�ְ
			for(Staff staff: staffList) {
				if(staff.getIdentity()==2) {	
					HashMap<String, Object> updateMap = new HashMap<String, Object>();
					updateMap.put("identity", 1);
					updateMap.put("s_id", staff.getS_id());
					staffService.updateStaffInfoReturnId(updateMap);					
					
				}
			}
		}
		
		//���������ķ���
		staffService.updateStaffInfoReturnId(staffMap);
		request.setAttribute("staff1", staff1);
		request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("dep_id", dep_id);
		session.setAttribute("s_id", sId);
		request.setAttribute("dep_id", dep_id);

		return "redirect:/manager/managerSelectStaffInfo";
	}
	
	
	//������������������������Ա�����ֽ���������������������������
	
	
	//��Ա�����ģ��������Ƿ������µ���ʱ�ԼӰ�
	public String addMessageToAllStaffForTmpOvertime(){
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String m_time = formatter.format(new Date());
	    //Staff applyStaff = staffService.findStaffById(s_id);
	    //String s_name = applyStaff.getS_name();
	    Integer is_read = 0;
	    String m_content = "���������µ�ȫ��λ��ʱ�ԼӰ࣬��ȥ�鿴�ɣ���";
	    
	    //���ȫ��λ��Ա��
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
		return "manager/selectAllTemporaryOvertime";
	}
	
	@RequestMapping("/addNewTemporaryOvertimeJump")
	public String addNewTemporaryOvertimeJump(HttpServletRequest request) throws ServletException, IOException{
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
		
		return "manager/addNewTemporaryOvertime";
	}
	
	@RequestMapping("/addNewTemporaryOvertime")
	public String addNewTemporaryOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		//���ҳ�洫���Ĳ���
		String getStartTime = request.getParameter("t_overtime_start");
		String getEndTime = request.getParameter("t_overtime_end");
		String t_o_reason = request.getParameter("t_o_reason");
		
		//�Ѳ����ŵ�Map��
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		toMap.put("t_overtime_start", getStartTime);
		toMap.put("t_overtime_end", getEndTime);
		toMap.put("t_o_reason", t_o_reason);
		
		//���������ķ���
		tmpOvertimeService.insertTempOvertime(toMap);
		
		//���÷���Ϣ�ķ�����֪ͨȫ��λԱ��
		addMessageToAllStaffForTmpOvertime();
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		return "redirect:/manager/selectAllTemporaryOvertime";
		//return "manager/selectAllTemporaryOvertime";
	}
	
	@RequestMapping("/deleteTempOvertime")
	public String deleteTempOvertime(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//��ò���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//ҳ�洫���Ĳ���
			sId = Integer.parseInt(request.getParameter("s_id"));
		}else if(request.getSession() != null){	//controller֮��ͨ��session���ε�
			HttpSession session = request.getSession();
			sId = (Integer)session.getAttribute("s_id");
		}
		
		Integer to_id = 0;
		if(request.getParameter("to_id") != null){
			to_id = Integer.parseInt(request.getParameter("to_id"));
		}
		
		//����ɾ������
		//���ж���û������
		Temporaryovertime selectTmpOvertime = tmpOvertimeService.findTempOvertimeByToid(to_id);
		if(selectTmpOvertime != null){	//ȷ��ɾ��
			tmpOvertimeService.deleteTempovertimeByToid(to_id);
		}
		
		//����
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		//return "manager/selectAllTemporaryOvertime";
		return "redirect:/manager/selectAllTemporaryOvertime";
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
		
		return "manager/homepage";
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
		//Integer dep_id = staffService.findDepidBySid(sId);	//��ø����ܶ�Ӧ��dep_id
		//�ò�������Ա��List
		List<Staff> staffList = staffService.selectStaffByDepId(select_dep_id);
		
		//�������������Ա���Ĺ������List
		//˫��ѭ�������ѭ������µ�ÿһ�죬�ڲ�ѭ������������Ա��
		//����Ҫ�õ��Ĺ������list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//�ò��Ÿ�������Ա�������й������
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("dep_id", select_dep_id);
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
				selectOneShiftMap.put("dep_id", select_dep_id);
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
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(todayAttendanceShift.getClock_in());
						workCondition.setWorking_end(todayAttendanceShift.getClock_off());
						workCondition.setS_name(staff.getS_name());
					}else{	//��Ա����һ��û�п��ڼ�¼
						//��¼Ϊ����
						workCondition.setAttendence_status("����");	//6�������
						workCondition.setDep_id(select_dep_id);
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
						workCondition.setDep_id(select_dep_id);
						workCondition.setS_id(staff.getS_id());
						workCondition.setWorking_start(empty_date);
						workCondition.setWorking_end(empty_date);
						workCondition.setAttendence_status("���");//5�������״̬
						workCondition.setS_name(staff.getS_name());
					}else{	//Ҳû����ټ�¼��û�а�ΰ��ţ�Ҳû����٣�˵���ż٣�����ĩ��������
						workCondition.setAttendence_status("��");
						workCondition.setDep_id(select_dep_id);
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
		
		//������еĲ����б�
		List<Department> departmentList = departmentService.selectAllDepartment();
		
		//����
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
		
		//�Ӱ�ʱ��
		long last_shiftwork_hour = 0;
		int late_count = 0;	//�ٵ�����
		int early_count = 0;	//���˼���
		int miss_count = 0;	//��������
		
		//��Ա������
		Staff select_staff = staffService.findStaffById(select_sid);
		//��ò���id
		//Integer dep_id = staffService.findDepidBySid(select_sid);	//��ø�Ա����Ӧ��dep_id
		//�ò�������Ա���б�
		List<Staff> staffList = staffService.selectStaffByDepId(select_dep_id);
		
		//��������¸�Ա���Ĺ������List
		//����ѭ�������ѭ������µ�ÿһ��
		//����Ҫ�õ��Ĺ������list
		List<WorkCondition> workConditionList = new ArrayList<WorkCondition>();
		//�ò��Ÿ�������Ա�������й������
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("s_id", select_sid);
		selectMap.put("dep_id", select_dep_id);
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
			selectOneShiftMap.put("dep_id", select_dep_id);
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
						late_count++;
					}else if(attendance_status == 3){
						workCondition.setAttendence_status("����");
						early_count++;
					}else{
						workCondition.setAttendence_status("�ȳٵ�������");
						late_count++;
						early_count++;
					}
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(todayAttendanceShift.getClock_in());
					workCondition.setWorking_end(todayAttendanceShift.getClock_off());
					workCondition.setS_name(select_staff.getS_name());
					if(todayAttendanceShift.getClock_off() != null){
						//����ʱ��
						String shiftwork_begin_str = sdf_detail.format(todayAttendanceShift.getClock_in());
						String shiftwork_end_str = sdf_detail.format(todayAttendanceShift.getClock_off());
						
						//����ʱ���
						long tmp_last_hour = getLastHour(shiftwork_begin_str, shiftwork_end_str);
						
						last_shiftwork_hour += tmp_last_hour;
					}
				}else{	//��Ա����һ��û�п��ڼ�¼
					//��¼Ϊ����
					workCondition.setAttendence_status("����");	//6�������
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
					
					miss_count++;
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
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setAttendence_status("���");//5�������״̬
					workCondition.setS_name(select_staff.getS_name());
				}else{	//Ҳû����ټ�¼��û�а�ΰ��ţ�Ҳû����٣�˵���ż٣�����ĩ��������
					workCondition.setAttendence_status("��");
					workCondition.setDep_id(select_dep_id);
					workCondition.setS_id(select_staff.getS_id());
					workCondition.setWorking_start(empty_date);
					workCondition.setWorking_end(empty_date);
					workCondition.setS_name(select_staff.getS_name());
				}
			}
			//һ��ѭ������������һ�����������ӵ�List��
			workConditionList.add(workCondition);
		}
		
		//������еĲ����б�
		List<Department> departmentList = departmentService.selectAllDepartment();
		
		//����
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
			if(selectAttendance.getClock_off() != null){
				continue;
			}
					
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
		
		return "manager/selectOneOvertimeConByMonthByDep";
	}
	
	
	
	
	//��������������������������������������β��֡�������������������
	
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
		sallMap.put("dep_id", 1001);
		sallMap.put("working_start_start",nowDate);
		sallMap.put("working_start_end", nextDate);
		
		System.out.println("controller.selectall.initial:��ʼֵsallMap:��ʼ����"+nowDate+"��������"+nextDate);
		
		
		//���õ���������jsp
		List <ShiftWork> swallList = shiftWorkService.selectAllStaffWorkConByMonthByDep(sallMap );
		List<Department> depList = departmentService.selectAllDepartment();
		List<Staff> sList = staffService.selectStaffByDepId(1001);
		
		if (swallList.size()<=0) {
			System.out.println("controller.selectall.initial:swallList-----null\n");
		} else {
			for (ShiftWork shiftWork : swallList) {
				System.out.println("controller.selectall.initial:swallList��ʼֵ��Ա��ID"+shiftWork.getS_id()+"**\n");
			}
		}
		
		if (depList.size()<=0) {
			System.out.println("controller.selectall.initial:depList-----null\n");
		} else {
			for (Department department : depList) {
				System.out.println("controller.selectall.initial:depList��ʼֵ������ID"+department.getDep_id()+"**\n");
			}
		}
		
		if (sList.size()<=0) {
			System.out.println("controller.selectall.initial:sList-----null\n");
		} else {
			for (Staff staff : sList) {
				System.out.println("controller.selectall.initial:sList��ʼֵ��Ա��ID"+staff.getS_id()+"**\n");
			}
		}
		
		for (Staff staff : sList) {
			System.out.println("controller.selectall.initial��ʼҳ��õ�Ա������");
			System.out.println(staff.getS_name());
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
		request.setAttribute("depList",depList);
		request.setAttribute("sList", sList);
		request.setAttribute("dep_id", 1001);
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", 0);
		request.setAttribute("usr_id", usrid);
		//����
		return "manager/selectAllShiftWork";
	
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
		
		
		//��ò���----�ض������jsp
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

		System.out.println("controller.selectall.  from jsp:��"+year+"��"+month+"����ID"+getDepid+"Ա��ID"+getSid);
		
		
		//�Ѹò������е�staff����jsp
		Integer depid = Integer.parseInt(getDepid);
		Integer sid  = Integer.parseInt(getSid);
		List<Staff> sList = staffService.selectStaffByDepId(depid);
		
		
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
		}
		else {
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
			
			//����
			request.setAttribute("swList", jspswList);
		}
		//for (Staff st : sList) {
				//	System.out.println("����controller");
				//	System.out.println("��staff��"+st.getS_name()+"---------------------\n");
				//}
		
		
		request.setAttribute("select_year", year);
		request.setAttribute("select_month", month);
		request.setAttribute("s_id", sid);
		request.setAttribute("depList", depList);
		request.setAttribute("sList", sList);
		request.setAttribute("usr_id", usr_id);
		
		return "manager/selectAllShiftWork";
	}
	
	
	//��������������������������β��ֽ�������������������������������
	
	
}