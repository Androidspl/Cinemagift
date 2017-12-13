package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.AddressBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 反馈信息
 * @author min.yuan
 *
 */
public class BackInfoActivity extends BaseThreadActivity{
	private EditText backinfo;
	private TextView commit;
	private AddressBiz addressBiz;
	private static final int FEEDBACK= 0x0103;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backinfo);
		addressBiz = (AddressBiz) CsqManger.getInstance().get(Type.ADDRESSBIZ);
		backinfo = (EditText) findViewById(R.id.backinfo);
		commit = (TextView) findViewById(R.id.commit);
		commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(backinfo.getText().toString().trim())){
					CsqToast.show("请输入反馈意见", BackInfoActivity.this);
				}
				else{
					String info = backinfo.getText().toString().trim();
					//TODO 提交
					CsqToast.show("提交", BackInfoActivity.this);
					new CsqRunnable(FEEDBACK, info);
				}
			}
		});
	}
	
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("反馈信息");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if(operate == FEEDBACK){
//			addressBiz.feedBack(User_id, (String)objs[0]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
