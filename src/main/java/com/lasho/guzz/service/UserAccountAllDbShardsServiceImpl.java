package com.lasho.guzz.service;

import java.util.ArrayList;
import java.util.List;

import org.guzz.Guzz;
import org.guzz.GuzzContext;
import org.guzz.orm.se.SearchExpression;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.WriteTranSession;

import com.lasho.guzz.domain.UserAccount;
import com.lasho.guzz.routing.EntityMapping;
import com.lasho.guzz.routing.TableCondition;

public class UserAccountAllDbShardsServiceImpl implements UserAccountService {

	private GuzzContext guzzContext ;
	
	private EntityMapping entityMap ;
	
	public GuzzContext getGuzzContext() {
		return guzzContext;
	}

	public void setGuzzContext(GuzzContext guzzContext) {
		this.guzzContext = guzzContext;
	}

	public EntityMapping getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(EntityMapping entityMap) {
		this.entityMap = entityMap;
	}

	
	public int save(UserAccount account) {
		int result = -1 ;
		if(null!=account){
			List<TableCondition> conds = this.getTableCondition(account,true) ;
			WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
			try{
				result =  (Integer)rwSession.insert(account,conds.get(0)) ;
			}finally{
				rwSession.close() ;
			}
		}
		return result ;
	}

	public boolean update(UserAccount account) {
		if(null != account){
			List<TableCondition> conds = this.getTableCondition(account,true) ;
			WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
			try{
				return rwSession.update(account,conds.get(0)) ;
			}finally{
				rwSession.close() ;
			}
		}
		return false;
	}

	public UserAccount findById(Integer id, Integer userId, Integer areaId) {
		ReadonlyTranSession cacheRoSession = guzzContext.getTransactionManager().openDelayReadTran() ;
		return (UserAccount)cacheRoSession.findObjectByPK(UserAccount.class, null) ;
	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> findAllAccounts() {
		List<UserAccount> result = new ArrayList<UserAccount>() ;
		ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran();
		SearchExpression condition = SearchExpression.forLoadAll(UserAccount.class);
		List<TableCondition> conds = this.getTableCondition(null, false) ;
		for(TableCondition cond : conds){
			Guzz.setTableCondition(cond);
			List<UserAccount> tempList = readOnlySession.list(condition);
			result.addAll(tempList) ;
		}
		return result ;
	}
	
	private List<TableCondition> getTableCondition(UserAccount acct,boolean isSpecial){
		List<TableCondition> conds = new ArrayList<TableCondition>() ;
		String tableName = this.getTableName(acct,isSpecial) ;
		List<String> dbNames = this.getDatabaseNames(acct,isSpecial) ;
		for(String dbName : dbNames){
			TableCondition tcond = new TableCondition() ;
			tcond.setSchmName(dbName) ;
			tcond.setTableName(tableName);
			conds.add(tcond) ;
		}
		return conds ;
	}
	
	private String getTableName(UserAccount acct,boolean isSpecial){
		String tableKey = null ;
		if(null == acct){
			if(isSpecial){
				throw new UnsupportedOperationException("not the special table");
			}else{
//			String configTableName = guzzContext.getBusiness("useraccount-all").getTable().getConfigTableName() ;
//			return configTableName ;
				tableKey = "all"; //all: view
			}
		}else{
			tableKey = acct.getUserId() + "" ;
		}
		String tableName = entityMap.getTableMap().get(tableKey) ;
		if(null == tableName){
			throw new RuntimeException("not found mapping table-name");
		}
		return tableName ;
	}
	
	private List<String> getDatabaseNames(UserAccount acct,boolean isSpecial){
		List<String> dbNames = new ArrayList<String>() ;
		if(null==acct){
			if(isSpecial){
				throw new UnsupportedOperationException("not the special database");
			}else{
				for(String dbKey : entityMap.getDbMap().keySet()){
					dbNames.add(entityMap.getDbMap().get(dbKey));
				}
			}
		}else{
			String dbKey = acct.getAreaId() + "" ;
			String dbName = entityMap.getDbMap().get(dbKey) ;
			if(null == dbName){
				throw new RuntimeException("not found mapping dbname");
			}
			dbNames.add(dbName);
		}
		return dbNames ;
	}
}
