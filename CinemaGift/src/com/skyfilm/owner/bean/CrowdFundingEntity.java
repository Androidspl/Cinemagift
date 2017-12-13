package com.skyfilm.owner.bean;

import java.util.List;

import com.skyfilm.owner.bean.mine.MyBanner;

/**
 * 商场
 * @author lei
 *
 */
public class CrowdFundingEntity {

	/** 广告轮播图 */
	public List<MyBanner> mybanner;
	private String id;
	private String banner_title;
	private String img_url;
	private String link_url;
	/** 众筹列表 */
	public String pic_pass  ;
	public int count  ;
	public int progress  ;
	public String crowdfund_name  ;
	public String designer  ;
	public String brand  ;
	public int residual_time  ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBanner_title() {
		return banner_title;
	}

	public void setBanner_title(String banner_title) {
		this.banner_title = banner_title;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public List<MyBanner> getMybanner() {
		return mybanner;
	}

	public void setMybanner(List<MyBanner> mybanner) {
		this.mybanner = mybanner;
	}

	public String getPic_pass() {
		return pic_pass;
	}

	public void setPic_pass(String pic_pass) {
		this.pic_pass = pic_pass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getCrowdfund_name() {
		return crowdfund_name;
	}

	public void setCrowdfund_name(String crowdfund_name) {
		this.crowdfund_name = crowdfund_name;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getResidual_time() {
		return residual_time;
	}

	public void setResidual_time(int residual_time) {
		this.residual_time = residual_time;
	}
	
}
