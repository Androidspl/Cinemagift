package com.skyfilm.owner.sina;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.RegisterActivity;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.sina.openapi.UsersAPI;
import com.skyfilm.owner.user.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class AuthListener implements WeiboAuthListener {

	private Oauth2AccessToken mAccessToken;
	private Context context;
	/** 显示认证后的信息，如 AccessToken */
	private TextView mTokenText;
	private String access_token;
	private static final int LOGIN_IN = 0x11;
	private static final int USER_INFO = 0x12;
	private String Open_id;
	private String name;
	private String avatarHd;
	private UserBiz userBiz;
	private String is_bind;

	public AuthListener(Context context) {
		this.context = context;
	}

	@Override
	public void onComplete(Bundle values) {
		// 从 Bundle 中解析 Token
		mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		access_token = values.getString("access_token");
		String nick_name = values.getString("com.sina.weibo.intent.extra.NICK_NAME");
		String userName = values.getString("userName");
		String uid = values.getString("uid");
		String user_icon = values.getString("com.sina.weibo.intent.extra.USER_ICON");
		// 从这里获取用户输入的 电话号码信息
		String phoneNum = mAccessToken.getPhoneNum();
		// values.getst
		if (mAccessToken.isSessionValid()) {
			// 显示 Token
			// updateTokenView(false);
			// 保存 Token 到 SharedPreferences
			AccessTokenKeeper.writeAccessToken(context, mAccessToken);
			Toast.makeText(context, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
		} else {
			// 以下几种情况，您会收到 Code：
			// 1. 当您未在平台上注册的应用程序的包名与签名时；
			// 2. 当您注册的应用程序包名与签名不正确时；
			// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
			String code = values.getString("code");
			String message = context.getString(R.string.weibosdk_demo_toast_auth_failed);
			if (!TextUtils.isEmpty(code)) {
				message = message + "\nObtained the code: " + code;
			}
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			Log.d("Tag", "message=" + message);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				UsersAPI usersAPI = new UsersAPI(context, Const.SINA_APP_KEY, mAccessToken);
				usersAPI.show(Long.valueOf(mAccessToken.getUid()), new RequestListener() {

					@Override
					public void onWeiboException(WeiboException arg0) {
						Log.d("Tag", arg0.toString());
					}

					@Override
					public void onComplete(String response) {
						try {
							JSONObject jsonObject = new JSONObject(response);
							Open_id = jsonObject.getString("idstr");// 唯一标识符(uid)
							name = jsonObject.getString("name");// 姓名
							avatarHd = jsonObject.getString("avatar_hd");// 头像
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}).start();
	}

	@Override
	public void onCancel() {
		Toast.makeText(context, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onWeiboException(WeiboException e) {
		Toast.makeText(context, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		Log.d("Tag", "onWeiboException=" + "onWeiboException");
	}

	/**
	 * 显示当前 Token 信息。
	 * 
	 * @param hasExisted
	 *            配置文件中是否已存在 token 信息并且合法
	 */
	private void updateTokenView(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = context.getString(R.string.weibosdk_demo_token_to_string_format_1);
		// mTokenText.setText(String.format(format, mAccessToken.getToken(),
		// date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted) {
			message = context.getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
		}
		// mTokenText.setText(message);
	}
}
