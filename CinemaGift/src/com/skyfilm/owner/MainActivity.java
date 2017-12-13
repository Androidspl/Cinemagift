package com.skyfilm.owner;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.skyfilm.owner.base.BaseActivity;
import com.skyfilm.owner.homepage.CrowdFundingFragment;
import com.skyfilm.owner.homepage.HomePageFragment;
import com.skyfilm.owner.homepage.MineFragment;
import com.skyfilm.owner.homepage.StoreFragment;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends BaseActivity implements OnCheckedChangeListener {
	private RadioGroup rGroup;
	private FragmentTransaction ft;
	private long firstTime;

	// private final static float TARGET_HEAP_UTILIZATION = 0.75f;
	// private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hildActionBar(getSupportActionBar());
		initView();
		initData();
	}

	private void initView() {
		rGroup = (RadioGroup) findViewById(R.id.rGroup);
	}

	private void initData() {
		rGroup.setOnCheckedChangeListener(this);
		ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.mainBody, new HomePageFragment()).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_page:// 首页
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.mainBody, new HomePageFragment()).commit();
			break;
		case R.id.store:// 商城
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.mainBody, new StoreFragment()).commit();
			break;
		case R.id.crowd_funding:// 众筹
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.mainBody, new CrowdFundingFragment()).commit();
			break;
		case R.id.mine:// 我的
			ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.mainBody, new MineFragment()).commit();
			break;
		default:
			break;
		}
	}

	/**
	 * 两次返回键推出程序
	 */
	@Override
	public boolean onKeyDown(int keyCoder, KeyEvent event) {

		if (keyCoder == KeyEvent.KEYCODE_BACK) {
			boolean isDealed = false;
//			try {
//				if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_business) {
//					// isDealed = serviceForOthersFragment.keyBackPressed();
//				}
//			} catch (Exception e) {
//			}
			if (!isDealed) {
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 800) {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
					return true;
				} else {
					// 否则退出程序
					MainApplication.getInstance().exit();
				}
			}
		}
		super.onKeyDown(keyCoder, event);
		return false;
	}
}
