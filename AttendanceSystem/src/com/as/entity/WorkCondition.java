package com.as.entity;

import java.sql.Timestamp;

/**
 * 工作情况类
 * @author dzz
 * 2019年6月10日 上午11:02:56
 * AttendanceSystem
 */
public class WorkCondition {

	//还应该有年月日，即那一天的工作情况
	private Integer s_id;//员工id
	private String s_name;	//员工姓名
	private Integer dep_id;//部门id
	private Timestamp working_start;//工作实际开始时间
	private Timestamp working_end;//工作实际结束时间
	private String attendence_status;//考勤状态（1：正常签到，2：迟到，3：早退，4：又迟到又早退，5：请假，6：旷工，7：无(即这天他跟公司没关系)）
	
	//还应该记录本应该的上班时间吗？？？？？？？？？？、
	
	public Integer getS_id() {
		return s_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getAttendence_status() {
		return attendence_status;
	}
	public void setAttendence_status(String attendence_status) {
		this.attendence_status = attendence_status;
	}
	public Timestamp getWorking_start() {
		return working_start;
	}
	public void setWorking_start(Timestamp working_start) {
		this.working_start = working_start;
	}
	public Timestamp getWorking_end() {
		return working_end;
	}
	public void setWorking_end(Timestamp working_end) {
		this.working_end = working_end;
	}
}