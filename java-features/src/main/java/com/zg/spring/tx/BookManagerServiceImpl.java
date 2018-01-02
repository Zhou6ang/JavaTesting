package com.zg.spring.tx;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zg.spring.tx.dao.BookDAO;
import com.zg.spring.tx.dao.UserDAO;
import com.zg.spring.tx.pojo.Book;
import com.zg.spring.tx.pojo.User;

public class BookManagerServiceImpl implements BookManagerService {
	private static final Logger logger = LogManager.getLogger(BookManagerServiceImpl.class);
	private UserDAO userDAO;
	private BookDAO bookDAO;
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public BookDAO getBookDAO() {
		return bookDAO;
	}

	public void setBookDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public List<Book> getBookById(int id){
		return bookDAO.queryById(id);
	}
	
	public List<Book> getAllBooks(){
		return bookDAO.queryAll();
	}
	
	public List<Book> getBooksByName(String name){
		return bookDAO.queryByName(name);
	}

	@Override
	public boolean createBook(User user) {
		boolean rsl = userDAO.insertUser(user);
		return rsl && bookDAO.insert(user.getBook());
	}

	@Override
	public boolean dropBook(User user) {
		List<User> list = userDAO.queryByName(user.getUsername());
		for (User u : list) {
			if(u.getPassword().equalsIgnoreCase(user.getPassword())){
				return bookDAO.deleteById(u.getBook().getId()) && userDAO.deleteUser(u);
			}
		}
		
		return false;
	}

	@Override
	public boolean updateBook(User user) {
		List<User> list = userDAO.queryByName(user.getUsername());
		for (User u : list) {
			if(u.getPassword().equalsIgnoreCase(user.getPassword())){
				return bookDAO.update(user.getBook());
			}
		}
		return false;
	}

	public void initDB() throws SQLException{
		try(Connection conn = dataSource.getConnection()){
			String user = "create table if not exists user(id int not null auto_increment primary key, username varchar(20), password varchar(20),age int,sex boolean,lastLoginTime varchar(50),bookId int);";
			String book = "create table if not exists book(id int primary key, name varchar(20),price float, publishedDate varchar(50));";
			boolean result = conn.prepareStatement(user+book).execute();
			logger.debug("created user and book table "+(result?"successfully.":"failed."));
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.queryAll();
	}

	@Override
	public User getUserByBookId(int id) {
		return userDAO.queryByBookId(id);
	}
}
