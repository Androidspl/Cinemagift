package com.skyfilm.owner.user;

public class User {
	/**
	 * 用户手机
	 */
	private String user_mobile;
	/**
	 * 用户ID
	 */
	private String user_id;
	/**
	 * 用户TYPE
	 */
	private String type;
	/**
	 * 绑定手机号的用户TYPE
	 */
	private String bind_type;
	/**
	 * 用户USER_PIC
	 */
	private String user_pic;
	/**
	 * 密码
	 */
	private String pass_word;
	/**
	 * 昵称
	 */
	private String nick_name;
	/**
	 * 姓名
	 */
	private String user_name;
	/**
	 * 个性签名
	 */
	private String personality;
	/**
	 * 性别
	 */
	private int gender;
	/**
	 * 生日
	 */
	private String birth_date;

	/**
	 * 头像
	 */

	private String head_pic;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_pic() {
		return user_pic;
	}

	public void setUser_pic(String user_pic) {
		this.user_pic = user_pic;
	}
	
	

	public String getBind_type() {
		return bind_type;
	}

	public void setBind_type(String bind_type) {
		this.bind_type = bind_type;
	}

	@Override
	public String toString() {
		return "User [user_mobile=" + user_mobile + ", user_id=" + user_id + ", type=" + type + ", user_pic=" + user_pic
				+ ", pass_word=" + pass_word + ", nick_name=" + nick_name + ", user_name=" + user_name
				+ ", personality=" + personality + ", gender=" + gender + ", birth_date=" + birth_date + ", head_pic="
				+ head_pic + "]";
	}

	
}
