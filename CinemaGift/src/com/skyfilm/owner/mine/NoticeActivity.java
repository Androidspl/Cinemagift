package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.adapter.NoticeListViewAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.mine.Notice;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 通知消息
 * @author min.yuan
 *
 */
public class NoticeActivity extends BaseListActivity<Notice>{
private UserInfoBiz userInfoBiz;
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("通知消息");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
		userInfoBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
	}
	@Override
	protected void onListItemClick(Notice item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Notice> getDataListFromCache(String userId, String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Notice> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		// TODO Auto-generated method stub
//		return userInfoBiz.getNoticeList(User_id, Page, Page_size);
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Notice> data) {
		// TODO Auto-generated method stub
		return new NoticeListViewAdapter(NoticeActivity.this, data);
	}

}
