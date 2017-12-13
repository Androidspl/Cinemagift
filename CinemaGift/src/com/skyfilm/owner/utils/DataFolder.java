package com.skyfilm.owner.utils;

import java.io.File;

import com.skyfilm.owner.MainApplication;

import android.os.Environment;
/**
 * App应用的数据根目录
 */
public class DataFolder {
	 private static String appDataRoot = null;
	 
	 public static String getAppDataRoot(){
		 if(appDataRoot == null){
			 CreateDataFolders();
		 }
		 return appDataRoot;
	 }
	 
	 private static void CreateDataFolders(){
		File files = new File(Environment.getExternalStorageDirectory() + File.separator + "loganowner");
		if (!files.exists()) {
			files.mkdirs();
		}
		
		if(!(files.canWrite()&&files.canRead())){
			files = MainApplication.getInstance().getFilesDir();
			if(!(files.canWrite()&&files.canRead())){
				files = MainApplication.getInstance().getExternalCacheDir();
			}
		}
		appDataRoot = files.getAbsolutePath() + File.separator;
		
	 }
}
