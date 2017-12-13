package com.skyfilm.owner.bean.mine;

import java.io.Serializable;
import java.util.List;

/**
 * 成为设计师bean
 * 
 * @author min.yuan
 *
 */
public class BecomeStylist implements Serializable{
	private String real_name;
	private String real_phoneNum;
	private String real_email;
	private String des;
	private String qq;
	private String wx;
	private String wb;
	private String design_des;
	private List<String> design;
	private List<String> files;
	
	
	public List<String> getDesign() {
		return design;
	}
	public void setDesign(List<String> design) {
		this.design = design;
	}
	public String getDesign_des() {
		return design_des;
	}
	public void setDesign_des(String design_des) {
		this.design_des = design_des;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getReal_phoneNum() {
		return real_phoneNum;
	}
	public void setReal_phoneNum(String real_phoneNum) {
		this.real_phoneNum = real_phoneNum;
	}
	public String getReal_email() {
		return real_email;
	}
	public void setReal_email(String real_email) {
		this.real_email = real_email;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWx() {
		return wx;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}
	public String getWb() {
		return wb;
	}
	public void setWb(String wb) {
		this.wb = wb;
	}
	
	
}
