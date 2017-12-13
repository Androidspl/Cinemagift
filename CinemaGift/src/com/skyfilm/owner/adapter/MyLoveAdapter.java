package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.MyLove;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyLoveAdapter extends BaseAdapter {
	private Context mContext;
	private List<MyLove> mList;
	private int count = 6;
	private ImageLoader loader = ImageLoader.getInstance();
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.build();

	public MyLoveAdapter(Context context, List<MyLove> list) {
		this.mContext = context;
		this.mList = list;
//		if (mList.size() % 2 == 0) {
//			count = mList.size() >> 1;
//		} else {
//			count = (mList.size() >> 1) + 1;
//		}
	}

	@Override
	public int getCount() {
		return count;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.listview_image, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		// if (mList != null && mList.size() > 0) {
//		if(2 * position < mList.size()){
//			MyLove myLove1 = mList.get(2 * position);
//		if (2 * position + 1 < mList.size()) {
//			MyLove myLove2 = mList.get(2 * position + 1);
//		}
//		}
		// loader.displayImage(, holder.pic1, options);
		// }
		return convertView;
	}

	public class ViewHolder {
		ImageView pic1, pic2;
		TextView goods_name1, goods_price1, goods_name2, goods_price2;

		public ViewHolder(View v) {
			pic1 = (ImageView) v.findViewById(R.id.pic1);
			pic2 = (ImageView) v.findViewById(R.id.pic2);
			goods_name1 = (TextView) v.findViewById(R.id.goods_name1);
			goods_name2 = (TextView) v.findViewById(R.id.goods_name2);
			goods_price1 = (TextView) v.findViewById(R.id.goods_price1);
			goods_price2 = (TextView) v.findViewById(R.id.goods_price2);
		}
	}

}
