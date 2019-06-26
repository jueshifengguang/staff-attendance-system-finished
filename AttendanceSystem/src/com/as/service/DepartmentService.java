package com.as.service;

import java.util.List;

import com.as.entity.Department;

public interface DepartmentService {
	//通过部门id查找部门
	public Department findDepById(Integer dep_id);

	//通过部门名称模糊查找部门
	public List<Department> selectByDepName(String dep_name);
	
	//新增部门，不返回参数
	public void insertNewDepNotReturn(Integer dep_id, String dep_name);
	
	//查找所有部门
	public List<Department> selectAllDepartment();
}