package com.skyfilm.owner.bean.mine;

/**
 * 专题
 * 
 * @author min.yuan
 *
 */
public class Topic {
	// "topic_title":"喜马拉雅天梯",
	// "topic_img":"upload/movie/ED36BE47-1364-28F6-D969-4B51CCBA8C88/20160721/D693D775-43C8-C303-9919-9989EE7FD038.jpg",
	// "link_url":"http://dylw.test.csq365.com/Home/topicInfo/D693D775-43C8-C303-9919-9989EE7FD038"
	private String topic_title;
	private String topic_img;
	private String link_url;
	public String getTopic_title() {
		return topic_title;
	}
	public void setTopic_title(String topic_title) {
		this.topic_title = topic_title;
	}
	public String getTopic_img() {
		return topic_img;
	}
	public void setTopic_img(String topic_img) {
		this.topic_img = topic_img;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	

}
