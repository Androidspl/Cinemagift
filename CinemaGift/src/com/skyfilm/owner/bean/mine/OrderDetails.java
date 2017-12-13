package com.skyfilm.owner.bean.mine;

import java.util.List;

/**
 * 订单详情
 * 
 * @author min.yuan
 *
 */
public class OrderDetails {

	private MyAddress useraddr;// 地址
	private List<Goods> standard;// 商品
	private String money;// "订单总价格"
	
	public MyAddress getUseraddr() {
		return useraddr;
	}

	public void setUseraddr(MyAddress useraddr) {
		this.useraddr = useraddr;
	}

	public List<Goods> getStandard() {
		return standard;
	}

	public void setStandard(List<Goods> standard) {
		this.standard = standard;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public class Goods{
		private String status;
		private String id;// "规格id"
		private String standard_name;// "规格名称"
		private String goods_price;// "价格"
		private String goods_id;// "商品id"
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getStandard_name() {
			return standard_name;
		}
		public void setStandard_name(String standard_name) {
			this.standard_name = standard_name;
		}
		public String getGoods_price() {
			return goods_price;
		}
		public void setGoods_price(String goods_price) {
			this.goods_price = goods_price;
		}
		public String getGoods_id() {
			return goods_id;
		}
		public void setGoods_id(String goods_id) {
			this.goods_id = goods_id;
		}
		
	}
	
}
