package com.skyfilm.owner.bean;

/**
 * 标签商品
 * 
 * @author lei
 *
 */
public class Tag {
	public String tag_name;
	public String id;
	public String homepage_recommend;
	public String status;
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHomepage_recommend() {
		return homepage_recommend;
	}
	public void setHomepage_recommend(String homepage_recommend) {
		this.homepage_recommend = homepage_recommend;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
