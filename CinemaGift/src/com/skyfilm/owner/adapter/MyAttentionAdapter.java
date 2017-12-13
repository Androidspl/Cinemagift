package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.MyAttention;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionAdapter extends BaseAdapter {

	private Context mContext;
	private List<MyAttention> mList;
	private OnClickListener mClick;
	
	public MyAttentionAdapter(Context context,List<MyAttention> list,OnClickListener click) {
		this.mContext = context;
		this.mList = list;
		this.mClick = click;
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
			convertView = View.inflate(mContext, R.layout.item_myattention, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
//		if(mList != null && mList.size()>0){
//			MyAttention myAttention = mList.get(position);
			holder.stylist_label.setAdapter(new MyAttentionGVAdapter(mContext, null));
//			holder.cancel_attention.setTag(myAttention);
			holder.cancel_attention.setOnClickListener(mClick);
//		}
		//TODO 设置值
		return convertView;
	}
	
	class ViewHolder{
		ImageView stylist_icon;
		TextView stylist_name,cancel_attention,fans;
		GridView stylist_label;
		ViewHolder(View v){
			stylist_icon = (ImageView) v.findViewById(R.id.stylist_icon);
			stylist_label = (GridView) v.findViewById(R.id.stylist_label);
			stylist_name = (TextView) v.findViewById(R.id.stylist_name);
			cancel_attention = (TextView) v.findViewById(R.id.cancel_attention);
			fans = (TextView) v.findViewById(R.id.fans);
		}
	}

}
