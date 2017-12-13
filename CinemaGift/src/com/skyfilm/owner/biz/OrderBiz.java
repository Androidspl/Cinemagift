package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.mine.CrowdFunding;
import com.skyfilm.owner.bean.mine.Order;
import com.skyfilm.owner.bean.mine.OrderDetails;
import com.skyfilm.owner.communication.mine.MyCollectMoneyCom;
import com.skyfilm.owner.communication.mine.OrderCom;
import com.skyfilm.owner.communication.mine.OrderDetailsCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class OrderBiz {
	private OrderCom orderCom;
	private MyCollectMoneyCom myCollectMoneyCom;
	private OrderDetailsCom orderDetailsCom;
	public OrderBiz() {
		orderCom = (OrderCom) CsqManger.getInstance().get(Type.ORDERCOM);
		myCollectMoneyCom = (MyCollectMoneyCom) CsqManger.getInstance().get(Type.MYCOLLECTMONEYCOM);
		orderDetailsCom = (OrderDetailsCom) CsqManger.getInstance().get(Type.ORDERDETAILSCOM);
	}
	/**
	 * 获取订单列表
	 * @param User_id
	 * @param Order_status
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
//	public List<Order> getOrderList(String User_id, String Order_status, String Page, String Page_size) throws CsqException{
		public List<Order> getOrderList(String Page, String Page_size) throws CsqException{
		return orderCom.getOrderList(Page, Page_size);
	}
	/**
	 * 我参与的众筹列表
	 * @param User_id
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	public List<CrowdFunding> getMyCollectMoneyList(String User_id,String Page, String Page_size) throws CsqException{
		return myCollectMoneyCom.getMyCollectMoneyList(User_id, Page, Page_size);
	}
	/**
	 * 取消订单
	 * @param User_id
	 * @param Order_id
	 * @return
	 * @throws CsqException
	 */
	public String cancelOrder(String User_id,String Order_id)throws CsqException{
		return orderCom.cancelOrder(User_id, Order_id);
	}
	/**
	 * 确认收货
	 * @param User_id
	 * @param Order_id
	 * @return
	 * @throws CsqException
	 */
	public String confirmOrder(String User_id,String Order_id)throws CsqException{
		return orderCom.confirmOrder(User_id, Order_id);
	}
	/**
	 * 订单评价
	 * @param User_id
	 * @param Order_id
	 * @param Staus
	 * @param Content
	 * @return
	 * @throws CsqException
	 */
	public String commentOrder(String User_id,String Order_id,String Staus,String Content)throws CsqException{
		return orderCom.commentOrder(User_id, Order_id, Staus, Content);
	}
	/**
	 * 获取订单详情
	 * @param Order_id
	 * @return
	 * @throws CsqException
	 */
	public OrderDetails getOrderDetails(List<String> standard,String user_id,String money) throws CsqException {
		return orderDetailsCom.getOrderDetails(standard,user_id,money);
	}
}
