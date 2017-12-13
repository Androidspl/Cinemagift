package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity{
	private ImageView logo;
	private TextView des;//app name and versions
	private TextView agreement;//用户协议
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("关于");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
}
