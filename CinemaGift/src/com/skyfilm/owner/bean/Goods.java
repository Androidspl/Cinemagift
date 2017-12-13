package com.skyfilm.owner.bean;

import java.util.List;

/**
 * 标签商品
 * 
 * @author lei
 *
 */
public class Goods {
	public String shopping_guide;
	public String goods_name;
	public String type;
	public String create_time;
	public double goods_price;
	public String movie_subject_id;
	public String goods_desc;
	public String designer_id;
	public String brand_id;
	public String goods_tag_id;
	public String pic_pass;
	public String admin_id;
	public String goods_type_id;
	public String id;
	public String goods_stock;
	public String status;
	public String service_notice;
	
	public Goods(String pic_pass, String goods_desc, double goods_price) {

		this.pic_pass = pic_pass;
		this.goods_desc = goods_desc;
		this.goods_price = goods_price;
	}

	public String getShopping_guide() {
		return shopping_guide;
	}

	public void setShopping_guide(String shopping_guide) {
		this.shopping_guide = shopping_guide;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}

	public String getMovie_subject_id() {
		return movie_subject_id;
	}

	public void setMovie_subject_id(String movie_subject_id) {
		this.movie_subject_id = movie_subject_id;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getDesigner_id() {
		return designer_id;
	}

	public void setDesigner_id(String designer_id) {
		this.designer_id = designer_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getGoods_tag_id() {
		return goods_tag_id;
	}

	public void setGoods_tag_id(String goods_tag_id) {
		this.goods_tag_id = goods_tag_id;
	}

	public String getPic_pass() {
		return pic_pass;
	}

	public void setPic_pass(String pic_pass) {
		this.pic_pass = pic_pass;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getGoods_type_id() {
		return goods_type_id;
	}

	public void setGoods_type_id(String goods_type_id) {
		this.goods_type_id = goods_type_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_stock() {
		return goods_stock;
	}

	public void setGoods_stock(String goods_stock) {
		this.goods_stock = goods_stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getService_notice() {
		return service_notice;
	}

	public void setService_notice(String service_notice) {
		this.service_notice = service_notice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Goods [goods_name=" + goods_name + ", type=" + type + "]";
	}
	
	
}
