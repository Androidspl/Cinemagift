package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.OrderCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 我的订单
 * @author min.yuan
 *
 */
public class OrderComImpl extends AbstractCom implements OrderCom,String2Object<Order
>{

	@Override
	public List<Order> getOrderList(String Page, String Page_size)
			throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
//			object.put("User_id", User_id);
//			object.put("Order_status", Order_status);
			object.put("Page", Page);
			object.put("Page_size", Page_size);
		} catch (JSONException e) {
		}

//		pairs.add(new BasicNameValuePair("A", "My_Order"));
		pairs.add(new BasicNameValuePair("A", "Ap_Appointment_List"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public String cancelOrder(String User_id, String Order_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Order_id", Order_id);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Cancel_Order"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}
	/**
	 * 确认收货
	 */
	@Override
	public String confirmOrder(String User_id, String Order_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Order_id", Order_id);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Confirm_Order"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}
	/**
	 * 评价订单
	 */
	@Override
	public String commentOrder(String User_id, String Order_id, String Staus, String Content) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Order_id", Order_id);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Comment_Order"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}

	@Override
	public Order string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Order.class);
	}

}
