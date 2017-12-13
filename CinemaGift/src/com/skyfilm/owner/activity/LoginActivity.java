package com.skyfilm.owner.activity;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.sina.AccessTokenKeeper;
import com.skyfilm.owner.sina.openapi.UsersAPI;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.widget.MyProgress;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseThreadActivity implements OnClickListener, IWXAPIEventHandler {

	private TextView forget_pwd, login_phone, login_pwd;
	private ImageView log_sina, log_wx;
	private Button login, register;
	/** 新浪微博 */
	private AuthInfo mAuthInfo;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	private String pwd;
	private String phone;
	private CheckBox userProtocolCheckBox;
	private IWXAPI api;
	private String code;
	private static final int LOGIN_IN = 0x11;
	private static final int BIND_WECHAT = 0x12;
	private static final int BIND_WEIBO = 0x13;
	private UMShareAPI mShareAPI;
	private boolean IsFirst = true;
	private User user;
	private String Open_id;
	private String User_name;
	private String Access_token;
	private String Open_pic;
	private User is_bind_wechat;
	private User is_bind_weibo;
	private String name;
	private String avatarHd;
	private Oauth2AccessToken mAccessToken;
	private String access_token;
	private RelativeLayout rl_title;
	private TextView tv_center;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hildActionBar(getSupportActionBar());
		setContentView(R.layout.activity_login);
		user = userBiz.getCurrentUser();
		if (user!=null) {
			Intent intent_login = new Intent(getApplication(),MainActivity.class);
			startActivity(intent_login);
			finish();
		}
		initView();
	}
	
	private void initView() {
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);
		rl_title.setBackgroundColor(getResources().getColor(R.color.white));
		tv_center = (TextView) findViewById(R.id.tv_center);
		tv_center.setText("登陆");
		login_phone = (TextView) findViewById(R.id.login_phone);
		login_pwd = (TextView) findViewById(R.id.login_pwd);
		login = (Button) findViewById(R.id.login);
		login.setEnabled(false);
		register = (Button) findViewById(R.id.register);
		log_sina = (ImageView) findViewById(R.id.log_sina);
		log_wx = (ImageView) findViewById(R.id.log_wx);
		forget_pwd = (TextView) findViewById(R.id.forget_pwd);
		userProtocolCheckBox = (CheckBox) findViewById(R.id.userProtocolCheckBox);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Const.WECHAT_APP_ID, true);
		// 将该app注册到微信
		api.registerApp(Const.WECHAT_APP_ID);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
		log_sina.setOnClickListener(this);
		log_wx.setOnClickListener(this);
		forget_pwd.setOnClickListener(this);
		login_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				login_phone.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
//				if(TextUtils.isEmpty(s)){
//					login_phone.setEnabled(false);
//				}else {
//					login_phone.setEnabled(true);
//				}
//				login_phone.setEnabled(checkPhoneNoToast());
				login.setEnabled(checkInputNoToast());
			}
		});
		login_pwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				login_pwd.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
