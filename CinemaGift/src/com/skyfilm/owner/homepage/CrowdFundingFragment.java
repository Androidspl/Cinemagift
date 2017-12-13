package com.skyfilm.owner.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.SearchActivity;
import com.skyfilm.owner.activity.ShoppingCartActivity;
import com.skyfilm.owner.adapter.CrowdAdapter;
import com.skyfilm.owner.adapter.CrowdViewPagerAdapter;
import com.skyfilm.owner.adapter.InfoViewPagerAdapter;
import com.skyfilm.owner.adapter.StoreViewPagerAdapter;
import com.skyfilm.owner.adapter.InfoViewPagerAdapter.Banner;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.CrowdFundingEntity;
import com.skyfilm.owner.biz.CrowdFundBiz;
import com.skyfilm.owner.biz.HomePageBiz;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.NoticeActivity;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DensityUtil;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.webView.WebViewActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 众筹
 * 
 * @author min.yuan
 *
 */
public class CrowdFundingFragment extends BaseListFragment<CrowdFundingEntity> implements OnClickListener {
	View rootView;
	private ImageView left;
	private TextView center;
	private ImageView right1;
	private TextView right2;
	private CrowdAdapter crowdAdapter;
	private ViewPager vp;
	private LinearLayout layoutDots;
	private ArrayList<String> urls;
	private ImageView[] mDots;
	ArrayList<Banner> bannerlist;
	int selectIndex = 0;
	ScheduledExecutorService picScrollExec;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader = ImageLoader.getInstance();
	Runnable viewpagerRunnable;
	private View bannerView;
	private List<CrowdFundingEntity> mlist = new ArrayList<CrowdFundingEntity>();
	private CrowdFundBiz crowdfundBiz;
	private List<CrowdFundingEntity> crowBanner;
	private List<CrowdFundingEntity> crowdEntity;
	private static final int BANNDER = 0x11;

	@Override
	protected void addHeaderView() {
		super.addHeaderView();
		initData();
		listView.setDivider(null);
		bannerView = View.inflate(getActivity(), R.layout.banner, null);
		initView();
		new CsqRunnable(BANNDER).start();
	}

	private void initView() {
		RelativeLayout rl_banner = (RelativeLayout) bannerView.findViewById(R.id.rl_banner);
		LinearLayout ll_mark = (LinearLayout) bannerView.findViewById(R.id.ll_mark);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.bottomMargin = 8;
		rl_banner.setLayoutParams(layoutParams);
		//ll_mark.setVisibility(View.VISIBLE);
		vp = (ViewPager) bannerView.findViewById(R.id.vp);
		layoutDots = (LinearLayout) bannerView.findViewById(R.id.dot);
	}

	public void initBannerView() {
		vp.setAdapter(new CrowdViewPagerAdapter(context, crowBanner));
		initDots();
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initDots() {
		mDots = new ImageView[crowBanner.size()];
		layoutDots.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DensityUtil.dip2px(getActivity(), 8);
		params.setMargins(0, 0, margin, 0);
		for (int i = 0; i < crowBanner.size(); i++) {
			mDots[i] = (ImageView) View.inflate(getActivity(), R.layout.dot_imageview, null);
			layoutDots.addView(mDots[i], params);

		}
		mDots[selectIndex].setImageResource(R.drawable.banner_circle_s);
	}

	private void initRunnable() {

		viewpagerRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							int nowIndex = vp.getCurrentItem();
							vp.setCurrentItem(nowIndex + 1);
						}
					});
				} catch (Exception e) {
				}

			}
		};
		if (picScrollExec != null) {
			stopPicScroll();
		}
		picScrollExec = Executors.newSingleThreadScheduledExecutor();
		picScrollExec.scheduleAtFixedRate(viewpagerRunnable, 3, 3, TimeUnit.SECONDS);
	}

	private void stopPicScroll() {
		if (picScrollExec != null) {
			picScrollExec.shutdownNow();
			picScrollExec = null;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		stopPicScroll();
	}

	@Override
	public void onResume() {
		super.onResume();
		initRunnable();
	}

	private void setCurrentDot(int currentPosition) {

		if (selectIndex != currentPosition) {
			selectIndex = currentPosition;
			for (int i = 0; i < mDots.length; i++) {
				if (i == currentPosition) {
					mDots[i].setImageResource(R.drawable.banner_circle_s);
				} else {
					mDots[i].setImageResource(R.drawable.banner_circle_n);
				}
			}
		}
	}

	private void initData() {
		crowdfundBiz = (CrowdFundBiz) CsqManger.getInstance().get(Type.CROWDFUNDBIZ);
		ic_title.setVisibility(View.VISIBLE);
		left = (ImageView) ic_title.findViewById(R.id.iv_left);
		center = (TextView) ic_title.findViewById(R.id.tv_center);
		right1 = (ImageView) ic_title.findViewById(R.id.iv_right1);
		right2 = (TextView) ic_title.findViewById(R.id.tv_right2);
		center.setText("众筹");
		// TODO left下拉按钮，right1放大镜，right2购物车
		left.setBackgroundResource(R.drawable.ico_msgs);
		right1.setBackgroundResource(R.drawable.icon_search);
		right2.setBackgroundResource(R.drawable.ico_shoppingcart);

		left.setOnClickListener(this);
		right1.setOnClickListener(this);
		right2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			// TODO 跳转客服标准问答
			CsqToast.show("跳转消息中心", getActivity());
			Intent intent_notice = new Intent(getActivity(), NoticeActivity.class);
			getActivity().startActivity(intent_notice);
			break;
		case R.id.iv_right1:
			// 跳转搜索
			CsqToast.show("跳转搜索", getActivity());
			Intent intent_search = new Intent(getActivity(), SearchActivity.class);
			startActivity(intent_search);
			break;
		case R.id.tv_right2:
			// TODO 跳转购物车
			CsqToast.show("跳转购物车", getActivity());
			Intent intent_shopping_cart = new Intent(getActivity(), ShoppingCartActivity.class);
			startActivity(intent_shopping_cart);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onListItemClick(CrowdFundingEntity item) {
		
	}

	@Override
	protected int getPage() {
		return 0;
	}

	@Override
	protected List<CrowdFundingEntity> getDataListFromCache(String userId, String cid) {
		return null;
	}

	@Override
	protected List<CrowdFundingEntity> getDataList(String userId, String cid, int page, int pageSize)
			throws CsqException {
		crowdEntity = crowdfundBiz.getCrowdFunding(null, "1", "10");
		return crowdEntity;
	}

	@Override
	protected BaseAdapter getListAdapter(List<CrowdFundingEntity> data) {
		if (data != null && data.size() > 0) {
			return new CrowdAdapter(getActivity(), data);
		}
		return null;
	}
	@Override
	public void onDestroy() {
//		BitmapUtil.releaseView(rootView);
		rootView = null;
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(vp != null){
			vp.removeAllViews();
			vp = null;
		}
	}
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = super.initView(inflater, container, savedInstanceState);
		return rootView;
	}
	
	@Override
	public void click() {
		super.click();
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra("url", "https://www.baudu.com");
		intent.putExtra("TITLE", "详情页");
		intent.putExtra("PAGETYPE", true);
		intent.putExtra("COUNSELOR", true);
		context.startActivity(intent);
	}
	
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == BANNDER) {
			crowBanner = crowdfundBiz.getCrowdFundBanner();
		}
		return super.doInBackground(operate, objs);
	}
	
	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && operate == BANNDER) {
			initBannerView();
			listView.addHeaderView(bannerView);
		}
		return super.handleResult(result, operate, obj);
	}
	
}
