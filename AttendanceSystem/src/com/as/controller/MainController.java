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
 * 主控制器（大家都用的）
 * @author dzz
 * 2019年6月8日 上午3:30:44
 * AttendanceSystem
 */
@Controller
@RequestMapping("/main")
public class MainController {

	//依赖的Service对象
	private StaffService staffService = new StaffServiceImpl();
	private OvertimeapplicationService overtimeService = new OvertimeapplicationServiceImpl();
	
	//判断一个字符串是否都由数字组成
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
	
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
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String message = "";
		
		//获得登录的参数
		Integer sId = 0;
		if(request.getParameter("s_id") != null){	//判断是否为空值
			if( isNumeric(request.getParameter("s_id")) ){	//判断是否都是数字
				sId = Integer.parseInt(request.getParameter("s_id"));
			}else{
				message = "员工编号由数字组成！";
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
		
		//先判断是否存在该员工id
		Staff loginStaff = staffService.findStaffById(sId);
		if(loginStaff == null){
			message = "该员工编号不存在！";
			request.setAttribute("message", message);
			return "main/login";
		}
		
		//比对员工id和密码是否正确
		if( !s_pass.equals(loginStaff.getS_pass()) ){	//比对错误
			message = "密码错误！";
			request.setAttribute("message", message);
			return "main/login";
		}
		
		//获得该员工当天的工作班次列表和加班列表（经理不用！！！！！！）
//		if(loginStaff.getIdentity() != 3){
//			//获得今天的日期
//			Date todayDate = new Date();
//			SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String now_date = sdf_overtime.format(todayDate);	//当前时间
//			String next_date = getNextDate(todayDate);	//下一天的零点的日期
//			String begin_date = getBeginDate(todayDate);	//当前日期的开始时间
//			System.out.println("当前时间："+now_date+"下一天日期："+next_date
//					+"当前日期的开始："+begin_date);
//			
//			
//			//工作班次列表
//			
//			//加班列表
//			HashMap<String, Object> overtimeMap = new HashMap<String, Object>();
//			overtimeMap.put("s_id", sId);
//			overtimeMap.put("now_date", now_date);
//			overtimeMap.put("next_date", next_date);
//			overtimeMap.put("begin_date", begin_date);
//			//获得可以签到的临时加班列表
//			List<Overtimeapplication> overtimeSignInList = overtimeService.selectSignInOvertimeApply(overtimeMap);
//			//获得可以签退的临时加班列表
//			List<Overtimeapplication> overtimeSignOffList = overtimeService.selectSignOffOvertimeApply(overtimeMap);
//			//传参
//			request.setAttribute("overtimeSignInList", overtimeSignInList);
//			request.setAttribute("overtimeSignOffList", overtimeSignOffList);
//		}
		
//		System.out.println("输出路径——————————————————————————————————————");
//		System.out.println(System.getProperty("java.library.path"));
		
		//登陆成功，根据其身份判断跳转到哪个首页
		request.setAttribute("s_id", sId);
		HttpSession session = request.getSession();
		session.setAttribute("s_id", sId);
		
		if(loginStaff.getIdentity() == 3){	//是经理
			return "manager/homepage";
		}else if(loginStaff.getIdentity() == 2){	//是部门主管
			//重定向
			//return "director/homepage";
			return "redirect:/director/selectTodayWorkHomepage";
		}else{	//否则，是普通员工
			//return "staff/homepage";
			return "redirect:/staff/selectTodayWorkHomepage";
		}
	}
	
	
	//检查日期格式是否正确
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
			//先格式化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date_start_date = sdf.parse(date_start);
			Date date_end_date = sdf.parse(date_end);
			//比较先后
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
	
	//验证输入的日期是否正确，AJAX方法不能有转发或重定向等返回页面的行为
	@RequestMapping("/checkDate")
	public void checkDate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		try{
			//获取参数 
			String date_start= request.getParameter("date_start");
			String date_end= request.getParameter("date_end");
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= response.getWriter();
			
			//先判断格式是否正确
			if(!checkDateFormat(date_start) || !checkDateFormat(date_end)){
				//格式不正确
				out.print("false");
				//System.out.println("格式false");
			}else{
				//判断end的日期是否在start之后
				if(checkDateOrder(date_start, date_end)){
					out.print("true");
					//System.out.println("正确！！！！！！！！");
				}else{
					out.print("false");
					//System.out.println("先后false！1");
				}
			}
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//验证输入的日期是否正确
	@RequestMapping("/checkTime")
	public void checkTime(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		try{
			//System.out.println("test--------------");
			
			//获取参数 
			Integer start_hour= Integer.parseInt(request.getParameter("start_hour"));
			Integer end_hour= Integer.parseInt(request.getParameter("end_hour"));
			Integer start_minute= Integer.parseInt(request.getParameter("start_minute"));
			Integer end_minute= Integer.parseInt(request.getParameter("end_minute"));
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= response.getWriter();
			
			//HH:mm>HH:mm
			if(start_hour<end_hour){
				out.print("true");
				System.out.println("格式true:"+start_hour+"<"+end_hour);
			}else if ((start_hour.equals(end_hour))&&(start_minute<end_minute)) {
				out.print("true");
				System.out.println("格式true:"+start_minute+"<"+end_minute);
			}else {
				out.print("false");
				System.out.println("格式false:"+start_hour+start_minute+"   "+end_hour+end_minute);
			}
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}