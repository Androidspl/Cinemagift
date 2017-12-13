package com.skyfilm.owner.communication;

import java.util.List;

import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.bean.mine.MyBanner;
import com.skyfilm.owner.exception.CsqException;

/**
 * 商城管理接口
 * 
 * @author min.yuan
 *
 */
public interface StoreCom {
	/**
	 * 返回商城的banner信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<StoreEntity> getStoreBanner() throws CsqException;
	
	/**
	 * 获取商城首页
	 * 
	 * @param Type
	 * @return
	 * @throws CsqException
	 */
	StoreEntity getStroeIndex(String type) throws CsqException;
	/**
	 * 返回购物车信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public StoreEntity getCarList(String User_id) throws CsqException;
	
	/**
	 * 绑定订单地址
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public Boolean bindOrderAddress(String Address, String Order_id) throws CsqException;
	
}
