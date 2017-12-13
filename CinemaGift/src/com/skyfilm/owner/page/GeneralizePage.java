package com.skyfilm.owner.page;

import java.util.List;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.adapter.RecommendAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.biz.HomePageBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.MyAttentionActivity;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.webView.WebViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 推荐
 * 
 * @author min.yuan
 *
 */
public class GeneralizePage extends BaseListFragment<Recommend> {

	private ListView recommend_lv;
	private RecommendAdapter adapter;
	private static final int ATTENTION = 0x114;
//	private List<Recommend> recommend;
	private HomePageBiz homePageBiz;
	private FragmentActivity mContext;

	public GeneralizePage(List<Recommend> data,FragmentActivity context) {
//		this.recommend = data;
		this.mContext = context;
		homePageBiz = (HomePageBiz) CsqManger.getInstance().get(Type.HOMEPAGEBIZ);
	}

	@Override
	protected void onListItemClick(Recommend item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getPage() {
		// TODO Auto-generated method stub
		return getCurrentPage() + 1;
	}

	@Override
	protected List<Recommend> getDataListFromCache(String userId, String cid) {
		return homePageBiz.getRecommendFromCache("User_id");
	}

	@Override
	protected List<Recommend> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		setCurrentPage(1);
		return homePageBiz.getRecommendList(null, "1", "10");
	}

	@Override
	protected BaseAdapter getListAdapter(List<Recommend> data) {
		return new RecommendAdapter(getActivity(), data, attention);
	}

	private OnClickListener attention = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new CsqRunnable(ATTENTION).start();
			CsqToast.show("关注", getActivity());
		}
	};

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == ATTENTION) {// 关注
			// orderBiz.cancelOrder(User_id, Order_id);
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		// if (result) {
		// if (operate == ATTENTION) {
		new CsqRunnable(Const.LIST_REFRESH, 1).start();
		// }
		return super.handleResult(result, operate, obj);
	}
}
