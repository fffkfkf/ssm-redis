package com.dell.anli04_serviceimple;


import com.dell.anli03_service.AdminService;
import com.dell.anli05_dao.AdminDao;
import com.dell.anli07_domain.Product;
import com.dell.anli07_domain.Product2;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyBeanFactory;
import com.dell.anli08_util.MyJedisUtils;
import com.dell.anli08_util.Pager;
import com.dell.anli08_util.UUIDUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AdminServiceImpl implements AdminService {
	//1:管理员登录的方法
	@Override
	public User login(String un, String ps) throws SQLException {
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		return ad.login(un,ps);
	}
	//2:删除一个分类信息的方法
    @Override
	public String delCategoryByCid(String cid) throws Exception {
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		//先查,后删
		int i=ad.findProductCountByCid(cid);
		if(i>0){
			return "分类下的商品不为空,无法删除该分类,请先手动删除商品,然后再删该分类!";
		}
		ad.delCategoryByCid(cid);
		//清空redis的缓存
		Jedis jedis = MyJedisUtils.getJedis();
		jedis.del("clist");
		jedis.close();
		return "删除成功!";
	}
	////2:添加一个分类信息的方法
    @Override
	public void saveCategory(String cname) throws SQLException {
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		ad.saveCategory(UUIDUtils.getId(),cname);
		////清空redis的缓存
		Jedis jedis = MyJedisUtils.getJedis();
		jedis.del("clist");
		jedis.close();
	}
	//3:根据分类编号修改分类信息的方法
    @Override
	public void updateCategory(String cid, String cname) throws SQLException {
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		ad.updateCategory(cid,cname);
		////清空redis的缓存
		Jedis jedis = MyJedisUtils.getJedis();
		jedis.del("clist");
		jedis.close();
	}
	//4:根据分页查询商品列表信息,封装成一个分页对象(Pager)
    @Override
	public Pager<Product2> findProductByPageNumberAjax(String pageNumber, String pageSize) throws SQLException {
		/*
		 * 1:从数据库中查询所有商品总数量;
		 * 2:根据当前页,每页显示数量,查询商品集合;
		 * 3:将总数量和list集合分别保存到Pager对象中
		 * 4:返回装满了数据的对象
		 */
		//1:从数据库中查询所有商品总数量;
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		int c=ad.findProductTotalCount();
		//计算起始索引
		int i=Integer.parseInt(pageNumber);
		int j=Integer.parseInt(pageSize);
		//2:根据当前页,每页显示数量,查询商品集合;
		List<Product2> list=ad.findProductListByPageNumber((i-1)*j,j);
		//3:将总数量和list集合分别保存到Pager对象中
		Pager<Product2> p = new Pager<Product2>();
		p.setTotal(c);
		p.setRows(list);
		//4:返回装满了数据的对象
		return p;
	}
	//添加商品信息到数据库中
@Override
	public void saveProduct(Product p) throws SQLException {
		/*
		 * 1:给商品补全信息
		 * 2:调dao操作数据库
		 */
		p.setMarket_price(p.getShop_price()+100);
		p.setPdate(new Date());
		p.setPid(UUIDUtils.getId());
		AdminDao ad = (AdminDao)MyBeanFactory.getBean("AdminDao");
		ad.saveProduct(p);
	}

}
