package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.communication.mine.HomePageCom;
import com.skyfilm.owner.communication.mine.InformationCom;
import com.skyfilm.owner.communication.mine.RecommendCom;
import com.skyfilm.owner.communication.mine.TopicCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class HomePageBiz {
	private HomePageCom homepageCom;
	private InformationCom informationCom;
	private RecommendCom recommendCom;
	private TopicCom topicCom;

	public HomePageBiz() {
		homepageCom = (HomePageCom) CsqManger.getInstance().get(Type.HOMEPAGECOM);
		informationCom = (InformationCom) CsqManger.getInstance().get(Type.INFORMATIONCOM);
		topicCom = (TopicCom) CsqManger.getInstance().get(Type.TOPICCOM);
		recommendCom = (RecommendCom) CsqManger.getInstance().get(Type.RECOMMENDCOM);
	}

	/**
	 * 返回首页信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public HomePage getHomePage(String User_id, String Page, String Page_Size) throws CsqException {
		HomePage homePage = homepageCom.getHomePage(User_id, Page, Page_Size);
		homepageCom.saveHomePage2Cache("User_id", homePage);
		return homePage;
	}

	/**
	 * 返回资讯信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Information> getInformationList(String User_id, String Page, String Page_Size) throws CsqException {
		return informationCom.getInformationList(User_id, Page, Page_Size);
	}

	/**
	 * 返回专题信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Topic> getTopicList(String User_id, String Page, String Page_Size) throws CsqException {
		List<Topic> topicList = topicCom.getTopicList(User_id, Page, Page_Size);
		if("1".equals(Page)){
			topicCom.saveTopic2Cache("User_id", topicList);
		}
		return topicList;
	}

	/**
	 * 返回推荐信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Recommend> getRecommendList(String User_id, String Page, String Page_Size) throws CsqException {
		List<Recommend> recommendList = recommendCom.getRecommendList(User_id, Page, Page_Size);
		if("1".equals(Page)){
			recommendCom.saveRecommend2Cache("User_id", recommendList);
		}
		return recommendList;
	}

	/**
	 * 点赞
	 * 
	 * @param Type
	 *            1:资讯详情2：电影主题详情
	 * @param id
	 *            当前产品id
	 * @return
	 * @throws CsqException
	 */
	public String praise(String User_id, int Type, String id) throws CsqException {
		return homepageCom.praise(User_id, Type, id);
	}

	/**
	 *从缓存中获取首页信息
	 * @param userId
	 * @return
	 */
	public HomePage getHomePageFromCache(String userId) {
		return homepageCom.getHomePageFromCache(userId);
	}
	
	public List<Topic> getTopicFromCache(String userId) {
		return topicCom.getTopicFromCache(userId);
	}
	public List<Recommend> getRecommendFromCache(String userId) {
		return recommendCom.getRecommendFromCache(userId);
	}
}
