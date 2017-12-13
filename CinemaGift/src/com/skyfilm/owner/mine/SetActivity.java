package com.skyfilm.owner.mine;

import java.io.File;
import java.util.List;

import com.skyfilm.owner.MainActivity;
import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.LoginActivity;
import com.skyfilm.owner.base.BaseActivity;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.DataCleanManager;
import com.skyfilm.owner.utils.MyDialogUtils;
import com.skyfilm.owner.widget.MyDialog;
import com.skyfilm.owner.widget.MyProgress;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 设置
 * 
 * @author min.yuan
 *
 */
public class SetActivity extends BaseThreadActivity implements OnClickListener {
	private TextView backinfo, about, clean, exit;
	private Dialog dialog;
	private static final int CHECKCACHE = 0X0120;
	private static final int CLEANCACHE = 0X0121;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		initView();
		initData();
	}

	private void initView() {
		backinfo = (TextView) findViewById(R.id.backinfo);
		about = (TextView) findViewById(R.id.about);
		clean = (TextView) findViewById(R.id.clean);
		exit = (TextView) findViewById(R.id.exit);
	}

	private void initData() {
		new CsqRunnable(CHECKCACHE).start();
		MyProgress.show("加载缓存",this);
		backinfo.setOnClickListener(this);
		about.setOnClickListener(this);
		clean.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	private String calculateCache() {
		// getCacheDir()+ "/webviewCacheChromium"
		String webviewSize = "0K";
		File directory;
		directory = new File(getCacheDir()+"");
		try {
			webviewSize = DataCleanManager.getTotalCacheSize(this, directory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webviewSize;
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("设置");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.backinfo:
			intent.setClass(SetActivity.this, BackInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.about:
			intent.setClass(SetActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.clean:
			showDialog();
			break;
		case R.id.exit:
			MyDialog.show(this, "提示", "退出将会回到登录界面，确认退出么？", "确定", "取消", new OnClickListener() {
				@Override
				public void onClick(View v) {
					//finish();
//					MainApplication.getInstance().exit();
					try {
						userBiz.logout(userBiz.getCurrentUserID());
					} catch (CsqException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(SetActivity.this,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}, null);
			break;
		default:
			break;
		}
	}

	private void showDialog() {
		View view = View.inflate(this, R.layout.choose_picture, null);
		TextView des = (TextView) view.findViewById(R.id.takePhoto);
		TextView sure = (TextView) view.findViewById(R.id.pickPhoto);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		des.setText("清理后将删除所有离线的内容，是否清除？");
		sure.setText("确定");
		cancel.setText("取消");
		OnClickListener cancelClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		};
		OnClickListener sureClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 清理缓存
				new CsqRunnable(CLEANCACHE).start();
				MyProgress.show("清理中", SetActivity.this);
			}
		};
		cancel.setOnClickListener(cancelClick);
		sure.setOnClickListener(sureClick);
		des.setTextColor(getResources().getColor(R.color.hint));
		dialog = MyDialogUtils.showButtomDialog(this, view);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if(CHECKCACHE == operate){
			return calculateCache();
		}else if(CLEANCACHE == operate){
//			DataCleanManager.cleanWebviewCache(SetActivity.this);
//			DataCleanManager.cleanWebviewDatabases(SetActivity.this);
			DataCleanManager.cleanInternalCache(SetActivity.this);
			return calculateCache();
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(CLEANCACHE == operate && result){
			dialog.dismiss();
			CsqToast.show("清理缓存完成", SetActivity.this);
//			clean.setText((String)obj);
			clean.setText("0K");
		}else if(CHECKCACHE == operate && result){
			clean.setText((String)obj);
			
		}
		return false;
	}

}
