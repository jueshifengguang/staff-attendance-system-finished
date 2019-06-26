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
	
	
	@Override
	public Askforleave findAskforApplyByAflid(Integer afl_id) {
		try {
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
		
			//1.测试通过请假表id查找
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
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
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
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			
			// TODO Auto-generated method stub
			
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//获得参数
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
			
			String leaveReason = "无";
			if(insertMap.get("leave_reason") != null) {
				leaveReason = (String)insertMap.get("leave_reason");
			}
			
			String approvedReason = "无";
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
			
			//新增方法
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
			//调用新增方法
			askforApplyDao.insertNewAskforApply(toInsert);
			
			//手动提交事务
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
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			
			// TODO Auto-generated method stub
			
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> toUpdate = new HashMap<String, Object>();
			
			//获取参数
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
			
			String leaveReason = "无";
			if(updateMap.get("leave_reason") != null) {
				leaveReason = (String)updateMap.get("leave_reason");
				toUpdate.put("leave_reason", leaveReason);
			}	
			
			String approvedReason = "无";
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
			//调用更新方法
			askforApplyDao.updateAskApplyforByAflid(toUpdate);
			
			//手动提交事务
			session.commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	@Override
	public List<Askforleave> selectAllAskforApply() {
		try {
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//查找方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			AskforleaveDao askforleaveDao = session.getMapper(AskforleaveDao.class);
	
			//查找的方法
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
				//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//查找方法
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
			//获得session	
			SqlSession session =getSession();
			//获取代理对象
			AskforleaveDao askforApplyDao = session.getMapper(AskforleaveDao.class);
			
			//时间格式转换
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
			
			//查找方法
			List<Askforleave> askList = askforApplyDao.selectAskforleaveByNowDate(selectDaoMap);
			
			session.close();
			
			return askList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}