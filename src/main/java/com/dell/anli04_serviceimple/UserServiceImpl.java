package com.dell.anli04_serviceimple;


import com.dell.anli03_service.UserService;
import com.dell.anli05_dao.UserDao;
import com.dell.anli06_daoImpl.UserDaoImple;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.UUIDUtils;

import java.sql.SQLException;

/*
 * 业务层的实现类
 */
public class UserServiceImpl implements UserService {
	//1:用户注册的方法
    @Override
	public String regist(User u) throws SQLException {
		/*
		 * 1:设置用户的主键值;
			2:设置一个激活码;
			3:给用户填写的邮箱发送激活邮件
			4:将用户对象保存到数据库中
		 */
		u.setUid(UUIDUtils.getId());
		//2:设置一个激活码;
		String code = UUIDUtils.getId();
		u.setCode(code);
        //3:给用户填写的邮箱发送激活邮件
        //注释了,需要解开
        //MailUtils.sendNetMail(u.getEmail(),"恭喜您,注册成功,请  <a href='http://localhost/myestore/user?m=activeUser&code="+code+"'>点击激活</a>账户!!!");
        System.out.println("-------------");
		/*} catch (AddressException e) {
			e.printStackTrace();
			return "亲,注册失败!邮箱地址不正确!";*/
        //4:将用户对象保存到数据库中
		UserDao ud =(UserDao) new UserDaoImple();
		ud.regist(u);
		return "注册成功,请登录邮箱激活账户!";
	}
	//2:用户激活的方法
    @Override
	public String activeUser(String code) throws SQLException {
		//调用dao层修改数据库用户状态,如果影响0行,说明重复激活了或激活码无效;
		UserDao ud = (UserDao)new UserDaoImple();
		int i=ud.activeUser(code);
		if(i==0){
			return "激活失败,请不要重复激活,或激活码无效!";
		}else{
			return "激活成功,快快去登录吧!";
		}
	}
	//3:根据用户名查询用户对象的方法
    @Override
	public User findUserByUsername(String username) throws SQLException {
		UserDao ud = (UserDao)new UserDaoImple();
		return ud.findUserByUsername(username);
	}

}
