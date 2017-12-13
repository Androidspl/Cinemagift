package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFundingGoods;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我的众筹产品接口
 * @author min.yuan
 *
 */
public interface CrowdFundingGoodsCom {
	/**
	 * 我的众筹商品
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<CrowdFundingGoods> getMyCollectGoodsList(String User_id,String Page,String Page_size)throws CsqException;
}
