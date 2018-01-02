package com.zg.spring.tx.pojo;

public class User {
	
	private String username;
	private String password;
	private int age;
	private boolean sex;
	private String lastLoginTime;
	private int id;
	private Book book;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Override
	public String toString() {
		return String.format("{id:%s,username:%s,password:%s,age:%d,sex:%s,lastLoginTime:%s,bookId:%s}", id, username, password,
				age, sex?"man":"woman", lastLoginTime,book.getId());
	}
}
