package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.OrderListViewAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.base.BaseListFragment.DeleteType;
import com.skyfilm.owner.base.BaseListFragment.PullType;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.biz.OrderBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.MyAddressActivity;
import com.skyfilm.owner.mine.OrderDetailsActivity;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DensityUtil;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * 我的订单——全部
 * 
 * @author min.yuan
 *
 */
public class OrderAllPage extends BaseListFragment<Order> {
	private OrderBiz orderBiz;
	private static final int BUTTON1 = 0x111;
	private static final int BUTTON2 = 0x112;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderBiz = (OrderBiz) CsqManger.getInstance().get(Type.ORDERBIZ);
	}

	@Override
	public void click() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
				if (position == 1) {// 待付款普通
					intent.putExtra("tag", 0);
					intent.putExtra("ordernum", "123456789");
					intent.putExtra("is_crowd_funding", false);
				} else if (position == 2) {// 待付款众筹
					intent.putExtra("tag", 0);
					intent.putExtra("ordernum", "987654321");
					intent.putExtra("is_crowd_funding", true);
				} else if (position == 3) {// 待发货
					intent.putExtra("tag", 1);
				} else if (position == 4) {// 待收货
					intent.putExtra("tag", 2);
				} else if (position == 5) {// 待评价
					intent.putExtra("tag", 3);
				}
				getActivity().startActivity(intent);
			}

		});
	}

	@Override
	protected void onListItemClick(Order item) {
	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Order> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Order> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		// return orderBiz.getOrderList(User_id, "all", Page, Page_size);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Order> data) {
		listView.setVisibility(View.VISIBLE);
		noDateInfoTextView.setVisibility(View.GONE);
		OrderListViewAdapter orderListViewAdapter = new OrderListViewAdapter(getActivity(), data, 0, button1, button2);
		listView.setAdapter(orderListViewAdapter);
		return orderListViewAdapter;
	}

	private OnClickListener button1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Order order = (Order)v.getTag();
			new CsqRunnable(BUTTON1).start();
			CsqToast.show("取消订单", getActivity());
		}
	};

	private OnClickListener button2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new CsqRunnable(BUTTON2).start();
			CsqToast.show("确认收货", getActivity());
		}
	};

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == BUTTON1) {// 取消订单
			// return orderBiz.cancelOrder(User_id, Order_id);
		} else if (operate == BUTTON2) {// 确认收货
			// return orderBiz.confirmOrder(User_id, Order_id);
		}
		super.doInBackground(operate, objs);
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// if (result) {
		// if (operate == BUTTON1) {
//		 new CsqRunnable(Const.LIST_REFRESH, 1).start();
		// }else if(operate == BUTTON2){
		// new CsqRunnable(Const.LIST_REFRESH, 1).start();
		// }
		// }
		super.handleResult(result, operate, obj);
		return false;
	}
}
