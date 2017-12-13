package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFundingGoods;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.MyCollect;
import com.skyfilm.owner.bean.mine.MyLove;
import com.skyfilm.owner.bean.mine.Notice;
import com.skyfilm.owner.bean.mine.Onsell;
import com.skyfilm.owner.bean.mine.UserInfo;
import com.skyfilm.owner.exception.CsqException;

/**
 * 我的接口
 * @author min.yuan
 *
 */
public interface UserInfoCom {
//User_Info,Edit_Profile，Notice_List
	/**
	 * 获取用户信息
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	UserInfo getUserInfo(String User_id) throws CsqException;
	/**
	 * 编辑个人资料
	 * @param User_id
	 * @param User_name
	 * @param Files
	 * @return
	 * @throws CsqException
	 */
	String editProfile(String User_id,String User_name,String Files) throws CsqException;
	
	//My_Like,My_Collection,My_Follow,Cancel_Follow
	//My_Goods,My_Collect_Goods
	
}
