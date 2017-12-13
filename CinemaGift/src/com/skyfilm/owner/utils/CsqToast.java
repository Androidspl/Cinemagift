package com.skyfilm.owner.utils;

import com.skyfilm.owner.R;
import com.skyfilm.owner.exception.CsqException;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CsqToast {

	public static void showOnSubThread(final String text, final Context context) {
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				try {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
				}
			}
		});
	}

	public static void show(final String text, final Context context) {
		try {
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	public static void customer(final String title,final String content,int time,final Context context){
		Toast toast =null;
		LayoutInflater inflater =   LayoutInflater.from(context);
		View layout=inflater.inflate(R.layout.self_dialog_layout, null);
		TextView tit = (TextView) layout.findViewById(R.id.title);  
		tit.setText(title);  
		TextView cont = (TextView) layout.findViewById(R.id.dialog_text_content);  
		cont.setText(content);  
		toast = new Toast(context);  
		toast.setGravity(Gravity.CENTER, 12, 40);  
		toast.setDuration(time);  
		toast.setView(layout);  
		toast.show();  
	}


	public static void showException(final int exCode, final Context context) {
		try {
			Toast.makeText(context, CsqException.getCsqMessage(exCode), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	public static void showException(final Exception exception, final Context context) {
		try {
			Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	public static void show(final int resId, final Context context) {
		try {
			Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
		} catch (NotFoundException e) {
		}
	}

	public static void showLong(final String text, final Context context) {
		try {
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
		}
	}

	public static void showLong(final int resId, final Context context) {
		try {
			Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
		} catch (NotFoundException e) {
		}
	}
}