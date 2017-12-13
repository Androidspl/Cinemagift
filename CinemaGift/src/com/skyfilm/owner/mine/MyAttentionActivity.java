package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.adapter.MyAttentionAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqToast;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的关注
 * 
 * @author min.yuan
 *
 */
public class MyAttentionActivity extends BaseListActivity<MyAttention> {
	private static final int CANCELATTENTION = 0x113;

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("我的关注");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onListItemClick(MyAttention item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<MyAttention> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<MyAttention> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<MyAttention> data) {
		// TODO Auto-generated method stub
		return new MyAttentionAdapter(this, data, cancelattention);
	}

	private OnClickListener cancelattention = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new CsqRunnable(CANCELATTENTION).start();
			CsqToast.show("取消关注", MyAttentionActivity.this);
		}
	};

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == CANCELATTENTION) {// 取消订单
			// orderBiz.cancelOrder(User_id, Order_id);
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// if (result) {
		// if (operate == CANCELATTENTION) {
		new CsqRunnable(Const.LIST_REFRESH, 1).start();
		// }
		return super.handleResult(result, operate, obj);
	}
}
