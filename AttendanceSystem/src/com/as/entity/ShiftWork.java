package com.as.entity;
import java.sql.Timestamp;

public class ShiftWork {
	private Integer sw_id;//��¼id
	private Integer s_id;//Ա��id
	private Integer dep_id;//����id
	private Timestamp working_start;//������ʼʱ��
	private Timestamp working_end;//��������ʱ��
	private Integer attendence_status;//����״̬
	
	public Integer getAttendence_status() {
		return attendence_status;
	}
	public void setAttendence_status(Integer attendence_status) {
		this.attendence_status = attendence_status;
	}
	public Integer getSw_id() {
		return sw_id;
	}
	public void setSw_id(Integer sw_id) {
		this.sw_id = sw_id;
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