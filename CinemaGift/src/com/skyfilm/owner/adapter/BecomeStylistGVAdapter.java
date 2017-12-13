package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.MyLoveAdapter.ViewHolder;
import com.skyfilm.owner.bean.mine.Information;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BecomeStylistGVAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> mList;
	
	public BecomeStylistGVAdapter(Context context,ArrayList<String> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
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
		ViewHolder holder = null;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.gv_becomestylist, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
//		if (mList != null && mList.size() > 0) {
//			loader.displayImage(mList.get(position), holder.pic, options);
//		}
		holder.pic.setImageResource(R.drawable.add);
		return convertView;
	}

	public class ViewHolder{
		ImageView pic;
		public ViewHolder(View v){
			pic = (ImageView) v.findViewById(R.id.pic);
		}
	}
}
