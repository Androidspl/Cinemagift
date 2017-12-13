package com.skyfilm.owner.activity;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends BaseThreadActivity implements OnClickListener {

	private TextView register_tel, input_pwd, comfirm_pwd, register_new, center;
	private ImageView left;
	private int count;
	private ScheduledExecutorService service;
	private String code, phone;
	private static final int GET_VALIDE_CODE = 0x101;
	private static final int USER_REGISTER = 0x102;
	private static final int USER_BIND_SINA = 0x103;
	private static final int USER_BIND_WHCHAT = 0x104;
	private static final int LOGIN_IN = 0x105;
	private String Open_type;
	private String Open_id;
	private String Open_pic;
	private String Access_token;
	private String User_name;
	private EditText ident_code;
	private Button tv_ident_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		register_tel = (TextView) findViewById(R.id.register_tel);
		ident_code = (EditText) findViewById(R.id.ident_code);
		// 验证码读秒
		tv_ident_code = (Button) findViewById(R.id.tv_ident_code);
		input_pwd = (TextView) findViewById(R.id.input_pwd);
		comfirm_pwd = (TextView) findViewById(R.id.comfirm_pwd);
		register_new = (TextView) findViewById(R.id.register_new);
		left = (ImageView) findViewById(R.id.iv_left);
		center = (TextView) findViewById(R.id.tv_center);

		Intent intent = getIntent();
		Open_type = intent.getStringExtra("Type");
		Open_id = intent.getStringExtra("uid");
		Access_token = intent.getStringExtra("Access_token");
		Open_pic = intent.getStringExtra("Open_pic");
		User_name = intent.getStringExtra("User_name");
		if (Open_type != null && Open_type.equals("registe")) {
			center.setText("注册");
			register_new.setText("注册");
		} else if (Open_type != null) {
			center.setText("绑定手机号");
			register_new.setText("绑定");
		}
//		center.setTextColor(getResources().getColor(R.color.black));
//		center.setTextSize(18);

		tv_ident_code.setOnClickListener(this);
		register_new.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ident_code:
			if (checkPhone()) {
				tv_ident_code.setEnabled(false);
				count = 60;
				service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleAtFixedRate(new OneMinuteTimerTask(), 1, 1, TimeUnit.SECONDS);
				new CsqRunnable(GET_VALIDE_CODE).start();
			}
			break;

		case R.id.register_new:
			if (checkInput()) {
				if (Open_type != null && Open_type.equals("registe")) {
					new CsqRunnable(USER_REGISTER).start();
					register_new.setEnabled(false);
				}else if (Open_type != null && Open_type.equals("weibo")) {
					new CsqRunnable(USER_BIND_SINA).start();
//					register_new.setEnabled(false);
				}else if (Open_type != null && Open_type.equals("wechat")) {
					new CsqRunnable(USER_BIND_WHCHAT).start();
//					register_new.setEnabled(false);
				}
			}
			break;

		default:
			break;
		}
	}

	private boolean checkInput() {
		if (!checkPhone()) {
			return false;
		}
		if (!checkCode()) {
			return false;
		}
		if (!checkPwd(input_pwd.getText().toString())) {
			return false;
		}
		if (!checkPwd(comfirm_pwd.getText().toString())) {
			return false;
		}
		if (!input_pwd.getText().toString().equals(comfirm_pwd.getText().toString())) {
			CsqToast.show("两次密码不相同", this);
			return false;
		}
		// return userProtocolCheckBox.isChecked();
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

	private boolean checkPwd(String pwd) {
		// pwd = etPwd.getText().toString();
		if (StringUtil.isNull(pwd)) {
			CsqToast.show("密码不能为空", this);
			return false;
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
		phone = register_tel.getText().toString();
		return AppUtils.isMobileNO(phone);
	}

	private boolean checkCodeNoToast() {
		code = ident_code.getText().toString();
		return !StringUtil.isNull(code);
	}

	private boolean checkCode() {
		if (!checkCodeNoToast()) {
			CsqToast.show("请输入验证码", this);
			return false;
		}
		return true;
	}

	private class OneMinuteTimerTask extends TimerTask {

		@Override
		public void run() {
			count--;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					boolean isLast = count <= 0;
					tv_ident_code
							.setText(!isLast ? count + "s" : getResources().getString(R.string.get_valide_code_again));
					if (isLast) {
						tv_ident_code.setEnabled(true);
					}
				}
			});
			if (count <= 0) {
				service.shutdownNow();
			}
		}
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == GET_VALIDE_CODE) {
			String validCode = userBiz.getValidCode(phone);
			Log.d("Tag", validCode.toString());
			return validCode;
		} else if (operate == USER_REGISTER) {
			return userBiz.valideUser(phone, input_pwd.getText().toString(), comfirm_pwd.getText().toString(), code,
					null, null, null, null, null, null, null);
		}else if (operate == USER_BIND_SINA) {
			return userBiz.valideUser(phone, input_pwd.getText().toString(), comfirm_pwd.getText().toString(), code,
					Open_id, "weibo", "", Access_token, "refresh_token", Open_pic, User_name);
		}else if (operate == USER_BIND_WHCHAT) {
			return userBiz.valideUser(phone, input_pwd.getText().toString(), comfirm_pwd.getText().toString(), code,
					Open_id, "wechat", "", Access_token, "refresh_token", Open_pic, User_name);
		}else if (operate == LOGIN_IN) {
			return userBiz.bindlogin(Open_id, Open_type);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && operate == USER_REGISTER) {
			//SharedPrefUtil.getSharedPrefe(userBiz.getCurrentUserID()).edit().putBoolean("isFirst", false).commit();
			Intent intent_login = new Intent(getApplication(), MainActivity.class);
			startActivity(intent_login);
			finish();
		}else if (result && operate == USER_BIND_SINA) {
//			new CsqRunnable(LOGIN_IN).start();
			Intent intent_login = new Intent(getApplication(), MainActivity.class);
			startActivity(intent_login);
			finish();
		}else if (result && operate == USER_BIND_WHCHAT) {
			Intent intent_login = new Intent(getApplication(), MainActivity.class);
			startActivity(intent_login);
			finish();
//			new CsqRunnable(LOGIN_IN).start();
		}else if (result && operate == LOGIN_IN) {
			Intent intent_login = new Intent(getApplication(), MainActivity.class);
			startActivity(intent_login);
			finish();
		}
		return false;
	}

}
