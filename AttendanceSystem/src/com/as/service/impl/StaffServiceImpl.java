package com.as.service.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Staff;
import com.as.mapping.StaffDao;
import com.as.service.StaffService;

public class StaffServiceImpl implements StaffService {
	
	public static SqlSession getSession() {
		try {
			// 构建器
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			// 读取配置文件
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			// 获取会话工厂
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			// 获取会话
			SqlSession session = sqlFactory.openSession();
			return session;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取代理对象
	public Staff findStaffById(Integer s_id){
		try{
			//获得session
			SqlSession session = getSession();
			//测试查找员工
			StaffDao staffDao = session.getMapper(StaffDao.class);
			Staff staff = staffDao.findStaffById(s_id);
			session.close();
			return staff;
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public  void updateStaffInfoReturnId(HashMap<String, Object> staffMap) {
		try {
			//获得session
			SqlSession session = getSession();
			//3.测试更新员工信息方法
			StaffDao staffDao = session.getMapper(StaffDao.class);
	        HashMap<String, Object> updateStaffMap = new HashMap<String,Object>();
			//获取参数
		       Integer s_id = 1002;
				if(staffMap.get("s_id")!=null) {
					s_id = Integer.parseInt(staffMap.get("s_id").toString());
			           updateStaffMap.put("s_id", s_id);
				}

		       Integer dep_id = 1001;
				if(staffMap.get("dep_id")!=null) 
				{
					dep_id = Integer.parseInt(staffMap.get("dep_id").toString());
			           updateStaffMap.put("dep_id", dep_id);
				}
				String s_name = "Alice";
				if(staffMap.get("s_name") != null){
				s_name = (String) staffMap.get("s_name");
		           updateStaffMap.put("s_name", s_name);
				}
				Integer identity = 1;
				if(staffMap.get("identity")!=null) 
				{
					identity = Integer.parseInt(staffMap.get("identity").toString());
			           updateStaffMap.put("identity", identity);

				}
				String s_pass = "123456";
				if(staffMap.get("s_pass") != null){
					s_pass = (String) staffMap.get("s_pass");
			           updateStaffMap.put("s_pass", s_pass);
				        
				}
	            //格式转换
				String entry_time = "2019-01-01 17:00:00";
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           	    Timestamp sqlDate = new Timestamp(sdf.parse(entry_time).getTime());	
				if(staffMap.get("entry_time") != null){
					entry_time = (String)staffMap.get("entry_time");
		            updateStaffMap.put("entry_time", sqlDate);
				}
				
				System.out.println("--"+s_id+entry_time);

		        
	            //调用更新员工信息函数
	            staffDao.updateStaffInfoReturnId(updateStaffMap);
	            session.commit();
	            session.close();		       
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
	   public  void insertNewStaffInfoReturnId(HashMap<String, Object> staffMap) {
		   try {
			   //获取session
			   SqlSession session = getSession();
			   //5.测试新增员工方法
			   StaffDao staffDao = session.getMapper(StaffDao.class);
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		       HashMap<String, Object> insertStaffMap = new HashMap<String, Object>();
		       //获取参数
		       Integer s_id = 1002;
				if(staffMap.get("s_id")!=null) {
					s_id = Integer.parseInt(staffMap.get("s_id").toString());
				}

		       Integer dep_id = 1001;
				if(staffMap.get("dep_id")!=null) 
				{
					dep_id = Integer.parseInt(staffMap.get("dep_id").toString());
				}
				String s_name = "Alice";
				if(staffMap.get("s_name") != null){
					s_name = (String) staffMap.get("s_name");
				}
				Integer identity = 1;
				if(staffMap.get("identity")!=null) 
				{
			     	identity = Integer.parseInt(staffMap.get("identity").toString());
				}
				String s_pass = "123456";
				if(staffMap.get("s_pass") != null){
					s_pass = (String) staffMap.get("s_pass");
				}
				String entry_time = "2019-01-01 17:00:00";
				if(staffMap.get("entry_time") != null){
					entry_time= (String)staffMap.get("entry_time");}
				
				//System.out.println("--"+s_id);
				
		       insertStaffMap.put("s_id", s_id);
		       insertStaffMap.put("s_name", s_name);
		       Timestamp entry_time1 = new Timestamp(sdf.parse(entry_time).getTime());//时间格式转换时注意命名，否则会导致新增操作不能作用到数据库
		       insertStaffMap.put("entry_time", entry_time1);
		       insertStaffMap.put("dep_id", dep_id);
		       insertStaffMap.put("identity", identity);
		       insertStaffMap.put("s_pass", s_pass);
		       //调用新增员工函数
		       staffDao.insertNewStaffInfoReturnId(insertStaffMap);
		       session.commit();
		       session.close();
		       }catch(Exception e){
				e.printStackTrace();}
	   }
	   public void deleteStaffInfoReturnId(Integer s_id) {
		   try {
			   //获取session
			   SqlSession session = getSession();
		       //6.测试删除某位员工信息
			   StaffDao staffDao = session.getMapper(StaffDao.class);
			   //获取参数
		       staffDao.deleteStaffInfoReturnId(s_id);
		       session.commit();
		       session.close();
		   }catch(Exception e){
				e.printStackTrace();
	   }
			
	}

	@Override
	public List<Staff> selectAllStaff() {
		try {
			//获取session
			SqlSession session = getSession();
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//调用查询方法
			List<Staff> staffList = staffDao.selectAllStaff();
			
			return staffList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Integer findDepidBySid(Integer s_id) {
		try {
			//获取session
			SqlSession session = getSession();
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//调用查询方法
			Integer depId = staffDao.findDepidBySid(s_id);
			
			return depId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Staff> selectStaffByDepId(Integer dep_id) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//调用查找方法
			List<Staff> staffList = staffDao.selectStaffByDepId(dep_id);
			
			return staffList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}