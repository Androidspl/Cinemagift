package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFundingGoods;
import com.skyfilm.owner.bean.mine.MyAttention;
import com.skyfilm.owner.bean.mine.MyCollect;
import com.skyfilm.owner.bean.mine.MyLove;
import com.skyfilm.owner.bean.mine.Notice;
import com.skyfilm.owner.bean.mine.Onsell;
import com.skyfilm.owner.bean.mine.UserInfo;
import com.skyfilm.owner.communication.mine.CrowdFundingGoodsCom;
import com.skyfilm.owner.communication.mine.MyAttentionCom;
import com.skyfilm.owner.communication.mine.MyCollectCom;
import com.skyfilm.owner.communication.mine.MyLoveCom;
import com.skyfilm.owner.communication.mine.NoticeCom;
import com.skyfilm.owner.communication.mine.OnsellCom;
import com.skyfilm.owner.communication.mine.UserInfoCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class UserInfoBiz {
	private UserInfoCom userInfoCom;
	private NoticeCom noticeCom;
	private MyLoveCom myLoveCom;
	private MyCollectCom myCollectCom;
	private MyAttentionCom myAttentionCom;
	private OnsellCom onsellCom;
	private CrowdFundingGoodsCom crowdFundingGoodsCom;
	public UserInfoBiz() {
		userInfoCom = (UserInfoCom) CsqManger.getInstance().get(Type.USERINFOCOM);
		noticeCom = (NoticeCom) CsqManger.getInstance().get(Type.NOTICECOM);
		myLoveCom = (MyLoveCom) CsqManger.getInstance().get(Type.MYLOVECOM);
		myCollectCom = (MyCollectCom) CsqManger.getInstance().get(Type.MYCOLLECTCOM);
		myAttentionCom = (MyAttentionCom) CsqManger.getInstance().get(Type.MYATTENTIONCOM);
		crowdFundingGoodsCom = (CrowdFundingGoodsCom) CsqManger.getInstance().get(Type.CROWDFUNDINGGOODSCOM);
		onsellCom = (OnsellCom) CsqManger.getInstance().get(Type.ONSELLCOM);
	}
	/**
	 * 获取用户信息
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	public UserInfo getUserInfo(String User_id) throws CsqException{
		return userInfoCom.getUserInfo(User_id);
	}
	/**
	 * 编辑个人信息
	 * @param User_id
	 * @param User_name
	 * @param Files
	 * @return
	 * @throws CsqException
	 */
	public String editProfile(String User_id,String User_name,String Files) throws CsqException{
		return userInfoCom.editProfile(User_id, User_name, Files);
	}
	/**
	 * 获取消息列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<Notice> getNoticeList(String User_id,String Page,String Page_size)throws CsqException{
		return noticeCom.getNoticeList(User_id, Page, Page_size);
	}
	
	/**
	 * 我的喜欢列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<MyLove> getMyLoveList(String User_id,String Page,String Page_size)throws CsqException{
		return myLoveCom.getMyLoveList(User_id, Page, Page_size);
	}
	/**
	 * 我的收藏列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<MyCollect> getMyCollectList(String User_id,String Page,String Page_size)throws CsqException{
		return myCollectCom.getMyCollectList(User_id, Page, Page_size);
	}
	/**
	 * 我的关注列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<MyAttention> getMyAttentionList(String User_id,String Page,String Page_size)throws CsqException{
		return myAttentionCom.getMyAttentionList(User_id, Page, Page_size);
	}
	/**
	 * 在售商品
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<Onsell> getOnsellList(String User_id,String Page,String Page_size)throws CsqException{
		return onsellCom.getOnsellList(User_id, Page, Page_size);
	}
	/**
	 * 众筹商品
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<CrowdFundingGoods> getMyCollectGoodsList(String User_id,String Page,String Page_size)throws CsqException{
		return crowdFundingGoodsCom.getMyCollectGoodsList(User_id, Page, Page_size);
	}
	/**
	 * 取消关注
	 * @param User_id
	 * @param Follow_user_id
	 * @return
	 * @throws CsqException
	 */
	public String cancelAttention(String User_id,String Follow_user_id) throws CsqException{
		return myAttentionCom.cancelAttention(User_id, Follow_user_id);
	}
}
