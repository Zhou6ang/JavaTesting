package com.zg.spring.boot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zg.spring.tx.BookManagerService;
import com.zg.spring.tx.pojo.Book;
import com.zg.spring.tx.pojo.User;

@RestController
public class ServiceController {

	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LogManager.getLogger(ServiceController.class);
	private static ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aop-context.xml");
	
	@RequestMapping(name="books",value={"/bm/books"})
	public Object getAllBooks() throws JsonProcessingException{
		//http://127.0.0.1:8080/bm/books
		logger.info("recieved request for getAllBooks");
		BookManagerService managment = (BookManagerService)ctx.getBean("bookManagerService");
		return mapper.writeValueAsString(managment.getAllBooks());
	}
	
	@RequestMapping(name="users",path={"/bm/users"},headers = { "Content-type=application/json" }, method = RequestMethod.GET)
	public Object getAllUsers() throws JsonProcessingException{
		/**
		 * curl --request GET \
  		 --url http://127.0.0.1:8080/bm/users \
  		--header 'cache-control: no-cache' \
  		--header 'content-type: application/json'
		 */
		logger.info("recieved request for getAllBooks");
		BookManagerService managment = (BookManagerService)ctx.getBean("bookManagerService");
		return mapper.writeValueAsString(managment.getAllUsers());
	}
	
	@RequestMapping(name="createBook",path={"/bm/book/create"}, method = RequestMethod.GET)
	public Object createBook(@RequestParam(name="bookId",required=true)int id,@RequestParam(name="bookname",required=true)String name,@RequestParam(value="price",required=true)double price,@RequestParam(value="username",required=true)String username,@RequestParam(value="password",required=true)String pwd,@RequestParam(defaultValue="100")int age,@RequestParam(defaultValue="man")String sex) throws JsonProcessingException{
		//http://127.0.0.1:8080/bm/book/create?bookId=1&bookname=abc&username=zg&password=zg&price=33.3
		logger.info("recieved request for create book.");
		BookManagerService managment = (BookManagerService)ctx.getBean("bookManagerService");
		Book book = new Book();
		book.setId(id);
		book.setName(name);
		book.setPrice(price);
		book.setPublishedDate(LocalDateTime.now().toString());
		User u = new User();
		u.setAge(age);
		u.setUsername(username);
		u.setPassword(pwd);
		u.setSex(sex.equalsIgnoreCase("man")?true:false);
		u.setBook(book);
		u.setLastLoginTime(LocalDateTime.now().toString());
		
		return managment.createBook(u)?"create book successfully.":"create book failed.";
	}
	
	@RequestMapping(name="updateBook",path={"/bm/book/update"})
	public Object updateBook(@RequestParam(name = "bookId", required = true) int id,
			@RequestParam(name = "bookname", required = false) String name,
			@RequestParam(value = "price", required = false) double price,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String pwd) throws JsonProcessingException {
		//http://127.0.0.1:8080/bm/book/update?bookId=1&bookname=hahah&username=zg&password=zg&price=98.3
		logger.info("recieved request for update book.");
		BookManagerService managment = (BookManagerService)ctx.getBean("bookManagerService");
		List<Book> list = managment.getBookById(id);
		if(list.isEmpty()){
			return "Could not found Book with id:"+id;
		}
		Book old = list.get(0);
		Book book = new Book();
		book.setId(id);
		book.setName((name == null || "".equalsIgnoreCase(name))?old.getName():name);
		book.setPrice((price == 0)?old.getPrice():price);
		book.setPublishedDate(LocalDateTime.now().toString());
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(pwd);
		u.setBook(book);
		u.setLastLoginTime(LocalDateTime.now().toString());
		
		return managment.updateBook(u)?"update book successfully.":"update book failed.";
	}
	
	@RequestMapping(name="deleteBook",path={"/bm/book/delete"})
	public Object deleteBook(@RequestParam(name = "bookId", required = true) int id,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String pwd) throws JsonProcessingException {
		//http://127.0.0.1:8080/bm/book/delete?bookId=1&username=zg&password=zg
		logger.info("recieved request for delete book.");
		BookManagerService managment = (BookManagerService)ctx.getBean("bookManagerService");
		List<Book> list = managment.getBookById(id);
		if(list.isEmpty()){
			return "Could not found book with id:"+id;
		}
		Book book = list.get(0);
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(pwd);
		u.setBook(book);
		
		return managment.dropBook(u)?"delete book successfully.":"delete book failed.";
	}
}
