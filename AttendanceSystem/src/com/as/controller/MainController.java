package com.as.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.as.entity.Overtimeapplication;
import com.as.entity.Staff;
import com.as.service.OvertimeapplicationService;
import com.as.service.StaffService;
import com.as.service.impl.OvertimeapplicationServiceImpl;
import com.as.service.impl.StaffServiceImpl;

/**
 * ������������Ҷ��õģ�
 * @author dzz
 * 2019��6��8�� ����3:30:44
 * AttendanceSystem
 */
@Controller
@RequestMapping("/main")
public class MainController {

	//������Service����
	private StaffService staffService = new StaffServiceImpl();
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	
	//�ж�һ���ַ����Ƿ����������
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
	
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
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String message = "";
		
		//��õ�¼�Ĳ���
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//�ж��Ƿ�Ϊ��ֵ
			if( isNumeric(request.getParameter("s_id")) ){	//�ж��Ƿ�������
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else{
				message = "Ա�������������ɣ�";
				request.setAttribute("message", message);
				return "main/login";
			}
		}else{
			message = "";
			request.setAttribute("message", message);
			return "main/login";
		}
		
		String s_pass = "";
		if(request.getParameter("s_pass")!=null && !"".equals(request.getParameter("s_pass"))){
			s_pass = request.getParameter("s_pass");
		}
		
		//���ж��Ƿ���ڸ�Ա��id
		Staff loginStaff = staffService.findStaffById(sId);
		if(loginStaff == null){
			message = "��Ա����Ų����ڣ�";
			request.setAttribute("message", message);
			return "main/login";
		}
		
		//�ȶ�Ա��id�������Ƿ���ȷ
		if( !s_pass.equals(loginStaff.getS_pass()) ){	//�ȶԴ���
			message = "�������";
			request.setAttribute("message", message);
			return "main/login";
		}
		
		//��ø�Ա������Ĺ�������б�ͼӰ��б������ã�������������
//		if(loginStaff.getIdentity() != 3){
//			//��ý��������
//			Date todayDate = new Date();
//			SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String now_date = sdf_overtime.format(todayDate);	//��ǰʱ��
//			String next_date = getNextDate(todayDate);	//��һ�����������
//			String begin_date = getBeginDate(todayDate);	//��ǰ���ڵĿ�ʼʱ��
//			System.out.println("��ǰʱ�䣺"+now_date+"��һ�����ڣ�"+next_date
//					+"��ǰ���ڵĿ�ʼ��"+begin_date);
//			
//			
//			//��������б�
//			
//			//�Ӱ��б�
//			HashMap<String, Object> overtimeMap = new HashMap<String, Object>();
//			overtimeMap.put("s_id", sId);
//			overtimeMap.put("now_date", now_date);
//			overtimeMap.put("next_date", next_date);
//			overtimeMap.put("begin_date", begin_date);
//			//��ÿ���ǩ������ʱ�Ӱ��б�
//			List<Overtimeapplication> overtimeSignInList = overtimeService.selectSignInOvertimeApply(overtimeMap);
//			//��ÿ���ǩ�˵���ʱ�Ӱ��б�
//			List<Overtimeapplication> overtimeSignOffList = overtimeService.selectSignOffOvertimeApply(overtimeMap);
//			//����
//			request.setAttribute("overtimeSignInList", overtimeSignInList);
//			request.setAttribute("overtimeSignOffList", overtimeSignOffList);
//		}
		
//		System.out.println("���·������������������������������������������������������������������������������");
//		System.out.println(System.getProperty("java.library.path"));
		
		//��½�ɹ�������������ж���ת���ĸ���ҳ
		request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		if(loginStaff.getIdentity() == 3){	//�Ǿ���
			return "manager/homepage";
		}else if(loginStaff.getIdentity() == 2){	//�ǲ�������
			//�ض���
			//return "director/homepage";
			return "redirect:/director/selectTodayWorkHomepage";
		}else{	//��������ͨԱ��
			//return "staff/homepage";
			return "redirect:/staff/selectTodayWorkHomepage";
		}
	}
	
	
	//������ڸ�ʽ�Ƿ���ȷ
	boolean checkDateFormat(String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date changToDate = sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	boolean checkDateOrder(String date_start, String date_end){
		try {
			//�ȸ�ʽ��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date_start_date = sdf.parse(date_start);
			Date date_end_date = sdf.parse(date_end);
			//�Ƚ��Ⱥ�
			if(date_start_date.before(date_end_date)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//��֤����������Ƿ���ȷ��AJAX����������ת�����ض���ȷ���ҳ�����Ϊ
	@RequestMapping("/checkDate")
	public void checkDate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		try{
			//��ȡ���� 
			String date_start= request.getParameter("date_start");
			String date_end= request.getParameter("date_end");
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= response.getWriter();
			
			//���жϸ�ʽ�Ƿ���ȷ
			if(!checkDateFormat(date_start) || !checkDateFormat(date_end)){
				//��ʽ����ȷ
				out.print("false");
				//System.out.println("��ʽfalse");
			}else{
				//�ж�end�������Ƿ���start֮��
				if(checkDateOrder(date_start, date_end)){
					out.print("true");
					//System.out.println("��ȷ����������������");
				}else{
					out.print("false");
					//System.out.println("�Ⱥ�false��1");
				}
			}
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//��֤����������Ƿ���ȷ
	@RequestMapping("/checkTime")
	public void checkTime(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		try{
			//System.out.println("test--------------");
			
			//��ȡ���� 
			Integer start_hour= Integer.parseInt(request.getParameter("start_hour"));
			Integer end_hour= Integer.parseInt(request.getParameter("end_hour"));
			Integer start_minute= Integer.parseInt(request.getParameter("start_minute"));
			Integer end_minute= Integer.parseInt(request.getParameter("end_minute"));
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= response.getWriter();
			
			//HH:mm>HH:mm
			if(start_hour<end_hour){
				out.print("true");
				System.out.println("��ʽtrue:"+start_hour+"<"+end_hour);
			}else if ((start_hour.equals(end_hour))&&(start_minute<end_minute)) {
				out.print("true");
				System.out.println("��ʽtrue:"+start_minute+"<"+end_minute);
			}else {
				out.print("false");
				System.out.println("��ʽfalse:"+start_hour+start_minute+"   "+end_hour+end_minute);
			}
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}