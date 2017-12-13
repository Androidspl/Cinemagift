package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.Onsell;
import com.skyfilm.owner.exception.CsqException;

/**
 * 在售商品接口
 * @author min.yuan
 *
 */
public interface OnsellCom {
	/**
	 * 我的在售商品
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<Onsell> getOnsellList(String User_id,String Page,String Page_size)throws CsqException;
}
