package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Staff;

public interface StaffService {
	//通过员工id查找员工
	public Staff findStaffById(Integer s_id);
	
	//通过员工名称模糊查找员工
	//public List<Staff> selectStaffByName(String s_name);
	
	//新增员工信息返回参数
	public  void insertNewStaffInfoReturnId(HashMap<String, Object> staffMap);
	
	//修改员工信息返回参数
	public void updateStaffInfoReturnId(HashMap<String, Object> staffMap);
	
	//删除员工信息返回参数
	public void deleteStaffInfoReturnId(Integer s_id);
	
	//查询所有记录
	public  List<Staff> selectAllStaff();
	
	//通过s_id查找所属的部门dep_id
	public Integer findDepidBySid(Integer s_id);
	
	//通过部门id查找该部门所有员工
	public List<Staff> selectStaffByDepId(Integer dep_id);
}