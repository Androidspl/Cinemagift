package com.skyfilm.owner.mine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.MyDialogUtils;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.MyDialog;
import com.skyfilm.owner.widget.MyProgress;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 我的资料设置
 * @author min.yuan
 *
 */
public class SetMyDataActivity extends BaseThreadActivity implements OnClickListener {
	private RelativeLayout choose_headicon;
	private ImageView head_icon;
	private EditText edit_nick;

	static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final int MAX_PHOTO_COUNT = 3;
	private static final int RESULT = 0x0100;
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.build();
	ImageLoader imageLoader = ImageLoader.getInstance();
	// 创建一个以当前时间为名称的文件
	File tempFile;
	private Dialog dialog;
	private GridView getPhoto;
	private UserInfoBiz uiBiz;
	private static final int EDITPROFILE = 001;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setmydata);
		initView();
		initData();
	}

	private void initView() {
		choose_headicon = (RelativeLayout) findViewById(R.id.choose_headicon);
		head_icon = (ImageView) findViewById(R.id.head_icon);
		edit_nick = (EditText) findViewById(R.id.edit_nick);
	}

	private void initData() {
		uiBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
		choose_headicon.setOnClickListener(this);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("编辑个人信息");
		right1.setVisibility(View.GONE);
		right2.setText("完成");
		
	}
	@Override
	protected void onRightViewClick2(View v) {
		super.onRightViewClick2(v);
		//TODO 提交数据
		String name;
		String path = "";
		if(!TextUtils.isEmpty(edit_nick.getText().toString().trim())){
			name = edit_nick.getText().toString().trim();
		}else{
			name = "请编辑您的昵称";
		}
		CsqToast.show("提交数据", SetMyDataActivity.this);
		if(null != tempFile){
			path = tempFile.getPath();
		}
		MyProgress.show("提交中", this);
		new CsqRunnable(EDITPROFILE, name,path).start();
		
	}
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if(operate == EDITPROFILE){
			return uiBiz.editProfile("", (String)objs[0], (String)objs[1]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(operate == EDITPROFILE && result){
			MyDialog.show(this, "提醒", "资料保存成功", "确定", "取消", new OnClickListener() {
				@Override
				public void onClick(View v) {
//					SetMyDataActivity.this.finish();
				}
			}, new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Dialog dialog = null;
		switch (v.getId()) {
		case R.id.choose_headicon:// 选择照片
			showDialog();
			break;
		default:
			break;
		}
	}

	// 提示对话框方法
	@SuppressWarnings("static-access")
	private void showDialog() {
		View view = View.inflate(this, R.layout.choose_picture, null);
		OnClickListener takePhoto = new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				tempFile = new File(getAppDataRoot() + "image/", getPhotoFileName());
				if (tempFile != null) {
					if (!tempFile.getParentFile().exists()) {
						tempFile.getParentFile().mkdirs();
					}
				}
				// 指定调用相机拍照后照片的储存路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		};
		OnClickListener pickPhoto = new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
			}
		};
		OnClickListener cancel = new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		};
		view.findViewById(R.id.takePhoto).setOnClickListener(takePhoto);
		view.findViewById(R.id.pickPhoto).setOnClickListener(pickPhoto);
		view.findViewById(R.id.cancel).setOnClickListener(cancel);
		dialog = MyDialogUtils.showButtomDialog(this, view);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (PHOTO_REQUEST_TAKEPHOTO == requestCode || PHOTO_REQUEST_GALLERY == requestCode
				|| PHOTO_REQUEST_CUT == requestCode)
			if (resultCode == Activity.RESULT_OK) {
				switch (requestCode) {
				case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
					startPhotoZoom(Uri.fromFile(tempFile), 150);
					break;

				case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
					// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
					if (data != null)
						startPhotoZoom(data.getData(), 150);
					break;

				case PHOTO_REQUEST_CUT:// 返回的结果
					if (data != null)
						setPicToView(data);
					break;
				}
			} else {
				if (tempFile != null)
					tempFile.delete();
			}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private static String appDataRoot = null;

	public String getAppDataRoot() {
		if (appDataRoot == null) {
			CreateDataFolders();
		}
		return appDataRoot;
	}

	private void CreateDataFolders() {
		File files = new File(Environment.getExternalStorageDirectory() + File.separator + "skyfilm");
		if (!files.exists()) {
			files.mkdirs();
		}

		if (!(files.canWrite() && files.canRead())) {
			files = this.getFilesDir();
			if (!(files.canWrite() && files.canRead())) {
				files = getExternalCacheDir();
			}
		}
		appDataRoot = files.getAbsolutePath() + File.separator;

	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	@SuppressWarnings("deprecation")
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			head_icon.setBackground(drawable);
			if (tempFile != null)
				tempFile.delete();
			tempFile = new File(getAppDataRoot() + "/image/", getPhotoFileName());
			if (tempFile != null) {
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
			}
			try {
				photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(tempFile));
//				user.setHead_pic(tempFile.getPath());
//				changes.put("head_icon",  tempFile.getPath());
			} catch (FileNotFoundException e) {
			}
		}

	}
}
