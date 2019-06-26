package com.as.entity;

import java.sql.Timestamp;
/**
 * 临时加班类
 * @author dzz
 * 2019年6月1日 下午8:16:05
 * AttendanceSystem
 */
public class Temporaryovertime {
	private Integer to_id;	//记录id
	private Timestamp t_overtime_start;	//开始时间
	private Timestamp t_overtime_end;	//结束时间
	private String t_o_reason;	//发起原因
	
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