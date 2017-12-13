package com.skyfilm.owner.page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.InfoListViewAdapter;
import com.skyfilm.owner.adapter.InfoViewPagerAdapter;
import com.skyfilm.owner.adapter.InfoViewPagerAdapter.Banner;
import com.skyfilm.owner.adapter.ViewPagerAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.MyBanner;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.biz.HomePageBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.BitmapUtil;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DensityUtil;
import com.skyfilm.owner.webView.WebViewActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 资讯
 * 
 * @author min.yuan
 *
 */
public class InformationPage extends BaseListFragment<Information> {
	View rootView;
	private ViewPager vp;
	ImageLoader loader = ImageLoader.getInstance();
	LinearLayout layoutDots = null;
	List<String> urls;
	ArrayList<Banner> bannerlist;
	ImageView[] mDots;
	int selectIndex = 0;
	Runnable viewpagerRunnable;
	ScheduledExecutorService picScrollExec;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.cacheOnDisk(true).build();
	private View bannerView;
	private HomePage info;
	private HomePageBiz homePageBiz;
	private FragmentActivity mContext;

	public InformationPage(HomePage data, FragmentActivity context) {
		this.info = data;
		this.mContext = context;
	}

	@Override
	protected void addHeaderView() {
		super.addHeaderView();
		homePageBiz = (HomePageBiz) CsqManger.getInstance().get(Type.HOMEPAGEBIZ);
		loader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		bannerView = View.inflate(getActivity(), R.layout.banner, null);
		vp = (ViewPager) bannerView.findViewById(R.id.vp);
		layoutDots = (LinearLayout) bannerView.findViewById(R.id.dot);
		listView.setDivider(null);
		initBannerView();
		listView.addHeaderView(bannerView);
	}

	// @Override
	// public void click() {
	// super.click();
	// Intent intent = new Intent(getActivity(), WebViewActivity.class);
	// intent.putExtra("url", "https://www.baidu.com/");
	// intent.putExtra("PAGETYPE", true);
	// // intent.putExtra("TITLE", "咨询详情");
	// // intent.putExtra("COUNSELOR", true);
	// getActivity().startActivity(intent);
	// }

	public void initBannerView() {
		initBannerData();
		vp.setAdapter(new InfoViewPagerAdapter(context, bannerlist));
		initDots();
		// initRunnable();
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
		if (bannerlist != null && bannerlist.size() > 0) {
			mDots = new ImageView[bannerlist.size()];
			layoutDots.removeAllViews();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			int margin = DensityUtil.dip2px(getActivity(), 8);
			params.setMargins(0, 0, margin, 0);
			for (int i = 0; i < bannerlist.size(); i++) {
				mDots[i] = (ImageView) View.inflate(getActivity(), R.layout.dot_imageview, null);
				layoutDots.addView(mDots[i], params);

			}
			mDots[selectIndex].setImageResource(R.drawable.banner_circle_s);
		}
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

	public void initBannerData() {
		bannerlist = new ArrayList<Banner>();
		if (info != null) {
			List<MyBanner> banners = info.getBannerList();
			if (banners != null && banners.size() > 0) {
				for (int i = 0; i < banners.size(); i++) {
					MyBanner myBanner = banners.get(i);
					Banner banner = new Banner(myBanner.getImg_url(), myBanner.getBanner_title(),myBanner.getLink_url());
					bannerlist.add(banner);
				}
			}
		}
	}

	/**
	 * ��ҳ�����Ķ�ʱ�ֲ�
	 */
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

	@Override
	protected void onListItemClick(Information item) {
		Intent intent = new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra("url", item.getInfo_url());
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
	protected List<Information> getDataListFromCache(String userId, String cid) {
		if(info != null){
			return info.getInfoList();
		}
		return null;
	}

	@Override
	protected List<Information> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		setCurrentPage(2);
		boolean networkConnected = AppUtils.isNetworkConnected(getActivity());
		if(networkConnected){
			List<Information> informationList = null;
			try{
				informationList = homePageBiz.getInformationList(null, "1", "10");
			}catch(Exception e){
//				CsqToast.show(" ", mContext);
			}
			return informationList;
		}else{
			CsqToast.show("请链接网络", mContext);
			return info.getInfoList();
		}
	}

	@Override
	protected BaseAdapter getListAdapter(List<Information> data) {
		if (data == null) {
			listView.setVisibility(View.VISIBLE);
			noDateInfoTextView.setVisibility(View.GONE);
			if (info != null) {
				data = info.getInfoList();
			}
		}
		return new InfoListViewAdapter(getActivity(), data);
	}

	// @Override
	// public void onDetach() {
	// // TODO Auto-generated method stub
	// super.onDetach();
	// BitmapUtil.releaseView(bannerView);
	// }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (vp != null) {
			// vp.removeAllViews();
			// vp = null;
		}
	}

	@Override
	public void onDestroy() {
		// BitmapUtil.releaseView(rootView);
		// rootView = null;
		super.onDestroy();
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = super.initView(inflater, container, savedInstanceState);
		return rootView;
	}
}
