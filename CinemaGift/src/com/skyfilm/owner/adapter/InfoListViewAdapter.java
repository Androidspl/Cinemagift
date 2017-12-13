package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.utils.DensityUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class InfoListViewAdapter extends BaseAdapter {

	private FragmentActivity mContext;
	private List<Information> mList;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;
	public InfoListViewAdapter(FragmentActivity context, List<Information> list) {
		this.mContext = context;
		this.mList = list;
		this.loader =ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		if(mList != null && mList.size()>0){
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
			convertView = View.inflate(mContext, R.layout.item_infopage, null);
			holder = new ViewHolder(convertView, position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		} 

		
		// TODO 设置值
		holder.img.setImageBitmap(null);
		if(mList != null && mList.size()>0){
			if(position == mList.size()-1){
				holder.img_blank.setVisibility(View.VISIBLE);
//				// TODO 设置值
//				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//						LayoutParams.WRAP_CONTENT);
//				int dip2px = DensityUtil.dip2px(mContext, 18);
//				layoutParams.setMargins(0, 0, dip2px, dip2px);
//				holder.comment.setLayoutParams(layoutParams);
			}else{
				holder.img_blank.setVisibility(View.GONE);
			}
			Information information = mList.get(position);
			String info_type = information.getInfo_type();
			if("专访".endsWith(info_type)){
				holder.label.setText("专题");
				holder.label.setTextColor(mContext.getResources().getColor(R.color.mine_banner_point_s));
			}
			else if("潮品".endsWith(info_type)){
				holder.label.setText("潮品推荐");
				holder.label.setTextColor(mContext.getResources().getColor(R.color.mine_text_info_2ba0f8));
			}
			else if("快讯".endsWith(info_type)){
				holder.label.setText("快讯");
				holder.label.setTextColor(mContext.getResources().getColor(R.color.mine_text_info_2ba0f8));
			}
			holder.comment.setText(information.getComments_count());
			holder.des.setText(information.getInfo_title());
			loader.displayImage(Const.BASE_URL+"/"+information.getInfo_pic(), holder.img, options);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img,img1,img_blank;
		TextView des;
		TextView label;
		TextView comment;

		ViewHolder(View v, int position) {
			img = (ImageView) v.findViewById(R.id.img);
			img1 = (ImageView) v.findViewById(R.id.img1);
			img_blank = (ImageView) v.findViewById(R.id.img_blank);
			des = (TextView) v.findViewById(R.id.des);
			label = (TextView) v.findViewById(R.id.label);
			comment = (TextView) v.findViewById(R.id.comment);
		}
	}

}
