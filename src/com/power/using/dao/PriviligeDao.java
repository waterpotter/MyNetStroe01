package com.power.using.dao;

import java.util.List;

import com.power.using.domian.Function;
import com.power.using.domian.Role;
import com.power.using.domian.User;

public interface PriviligeDao {

	/**
	 * 根据用户名密码查询后台登录
	 * @param username
	 * @param password
	 * @return
	 */
	User find(String username, String password);

	/**
	 * 根据用户查询角色
	 * @param user
	 * @return
	 */
	List<Role> findRolesByUser(User user);

	/**
	 * 根据角色查询功能
	 * @param role
	 * @return
	 */
	List<Function> findFunctionByRole(Role role);

}
