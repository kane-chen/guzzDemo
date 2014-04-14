package com.lasho.guzz.service;

import java.util.List;
import java.util.Map;

import com.lasho.guzz.domain.Message;
import com.lasho.guzz.model.MessageCountModel;


public interface MessageService {

	/**
	 * save message
	 * @param message
	 */
	int save(Message message);
	
	/**
	 * find by primary key
	 * @param id
	 * @return
	 */
	Message findById(Integer id) ;
	
	/**
	 * update
	 * @param message
	 * @return
	 */
	boolean update(Message message);
	
	/**
	 * find all
	 * @return
	 */
	List<Message> findAll();
	
	/**
	 * 
	 * @param ids
	 * @param content
	 * @param userIds
	 * @return
	 */
	List<Message> findByConditions(List<Integer> ids, String content,
			List<Integer> userIds,int pageNo,int pageSize) ;
	
	/**
	 * special-sql
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Message> findByCompiledSql(String sql,Map<String,Object> params) ;
	
	/**
	 * count-messages by userid
	 * @param sqlMapKey
	 * @param param
	 * @return
	 */
	List<MessageCountModel> countMessageByUserId(String sqlMapKey,Map<String,Object> param);

	/**
	 * save batch
	 */
	int[] saveBatch(List<Message> messages);
	
}
