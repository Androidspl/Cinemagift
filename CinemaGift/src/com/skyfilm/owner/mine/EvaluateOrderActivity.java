package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.OrderBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
/**
 * 订单评价
 * @author min.yuan
 *
 */
public class EvaluateOrderActivity extends BaseThreadActivity implements OnClickListener, TextWatcher, OnCheckedChangeListener {
	private RadioGroup rGroup;
	private RadioButton emoji1, emoji2, emoji3;
	private EditText container;
	private TextView publish_evaluate;
	private OrderBiz orderBiz;
	private static final int EVALUATE = 0x0107;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluate_order);
		initView();
		initData();
	}

	private void initView() {
		emoji1 = (RadioButton) findViewById(R.id.emoji1);
		emoji2 = (RadioButton) findViewById(R.id.emoji2);
		emoji3 = (RadioButton) findViewById(R.id.emoji3);
		container = (EditText) findViewById(R.id.container);
		rGroup = (RadioGroup) findViewById(R.id.rGroup);
		publish_evaluate = (TextView) findViewById(R.id.publish_evaluate);
	}

	private void initData() {
		orderBiz = (OrderBiz)CsqManger.getInstance().get(Type.ORDERBIZ);
		container.addTextChangedListener(this);
		emoji1.setOnCheckedChangeListener(this);
		emoji2.setOnCheckedChangeListener(this);
		emoji3.setOnCheckedChangeListener(this);
		publish_evaluate.setOnClickListener(this);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		// TODO Auto-generated method stub
		super.initTiltle(left, title, right1, right2);
		title.setText("评价订单");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		int emoji = 0;
		String content;
		if(operate == EVALUATE){
			content = (String)objs[1];
			emoji = (int)objs[0];
//			orderBiz.commentOrder(User_id, Order_id, (int)objs[0], (String)objs[1]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(result && operate == EVALUATE){
//			int a ;
//			a = (int)obj;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.publish_evaluate:// 提交评价
			int emoji = 0;
			String content;
			if (!isNull()) {
				if(rGroup.getCheckedRadioButtonId() == emoji1.getId()){
					CsqToast.show("评价非常满意", this);
					emoji = 1;
				}
				else if(rGroup.getCheckedRadioButtonId() == emoji2.getId()){
					CsqToast.show("评价满意", this);
					emoji = 2;
				}
				else if(rGroup.getCheckedRadioButtonId() == emoji3.getId()){
					CsqToast.show("评价一般", this);
					emoji = 3;
				}
				CsqToast.show("提交评价", this);
				content = container.getText().toString().trim();
				new CsqRunnable(EVALUATE, emoji,content).start();
			}
			break;

		default:
			break;
		}

	}

	private boolean isNull() {
		if (TextUtils.isEmpty(container.getText().toString().trim())) {
			CsqToast.show("请输入评价内容", this);
			return true;
		} else if (!(emoji1.isChecked() || emoji2.isChecked() || emoji3.isChecked())) {
			CsqToast.show("请选择印象", this);
			return true;
		}
		return false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setPublishEvaluateStatus();
	}
	@Override
	public void afterTextChanged(Editable s) {
	}
	private void setPublishEvaluateStatus() {
		boolean emojiStatus = (emoji1.isChecked() || emoji2.isChecked() || emoji3.isChecked());
		boolean containerStatus = !TextUtils.isEmpty(container.getText().toString().trim());
		if(emojiStatus && containerStatus){
			publish_evaluate.setEnabled(true);
			publish_evaluate.setBackgroundResource(R.color.mine_banner_point_s);
		}else{
			publish_evaluate.setEnabled(false);
			publish_evaluate.setBackgroundResource(R.color.mine_line_f0);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		setPublishEvaluateStatus();
	}

}
