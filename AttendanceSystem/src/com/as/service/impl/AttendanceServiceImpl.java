package com.as.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Attendance;
import com.as.mapping.AttendanceDao;
import com.as.service.AttendanceService;

public class AttendanceServiceImpl implements AttendanceService {

	public static SqlSession getSession() {
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
	
	@Override
	public Attendance findAttByAttId(Integer at_id) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			
			Attendance attendance = attDao.findAttByAttId(at_id);
			System.out.println("Ա��s_id:"+attendance.getS_id());
			
			session.close();
			return attendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Attendance> findAttById(Integer s_id) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
		
			List<Attendance> attendance = attDao.findAttById(s_id);
		
			session.close();
			return attendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Attendance> findAttByDepId(Integer dep_id) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������		
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			
			List<Attendance> attendance = attDao.findAttByDepId(dep_id);
			for(Attendance attOt : attendance) {
				System.out.println("���id��"+attOt.getAt_id());
			}
		
			session.close();
			return attendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Attendance> findAttByMonth(HashMap<String, Object> dateMap) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������		
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			//����ת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			// ��ȡ�������
			//����ĳһ�·ݽ��в���
			String startDate = "2019-05-01 00:00:00";
			String endDate = "2019-06-01 00:00:00";
			Integer depId = 1001;
			System.out.println("dep_id="+depId);
//			String temp = startDate.split(" ")[0];
//			String month = temp.split("-")[1];
//			Integer month2 = Integer.parseInt(month);
//			String mm = "";
//			if(month2<12&&month2>8) mm = month2+1 +"";
//			else if(month2 == 12) mm = "01";
//			else mm = "0"+(month2+1);
//			String endTime = temp.split("-")[0]+"-"+mm+"-"+temp.split("-")[2]+" "+startDate.split(" ")[1];
//			System.out.println(endTime);
//			
//			StringBuilder startDateBuilder = new StringBuilder(startDate);
			if(dateMap.get("firstDate")!= null) {
				startDate = (String)dateMap.get("firstDate");
			}
			if(dateMap.get("lastDate")!= null) {
				endDate = (String)dateMap.get("lastDate");
			}		
			if(dateMap.get("depId")!= null) {
				depId = (Integer) dateMap.get("depId");
			}	
		
			Timestamp sqlStart = new Timestamp(sdf.parse(startDate).getTime());
			dateMap.put("firstDate", sqlStart);
			
			Timestamp sqlEnd = new Timestamp(sdf.parse(endDate).getTime());
			dateMap.put("lastDate", sqlEnd);
		
			List<Attendance> attendance = attDao.fintAttByMonth(dateMap);

			session.close();
			return attendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Attendance> findAttBySidAndDate(HashMap<String, Object> attMap) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������		
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			
			//����ת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			// ��ȡ�������
			//����ĳһ�·ݽ��в���
			String startDate = "2019-05-01 00:00:00";
			String endDate = "2019-06-01 00:00:00";
			Integer sId = 9;
			
			if(attMap.get("firstDate")!= null) {
				startDate = (String)attMap.get("firstDate");
			}
			if(attMap.get("lastDate")!= null) {
				endDate = (String)attMap.get("lastDate");
			}		
			if(attMap.get("sId")!= null) {
				sId = (Integer) attMap.get("sId");	
			}	
			
			HashMap<String,Object> dateMap = new HashMap<String,Object>();
			
			Timestamp sqlStart = new Timestamp(sdf.parse(startDate).getTime());
			dateMap.put("firstDate", sqlStart);
			
			Timestamp sqlEnd = new Timestamp(sdf.parse(endDate).getTime());
			dateMap.put("lastDate", sqlEnd);
			dateMap.put("s_id", sId);
			List<Attendance> attendance = attDao.findAttBySidAndDate(dateMap);
			

			session.close();
			return attendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
	}


