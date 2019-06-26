package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Attendance;

public interface AttendanceService {
	
	//ͨ��Attendance��id���Ҽ�¼
	public Attendance findAttByAttId(Integer at_id);
	
	//ͨ��Ա��id���Ҽ�¼
	public List<Attendance> findAttById(Integer s_id);
	
	//ͨ������idģ�����Ҵ򿨼�¼
	public List<Attendance> findAttByDepId(Integer dep_id);
	
	//���ºͲ���id���Ҵ򿨼�¼
	public List<Attendance> findAttByMonth(HashMap<String,Object> dateMap);
	
	//ͨ�����ں�Ա��id���ұ��id 
	public Attendance findAtidBySidAndDate(HashMap<String,Object> atidMap);
	
	//ͨ�����ں�Ա��id���ұ�
	public List<Attendance> findAttBySidAndDate(HashMap<String,Object> attMap);
	
//	//ͨ�����id����״̬
//	public void updateAttendanceStatus(HashMap<String, Object> statusMap);
	
	//ͨ�����ں�Ա��id�����°�ʱ���״̬
	public int updateClockoffStatus(HashMap<String, Object> toMap);
	
	//ͨ�����id���´򿨵ĵڶ���ʱ���״̬
	public int updateAttendanceByAtid(HashMap<String, Object> clockoffMap);
	
	//�������ڼ�¼���������ڶ���ʱ��
	public int insertNewAtt(HashMap<String,Object> atMap);
	
	//ɾ��ĳ�����ڼ�¼
	public int deleteAttdanceByAtid(Integer at_id);
	
	//�����Ƿ��ǼӰ��ʶ����Ӧ�ļ�¼id�������������ڼ�¼
	public Attendance findAttendanceByRecordId(HashMap<String, Object> selectMap);
	
}