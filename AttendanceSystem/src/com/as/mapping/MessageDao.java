package com.as.mapping;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Message;

public interface MessageDao {
			
	//新增消息，不返回参数
	public int insertNewMessage(HashMap<String,Object> insertMap);
		
	//修改消息属性
	public void updateMessage(HashMap<String,Object> updateMap);
	
	
	//删除消息
	public void deleteMessage(Integer m_id); 
	
	//查找已读消息修改
	public List<Message> selectReadMessageApply(Integer s_id);
	
	//查找未读消息
	public List<Message> selectNoReadMessageApply(Integer s_id);
	
	//查找消息
	public Message findMessageByMid(Integer m_id);
}