package com.power.using.service;

import java.util.List;

import com.power.using.common.Page;
import com.power.using.domian.Book;
import com.power.using.domian.Category;

public interface BusinessServices {

	/**
	 * 添加分类
	 * @param c
	 */
	void addCategory(Category c);
	
	/**
	 * 查询所有的分类
	 * @return
	 */
	List<Category> findAllCategories();
	
	/**
	 * 根据id查询分类
	 * @param categoryId
	 * @return
	 */
	Category findCategoryById(String categoryId);
	
	/**
	 * 添加书籍
	 * @param book
	 * 如果book关联的Category为null,要跑出参数错误异常
	 */
	void addBook(Book book); 
	
	/**
	 * 根据id查询一本书
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
	
	/**
	 * 根据用户要查看的页码,返回封装了所有与分页有关的数据
	 * @param num 页码,如果为null或"" ,默认为1
	 * @return
	 */
	Page findBookPageRecords(String num);
	
}