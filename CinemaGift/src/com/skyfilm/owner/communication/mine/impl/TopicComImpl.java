package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.cache.TempCache.CacheType;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.TopicCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 专题
 * @author min.yuan
 *
 */
public class TopicComImpl extends AbstractCom implements TopicCom,String2Object<Topic>{

	@Override
	public List<Topic> getTopicList(String User_id, String Page, String Page_Size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("Page", Page);
			object.put("Page_size", Page_Size);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "Home_Topic"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public Topic string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Topic.class);
	}

	@Override
	public void saveTopic2Cache(String User_id, List<Topic> topic) {
		try {
			String data = "";
			if(topic != null){
//				data = gson.toJson(topic,Topic.class);
				data = gson.toJson(topic);
			}
			saveData2Cach(User_id, CacheType.TOPIC_PAGE_INFO, data);
		} catch (Exception e) {}		
	}

	@Override
	public List<Topic> getTopicFromCache(String userId) {
		try {
			return getListFromData(getDataFromCach(userId,CacheType.TOPIC_PAGE_INFO), this);
		} catch (CsqException e) {
			return null;
		}
	}

}
