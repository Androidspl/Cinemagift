package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.BecomeStylist;
import com.skyfilm.owner.bean.mine.DesignType;
import com.skyfilm.owner.exception.CsqException;

/**
 * 成为设计师接口
 * 
 * @author min.yuan
 *
 */
public interface BecomeStylistCom {
	// Apply_Designer，Get_Design_Type
	/**
	 * 成为设计师
	 * @param User_id
	 * @param becomeStylist
	 * @return
	 * @throws CsqException
	 */
	String applyDesigner(String User_id, BecomeStylist becomeStylist) throws CsqException;

	/**
	 * 获取设计方向
	 * 
	 * @param User_id
	 * @param Type
	 * @return
	 * @throws CsqException
	 */
	List<DesignType> getDesignType() throws CsqException;
}
