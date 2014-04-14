package com.lasho.guzz.routing;

import org.guzz.exception.GuzzException;
import org.guzz.orm.AbstractShadowTableView;

public class MessageShadowTableView extends AbstractShadowTableView {

	public String toTableName(Object tableCondition) {
		if (tableCondition == null) { // Check condition
			throw new GuzzException("null table conditon is not allowed.");
		}

		Integer userId = (Integer) tableCondition;

		// tb_message_${userId}
		return "tb_message_" + userId.intValue();
	}

}