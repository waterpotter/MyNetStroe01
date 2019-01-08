package com.power.using.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.power.using.common.Page;
import com.power.using.constant.Constants;
import com.power.using.domian.Book;
import com.power.using.domian.Category;
import com.power.using.domian.Customer;
import com.power.using.service.BusinessServices;
import com.power.using.service.impl.BusinessServiceImpl;
import com.power.using.util.WebUtil;
import com.power.using.web.beans.Cart;
import com.power.using.web.beans.CartItem;

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
		}else if("buy".equals(op)){
			buy(request,response);
		}else if("changeNum".equals(op)){
			changeNum(request,response);
		}else if("delOneItem".equals(op)){
			delOneItem(request,response);
		}else if("delAllItem".equals(op)){
			delAllItem(request,response);
		}else if("registCustomer".equals(op)){
			registCustomer(request,response);
		}else if("loginCustomer".equals(op)){
			loginCustomer(request,response);
		}else if("logoutCustomer".equals(op)){
			logoutCustomer(request,response);
		}
		
	}

	/**
	 * 用户注销
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logoutCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.getSession().removeAttribute(Constants.CUSTOMER_LOGIN_FLAG);
		
		response.sendRedirect(request.getContextPath());
		
		
	}

	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loginCustomer(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Customer c = s.customerLogin(username, password);
		request.getSession().setAttribute(Constants.CUSTOMER_LOGIN_FLAG, c);
		response.sendRedirect(request.getContextPath());
		
	
	}


	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void registCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Customer c = WebUtil.fillBean(request, Customer.class);
		s.addCustomer(c);
		response.getWriter().write("注册成功!2秒后跳转主页");
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
		
	
	}

	/**
	 * 删除所有条目
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delAllItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().removeAttribute(Constants.HTTPSESSION_CART);
		
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}

	/**
	 * 删除一个条目
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delOneItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String bookId = request.getParameter("bookId");
		Cart cart=(Cart) request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		cart.getItems().remove(bookId);
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	
	
	}

	/**
	 * 购物车改变数量后,交由服务器改变相应的书库
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void changeNum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String num = request.getParameter("num");
		String bookId = request.getParameter("bookId");
		
		Cart cart=(Cart) request.getSession().getAttribute(Constants.HTTPSESSION_CART);
		
		CartItem item = cart.getItems().get(bookId);
		item.setQuantity(Integer.parseInt(num));
		
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
		
	
	
	}

	/**
	 * 把书籍添加进购物车
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		/*
		 * 为保证购物车只有一个,当使用的时候没有的话new一个,保存起来;有的话,就是用已经存在的
		 * 这里放到session中
		 * 原理:单例
		 * */
		HttpSession session = request.getSession();
		Cart cart=(Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		if(cart==null){
			cart = new Cart();
			session.setAttribute(Constants.HTTPSESSION_CART, cart);
		}
		
		//购物车存在
		cart.addBook(book);
		//现在做的是同步处理,同步处理,处理完,都需要考虑数据返回在页面上的操作
		//异步的话,则不用考虑
		//此处先不用异步
		//在添加完购物车后,跳转到主页面
		response.getWriter().write("添加成功!2秒后跳转主页");
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
		
	
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
