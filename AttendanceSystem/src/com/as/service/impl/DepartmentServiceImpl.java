package com.as.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Department;
import com.as.mapping.DepartmentDao;
import com.as.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService {
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
	public Department findDepById(Integer dep_id) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//查找方法
			Department department = departmentDao.findDepById(dep_id);

			session.close();
			
			return department;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Department> selectByDepName(String dep_name) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//查找方法
			List<Department> departmentList = departmentDao.selectByDepName(dep_name);

			session.close();
			
			return departmentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertNewDepNotReturn(Integer dep_id, String dep_name) {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//新增方法
			departmentDao.insertNewDepNotReturn(dep_id, dep_name);

			//手动提交session
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Department> selectAllDepartment() {
		try {
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//查找方法
			List<Department> departmentList = departmentDao.selectAllDepartment();

			session.close();
			
			return departmentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}