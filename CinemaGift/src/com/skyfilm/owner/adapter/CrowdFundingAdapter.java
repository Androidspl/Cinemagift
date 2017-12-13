package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.CrowdFunding;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CrowdFundingAdapter extends BaseAdapter {

	private Context mContext;
	private List<CrowdFunding> mList;
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
            .build();
    ImageLoader imageLoader = ImageLoader.getInstance();
	public CrowdFundingAdapter(Context context,List<CrowdFunding> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
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
			convertView = View.inflate(mContext, R.layout.item_crowdfunding, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		CrowdFunding crowdFunding = mList.get(position);
//		imageLoader.displayImage(crowdFunding.getGoods_logo(), holder.goods_logo, options);
//		holder.goods_name.setText(crowdFunding.getGoods_name());
//		holder.goods_money.setText(crowdFunding.getGoods_money());
//		if("".equals(crowdFunding.getCrowdfunding())){
			holder.crowdfunding.setText("众筹中");
//		}else{
//			holder.crowdfunding.setText("众筹结束");
//		}
		return convertView;
	}
	
	class ViewHolder{
		ImageView goods_logo;
		TextView goods_name,goods_money,crowdfunding;
		ViewHolder(View v){
			goods_logo = (ImageView) v.findViewById(R.id.goods_logo);
			goods_name = (TextView) v.findViewById(R.id.goods_name);
			goods_money = (TextView) v.findViewById(R.id.goods_money);
			crowdfunding = (TextView) v.findViewById(R.id.crowdfunding);
		}
	}

}
