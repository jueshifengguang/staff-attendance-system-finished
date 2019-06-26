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
	public Temporaryovertime findTempOvertimeByToid(Integer to_id) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//查找方法
			Temporaryovertime tmpOvertime = tempOvertimeDao.findTempOvertimeByToid(to_id);
			System.out.println(tmpOvertime.getT_overtime_start() + "我是分隔符"+
					tmpOvertime.getT_overtime_end()+"分隔符"+tmpOvertime.getT_o_reason());
			
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//更新方法
			HashMap<String, Object> updateMap = new HashMap<String, Object>();
			
			//获得参数
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
			String to_reason = "无";
			if(toMap.get("t_o_reason") != null){
				to_reason = (String) toMap.get("t_o_reason");
				updateMap.put("t_o_reason",to_reason);
			}
			
			
			updateMap.put("to_id", toId);
			
			//调用更新方法
			tempOvertimeDao.updateTempovertimeByToid(updateMap);
			
			//手动提交事务
			session.commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public int insertTempOvertime(HashMap<String, Object> toMap) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//获得参数
			String startTime = "2019-01-01 17:00:00";
			if(toMap.get("t_overtime_start") != null){
				startTime = (String)toMap.get("t_overtime_start");
			}
			String endTime = "2019-01-01 18:00:00";
			if(toMap.get("t_overtime_end") != null){
				endTime = (String) toMap.get("t_overtime_end");
			}
			String to_reason = "无";
			if(toMap.get("t_o_reason") != null){
				to_reason = (String) toMap.get("t_o_reason");
			}
			
			//新增方法
			HashMap<String, Object> insertMap = new HashMap<String, Object>();
			Timestamp sqlStart = new Timestamp(sdf.parse(startTime).getTime());
			insertMap.put("t_overtime_start", sqlStart);
			Timestamp sqlEnd = new Timestamp(sdf.parse(endTime).getTime());
			insertMap.put("t_overtime_end", sqlEnd);
			insertMap.put("t_o_reason",to_reason);
			//调用新增方法
			tempOvertimeDao.insertTempOvertime(insertMap);
			
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
	public List<Temporaryovertime> selectAllTempOvertime() {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//查找方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//格式转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//传参列表
			HashMap<String, Object> selectMap = new HashMap<String, Object>();
			
			//获取当前时间
			Date nowDateNow = new Date();
			String nowDateStr = sdf.format(nowDateNow);
			Timestamp nowDateSql = new Timestamp(sdf.parse(nowDateStr).getTime());
			selectMap.put("t_overtime_start", nowDateSql);
			
			//调用查询方法
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
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			TemporaryovertimeDao tempOvertimeDao = session.getMapper(TemporaryovertimeDao.class);
			
			//调用删除方法
			tempOvertimeDao.deleteTempovertimeByToid(to_id);
			
			//手动提交session
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}