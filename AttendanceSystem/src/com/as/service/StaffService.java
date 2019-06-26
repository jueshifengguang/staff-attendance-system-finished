package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Staff;

public interface StaffService {
	//ͨ��Ա��id����Ա��
	public Staff findStaffById(Integer s_id);
	
	//ͨ��Ա������ģ������Ա��
	//public List<Staff> selectStaffByName(String s_name);
	
	//����Ա����Ϣ���ز���
	public  void insertNewStaffInfoReturnId(HashMap<String, Object> staffMap);
	
	//�޸�Ա����Ϣ���ز���
	public void updateStaffInfoReturnId(HashMap<String, Object> staffMap);
	
	//ɾ��Ա����Ϣ���ز���
	public void deleteStaffInfoReturnId(Integer s_id);
	
	//��ѯ���м�¼
	public  List<Staff> selectAllStaff();
	
	//ͨ��s_id���������Ĳ���dep_id
	public Integer findDepidBySid(Integer s_id);
	
	//ͨ������id���Ҹò�������Ա��
	public List<Staff> selectStaffByDepId(Integer dep_id);
}