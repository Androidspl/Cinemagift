package com.skyfilm.owner.mine;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CopyrightAdapter;
import com.skyfilm.owner.adapter.TypeAdapter;
import com.skyfilm.owner.base.BaseThreadActivity;
import com.skyfilm.owner.bean.mine.FilmTheme;
import com.skyfilm.owner.biz.AddressBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.SpUtils;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.MyFlowLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 发布作品选择界面
 * 
 * @author min.yuan
 *
 */
public class IssueChooseActivity extends BaseThreadActivity {
	private String tag = null;// 选择影片主题，选择作品类型，版权
	private CopyrightAdapter copyrightAdapter;
	private TypeAdapter typeAdapter;
//	private CheckBox inbetweening, model, handwork, others, China_painting, toy;
	private ListView lv_copyright;
	private ListView type;
	private MyFlowLayout mFlowLayout;
	private String mNames[] = { "大圣归来", "变形金刚", "大鱼海棠", "复仇者联盟", "麦兜", "忍者神龟" };
	private AddressBiz addressBiz;
	private static final int GET_TYPE = 0x116;
	private List<FilmTheme> filmTheme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addressBiz = (AddressBiz) CsqManger.getInstance().get(Type.ADDRESSBIZ);
		tag = getIntent().getStringExtra("tag");
		if ("choose_theme".equals(tag)) {// 选择影片主题
			setContentView(R.layout.activity_issue_theme);
		} else if ("choose_type".equals(tag)) {// 选择作品类型
			setContentView(R.layout.activity_issue_type);
			new CsqRunnable(GET_TYPE).start();
		} else if ("chooose_copyright".equals(tag)) {// 版权
			setContentView(R.layout.activity_issue_copyright);
		}
		initView();
	}

	private void initView() {
		if ("choose_theme".equals(tag)) {// 选择影片主题
			initChildViews();
			mFlowLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
		} else if ("choose_type".equals(tag)) {// 选择作品类型
			type = (ListView) findViewById(R.id.type);
			typeAdapter = new TypeAdapter(this, null);
			type.setAdapter(typeAdapter);
			type.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
				}
			});;
//			inbetweening = (CheckBox) findViewById(R.id.inbetweening);// 绘画
//			model = (CheckBox) findViewById(R.id.model);// 模型
//			handwork = (CheckBox) findViewById(R.id.handwork);// 工艺
//			China_painting = (CheckBox) findViewById(R.id.China_painting);// 同人
//			toy = (CheckBox) findViewById(R.id.toy);// 平面
//			others = (CheckBox) findViewById(R.id.others);// 其他
			initCheckBox();
		} else if ("chooose_copyright".equals(tag)) {// 2版权
			copyrightAdapter = new CopyrightAdapter(this, null);
			lv_copyright = (ListView) findViewById(R.id.lv_copyright);
			lv_copyright.setAdapter(copyrightAdapter);
			lv_copyright.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// 跳转版权说明
					Intent intent = new Intent(IssueChooseActivity.this, InstructionActivity.class);
					startActivity(intent);
				}
			});
		}

	}

	private void initCheckBox() {
		String data = SpUtils.getString(this, "type", null);
//		if (data != null) {
//			if (data.contains("绘画")) {
//				inbetweening.setChecked(true);
//			}
//			if (data.contains("模型")) {
//				model.setChecked(true);
//			}
//			if (data.contains("工艺")) {
//				handwork.setChecked(true);
//			}
//			if (data.contains("同人")) {
//				China_painting.setChecked(true);
//			}
//			if (data.contains("平面")) {
//				toy.setChecked(true);
//			}
//			if (data.contains("其他")) {
//				others.setChecked(true);
//			}
//		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ("choose_type".equals(tag)) {
				setTypeData();
			} else {
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onLeftViewClick(View v) {

		if ("choose_type".equals(tag)) {
			setTypeData();
			super.onLeftViewClick(v);
		} else {
			super.onLeftViewClick(v);
		}
	}

	private void setTypeData() {
//		String data = "";
//		if (inbetweening.isChecked()) {
//			data = "绘画，";
//		}
//		if (model.isChecked()) {
//			data = data + "模型，";
//		}
//		if (handwork.isChecked()) {
//			data = data + "工艺，";
//		}
//		if (China_painting.isChecked()) {
//			data = data + "同人，";
//		}
//		if (toy.isChecked()) {
//			data = data + "平面，";
//		}
//		if (others.isChecked()) {
//			data = data + "其他";
//		}
//		if (data.equals("") && data.length() < 1) {
//			data = "选择产品类型";
//		} else if (data.endsWith("，")) {
//			data = data.substring(0, data.length() - 1);
//		}

//		SpUtils.setString(IssueChooseActivity.this, "type", data);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		if ("choose_theme".equals(tag)) {
			title.setText("影片主题");
		} else if ("choose_type".equals(tag)) {
			title.setText("产品类型");
			left.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onLeftViewClick(v);
				}
			});
		} else if ("chooose_copyright".equals(tag)) {
			title.setText("版权设置");
		}
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (GET_TYPE == operate) {
			// return addressBiz.getFilmThemeList(User_id);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && GET_TYPE == operate) {
			filmTheme = (List<FilmTheme>) obj;
		}
		return false;
	}

	/**
	 * 加载流布局中的textview
	 */
	private void initChildViews() {
		// TODO Auto-generated method stub
		mFlowLayout = (MyFlowLayout) findViewById(R.id.flowlayout);
		MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 18;
		lp.rightMargin = 18;
		lp.topMargin = 18;
		lp.bottomMargin = 18;
		// for (int i = 0; i < filmTheme.size(); i++) {
		for (int i = 0; i < mNames.length; i++) {
			TextView view = (TextView) View.inflate(this, R.layout.flowlayout_textview, null);
			view.setText(mNames[i]);
			// view.setText(filmTheme.get(i));
			// view.setTextColor(Color.WHITE);
			// view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_style));
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String text = ((TextView) v).getText().toString();
					Log.d("IssueChooseActivity", text);
					SpUtils.setString(IssueChooseActivity.this, "theme", text);
					IssueChooseActivity.this.finish();
				}
			});
			mFlowLayout.addView(view, lp);
		}
	}
}
