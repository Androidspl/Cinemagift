package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我的关注接口
 * @author min.yuan
 *
 */
public interface MyAttentionCom {
	/**
	 * 我的关注列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<MyAttention> getMyAttentionList(String User_id,String Page,String Page_size)throws CsqException;
	/**
	 * 取消关注
	 * @param User_id
	 * @param Follow_user_id
	 * @return
	 * @throws CsqException
	 */
	String cancelAttention(String User_id,String Follow_user_id) throws CsqException;
}
