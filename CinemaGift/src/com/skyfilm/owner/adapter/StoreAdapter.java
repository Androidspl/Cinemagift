package com.skyfilm.owner.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.bean.Tag_goods;
import com.skyfilm.owner.bean.Type_goods;
import com.skyfilm.owner.webView.WebViewActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;

public class StoreAdapter extends BaseExpandableListAdapter implements OnClickListener {

	private Context context;
	private List<Tag_goods> tag_goods;
	private List<Type_goods> type_goods;
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.build();
	ImageLoader imageLoader = ImageLoader.getInstance();

	public StoreAdapter(Context context, List<Tag_goods> tag_goods, List<Type_goods> type_goods) {
		super();
		this.context = context;
		this.tag_goods = tag_goods;
		this.type_goods = type_goods;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.first_mark_goods:
			Intent intent_first_mark_goods = new Intent(context, WebViewActivity.class);
			intent_first_mark_goods.putExtra("url", "https://www.baudu.com");
			intent_first_mark_goods.putExtra("TITLE", "详情页");
			intent_first_mark_goods.putExtra("PAGETYPE", true);
			intent_first_mark_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_first_mark_goods);
			break;

		case R.id.second_mark_goods:
			Intent intent_second_mark_goods = new Intent(context, WebViewActivity.class);
			intent_second_mark_goods.putExtra("url", "https://www.baudu.com");
			intent_second_mark_goods.putExtra("TITLE", "详情页");
			intent_second_mark_goods.putExtra("PAGETYPE", true);
			intent_second_mark_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_second_mark_goods);
			break;

		case R.id.third_mark_goods:
			Intent intent_third_mark_goods = new Intent(context, WebViewActivity.class);
			intent_third_mark_goods.putExtra("url", "https://www.baudu.com");
			intent_third_mark_goods.putExtra("TITLE", "详情页");
			intent_third_mark_goods.putExtra("PAGETYPE", true);
			intent_third_mark_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_third_mark_goods);
			break;

		case R.id.first_classify_goods:
			Intent intent_first_hot_goods = new Intent(context, WebViewActivity.class);
			intent_first_hot_goods.putExtra("url", "https://www.baidu.com");
			intent_first_hot_goods.putExtra("TITLE", "详情页");
			intent_first_hot_goods.putExtra("PAGETYPE", true);
			intent_first_hot_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_first_hot_goods);
			break;
		case R.id.second_classify_goods:
			Intent intent_second_classify_goods = new Intent(context, WebViewActivity.class);
			intent_second_classify_goods.putExtra("url", "https://www.baidu.com");
			intent_second_classify_goods.putExtra("TITLE", "详情页");
			intent_second_classify_goods.putExtra("PAGETYPE", true);
			intent_second_classify_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_second_classify_goods);
			break;
		case R.id.third_classify_goods:
			Intent intent_third_classify_goods = new Intent(context, WebViewActivity.class);
			intent_third_classify_goods.putExtra("url", "https://www.baidu.com");
			intent_third_classify_goods.putExtra("TITLE", "详情页");
			intent_third_classify_goods.putExtra("PAGETYPE", true);
			intent_third_classify_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_third_classify_goods);
			break;
		case R.id.four_classify_goods:
			Intent intent_four_classify_goods = new Intent(context, WebViewActivity.class);
			intent_four_classify_goods.putExtra("url", "https://www.baidu.com");
			intent_four_classify_goods.putExtra("TITLE", "详情页");
			intent_four_classify_goods.putExtra("PAGETYPE", true);
			intent_four_classify_goods.putExtra("COUNSELOR", true);
			context.startActivity(intent_four_classify_goods);
			break;

		default:
			break;
		}
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView, ViewGroup arg4) {
		final ChildHolder cholder;
		if (convertView == null) {
			cholder = new ChildHolder();
			convertView = View.inflate(context, R.layout.item_shopcart_product, null);
			cholder.gv_goods = (GridView) convertView.findViewById(R.id.gv_goods);
			convertView.setTag(cholder);
		} else {
			cholder = (ChildHolder) convertView.getTag();
		}
//		GoodsInfoAdapter goodsInformation = new GoodsInfoAdapter(context, goodsEntities);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// if (groupPosition < tag_goods.size()) {
		// return tag_goods.get(groupPosition).getGoods().size();
		// }
		// return type_goods.get(groupPosition -
		// tag_goods.size()).getGoods().size();
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return tag_goods.size() + type_goods.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupHolder gholder;
		Log.d("groupPosition=", groupPosition + "");
		if (convertView == null) {
			gholder = new GroupHolder();
			convertView = View.inflate(context, R.layout.item_shopcart_group, null);
			convertView.setTag(gholder);
		} else {
			gholder = (GroupHolder) convertView.getTag();
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	/**
	 * 组元素绑定器
	 * 
	 * 
	 */
	private class GroupHolder {
	}

	/**
	 * 子元素绑定器
	 * 
	 * 
	 */
	private class ChildHolder {
		GridView gv_goods;
	}

}
