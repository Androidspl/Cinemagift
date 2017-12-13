package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.MyLove;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我的喜欢接口
 * @author min.yuan
 *
 */
public interface MyLoveCom {
	/**
	 * 我的喜欢列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<MyLove> getMyLoveList(String User_id,String Page,String Page_size)throws CsqException;
}
