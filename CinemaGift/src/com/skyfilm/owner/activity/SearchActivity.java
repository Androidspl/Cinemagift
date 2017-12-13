package com.skyfilm.owner.activity;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.SearchAdapter;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.fragment.CrowdSearch;
import com.skyfilm.owner.fragment.GoodsSearch;
import com.skyfilm.owner.fragment.InformationSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SearchActivity extends BaseThreadActivity implements OnCheckedChangeListener,OnClickListener{
	
	private ViewPager home_vp;
	private RadioGroup rg;
	private ArrayList<Fragment> vList;
	private List<Goods> mlist = new ArrayList<Goods>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hildActionBar(getSupportActionBar());
		setContentView(R.layout.activity_search);
		
		initView();
	}
	
	/**
	 * 模拟数据<br>
	 * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
	 * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
	 */
	private void virtualData() {

		for (int i = 0; i < 10; i++) {
			Goods goods = new Goods("image", "第八号当铺" + "的第" + (i + 1) + "个商品", 120.45 + i * 2);
			mlist.add(goods);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
		}
	}

	private void initView() {
		home_vp = (ViewPager) findViewById(R.id.home_vp);
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		setCurrentItem();
		initFragment();
	}
	
	private void setCurrentItem() {
		home_vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if(position == 0){
					rg.check(R.id.information);
				}else if(position == 1){
					rg.check(R.id.topic);
				}else {
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
	
	public void initFragment() {
		virtualData();
		vList = new ArrayList<Fragment>();
		vList.add(new GoodsSearch(mlist,this));
		vList.add(new CrowdSearch(mlist,this));
		vList.add(new InformationSearch(mlist,this));
		home_vp.setAdapter(new SearchAdapter(getSupportFragmentManager(), vList));
		home_vp.setCurrentItem(0);
	}

	public void onClick(View v) {
		
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.information://商品
			home_vp.setCurrentItem(0);
			break;
		case R.id.topic://众筹
			home_vp.setCurrentItem(1);
			break;
		case R.id.generalize://资讯
			home_vp.setCurrentItem(2);
			break;
		default:
			break;
		}
		
	}

}
