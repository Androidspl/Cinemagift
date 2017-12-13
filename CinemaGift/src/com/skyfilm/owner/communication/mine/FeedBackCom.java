package com.skyfilm.owner.communication.mine;

import com.skyfilm.owner.exception.CsqException;

/**
 * 反馈信息接口
 * @author min.yuan
 *
 */
public interface FeedBackCom {
	//Feed_Back
	String feedBack(String User_id,String Content) throws CsqException;
}
