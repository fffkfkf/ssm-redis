package com.dell.anli06_daoImpl;

import com.dell.anli05_dao.AdminDao;
import com.dell.anli07_domain.Product;
import com.dell.anli07_domain.Product2;
import com.dell.anli07_domain.User;
import com.dell.anli08_util.MyC3P0Utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
	//1:管理员登录的方法

	@Override
	public User login(String un, String ps) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return (User) q.query(sql, new BeanHandler(User.class),un,ps);
	}
	//2:删除一个分类信息的方法
	@Override
	public void delCategoryByCid(String cid) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		q.update("delete from category where cid = ?",cid);
	}
	//3:根据分类id查询商品数量
	@Override
	public int findProductCountByCid(String cid) throws Exception {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Long l = (Long)q.query("select count(*) from product where cid=?",new ScalarHandler(), cid);
		return l.intValue();
	}
	//2:添加一个分类信息的方法
	@Override
	public void saveCategory(String id, String cname) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		q.update("insert into category values (?,?)",id,cname);
	}
	//3:根据分类编号修改分类信息的方法
	@Override
	public void updateCategory(String cid, String cname) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		q.update("update category set cname=? where cid = ?",cname,cid);
	}
	//4:查询商品总数量的方法
	@Override
	public int findProductTotalCount() throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Long l = (Long) q.query("select count(*) from product",new ScalarHandler());
		return l.intValue();
	}
	//5:根据起始索引,查询商品列表集合
	@Override
	public List<Product2> findProductListByPageNumber(int i, int j) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return q.query("select * from product order by pdate desc limit ?,?", new BeanListHandler<>(Product2.class),i,j);
	}
	//6:添加商品信息到数据库中
	@Override
	public void saveProduct(Product p) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Object[] params = {p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getCid()};
		q.update("insert into product values(?,?,?,?,?,?,?,?,?)", params);
	}

}
