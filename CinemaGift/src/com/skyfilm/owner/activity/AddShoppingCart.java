package com.skyfilm.owner.activity;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.GoodsClassifyAdapter;
import com.skyfilm.owner.adapter.GoodsStyleAdapter;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.widget.MyPopWindows;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AddShoppingCart extends BaseThreadActivity implements OnClickListener {

	private ImageView up_down_arrow;
	private MyPopWindows my_popupwindow;
	private View goods_style;
	private ListView lv_goods_style;
	private LinearLayout ll_goods_style;
	private GoodsStyleAdapter goodsstyleAdapter;
	private TextView tv_goods_style;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_shopping_cart);
		initView();
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		// left.setVisibility(View.VISIBLE);
		title.setVisibility(View.GONE);
	}

	private void initView() {
		up_down_arrow = (ImageView) findViewById(R.id.up_down_arrow);
		ll_goods_style = (LinearLayout) findViewById(R.id.ll_goods_style);
		tv_goods_style = (TextView) findViewById(R.id.tv_goods_style);

		/** 初始化产品分类信息数据 */
		my_popupwindow = new MyPopWindows();
		my_popupwindow.setHeight(192 * 2);
		/** pop */
		goods_style = LayoutInflater.from(getApplicationContext()).inflate(R.layout.goods_classify, null);
		lv_goods_style = (ListView) goods_style.findViewById(R.id.lv_goods_classify);
		goodsstyleAdapter = new GoodsStyleAdapter(getApplicationContext());
		//goodsstyleAdapter.setStrings(goods);
		lv_goods_style.setAdapter(goodsstyleAdapter);
		lv_goods_style.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv_goods = (TextView) view.findViewById(R.id.tv_goods);
				tv_goods_style.setText(tv_goods.getText().toString());
			}
		});

		up_down_arrow.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(my_popupwindow!=null && my_popupwindow.isShowing()){
			my_popupwindow.dismiss();
			return;
		}
		switch (view.getId()) {
		case R.id.up_down_arrow:
			my_popupwindow.setOutsideTouchable(false);
			my_popupwindow.setContentView(goods_style);
			my_popupwindow.showAsDropDown(ll_goods_style);
			my_popupwindow.setFocusable(true); // 设置PopupWindow可获得焦点
			my_popupwindow.setTouchable(true); // 设置PopupWindow可触摸
			break;

		default:
			break;
		}
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
