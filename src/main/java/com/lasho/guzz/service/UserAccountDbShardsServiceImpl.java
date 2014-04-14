package com.lasho.guzz.service;

import java.util.ArrayList;
import java.util.List;

import org.guzz.GuzzContext;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.WriteTranSession;

import com.lasho.guzz.domain.UserAccount;

public class UserAccountDbShardsServiceImpl implements UserAccountService {

	private GuzzContext guzzContext ;
	
	public GuzzContext getGuzzContext() {
		return guzzContext;
	}

	public void setGuzzContext(GuzzContext guzzContext) {
		this.guzzContext = guzzContext;
	}

	public int save(UserAccount account) {
		int result = -1 ;
		WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
		try{
			result =  (Integer)rwSession.insert(account,account) ;
			System.out.println("done"+result);
		}finally{
			rwSession.close() ;
		}
		return result ;
	}

	public boolean update(UserAccount account) {
		if(null != account){
			WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
			try{
				return rwSession.update(account,account) ;
			}finally{
				rwSession.close() ;
			}
		}
		return false;
	}

	public UserAccount findById(Integer id, Integer userId, Integer areaId) {
		ReadonlyTranSession cacheRoSession = guzzContext.getTransactionManager().openDelayReadTran() ;
		return (UserAccount)cacheRoSession.findObjectByPK(UserAccount.class, id) ;
	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> findAllAccounts() {
		List<UserAccount> allAccounts = new ArrayList<UserAccount>() ;
		ReadonlyTranSession cacheRoSession = guzzContext.getTransactionManager().openNoDelayReadonlyTran() ;
		SearchExpression exp = SearchExpression.forLoadAll(UserAccount.class) ;
		exp.and(Terms.notNull("account"));
		for(int i=1;i<=2;i++){
			for(int j=1;j<=3;j++){
				UserAccount acct = new UserAccount() ;
				acct.setAreaId(i);
				acct.setUserId(j);
				exp.setTableCondition(acct);
				List<UserAccount> tempList = (List<UserAccount>)cacheRoSession.list(exp) ;
				allAccounts.addAll(tempList);
			}
		}
		allAccounts = (List<UserAccount>)cacheRoSession.list(exp) ;
		return allAccounts ;
	}

}
