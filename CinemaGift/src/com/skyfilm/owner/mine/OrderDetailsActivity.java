package com.skyfilm.owner.mine;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.PayStyleActivity;
import com.skyfilm.owner.adapter.GoodsAdapter;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.bean.mine.OrderDetails;
import com.skyfilm.owner.bean.mine.OrderDetails.Goods;
import com.skyfilm.owner.biz.OrderBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.SpUtils;
import com.skyfilm.owner.webView.WebViewActivity;
import com.skyfilm.owner.widget.MyProgress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 订单详情
 * 
 * @author min.yuan
 *
 */
public class OrderDetailsActivity extends BaseThreadActivity implements OnClickListener {

	private int tag = 0;// (0待付款。1待发货。2待收货)
	private boolean is_crowd_funding = false;
	private RelativeLayout rl_dfk, rl_wl, all_address_des;
	private LinearLayout ll_payfor;
	private TextView name, address, phoneNum, choose_address;
	private TextView total, to_pay, sure, evaluate,blank;
	private ListView goods_listview;
	private TextView send_way_text, send_way, num, seeWL, money;
	private ImageView go;
	private GoodsAdapter goodsAdapter;
	private boolean haveAddress = true;
	private String ordernum = null;

	private OrderBiz orderBiz;
	private static final int GET_ORDER_DETAILS = 0X0114;
	private String orderId;
	private String status = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails);
		// orderId = getIntent().getStringExtra("orderId");
		String a = "0ECF8916-A166-417C-A0AA-C72249CE4E1C";
		String b = "187882BF-A22F-46B5-8F2A-CADB81AA3F57";
		List<String> ab = new ArrayList<>();
		ab.add(a);
		ab.add(b);
		// orderId = ab.toString();
		// orderId = convertList2String(ab);
		orderBiz = (OrderBiz) CsqManger.getInstance().get(Type.ORDERBIZ);
		status = getIntent().getIntExtra("tag", 0)+"";
		initView();
		new CsqRunnable(GET_ORDER_DETAILS, ab).start();
		MyProgress.show("加载中", this);
		initData(null);
	}

	/**
	 * 将多张图片的路径用,连接后返回
	 * 
	 * @param paths
	 *            图片的路径
	 * @return “” if paths is empty
	 */
	public static String convertList2String(List<String> paths) {
		if (paths == null || paths.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String str : paths) {
			builder.append(str);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if ("0".equals(status)) {
			name.setText(SpUtils.getString(this, "name", "name"));
			address.setText(SpUtils.getString(this, "address", "address"));
			phoneNum.setText(SpUtils.getString(this, "phoneNum", "phoneNum"));
		}
	}

	private void initView() {
		goods_listview = (ListView) findViewById(R.id.goods_listview);
		
		rl_dfk = (RelativeLayout) findViewById(R.id.rl_dfk);
		rl_wl = (RelativeLayout) findViewById(R.id.rl_wl);
		all_address_des = (RelativeLayout) findViewById(R.id.all_address_des);
		ll_payfor = (LinearLayout) findViewById(R.id.ll_payfor);

		blank = (TextView) findViewById(R.id.blank);
		name = (TextView) findViewById(R.id.recevice_name);
		address = (TextView) findViewById(R.id.recevice_address);
		phoneNum = (TextView) findViewById(R.id.recevice_phoneNum);
		choose_address = (TextView) findViewById(R.id.choose_address);
		go = (ImageView) findViewById(R.id.go);

		send_way_text = (TextView) findViewById(R.id.send_way_text);
		send_way = (TextView) findViewById(R.id.send_way);
		num = (TextView) findViewById(R.id.num);
		seeWL = (TextView) findViewById(R.id.seeWL);

		money = (TextView) findViewById(R.id.money);

		total = (TextView) findViewById(R.id.total);
		to_pay = (TextView) findViewById(R.id.to_pay);
		sure = (TextView) findViewById(R.id.sure);
		evaluate = (TextView) findViewById(R.id.evaluate);
	}

	private void initData(OrderDetails orderDetails) {
		goodsAdapter = new GoodsAdapter(this, null);
		goods_listview.setAdapter(goodsAdapter);
		String logo;
		String g_name;
		String type;
		String money;
		if ("0".equals(status)) {// 待付款
			rl_wl.setVisibility(View.GONE);
			seeWL.setVisibility(View.GONE);
			sure.setVisibility(View.GONE);
			evaluate.setVisibility(View.GONE);
			rl_dfk.setVisibility(View.VISIBLE);
			ll_payfor.setVisibility(View.VISIBLE);
			all_address_des.setOnClickListener(this);
			to_pay.setOnClickListener(this);
			if (haveAddress) {
				name.setText(SpUtils.getString(this, "name", "name"));
				address.setText(SpUtils.getString(this, "address", "address"));
				phoneNum.setText(SpUtils.getString(this, "phoneNum", "phoneNum"));
			} else {
				choose_address.setVisibility(View.VISIBLE);
				all_address_des.setVisibility(View.GONE);
			}
		} else if ("1".equals(status)) {// 待发货
			rl_wl.setVisibility(View.GONE);
			ll_payfor.setVisibility(View.GONE);
			seeWL.setVisibility(View.GONE);
			sure.setVisibility(View.GONE);
			evaluate.setVisibility(View.GONE);
			rl_dfk.setVisibility(View.VISIBLE);
			go.setVisibility(View.GONE);
			blank.setVisibility(View.GONE);
		} else if ("2".equals(status)) {// 待收货
			go.setVisibility(View.GONE);
			ll_payfor.setVisibility(View.GONE);
			evaluate.setVisibility(View.GONE);
			sure.setVisibility(View.VISIBLE);
			seeWL.setVisibility(View.VISIBLE);
			seeWL.setOnClickListener(this);
			sure.setOnClickListener(this);
		} else if ("3".equals(status)) {// 待评价
			go.setVisibility(View.GONE);
			seeWL.setVisibility(View.GONE);
			sure.setVisibility(View.GONE);
			ll_payfor.setVisibility(View.GONE);
			evaluate.setVisibility(View.VISIBLE);
			evaluate.setOnClickListener(this);
		}
		if (orderDetails != null) {
			List<Goods> goods = orderDetails.getStandard();
			MyAddress myAddress = orderDetails.getUseraddr();
			if (goods != null && goods.size() > 0) {

				if (myAddress != null) {
					haveAddress = true;
				}

				status = goods.get(0).getStatus();
				g_name = goods.get(0).getStandard_name();
				money = goods.get(0).getGoods_price();
				type = goods.get(0).getId();
			}
			
		}
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		Intent i = getIntent();
		ordernum = i.getStringExtra("ordernum");
		tag = i.getIntExtra("tag", 0);
		is_crowd_funding = i.getBooleanExtra("is_crowd_funding", false);
		if (is_crowd_funding) {
			title.setText("众筹支持");
		} else {
			title.setText("订单详情");
		}
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (GET_ORDER_DETAILS == operate) {
			return orderBiz.getOrderDetails((List<String>) objs[0], "1A7D3A5F-598A-4BCA-8217-7BCB14440BBE", "1");
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// 获取返回值，得到状态设置显隐
		if (GET_ORDER_DETAILS == operate && result) {
			OrderDetails orderDetails = (OrderDetails) obj;
			initData(orderDetails);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.recevice_address:// 选择收货地址
			intent.setClass(OrderDetailsActivity.this, MyAddressActivity.class);
			intent.putExtra("isSee", false);
			startActivity(intent);
			break;
		case R.id.seeWL:// 查看物流
			// TODO
			CsqToast.show("查看物流", OrderDetailsActivity.this);
			// String url =
			// "http://www.kuaidi100.com/chaxun?com=shunfeng&nu=304708923294";

			String url = "http://m.kuaidi100.com/index_all.html?type=shunfeng&postid=304708923294";
			intent.setClass(OrderDetailsActivity.this, WebViewActivity.class);
			intent.putExtra("url", url);
			startActivity(intent);
			break;
		case R.id.to_pay:// 去支付
			// TODO
			CsqToast.show("去支付", OrderDetailsActivity.this);
			intent.setClass(OrderDetailsActivity.this, PayStyleActivity.class);
			intent.putExtra("Address", "address");
			intent.putExtra("Order_id", "0ECF8916-A166-417C-A0AA-C72249CE4E1C");
			intent.putExtra("Money", "");
			startActivity(intent);
			break;
		case R.id.sure:// 确认收货
			// TODO
			CsqToast.show("确认收货", OrderDetailsActivity.this);
			break;
		case R.id.evaluate:// 评价
			// TODO
			CsqToast.show("评价", OrderDetailsActivity.this);
			intent.setClass(OrderDetailsActivity.this, EvaluateOrderActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		String[] keys = { "name", "address", "phoneNum" };
		SpUtils.remove(this, keys);
		super.onDestroy();
	}
}
