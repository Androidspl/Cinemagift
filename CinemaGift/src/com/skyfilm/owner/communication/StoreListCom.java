package com.skyfilm.owner.communication;

import java.util.List;

import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.exception.CsqException;

/**
 * 商品列表接口
 * 
 * @author lei
 *
 */
public interface StoreListCom {
	/**
	 * 获取商品列表
	 * 
	 * @param Type
	 * @return
	 * @throws CsqException
	 */
	List<Goods> getStroeList(String type, String tag) throws CsqException;
}
