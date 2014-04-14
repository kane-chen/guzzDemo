package com.lasho.guzz.routing;

import org.guzz.connection.AbstractVirtualDBView;
import org.guzz.connection.VirtualDBGroup;
import org.guzz.exception.GuzzException;

import com.lasho.guzz.domain.UserAccount;

public class UserAccountShardDBView extends AbstractVirtualDBView {

	public String getPhysicsDBGroupName(Object tableCondition) {
		if (tableCondition == null) { // Check condition
			throw new GuzzException("null table conditon is not allowed.");
		}
		if("all".equals(tableCondition)){
			VirtualDBGroup dbGroup = this.getConfiguredVirtualDBGroup();
			System.out.println(dbGroup.getGroupName());
			return "accountdbg1" ;
		}
		
		if(tableCondition instanceof UserAccount){
			UserAccount  acct = (UserAccount) tableCondition ;
			Integer areaId = acct.getAreaId() ;
			return "accountdbg" + areaId.intValue();
		}else{
			throw new GuzzException("invalid param");
		}
		
		
	}

}