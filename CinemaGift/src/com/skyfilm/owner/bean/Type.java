package com.skyfilm.owner.bean;

/**
 * 分类商品
 * 
 * @author lei
 *
 */
public class Type {
	public String type_name;
	public String id;
	public String homepage_recommend;
	public String status;

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
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

	@Override
	public String toString() {
		return "Type [type_name=" + type_name + ", id=" + id + ", homepage_recommend=" + homepage_recommend
				+ ", status=" + status + "]";
	}

}
