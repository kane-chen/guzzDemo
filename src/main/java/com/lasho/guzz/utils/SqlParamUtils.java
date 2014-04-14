package com.lasho.guzz.utils;

public class SqlParamUtils {

	
	public static String geneLikeParam(String value){
		if(null!=value && !value.trim().equals("")){
			int r = value.indexOf("%");
			if (r >= 0) {
				value = value.replaceAll("%", "/%");
				value = "%" + value + "%" + " ESCAPE '/' ";
			} else {
				value = "%" + value + "%";
			}
			return value ;
		}
		return null ;
	}
	
}
