package com.skyfilm.owner.communication.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.CrowdFundingEntity;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.CrowdFundCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;

public class CrowdComImpl extends AbstractCom implements CrowdFundCom, String2Object<CrowdFundingEntity> {

	/**
	 * 获取众筹首页信息
	 */
	@Override
	public List<CrowdFundingEntity> getCrowdFunding(String User_id, String Page, String Page_size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Crowd_List"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("User_id", User_id);
			obj.put("Page", Page);
			obj.put("Page_size", Page_size);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}
	
	@Override
	public List<CrowdFundingEntity> getCrowdFundBanner() throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Banner_Load"));
		JSONObject obj = new JSONObject();
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public CrowdFundingEntity string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, CrowdFundingEntity.class);
	}

}
