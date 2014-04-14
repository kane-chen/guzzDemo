package com.lasho.guzz.service;

import java.util.List;
import java.util.Map;

import org.guzz.GuzzContext;
import org.guzz.jdbc.ObjectBatcher;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.SearchTerm;
import org.guzz.orm.se.Terms;
import org.guzz.orm.sql.BindedCompiledSQL;
import org.guzz.orm.sql.CompiledSQL;
import org.guzz.orm.sql.CompiledSQLBuilder;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.TransactionManager;
import org.guzz.transaction.WriteTranSession;

import com.lasho.guzz.domain.Message;
import com.lasho.guzz.model.MessageCountModel;
import com.lasho.guzz.utils.SqlParamUtils;


public class MessageServiceImpl implements MessageService {

	private GuzzContext guzzContext ;
	
	public GuzzContext getGuzzContext() {
		return guzzContext;
	}

	public void setGuzzContext(GuzzContext guzzContext) {
		this.guzzContext = guzzContext;
	}
	public int save(Message message) {
		int result = -1 ;
		WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
		try{
			result =  (Integer)rwSession.insert(message) ;
			System.out.println("done"+result);
		}finally{
			rwSession.close() ;
		}
		return result ;
	}

	public Message findById(Integer id) {
		if(null!=id){
			ReadonlyTranSession cacheRoSession = guzzContext.getTransactionManager().openDelayReadTran() ;
			Message message = (Message)cacheRoSession.findObjectByPK(Message.class, id);
			return message ;
		}
		return null;
	}

	public boolean update(Message message) {
		boolean result = false ;
		if(null!=message){
			WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true);
			result = rwSession.update(message) ;
		}
		return result;
	}

	public List<Message> findAll(){
		ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran();
		SearchExpression condition = SearchExpression.forLoadAll(Message.class);
		@SuppressWarnings("unchecked")
		List<Message> result = readOnlySession.list(condition);
//		SearchTerm term = Terms.eq("userId", "2");
//		condition.setCondition(term) ;
//		condition.and(Terms.notNull("content"));
		return result ;
	}
	
	public List<Message> findByConditions(List<Integer> ids, String content,
			List<Integer> userIds,int pageNo,int pageSize){
		ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran() ;
		SearchExpression exp = SearchExpression.forClass(Message.class, pageNo, pageSize) ;
		exp.setSelectedProps("id,content,userId");// * means all
		if(null!=ids && !ids.isEmpty()){
			SearchTerm condition = Terms.in("id", ids);
			exp.and(condition);
		}
		content = SqlParamUtils.geneLikeParam(content) ;
		if(null!=content){
			SearchTerm condition = Terms.like("content", content, true) ;
			exp.and(condition) ;
		}
		if(null!=userIds && !userIds.isEmpty()){
			SearchTerm condition = Terms.in("userId", userIds) ;
			exp.and(condition);
		}
		exp.setPageNo(pageNo);
		exp.setPageSize(pageSize) ;
		exp.setOrderBy("id asc") ;
		@SuppressWarnings("unchecked")
		List<Message> messages =  readOnlySession.list(exp) ;
		return messages ;
	}

	/**
	 * @param sql suchas: select count(*) from @@tableName group by(col1) having @colName = :colValue 
	 */
	public List<Message> findByCompiledSql(String sql, Map<String, Object> params) {
		if(null != sql){
			TransactionManager tranMan = guzzContext.getTransactionManager() ;
			CompiledSQLBuilder sqlBuilder = tranMan.getCompiledSQLBuilder() ;
			BindedCompiledSQL bindedSql = null ;
			CompiledSQL compiledSql = sqlBuilder.buildCompiledSQL(Message.class, sql) ;
			if(null!=params){
				for(String key : params.keySet()){
					if(null!=bindedSql){
						bindedSql.bind(key, params.get(key));
					}else{
						bindedSql = compiledSql.bind(key, params.get(key)) ;
					}
				}
			}
			@SuppressWarnings("unchecked")
			List<Message> records = (List<Message>) tranMan.openNoDelayReadonlyTran().list(bindedSql) ;
			return records ;
		}
		return null;
	}

	public List<MessageCountModel> countMessageByUserId(String sqlMapKey,
			Map<String, Object> param) {
		if(null!=sqlMapKey){
			ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran() ;
			@SuppressWarnings("unchecked")
			List<MessageCountModel> messages = readOnlySession.list(sqlMapKey, param);
			return messages ;
		}
		return null;
	}

	public int[] saveBatch(List<Message> messages) {
		if(null == messages || messages.isEmpty())
			return null;
		else{
			WriteTranSession wSession = guzzContext.getTransactionManager().openRWTran(false);
			ObjectBatcher batcher =  wSession.createObjectBatcher() ;
			for(Message mess : messages){
				batcher.insert(mess) ;
			}
			return batcher.executeUpdate() ;
		}
	}
	
	
	
}
