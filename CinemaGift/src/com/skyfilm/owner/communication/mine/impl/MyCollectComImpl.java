package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.MyCollect;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.MyCollectCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 我的收藏
 * @author min.yuan
 *
 */
public class MyCollectComImpl extends AbstractCom implements MyCollectCom,String2Object<MyCollect>{

	@Override
	public List<MyCollect> getMyCollectList(String User_id, String Page, String Page_size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("User_id", User_id);
			object.put("Page", Page);
			object.put("Page_size", Page_size);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "My_Collection"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public MyCollect string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, MyCollect.class);
	}

}
