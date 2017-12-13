package com.skyfilm.owner.communication.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.StoreListCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;
import com.umeng.socialize.utils.Log;

public class StoreListComImpl extends AbstractCom implements StoreListCom, String2Object<Goods> {

	/**
	 * 获取商城首页信息
	 */
	@Override
	public List<Goods> getStroeList(String type, String tag) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Shop_List"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("Type", type);
			obj.put("Tag", tag);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public Goods string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Goods.class);
	}
}
