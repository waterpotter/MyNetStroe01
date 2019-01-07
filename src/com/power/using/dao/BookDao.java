package com.power.using.dao;

import java.util.List;

import com.power.using.domian.Book;

public interface BookDao {

	/**
	 * 保存书的数据
	 * @param book
	 */
	void save(Book book);

	/**
	 * 根据书的id查询书
	 * 书的分类查询
	 * @param bookId
	 * @return
	 */
	Book findBookId(String bookId);

	/**
	 * 获取总共的记录条数
	 * @return
	 */
	int getTotalRecordsNum();

	
	/**
	 * 根据其实条数和页码,查找记录条数
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List findPageRecords(int startIndex, int pageSize);

}
