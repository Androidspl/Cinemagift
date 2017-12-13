package com.skyfilm.owner.utils;



import com.skyfilm.owner.Const;

import android.content.Context;
import android.util.Log;
//Logcat统一管理类
public class L
{

	private static final int LEVEL_VERBOSE = 5;
	private static final int LEVEL_DEBUG = 4;
	private static final int LEVEL_INFO = 3 ;
	private static final int LEVEL_WARN = 2;
	private static final int LEVEL_ERROR = 1;
	private static final int LEVEL_ASSERT = 0;
	private static  int LOG_LEVEL = 2;
	private L()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	private static final String TAG = "CSQ";

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		i(TAG, msg);
	}

	public static void d(String msg)
	{
		d(TAG, msg);
	}

	public static void e(String msg)
	{
		e(TAG, msg);
	}
	public static void w(String msg)
	{
		w(TAG, msg);
	}


	public static void v(String msg)
	{

		v(TAG, msg);
	}


	// 下面是传入类名作为tag的函数
	public static void i(Class<?> tag, String msg)
	{
		i(tag.getSimpleName(), msg);
	}

	public static void d(Class<?> tag, String msg)
	{
		d(tag.getSimpleName(), msg);
	}

	public static void e(Class<?> tag, String msg)
	{
		e(tag.getSimpleName(), msg);
	}
	public static void w(Class<?> tag, String msg)
	{
		w(tag.getSimpleName(), msg);
	}

	public static void v(Class<?> tag, String msg)
	{
		L.v(tag.getSimpleName(), msg);
	}

	//下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_INFO){
			if(Const.DEBUG)
				Log.i(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[INFO_TAG]:"+tag+"[INFO_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[INFO_MSG]:"+msg);

		}
	}

	public static void d(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_DEBUG){
			if(Const.DEBUG)
				Log.d(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[DEBUG_TAG]:"+tag+"[DEBUG_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[DEBUG_MSG]:"+msg);

		}
	}

	public static void e(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_ERROR){
			if(Const.DEBUG)
				Log.e(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[EROOR_TAG]:"+tag+"[EROOR_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[EROOR_MSG]:"+msg);

		}
	}

	public static void v(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_VERBOSE){
			if(Const.DEBUG)
				Log.v(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[VERBOSE_TAG]:"+tag+"[VERBOSE_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[VERBOSE_MSG]:"+msg);

		}
	}
	public static void w(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_WARN){
			if(Const.DEBUG)
				Log.w(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[WARN_TAG]:"+tag+"[WARN_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[WARN_MSG]:"+msg);

		}
	}
	public static void a(String tag, String msg)
	{
		if (LOG_LEVEL>LEVEL_ASSERT){
			if(Const.DEBUG)
				Log.e(tag, msg);
			else
				if(!TAG.equals(tag))
					SharedPrefUtil.Log("[ASSERT_TAG]:"+tag+"[ASSERT_MSG]:"+msg);
				else
					SharedPrefUtil.Log("[ASSERT_MSG]:"+msg);

		}
	}

	// 下面是传入类名作为tag的函数
	public static void i(Context tag, String msg)
	{
		i(tag.getClass(), msg);
	}

	public static void d(Context tag, String msg)
	{
		d(tag.getClass(), msg);
	}

	public static void e(Context tag, String msg)
	{
		e(tag.getClass(), msg);
	}

	public static void v(Context tag, String msg)
	{
		v(tag.getClass(), msg);
	}

	public static void printStackTrace(Throwable e)
	{
		if (Const.DEBUG){
			e.printStackTrace();
		}else{
			if(LOG_LEVEL>LEVEL_ERROR)
				SharedPrefUtil.Log(Log.getStackTraceString(e));
		}
	}
}