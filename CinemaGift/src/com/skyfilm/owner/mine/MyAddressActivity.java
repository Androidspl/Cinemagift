package com.skyfilm.owner.mine;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.AddressAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.base.BaseListActivity.DeleteType;
import com.skyfilm.owner.base.BaseListActivity.PullType;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.biz.AddressBiz;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的地址管理
 * 
 * @author min.yuan
 *
 */
public class MyAddressActivity extends BaseListActivity<MyAddress> implements OnClickListener {

	private AddressAdapter mAdapter;
	private List<MyAddress> addressList;
	private boolean isSee = true;
	private AddressBiz addressBiz;
	private LinearLayout add_address;
	private View view;
	private UserBiz userBiz;
	private User user;
	private static final int DELETE = 0x0109;
	private static final int SETDEFAULT = 0x0110;
	private LocalBroadcastManager mBroadcastManager;
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Const.ADD_ADDRESS_REFRESH)) {// 添加地址刷新
				new CsqRunnable(Const.LIST_REFRESH, 1).start();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
		user = userBiz.getCurrentUser();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ADD_ADDRESS_REFRESH);
		mBroadcastManager = LocalBroadcastManager.getInstance(this);
		mBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
		initData();
	}

	private void initData() {
		addressBiz = (AddressBiz) CsqManger.getInstance().get(Type.ADDRESSBIZ);
		Intent i = getIntent();
		isSee = i.getBooleanExtra("isSee", true);
		addressList = new ArrayList<MyAddress>();
		view = View.inflate(this, R.layout.add_address, null);
		add_address = (LinearLayout) view.findViewById(R.id.add_address);
		// listView.addFooterView(view);
	}

	@Override
	protected void setEventOption() {
		setEventOption(PullType.REFRESH, DeleteType.NONE);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("我的收货地址");
		// left.setImageResource(R.drawable.btn_back_gray);
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (SETDEFAULT == operate) {
			return addressBiz.setReceiverAddress(user.getUser_id(), (String) objs[0]);
		} else if (DELETE == operate) {
			return addressBiz.setDelAddress((String) objs[0]);
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (SETDEFAULT == operate && result) {
			new CsqRunnable(Const.LIST_REFRESH, 1).start();
			CsqToast.show("设置成功", this);
		} else if (DELETE == operate && result) {
			new CsqRunnable(Const.LIST_REFRESH, 1).start();
			CsqToast.show("删除成功", this);
		}
		return super.handleResult(result, operate, obj);
	}

	@Override
	public void onClick(View v) {
		// switch (v.getId()) {
		// case value:
		//
		// break;
		//
		// default:
		// break;
		// }
	}

	// 设置默认地址
	private OnClickListener setDefault = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// {"Address_id":"00C18C5C-AB70-4646-8779-6B1858F19D8A","User_id":"2a"}
			MyAddress myAddress = (MyAddress) v.getTag();
			String id = myAddress.getId();
			new CsqRunnable(SETDEFAULT, id).start();
			CsqToast.show("设为默认地址", MyAddressActivity.this);
		}
	};
	// 删除地址
	private OnClickListener delete = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// {"Address_id":"00C18C5C-AB70-4646-8779-6B1858F19D8A","User_id":"2a"}
			// MyAddress myAddress = (MyAddress) v.getTag();
			// String id = myAddress.getId();
			String id = (String) v.getTag();
			new CsqRunnable(DELETE, id).start();
			CsqToast.show("删除地址", MyAddressActivity.this);
		}
	};
	// 选择地址
	private OnClickListener choose = new OnClickListener() {

		@Override
		public void onClick(View v) {
		}
	};

	@Override
	protected void onListItemClick(MyAddress item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getPage() {
		return getCurrentPage() + 1;
	}

	@Override
	protected List<MyAddress> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<MyAddress> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		setCurrentPage(1);
		try {
			List<MyAddress> myAddressList = addressBiz.getMyAddressList(user.getUser_id());
			return myAddressList;
		} catch (Exception e) {
			Message msg = Message.obtain();
			msg.obj = e.getMessage();
			if ("No data".equals(msg)) {
				return null;
			}
		}
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<MyAddress> data) {
		addressList = data;

		// if (addressList == null) {
		// listView.removeFooterView(view);
		// }
		if (isSee) {
			// 查看地址
			mAdapter = new AddressAdapter(this, addressList, 1, setDefault, delete);
		} else {
			// 选择地址
			mAdapter = new AddressAdapter(this, addressList, 0, setDefault, choose);
		}
		// 新增地址
		add_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
				startActivity(intent);
				// finish();
			}
		});
		listView.setDividerHeight(0);
		listView.setDivider(getResources().getDrawable(R.color.white_background));
		// listView.setFooterDividersEnabled(false);
		listView.setPullLoadEnable(false);
		listView.setVisibility(View.VISIBLE);
		noDateInfoTextView.setVisibility(View.GONE);
		listView.setAdapter(mAdapter);
		return mAdapter;
	}
	@Override
	protected void onDestroy() {
		mBroadcastManager.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
}
