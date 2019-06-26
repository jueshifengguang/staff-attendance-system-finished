package com.as.mapping;
import java.util.HashMap;
import java.util.List;
import com.as.entity.ShiftWork;

public interface ShiftWorkDao {
	//新增工作情况
	public int insertShiftWork(HashMap<String,Object> iswMap);
	
	//删除工作情况	
	public void deleteShiftWork(Integer sw_id);
	
	//更新工作情况
	public void updateShiftWork(HashMap<String,Object> uswMap);
	
	//查询员工该天的打卡状态
	public List<ShiftWork> getAttenStatus(HashMap<String,Object> asMap );
	
	//查询---上班
	//public List<ShiftWork> ableCheckIn(HashMap<String, Object> ciMap);
	//查询可进行下班打卡
	//public List<ShiftWork> ableCheckOff(HashMap<String, Object> coMap);
	
	//查询某月某部门的工作情况
	public List<ShiftWork> selectAllStaffWorkConByMonthByDep(HashMap<String,Object> sallMap);//dep_id,working_start_start该月第一天,working_start_end下月第一天
	//查询某月某部门某员工工作情况
	public List<ShiftWork> selectOneStaffWorkConByMonthByDep(HashMap<String,Object> soneMap);//dep_id,s_id,start,end
	
	//通过记录id查找该记录
	public ShiftWork findShiftWorkByswid(Integer sw_id);
}