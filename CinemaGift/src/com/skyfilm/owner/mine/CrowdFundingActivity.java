package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.adapter.CrowdFundingAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.CrowdFunding;
import com.skyfilm.owner.biz.OrderBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 我的众筹
 * @author min.yuan
 *
 */
public class CrowdFundingActivity extends BaseListActivity<CrowdFunding> {
private OrderBiz orderBiz;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderBiz = (OrderBiz) CsqManger.getInstance().get(Type.ORDERBIZ);
	}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		// TODO Auto-generated method stub
		super.initTiltle(left, title, right1, right2);
		title.setText("我的众筹");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onListItemClick(CrowdFunding item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<CrowdFunding> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<CrowdFunding> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
//		return orderBiz.getMyCollectMoneyList(User_id, Page, Page_size);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<CrowdFunding> data) {
		// TODO Auto-generated method stub
		return new CrowdFundingAdapter(this, data);
	}

}
