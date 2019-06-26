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

import com.as.entity.Askforleave;
import com.as.mapping.AskforleaveDao;
import com.as.service.AskforleaveService;

public class AskforleaveServiceImpl implements AskforleaveService {

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
	public Askforleave findAskforApplyByAflid(Integer afl_id) {
		try {
			//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
		
			//1.����ͨ����ٱ�id����
			Askforleave  aflApplyByAflid = askforApplyDao.findAskforApplyByAflid(afl_id);
			/*System.out.println(aflApplyByAflid.getS_id() + "   " + 
				aflApplyByAflid.getStarting_date() + "   " + 
				aflApplyByAflid.getEnding_date() + "   " +
				aflApplyByAflid.getLeave_reason() + "   " + 
				aflApplyByAflid.getIs_approved() +"   " + aflApplyByAflid.getIs_resumed());
		*/
			session.close();
		
			return aflApplyByAflid;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Askforleave findAskforApplyBySid(Integer s_id) {
		try {
			//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			// TODO Auto-generated method stub
			Askforleave  aflApplyBySid = askforApplyDao.findAskforApplyBySid(s_id);
			/*System.out.println(aflApplyBySid.getS_id() + "   " + 
					aflApplyBySid.getStarting_date() + "   " + 
					aflApplyBySid.getEnding_date() + "   " +
					aflApplyBySid.getLeave_reason() + "   " + 
					aflApplyBySid.getIs_approved() +"   " + aflApplyBySid.getIs_resumed());
					*/
			session.close();
			
			return aflApplyBySid;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
			}
		}

	@Override
	public int insertNewAskforApply(HashMap<String, Object> insertMap) {
		try{
			//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			
			// TODO Auto-generated method stub
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//��ò���
			Integer sId = 1;
			if(insertMap.get("s_id") != null) {
				sId = (Integer)insertMap.get("s_id");
			}	
			
			String startDate = "2019-01-01 17:00:00";
			if(insertMap.get("starting_date") != null) {
				startDate = (String)insertMap.get("starting_date");
			}
			
			String endDate = "2019-01-01 18:00:00";
			if(insertMap.get("ending_date") != null) {
				endDate = (String)insertMap.get("ending_date");
			}
			
			String leaveReason = "��";
			if(insertMap.get("leave_reason") != null) {
				leaveReason = (String)insertMap.get("leave_reason");
			}
			
			String approvedReason = "��";
			if(insertMap.get("approved_reason") != null) {
				approvedReason = (String)insertMap.get("approved_reason");
			}	
			Integer isApproved =0;
			if(insertMap.get("is_approved") != null) {
				isApproved = (Integer)insertMap.get("is_approved");
			}
			
			Integer isResumed = 0;
			if(insertMap.get("is_resumed") != null) {
				isResumed = (Integer)insertMap.get("is_resumed");
			}
			
			Integer leaveType=0;
			if(insertMap.get("leave_type") != null) {
				leaveType = (Integer)insertMap.get("leave_type");
			}
			//System.out.println(sId+isApproved+isResumed+startDate+endDate+leaveReason);
			
			//��������
			HashMap<String,Object> toInsert = new HashMap<String,Object>();
			Timestamp sqlStart = new Timestamp(sdf.parse(startDate).getTime());
			toInsert.put("starting_date", sqlStart);
			Timestamp sqlEnd = new Timestamp(sdf.parse(endDate).getTime());
			toInsert.put("ending_date", sqlEnd);
			toInsert.put("leave_reason", leaveReason);
			toInsert.put("approved_reason", approvedReason);
			toInsert.put("is_approved",isApproved);
			toInsert.put("is_resumed",isResumed);
			toInsert.put("s_id",sId);
			toInsert.put("leave_type", leaveType);
			//������������
			askforApplyDao.insertNewAskforApply(toInsert);
			
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
	public void updateAskApplyforByAflid(HashMap<String, Object> updateMap) {
		try {
			//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			
			// TODO Auto-generated method stub
			
			//��ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//���·���
			HashMap<String, Object> toUpdate = new HashMap<String, Object>();
			
			//��ȡ����
			Integer aflId=0;
			if(updateMap.get("afl_id") != null) {
				aflId = (Integer)updateMap.get("afl_id");				
			}
			
			//System.out.println("service"+aflId);
			
			Integer sId = 1;
			if(updateMap.get("s_id") != null) {
				sId = (Integer)updateMap.get("sId");
				toUpdate.put("s_id", sId);
				
			}	
			
			String startDate = "2019-01-01 17:00:00";
			if(updateMap.get("starting_date") != null) {
				startDate = (String)updateMap.get("starting_date");
				Timestamp sqlStart = new Timestamp(sdf.parse(startDate).getTime());
				toUpdate.put("starting_date", sqlStart);
			}
			
			String endDate = "2019-01-01 18:00:00";
			if(updateMap.get("ending_date") != null) {
				endDate = (String)updateMap.get("ending_date");
				Timestamp sqlEnd = new Timestamp(sdf.parse(endDate).getTime());
				toUpdate.put("ending_date", sqlEnd);
			}
			
			String leaveReason = "��";
			if(updateMap.get("leave_reason") != null) {
				leaveReason = (String)updateMap.get("leave_reason");
				toUpdate.put("leave_reason", leaveReason);
			}	
			
			String approvedReason = "��";
			if(updateMap.get("approved_reason") != null) {
				approvedReason = (String)updateMap.get("approved_reason");
				toUpdate.put("approved_reason", approvedReason);
			}	
			
			Integer isApproved =0;
			if(updateMap.get("is_approved") != null) {
				isApproved = (Integer)updateMap.get("is_approved");
				toUpdate.put("is_approved", isApproved);
			}
			
			Integer leaveType=0;
			if(updateMap.get("leave_type") != null) {
				leaveType = (Integer)updateMap.get("leave_type");
				toUpdate.put("leave_type", leaveType);
			}
			
			Integer isResumed = 0;
			if(updateMap.get("is_resumed") != null) {
				isResumed = Integer.parseInt((String)updateMap.get("is_resumed"));
				toUpdate.put("is_resumed", isResumed);
			}
			
			System.out.println(isResumed);
			
			toUpdate.put("afl_id", aflId);
			//���ø��·���
			askforApplyDao.updateAskApplyforByAflid(toUpdate);
			
			//�ֶ��ύ����
			session.commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	@Override
	public List<Askforleave> selectAllAskforApply() {
		try {
			//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//���ҷ���
			List<Askforleave> askList = askforApplyDao.selectAllAskforApply();
			
			session.close();
			
			return askList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void deleteAskforApplyByAflid(Integer afl_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Askforleave> selectNoncheckedAskforApplyByDep(
			Integer dep_id) {
		try{
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			AskforleaveDao askforleaveDao = session.getMapper(AskforleaveDao.class);
	
			//���ҵķ���
			List<Askforleave> askApplyList = askforleaveDao.selectNoncheckedAskforApplyByDep(dep_id);

			session.close();
			
			return askApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Askforleave> selectAllAskforApplyBySid(Integer s_id) {
		try {
				//���session	
			SqlSession session =getSession();
		
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//���ҷ���
			List<Askforleave> askList = askforApplyDao.selectAllAskforApplyBySid(s_id);
			
			session.close();
			
			return askList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Askforleave> selectAskforleaveByNowDate(
			HashMap<String, Object> selectMap) {
		try {
			//���session	
			SqlSession session =getSession();
			//��ȡ�������
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//ʱ���ʽת��
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Integer sId = 101;
			if(selectMap.get("s_id")!=null) {
				sId = (Integer)selectMap.get("s_id");
			}
			
			String nowDateStr = "2019-01-01 08:00:00";
			if(selectMap.get("now_date") != null){
				nowDateStr = (String)selectMap.get("now_date")+" 01:00:00";
			}
			
			Timestamp nowDateSql = new Timestamp(sdf.parse(nowDateStr).getTime());
			
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			selectDaoMap.put("s_id", sId);
			selectDaoMap.put("now_date", nowDateSql);
			
			//���ҷ���
			List<Askforleave> askList = askforApplyDao.selectAskforleaveByNowDate(selectDaoMap);
			
			session.close();
			
			return askList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}