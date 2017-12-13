package com.skyfilm.owner.base;

import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.R;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseActivity extends ActionBarActivity {

	protected ActionBar actionBar;
	private View customView;
	private ImageView left;
	private ImageView right1;
	private TextView right2;
	private TextView right3;
	private TextView center;
	protected UserBiz userBiz;
	public static boolean IS_STOP_BY_HOME_PRESS = false;
	/**
	 * 初始化activity的顶部导航栏。
	 * 
	 * @param left
	 * @param title
	 * @param right1
	 * @param right2
	 */
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
	}
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2,TextView right3) {
	}

	public void hildActionBar(ActionBar actionBar) {
		actionBar.hide();
	}

	/**
	 * 初始化ActionBar,包含布局和点击功能
	 */
	protected void initActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.title);
//		actionBar.setBackgroundDrawable(new ColorDrawable(getActionBarColor()));
		customView = actionBar.getCustomView();
		left = (ImageView) customView.findViewById(R.id.iv_left);
		left.setImageResource(R.drawable.btn_back_gray);
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onLeftViewClick(v);
			}
		});

		right1 = (ImageView) customView.findViewById(R.id.iv_right1);
		right1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onRightViewClick1(v);
			}
		});
		right2 = (TextView) customView.findViewById(R.id.tv_right2);
		right2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onRightViewClick2(v);
			}
		});
		right3 = (TextView) customView.findViewById(R.id.tv_right3);
		right3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRightViewClick3(v);
			}
		});

		center = (TextView) customView.findViewById(R.id.tv_center);
		center.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTitleClick(v);
			}
		});
		initTiltle(left, center, right1, right2);
		initTiltle(left, center, right1, right2,right3);
	}

	protected final void setLeftImage(int resId) {

		if (resId > 0) {
			left.setBackground((getResources().getDrawable(resId)));
		}
	}

	protected final void setCenterTitle(String s) {
		center.setText(s);
	}

	protected final void setRightImage1(int resId) {

		if (resId > 0) {
			right1.setBackground((getResources().getDrawable(resId)));
		}
	}

	protected final void setRightImage2(int resId) {

		if (resId > 0) {
			right2.setBackground((getResources().getDrawable(resId)));
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initActionBar();
	};

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initActionBar();
	}
	
	/**
	 * 继承android的onCreate,将本activity添加到应用。
	 * 获取userbiz
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainApplication.getInstance().addActivity(this);
		userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
	}

	/**
	 * 导航右侧文本框的点击
	 * 
	 * @param v
	 */
	protected void onRightViewClick1(View v) {
	}

	protected void onRightViewClick2(View v) {
	}
	
	protected void onRightViewClick3(View v) {
	}

	/**
	 * 顶部导航左侧 返回键的点击功能,消除activity的所有回调和信息。
	 * 
	 * @param v
	 */
	protected void onLeftViewClick(View v) {
		this.finish();
	}

	protected void onTitleClick(View v) {
	}

	/**
	 * 获取actiobar的颜色
	 * 
	 * @return
	 */
	protected int getActionBarColor() {
		return getResources().getColor(R.color.title_bg);
	}

	public interface ScanResultListener {
		boolean resultForScan(String result);
	}

	protected void resultForScan(String result) {
		CsqToast.show(result, this);
	}

	final void setTitleImage(int leftRes, int topRes, int rightRes, int bottomRes) {
		Drawable left = null, top = null, right = null, bottom = null;
		if (leftRes != -1) {
			left = getResources().getDrawable(leftRes);
			left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
		}
		if (rightRes != -1) {
			right = getResources().getDrawable(rightRes);
			right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
		}
		if (topRes != -1) {
			top = getResources().getDrawable(topRes);
			top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
		}
		if (bottomRes != -1) {
			bottom = getResources().getDrawable(bottomRes);
			bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());
		}
		if (center != null)
			center.setCompoundDrawables(left, top, right, bottom);
	}

	void setCustomTitleTextcolor(int color) {
		if (center != null && color != 0) {
			center.setTextColor(color);
		}
	}

	void setCustomTitleBackgroundColor(int color) {
		if (customView != null && color != 0) {
			customView.setBackgroundColor(color);
		}
	}

	void hideLeftView() {
		if (left != null)
			left.setVisibility(View.GONE);
	}

	void hideRightView1() {
		if (right1 != null)
			right1.setVisibility(View.GONE);
	}

	void hideRightView2() {
		if (right2 != null)
			right2.setVisibility(View.GONE);
	}

	void showLeftView() {
		if (left != null)
			left.setVisibility(View.VISIBLE);
	}

	void showRightView1() {
		if (right1 != null)
			right1.setVisibility(View.VISIBLE);
	}

	void showRightView2() {
		if (right2 != null)
			right2.setVisibility(View.VISIBLE);
	}

	@SuppressLint("NewApi")
	void initRightView1(int resId) {
		Drawable drawable = null;
		if (resId > 0) {
			drawable = getResources().getDrawable(resId);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			right1.setImageResource(resId);
			// right1.setCompoundDrawables(null, null, drawable, null);
			// right1.setCompoundDrawablePadding(6);
		}
		// if(text != null)
		// right1.setText(text);
	}

	@SuppressLint("NewApi")
	void initRightView2(int resId) {
		Drawable drawable = null;
		if (resId > 0) {
			drawable = getResources().getDrawable(resId);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			right2.setBackgroundResource(resId);
			// right1.setCompoundDrawables(null, null, drawable, null);
			// right1.setCompoundDrawablePadding(6);
		}
		// if(text != null)
		// right1.setText(text);
	}

	void initLeftView(int resId) {
		Drawable drawable = null;
		if (resId > 0) {
			drawable = getResources().getDrawable(resId);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			left.setImageResource(resId);
			// left.setCompoundDrawables(drawable, null, null, null);
		}
		// if(text != null)
		// left.setText(text);

	}

	View getLeftView() {
		return left;
	}

	View getRightView1() {
		return right1;
	}

	View getRightView2() {
		return right2;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainApplication.getInstance().removeActivity(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event != null && keyCode == KeyEvent.KEYCODE_HOME) 
		{
			IS_STOP_BY_HOME_PRESS = true;
		}
		else if(keyCode == KeyEvent.KEYCODE_MENU)
		{
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
