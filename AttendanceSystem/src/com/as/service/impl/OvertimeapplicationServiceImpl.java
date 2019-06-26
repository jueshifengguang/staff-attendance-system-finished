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
	public Overtimeapplication findOvertimeApplyByOaid(Integer oa_id) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//查找方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> updateMapReal = new HashMap<String, Object>();
			
			//获得参数
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
			String oa_reason = "无";
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
			String examination = "无";
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
						
			//调用更新方法
			overtimeDao.updateOvertimeApplyByOaid(updateMapReal);
			
			//手动提交session
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insertOvertimeApply(HashMap<String, Object> insertMap) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> insertMapReal = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 1;
			if(insertMap.get("s_id") != null){
				sId = (Integer) insertMap.get("s_id");
			}
			insertMapReal.put("s_id", sId);
			//开始时间
			String startTime = "2019-01-01 17:00:00";
			if(insertMap.get("overtime_start") != null){
				startTime = (String)insertMap.get("overtime_start");
			}
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMapReal.put("overtime_start", sqlStart);
			//结束时间
			String endTime = "2019-01-01 18:00:00";
			if(insertMap.get("overtime_end") != null){
				endTime = (String) insertMap.get("overtime_end");
			}
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMapReal.put("overtime_end", sqlEnd);
			//加班申请理由
			String oa_reason = "无";
			if(insertMap.get("overtime_reason") != null){
				oa_reason = (String) insertMap.get("overtime_reason");
			}
			insertMapReal.put("overtime_reason",oa_reason);
			//是否被审批
			Integer isApproved = 0;
			if(insertMap.get("is_approved") != null){
				isApproved = (Integer) insertMap.get("is_approved");
			}
			insertMapReal.put("is_approved", isApproved);
			//是否是临时性加班
			Integer isTemporary = 0;
			if(insertMap.get("is_temporary") != null){
				isTemporary = (Integer) insertMap.get("is_temporary");
			}
			insertMapReal.put("is_temporary", isTemporary);
			//审批意见
			String examination = "无";
			if(insertMap.get("examination") != null){
				examination = (String) insertMap.get("examination");
			}
			insertMap.put("examination",examination);
			//标识打卡位
			Integer isSign = 0;
			if(insertMap.get("is_sign") != null){
				isSign = (Integer) insertMap.get("is_sign");
			}
			insertMapReal.put("is_sign", isSign);
			
			
			//调用新增方法
			overtimeDao.insertOvertimeApply(insertMapReal);
			
			//手动提交session
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);

			//查找方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//删除方法
			overtimeDao.deleteOvertimeApplyByOaid(oa_id);
			
			//手动提交session
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> selectSignInMap = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectSignInMap.put("s_id", sId);
			//当前时间
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectSignInMap.put("now_date", sqlNowDate);
			//这一天的开始时间
			String begin_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectSignInMap.put("begin_date", sqlBeginDate);
			//这一天的结束时间
			String next_date_str = "2019-01-02 00:00:00";
			if(selectMap.get("next_date") != null){
				next_date_str = (String)selectMap.get("next_date");
			}
			Timestamp sqlNextDate = new Timestamp(sdf.parse(next_date_str).getTime());
			selectSignInMap.put("next_date", sqlNextDate);
			
			
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> selectSignOffMap = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectSignOffMap.put("s_id", sId);
			//当前时间
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectSignOffMap.put("now_date", sqlNowDate);
			//这一天的开始时间
			String begin_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectSignOffMap.put("begin_date", sqlBeginDate);
			//这一天的结束时间
			String next_date_str = "2019-01-02 00:00:00";
			if(selectMap.get("next_date") != null){
				next_date_str = (String)selectMap.get("next_date");
			}
			Timestamp sqlNextDate = new Timestamp(sdf.parse(next_date_str).getTime());
			selectSignOffMap.put("next_date", sqlNextDate);
			
			
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> selectNowMap = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 1;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectNowMap.put("s_id", sId);
			//当前时间
			String now_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("now_date") != null){
				now_date_str = (String)selectMap.get("now_date");
			}
			Timestamp sqlNowDate = new Timestamp(sdf.parse(now_date_str).getTime());
			selectNowMap.put("now_date", sqlNowDate);	
			
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换,格式化 yyyy-MM-dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = "2019-06-10";
			if(selectMap.get("date") != null){
				date = (String)selectMap.get("date");
			}
			//这里改成强转了
			Integer s_id = 101;
			if(selectMap.get("s_id") != null){
				s_id = (Integer)selectMap.get("s_id");
			}
			
			//转换格式
			Date date_date = sdf.parse(date);
			String sqldate = sdf.format(date_date);
			
			System.out.println("这里是Overtimeimpl的参数测试：date："+sqldate + "---sId："+s_id);
			
			//传给dao
			HashMap<String, Object> selectDaoMap = new HashMap<String,Object>();
			selectDaoMap.put("date",sqldate);
			selectDaoMap.put("s_id",s_id);	
			
			//查找的方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 103;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectDaoMap.put("s_id", sId);
			
			//开始时间
			String begin_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectDaoMap.put("begin_date", sqlBeginDate);
			
			//结束时间
			String end_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("end_date") != null){
				end_date_str = (String)selectMap.get("end_date");
			}
			Timestamp sqlEndDate = new Timestamp(sdf.parse(end_date_str).getTime());
			selectDaoMap.put("end_date", sqlEndDate);
			
			System.out.println("这里是impl的方法，sid是："+sId);
			System.out.println(sqlBeginDate);
			System.out.println(sqlEndDate);
			
			//查找的方法
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectOvertimeByMonth(selectDaoMap);
					
			session.close();
			
			System.out.println("这里还是impl，这里的list的长度是："+overtimeApplyList.size());
			
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			OvertimeapplicationDao overtimeDao = session.getMapper(OvertimeapplicationDao.class);
	
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> selectDaoMap = new HashMap<String, Object>();
			
			//获得参数
			Integer sId = 103;
			if(selectMap.get("s_id") != null){
				sId = (Integer) selectMap.get("s_id");
			}
			selectDaoMap.put("s_id", sId);
			
			//开始时间
			String begin_date_str = "2019-01-01 17:00:00";
			if(selectMap.get("begin_date") != null){
				begin_date_str = (String)selectMap.get("begin_date");
			}
			Timestamp sqlBeginDate = new Timestamp(sdf.parse(begin_date_str).getTime());
			selectDaoMap.put("begin_date", sqlBeginDate);
			
			//结束时间
			String end_date_str = "2019-01-01 00:00:00";
			if(selectMap.get("end_date") != null){
				end_date_str = (String)selectMap.get("end_date");
			}
			Timestamp sqlEndDate = new Timestamp(sdf.parse(end_date_str).getTime());
			selectDaoMap.put("end_date", sqlEndDate);
			
			//查找的方法
			List<Overtimeapplication> overtimeApplyList = overtimeDao.selectTmpOvertimeByMonth(selectDaoMap);
			
			session.close();
			
			return overtimeApplyList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}