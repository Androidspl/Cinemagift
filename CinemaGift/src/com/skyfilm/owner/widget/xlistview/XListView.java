package com.skyfilm.owner.widget.xlistview;

import com.skyfilm.owner.R;
import com.skyfilm.owner.utils.DensityUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class XListView extends ListView implements OnScrollListener {

	private static final String TAG = "XListView";

	public static interface OnPositionChangedListener {
		public void onPositionChanged(XListView listView, int position, View scrollBarPanel);

		public void onScollPositionChanged(View scrollBarPanel, int top);

		public void onScollerChanged(AbsListView view, XListView xListView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
	}

	/**
	 * 刷新控件
	 */
	private float mLastY = -1; // save event y
	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener
	// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;
	//private IXListViewListener mListViewListener;

	// -- header view
	private XListViewHeader mHeaderView;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	private TextView mHeaderTimeView;
	private int mHeaderViewHeight; // header view's height
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // is refreashing.

	// -- footer view
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;

	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 200; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
	// at bottom, trigger
	// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
	// feature.
	// ---------------------------------------------------------------------------------->end
	// of refresh
	private OnScrollListener mOnScrollListener = null;

	private View mScrollBarPanel = null;

	private int mScrollBarPanelPosition = 0;

	private OnPositionChangedListener mPositionChangedListener;

	private int mLastPosition = -1;

	private Animation mInAnimation = null;
	private Animation mOutAnimation = null;

	private final Handler mHandler = new Handler();

	private final Runnable mScrollBarPanelFadeRunnable = new Runnable() {
		@Override
		public void run() {
			if (mOutAnimation != null) {
				mScrollBarPanel.startAnimation(mOutAnimation);
			}
		}
	};

	/*
	 * keep track of Measure Spec
	 */
	private int mWidthMeasureSpec;
	private int mHeightMeasureSpec;

	public XListView(Context context) {
		this(context, null);
	}

	public XListView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.listViewStyle);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XListView);
		final int scrollBarPanelLayoutId = a.getResourceId(R.styleable.XListView_scrollBarPanel, -1);
		final int scrollBarPanelInAnimation = a.getResourceId(R.styleable.XListView_scrollBarPanelInAnimation, -1);
		final int scrollBarPanelOutAnimation = a.getResourceId(R.styleable.XListView_scrollBarPanelOutAnimation,-1);
		a.recycle();

		if (scrollBarPanelLayoutId != -1) {
			setScrollBarPanel(scrollBarPanelLayoutId);
		}

		final int scrollBarPanelFadeDuration = ViewConfiguration.getScrollBarFadeDuration();

		if (scrollBarPanelInAnimation > 0) {
			mInAnimation = AnimationUtils.loadAnimation(getContext(), scrollBarPanelInAnimation);
		}

		if (scrollBarPanelOutAnimation > 0) {
			mOutAnimation = AnimationUtils.loadAnimation(getContext(), scrollBarPanelOutAnimation);
			mOutAnimation.setDuration(scrollBarPanelFadeDuration);

			mOutAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (mScrollBarPanel != null) {
						mScrollBarPanel.setVisibility(View.GONE);
					}
				}
			});
		}
		initRefreshPanel(context);
	}

	
	private void initRefreshPanel(Context context) {
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		// add header view
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new XListViewFooter(context);

		// init header height
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
/*
				if (isAutoRefresh) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					mScrollBack = SCROLLBACK_HEADER;
					mScroller.startScroll(0, 0, 0, mHeaderViewHeight,
							SCROLL_DURATION);
					invalidate();
				}*/
			}
		});
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.setVisibility(View.GONE);
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.setVisibility(View.VISIBLE);
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	public void startRefresh(boolean fromout){
		mPullRefreshing = true;
		mHeaderView.setState(XListViewHeader.STATE_REFRESHING);	
		if (mListViewListener != null) {
			mListViewListener.onRefresh();
		}

		if(fromout){
			updateHeaderHeight(mHeaderViewHeight> 0?mHeaderViewHeight: 150f);
		}
	}
	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		// if (mPullRefreshing == true) {
		mPullRefreshing = false;
		mHeaderView.setState(XListViewHeader.STATE_NORMAL);
		resetHeaderHeight();
		// }
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		// if (mPullLoading == true) {
		mPullLoading = false;
		mFooterView.setState(XListViewFooter.STATE_NORMAL);
		mFooterView.normal();
		this.setPullLoadEnable(false);
		// }
	}

	/**
	 * set last refresh time
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	private void invokeOnScrolling() {
		if (mOnScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mOnScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		// if (height == 0) {// not visible.
		// return;
		// }
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height != mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - (height+DensityUtil.dip2px(getContext(), 1)), SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
				// more.
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		//setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// MCLog.i(TAG, "onScrollStateChanged");
		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// MCLog.i(TAG, "onScroll");
		mTotalItemCount = totalItemCount;
		if (null != mPositionChangedListener) {

			// Don't do anything if there is no itemviews
			if (totalItemCount > 0) {
				/*
				 * from android source code (ScrollBarDrawable.java)
				 */
				final int thickness = getVerticalScrollbarWidth();
				int height = Math.round((float) getMeasuredHeight() * computeVerticalScrollExtent() / computeVerticalScrollRange());
				int thumbOffset = Math.round((float) (getMeasuredHeight() - height) * computeVerticalScrollOffset() / (computeVerticalScrollRange() - computeVerticalScrollExtent()));
				final int minLength = thickness * 2;
				if (height < minLength) {
					height = minLength;
				}
				thumbOffset += height / 2;

				if (mOnScrollListener != null) {
					mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				}
				/*
				 * find out which itemviews the center of thumb is on
				 */
				final int count = getChildCount();
				for (int i = 0; i < count; ++i) {
					final View childView = getChildAt(i);
					if (childView != null) {
						if (thumbOffset > childView.getTop() && thumbOffset < childView.getBottom()) {
							/*
							 * we have our candidate
							 */
							if (mLastPosition != firstVisibleItem + i) {
								mLastPosition = firstVisibleItem + i;

								/*
								 * inform the position of the panel has changed
								 */
								mPositionChangedListener.onPositionChanged(this, mLastPosition, null);

								/*
								 * measure panel right now since it has just
								 * changed INFO: quick hack to handle TextView
								 * has ScrollBarPanel (to wrap text in case
								 * TextView's content has changed)
								 */
								// measureChild(mScrollBarPanel,
								// mWidthMeasureSpec, mHeightMeasureSpec);
							}
							break;
						}
					}
				}

				/*
				 * update panel position
				 */
				// mScrollBarPanelPosition = thumbOffset -
				// mScrollBarPanel.getMeasuredHeight() / 2;
				// final int x = getMeasuredWidth() -
				// mScrollBarPanel.getMeasuredWidth()
				// - getVerticalScrollbarWidth();
				// System.out.println("left==" + x + " top==" +
				// mScrollBarPanelPosition + " bottom=="
				// + (x + mScrollBarPanel.getMeasuredWidth()) + " right=="
				// + (mScrollBarPanelPosition +
				// mScrollBarPanel.getMeasuredHeight()));
				// mScrollBarPanel.layout(x, mScrollBarPanelPosition,
				// x + mScrollBarPanel.getMeasuredWidth(),
				// mScrollBarPanelPosition
				// + mScrollBarPanel.getMeasuredHeight());
				mPositionChangedListener.onScollPositionChanged(this, thumbOffset);
				mPositionChangedListener.onScollerChanged(view, this, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}

		/*
		 * 处理刷新滚动事件
		 */
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setOnPositionChangedListener(OnPositionChangedListener onPositionChangedListener) {
		mPositionChangedListener = onPositionChangedListener;
	}

	@Override
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		mOnScrollListener = onScrollListener;
	}

	public void setScrollBarPanel(View scrollBarPanel) {
		mScrollBarPanel = scrollBarPanel;
		mScrollBarPanel.setVisibility(View.GONE);
		requestLayout();
	}

	public void setScrollBarPanel(int resId) {
		setScrollBarPanel(LayoutInflater.from(getContext()).inflate(resId, this, false));
	}

	// public View getScrollBarPanel() {
	// return mScrollBarPanel;
	// }

	@Override
	protected boolean awakenScrollBars(int startDelay, boolean invalidate) {
		final boolean isAnimationPlayed = super.awakenScrollBars(startDelay, invalidate);

		if (isAnimationPlayed == true && mScrollBarPanel != null) {
			if (mScrollBarPanel.getVisibility() == View.GONE) {
				mScrollBarPanel.setVisibility(View.VISIBLE);
				if (mInAnimation != null) {
					mScrollBarPanel.startAnimation(mInAnimation);
				}
			}

			mHandler.removeCallbacks(mScrollBarPanelFadeRunnable);
			mHandler.postAtTime(mScrollBarPanelFadeRunnable,
					AnimationUtils.currentAnimationTimeMillis() + startDelay);
		}

		return isAnimationPlayed;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// if (mScrollBarPanel != null && getAdapter() != null) {
		// mWidthMeasureSpec = widthMeasureSpec;
		// mHeightMeasureSpec = heightMeasureSpec;
		//
		// measureChild(mScrollBarPanel, widthMeasureSpec, heightMeasureSpec);
		// }
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// if (mScrollBarPanel != null) {
		// final int x = getMeasuredWidth() - mScrollBarPanel.getMeasuredWidth()
		// - getVerticalScrollbarWidth();
		// mScrollBarPanel.layout(x, mScrollBarPanelPosition,
		// x + mScrollBarPanel.getMeasuredWidth(), mScrollBarPanelPosition
		// + mScrollBarPanel.getMeasuredHeight());
		// }

	}

	// @Override
	// protected void dispatchDraw(Canvas canvas) {
	// super.dispatchDraw(canvas);
	// }

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mHandler.removeCallbacks(mScrollBarPanelFadeRunnable);
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * 下拉刷新事件重载处理方法
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}



	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (!mPullRefreshing) {
				if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
					// the first item is showing, header has shown or pull down.
					updateHeaderHeight(deltaY / OFFSET_RADIO);
					invokeOnScrolling();
				} else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
					// last item, already pulled up or want to pull up.
					updateFooterHeight(-deltaY / OFFSET_RADIO);
				}
			}
			break;
		default:
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					startRefresh(false);
				}
				resetHeaderHeight();
			}
			if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}

	/**
	 * @param context
	 */
	public void showHeaderView(Context context) {

		mPullRefreshing = true;
		mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
		mScrollBack = SCROLLBACK_HEADER;
		int distance = 0;
		if(mHeaderViewHeight<=0){
			distance = DensityUtil.dip2px(context, 60);
		}else{
			distance = mHeaderViewHeight;
		}
		mScroller.startScroll(0, 0, 0, distance, SCROLL_DURATION);
		invalidate();
	}

}
