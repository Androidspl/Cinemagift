package com.skyfilm.owner.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CheckAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.OrderDetailsActivity;
import com.skyfilm.owner.myinterface.CheckInterface;
import com.skyfilm.owner.myinterface.DelectInterface;
import com.skyfilm.owner.myinterface.ModifyCountInterface;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.xlistview.XListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingCartActivity extends BaseListActivity<StoreEntity>
		implements OnClickListener, ModifyCountInterface, CheckInterface, DelectInterface {

	private CheckAdapter shoppingcartAdapter;
	private CheckBox cb_check_all;
	private List<StoreEntity> mlist = new ArrayList<StoreEntity>();
	private TextView tv_total_price, tv_go_to_pay;
	private double totalPrice = 0.00;// 购买的商品总价
	private StoreBiz storeBiz;
	private StoreEntity carListEntity;

	/**
	 * 模拟数据<br>
	 * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
	 * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
	 */
	private void virtualData() {

		for (int i = 0; i < 10; i++) {

			StoreEntity StoreEntity = new StoreEntity(i + "", i + 10 + "寸" + i + 20 + "cm", "imageUrl",
					"第八号当铺" + "的第" + (i + 1) + "个商品", 120.45 + i * 2, 1);
			mlist.add(StoreEntity);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		storeBiz = (StoreBiz) CsqManger.getInstance().get(Type.STOREBIZ);
	}

	@Override
	protected void initView() {
		virtualData();
		super.initView();
		setContentView(R.layout.activity_shopping_cart);
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);
		/** 结账 */
		tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
		cb_check_all = (CheckBox) findViewById(R.id.all_chekbox);
		listView = (XListView) findViewById(R.id.lv_store);
		
		cb_check_all.setOnClickListener(this);
		tv_go_to_pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
				startActivity(intent);
			}
		});
		
		setEventOption();
		
	}

	private void dataChanged() {
		// 通知listView刷新
		shoppingcartAdapter.notifyDataSetChanged();
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("购物车");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		/** 全选 */
		case R.id.all_chekbox:
			doCheckAll();
			break;

		default:
			break;
		}
		
	}

	/** 全选与反选 */
	private void doCheckAll() {
		if (shoppingcartAdapter != null) {
			for (int i = 0; i < mlist.size(); i++) {
				mlist.get(i).setChoosed(cb_check_all.isChecked());
			}
			calculate();
			dataChanged();
		}
	}

	@Override
	protected void onListItemClick(StoreEntity item) {

	}

	@Override
	protected int getPage() {
		return 0;
	}

	@Override
	protected List<StoreEntity> getDataListFromCache(String userId, String cid) {
		return null;
	}

	@Override
	protected List<StoreEntity> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
//		carListEntity = storeBiz.getCarList(userBiz.getCurrentUserID());
		return mlist;
	}

	@Override
	protected BaseAdapter getListAdapter(List<StoreEntity> data) {
		if (shoppingcartAdapter == null) {
			shoppingcartAdapter = new CheckAdapter(mlist, context);
		}
		shoppingcartAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
		shoppingcartAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
		shoppingcartAdapter.setDelectGoodsInterface(this);// 关键步骤3,设置删除商品的接口
		listView.setDivider(null);
		listView.setAdapter(shoppingcartAdapter);
		return shoppingcartAdapter;
	}

	@Override
	public void doIncrease(int position, View showCountView, boolean isChecked) {

		StoreEntity product = (StoreEntity) shoppingcartAdapter.getItem(position);
		int currentCount = product.getCount();
		currentCount++;
		product.setCount(currentCount);
		((TextView) showCountView).setText(currentCount + "");

		dataChanged();
		calculate();
	}

	@Override
	public void doDecrease(int position, View showCountView, boolean isChecked) {
		StoreEntity product = (StoreEntity) shoppingcartAdapter.getItem(position);
		int currentCount = product.getCount();
		if (currentCount == 1)
			return;
		currentCount--;
		product.setCount(currentCount);
		((TextView) showCountView).setText(currentCount + "");

		dataChanged();
		calculate();
	}
	
	@Override
	public void doDelect(int position) {
		
		mlist.remove(position);
		dataChanged();
		calculate();
	}

	@Override
	public void checkGoods(int position, boolean isChecked) {
		mlist.get(position).setChoosed(isChecked);
		calculate();
	}

	/**
	 * 统计操作<br>
	 * 1.先清空全局计数器<br>
	 * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
	 * 3.给底部的textView进行数据填充
	 */
	private void calculate() {
		totalPrice = 0.00;
		String exact_totalPrice = null;
		for (int i = 0; i < mlist.size(); i++) {
			StoreEntity product = mlist.get(i);
			if (product.isChoosed()) {
//				totalPrice += product.getPrice() * product.getCount();
				double total = exactcalculate(product.getPrice(),product.getCount());
				totalPrice += total;
				DecimalFormat fnum = new DecimalFormat("##0.00"); 
				exact_totalPrice = fnum.format(totalPrice);
			}
		}
		if (exact_totalPrice == null) {
			tv_total_price.setText("￥0.00");
		}else {
			tv_total_price.setText("￥" + exact_totalPrice);
		}
	}
	
	private double exactcalculate(double price, int count) {
		BigDecimal mprice = new BigDecimal(price);
		BigDecimal mcount = new BigDecimal(count+"");
		BigDecimal multiply = mprice.multiply(mcount);
		multiply.doubleValue();
		return multiply.doubleValue();
	}

}
