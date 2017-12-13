package com.skyfilm.owner.adapter;

import com.skyfilm.owner.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class GoodsStyleAdapter extends BaseAdapter{

	private Context context;
	//private List<WzEntity> wzEntities;
	private String[] strings;;
	private LayoutParams layoutParams;
	
	public String[] getStrings() {
		return strings;
	}

	public void setStrings(String[] strings) {
		this.strings = strings;
	}

	public GoodsStyleAdapter(Context context) {
		this.context = context;
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		layoutParams.rightMargin = 10;
		layoutParams.leftMargin = 10;
	}

	/*public ConsultFilterAdapter(List<WzEntity> wzEntities,Context context) {
		this.context = context;
		this.wzEntities = wzEntities;
	}*/

	@Override
	public int getCount() {
		//return wzEntities.size();
		//return strings.length;
		return 3;
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
		//holder.tv_goods.setText(strings[position]);
		//holder.ll_lv_adapter.setLayoutParams(layoutParams);
		return convertView;
	}

	static class GoodsClassifyHolder {
		
		TextView tv_goods;
		private LinearLayout ll_lv_adapter;
		
		public GoodsClassifyHolder(View convertView) {
			tv_goods = (TextView) convertView.findViewById(R.id.tv_goods);
			ll_lv_adapter = (LinearLayout) convertView.findViewById(R.id.ll_lv_adapter);
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
