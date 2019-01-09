package com.power.using.domian;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private String id;
	private int quantity;
	private float price;
	
	//多个订单项对应一本书
	private Book book;
	
	//多个订单项对应一个订单
	private Order order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
