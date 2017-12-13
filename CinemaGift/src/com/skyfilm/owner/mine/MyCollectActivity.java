package com.skyfilm.owner.mine;

import java.util.List;

import org.apache.http.message.BasicStatusLine;

import com.skyfilm.owner.adapter.MyCollectAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.MyCollect;
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
 * 我的收藏
 * @author min.yuan
 *
 */
public class MyCollectActivity extends BaseListActivity<MyCollect>{

	private UserInfoBiz userInfoBiz;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userInfoBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
	}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("我的收藏");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
	@Override
	protected void onListItemClick(MyCollect item) {
		
	}

	@Override
	protected int getPage() {
		return 0;
	}

	@Override
	protected List<MyCollect> getDataListFromCache(String userId, String cid) {
		return null;
	}

	@Override
	protected List<MyCollect> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
//		return userInfoBiz.getMyCollectList(User_id, Page, Page_size);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<MyCollect> data) {
		// TODO Auto-generated method stub
		return new MyCollectAdapter(this, data);
	}

	

}
