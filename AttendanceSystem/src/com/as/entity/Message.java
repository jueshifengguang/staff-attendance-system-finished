package com.as.entity;

import java.sql.Timestamp;

public class Message {

	private Integer m_id;
	private Integer s_id;
	private Integer is_read;
	private Timestamp m_time;
	private String m_content;
	
	public Integer getM_id(){
		return m_id;
	}
	public Integer getS_id() {
		return s_id;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public Timestamp getM_time() {
		return m_time;
	}
	public String getM_content() {
		return m_content;
	}
	public void setM_id(Integer m_id) {
		this.m_id = m_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	public void setM_time(Timestamp m_time) {
		this.m_time = m_time;
	}
	public void setM_content(String m_content){
		this.m_content = m_content;
	}
}