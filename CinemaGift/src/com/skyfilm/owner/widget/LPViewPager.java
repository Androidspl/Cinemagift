package com.skyfilm.owner.widget;

import java.lang.reflect.Field;

import com.skyfilm.owner.adapter.LoopPagerAdapterWrapper;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * ������Ĺ��ͼ��ViewPager
 * ������չ��Viewpager
 * ʵ����ͼƬ��ѭ���ֲ�
 *
 */
public class LPViewPager extends ViewPager {

	private static final boolean DEFAULT_BOUNDARY_CASHING = false;

	OnPageChangeListener mOuterPageChangeListener;
	private LoopPagerAdapterWrapper mAdapter;
	private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
	private int mDuration = 400;

	/**
	 * helper function which may be used when implementing FragmentPagerAdapter
	 *   
	 * @param position
	 * @param count
	 * @return (position-1)%count
	 */
	public static int toRealPosition( int position, int count ){
		position = position-1;
		if( position < 0 ){
			position += count;
		}else{
			position = position%count;
		}
		return position;
	}

	/**
	 * If set to true, the boundary views (i.e. first and last) will never be destroyed
	 * This may help to prevent "blinking" of some views 
	 * 
	 * @param flag
	 */
	public void setBoundaryCaching(boolean flag) {
		mBoundaryCaching = flag;
		if (mAdapter != null) {
			mAdapter.setBoundaryCaching(flag);
		}
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		if(adapter.getCount()>1){
			mAdapter = new LoopPagerAdapterWrapper(adapter);
			mAdapter.setBoundaryCaching(mBoundaryCaching);
			super.setAdapter(mAdapter);
		}else{
			super.setAdapter(adapter);
		}
		setCurrentItem(0, false);
	}

	@Override
	public PagerAdapter getAdapter() {
		return mAdapter != null ? mAdapter.getRealAdapter() : super.getAdapter();
	}

	@Override
	public int getCurrentItem() {
		return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
	}

	public void setCurrentItem(int item, boolean smoothScroll) {
		int realItem = item;
		if(mAdapter != null){
			realItem = mAdapter.toInnerPosition(item);
		}
		super.setCurrentItem(realItem, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		if (getCurrentItem() != item) {
			setCurrentItem(item, true);
		}
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mOuterPageChangeListener = listener;
	};

	public LPViewPager(Context context) {
		super(context);
		init();
	}

	public LPViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		super.setOnPageChangeListener(onPageChangeListener);
	}

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		private float mPreviousOffset = -1;
		private float mPreviousPosition = -1;

		@Override
		public void onPageSelected(int position) {

			int realPosition = mAdapter.toRealPosition(position);
			if (mPreviousPosition != realPosition) {
				mPreviousPosition = realPosition;
				if (mOuterPageChangeListener != null) {
					mOuterPageChangeListener.onPageSelected(realPosition);
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			try {
				int realPosition = position;
				if (mAdapter != null) {
					realPosition = mAdapter.toRealPosition(position);

					if (positionOffset == 0
							&& mPreviousOffset == 0
							&& (position == 0 || position == mAdapter.getCount() - 1)) {
						setCurrentItem(realPosition, false);
					}
				}

				mPreviousOffset = positionOffset;
				if (mOuterPageChangeListener != null) {
					if (realPosition != mAdapter.getRealCount() - 1) {
						mOuterPageChangeListener.onPageScrolled(realPosition,
								positionOffset, positionOffsetPixels);
					} else {
						if (positionOffset > .5) {
							mOuterPageChangeListener.onPageScrolled(0, 0, 0);
						} else {
							mOuterPageChangeListener.onPageScrolled(realPosition,
									0, 0);
						}
					}
				}
			} catch (Exception e) {
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (mAdapter != null) {
				int position = LPViewPager.super.getCurrentItem();
				int realPosition = mAdapter.toRealPosition(position);
				if (state == ViewPager.SCROLL_STATE_IDLE
						&& (position == 0 || position == mAdapter.getCount() - 1)) {
					setCurrentItem(realPosition, false);
				}
			}
			if (mOuterPageChangeListener != null) {
				mOuterPageChangeListener.onPageScrollStateChanged(state);
			}
		}
	};

	private void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {  
		try {  
			Field field = ViewPager.class.getDeclaredField("mScroller");  
			field.setAccessible(true);  
			ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(), new AccelerateInterpolator());  
			field.set(viewPager, viewPagerScroller);  
			viewPagerScroller.setDuration(speed);  
		} catch (NoSuchFieldException e) {  
			e.printStackTrace();  
		} catch (IllegalAccessException e) {  
			e.printStackTrace();  
		}  
	}  

	public class ViewPagerScroller extends Scroller {  
		private int mDuration;  

		public ViewPagerScroller(Context context) {  
			super(context);  
		}  

		public ViewPagerScroller(Context context, Interpolator interpolator) {  
			super(context, interpolator);  
		}  

		public void setDuration(int mDuration) {  
			this.mDuration = mDuration;  
		}  

		@Override  
		public void startScroll(int startX, int startY, int dx, int dy) {  
			super.startScroll(startX, startY, dx, dy, this.mDuration);  
		}  

		@Override  
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {  
			super.startScroll(startX, startY, dx, dy, this.mDuration);  
		}  
	}


	public void setSpeed(int mDuration){
		this.mDuration = mDuration;
		setViewPagerScrollSpeed(this,mDuration);
	}
}
