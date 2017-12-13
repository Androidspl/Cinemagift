package com.skyfilm.owner.fragment;

import java.util.List;

import com.skyfilm.owner.adapter.GoodsSearchAdapter;
import com.skyfilm.owner.base.BaseListFragment;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.biz.GoodsBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InformationSearch extends BaseListFragment<Goods> {
	private Context context;
	private List<Goods> goods;
	private GoodsBiz goodsBiz;

	public InformationSearch(List<Goods> goods, Context context) {
		this.context = context;
		this.goods = goods;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		goodsBiz = (GoodsBiz) CsqManger.getInstance().get(Type.GOODSBIZ);
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
		setCurrentPage(2);
		listView.setDivider(null);
		return goods;
	}

	@Override
	protected BaseAdapter getListAdapter(List<Goods> data) {
		GoodsSearchAdapter goodsSearchAdapter = new GoodsSearchAdapter(context, null);
		return goodsSearchAdapter;
	}

}
