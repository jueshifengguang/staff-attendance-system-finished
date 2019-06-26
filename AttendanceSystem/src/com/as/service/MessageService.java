package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Message;

public interface MessageService {
	//新增消息，不返回参数
	public int insertNewMessage(HashMap<String,Object> insertMap);
		
	//修改请假记录
	public void updateMessage(HashMap<String,Object> updateMap);
	
	
	//删除请假记录
	public void deleteMessage(Integer m_id); 
	
	//查找已读消息修改
	public List<Message> selectReadMessageApply(Integer s_id);
	
	//查找未读消息
	public List<Message> selectNoReadMessageApply(Integer s_id);
	
	//查找消息
	public Message findMessageByMid(Integer m_id);

}