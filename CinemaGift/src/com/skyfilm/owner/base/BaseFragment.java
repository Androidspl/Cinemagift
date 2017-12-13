package com.skyfilm.owner.base;

import java.lang.reflect.Field;

import com.skyfilm.owner.base.BaseActivity.ScanResultListener;
import com.skyfilm.owner.biz.StoreBiz;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements ScanResultListener {

	private View rootView;
//	protected StoreBiz storeBiz;

	protected void setTitle(CharSequence title) {
		if (getActivity() != null) {
			getActivity().setTitle(title);
			setTitleImage(-1, -1, -1, -1);
		}
	}

	protected void setTitleImage(int leftRes, int topRes, int rightRes, int bottomRes) {
		if (getActivity() != null) {
			((BaseActivity) getActivity()).setTitleImage(leftRes, topRes, rightRes, bottomRes);
		}
	}

	protected void setTitleTextColor(int color) {
		((BaseActivity) getActivity()).setCustomTitleTextcolor(color);
	}

	protected void setTitleBackground(int color) {
		((BaseActivity) getActivity()).setCustomTitleBackgroundColor(color);
	}

	protected void hideLeftView() {
		((BaseActivity) getActivity()).hideLeftView();
	}

	protected void hideRightView1() {
		((BaseActivity) getActivity()).hideRightView1();
	}

	protected void hideRightView2() {
		((BaseActivity) getActivity()).hideRightView2();
	}

	protected void showLeftView() {
		((BaseActivity) getActivity()).showLeftView();
	}

	protected void showRightView1() {
		((BaseActivity) getActivity()).showRightView1();
	}

	protected void showRightView2() {
		((BaseActivity) getActivity()).showRightView2();
	}

	protected void initRightView1(int resId) {
		((BaseActivity) getActivity()).initRightView1(resId);
	}

	protected void initRightView2(int resId) {
		((BaseActivity) getActivity()).initRightView2(resId);
	}

	protected void initLeftView(int resId) {
		((BaseActivity) getActivity()).initLeftView(resId);
	}

	protected View getLeftView() {
		return ((BaseActivity) getActivity()).getLeftView();
	}

	protected View getRightView1() {
		return ((BaseActivity) getActivity()).getRightView1();
	}

	protected View getRightView2() {
		return ((BaseActivity) getActivity()).getRightView2();
	}

	// protected void startScan () {
	// ((BaseActivity)getActivity()).startScan();
	// ((BaseActivity)getActivity()).setScanResultListener(this);;
	// }
	public boolean resultForScan(String result) {
		return false;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (null != rootView) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		} else {
			rootView = initView(inflater, container, savedInstanceState);// 控件初始化
		}
		// userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
		return rootView;
	}

	/**
	 * 初始化控件
	 * 
	 * @return
	 */
	protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//		storeBiz = (StoreBiz) CsqManger.getInstance().get(Type.STOREBIZ);
		if (savedInstanceState != null) {
			savedInstanceState.remove("android:support:fragments");
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	public boolean keyBackPressed() {
		return false;
	}

	/**
	 * 显示对应的fragment
	 * 
	 * @param fragment
	 *            对象
	 * @param id
	 *            对象的id
	 * @param dstId
	 *            容器的id
	 */
	protected final void show(Fragment fragment, int id, int dstId) {
		FragmentManager manger = getChildFragmentManager();
		FragmentTransaction transaction = manger.beginTransaction();
		Fragment fragmentOld = manger.findFragmentByTag(id + "");
		if (fragmentOld != null) {
			transaction.remove(fragmentOld);
		}
		transaction.replace(dstId, fragment, id + "");
		try {
			transaction.commitAllowingStateLoss();
			transaction.show(fragment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onDetach() {

		super.onDetach();

		try {

			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");

			childFragmentManager.setAccessible(true);

			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {

			// throw new RuntimeException(e);

		} catch (IllegalAccessException e) {

			// throw new RuntimeException(e);

		}

	}

}
