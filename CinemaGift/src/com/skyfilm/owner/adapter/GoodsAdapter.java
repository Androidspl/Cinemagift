package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CustomerAdapter.ViewHolder;
import com.skyfilm.owner.bean.mine.Information;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<Information> mList;
	
	public GoodsAdapter(Context context,ArrayList<Information> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.item_goods, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//TODO 设置值
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
