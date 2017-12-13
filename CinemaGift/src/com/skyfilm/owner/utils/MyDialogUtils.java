package com.skyfilm.owner.utils;

import com.skyfilm.owner.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyDialogUtils {
	public static Dialog showButtomDialog(Context c,View v){
 		Window win;
 		Dialog dialog = new Dialog(c, R.style.MyButtomDialog);
 		dialog.setContentView(v);
 		dialog.show();
 		win = dialog.getWindow();
 		win.setWindowAnimations(R.style.dialogButtomAnim);
 		win.setGravity(Gravity.BOTTOM);
 		win.getDecorView().setPadding(0, 0, 0, 0);
 		WindowManager.LayoutParams lp = win.getAttributes();
 		lp.width = WindowManager.LayoutParams.FILL_PARENT;
 		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
 		win.setAttributes(lp);
 		return dialog;
 	}
}
