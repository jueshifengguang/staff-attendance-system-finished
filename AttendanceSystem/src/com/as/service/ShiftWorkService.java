package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.ShiftWork;

public interface ShiftWorkService {
	//新增工作情况
	public int insertShiftWork(HashMap<String,Object> iswMap);
	
	//删除工作情况	
	public void deleteShiftWork(Integer sw_id);
	
	//更新工作情况
	public void updateShiftWork(HashMap<String,Object> uswMap);
	
	//返回该天该员工的考勤状态
	public ShiftWork getAttenStatus(HashMap<String, Object> asMap);
	//按员工id查看当天是否可以进行上班考勤
	//public ShiftWork ableCheckIn(HashMap<String, Object> ciMap);
	//按员工id查看当天是否可以进行下班考勤
	//public ShiftWork ableCheckOff(HashMap<String, Object> coMap);
	
	
	//查询某月某部门的工作情况
	public List<ShiftWork> selectAllStaffWorkConByMonthByDep(HashMap<String,Object> sallMap);//dep_id,working_start_start该月第一天,working_start_end下月第一天
	//查询某月某部门某员工工作情况
	public List<ShiftWork> selectOneStaffWorkConByMonthByDep(HashMap<String,Object> soneMap);//dep_id,s_id,start,end
	
	//通过记录id查找该记录
	public ShiftWork findShiftWorkByswid(Integer sw_id);
	
}