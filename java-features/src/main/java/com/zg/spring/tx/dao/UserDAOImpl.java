package com.zg.spring.tx.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zg.spring.tx.pojo.Book;
import com.zg.spring.tx.pojo.User;

public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);
	private JdbcOperations jdbcTempl; //solution 1.
//	private JdbcTemplate jdbcTempl; //solution 2. another way for tx management.
	private RowMapper<User> mapper = (ResultSet rs, int rowNum) -> {
		User b = new User();
		b.setId(rs.getInt("id"));
		b.setUsername(rs.getString("username"));
		b.setPassword(rs.getString("password"));
		b.setSex(rs.getBoolean("sex"));
		b.setAge(rs.getInt("age"));
		b.setLastLoginTime(rs.getString("lastLoginTime"));
		Book book = new Book();
		book.setId(rs.getInt("bookId"));
		b.setBook(book);
		return b;
	};
	
	//solution 1.
	public JdbcOperations getJdbcTempl() {
		return jdbcTempl;
	}
	public void setJdbcTempl(JdbcOperations jdbcTempl) {
		this.jdbcTempl = jdbcTempl;
	}
	public UserDAOImpl() {
	}

	//solution 2.
	public UserDAOImpl(DataSource dataSource) {
		jdbcTempl = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean insertUser(User db) {
		int rsl = jdbcTempl.update("insert into user(username,password,age,sex,lastLoginTime,bookId) values(?,?,?,?,?,?)",db.getUsername(),db.getPassword(),db.getAge(),db.isSex(),db.getLastLoginTime(),db.getBook().getId());
		logger.debug("insert data record: "+rsl);
		return rsl >= 1;
	}

	@Override
	public boolean deleteUser(User db) {
		int rsl = jdbcTempl.update("delete from user where bookId = ? and username = ?", db.getBook().getId(),db.getUsername());
		return rsl == 1;
	}

	@Override
	public boolean updateUser(User db) {
		int result = jdbcTempl.update("update user set username=?, password=?, age=?,lastLoginTime=? where id = ?", db.getUsername(),db.getPassword(),db.getAge(),db.getLastLoginTime(),db.getId());
		return result == 1;
	}

	@Override
	public User queryByBookId(int id) {
		return jdbcTempl.queryForObject("select * from user where bookId = ?",new Object[]{id}, mapper);
	}

	@Override
	public List<User> queryByName(String username) {
		return jdbcTempl.query("select * from user where username = ?",new Object[]{username},mapper);
	}

	@Override
	public List<User> queryAll() {
		return jdbcTempl.query("select * from user", mapper);
	}

}
