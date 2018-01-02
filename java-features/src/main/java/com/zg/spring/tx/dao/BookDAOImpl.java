package com.zg.spring.tx.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zg.spring.tx.pojo.Book;

public class BookDAOImpl implements BookDAO {

	private JdbcOperations jdbcTempl; //solution 1.
//	private JdbcTemplate jdbcTempl; //solution 2, another way for tx management.
	private RowMapper<Book> mapper = (ResultSet rs, int rowNum)->{
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setName(rs.getString("name"));
		b.setPrice(rs.getDouble("price"));
		b.setPublishedDate(rs.getString("publishedDate"));
		return b;
	};
	

	//solution 1.
	public void setJdbcTempl(JdbcOperations jdbcTempl) {
		this.jdbcTempl = jdbcTempl;
	}
	public JdbcOperations getJdbcTempl() {
		return jdbcTempl;
	}
	public BookDAOImpl() {
	}
	
	//solution 2.
	public BookDAOImpl(DataSource dataSource) {
		jdbcTempl = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean insert(Book db) {
		int rsl = jdbcTempl.update("insert into book (id,name,price,publishedDate) values(?,?,?,?)", db.getId(),db.getName(),db.getPrice(),db.getPublishedDate());
		System.out.println("insert data record: "+rsl);
		return rsl >= 1;
	}

	@Override
	public boolean deleteById(int id) {
		int result = jdbcTempl.update("delete from book where id = ?", id);
		return result == 1;
	}

	@Override
	public List<Book> queryById(int id) {
		return jdbcTempl.query("select * from book where id = ?",new Object[]{id}, mapper);
	}

	@Override
	public List<Book> queryByName(String name) {
		return jdbcTempl.query("select * from book where name = ?",new Object[]{name}, mapper);
	}

	@Override
	public boolean update(Book db) {
		int result = jdbcTempl.update("update book set name=?,price=?,publishedDate=? where id = ?", db.getName(),db.getPrice(),db.getPublishedDate(),db.getId());
		return result == 1;
	}

	@Override
	public List<Book> queryAll() {
		return jdbcTempl.query("select * from book", mapper);
	}

}
