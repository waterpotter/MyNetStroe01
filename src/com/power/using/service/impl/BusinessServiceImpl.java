package com.power.using.service.impl;

import java.util.List;

import com.power.using.common.Page;
import com.power.using.dao.BookDao;
import com.power.using.dao.CategoryDao;
import com.power.using.dao.CustomerDao;
import com.power.using.dao.OrderDao;
import com.power.using.dao.impl.BookDaoImpl;
import com.power.using.dao.impl.CategoryDaoImpl;
import com.power.using.dao.impl.CustometDaoImpl;
import com.power.using.dao.impl.OrderDaoImpl;
import com.power.using.domian.Book;
import com.power.using.domian.Category;
import com.power.using.domian.Customer;
import com.power.using.domian.Order;
import com.power.using.domian.OrderItem;
import com.power.using.service.BusinessServices;
import com.power.using.util.IdGenertor;

public class BusinessServiceImpl implements BusinessServices {

	private CategoryDao categoryDao=new CategoryDaoImpl();
	
	private BookDao bookDao=new BookDaoImpl();
	
	private CustomerDao customerDao=new CustometDaoImpl();
	
	private OrderDao orderDao=new OrderDaoImpl();
	
	@Override
	public void addCategory(Category c) {
		c.setId(IdGenertor.genGUID());
		categoryDao.save(c);
	}

	@Override
	public List<Category> findAllCategories() {

		return categoryDao.findAll();
	}

	@Override
	public Category findCategoryById(String categoryId) {

		return categoryDao.findById(categoryId);
	}

	@Override
	public void addBook(Book book) {
		
		if(book==null){
			throw new IllegalArgumentException("book can't be null");
		}
		if(book.getCategory()==null){
			throw new IllegalArgumentException("Category can't be null");
		}
		
		book.setId(IdGenertor.genGUID());
		bookDao.save(book);
		
	}

	@Override
	public Book findBookById(String bookId) {
		
		return bookDao.findBookId(bookId);
	}

	@Override
	public Page findBookPageRecords(String num) {
		int pageNum=1;
		if(num!=null&&!"".equals(num)){
			pageNum=Integer.parseInt(num);
		}
		
		int totalRecordsNum=bookDao.getTotalRecordsNum();
		
		Page page = new Page(pageNum,totalRecordsNum);
		
		List records=bookDao.findPageRecords(page.getStartIndex(),page.getPageSize());
		
		page.setRecords(records);
		
		return page;
	}

	@Override
	public Page findBookPageRecords(String num, String categoryId) {
		
		int pageNum=1;
		if(num!=null&&!"".equals(num)){
			pageNum=Integer.parseInt(num);
		}
		
		int totalRecordsNum=bookDao.getTotalRecordsNum(categoryId);
		
		Page page = new Page(pageNum,totalRecordsNum);
		
		List records=bookDao.findPageRecords(page.getStartIndex(),page.getPageSize(),categoryId);
		
		page.setRecords(records);
		
		return page;
	}

	@Override
	public void addCustomer(Customer c) {
		c.setId(IdGenertor.genGUID());
		customerDao.save(c);
		
	}

	@Override
	public Customer fingCustomer(String customerId) {
		
		return customerDao.findOne(customerId);
	}

	@Override
	public Customer customerLogin(String username, String password) {
		
		return customerDao.find(username,password);
	}

	@Override
	public void genOrder(Order o) {
		
		if(o.getCustomer()==null){
			throw new IllegalArgumentException("订单所属客户信息不存在");
		}
		if(o.getItems()==null||o.getItems().size()==0){
			throw new IllegalArgumentException("订单的订单项不存在,没必要生成订单");
		}
	
		orderDao.save(o);
		
		
	}

	@Override
	public Order findOrderByNum(String ordernum) {
		
		return orderDao.findByNum(ordernum);
	}

	@Override
	public List<Order> findCustomerOrders(Customer c) {
		
		return orderDao.findByCustomer(c.getId());
	}

	@Override
	public void changeOrderStatus(Order order) {
		orderDao.updateStatus(order);
		
	}

}
