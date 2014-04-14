package com.lasho.guzz.service;

import java.util.List;
import java.util.Map;

import org.guzz.Guzz;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.SearchTerm;
import org.guzz.orm.se.Terms;
import org.guzz.orm.sql.BindedCompiledSQL;
import org.guzz.orm.sql.CompiledSQL;
import org.guzz.orm.sql.CompiledSQLBuilder;
import org.guzz.transaction.TransactionManager;

import com.lasho.guzz.domain.Message;
import com.lasho.guzz.model.MessageCountModel;


public class MessageServiceDaoImpl extends GuzzBaseDao implements
		MessageService {

	public int save(Message message) {
		if(null!=message){
			return (Integer)super.insert(message,message.getUserId()) ;
		}
		return 0;
	}

	public Message findById(Integer id) {
		if(null!=id){
			Guzz.setTableCondition(id) ;
			return (Message)super.getForRead(Message.class, id) ;
		}
		return null;
	}

	public boolean update(Message message) {
		if(null!=message){
			super.update(message,message.getUserId()) ;
			return true ;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Message> findAll() {
		SearchExpression exp = SearchExpression.forLoadAll(Message.class);
		return super.list(exp) ;
	}

	public List<Message> findByConditions(List<Integer> ids, String content,
			List<Integer> userIds, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Message> findByCompiledSql(String sql,
			Map<String, Object> params) {
		TransactionManager tranMan = super.getTransactionManager() ;
		CompiledSQLBuilder sqlBuilder = tranMan.getCompiledSQLBuilder() ;
		CompiledSQL compiledSql = sqlBuilder.buildCompiledSQL(Message.class, sql) ;
		BindedCompiledSQL bindedSql = compiledSql.bind(params) ;
		return super.list(bindedSql, 0, 100) ;
	}

	public List<MessageCountModel> countMessageByUserId(String sqlMapKey,
			Map<String, Object> param) {
		SearchExpression exp = SearchExpression.forClass(Message.class) ;
		for(String prop : param.keySet()){
			SearchTerm term = Terms.eq(prop, param.get(prop)) ;
			exp.and(term) ;
		}
		Long count =  super.count(exp);
		MessageCountModel result = new MessageCountModel() ;
		result.setMessageCount(count.intValue());
		return null;
	}

	public int[] saveBatch(List<Message> messages) {
		// TODO Auto-generated method stub
		return null;
	}

}
