package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.HomePageCom;
import com.skyfilm.owner.communication.mine.InformationCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

public class InformationComImpl extends AbstractCom implements InformationCom,String2Object<Information>{

	@Override
	public List<Information> getInformationList(String User_id, String Page, String Page_Size) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
				JSONObject object = new JSONObject();
				try {
					object.put("Page", Page);
					object.put("Page_size", Page_Size);
				} catch (JSONException e) {
				}
		
				pairs.add(new BasicNameValuePair("A", "NewsLoad"));
				pairs.add(new BasicNameValuePair("P", object.toString()));
				return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public Information string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, Information.class);
	}
}
