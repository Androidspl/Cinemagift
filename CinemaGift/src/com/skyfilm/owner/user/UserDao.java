package com.skyfilm.owner.user;

public interface UserDao {
	/**
	 * 保存用户信息
	 * @param u
	 * @return
	 */
	boolean saveUser(User u);
	/**
	 * 通过ID 获取相应用户
	 * @param userId
	 * @return
	 */
	User getUserById(String userId);

	/**
	 * 更改一个用户
	 * @param u
	 * @return
	 */
	boolean updateUser(User u);

	/**
	 * 该用户ID是否存在
	 * @param userId
	 * @return 
	 */
	boolean isExists(String userId);
}
