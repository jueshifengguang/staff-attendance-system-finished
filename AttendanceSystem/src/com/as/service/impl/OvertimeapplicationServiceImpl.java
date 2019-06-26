package com.as.service.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Overtimeapplication;
import com.as.mapping.OvertimeapplicationDao;
import com.as.service.OvertimeapplicationService;

public class OvertimeapplicationServiceImpl implements
		OvertimeapplicationService {
	
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

	@Override
	public Overtimeapplication findOvertimeApplyByOaid(Integer oa_id) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//���ҷ���
			Overtimeapplication overtimeApply = overtimeDao.findOvertimeApplyByOaid(oa_id);

			session.close();
			
			return overtimeApply;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateOvertimeApplyByOaid(HashMap<String, Object> updateMap) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> updateMapReal = new HashMap<String, Object>();
			
			//��ò���
			Integer oaId = 0;
			if(updateMap.get("oa_id") != null){
				oaId = (Integer) updateMap.get("oa_id");
			}
//			Integer sId = 0;
//			if(updateMap.get("s_id") != null){
//				sId = (Integer) updateMap.get("s_id");
//			}
			String startTime = "2019-01-01 17:00:00";
			if(updateMap.get("overtime_start") != null){
				startTime = (String)updateMap.get("overtime_start");
				Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
				updateMapReal.put("overtime_start", sqlStart);
			}
			String endTime = "2019-01-01 18:00:00";
			if(updateMap.get("overtime_end") != null){
				endTime = (String) updateMap.get("overtime_end");
				Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
				updateMapReal.put("overtime_end", sqlEnd);
			}
			String oa_reason = "��";
			if(updateMap.get("overtime_reason") != null){
				oa_reason = (String) updateMap.get("overtime_reason");
				updateMapReal.put("overtime_reason",oa_reason);
			}
			Integer isApproved = 0;
			if(updateMap.get("is_approved") != null){
				isApproved = (Integer) updateMap.get("is_approved");
				updateMapReal.put("is_approved", isApproved);
			}
			Integer isTemporary = 0;
			if(updateMap.get("is_temporary") != null){
				isTemporary = (Integer) updateMap.get("is_temporary");
				updateMapReal.put("is_temporary", isTemporary);
			}
			String examination = "��";
			if(updateMap.get("examination") != null){
				examination = (String) updateMap.get("examination");
				updateMapReal.put("examination",examination);
			}
			Integer isSign = 0;
			if(updateMap.get("is_sign") != null){
				isSign = (Integer) updateMap.get("is_sign");
				updateMapReal.put("is_sign", isSign);
			}
			
			updateMapReal.put("oa_id", oaId);
			//updateMapReal.put("s_id", sId);
						
			//���ø��·���
			overtimeDao.updateOvertimeApplyByOaid(updateMapReal);
			
			//�ֶ��ύsession
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insertOvertimeApply(HashMap<String, Object> insertMap) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> insertMapReal = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 1;
			if(insertMap.get("s_id") != null){
				sId = (Integer) insertMap.get("s_id");
			}
			insertMapReal.put("s_id", sId);
			//��ʼʱ��
			String startTime = "2019-01-01 17:00:00";
			if(insertMap.get("overtime_start") != null){
				startTime = (String)insertMap.get("overtime_start");
			}
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMapReal.put("overtime_start", sqlStart);
			//����ʱ��
			String endTime = "2019-01-01 18:00:00";
			if(insertMap.get("overtime_end") != null){
				endTime = (String) insertMap.get("overtime_end");
			}
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMapReal.put("overtime_end", sqlEnd);
			//�Ӱ���������
			String oa_reason = "��";
			if(insertMap.get("overtime_reason") != null){
				oa_reason = (String) insertMap.get("overtime_reason");
			}
			insertMapReal.put("overtime_reason",oa_reason);
			//�Ƿ�����
			Integer isApproved = 0;
			if(insertMap.get("is_approved") != null){
				isApproved = (Integer) insertMap.get("is_approved");
			}
			insertMapReal.put("is_approved", isApproved);
			//�Ƿ�����ʱ�ԼӰ�
			Integer isTemporary = 0;
			if(insertMap.get("is_temporary") != null){
				isTemporary = (Integer) insertMap.get("is_temporary");
			}
			insertMapReal.put("is_temporary", isTemporary);
			//�������
			String examination = "��";
			if(insertMap.get("examination") != null){
				examination = (String) insertMap.get("examination");
			}
			insertMap.put("examination",examination);
			//��ʶ��λ
			Integer isSign = 0;
			if(insertMap.get("is_sign") != null){
				isSign = (Integer) insertMap.get("is_sign");
			}
			insertMapReal.put("is_sign", isSign);
			
			
			//������������
			overtimeDao.insertOvertimeApply(insertMapReal);
			
			//�ֶ��ύsession
			session.commit();
			session.close();
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Overtimeapplication> selectAllOvertimeApplyBySid(Integer s_id){
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//���ҷ���
			List<Overtimeapplication> overtimeList = overtimeDao.selectAllOvertimeApplyBySid(s_id);
			
			session.close();
			
			return overtimeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteOvertimeApplyByOaid(Integer oa_id) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//ɾ������
			overtimeDao.deleteOvertimeApplyByOaid(oa_id);
			
			//�ֶ��ύsession
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Overtimeapplication> selectNoncheckedOvertimeApplyByDep(
			Integer dep_id) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectNoncheckedOvertimeApplyByDep(dep_id);

			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Overtimeapplication> selectCheckedOvertimeApplyByDep(
			Integer dep_id) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectCheckedOvertimeApplyByDep(dep_id);

			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Overtimeapplication> selectSignInOvertimeApply(
			HashMap<String, Object> selectMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> selectSignInMap = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectSignInMap.put("s_id", sId);
			//��ǰʱ��
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectSignInMap.put("now_date", sqlNowDate);
			//��һ��Ŀ�ʼʱ��
			String begin_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectSignInMap.put("begin_date", sqlBeginDate);
			//��һ��Ľ���ʱ��
			String next_date_str = "2019-01-02 00:00:00";
			if(selectMap.get("next_date") != null){
				next_date_str = (String)selectMap.get("next_date");
			}
			Timestamp sqlNextDate = new Timestamp(sdf.parse(next_date_str).getTime());
			selectSignInMap.put("next_date", sqlNextDate);
			
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectSignInOvertimeApply(selectSignInMap);
					
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Overtimeapplication> selectSignOffOvertimeApply(
			HashMap<String, Object> selectMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> selectSignOffMap = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectSignOffMap.put("s_id", sId);
			//��ǰʱ��
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectSignOffMap.put("now_date", sqlNowDate);
			//��һ��Ŀ�ʼʱ��
			String begin_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectSignOffMap.put("begin_date", sqlBeginDate);
			//��һ��Ľ���ʱ��
			String next_date_str = "2019-01-02 00:00:00";
			if(selectMap.get("next_date") != null){
				next_date_str = (String)selectMap.get("next_date");
			}
			Timestamp sqlNextDate = new Timestamp(sdf.parse(next_date_str).getTime());
			selectSignOffMap.put("next_date", sqlNextDate);
			
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectSignOffOvertimeApply(selectSignOffMap);
					
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Overtimeapplication> selectOvertimeApplyByNowDate(
			HashMap<String, Object> selectMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> selectNowMap = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectNowMap.put("s_id", sId);
			//��ǰʱ��
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectNowMap.put("now_date", sqlNowDate);	
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectOvertimeApplyByNowDate(selectNowMap);
					
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Overtimeapplication> selectTodayOvertimeApply(HashMap<String, Object> selectMap){
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��,��ʽ�� yyyy-MM-dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = "2019-06-10";
			if(selectMap.get("date") != null){
				date = (String)selectMap.get("date");
			}
			//����ĳ�ǿת��
			Integer s_id = 101;
			if(selectMap.get("s_id") != null){
				s_id = (Integer)selectMap.get("s_id");
			}
			
			//ת����ʽ
			Date date_date = sdf.parse(date);
			String sqldate = sdf.format(date_date);
			
			System.out.println("������Overtimeimpl�Ĳ������ԣ�date��"+sqldate + "---sId��"+s_id);
			
			//����dao
			HashMap<String, Object> selectDaoMap = new HashMap<String,Object>();
			selectDaoMap.put("date",sqldate);
			selectDaoMap.put("s_id",s_id);	
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectTodayOvertimeApply(selectDaoMap);
					
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Overtimeapplication> selectOvertimeByMonth(
			HashMap<String, Object> selectMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 103;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectDaoMap.put("s_id", sId);
			
			//��ʼʱ��
			String begin_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectDaoMap.put("begin_date", sqlBeginDate);
			
			//����ʱ��
			String end_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("end_date") != null){
				end_date_str = (String)selectMap.get("end_date");
			}
			Timestamp sqlEndDate = new Timestamp(sdf.parse(end_date_str).getTime());
			selectDaoMap.put("end_date", sqlEndDate);
			
			System.out.println("������impl�ķ�����sid�ǣ�"+sId);
			System.out.println(sqlBeginDate);
			System.out.println(sqlEndDate);
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectOvertimeByMonth(selectDaoMap);
					
			session.close();
			
			System.out.println("���ﻹ��impl�������list�ĳ����ǣ�"+overtimeApplyList.size());
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Overtimeapplication> selectTmpOvertimeByMonth(
			HashMap<String, Object> selectMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			
			//��ò���
			Integer sId = 103;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectDaoMap.put("s_id", sId);
			
			//��ʼʱ��
			String begin_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectDaoMap.put("begin_date", sqlBeginDate);
			
			//����ʱ��
			String end_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("end_date") != null){
				end_date_str = (String)selectMap.get("end_date");
			}
			Timestamp sqlEndDate = new Timestamp(sdf.parse(end_date_str).getTime());
			selectDaoMap.put("end_date", sqlEndDate);
			
			//���ҵķ���
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectTmpOvertimeByMonth(selectDaoMap);
			
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}