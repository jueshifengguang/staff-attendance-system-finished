package com.as.entity;

import java.sql.Timestamp;

/**
 * ���������
 * @author dzz
 * 2019��6��10�� ����11:02:56
 * AttendanceSystem
 */
public class WorkCondition {

	//��Ӧ���������գ�����һ��Ĺ������
	private Integer s_id;//Ա��id
	private String s_name;	//Ա������
	private Integer dep_id;//����id
	private Timestamp working_start;//����ʵ�ʿ�ʼʱ��
	private Timestamp working_end;//����ʵ�ʽ���ʱ��
	private String attendence_status;//����״̬��1������ǩ����2���ٵ���3�����ˣ�4���ֳٵ������ˣ�5����٣�6��������7����(������������˾û��ϵ)��
	
	//��Ӧ�ü�¼��Ӧ�õ��ϰ�ʱ���𣿣�������������������
	
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