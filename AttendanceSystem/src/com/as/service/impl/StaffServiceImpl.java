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
			// ������
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			// ��ȡ�����ļ�
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			// ��ȡ�Ự����
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			// ��ȡ�Ự
			SqlSession session = sqlFactory.openSession();
			return session;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// ��ȡ�������
	public Staff findStaffById(Integer s_id){
		try{
			//���session
			SqlSession session = getSession();
			//���Բ���Ա��
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
			//���session
			SqlSession session = getSession();
			//3.���Ը���Ա����Ϣ����
			StaffDao staffDao = session.getMapper(StaffDao.class);
	        HashMap<String, Object> updateStaffMap = new HashMap<String,Object>();
			//��ȡ����
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
	            //��ʽת��
				String entry_time = "2019-01-01 17:00:00";
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           	    Timestamp sqlDate = new Timestamp(sdf.parse(entry_time).getTime());	
				if(staffMap.get("entry_time") != null){
					entry_time = (String)staffMap.get("entry_time");
		            updateStaffMap.put("entry_time", sqlDate);
				}
				
				System.out.println("--"+s_id+entry_time);

		        
	            //���ø���Ա����Ϣ����
	            staffDao.updateStaffInfoReturnId(updateStaffMap);
	            session.commit();
	            session.close();		       
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
	   public  void insertNewStaffInfoReturnId(HashMap<String, Object> staffMap) {
		   try {
			   //��ȡsession
			   SqlSession session = getSession();
			   //5.��������Ա������
			   StaffDao staffDao = session.getMapper(StaffDao.class);
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		       HashMap<String, Object> insertStaffMap = new HashMap<String, Object>();
		       //��ȡ����
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
		       Timestamp entry_time1 = new Timestamp(sdf.parse(entry_time).getTime());//ʱ���ʽת��ʱע������������ᵼ�����������������õ����ݿ�
		       insertStaffMap.put("entry_time", entry_time1);
		       insertStaffMap.put("dep_id", dep_id);
		       insertStaffMap.put("identity", identity);
		       insertStaffMap.put("s_pass", s_pass);
		       //��������Ա������
		       staffDao.insertNewStaffInfoReturnId(insertStaffMap);
		       session.commit();
		       session.close();
		       }catch(Exception e){
				e.printStackTrace();}
	   }
	   public void deleteStaffInfoReturnId(Integer s_id) {
		   try {
			   //��ȡsession
			   SqlSession session = getSession();
		       //6.����ɾ��ĳλԱ����Ϣ
			   StaffDao staffDao = session.getMapper(StaffDao.class);
			   //��ȡ����
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
			//��ȡsession
			SqlSession session = getSession();
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//���ò�ѯ����
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
			//��ȡsession
			SqlSession session = getSession();
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//���ò�ѯ����
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
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			StaffDao staffDao = session.getMapper(StaffDao.class);
			
			//���ò��ҷ���
			List<Staff> staffList = staffDao.selectStaffByDepId(dep_id);
			
			return staffList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}