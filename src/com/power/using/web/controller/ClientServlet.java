package com.power.using.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.power.using.common.Page;
import com.power.using.domian.Book;
import com.power.using.domian.Category;
import com.power.using.service.BusinessServices;
import com.power.using.service.impl.BusinessServiceImpl;

public class ClientServlet extends HttpServlet {

	private BusinessServices s=new BusinessServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op = request.getParameter("op");
		
		if("showIndex".equals(op)){
			showIndex(request,response);
		}else if("showCategoryBooks".equals(op)){
			showCategoryBooks(request,response);
		}else if("showBookDetial".equals(op)){
			showBookDetial(request,response);
		}
		
	}

	/**
	 * 展示书籍详细信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showBookDetial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/bookDetial.jsp").forward(request, response);
	
	}

	/**
	 * 按照分类查询分页信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showCategoryBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		
		String num = request.getParameter("num");
		
		String categoryId = request.getParameter("categoryId");
		
		Page page=s.findBookPageRecords(num,categoryId);
	
		page.setUrl("/client/ClientServlet?op=showCategoryBooks&categoryId="+categoryId);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
	 
	
	
	}

	/**
	 * 展示主页
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		//查询除所有分类
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//查询所有书籍
		String num = request.getParameter("num");
		Page page = s.findBookPageRecords(num);
		page.setUrl("/client/ClientServlet?op=showIndex");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/listBooks.jsp").forward(request, response);
	 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
		
	}

}
