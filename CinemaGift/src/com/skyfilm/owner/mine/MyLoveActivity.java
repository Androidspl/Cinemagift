package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.MyLoveAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.MyLove;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 我的喜欢
 * @author min.yuan
 *
 */
public class MyLoveActivity extends BaseListActivity<MyLove>{
	private UserInfoBiz userInfoBiz;
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("我的喜欢");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
		userInfoBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
	}
	@Override
	protected void onListItemClick(MyLove item) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected List<MyLove> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected List<MyLove> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
//		userInfoBiz.getMyLoveList(User_id, Page, Page_size);
		return null;
	}
	@Override
	protected BaseAdapter getListAdapter(List<MyLove> data) {
		
		return new MyLoveAdapter(this, data);
	}

}
