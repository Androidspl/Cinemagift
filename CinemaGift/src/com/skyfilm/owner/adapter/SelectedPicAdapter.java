package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.skyfilm.owner.R;
import com.skyfilm.owner.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 选择图片的适配器
 * @author Administrator
 *
 */
public class SelectedPicAdapter extends BaseAdapter {

	private static final String TAG = "SelectedPicAdapter";

	public static final String BAOSHI_IMAGE = "baoshi_image";
	private Context mContext;
	private ArrayList<String> dataList;
	private DisplayMetrics dm;
	private ImageLoader loader;
	private ONItemDeleteListener listener;
	private int maxCount;
	DisplayImageOptions options; // 配置图片加载及显示选项  
	public SelectedPicAdapter(Context c, ArrayList<String> dataList,ONItemDeleteListener listener,int maxCount) {
		this.maxCount = maxCount;
		mContext = c;
		this.dataList = dataList;
		this.listener = listener;
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public String getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position,  View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.image_attach_grid_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
			holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String path = dataList.get(position);
		if(position == 2){
//			Toast.makeText(mContext, path, 0).show();
		}
		//loader.cancelDisplayTask(holder.imageView);
		if (path.contains(BAOSHI_IMAGE)){
			holder.ivDelete.setVisibility(View.GONE);
			holder.imageView.setImageResource(R.drawable.btn_addphoto);
		}else {
			holder.ivDelete.setVisibility(View.VISIBLE);
			holder.ivDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(dataList.size()>=maxCount && !BAOSHI_IMAGE.equals(dataList.get(dataList.size()-1))){
						dataList.add(BAOSHI_IMAGE);
					}
//					dataList.remove(position);
					if(listener != null){
						listener.onDelete(position);
					}
					SelectedPicAdapter.this.notifyDataSetChanged();
				}
			});
			
			 Util.diaplayImage(this,loader, path, holder.imageView, 88);
		}
		return convertView;
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

	class ViewHolder {
		ImageView imageView;
		ImageView ivDelete;
	}
	
	public interface ONItemDeleteListener{
		public void onDelete(int index);
	}
	
}
