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

import com.as.entity.Temporaryovertime;
import com.as.mapping.TemporaryovertimeDao;
import com.as.service.TemporaryovertimeService;

public class TemporaryovertimeServiceImpl implements TemporaryovertimeService {
	
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
	public Temporaryovertime findTempOvertimeByToid(Integer to_id) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//���ҷ���
			Temporaryovertime tmpOvertime = tempOvertimeDao.findTempOvertimeByToid(to_id);
			System.out.println(tmpOvertime.getT_overtime_start() + "���Ƿָ���"+
					tmpOvertime.getT_overtime_end()+"�ָ���"+tmpOvertime.getT_o_reason());
			
			session.close();
			
			return tmpOvertime;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateTempovertimeByToid(HashMap<String, Object> toMap) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> updateMap = new HashMap<String, Object>();
			
			//��ò���
			Integer toId = 0;
			if(toMap.get("to_id") != null){
				toId = Integer.parseInt((String) toMap.get("to_id"));
			}
			String startTime = "2019-01-01 17:00:00";
			if(toMap.get("t_overtime_start") != null){
				startTime = (String)toMap.get("t_overtime_start");
				Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
				updateMap.put("t_overtime_start", sqlStart);
			}
			String endTime = "2019-01-01 18:00:00";
			if(toMap.get("t_overtime_end") != null){
				endTime = (String) toMap.get("t_overtime_end");
				Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
				updateMap.put("t_overtime_end", sqlEnd);
			}
			String to_reason = "��";
			if(toMap.get("t_o_reason") != null){
				to_reason = (String) toMap.get("t_o_reason");
				updateMap.put("t_o_reason",to_reason);
			}
			
			
			updateMap.put("to_id", toId);
			
			//���ø��·���
			tempOvertimeDao.updateTempovertimeByToid(updateMap);
			
			//�ֶ��ύ����
			session.commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public int insertTempOvertime(HashMap<String, Object> toMap) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//��ò���
			String startTime = "2019-01-01 17:00:00";
			if(toMap.get("t_overtime_start") != null){
				startTime = (String)toMap.get("t_overtime_start");
			}
			String endTime = "2019-01-01 18:00:00";
			if(toMap.get("t_overtime_end") != null){
				endTime = (String) toMap.get("t_overtime_end");
			}
			String to_reason = "��";
			if(toMap.get("t_o_reason") != null){
				to_reason = (String) toMap.get("t_o_reason");
			}
			
			//��������
			HashMap<String, Object> insertMap = new HashMap<String, Object>();
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMap.put("t_overtime_start", sqlStart);
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMap.put("t_overtime_end", sqlEnd);
			insertMap.put("t_o_reason",to_reason);
			//������������
			tempOvertimeDao.insertTempOvertime(insertMap);
			
			//�ֶ��ύ����
			session.commit();
			session.close();
			
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Temporaryovertime> selectAllTempOvertime() {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//���ҷ���
			List<Temporaryovertime> tempList = tempOvertimeDao.selectAllTempOvertime();
			
			session.close();
			
			return tempList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Temporaryovertime> selectTmpOvertimeByNowDate() {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//�����б�
			HashMap<String, Object> selectMap = new HashMap<String, Object>();
			
			//��ȡ��ǰʱ��
			Date nowDateNow = new Date();
			String nowDateStr = sdf.format(nowDateNow);
			Timestamp nowDateSql = new Timestamp(sdf.parse(nowDateStr).getTime());
			selectMap.put("t_overtime_start", nowDateSql);
			
			//���ò�ѯ����
			List<Temporaryovertime> tmpListNow = tempOvertimeDao.selectTmpOvertimeByNowDate(selectMap);
					
			session.close();
			
			return tmpListNow;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteTempovertimeByToid(Integer to_id) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//����ɾ������
			tempOvertimeDao.deleteTempovertimeByToid(to_id);
			
			//�ֶ��ύsession
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}