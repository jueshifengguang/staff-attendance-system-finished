package com.as.entity;

import java.sql.Timestamp;

public class Overtimeapplication {
	
	private Integer oa_id;	//��¼id
	private Integer s_id;	//Ա��id
	private Timestamp overtime_start;	//�Ӱ࿪ʼʱ��
	private Timestamp overtime_end;		//�Ӱ����ʱ��
	private String overtime_reason;		//�Ӱ�����
	private int is_approved;	//�Ƿ�ͨ����0��δͨ����1����ͨ����
	private int is_temporary;	//�Ƿ�����ʱ�Ӱ�
	private String examination;	//�������
	private int is_sign;	//�򿨱�ʶ��0��δ�򿨣�1�������ϰ࿨��2�������°࿨��
	
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