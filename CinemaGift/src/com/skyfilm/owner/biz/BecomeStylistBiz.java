package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.mine.BecomeStylist;
import com.skyfilm.owner.bean.mine.DesignType;
import com.skyfilm.owner.communication.mine.BecomeStylistCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class BecomeStylistBiz {
	private BecomeStylistCom becomeStylistCom;

	public BecomeStylistBiz() {
		becomeStylistCom = (BecomeStylistCom) CsqManger.getInstance().get(Type.BECOMESTYLISTCOM);
	}

	/**
	 * 申请成为设计师
	 * 
	 * @param User_id
	 * @param becomeStylist
	 * @return
	 * @throws CsqException
	 */
	public String applyDesigner(String User_id, BecomeStylist becomeStylist) throws CsqException {
		return becomeStylistCom.applyDesigner(User_id, becomeStylist);
	}

	/**
	 * 返回设计方向
	 * 
	 * @param User_id
	 * @param Type
	 * @return
	 * @throws CsqException
	 */
	public List<DesignType> getDesignType() throws CsqException {
		return becomeStylistCom.getDesignType();
	}
}
