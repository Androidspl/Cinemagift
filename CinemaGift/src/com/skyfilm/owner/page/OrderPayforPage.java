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
 * 我的订单——待付款
 * @author min.yuan
 *
 */
public class OrderPayforPage extends BaseListFragment<Order>{
	private static final int CANCEL = 0x111;
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
		OrderListViewAdapter orderListViewAdapter = new OrderListViewAdapter(getActivity(), data, 1,cancel,null);
		listView.setAdapter(orderListViewAdapter);
		return orderListViewAdapter;
	}
	
	private OnClickListener cancel = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new CsqRunnable(CANCEL).start();
			CsqToast.show("取消订单", getActivity());
		}
	};
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == CANCEL) {// 取消订单
//			orderBiz.cancelOrder(User_id, Order_id);
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
//		if (result) {
//			if (operate == CANCEL) {
//				new CsqRunnable(Const.LIST_REFRESH, 1).start();
//		}
		return super.handleResult(result, operate, obj);
	}

}
