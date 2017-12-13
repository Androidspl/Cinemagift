package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.AddressCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

public class AddressComImpl extends AbstractCom implements AddressCom,String2Object<MyAddress> {

	/**
	 * 添加地址
	 */
	@Override
	public String addAddress(String User_id, HashMap<String, String> addAddress) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try {
			// {"Address":"1c","Name":"2a","Mobile":"15512364578","User_id":"2a","Is_default":"1"}
			obj.put("User_id", User_id);
			obj.put("Name", addAddress.get("Name"));
			obj.put("Mobile", addAddress.get("Mobile"));
			obj.put("Address", addAddress.get("Address"));
			obj.put("Is_default", addAddress.get("Is_default"));
		} catch (JSONException e) {
		}
		pairs.add(new BasicNameValuePair("A", "Add_Address"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String result = webClient.doPost(Const.EXEC_URL, pairs);
		return GetResultFromJsonRespons(result);
	}

	/**
	 * 设置默认地址
	 */
	@Override
	public String setReceiverAddress(String User_id, String Id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("Address_id", Id);
			obj.put("User_id", User_id);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Set_Default"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}
	
	/**
	 * 删除地址
	 */
	@Override
	public String setDelAddress(String Id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		array.put(Id);
		try{
			obj.put("Address_id", array);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Del_Address"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}

	/**
	 * 获取我的地址列表
	 */
	@Override
	public List<MyAddress> getMyAddressList(String user_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("User_id", user_id);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "Address_List"));
//		pairs.add(new BasicNameValuePair("A", "Get_Receiver_Address"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public MyAddress string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, MyAddress.class);
	}

}
