package com.zg.spring.tx.dao;

import java.util.List;

import com.zg.spring.tx.pojo.User;

public interface UserDAO{
	public boolean insertUser(User db);
	public boolean deleteUser(User db);
	public boolean updateUser(User db);
	public User queryByBookId(int id);
	public List<User> queryByName(String username);
	public List<User> queryAll();
}
