package com.skyfilm.owner.bean.mine;

/**
 * 我的信息bean
 * 
 * @author min.yuan
 *
 */
public class UserInfo {
	private String pic;
	private String name;
	private String identity;
	private String vip_grade;
	private String attentionNum;
	private String my_fond;
	private String my_collection;
	private String my_attention;
	
	
	public String getMy_fond() {
		return my_fond;
	}
	public void setMy_fond(String my_fond) {
		this.my_fond = my_fond;
	}
	public String getMy_collection() {
		return my_collection;
	}
	public void setMy_collection(String my_collection) {
		this.my_collection = my_collection;
	}
	public String getMy_attention() {
		return my_attention;
	}
	public void setMy_attention(String my_attention) {
		this.my_attention = my_attention;
	}
	public String getVip_grade() {
		return vip_grade;
	}
	public void setVip_grade(String vip_grade) {
		this.vip_grade = vip_grade;
	}
	public String getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(String attentionNum) {
		this.attentionNum = attentionNum;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}

}
