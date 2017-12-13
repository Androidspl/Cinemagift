package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.mine.FeedBackCom;
import com.skyfilm.owner.exception.CsqException;

/**
 * 反馈信息
 * @author min.yuan
 *
 */
public class FeedBackComImpl extends AbstractCom implements FeedBackCom{

	@Override
	public String feedBack(String User_id, String Content) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Content", Content);
		}
		catch(JSONException e){
		}
		pairs.add(new BasicNameValuePair("A", "Feed_Back"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs);
	}

}
