package com.dell.anli05_dao;

import com.dell.anli07_domain.User;

import java.sql.SQLException;

public interface UserDao {

	void regist(User u) throws SQLException;

	int activeUser(String code) throws SQLException;

	User findUserByUsername(String username) throws SQLException;

}
