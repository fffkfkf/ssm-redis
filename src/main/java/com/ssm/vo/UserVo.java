package com.ssm.vo;

import java.util.Date;

import com.ssm.pojo.User;
/**
 * 扩展类  一般用作非数据库字段属性查询
 * 比如：查询条件是用户名或学号，搜索框输入某个字段（这个字段就放在扩展类，这样在xml的sql里面就可以写）
 * 举例：输入框内容取名为content
 *      table.username like '%${content}%'  or .....  $符号安全性不好   有兴趣的同学可以去深入研究一个sql注入问题
 * @author Administrator
 * 
 */
public class UserVo {
private User user;
private String content;
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}


}
