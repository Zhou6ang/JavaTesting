package com.mycompany.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.app.pojo.User;

public interface UserDao  extends JpaRepository<User, Long>{

}
