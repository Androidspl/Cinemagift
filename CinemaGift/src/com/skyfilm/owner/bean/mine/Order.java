package com.skyfilm.owner.bean.mine;

/**
 * 我的订单bean
 * 
 * @author min.yuan
 *
 */
public class Order {
	private String goods_logo;
	private String goods_name;
	private String goods_type;
	private String goods_money;
	private String status;
	private String addressId;
	
	
	private String Appointment_id;
	private String Appointment_name;
	
	
	public String getAppointment_id() {
		return Appointment_id;
	}
	public void setAppointment_id(String appointment_id) {
		Appointment_id = appointment_id;
	}
	public String getAppointment_name() {
		return Appointment_name;
	}
	public void setAppointment_name(String appointment_name) {
		Appointment_name = appointment_name;
	}
	public String getGoods_logo() {
		return goods_logo;
	}
	public void setGoods_logo(String goods_logo) {
		this.goods_logo = goods_logo;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getGoods_money() {
		return goods_money;
	}
	public void setGoods_money(String goods_money) {
		this.goods_money = goods_money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
