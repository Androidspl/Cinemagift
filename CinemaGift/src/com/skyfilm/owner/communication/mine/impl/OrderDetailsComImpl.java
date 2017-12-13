package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.OrderDetails;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.OrderDetailsCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.StringUtil;

public class OrderDetailsComImpl extends AbstractCom implements OrderDetailsCom,String2Object<OrderDetails>{

	/**
	 * 获取订单详情
	 */
	@Override
	public OrderDetails getOrderDetails(List<String> standard,String user_id,String money) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
//		for(int i = 0;i<standard.size();i++){
//			
//		}
		String a = "0ECF8916-A166-417C-A0AA-C72249CE4E1C";
		String b = "187882BF-A22F-46B5-8F2A-CADB81AA3F57";
		JSONArray array = new JSONArray();
		array.put(a);
		array.put(b);
		JSONObject obj = new JSONObject();
		try {
			obj.put("Standard", array);
			obj.put("User_id", user_id);
			obj.put("Money", money);
		} catch (JSONException e) {
			L.printStackTrace(e);
		}
		pairs.add(new BasicNameValuePair("P",obj.toString()));
		pairs.add(new BasicNameValuePair("A","Order_Details"));
		String resp  = webClient.doPost(Const.EXEC_URL, pairs);
		return string2Object(GetResultFromJsonRespons(resp));
	}

	@Override
	public OrderDetails string2Object(String data) throws CsqException {
		return gson.fromJson(data, OrderDetails.class);
	}
	/**
	 * 将以,拼接的路径拆分为一个List
	 * @param str 带拆分的字符串
	 * @return 拆分后的集合
	 */
	public static List<String>convertString2List(String str){
		ArrayList<String> result = null; 
		if(!StringUtil.isNull(str)){
			String [] strs = str.split(",");
			if(strs!=null&&strs.length>0){
				result = new ArrayList<String>();
				for(String s:strs){
					result.add(s);
				}
			}
		}
		return result;
	}

}
