package com.skyfilm.owner.myinterface;

/**
 * 复选框接口
 * 
 * 
 */
public interface CheckInterface {
	/**
	 * 选框状态改变时触发的事件
	 * 
	 * @param position
	 *            元素位置
	 * @param isChecked
	 *            元素选中与否
	 */
	public void checkGoods(int position, boolean isChecked);
}
