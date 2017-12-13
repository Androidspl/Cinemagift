package com.skyfilm.owner.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	/**
	 * 初始化GSON
	 * @return
	 */
	public static Gson gson(){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		return builder.create();
	}
	
	public static Gson gson(String dateFormat){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(dateFormat);
		return builder.create();
	}

}
