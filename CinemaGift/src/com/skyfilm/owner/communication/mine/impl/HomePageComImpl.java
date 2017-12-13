package com.skyfilm.owner.communication.mine.impl;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.cache.TempCache.CacheType;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.HomePageCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;

public class HomePageComImpl extends AbstractCom implements HomePageCom,String2Object<HomePage>{
	private Gson dateGson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
			.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG).create();
	/**
	 * 获取首页信息
	 */
	@Override
	public HomePage getHomePage(String User_id, String Page, String Page_Size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		JSONObject obj = new JSONObject();
		try {
			obj.put("Page", Page);
			obj.put("Page_size", Page_Size);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("A", "Home_Page"));
		pairs.add(new BasicNameValuePair("P",obj.toString()));
		String resp  = webClient.doPost(Const.EXEC_URL, pairs);
		return string2Object(GetResultFromJsonRespons(resp));
	}

/**
 * 点赞
 */
	@Override
	public String praise(String User_id, int Type, String id) throws CsqException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveHomePage2Cache(String User_id, HomePage HomePage) {
			try {
				String data = "";
				if(HomePage != null){
					data = gson.toJson(HomePage,HomePage.class);
				}
				saveData2Cach(User_id, CacheType.HOME_PAGE_INFO, data);
			} catch (Exception e) {}
		
	}

	@Override
	public HomePage getHomePageFromCache(String userId) {
			try {
				return string2Object(getDataFromCach(userId,CacheType.HOME_PAGE_INFO));
			} catch (CsqException e) {
				return null;
			}
	}
	@Override
	public HomePage string2Object(String data) throws CsqException {
		// TODO Auto-generated method stub
		return dateGson.fromJson(data, HomePage.class);
	}
	
	public class DateSerializer implements JsonSerializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.getTime()/1000);
		}
	}
	public class DateDeserializer implements JsonDeserializer<java.util.Date> {

		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			try {
				return new java.util.Date(json.getAsJsonPrimitive().getAsLong()*1000);
			} catch (Exception e) {
				try {
					return formator.parse(json.getAsJsonPrimitive().getAsString());
				} catch (ParseException e1) {
					return null;
				}
			}
		}
	}
}
