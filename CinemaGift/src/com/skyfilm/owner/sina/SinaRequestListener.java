package com.skyfilm.owner.sina;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.skyfilm.owner.utils.L;

import android.util.Log;

public class SinaRequestListener implements RequestListener {

	@Override
	public void onComplete(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			String idStr = jsonObject.getString("idstr");// 唯一标识符(uid)
			Log.d("Tag", "唯一标识符(uid)=" + idStr);
			String name = jsonObject.getString("name");// 姓名
			Log.d("Tag", "唯一标识符(uid)=" + name);
			String avatarHd = jsonObject.getString("avatar_hd");// 头像
			Log.d("Tag", "头像 =" + avatarHd);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onWeiboException(WeiboException arg0) {
		Log.d("Tag", "onWeiboException");
	}

}
