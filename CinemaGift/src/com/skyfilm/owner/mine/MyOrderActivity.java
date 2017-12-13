package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.homepage.CrowdFundingFragment;
import com.skyfilm.owner.homepage.HomePageFragment;
import com.skyfilm.owner.homepage.MineFragment;
import com.skyfilm.owner.homepage.StoreFragment;
import com.skyfilm.owner.page.OrderAllPage;
import com.skyfilm.owner.page.OrderEvaluatePage;
import com.skyfilm.owner.page.OrderPayforPage;
import com.skyfilm.owner.page.OrderReceivePage;
import com.skyfilm.owner.page.OrderSendPage;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
/**
 * 我的订单
 * @author min.yuan
 *
 */
public class MyOrderActivity extends BaseThreadActivity implements OnCheckedChangeListener {

	private RadioGroup tag_title;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		initView();
		initData();
	}

	private void initView() {
		tag_title = (RadioGroup) findViewById(R.id.tag_title);
	}

	private void initData() {
		tag_title.setOnCheckedChangeListener(this);
		ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fl_container, new OrderAllPage()).commit();
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		// TODO Auto-generated method stub
		super.initTiltle(left, title, right1, right2);
		title.setText("我的订单");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.all:// 全部 
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fl_container, new OrderAllPage()).commit();
			break;
		case R.id.payfor:// 待付款
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fl_container, new OrderPayforPage()).commit();
			break;
		case R.id.send:// 待发货
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fl_container, new OrderSendPage()).commit();
			break;
		case R.id.receive:// 待收货
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fl_container, new OrderReceivePage()).commit();
			break;
		case R.id.evaluate:// 待评价
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fl_container, new OrderEvaluatePage()).commit();
			break;
		default:
			break;

		}
	}
}
