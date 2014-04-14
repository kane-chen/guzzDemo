package com.lasho.guzz.routing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.guzz.orm.AbstractCustomTableView;
import org.guzz.orm.mapping.POJOBasedObjectMapping;
import org.guzz.orm.rdms.TableColumn;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.transaction.ReadonlyTranSession;
import org.junit.Assert;

import com.lasho.guzz.domain.custom.Cargo;
import com.lasho.guzz.domain.custom.SpecialProperty;


public class CargoCustomTableView extends AbstractCustomTableView {

	private Map<String, POJOBasedObjectMapping> orms_cache = new HashMap<String, POJOBasedObjectMapping>();


	/**
	 * startup once only the-first-tableCondition
	 */
	@Override
	protected void initCustomTableColumn(POJOBasedObjectMapping mapping,
			Object tableCondition) {
		String cargoName = (String) tableCondition;
		System.out.println("initCustomTableColumn="+cargoName);
		// read the special properties from the master database.
		ReadonlyTranSession session = this.guzzContext.getTransactionManager().openNoDelayReadonlyTran();
		try {
			SearchExpression se = SearchExpression.forLoadAll(SpecialProperty.class);
			se.and(Terms.eq("cargoName", cargoName));
			@SuppressWarnings("unchecked")
			List<Object> properties = session.list(se);

			for (int i = 0; i < properties.size(); i++) {
				SpecialProperty sp = (SpecialProperty) properties.get(i);
				// create the TableColumn with the super helper method.
				TableColumn tc = super.createTableColumn(mapping,sp.getPropName(), 
						sp.getColName(), sp.getDataType(),null);
				// add it to the mapping with the super helper method too.
				super.addTableColumn(mapping, tc);
			}

		} finally {
			session.close();
		}

	}
	
	/**
	 * generate mapping-class
	 */
	public POJOBasedObjectMapping getRuntimeObjectMapping(Object tableCondition) {
		System.out.println("get pojo mapping");
		String cargoName = (String) tableCondition;
		Assert.assertNotNull("tableCondition can't be null", tableCondition);
		if ("all".equals(tableCondition)) {
			return this.getConfiguredMapping();
		}
		
		POJOBasedObjectMapping map = (POJOBasedObjectMapping) this.orms_cache.get(cargoName);
		if (map == null) {
			// create it
			map = super.createRuntimeObjectMapping(cargoName);
			// cache
			this.orms_cache.put(cargoName, map);
		}

		return map;
	}

	/**
	 * table-shards selector
	 */
	public String toTableName(Object tableCondition) {
		 String cargoName = (String) tableCondition ;
		 Assert.assertNotNull("tableCondition can't be null", tableCondition);
		 if("all".equals(cargoName)){
			 cargoName = "book" ;
		 }
         //different tableConditions mapped to different tables.
         return "tb_cargo_" + cargoName;

	}

	/**
	 * data : bean >> table
	 */
	@Override
	public Object getCustomPropertyValue(Object beanInstance, String propName) {
		 System.out.println("getCustomPropertyValue"+propName);
		 Cargo c = (Cargo) beanInstance ;
         return c.getSpecialProps().get(propName) ;
	}

	/**
	 * data : table >> bean
	 */
	@Override
	public void setCustomPropertyValue(Object beanInstance, String propName,Object value) {
		System.out.println("setCustomPropertyValue"+propName+"="+value);
		Cargo c = (Cargo) beanInstance ;
		c.getSpecialProps().put(propName, value) ;
	}

}
