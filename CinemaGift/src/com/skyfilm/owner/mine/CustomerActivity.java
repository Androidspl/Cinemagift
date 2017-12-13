package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CustomerAdapter;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 客服咨询
 * @author min.yuan
 *
 */
public class CustomerActivity extends BaseThreadActivity{
	private ListView lv_customer;
	private CustomerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		lv_customer = (ListView) findViewById(R.id.lv_customer);
		adapter = new CustomerAdapter(this, null);
		lv_customer.setAdapter(adapter);
	}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("客服");
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

}
