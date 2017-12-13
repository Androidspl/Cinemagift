package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.StoreEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreListAdapter extends BaseAdapter {

	private Context context;
	private List<Goods> storelistEntities;
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.build();
	ImageLoader imageLoader = ImageLoader.getInstance();

	public StoreListAdapter(Context context) {
		this.context = context;
	}

	public StoreListAdapter(Context context, List<Goods> storelistEntities) {
		this.context = context;
		this.storelistEntities = storelistEntities;
	}

	@Override
	public int getCount() {
		return storelistEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return storelistEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_store_list, null);
		}
		StoreListAdapterHolder holder = StoreListAdapterHolder.getHolder(convertView);
		Goods goods = storelistEntities.get(position);
		imageLoader.displayImage(Const.URL_T_CJ_ + goods.getPic_pass(), holder.goods_pic, options);
		holder.goods_name.setText(goods.getGoods_name());
		holder.designer.setText(goods.getDesigner_id());
		holder.goods_price.setText("￥"+goods.getGoods_price());
		return convertView;
	}

	static class StoreListAdapterHolder {

		TextView goods_name, designer, goods_price;
		ImageView goods_pic;

		public StoreListAdapterHolder(View convertView) {
			goods_pic = (ImageView) convertView.findViewById(R.id.goods_pic);
			goods_name = (TextView) convertView.findViewById(R.id.goods_name);
			designer = (TextView) convertView.findViewById(R.id.designer);
			goods_price = (TextView) convertView.findViewById(R.id.goods_price);
		}

		public static StoreListAdapterHolder getHolder(View convertView) {
			StoreListAdapterHolder holder = (StoreListAdapterHolder) convertView.getTag();
			if (holder == null) {
				holder = new StoreListAdapterHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}

}
