package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.mine.AddAddressActivity;
import com.skyfilm.owner.mine.MyAddressActivity;
import com.skyfilm.owner.utils.SpUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {

	private MyAddressActivity mContext;
	private List<MyAddress> mList;
	private int mTag;// 标识选择(0)还是查看(1)收货地址
	OnClickListener set_default;// (setDefault)
	OnClickListener delete;// (删除)

	public AddressAdapter(MyAddressActivity context, List<MyAddress> list, int tag, OnClickListener set_default,
			OnClickListener delete) {
		this.mContext = context;
		this.mList = list;
		this.mTag = tag;
		this.set_default = set_default;
		this.delete = delete;
	}

	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
	public int getCount() {
		if (mList != null && mList.size() != 0) {
			return mList.size();
		}
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_address, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (mList != null && mList.size() > 0) {
			MyAddress myAddress = mList.get(position);
			holder.receive_name.setText(myAddress.getName());
			holder.receive_address.setText(myAddress.getAddress());
			holder.receive_phoneNum.setText(myAddress.getMobile());
			// "is_default判断默认地址字段，1默认，2非默认"
			String default_address = myAddress.getIs_default();
			if (position == mList.size() - 1) {
				holder.line2.setVisibility(View.GONE);
				holder.add.setVisibility(View.VISIBLE);
				addClick(holder);
				holder.line3.setVisibility(View.VISIBLE);
			} else {
				holder.add.setVisibility(View.GONE);
				holder.line2.setVisibility(View.VISIBLE);
				holder.line3.setVisibility(View.GONE);
			}
			if (mTag == 1) {// 查看
				holder.choose_default.setVisibility(View.GONE);
				holder.choose_address.setVisibility(View.GONE);
				holder.rl_default.setVisibility(View.VISIBLE);
				// holder.add.setVisibility(View.VISIBLE);
				String id = myAddress.getId();
				holder.delete.setTag(id);
				holder.delete.setOnClickListener(delete);

				holder.all_address_des.setVisibility(View.VISIBLE);
				holder.line.setVisibility(View.VISIBLE);
				holder.line1.setVisibility(View.VISIBLE);
				if ("1".equals(default_address)) {
					holder.set_default.setText("默认地址");
					holder.img_set_default.setImageResource(R.drawable.radio_on);
					holder.img_set_default.setClickable(false);
				} else {
					holder.set_default.setText("设为默认");
					holder.img_set_default.setClickable(false);
					holder.img_set_default.setImageResource(R.drawable.radio_off);
					holder.img_set_default.setTag(myAddress);
					holder.img_set_default.setOnClickListener(set_default);
				}

			} else {// 选择
				if(position == 0){
					holder.line1.setVisibility(View.VISIBLE);
				}else{
					holder.line1.setVisibility(View.GONE);
				}
				if ("1".equals(default_address)) {//默认地址
					holder.choose_default.setVisibility(View.VISIBLE);
					holder.choose_default.setImageResource(R.drawable.default_address);
					holder.choose_address.setImageResource(R.drawable.radio_on);
				}else{
					holder.choose_default.setVisibility(View.GONE);
				}
				holder.choose_address.setVisibility(View.VISIBLE);
				holder.rl_default.setVisibility(View.GONE);
				holder.line2.setVisibility(View.GONE);
				holder.line3.setVisibility(View.GONE);
//				holder.add.setVisibility(View.GONE);
				// holder.default_address.setTag(myAddress);
				// 保存对勾状态，将选择的地址id保存，再次进来时遍历每一个地址，id和保存的id一致的设置其对勾状态为选中，其余的设置为默认状态
				int posi = SpUtils.getInt(mContext, "positon", 0);
				if (position == posi) {
					saveData(position, holder);
				}
				holder.choose_address.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						saveData(position, holder);
						holder.choose_address.setImageResource(R.drawable.radio_on);
						mContext.finish();
					}

				});
			}
		} else {//集合为空
			holder.all_address_des.setVisibility(View.GONE);
			holder.rl_default.setVisibility(View.GONE);
			holder.line.setVisibility(View.GONE);
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.add.setVisibility(View.VISIBLE);
			addClick(holder);
		}

		return convertView;
	}

	private void addClick(final ViewHolder holder) {
		holder.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AddAddressActivity.class);
				mContext.startActivity(intent);
				// mContext.finish();
			}
		});
	}

	private void saveData(final int position, final ViewHolder holder) {
		String name = holder.receive_name.getText().toString().trim();
		String address = holder.receive_address.getText().toString().trim();
		String phoneNum = holder.receive_phoneNum.getText().toString().trim();
		SpUtils.setString(mContext, "name", name);
		SpUtils.setString(mContext, "address", address);
		SpUtils.setString(mContext, "phoneNum", phoneNum);
		SpUtils.setInt(mContext, "positon", position);
	}

	class ViewHolder {
		TextView receive_name, receive_address, receive_phoneNum, set_default;
		ImageView choose_default, choose_address, delete, line1, line2, line, line3, img_set_default;
		RelativeLayout rl_default, add, all_address_des;

		ViewHolder(View v) {
			all_address_des = (RelativeLayout) v.findViewById(R.id.all_address_des);
			rl_default = (RelativeLayout) v.findViewById(R.id.rl_default);
			receive_name = (TextView) v.findViewById(R.id.receive_name);
			choose_address = (ImageView) v.findViewById(R.id.choose_address);
			set_default = (TextView) v.findViewById(R.id.set_default);
			receive_address = (TextView) v.findViewById(R.id.receive_address);
			receive_phoneNum = (TextView) v.findViewById(R.id.receive_phoneNum);
			delete = (ImageView) v.findViewById(R.id.delete);
			line = (ImageView) v.findViewById(R.id.line);
			line1 = (ImageView) v.findViewById(R.id.line1);
			line2 = (ImageView) v.findViewById(R.id.line2);
			line3 = (ImageView) v.findViewById(R.id.line3);
			img_set_default = (ImageView) v.findViewById(R.id.img_set_default);
			add = (RelativeLayout) v.findViewById(R.id.add);
			choose_default = (ImageView) v.findViewById(R.id.choose_default);
		}
	}
}
