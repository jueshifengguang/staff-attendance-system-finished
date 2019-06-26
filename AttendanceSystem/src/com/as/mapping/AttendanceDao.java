package com.as.mapping;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.as.entity.Attendance;

public interface AttendanceDao {
	
	//通过表的id查找记录
	public Attendance findAttByAttId(Integer at_id);
	
	//通过员工id查找记录
	public List<Attendance> findAttById(Integer s_id);
	
	//通过部门id查找打卡记录
	public List<Attendance> findAttByDepId(Integer dep_id);
	
	//按月查找打卡记录
	public List<Attendance> fintAttByMonth(HashMap<String,Object> dateMap);
	
	//通过按月按部门查找考勤记录
	public List<Attendance> findAttByMonthAndDepid(HashMap<String,Object> findMap);
	
	//通过日期和员工id查找表的id 
	public Attendance  findAtidBySidAndDate(HashMap<String,Object> atidMap);
	
	//通过日期和员工id查找表
	public List<Attendance>  findAttBySidAndDate(HashMap<String,Object> dateMap);	
	
	
	//通过员工ID和日期更新打卡的下班时间和状态
	public void updateAttByDateAndSid(HashMap<String, Object> toMap);
	
	//通过表的id更新打卡的第二个时间和状态
	public void updateAttendanceByAtid(HashMap<String, Object> clockoffMap);
	
	//新增考勤记录，不包括第二个时间
	public int insertNewAtt(HashMap<String,Object> atMap);
	
	//删除某条考勤记录
	public void deleteAttdanceByAtid(Integer at_id);
		
	//根据是否是加班标识、对应的记录id，查找那条考勤记录
	public List<Attendance> findAttendanceByRecordId(HashMap<String, Object> selectMap);
	
}