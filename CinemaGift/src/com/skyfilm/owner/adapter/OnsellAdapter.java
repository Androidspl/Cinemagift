package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Onsell;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OnsellAdapter extends BaseAdapter {

	private Context mContext;
	private List<Onsell> mList;
	
	public OnsellAdapter(Context context,List<Onsell> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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
			convertView = View.inflate(mContext, R.layout.item_onsell, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(mList != null && mList.size()>0){
			Onsell onsell = mList.get(position);
			holder.goods_name.setText(onsell.getAppointment_id());
			holder.loveNum.setText(onsell.getAppointment_name());
		}
		//TODO 设置值
		return convertView;
	}
	
	class ViewHolder{
		ImageView goods_logo;
		TextView goods_name;
		TextView loveNum,goods_money,sellNum,date;
		ViewHolder(View v){
			goods_logo = (ImageView) v.findViewById(R.id.goods_logo);
			goods_name = (TextView) v.findViewById(R.id.goods_name);
			loveNum = (TextView) v.findViewById(R.id.loveNum);
			sellNum = (TextView) v.findViewById(R.id.sellNum);
			date = (TextView) v.findViewById(R.id.date);
		}
	}

}
