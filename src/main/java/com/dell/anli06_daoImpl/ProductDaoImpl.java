package com.dell.anli06_daoImpl;
/*

import com.itheima.anli05_dao.ProductDao;
import com.itheima.anli07_domain.Product;
import com.itheima.anli08_utils.MyC3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
*/

import com.dell.anli05_dao.ProductDao;
import com.dell.anli07_domain.Product;
import com.dell.anli08_util.MyC3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("all")
public class ProductDaoImpl implements ProductDao {
	//1:查询9个热门商品
	@Override
	public List<Product> findHots9() throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return (List<Product>) q.query("select * from product where is_hot = 1 limit 0,9",new BeanListHandler(Product.class));
	}
	//1:查询9个最新商品
	@Override
	public List<Product> findNews9() throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return (List<Product>) q.query("select * from product order by pdate desc limit 0,9",new BeanListHandler(Product.class));
	}
	//2:查询商品详情的方法
	@Override
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return (Product) q.query("select * from product where pid = ?",new BeanHandler(Product.class),pid);
	}
	//3:根据分类id查询数据库中商品数量;
	@Override
	public int findCountByCid(String cid) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		Long l=(Long) q.query("select count(*) from product where cid = ?",new ScalarHandler(),cid);
		return l.intValue();
	}
	//4:根据起始索引,每页条数,分类id查询当前页商品集合;
	@Override
	public List<Product> findProductListByCidAndPage(int startIndex, int pageSize, String cid) throws SQLException {
		QueryRunner q = new QueryRunner(MyC3P0Utils.getDataSource());
		return (List<Product>) q.query("select * from product where cid = ? limit ?,?",new BeanListHandler(Product.class),cid,startIndex,pageSize);
	}

}
