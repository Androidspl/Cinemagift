package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.MyCollect;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我的收藏接口
 * @author min.yuan
 *
 */
public interface MyCollectCom {
	/**
	 * 我的收藏列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<MyCollect> getMyCollectList(String User_id,String Page,String Page_size)throws CsqException;
}
