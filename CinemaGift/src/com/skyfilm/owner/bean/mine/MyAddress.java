package com.skyfilm.owner.bean.mine;

/**
 * 地址管理列表bean
 * 
 * @author min.yuan
 *
 */
public class MyAddress {
//	 "id": "00C18C5C-AB70-4646-8779-6B1858F19D8A", 
//     "user_id": "2a", 
//     "name": "2a", 
//     "address": "1c", 
//     "mobile": "15512364578", 
//     "is_default": "1",  "is_default判断默认地址字段，1默认，2非默认"
//     "create_time": "1468835098", 
//     "status": "1"
	private String id;
	private String user_id;
	private String name;
	private String address;
	private String mobile;
	private String is_default;
	private String create_time;
	private String status;
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIs_default() {
		return is_default;
	}
	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

}