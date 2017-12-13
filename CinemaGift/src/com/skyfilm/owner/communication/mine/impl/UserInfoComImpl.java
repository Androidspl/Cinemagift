package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.UserInfo;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.UserInfoCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 用户信息
 * 
 * @author min.yuan
 *
 */
public class UserInfoComImpl extends AbstractCom implements UserInfoCom, String2Object<UserInfo> {
	/**
	 * 得到用户信息
	 */
	@Override
	public UserInfo getUserInfo(String User_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "User_Info"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("User_id", User_id);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return string2Object(GetResultFromJsonRespons(resp));
	}

	/**
	 * 编辑用户信息
	 */
	@Override
	public String editProfile(String User_id, String User_name, String Files) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try {
//			obj.put("User_id", User_id);
//			obj.put("User_name", User_name);
			obj.put("User_id", "15810000000ID");
			obj.put("G_title", "标题");
			obj.put("G_negotiable", "1");
			obj.put("G_price", "2500");
			obj.put("G_quality", "1");
			obj.put("G_contact", "1");
			obj.put("G_mobile", "15811111111");
			obj.put("G_community_id", "427A16D4-B1C0-898B-A3ED-2BCB82A774CD");
			obj.put("G_space_id", "空间ID");
			obj.put("G_desc", "最多150个字");
		} catch (JSONException e) {
		}
		ArrayList<String> files = new ArrayList<>();
		files.add(Files);
//		pairs.add(new BasicNameValuePair("A", "Edit_Profile"));
		pairs.add(new BasicNameValuePair("A", "HpLf_FleaMarket_Publish"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs, files);
	}

	@Override
	public UserInfo string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, UserInfo.class);
	}

}
