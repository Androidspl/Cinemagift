package com.skyfilm.owner.mine;

import java.util.HashMap;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.AddressBiz;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.SpUtils;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.MyDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 添加地址
 * 
 * @author min.yuan
 *
 */
public class AddAddressActivity extends BaseThreadActivity implements TextWatcher {

	private EditText et_receive_name;
	private EditText et_receive_address;
	private EditText et_receive_phoneNum;
	private Button commit;
	private UserBiz userBiz;
	private ImageView set_default;
	private boolean is_default = false;
	private AddressBiz addressBiz;
	private HashMap<String, String> addAddress;
	private static final int COMMIT = 0X0102;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		initView();
		initData();
	}

	private void initView() {
		et_receive_name = (EditText) findViewById(R.id.et_receive_name);
		et_receive_address = (EditText) findViewById(R.id.et_receive_address);
		et_receive_phoneNum = (EditText) findViewById(R.id.et_receive_phoneNum);
		set_default = (ImageView) findViewById(R.id.set_default);
		commit = (Button) findViewById(R.id.commit);
	}


	private void set_commit_status() {
		String name = et_receive_name.getText().toString().trim();
		String address = et_receive_address.getText().toString().trim();
		String phonenum = et_receive_phoneNum.getText().toString().trim();
		if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(address)) && (!TextUtils.isEmpty(phonenum))) {
			commit.setBackgroundResource(R.color.mine_banner_point_s);
			commit.setEnabled(true);
		} else {
			commit.setBackgroundResource(R.color.mine_line_f0);
			commit.setEnabled(false);
		}
	}

	private void initData() {
		userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
		et_receive_name.addTextChangedListener(this);
		et_receive_address.addTextChangedListener(this);
		et_receive_phoneNum.addTextChangedListener(this);
		addressBiz = (AddressBiz) CsqManger.getInstance().get(Type.ADDRESSBIZ);
		addAddress = new HashMap<>();
		set_default.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (is_default) {
					is_default = false;
					set_default.setBackgroundResource(R.drawable.acquiesce_off);
				} else {
					is_default = true;
					set_default.setBackgroundResource(R.drawable.acquiesce_on);
				}
			}
		});
		commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (checkNull()) {
				// TODO 提交： 姓名，收货地址，电话，默认状态'is_default设置默认地址字段，1默认，2非默认'
				if (!AppUtils.isMobileNO(et_receive_phoneNum.getText().toString().trim())) {
					CsqToast.show("请输入正确的手机号", AddAddressActivity.this);
				} else {
					// {"Address":"1c","Name":"2a","Mobile":"15512364578","User_id":"2a","Is_default":"1"}
					addAddress.put("Name", et_receive_name.getText().toString().trim());
					addAddress.put("Mobile", et_receive_phoneNum.getText().toString().trim());
					addAddress.put("Address", et_receive_address.getText().toString().trim());
					if (is_default) {
						addAddress.put("Is_default", "1");
					} else {
						addAddress.put("Is_default", "2");
					}
					new CsqRunnable(COMMIT, addAddress).start();
				}
			}
			// }
		});

	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("添加收货地址");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == COMMIT) {
			User user = userBiz.getCurrentUser();
			addressBiz.addAddress(user.getUser_id(), (HashMap<String, String>) objs[0]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (operate == COMMIT && result) {
			MyDialog.show(this, "提醒", "地址新增成功", "确定", "取消", new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Const.ADD_ADDRESS_REFRESH);
					LocalBroadcastManager.getInstance(AddAddressActivity.this).sendBroadcast(intent);
					Intent intent1 = new Intent(AddAddressActivity.this, MyAddressActivity.class);
					startActivity(intent1);
					finish();
				}
			}, new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Const.ADD_ADDRESS_REFRESH);
					LocalBroadcastManager.getInstance(AddAddressActivity.this).sendBroadcast(intent);
				}
			});
		}
		return false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		set_commit_status();
	}
	@Override
	public void afterTextChanged(Editable s) {
		
	}

}
