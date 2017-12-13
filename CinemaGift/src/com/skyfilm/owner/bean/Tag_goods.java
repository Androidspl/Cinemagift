package com.skyfilm.owner.bean;

import java.util.List;

/**
 * 标签商品
 * 
 * @author lei
 *
 */
public class Tag_goods {
	public String tag_name;
	public List<Goods> goods;

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
}
