package com.skyfilm.owner.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import android.content.Context;

public class PropertiesUtils {
	private static final String TAG = "PropertiesUtils";
	private static final String DEFAULT_PROPERTY_NAME = "csq";
	private static final String DEFAULT_SUFFIX = ".properties";
	private static HashMap<String, Properties> cacheMap = new HashMap<String, Properties>();

	private  static Properties getProperties(Context c){
		return getProperties(c, DEFAULT_PROPERTY_NAME);
	}

	private synchronized static Properties getProperties(Context c,String name){
		if(StringUtil.isNull(name)){
			throw new IllegalArgumentException("invalid properties name");
		}
		if(name.endsWith(DEFAULT_SUFFIX)){
			name = name.substring(0, name.length()-DEFAULT_SUFFIX.length());
		}
		Properties properties = cacheMap.get(name);
		if(properties == null){
			properties = new Properties();
			try {
				properties.load(c.getAssets().open(name+DEFAULT_SUFFIX));
				cacheMap.put(name, properties);
			} catch (IOException e) {
//				L.e(TAG, "load properties fail");
			}
		}
		return properties;
	}
	
	public static String getValue(Context c,String name, String tag){
		if(StringUtil.isNull(name)){
			return getValue(c, tag);
		}
		return (String) getProperties(c,name).get(tag);
	}
	
	public static String getValue(Context c, String tag){
		return (String) getProperties(c).get(tag);
	}
}
