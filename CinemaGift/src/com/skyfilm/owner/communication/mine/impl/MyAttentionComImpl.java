package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.MyAttentionCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 我的关注
 * @author min.yuan
 *
 */
public class MyAttentionComImpl extends AbstractCom implements MyAttentionCom,String2Object<MyAttention>{

/**
 * 获取我的关注列表
 */
	@Override
	public List<MyAttention> getMyAttentionList(String User_id, String Page, String Page_size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("User_id", User_id);
			object.put("Page", Page);
			object.put("Page_size", Page_size);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "My_Follow"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}
/**
 * 取消关注
 */
	@Override
	public String cancelAttention(String User_id, String Follow_user_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Follow_user_id", Follow_user_id);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Cancel_Follow"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}
	@Override
	public MyAttention string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, MyAttention.class);
	}

}
