package com.lasho.guzz.routing;

import org.guzz.connection.AbstractVirtualDBView;
import org.guzz.exception.GuzzException;

public class DatabaseNameShardDBView extends AbstractVirtualDBView {

	public String getPhysicsDBGroupName(Object tableCondition) {
		if (tableCondition == null) { // Check condition
			throw new GuzzException("null table conditon is not allowed.");
		}
		if(tableCondition instanceof TableCondition){
			TableCondition condition = (TableCondition) tableCondition ;
			return condition.getSchmName() ;
		}
		throw new GuzzException("tableCondition param-error");
	}

}