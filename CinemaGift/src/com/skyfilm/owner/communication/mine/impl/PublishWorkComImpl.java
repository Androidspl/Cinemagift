package com.skyfilm.owner.communication.mine.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.bean.mine.FilmTheme;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.PublishWork;
import com.skyfilm.owner.communication.AbstractCom;
import com.skyfilm.owner.communication.String2Object;
import com.skyfilm.owner.communication.mine.PublishWorkCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.StringUtil;

/**
 * 发布作品
 * @author min.yuan
 *
 */
public class PublishWorkComImpl extends AbstractCom implements PublishWorkCom,String2Object<FilmTheme>{

	@Override
	public String publishWork(String User_id, PublishWork publishWork) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject obj = new JSONObject();
		try{
			obj.put("User_id", User_id);
			obj.put("Title", publishWork.getOpus_title());
			obj.put("Desc", publishWork.getOpus_des());
//			obj.put("Files[]", publishWork.getPhoto());
			obj.put("Theme_id", publishWork.getTheme());
			obj.put("Design_Type", publishWork.getType());
			obj.put("Copyright", publishWork.getCopyright());
		}
		catch(JSONException e){
		}
		ArrayList<String> photo = publishWork.getPhoto();
		pairs.add(new BasicNameValuePair("A", "Publish_Work"));
		pairs.add(new BasicNameValuePair("P", obj.toString()));
		return webClient.doPost(Const.EXEC_URL, pairs,photo);
	}

	@Override
	public List<FilmTheme> getFilmThemeList(String User_id) throws CsqException {
		ArrayList<NameValuePair> pairs = new ArrayList<>();
		JSONObject object = new JSONObject();
		try {
			object.put("User_id", User_id);
		} catch (JSONException e) {
		}

		pairs.add(new BasicNameValuePair("A", "Get_Film_Theme"));
		pairs.add(new BasicNameValuePair("P", object.toString()));
		return getListFromData(GetResultFromJsonRespons(webClient.doPost(Const.EXEC_URL, pairs)), this);
	}

	@Override
	public FilmTheme string2Object(String data) throws CsqException {
		if(StringUtil.isNull(data))
			return null;
		return gson.fromJson(data, FilmTheme.class);
	}

}
