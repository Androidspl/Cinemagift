package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFunding;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.bean.mine.OrderDetails;
import com.skyfilm.owner.exception.CsqException;

/**
 * 订单接口
 * 
 * @author min.yuan
 *
 */
public interface OrderCom {
	// My_Order,Cancel_Order，Confirm_Order，Comment_Order，My_Collect_Money
	
	/**
	 * 获取订单列表
	 * @param User_id
	 * @param Order_status
	 *            all,hold_pay,hold_shiping,hold_delivery,hold_comment
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	List<Order> getOrderList(String Page, String Page_size) throws CsqException;
//	List<Order> getOrderList(String User_id, String Order_status, String Page, String Page_size) throws CsqException;
	/**
	 *取消订单
	 * @param User_id
	 * @param Order_id
	 * @return
	 * @throws CsqException
	 */
	String cancelOrder(String User_id,String Order_id)throws CsqException;
	/**
	 * 确认收货
	 * @param User_id
	 * @param Order_id
	 * @return
	 * @throws CsqException
	 */
	String confirmOrder(String User_id,String Order_id)throws CsqException;
	/**
	 * 订单评价
	 * @param User_id
	 * @param Order_id
	 * @param Staus
	 * @param Content
	 * @return
	 * @throws CsqException
	 */
	String commentOrder(String User_id,String Order_id,String Staus,String Content)throws CsqException;
	
}
