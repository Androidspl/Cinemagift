package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.CrowdFundingEntity;
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

public class CrowdAdapter extends BaseAdapter {

	private Context context;
	private List<CrowdFundingEntity> crowdEntities;
	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;

	public CrowdAdapter(Context context) {
		this.context = context;
		this.loader = ImageLoader.getInstance();
	}

	public CrowdAdapter(Context context, List<CrowdFundingEntity> crowdEntities) {
		this.context = context;
		this.crowdEntities = crowdEntities;
		this.loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return crowdEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return crowdEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_crowd, null);
		}
		CrowdAdapterHolder holder = CrowdAdapterHolder.getHolder(convertView);
		CrowdFundingEntity crowdFundingEntity = crowdEntities.get(position);
		
		if (position == 0) {
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			layoutParams.topMargin = 24;
			layoutParams.leftMargin = 24;
			layoutParams.rightMargin = 24;
			holder.crowd_img.setLayoutParams(layoutParams);
		}
		
		loader.displayImage(Const.URL_T_CJ_ + crowdFundingEntity.getPic_pass(), holder.crowd_img, options);
		holder.crowd_name.setText(crowdFundingEntity.getCrowdfund_name());
		if (!crowdFundingEntity.getDesigner().equals("")&&!crowdFundingEntity.getBrand().equals("")) {
			holder.crowd_designer.setText(crowdFundingEntity.getDesigner());
			holder.crowd_brand.setText(crowdFundingEntity.getBrand());
			holder.crowd_designer.setVisibility(View.VISIBLE);
			holder.crowd_brand.setVisibility(View.VISIBLE);
		}else if (!crowdFundingEntity.getDesigner().equals("")&&crowdFundingEntity.getBrand().equals("")) {
			holder.crowd_designer.setText(crowdFundingEntity.getDesigner());
			holder.crowd_designer.setVisibility(View.VISIBLE);
			holder.crowd_brand.setVisibility(View.GONE);
		}else if (crowdFundingEntity.getDesigner().equals("")&&!crowdFundingEntity.getBrand().equals("")) {
			holder.crowd_brand.setText(crowdFundingEntity.getBrand());
			holder.crowd_designer.setVisibility(View.GONE);
			holder.crowd_brand.setVisibility(View.VISIBLE);
		}else if (crowdFundingEntity.getDesigner().equals("")&&crowdFundingEntity.getBrand().equals("")) {
			holder.crowd_designer.setVisibility(View.GONE);
			holder.crowd_brand.setVisibility(View.GONE);
		}
		
		holder.progress.setProgress(crowdFundingEntity.getProgress());
		holder.crowd_count.setText(crowdFundingEntity.getCount()+"");
		holder.crowd_complete.setText(crowdFundingEntity.getProgress()+"%");
		holder.crowd_residual_time.setText(crowdFundingEntity.getResidual_time()+"");
		return convertView;
	}

	static class CrowdAdapterHolder {

		TextView crowd_name,crowd_designer,crowd_brand,crowd_count,crowd_complete,crowd_residual_time;
		private ImageView crowd_img;
		private ProgressBar progress;
		private ImageView crowdfunding;

		public CrowdAdapterHolder(View convertView) {
			// 众筹图片
			crowd_img = (ImageView) convertView.findViewById(R.id.crowd_img);
			// 众筹中或者众筹完成
			crowdfunding = (ImageView) convertView.findViewById(R.id.crowdfunding);
			// 众筹名字
			crowd_name = (TextView) convertView.findViewById(R.id.crowd_name);
			// 众筹设计师
			crowd_designer = (TextView) convertView.findViewById(R.id.crowd_designer);
			// 众筹品牌
			crowd_brand = (TextView) convertView.findViewById(R.id.crowd_brand);
			// 众筹进度
			progress = (ProgressBar) convertView.findViewById(R.id.progress);
			// 众筹人数
			crowd_count = (TextView) convertView.findViewById(R.id.crowd_count);
			// 众筹完成度
			crowd_complete = (TextView) convertView.findViewById(R.id.crowd_complete);
			// 众筹天数
			crowd_residual_time = (TextView) convertView.findViewById(R.id.crowd_residual_time);
		}

		public static CrowdAdapterHolder getHolder(View convertView) {
			CrowdAdapterHolder holder = (CrowdAdapterHolder) convertView.getTag();
			if (holder == null) {
				holder = new CrowdAdapterHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}

}
