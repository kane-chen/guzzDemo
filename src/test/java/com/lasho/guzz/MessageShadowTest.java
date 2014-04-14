package com.lasho.guzz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lasho.guzz.domain.Message;
import com.lasho.guzz.model.MessageCountModel;
import com.lasho.guzz.service.MessageService;


public class MessageShadowTest {

	static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	static MessageService messageService = (MessageService)appContext.getBean("messageShardingService", MessageService.class); 
	
	@Test
	public void testSave(){
		Message message = new Message() ;
		message.setUserId(1);
		message.setContent("message-content") ;
		message.setCreatedTime(new Date());
		int saveResult = messageService.save(message);
		System.out.println(String.format("save result is %s", saveResult));
		Assert.assertTrue(saveResult > 0) ;
	}

	@Test
	public void testUpdate(){
		Message upMess = new Message() ;
		upMess.setId(1) ;
		upMess.setUserId(2);
		upMess.setContent("update-content") ;
		boolean upResult = messageService.update(upMess);
		System.out.println(String.format("update result is %s", upResult));
	}
	
	@Test
	public void testFind(){
		Message findMess = messageService.findById(1) ;
		System.out.println(String.format("find result is %s", findMess.getContent()));
		List<Message> mesList = messageService.findAll() ;
		System.out.println(mesList.size());
	}
	
	@Test
	public void testFindByCondition(){
		List<Integer> ids = new ArrayList<Integer>() ;
		ids.add(1);
		ids.add(2);
		ids.add(3);
		ids.add(4);
		ids.add(5);
		String content = "message" ;
		List<Integer> userIds = new ArrayList<Integer>() ;
		userIds.add(1);
		userIds.add(2);
		@SuppressWarnings("unused")
		List<Message> records = messageService.findByConditions(ids, content, userIds, 1, 2);
	}
	
	@Test
	public void testCompiledSql(){
		String compiledSql = "select @id,@userId,@content from @@message where @userId= :userId and @content like :content" ;
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("userId", "1") ;
		param.put("content", "message%");
		@SuppressWarnings("unused")
		List<Message> result = messageService.findByCompiledSql(compiledSql, param);
	}
	
	@Test
	public void testXmlSql(){
		String sqlMapKey = "countMessagesByUserid" ;
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("userId", "1") ;
		param.put("content", "message%");
		@SuppressWarnings("unused")
		List<MessageCountModel> result = messageService.countMessageByUserId(sqlMapKey, param);
	}
	
	@Test
	public void testBatch(){
		List<Message> messages = new ArrayList<Message>() ;
		for(int i=10;i<=15;i++){
			Message mess = new Message() ;
			mess.setId(i);
			mess.setContent("content"+i) ;
			mess.setUserId(i % 7) ;
			mess.setCreatedTime(new Date()) ;
			messages.add(mess) ;
		}
		int[] result = messageService.saveBatch(messages);
		System.out.println(result);
	}
}
