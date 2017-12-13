package com.skyfilm.owner.utils;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {
    /**
     * 得到存储的boolean值
     * @param context
     * @param key
     * @param value 默认的值
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    /**
     * 存储boolean的值
     * @param context
     * @param key
     * @param value
     */
    public static void setBoolean(Context context,String key,boolean value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static String getString(Context context,String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void setString(Context context,String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context,String key,int value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    public static void setInt(Context context,String key,int value) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    public static void remove(Context context,String[] keys){
    	SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
    	Editor edit = sp.edit();
    	if(keys == null && keys.length<=0){
    		return;
    	}else{
    		for(int i = 0;i<keys.length;i++){
    			edit.remove(keys[i]);  
    			edit.clear();   
    			edit.commit();  
//    			CsqToast.show("清除SharedPreferences数据成功", context);
    		}
    	}
    }
}
