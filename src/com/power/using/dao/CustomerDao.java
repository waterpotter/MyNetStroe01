package com.power.using.dao;

import com.power.using.domian.Customer;

public interface CustomerDao {

	void save(Customer c);

	Customer findOne(String customerId);

	Customer find(String username, String password);

}
