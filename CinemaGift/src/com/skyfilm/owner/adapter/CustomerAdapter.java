package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Information> mList;
	
	public CustomerAdapter(Context context,ArrayList<Information> list) {
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
			convertView = View.inflate(mContext, R.layout.item_customer, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.des.setText("标准问答"+position);
		//TODO 设置值
		return convertView;
	}
	
	class ViewHolder{
		TextView des;
		ViewHolder(View v){
			des = (TextView) v.findViewById(R.id.des);
		}
	}

}
