package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.CrowdFundingEntity;
import com.skyfilm.owner.communication.CrowdFundCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class CrowdFundBiz {
	private CrowdFundCom crowdfundCom;

	public CrowdFundBiz() {
		this.crowdfundCom = (CrowdFundCom) CsqManger.getInstance().get(Type.CROWDFUNDCOM);
	}
	
	/**
	 * 返回众筹的banner信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<CrowdFundingEntity> getCrowdFundBanner() throws CsqException {
		return crowdfundCom.getCrowdFundBanner();
	}
	
	/**
	 * 返回众筹信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<CrowdFundingEntity> getCrowdFunding(String User_id, String Page, String Page_Size) throws CsqException {
		return crowdfundCom.getCrowdFunding(User_id, Page, Page_Size);
	}

}
