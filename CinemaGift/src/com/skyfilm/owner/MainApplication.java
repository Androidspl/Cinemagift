 package com.skyfilm.owner;

import java.io.File;
import java.util.Vector;
import org.apache.http.client.CookieStore;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.skyfilm.owner.utils.SPCookieStore;
import com.umeng.common.message.Log;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.style.TtsSpan.TimeBuilder;
import android.widget.Toast;

public class MainApplication extends Application {
	private static MainApplication instance;
	private static SPCookieStore SPCookieStore;
	private Vector<Activity> activies = new Vector<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
 		instance = this;
 		startPush();
		initCookieStore();
		initImageLoader();
		configData();
	}

	private void startPush() {
		PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
		mPushAgent.enable();
		String device_token = UmengRegistrar.getRegistrationId(getApplicationContext());
		Log.d("Tag", "device_token = "+device_token);
		/**
		 * 该Handler是在BroadcastReceiver中被调用，故
		 * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
		 * */
		UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
		    @Override
		    public void dealWithCustomAction(Context context, UMessage msg) {
		        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
		    }
		};
		mPushAgent.setNotificationClickHandler(notificationClickHandler);
	}
	
	private void configData() {
		// 微信 appid appsecret
		PlatformConfig.setWeixin("wx32377228dac3cb59", "a30de5a018ad966882065c5319fb8ad3");
		// 新浪微博 appkey appsecret
		PlatformConfig.setSinaWeibo("1967505809", "1b2c6aa1f6881d18b9483c6314d7e97b");
		// QQ和Qzone appid appkey
		PlatformConfig.setQQZone("1105452589", "xI2Ih0mRIdYFIrQL");
	}

	/**
	 * 获取应用的实例化对象
	 * 
	 * @return instance
	 */
	public static MainApplication getInstance() {
		return instance;
	}

	private void initCookieStore() {
		SPCookieStore = new SPCookieStore(getApplicationContext());
	}

	/**
	 * 获取CookieStore
	 * 
	 * @return
	 */
	public CookieStore getCookieStore() {
		return SPCookieStore;
	}

	/**
	 * ImageLoader 的初始化
	 */
	private void initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "caches/image");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.memoryCacheExtraOptions(800, 1200).threadPoolSize(3)
				// 线程池的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 线程池的属性
				.denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
				.memoryCacheSize(4 * 1024 * 1024).diskCacheSize(100 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO).diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCache(new UnlimitedDiskCache(cacheDir))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(getApplicationContext())).build();

		ImageLoader.getInstance().init(config);
	}

	public void addActivity(Activity a) {
		if (activies == null){
			activies = new Vector<>();
		}else{
			if(activies.contains(a)){
				return;
			}else{
				activies.add(a);
			}
		}
	}

	public void removeActivity(Activity a) {
		if (activies != null)
			activies.remove(a);
	}

	public void finishActivity(Activity a) {
		Activity activity = null;
		for (int i = 0; i < activies.size(); i++) {
			activity = activies.get(i);
			if (activity.getClass().equals(a.getClass())) {
				if (!a.isFinishing())
					a.finish();
				activies.remove(i);
				
				i--;
			}
		}
	}
	public void exit(){
		if(activies != null && !activies.isEmpty()){
			Activity activity = null;
			while (activies.size()>0) {
				activity = activies.get(activies.size()-1);
				activies.remove(activity);
				try {
					if(activity!=null && !activity.isFinishing()){
						activity.finish();
					}
				} catch (Exception e) {
				}
			}
		}
		activies = null;
//		MiaodouKeyAgent.setNeedSensor(false);
//		NEED_CHECK_LOCK = true;
//		NEED_CHECK_SET_LOCK = true;
//		Intent intent = new Intent();
//		intent.setAction("android.intent.action.MAIN");
//		intent.addCategory("android.intent.category.LAUNCHER");
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
	}
}
