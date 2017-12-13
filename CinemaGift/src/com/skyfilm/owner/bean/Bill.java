package com.skyfilm.owner.bean;

import java.util.Date;

/**
 * 账单的实体类
 * @author AL
 *
 */
public class Bill {
	/**
	 * 账单总费用
	 */
	private double totalFee;
	/**
	 * 账单生成时间
	 */
	private Date createTime;
	/**
	 * 账单缴纳时间
	 */
	private Date paymentTime;

	/**
	 * 缴费状态
	 */
	private String status ;

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
