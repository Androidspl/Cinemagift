package com.skyfilm.owner.myinterface;

import android.view.View;

/**
 * 改变数量的接口
 * 
 * 
 */

public interface ModifyCountInterface {

	/**
	 * 增加操作
	 * 
	 * @param groupPosition
	 *            组元素位置
	 * @param childPosition
	 *            子元素位置
	 * @param showCountView
	 *            用于展示变化后数量的View
	 * @param isChecked
	 *            子元素选中与否
	 */
	public void doIncrease(int position, View showCountView, boolean isChecked);

	/**
	 * 删减操作
	 * 
	 * @param groupPosition
	 *            组元素位置
	 * @param childPosition
	 *            子元素位置
	 * @param showCountView
	 *            用于展示变化后数量的View
	 * @param isChecked
	 *            子元素选中与否
	 */
	public void doDecrease(int position, View showCountView, boolean isChecked);

}
