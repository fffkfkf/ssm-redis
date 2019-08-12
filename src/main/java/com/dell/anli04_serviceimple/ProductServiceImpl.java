package com.dell.anli04_serviceimple;

import com.dell.anli03_service.ProductService;
import com.dell.anli05_dao.ProductDao;
import com.dell.anli06_daoImpl.ProductDaoImpl;
import com.dell.anli07_domain.Product;
import com.dell.anli08_util.PageBean;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
	//1:查询9个热门商品
	public List<Product> findHots9() throws SQLException {
		ProductDao pd =(ProductDao) new ProductDaoImpl();
		return pd.findHots9();
	}

	//1:查询9个最新商品
	public List<Product> findNews9() throws SQLException {
		ProductDao pd =(ProductDao) new ProductDaoImpl();
		return pd.findNews9();
	}
	//2:查询商品详情的方法
	public Product findProductByPid(String pid) throws SQLException {
		ProductDao pd = (ProductDao)new ProductDaoImpl();
		return pd.findProductByPid(pid);
	}
	//3:分页查询商品列表的方法
	public PageBean<Product> findProductListByPage(String pn, String cid) throws SQLException {
		/*
		 * 1:根据当前页码值和固定每页条数(12)创建一个分页对象;
			2:根据分类id查询数据库中商品数量;
			3:将查询的数量保存到分页对象中
			4:根据起始索引,每页条数,分类id查询当前页商品集合;
			5:将集合保存到分页对象;
			6:返回分页对象;
		 */
		//1:根据当前页码值和固定每页条数(12)创建一个分页对象;
		PageBean<Product> pb = new PageBean<Product>(Integer.parseInt(pn),12);
		//2:根据分类id查询数据库中商品数量;
		ProductDao pd = (ProductDao)new ProductDaoImpl();
		int count=pd.findCountByCid(cid);
		//3:将查询的数量保存到分页对象中
		pb.setTotalRecord(count);
		//4:根据起始索引,每页条数,分类id查询当前页商品集合;
		List<Product> li=pd.findProductListByCidAndPage(pb.getStartIndex(),pb.getPageSize(),cid);
		//5:将集合保存到分页对象;
		pb.setData(li);
		//6:返回分页对象;
		return pb;
	}

}
