package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.BecomeStylist;
import com.skyfilm.owner.bean.mine.DesignType;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.BecomeStylistCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

public class BecomeStylistComImpl extends AbstractCom implements BecomeStylistCom,String2Object<DesignType> {
	/**
	 * 
	 * 申请成为设计师
	 */
	@Override
	public String applyDesigner(String User_id, BecomeStylist becomeStylist) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
//		{"User_id":"98B1A078-8673-4268-963F-3BFA0262E287",
//			"Real_name":"测试设计师",
//			"Mobile":"15512345678",
//			"Email":"ceshi@qq.com",
//			"Design_type_id":"05FAD32B-8570-4C1B-9E4E-39F684C835C0",
//			"Desc":"描述",
//			"Qq":"QQ",
//			"Wechat":"weixin",
//			"Weibo":"weibo",
//			"Design_desc":"设计描述"}
		JSONArray array = new JSONArray();
		if(becomeStylist.getDesign() != null && becomeStylist.getDesign().size()>0){
			List<String> design = becomeStylist.getDesign();
			for (int i = 0; i < design.size(); i++) {
				array.put(design.get(i));
			}
		}
		becomeStylist.getDesign();
		try {
			obj.put("User_id", User_id);
			obj.put("Real_name", becomeStylist.getReal_name());
			obj.put("Mobile", becomeStylist.getReal_phoneNum());
			obj.put("Email", becomeStylist.getReal_email());
			obj.put("Design_type_id",array);
			obj.put("Desc", becomeStylist.getDes());
			obj.put("Qq", becomeStylist.getQq());
			obj.put("Wechat", becomeStylist.getWx());
			obj.put("Weibo", becomeStylist.getWb());
			obj.put("Design_desc", becomeStylist.getDesign_des());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<String> files = becomeStylist.getFiles();
		pairs.add(new BasicNameValuePair("A", "Apply_Designer"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String result = webClient.doPost(Const.EXEC_URL, pairs, files);
		return GetResultFromJsonRespons(result);
	}

	/**
	 * 获取设计方向
	 */
	@Override
	public List<DesignType> getDesignType() throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		pairs.add(new BasicNameValuePair("A", "Get_Design_Type"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		String result = webClient.doPost(Const.EXEC_URL, pairs);
		return getListFromData(GetResultFromJsonRespons(result), this);
	}

	@Override
	public DesignType string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, DesignType.class);
	}

}
