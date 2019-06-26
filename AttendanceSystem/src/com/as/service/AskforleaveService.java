package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Askforleave;

public interface AskforleaveService {
	
	//通过请假记录id查找请假记录
	public Askforleave findAskforApplyByAflid(Integer afl_id);

	//通过员工id查找请假记录
	public Askforleave findAskforApplyBySid(Integer s_id);
	
	//新增请假记录，不返回参数
	public int insertNewAskforApply(HashMap<String,Object> insertMap);
		
	//修改请假记录
	public void updateAskApplyforByAflid(HashMap<String,Object> updateMap);
	
	//查询所有请假记录
	public List<Askforleave> selectAllAskforApply();
	
	//删除请假记录
	public void deleteAskforApplyByAflid(Integer afl_id);

	//按部门id查找该部门所有请假申请记录
	public List<Askforleave> selectNoncheckedAskforApplyByDep(Integer dep_id);
	
	//通过员工ID查询所有请假记录
	public List<Askforleave> selectAllAskforApplyBySid(Integer s_id);
	
	//通过当前时间，查找已被通过的请假列表
	public List<Askforleave> selectAskforleaveByNowDate(HashMap<String,Object> selectMap);
}