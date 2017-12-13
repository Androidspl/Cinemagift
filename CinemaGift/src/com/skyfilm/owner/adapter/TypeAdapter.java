package com.skyfilm.owner.adapter;

import java.util.ArrayList;

import com.skyfilm.owner.R;
import com.skyfilm.owner.adapter.CopyrightAdapter.ViewHolder;
import com.skyfilm.owner.bean.mine.DesignType;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.mine.IssueChooseActivity;
import com.skyfilm.owner.utils.SpUtils;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TypeAdapter extends BaseAdapter{

	private IssueChooseActivity mContext;
	private ArrayList<DesignType> mList;
	
	public TypeAdapter(IssueChooseActivity context,ArrayList<DesignType> list) {
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
			convertView = View.inflate(mContext, R.layout.item_type, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//TODO 选中版权数据返回 
		holder.type_line.setVisibility(View.GONE);
		if(position == 4){
			holder.type_line.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	class ViewHolder{
		CheckBox cb_type;
		ImageView type_line;
		ViewHolder(View v){
			cb_type = (CheckBox) v.findViewById(R.id.cb_type);
			type_line = (ImageView) v.findViewById(R.id.type_line);
		}
	}

}
