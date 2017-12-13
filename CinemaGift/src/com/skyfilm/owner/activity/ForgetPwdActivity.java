package com.skyfilm.owner.activity;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgetPwdActivity extends BaseThreadActivity implements OnClickListener {

	private TextView register_tel, ident_code, input_pwd, comfirm_pwd, reset_pwd;
	private ImageView left;
	private int count;
	private ScheduledExecutorService service;
	private String phone;
	private String code;
	private Button tv_ident_code;
	private static final int GET_VALIDE_CODE = 0x101;
	private static final int USER_REGISTER = 0x102;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// hildActionBar(getSupportActionBar());
		setContentView(R.layout.activity_forgetpwd);
		initView();
	}

	private void initView() {
		register_tel = (TextView) findViewById(R.id.register_tel);
		ident_code = (TextView) findViewById(R.id.ident_code);
		// 验证码读秒
		tv_ident_code = (Button) findViewById(R.id.tv_ident_code);
		input_pwd = (TextView) findViewById(R.id.input_pwd);
		comfirm_pwd = (TextView) findViewById(R.id.comfirm_pwd);
		reset_pwd = (TextView) findViewById(R.id.register_new);
		left = (ImageView) findViewById(R.id.iv_left);

		reset_pwd.setText("重置密码");

		tv_ident_code.setOnClickListener(this);
		reset_pwd.setOnClickListener(this);
	}
	
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("忘记密码");
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
				new CsqRunnable(USER_REGISTER).start();
				reset_pwd.setEnabled(false);
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
			return userBiz.getValidCode(phone);
		} else if (operate == USER_REGISTER) {
			return userBiz.resetUserPwd(phone, input_pwd.getText().toString(), comfirm_pwd.getText().toString(), code);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && operate == USER_REGISTER) {
			//SharedPrefUtil.getSharedPrefe(userBiz.getCurrentUserID()).edit().putBoolean("isFirst", false).commit();
			Intent intent_login = new Intent(getApplication(), LoginActivity.class);
			startActivity(intent_login);
			finish();
		}
		return false;
	}
}
