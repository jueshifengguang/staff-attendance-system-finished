package com.as.entity;

import java.sql.Timestamp;
/**
 * ��ʱ�Ӱ���
 * @author dzz
 * 2019��6��1�� ����8:16:05
 * AttendanceSystem
 */
public class Temporaryovertime {
	private Integer to_id;	//��¼id
	private Timestamp t_overtime_start;	//��ʼʱ��
	private Timestamp t_overtime_end;	//����ʱ��
	private String t_o_reason;	//����ԭ��
	
	public Integer getTo_id() {
		return to_id;
	}
	public void setTo_id(Integer to_id) {
		this.to_id = to_id;
	}
	public Timestamp getT_overtime_start() {
		return t_overtime_start;
	}
	public void setT_overtime_start(Timestamp t_overtime_start) {
		this.t_overtime_start = t_overtime_start;
	}
	public Timestamp getT_overtime_end() {
		return t_overtime_end;
	}
	public void setT_overtime_end(Timestamp t_overtime_end) {
		this.t_overtime_end = t_overtime_end;
	}
	public String getT_o_reason() {
		return t_o_reason;
	}
	public void setT_o_reason(String t_o_reason) {
		this.t_o_reason = t_o_reason;
	}	
}