package com.as.service.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.ShiftWork;
import com.as.mapping.ShiftWorkDao;
import com.as.mapping.TemporaryovertimeDao;
import com.as.service.ShiftWorkService;

public class ShiftWorkServiceImpl implements ShiftWorkService{
	
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
	public int insertShiftWork(HashMap<String, Object> iswMap) {
		// TODO Auto-generated method stub
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftworkDao = session.getMapper(ShiftWorkDao.class);
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//��ò���
			Integer depId = 1001;
			if(iswMap.get("dep_id")!=null) {
				depId = Integer.parseInt(iswMap.get("dep_id").toString());
			}
			
			Integer sId = 1001001;
			if(iswMap.get("s_id")!= null) {
				sId = Integer.parseInt(iswMap.get("s_id").toString());
			}
			
			String workStart = "2019-01-01 17:00:00";
			if(iswMap.get("work_start") != null){
				workStart = iswMap.get("work_start").toString();
			}
			
			String workEnd = "2019-01-01 17:00:00";
			if(iswMap.get("work_end") != null){
				workEnd = iswMap.get("work_end").toString();
			}
			
			System.out.println("--"+depId+"--"+sId+"--"+workStart+"--"+workEnd+"--");

			//��������
			HashMap<String, Object> insertMap = new HashMap<String, Object>();
			
			insertMap.put("dep_id", depId);
			
			insertMap.put("s_id", sId);
			
			Timestamp sqlswStart = new Timestamp(sdf.parse(workStart).getTime());
			insertMap.put("working_start", sqlswStart);
			
			Timestamp sqlswEnd = new Timestamp(sdf.parse(workEnd).getTime());
			insertMap.put("working_end", sqlswEnd);
			
			//������������
			shiftworkDao.insertShiftWork(insertMap);
			
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
	public void deleteShiftWork(Integer sw_id) {
		SqlSession session = getSession();
		//��ȡ�������
		ShiftWorkDao shiftworkDao = session.getMapper(ShiftWorkDao.class);
		
		shiftworkDao.deleteShiftWork(sw_id);
		System.out.println("service.delete:�ɹ�");
		
		session.commit();
		session.close();
	}

	@Override
	public void updateShiftWork(HashMap<String, Object> uswMap) {
		// TODO Auto-generated method stub
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftworkDao = session.getMapper(ShiftWorkDao.class);
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			HashMap<String, Object> updateMap = new HashMap<String, Object>();
			
			//��ò���
			Integer swId = 1;
			if (uswMap.get("sw_id")!=null) {
				swId = (Integer)uswMap.get("sw_id");
			} 
			updateMap.put("sw_id", swId);
			
			Integer depId = 1001;
			if(uswMap.get("dep_id")!=null) {
				depId = (Integer)uswMap.get("dep_id");
				updateMap.put("dep_id", depId);
			}
			
			Integer sId = 1001001;
			if(uswMap.get("s_id")!= null) {
				sId = (Integer)uswMap.get("s_id");
				updateMap.put("s_id", sId);
			}
			
			String workStart = "2019-01-01 17:00:00";
			if(uswMap.get("work_start") != null){
				workStart = (String)uswMap.get("work_start");
				updateMap.put("working_start", workStart);
			}
			
			String workEnd = "2019-01-01 17:00:00";
			if(uswMap.get("work_end") != null){
				workEnd = (String)uswMap.get("work_end");
				updateMap.put("working_end", workEnd);
			}
			
			
			Integer getAttenStatus = 0;
			if (uswMap.get("attendence_status")!=null) {
				getAttenStatus = (Integer)uswMap.get("attendence_status");
				updateMap.put("attendence_status", getAttenStatus);
			}
				
			System.out.println("--"+depId+"--"+sId+"--"+workStart+"--"+workEnd+"--");

			
			
			//������������
			shiftworkDao.updateShiftWork(updateMap);
			
			//�ֶ��ύ����
			session.commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// ���²���ĳ��������Ա����������
	@Override
	public List<ShiftWork> selectAllStaffWorkConByMonthByDep(HashMap<String, Object> sallMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
		
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//��ò���
			Integer depId = 1001;
			if(sallMap.get("dep_id")!=null) {
				depId = Integer.parseInt(sallMap.get("dep_id").toString());
			}
			
			String workStart = "2019-01-01 08:00:00";
			if(sallMap.get("working_start_start") != null){
				workStart = (String)sallMap.get("working_start_start")+" 00:00:00";
			}
			
			String workEnd = "2019-01-01 17:00:00";
			if(sallMap.get("working_start_end") != null){
				workEnd = (String)sallMap.get("working_start_end")+" 00:00:00";
			}
			
			//����Dao���Map
			HashMap<String, Object> selectAllMap= new HashMap<String,Object>();
	
			selectAllMap.put("dep_id", depId);
			
			Timestamp sqlswStart = new Timestamp(sdf.parse(workStart).getTime());
			selectAllMap.put("working_start_start", sqlswStart);
			
			Timestamp sqlswEnd = new Timestamp(sdf.parse(workEnd).getTime());
			selectAllMap.put("working_start_end", sqlswEnd);
			
			System.out.println("selectAllMap"+depId+" "+workStart+" "+workEnd+"\n");
			//���ҷ���
			List<ShiftWork> swList = shiftWorkDao.selectAllStaffWorkConByMonthByDep(selectAllMap);
			
			session.close();
			//�õ������б��controller
			return swList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ShiftWork> selectOneStaffWorkConByMonthByDep(HashMap<String, Object> soneMap) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
		
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//��ò���
			Integer sId = 1001001;
			if ((soneMap.get("s_id"))!=null) {
				sId = Integer.parseInt(soneMap.get("s_id").toString());
			}
			
			Integer depId = 1001;
			if(soneMap.get("dep_id")!=null) {
				depId = Integer.parseInt(soneMap.get("dep_id").toString());
			}
			
			String workStart = "2019-01-01 08:00:00";
			if(soneMap.get("working_start_start") != null){
				workStart = (String)soneMap.get("working_start_start")+" 00:00:00";
			}
			
			String workEnd = "2019-01-01 17:00:00";
			if(soneMap.get("working_start_end") != null){
				workEnd = (String)soneMap.get("working_start_end")+" 00:00:00";
			}
			
			//����Dao���Map
			HashMap<String, Object> selectOneMap= new HashMap<String,Object>();
			
			selectOneMap.put("s_id", sId);
			selectOneMap.put("dep_id", depId);
			
			Timestamp sqlswStart = new Timestamp(sdf.parse(workStart).getTime());
			selectOneMap.put("working_start_start", sqlswStart);
			
			Timestamp sqlswEnd = new Timestamp(sdf.parse(workEnd).getTime());
			selectOneMap.put("working_start_end", sqlswEnd);
			
			//System.out.println("selectOneMap"+depId+" "+sId+" "+workStart+" "+workEnd+"\n");
			//���ҷ���
			List<ShiftWork> swList = shiftWorkDao.selectOneStaffWorkConByMonthByDep(selectOneMap);
			
			session.close();
			//�õ������б��controller
			return swList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	/*
	//��Ա��id�鿴�����Ƿ���ϰ࿼��
	@Override
	public ShiftWork ableCheckIn(HashMap<String, Object> ciMap) {
		// TODO Auto-generated method stub
		//���session
		SqlSession session = getSession();
		//��ȡ�������
		ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
		//����controller
		String date = (String)ciMap.get("date");
		Integer s_id = Integer.parseInt(ciMap.get("s_id").toString());
		
		//ʱ���ʽ�� yyyy-MM-dd
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sqldate = sdf.format(date);
		//����dao
		HashMap<String, Object> ciMapDao = new HashMap<String,Object>();
		ciMapDao.put("date",sqldate);
		ciMapDao.put("s_id",s_id);
		
		List<ShiftWork> ciList = shiftWorkDao.ableCheckIn(ciMapDao);
		ShiftWork cisw = new ShiftWork();
		if (ciList.size()>0) {
			cisw = ciList.get(0);
		}else {
			cisw = null;
		}
		
		return cisw;
	}

	@Override
	public ShiftWork ableCheckOff(HashMap<String, Object> coMap) {
		// TODO Auto-generated method stub
		//���session
				SqlSession session = getSession();
				//��ȡ�������
				ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
				//����controller
				String date = (String)coMap.get("date");
				Integer s_id = Integer.parseInt(coMap.get("s_id").toString());
				//ʱ���ʽ�� yyyy-MM-dd
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String sqldate = sdf.format(date);
				//����dao
				HashMap<String, Object> coMapDao = new HashMap<String,Object>();
				coMapDao.put("date",date);
				coMapDao.put("s_id",s_id);
				
				List<ShiftWork> coList = shiftWorkDao.ableCheckIn(coMapDao);
				ShiftWork cosw = new ShiftWork();
				if (coList.size()>0) {
					cosw = coList.get(0);
				}else {
					cosw = null;
				}
				
				return cosw;
		return null;
	}
	*/

	@Override
	public ShiftWork getAttenStatus(HashMap<String, Object> asMap) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
			//����controller
			String date = (String)asMap.get("date");
			
			//����ĳ�ǿת��
			Integer s_id = (Integer)asMap.get("s_id");
			
			//ʱ���ʽ�� yyyy-MM-dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_date = sdf.parse(date);
			String sqldate = sdf.format(date_date);
			
			System.out.println("������impl�Ĳ������ԣ�date��"+sqldate + "---sId��"+s_id);
			
			//����dao
			HashMap<String, Object> asMapDao = new HashMap<String,Object>();
			asMapDao.put("date",sqldate);
			asMapDao.put("s_id",s_id);
			
			List<ShiftWork> asList = shiftWorkDao.getAttenStatus(asMapDao);
			ShiftWork assw = new ShiftWork();
			if (asList.size()>0) {
				assw = asList.get(0);
			}else {
				assw = null;
			}
			
			session.close();
			
			return assw;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ShiftWork findShiftWorkByswid(Integer sw_id) {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			ShiftWorkDao shiftWorkDao = session.getMapper(ShiftWorkDao.class);
			
			//���ò�ѯ����
			ShiftWork shiftWork = shiftWorkDao.findShiftWorkByswid(sw_id);

			session.close();
			
			return shiftWork;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}