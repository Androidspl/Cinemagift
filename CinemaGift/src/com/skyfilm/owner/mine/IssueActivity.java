package com.skyfilm.owner.mine;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CustomerAdapter;
import com.skyfilm.owner.adapter.SelectedPicAdapter;
import com.skyfilm.owner.adapter.SelectedPicAdapter.ONItemDeleteListener;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.PublishWork;
import com.skyfilm.owner.biz.AddressBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.SpUtils;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.MyDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 发布作品
 * 
 * @author min.yuan
 *
 */
public class IssueActivity extends BaseThreadActivity implements OnClickListener, ONItemDeleteListener {
	private EditText opus_title, opus_des;
	private GridView opus_pic;
	private RelativeLayout choose_theme, choose_type, chooose_copyright;
	private TextView theme, type, copyright;
	private ArrayList<String> photo;
	private SelectedPicAdapter adapter;
	private int maxCount = 6;
	private AddressBiz addressBiz;
	private PublishWork publishWork;
	private static final int ISSUE = 0x0105;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_issue);
		initView();
		initData();
	}

	private void initView() {
		opus_title = (EditText) findViewById(R.id.opus_title);
		opus_des = (EditText) findViewById(R.id.opus_des);

		theme = (TextView) findViewById(R.id.theme);
		type = (TextView) findViewById(R.id.type);
		copyright = (TextView) findViewById(R.id.copyright);

		opus_pic = (GridView) findViewById(R.id.opus_pic);

		choose_theme = (RelativeLayout) findViewById(R.id.choose_theme);
		choose_type = (RelativeLayout) findViewById(R.id.choose_type);
		chooose_copyright = (RelativeLayout) findViewById(R.id.chooose_copyright);

	}
	
	private boolean check() {
		if (TextUtils.isEmpty(opus_title.getText().toString().trim())) {
			CsqToast.show("请输入作品标题", this);
			return false;
		} else if (TextUtils.isEmpty(opus_des.getText().toString().trim())) {
			CsqToast.show("请输入作品描述", this);
			return false;
		} else if (theme.getText().toString().trim().equals("选择影片主题")) {
			CsqToast.show("请选择影片主题", this);
			return false;
		} else if (type.getText().toString().trim().equals("选择产品类型")) {
			CsqToast.show("请选择产品类型", this);
			return false;
		} else if (copyright.getText().toString().trim().equals("版权")) {
			CsqToast.show("请选择版权", this);
			return false;
		}
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		theme.setText(SpUtils.getString(this, "theme", "选择影片主题"));
		type.setText(SpUtils.getString(this, "type", "选择产品类型"));
		copyright.setText(SpUtils.getString(this, "copyright", "版权"));
	}

	private void initData() {
		addressBiz = (AddressBiz) CsqManger.getInstance().get(Type.ADDRESSBIZ);
		choose_theme.setOnClickListener(this);
		choose_type.setOnClickListener(this);
		chooose_copyright.setOnClickListener(this);
		photo = new ArrayList<>();
		photo.add(SelectedPicAdapter.BAOSHI_IMAGE);
		adapter = new SelectedPicAdapter(this, photo, this, maxCount);
		opus_pic.setAdapter(adapter);
		// 图片描述
		opus_pic.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View arg1, int position, long arg3) {
				if (SelectedPicAdapter.BAOSHI_IMAGE.equals(photo.get(photo.size() - 1)))
					photo.remove(photo.size() - 1);
				Intent intent = new Intent();
				intent.setClass(IssueActivity.this, SelectPicActivity.class);
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("dataList", photo);
				intent.putExtras(bundle);
				intent.putExtra("SEL_CAPACITY", maxCount);
				IssueActivity.this.startActivityForResult(intent, SelectPicActivity.REQUEST_ALBUM);
				// }
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			int type = intent.getIntExtra("type", SelectPicActivity.REQUEST_IMAGE);
			if (type == SelectPicActivity.REQUEST_ALBUM) {
				Bundle bundle = intent.getExtras();
				photo = (ArrayList<String>) bundle.getSerializable("dataList");
				if (photo.size() < maxCount) {
					photo.add(SelectedPicAdapter.BAOSHI_IMAGE);
				}
				adapter = new SelectedPicAdapter(this, photo, this, maxCount);
				opus_pic.setAdapter(adapter);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				}, 1500);
				// adapter.notifyDataSetChanged();
			} else {
				if (photo.size() < maxCount) {
					photo.add(SelectPicActivity.mPhotoPath);
					photo.add(SelectedPicAdapter.BAOSHI_IMAGE);
				} else {
					photo.set(photo.size() - 1, SelectPicActivity.mPhotoPath);
				}
				adapter = new SelectedPicAdapter(this, photo, this, maxCount);
				opus_pic.setAdapter(adapter);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				}, 1500);
				// adapter.notifyDataSetChanged();
			}
		} else {
			photo.add(SelectedPicAdapter.BAOSHI_IMAGE);
		}
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("上传作品");
		right1.setVisibility(View.GONE);
		right2.setText("发布");
		right2.setTextColor(getResources().getColor(R.color.mine_text_bc));
		right2.setTextSize(12);;
	}

	@Override
	protected void onRightViewClick2(View v) {
		super.onRightViewClick2(v);
		// TODO 发布
		if (check()) {
			publishWork = new PublishWork();
			publishWork.setCopyright(copyright.getText().toString().trim());
			publishWork.setOpus_des(opus_des.getText().toString().trim());
			publishWork.setOpus_title(opus_title.getText().toString().trim());
			publishWork.setTheme(theme.getText().toString().trim());
			publishWork.setType(type.getText().toString().trim());
			if(photo == null || photo.size()<2){
				CsqToast.show("请添加照片", this);
			}else{
				ArrayList<String> items = new ArrayList<>();
				items = new ArrayList<>(photo);
				if (photo.contains(SelectedPicAdapter.BAOSHI_IMAGE)) {
					items.remove(SelectedPicAdapter.BAOSHI_IMAGE);
				}
				publishWork.setPhoto(items);
				CsqToast.show("发布", this);
				new CsqRunnable(ISSUE, publishWork).start();
			}
		}
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if(operate == ISSUE){
//			addressBiz.publishWork(User_id, (PublishWork)obj[0]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(operate == ISSUE && result){
			CsqToast.show("发布成功", this);
		}else{
			CsqToast.show("发布失败", this);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(IssueActivity.this, IssueChooseActivity.class);
		switch (v.getId()) {
		case R.id.choose_theme:
			intent.putExtra("tag", "choose_theme");
			startActivity(intent);
			break;
		case R.id.choose_type:
			intent.putExtra("tag", "choose_type");
			startActivity(intent);
			break;
		case R.id.chooose_copyright:
			intent.putExtra("tag", "chooose_copyright");
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void onDelete(int index) {
		try {
			photo.remove(index);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		String[] keys = {"theme","type","copyright"};
		SpUtils.remove(this, keys);
	}
}
