package com.as.entity;

import java.sql.Timestamp;

public class Staff {
	private Integer s_id;
	private String s_name;
	private Integer dep_id;
	private Timestamp entry_time;
	private Integer identity;
	private String s_pass;
	public Integer getS_id() {
		return s_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id=s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name=s_name;
	}
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public Timestamp getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(Timestamp entry_time) {
		this.entry_time = entry_time;
	}
	public Integer getIdentity() {
		return identity;
	}
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	public String getS_pass() {
		return s_pass;
	}
	public void setS_pass(String s_pass) {
		this.s_pass = s_pass;
	}
}