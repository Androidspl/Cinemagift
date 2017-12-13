package com.skyfilm.owner.widget;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 自定义
 * @author lei
 * @date 2016年06月23日
 * @version 1.0
 */
public class MyPopWindows extends PopupWindow{
	public MyPopWindows() {
		init();
	}
	public MyPopWindows(View contentView) {
		setContentView(contentView);
		init();
	}
	private void init() {
		this.setWidth(LayoutParams.MATCH_PARENT);  
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.MATCH_PARENT); 
        
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);  
        
	}
}
