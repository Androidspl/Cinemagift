package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.adapter.OnsellAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.Onsell;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OnsellActivity extends BaseListActivity<Onsell> {
	private UserInfoBiz userInfoBiz;

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("在售商品");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
		userInfoBiz = (UserInfoBiz)CsqManger.getInstance().get(Type.USERINFOBIZ);
	}

	@Override
	protected void onListItemClick(Onsell item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getPage() {
		return getCurrentPage()+1;
	}

	@Override
	protected List<Onsell> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Onsell> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		List<Onsell> onsellList = userInfoBiz.getOnsellList(null, page+"", "20");
		setCurrentPage(1);
		return onsellList;
//		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Onsell> data) {
		// TODO Auto-generated method stub
		return new OnsellAdapter(this, data);
	}

}
