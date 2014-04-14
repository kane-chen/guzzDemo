package com.lasho.guzz.routing;

import java.util.Map;

public class EntityMapping {

	private Map<String,String> tableMap ;
	private Map<String,String> dbMap ;
	public Map<String,String> getTableMap() {
		return tableMap;
	}
	public void setTableMap(Map<String,String> tableMap) {
		this.tableMap = tableMap;
	}
	public Map<String,String> getDbMap() {
		return dbMap;
	}
	public void setDbMap(Map<String,String> dbMap) {
		this.dbMap = dbMap;
	}
	
	
}
