package com.skyfilm.owner.biz;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.skyfilm.owner.bean.BindUser;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.user.UserCom;
import com.skyfilm.owner.user.UserDao;
import com.skyfilm.owner.utils.AppUtils;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqThreadFactory;
import com.skyfilm.owner.utils.SharedPrefUtil;
import com.skyfilm.owner.utils.StringUtil;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.umeng.socialize.utils.Log;

import android.widget.Toast;

/**
 * 用户相关操作的协同处理（业务类）
 * 
 * @author AL
 *
 */
public class UserBiz {
	private UserCom userCom;
	private UserDao dao;
	private User u;

	public UserBiz() {
		this.userCom = (UserCom) CsqManger.getInstance().get(Type.USERCOM);
		this.dao = (UserDao) CsqManger.getInstance().get(Type.USERDAO);
		this.u = getCurrentUser();
	}

	/**
	 * 保存用户信息
	 * 
	 * @param u
	 * @return
	 */
	private boolean saveUser(User u) {
		if (u != null && dao.saveUser(u)) {
			this.u = u;
			SharedPrefUtil.putUserID(u.getUser_id());
			return true;
		}
		return false;
	}

	/**
	 * 更新一个用户的信息
	 * 
	 * @param u
	 * @return
	 */
	public boolean updateUser(User u) {
		if (u != null) {
			if (dao.updateUser(u)) {
				if (u.getUser_id() == null)
					u.setUser_id("");
				if (this.u != null && u.getUser_id().equalsIgnoreCase(this.u.getUser_id())) {
					this.u = getUserById(u.getUser_id());
				} else if (this.u == null) {
					this.u = u;
				}
				AppUtils.resetTags();
				return true;
			}
		} else if (this.u == null || StringUtil.isNull(this.u.getUser_id())) {
			this.u = u;
			AppUtils.resetTags();
		}
		return false;
	}

	/**
	 * 登录
	 * 
	 * @param Mobile
	 * @param Pwd
	 * @return
	 * @throws CsqException
	 */
	public User login(String Mobile, String Pwd) throws CsqException {
		User u = userCom.login(Mobile, Pwd);
		if (u != null && !StringUtil.isNull(u.getUser_id())) {
			User uT = getUserById(u.getUser_id());
			saveUser(u);
			CsqThreadFactory.getExecutorService().execute(new Runnable() {

				@Override
				public void run() {
					if (getCurrentUserID() != null) {
						// setDevice(getCurrentUserID(),
						// UmengRegistrar.getRegistrationId(MainApplication.getInstance()));
					}
				}
			});
		}
		AppUtils.resetTags();
		return u;
	}

	/**
	 * 三方登录
	 * 
	 * @param Mobile
	 * @param Pwd
	 * @return
	 * @throws CsqException
	 */
	public User bindlogin(String Open_id, String Open_type) throws CsqException {
		User bindlogin = userCom.bindlogin(Open_id, Open_type);
		if (bindlogin != null) {
			saveUser(bindlogin);
		}
		return bindlogin;
	}
	
	private void setCurrentUser(User u) {
		SharedPrefUtil.putUserID(u == null ? "" : u.getUser_id());
		this.u = u;
		if (u != null)
			dao.saveUser(u);
	}

	/**
	 * 注册用户
	 * 
	 * @param Mobile
	 * @param code
	 * @param pwd
	 * @return
	 * @throws CsqException
	 */
	public User valideUser(String Mobile, String Pwd, String Sure_pwd, String Valid_code, String Open_id,
			String Open_type, String Expires_in, String Access_token, String Refresh_token, String Open_pic, String User_name)
					throws CsqException {
		User u = userCom.valideUser(Mobile, Pwd, Sure_pwd, Valid_code, Open_id, Open_type, Expires_in, Access_token,
				Refresh_token, Open_pic, User_name);

		if (u != null) {
			setCurrentUser(u);
		}

		return u;
	}

	/**
	 * 清除当前用户
	 */
	public void clearCurrentUser() {
		setCurrentUser(null);
	}

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		if (u == null) {
			String userId = SharedPrefUtil.getUserID();
			u = getUserById(userId);
		}
		return u;
	}

	/**
	 * 获取当前的用户ID，如果没有登录返回“”
	 * 
	 * @return
	 */
	public String getCurrentUserID() {
		return SharedPrefUtil.getUserID();
	}

	/**
	 * 通过ID 查找获取本地存储的用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId) {
		return dao.getUserById(userId);
	}

	/**
	 * 通过id到服务端获取用户信息
	 * 
	 * @param userId
	 * @param rKey
	 * @param sKey
	 * @return
	 * @throws CsqException
	 */
	public User getUserByIdFromServer(String userId, String rKey, String sKey) throws CsqException {

		return dao.getUserById(userId);
	}

	/**
	 * 获取验证码
	 * 
	 * @param Mobile
	 * @return code
	 * @throws CsqException
	 */
	public String getValidCode(String Mobile) throws CsqException {

		return userCom.getValidCode(Mobile);
	}

	/**
	 * 推送设备与用户的绑定
	 * 
	 * @param bid
	 * @param cid
	 * @param userId
	 * @return
	 * @throws CsqException
	 */
	public boolean bindUser(String bid, String cid, String userId) throws CsqException {
		return userCom.bindUser(bid, cid, userId);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param code
	 * @param newPwd
	 * @param mobile
	 * @return
	 * @throws CsqException
	 */
	public boolean modifyUserPwd(String code, String newPwd, String mobile) throws CsqException {

		return userCom.modifyUserPwd(code, newPwd, mobile);
	}
	
	/**
	 * 找回用户密码
	 * 
	 * @param code
	 * @param newPwd
	 * @param mobile
	 * @return
	 * @throws CsqException
	 */
	public User resetUserPwd(String Mobile, String Pwd, String Sure_pwd, String Valid_code) throws CsqException {
		return userCom.resetUserPwd(Mobile, Pwd, Sure_pwd, Valid_code);
	}

	/**
	 * 反馈建议
	 * 
	 * @param phone
	 * @param content
	 * @param community_id
	 * @param name
	 * @return
	 * @throws CsqException
	 */
	public boolean setAdvice(String phone, String content, String community_id, String name, List<String> files)
			throws CsqException {

		return userCom.setAdvice(phone, content, community_id, name, files);
	}

	/**
	 * 缂栬緫鐢ㄦ埛淇℃伅
	 * 
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

	public User editUserInfo(HashMap<String, String> changes) throws CsqException {
		User u = userCom.editUserInfo(changes);
		if (u != null) {
			updateUser(u);
		}
		return u;
	}

	public boolean isValidCode(String Mobile, String code) throws CsqException {
		return userCom.isValidCode(Mobile, code);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param u
	 * @throws CsqException
	 */
	public void modifyUserInfo(User u) throws CsqException {
		if (userCom.modifyUserInfo(u)) {
			updateUser(u);
		}
	}

	public void setDevice(String userId, String deviceToken) {
		try {
			userCom.setDeviceToken(userId, deviceToken);
		} catch (CsqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 退出
	 * 
	 * @param userId
	 * @throws CsqException
	 */
	public void logout(String userId) throws CsqException {
		// if(userCom.logout(userId)){
		User u = dao.getUserById(userId);
		if (u != null) {
			AppUtils.stopPush(u);
		}
		setCurrentUser(null);
		// }
	}
}
