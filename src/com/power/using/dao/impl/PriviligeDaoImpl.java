package com.power.using.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.power.using.dao.PriviligeDao;
import com.power.using.domian.Function;
import com.power.using.domian.Role;
import com.power.using.domian.User;
import com.power.using.util.DBCPUtil;

public class PriviligeDaoImpl implements PriviligeDao {

	private QueryRunner qr=new QueryRunner(DBCPUtil.getDataSource());
	
	@Override
	public User find(String username, String password) {
		
		try {
			User user = qr.query("select * from users where username=? and password=?", new BeanHandler<User>(User.class),username,password);
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	@Override
	public List<Role> findRolesByUser(User user) {
		
		if(user==null||user.getId()==null){
			throw new IllegalArgumentException("用户信息为null");
		}
		
		try {
			List<Role> list = qr.query("select r.* from roles r,user_role ur where r.id=ur.r_id and ur.u_id=?", new BeanListHandler<Role>(Role.class),user.getId());
			
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Function> findFunctionByRole(Role role) {
		
		if(role==null||role.getId()==null){
			throw new IllegalArgumentException("角色信息为null");
		}
		
		try {
			List<Function> list = qr.query("select f.* from functions f,role_function rf where f.id=rf.f_id and rf.r_id=?", new BeanListHandler<Function>(Function.class),role.getId());
			
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
