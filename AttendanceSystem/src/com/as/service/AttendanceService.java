package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Attendance;

public interface AttendanceService {
	
	//通过Attendance的id查找记录
	public Attendance findAttByAttId(Integer at_id);
	
	//通过员工id查找记录
	public List<Attendance> findAttById(Integer s_id);
	
	//通过部门id模糊查找打卡记录
	public List<Attendance> findAttByDepId(Integer dep_id);
	
	//按月和部门id查找打卡记录
	public List<Attendance> findAttByMonth(HashMap<String,Object> dateMap);
	
	//通过日期和员工id查找表的id 
	public Attendance findAtidBySidAndDate(HashMap<String,Object> atidMap);
	
	//通过日期和员工id查找表
	public List<Attendance> findAttBySidAndDate(HashMap<String,Object> attMap);
	
//	//通过表的id更新状态
//	public void updateAttendanceStatus(HashMap<String, Object> statusMap);
	
	//通过日期和员工id更新下班时间和状态
	public int updateClockoffStatus(HashMap<String, Object> toMap);
	
	//通过表的id更新打卡的第二个时间和状态
	public int updateAttendanceByAtid(HashMap<String, Object> clockoffMap);
	
	//新增考勤记录，不包括第二个时间
	public int insertNewAtt(HashMap<String,Object> atMap);
	
	//删除某条考勤记录
	public int deleteAttdanceByAtid(Integer at_id);
	
	//根据是否是加班标识、对应的记录id，查找那条考勤记录
	public Attendance findAttendanceByRecordId(HashMap<String, Object> selectMap);
	
}