package com.power.using.web.beans;

import java.io.Serializable;

import com.power.using.domian.Book;

public class CartItem implements Serializable {
	
	private Book book;
	
	private int quantity;//本购物项,书的数量
	private float money;//本项小计

	public CartItem(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getMoney() {
		return book.getPrice()*quantity;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public Book getBook() {
		return book;
	}
	
	
	
	
	

}
