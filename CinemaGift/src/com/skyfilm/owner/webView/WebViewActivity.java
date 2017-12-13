package com.skyfilm.owner.webView;

import java.util.Map;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.CustomerActivity;
import com.skyfilm.owner.utils.SPCookieStore;
import com.skyfilm.owner.utils.Util;
import com.skyfilm.owner.widget.ProgressWebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends BaseThreadActivity implements OnClickListener {
	private ProgressWebView webView;
	private String url;
	private String title;
	private String picPath;

	private View activityRootView;
	// 屏幕高度
	private int screenHeight = 0;
	// 软件盘弹起后所占高度阀值
	private int keyHeight = 0;

	TextView show;
	private View webNavigation;
	private View webTopSearch;
	private TextView titleTextView;
	private TextView cancleTopSearch;
	private ImageView webLeftIcon;
	private ImageView webRightIcon;
	private TextView webCenterDesc;
	private boolean isSearch = false;
	private AutoCompleteTextView searchContent;
	private ArrayAdapter<String> arrayAdapter;
	private static final int TYPE_LEFT = 1;
	private static final int TYPE_RIGHT = 2;
	private static final int STYLE_SCAN = 2;
	private static final int STYLE_SEARCH = 1;
	private static final int STYLE_BACE = 3;
	private static final int STYLE_HIDE = -1;
	private int currentLeftType = STYLE_BACE;
	private int currentRightType = STYLE_HIDE;
	private int screenWidth;
	private boolean notitle;
	private boolean fromList;

	private LinearLayout ll_bottom;
	private ImageView mCounselor;
	private TextView comment;
	private TextView share;
	private TextView praise;

	private String SHOPPINGCAR = "shoppingCar";
	private String GOODSDETAILS = "goodsDetails";
	private String OTHERS = "others";
	private boolean havebottom = false;// (带评论转发的)PAGETYPE
	private boolean counselor = false;// (带悬浮咨询的)COUNSELOR
	private boolean noactionbar = false;// (没有actionbar)noactionbar
	//url TITLE 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		setContentView(R.layout.webview_activity);
		activityRootView = findViewById(R.id.root);
		// 获取屏幕高度
		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
		// 阀值设置为屏幕高度的1/3
		keyHeight = screenHeight / 3;
		initView();
		if(noactionbar){
			hildActionBar(getSupportActionBar());
		}else{
			initActionBar();
		}
		initWebView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		// activityRootView.addOnLayoutChangeListener(this);
	}

	private void initView() {
		ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
		if (havebottom) {
			ll_bottom.setVisibility(View.VISIBLE);
			comment = (TextView) findViewById(R.id.comment);//评论
			share = (TextView) findViewById(R.id.share);//分享
			praise = (TextView) findViewById(R.id.praise);//点赞
			comment.setOnClickListener(this);
			share.setOnClickListener(this);
			praise.setOnClickListener(this);
		}else{
			ll_bottom.setVisibility(View.GONE);
		}
		mCounselor = (ImageView) findViewById(R.id.counselor);
		if(counselor){
			mCounselor.setVisibility(View.VISIBLE);
		}else{
			mCounselor.setVisibility(View.GONE);
		}
		mCounselor.setOnClickListener(this);
		
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		this.titleTextView = title;
		left.setVisibility(View.VISIBLE);
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
		// TODO 设置左箭头
		// setLeftTitleImage(R.drawable.icon_jiantou_left);
		title.setText(getMyTitle());
		title.setTextColor(Util.getColor(R.color.text_grey));
	}

	private void hideSoftKeyboard() {
		try {
			searchContent.dismissDropDown();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchContent.getWindowToken(), 0);
		} catch (Exception e) {
		}
	}

	// 当左边图片是返回时,finish页面。
//	protected void onLeftViewClick(View v) {
//		onWebViewTitleClick(currentLeftType);
//	}

