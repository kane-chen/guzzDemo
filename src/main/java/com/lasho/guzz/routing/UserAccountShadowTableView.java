package com.lasho.guzz.routing;

import org.guzz.exception.GuzzException;
import org.guzz.orm.AbstractShadowTableView;
import org.springframework.stereotype.Service;
import com.lasho.guzz.domain.UserAccount;

@Service
public class UserAccountShadowTableView extends AbstractShadowTableView {
	
	public String toTableName(Object tableCondition) {
		if (tableCondition == null) { // Check condition
			throw new GuzzException("null table conditon is not allowed.");
		}
		if(tableCondition instanceof UserAccount){
			UserAccount  acct = (UserAccount) tableCondition ;
			Integer userId = acct.getUserId() ;
			return "tb_account_" + userId.intValue();
		}else{
			throw new GuzzException("invalid param");
		}

	}

}