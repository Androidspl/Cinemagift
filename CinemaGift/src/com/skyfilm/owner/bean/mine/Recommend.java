package com.skyfilm.owner.bean.mine;

import java.util.List;

/**
 * 推荐
 * @author min.yuan
 *
 */
public class Recommend {
//	"designer_name": "设计师名", 
//    "designer_pic": "设计师头像", 
//    "desc": "描述", 
//    "goods_sum": "商品数量", 
//    "focus_sum": "关注量", 
//    "person_homepage": "个人主页", 
//    "is_follow": "是否关注，1已关注 2未关注", 
//    "goods_sale": [
//        {
//            "goods_name": "商品名", 
//            "goods_pic": "商品图片url", 
//            "goods_url": "商品链接"
//        }
//        {
//            "designer_name": "设计师名", 
//            "designer_pic": "设计师头像", 
//            "desc": "描述", 
//            "goods_sum": "商品数量", 
//            "focus_sum": "关注量", 
//            "person_homepage": "个人主页", 
//            "is_follow": "是否关注，1已关注 2未关注", 
//            "goods_sale": [
//                {
//                    "goods_name": "商品名", 
//                    "goods_pic": "商品图片url", 
//                    "goods_url": "商品链接"
//                }
	private String designer_name;
	private String designer_pic;
	private String desc;
	private String goods_sum;
	private String focus_sum;
	private String person_homepage;
	private String is_follow;
	private List<MyGoods> goods_sale;
	
	
	public String getDesigner_name() {
		return designer_name;
	}


	public void setDesigner_name(String designer_name) {
		this.designer_name = designer_name;
	}


	public String getDesigner_pic() {
		return designer_pic;
	}


	public void setDesigner_pic(String designer_pic) {
		this.designer_pic = designer_pic;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getGoods_sum() {
		return goods_sum;
	}


	public void setGoods_sum(String goods_sum) {
		this.goods_sum = goods_sum;
	}


	public String getFocus_sum() {
		return focus_sum;
	}


	public void setFocus_sum(String focus_sum) {
		this.focus_sum = focus_sum;
	}


	public String getPerson_homepage() {
		return person_homepage;
	}


	public void setPerson_homepage(String person_homepage) {
		this.person_homepage = person_homepage;
	}


	public String getIs_follow() {
		return is_follow;
	}


	public void setIs_follow(String is_follow) {
		this.is_follow = is_follow;
	}


	public List<MyGoods> getGoods_sale() {
		return goods_sale;
	}


	public void setGoods_sale(List<MyGoods> goods_sale) {
		this.goods_sale = goods_sale;
	}


	public class MyGoods{
		private String goods_name;
		private String goods_pic;
		private String goods_url;
		public String getGoods_name() {
			return goods_name;
		}
		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}
		public String getGoods_pic() {
			return goods_pic;
		}
		public void setGoods_pic(String goods_pic) {
			this.goods_pic = goods_pic;
		}
		public String getGoods_url() {
			return goods_url;
		}
		public void setGoods_url(String goods_url) {
			this.goods_url = goods_url;
		}
		
	}
}
