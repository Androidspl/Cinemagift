package com.skyfilm.owner.communication.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.GoodsCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;

public class GoodsComImpl extends AbstractCom implements GoodsCom, String2Object<Goods> {
	
	/**
	 * 返回搜索商品信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Goods> getSearchGoods(String Type, String Keyword) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Search_Keyword"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("Type", Type);
			obj.put("Keyword", Keyword);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(resp), this);
	}
	
	@Override
	public Goods string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Goods.class);
	}
}
