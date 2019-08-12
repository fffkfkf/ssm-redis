package com.dell.anli06_daoImpl;
/*

import com.itheima.anli05_dao.UserDao;
import com.itheima.anli07_domain.User;
import com.itheima.anli08_utils.MyC3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
*/

import com.dell.anli05_dao.UserDao;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyC3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImple implements UserDao {
	//1:用户注册的方法
	public void regist(User u) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode()};
		q.update(sql, param);
	}
	//2:用户激活的方法
	public int activeUser(String code) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		String sql = "update user set state =1 ,code = null where code = ?";
		return q.update(sql, code);
	}
	////3:根据用户名查询用户对象的方法
	public User findUserByUsername(String username) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		String sql = "select * from user where username = ?";
		return (User) q.query(sql,new BeanHandler(User.class),username);
	}

}
