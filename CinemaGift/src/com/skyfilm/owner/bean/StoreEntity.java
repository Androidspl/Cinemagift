package com.skyfilm.owner.bean;

import java.util.List;

import com.skyfilm.owner.bean.mine.MyBanner;

/**
 * 商场
 * 
 * @author lei
 *
 */
public class StoreEntity {

	/** 分类商品 */
	public List<Type> type;
	/** 标签商品 */
	public List<Tag> tag;
	/** 分类图片显示三张 */
	public List<Type_goods> type_goods;
	/** 标签图片显示四张 */
	public List<Tag_goods> tag_goods;
	/** 广告轮播图 */
	public List<MyBanner> mybanner;
	private String id;
	private String banner_title;
	private String img_url;
	private String link_url;

	public List<Type> getType() {
		return type;
	}

	public void setType(List<Type> type) {
		this.type = type;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public List<Type_goods> getType_goods() {
		return type_goods;
	}

	public void setType_goods(List<Type_goods> type_goods) {
		this.type_goods = type_goods;
	}

	public List<Tag_goods> getTag_goods() {
		return tag_goods;
	}

	public void setTag_goods(List<Tag_goods> tag_goods) {
		this.tag_goods = tag_goods;
	}

	public List<MyBanner> getMybanner() {
		return mybanner;
	}

	public void setMybanner(List<MyBanner> mybanner) {
		this.mybanner = mybanner;
	}

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

	private String imageUrl;
	private String desc;
	private double price;
	private int count;
	private String name;
	private int position;// 绝对位置，只在ListView构造的购物车中，在删除时有效

	public StoreEntity() {
		super();
	}

	public StoreEntity(String id, String name, String imageUrl, String desc, double price, int count) {

		this.imageUrl = imageUrl;
		this.desc = desc;
		this.price = price;
		this.count = count;
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean isChoosed;

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
	
	

}
