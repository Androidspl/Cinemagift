package com.skyfilm.owner.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.umeng.message.PushAgent;

//跟App相关的辅助类
public class AppUtils {

	private static String APP_ID;

	private AppUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本号]
	 * 
	 * @param context
	 * @return 当前应用的版本
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	 * 
	 * @return The number of cores, or 1 if failed to get result
	 */
	public static int getNumCores() {
		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					// Check if filename is "cpu", followed by a single digit
					// number
					if (Pattern.matches("cpu[0-9]", pathname.getName())) {
						return true;
					}
					return false;
				}

			});
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			L.printStackTrace(e);
			return 1;
		}
	}

	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	public static String getAppId() {
		if (StringUtil.isNull(APP_ID)) {
			APP_ID = getMetaValue(MainApplication.getInstance(), "App_id");
		}
		return APP_ID;
	}
	
	public static void resetTags(){
		//final PushAgent mPushAgent = PushAgent.getInstance(MainApplication.getInstance());
		CsqThreadFactory.getExecutorService().execute(new Runnable() {

			@Override
			public void run() {
				try {
					UserBiz biz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
					try {
						//mPushAgent.getTagManager().reset();
					} catch (Exception e) {
					}
					try {
						//mPushAgent.getTagManager().add(biz.getCurrentCommunityId());
					} catch (Exception e) {
					}
				} catch (Exception e) {
					L.printStackTrace(e);
				}

			}
		});
	}


	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		if (TextUtils.isEmpty(mobiles))
			return false;
		return mobiles.matches(Const.MOBILE_REG);
	}
	public static void stopPush(final User u) {
		if(u == null) return;
		final PushAgent mPushAgent = PushAgent.getInstance(MainApplication.getInstance());
		CsqThreadFactory.getExecutorService().execute(new Runnable() {

			@Override
			public void run() {
				try {
					//删除别名
					if(!StringUtil.isNull(u.getUser_id())){
						try {
							mPushAgent.removeAlias(u.getUser_id(), "user_id");
						} catch (Exception e) {
						}
					}
					//删除所有标签
					try {
						mPushAgent.getTagManager().reset();
					} catch (Exception e) {
					}
				} catch (Exception e) {
					L.printStackTrace(e);
				}
			}
		});
	}
	
	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
