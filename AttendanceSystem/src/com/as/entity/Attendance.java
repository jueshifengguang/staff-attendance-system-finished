package com.as.entity;

import java.sql.Timestamp;

public class Attendance {

	private Integer at_id;
	private Integer s_id;
	private Integer dep_id;
	private Timestamp clock_in;
	private Timestamp clock_off;
	private Integer attendance_status;

	private Integer is_overtime;
	private Integer record_id;

	public Integer getIs_overtime() {
		return is_overtime;
	}

	public void setIs_overtime(Integer is_overtime) {
		this.is_overtime = is_overtime;
	}

	public Integer getRecord_id() {
		return record_id;
	}

	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
	}

	public Integer getAt_id() {
		return at_id;
	}

	public void setAt_id(Integer at_id) {
		this.at_id = at_id;
	}

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

	public Timestamp getClock_in() {
		return clock_in;
	}

	public void setClock_in(Timestamp clock_in) {
		this.clock_in = clock_in;
	}

	public Timestamp getClock_off() {
		return clock_off;
	}

	public void setClock_off(Timestamp clock_off) {
		this.clock_off = clock_off;
	}

	public Integer getAttendance_status() {
		return attendance_status;
	}

	public void setAttendance_status(Integer attendance_status) {
		this.attendance_status = attendance_status;
	}
}