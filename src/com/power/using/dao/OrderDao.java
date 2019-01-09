package com.power.using.dao;

import java.util.List;

import com.power.using.domian.Customer;
import com.power.using.domian.Order;

public interface OrderDao {

	/**
	 * 保存订单的基本信息
	 * 还要保存订单关联的订单项信息
	 * @param o
	 */
	void save(Order o);

	/**
	 * 根据订单号查询订单
	 * @param ordernum
	 * @return
	 */
	Order findByNum(String ordernum);

	/**
	 * 根据用户查询订单
	 * 按照日期降序排列
	 * @param c
	 * @return
	 */
	List<Order> findByCustomer(String customerId);

}
