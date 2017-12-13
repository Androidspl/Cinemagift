package com.skyfilm.owner.adapter;

import java.util.List;

import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.ProductInfo;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.myinterface.CheckInterface;
import com.skyfilm.owner.myinterface.DelectInterface;
import com.skyfilm.owner.myinterface.ModifyCountInterface;
import com.skyfilm.owner.widget.MyDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckAdapter extends BaseAdapter {
	// 填充数据的list
	private List<StoreEntity> mlist;
	// 上下文
	private Context context;
	// 用来导入布局
	private ModifyCountInterface modifyCountInterface;
	private CheckInterface checkInterface;
	private DelectInterface delectInterface;

	// 构造器
	public CheckAdapter(List<StoreEntity> mlist, Context context) {
		this.context = context;
		this.mlist = mlist;
	}

	public void setCheckInterface(CheckInterface checkInterface)
	{
		this.checkInterface = checkInterface;
	}
	
	public void setModifyCountInterface(ModifyCountInterface modifyCountInterface)
	{
		this.modifyCountInterface = modifyCountInterface;
	}
	
	public void setDelectGoodsInterface(DelectInterface delectInterface)
	{
		this.delectInterface = delectInterface;
	}

	@Override
	public int getCount() {
		 return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = View.inflate(context, R.layout.check_item, null);
			
			holder.delect_goods = (ImageView) convertView.findViewById(R.id.delect_goods);
			holder.trade_name = (TextView) convertView.findViewById(R.id.trade_name);
			holder.trade_price = (TextView) convertView.findViewById(R.id.trade_price);
			holder.goods_style = (TextView) convertView.findViewById(R.id.goods_style);
			/** 商品列表的checkbox */
			holder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);
			/** 商品数量加一 */
			holder.iv_increase = (ImageView) convertView.findViewById(R.id.tv_add);
			/** 商品数量减一 */
			holder.iv_decrease = (ImageView) convertView.findViewById(R.id.tv_reduce);

			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		
		final StoreEntity product = (StoreEntity) mlist.get(position);

		if (product != null) {
			/** 商品描述 */
			holder.trade_name.setText(product.getDesc());
			/** 商品价格 */
			holder.trade_price.setText("￥"+product.getPrice());
			/** 商品样式 */
			holder.goods_style.setText(product.getName());
			/** 商品数量 */
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_num);
			holder.cb_check.setChecked(product.isChoosed());
			holder.cb_check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					product.setChoosed(((CheckBox) v).isChecked());
					holder.cb_check.setChecked(((CheckBox) v).isChecked());
					checkInterface.checkGoods(position, ((CheckBox) v).isChecked());// 暴露子选接口
				}
			});
			holder.iv_increase.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					modifyCountInterface.doIncrease(position, holder.tv_count,
							holder.cb_check.isChecked());// 暴露增加接口
				}
			});
			holder.iv_decrease.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					modifyCountInterface.doDecrease(position, holder.tv_count,
							holder.cb_check.isChecked());// 暴露删减接口
				}
			});
		}
		
		// 设置list中TextView的显示
		holder.delect_goods.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				MyDialog.show(context, "", "删除该商品", "是", "否", true, new OnClickListener() {
					@Override
					public void onClick(View v) {
						delectInterface.doDelect(position);
					}
				}, new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}, new DialogInterface.OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {

					}
				}, new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {

					}
				});
			}
		});

		return convertView;
	}

	public static class ViewHolder {
		
		public TextView trade_name, trade_price, goods_style;
		public ImageView delect_goods, iv_increase, iv_decrease;
		public CheckBox cb_check;
		public TextView tv_count;

	}

}
