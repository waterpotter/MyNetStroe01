package com.power.using.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.power.using.common.Page;
import com.power.using.domian.Book;
import com.power.using.domian.Category;
import com.power.using.service.BusinessServices;
import com.power.using.service.impl.BusinessServiceImpl;
import com.power.using.util.IdGenertor;
import com.power.using.util.WebUtil;

public class ManageServlet extends HttpServlet {

	private BusinessServices s = new BusinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op = request.getParameter("op");
		if ("addCategory".equals(op)) {
			addCategory(request, response);
		} else if ("showAllCategory".equals(op)) {
			showAllCategory(request, response);
		} else if ("addBookUI".equals(op)) {
			addBookUI(request, response);
		} else if ("addBook".equals(op)) {
			addBook(request, response);
		}else if("showPageBooks".equals(op)){
			showPageBooks(request,response);
		}

	}

	/**
	 * 查询图书记录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showPageBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String num = request.getParameter("num");//用户要看的页码
		
		Page page = s.findBookPageRecords(num);
		page.setUrl("/manage/ManageServlet?op=showPageBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
		
		
	}

	/**
	 * 添加书籍操作
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			throw new RuntimeException("上传文件表单的类型不是multipart/form-data");
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			// 将request中的表单数据取出,
			items = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Book book = new Book();
		for (FileItem item : items) {
			// 普通字段:把数据封装到book对象中
			if (item.isFormField()) {
				processFormFiled(item, book);
			} else {
				// 上传字段:上传
				processUploadFiled(item, book);
			}
		}
		
		//书籍分类
	//	book.setCategory(s.findCategoryById(request.getParameter("categoryId")));
		
		//把书籍信息保存到数据库中
		s.addBook(book);
		
		response.sendRedirect(request.getContextPath()+"/common/message.jsp");

	}

	/**
	 * 上传字段:上传
	 * 
	 * @param item
	 */
	private void processUploadFiled(FileItem item, Book book) {
		// 存放路径
		String storeDirectory = getServletContext().getRealPath("/images");
		File rootDirectory = new File(storeDirectory);
		if (!rootDirectory.exists()) {
			rootDirectory.mkdirs();
		}
		String filename = item.getName();
		if (filename != null) {
			// 搞拓展名
			filename = IdGenertor.genGUID() + "." + FilenameUtils.getExtension(filename);
			book.setFilename(filename);
		}

		// 计算子目录
		String path = genChildDirectory(storeDirectory, filename);
		book.setPath(path);
		
		//处理文件上传
		try {
			item.write(new File(rootDirectory,path+"/"+filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 使用文件名的hashcode值
	private String genChildDirectory(String realPath, String fileName) {

		int hashCode = fileName.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode & 0xf0) >> 4;

		String str = dir1 + File.separator + dir2;
		File file = new File(realPath,str);
		if (!file.exists()) {
			file.mkdirs();
		}
		return str;
	}

	/**
	 * 普通字段:把数据封装到book对象中
	 * 
	 * @param item
	 * @param book
	 */
	private void processFormFiled(FileItem item, Book book) {
		try {
			/*
			 * 这段代码在循环内 每次获取一个字段以及每个字段对应的value 然后通过beanutils框架将数据放进book对象中
			 */
			String fieldName = item.getFieldName();
			String filedValue = item.getString("UTF-8");

			BeanUtils.setProperty(book, fieldName, filedValue);
			
			if("categoryId".equals(fieldName)){
				book.setCategory(s.findCategoryById(filedValue));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 跳转到添加书籍页面
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addBookUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);

		request.getRequestDispatcher("/manage/addBooks.jsp").forward(request, response);
	}

	/**
	 * 查询所有分类
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		// 将数据显示到另一个页面,使用转发
		// 转发是服务器行为,用 "/"
		// 重定向是浏览器行为,用 request.getContextPath()
		request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);

	}

	/**
	 * 添加分类
	 * 
	 * @param request
	 * @param response
	 */
	private void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Category c = WebUtil.fillBean(request, Category.class);
		s.addCategory(c);

		// 现在开发为先为同步开发--->保存结束后,跳转到一个页面
		// 使用重定向,跳转过去,刷新的是message,不会重复提交addCategory页面数据
		response.sendRedirect(request.getContextPath() + "/common/message.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
