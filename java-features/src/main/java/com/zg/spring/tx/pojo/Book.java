package com.zg.spring.tx.pojo;

public class Book{
	
	private double price;
	private String publishedDate;
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return String.format("{id:%s,name:%s,price:%f,publishedDate:%s}", id, name, price, publishedDate);
	}
	
	
	
}
