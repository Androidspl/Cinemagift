package com.skyfilm.owner.adapter;

import java.util.List;

import com.skyfilm.owner.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsClassifyAdapter extends BaseAdapter{

	private Context context;
	//private List<WzEntity> wzEntities;
	private int select=0;//默认是第一个选中
	private int type=-1;
	private String[] strings;;

	
	
	public void setType(int type) {
		this.type = type;
	}

	public String[] getStrings() {
		return strings;
	}

	public void setStrings(String[] strings) {
		this.strings = strings;
	}

	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}
	
	public GoodsClassifyAdapter(Context context) {
		this.context = context;
	}

	/*public ConsultFilterAdapter(List<WzEntity> wzEntities,Context context) {
		this.context = context;
		this.wzEntities = wzEntities;
	}*/

	@Override
	public int getCount() {
		//return wzEntities.size();
		return strings.length;
	}

	@Override
	public Object getItem(int position) {
		//return wzEntities.get(position);
		return strings[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_goods, null);

		}
		GoodsClassifyHolder holder = GoodsClassifyHolder.getHolder(convertView);
		holder.tv_goods.setText(strings[position]);
		return convertView;
	}

	static class GoodsClassifyHolder {
		
		TextView tv_goods;
		public GoodsClassifyHolder(View convertView) {
			tv_goods = (TextView) convertView.findViewById(R.id.tv_goods);
		}

		public static GoodsClassifyHolder getHolder(View convertView) {
			GoodsClassifyHolder holder = (GoodsClassifyHolder) convertView.getTag();
			if (holder == null) {
				holder = new GoodsClassifyHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}
	

}
