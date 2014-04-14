package com.lasho.guzz.service;

import java.util.List;

import org.guzz.Guzz;
import org.guzz.GuzzContext;
import org.guzz.orm.se.SearchExpression;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.WriteTranSession;

import com.lasho.guzz.domain.custom.Cargo;


public class CargoServiceCustomImpl implements CargoService {

private GuzzContext guzzContext ;
	
	public GuzzContext getGuzzContext() {
		return guzzContext;
	}

	public void setGuzzContext(GuzzContext guzzContext) {
		this.guzzContext = guzzContext;
	}
	
	public int save(Cargo cargo,Object tableCondition) {
		int result = -1 ;
		WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
		try{
			result =  (Integer)rwSession.insert(cargo,tableCondition) ;
			System.out.println("done"+result);
		}finally{
			rwSession.close() ;
		}
		return result ;
	}

	public boolean update(Cargo cargo,Object tableCondition) {
		if(null!=cargo){
			WriteTranSession rwSession = guzzContext.getTransactionManager().openRWTran(true) ;
			try{
				Guzz.setTableCondition(tableCondition) ;
				return rwSession.update(cargo,tableCondition) ;
			}finally{
				rwSession.close() ;
			}
		}
		return false;
	}

	public Cargo findById(int cargoId, Object tableCondition) {
		ReadonlyTranSession cacheRoSession = guzzContext.getTransactionManager().openDelayReadTran() ;
		Guzz.setTableCondition(tableCondition) ;
		Cargo cargo = (Cargo)cacheRoSession.findObjectByPK(Cargo.class, cargoId);
		return cargo ;
	}

	public List<Cargo> findAll(Object tableCondition) {
		ReadonlyTranSession readOnlySession = guzzContext.getTransactionManager().openNoDelayReadonlyTran();
		SearchExpression condition = SearchExpression.forLoadAll(Cargo.class);
		Guzz.setTableCondition(tableCondition);
		@SuppressWarnings("unchecked")
		List<Cargo> result = readOnlySession.list(condition);
		return result ;
	}


}
