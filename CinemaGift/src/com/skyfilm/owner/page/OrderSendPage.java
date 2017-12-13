package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.OrderListViewAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.biz.OrderBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.OrderDetailsActivity;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.DensityUtil;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的订单——待发货
 * @author min.yuan
 *
 */
public class OrderSendPage extends BaseListFragment<Order>{
	private OrderBiz orderBiz;
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
				} 
				else if (position == 4) {// 待收货
					intent.putExtra("tag", 2);
				}
				else if (position == 5) {// 待评价
					intent.putExtra("tag", 3);
				}
				getActivity().startActivity(intent);
			}

		});
	}
	@Override
	protected void onListItemClick(Order item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return getCurrentPage()+1;
	}

	@Override
	protected List<Order> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Order> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
//		List<Order> orderList = orderBiz.getOrderList(page+"", "20");
//		setCurrentPage(1);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Order> data) {
		listView.setVisibility(View.VISIBLE);
		noDateInfoTextView.setVisibility(View.GONE);
		OrderListViewAdapter orderListViewAdapter = new OrderListViewAdapter(getActivity(), data, 2,null,null);
		listView.setAdapter(orderListViewAdapter);
		return orderListViewAdapter;
	}

}
