package com.skyfilm.owner.bean;

import java.util.List;

/**
 * 标签商品
 * 
 * @author lei
 *
 */
public class Type_goods {
	public String type_name;
	public List<Goods> goods;

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	@Override
	public String toString() {
		return "Type_goods [type_name=" + type_name + ", goods=" + goods + "]";
	}
	
}
