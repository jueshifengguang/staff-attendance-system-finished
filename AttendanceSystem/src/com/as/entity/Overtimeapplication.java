package com.as.entity;

import java.sql.Timestamp;

public class Overtimeapplication {
	
	private Integer oa_id;	//记录id
	private Integer s_id;	//员工id
	private Timestamp overtime_start;	//加班开始时间
	private Timestamp overtime_end;		//加班结束时间
	private String overtime_reason;		//加班理由
	private int is_approved;	//是否被通过（0：未通过，1：已通过）
	private int is_temporary;	//是否是临时加班
	private String examination;	//审批意见
	private int is_sign;	//打卡标识（0：未打卡，1：打了上班卡，2：打了下班卡）
	
	public Integer getOa_id() {
		return oa_id;
	}
	public void setOa_id(Integer oa_id) {
		this.oa_id = oa_id;
	}
	public Integer getS_id() {
		return s_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}
	public Timestamp getOvertime_start() {
		return overtime_start;
	}
	public void setOvertime_start(Timestamp overtime_start) {
		this.overtime_start = overtime_start;
	}
	public Timestamp getOvertime_end() {
		return overtime_end;
	}
	public void setOvertime_end(Timestamp overtime_end) {
		this.overtime_end = overtime_end;
	}
	public String getOvertime_reason() {
		return overtime_reason;
	}
	public void setOvertime_reason(String overtime_reason) {
		this.overtime_reason = overtime_reason;
	}
	public int getIs_approved() {
		return is_approved;
	}
	public void setIs_approved(int is_approved) {
		this.is_approved = is_approved;
	}
	public int getIs_temporary() {
		return is_temporary;
	}
	public void setIs_temporary(int is_temporary) {
		this.is_temporary = is_temporary;
	}
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}
	public int getIs_sign() {
		return is_sign;
	}
	public void setIs_sign(int is_sign) {
		this.is_sign = is_sign;
	}
}