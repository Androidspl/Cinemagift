package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.ProductInfo;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.bean.mine.MyBanner;
import com.skyfilm.owner.communication.StoreCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class StoreBiz {
	private StoreCom storeCom;

	public StoreBiz() {
		this.storeCom = (StoreCom) CsqManger.getInstance().get(Type.STORECOM);
	}
	
	/**
	 * 返回商城的banner信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<StoreEntity> getStoreBanner() throws CsqException {
		return storeCom.getStoreBanner();
	}
	
	/**
	 * 返回商城信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public StoreEntity getStoreEntity(String type) throws CsqException {
		return storeCom.getStroeIndex(type);
	}
	
	/**
	 * 返回购物车信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public StoreEntity getCarList(String User_id) throws CsqException {
		return storeCom.getCarList(User_id);
	}
	
	/**
	 * 绑定订单地址
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public Boolean bindOrderAddress(String Address, String Order_id) throws CsqException {
		return storeCom.bindOrderAddress(Address, Order_id);
	}

}
