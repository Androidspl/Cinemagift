package com.skyfilm.owner.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class SearchAdapter extends FragmentStatePagerAdapter {

private List<Fragment> list;
	
	public SearchAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.list = list ;
	}
	/*
	 * 切换具体的Frament
	 */
	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}
	/**
	 * 计算参与切换的Frament
	 */
	@Override
	public int getCount() {
		return list.size();
	}
    // 当某子项被摧毁时
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    // 判断该view是否来自对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }
}