	@Override
	public int updateClockoffStatus(HashMap<String, Object> toMap) {
		try {
			SqlSession session = getSession();
			// ��ȡ�������		
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			
			//����ת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			HashMap<String, Object> updateClockMap = new HashMap<String, Object>();
			
			//��ò���
			//����ĳһ�·ݽ��в���
			String endDate = "2019-06-01 00:00:00";
			Integer at_id = 1;
			Integer attendance_status = 1;
			
			//�°��ʱ��
			if(toMap.get("clock_off")!= null) {
				endDate = (String)toMap.get("clock_off");
				updateClockMap.put("clock_off", endDate);
			}
			//����״̬���Ƿ����ˣ�
			if(toMap.get("attendance_status")!= null) {
				attendance_status = (Integer) toMap.get("attendance_status");
				updateClockMap.put("attendance_status", attendance_status);
			}
			//���ڼ�¼id
			if(toMap.get("at_id") != null){
				at_id = (Integer)toMap.get("at_id");
			}
			updateClockMap.put("at_id", at_id);
			
			//�����޸ĵķ���
			attDao.updateAttByDateAndSid(updateClockMap);
			
			session.commit();
			session.close();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	@Override
	public int updateAttendanceByAtid(HashMap<String, Object> clockoffMap) {
		try {
			SqlSession session = getSession();
			// ��ȡ�������		
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			attDao.updateAttendanceByAtid(clockoffMap);
			
			session.commit();
			session.close();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertNewAtt(HashMap<String, Object> atMap) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			//ʱ��ת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			//��ò���		
//			Date nowDate = new Date();
//			String firstDate = sdf.format(nowDate);
			
			Integer sId = 1;
			if(atMap.get("s_id") != null) {
				sId = (Integer)atMap.get("s_id");
			}
			Integer depId = 1001;
			if(atMap.get("dep_id") != null) {
				depId = (Integer)atMap.get("dep_id");
			}
			Integer attendanceStatus = 1;
			if(atMap.get("attendance_status") != null) {
				attendanceStatus = (Integer)atMap.get("attendance_status");
			}
			String clockIn = "2019-01-01 00:00:00";
			if(atMap.get("clock_in") != null) {
				clockIn = (String) atMap.get("clock_in");
			}
			Integer isOvertime = 0;
//			System.out.println("is_overtime:"+atMap.get("is_overtime"));
			if(atMap.get("is_overtime") != null) {
				isOvertime = (Integer) atMap.get("is_overtime");
			}
			Integer recordId = 0;

//			System.out.println("record:"+recordId);
			if(atMap.get("record_id") != null) {
				recordId = (Integer) atMap.get("record_id");
			}
			

			
//			insertMap.put("s_id", 1);
//			insertMap.put("dep_id",1001);
//			
//			Timestamp startTime = new Timestamp(sdf.parse(firstDate).getTime());
			HashMap<String,Object> insertMap = new HashMap<String,Object>();
			Timestamp sqlStart = new Timestamp(sdf.parse(clockIn).getTime());
			insertMap.put("s_id", sId);
			insertMap.put("dep_id", depId);
			insertMap.put("clock_in", sqlStart);
			insertMap.put("attendance_status", attendanceStatus);
			insertMap.put("is_overtime",isOvertime);
			insertMap.put("record_id", recordId);
			attDao.insertNewAtt(insertMap);
			System.out.println("is_overtime:"+isOvertime);
			System.out.println("record:"+recordId);
			
			session.commit();
			session.close();
			return 1;
//			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
//			Attendance attendance = attDao.findAttByAttId(1001);
//			System.out.println(attendance.getS_id());
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public int deleteAttdanceByAtid(Integer at_id) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			attDao.deleteAttdanceByAtid(at_id);
			session.commit();
			session.close();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Attendance findAtidBySidAndDate(HashMap<String, Object> atidMap) {
		return null;
	}

	@Override
	public Attendance findAttendanceByRecordId(HashMap<String, Object> selectMap) {
		try {
			//���session
			SqlSession session = getSession();
			// ��ȡ�������
			AttendanceDao attDao = session.getMapper(AttendanceDao.class);
			
			//��ò���
			Integer is_overtime = 0;
			Integer record_id = 0;
			//�Ƿ��ǼӰ� �ı�ʶ
			if(selectMap.get("is_overtime") != null){
				is_overtime = (Integer)selectMap.get("is_overtime");
			}
			//��Ӧ�ļ�¼id
			if(selectMap.get("record_id") != null){
				record_id = (Integer)selectMap.get("record_id");
			}
			//�����б�
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			selectDaoMap.put("is_overtime", is_overtime);
			selectDaoMap.put("record_id", record_id);
			
			//���ò�ѯ����
			List<Attendance> attList = attDao.findAttendanceByRecordId(selectDaoMap);
			Attendance selectAttendance = new Attendance();
			
			if(attList.size() > 0){
				selectAttendance = attList.get(0);
			}else{
				selectAttendance = null;
			}
			
			session.close();
			return selectAttendance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}