//	private void onWebViewTitleClick(int type) {
//		switch (type) {
//		// case STYLE_SEARCH:
//		// onSearchViewClick();
//		// break;
//		case STYLE_BACE:
//			this.finish();
//			break;
//		// case STYLE_SCAN:
//		// case STYLE_HIDE:
//		// default:
//		// return;
//		}
//	}

	// @Override
	// protected void resultForScan(String result) {
	// webView.loadUrl("javascript:resultForScan('" + result + "');");
	// }

	// private void dealTitleChange(ImageView iv, int style) {
	// if (iv == null)
	// return;
	// switch (style) {
	// case STYLE_SEARCH:
	// break;
	// case STYLE_BACE:
	// break;
	// case STYLE_SCAN:
	// break;
	// case STYLE_HIDE:
	// iv.setVisibility(View.GONE);
	// default:
	// return;
	// }
	// iv.setVisibility(View.VISIBLE);
	//
	// }

	private void initData() {
		Intent intent = getIntent();
		if (intent != null) {
			url = intent.getStringExtra("url");
			// url = "https://www.baidu.com/";
			title = intent.getStringExtra("TITLE");
			havebottom = intent.getBooleanExtra("PAGETYPE", false);
			counselor = intent.getBooleanExtra("COUNSELOR", false);
			noactionbar = intent.getBooleanExtra("noactionbar", false);
		}
	}

	public String getMyTitle() {
//		return StringUtil.isNull(title) ? getApplicationInfo().loadLabel(getPackageManager()).toString() : title;
		return title;
	}

	@SuppressLint("SetJavaScriptEnabled")
	// 初始化webview
	private void initWebView() {
		webView = (ProgressWebView) findViewById(R.id.webView);
		SPCookieStore.syncLocalCookie2WebView();
		webView.addJavascriptInterface(new dylwJavaScriptInterface(), "dylw");
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setCacheMode(WebSettings.LOAD_DEFAULT);
		ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		ws.setUseWideViewPort(true);// 可任意比例缩放
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSaveFormData(true);// 保存表单数据
		ws.setDomStorageEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl(url);
	}

	public boolean onKeyDown(int keyCoder, KeyEvent event) {
		if (isSearch && keyCoder == KeyEvent.KEYCODE_BACK) {
			isSearch = false;
			hideSoftKeyboard();
			return true;
		}
		if (KeyEvent.KEYCODE_ENTER == keyCoder && isSearch) {
			hideSoftKeyboard();
			return true;
		}
		if (webView != null && webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
			webView.goBack();
			return true;
		}

		return super.onKeyDown(keyCoder, event);
	}

	@Override
	protected int getActionBarColor() {
		return Util.getColor(R.color.white);
	}
//
//	@Override
//	protected void initActionBar() {
//		try {
//			getActionBar().hide();
//		} catch (Exception e) {
//		}
//	}

	protected void onDestroy() {
		super.onDestroy();
		webView.clearHistory();
		setWebviewSecurityConfig();
	}

	public void setWebviewSecurityConfig() {
		webView.removeJavascriptInterface("dylw");
	}

	public class dylwJavaScriptInterface {
		@JavascriptInterface
		public boolean isApp() {
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.comment://评论
			intent.setClass(this, WebViewActivity.class);
			intent.putExtra("url", "https://www.baidu.com/");
			intent.putExtra("PAGETYPE", false);
			intent.putExtra("TITLE", "评论列表");
			intent.putExtra("COUNSELOR", false);
			startActivity(intent);
			break;
		case R.id.share://分享
			UMImage image = new UMImage(WebViewActivity.this, "http://www.umeng.com/images/pic/social/integrated_3.png");
			final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
					SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.TENCENT};
			new ShareAction(this).setDisplayList(displaylist)
			.withText("呵呵")
			.withTitle("title")
			.withTargetUrl("http://www.baidu.com")
			.withMedia(image)
			.setListenerList(umShareListener)
			.open();
			break;
		case R.id.praise://点赞
			
			break;
		case R.id.counselor://客服
			intent.setClass(this, CustomerActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		return false;
	}
	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			Log.d("plat", "platform" + platform);
			if (platform.name().equals("WEIXIN_FAVORITE")) {
				Toast.makeText(WebViewActivity.this, platform + " 收藏成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(WebViewActivity.this, platform + " 分享成功", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(WebViewActivity.this, platform + " 分享失败", Toast.LENGTH_SHORT).show();
			if (t != null) {
				Log.d("throw", "throw:" + t.getMessage());
			}
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(WebViewActivity.this, platform + " 分享取消", Toast.LENGTH_SHORT).show();
		}
	};
}
