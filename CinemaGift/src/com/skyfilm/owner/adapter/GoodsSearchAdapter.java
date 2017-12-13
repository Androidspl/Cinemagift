package com.skyfilm.owner.adapter;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.Goods;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsSearchAdapter extends BaseAdapter{

	private Context mContext;
	private List<Goods> mList;
	
	public GoodsSearchAdapter(Context context,List<Goods> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.item_search_goods, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	class ViewHolder{
		ImageView goods_logo;
		TextView goods_name,goods_money,goods_type;
		ViewHolder(View v){
			goods_logo = (ImageView) v.findViewById(R.id.goods_logo);
			goods_name = (TextView) v.findViewById(R.id.goods_name);
			goods_money = (TextView) v.findViewById(R.id.goods_money);
			goods_type = (TextView) v.findViewById(R.id.goods_type);
		}
	}

}
