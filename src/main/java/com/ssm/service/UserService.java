package com.ssm.service;

import com.ssm.pojo.User;


public interface UserService {
//User selectById(Integer id);
	
	User selectByPrimaryKey(Integer id);
}
