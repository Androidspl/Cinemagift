package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.Notice;
import com.skyfilm.owner.exception.CsqException;

/**
 * 消息接口
 * @author min.yuan
 *
 */
public interface NoticeCom {
	/**
	 * 获取消息列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<Notice> getNoticeList(String User_id,String Page,String Page_size)throws CsqException;
}
