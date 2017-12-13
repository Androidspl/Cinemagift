package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.webView.WebViewActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreViewPagerAdapter extends PagerAdapter {

	private View[] mList;
	private List<StoreEntity> datas;
	private Context mContext;

	DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
			.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
	ImageLoader loader;

	public StoreViewPagerAdapter(Context context, List<StoreEntity> vList) {
		this.mContext = context;
		this.datas = vList;
		mList = new View[datas.size()];
		this.loader = ImageLoader.getInstance();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(View view, final int position) {
		try {
			ViewPager pViewPager = ((ViewPager) view);
			ImageView imageview = null;
			// TODO
			View v = mList[position];
			if (v == null) {
				v = View.inflate(mContext, R.layout.imageview_layout, null);
				imageview = (ImageView) v.findViewById(R.id.imageview);
				TextView banner_text = (TextView) v.findViewById(R.id.banner_text);
				banner_text.setText(datas.get(position).getBanner_title());
				v.setTag(imageview);
				mList[position] = v;
			} else {
				imageview = (ImageView) v.getTag();
			}
			loader.displayImage(Const.BASE_URL + "/" + datas.get(position).getImg_url(), imageview, options);
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, WebViewActivity.class);
					intent.putExtra("url", datas.get(position).getLink_url());
					intent.putExtra("PAGETYPE", true);
					// intent.putExtra("TITLE", "咨询详情");
					// intent.putExtra("COUNSELOR", true);
					mContext.startActivity(intent);
				}
			});
			pViewPager.addView(v);
		} catch (Exception e) {
		}
		return mList[position];
	}

	@Override
	public void destroyItem(View view, int position, Object object) {
		ImageView v = (ImageView) view.findViewById(R.id.imageview);
		if (position == 1) {
			return;
		} else {
			if (v != null) {
				v.setImageBitmap(null);
			}
		}
		((ViewPager) view).removeView(view);
		System.gc();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	public static class Banner {
		String img_url;
		String link_url;
		String title;

		public Banner(String img_url, String title, String link_url) {
			this.img_url = img_url;
			this.link_url = link_url;
			this.title = title;
		}

		public String getImg_url() {
			return img_url;
		}

		public void setImg_url(String img_url) {
			this.img_url = img_url;
		}

		public String getLink_url() {
			return link_url;
		}

		public void setLink_url(String link_url) {
			this.link_url = link_url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Banner() {
		}
	}
}
