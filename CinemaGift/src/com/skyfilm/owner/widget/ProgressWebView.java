package com.skyfilm.owner.widget;

import java.lang.reflect.Method;

import com.skyfilm.owner.utils.L;
import com.skyfilm.owner.utils.SharedPrefUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

	private ProgressBar progressbar;
	private ZoomButtonsController zoom_controll;
	private float downX,downY;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 5, 0, 0));
		addView(progressbar);
		setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				MyProgress.dismiss();
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				SharedPrefUtil.Log("errorCode="+errorCode+" ,description="+description+" failingUrl="+failingUrl);
				if(ERROR_UNSUPPORTED_SCHEME == errorCode||ERROR_BAD_URL==errorCode || ERROR_CONNECT == errorCode || ERROR_FILE_NOT_FOUND == errorCode||ERROR_TIMEOUT==errorCode){
					view.loadUrl("file:///android_asset/ErrorPage/404.html");
				}else if(errorCode ==ERROR_UNKNOWN){
					view.loadUrl("file:///android_asset/ErrorPage/500.html");
				}else{
					super.onReceivedError(view, errorCode, description, failingUrl);
				}


			}
		});
		setWebChromeClient(new WebChromeClient());
		disableControls();
	}
	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			Dialog dialog = MyDialog.show(view.getContext(), "提示", message,null , "确定", null, null);
			dialog.setCancelable(false);
			dialog.setOnKeyListener(new DialogInterface.OnKeyListener(){

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					return true;
				}
			});

			result.confirm();
			return true;
		}
		@Override
		public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
			// TODO Auto-generated method stub
			return super.onJsConfirm(view, url, message, result);
		}
		@Override
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
				JsPromptResult result) {
			// TODO Auto-generated method stub
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	}


	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/** 
	 * Disable the controls 
	 */  
	private void disableControls() {  
		WebSettings settings = this.getSettings();  
		//基本的设置  
		settings.setJavaScriptEnabled(true);  
		settings.setBuiltInZoomControls(true);//support zoom  
		
		settings.setUseWideViewPort(true);  
		settings.setLoadWithOverviewMode(true);  
		if (Build.VERSION.SDK_INT >= 8) {  
			settings.setPluginState(WebSettings.PluginState.ON);  
		}
		this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);  
		//去掉滚动条  
		this.setVerticalScrollBarEnabled(false);  
		this.setHorizontalScrollBarEnabled(false);  

		//去掉缩放按钮  
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {  
			// Use the API 11+ calls to disable the controls  
			this.getSettings().setBuiltInZoomControls(true);  
			this.getSettings().setDisplayZoomControls(false);  
		} else {  
			// Use the reflection magic to make it work on earlier APIs  
			getControlls();  
		}  
		settings.setBuiltInZoomControls(false);
		settings.setSupportZoom(false);
		settings.setDisplayZoomControls(false);
	}  

	/** 
	 * This is where the magic happens :D 
	 */  
	private void getControlls() {  
		try {  
			Class webview = Class.forName("android.webkit.WebView");  
			Method method = webview.getMethod("getZoomButtonsController");  
			zoom_controll = (ZoomButtonsController) method.invoke(this, true);  
		} catch (Exception e) {  
			L.printStackTrace(e); 
		}  
	}  
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(event.getY()-downY>0 && getScaleY() == 0){
				return false;
			}
			break;
		default:
			downY = 0;
			break;
		}
		return super.onTouchEvent(event);
	}


	@Override
	public void loadUrl(String url) {
		if(url.startsWith("http://")){
			MyProgress.show("加载中",getContext());
		}
		super.loadUrl(url);
	}
}