package com.skyfilm.owner.activity;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.StoreListAdapter;
import com.skyfilm.owner.base.BaseListActivity;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.biz.StoreListBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.widget.MyProgress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreListActivity extends BaseListActivity<Goods> implements OnClickListener{

	private GridView gridView;
	private StoreListBiz storelistBiz;
	private String tag = "";
	private String type = "";
	private List<Goods> storelistEntity;
	private StoreListAdapter storelistAdapter;
	private static final int SHOP_LIST = 0x11;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_list);
		
		Intent intent = getIntent();
		if (intent.hasExtra("Tag")) {
			tag = intent.getStringExtra("Tag");
		}else if (intent.hasExtra("Type")) {
			type = intent.getStringExtra("Type");
		}
		gridView = (GridView) findViewById(R.id.gridView);
		initData();
	}
	
	private void initData() {
		storelistBiz = (StoreListBiz) CsqManger.getInstance().get(Type.STORELISTBIZ);
		new CsqRunnable(SHOP_LIST).start(); 
	}
	
	@Override
	protected void initView() {
		MyProgress.show("", this);
	}

	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("上个界面传过来的标题");
	}

	@Override
	public void onClick(View v) {
		
	}

	
	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		switch (operate) {
		case SHOP_LIST:
			storelistEntity = storelistBiz.getStoreEntity(type, tag);
			break;
		}
		return null;
	}
	
	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result) {
			switch (operate) {
			case SHOP_LIST:
				/**设置商品类表listview的adapter*/
				storelistAdapter = new StoreListAdapter(getApplicationContext(), storelistEntity);
				gridView.setAdapter(storelistAdapter);
				break;
			}
		}
		return false;
	}

	@Override
	protected void onListItemClick(Goods item) {
		
	}

	@Override
	protected int getPage() {
		return 0;
	}

	@Override
	protected List<Goods> getDataListFromCache(String userId, String cid) {
		return null;
	}

	@Override
	protected List<Goods> getDataList(String userId, String cid, int page, int pageSize) throws CsqException {
		return null;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Goods> data) {
		return null;
	}
}
