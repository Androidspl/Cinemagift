package com.skyfilm.owner.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.SearchActivity;
import com.skyfilm.owner.activity.ShoppingCartActivity;
import com.skyfilm.owner.activity.StoreListActivity;
import com.skyfilm.owner.adapter.GoodsClassifyAdapter;
import com.skyfilm.owner.adapter.InfoViewPagerAdapter.Banner;
import com.skyfilm.owner.adapter.StoreAdapter;
import com.skyfilm.owner.adapter.StoreViewPagerAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.bean.Tag_goods;
import com.skyfilm.owner.bean.Type_goods;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DensityUtil;
import com.skyfilm.owner.widget.MyPopWindows;
import com.skyfilm.owner.widget.MyProgress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 商城
 * 
 * @author min.yuan
 *
 */
public class StoreFragment extends BaseListFragment<StoreEntity> implements OnClickListener {
	private ImageView left;
	private TextView center;
	private ImageView right1;
	private TextView right2;
	private MyPopWindows my_popupwindow;
	private View goods_classify;
	private ListView lv_goods_classify;
	private String[] goods = { "全部商品", "数码", "家居", "服饰" };
	private GoodsClassifyAdapter goodsClassifyAdapter;
	private ViewPager vp;
	private LinearLayout layoutDots;
	private ArrayList<String> urls;
	private ImageView[] mDots;
	ArrayList<Banner> bannerlist;
	int selectIndex = 0;
	ScheduledExecutorService picScrollExec;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.cacheOnDisk(true).build();
	ImageLoader loader = ImageLoader.getInstance();
	Runnable viewpagerRunnable;
	private View bannerView;
	private View rootView;
	private TextView toy;
	private TextView diy;
	private TextView electron;
	private TextView home;
	private StoreBiz storeBiz;
	private StoreEntity storeEntity;
	private List<StoreEntity> storeBanner;
	private List<Type_goods> type_goods;
	private List<Tag_goods> tag_goods;
	private ExpandableListView ex_listview;
	private static final int BANNDER = 0x11;
	private static final int SHOP_INDEX = 0x12;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		storeBiz = (StoreBiz) CsqManger.getInstance().get(Type.STOREBIZ);
	}

	@Override
	protected void addHeaderView() {
		super.addHeaderView();
		initData();
		// listView.setDivider(null);
		// bannerView = View.inflate(getActivity(), R.layout.banner, null);
		initView();
		// new CsqRunnable(BANNDER).start();
	}

	private void initView() {
		LinearLayout ll_mark = (LinearLayout) bannerView.findViewById(R.id.ll_mark);
		ll_mark.setVisibility(View.VISIBLE);
		vp = (ViewPager) bannerView.findViewById(R.id.vp);
		layoutDots = (LinearLayout) bannerView.findViewById(R.id.dot);
		// 玩具
		toy = (TextView) bannerView.findViewById(R.id.toy);
		// 手办
		diy = (TextView) bannerView.findViewById(R.id.diy);
		// 电子
		electron = (TextView) bannerView.findViewById(R.id.electron);
		// 居家
		home = (TextView) bannerView.findViewById(R.id.home);
		toy.setOnClickListener(this);
		diy.setOnClickListener(this);
		electron.setOnClickListener(this);
		home.setOnClickListener(this);
	}

	public void initBannerView() {
		vp.setAdapter(new StoreViewPagerAdapter(context, storeBanner));
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

	@Override
	public void onPause() {
		super.onPause();
		stopPicScroll();
	}

	@Override
	public void onResume() {
		super.onResume();
		// initRunnable();
	}

	private void initDots() {
		if (storeBanner != null && storeBanner.size() > 0) {
			mDots = new ImageView[storeBanner.size()];
			layoutDots.removeAllViews();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			int margin = DensityUtil.dip2px(getActivity(), 8);
			params.setMargins(0, 0, margin, 0);
			for (int i = 0; i < storeBanner.size(); i++) {
				mDots[i] = (ImageView) View.inflate(getActivity(), R.layout.dot_imageview, null);
				layoutDots.addView(mDots[i], params);

			}
			mDots[selectIndex].setImageResource(R.drawable.banner_circle_s);
		}
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
		ic_title.setVisibility(View.VISIBLE);
		left = (ImageView) ic_title.findViewById(R.id.iv_left);
		center = (TextView) ic_title.findViewById(R.id.tv_center);
		right1 = (ImageView) ic_title.findViewById(R.id.iv_right1);
		right2 = (TextView) ic_title.findViewById(R.id.tv_right2);
		center.setText("商城");
		// left下拉按钮，right1放大镜，right2购物车
		left.setBackgroundResource(R.drawable.icon_menu);
		right1.setBackgroundResource(R.drawable.icon_search);
		right2.setBackgroundResource(R.drawable.ico_shoppingcart);

		/** 初始化产品分类信息数据 */
		my_popupwindow = new MyPopWindows();
		my_popupwindow.setHeight(192 * 2);
		/** pop */
		goods_classify = LayoutInflater.from(getActivity()).inflate(R.layout.goods_classify, null);
		lv_goods_classify = (ListView) goods_classify.findViewById(R.id.lv_goods_classify);
		goodsClassifyAdapter = new GoodsClassifyAdapter(getActivity());
		goodsClassifyAdapter.setStrings(goods);
		lv_goods_classify.setAdapter(goodsClassifyAdapter);
		lv_goods_classify.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (my_popupwindow != null && my_popupwindow.isShowing()) {
					my_popupwindow.dismiss();
				}
				Intent intent_search = new Intent(getActivity(), StoreListActivity.class);
				startActivity(intent_search);
			}
		});

		left.setOnClickListener(this);
		right1.setOnClickListener(this);
		right2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (my_popupwindow != null && my_popupwindow.isShowing()) {
			my_popupwindow.dismiss();
			return;
		}
		switch (v.getId()) {
		case R.id.iv_left:
			// 弹出下拉选择
			// CsqToast.show("下拉选择", getActivity());
			my_popupwindow.setOutsideTouchable(false);
			my_popupwindow.setContentView(goods_classify);
			my_popupwindow.showAsDropDown(left);
			my_popupwindow.setFocusable(true); // 设置PopupWindow可获得焦点
			my_popupwindow.setTouchable(true); // 设置PopupWindow可触摸
			break;
		case R.id.iv_right1:
			// 跳转搜索
			CsqToast.show("跳转搜索", getActivity());
			Intent intent_search = new Intent(getActivity(), SearchActivity.class);
			startActivity(intent_search);
			break;
		case R.id.tv_right2:
			// 跳转购物车
			CsqToast.show("跳转购物车", getActivity());
			Intent intent_shopping_cart = new Intent(getActivity(), ShoppingCartActivity.class);
			startActivity(intent_shopping_cart);
			break;
		case R.id.toy:
			// 跳转玩具
			CsqToast.show("跳转玩具", getActivity());
			Intent intent_toy = new Intent(getActivity(), StoreListActivity.class);
			startActivity(intent_toy);
			break;
		case R.id.diy:
			// 跳转手办
			CsqToast.show("跳转手办", getActivity());
			Intent intent_diy = new Intent(getActivity(), StoreListActivity.class);
			startActivity(intent_diy);
			break;
		case R.id.electron:
			// 跳转电子
			CsqToast.show("跳转电子", getActivity());
			Intent intent_electron = new Intent(getActivity(), StoreListActivity.class);
			startActivity(intent_electron);
			break;
		case R.id.home:
			// 跳转居家
			CsqToast.show("跳转居家", getActivity());
			Intent intent_home = new Intent(getActivity(), StoreListActivity.class);
			startActivity(intent_home);
			break;

		default:
			break;
		}

	}

	@Override
	public void onDestroy() {
		// BitmapUtil.releaseView(rootView);
		// rootView = null;
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (vp != null) {
			vp.removeAllViews();
			vp = null;
		}
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// rootView = super.initView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_store, null);
		ex_listview = (ExpandableListView) rootView.findViewById(R.id.ex_listview);
		// MyProgress.show("", getActivity());
		new CsqRunnable(SHOP_INDEX).start();
		// initView();
		return rootView;
	}

	@Override
	protected void onListItemClick(StoreEntity item) {

	}

	@Override
	protected int getPage() {
		return 0;
	}

	@Override
	protected List<StoreEntity> getDataListFromCache(String userId, String cid) {
		return null;
	}

	@Override
	protected List<StoreEntity> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<StoreEntity> data) {
		// if (data != null && data.size() > 0) {
		// return new StoreAdapter(getActivity(), tag_goods, type_goods);
		// }
		return null;
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == BANNDER) {
			storeBanner = storeBiz.getStoreBanner();
		} else if (operate == SHOP_INDEX) {
			storeEntity = storeBiz.getStoreEntity("shop_inde");
		}
		return super.doInBackground(operate, objs);
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && operate == BANNDER) {
			initBannerView();
			listView.addHeaderView(bannerView);
		} else if (result && operate == SHOP_INDEX) {
			tag_goods = storeEntity.tag_goods;
			type_goods = storeEntity.type_goods;
			StoreAdapter storeadapter = new StoreAdapter(getActivity(), tag_goods, type_goods);
			ex_listview.setAdapter(storeadapter);
			for (int i = 0; i < storeadapter.getGroupCount(); i++) {
				ex_listview.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
			}
		}
		return super.handleResult(result, operate, obj);
	}

}
