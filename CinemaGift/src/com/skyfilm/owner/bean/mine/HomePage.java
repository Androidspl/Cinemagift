package com.skyfilm.owner.bean.mine;

import java.util.List;

/**
 * 首页实体类
 * 
 * @author min.yuan
 *
 */
public class HomePage {
	private List<MyBanner> bannerList;
	private List<Information> infoList;


	public List<Information> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Information> infoList) {
		this.infoList = infoList;
	}

	public List<MyBanner> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<MyBanner> bannerList) {
		this.bannerList = bannerList;
	}

}
