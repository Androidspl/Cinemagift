package com.skyfilm.owner.dao;

import com.skyfilm.owner.user.User;
import com.skyfilm.owner.user.UserDao;
import com.skyfilm.owner.utils.GsonUtil;
import com.skyfilm.owner.utils.SharedPrefUtil;
import com.skyfilm.owner.utils.StringUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean saveUser(User u) {
			if(u != null){
				SharedPrefUtil.setMsg("user", StringUtil.isNull(u.getUser_id())?"":u.getUser_id(), GsonUtil.gson().toJson(u));
				return true;
			}
		return false;
	}

	@Override
	public User getUserById(String userId) {
		String result = SharedPrefUtil.getMsg("user", userId==null?"":userId);
		return GsonUtil.gson().fromJson(result, User.class);
	}

	@Override
	public boolean updateUser(User u) {
			if(u !=null){
				SharedPrefUtil.setMsg("user", StringUtil.isNull(u.getUser_id())?"":u.getUser_id(), GsonUtil.gson().toJson(u));
				return true;
			}
		return false;
	}

	@Override
	public boolean isExists(String userId) {
		String result = SharedPrefUtil.getMsg("user", userId);
		if(result !=null){
			return true;
		}
		return false;
	}

}
