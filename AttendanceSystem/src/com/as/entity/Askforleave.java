package com.as.entity;

import java.sql.Timestamp;

public class Askforleave {

	private Integer afl_id;
	private Integer s_id;
	private Integer is_approved;
	private Integer is_resumed;
	private Integer leave_type;
	private Timestamp starting_date;
	private Timestamp ending_date;
	private String leave_reason;
	private String approved_reason;
	
	public Integer getAfl_id() {
		return afl_id;
	}
	public Integer getS_id() {
		return s_id;
	}
	public Integer getIs_approved() {
		return is_approved;
	}
	public Integer getIs_resumed() {
		return is_resumed;
	}
	public Integer getLeave_type() {
		return leave_type;
	}
	public Timestamp getStarting_date() {
		return starting_date;
	}
	public Timestamp getEnding_date() {
		return ending_date;
	}
	public String getLeave_reason() {
		return leave_reason;
	}
	public String getApproved_reason() {
		return approved_reason;
	}
	public void setAfl_id(Integer afl_id) {
		this.afl_id = afl_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}
	public void setIs_approved(Integer is_approved) {
		this.is_approved = is_approved;
	}
	public void setIs_resumed(Integer is_resumed) {
		this.is_resumed = is_resumed;
	}
	public void setLeave_type(Integer leave_type) {
		this.leave_type = leave_type;
	}
	public void setStarting_date(Timestamp starting_date) {
		this.starting_date = starting_date;
	}
	public void setEnding_date(Timestamp ending_date) {
		this.ending_date = ending_date;
	}
	public void setLeave_reason(String leave_reason){
		this.leave_reason = leave_reason;
	}
	public void setApproved_reason(String approved_reason){
		this.approved_reason = approved_reason;
	}
}