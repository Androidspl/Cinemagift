package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFunding;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我参与的众筹接口
 * @author min.yuan
 *
 */
public interface MyCollectMoneyCom {
	/**
	 * 我参与的众筹列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<CrowdFunding> getMyCollectMoneyList(String User_id,String Page, String Page_size) throws CsqException;
}
