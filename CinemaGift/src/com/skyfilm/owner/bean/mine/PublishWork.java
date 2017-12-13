package com.skyfilm.owner.bean.mine;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布作品bean
 * @author min.yuan
 *
 */
public class PublishWork {
	private String opus_title;
	private String opus_des;
	private String theme;
	private String type;
	private ArrayList<String> photo;
	private String copyright;//版权
	public String getOpus_title() {
		return opus_title;
	}
	public void setOpus_title(String opus_title) {
		this.opus_title = opus_title;
	}
	public String getOpus_des() {
		return opus_des;
	}
	public void setOpus_des(String opus_des) {
		this.opus_des = opus_des;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<String> getPhoto() {
		return photo;
	}
	public void setPhoto(ArrayList<String> photo) {
		this.photo = photo;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	   
}
