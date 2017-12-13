package com.skyfilm.owner.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.BecomeStylist;
import com.skyfilm.owner.bean.mine.DesignType;
import com.skyfilm.owner.biz.BecomeStylistBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.webView.WebViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 成为设计师
 * 
 * @author min.yuan
 *
 */
public class BecomeStylistActivity extends BaseThreadActivity implements TextWatcher, OnCheckedChangeListener {
	private EditText real_name, real_phoneNum, real_email, des;
	private EditText qq, wx, wb;
	private CheckBox painting, craft, flat, model, colleagues, others;
	private Button next;
	private List<DesignType> designType;
	private Map<String, String> mapType;
	private BecomeStylistBiz becomeStylistBiz;
	private static final int GETDESIGNTYPE = 0x119;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_becomestylist);
		initView();
		initData();
	}

	private void initView() {
		real_name = (EditText) findViewById(R.id.real_name);
		real_phoneNum = (EditText) findViewById(R.id.real_phoneNum);
		real_email = (EditText) findViewById(R.id.real_email);
		des = (EditText) findViewById(R.id.des);

		qq = (EditText) findViewById(R.id.qq);
		wx = (EditText) findViewById(R.id.wx);
		wb = (EditText) findViewById(R.id.wb);

		next = (Button) findViewById(R.id.next);
		
		painting = (CheckBox) findViewById(R.id.painting);
		flat = (CheckBox) findViewById(R.id.flat);
		colleagues = (CheckBox) findViewById(R.id.colleagues);
		model = (CheckBox) findViewById(R.id.model);
		craft = (CheckBox) findViewById(R.id.craft);
		others = (CheckBox) findViewById(R.id.others);
	}

	private void initData() {
		mapType = new HashMap<>();
		next.setBackgroundResource(R.drawable.next_bg_n);
		next.setClickable(false);
		painting.setOnCheckedChangeListener(this);
		flat.setOnCheckedChangeListener(this);
		colleagues.setOnCheckedChangeListener(this);
		model.setOnCheckedChangeListener(this);
		craft.setOnCheckedChangeListener(this);
		others.setOnCheckedChangeListener(this);
		real_name.addTextChangedListener(this);
		real_phoneNum.addTextChangedListener(this);
		real_email.addTextChangedListener(this);
		des.addTextChangedListener(this);
//		becomeStylistBiz = (BecomeStylistBiz) CsqManger.getInstance().get(Type.BECOMESTYLISTBIZ);
		becomeStylistBiz = new BecomeStylistBiz();
		new CsqRunnable(GETDESIGNTYPE).start();
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (check()) {// 进入下一页
					// 保存填写的数据
					List<String> list = new ArrayList<>();
					BecomeStylist data = new BecomeStylist();
					data.setReal_name(real_name.getText().toString().trim());
					data.setReal_phoneNum(real_phoneNum.getText().toString().trim());
					data.setReal_email(real_email.getText().toString().trim());
					data.setDes(des.getText().toString().trim());
					data.setQq(qq.getText().toString().trim());
					data.setWx(wx.getText().toString().trim());
					data.setWb(wb.getText().toString().trim());
					if (painting.isChecked()) {
						list.add(mapType.get(painting.getText().toString().trim()));
					}
					if (craft.isChecked()) {
						list.add(mapType.get(craft.getText().toString().trim()));
					}
					if (flat.isChecked()) {
						list.add(mapType.get(flat.getText().toString().trim()));
					}
					if (model.isChecked()) {
						list.add(mapType.get(model.getText().toString().trim()));
					}
					if (colleagues.isChecked()) {
						list.add(mapType.get(colleagues.getText().toString().trim()));
					}
					if (others.isChecked()) {
						list.add(mapType.get(others.getText().toString().trim()));
					}
					data.setDesign(list);
					Intent intent = new Intent(BecomeStylistActivity.this, BecomeStylistActivity2.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", data);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}

		});
	}

	private void setNextStatus() {
		String name = real_name.getText().toString().trim();
		String phoneNum = real_phoneNum.getText().toString().trim();
		String email = real_email.getText().toString().trim();
		String desString = des.getText().toString().trim();
		boolean edittext_status = (!StringUtil.isNull(name)) && (!StringUtil.isNull(phoneNum))&& (!StringUtil.isNull(email))&& (!StringUtil.isNull(desString));
		boolean checkbox_status = painting.isChecked() || model.isChecked() || craft.isChecked() || others.isChecked()|| colleagues.isChecked()|| flat.isChecked();
		if(edittext_status && checkbox_status){
			next.setBackgroundResource(R.drawable.next_bg_s);
			next.setEnabled(true);
		}else{
			next.setBackgroundResource(R.drawable.next_bg_n);
			next.setEnabled(false);
		}

	}

	private boolean check() {
		if (!AppUtils.isMobileNO(real_phoneNum.getText().toString().trim())) {
			CsqToast.show("请输入正确的电话号码", this);
			return false;
		}
		return true;
	}

	protected void onRightViewClick3(View v) {
		super.onRightViewClick2(v);
		Intent intent = new Intent(BecomeStylistActivity.this, WebViewActivity.class);
		String url = "https://www.baidu.com/";
		intent.putExtra("url", url);
		startActivity(intent);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2,TextView right3) {
		super.initTiltle(left, title, right1, right2);
		title.setText("成为设计师");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.GONE);
		right3.setVisibility(View.VISIBLE);
		right3.setText("@帮助手册");
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (GETDESIGNTYPE == operate) {
			 return becomeStylistBiz.getDesignType();
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(result && GETDESIGNTYPE == operate){
			designType = (List<DesignType>)obj;
			if (designType != null && designType.size() > 0) {
				for (int i = 0; i < designType.size(); i++) {
				DesignType designType2 = designType.get(i);
				mapType.put(designType2.getType_name(), designType2.getType_id());
			}
				painting.setText(designType.get(0).getType_name());
				craft.setText(designType.get(1).getType_name());
				flat.setText(designType.get(2).getType_name());
				model.setText(designType.get(3).getType_name());
				colleagues.setText(designType.get(4).getType_name());
				others.setText(designType.get(5).getType_name());
			}
		}
		return false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setNextStatus();
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		setNextStatus();
	}
}
