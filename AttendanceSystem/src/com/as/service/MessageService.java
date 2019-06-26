package com.as.service;

import java.util.HashMap;
import java.util.List;

import com.as.entity.Message;

public interface MessageService {
	//������Ϣ�������ز���
	public int insertNewMessage(HashMap<String,Object> insertMap);
		
	//�޸���ټ�¼
	public void updateMessage(HashMap<String,Object> updateMap);
	
	
	//ɾ����ټ�¼
	public void deleteMessage(Integer m_id); 
	
	//�����Ѷ���Ϣ�޸�
	public List<Message> selectReadMessageApply(Integer s_id);
	
	//����δ����Ϣ
	public List<Message> selectNoReadMessageApply(Integer s_id);
	
	//������Ϣ
	public Message findMessageByMid(Integer m_id);

}