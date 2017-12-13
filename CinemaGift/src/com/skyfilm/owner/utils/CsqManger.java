package com.skyfilm.owner.utils;

import java.util.HashMap;
import java.util.Map;

import com.skyfilm.owner.MainApplication;


public class CsqManger {
	
	private static class CsqMangerHolder{
		private static final CsqManger INSTANCE = new CsqManger();
	} 
	
	public enum Type{
		USERDAO,
		USERCOM,
		USERBIZ,
		
		HOMEPAGEBIZ,
		HOMEPAGECOM,
		HOMEPAGE,
		
		ADDRESSCOM,
		ADDRESSBIZ,
		
		BECOMESTYLISTCOM,
		BECOMESTYLISTBIZ,
		
		FEEDBACKCOM,
		
		PUBLISHWORKCOM,
		
		ORDERCOM,
		ORDERBIZ,
		ORDERDETAILSCOM,
		
		USERINFOCOM,
		USERINFOBIZ,
		
		INFORMATIONCOM,
		TOPICCOM,
		CROWDFUNDINGGOODSCOM,
		MYATTENTIONCOM,
		MYCOLLECTCOM,
		MYCOLLECTMONEYCOM,
		MYLOVECOM,
		NOTICECOM,
		ONSELLCOM,
		RECOMMENDCOM,
		/** 商场  */
		STORECOM,
		STOREBIZ,
		STORELISTCOM,
		STORELISTBIZ,
		/** 众筹 */
		CROWDFUNDCOM,
		CROWDFUNDBIZ,
		/** 搜索 */
		GOODSCOM,
		GOODSBIZ
		
	}
	private Map<String, Object> map = new HashMap<String, Object>();
	public static CsqManger getInstance(){
		return CsqMangerHolder.INSTANCE;
	}
	private CsqManger(){};
	
	public Object get(Type type) {
		Object obj = this.map.get(type.name());
		if (obj == null) {
			obj = init(type);
			map.put(type.name(), obj);
		}
		return obj;
	}

	private Object init(Type type) {

		Object obj = null;
		String name = PropertiesUtils.getValue(MainApplication.getInstance(), type.name());
		try {
			name = name.trim();
			obj = Class.forName(name).newInstance();
		} catch (Exception e) {
			L.printStackTrace(e);
		}
		return obj;
	}
}
