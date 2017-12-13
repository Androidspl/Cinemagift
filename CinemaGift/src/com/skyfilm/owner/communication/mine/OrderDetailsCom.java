package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.OrderDetails;
import com.skyfilm.owner.exception.CsqException;

public interface OrderDetailsCom {
	/**
	 * 获取订单详情
	 * @param User_id
	 * @param Order_status
	 *            all,hold_pay,hold_shiping,hold_delivery,hold_comment
	 * @param Page
	 * @param Page_size
	 * @return
	 * @throws CsqException
	 */
	OrderDetails getOrderDetails(List<String> standard,String user_id,String money) throws CsqException;
}
