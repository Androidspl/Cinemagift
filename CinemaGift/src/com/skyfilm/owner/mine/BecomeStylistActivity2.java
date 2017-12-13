package com.skyfilm.owner.mine;

import java.util.ArrayList;

import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.SelectedPicAdapter;
import com.skyfilm.owner.adapter.SelectedPicAdapter.ONItemDeleteListener;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.BecomeStylist;
import com.skyfilm.owner.biz.BecomeStylistBiz;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.webView.WebViewActivity;
import com.skyfilm.owner.widget.MyDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 成为设计师2
 * 
 * @author min.yuan
 *
 */
public class BecomeStylistActivity2 extends BaseThreadActivity implements ONItemDeleteListener, TextWatcher {
	private GridView myproduction;
	private EditText production_des;
	private CheckBox agreement;
	private Button commit;
	private SelectedPicAdapter adapter;
	private ArrayList<String> photo;
	private int maxCount = 9;
	private BecomeStylist data;
	private BecomeStylistBiz becomeStylistBiz;
	private UserBiz userBiz;
	private static final int BECOMESTYLIST = 0x0106;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_becomestylist2);
		initView();
		initData();
	}

	private void initView() {
		myproduction = (GridView) findViewById(R.id.myproduction);
		production_des = (EditText) findViewById(R.id.production_des);
		agreement = (CheckBox) findViewById(R.id.agreement);
		commit = (Button) findViewById(R.id.commit);
	}

	private void initData() {
		data = (BecomeStylist) getIntent().getSerializableExtra("data");
		production_des.addTextChangedListener(this);
		becomeStylistBiz = (BecomeStylistBiz) CsqManger.getInstance().get(Type.BECOMESTYLISTBIZ);
		userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
		photo = new ArrayList<>();
		photo.add(SelectedPicAdapter.BAOSHI_IMAGE);
		adapter = new SelectedPicAdapter(this, photo, this, maxCount);
		myproduction.setAdapter(adapter);
		// 图片描述
		myproduction.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View arg1, int position, long arg3) {
				if (SelectedPicAdapter.BAOSHI_IMAGE.equals(photo.get(photo.size() - 1)))
					photo.remove(photo.size() - 1);
				Intent intent = new Intent();
				intent.setClass(BecomeStylistActivity2.this, SelectPicActivity.class);
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("dataList", photo);
				intent.putExtras(bundle);
				intent.putExtra("SEL_CAPACITY", maxCount);
				BecomeStylistActivity2.this.startActivityForResult(intent, SelectPicActivity.REQUEST_ALBUM);
				// }
			}
		});
		agreement.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setCommitStatus();
			}
		});
		commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				data.setDesign_des(production_des.getText().toString().trim());
				if (photo == null || photo.size() < 2) {
					CsqToast.show("请添加作品", BecomeStylistActivity2.this);
				} else {
					ArrayList<String> items = new ArrayList<>();
					items = new ArrayList<>(photo);
					if (photo.contains(SelectedPicAdapter.BAOSHI_IMAGE)) {
						items.remove(SelectedPicAdapter.BAOSHI_IMAGE);
					}
					data.setFiles(items);
					new CsqRunnable(BECOMESTYLIST, data).start();
					CsqToast.show("提交", BecomeStylistActivity2.this);
				}
			}
		});

	}

	private void setCommitStatus() {
		final boolean pic_status = photo.size() > 1;
		final String des = production_des.getText().toString().trim();
		if (agreement.isChecked()) {
			if (!StringUtil.isNull(des) && pic_status) {
				commit.setBackgroundResource(R.drawable.next_bg_s);
				commit.setEnabled(true);
			} else {
				commit.setBackgroundResource(R.drawable.next_bg_n);
				commit.setEnabled(false);
			}
		} else {
			commit.setBackgroundResource(R.drawable.next_bg_n);
			commit.setEnabled(false);
		}
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
				myproduction.setAdapter(adapter);
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
				setCommitStatus();
				myproduction.setAdapter(adapter);
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
		setCommitStatus();
	}

	protected void onRightViewClick3(View v) {
		super.onRightViewClick2(v);
		Intent intent = new Intent(BecomeStylistActivity2.this, WebViewActivity.class);
		String url = "https://www.baidu.com/";
		intent.putExtra("url", url);
		startActivity(intent);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2,TextView right3) {
		super.initTiltle(left, title, right1, right2);
		title.setText("成为设计师");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.GONE);
		right3.setVisibility(View.VISIBLE);
		right3.setText("@帮助手册");
	}


	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (operate == BECOMESTYLIST) {
			String user_id = userBiz.getCurrentUser().getUser_id();
			 becomeStylistBiz.applyDesigner(user_id, (BecomeStylist)objs[0]);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if(result && operate == BECOMESTYLIST){
			MyDialog.show(this, "提醒", "成为设计师申请成功", "确定", "取消", new OnClickListener() {
				@Override
				public void onClick(View v) {
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
	public void onDelete(int index) {
		try {
			photo.remove(index);
			setCommitStatus();
		} catch (Exception e) {
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setCommitStatus();
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

}
