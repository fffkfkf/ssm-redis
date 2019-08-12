package com.dell.anli03_service;

import com.dell.anli07_domain.User;

import java.sql.SQLException;

/*
 * 业务层的接口
 */
public interface UserService {

	String regist(User u) throws SQLException;

	String activeUser(String code) throws SQLException;

	User findUserByUsername(String username) throws SQLException;

}
