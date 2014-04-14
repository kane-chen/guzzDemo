package com.lasho.guzz.routing;

import org.guzz.exception.GuzzException;
import org.guzz.orm.AbstractShadowTableView;

public class TableNameShadowView extends AbstractShadowTableView {

	public String toTableName(Object tableCondition) {
		if (tableCondition == null) { // Check condition
			throw new GuzzException("null table conditon is not allowed.");
		}
		if(tableCondition instanceof TableCondition){
			TableCondition condition = (TableCondition) tableCondition ;
			return condition.getTableName() ;
		}
		throw new GuzzException("tableCondition param-error");
	}

}
