package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.Recommend;
import com.skyfilm.owner.bean.mine.Topic;
import com.skyfilm.owner.exception.CsqException;

/**
 * 推荐接口
 * @author min.yuan
 *
 */
public interface RecommendCom {
	/**
	 * 获取推荐信息
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	List<Recommend> getRecommendList(String User_id,String Page,String Page_Size)throws CsqException;
	void saveRecommend2Cache(String User_id,List<Recommend> recommend);
	List<Recommend> getRecommendFromCache(String userId);
}
