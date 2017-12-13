package com.skyfilm.owner.base;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.Const;
import com.skyfilm.owner.R;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqToast;
import com.skyfilm.owner.widget.MyProgress;
import com.skyfilm.owner.widget.xlistview.XListView;
import com.skyfilm.owner.widget.xlistview.XListView.IXListViewListener;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseListFragment<T> extends BaseThreadFragment
		implements IXListViewListener, OnItemLongClickListener {

	public enum PullType {
		NONE, REFRESH, LOADMORE, ALL
	}

	public enum DeleteType {
		NONE, DELETE, DELETEINTHREAD
	}

	protected TextView noDateInfoTextView;
	protected XListView listView;
	protected List<T> dataList;
	protected BaseAdapter listAdapter;
	protected Activity context;

	protected ImageLoader Loader;

	private PullType pullType;
	private DeleteType deleteType;

	protected boolean chatStyle = false;
	private int currentPage = 0;
	public View view;
	protected View ic_title;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	protected void afterListRefresh() {
	}

	protected boolean getIsCommuntityCode() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListView();
	}

	protected abstract void onListItemClick(T item);

	protected abstract int getPage();

	protected abstract List<T> getDataListFromCache(String userId, String cid);

	protected abstract List<T> getDataList(String userId, String cid, int page, int pageSize) throws CsqException;

	// protected abstract List<T> getDataList(String userId, String cid,String
	// lastId, int pageSize) throws CsqException;
	protected abstract BaseAdapter getListAdapter(List<T> data);

	protected boolean deleteItem(T item) {
		return false;
	};

	protected void deleteItemInThread(T item) throws CsqException {
	};

	protected void addHeaderView() {
	}

	protected void initHeaderView() {
	}

	protected void setEventOption() {
		setEventOption(PullType.ALL, DeleteType.NONE);
	};

	protected int getNoDateTipsRId() {
		return R.string.not_data_tip;
	};

	protected void setEventOption(PullType pullType, DeleteType deleteType) {

		this.pullType = pullType;
		this.deleteType = deleteType;

		if (pullType != PullType.NONE) {
			listView.setXListViewListener(this);

			if (pullType == PullType.REFRESH) {
				this.listView.setPullRefreshEnable(true);
				this.listView.setPullLoadEnable(false);
			} else if (pullType == PullType.LOADMORE) {
				this.listView.setPullRefreshEnable(false);
				this.listView.setPullLoadEnable(true);
			} else if (pullType == PullType.ALL) {
				this.listView.setPullRefreshEnable(true);
				this.listView.setPullLoadEnable(true);
			}
		}

		if (deleteType != DeleteType.NONE) {
			this.listView.setOnItemLongClickListener(this);

		}
	}

	protected void initView(View view) {
		noDateInfoTextView = (TextView) view.findViewById(R.id.noDateInfoTextView);
		listView = (XListView) view.findViewById(R.id.contentListView);
		ic_title = (View) view.findViewById(R.id.ic_title);
//		BaseAdapter listAdapter2 = getListAdapter(dataList);
//		listView.setAdapter(listAdapter2);
		addHeaderView();
		setEventOption();

		Loader = ImageLoader.getInstance();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//				try {
//					onListItemClick(dataList.get(position - listView.getHeaderViewsCount()));
//				} catch (ArrayIndexOutOfBoundsException e) {
//					onListItemClick(dataList.get(position));
//				}
				click();
			}

		});

	}

	public void click() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	protected void updateListView(List<T> newDataList) {
		updateListView(true, Const.LIST_REFRESH, newDataList);
	}

	protected final void initListView() {
		List<T> listInCache = getDataListFromCache(null, null);
		if (listInCache != null) {
			updateListView(true, Const.LIST_REFRESH, listInCache);
			if (pullType == PullType.REFRESH || pullType == PullType.ALL) {
				if (chatStyle) {
					new CsqRunnable(Const.LIST_REFRESH, 1).start();
				} else {
					listView.startRefresh(true);
				}
			}
		} else if (pullType == PullType.REFRESH || pullType == PullType.ALL) {
			MyProgress.show(getString(R.string.prg_loading), context);
			new CsqRunnable(Const.LIST_REFRESH, 1).start();
		}
	}

	protected void updateListView(boolean result, int operate, List<T> newDataList) {

		if (operate == Const.LIST_REFRESH) {
			if (result) {
				if (newDataList != null && newDataList.size() > 0) {
					this.dataList = newDataList;

					initHeaderView();
					listAdapter = getListAdapter(dataList);
					listView.setAdapter(listAdapter);
					if (listAdapter.getCount() > 0) {
						listView.setVisibility(View.VISIBLE);
						noDateInfoTextView.setVisibility(View.GONE);
					} else {
						listView.setVisibility(View.GONE);
						noDateInfoTextView.setVisibility(View.VISIBLE);
						listView.setAdapter(null);
					}
					if (chatStyle) {
						this.context.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								listView.setSelection(listView.getBottom());
							}
						});
					}
				} else {
					noDateInfoTextView.setText(getNoDateTipsRId());
					listView.setVisibility(View.GONE);
					noDateInfoTextView.setVisibility(View.VISIBLE);
					listAdapter = getListAdapter(newDataList);
					listView.setAdapter(listAdapter);
				}
				if (pullType == PullType.LOADMORE || pullType == PullType.ALL) {
					listView.setPullLoadEnable(true);
				} else {
					listView.stopLoadMore();
				}
			}
			listView.stopRefresh();
		} else if (dataList != null && dataList.size() > 0) {
			if (newDataList != null && newDataList.size() > 0) {
				if (chatStyle) {
					dataList.addAll(0, newDataList);
				} else {
					dataList.addAll(newDataList);
				}
				listAdapter.notifyDataSetChanged();
			}
			if (chatStyle) {
				listView.stopRefresh();
			}
		}

		if (result) {
			if (newDataList == null || newDataList.size() < Const.LIST_PAGE_SIZE) {
				listView.stopLoadMore();
			}
		}
		afterListRefresh();
	}

	protected void updateDeleteItem(T item) {
		dataList.remove(item);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh() {
		if (chatStyle) {
			if (dataList != null && dataList.size() > 0) {
				// new CsqRunnable(Const.LIST_LOADMORE, getPage()).start();
				new CsqRunnable(Const.LIST_LOADMORE, 1).start();
			}
		} else {
			new CsqRunnable(Const.LIST_REFRESH, 1).start();
		}
	}

	@Override
	public void onLoadMore() {
		if (chatStyle) {
			new CsqRunnable(Const.LIST_REFRESH, 1).start();
		} else {
			if (dataList != null && dataList.size() > 0) {
				new CsqRunnable(Const.LIST_LOADMORE, getPage()).start();
			}
		}
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		switch (operate) {
		case Const.LIST_REFRESH:
		case Const.LIST_LOADMORE:
			return getDataList(null, null, (int) objs[0], 10);
		case Const.LIST_DELETE:
			deleteItemInThread((T) objs[0]);
			return objs[0];
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result) {
			switch (operate) {
			case Const.LIST_REFRESH:
			case Const.LIST_LOADMORE:
				updateListView(result, operate, (List<T>) obj);
				break;
			case Const.LIST_DELETE:
				updateDeleteItem((T) obj);
				break;
			}
		}
		return false;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id) {
		AlertDialog dialog = new AlertDialog.Builder(this.context)
				.setItems(new String[] { "删除", "取消" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							T item = dataList.get(position - listView.getHeaderViewsCount());
							if (deleteType == DeleteType.DELETE) {
								if (deleteItem(item)) {
									updateDeleteItem(item);
								}
							} else if (deleteType == DeleteType.DELETEINTHREAD) {
								MyProgress.show(R.string.prg_sending, BaseListFragment.this.context);
								new CsqRunnable(Const.LIST_DELETE, item).start();
								;
							}
						}
					}
				}).show();
		return true;
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// view = inflater.inflate(R.layout.my_extended_list_fragment,
		// container, false);
		View view = inflater.inflate(R.layout.my_extended_list_fragment, container, false);
		initView(view);
		return view;
	}

}
