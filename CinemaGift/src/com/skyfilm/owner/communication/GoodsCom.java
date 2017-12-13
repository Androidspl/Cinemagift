package com.skyfilm.owner.communication;

import java.util.List;

import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.exception.CsqException;

/**
 * 商城管理接口
 * 
 * @author min.yuan
 *
 */
public interface GoodsCom {
	
	/**
	 * 返回搜索商品信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Goods> getSearchGoods(String Type, String Keyword) throws CsqException;
	
	
}
