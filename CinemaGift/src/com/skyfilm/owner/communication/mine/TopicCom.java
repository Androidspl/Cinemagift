package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.exception.CsqException;

/**
 * 专题接口
 * @author min.yuan
 *
 */
public interface TopicCom {
	/**
	 * 获取专题信息
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	List<Topic> getTopicList(String User_id,String Page,String Page_Size)throws CsqException;
	void saveTopic2Cache(String User_id,List<Topic> topic);
	List<Topic> getTopicFromCache(String userId);
}
