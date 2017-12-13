package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.cache.TempCache.CacheType;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.RecommendCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 推荐
 * @author min.yuan
 *
 */
public class RecommendComImpl extends AbstractCom implements RecommendCom,String2Object<Recommend>{

	@Override
	public List<Recommend> getRecommendList(String User_id, String Page, String Page_Size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("Page", Page);
			object.put("Page_size", Page_Size);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "Home_Recommend"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public Recommend string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Recommend.class);
	}
	@Override
	public void saveRecommend2Cache(String User_id, List<Recommend> recommend) {
		try {
			String data = "";
			if(recommend != null){
				data = gson.toJson(recommend);
			}
			saveData2Cach(User_id, CacheType.RECOMMEND_PAGE_INFO, data);
		} catch (Exception e) {}		
	}

	@Override
	public List<Recommend> getRecommendFromCache(String userId) {
		try {
			return getListFromData(getDataFromCach(userId,CacheType.RECOMMEND_PAGE_INFO), this);
		} catch (CsqException e) {
			return null;
		}
	}
}
