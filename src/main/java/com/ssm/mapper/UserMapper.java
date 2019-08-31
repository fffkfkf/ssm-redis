package com.ssm.mapper;


import java.util.List;

import com.ssm.pojo.User;

public interface UserMapper {

    List<User> list();

    User selectById(Integer id);

//    int countByExample(UserExample example);
//
//    int deleteByExample(UserExample example);
//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(User record);
//
//    int insertSelective(User record);
//
//    List<User> selectByExample(UserExample example);
//
//    User selectByPrimaryKey(Integer id);
//
//    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
//
//    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
//
//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}