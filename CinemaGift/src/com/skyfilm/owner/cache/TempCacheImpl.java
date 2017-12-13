package com.skyfilm.owner.cache;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class TempCacheImpl implements TempCache {

	private static final String VALUE = "VALUE";
	private static final String TIME = "TIME";
	
	private ACache mCache;
	public TempCacheImpl(Context context){
		mCache = ACache.get(context);
	}
	
	@Override
	public String getCache(String uid, CacheType type, String key) {
		try {
			JSONObject obj = mCache.getAsJSONObject(createKey(uid, type, key));		
			if(obj != null){
				return obj.optString(VALUE);
			}
		} catch (Exception e) {
		}
		return "";
		
	}

	@Override
	public Date getRefreshtime(String uid, CacheType type, String key) {
		JSONObject obj = mCache.getAsJSONObject(createKey(uid, type, key));		
		if(obj !=null){
			return new Date(obj.optLong(TIME));
		}
		return null;		
	}

	@Override
	public void updateCache(String uid, CacheType type, String key, String cache) {
		JSONObject obj = new JSONObject();
		try {
			obj.put(TIME, new Date().getTime());
			obj.put(VALUE, cache);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mCache.put(createKey(uid, type, key), obj);
	}
	@Override
	public String getCache(String uid, CacheType type) {
		return getCache(uid, type,"");
	}

	@Override
	public void updateCache(String uid, CacheType type, String cache) {
		 updateCache(uid, type, "", cache);
	}

	@Override
	public Date getRefreshtime(String uid, CacheType type) {
		return getRefreshtime(uid, type);
	}
	
	private String createKey(String uid, CacheType type, String key){
		StringBuilder sb = new StringBuilder(uid);
		sb.append("_");
		sb.append(type.name());
		sb.append("_");	
		sb.append(key);
		return sb.toString();
	}
}
