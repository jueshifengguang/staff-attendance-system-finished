package com.as.service.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.as.entity.Askforleave;
import com.as.entity.Message;
import com.as.mapping.AskforleaveDao;
import com.as.mapping.MessageDao;
import com.as.service.MessageService;

public class MessageServiceImpl implements MessageService {

	//获得Session会话
	public static SqlSession getSession(){
		try {
			//构建器
			SqlSessionFactoryBuilder sqlSessionBuilder = new SqlSessionFactoryBuilder();
			//读取配置文件
			InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
			//获取会话工厂
			SqlSessionFactory sqlFactory = sqlSessionBuilder.build(inputStream);
			//获取session
			SqlSession session = sqlFactory.openSession();
			
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public int insertNewMessage(HashMap<String, Object> insertMap) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
				//获得session
				SqlSession session = getSession();
				//获取代理对象
				MessageDao messageDao = session.getMapper(MessageDao.class);
				//格式转换
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//新增方法
				HashMap<String, Object> insertMapReal = new HashMap<String, Object>();
				//获得参数		
				Integer sId = 0;
				if(insertMap.get("s_id") != null)
				{
					sId = (Integer)insertMap.get("s_id");
				}
				insertMapReal.put("s_id", sId);
				Integer isRead = 0;
				if(insertMap.get("is_read") != null)
				{
					isRead=(Integer)insertMap.get("is_read");				
				}
				insertMapReal.put("is_read", isRead);
				
				String mTime = "2019-01-01 00:00:00";
				if(insertMap.get("m_time") != null)
				{
					mTime=(String)insertMap.get("m_time");
				}
				Timestamp sqlTime = new Timestamp(sdf.parse(mTime).getTime());
				insertMapReal.put("m_time", sqlTime);
				String mContent = "空";
				if(insertMap.get("m_content") != null)
				{
					mContent = (String)insertMap.get("m_content");
				}
				insertMapReal.put("m_content", mContent);
				messageDao.insertNewMessage(insertMapReal);
				//手动提交session
				session.commit();
				session.close();
				
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

	@Override
	public void updateMessage(HashMap<String, Object> updateMap) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
				//获得session
				SqlSession session = getSession();
				//获取代理对象
				MessageDao messageDao = session.getMapper(MessageDao.class);
				//格式转换
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//更新方法
				HashMap<String, Object> updateMapReal = new HashMap<String, Object>();
		        //获取参数
				Integer mId = 0;
				if(updateMap.get("m_id") != null)
				{
					mId = (Integer)updateMap.get("m_id");
				}
				Integer sId = 0;
				if(updateMap.get("s_id") != null)
				{
					sId = (Integer)updateMap.get("s_id");
				}
				Integer isRead = 0;
				if(updateMap.get("is_read") != null)
				{
					isRead=(Integer)updateMap.get("is_read");
					updateMapReal.put("is_read", isRead);
				}
				String mTime = "2019-01-01 00:00:00";
				if(updateMap.get("m_time") != null)
				{
					mTime=(String)updateMap.get("m_time");
					Timestamp sqlTime = new Timestamp(sdf.parse(mTime).getTime());
					updateMapReal.put("m_time", sqlTime);
				}
				String mContent = "空";
				if(updateMap.get("m_content") != null)
				{
					mContent = (String)updateMap.get("m_content");
					updateMapReal.put("m_content", mContent);
				}
				updateMapReal.put("m_id", mId);
				
				messageDao.updateMessage(updateMapReal);
		
				//手动提交session
				session.commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	@Override
	public void deleteMessage(Integer m_id){
		try {
			// TODO Auto-generated method stub
			//获得session
			SqlSession session = getSession();
			//获取代理对象
			MessageDao messageDao = session.getMapper(MessageDao.class);
			//删除方法
			messageDao.deleteMessage(m_id);
			//手动提交session
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Message> selectReadMessageApply(Integer s_id) {
		try {
				//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			MessageDao messageDao = session.getMapper(MessageDao.class);
			
			//查找方法
			List<Message> messageList = messageDao.selectReadMessageApply(s_id);
			
			session.close();
			
			return messageList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Message> selectNoReadMessageApply(Integer s_id) {
		try {
				//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			MessageDao messageDao = session.getMapper(MessageDao.class);
			
			//查找方法
			List<Message> messageList = messageDao.selectNoReadMessageApply(s_id);
			
			session.close();
			
			return messageList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Message findMessageByMid(Integer m_id) {
		try {
			//获得session	
			SqlSession session =getSession();
		
			//获取代理对象
			MessageDao messageDao = session.getMapper(MessageDao.class);

			
			Message  MessageApply = messageDao.findMessageByMid(m_id);
			/*System.out.println(aflApplyBySid.getS_id() + "   " + 
					aflApplyBySid.getStarting_date() + "   " + 
					aflApplyBySid.getEnding_date() + "   " +
					aflApplyBySid.getLeave_reason() + "   " + 
					aflApplyBySid.getIs_approved() +"   " + aflApplyBySid.getIs_resumed());
					*/
			session.close();
			
			return MessageApply;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
			}
		}
}

