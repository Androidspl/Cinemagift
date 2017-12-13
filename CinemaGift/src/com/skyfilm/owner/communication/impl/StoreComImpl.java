package com.skyfilm.owner.communication.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.ProductInfo;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.StoreCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;

public class StoreComImpl extends AbstractCom implements StoreCom, String2Object<StoreEntity> {

	/**
	 * 获取商城首页信息
	 */
	@Override
	public StoreEntity getStroeIndex(String type) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Shop_Index"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("Type", type);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return string2Object(GetResultFromJsonRespons(resp));
	}

	@Override
	public List<StoreEntity> getStoreBanner() throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "Banner_Load"));
		JSONObject obj = new JSONObject();
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	/**
	 * 返回购物车信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public StoreEntity getCarList(String User_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "CarList"));
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
	 * 绑定订单地址
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public Boolean bindOrderAddress(String Address, String Order_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("A", "CarList"));
		JSONObject obj = new JSONObject();
		try {
			obj.put("Address", Address);
			obj.put("Order_id", Order_id);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String resp = webClient.doPost(Const.EXEC_URL, pairs);
		try {
			JSONObject jsonObject = new JSONObject(resp);
			String status = jsonObject.getString("Status");
			if (status.equals("success")) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public StoreEntity string2Object(String data) throws CsqException {
		if (StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, StoreEntity.class);
	}
}
