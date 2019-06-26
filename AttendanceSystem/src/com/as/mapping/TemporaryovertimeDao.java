package com.as.mapping;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.as.entity.Temporaryovertime;

public interface TemporaryovertimeDao {

	//ͨ����¼id������ʱ�Ӱ���
	public Temporaryovertime findTempOvertimeByToid(Integer to_id);
	
	//�޸���ʱ�Ӱ���
	public void updateTempovertimeByToid(HashMap<String, Object> toMap);
	
	//������ʱ�Ӱ���
	public int insertTempOvertime(HashMap<String, Object> toMap);
	
	//��ѯ���м�¼
	public List<Temporaryovertime> selectAllTempOvertime();
	
	//��ѯ��ǰ���ڿ�ѡ�����мӰ��¼
	public List<Temporaryovertime> selectTmpOvertimeByNowDate(HashMap<String, Object> toMap);
	
	//�޸���ʱ�Ӱ���
	public void deleteTempovertimeByToid(Integer to_id);
}