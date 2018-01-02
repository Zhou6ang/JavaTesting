package com.zg.spring.tx;

import java.util.List;

import com.zg.spring.tx.pojo.Book;
import com.zg.spring.tx.pojo.User;

public interface BookManagerService {

	public List<Book> getBookById(int id);
	
	public User getUserByBookId(int id);
	
	public List<Book> getAllBooks();
	
	public List<User> getAllUsers();
	
	public List<Book> getBooksByName(String name);
	
	public boolean createBook(User user);
	
	public boolean dropBook(User user);
	
	public boolean updateBook(User user);
}
