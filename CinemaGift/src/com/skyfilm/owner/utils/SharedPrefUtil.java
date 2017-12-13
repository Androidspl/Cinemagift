package com.skyfilm.owner.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.skyfilm.owner.MainApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtil {
	private static final String USER_ID_KEY = "key4cownerlogin";
	private static final String SP_NAME = "cowner";
	private static final String GONGDAN = "gongdan";
	private static final String USER_SP_NAME = "cowneruser";
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String USER_COOKIE_SP_NAME = "cownerusercookie";
	private static final String INPUT_SPNAME = "USERINPUT";
	private static final String INPUT_DEFAULT_KEY = "DEFAULT_USERINPUT";
	public static SharedPreferences getSharedPrefe(String spName) {
		return MainApplication.getInstance().getSharedPreferences(StringUtil.isNull(spName)?SP_NAME:spName,
				Context.MODE_PRIVATE);
	}

	public static void putUserID(String userId) {
		MainApplication.getInstance()
		.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE)
		.edit().putString(USER_ID_KEY, userId).commit();
	}

	public static String getUserID() {
		return MainApplication.getInstance()
				.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE)
				.getString(USER_ID_KEY, null);
	}
	
	//保存用户输入的信息
	public static void saveUserInput(String key,String input){
		if(StringUtil.isNull(input)) return ;
		if(StringUtil.isNull(key)) {
			key=INPUT_DEFAULT_KEY;
		}
		SharedPreferences preferences = SharedPrefUtil.getSharedPrefe(INPUT_SPNAME);
		Editor e = preferences.edit();

		String content=preferences.getString(key, null);
		if(content!=null ) {
			if(!content.contains(input))
				input = content.concat(",").concat(input); 
			else
				return;
		}
		e.putString(key, input);
		e.commit();
	} 
	public static void saveUserInput(String input){
		saveUserInput(INPUT_DEFAULT_KEY,input);
	} 
	//获取用户输入的信息
	public static String[] getUserInput(String key){		
		String result[] = null ;
		try {
			String	content = SharedPrefUtil.getSharedPrefe(INPUT_SPNAME).getString(key, "");
			if(!StringUtil.isNull(content)){
				result=content.split(",");
			}
		} catch (Exception e) {
//			L.printStackTrace(e);
		}
		return result ;
	}
	public static String[] getUserInput(){

		return getUserInput(INPUT_DEFAULT_KEY);
	}

	//保存执行页的信息
	public static void setMsg(String spName,String key,Object  msg){
		Editor e = SharedPrefUtil.getSharedPrefe(spName).edit();
		e.putString(key, String.valueOf(msg));
		e.commit();
	} 
	//获取执行页的信息
	public static String getMsg(String spName,String key){		
		String result = "";
		try {
			result = SharedPrefUtil.getSharedPrefe(spName).getString(key, "");
		} catch (Exception e) {
//			L.printStackTrace(e);
		}
		return result ;

	}

	//
	public static String ReadLine(String Path)
	{
		String ret=null;
		try {
			File file = new File(Path);
			FileReader fr=null;
			BufferedReader reader=null;
			try{
				fr=new FileReader(file);
				reader=new BufferedReader(fr);
				ret=reader.readLine();
			} catch (FileNotFoundException e) { 
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
					fr.close();
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void WriteText(String Path,String Txt)
	{
		//LogMsg=LogMsg+"\r\n";
		try {
			File file = new File(Path);
			FileWriter fw = null;
			BufferedWriter writer = null;
			try {
				fw = new FileWriter(file,false);
				writer = new BufferedWriter(fw);            
				writer.write(Txt);
				writer.newLine();//换行
				writer.flush();
			} catch (FileNotFoundException e) { 
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					writer.close();
					fw.close();
				} catch (IOException e) {	
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//by zhouchen 2015-04-21 for service log
	public static void WriteText2(String Path,String Txt)
	{
		//LogMsg=LogMsg+"\r\n";
		try {
			File file = new File(Path);
			FileWriter fw = null;
			BufferedWriter writer = null;
			try {
				fw = new FileWriter(file,true);//by zhouchen 2015-04-21 for service log
				writer = new BufferedWriter(fw);            
				writer.write(Txt);
				writer.newLine();//换行
				writer.flush();
			} catch (FileNotFoundException e) { 
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					writer.close();
					fw.close();
				} catch (IOException e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Log(String LogMsg)
	{
		LogMsg="TIME:"+df.format(new Date())+"\t"+LogMsg+"\r\n";
		WriteText2(DataFolder.getAppDataRoot()+"log.txt", LogMsg);	//by zhouchen 2015-04-21 for service log
	}

	public static void setInfo(String key,String value){
		if(StringUtil.isNull(key)){
			return;
		}
		MainApplication.getInstance()
		.getSharedPreferences(USER_COOKIE_SP_NAME, Context.MODE_PRIVATE)
		.edit().putString(key, value).commit();
	}

	public static String  getInfo(String key){
		if(StringUtil.isNull(key)){
			return "";
		}
		return MainApplication.getInstance()
				.getSharedPreferences(USER_COOKIE_SP_NAME, Context.MODE_PRIVATE).getString(key, null);
	}

	public static void  clearInfo(){
		MainApplication.getInstance()
		.getSharedPreferences(USER_COOKIE_SP_NAME, Context.MODE_PRIVATE).edit().clear().commit();
	}
}
