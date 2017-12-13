package com.skyfilm.owner.communication;

import java.util.List;

import com.skyfilm.owner.bean.CrowdFundingEntity;
import com.skyfilm.owner.exception.CsqException;

/**
 * 众筹管理接口
 * 
 * @author min.yuan
 *
 */
public interface CrowdFundCom {
	/**
	 * 返回众筹的banner信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<CrowdFundingEntity> getCrowdFundBanner() throws CsqException;
	
	/**
	 * 获取众筹首页
	 * 
	 * @param Type
	 * @return
	 * @throws CsqException
	 */
	List<CrowdFundingEntity> getCrowdFunding(String User_id, String Page, String Page_Size) throws CsqException;
}
