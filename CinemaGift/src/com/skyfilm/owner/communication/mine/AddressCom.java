package com.skyfilm.owner.communication.mine;

import java.util.HashMap;
import java.util.List;

import com.skyfilm.owner.bean.mine.HomePage;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.exception.CsqException;

/**
 * 地址管理接口
 * @author min.yuan
 *
 */
public interface AddressCom {
//Add_Address，Get_Receiver_Address，Set_Receiver_Address
	/**
	 * 添加收货地址
	 * @param User_id
	 * @param addAddress
	 * @return
	 * @throws CsqException
	 */
	String addAddress(String User_id,HashMap<String, String> addAddress)throws CsqException;
	/**
	 * 设置默认地址
	 * @param Id
	 * @return
	 * @throws CsqException
	 */
	String setReceiverAddress(String User_id,String Id)throws CsqException;
	/**
	 * 删除地址
	 * @param Id
	 * @return
	 * @throws CsqException
	 */
	String setDelAddress(String Id)throws CsqException;
	/**
	 * 获取地址列表
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	List<MyAddress> getMyAddressList(String user_id)throws CsqException;

}
