package com.skyfilm.owner.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.LoginActivity;
import com.skyfilm.owner.adapter.HomeAdapter;
import com.skyfilm.owner.base.BaseThreadFragment;
import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.biz.HomePageBiz;
import com.skyfilm.owner.communication.mine.HomePageCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.NoticeActivity;
import com.skyfilm.owner.page.GeneralizePage;
import com.skyfilm.owner.page.InformationPage;
import com.skyfilm.owner.page.TopicPage;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页
 * 
 * @author min.yuan
 * @param <T>
 *
 */
public class HomePageFragment<T> extends BaseThreadFragment implements OnCheckedChangeListener, OnClickListener {
	private View rootView;
	private ViewPager home_vp;
	private List<Fragment> vList;
	private TextView information;
	private TextView topic;
	private TextView generalize;
	private RadioGroup rg;

	private ImageView left;
	private TextView center;
	private ImageView right1;
	private TextView right2;

	private HomePageCom homepageCom;
	private HomePageBiz homePageBiz;
	private static final int HOME_INFO = 0x0122;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = View.inflate(getActivity(), R.layout.home_page_f, null);
		initData();
		return rootView;
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		homePageBiz = (HomePageBiz) CsqManger.getInstance().get(Type.HOMEPAGEBIZ);
		left = (ImageView) rootView.findViewById(R.id.iv_left);
		center = (TextView) rootView.findViewById(R.id.tv_center);
		right1 = (ImageView) rootView.findViewById(R.id.iv_right1);
		right2 = (TextView) rootView.findViewById(R.id.tv_right2);
		center.setText("电影礼物");
		right1.setImageResource(R.drawable.icon_search);
		right2.setBackgroundResource(R.drawable.ico_shoppingcart);
		left.setImageResource(R.drawable.ico_msgs);
		left.setOnClickListener(this);
		right1.setOnClickListener(this);
		right2.setOnClickListener(this);

		home_vp = (ViewPager) rootView.findViewById(R.id.home_vp);
		rg = (RadioGroup) rootView.findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		setCurrentItem();
		boolean networkConnected = AppUtils.isNetworkConnected(getActivity());
		initFragment(homePageBiz.getHomePageFromCache("User_id"));
		new CsqRunnable(HOME_INFO).start();

	}

	public void initFragment(HomePage homepage) {
		FragmentActivity activity = getActivity();
		vList = new ArrayList<Fragment>();
		vList.add(new InformationPage(homepage, activity));
		vList.add(new TopicPage(null, activity));
		vList.add(new GeneralizePage(null, activity));
		home_vp.setAdapter(new HomeAdapter(getFragmentManager(), vList));
		home_vp.setCurrentItem(0);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		HomePage homePage = null;
		if (HOME_INFO == operate) {
			try {
				homePage = homePageBiz.getHomePage(null, "1", "10");
			} catch (Exception e) {
				homePage = homePageBiz.getHomePageFromCache("User_id");
			}
			return homePage;
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (HOME_INFO == operate && result) {
			HomePage homepage = (HomePage) obj;
			initFragment(homepage);
		}
		return false;
	}

	private void setCurrentItem() {
		home_vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					rg.check(R.id.information);
				} else if (position == 1) {
					rg.check(R.id.topic);
				} else {
					rg.check(R.id.generalize);
				}
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.information:// 资讯
			home_vp.setCurrentItem(0);
			break;
		case R.id.topic:// 专题
			home_vp.setCurrentItem(1);
			break;
		case R.id.generalize:// 推广
			home_vp.setCurrentItem(2);
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_left:
			// TODO 跳转消息
			intent.setClass(getActivity(), NoticeActivity.class);
			getActivity().startActivity(intent);
			CsqToast.show("跳转消息", getActivity());
			break;
		case R.id.iv_right1:
			// TODO 跳转搜索
			CsqToast.show("跳转搜索", getActivity());
			break;
		case R.id.tv_right2:
			// TODO 跳转购物车
			CsqToast.show("跳转购物车", getActivity());
			break;

		default:
			break;
		}
	}
}
