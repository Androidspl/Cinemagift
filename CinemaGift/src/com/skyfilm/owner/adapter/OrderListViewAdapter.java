package com.skyfilm.owner.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.activity.PayStyleActivity;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.mine.EvaluateOrderActivity;
import com.skyfilm.owner.mine.OrderDetailsActivity;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.webView.WebViewActivity;
import com.skyfilm.owner.widget.MyDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<Order> mList;
	private OnClickListener mCancel;
	private OnClickListener mSure;
	private int mTag;// 0全部，1待付款，2待发货，3待收货，4待评价
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.build();
	ImageLoader imageLoader = ImageLoader.getInstance();

	public OrderListViewAdapter(Context context, List<Order> list, int tag, OnClickListener cancel,
			OnClickListener sure) {
		this.mContext = context;
		this.mList = list;
		this.mTag = tag;
		this.mCancel = cancel;
		this.mSure = sure;
	}

	@Override
	public int getCount() {
		if (mList != null && mList.size() > 0) {
			return mList.size();
		}
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
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_order, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String status = null;
		// if(mList != null && mList.size()>0){
		// Order order = mList.get(position);
		// status = order.getStatus();
		// imageLoader.displayImage(order.getGoods_logo(), holder.goods_logo,
		// options);
		// holder.goods_name.setText(order.getGoods_name());
		// holder.goods_type.setText(order.getGoods_type());
		// holder.goods_money.setText(order.getGoods_money());
		if (mTag == 0) {// 全部订单
			// if("hold_pay".equals(status)){
			holder.bottom1.setText("取消订单");
			// holder.bottom1.setTag(order);
			holder.bottom1.setOnClickListener(mCancel);
			holder.bottom2.setText("去支付");
			gotoPay(holder.bottom2);
			// if(!StringUtil.isNull(order.getAddressId())){
			// gotoPay(holder.bottom2);
			// }else{
			// Intent intent = new Intent(mContext, OrderDetailsActivity.class);
			// showDialog("该订单没有填写地址，请选择地址",intent);
			// }

		} else if ("hold_shiping".equals(status)) {
			holder.bottom1.setVisibility(View.GONE);
			holder.bottom2.setVisibility(View.GONE);
		} else if ("hold_delivery".equals(status)) {
			holder.bottom1.setText("查看物流");
			click(holder.bottom1);
			holder.bottom2.setText("确认收货");
			// holder.bottom2.setTag(order);
			holder.bottom2.setOnClickListener(mSure);
		} else if ("hold_comment".equals(status)) {
			holder.bottom1.setVisibility(View.GONE);
			holder.bottom2.setText("评价");
			comment(holder.bottom2);
			// }
		} else if (mTag == 1) {// 待付款订单
			holder.bottom1.setText("取消订单");
			// holder.bottom1.setTag(order);
			holder.bottom1.setOnClickListener(mCancel);
			holder.bottom2.setText("去支付");
			gotoPay(holder.bottom2);
			// if(!StringUtil.isNull(order.getAddressId())){
			// gotoPay(holder.bottom2);
			// }else{
			// Intent intent = new Intent(mContext, OrderDetailsActivity.class);
			// showDialog("该订单没有填写地址，请选择地址",intent);
			// }
		} else if (mTag == 2) {// 待发货订单
			holder.bottom1.setVisibility(View.GONE);
			holder.bottom2.setVisibility(View.GONE);
			if (mList != null && mList.size() > 0) {
				Order order = mList.get(position);
				holder.goods_name.setText(order.getAppointment_id());
				holder.goods_type.setText(order.getAppointment_name());
			}
		} else if (mTag == 3) {// 待收货订单
			holder.bottom1.setText("查看物流");
			click(holder.bottom1);
			holder.bottom2.setText("确认收货");
			// holder.bottom2.setTag(order);
			holder.bottom2.setOnClickListener(mSure);
		} else if (mTag == 4) {// 待评价订单
			holder.bottom1.setVisibility(View.GONE);
			holder.bottom2.setText("评价");
			comment(holder.bottom2);
		}
		// }
		// TODO 设置值
		return convertView;
	}

	private void showDialog(String str, final Intent intent) {
		MyDialog.show(mContext, "提醒", str, "确定", "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				mContext.startActivity(intent);
			}
		}, new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	private void comment(TextView bottom2) {
		bottom2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, EvaluateOrderActivity.class);
				mContext.startActivity(intent);
			}
		});

	}

	private void gotoPay(TextView bottom2) {
		bottom2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PayStyleActivity.class);
				mContext.startActivity(intent);
			}
		});

	}

	private void click(TextView bottom1) {
		bottom1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String url = "http://m.kuaidi100.com/index_all.html?type=shunfeng&postid=304708923294&callbackurl=https://www.baidu.com/";
				Intent intent = new Intent();
				intent.setClass(mContext, WebViewActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("noactionbar", true);
				mContext.startActivity(intent);
				// Uri uri = Uri.parse(url);
				// Intent it = new Intent(Intent.ACTION_VIEW, uri);
				// mContext.startActivity(it);
			}
		});
	}

	class ViewHolder {
		ImageView goods_logo;
		TextView goods_name, goods_type, goods_money, bottom1, bottom2;

		ViewHolder(View v) {
			goods_logo = (ImageView) v.findViewById(R.id.goods_logo);
			goods_name = (TextView) v.findViewById(R.id.goods_name);
			goods_type = (TextView) v.findViewById(R.id.goods_type);
			goods_money = (TextView) v.findViewById(R.id.goods_money);
			bottom1 = (TextView) v.findViewById(R.id.bottom1);
			bottom2 = (TextView) v.findViewById(R.id.bottom2);
		}
	}

}
