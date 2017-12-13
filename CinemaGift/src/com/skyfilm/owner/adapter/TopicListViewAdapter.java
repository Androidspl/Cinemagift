package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.utils.DensityUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopicListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<Topic> mList;
	private int mHigh;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;

	public TopicListViewAdapter(Context context,List<Topic> list, int width) {
		this.mContext = context;
		this.mHigh = width;
		this.mList = list;
		this.loader =ImageLoader.getInstance();
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
			convertView = View.inflate(mContext, R.layout.item_topicpage, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// TODO 设置值
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				DensityUtil.dip2px(mContext, mHigh));
		holder.img.getLayoutParams().height = DensityUtil.dip2px(mContext, mHigh);
		holder.img1.getLayoutParams().height = DensityUtil.dip2px(mContext, mHigh);
		holder.top_des.getLayoutParams().height = DensityUtil.dip2px(mContext, mHigh);
		holder.img1.setAlpha(0.5f);
		holder.img.setLayoutParams(layoutParams);
		holder.img1.setLayoutParams(layoutParams);
		if (mList != null && mList.size() > 0) {
			if(position == mList.size()-1){
				holder.blank_img.setVisibility(View.GONE);
			}else{
				holder.blank_img.setVisibility(View.VISIBLE);
			}
			Topic topic = mList.get(position);
			holder.top_des.setText(topic.getTopic_title());
			loader.displayImage(Const.BASE_URL+"/"+topic.getTopic_img(), holder.img, options);
		}
		
		return convertView;
	}

	class ViewHolder {
		ImageView img, img1,blank_img;
		RelativeLayout rl;
		TextView top_des;

		ViewHolder(View v) {
			img = (ImageView) v.findViewById(R.id.img);
			img1 = (ImageView) v.findViewById(R.id.img1);
			blank_img = (ImageView) v.findViewById(R.id.blank_img);
			rl = (RelativeLayout) v.findViewById(R.id.rll);
			top_des = (TextView) v.findViewById(R.id.top_des);
		}
	}

}
