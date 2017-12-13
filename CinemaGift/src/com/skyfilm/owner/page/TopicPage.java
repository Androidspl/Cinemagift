package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.TopicListViewAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.biz.HomePageBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.webView.WebViewActivity;
import com.skyfilm.owner.utils.DensityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

/**
 * 专题
 * 
 * @author min.yuan
 *
 */
public class TopicPage extends BaseListFragment<Topic> {
	// private List<Topic> topic;
	private HomePageBiz homePageBiz;
	private FragmentActivity mContext;
	private int mWidth;

	public TopicPage(List<Topic> data, FragmentActivity context) {
		// this.topic = data;
		this.mContext = context;
		homePageBiz = (HomePageBiz) CsqManger.getInstance().get(Type.HOMEPAGEBIZ);
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = mContext.getWindowManager();
		windowManager.getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		int widthDP = DensityUtil.px2dip(mContext, widthPixels);
		mWidth = (int) (widthDP * 0.56);
	}

	@Override
	protected void onListItemClick(Topic item) {
		Intent intent = new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra("url", item.getLink_url());
		intent.putExtra("PAGETYPE", true);
		// intent.putExtra("TITLE", "咨询详情");
		// intent.putExtra("COUNSELOR", true);
		getActivity().startActivity(intent);
	}

	@Override
	protected int getPage() {
		return getCurrentPage() + 1;
	}

	@Override
	protected List<Topic> getDataListFromCache(String userId, String cid) {
		return homePageBiz.getTopicFromCache("User_id");
	}

	@Override
	protected List<Topic> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		setCurrentPage(1);
		return homePageBiz.getTopicList(null, "1", "10");
	}

	@Override
	protected BaseAdapter getListAdapter(List<Topic> data) {
		listView.setDividerHeight(0);
		return new TopicListViewAdapter(getActivity(), data, mWidth);
	}
}
