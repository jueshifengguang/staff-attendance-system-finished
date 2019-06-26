package com.as.mapping;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Message;

public interface MessageDao {
			
	//������Ϣ�������ز���
	public int insertNewMessage(HashMap<String,Object> insertMap);
		
	//�޸���Ϣ����
	public void updateMessage(HashMap<String,Object> updateMap);
	
	
	//ɾ����Ϣ
	public void deleteMessage(Integer m_id); 
	
	//�����Ѷ���Ϣ�޸�
	public List<Message> selectReadMessageApply(Integer s_id);
	
	//����δ����Ϣ
	public List<Message> selectNoReadMessageApply(Integer s_id);
	
	//������Ϣ
	public Message findMessageByMid(Integer m_id);
}