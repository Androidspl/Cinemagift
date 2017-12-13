package com.skyfilm.owner.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 
 * @author yangbin
 * �̼ҵ��Զ���view
 *
 */
public class FullHeightListView extends ListView {

	public FullHeightListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FullHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FullHeightListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int hightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, hightMeasureSpec);
	}
}
