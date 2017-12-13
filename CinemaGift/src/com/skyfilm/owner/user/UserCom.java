package com.skyfilm.owner.user;


import java.util.HashMap;
import java.util.List;

import com.skyfilm.owner.bean.BindUser;
import com.skyfilm.owner.exception.CsqException;

public interface UserCom {

	/**
	 * 三方登录接口
	 * @param Mobile
	 * @param Pwd
	 * @return
	 * @throws CsqException IOException 及 服务端返回的异常信息
	 */
	User bindlogin(String Mobile,String Pwd)throws CsqException;
	
	/**
	 * 登录接口
	 * @param Mobile
	 * @param Pwd
	 * @return
	 * @throws CsqException IOException 及 服务端返回的异常信息
	 */
	User login(String Open_id, String Open_type)throws CsqException;

	/**
	 * 登出
	 * @param userId
	 * @return
	 * @throws CsqException
	 */

	boolean logout(String userId)throws CsqException;

	/**
	 * 用户验证码验证
	 * @param Mobile
	 * @param code  
	 * @return
	 * @throws CsqException
	 */

	User valideUser(String Mobile, String Pwd, String Sure_pwd, String Valid_code, String Open_id,
			String Open_type, String Expires_in, String Access_token, String Refresh_token, String Open_pic, String User_name)throws CsqException;



	/**
	 * 获取用户信息
	 * @param Mobile
	 * @param code
	 * @return
	 * @throws CsqException
	 */

	User getUserById(String userId,String rKey,String sKey)throws CsqException;


	/**
	 * 获取验证码接口
	 * @param Mobile
	 * @return code
	 * @throws CsqException
	 */
	String getValidCode(String Mobile)throws CsqException;
	
	boolean isValidCode(String Mobile ,String code)throws CsqException;

	/**
	 * 推送的id和用户的绑定
	 * @param bid 推送的userid
	 * @param cid 推送的channelId
	 * @param userId userId
	 * @return
	 * @throws CsqException
	 */
	boolean bindUser(String bid,String cid,String userId)throws CsqException;
	/**
	 * 修改用户密码
	 * @param code 验证码
	 * @param newPwd 新密码
	 * @param userId 用户ID
	 * @return 
	 * @throws CsqException
	 */
	boolean modifyUserPwd(String code,String newPwd,String userId)throws CsqException;
	/**
	 * 忘记用户密码
	 * @param code 验证码
	 * @param newPwd 新密码
	 * @param userId 用户ID
	 * @return 
	 * @throws CsqException
	 */
	User resetUserPwd(String Mobile, String Pwd, String Sure_pwd, String Valid_code)throws CsqException;
	/**
	 * 修改用户信息
	 * @param u 修改后的用户对象
	 * @return
	 * @throws CsqException
	 */
	boolean modifyUserInfo(User u)throws CsqException;
	
	/**
	 * 编辑用户信息 
	 * @param nick_name
	 * @param name
	 * @param gender
	 * @param birth_date
	 * @param default_address
	 * @param id_number
	 * @param head_pic
	 * @return
	 * @throws CsqException
	 */
	User editUserInfo( HashMap<String, String> changes)throws CsqException;
	
	
	void setDeviceToken(String userId,String token)throws CsqException;

	boolean setAdvice(String phone, String content, String community_id, String name, List<String> files)
			throws CsqException;

}
