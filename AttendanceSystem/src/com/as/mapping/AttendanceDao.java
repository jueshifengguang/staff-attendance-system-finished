package com.as.mapping;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.as.entity.Attendance;

public interface AttendanceDao {
	
	//ͨ�����id���Ҽ�¼
	public Attendance findAttByAttId(Integer at_id);
	
	//ͨ��Ա��id���Ҽ�¼
	public List<Attendance> findAttById(Integer s_id);
	
	//ͨ������id���Ҵ򿨼�¼
	public List<Attendance> findAttByDepId(Integer dep_id);
	
	//���²��Ҵ򿨼�¼
	public List<Attendance> fintAttByMonth(HashMap<String,Object> dateMap);
	
	//ͨ�����°����Ų��ҿ��ڼ�¼
	public List<Attendance> findAttByMonthAndDepid(HashMap<String,Object> findMap);
	
	//ͨ�����ں�Ա��id���ұ��id 
	public Attendance  findAtidBySidAndDate(HashMap<String,Object> atidMap);
	
	//ͨ�����ں�Ա��id���ұ�
	public List<Attendance>  findAttBySidAndDate(HashMap<String,Object> dateMap);	
	
	
	//ͨ��Ա��ID�����ڸ��´򿨵��°�ʱ���״̬
	public void updateAttByDateAndSid(HashMap<String, Object> toMap);
	
	//ͨ�����id���´򿨵ĵڶ���ʱ���״̬
	public void updateAttendanceByAtid(HashMap<String, Object> clockoffMap);
	
	//�������ڼ�¼���������ڶ���ʱ��
	public int insertNewAtt(HashMap<String,Object> atMap);
	
	//ɾ��ĳ�����ڼ�¼
	public void deleteAttdanceByAtid(Integer at_id);
		
	//�����Ƿ��ǼӰ��ʶ����Ӧ�ļ�¼id�������������ڼ�¼
	public List<Attendance> findAttendanceByRecordId(HashMap<String, Object> selectMap);
	
}