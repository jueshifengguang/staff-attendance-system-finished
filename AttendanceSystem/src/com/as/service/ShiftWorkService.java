package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.ShiftWork;

public interface ShiftWorkService {
	//�����������
	public int insertShiftWork(HashMap<String,Object> iswMap);
	
	//ɾ���������	
	public void deleteShiftWork(Integer sw_id);
	
	//���¹������
	public void updateShiftWork(HashMap<String,Object> uswMap);
	
	//���ظ����Ա���Ŀ���״̬
	public ShiftWork getAttenStatus(HashMap<String, Object> asMap);
	//��Ա��id�鿴�����Ƿ���Խ����ϰ࿼��
	//public ShiftWork ableCheckIn(HashMap<String, Object> ciMap);
	//��Ա��id�鿴�����Ƿ���Խ����°࿼��
	//public ShiftWork ableCheckOff(HashMap<String, Object> coMap);
	
	
	//��ѯĳ��ĳ���ŵĹ������
	public List<ShiftWork> selectAllStaffWorkConByMonthByDep(HashMap<String,Object> sallMap);//dep_id,working_start_start���µ�һ��,working_start_end���µ�һ��
	//��ѯĳ��ĳ����ĳԱ���������
	public List<ShiftWork> selectOneStaffWorkConByMonthByDep(HashMap<String,Object> soneMap);//dep_id,s_id,start,end
	
	//ͨ����¼id���Ҹü�¼
	public ShiftWork findShiftWorkByswid(Integer sw_id);
	
}