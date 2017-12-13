/**
 * 
 */
package com.skyfilm.owner.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;


public class ViewPagerAdapter extends PagerAdapter
{

	private List<ImageView> list;

	public ViewPagerAdapter(List<ImageView> list)
	{
		this.list = list;
	}

	@Override
	public void destroyItem(View view, int position, Object arg2)
	{
		 ImageView v = (ImageView)arg2;  
        ((ViewPager) view).removeView(view);  
	}

	@Override
	public void finishUpdate(View arg0)
	{
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object instantiateItem(View view, int position)
	{
		try {
			ViewPager pViewPager = ((ViewPager) view);
			//TODO 
			pViewPager.addView(list.get(position));
		} catch (Exception e) {
		}
		return list.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1)
	{
	}
}
