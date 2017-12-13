package com.skyfilm.owner.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.text.TextUtils;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(CharSequence str) {
		if (str == null)
			return true;
		str = str.toString().trim();
		if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str.toString())
				|| "UNDEFINED".equalsIgnoreCase(str.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 将多张图片的路径用？连接后返回
	 * 
	 * @param paths
	 *            图片的路径
	 * @return “” if paths is empty
	 */
	public static String convertList2String(List<String> paths) {
		if (paths == null || paths.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String str : paths) {
			builder.append(str);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

	/**
	 * 将以,拼接的路径拆分为一个List
	 * 
	 * @param str
	 *            带拆分的字符串
	 * @return 拆分后的集合
	 */
	public static List<String> convertString2List(String str) {
		ArrayList<String> result = null;
		if (!StringUtil.isNull(str)) {
			String[] strs = str.split(",");
			if (strs != null && strs.length > 0) {
				result = new ArrayList<String>();
				for (String s : strs) {
					if (s.startsWith("file://")) {
						s = s.substring("file://".length());
					} else if (s.startsWith("http://")) {
						continue;
					}
					result.add(s);
				}
			}
		}
		return result;
	}

	public static List<String> convertString2List(String str, String reg) {
		ArrayList<String> result = null;
		if (!StringUtil.isNull(str)) {
			String[] strs = str.split(reg);
			if (strs != null && strs.length > 0) {
				result = new ArrayList<String>();
				for (String s : strs) {
					result.add(s);
				}
			}
		}
		return result;
	}

	/**
	 * 获取开始和结束的时间段
	 * 
	 * @param startTime
	 * @param endTime
	 * @param formator
	 * @return
	 */
	public static String getTimeSectionString(long startTime, long endTime, SimpleDateFormat formator) {
		String start = formator.format(new Date(startTime));
		String end = formator.format(new Date(endTime));
		char[] starts = start.toCharArray();
		char[] ends = end.toCharArray();
		int differentIndex = 0;
		for (int i = 0; i < starts.length; i++) {
			differentIndex = i;
			if (starts[i] != ends[i]) {
				break;
			}
		}
		if (differentIndex < 2) {
			differentIndex = 0;
		}
		// 年份后两位 只取后两位
		else if (differentIndex < 4 && differentIndex > 1) {
			differentIndex = 2;
		} else if (differentIndex > 4 && differentIndex < 7) {
			differentIndex = 5;
		} else if (differentIndex > 7 && differentIndex < 10) {
			differentIndex = 8;
		} else if (differentIndex > 10) {
			differentIndex = 11;
		}
		String same = new String(starts, 0, differentIndex);
		if (differentIndex < starts.length) {
			StringBuilder builder = new StringBuilder(same);
			builder.append(starts, differentIndex, starts.length - differentIndex);
			builder.append("~");
			builder.append(ends, differentIndex, ends.length - differentIndex);
			same = builder.toString();
		}
		return same;
	}

	/* 时间戳转换成字符窜 */
	static SimpleDateFormat sf  = new SimpleDateFormat("yyyy年MM月dd日");

	public static String changeDateToString(long time) {
		return changeDateToString(time, null);
	}
	
	public static String changeTimeStampToString(long time) {
		return changeTimeStampToString(time,null);
	}
	
	public static String changeTimeStampToString(long time,SimpleDateFormat format) {
		return changeDateToString(time*1000,format);
	}
	
	public static String changeDateToString(long time,SimpleDateFormat format) {
		Date d = new Date(time);
		if(format == null) format = sf;
		return sf.format(d);
	}
}
