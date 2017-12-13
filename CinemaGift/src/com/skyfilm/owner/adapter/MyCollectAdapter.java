package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.MyCollect;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyCollectAdapter extends BaseAdapter {

	private Context mContext;
	private List<MyCollect> mList;
	
	public MyCollectAdapter(Context context,List<MyCollect> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
			convertView = View.inflate(mContext, R.layout.item_mycollect, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//TODO 设置值
		return convertView;
	}
	
	class ViewHolder{
		ImageView goods_icon;
		TextView goods_name,goods_des,stylist_name,brand_name,collect_count,progress,collect_residual_time;
		ProgressBar progressbar;
		ViewHolder(View v){
			goods_icon = (ImageView) v.findViewById(R.id.goods_icon);
			goods_name = (TextView) v.findViewById(R.id.goods_name);
			goods_des = (TextView) v.findViewById(R.id.goods_des);
			stylist_name = (TextView) v.findViewById(R.id.stylist_name);
			brand_name = (TextView) v.findViewById(R.id.brand_name);
			progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
			collect_count = (TextView) v.findViewById(R.id.collect_count);
			progress = (TextView) v.findViewById(R.id.progress);
			collect_residual_time = (TextView) v.findViewById(R.id.collect_residual_time);
		}
	}

}
