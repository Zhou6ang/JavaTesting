package com.zg.spring.tx;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zg.spring.tx.pojo.Book;
import com.zg.spring.tx.pojo.User;

public class TestCases_Tx {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public static void main(String[] args) {
		new TestCases_Tx().testQuery();
	}
	
	@Test(expected=RuntimeException.class)
	public void testTransactionRollback() {
		try(ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring-tx-context.xml")){
			BookManagerService svc = (BookManagerService)ctx.getBean("bookManagerService");
			
			Book book = new Book();
			book.setId(1);
			book.setName("Gof design pattern");
			book.setPrice(55.8);
			book.setPublishedDate(LocalDateTime.now().toString());
			User user = new User();
			user.setUsername("demo");
			user.setPassword("demo");
			user.setAge(20);
			user.setLastLoginTime(LocalDateTime.now().toString());
			user.setBook(book);
			System.out.println(svc.createBook(user));
			//create duplicate book which id are same, so create book failed which will cause tx rollback.
			System.out.println(svc.createBook(user));
		}
	}
	
	@Test
	public void testQuery() {
		try(ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring-tx-context.xml")){
			BookManagerService svc = (BookManagerService)ctx.getBean("bookManagerService");
			
			System.err.println("All Books: "+svc.getAllBooks());
			List<User> allUser1 = svc.getAllUsers();
			for (User user2 : allUser1) {
				System.err.println("Drop User by id="+user2.getId()+", result:"+svc.dropBook(user2));
			}
			
			Book book = new Book();
			book.setId(1);
			book.setName("Gof design pattern");
			book.setPrice(55.8);
			book.setPublishedDate(LocalDateTime.now().toString());
			User user = new User();
			user.setUsername("demo");
			user.setPassword("demo");
			user.setAge(20);
			user.setLastLoginTime(LocalDateTime.now().toString());
			user.setBook(book);
			System.err.println("create book:"+svc.createBook(user));
			
			Book upbook = new Book();
			upbook.setId(1);
			upbook.setName("haha");
			upbook.setPrice(122.5);
			upbook.setPublishedDate(LocalDateTime.now().toString());
			User up = new User();
			up.setUsername("demo");
			up.setPassword("demo");
			up.setBook(upbook);
			System.err.println("update book by id = "+upbook.getId()+",result:"+svc.updateBook(up));
			
			List<Book> listB = svc.getAllBooks();
			List<User> listU =  svc.getAllUsers();
			
			System.err.println("All Users: "+listU);
			System.err.println("All Books: "+listB);
			
			int bookId = listB.get(0).getId();
			System.err.println("query book by id="+bookId+",result:"+svc.getBookById(bookId));
			System.err.println("query book by name="+listB.get(0).getName()+",result:"+svc.getBooksByName(listB.get(0).getName()));
			
			User user1 = svc.getUserByBookId(bookId);
			System.err.println("delete book:"+svc.dropBook(user1));
			
			System.err.println("All Books: "+svc.getAllBooks());
			List<User> allUser = svc.getAllUsers();
			System.err.println("All Users: "+allUser);
			for (User user2 : allUser) {
				System.err.println("Drop User by id="+user2.getId()+", result:"+svc.dropBook(user2));
			}
			
			
		}
	}

}
