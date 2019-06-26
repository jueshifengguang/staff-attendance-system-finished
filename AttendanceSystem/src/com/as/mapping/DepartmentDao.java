package com.as.mapping;

import java.util.List;

import com.as.entity.Department;

public interface DepartmentDao {
	
	//ͨ������id���Ҳ���
	public Department findDepById(Integer dep_id);

	//ͨ����������ģ�����Ҳ���
	public List<Department> selectByDepName(String dep_name);
	
	//�������ţ������ز���
	public void insertNewDepNotReturn(Integer dep_id, String dep_name);
	
	//�������в���
	public List<Department> selectAllDepartment();
}