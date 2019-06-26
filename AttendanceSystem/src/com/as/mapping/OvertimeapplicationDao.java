package com.as.mapping;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Overtimeapplication;

public interface OvertimeapplicationDao {

	//ͨ����¼id���ҼӰ������¼
	public Overtimeapplication findOvertimeApplyByOaid(Integer oa_id);
	
	//�޸ļӰ������¼
	public void updateOvertimeApplyByOaid(HashMap<String, Object> updateMap);
	
	//�����Ӱ������¼
	public int insertOvertimeApply(HashMap<String, Object> insertMap);
	
	//��ѯĳ�˵����м�¼
	public List<Overtimeapplication> selectAllOvertimeApplyBySid(Integer s_id);
	
	//ɾ��ĳ����¼
	public void deleteOvertimeApplyByOaid(Integer oa_id);
	
	//������id���Ҹò�������δ�����ļӰ������¼
	public List<Overtimeapplication> selectNoncheckedOvertimeApplyByDep(Integer dep_id);
	
	//������id���Ҹò��������������ļӰ������¼
	public List<Overtimeapplication> selectCheckedOvertimeApplyByDep(Integer dep_id);
	
	//���Ҹ�Ա����ǰ���Դ򿨣��ɿ�ʼ����������ʱ�Ӱ��¼���Ѿ�����ͨ���ģ�
	public List<Overtimeapplication> selectSignInOvertimeApply(HashMap<String, Object> selectMap);
	
	//���Ҹ�Ա����ǰ����ǩ�ˣ��ɽ�������������ʱ�Ӱ��¼���Ѿ�����ͨ���ģ�
	public List<Overtimeapplication> selectSignOffOvertimeApply(HashMap<String, Object> selectMap);
	
	//���Ҹ�Ա���ڵ�ǰʱ������мӰ������¼
	public List<Overtimeapplication> selectOvertimeApplyByNowDate(HashMap<String, Object> selectMap);
	
	//���Ҹ�Ա���ڵ�ǰʱ������мӰ������¼
	public List<Overtimeapplication> selectTodayOvertimeApply(HashMap<String, Object> selectMap);
	
	//���Ҹ�Ա����ĳ���µ������������롢������ͨ���ļӰ�
	public List<Overtimeapplication> selectOvertimeByMonth(HashMap<String, Object> selectMap);
		
	//���Ҹ�Ա����ĳ���µ��п��ڼ�¼����ʱ�ԼӰ�
	public List<Overtimeapplication> selectTmpOvertimeByMonth(HashMap<String, Object> selectMap);
	
}