package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.CrowdFundingEntity;
import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.StoreEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class GoodsInfoAdapter extends BaseAdapter {

	private Context context;
	private List<Goods> goodsEntities;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;

	public GoodsInfoAdapter(Context context, List<Goods> goodsEntities) {
		this.context = context;
		this.goodsEntities = goodsEntities;
		this.loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return goodsEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return goodsEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_goods_info, null);
		}
		GoodsInfoAdapterHolder holder = GoodsInfoAdapterHolder.getHolder(convertView);
		Goods goodsEntity = goodsEntities.get(position);
		
//		loader.displayImage(Const.URL_T_CJ_ + crowdFundingEntity.getPic_pass(), holder.crowd_img, options);
		return convertView;
	}

	static class GoodsInfoAdapterHolder {

		TextView crowd_name,_residual_time;
		private ImageView crowd_img;

		public GoodsInfoAdapterHolder(View convertView) {
			// 众筹天数
//			crowd_residual_time = (TextView) convertView.findViewById(R.id.crowd_residual_time);
		}

		public static GoodsInfoAdapterHolder getHolder(View convertView) {
			GoodsInfoAdapterHolder holder = (GoodsInfoAdapterHolder) convertView.getTag();
			if (holder == null) {
				holder = new GoodsInfoAdapterHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}

}
