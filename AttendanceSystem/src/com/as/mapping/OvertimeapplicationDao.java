package com.as.mapping;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Overtimeapplication;

public interface OvertimeapplicationDao {

	//通过记录id查找加班申请记录
	public Overtimeapplication findOvertimeApplyByOaid(Integer oa_id);
	
	//修改加班申请记录
	public void updateOvertimeApplyByOaid(HashMap<String, Object> updateMap);
	
	//新增加班申请记录
	public int insertOvertimeApply(HashMap<String, Object> insertMap);
	
	//查询某人的所有记录
	public List<Overtimeapplication> selectAllOvertimeApplyBySid(Integer s_id);
	
	//删除某条记录
	public void deleteOvertimeApplyByOaid(Integer oa_id);
	
	//按部门id查找该部门所有未审批的加班申请记录
	public List<Overtimeapplication> selectNoncheckedOvertimeApplyByDep(Integer dep_id);
	
	//按部门id查找该部门所有已审批的加班申请记录
	public List<Overtimeapplication> selectCheckedOvertimeApplyByDep(Integer dep_id);
	
	//查找该员工当前可以打卡（可开始）的所有临时加班记录（已经审批通过的）
	public List<Overtimeapplication> selectSignInOvertimeApply(HashMap<String, Object> selectMap);
	
	//查找该员工当前可以签退（可结束）的所有临时加班记录（已经审批通过的）
	public List<Overtimeapplication> selectSignOffOvertimeApply(HashMap<String, Object> selectMap);
	
	//查找该员工在当前时间的所有加班申请记录
	public List<Overtimeapplication> selectOvertimeApplyByNowDate(HashMap<String, Object> selectMap);
	
	//查找该员工在当前时间的所有加班申请记录
	public List<Overtimeapplication> selectTodayOvertimeApply(HashMap<String, Object> selectMap);
	
	//查找该员工在某个月的所有正常申请、且审批通过的加班
	public List<Overtimeapplication> selectOvertimeByMonth(HashMap<String, Object> selectMap);
		
	//查找该员工在某个月的有考勤记录的临时性加班
	public List<Overtimeapplication> selectTmpOvertimeByMonth(HashMap<String, Object> selectMap);
	
}