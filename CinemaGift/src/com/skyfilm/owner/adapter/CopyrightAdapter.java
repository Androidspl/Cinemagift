package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.mine.InstructionActivity;
import com.skyfilm.owner.mine.IssueChooseActivity;
import com.skyfilm.owner.utils.SpUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CopyrightAdapter extends BaseAdapter {

	private IssueChooseActivity mContext;
	private ArrayList<Information> mList;
	
	public CopyrightAdapter(IssueChooseActivity context,ArrayList<Information> list) {
		this.mContext = context;
		this.mList = list;
	}
	@Override
	public int getCount() {
		return 5;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.item_copyright, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position == 4){
			holder.line_lastone.setVisibility(View.VISIBLE);
		}
		//TODO 设置值
		holder.copyright.setText("版权说明"+position);
		int posi = SpUtils.getInt(mContext, "position", 0);
		if(posi == position){
			SpUtils.setString(mContext, "copyright", "版权说明"+position);
			holder.copyright.setTextColor(mContext.getResources().getColor(R.color.help_bule));
			holder.ok.setImageResource(R.drawable.radio_on);
		}
		holder.ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				holder.ok.setImageResource(R.drawable.radio_on);
//				holder.copyright.setText("版权说明"+position);
//				holder.copyright.setTextColor(0x00f);
				SpUtils.setString(mContext, "copyright", "版权说明"+position);
				SpUtils.setInt(mContext, "position", position);
				mContext.finish();
			}
		});
		//TODO 选中版权数据返回 
		return convertView;
	}
	
	class ViewHolder{
		TextView copyright;
		ImageView ok,line_lastone;
		ViewHolder(View v){
			ok = (ImageView) v.findViewById(R.id.ok);
			line_lastone = (ImageView) v.findViewById(R.id.line_lastone);
			copyright = (TextView) v.findViewById(R.id.copyright);
		}
	}

}
