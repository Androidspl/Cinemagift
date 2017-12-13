package com.skyfilm.owner.cache;

import java.util.Date;

public interface TempCache {
	
	public enum CacheType{
		HOME_PAGE_INFO,
		TOPIC_PAGE_INFO,
		RECOMMEND_PAGE_INFO,
	}
	
	public abstract String getCache(String uid, CacheType type, String key);
	public abstract void updateCache(String uid, CacheType type, String key, String cache);
	public abstract Date getRefreshtime(String uid, CacheType type, String key);
	
	public abstract String getCache(String uid, CacheType type);
	public abstract void updateCache(String uid, CacheType type, String cache);
	public abstract Date getRefreshtime(String uid, CacheType type );

}