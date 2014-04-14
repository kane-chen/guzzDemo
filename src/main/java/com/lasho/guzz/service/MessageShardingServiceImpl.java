package com.lasho.guzz.service;

import java.util.List;
import java.util.Map;

import org.guzz.Guzz;
import org.guzz.GuzzContext;
import org.guzz.orm.se.SearchExpression;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.WriteTranSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.lasho.guzz.domain.Message;
import com.lasho.guzz.model.MessageCountModel;


public class MessageShardingServiceImpl implements MessageService {

	@Autowired
	private GuzzContext guzzContext ;
	
	public int save(Message message) {
		if(null!=message){
			WriteTranSession wsession = guzzContext.getTransactionManager().openRWTran(true) ;
			Integer result = (Integer)wsession.insert(message, message.getUserId());
			return result ;
		}
		return 0;
	}

	public Message findById(Integer id) {
		if(null == id)
			return null;
		ReadonlyTranSession rsession = guzzContext.getTransactionManager().openNoDelayReadonlyTran() ;
		Guzz.setTableCondition(id);
		Message mess = (Message)rsession.findObjectByPK(Message.class, id);
		return mess ;
	}

	public boolean update(Message message) {
		if(null == message)
			return false;
		WriteTranSession wsession = guzzContext.getTransactionManager().openRWTran(true) ;
		return wsession.update(message, message.getUserId()) ;
	}

	public List<Message> findAll() {
		ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran();
		SearchExpression condition = SearchExpression.forLoadAll(Message.class);
		@SuppressWarnings("unchecked")
		List<Message> result = readOnlySession.list(condition);
		return result ;
	}

	public List<Message> findByConditions(List<Integer> ids, String content,
			List<Integer> userIds, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Message> findByCompiledSql(String sql,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MessageCountModel> countMessageByUserId(String sqlMapKey,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] saveBatch(List<Message> messages) {
		// TODO Auto-generated method stub
		return null;
	}

	public GuzzContext getGuzzContext() {
		return guzzContext;
	}

	public void setGuzzContext(GuzzContext guzzContext) {
		this.guzzContext = guzzContext;
	}

}
