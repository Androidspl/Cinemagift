package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Notice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<Notice> mList;
	
	public NoticeListViewAdapter(Context context,List<Notice> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 30;
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
			convertView = View.inflate(mContext, R.layout.item_notice, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//TODO 设置值
		return convertView;
	}
	
	class ViewHolder{
		ImageView img;
		TextView content;
		TextView date;
		ViewHolder(View v){
			img = (ImageView) v.findViewById(R.id.img);
			content = (TextView) v.findViewById(R.id.content);
			date = (TextView) v.findViewById(R.id.date);
		}
	}

}
