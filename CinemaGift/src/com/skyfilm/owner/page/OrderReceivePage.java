package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.OrderListViewAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DensityUtil;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

/**
 * 我的订单——待收货
 * @author min.yuan
 *
 */
public class OrderReceivePage extends BaseListFragment<Order>{
	private static final int SURE = 0x112;
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
		// TODO Auto-generated method stub
		listView.setVisibility(View.VISIBLE);
		noDateInfoTextView.setVisibility(View.GONE);
		OrderListViewAdapter orderListViewAdapter = new OrderListViewAdapter(getActivity(), null, 3,null,sure);
		listView.setAdapter(orderListViewAdapter);
		return orderListViewAdapter;
	}

	private OnClickListener sure = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			new CsqRunnable(SURE).start();
			CsqToast.show("确认收货", getActivity());
		}
	};
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == SURE) {// 取消订单
//			orderBiz.cancelOrder(User_id, Order_id);
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
//		if (result) {
//			if (operate == SURE) {
//				new CsqRunnable(Const.LIST_REFRESH, 1).start();
//		}
		return super.handleResult(result, operate, obj);
	}
}
