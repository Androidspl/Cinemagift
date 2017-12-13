package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.OrderListViewAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.DensityUtil;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * 我的订单——待评价
 * @author min.yuan
 *
 */
public class OrderEvaluatePage extends BaseListFragment<Order>{

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Order> data) {
		listView.setVisibility(View.VISIBLE);
		noDateInfoTextView.setVisibility(View.GONE);
		OrderListViewAdapter orderListViewAdapter = new OrderListViewAdapter(getActivity(), null, 4,null,null);
		listView.setAdapter(orderListViewAdapter);
		return orderListViewAdapter;
	}

}
