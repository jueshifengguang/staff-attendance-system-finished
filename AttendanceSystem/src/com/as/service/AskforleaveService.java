package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Askforleave;

public interface AskforleaveService {
	
	//ͨ����ټ�¼id������ټ�¼
	public Askforleave findAskforApplyByAflid(Integer afl_id);

	//ͨ��Ա��id������ټ�¼
	public Askforleave findAskforApplyBySid(Integer s_id);
	
	//������ټ�¼�������ز���
	public int insertNewAskforApply(HashMap<String,Object> insertMap);
		
	//�޸���ټ�¼
	public void updateAskApplyforByAflid(HashMap<String,Object> updateMap);
	
	//��ѯ������ټ�¼
	public List<Askforleave> selectAllAskforApply();
	
	//ɾ����ټ�¼
	public void deleteAskforApplyByAflid(Integer afl_id);

	//������id���Ҹò���������������¼
	public List<Askforleave> selectNoncheckedAskforApplyByDep(Integer dep_id);
	
	//ͨ��Ա��ID��ѯ������ټ�¼
	public List<Askforleave> selectAllAskforApplyBySid(Integer s_id);
	
	//ͨ����ǰʱ�䣬�����ѱ�ͨ��������б�
	public List<Askforleave> selectAskforleaveByNowDate(HashMap<String,Object> selectMap);
}