//				login_pwd.setEnabled(false);
			}

			@Override
			public void afterTextChanged(Editable s) {
//				if(TextUtils.isEmpty(s)){
//					login_pwd.setEnabled(false);
//				}else {
//					login_pwd.setEnabled(true);
//				}
//				login_pwd.setEnabled(checkPwdNoToast());
				login.setEnabled(checkInputNoToast());
			}
		});
		
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			// 检查手机号码的格式是否正确
			if (checkInput()) {
				MyProgress.show("登录中", LoginActivity.this);
				new CsqRunnable(LOGIN_IN).start();
				//IsFirst = false;
			}
			break;

		case R.id.register:
			Intent intent_register = new Intent(getApplication(), RegisterActivity.class);
			intent_register.putExtra("Type", "registe");
			startActivity(intent_register);
			break;

		case R.id.forget_pwd:
			Intent intent_forget_pwd = new Intent(getApplication(), ForgetPwdActivity.class);
			startActivity(intent_forget_pwd);
			break;

		case R.id.log_sina:// 新浪登录
			// 新浪微博
			// 创建微博实例
			// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
			mAuthInfo = new AuthInfo(this, Const.SINA_APP_KEY, Const.REDIRECT_URL, Const.SCOPE);
			mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
			// SSO 授权, 仅客户端LoginActivity
			mSsoHandler.authorizeClientSso(new WeiboAuthListener() {
				
				/** 微博的监听 */
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
								AccessTokenKeeper.writeAccessToken(getApplicationContext(), mAccessToken);
								Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
							} else {
								// 以下几种情况，您会收到 Code：
								// 1. 当您未在平台上注册的应用程序的包名与签名时；
								// 2. 当您注册的应用程序包名与签名不正确时；
								// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
								String code = values.getString("code");
								String message = getString(R.string.weibosdk_demo_toast_auth_failed);
								if (!TextUtils.isEmpty(code)) {
									message = message + "\nObtained the code: " + code;
								}
								Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
								Log.d("Tag", "message=" + message);
							}
							new Thread(new Runnable() {

								@Override
								public void run() {
									UsersAPI usersAPI = new UsersAPI(getApplicationContext(), Const.SINA_APP_KEY, mAccessToken);
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
												new CsqRunnable(BIND_WEIBO).start();
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
					Toast.makeText(getApplicationContext(), R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
				}

				@Override
				public void onWeiboException(WeiboException e) {
					Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
					Log.d("Tag", "onWeiboException=" + "onWeiboException");
				};
			});
			break;

		case R.id.log_wx:
			// 微信登录
			// 测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
			// 打包签名apk,然后才能产生微信的登录
//			 final SendAuth.Req req = new SendAuth.Req();
//			 req.scope = "snsapi_userinfo";
//			 req.state = "wechat_sdk_demo_test";
//			 api.sendReq(req);
			mShareAPI = UMShareAPI.get(getApplication());
			SHARE_MEDIA platform2 = SHARE_MEDIA.WEIXIN;
			mShareAPI.doOauthVerify(LoginActivity.this, platform2, umAuthListener);
			break;

		default:
			break;
		}
	}

	/** 检查输入的手机号和密码格式是否正确 */
	private boolean checkInput() {
		if (!checkPhone()) {
			return false;
		}
		if (!checkPwd()) {
			return false;
		}
		if (!userProtocolCheckBox.isChecked()) {
			CsqToast.show("同意用户须知", this);
			return false;
		}
		return true;
	}

	private boolean checkPhone() {
		if (!checkPhoneNoToast()) {
			CsqToast.show("请输入正确的手机号", this);
			return false;
		} else {
			return true;
		}
	}

	private boolean checkPwd() {
		pwd = login_pwd.getText().toString();
		if (StringUtil.isNull(pwd)) {
			CsqToast.show("密码不能为空", this);
		}
		pwd = pwd.trim();
		if (pwd.length() > 16) {
			CsqToast.show("密码长度不能超过16位", this);
			return false;
		}
		if (pwd.length() < 6 || !pwd.matches(Const.PWD_REG)) {
			CsqToast.show("密码由6-16位字母、数字组成", this);
			return false;
		}
		return true;
	}

	private boolean checkPhoneNoToast() {
		phone = login_phone.getText().toString();
		return AppUtils.isMobileNO(phone);
	}
	
	private boolean checkPwdNoToast(){
		pwd = login_pwd.getText().toString();
		if(StringUtil.isNull(pwd)){
			return false;
		}
		pwd = pwd.trim();
		return pwd.matches(Const.PWD_REG);
	}
	
	private boolean checkInputNoToast(){
		return checkPhoneNoToast()&& checkPwdNoToast();
	}

	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			//goToGetMsg();
			Log.d("Tag", "回调成功");
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			// goToShowMsg((ShowMessageFromWX.Req) req);
			Log.d("Tag", "回调成功");
			break;
		default:
			break;
		}
		// Toast.makeText(this, "回调", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		Bundle bundle = new Bundle();
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			// result = R.string.errcode_success;
			resp.toBundle(bundle);
			Resp sp = new Resp(bundle);
			code = sp.code;
			Toast.makeText(getApplicationContext(), "回调成功", Toast.LENGTH_LONG).show();
			Log.d("Tag", "回调成功");
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			// result = R.string.errcode_cancel;
			Toast.makeText(getApplicationContext(), "回调取消", Toast.LENGTH_LONG).show();
			Log.d("Tag", "回调取消");
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			// result = R.string.errcode_deny;
			Log.d("Tag", "ERR_AUTH_DENIED");
			break;
		default:
			// result = R.string.errcode_unknown;
			break;
		}
	}

	private void goToGetMsg() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}

	private UMAuthListener umAuthListener = new UMAuthListener() {

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Toast.makeText(getApplication(), "成功授权", Toast.LENGTH_SHORT).show();
			Log.d("Tag", data.toString());
			// 用户access_token
			Access_token = data.get("access_token");
			Log.d("Tag", "唯一access_token(access_token)=" + data.get("access_token"));
			Log.d("Tag", "scope(scope)=" + data.get("scope"));
			Log.d("Tag", "refresh_token(refresh_token)=" + data.get("refresh_token"));
			mShareAPI.getPlatformInfo(LoginActivity.this, platform, AuthListener);
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Toast.makeText(getApplication(), "失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_SHORT).show();
		}
	};
	private UMAuthListener AuthListener = new UMAuthListener() {

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Toast.makeText(getApplication(), "成功获取用户信息", Toast.LENGTH_SHORT).show();
			Log.d("Tag", data.toString());
			// 用户id
			Open_id = data.get("openid");
			Log.d("Tag", "唯一标识符(openid)=" + data.get("openid"));
			// 姓名
			User_name = data.get("userName");
			Log.d("Tag", "用户名字(userName)=" + data.get("userName"));
			// 头像
			Open_pic = data.get("headimgurl");
			Log.d("Tag", "头像 (headimgurl)=" + data.get("headimgurl"));
			// 昵称
			Log.d("Tag", "用户昵称(NICK_NAME)=" + data.get("nickname"));
			// access_token
			// Log.d("Tag", "用户token(access_token)=" +
			// data.get("access_token"));
			// 性别 0或1
			Log.d("Tag", "用户性别(sex)=" + data.get("sex"));
			new CsqRunnable(BIND_WECHAT).start();
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Toast.makeText(getApplication(), "失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == LOGIN_IN) {
			return userBiz.login(phone, pwd);
		}else if (operate == BIND_WECHAT) {
			is_bind_wechat = userBiz.bindlogin(Open_id, "wechat");
		}else if (operate == BIND_WEIBO) {
			if (Open_id != null) {
				is_bind_weibo = userBiz.bindlogin(Open_id, "weibo");
			}
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && operate == LOGIN_IN) {
			Intent intent_login = new Intent(getApplication(), MainActivity.class);
			startActivity(intent_login);
			finish();
		}else if (result && operate == BIND_WECHAT) {
			if (is_bind_wechat.getBind_type().equals("2")) {
				Intent intent_bind = new Intent(getApplicationContext(), RegisterActivity.class);
				intent_bind.putExtra("uid", Open_id);
				intent_bind.putExtra("User_name", User_name);
				intent_bind.putExtra("Type", "wechat");
				if (Access_token!=null) {
					intent_bind.putExtra("Access_token", Access_token);
				}
				intent_bind.putExtra("Open_pic", Open_pic);
				startActivity(intent_bind);
			}else {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		}else if (result && operate == BIND_WEIBO) {
			if (is_bind_weibo.getBind_type().equals("2")) {
				Intent intent_bind = new Intent(getApplicationContext(), RegisterActivity.class);
				intent_bind.putExtra("uid", Open_id);
				intent_bind.putExtra("User_name", name);
				intent_bind.putExtra("Type", "weibo");
				if (access_token != null) {
					intent_bind.putExtra("Access_token", access_token);
				}
				intent_bind.putExtra("Open_pic", avatarHd);
				startActivity(intent_bind);
			} else {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		}
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		if (mShareAPI != null) {
			mShareAPI.onActivityResult(requestCode, resultCode, data);
		}
//		finish();
	}
	
}
