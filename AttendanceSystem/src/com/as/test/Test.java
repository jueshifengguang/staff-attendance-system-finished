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
 * ������
 * @author dzz
 * 2019��6��1�� ����9:26:48
 * AttendanceSystem
 */
public class Test {
	
	//���Session�Ự
	public static SqlSession getSession(){
		try {
			//������
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			//��ȡ�����ļ�
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			//��ȡ�Ự����
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			//��ȡsession
			SqlSession session = sqlFactory.openSession();
			
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//���ű�ĸ���sql����
	public static void testDepartment(){
		try {
//			//���session
//			SqlSession session = getSession();
//			
//			//��ȡ�������
//			DepartmentDao depDao = session.getMapper(DepartmentDao.class);
//			Department department = depDao.findDepById(1001);
//			System.out.println(department.getDep_name());
//			
//			
//			session.close();
			
			DepartmentService departmentService = new DepartmentServiceImpl();
			//���Բ�ѯ���в��ŵķ���
			List<Department> departmentList = departmentService.selectAllDepartment();
			for (Department department : departmentList) {
				System.out.println("����id"+department.getDep_id());
				System.out.println("��������"+department.getDep_name());
			}
			
			//����ͨ��id���Ҳ���
			Department findDep = departmentService.findDepById(1001);
			System.out.println("����id�ġ�������������������������������");
			System.out.println(findDep.getDep_id()+findDep.getDep_name());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ʱ�Ӱ��ĸ���sql����
	public static void testTemporaryovertime(){
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
//			//1.���Բ��ҷ���
//			Temporaryovertime tmpOvertime = tempOvertimeDao.findTempOvertimeByToid(2);
//			System.out.println(tmpOvertime.getT_overtime_start() + "���Ƿָ���"+
//					tmpOvertime.getT_overtime_end()+"�ָ���"+tmpOvertime.getT_o_reason());
//			
//			
//			//2.���Ը��·���
//			//������ʱ�Ӱ���
//			HashMap<String, Object> toMap = new HashMap<String, Object>();
//			toMap.put("to_id", 1);
//			toMap.put("t_o_reason", "����ʱ����޸ķ���");
//			
//			//����������ʾ
//			Date nowDate = new Date();
//			System.out.println(nowDate);//��ʾһ�µ�ǰʱ�俴��ʲô��
//			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String currentDate = sdf.format(nowDate);
//			Timestamp sqlDate = new Timestamp(sdf.parse(currentDate).getTime());
//			
//			toMap.put("t_overtime_end", sqlDate);
//			//���ø��·���
//			tempOvertimeDao.updateTempovertimeByToid(toMap);
//			
//			
//			//3.������������
//			HashMap<String, Object> insertMap = new HashMap<String, Object>();
//			String startTime = "2019-02-02 17:00:00";
//			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
//			insertMap.put("t_overtime_start", sqlStart);
//			String endTime = "2019-02-02 18:00:00";
//			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
//			insertMap.put("t_overtime_end", sqlEnd);
//			insertMap.put("t_o_reason", "������������");
//			//������������
//			tempOvertimeDao.insertTempOvertime(insertMap);
//			
//			
//			//4.������м�¼����Է�������ListʱӦ����ô��
//			List<Temporaryovertime> tempList = tempOvertimeDao.selectAllTempOvertime();
//			for (Temporaryovertime tmpOt : tempList) {
//				System.out.println("id��"+tmpOt.getTo_id());
//				System.out.println("��ʼʱ�䣺"+tmpOt.getT_overtime_start());
//				System.out.println("����ʱ�䣺"+tmpOt.getT_overtime_end());
//				System.out.println("����ԭ��"+tmpOt.getT_o_reason());
//			}
			
			
			//5.����ѡ��ǰ���ڿ�ѡ����ʱ�Ӱ��¼
			HashMap<String, Object> selectMap = new HashMap<String, Object>();
			
			Date nowDateNow = new Date();
			String nowDateStr = sdf.format(nowDateNow);
			Timestamp nowDateSql = new Timestamp(sdf.parse(nowDateStr).getTime());
			selectMap.put("t_overtime_end", nowDateSql);
			
			//���ò�ѯ����
			List<Temporaryovertime> tmpListNow = tempOvertimeDao.selectTmpOvertimeByNowDate(selectMap);
			for (Temporaryovertime temporaryovertime : tmpListNow) {
				System.out.println("id��"+temporaryovertime.getTo_id());
				System.out.println("��ʼʱ�䣺"+temporaryovertime.getT_overtime_start());
				System.out.println("����ʱ�䣺"+temporaryovertime.getT_overtime_end());
				System.out.println("����ԭ��"+temporaryovertime.getT_o_reason());
			}
			
			//�ֶ��ύ����
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//��ʱ�Ӱ��ĸ���sql����
	public static void testOvertimeapplication(){
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeApplyDao = session.getMapper(OvertimeapplicationDao.class);
					
			//1.���Բ��ҷ���
			Overtimeapplication overtimeApply = overtimeApplyDao.findOvertimeApplyByOaid(1);
			System.out.println(overtimeApply.getOvertime_start() + "�ָ���1"+
					overtimeApply.getOvertime_end() + "�ָ���2" + 
					overtimeApply.getOvertime_reason() + "�ָ���3"+overtimeApply.getIs_approved());
			
			
			//2.���Ը��·���
			//���¼Ӱ�����
			HashMap<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("oa_id", 1);
			updateMap.put("overtime_reason", "�����޸ķ���");
			updateMap.put("is_approved",1);
			
			//����������ʾ
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = "2019-04-01 14:30:00";
			Timestamp sqlDate = new Timestamp(sdf.parse(currentDate).getTime());
			
			updateMap.put("overtime_start", sqlDate);
			//���ø��·���
			overtimeApplyDao.updateOvertimeApplyByOaid(updateMap);
			
			
			//3.������������
			HashMap<String, Object> insertMap = new HashMap<String, Object>();
			String startTime = "2019-04-05 17:00:00";
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMap.put("overtime_start", sqlStart);
			String endTime = "2019-04-05 18:00:00";
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMap.put("overtime_end", sqlEnd);
			insertMap.put("overtime_reason", "������������2");
			insertMap.put("is_approved",0);
			insertMap.put("s_id",101);
			//������������
			overtimeApplyDao.insertOvertimeApply(insertMap);
			
			
			//4.������м�¼����Է�������ListʱӦ����ô��
			List<Overtimeapplication> overtimeApplyList = overtimeApplyDao.selectAllOvertimeApplyBySid(101);
			for (Overtimeapplication oApply : overtimeApplyList) {
				System.out.println("id��"+oApply.getOa_id());
				System.out.println("sid��"+oApply.getS_id());
				System.out.println("��ʼʱ�䣺"+oApply.getOvertime_start());
				System.out.println("����ʱ�䣺"+oApply.getOvertime_end());
				System.out.println("����Ӱ�ԭ��"+oApply.getOvertime_reason());
				System.out.print("�Ƿ���׼��");
				if(oApply.getIs_approved() == 0){
					System.out.println("��δ������");
				}else if(oApply.getIs_approved() == 1){
					System.out.println("������ͨ��");
				}else{
					System.out.println("���뱻�ܾ�");
				}
			}
			
			
			//5.����ɾ���ķ���
			overtimeApplyDao.deleteOvertimeApplyByOaid(5);
			
			
			//�ֶ��ύ����
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testCalendar(){
		//����Ӧ����ҳ�洫����ֵ
		int year = 2019;
		int month = 2;
		//Ĭ�ϵ�
		int date = 1;
		
		//��һ����
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(year, month, date);
		
		calendar1.add(calendar1.MONTH, -1);
		calendar1.set(calendar1.DAY_OF_MONTH, calendar1.getActualMinimum(calendar1.DAY_OF_MONTH));
		
		//��һ����
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
		//��ȡservice����
		StaffService staffService = new StaffServiceImpl();
		
		//���Ի��depid�ķ���
		Integer sId = 101;
		
		//����
		Integer depId = staffService.findDepidBySid(sId);
		
		System.out.println(depId);
		
		//����ͨ������id���Ҹò�������Ա��
		List<Staff> staffList = staffService.selectStaffByDepId(1001);
		for (Staff staff : staffList) {
			System.out.println("Ա��id��"+staff.getS_id());
			System.out.println("Ա��������"+staff.getS_name());
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
		Integer day = cal.get(Calendar.DAY_OF_MONTH);	//�����һ�������һ���ж�����
		System.out.println("������У�"+day+"��");
		
		int out_i = 1;
		int inner_i = 1;
		int index = 0;
		
		//�õ����µ�һ����¸��µ�һ��
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar month_begin_cal = Calendar.getInstance();
		System.out.println("�����ջ�õ�ʵ����ɶ����"+month_begin_cal.getTime());
		month_begin_cal.set(year, month-1, 1);
		System.out.println("set֮���أ�"+month_begin_cal.getTime());
		String begin_date_str = sdf_date.format(month_begin_cal.getTime());	//���µ�һ���String
		
		Calendar month_end_cal = Calendar.getInstance();
		month_end_cal.set(year, month, 1);
		//month_end_cal.add(Calendar.MONTH, 1);//�·�+1
		String end_date_str = sdf_date.format(month_end_cal.getTime());	//�¸��µ�һ���String
		
		System.out.println("���µ�һ�죺"+begin_date_str+"���¸��µ�һ�죺"+end_date_str);
	}
	
	public static String getDateStrByYearMonthDay(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);	//��������
		//���и�ʽ��
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		String format_date = sdf_date.format(cal.getTime());
		System.out.println(format_date);
		return format_date;
	}
	
	public static void testTime3(Integer year, Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);	//��������
		
		System.out.println(cal.getTime());
		
		Integer weekDay = cal.get(Calendar.DAY_OF_WEEK);
		System.out.println("weekDay="+weekDay);
		
		//return weekDay;
	}
	
	public static void testTime4(){		
		
		Calendar cal = Calendar.getInstance();
		
		//Ĭ�ϵ�ǰ��
		Integer year = cal.get(Calendar.YEAR);
		//Ĭ�ϵ�ǰ��
		Integer month = cal.get(Calendar.MONTH)+1;
		
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
		
		System.out.println("���µ�һ�죺"+begin_date_str_detail+"���¸��µ�һ�죺"+end_date_str_detail);
		
	}
	
	
	public static void main(String[] args) {
		//����department
		//testDepartment();

		//����Temporaryovertime
		//testTemporaryovertime();
		
		//����Overtimeapplication
		//testOvertimeapplication();
		
		//������������һ���¡���һ���µĵ�һ������ڣ�
		//testCalendar();
		
		//testStaff();
		
		//testTime();
		
		//���������µĸ��µ�һ����¸��µ�һ��
		//testTime2();
		
		//����ɢ������ƴ�ӳ�һ����ʽ��������
		//getDateStrByYearMonthDay(2019,1,32);
		
		//��������ڼ�
		//testTime3(2019,7,1);
		
		testTime4();
	}
}