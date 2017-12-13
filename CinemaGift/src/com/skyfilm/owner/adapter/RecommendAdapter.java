package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.pool.PoolStats;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Recommend.MyGoods;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendAdapter extends BaseAdapter {

	private Context mContext;
	private List<Recommend> mList;
	private OnClickListener mClick;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;

	public RecommendAdapter(Context context, List<Recommend> list, OnClickListener click) {
		this.mContext = context;
		this.mList = list;
		this.mClick = click;
		this.loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		if (mList != null && mList.size() > 0) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_recommend, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == 0) {
			holder.line_grey.setVisibility(View.GONE);
		} else {
			holder.line_grey.setVisibility(View.VISIBLE);
		}
		// TODO 设置值
		// holder.attention.setTag(tag);
		// holder.attention.setOnClickListener(mClick);

		if (mList != null && mList.size() > 0) {
			Recommend recommend = mList.get(position);
			if (recommend != null) {
				holder.name.setText(recommend.getDesigner_name());
				holder.goods.setText("商品 " + recommend.getGoods_sum());
				holder.fans.setText("粉丝 " + recommend.getFocus_sum());
				loader.displayImage(Const.BASE_URL + "/" + recommend.getDesigner_pic(), holder.head_icon, options);
				List<MyGoods> goods_sale = recommend.getGoods_sale();
				if (goods_sale != null && goods_sale.size() > 0) {
					if (goods_sale.size() == 1) {
						MyGoods myGoods = goods_sale.get(0);
						loader.displayImage(Const.BASE_URL + "/" + myGoods.getGoods_pic(), holder.img1, options);
					} else if (goods_sale.size() == 2) {
						MyGoods myGoods0 = goods_sale.get(0); 
						MyGoods myGoods1 = goods_sale.get(1);
						loader.displayImage(Const.BASE_URL + "/" + myGoods0.getGoods_pic(), holder.img2, options);
						loader.displayImage(Const.BASE_URL + "/" + myGoods1.getGoods_pic(), holder.img2, options);

					} else if (goods_sale.size() == 3) {
						MyGoods myGoods0 = goods_sale.get(0);
						MyGoods myGoods1 = goods_sale.get(1);
						MyGoods myGoods2 = goods_sale.get(2);
						loader.displayImage(Const.BASE_URL + "/" + myGoods0.getGoods_pic(), holder.img2, options);
						loader.displayImage(Const.BASE_URL + "/" + myGoods1.getGoods_pic(), holder.img2, options);
						loader.displayImage(Const.BASE_URL + "/" + myGoods2.getGoods_pic(), holder.img3, options);

					}
				}
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView head_icon, img1, img2, img3, line_grey;
		TextView name, identity, goods, fans, des, more;

		ViewHolder(View v) {
			head_icon = (ImageView) v.findViewById(R.id.head_icon);
			img1 = (ImageView) v.findViewById(R.id.img0);
			img2 = (ImageView) v.findViewById(R.id.img1);
			img3 = (ImageView) v.findViewById(R.id.img2);
			line_grey = (ImageView) v.findViewById(R.id.line_grey);
			name = (TextView) v.findViewById(R.id.name);
			identity = (TextView) v.findViewById(R.id.identity);
			goods = (TextView) v.findViewById(R.id.goods);
			fans = (TextView) v.findViewById(R.id.fans);
			des = (TextView) v.findViewById(R.id.des);
		}
	}

}
