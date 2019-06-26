package com.as.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Department;
import com.as.entity.Overtimeapplication;
import com.as.entity.Staff;
import com.as.entity.Temporaryovertime;
import com.as.mapping.DepartmentDao;
import com.as.mapping.OvertimeapplicationDao;
import com.as.mapping.TemporaryovertimeDao;
import com.as.service.DepartmentService;
import com.as.service.StaffService;
import com.as.service.impl.DepartmentServiceImpl;
import com.as.service.impl.StaffServiceImpl;

/**
 * 测试类
 * @author dzz
 * 2019年6月1日 下午9:26:48
 * AttendanceSystem
 */
public class Test {
	
	//获得Session会话
	public static SqlSession getSession(){
		try {
			//构建器
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			//读取配置文件
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			//获取会话工厂
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			//获取session
			SqlSession session = sqlFactory.openSession();
			
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//部门表的各种sql操作
	public static void testDepartment(){
		try {
//			//获得session
//			SqlSession session = getSession();
//			
//			//获取代理对象
//			DepartmentDao depDao = session.getMapper(DepartmentDao.class);
//			Department department = depDao.findDepById(1001);
//			System.out.println(department.getDep_name());
//			
//			
//			session.close();
			
			DepartmentService departmentService = new DepartmentServiceImpl();
			//测试查询所有部门的方法
			List<Department> departmentList = departmentService.selectAllDepartment();
			for (Department department : departmentList) {
				System.out.println("部门id"+department.getDep_id());
				System.out.println("部门名称"+department.getDep_name());
			}
			
			//测试通过id查找部门
			Department findDep = departmentService.findDepById(1001);
			System.out.println("测试id的————————————————");
			System.out.println(findDep.getDep_id()+findDep.getDep_name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//临时加班表的各种sql操作
	public static void testTemporaryovertime(){
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
//			//1.测试查找方法
//			Temporaryovertime tmpOvertime = tempOvertimeDao.findTempOvertimeByToid(2);
//			System.out.println(tmpOvertime.getT_overtime_start() + "我是分隔符"+
//					tmpOvertime.getT_overtime_end()+"分隔符"+tmpOvertime.getT_o_reason());
//			
//			
//			//2.测试更新方法
//			//更新临时加班项
//			HashMap<String, Object> toMap = new HashMap<String, Object>();
//			toMap.put("to_id", 1);
//			toMap.put("t_o_reason", "测试时间的修改方法");
//			
//			//测试日期显示
//			Date nowDate = new Date();
//			System.out.println(nowDate);//显示一下当前时间看看什么样
//			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String currentDate = sdf.format(nowDate);
//			Timestamp sqlDate = new Timestamp(sdf.parse(currentDate).getTime());
//			
//			toMap.put("t_overtime_end", sqlDate);
//			//调用更新方法
//			tempOvertimeDao.updateTempovertimeByToid(toMap);
//			
//			
//			//3.测试新增方法
//			HashMap<String, Object> insertMap = new HashMap<String, Object>();
//			String startTime = "2019-02-02 17:00:00";
//			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
//			insertMap.put("t_overtime_start", sqlStart);
//			String endTime = "2019-02-02 18:00:00";
//			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
//			insertMap.put("t_overtime_end", sqlEnd);
//			insertMap.put("t_o_reason", "测试新增方法");
//			//调用新增方法
//			tempOvertimeDao.insertTempOvertime(insertMap);
//			
//			
//			//4.获得所有记录项（测试返回项是List时应该怎么用
//			List<Temporaryovertime> tempList = tempOvertimeDao.selectAllTempOvertime();
//			for (Temporaryovertime tmpOt : tempList) {
//				System.out.println("id："+tmpOt.getTo_id());
//				System.out.println("开始时间："+tmpOt.getT_overtime_start());
//				System.out.println("结束时间："+tmpOt.getT_overtime_end());
//				System.out.println("发起原因："+tmpOt.getT_o_reason());
//			}
			
			
			//5.测试选择当前日期可选的临时加班记录
			HashMap<String, Object> selectMap = new HashMap<String, Object>();
			
			Date nowDateNow = new Date();
			String nowDateStr = sdf.format(nowDateNow);
			Timestamp nowDateSql = new Timestamp(sdf.parse(nowDateStr).getTime());
			selectMap.put("t_overtime_end", nowDateSql);
			
			//调用查询方法
			List<Temporaryovertime> tmpListNow = tempOvertimeDao.selectTmpOvertimeByNowDate(selectMap);
			for (Temporaryovertime temporaryovertime : tmpListNow) {
				System.out.println("id："+temporaryovertime.getTo_id());
				System.out.println("开始时间："+temporaryovertime.getT_overtime_start());
				System.out.println("结束时间："+temporaryovertime.getT_overtime_end());
				System.out.println("发起原因："+temporaryovertime.getT_o_reason());
			}
			
			//手动提交事务
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//临时加班表的各种sql操作
	public static void testOvertimeapplication(){
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeApplyDao = session.getMapper(OvertimeapplicationDao.class);
					
			//1.测试查找方法
			Overtimeapplication overtimeApply = overtimeApplyDao.findOvertimeApplyByOaid(1);
			System.out.println(overtimeApply.getOvertime_start() + "分隔符1"+
					overtimeApply.getOvertime_end() + "分隔符2" + 
					overtimeApply.getOvertime_reason() + "分隔符3"+overtimeApply.getIs_approved());
			
			
			//2.测试更新方法
			//更新加班申请
			HashMap<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("oa_id", 1);
			updateMap.put("overtime_reason", "测试修改方法");
			updateMap.put("is_approved",1);
			
			//测试日期显示
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = "2019-04-01 14:30:00";
			Timestamp sqlDate = new Timestamp(sdf.parse(currentDate).getTime());
			
			updateMap.put("overtime_start", sqlDate);
			//调用更新方法
			overtimeApplyDao.updateOvertimeApplyByOaid(updateMap);
			
			
			//3.测试新增方法
			HashMap<String, Object> insertMap = new HashMap<String, Object>();
			String startTime = "2019-04-05 17:00:00";
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMap.put("overtime_start", sqlStart);
			String endTime = "2019-04-05 18:00:00";
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMap.put("overtime_end", sqlEnd);
			insertMap.put("overtime_reason", "测试新增方法2");
			insertMap.put("is_approved",0);
			insertMap.put("s_id",101);
			//调用新增方法
			overtimeApplyDao.insertOvertimeApply(insertMap);
			
			
			//4.获得所有记录项（测试返回项是List时应该怎么用
			List<Overtimeapplication> overtimeApplyList = overtimeApplyDao.selectAllOvertimeApplyBySid(101);
			for (Overtimeapplication oApply : overtimeApplyList) {
				System.out.println("id："+oApply.getOa_id());
				System.out.println("sid："+oApply.getS_id());
				System.out.println("开始时间："+oApply.getOvertime_start());
				System.out.println("结束时间："+oApply.getOvertime_end());
				System.out.println("申请加班原因："+oApply.getOvertime_reason());
				System.out.print("是否被批准：");
				if(oApply.getIs_approved() == 0){
					System.out.println("还未被审批");
				}else if(oApply.getIs_approved() == 1){
					System.out.println("申请已通过");
				}else{
					System.out.println("申请被拒绝");
				}
			}
			
			
			//5.测试删除的方法
			overtimeApplyDao.deleteOvertimeApplyByOaid(5);
			
			
			//手动提交事务
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testCalendar(){
		//这俩应该是页面传来的值
		int year = 2019;
		int month = 2;
		//默认的
		int date = 1;
		
		//这一个月
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
		
		calendar1.add(calendar1.MONTH, -1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));
		
		//下一个月
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);
		
		calendar2.set(calendar2.DAY_OF_MONTH, calendar2.getActualMinimum(calendar2.DAY_OF_MONTH));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(calendar1.getTime());
		String nextDate = sdf.format(calendar2.getTime());
		
		System.out.println(nowDate);
		System.out.println(nextDate);
	}
	
	public static void testStaff(){
		//获取service对象
		StaffService staffService = new StaffServiceImpl();
		
		//测试获得depid的方法
		Integer sId = 101;
		
		//调用
		Integer depId = staffService.findDepidBySid(sId);
		
		System.out.println(depId);
		
		//测试通过部门id查找该部门所有员工
		List<Staff> staffList = staffService.selectStaffByDepId(1001);
		for (Staff staff : staffList) {
			System.out.println("员工id："+staff.getS_id());
			System.out.println("员工姓名："+staff.getS_name());
		}
	}
	
	public static void testTime(){
		Date todayDate = new Date();
		SimpleDateFormat sdf_overtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now_date = sdf_overtime.format(todayDate);
		
		Calendar calendar_overtime = new GregorianCalendar();
		calendar_overtime.setTime(todayDate);
		calendar_overtime.add(Calendar.DATE, 1);
		Date nextDate = calendar_overtime.getTime();
		System.out.println(nextDate);
		
		SimpleDateFormat sdf_riqi = new SimpleDateFormat("yyyy-MM-dd");
		String next_date = sdf_riqi.format(nextDate);
		next_date += " 00:00:00";
		System.out.println(next_date);
	}
	
	public static void testTime2(){
		Integer year = 2019;
		Integer month = 12;
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 0);
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//获得这一年这个月一共有多少天
		System.out.println("这个月有："+day+"天");
		
		int out_i = 1;
		int inner_i = 1;
		int index = 0;
		
		//得到该月第一天和下个月第一天
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar month_begin_cal = Calendar.getInstance();
		System.out.println("看看刚获得的实例是啥样："+month_begin_cal.getTime());
		month_begin_cal.set(year, month-1, 1);
		System.out.println("set之后呢："+month_begin_cal.getTime());
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//该月第一天的String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//月份+1
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//下个月第一天的String
		
		System.out.println("该月第一天："+begin_date_str+"，下个月第一天："+end_date_str);
	}
	
	public static String getDateStrByYearMonthDay(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);	//设置日期
		//进行格式化
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		String format_date = sdf_date.format(cal.getTime());
		System.out.println(format_date);
		return format_date;
	}
	
	public static void testTime3(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);	//设置日期
		
		System.out.println(cal.getTime());
		
		Integer weekDay = cal.get(Calendar.DAY_OF_WEEK);
		System.out.println("weekDay="+weekDay);
		
		//return weekDay;
	}
	
	public static void testTime4(){		
		
		Calendar cal = Calendar.getInstance();
		
		//默认当前年
		Integer year = cal.get(Calendar.YEAR);
		//默认当前月
		Integer month = cal.get(Calendar.MONTH)+1;
		
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
		
		System.out.println("该月第一天："+begin_date_str_detail+"，下个月第一天："+end_date_str_detail);
		
	}
	
	
	public static void main(String[] args) {
		//测试department
		//testDepartment();

		//测试Temporaryovertime
		//testTemporaryovertime();
		
		//测试Overtimeapplication
		//testOvertimeapplication();
		
		//测试日历（这一个月、下一个月的第一天的日期）
		//testCalendar();
		
		//testStaff();
		
		//testTime();
		
		//获得这个年月的该月第一天和下个月第一天
		//testTime2();
		
		//将零散的属性拼接成一个格式化的日期
		//getDateStrByYearMonthDay(2019,1,32);
		
		//获得是星期几
		//testTime3(2019,7,1);
		
		testTime4();
	}
}