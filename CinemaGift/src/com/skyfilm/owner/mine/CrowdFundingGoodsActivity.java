package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.adapter.CrowdFundingGoodsAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.CrowdFundingGoods;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 众筹商品
 * @author min.yuan
 *
 */
public class CrowdFundingGoodsActivity extends BaseListActivity<CrowdFundingGoods>{
	private UserInfoBiz userInfoBiz;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userInfoBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
	}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("众筹商品");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
	@Override
	protected void onListItemClick(CrowdFundingGoods item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<CrowdFundingGoods> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<CrowdFundingGoods> getDataList(String userId, String cid, int page, int pageSize)
			throws CsqException {
//		userInfoBiz.getMyCollectGoodsList(User_id, Page, Page_size);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<CrowdFundingGoods> data) {
		// TODO Auto-generated method stub
		return new CrowdFundingGoodsAdapter(this, data);
	}

}
