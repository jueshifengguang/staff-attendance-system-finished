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
	//���Session�Ự
	public static SqlSession getSession(){
		try {
			//������
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			//��ȡ�����ļ�
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			//��ȡ�Ự����
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			//��ȡsession
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
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//���ҷ���
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
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//���ҷ���
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
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//��������
			departmentDao.insertNewDepNotReturn(dep_id, dep_name);

			//�ֶ��ύsession
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Department> selectAllDepartment() {
		try {
			//���session
			SqlSession session = getSession();
			//��ȡ�������
			DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);

			//���ҷ���
			List<Department> departmentList = departmentDao.selectAllDepartment();

			session.close();
			
			return departmentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}