package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 渠道拓展
 * @author min.yuan
 *
 */
public class ChannelExpandActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand);
	}
	
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("渠道拓展");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
}
