package com.skyfilm.owner.communication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.activity.LoginActivity;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.cache.TempCache;
import com.skyfilm.owner.cache.TempCacheImpl;
import com.skyfilm.owner.cache.TempCache.CacheType;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.GsonUtil;
import com.skyfilm.owner.utils.StringUtil;

import android.content.Intent;

/**
 * 抽象的联网类，构建Webclient,实现网页数据的响应
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractCom {
	protected WebClient webClient;
	protected Gson gson;
	protected SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected TempCache tempCache;

	public AbstractCom() {
		this.webClient = WebClient.getInstance();
		this.gson = GsonUtil.gson();
		formator.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		tempCache = new TempCacheImpl(MainApplication.getInstance());
	}

	/**
	 * 从响应数据中获取Data数据
	 * 
	 * @param jsonRepons
	 * @return
	 * @throws Exception
	 */
	protected String GetResultFromJsonRespons(String jsonRepons) throws CsqException {

		JSONObject jsonData;
		try {
			jsonData = new JSONObject(jsonRepons);
			if ("SUCCESS".equalsIgnoreCase(jsonData.optString("Status"))) {
				return jsonData.optString("Data");
			} else if ("SUCCESS".equalsIgnoreCase(jsonData.optString("status"))) {
				String data = jsonData.optString("data");
				if (StringUtil.isNull(data)) {
					if (jsonData.has("status"))
						jsonData.remove("status");
					if (jsonData.has("msg"))
						jsonData.remove("msg");
					return jsonData.toString();
				}
				return data;
			} else if ("Error".equalsIgnoreCase(jsonData.optString("status"))) {
				throw (new CsqException(jsonData.optInt("ErrorCode"), jsonData.optString("msg")));
			} else if ("1".equals(jsonData.optString("status"))) {
				return jsonRepons;
			} else if ("0".equals(jsonData.optString("status"))) {
				throw (new CsqException(jsonData.optInt("ErrorCode"), jsonData.optString("msg")));
			} else {
				if (jsonData.optInt("ErrorCode") == CsqException.ERROR_USER_NOT_LOGIN) {
					Intent intent = new Intent(MainApplication.getInstance(), LoginActivity.class);
					UserBiz userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
					userBiz.clearCurrentUser();
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					MainApplication.getInstance().startActivity(intent);
				}
				throw (new CsqException(jsonData.optInt("ErrorCode"), jsonData.optString("Message")));
			}
		} catch (JSONException e) {
			throw (new CsqException(CsqException.ERROR_DATA_FORMAT));
		}
	}

	protected void saveData2Cach(String userId, String cId, CacheType type, String data, Object... keys) {
		String key = cId;
		if (keys != null && keys.length > 0) {
			key = key.concat("_").concat(join("_", keys));
		}
		tempCache.updateCache(userId, type, key, data);
	}

	protected String getDataFromCach(String userId, String cId, CacheType type, Object... keys) {
		String key = cId;
		if (keys != null && keys.length > 0) {
			key = key.concat("_").concat(join("_", keys));
		}
		return tempCache.getCache(userId, type, key);
	}

	protected void saveData2Cach(String userId, String cId, CacheType type, String data) {
		tempCache.updateCache(userId, type, cId, String.valueOf(data));
	}

	protected void saveData2Cach(String userId, CacheType type, String data) {
		tempCache.updateCache(userId, type, String.valueOf(data));
	}

	protected String getDataFromCach(String userId, CacheType type) {
		return tempCache.getCache(userId, type);
	}

	/**
	 * 从缓存获取data
	 * 
	 * @param userId
	 * @param cId
	 * @param type
	 * @return
	 */
	protected String getDataFromCach(String userId, String cId, CacheType type) {
		return tempCache.getCache(userId, type, cId);
	}

	public static String join(String join, Object... keys) {
		if (keys == null || keys.length < 1) {
			return "";
		}
		if (join == null) {
			join = ",";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			if (i == (keys.length - 1)) {
				sb.append(keys[i]);
			} else {
				sb.append(keys[i]).append(join);
			}
		}
		return sb.toString();
	}

	/**
	 * 从list数组中获取Data
	 * 
	 * @param data
	 * @param json2Object
	 * @return
	 * @throws CsqException
	 */
	protected <T> List<T> getListFromData(String data, String2Object<T> json2Object) throws CsqException {
		return getListFromData(data, json2Object, null);
	}

	protected <T> List<T> getListFromData(String data, String2Object<T> json2Object, String key) throws CsqException {

		if (StringUtil.isNull(data)) {
			return null;
		}
		List<T> dataList = null;
		JSONObject obj = null;
		JSONArray jArrOfOriginalDatas = null;
		if (!StringUtil.isNull(key)) {
			try {
				jArrOfOriginalDatas = new JSONObject(data).optJSONArray(key);
			} catch (JSONException e) {
			}
		}
		try {
			if (jArrOfOriginalDatas == null)
				jArrOfOriginalDatas = new JSONArray(data);
			if (jArrOfOriginalDatas != null) {
				dataList = new ArrayList<T>();
				for (int i = 0; i < jArrOfOriginalDatas.length(); i++) {
					dataList.add(json2Object.string2Object(jArrOfOriginalDatas.getJSONObject(i).toString()));
				}
			}
		} catch (JSONException e) {
			// L.printStackTrace(e);
			throw new CsqException(CsqException.ERROR_DATA_FORMAT);
		}
		return dataList;
	}
}
