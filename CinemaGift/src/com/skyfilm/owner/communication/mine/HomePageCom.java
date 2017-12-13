package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.exception.CsqException;


/**
 * 首页接口
 * @author min.yuan
 *
 */
public interface HomePageCom {
	/**
	 * 获取首页信息
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	//Home_Page,News_Load,Home_Topic,Home_Recommend,User_Praise
	HomePage getHomePage(String User_id,String Page,String Page_Size)throws CsqException;
	/**
	 * 点赞
	 * @param Type 1:资讯详情2：电影主题详情
	 * @param id 当前产品id
	 * @return
	 * @throws CsqException
	 */
	String praise(String User_id,int Type,String id)throws CsqException;
	void saveHomePage2Cache(String User_id,HomePage HomePage);
	HomePage getHomePageFromCache(String userId);
}
