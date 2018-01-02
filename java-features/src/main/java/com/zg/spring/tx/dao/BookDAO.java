package com.zg.spring.tx.dao;

import java.util.List;

import com.zg.spring.tx.pojo.Book;

public interface BookDAO{
	public boolean insert(Book db);
	public boolean deleteById(int id);
	public boolean update(Book db);
	public List<Book> queryById(int id);
	public List<Book> queryByName(String name);
	public List<Book> queryAll();
}
